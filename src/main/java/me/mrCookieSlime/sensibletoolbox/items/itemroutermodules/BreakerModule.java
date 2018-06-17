package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import me.mrCookieSlime.sensibletoolbox.api.SensibleToolbox;
import me.mrCookieSlime.sensibletoolbox.api.util.BlockProtection;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;

import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

import java.util.List;

public class BreakerModule extends DirectionalItemRouterModule {
    private static final MaterialData md = new MaterialData(Material.PAPER);
    //private static final MaterialData md = makeDye(DyeColor.YELLOW);
    private static final ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE, 1);

    public BreakerModule() {
    }

    public BreakerModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public boolean execute(Location loc) {
        Block b = getTargetLocation(loc).getBlock();
		//check block meta data
		if (b.getType() == Material.STAINED_CLAY) {
			return false;
		}
		if (b.getType() == Material.ENDER_CHEST) {
			return false;
		}
		if (b.getType() == Material.OBSIDIAN) {
			return false;
		}
		if (b.getType() == Material.DROPPER) {
			return false;
		}
		if (b.getType() == Material.SKULL) {
			return false;
		}
		if (b.getType() == Material.NETHER_BRICK) {
			return false;
		}
		if (b.getType() == Material.LOG) {
			return false;
		}
		if (b.getType() == Material.STAINED_GLASS) {
			return false;
		}
		//test
        if (b.isEmpty() || b.isLiquid() || STBUtil.getMaterialHardness(b.getType()) == Double.MAX_VALUE) {
            return false;
        }
        List<ItemStack> d = STBUtil.calculateDrops(b, getBreakerTool());
        if (d.isEmpty()) {
            return false;
        }
        ItemStack[] drops = d.toArray(new ItemStack[d.size()]);
        ItemStack mainDrop = drops[0];
        ItemStack inBuffer = getItemRouter().getBufferItem();
        if (inBuffer == null || inBuffer.isSimilar(mainDrop) && inBuffer.getAmount() < inBuffer.getMaxStackSize()) {
            if (getFilter().shouldPass(mainDrop) && SensibleToolbox.getBlockProtection().playerCanBuild(getItemRouter().getOwner(), b, BlockProtection.Operation.BREAK)) {
                if (inBuffer == null) {
                    getItemRouter().setBufferItem(mainDrop);
                } else {
                    int toAdd = Math.min(mainDrop.getAmount(), inBuffer.getMaxStackSize() - inBuffer.getAmount());
                    getItemRouter().setBufferAmount(inBuffer.getAmount() + toAdd);
                    if (toAdd < mainDrop.getAmount()) {
                        ItemStack stack = mainDrop.clone();
                        stack.setAmount(mainDrop.getAmount() - toAdd);
                        b.getWorld().dropItemNaturally(b.getLocation(), stack);
                    }
                }
                b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
                b.setType(Material.AIR);
                for (int i = 1; i < drops.length; i++) {
                    b.getWorld().dropItemNaturally(b.getLocation(), drops[i]);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "��Ʒ·��ģ�飺 �ƻ���";
    }

    @Override
    public String[] getLore() {
        return new String[] { 
				"TIDX:03",
        		"����ģ�鰲װ����Ʒ·������.", 
        		"����:�ƻ�ָ������", 
        		"      �ķ���", 
        		"�����ƻ�����Ʒ����·������"
        };
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        recipe.addIngredient(bm.getMaterialData());
        recipe.addIngredient(Material.DIAMOND_PICKAXE);
        recipe.addIngredient(Material.HOPPER);
        return recipe;
    }

    protected ItemStack getBreakerTool() {
        return pick;
    }
}
