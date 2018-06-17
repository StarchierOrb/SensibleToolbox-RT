package me.mrCookieSlime.sensibletoolbox.items.components;

import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.blocks.machines.FiftyKBatteryBox;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class UnlinkedSCURelay extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.PRISMARINE_CRYSTALS);

    public UnlinkedSCURelay() {
        super();
    }

    public UnlinkedSCURelay(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "δ���ӵ� SCU �̵���";
    }

    @Override
    public String[] getLore() {
        return new String[] {"TID:671", "ͨ���ɶ�ʹ��", "��������������������" };
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        FiftyKBatteryBox bb = new FiftyKBatteryBox();
        IntegratedCircuit ic = new IntegratedCircuit();
        EnergizedGoldIngot eg = new EnergizedGoldIngot();
        registerCustomIngredients(bb, ic, eg);
        recipe.shape("GCG", " E ", " B ");
        recipe.setIngredient('B', bb.getMaterialData());
        recipe.setIngredient('C', ic.getMaterialData());
        recipe.setIngredient('E', Material.ENDER_CHEST);
        recipe.setIngredient('G', eg.getMaterialData());
        return recipe;
    }
}
