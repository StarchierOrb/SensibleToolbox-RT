package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class BlankModule extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.PAPER);

    public BlankModule() {
    }

    public BlankModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "基础路由模块";
    }

    @Override
    public String[] getLore() {
        return new String[]{
				"TIDX:02",
        		"此模块用于合成并激活", 
        		" 具有特定用途的路由器模块 "
        };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack(8));
        recipe.shape("PPP", "PRP", "PBP");
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('B', STBUtil.makeColouredMaterial(Material.INK_SACK, DyeColor.BLUE));
        return recipe;
    }
}
