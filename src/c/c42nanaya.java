package c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import event.Skill;
import manager.AdvManager;
import types.box;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c42nanaya extends c00main{
	int count = 5;
	int tick = 0;
	int ty = 0;
	
	float co[] = new float[4];
	
	HashMap<Entity,Integer> stack = new HashMap<Entity,Integer>();
	
	int c = 0;
	public c42nanaya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 42;
		load();
		text();
		for(int i = 0; i<4;i++) co[i] = setcooldown[i];
		
	}
	

	@Override
	public boolean skill1() {
		skill("c42_s1");
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c42_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.giveBuff(player, new Stun(player), 16);
		ARSystem.giveBuff(player, new Silence(player), 16);
		
		skill("c42_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.giveBuff(player, new Silence(player), 60);
		skill("c42_s4");
		return false;
	}
	
	@Override
	public boolean skill5() {
		skill("c42_s5");
		int stacks = 0;
		for(Entity p : stack.keySet()) {
			if(stack.get(p) > 0) {
				stacks += stack.get(p);
				ARSystem.spellCast(player, p, "c42_s5");
			}
		}
		stack.clear();
		tick = stacks*10;
		
		return false;
	}

	@Override
	public boolean tick() {
		if(tick > 0) tick--;
		if(tick > 0 && tk%4 == 0) {
			for(Entity e : ARSystem.box(player, new Vector(10,4,10),box.TARGET)) {
				LivingEntity entity = (LivingEntity)e;
				if(entity.getHealth() < 14 && !(entity instanceof Player && ((Player)entity).getGameMode() == GameMode.SPECTATOR)) {
					ARSystem.spellCast(player, entity, "c42_p");
				}
			}
			
		}
		
		if(tk%20==0) {
			if(tick > 0) {
				ARSystem.heal(player, 1);
				scoreBoardText.add("&c ["+Main.GetText("c42:sk5")+ "] : "+ tick*0.05);
			}
			
			for(Entity p : stack.keySet()) {
				if(stack.get(p) > 0) {
					scoreBoardText.add("&c ["+Main.GetText("c42:t1")+ "] : "+p.getName() +":"+ stack.get(p));
				}
			}
		}
		return true;
	}

	public void stacks(Entity e,int num) {
		if(tick > 0) num *= 2;
		
		if(stack.get(e) != null) {
			stack.put(e, num + stack.get(e));
		} else {
			stack.put(e, num);
		}
	}

	@Override
	public void TargetSpell(SpellTargetEvent e, boolean mycaster) {
		if(mycaster && !(e.getTarget() instanceof Player && ((Player)e.getTarget()).getGameMode() == GameMode.SPECTATOR )) {
			if(e.getSpell().getName().equals("c42_s1_a")) {
				stacks(e.getTarget(),1);
				if(tick > 0 && e.getTarget().getHealth() <= 14) {
					if(skillCooldown(0)) {
						ty++;
						if(ty >= 2) {
							Rule.playerinfo.get(player).tropy(42,1);
						}
						spskillon();
						spskillen();
						ARSystem.giveBuff(player, new Silence(player), 60);
						ARSystem.giveBuff(player, new Stun(player), 60);
						ARSystem.giveBuff(player, new Nodamage(player), 60);
						ARSystem.spellCast(player, e.getTarget(), "c42_sp");
					}
				}
			}
			if(e.getSpell().getName().equals("c42_s2_a")) {
				stacks(e.getTarget(),2);
			}
			if(e.getSpell().getName().equals("c42_s3_a")) {
				if(stack.get(e.getTarget()) != null && stack.get(e.getTarget()) > 0) {
					stack.put(e.getTarget(), stack.get(e.getTarget())-3);
					if(tick > 0) stack.put(e.getTarget(), stack.get(e.getTarget())-2);
					e.getTarget().setNoDamageTicks(0);
					e.getTarget().damage(3,player);
				}
			}
			if(e.getSpell().getName().equals("c42_s4_a")) {
				stacks(e.getTarget(),3);
			}
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
			if(!e.getEntity().isDead() && !(e.getEntity() instanceof Player && ((Player)e.getEntity()).getGameMode() != GameMode.SPECTATOR)) {
				stacks(e.getEntity(),1);
				if(stack.get(e.getEntity()) > 0) {
					e.setDamage(e.getDamage() + e.getDamage() * stack.get(e.getEntity())*0.05);
				}
			}
		} else {

		}
		return true;
	}
}
