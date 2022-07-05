package util;

import java.util.Random;

import org.bukkit.util.Vector;

public class AMath {
	static Random rand = new Random(System.currentTimeMillis()); 
	
	static public int random(int i,int j) {
		return rand.nextInt(j)-i+1;
	}
	
	static public int random(int i) {
		return rand.nextInt(i)+1;
	}
	
	public static Vector Vector(Vector vector) {
		final double x = vector.getX(), y = vector.getY(), z = vector.getZ();
		if (Double.isInfinite(x) || Double.isNaN(x)) vector.setX(0);
		if (Double.isInfinite(y) || Double.isNaN(y)) vector.setY(0);
		if (Double.isInfinite(z) || Double.isNaN(z)) vector.setZ(0);
		return vector;
	}

	public static double round(double speed, int i) {
		int n = 1;
		for(;i>0;i--) {
			n*=10;
		}
		return (Math.round(speed*n)/(double)n);
	}
	
}
