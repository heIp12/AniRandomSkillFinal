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
import buff.Cindaella;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c60gil extends c00main{
	float sword = 0;
	Location gob;
	boolean sk2 = false;
	Entity target = null;
	int count = 0;
	int sp = 0;
	
	public c60gil(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 60;
		load();
		text();
		c = this;
		sword = 50;
	}
	@Override
	public void setStack(float f) {
		sword = (int) f;
	}

	@Override
	public boolean skill1() {
		skill("c60_s1");
		ARSystem.playSound((Entity)player, "c60s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(sk2) {
			sk2 = false;
		} else {
			gob = player.getLocation();
			ARSystem.playSound((Entity)player, "c60s2");
			sk2 = true;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c60_s3");
		ARSystem.playSound((Entity)player, "c60s3");
		return true;
	}
	
	
	@Override
	public void PlayerSpCast(Player p) {
		if(Rule.c.get(p) != null && !(Rule.c.get(p) instanceof c60gil) && ARSystem.AniRandomSkill == null || (ARSystem.AniRandomSkill.getTime() >= 1)) {
			count++;
			if(ARSystem.AniRandomSkill.startplayer.size() > 5 && AMath.random(10) <= 3) {
				count++;
			}
			if(ARSystem.AniRandomSkill.startplayer.size() > 10 && AMath.random(10) <= 3) {
				count++;
			}
			if(count >= ARSystem.AniRandomSkill.startplayer.size()/2&& skillCooldown(0)) {
				spskillon();
				spskillen();
				
				skill("c60_sp0");
				ARSystem.playSoundAll("c60sp0");
				ARSystem.giveBuff(player, new Stun(player), 320);
				ARSystem.giveBuff(player, new Silence(player), 320);
				delay(()->{
					ARSystem.playSoundAll("c60sp1");
					
				},100);
				delay(()->{
					ARSystem.playSoundAll("c60sp2");
				},120);
				delay(()->{
					sp = 200;
				},140);
			} else {
				if(Rule.c.get(p).getCode() == 30 && ARSystem.isTarget(p, player,box.TARGET)) {
					target = p;
					if(player.getLocation().distance(p.getLocation()) < 20) {
						ARSystem.giveBuff(player, new TimeStop(player), 540);
						player.teleport(ULocal.lookAt(player.getLocation(), p.getLocation()));
						p.teleport(ULocal.lookAt(p.getLocation(), player.getLocation()));
						delay(()->{
							ARSystem.playSoundAll("c60c1");
							delay(()->{p.performCommand("c c30_s3");},10);
							
							for(int i=0;i<60;i++) {
								delay(()->{
									Location loc = player.getLocation();
									loc = ULocal.offset(loc, new Vector(-2,1+AMath.random(100)*0.05,4-AMath.random(160)*0.05));
									loc = ULocal.lookAt(loc, p.getLocation());
									ARSystem.spellLocCast(player, loc, "c60_p2_"+AMath.random(9));
									ARSystem.spellLocCast(player, loc, "c60_p1"+AMath.random(3));
								},i);
							}
						},240);
					}
				}
			}
		}
	}
	@Override
	public boolean tick() {
		if(sp > 0) {
			sp--;
			for(Entity e : ARSystem.box(player, new Vector(1000,1000,1000), box.TARGET)){
				((LivingEntity)e).setNoDamageTicks(0);
				((LivingEntity)e).damage(1, player);
			}
		}
		if(tk%20 == 0 && ARSystem.AniRandomSkill != null) {
			scoreBoardText.add("&c ["+Main.GetText("c60:sk0")+ "] : "+ count +" / " + ARSystem.AniRandomSkill.startplayer.size()/2);
		}
		if(tk%10 == 0) {
			if(sword < 200) {
				sword+= 1*(skillmult + sskillmult);
				if(ARSystem.isGameMode("lobotomy")) sword += 2;
			} else {
				Rule.playerinfo.get(player).tropy(60,1);
			}
			scoreBoardText.add("&c ["+Main.GetText("c60:ps")+ "] : "+ sword);
		}
		if(sk2 && sword > 0 && tk%2 == 0) {
			sword--;
			Location l = ULocal.offset(gob.clone(), new Vector(-2,1+AMath.random(100)*0.05,4-AMath.random(160)*0.05));
			ARSystem.spellLocCast(player, l, "c60_p2_"+AMath.random(9));
			ARSystem.spellLocCast(player, l, "c60_p1"+AMath.random(3));
			if(sword <= 0) {
				sk2 = false;
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player && Rule.c.get(p) != null && Rule.c.get(p).number == 30) {
			ARSystem.playSoundAll("c60c2");
		}
		if(p == player && Rule.c.get(e) != null && Rule.c.get(e).number == 30) {
			ARSystem.playSoundAll("c60c3");
		}
	}
	

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
			if(AMath.random(10) <= 3 && sp <= 0) {
				Location loc = player.getLocation();
				loc = ULocal.offset(loc, new Vector(-2,1+AMath.random(100)*0.05,4-AMath.random(160)*0.05));
				loc = ULocal.lookAt(loc, e.getEntity().getLocation());
				ARSystem.spellLocCast(player, loc, "c60_p2_"+AMath.random(9));
				ARSystem.spellLocCast(player, loc, "c60_p1"+AMath.random(3));
			}
		} else {
			if(target != null && AMath.random(10) <= 9) {
				e.setDamage(0);
				if(AMath.random(10) <= 5) {
					Location loc = player.getLocation();
					loc = ULocal.offset(loc, new Vector(-2,1+AMath.random(100)*0.05,4-AMath.random(160)*0.05));
					loc = ULocal.lookAt(loc, target.getLocation());
					ARSystem.spellLocCast(player, loc, "c60_p2_"+AMath.random(7));
					ARSystem.spellLocCast(player, loc, "c60_p1"+AMath.random(3));
				}
			}
		}
		return true;
	}
}
