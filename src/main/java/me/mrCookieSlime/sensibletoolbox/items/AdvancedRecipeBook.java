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
        return "�߼��ϳ�ָ��";
    }

    @Override
    public boolean isAdvanced() {
        return true;
    }

    @Override
    public String[] getExtraLore() {
        return new String[] { 
        		"��d�ںϳ���Ʒ�Ĺ�����",
        		"��d����ֱ��ʹ�ñ��������Ʒ�ϳ�"
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
