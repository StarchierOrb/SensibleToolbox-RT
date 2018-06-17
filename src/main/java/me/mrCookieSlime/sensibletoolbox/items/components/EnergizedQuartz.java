package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

public class EnergizedQuartz extends BaseSTBItem {
	
    public static final MaterialData md = new MaterialData(Material.QUARTZ);

    public EnergizedQuartz() {
    }

    public EnergizedQuartz(ConfigurationSection conf) {
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "带电石英";
    }

    @Override
    public String[] getLore() {
        return new String[] {"TID:661", "带电的物品通常都", "有着奇怪的样子..."};
    }

    @Override
    public Recipe getRecipe() {
    	ShapelessRecipe recipe = new ShapelessRecipe(toItemStack(1));
        InfernalDust dust = new InfernalDust();
        registerCustomIngredients(dust);
        recipe.addIngredient(dust.getMaterialData());
        recipe.addIngredient(new MaterialData(Material.QUARTZ));
        return recipe;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }
}
