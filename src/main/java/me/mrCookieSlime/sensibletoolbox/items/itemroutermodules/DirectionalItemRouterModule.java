package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Directional;
import org.bukkit.material.Wool;

import me.desht.dhutils.ItemNames;
import me.mrCookieSlime.sensibletoolbox.api.Filtering;
import me.mrCookieSlime.sensibletoolbox.api.STBInventoryHolder;
import me.mrCookieSlime.sensibletoolbox.api.SensibleToolbox;
import me.mrCookieSlime.sensibletoolbox.api.gui.DirectionGadget;
import me.mrCookieSlime.sensibletoolbox.api.gui.FilterTypeGadget;
import me.mrCookieSlime.sensibletoolbox.api.gui.GUIUtil;
import me.mrCookieSlime.sensibletoolbox.api.gui.InventoryGUI;
import me.mrCookieSlime.sensibletoolbox.api.gui.ToggleButton;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBBlock;
import me.mrCookieSlime.sensibletoolbox.api.util.Filter;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.api.util.VanillaInventoryUtils;
import me.mrCookieSlime.sensibletoolbox.blocks.EnderBox;
import me.mrCookieSlime.sensibletoolbox.blocks.ItemRouter;
import me.mrCookieSlime.sensibletoolbox.util.UnicodeSymbol;

public abstract class DirectionalItemRouterModule extends ItemRouterModule implements Filtering, Directional {
	
    private static final String LIST_ITEM = ChatColor.LIGHT_PURPLE + UnicodeSymbol.CENTERED_POINT.toUnicode() + " " + ChatColor.AQUA;
    private static final ItemStack WHITE_BUTTON = GUIUtil.makeTexture(
            new Wool(DyeColor.WHITE), ChatColor.RESET.toString() + ChatColor.UNDERLINE + "白名单模式",
            "模块只处理", "与筛选器匹配的物品."
    );
    private static final ItemStack BLACK_BUTTON = GUIUtil.makeTexture(
            new Wool(DyeColor.BLACK), ChatColor.RESET.toString() + ChatColor.UNDERLINE + "黑名单模式",
            "模块不会处理", "筛选器中标记的物品."
    );
    private static final ItemStack OFF_BUTTON = GUIUtil.makeTexture(
            STBUtil.makeColouredMaterial(Material.STAINED_GLASS, DyeColor.LIGHT_BLUE), ChatColor.RESET.toString() + ChatColor.UNDERLINE + "强制关闭",
            "将按顺序执行在路由器安装的其", "他模块来处理物品", "*同时执行下一个模块."
    );
    private static final ItemStack ON_BUTTON = GUIUtil.makeTexture(
            new Wool(DyeColor.ORANGE), ChatColor.RESET.toString() + ChatColor.UNDERLINE + "强制开启",
            "如果这个模块正在处理物品，", "物品路由器", "将不会这时候", "执行其他模块."
    );
    public static final int FILTER_LABEL_SLOT = 0;
    public static final int DIRECTION_LABEL_SLOT = 5;
    private final Filter filter;
    private BlockFace direction;
    private boolean terminator;
    private InventoryGUI gui;
	private ItemStack handid;
    private final int[] filterSlots = {1, 2, 3, 10, 11, 12, 19, 20, 21};

    /**
     * Run this module's action.
     *
     * @param loc the location of the module's owning item router
     * @return true if the module did some work on this tick
     */
    public abstract boolean execute(Location loc);

    public DirectionalItemRouterModule() {
        filter = new Filter();  // default filter: blacklist, no items
        //setFacingDirection(BlockFace.SELF);
        setFacingDirection(BlockFace.UP);
    }

    public DirectionalItemRouterModule(ConfigurationSection conf) {
        super(conf);
        setFacingDirection(BlockFace.valueOf(conf.getString("direction")));
        setTerminator(conf.getBoolean("terminator", false));
        if (conf.contains("filtered")) {
            boolean isWhite = conf.getBoolean("filterWhitelist", true);
            //Filter.FilterType filterType = Filter.FilterType.valueOf(conf.getString("filterType", "MATERIAL"));
            Filter.FilterType filterType = Filter.FilterType.valueOf(conf.getString("filterType", "ITEM_META"));
            @SuppressWarnings("unchecked")
            List<ItemStack> l = (List<ItemStack>) conf.getList("filtered");
            filter = Filter.fromItemList(isWhite, l, filterType);
        } else {
            filter = new Filter();
        }
    }

    public YamlConfiguration freeze() {
        YamlConfiguration conf = super.freeze();
        conf.set("direction", getFacing().toString());
        conf.set("terminator", isTerminator());
        if (filter != null) {
            conf.set("filtered", filter.listFiltered());
            conf.set("filterWhitelist", filter.isWhiteList());
            conf.set("filterType", filter.getFilterType().toString());
        }
        return conf;
    }

    @Override
    public String[] getExtraLore() {
        if (filter == null) {
            return new String[0];
        } else {
            String[] lore = new String[(filter.size() + 1) / 2 + 2];
            String what = filter.isWhiteList() ? "被添加到白名单" : "被添加到黑名单";
            String s = filter.size() == 1 ? "" : "";
            lore[0] = ChatColor.GOLD.toString() + filter.size() + " 个物品" + s + " " + what;
            //String what = filter.isWhiteList() ? "white-listed" : "black-listed";
            //String s = filter.size() == 1 ? "" : "s";
            //lore[0] = ChatColor.GOLD.toString() + filter.size() + " item" + s + " " + what;
            if (isTerminator()) {
                lore[0] += ", " + ChatColor.BOLD + "强制模式开启！";
            }
            lore[1] = ChatColor.GOLD + filter.getFilterType().getLabel();
            int i = 2;
            for (ItemStack stack : filter.listFiltered()) {
                int n = i / 2 + 1;
                String name = ItemNames.lookup(stack);
                lore[n] = lore[n] == null ? LIST_ITEM + name : lore[n] + " " + LIST_ITEM + name;
                i++;
            }
            return lore;
        }
    }

    @Override
    public String getDisplaySuffix() {
        return direction != BlockFace.SELF ? direction.toString() : null;
    }

    @Override
    public void setFacingDirection(BlockFace blockFace) {
        direction = blockFace;
    }

    @Override
    public BlockFace getFacing() {
        return direction;
    }

    public Filter getFilter() {
        return filter;
    }

    public boolean isTerminator() {
        return terminator;
    }

    public void setTerminator(boolean terminator) {
        this.terminator = terminator;
    }

    @Override
    public void onInteractItem(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            // set module direction based on clicked block face
            setFacingDirection(event.getBlockFace().getOppositeFace());
            event.getPlayer().setItemInHand(toItemStack(event.getPlayer().getItemInHand().getAmount()));
            event.setCancelled(true);
        } else if (event.getAction() == Action.LEFT_CLICK_AIR && event.getPlayer().isSneaking()) {
            // unset module direction
            setFacingDirection(BlockFace.SELF);
            event.getPlayer().setItemInHand(toItemStack(event.getPlayer().getItemInHand().getAmount()));
            event.setCancelled(true);
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemRouter rtr = event.getClickedBlock() == null ?
                    null :
                    SensibleToolbox.getBlockAt(event.getClickedBlock().getLocation(), ItemRouter.class, true);
            if (event.getClickedBlock() == null || (rtr == null && !STBUtil.isInteractive(event.getClickedBlock().getType()))) {
                // open module configuration GUI
                gui = createGUI(event.getPlayer());
				//get slot
				handid = event.getPlayer().getItemInHand();
				//
                gui.show(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

    private InventoryGUI createGUI(Player player) {
        final InventoryGUI theGUI = GUIUtil.createGUI(player, this, 36, ChatColor.DARK_RED + "模块设置");

        theGUI.addGadget(new ToggleButton(theGUI, 28, getFilter().isWhiteList(), WHITE_BUTTON, BLACK_BUTTON, new ToggleButton.ToggleListener() {
            @Override
            public boolean run(boolean newValue) {
                if (getFilter() != null) {
                    getFilter().setWhiteList(newValue);
                    return true;
                } else {
                    return false;
                }
            }
        }));
        theGUI.addGadget(new FilterTypeGadget(theGUI, 29));
        theGUI.addGadget(new ToggleButton(theGUI, 30, isTerminator(), ON_BUTTON, OFF_BUTTON, new ToggleButton.ToggleListener() {
            @Override
            public boolean run(boolean newValue) {
                setTerminator(newValue);
                return true;
            }
        }));

        theGUI.addLabel("筛选器", FILTER_LABEL_SLOT, null, "在筛选器中放入", "最多9个物品来标记 " + UnicodeSymbol.ARROW_RIGHT.toUnicode());
        for (int slot : filterSlots) {
            theGUI.setSlotType(slot, InventoryGUI.SlotType.ITEM);
        }
        populateFilterInventory(theGUI.getInventory());

        theGUI.addLabel("模块方向", DIRECTION_LABEL_SLOT, null,
                "设置模块", "运行方向");
        ItemStack texture = new ItemRouter().getMaterialData().toItemStack();
		//direction setting root:api\gui\DirectionGadget.java
		
        /**GUIUtil.setDisplayName(texture, "需要设置方向");
        theGUI.addGadget(new DirectionGadget(theGUI, 16, texture));**/
		GUIUtil.setDisplayName(texture, "需要设置方向");
        DirectionGadget dg = new DirectionGadget(theGUI, 16, texture);
        dg.setAllowSelf(false);
        theGUI.addGadget(dg);
        return theGUI;
    }

    private void populateFilterInventory(Inventory inv) {
        int n = 0;
        for (ItemStack stack : filter.listFiltered()) {
            inv.setItem(filterSlots[n], stack);
            if (++n >= filterSlots.length) {
                break;
            }
        }
    }

    protected String[] makeDirectionalLore(String... lore) {
        String[] newLore = Arrays.copyOf(lore, lore.length + 3);
        //String[] newLore = Arrays.copyOf(lore, lore.length + 2);
        //newLore[lore.length] = "左键点击方块: " + ChatColor.RESET + " 设置方向";
        newLore[lore.length] = "TIDX:04";
        newLore[lore.length + 1] = "左键点击方块: " + ChatColor.RESET + " 设置方向";
        newLore[lore.length + 2] = UnicodeSymbol.ARROW_UP.toUnicode() + " + 左键点击空气: " + ChatColor.RESET + " 取消选定的方向";
        return newLore;
    }

    @Override
    public boolean onSlotClick(HumanEntity player, int slot, ClickType click, ItemStack inSlot, ItemStack onCursor) {
        if (onCursor.getType() == Material.AIR) {
            gui.getInventory().setItem(slot, null);
        } else {
            ItemStack stack = onCursor.clone();
            stack.setAmount(1);
            gui.getInventory().setItem(slot, stack);
        }
        return false;
    }

	
    @Override
    public boolean onPlayerInventoryClick(HumanEntity player, int slot, ClickType click, ItemStack inSlot, ItemStack onCursor) {
		if (inSlot.getType() == Material.PAPER) {
			return false;
		}else{
			return true;
		}
        
    }

    @Override
    public int onShiftClickInsert(HumanEntity player, int slot, ItemStack toInsert) {
        return 0;
    }

    @Override
    public boolean onShiftClickExtract(HumanEntity player, int slot, ItemStack toExtract) {
        return false;
    }

    @Override
    public boolean onClickOutside(HumanEntity player) {
        return false;
    }

    @Override
    public void onGUIClosed(HumanEntity player) {
        filter.clear();
        for (int slot : filterSlots) {
            ItemStack stack = gui.getInventory().getItem(slot);
            if (stack != null) {
                filter.addItem(stack);
            }
        }
        player.setItemInHand(toItemStack(player.getItemInHand().getAmount()));
    }

    protected boolean doPull(BlockFace from, Location loc) {
        ItemStack inBuffer = getItemRouter().getBufferItem();
        if (inBuffer != null && inBuffer.getAmount() >= inBuffer.getType().getMaxStackSize()) {
            return false;
        }
        int nToPull = getItemRouter().getStackSize();
        Location targetLoc = getTargetLocation(loc);
        ItemStack pulled;
        BaseSTBBlock stb = SensibleToolbox.getBlockAt(targetLoc, true);
        if (stb instanceof STBInventoryHolder) {
            pulled = ((STBInventoryHolder) stb).extractItems(from.getOppositeFace(), inBuffer, nToPull, getItemRouter().getOwner());
        } else {
            // possible vanilla inventory holder
            pulled = VanillaInventoryUtils.pullFromInventory(targetLoc.getBlock(), nToPull, inBuffer, getFilter(), getItemRouter().getOwner());
        }
        if (pulled != null) {
            if (stb != null) {
                stb.update(false);
            }
            getItemRouter().setBufferItem(inBuffer == null ? pulled : inBuffer);
            return true;
        }
        return false;
    }

    protected boolean vanillaInsertion(Block target, int amount, BlockFace side) {
        ItemStack buffer = getItemRouter().getBufferItem();
        int nInserted = VanillaInventoryUtils.vanillaInsertion(target, buffer, amount, side, false, getItemRouter().getOwner());
        if (nInserted == 0) {
            // no insertion happened
            return false;
        } else {
            // some or all items were inserted, buffer size has been adjusted accordingly
            getItemRouter().setBufferItem(buffer.getAmount() == 0 ? null : buffer);
            return true;
        }
    }

    protected Location getTargetLocation(Location loc) {
        BlockFace face = getFacing();
        return loc.clone().add(face.getModX(), face.getModY(), face.getModZ());
    }

    protected boolean creativeModeBlocked(BaseSTBBlock stb, Location loc) {
        return stb instanceof EnderBox
                && STBUtil.isCreativeWorld(loc.getWorld())
                && !SensibleToolbox.getPluginInstance().getConfigCache().isCreativeEnderAccess();
    }
}
