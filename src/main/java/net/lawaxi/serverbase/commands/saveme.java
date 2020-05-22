package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class saveme {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("saveme")
                        .executes(ctx -> {
                            File worldsavefolder = new File("Lawaxi"+File.separator+"datasaves");
                            if(!worldsavefolder.exists())
                                worldsavefolder.mkdir();

                            try{

                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                String uuid = player.getUuid().toString();

                                if(player.getServer().getPlayerManager().getWhitelist().isAllowed(player.getGameProfile()))
                                {
                                    File world = new File("world" + File.separator + "playerdata" + File.separator + uuid + ".dat");
                                    File worldsave = new File(configs.backupfolder+File.separator + uuid + ".dat");

                                    player.networkHandler.disconnect(new LiteralText(messages.get(59,player.getGameProfile().getName())));
                                    FileUtils.copyFile(world,worldsave);
                                }
                                else
                                {
                                    player.sendMessage(new LiteralText(messages.get(58,player.getGameProfile().getName())),true);
                                }

                            }
                            catch (IOException e)
                            {
                            }

                            return 1;
                            })
        );
    }
}
