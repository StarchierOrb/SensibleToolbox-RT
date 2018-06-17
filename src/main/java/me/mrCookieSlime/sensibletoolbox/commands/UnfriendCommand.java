package me.mrCookieSlime.sensibletoolbox.commands;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.desht.dhutils.DHValidate;
import me.desht.dhutils.MiscUtil;
import me.mrCookieSlime.sensibletoolbox.SensibleToolboxPlugin;
import me.mrCookieSlime.sensibletoolbox.api.FriendManager;

public class UnfriendCommand extends STBAbstractCommand {
	
    public UnfriendCommand() {
        super("stb unfriend", 1);
        setPermissionNode("stb.commands.unfriend");
        setUsage("/<command> unfriend <玩家名>");
    }

    @Override
    public boolean execute(Plugin plugin, CommandSender sender, String[] args) {
        FriendManager fm = ((SensibleToolboxPlugin) plugin).getFriendManager();

        Player target = getTargetPlayer(sender, getStringOption("p"));

        UUID id = getID(args[0]);
        DHValidate.notNull(id, "未知的玩家: " + args[0]);
        fm.removeFriend(target.getUniqueId(), id);
        MiscUtil.statusMessage(sender, target.getName() + " 不再是你的好友了！  玩家识别码: " + args[0]);

        return true;
    }
}
