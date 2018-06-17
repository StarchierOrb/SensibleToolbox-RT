package me.mrCookieSlime.sensibletoolbox.blocks.machines;

import me.mrCookieSlime.sensibletoolbox.api.items.AbstractIOMachine;
import me.mrCookieSlime.sensibletoolbox.api.recipes.CustomRecipeManager;
import me.mrCookieSlime.sensibletoolbox.api.recipes.SimpleCustomRecipe;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.components.EnergizedQuartz;
import me.mrCookieSlime.sensibletoolbox.items.components.FishBait;
import me.mrCookieSlime.sensibletoolbox.items.components.MachineFrame;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomPotion;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.CSCoreLibPlugin.general.String.StringUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class Fermenter extends AbstractIOMachine {
	
    private static final MaterialData md = STBUtil.makeColouredMaterial(Material.STAINED_CLAY, DyeColor.LIME);

    public Fermenter() {
    }

    public Fermenter(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public void addCustomRecipes(CustomRecipeManager crm) {
    	FishBait bait = new FishBait();

        crm.addCustomRecipe(new SimpleCustomRecipe(this, new ItemStack(Material.SPIDER_EYE), new ItemStack(Material.FERMENTED_SPIDER_EYE), 220));
        crm.addCustomRecipe(new SimpleCustomRecipe(this, new ItemStack(Material.APPLE), new CustomItem(new MaterialData(Material.APPLE), ChatColor.GRAY + "苹果渣", ChatColor.WHITE + "TID:362", "&7看来发酵过头了..."), 400));
        crm.addCustomRecipe(new SimpleCustomRecipe(this, new ItemStack(Material.ROTTEN_FLESH), bait.toItemStack(), 200));
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "电力发酵箱";
    }

    @Override
    public String[] getLore() {
        return new String[] {"可以发酵各种农作物", "并且可以用于制作鱼饵"};
    }

    @Override
    public Recipe getRecipe() {/*
        MachineFrame mf = new MachineFrame();
        EnergizedQuartz q = new EnergizedQuartz();
        registerCustomIngredients(mf, q);
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("SQM", "IFI", "RGR");
        recipe.setIngredient('S', Material.SUGAR);
        recipe.setIngredient('Q', q.getMaterialData());
        recipe.setIngredient('M', Material.BROWN_MUSHROOM);
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('F', mf.getMaterialData());
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        return recipe;*/
		return null;
    }

    @Override
    public int[] getInputSlots() {
        return new int[]{10};
    }

    @Override
    public int[] getOutputSlots() {
        return new int[]{14};
    }

    @Override
    public int[] getUpgradeSlots() {
        return new int[]{41, 42, 43, 44};
    }

    @Override
    public int getUpgradeLabelSlot() {
        return 40;
    }

    @Override
    public int getEnergyCellSlot() {
        return 36;
    }

    @Override
    public int getChargeDirectionSlot() {
        return 37;
    }

    @Override
    public int getInventoryGUISize() {
        return 45;
    }

    @Override
    public int getMaxCharge() {
        return 1000;
    }

    @Override
    public int getChargeRate() {
        return 20;
    }

    @Override
    public int getProgressItemSlot() {
        return 12;
    }

    @Override
    public int getProgressCounterSlot() {
        return 3;
    }

    @Override
    public ItemStack getProgressIcon() {
        return new ItemStack(Material.FLINT_AND_STEEL);
    }
    
    @Override
    protected void playActiveParticleEffect() {
        if (getTicksLived() % 20 == 0) {
            getLocation().getWorld().playEffect(getLocation(), Effect.STEP_SOUND, Material.LEAVES);
        }
    }
}
