package manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import ars.Rule;


public class Holo {
	public static void create(Location loc, String name, int tick, Vector v) {
		if(loc.getWorld().getLivingEntities().size() < 300) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				ArmorStand am = create(loc,name);
				new Ar(Rule.gamerule,am,tick,v);
			});
		}
	}

	public static ArmorStand create(Location loc, String name)
	  {
	    Location locPatch = loc.add(0,1,0);
	    ArmorStand as = (ArmorStand)locPatch.getWorld().spawnEntity(locPatch, EntityType.ARMOR_STAND);
	    as.setVisible(false);
	    as.setGravity(false);
	    as.setMarker(true);
	    as.setCanPickupItems(false);
	    as.setCustomName(name);
	    as.setCustomNameVisible(true);
	    return as;
	  }
	
	private static class Ar
    implements Runnable {
        int taskId;
        ArmorStand as;
        int tick = 0;
        Vector v;
        
        public Ar(Plugin plugin,ArmorStand as, int tick, Vector v) {
            this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, (Runnable)this, (long)1, 1);
            this.as = as;
            this.tick = tick;
            this.v = v;
        }

        public void stop() {
        	as.remove();
            Bukkit.getServer().getScheduler().cancelTask(this.taskId);
        }

        @Override
        public void run() {
        	if(tick>0) {
        		if(as.getLocation().getWorld().getLivingEntities().size() > 100) tick--;
        		if(as.getLocation().getWorld().getLivingEntities().size() > 200) tick--;
        		if(as.getLocation().getWorld().getLivingEntities().size() > 250) tick--;
        		tick--;
        		as.teleport(as.getLocation().add(v));
        	} else {
        		stop();
        	}
        }
	}
	
}
