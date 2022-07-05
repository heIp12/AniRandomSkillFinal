package c;

import java.sql.Time;
import java.util.Timer;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import ars.ARSystem;
import ars.Rule;
import types.modes;
import util.AMath;
import util.MSUtil;

public class c0200kirito extends c00main{
	int count1 = 0;
	int count2 = 0;
	int c=0;
	
	public c0200kirito(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1002;
		load();
		text();
		ARSystem.playSound(player, "c2select");
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		c++;
		ARSystem.playSound((Entity)player, "c2a"+AMath.random(2));
		if(isps && c%2==0) {
			delay(new Runnable() { @Override public void run() { skill("c"+number+"_s2"); }}, 4);
		}
		count1++;
		if(!isps &count1 >= 20 && count2 >= 8) {
			spskillon();
			spskillen();
			skill("c"+number+"_sp");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
		if(isps) {
			delay(new Runnable() { @Override public void run() {
				skill("c"+number+"_s1");
				ARSystem.playSound((Entity)player, "c2a"+AMath.random(2));
				
			}}, 4);
		}
		count2++;
		if(!isps &count1 >= 20 && count2 >= 8) {
			spskillon();
			spskillen();
			skill("c"+number+"_sp");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c"+number+"_s4");
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			e.setDamage(e.getDamage()*0.7);
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 3);
		}
		return true;
	}
}
