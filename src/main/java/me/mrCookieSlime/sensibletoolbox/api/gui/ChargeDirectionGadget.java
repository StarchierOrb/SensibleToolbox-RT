package me.mrCookieSlime.sensibletoolbox.api.gui;

import me.mrCookieSlime.sensibletoolbox.api.energy.ChargeDirection;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBMachine;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * A GUI gadget which can display and change the charge direction
 * for a STB device which can hold an energy cell.
 */
public class ChargeDirectionGadget extends CyclerGadget<ChargeDirection> {
    /**
     * Constructs a charge direction gadget.
     *
     * @param gui the GUI that the gadget belongs to
     * @param slot the GUI slot that the gadget occupies
     */
    public ChargeDirectionGadget(InventoryGUI gui, int slot) {
 //       super(gui, slot, "Charge");
        super(gui, slot, "能源模式");
        add(ChargeDirection.MACHINE, ChatColor.GOLD, new MaterialData(Material.MAGMA_CREAM),
                "TIDX:21", "能源将从具有储能功能的", "电池或机器传输到", "这台机器上！");
        add(ChargeDirection.CELL, ChatColor.GREEN, new MaterialData(Material.SLIME_BALL),
                "TIDX:22", "能量将从这台机器传输到", "另外一个具有储能功能的", "电池或机器上");
        setInitialValue(((BaseSTBMachine) gui.getOwningItem()).getChargeDirection());
    }

    @Override
    protected boolean ownerOnly() {
        return false;
    }

    @Override
    protected void apply(BaseSTBItem stbItem, ChargeDirection newValue) {
        ((BaseSTBMachine) stbItem).setChargeDirection(newValue);
    }
}
