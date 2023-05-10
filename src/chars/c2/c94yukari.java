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

public class c94yukari extends c00main{
	float p = 20;
	int s2_sound = 0;
	Location own_loc;
	Location tow_loc;
	
	boolean r = false;
	
	boolean s3 = false;
	
	int count = 0;
	
	@Override
	public void setStack(float f) {
		p = (int)f;
	}

	public c94yukari(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 94;
		load();
		text();
		c = this;
		if(ARSystem.isGameMode("lobotomy")) {
			setcooldown[0] *= 0.3;
		}
	}
	

	@Override
	public boolean skill1() {
		if(p > 20) {
			p-=20;
			if(own_loc != null) tow_loc = own_loc;
			own_loc = ULocal.offset(player.getLocation(),new Vector(4,0,0));
			if(player.isSneaking()) own_loc = player.getLocation();
			skill("c94_remove");
			ARSystem.spellLocCast(player, own_loc, "c94_yukari");
			if(tow_loc != null) ARSystem.spellLocCast(player, tow_loc, "c94_yukari");
			ARSystem.playSound((Entity)player, "c94s13");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(p > 30) {
			p-=30;
			ARSystem.playSound((Entity)player, "c94s2");
			for(int i=0; i< 5; i++) skill("c94_s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		s3 = !s3;
		if(s3) {
			for(Player p : Rule.c.keySet()) {
				player.hidePlayer(p);
				p.hidePlayer(player);
			}
			ARSystem.playSound((Entity)player, "c94s3");
		} else {
			for(Player p : Rule.c.keySet()) {
				player.showPlayer(p);
				p.showPlayer(player);
			}
			ARSystem.playSound((Entity)player, "c94s12");
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			for(int i=0;i<5;i++) {
				delay(()->{
					ARSystem.addBuff(target, new Stun(target), 1);
					target.setNoDamageTicks(0);
					target.damage(1,player);
				},i*1);
			}
		}
	}
	
	@Override
	public boolean skill4() {
		if(own_loc != null && tow_loc != null && p >= 100 && skillCooldown(0)) {
			p -= 100;
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c94sp");
			for(int i =0; i<10; i++) {
				delay(()->{
					ARSystem.spellLocCast(player, ULocal.lookAt(own_loc, tow_loc), "c94_sp");
					delay(()->{ARSystem.spellLocCast(player, tow_loc, "c94_sp2");},1);
				},i*4);
			}
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(p < 150 && tk%3 == 1 && !s3) p += AMath.round(1*(skillmult + sskillmult),2);
		if(s3 && tk%4 == 1) {
			p -=1;
			if(p <= 0) {
				s3 = false;
				for(Player p : Rule.c.keySet()) {
					player.showPlayer(p);
					p.showPlayer(player);
				}
				ARSystem.playSound((Entity)player, "c94s12");
			}
		}
		if(own_loc != null && tow_loc != null) {
			r = !r;
			if(r) {
				for(Entity e : own_loc.getWorld().getNearbyEntities(own_loc, 2, 2, 2)) {
					if(ARSystem.isTarget(e, player, box.MYALL)) {
						if(((e instanceof Player) && ((Player)e).isSneaking()) || (!(e instanceof Player)&& tk%20 < 10)) {
							if (e instanceof Player) ((Player) e).setSneaking(false);
							if(e == player) ARSystem.playSound((Entity)player, "c94s1");
							if(e != player) ((LivingEntity)e).damage(((LivingEntity)e).getMaxHealth()*0.05,e);
							e.teleport(tow_loc.clone().add(0,1,0));
							count++;
							if(count > 100) Rule.playerinfo.get(player).tropy(94,1);
						}
					}
				}
			} else {
				for(Entity e : tow_loc.getWorld().getNearbyEntities(tow_loc, 2, 2, 2)) {
					if(ARSystem.isTarget(e, player, box.MYALL)) {
						if(((e instanceof Player) && ((Player)e).isSneaking()) || (!(e instanceof Player)&& tk%20 >= 10)) {
							if (e instanceof Player) ((Player) e).setSneaking(false);
							if(e == player) ARSystem.playSound((Entity)player, "c94s1");
							if(e != player) ((LivingEntity)e).damage(((LivingEntity)e).getMaxHealth()*0.05,e);
							e.teleport(own_loc.clone().add(0,1,0));
							count++;
						}
					}
				}
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c94:ps")+"] : " + p);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*5);
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.2);
		}
		return true;
	}
}
