package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;

public class PullerModule extends DirectionalItemRouterModule {
    //private static final Dye md = makeDye(DyeColor.LIME);
    private static final MaterialData md = new MaterialData(Material.PAPER);

    public PullerModule() {
    }

    public PullerModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "I.R. ģ��: �ƶ�װ��";
    }

    @Override
    public String[] getLore() {
        return makeDirectionalLore("TIDX:08", "���԰�װ����Ʒ·������.", "���ٽ������ӻ�洢�������Ƴ���Ʒ");
    }

    @Override
    public Recipe getRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        recipe.addIngredient(bm.getMaterialData());
        recipe.addIngredient(Material.PISTON_STICKY_BASE);
        return recipe;
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public boolean execute(Location loc) {
        return getItemRouter() != null && doPull(getFacing(), loc);
    }
}
