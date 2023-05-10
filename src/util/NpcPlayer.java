package util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;




import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.WorldServer;

public class NpcPlayer {
	public static Player player;
	public static Player npc(Location loc) {
		
		if(player == null) {
			String name ="@ARS";
			MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
	        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
	       
	        Player target = Bukkit.getServer().getPlayer(name);
	        EntityPlayer npc;
	        
	        if (target != null) {
	            npc = new EntityPlayer(server, world, new GameProfile(target.getUniqueId(), name), new PlayerInteractManager(world));
	        } else {
	            OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(name);
	            npc = new EntityPlayer(server, world, new GameProfile(op.getUniqueId(), name), new PlayerInteractManager(world));
	        }
	        npc.setLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getPitch(),loc.getYaw());
	        Player p = (Player) npc.getBukkitEntity();
	        p.teleport(loc);
	        player = p;
		}
        return player;

	}
	
}
