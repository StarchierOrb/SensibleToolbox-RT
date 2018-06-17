package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

public class SpeedModule extends ItemRouterModule {
    private static final MaterialData md = new MaterialData(Material.BLAZE_POWDER);

    public SpeedModule() {
    }

    public SpeedModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "I.R. ģ��: ���ٳ���";
    }

    @Override
    public String[] getLore() {
        return new String[]{
				"TIDX:13",
                "���԰�װ����Ʒ·�����в���Ϊ",
                "����ģ��; ��������·��������:",
                "0 ��ģ�� = 1 �β��� / 20 ticks��1s��",
                "1 = 1/15, 2 = 1/10, 3 = 1/5",
                "��װ����3���ٶ���������ʧЧ."
        };
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        recipe.addIngredient(bm.getMaterialData());
        recipe.addIngredient(Material.BLAZE_POWDER);
        recipe.addIngredient(Material.EMERALD);
        return recipe;
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }
}
