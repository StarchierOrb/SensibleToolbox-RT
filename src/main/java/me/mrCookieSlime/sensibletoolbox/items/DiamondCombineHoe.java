package me.mrCookieSlime.sensibletoolbox.items;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;


public class DiamondCombineHoe extends CombineHoe {
    private static final MaterialData md = new MaterialData(Material.DIAMOND_HOE);

    public DiamondCombineHoe() {
        super();
    }

    public DiamondCombineHoe(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "多功能科技锄";
    }

    @Override
    public Recipe getRecipe() {
        /*ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("   ", " S ", "   ");
        recipe.setIngredient('S', Material.BARRIER);
        return recipe;*/
		return null;
    }

    @Override
    public int getWorkRadius() {
        return 2;
    }
}
