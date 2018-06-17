package me.mrCookieSlime.sensibletoolbox.blocks.machines;

import java.util.Arrays;

import me.mrCookieSlime.CSCoreLibPlugin.general.String.StringUtils;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.components.IntegratedCircuit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class HyperStorageUnit extends BigStorageUnit {
    private static final MaterialData md = STBUtil.makeLog(TreeSpecies.ACACIA);

    public HyperStorageUnit() {
        super();
    }

    public HyperStorageUnit(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "HSU";
    }

    @Override
    public Recipe getRecipe() {/*
        IntegratedCircuit ic = new IntegratedCircuit();
        BigStorageUnit bsu = new BigStorageUnit();
        registerCustomIngredients(ic, bsu);
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("OIO", "EBE", "RGR");
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('I', ic.getMaterialData());
        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('B', bsu.getMaterialData());
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        return recipe;*/
		return null;
    }

    @Override
    public Recipe[] getExtraRecipes() {
        return new Recipe[0];
    }

    @Override
    public String getCraftingNotes() {
        return null;
    }

    @Override
    public int getMaxCharge() {
        return 1000;
    }

    @Override
    public int getChargeRate() {
        return 10;
    }

    @Override
    public int getChargeMeterSlot() {
        return 35;
    }

    public int getStackCapacity() {
        return 25600;  // 2^31 items for a 64-item stack
        //return 33554431;  // 2^31 items for a 64-item stack
    }

    public int getEnergyCellSlot() {
        return 36;
    }

    public int getChargeDirectionSlot() {
        return 37;
    }

    @Override
    public int getInventoryGUISize() {
        return 45;
    }

    @Override
    public boolean acceptsEnergy(BlockFace face) {
        return true;
    }

    @Override
    protected boolean dropsItemsOnBreak() {
        return false;
    }

    @Override
    public String[] getLore() {
        return new String[]{
                "���洢��Ԫ",
                "���Դ��� " + getStackCapacity() + " ����Ʒ",
                "* ֻ�ܴ洢һ����Ʒ",
                "* �ڱ��ƻ���ʱ��Ҳ�ܴ���",
                "* ��Ҫ��������"
        };
    }

    @Override
    public String[] getExtraLore() {
        if (getTotalAmount() > 0) {
            String[] l = super.getExtraLore();
            String[] l2 = Arrays.copyOf(l, l.length + 1);
            l2[l2.length - 1] = ChatColor.WHITE + "�Ѿ�����: " + ChatColor.YELLOW + getTotalAmount() + " " + StringUtils.formatItemName(getStoredItemType(), false);
            return l2;
        } else {
            return super.getExtraLore();
        }
    }

    @Override
    public double getChargePerOperation(int nItems) {
        return 0.05 * nItems;
    }

    @Override
    public void onBlockUnregistered(Location loc) {
        // move all output items into storage so they don't get dropped
        ItemStack output = getOutputItem();
        if (output != null) {
            setStorageAmount(getStorageAmount() + output.getAmount());
            setOutputAmount(0);
            setOutputItem(null);
        }
        super.onBlockUnregistered(loc);
    }
}
