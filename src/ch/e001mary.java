package ch;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.TimeStop;
import c.c00main;
import util.GetChar;
import util.Map;

public class e001mary extends c00main{
	int count = 0;
	int size = 0;
	HashMap<Player,Integer> code;
	
	public e001mary(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 901;
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c901s1");
		skill("c901_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c901s2");
		skill("c901_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c901s3");
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		return true;
	}
	
	
	@Override
	public boolean tick() {
		skill("c901_s3");
		if(code == null) {
			code = new HashMap<>();
			size = Rule.c.size()-2;
			for(Player p : Rule.c.keySet()) {
				code.put(p, Rule.c.get(p).getCode());
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		count++;
		if(count >= size && skillCooldown(0)) {
			count = 0;
			spskillon();
			spskillen();
			ARSystem.playSoundAll("c901sp");
			ARSystem.giveBuff(player, new TimeStop(player), 200);
			skillmult += 0.2;
			delay(()->{
				ARSystem.playSoundAll("c901sp2");
				for(Player play : code.keySet()) {
					if(player != play)
					Rule.c.put(play, GetChar.get(play, plugin, ""+code.get(play)));
					play.teleport(Map.randomLoc());
				}
				player.teleport(Map.randomLoc());
			},170);
		}
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setVelocity(new Vector(0,-3,0));
		}
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(0);
			e.setCancelled(true);
		} else {

		}
		return true;
	}
}
