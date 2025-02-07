package chars.ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
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
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.MapVoid;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c8500maka extends c00main{
	List<LivingEntity> entitys = new ArrayList<>();
	List<LivingEntity> entitys2 = new ArrayList<>();
	HashMap<LivingEntity,MakaTargets> ps = new HashMap<>();
	
	
	int sp = 0;
	int s3 = 0;
	
	@Override
	public void setStack(float f) {
		super.setStack(f);
		sp = (int)f;
		if(sp >= 2 && !isps) {
			spskillon();
			spskillen();
		}
	}
	
	public c8500maka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1085;
		load();
		text();
		c = this;
		ARSystem.playSound(player, "c85db");
		
	}

	@Override
	public boolean skill1() {
		entitys.clear();
		skill("c1085_s1");
		ARSystem.playSound((Entity)player, "0katana4");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c1085_s2");
		ARSystem.giveBuff(player, new Silence(player), 20);
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.playSound((Entity)player, "c85s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c1085_s3");
		entitys2.clear();
		ARSystem.giveBuff(player, new Silence(player), 30);
		ARSystem.giveBuff(player, new Stun(player), 30);
		s3 = 100;
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.giveBuff(player, new Rampage(player), 160);
		ARSystem.giveBuff(player, new Panic(player), 160);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(!entitys.contains(target)) {
				entitys.add(target);
				ARSystem.playSound(target,"0slash",2);
				attack(target,4);
			}
		}
		if(n.equals("2")) {
			if(Rule.buffmanager.getBuffs(target) != null && Rule.buffmanager.getBuffs(target).getBuff() != null) {
				for(Buff buff : Rule.buffmanager.getBuffs(target).getBuff()) {
					buff.stop();
				}
			}
			ARSystem.giveBuff(target, new NoHeal(target), 400);
			ARSystem.playSound(target,"0slash3");
			ARSystem.spellCast(player, target, "bload");
			for(int i = 0; i < 10; i++) {
				delay(()->{
					attack(target,1);
				},1*i);
			}
		}
		if(n.equals("3")) {
			if(!entitys2.contains(target)) {
				entitys2.add(target);
				ARSystem.spellLocCast(player, target.getLocation(), "c1085_s3e");
				ARSystem.playSound((Entity)target,"0select",2);
				player.sendTitle(""+entitys2.size(), target.getName(),5,10,5);
			}
		}
		if(n.equals("4")) {
			attack(target,1);
		}
	}
	void attack(LivingEntity e, float damage) {
		e.setNoDamageTicks(0);
		e.damage(damage,player);
		if(!ps.containsKey(e)) {
			ps.put(e, new MakaTargets());
		}
		ps.get(e).time = 300;
		ps.get(e).damage += 0.008;
	}
	
	@Override
	public void kill(LivingEntity death, LivingEntity killer) {
		if(ps.containsKey(death)) {
			ps.remove(death);
		}
	}
	
	@Override
	public boolean tick() {
		if(Rule.buffmanager.isBuff(player, "rampage") && tk%5 == 0 && AMath.random(5) == 2) {
			Rule.buffmanager.selectBuffValue(player, "rampage", 2.5f);
			Entity target = ARSystem.boxSOne(player, new Vector(50,50,50), box.TARGET);
			if(target != null) {
				if(cooldown[1] <= 0 && AMath.random(10) <= 5) {
					cooldown[1] = setcooldown[1];
					skill1();
				} else if(AMath.random(10) <= 3 && target.getLocation().distance(player.getLocation()) <= 10){
					player.teleport(target);
					ARSystem.playSound((Entity)player, "0miss", 2f);
				} else if(AMath.random(10) <= 4) {
					skill("c1085_p3");
				} else if(AMath.random(10) <= 4) {
					skill("c1085_p");
				} 
			}
		}
		if(s3 > 0) {
			s3--;
			ARSystem.giveBuff(player, new Silence(player), 4);
			ARSystem.giveBuff(player, new Stun(player), 4);
			if(player.isSneaking()) s3 = 0;
			
			if(s3 == 0) {
				skill("c1085_s3c");
				
				int i = 0;
				for(LivingEntity e : entitys2) {
					int j = i;
					delay(()->{
						ARSystem.giveBuff(player, new Silence(player), 15);
						ARSystem.giveBuff(player, new Stun(player), 15);
						ARSystem.giveBuff(player, new Nodamage(player), 15);
						ARSystem.spellCast(player,e, "c1085_s3m");
						delay(()->{
							ARSystem.spellCast(player,e, "c1085_s3l");
							attack(e,8+(j*2));
							ARSystem.playSound((LivingEntity)e, "0katana3");
							ARSystem.giveBuff((LivingEntity)e, new Panic((LivingEntity)e), 60);
						},9);
					},i*10);
					i++;
				}
			}
		}
		if(tk%20 == 0 && !isps) {
			scoreBoardText.add("&c ["+Main.GetText("c1085:sk0")+"] : " +sp +"/ 3");
		}
		if(isps) {
			if(sp > 0) {
				sp--;
			}
			if(sp <= 0) {
				sp = 20;
				skill("c1085_sp");
			}
		}
		
		List<LivingEntity> removes = new ArrayList<>();
		for(LivingEntity en : ps.keySet()) {
			if(ps.get(en).time <= 0) {
				removes.add(en);
			} else {
				ps.get(en).time--;
			}
			if(ps.get(en).time%60 == 0) {
				en.setNoDamageTicks(0);
				ARSystem.spellLocCast(player, en.getLocation(), "c1085_p2");
				en.damage(ps.get(en).damage*en.getMaxHealth(),player);
			}
		}
		for(LivingEntity en : removes) ps.remove(en);
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == p) {
			if(sp >= 2 && !isps) {
				spskillon();
				spskillen();
			}
		}
	}
	
	@Override
	public void LocmakerSkill(Location loc, String name) {
		if(name.equals("maka")) {
			for(LivingEntity e : entitys) {
				ARSystem.giveBuff(e, new MapVoid(e), 2);
				e.teleport(loc);
			}
		}
		super.LocmakerSkill(loc, name);
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.buffmanager.isBuff(player, "rampage")) {
				ARSystem.heal(player, e.getDamage()*0.9f);	
			} else {
				ARSystem.heal(player, e.getDamage()*0.3f);	
			}
			if(isps) {
				ARSystem.heal(player, 1);
			}
		} else {
			if(Rule.buffmanager.isBuff(player, "rampage")) {
				e.setDamage(e.getDamage()*0.5f);
			}
		}
		return true;
	}
	
	public class MakaTargets{
		public float damage = 0;
		public int time = 0;
	}
}
