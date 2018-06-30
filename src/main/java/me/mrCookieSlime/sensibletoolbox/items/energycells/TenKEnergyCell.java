package me.mrCookieSlime.sensibletoolbox.items.energycells;

import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.components.EnergizedIronIngot;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class TenKEnergyCell extends EnergyCell {
    public TenKEnergyCell() {
        super();
    }

    public TenKEnergyCell(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public int getMaxCharge() {
        return 10000;
    }

    @Override
    public int getChargeRate() {
        return 100;
    }
/*
    @Override
    public Color getCellColor() {
        return Color.MAROON;
    }
*/
    @Override
    public String getItemName() {
        return "10K ÄÜÁ¿µç³Ø";
    }
	
    @Override
    public String[] getExtraLore() {
        return new String[] { 
		        STBUtil.getChargeString(this),
				" ",
        		"TID:657"
        };
    }
	
    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
		FiveKEnergyCell cell = new FiveKEnergyCell();
		cell.setCharge(0.0);
        EnergizedIronIngot ei = new EnergizedIronIngot();
        registerCustomIngredients(cell, ei);
        recipe.shape("WWW", "CCC", "III");
		recipe.setIngredient('I', ei.getMaterialData());
        recipe.setIngredient('W', STBUtil.makeWildCardMaterialData(Material.WOOD));
		recipe.setIngredient('C', STBUtil.makeWildCardMaterialData(cell));
        return recipe;
    }

}
