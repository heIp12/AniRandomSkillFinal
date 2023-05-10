package chars.ca;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Noattack;
import buff.Nodamage;
import buff.Stun;
import chars.c.c00main;
import event.Skill;
import util.Holo;
import types.MapType;
import util.AMath;
import util.Map;

public class c0400sans extends c00main{
	private int skillcount = 0;
	int count = 0;
	int sp = 0;
	int hit = 0;
	
	@Override
	public void setStack(float f) {
		hit = (int)f;
	}
	
	public c0400sans(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1004;
		load();
		text();
		ARSystem.playSound(player, "c4select",0.8f);
		
	}
	
	@Override
	public boolean skill1() {
		if(!isps) {
			ARSystem.playSound((Entity)player, "c4a", 0.5f);
			skill("c"+number+"_s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(!isps) {
			ARSystem.playSound((Entity)player, "c4a", 0.5f);
			skill("c"+number+"_s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(!isps) {
			ARSystem.playSound((Entity)player, "c4a3", 1.6f);
			ARSystem.giveBuff(player, new Stun(player), 40);
			skill("c"+number+"_s3");
		}
		return true;
	}
	
	@Override
	public boolean skill9() {
		ARSystem.playSound((Entity)player, "c4db", 0.9f - (0.1f*AMath.random(4)));
		return true;
	}
	
	public void sp(){
		spskillon();
		spskillen();
		ARSystem.playSoundAll("c4select",0.8f);
		Map.mapType = MapType.NORMAL;
		player.setMaxHealth(100);
		player.setHealth(100);
		Map.getMapinfo(1006);
		for(Player p : Bukkit.getOnlinePlayers()) {
			Map.playeTp(p);
			ARSystem.giveBuff(p, new Noattack(p), 100);
			ARSystem.giveBuff(p, new Nodamage(p), 100);
		}
		delay(()->{
			skill("c1004_sp");
			sp = 600;
		},60);
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1") && target.getVelocity().length() > 0) {
			for(int i=0;i<10;i++) {
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(1,player);
				},i);
			}
		}
		if(n.equals("2") && target.getVelocity().length() <= 0) {
			for(int i=0;i<10;i++) {
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(1,player);
				},i);
			}
		}
	}
	
	@Override
	public boolean tick() {
		if(AMath.random(10000-(hit*10)) <= 1 && !isps) {
			sp();
		}
		if(sp > 0) { 
			sp--;
			scoreBoardText.add("&c ["+Main.GetText("c1004:sk0")+ "] : "+ AMath.round(sp*0.05,0));
			if(sp <= 0) {
				Skill.death(player, player);
			}
		}
		return true;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			hit++;
			int i = 51;
			if(ARSystem.AniRandomSkill != null) i = ARSystem.AniRandomSkill.getTime();
			
			if((140 - i/2) > AMath.random(100) || isps) {
				player.setNoDamageTicks(5);
				ARSystem.playSound((Entity)player, "c4db", 0.8f);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(e.getCause() != DamageCause.ENTITY_ATTACK) {
			hit++;
			int i = 30;
			if(ARSystem.AniRandomSkill != null) i = ARSystem.AniRandomSkill.getTime();
			
			if(140 - i/2 > AMath.random(100)) {
				player.setNoDamageTicks(5);
				ARSystem.playSound((Entity)player, "c4db", 0.8f);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
