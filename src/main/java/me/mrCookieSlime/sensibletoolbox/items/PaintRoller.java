package me.mrCookieSlime.sensibletoolbox.items;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class PaintRoller extends PaintBrush {
	
    private static final MaterialData md = new MaterialData(Material.IRON_SPADE);

    public PaintRoller() {
        super();
    }

    public PaintRoller(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "ÓÍÆá¹öÍ²";
    }

    @Override
    public int getMaxPaintLevel() {
        return 100;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("WWW", "   ", "   ");
        recipe.setIngredient('W', Material.WOOL);
        return recipe;
    }

    @Override
    protected int getMaxBlocksAffected() {
        return 25;
    }
}
