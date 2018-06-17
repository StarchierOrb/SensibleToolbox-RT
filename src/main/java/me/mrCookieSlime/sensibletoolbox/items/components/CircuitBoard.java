package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;

public class CircuitBoard extends BaseSTBItem {
	
    private static final MaterialData md = new MaterialData(Material.PRISMARINE_SHARD);

    public CircuitBoard() {
    }

    public CircuitBoard(ConfigurationSection conf) {
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "电路板";
    }

    @Override
    public String[] getLore() {
        return new String[]{"TID:1001", "用于电子电路的建设"};
    }

    @Override
    public Recipe getRecipe() {/*
        Dye greenDye = new Dye();
        greenDye.setColor(DyeColor.GREEN);
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack(2));
        recipe.addIngredient(Material.STONE_PLATE);
        recipe.addIngredient(greenDye);
        return recipe;*/
		return null;
    }
}
