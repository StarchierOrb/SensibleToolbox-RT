package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class MachineFrame extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.IRON_BLOCK);

    public MachineFrame() {
    }

    public MachineFrame(ConfigurationSection conf) {
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "§d科技核心";
    }

    @Override
    public String[] getLore() {
        return new String[]{"§a所有机器的科技核心"};
    }

    @Override
    public Recipe getRecipe() {
		return null;
        /*ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("IBI", "B B", "IBI");
        recipe.setIngredient('B', Material.IRON_FENCE);
        recipe.setIngredient('I', Material.IRON_INGOT);
        return recipe;*/
    }

}
