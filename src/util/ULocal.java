package util;

import org.bukkit.Location;
import org.bukkit.util.Vector;


public class Local {
	
    public static Location lookAt(Location loc, Location lookat) {
        loc = loc.clone();

        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();

        if (dx != 0) {
            if (dx < 0) {
                loc.setYaw((float) (1.5 * Math.PI));
            } else {
                loc.setYaw((float) (0.5 * Math.PI));
            }
            loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0) {
            loc.setYaw((float) Math.PI);
        }

        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        loc.setPitch((float) -Math.atan(dy / dxz));

        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);
        if(Float.isInfinite(loc.getPitch()) || Float.isNaN(loc.getPitch()) || !Float.isFinite(loc.getPitch())) {
        	loc.setPitch(0);
        }
        loc.setPitch(loc.getPitch()%360);
        return loc;
    }
    
    public static Location offset(Location loc, Vector vt) {
    	loc = loc.clone();
    	vt = vt.clone();
    	
    	float startXOffset = (float) vt.getX();
		float startYOffset = (float) vt.getY();
		float startZOffset = (float) vt.getZ();
		Vector startDirection = loc.getDirection().normalize();
		Vector horizOffset = new Vector(-startDirection.getZ(), 0.0, startDirection.getX()).normalize();
		loc.add(horizOffset.multiply(startZOffset)).getBlock().getLocation();
		loc.add(loc.getDirection().multiply(startXOffset));
		loc.setY(loc.getY() + startYOffset);

    	return loc;
    }
}
