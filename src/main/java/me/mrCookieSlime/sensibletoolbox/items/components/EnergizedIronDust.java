package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

public class EnergizedIronDust extends BaseSTBItem {
    public static final MaterialData md = new MaterialData(Material.SUGAR);

    public EnergizedIronDust() {
    }

    public EnergizedIronDust(ConfigurationSection conf) {

    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "带电铁粉";
    }

    @Override
    public String[] getLore() {
        return new String[] {"TID:658", "通过冶炼可以得到带电铁锭" };
    }

    @Override
    public Recipe getRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack(1));
        InfernalDust dust1 = new InfernalDust();
        IronDust dust2 = new IronDust();
        registerCustomIngredients(dust1, dust2);
        recipe.addIngredient(dust1.getMaterialData());
        recipe.addIngredient(dust2.getMaterialData());
        return recipe;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }

    @Override
    public ItemStack getSmeltingResult() {
        return new EnergizedIronIngot().toItemStack();
    }
}
