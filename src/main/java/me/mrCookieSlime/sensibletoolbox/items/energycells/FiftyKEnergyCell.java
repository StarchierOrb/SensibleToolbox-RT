package me.mrCookieSlime.sensibletoolbox.items.energycells;

import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.components.EnergizedIronIngot;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class FiftyKEnergyCell extends EnergyCell {
    public FiftyKEnergyCell() {
        super();
    }

    public FiftyKEnergyCell(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public int getMaxCharge() {
        return 50000;
    }

    @Override
    public int getChargeRate() {
        return 500;
    }
/*
    @Override
    public Color getCellColor() {
        return Color.PURPLE;
    }
*/
    @Override
    public String getItemName() {
        return "50K ÄÜÁ¿µç³Ø";
    }
	
    @Override
    public String[] getExtraLore() {
        return new String[] { 
		        STBUtil.getChargeString(this),
				" ",
        		"TID:659"
        };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        TenKEnergyCell cell = new TenKEnergyCell();
        cell.setCharge(0.0);
        EnergizedIronIngot ei = new EnergizedIronIngot();
        registerCustomIngredients(cell, ei);
        recipe.shape("III", "CCC", "III");
        recipe.setIngredient('I', ei.getMaterialData());
        recipe.setIngredient('C', STBUtil.makeWildCardMaterialData(cell));
        return recipe;
    }
}
