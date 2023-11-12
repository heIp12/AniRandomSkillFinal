package chars.c3;

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
import buff.Sleep;
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

public class c105suya extends c00main{
	int time = 0;
	Location item;
	Location sleep;
	Location loc;
	int mp = 100;
	int tr = 0;
	
	int itemlv1 = 0;
	int itemlv2 = 0;
	
	public c105suya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 105;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		loc = player.getLocation();
		loc.setPitch(-75);
		player.teleport(loc);
		skill("c105_p2");
		Sleep sleep = new Sleep(player);
		sleep.isOne(false);
		ARSystem.giveBuff(player, sleep, 60 , 0.05);
		ARSystem.playSound((Entity)player, "c105p2");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(itemlv1 > 0 && mp > Math.max(16-itemlv1,4)) {
			mp -= Math.max(17-(itemlv1*2),5);
			player.setVelocity(player.getLocation().getDirection().multiply(0.5).setY(0));
			skill("c105_s2");
			ARSystem.playSound((Entity)player, "c105s2");
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
					LivingEntity en =((LivingEntity)e);
					en.setNoDamageTicks(0);
					en.damage(2 + (itemlv1),player);
				}
			},3);
			ARSystem.playSound((Entity)player, "c105s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(itemlv2 > 0 && mp > Math.max(22-itemlv2*2,10)) {
			mp -= Math.max(22-itemlv2*2,10);
			ARSystem.playSound((Entity)player, "minecraft:entity.cat.ambient");
			if(player.isSneaking()) {
				player.setVelocity(player.getLocation().getDirection().multiply(2));
			} else {
				ARSystem.potion(player, 1, 40, 4);
			}
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	public String getloc(Location loc) {
		Location local = ULocal.lookAt(player.getLocation(), loc);
		float yaw = local.getYaw() - player.getLocation().getYaw();
		if (yaw > 180) {
			yaw -= 360;
		} else if (yaw < -180) {
			yaw += 360;
		}
		String locstr = "";
		if(yaw > -22.5 && yaw < 22.5) {
			locstr = "↑";
		}
		else if(yaw >= 22.5 && yaw < 67.5) {
			locstr = "↗";
		}
		else if(yaw >= 67.5 && yaw < 112.5) {
			locstr = "→";
		}
		else if(yaw >= 112.5 && yaw > 157.5) {
			locstr = "↘";
		}
		else if(yaw <= -22.5 && yaw > -67.5) {
			locstr = "↖";
		}
		else if(yaw <= -67.5 && yaw > -112.5) {
			locstr = "←";
		}
		else if(yaw <= -112.5 && yaw > -157.5) {
			locstr = "↙";
		} else {
			locstr = "↓";
		}
		
		return locstr;
	}
	

	@Override
	public boolean tick() {
		Location local = player.getLocation().clone();
		local.setY(0);
		if(time%600 == 0) {
			item = Map.randomLoc();
			item.setY(0);
			int count = 0;
			while(item.distance(local) > 30 || item.distance(local) <  30 - (0.2*count)) {
				item = Map.randomLoc();
				item.setY(0);
				count++;
			}
		}
		if(time%1200 == 0) {
			sleep = Map.randomLoc();
			sleep.setY(0);
			item.setY(0);
			int count = 0;
			while(sleep.distance(local) < 100.0 - (count*0.2)) {
				sleep = Map.randomLoc();
				sleep.setY(0);
				count++;
			}
		}
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c105:s")+ "] : &f" + mp );
			if(sleep != null) scoreBoardText.add("&c ["+Main.GetText("c105:ps")+ "]  &f" + sleep.getX() + "," + + sleep.getZ() + "&e | "+ AMath.round(local.distance(sleep),2 )+"["+getloc(sleep)+"]");
			if(item != null) scoreBoardText.add("&c ["+Main.GetText("c105:p")+ "]  &f" + item.getX() + "," + + item.getZ()+ "&e | "+ AMath.round(local.distance(item),2)+"["+getloc(item)+"]");
			scoreBoardText.add("&c ["+Main.GetText("c105:sk2")+ "]  &f" + itemlv1);
			scoreBoardText.add("&c ["+Main.GetText("c105:sk3")+ "]  &f" + itemlv2);
		}
		if(sleep != null && local.distance(sleep) < 3) {
			time = -1;
			mp = (int)(100*(skillmult+sskillmult));
			loc = sleep;
			tr++;
			if(tr == 3)	Rule.playerinfo.get(player).tropy(105, 1);
			loc.setY(player.getLocation().getY());
			loc.setPitch(-75);
			loc.setYaw(player.getLocation().getYaw());
			player.teleport(loc);
			Sleep slp = new Sleep(player);
			slp.isOne(false);
			ARSystem.giveBuff(player, slp, 200 , 0.05);
			sleep = null;
			skill("c105_p1");
			ARSystem.playSound((Entity)player, "c105p2");
			cooldown[1] = 0;
			delay(()->{
				ARSystem.playSound(player, "c105p");
			},40);
		}
		
		if(item != null && local.distance(item) < 3) {
			item = null;
			if(AMath.random(10) <= 1 && skillCooldown(0)) {
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c105sp1");
				ARSystem.giveBuff(player, new TimeStop(player), 160);
				delay(()->{
					ARSystem.playSoundAll("c105sp2");
					for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
						ARSystem.giveBuff(((LivingEntity)e), new Sleep(((LivingEntity)e)), 400,5);
					}
					delay(()->{
						ARSystem.playSoundAll("c105sp3");
					},280);
				},160);
				time = -1;
			} else {
				time = -1;
				int rd = AMath.random(2);
				if(itemlv1 == 0) rd = 1;
				if(rd == 1) itemlv1++;
				if(rd == 2) {
					itemlv2++;
					player.setMaxHealth(player.getMaxHealth()+3);
					ARSystem.heal(player, 3);
				}
				player.sendTitle(Main.GetText("c105:sk"+(rd+1)), "");
				ARSystem.playSound((Entity)player, "c105get");
				delay(()->{
					ARSystem.playSound(player, "c105p");
				},60);
				
			}
		}
		
		if(Rule.buffmanager.GetBuffTime(player, "sleep") > 0) {
			mp += (int)(skillmult+sskillmult);
			if(mp > (int)(100*(skillmult+sskillmult))) mp = (int)(100*(skillmult+sskillmult));
			ARSystem.heal(player, 0.5 + (itemlv2*0.1));
			if(loc != null) player.teleport(loc);
		}
		time++;
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			
		}
		return true;
	}
	
}
