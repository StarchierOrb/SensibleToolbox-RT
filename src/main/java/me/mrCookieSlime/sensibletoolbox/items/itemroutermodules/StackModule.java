package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

public class StackModule extends ItemRouterModule {
    private static final MaterialData md = new MaterialData(Material.PRISMARINE_CRYSTALS);

    public StackModule() {
    }

    public StackModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "I.R. ģ��: ����������";
    }

    @Override
    public String[] getLore() {
        return new String[]{
				"TIDX:14",
                "���԰�װ����Ʒ·��������Ϊ",
                "����ģ��; ÿһ��������",
                "ʹÿһ�β����������Ʒ",
                "����һ��, ֱ����Ʒ�ﵽ",
                "���ѵ�.",
                "��װ����6����������ʧЧ.",
        };
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        recipe.addIngredient(bm.getMaterialData());
        recipe.addIngredient(Material.BRICK);
        return recipe;
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }
}
