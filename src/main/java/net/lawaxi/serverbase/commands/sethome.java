package net.lawaxi.serverbase.commands;

import com.google.common.io.Files;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.WorldDiscription;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class sethome {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("sethome")
                        .then(CommandManager.argument(messages.get(18,"null"), StringArgumentType.string())
                                .executes(ctx -> {


                                    if(!configs.homefolder.exists())
                                        configs.homefolder.mkdir();

                                    ServerPlayerEntity player =ctx.getSource().getPlayer();
                                    File homefolder = new File(configs.homefolder,player.getEntityName());
                                    if(!homefolder.exists())
                                        homefolder.mkdir();

                                    String homename=StringArgumentType.getString(ctx,messages.get(18,"null"));
                                    File homefile = new File(configs.homefolder,player.getEntityName() +File.separator+homename+".yml");
                                    if(homefile.exists())
                                    {
                                        player.sendMessage(new LiteralText(messages.get(19,player.getGameProfile().getName()).replace("%name%",homename)),false);
                                    }
                                    else
                                    {
                                        try{
                                            String world = WorldDiscription.getDiscription(player.getServerWorld(),player.getServer());
                                            if(world.equals("shit"))
                                            {
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(20,player.getGameProfile().getName())),false);
                                            }
                                            else
                                            {
                                                BufferedWriter buffer = Files.newWriter(homefile, StandardCharsets.UTF_8);

                                                buffer.write(world);
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getX()));
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getY()));
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getZ()));

                                                buffer.close();
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(21,player.getGameProfile().getName()).replace("%name%",homename)),false);
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(2,player.getGameProfile().getName()).replace("%to%",homename)),true);
                                            }
                                        }
                                        catch (IOException e)
                                        {
                                        }
                                    }
                                    return 1;
                                }))
                        .executes(ctx -> {
                                if(!configs.homefolder.exists())
                                    configs.homefolder.mkdir();

                                ServerPlayerEntity player =ctx.getSource().getPlayer();
                                File homefolder = new File(configs.homefolder,player.getEntityName());
                                if(!homefolder.exists())
                                     homefolder.mkdir();

                                String homename="home";
                                File homefile = new File(configs.homefolder,player.getEntityName() +File.separator+homename+".yml");
                                if(homefile.exists())
                                {
                                    player.sendMessage(new LiteralText(messages.get(19,player.getGameProfile().getName()).replace("%name%",homename)),false);
                                }
                                else
                                {
                                try{
                                    String world = WorldDiscription.getDiscription(player.getServerWorld(),player.getServer());
                                    if(world.equals("shit"))
                                    {
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(20,player.getGameProfile().getName())),false);
                                    }
                                    else
                                    {
                                        BufferedWriter buffer = Files.newWriter(homefile, StandardCharsets.UTF_8);

                                        buffer.write(world);
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getX()));
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getY()));
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getZ()));

                                        buffer.close();
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(21,player.getGameProfile().getName()).replace("%name%",homename)),false);
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(2,player.getGameProfile().getName()).replace("%to%",homename)),true);
                                    }
                                }
                                catch (IOException e)
                                {
                                }
                            }
                            return 1;
                        })
        );
    }
}
