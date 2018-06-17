package me.mrCookieSlime.sensibletoolbox.api.gui;

import me.mrCookieSlime.sensibletoolbox.api.Filtering;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;
import me.mrCookieSlime.sensibletoolbox.api.util.Filter;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * A GUI gadget which can display and change a filter's filter type.
 */
public class FilterTypeGadget extends CyclerGadget<Filter.FilterType> {
    /**
     * Construct a filter type gadget.
     *
     * @param gui the GUI that the gadget belongs to
     * @param slot the GUI slot that the gadget occupies
     */
    public FilterTypeGadget(InventoryGUI gui, int slot) {
        super(gui, slot, "ɸѡ��ģʽ");
        Validate.isTrue(gui.getOwningItem() instanceof Filtering, "����ɸѡ����ֻ�ܼ���ɸѡ���ָ����Ʒ!");
        add(Filter.FilterType.MATERIAL, ChatColor.GRAY, new MaterialData(Material.STONE),
                //"Match material only");
                "ɸѡ����ͬһID����Ʒ", "����Ⱦ�Ϻ���ë,������ɫ");
        add(Filter.FilterType.BLOCK_DATA, ChatColor.DARK_AQUA, new MaterialData(Material.DIAMOND_SWORD),
                "ɸѡ����ͬһ���͵���Ʒ", "����Ⱦ�Ϻ���ë,ɸѡͬ��ɫ��");
        add(Filter.FilterType.ITEM_META, ChatColor.LIGHT_PURPLE, new MaterialData(Material.ENCHANTED_BOOK),
                "ֻɸѡ��ȫ��ͬ����Ʒ", "������ƷNBT����");
        setInitialValue(((Filtering)getGUI().getOwningItem()).getFilter().getFilterType());
    }

    @Override
    protected boolean ownerOnly() {
        return false;
    }

    @Override
    protected void apply(BaseSTBItem stbItem, Filter.FilterType newValue) {
        ((Filtering) getGUI().getOwningItem()).getFilter().setFilterType(newValue);
    }
}
