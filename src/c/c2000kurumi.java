package c;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Stun;
import c2.c6800origami;
import c2.c68origami;
import event.Skill;
import types.box;
import types.modes;
import util.MSUtil;

public class c2000kurumi extends c00main{
	int ticks = 0;
	int count;
	
	public c2000kurumi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1020;
		load();
		text();
		ARSystem.playSound(p, "c20select");
	}
	
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
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
		ARSystem.heal(player, 8);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1);
			ARSystem.addBuff(target, new Stun(target), 4);
			ARSystem.potion(player, 1, 60, 2);
		}
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 3);
			if(ARSystem.gameMode == modes.LOBOTOMY) ARSystem.heal(player, e.getDamage()*0.05);
		} else {
			for(int i =0; i <cooldown.length;i++) {
				cooldown[i] -= 0.5;
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c6800origami || Rule.c.get(e) instanceof c68origami) {
					is = "origami";
					break;
				}

			}
		}
		
		if(is.equals("origami")) {
			ARSystem.playSound((Entity)player, "c20origami");
		} else {
			ARSystem.playSound((Entity)player, "c20db");
		}
		
		return true;
	}
}
