package me.mrCookieSlime.sensibletoolbox.blocks.machines;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.sensibletoolbox.api.items.AutoFarmingMachine;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.IronCombineHoe;
import me.mrCookieSlime.sensibletoolbox.items.components.MachineFrame;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomPotion;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.CSCoreLibPlugin.general.String.StringUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;

import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Skull;

public class AutoFarm extends AutoFarmingMachine {
	
    //private static final ItemStack md = new CustomItem(getSkull(Material.NETHER_STALK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVhNWM0YTBhMTZkYWJjOWIxZWM3MmZjODNlMjNhYzE1ZDAxOTdkZTYxYjEzOGJhYmNhN2M4YTI5YzgyMCJ9fX0="));
    private static final MaterialData md = STBUtil.makeColouredMaterial(Material.STAINED_CLAY, DyeColor.BROWN);
    //private static final MaterialData md = CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQxOGM2YjBhMjlmYzFmZTc5MWM4OTc3NGQ4MjhmZjYzZDJhOWZhNmM4MzM3M2VmM2FhNDdiZjNlYjc5In19fQ");
    //private static final MaterialData md = Skull(SkullType.PLAYER, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQxOGM2YjBhMjlmYzFmZTc5MWM4OTc3NGQ4MjhmZjYzZDJhOWZhNmM4MzM3M2VmM2FhNDdiZjNlYjc5In19fQ");
    private static final Map<Material, Material> crops = new HashMap<Material, Material>();
    private static final int radius = 3;
    
    static {
    	crops.put(Material.CROPS, Material.WHEAT);
    	crops.put(Material.POTATO, Material.POTATO_ITEM);
    	crops.put(Material.CARROT, Material.CARROT_ITEM);
    }
    
    private Set<Block> blocks;
    private Material buffer;

    public AutoFarm() {
    	super();
        blocks = new HashSet<Block>();
    }

    public AutoFarm(ConfigurationSection conf) {
        super(conf);
        blocks = new HashSet<Block>();
    }
    
    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "�Զ������";
    }

    @Override
    public String[] getLore() {
        return new String[] {
                "��d�Զ��ջ����ֲ",
                "��aС��/������/���ܲ� ��ũ����",
                "������Χ�� " + radius + "x" + radius + " ���һ���Ҫ����ũ���·�2������"
        };
    }

    @Override
    public Recipe getRecipe() {
		return null;
    	/*MachineFrame frame = new MachineFrame();
    	IronCombineHoe hoe = new IronCombineHoe();
    	registerCustomIngredients(frame, hoe);
        ShapedRecipe res = new ShapedRecipe(toItemStack());
        res.shape("   ", " R ", "   ");
        res.setIngredient('R', Material.BARRIER);
        return res;*/
    }
    
    @Override
    public void onBlockRegistered(Location location, boolean isPlacing) {
    	int i = radius / 2;
    	for (int x = -i; x <= i; x++) {
    		for (int z = -i; z <= i; z++) {
        		blocks.add(new Location(location.getWorld(), location.getBlockX() + x, location.getBlockY() + 2, location.getBlockZ() + z).getBlock());
        	}
    	}
    	
    	super.onBlockRegistered(location, isPlacing);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onServerTick() {
    	if (!isJammed()) {
    		for (Block crop: blocks) {
        		if (crops.containsKey(crop.getType()) && crop.getData() >= 7) {
        			if (getCharge() >= getScuPerCycle()) setCharge(getCharge() - getScuPerCycle());
        			else break;
        			crop.setData((byte) 0);
        			crop.getWorld().playEffect(crop.getLocation(), Effect.STEP_SOUND, crop.getType());
            		setJammed(!output(crops.get(crop.getType())));
        			break;
        		}
        	}
    	}
    	else if (buffer != null) {
    		setJammed(!output(buffer));
    	}
    	
        super.onServerTick();
    }
    
	private boolean output(Material m) {
		for (int slot: getOutputSlots()) {
			ItemStack stack = getInventoryItem(slot);
			if (stack == null || (stack.getType() == m && stack.getAmount() < stack.getMaxStackSize())) {
				if (stack == null) stack = new ItemStack(m);
				int amount = (stack.getMaxStackSize() - stack.getAmount()) > 3 ? (CSCoreLib.randomizer().nextInt(2) + 1): (stack.getMaxStackSize() - stack.getAmount());
				setInventoryItem(slot, new CustomItem(stack, stack.getAmount() + amount));
				buffer = null;
				return true;
			}
		}
		return false;
	}
    
    @Override
    public double getScuPerCycle() {
        return 25.0;
    }
}