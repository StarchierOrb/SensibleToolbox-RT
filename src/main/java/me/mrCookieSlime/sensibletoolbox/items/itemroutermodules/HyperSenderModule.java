package me.mrCookieSlime.sensibletoolbox.items.itemroutermodules;

import me.mrCookieSlime.sensibletoolbox.items.components.SubspaceTransponder;
import me.mrCookieSlime.sensibletoolbox.util.UnicodeSymbol;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.Dye;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class HyperSenderModule extends AdvancedSenderModule {
    //private static final Dye md = makeDye(DyeColor.CYAN);
    private static final MaterialData md = new MaterialData(Material.PAPER);

    public HyperSenderModule() {
        super();
    }

    public HyperSenderModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "I.R. ģ��: ��ά������";
    }

    @Override
    public String[] getLore() {
        return new String[]{
				"TIDX:07",
                "���԰�װ����Ʒ·������.",
                "���κ�һ��������һ��",
                "�����ӵĽ�����������Ʒ",
                "������ �Ѱ�װ������ģ��",
                "  ��·����: " + ChatColor.RESET + " ���ӿ�ά������",
                UnicodeSymbol.ARROW_UP.toUnicode() + " + ������: " + ChatColor.RESET + " �Ͽ�����������"
        };
    }

    @Override
    public Recipe getRecipe() {
        SenderModule sm = new SenderModule();
        SubspaceTransponder st = new SubspaceTransponder();
        registerCustomIngredients(sm, st);
        ShapelessRecipe recipe = new ShapelessRecipe(toItemStack());
        recipe.addIngredient(sm.getMaterialData());
        recipe.addIngredient(st.getMaterialData());
        return recipe;
    }

    protected boolean inRange(Location ourLoc) {
        return ourLoc != null;
    }
}
