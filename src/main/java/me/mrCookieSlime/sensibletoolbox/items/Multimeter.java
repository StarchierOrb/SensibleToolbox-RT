package me.mrCookieSlime.sensibletoolbox.items;

import me.mrCookieSlime.sensibletoolbox.api.SensibleToolbox;
import me.mrCookieSlime.sensibletoolbox.api.energy.EnergyNet;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBItem;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBMachine;
import me.mrCookieSlime.sensibletoolbox.api.util.HoloMessage;
import me.mrCookieSlime.sensibletoolbox.api.util.STBUtil;
import me.mrCookieSlime.sensibletoolbox.items.components.SimpleCircuit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class Multimeter extends BaseSTBItem {
    private static final MaterialData md = new MaterialData(Material.ENCHANTED_BOOK);

    public Multimeter() {
        super();
    }

    public Multimeter(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public MaterialData getMaterialData() {
        return md;
    }

    @Override
    public String getItemName() {
        return "万用表";
    }

    @Override
    public String[] getLore() {
        return new String[]{
				"TID:670",
                "用于电缆和机器",
                "检查能源网连接",
                "或者说监控能量使用",
                "右键点击: " + ChatColor.RESET + "使用"
        };
    }

    @Override
    public boolean hasGlow() {
        return true;
    }

    @Override
    public Recipe getRecipe() {
        SimpleCircuit sc = new SimpleCircuit();
        registerCustomIngredients(sc);
        ShapedRecipe recipe = new ShapedRecipe(toItemStack());
        recipe.shape("IGI", "CSC", " T ");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('G', Material.GLOWSTONE_DUST);
        recipe.setIngredient('C', sc.getMaterialData());
        recipe.setIngredient('S', Material.SIGN);
        recipe.setIngredient('T', Material.STICK);
        return recipe;
    }

    @Override
    public void onInteractItem(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            EnergyNet net = SensibleToolbox.getEnergyNet(event.getClickedBlock());
            Player player = event.getPlayer();
            if (net != null) {
                showNetInfo(player, net, event.getClickedBlock());
            } else {
                BaseSTBMachine machine = SensibleToolbox.getBlockAt(event.getClickedBlock().getLocation(), BaseSTBMachine.class, true);
                if (machine != null && machine.getMaxCharge() > 0) {
                    showMachineInfo(player, machine, event.getClickedBlock());
                } else {
                    // nothing to examine here
                    STBUtil.complain(player);
                }
            }
            event.setCancelled(true);
        }
    }

    private void showNetInfo(final Player player, EnergyNet net, Block clicked) {
        String s1 = net.getCableCount() == 1 ? "" : "s";
        String s2 = net.getSourceCount() == 1 ? "" : "s";
        String s3 = net.getSinkCount() == 1 ? "" : "s";
        String[] lines = new String[] {
                net.getSourceCount() + ChatColor.GOLD.toString() + " source" + s2 + ChatColor.RESET + ", " +
                        net.getSinkCount() + ChatColor.GOLD.toString() + " sink" + s3,
                net.getCableCount() + ChatColor.GOLD.toString() + " 电缆" + s1,
                String.format("需求: " + ChatColor.GOLD + "%5.2f SCU/t", net.getDemand()),
                String.format("供应: " + ChatColor.GOLD + "%5.2f SCU/t", net.getSupply()),
        };
        HoloMessage.popup(player, clicked.getLocation(), lines);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0f, 2.0f);
    }

    private void showMachineInfo(Player player, BaseSTBMachine machine, Block clicked) {
        int n = machine.getAttachedEnergyNets().length;
        String s = n == 1 ? "" : "s";
        String[] lines = new String[] {
                ChatColor.GOLD + machine.getItemName() + ChatColor.RESET + ":  " + n + " 能源网络" + s,
                "充电: " + STBUtil.getChargeString(machine),
                "最大充电速度: " + ChatColor.GOLD + machine.getChargeRate() + " SCU/t",
        };
        HoloMessage.popup(player, clicked.getLocation(), lines);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0f, 2.0f);
    }
}
