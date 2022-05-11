package net.kyrptonaught.CmdPortals;

import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.kyrptonaught.customportalapi.util.SHOULDTP;
import net.kyrptonaught.datapackportals.DatapackPortalsMod;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;


public class CMDPortalsMod implements ModInitializer {
    public static Identifier BUNGEECORD_ID = new Identifier("bungeecord", "main");

    @Override
    public void onInitialize() {
        DatapackPortalsMod.registerPortalType("cmdportals", CMDPortalData.class);
    }

    public static class CMDPortalData extends DatapackPortalsMod.DefaultPortalData {
        public String command;

        @Override
        public PortalLink toLink(Identifier identifier) {
            this.dim = "minecraft:overworld";
            PortalLink link = super.toLink(identifier);

            link.getBeforeTPEvent().register(entity -> {
                if (entity instanceof ServerPlayerEntity player) {
                    player.getServer().getCommandManager().execute(player.getCommandSource(), command);
                }
                return SHOULDTP.CANCEL_TP;
            });
            return link;
        }
    }
}