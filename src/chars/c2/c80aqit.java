package chars.c2;

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
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
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
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c80aqit extends c00main{
	boolean ps = false;
	double damage = 0;
	
	int s1 = 0;
	int s1t = 0;
	int s2 = 0;
	int s3 = 0;
	int t = 0;
	double mydamage = 1.0;
	double myhp = hp;
	
	HashMap<LivingEntity,Integer> attack;
	
	public c80aqit(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 80;
		load();
		text();
		c = this;
		
	}

	@Override
	public boolean skill1() {
		s1++;
		s1t = 60;
		if(s1 < 3) {
			cooldown[1] = 0.1f;
		}
		skill("c80_s1");
		player.setVelocity(player.getLocation().getDirection().multiply(1));
		ARSystem.playSound((Entity)player, "c80s1"+s1);
		player.setNoDamageTicks(0);
		
		if(s1 == 3) {
			s1t = s1 = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		s2%=3;
		s2++;
		skill("c80_s2");
		ARSystem.playSound((Entity)player, "c80s2"+s2);
		player.setNoDamageTicks(0);
		return true;
	}
	
	@Override
	public boolean skill3() {
		s3%=2;
		s3++;
		skill("c80_s3");
		ARSystem.playSound((Entity)player, "c80s3"+s3);
		if(damage >= 10) {
			damage-=10;
			cooldown[3] = 0.3f;
		}
		player.setNoDamageTicks(0);
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c80_s4");
		ARSystem.playSound((Entity)player, "c80s4");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.addBuff(target, new Stun(target), 10);
			target.setNoDamageTicks(0);
			target.damage(4,player);
		}
		if(n.equals("2")) {
			ARSystem.addBuff(target, new Silence(target), 40);
			ARSystem.addBuff(target, new Noattack(target), 100);
			for(int i=1;i<5;i++) {
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(2,player);
				},i*10);
			}
		}
	}
	
	@Override
	public boolean tick() {
		if(myhp < player.getHealth()) myhp = player.getHealth();
		if(player.getHealth() > myhp) player.setHealth(myhp);
		
		if(t>10 && t%140 == 0 && mydamage >= 0.4) {
			mydamage= mydamage-0.1;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c80:ps")+ "] : "+ AMath.round(mydamage*100,0) +"%");
			scoreBoardText.add("&c ["+Main.GetText("c80:sk3")+ "] : "+ AMath.round(damage,1));
		}
		
		if(s1t >0) {
			s1t--;
			if(s1t == 0) {
				cooldown[1] = setcooldown[1];
				s1 = 0;
			}
		}
		t++;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(cooldown[2] > 0) {
				cooldown[2]-= 1;
			}
			e.setDamage(e.getDamage() * mydamage);
			damage+= e.getDamage();
		} else {
			if(mydamage<0.6 && player.getHealth() - e.getDamage() < 1 && ((LivingEntity)e.getDamager()).getHealth()/((LivingEntity)e.getDamager()).getMaxHealth() < 0.5) {
				if(skillCooldown(0)) {
					e.setDamage(0);
					e.setCancelled(true);
					spskillen();
					spskillon();
					Rule.playerinfo.get(player).tropy(80,1);
					ARSystem.playSound((Entity)player, "c80sp");
					LivingEntity target = (LivingEntity) e.getDamager();
					ARSystem.spellCast(player, target, "c80_sp");
					
					ARSystem.addBuff(player, new TimeStop(player), 120);
					ARSystem.addBuff(target, new TimeStop(target), 120);
					ARSystem.spellCast(player, target, "look1");
					ARSystem.spellCast(player, target, "look2");
					delay(()->{
						Skill.remove(target, player);
						ARSystem.spellCast(player, target, "c80_sp3");
						for(Entity en : ARSystem.box(player, new Vector(999,999,999),box.ALL)) {
							ARSystem.addBuff((LivingEntity) en, new TimeStop((LivingEntity) en), 120);
						}
						mydamage = 0.8f;
					},120);
				}
			}
		}
		return true;
	}
	
}
