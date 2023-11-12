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
import buff.NoHeal;
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

public class c93bakugo extends c00main{
	float p = 0;
	int s2_sound = 0;
	Location frist_loc;
	int fly = 0;
	
	@Override
	public void setStack(float f) {
		p = f;
	}

	public c93bakugo(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 93;
		load();
		text();
		c = this;
		if(player != null) frist_loc = player.getLocation();
	}
	

	@Override
	public boolean skill1() {
		if(p > 10) {
			fly++;
			p-=10;
			ARSystem.playSound((Entity)player, "0explod", 1.4f,0.4f);
			player.setVelocity(player.getLocation().getDirection().multiply(0.8).setY(player.getLocation().getDirection().getY()*0.3));
			skill("c93_s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(p > 10) {
			p-=10;
			if(s2_sound <= 0) {
				s2_sound = 60;
				ARSystem.playSound((Entity)player, "c93s2");
			}
			player.setVelocity(player.getLocation().getDirection().multiply(-1.4).setY(-player.getLocation().getDirection().getY()*0.5));
			skill("c93_s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(fly >= 6 && p >= 50 && skillCooldown(0)) {
			p = 0;
			spskillen();
			spskillon();
			ARSystem.playSound((Entity)player,"c93sp");
			ARSystem.giveBuff(player, new Stun(player), 40);
			skill("c93_e");
			player.setFallDistance(0);
			delay(()->{
				skill("c93_move");
			},40);
			delay(()->{
				skill("c93_e2");
				for(Entity e : ARSystem.box(player, new Vector(8,12,8), box.TARGET)) {
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(25,player);
					delay(()->{
						if(e != null && !e.isDead()) {
							((LivingEntity)e).setVelocity(new Vector(0,20,0));
						}
					},2);
				}
				player.setFallDistance(0);
				ARSystem.giveBuff(player, new Silence(player), 0);
			},58);
		}
		else if(p > 20) {
			ARSystem.playSound((Entity)player, "c93s3");
			player.setVelocity(player.getLocation().getDirection().multiply(2.4));
			skill("c93_s3");
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new Stun(target), 10);
			target.setNoDamageTicks(0);
			target.damage(3,player);
		}

	}
	
	@Override
	public boolean tick() {
		if(frist_loc == null) frist_loc = player.getLocation();
		
		if(p < 100) p += frist_loc.distance(player.getLocation())*1;
		
		if(ARSystem.isGameMode("zombie")) p -= frist_loc.distance(player.getLocation())*0.6;
		p = (float) AMath.round(p, 2);
		frist_loc = player.getLocation();
		if(s2_sound > 0) s2_sound--;
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c93:ps")+"] : " + p);
			if(s_kill >= 3) {
				Rule.playerinfo.get(player).tropy(93,1);
			}
		}
		if(player.isOnGround()) {
			fly = 0;
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			for(int i=0;i<10;i++) cooldown[i] -= 0.1;
			
		} else {
			
		}
		return true;
	}
}
