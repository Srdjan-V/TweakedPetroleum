package srki2k.tweakedpetroleum.common.commands;

import blusunrize.immersiveengineering.api.Lib;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

import java.util.ArrayList;
import java.util.Locale;

public class CommandHelp extends CommandHandler.TPSubCommand {
    @Override
    public String getIdent() {
        return "help";
    }

    @Override
    public void perform(CommandHandler handler, MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length == 0) {
            String h = I18n.translateToLocal(getHelp(""));
            for (String s : h.split("<br>")) {
                sender.sendMessage(new TextComponentString(s));
            }
            StringBuilder sub = new StringBuilder();
            int i = 0;
            for (CommandHandler.TPSubCommand com : handler.commands) {
                sub.append((i++) > 0 ? ", " : "").append(com.getIdent());
            }
            sender.sendMessage(new TextComponentTranslation(Lib.CHAT_COMMAND + "available", sub.toString()));
            return;
        }

        StringBuilder sub = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            sub.append(".").append(args[i]);
        }
        for (CommandHandler.TPSubCommand com : handler.commands) {
            if (com.getIdent().equalsIgnoreCase(args[1])) {
                String h = I18n.translateToLocal(com.getHelp(sub.toString()));
                for (String s : h.split("<br>")) {
                    sender.sendMessage(new TextComponentString(s));
                }
            }
        }

    }

    @Override
    public ArrayList<String> getSubCommands(CommandHandler h, MinecraftServer server, ICommandSender sender, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (CommandHandler.TPSubCommand sub : h.commands) {
            if (sub != this && sender.canUseCommand(sub.getPermissionLevel(), h.getName())) {
                if (args.length == 1) {
                    if (args[0].isEmpty() || sub.getIdent().startsWith(args[0].toLowerCase(Locale.ENGLISH))) {
                        list.add(sub.getIdent());
                    }
                } else if (sub.getIdent().equalsIgnoreCase(args[0])) {
                    String[] redArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, redArgs, 0, redArgs.length);
                    ArrayList<String> subCommands = sub.getSubCommands(h, server, sender, redArgs);
                    if (subCommands != null) {
                        list.addAll(subCommands);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public int getPermissionLevel() {
        return 0;
    }
}
