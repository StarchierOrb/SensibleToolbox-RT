package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class SubspaceTransponder extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.BREWING_STAND_ITEM);

    public SubspaceTransponder() {
        super();
    }

    public SubspaceTransponder(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "多维空间应答器";
    }

    @Override
    public String[] getLore() {
        return new String[] { "此物品可以为某些道具提供", "多世界连接通道", "TID:670" };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        IntegratedCircuit ic = new IntegratedCircuit();
        EnergizedGoldIngot eg = new EnergizedGoldIngot();
        registerCustomIngredients(ic, eg);
        recipe.shape("DGE", " G ", " C ");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('E', Material.EYE_OF_ENDER);
        recipe.setIngredient('G', eg.getMaterialData());
        recipe.setIngredient('C', ic.getMaterialData());
        return recipe;
    }

    @Override
    public boolean hasGlow() {
        return true;
    }
}
