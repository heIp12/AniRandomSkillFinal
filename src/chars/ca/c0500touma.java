package chars.ca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.MSUtil;
import util.Pair;

public class c0500touma extends c00main{
	int damage = 0;
	List<Udamage> damages = new ArrayList<>();
	List<Udamage> removes = new ArrayList<>();
	int mult = 1;
	boolean d = false;
	
	public c0500touma(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1005;
		load();
		text();
		ARSystem.playSound(player, "c5select",1f);
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		List<Entity> entity = ARSystem.box(player, new Vector(10,10,10), box.TARGET);
		if(entity.size() > 0) {
			ARSystem.playSound((Entity)player, "c5a");
			delay(()->{
				ARSystem.playSound((Entity)player, "c5g");
			},20);
			for(Entity e : entity) {
				if(Rule.c.get(e) != null) {
					if(Rule.c.get(e).number > 1000) {
						spskillon();
						spskillen();
						ARSystem.playSound((Entity)player, "c5sp");
						ARSystem.giveBuff(player, new TimeStop(player), 140);
						ARSystem.giveBuff(((LivingEntity) e), new TimeStop((LivingEntity) e), 140);
						delay(()->{
							Rule.c.put((Player) e, GetChar.get((Player) e, plugin, "" + (Rule.c.get(e).number%1000)));
							for(int i=0;i<10;i++)Rule.c.get(e).cooldown[i] *=2.5;
						},140);
						return true;
					} else {
						delay(()->{
							for(int i =0; i<10; i++) {
									if(Rule.c.get(e).cooldown[i] > 0) {
										((LivingEntity)e).damage(3,player);
									}
									Rule.c.get(e).cooldown[i] = 10;
							}
						},20);
					}
				}
			}
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		mult*=2;
		ARSystem.playSound((Entity)player, "c5db");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.damage(damage,player);
			damages.clear();
		}
	}
	
	@Override
	public boolean tick() {
		if(tk%20==0) {
			damage = 0;
			for(Udamage dmg : damages) {
				damage += dmg.getDamage();
			}
			scoreBoardText.add("&c [Damage]&f : " + damage + " ["+damages.size()+"]");
			scoreBoardText.add("&c ["+Main.GetText("c1005:sk3")+"]&f : x" + mult);
		}
		
		for(Udamage damage : damages) {
			damage.run();
		}
		for(Udamage dmg : removes) {
			damages.remove(dmg);
		}
		removes.clear();
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

			
		} else {
			if(d) {
				d = false;
				e.setDamage(e.getDamage());
			} else {
				damages.add(new Udamage(e.getDamager(),e.getDamage()*mult));
				mult = 1;
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
	class Udamage{
		int tick = 200;
		private Entity target;
		private double damage;
		
		Udamage(Entity entity,double d){
			this.target = entity;
			this.damage = d;
		}
		void run() {
			tick--;
			if(tick == 0) {
				damage();
				removes.add(this);
			}
		}
		void damage() {
			d = true;
			player.setNoDamageTicks(0);
			player.damage(damage,target);
		}
		
		double getDamage() {
			return damage;
		}
	}
}
