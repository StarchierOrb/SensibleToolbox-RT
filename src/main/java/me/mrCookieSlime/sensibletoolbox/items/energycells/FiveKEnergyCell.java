package me.mrCookieSlime.sensibletoolbox.items.energycells;

import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.components.EnergizedIronIngot;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class FiveKEnergyCell extends EnergyCell {
    public FiveKEnergyCell() {
        super();
    }

    public FiveKEnergyCell(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public int getMaxCharge() {
        return 5000;
    }

    @Override
    public int getChargeRate() {
        return 70;
    }
/*
    @Override
    public Color getCellColor() {
        return Color.MAROON;
    }
*/
    @Override
    public String getItemName() {
        return "5K ÄÜÁ¿µç³Ø";
    }

    @Override
    public String[] getExtraLore() {
        return new String[] { 
		        STBUtil.getChargeString(this),
				" ",
        		"TID:656"
        };
    }
	
    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
		OneKEnergyCell cell = new OneKEnergyCell();
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
