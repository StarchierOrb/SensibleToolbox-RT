package me.mrCookieSlime.sensibletoolbox.blocks.machines;

import java.util.UUID;

import me.mrCookieSlime.sensibletoolbox.api.LightMeterHolder;
import me.mrCookieSlime.sensibletoolbox.api.RedstoneBehaviour;
import me.mrCookieSlime.sensibletoolbox.api.SensibleToolbox;
import me.mrCookieSlime.sensibletoolbox.api.energy.ChargeDirection;
import me.mrCookieSlime.sensibletoolbox.api.gui.GUIUtil;
import me.mrCookieSlime.sensibletoolbox.api.gui.InventoryGUI;
import me.mrCookieSlime.sensibletoolbox.api.gui.LightMeter;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBMachine;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.PVCell;
import me.mrCookieSlime.sensibletoolbox.items.components.SimpleCircuit;
import me.mrCookieSlime.sensibletoolbox.util.SunlightLevels;
import me.mrCookieSlime.sensibletoolbox.util.UnicodeSymbol;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

public class BasicSolarCell extends BaseSTBMachine implements LightMeterHolder {
	
    private static final MaterialData md = STBUtil.makeColouredMaterial(Material.STAINED_GLASS, DyeColor.SILVER);

    private static final int PV_CELL_SLOT = 1;
    private static final int LIGHT_METER_SLOT = 13;

    private byte effectiveLightLevel;
    private int lightMeterId;
    private int pvCellLife;

    public BasicSolarCell() {
        pvCellLife = 0;
        setChargeDirection(ChargeDirection.CELL);
    }

    public BasicSolarCell(ConfigurationSection conf) {
        super(conf);
        pvCellLife = conf.getInt("pvCellLife", 0);
    }

    @Override
    public YamlConfiguration freeze() {
        YamlConfiguration conf = super.freeze();
        conf.set("pvCellLife", pvCellLife);
        return conf;
    }

    @Override
    public int[] getInputSlots() {
        return new int[] { 1 };
    }

    @Override
    public int[] getOutputSlots() {
        return new int[] { 1 };
    }

    @Override
    public int[] getUpgradeSlots() {
        return new int[0];  // maybe one day
    }

    @Override
    public int getUpgradeLabelSlot() {
        return -1;
    }

    @Override
    public boolean onSlotClick(HumanEntity player, int slot, ClickType click, ItemStack inSlot, ItemStack onCursor) {
        boolean res = super.onSlotClick(player, slot, click, inSlot, onCursor);
        if (res) {
            rescanPVCell();
        }
        return res;
    }

    @Override
    public int onShiftClickInsert(HumanEntity player, int slot, ItemStack toInsert) {
        int inserted = super.onShiftClickInsert(player, slot, toInsert);
        if (inserted > 0) {
            rescanPVCell();
        }
        return inserted;
    }

    @Override
    public boolean onShiftClickExtract(HumanEntity player, int slot, ItemStack toExtract) {
        boolean res = super.onShiftClickExtract(player, slot, toExtract);
        if (res) {
            rescanPVCell();
        }
        return res;
    }

    @Override
    public void onGUIOpened(HumanEntity player) {
        drawPVCell(getGUI());
    }

    @Override
    public boolean acceptsItemType(ItemStack item) {
        return SensibleToolbox.getItemRegistry().isSTBItem(item, PVCell.class);
    }

    @Override
    public int insertItems(ItemStack toInsert, BlockFace side, boolean sorting, UUID uuid) {
        int n = super.insertItems(toInsert, side, sorting, uuid);
        if (n > 0) {
            rescanPVCell();
        }
        return n;
    }

    @Override
    public ItemStack extractItems(BlockFace face, ItemStack receiver, int amount, UUID uuid) {
        ItemStack stack = super.extractItems(face, receiver, amount, uuid);
        if (stack != null) {
            rescanPVCell();
        }
        return stack;
    }

    @Override
    public void repaint(Block block) {
        super.repaint(block);
        drawPVLayer(block.getRelative(BlockFace.UP));
    }

    private void rescanPVCell() {
        // defer this since we need to ensure the inventory slot is actually updated
        Bukkit.getScheduler().runTask(getProviderPlugin(), new Runnable() {
            @Override
            public void run() {
                PVCell cell = SensibleToolbox.getItemRegistry().fromItemStack(getGUI().getItem(PV_CELL_SLOT), PVCell.class);
                int pvl = cell == null ? 0 : cell.getLifespan();
                if (pvl != pvCellLife) {
                    boolean doRedraw = pvl == 0 || pvCellLife == 0;
                    pvCellLife = pvl;
                    update(doRedraw);
                    getLightMeter().repaintNeeded();
                }
            }
        });
    }

    private void drawPVCell(InventoryGUI gui) {
        if (pvCellLife > 0) {
            PVCell pvCell = new PVCell();
            pvCell.setLifespan(pvCellLife);
            gui.setItem(PV_CELL_SLOT, pvCell.toItemStack());
        } else {
            gui.setItem(PV_CELL_SLOT, null);
        }
    }

    @Override
    protected void playActiveParticleEffect() {
        // nothing
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "小型太阳能发电机";
    }

    @Override
    public String[] getLore() {
        return new String[]{"在露天且有明媚的阳光下", "能够产生 " + getPowerOutput() + " SCU/游戏刻 的能量", UnicodeSymbol.ARROW_UP.toUnicode() + " + 左键点击方块 (用空手): ",  ChatColor.RESET +"  - 提取太阳能板(光伏电池)"};
    }

    @Override
    public String[] getExtraLore() {
        String[] l = super.getExtraLore();
        String[] l2 = new String[l.length + 1];
        System.arraycopy(l, 0, l2, 0, l.length);
        if (pvCellLife == 0) {
            l2[l.length] = ChatColor.GRAY.toString() + ChatColor.ITALIC + "没有安装太阳能板,无法工作";
        } else {
            l2[l.length] = PVCell.formatCellLife(pvCellLife);
        }
        return l2;
    }

    @Override
    public Recipe getRecipe() {/*
        SimpleCircuit sc = new SimpleCircuit();
        registerCustomIngredients(sc);
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("DDD", "IQI", "RGR");
        recipe.setIngredient('D', Material.DAYLIGHT_DETECTOR);
        recipe.setIngredient('I', sc.getMaterialData());
        recipe.setIngredient('Q', Material.QUARTZ_BLOCK);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        return recipe;*/
		return null;
    }

    @Override
    public int getMaxCharge() {
        return 100;
    }

    @Override
    public int getChargeRate() {
        return 5;
    }

    @Override
    public int getEnergyCellSlot() {
        return 18;
    }

    @Override
    public int getChargeDirectionSlot() {
        return 19;
    }

    @Override
    public int getInventoryGUISize() {
        return 27;
    }

    @Override
    public int getTickRate() {
        return 20;
    }

    @SuppressWarnings("deprecation")
	private void drawPVLayer(Block b) {
        // put a carpet on top of the main block to represent the PV cell
        DyeColor color = pvCellLife > 0 ? getCapColour() : DyeColor.GRAY;
        MaterialData carpet = STBUtil.makeColouredMaterial(Material.CARPET, color);
        b.setTypeIdAndData(carpet.getItemTypeId(), carpet.getData(), true);
    }

    @Override
    public void onBlockRegistered(Location location, boolean isPlacing) {
        if (isPlacing) {
            drawPVLayer(location.getBlock().getRelative(BlockFace.UP));
        }
        super.onBlockRegistered(location, isPlacing);
    }

    @Override
    public void onBlockUnregistered(Location location) {
        // remove any pv cell in the gui; pv level is stored separately
        getGUI().setItem(PV_CELL_SLOT, null);

        super.onBlockUnregistered(location);
    }

    protected DyeColor getCapColour() {
        return DyeColor.BLUE;
    }

    @Override
    public RelativePosition[] getBlockStructure() {
        return new RelativePosition[]{new RelativePosition(0, 1, 0)};
    }

    @Override
    public void onInteractBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null && event.getAction() == Action.LEFT_CLICK_BLOCK && player.isSneaking()) {
            ItemStack stack = extractItems(event.getBlockFace(), null, 1, event.getPlayer().getUniqueId());
            if (stack != null) {
                Block block = event.getClickedBlock();
                block.getWorld().dropItemNaturally(block.getLocation(), stack);
                player.playSound(block.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 0.6f);
            }
        }
        super.onInteractBlock(event);
    }

    @Override
    public void onBlockPhysics(BlockPhysicsEvent event) {
        // ensure carpet layer doesn't get popped off (and thus not cleared) when block is broken
        if (event.getBlock().getType() == Material.CARPET) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onServerTick() {
        calculateLightLevel();

        if (pvCellLife > 0 && getCharge() < getMaxCharge() && isRedstoneActive()) {
            double toAdd = getPowerOutput() * getTickRate() * getChargeMultiplier(getLightLevel());
            if (toAdd > 0) {
                setCharge(getCharge() + toAdd);
                pvCellLife = Math.min(PVCell.MAX_LIFESPAN, Math.max(0, pvCellLife - getTickRate()));
                if (pvCellLife == 0) {
                    update(true);
                }
                if (!getGUI().getViewers().isEmpty()) {
                    drawPVCell(getGUI());
                }
            }
        }

        getLightMeter().doRepaint();

        super.onServerTick();
    }

    private LightMeter getLightMeter() {
        return (LightMeter) getGUI().getMonitor(lightMeterId);
    }

    private void calculateLightLevel() {
        Block b = getLocation().getBlock().getRelative(BlockFace.UP);
        byte newLight = SunlightLevels.getSunlightLevel(b.getWorld());
        byte lightFromSky = b.getLightFromSky();
        if (lightFromSky < 14) {
            newLight = 0;  // block is excessively shaded
        } else if (lightFromSky < 15) {
            newLight--;    // partially shaded
        }
        if (b.getWorld().hasStorm()) {
            newLight -= 3;  // raining: big efficiency drop
        }
        if (newLight < 0) {
            newLight = 0;
        }
        if (newLight != effectiveLightLevel) {
            getLightMeter().repaintNeeded();
            effectiveLightLevel = newLight;
        }
    }

    @Override
    protected InventoryGUI createGUI() {
        InventoryGUI gui = super.createGUI();

        gui.addLabel("太阳能板安装槽", 0, null, "请将太阳能板安装在这里");
        gui.setSlotType(PV_CELL_SLOT, InventoryGUI.SlotType.ITEM);

        drawPVCell(gui);

        lightMeterId = gui.addMonitor(new LightMeter(gui));

        return gui;
    }

    @Override
    protected boolean shouldPaintSlotSurrounds() {
        return false;
    }

    public byte getLightLevel() {
        return effectiveLightLevel;
    }

    @Override
    public int getLightMeterSlot() {
        return LIGHT_METER_SLOT;
    }

    @Override
    public ItemStack getLightMeterIndicator() {
        if (pvCellLife == 0) {
            return GUIUtil.makeTexture(new Wool(DyeColor.BLACK),
                    ChatColor.WHITE + "没有安装太阳能板!",
                    ChatColor.GRAY + "请在左上方插入一个太阳能板");
        } else {
            DyeColor dc = colors[effectiveLightLevel];
            ChatColor cc = STBUtil.dyeColorToChatColor(dc);
            double mult = getChargeMultiplier(effectiveLightLevel);
            return GUIUtil.makeTexture(new Wool(dc),
                    ChatColor.WHITE + "工作效率: " + cc + (int) (getChargeMultiplier(effectiveLightLevel) * 100) + "%",
                    ChatColor.GRAY + "电力输出: " + getPowerOutput() * mult + " SCU/每0.05秒");
        }
    }

    /**
     * Return the maximum SCU per tick that this solar can generate.  Note
     * time of day, weather and sky visibilty can all reduce the actual power
     * output.
     *
     * @return the maximum power output in SCU per tick
     */
    protected double getPowerOutput() {
        return 0.5;
    }

    private double getChargeMultiplier(byte light) {
        switch (light) {
            case 15:
                return 1.0;
            case 14:
                return 0.75;
            case 13:
                return 0.5;
            case 12:
                return 0.25;
            default:
                return 0.0;
        }
    }

    @Override
    public boolean acceptsEnergy(BlockFace face) {
        return false;
    }

    @Override
    public boolean suppliesEnergy(BlockFace face) {
        return true;
    }

    @Override
    public boolean supportsRedstoneBehaviour(RedstoneBehaviour behaviour) {
        return behaviour != RedstoneBehaviour.PULSED;
    }

    private static final DyeColor[] colors = new DyeColor[16];
    static {
        colors[15] = DyeColor.LIME;
        colors[14] = DyeColor.YELLOW;
        colors[13] = DyeColor.ORANGE;
        colors[12] = DyeColor.RED;
        for (int i= 0; i < 12; i++) {
            colors[i] = DyeColor.GRAY;
        }
    }
}
