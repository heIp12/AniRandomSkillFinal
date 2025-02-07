package util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import api.Rule;

public class HoloMove {
	public static List<ArmorStand> as = new ArrayList();
	  
	public static void create(Location loc, String name, int tick, int speed,int startcount) {
		if(loc.getWorld().getLivingEntities().size() < 300) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.plugin, ()->{
				ArmorStand am = create(loc);
				new Ar(Rule.plugin,am,tick,name,speed,startcount);
			});
		}
	}

	public static ArmorStand create(Location loc)
	  {
	    Location locPatch = loc.clone().add(0,1,0);
	    ArmorStand as = (ArmorStand)locPatch.getWorld().spawnEntity(locPatch, EntityType.ARMOR_STAND);
	    as.setVisible(false);
	    as.setGravity(false);
	    as.setMarker(true);
	    as.setCanPickupItems(false);
	    as.setCustomName(" ");
	    as.setCustomNameVisible(true);
	    return as;
	  }
	
	private static class Ar
    implements Runnable {
        int taskId;
        ArmorStand as;
        int tick = 0;
        int speed = 10;
        int count2 = 0;
        int count = 0;
        String name = "";
        
        public Ar(Plugin plugin,ArmorStand as, int tick, String name,int speed,int startcount) {
            this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, (Runnable)this, (long)1, 1);
            this.as = as;
            this.tick = tick;
            this.speed = speed;
            this.name = name;
            count2 = startcount;
			as.setCustomName(name.substring(0, Math.min(count2,name.length())));
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
        		count++;
        		if(count > speed) {
        			count = 0;
        			count2++;
        			as.setCustomName(name.substring(0, Math.min(count2,name.length())));
        		}
        	} else {
        		stop();
        	}
        }
	}
	
}
