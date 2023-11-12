package chars.c;

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

import com.mojang.authlib.yggdrasil.response.AuthenticationResponse;
import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.Sleep;
import buff.Stun;
import event.Skill;
import manager.AdvManager;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c41zanitu extends c00main{
	Location loc = null;
	LivingEntity target;
	
	int spcount = 1;
	int ptick = 0;
	int delay = 20;
	
	public c41zanitu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 41;
		load();
		text();

		if(p != player) loc = player.getLocation();
	}
	
	void sp() {
		Rule.buffmanager.selectBuffTime(player, "sleep", 0);
		ptick = 0;
		LivingEntity target = this.target;
		ARSystem.playSoundAll("c41s");
		ARSystem.potion(player, 14, 60, 60);
		ARSystem.giveBuff(target, new Stun(target), 60);
		ARSystem.giveBuff(target, new Silence(target), 60);
		skill("c41_s1_am");
		List<LivingEntity> targets = new ArrayList<LivingEntity>();
		
		delay(()->{
			target.teleport(player.getLocation().add(0,2,0));
			ARSystem.playSoundAll("c41sp");
			
			for(int k=0;k<5;k++) {
				target.teleport(target.getLocation().add(0,2,0));
				Location loc = ULocal.lookAt(player.getLocation(),target.getLocation());
				loc.setPitch(-10);
				loc.setYaw(loc.getYaw()+2);
				
				for(int i =0; i< 20; i++) {
					for(Entity e : ARSystem.locEntity(loc, new Vector(3,3,3), player)) {
						if(!targets.contains(e)) targets.add((LivingEntity)e);
					}
					Location l = loc.clone();
					delay(()->{
						ARSystem.spellLocCast(player, l, "c41_s1_e");
					},6+(i/10)+(k*2));
					loc.add(loc.getDirection());
				}
				player.teleport(loc);
				ARSystem.playSound((Entity)player, "c41a");
				delay(()->{ARSystem.spellLocCast(player, loc, "c41_s1_e2");},6+(k*2));
			}
			{
				target.teleport(target.getLocation().add(0,2,0));
				Location loc = ULocal.lookAt(player.getLocation(),target.getLocation());
				loc.setPitch(-10);
				
				for(int i =0; i< 10; i++) {
					for(Entity e : ARSystem.locEntity(loc, new Vector(3,3,3), player)) {
						if(!targets.contains(e)) targets.add((LivingEntity)e);
					}
					Location l = loc.clone();
					delay(()->{
						ARSystem.spellLocCast(player, l, "c41_s1_e");
					},16+(i/10));
					loc.add(loc.getDirection());
				}
				player.teleport(loc);
				ARSystem.playSound((Entity)player, "c41a");
				delay(()->{ARSystem.spellLocCast(player, loc, "c41_s1_e2");},16);
				target.teleport(loc);
			}
			{
				Location loc = player.getLocation();
				loc.setPitch(-90);
				
				for(int i =0; i< 10; i++) {
					for(Entity e : ARSystem.locEntity(loc, new Vector(3,3,3), player)) {
						if(!targets.contains(e)) targets.add((LivingEntity)e);
					}
					Location l = loc.clone();
					delay(()->{
						ARSystem.spellLocCast(player, l, "c41_s1_e");
						ARSystem.spellLocCast(player, loc, "c41_s1_e2");
					},20);
					loc.add(loc.getDirection());
				}
				
				player.teleport(loc);
				skill("c41_s1_am2");
				ARSystem.playSound((Entity)player, "c41a");
			}
			ARSystem.giveBuff(player, new Stun(player), 20);
			ARSystem.giveBuff(target, new Stun(target), 50);
			ARSystem.giveBuff(target, new Silence(target), 50);
			delay(()->{
				for(LivingEntity e : targets) {
					ARSystem.playSound(e, "0katana6");
					e.setNoDamageTicks(0);
					e.damage(15*6,player);
					ARSystem.spellCast(player,e, "bload");
				}
			},50);
		},20);
	}
	

	@Override
	public boolean skill1() {
		if(Rule.buffmanager.GetBuffTime(player, "sleep") > 0 && target != null) {
			if(BlockUtil.isAirbone(target.getLocation(), 1)) {
				if(AMath.random(10) <= spcount && skillCooldown(0)) {
					spskillon();
					spskillen();
					spcount = 1;
					sp();
					return true;
				} else {
					spcount++;
				}
			}
			Rule.buffmanager.selectBuffTime(player, "sleep", 0);
			ptick = 0;
			ARSystem.giveBuff(target, new Stun(target), 20);
			ARSystem.giveBuff(player, new Stun(player), 14);
			Location loc = ULocal.lookAt(player.getLocation(),target.getLocation());
			List<LivingEntity> targets = new ArrayList<LivingEntity>();
			
			for(int i =0; i< 20; i++) {
				for(Entity e : ARSystem.locEntity(loc, new Vector(3,3,3), player)) {
					if(!targets.contains(e)) targets.add((LivingEntity)e);
				}
				Location l = loc.clone();
				delay(()->{
					ARSystem.spellLocCast(player, l, "c41_s1_e");
				},6+(i/4));
				loc.add(loc.getDirection());
			}
			player.teleport(loc);
			ARSystem.playSound((Entity)player, "c41s1");
			ARSystem.playSound((Entity)player, "c41a");
			ARSystem.playSound((Entity)player, "minecraft:entity.lightning.thunder",1.6f,2f);
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			ARSystem.potion(player, 14, 20, 1);
			skill("c41_s1_am");
			
			delay(()->{ARSystem.playSound((Entity)player, "0katana7");},10);
			delay(()->{
				int i = 0;
				for(LivingEntity e : targets) {
					i++;
					delay(()->{
						ARSystem.playSound(e, "0katana6");
						e.setNoDamageTicks(0);
						e.damage(15,player);
						ARSystem.spellCast(player,e, "bload");
					},2*i);
				}
			},20);
			target = null;
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c41s2");
		ARSystem.giveBuff(player, new Sleep(player), 20, 1);
		return true;
	}
	
	@Override
	public boolean tick() {
		if(loc == null) loc = player.getLocation();
		if(player.isSneaking() && loc.distance(player.getLocation()) <= 0.1 && cooldown[1] <= 0) {
			ptick++;
			if(ptick > 10 && Rule.buffmanager.GetBuffTime(player, "sleep") < 3) {
				ARSystem.giveBuff(player, new Sleep(player), 10);
			}
		} else {
			ptick = 0;
		}
		
		if(Rule.buffmanager.GetBuffTime(player, "sleep") > 0) {
			((Sleep)Rule.buffmanager.selectBuff(player, "sleep")).isSilence(false);
			cooldown[1] = 0;
			Entity e = ARSystem.boxSOne(player, new Vector(12,6,12), box.TARGET);
			if(e != null) {
				target = (LivingEntity)e;
				ARSystem.potion(target, 24, 4, 4);
			} else {
				target = null;
			}
		}
		
		if(tk%20==0) {
			if(target != null) scoreBoardText.add("&c ["+Main.GetText("c41:ps")+ "] &a" + target.getName());
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c41:sk0")+ "] &f" + (spcount*10) +"%");
		}
		loc = player.getLocation();
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
