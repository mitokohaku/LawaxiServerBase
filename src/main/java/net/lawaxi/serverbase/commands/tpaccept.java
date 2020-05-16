package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.lawaxi.serverbase.utils.tparequest;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;

public class tpaccept {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("tpaccept")
                        .executes(ctx -> {
                            ServerPlayerEntity who = ctx.getSource().getPlayer();
                            tparequest request = tparequest.search(who);
                            if(request==null)
                            {
                                who.sendMessage(new LiteralText(messages.m.get(34)),false);
                            }
                            else
                            {
                                ServerPlayerEntity me,to;
                                if(!request.mode)
                                {
                                    me = request.me;
                                    to = who;
                                }
                                else
                                {
                                    to = request.me;
                                    me = who;
                                }

                                me.sendMessage(new LiteralText(messages.m.get(0)),false);
                                to.sendMessage(new LiteralText(messages.m.get(0)),false);
                                me.sendMessage(new LiteralText(messages.m.get(1).replace("%to%",to.getEntityName())),true);

                                me.teleport((ServerWorld)to.world,to.getX(),to.getY(),to.getZ(),to.yaw,to.pitch);
                            }
                            return 1;
                        })
        );
    }
}
