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
        super(gui, slot, "筛选器模式");
        Validate.isTrue(gui.getOwningItem() instanceof Filtering, "设置筛选器后只能加入筛选后的指定物品!");
        add(Filter.FilterType.MATERIAL, ChatColor.GRAY, new MaterialData(Material.STONE),
                //"Match material only");
                "筛选所有同一ID的物品", "例如染料和羊毛,无视颜色");
        add(Filter.FilterType.BLOCK_DATA, ChatColor.DARK_AQUA, new MaterialData(Material.DIAMOND_SWORD),
                "筛选所有同一类型的物品", "例如染料和羊毛,筛选同颜色的");
        add(Filter.FilterType.ITEM_META, ChatColor.LIGHT_PURPLE, new MaterialData(Material.ENCHANTED_BOOK),
                "只筛选完全相同的物品", "包括物品NBT数据");
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
