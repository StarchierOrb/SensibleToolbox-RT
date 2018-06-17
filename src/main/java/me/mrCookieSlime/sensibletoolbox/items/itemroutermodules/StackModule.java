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
        return "I.R. 模块: 容量提升器";
    }

    @Override
    public String[] getLore() {
        return new String[]{
				"TIDX:14",
                "可以安装到物品路由器中作为",
                "被动模块; 每一个提升器",
                "使每一次操作处理的物品",
                "提升一倍, 直到物品达到",
                "最大堆叠.",
                "安装超过6个提升器将失效.",
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
