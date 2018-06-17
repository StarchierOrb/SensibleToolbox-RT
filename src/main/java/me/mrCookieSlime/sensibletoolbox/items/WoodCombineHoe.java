package me.mrCookieSlime.sensibletoolbox.items;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class WoodCombineHoe extends CombineHoe {
    private static final MaterialData md = new MaterialData(Material.WOOD_HOE);

    public WoodCombineHoe() {
        super();
    }

    public WoodCombineHoe(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "B级 重型多功能锄 试用版";
    }

    @Override
    public Recipe getRecipe() {/*
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("SSS", "   ", "SSS");
        recipe.setIngredient('S', Material.BARRIER);
        return recipe;*/
		return null;
    }
}
