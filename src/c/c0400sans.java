package c;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Stun;
import manager.Holo;
import util.AMath;

public class c0400sans extends c00main{
	private int skillcount = 0;
	int count = 0;
	
	public c0400sans(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1004;
		load();
		text();
		ARSystem.playSound(player, "c4select",0.8f);
		
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c4a", 0.5f);
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c4a", 0.5f);
		skill("c"+number+"_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c4a3", 0.3f);
		ARSystem.giveBuff(player, new Stun(player), 70);
		skill("c"+number+"_s3");
		return true;
	}
	
	@Override
	public boolean skill9() {
		ARSystem.playSound((Entity)player, "c4db", 0.9f - (0.1f*AMath.random(4)));
		return true;
	}
	@Override
	public boolean tick() {

		return true;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			int i = 30;
			if(ARSystem.AniRandomSkill != null) i = ARSystem.AniRandomSkill.getTime();
			
			if((130 - i) > AMath.random(100)) {
				player.setNoDamageTicks(5);
				ARSystem.playSound((Entity)player, "c4db", 0.8f);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(e.getCause() != DamageCause.ENTITY_ATTACK) {
			int i = 30;
			if(ARSystem.AniRandomSkill != null) i = ARSystem.AniRandomSkill.getTime();
			
			if(130 - i > AMath.random(100)) {
				player.setNoDamageTicks(5);
				ARSystem.playSound((Entity)player, "c4db", 0.8f);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
