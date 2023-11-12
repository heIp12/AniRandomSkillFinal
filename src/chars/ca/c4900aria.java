package chars.ca;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import chars.c.c00main;
import event.Skill;
import util.AMath;
import util.Holo;
import util.ULocal;

public class c4900aria extends c00main{
	boolean ps = false;
	LivingEntity target;
	int tropy = 0;
	
	public c4900aria(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1049;
		load();
		text();
		ARSystem.playSound(p, "c49select");
	}
	

	@Override
	public boolean skill1() {
		skill("c1049_s1");
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c1049_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c49s3");
		ARSystem.giveBuff(player, new Nodamage(player), 10);
		player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(-10)).add(0,2,0));
		ARSystem.heal(player, 8);
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(target != null) {
			ARSystem.playSound((Entity)player, "c49s4");
			player.teleport(ULocal.lookAt(player.getLocation(), target.getLocation()));
			LivingEntity en = target;
			if(target.getHealth() / target.getMaxHealth() < 0.5f) {
				for(int i=0; i < 30; i++) {
					delay(()->{
						ARSystem.spellCast(player, en, "c1049_s4");
					},10*i);
				}
			} else {
				for(int i=0; i < 8; i++) {
					delay(()->{
						ARSystem.spellCast(player, en, "c1049_s4");
					},3*i);
				}
			}
		} else {
			cooldown[4] = 0;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(target != null  && target.getLocation().distance(player.getLocation()) > 30) target = null;
		if(tk%20 == 0 && target != null) scoreBoardText.add("&c ["+Main.GetText("c49:sk3")+ "] : "+ target.getName() + " : " + AMath.round(target.getLocation().distance(player.getLocation()),2));
		
		return true;
	}
	
	int i =0;
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.damage(1 + target.getHealth()*0.1,player);
		}
		if(n.equals("2")) {
			target.damage(2 + target.getMaxHealth() * 0.05,player);
		}
		if(n.equals("3")) {
			target.damage(target.getHealth() * 0.05,player);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			target = (LivingEntity)e.getEntity();
			
			double damage =  5 + 15 * (target.getHealth() / target.getMaxHealth());
			if(damage > AMath.random(100)) {
				Holo.create(target.getLocation(), "§4x5", 40, new Vector(0, 0, 0));
				e.setDamage(e.getDamage() * 5);
			}
			
		} else {

		}
		return true;
	}
}
