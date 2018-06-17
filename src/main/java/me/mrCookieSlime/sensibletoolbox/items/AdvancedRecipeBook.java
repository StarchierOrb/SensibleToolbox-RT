package me.mrCookieSlime.sensibletoolbox.items;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class AdvancedRecipeBook extends RecipeBook {
    public AdvancedRecipeBook() {
        super();
    }

    public AdvancedRecipeBook(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "高级合成指南";
    }

    @Override
    public boolean isAdvanced() {
        return true;
    }

    @Override
    public String[] getExtraLore() {
        return new String[] { 
        		"§d在合成物品的过程中",
        		"§d可以直接使用背包里的物品合成"
        };
    }

    @Override
    public Recipe getRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        RecipeBook book = new RecipeBook();
        registerCustomIngredients(book);
        recipe.addIngredient(Material.DIAMOND_BLOCK);
        recipe.addIngredient(book.getMaterialData());
        return recipe;
    }
}
