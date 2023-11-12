package chars.c;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.util.compat.EventUtil;

import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import manager.Bgm;
import types.box;
import util.AMath;
import util.Holo;
import util.MSUtil;
import util.Map;

public class c03remuru extends c00main{
	int ticks = 0;
	int s2 = 0;
	
	public c03remuru(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 3;
		Rule.buffmanager.selectBuffValue(player, "plushp",10);
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		Rule.buffmanager.selectBuffValue(player, "plushp",f);
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c3a");
		
		if(Rule.buffmanager.GetBuffValue(player, "plushp") > 100) {
			cooldown[1] *= 0.5;
		}
		if(Rule.buffmanager.GetBuffValue(player, "plushp")< 50) {
			skill("c"+number+"_s1_e1");
		} else if(Rule.buffmanager.GetBuffValue(player, "plushp") < 100) {
			skill("c"+number+"_s1_e2");
		} else if(Rule.buffmanager.GetBuffValue(player, "plushp") < 150) {
			skill("c"+number+"_s1_e3");
		} else if(Rule.buffmanager.GetBuffValue(player, "plushp") < 250) {
			skill("c"+number+"_s1_e4");
		} else {
			skill("c"+number+"_s1_e4");
			delay(new Runnable() { @Override public void run() {skill("c"+number+"_s1_e3");}}, 5);
			delay(new Runnable() { @Override public void run() {skill("c"+number+"_s1_e2");}}, 10);
		}
		
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c3d");
		s2 = 80;
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}

	
	public boolean skill0(EntityDamageByEntityEvent e) {
		if(skillCooldown(0)) {
			skill("c"+number+"_sps1");
			Rule.buffmanager.selectBuffValue(player, "plushp",0);
			Bgm.setBgm("c3");
			
			delay(new Runnable(){@Override public void run() {
				ARSystem.giveBuff(player, new TimeStop(player), 600);
				}}, 20);
			delay(()-> { ARSystem.playSoundAll("c3g2");}, 120);
			delay(()-> { 
				WinEvent event = new WinEvent(player);
				Bukkit.getPluginManager().callEvent(event);
				if(!event.isCancelled()) {
					skill("c"+number+"_sp_a");
					for(int i=0; i<60;i++) {
						Location l = Map.randomLoc();
						int j = 0;
						while(l.distance(player.getLocation()) > 60 + (j*0.001)) {
							j++;
							l = Map.randomLoc();
						}
						Location loc = l;
						delay(()->{
							ARSystem.spellLocCast(player, loc, "c3_spb");
						},i);
						
					}
					delay(()-> { 
						for(Entity en : ARSystem.box(player, new Vector(99,99,99),box.ALL)) {
							LivingEntity le = (LivingEntity)en;
							if(le instanceof Player) {
								((Player) le).setGameMode(GameMode.SPECTATOR);
							} else {
								Skill.quit(le);
							}
						}
					}, 40);
					delay(()-> { 
						Skill.win(player);
						tpsdelay(()->{
							ARSystem.playSoundAll("c3win");
						},40);
					}, 100);
				}
			}, 320);
			 
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		ticks++;
		if(s2>0)s2--;

		if(MSUtil.isbuff(player, "c3_s3") && player.isSneaking()) {
			player.setVelocity(new Vector(0,0.01,0));
		}
		if(MSUtil.isbuff(player, "c3_s3") && !player.isSneaking()) {
			player.setVelocity(player.getLocation().getDirection().multiply(0.5));
		}
		
		ticks%=20;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(target.getHealth() - 4  < 1) {
				boolean hs = false;
	
				if(target instanceof Player && ((Player)target).getGameMode() == GameMode.SPECTATOR) {
					hs = true;
				}
				if(Rule.buffmanager.OnBuffTime(target, "timestop")) {
					hs = true;
				}
				if(Rule.buffmanager.OnBuffTime(target, "nodamage")) {
					hs = true;
				}
				
	
				if(!hs) {
					player.sendTitle("","HP: "+Rule.buffmanager.GetBuffValue(player, "plushp")+"(+"+(target).getMaxHealth()/2.5+")",0,40,20);
					Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) (target.getMaxHealth()/2.5));
					Skill.remove(target, player);
					ARSystem.playSound((Entity)player, "c3s2");
				}
			} else {
				target.setNoDamageTicks(0);
				target.damage(4,player);
			}
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(Rule.buffmanager.GetBuffValue(player, "plushp") >= 500) {
			spskillon();
			spskillen();
			skill0(e);
		}
		
		if(isAttack) {
			if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() < 1) {

			}
		}
		if(!isAttack) {
			if(s2 > 0) {
				ARSystem.giveBuff(player, new Nodamage(player), 2);
				skill("c3_sound3");
				player.sendTitle("","HP: "+Rule.buffmanager.GetBuffValue(player, "plushp")+"(+"+AMath.round(e.getDamage()*0.7f,2)+")",0,30,10);
				Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) AMath.round(e.getDamage()*0.7f,2));
				if(e.getDamage() >= 100) {
					Rule.playerinfo.get(player).tropy(3,1);
				}
				player.setNoDamageTicks(20);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			} else if(Rule.buffmanager.OnBuffValue(player, "plushp")) {
				ARSystem.playSound((Entity)player, "c3s1");
			}
		}
		return true;
	}
}