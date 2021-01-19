package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;

public class spawn {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("spawn")
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            ServerWorld mainworld = player.getServer().getWorld(World.OVERWORLD);

                            player.sendMessage(new LiteralText(messages.get(1,player.getGameProfile().getName())),false);
                            player.sendMessage(new LiteralText(messages.get(23,player.getGameProfile().getName())),true);
                            player.teleport(mainworld,mainworld.getLevelProperties().getSpawnX()+10,mainworld.getLevelProperties().getSpawnY()+5,mainworld.getLevelProperties().getSpawnZ()+9,0,0);

                            return 1;
                        })
        );
    }
}
