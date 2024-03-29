package util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockUtil {
	public static boolean isPathable(Block block) {
		return isPathable(block.getType());
	}
	public static boolean isPathable(Material material) {
		return
				material == Material.AIR ||
				material == Material.SAPLING ||
				material == Material.WATER ||
				material == Material.STATIONARY_WATER ||
				material == Material.POWERED_RAIL ||
				material == Material.DETECTOR_RAIL ||
				material == Material.LONG_GRASS ||
				material == Material.DEAD_BUSH ||
				material == Material.YELLOW_FLOWER ||
				material == Material.RED_ROSE ||
				material == Material.BROWN_MUSHROOM ||
				material == Material.RED_MUSHROOM ||
				material == Material.TORCH ||
				material == Material.FIRE ||
				material == Material.REDSTONE_WIRE ||
				material == Material.CROPS ||
				material == Material.SIGN_POST ||
				material == Material.LADDER ||
				material == Material.RAILS ||
				material == Material.WALL_SIGN ||
				material == Material.LEVER ||
				material == Material.STONE_PLATE ||
				material == Material.WOOD_PLATE ||
				material == Material.REDSTONE_TORCH_OFF ||
				material == Material.REDSTONE_TORCH_ON ||
				material == Material.STONE_BUTTON ||
				material == Material.SNOW ||
				material == Material.SUGAR_CANE_BLOCK ||
				material == Material.VINE ||
				material == Material.WATER_LILY ||
				material == Material.NETHER_STALK ||
				material == Material.LADDER ||
				material.name().contains("plant".toUpperCase()) || 
				material.name().contains("flower".toUpperCase()) || 
				material.getId() == 101 || 
				material.getId() == 102 || 
				material.getId() == 106 || 
				material.getId() == 30 || 
				material.name().contains("door".toUpperCase()) || 
				material.name().contains("gate".toUpperCase()) || 
				material == Material.CARPET;
	}
	
	public static boolean isAirbone(Location loc,int size) {
		loc = loc.clone();
		for(int i=0;i<size+1;i++) {
			if(loc.getY() > 0) {
				if(!isPathable(loc.getBlock())) {
					if(loc.getBlock().getType() == Material.WATER) continue;
					if(loc.getBlock().getType() == Material.STATIONARY_WATER) continue;
					if(loc.getBlock().getType() == Material.STATIONARY_LAVA) continue;
					if(loc.getBlock().getType() == Material.LAVA) continue;
					return false;
				}
			}
			loc.add(0,-1,0);
		}
		
		return true;
	}
}
