package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class ToughMachineFrame extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.IRON_BLOCK);

    public ToughMachineFrame() {
    }

    public ToughMachineFrame(ConfigurationSection conf) {
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "粗制机框";
    }

    @Override
    public String[] getLore() {
        return new String[]{"此道具目前不可用." };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        EnergizedIronIngot ingot = new EnergizedIronIngot();
        MachineFrame frame = new MachineFrame();
        registerCustomIngredients(ingot, frame);
        recipe.shape(" I ", "IFI", " I ");
        recipe.setIngredient('F', frame.getMaterialData());
        recipe.setIngredient('I', ingot.getMaterialData());
        return recipe;
    }
}
