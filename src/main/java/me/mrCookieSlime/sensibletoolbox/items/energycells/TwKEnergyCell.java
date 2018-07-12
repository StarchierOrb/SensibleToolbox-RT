package me.mrCookieSlime.sensibletoolbox.items.energycells;

import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.components.EnergizedIronIngot;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class TwKEnergyCell extends EnergyCell {
    public TwKEnergyCell() {
        super();
    }

    public TwKEnergyCell(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public int getMaxCharge() {
        return 20000;
    }

    @Override
    public int getChargeRate() {
        return 250;
    }
/*
    @Override
    public Color getCellColor() {
        return Color.MAROON;
    }
*/
    @Override
    public String getItemName() {
        return "20K ÄÜÁ¿µç³Ø";
    }
	
    @Override
    public String[] getExtraLore() {
        return new String[] { 
		        STBUtil.getChargeString(this),
				" ",
        		"TID:658"
        };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
		TenKEnergyCell cell = new TenKEnergyCell();
		cell.setCharge(0.0);
        EnergizedIronIngot ei = new EnergizedIronIngot();
        registerCustomIngredients(cell, ei);
        recipe.shape("WWW", "C C", "III");
		recipe.setIngredient('I', ei.getMaterialData());
        recipe.setIngredient('W', STBUtil.makeWildCardMaterialData(Material.IRON_INGOT));
		recipe.setIngredient('C', STBUtil.makeWildCardMaterialData(cell));
        return recipe;
    }

}
