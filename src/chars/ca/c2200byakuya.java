package chars.ca;

import java.util.List;

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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Nodie;
import buff.TimeStop;
import chars.c.c00main;
import chars.c.c31ichigo;
import event.Skill;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;

public class c2200byakuya extends c00main{
	int ticks = 0;
	boolean start = true;
	boolean sp = false;
	
	public c2200byakuya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1022;
		load();
		text();
		ARSystem.playSound(p, "c22select");
	}
	
	@Override
	public boolean skill1() {
		if(MSUtil.isbuff(player, "c1022_spe")) {
			ARSystem.playSound((Entity)player, "c22sp2");
			ARSystem.giveBuff(player, new TimeStop(player), 80);
			skill("c1022_s1sp");
			for(Entity e : ARSystem.PlayerBeamBox(player, 8, 6, box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				ARSystem.giveBuff(en, new TimeStop(en), 80);
				delay(()->{
					delay(()->{
						ARSystem.playSound(en, "c22sp3");
						for(int i = 0;i<20;i++) {
							delay(()->{
								ARSystem.spellCast(player, en, "c1022_remove");
								ARSystem.giveBuff(en, new TimeStop(en), 80);
							},i);
						}
						delay(()->{
							Skill.remove(en, player);
						},30);
					},40);
				},40);
			}
			
		} else {
			ARSystem.playSound((Entity)player, "c22s1");
			skill("c1022_s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(MSUtil.isbuff(player, "c1022_spe")) {
			MSUtil.buffoff(player, "c1022_spe");
			player.removePassenger(player.getPassenger());
		}
		skill("c1022_s2");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(sp && !MSUtil.isbuff(player, "c1022_spe")) {
			skill("c1022_spe");
		}
		if(start){
			start = false;
			for(int i=0;i<30;i++) {
				skill("c1022_p");
			}
		}
		if(tk%10 == 0 && !isps) {
			boolean sp = true;
			for(Player p : Rule.c.keySet()) {
				if(p.getHealth()/p.getMaxHealth() > 0.4) {
					sp = false;
				}
			}
			
			if(sp && skillCooldown(0)) {
				spskillon();
				spskillen();
				ARSystem.giveBuff(player, new TimeStop(player), 200);
				ARSystem.giveBuff(player, new Nodie(player), 200);
				ARSystem.playSoundAll("c22sp");
				skill("removemyall");
				skill("c1022_sp");
				delay(()->{
					ARSystem.playSoundAll("c1022sp");
					skill("c1022_sps");
					delay(()->{
						skill("c1022_spe");
						this.sp = true;
					},50);
				},120);
			}
			
		}
		return false;
	}
	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player, "c20select");
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) e.setDamage(30);
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*10);
		} else {

		}
		return true;
	}
}
