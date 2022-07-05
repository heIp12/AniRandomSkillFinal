package c;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Barrier;
import buff.Buff;
import buff.Nodamage;
import buff.Reflect;
import buff.Silence;
import buff.Stun;
import manager.Holo;
import types.BuffType;
import types.modes;
import util.AMath;
import util.MSUtil;
import util.Text;

public class c25Accelerator extends c00main{
	int cc = 0;
	int count;
	
	public c25Accelerator(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 25;
		load();
		text();

		((Barrier)Rule.buffmanager.selectBuff(player, "barrier")).SetEffect("c25_p");
		Rule.buffmanager.selectBuffValue(player, "barrier",50);
	}
	
	@Override
	public boolean skill1() {
		ARSystem.giveBuff(player, new Stun(player), 20);
		if(isps) {
			skill("c"+number+"_s01");
			for(int i = AMath.random(9)-1; i< 18; i++) {
				delay(new Runnable() {
					@Override
					public void run() {
						skill("c"+number+"_s01_i");
						skill("c"+number+"_s01_ii");
					}
				},i);
				count++;
				if(count >= 100) {
					Rule.playerinfo.get(player).tropy(25,1);
				}
			}
		} else {
			skill("c"+number+"_s1");
			for(int i = AMath.random(9)-1; i< 12; i++) {
				delay(new Runnable() {
					@Override
					public void run() {
						skill("c"+number+"_s1_i");
					}
				},i);
			}
		}
		return true;
	}
	
		
	
	@Override
	public boolean skill2() {
		Reflect rep = new Reflect(player);
		rep.SetEffect("c25_s2p");
		rep.setValue(1);
		rep.SetNoDamage(true);
		rep.SetTargetEffect("c24_s2_e");
		ARSystem.giveBuff(player, rep, 100);
		ARSystem.playSound((Entity)player, "c25s2");
		return true;
	}

	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		if(ARSystem.gameMode == modes.ZOMBIE) {
			cooldown[3]+= 0.2;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(isps && tk%2==0) {
			skill("c25_ps");
		}
		if(tk%20 == 0) {
			if(!spben) {
				boolean iscc = false;
				int tcc = 0;
				if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
					for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
						if(buff.getTime() > 0) {
							iscc = true;
							if(buff.getTime() > tcc) tcc = buff.getTime() ;
						}
					}
				}
				if(Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
					for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
						if(buff.getTime() > 0) {
							iscc = true;
							if(buff.getTime() > tcc) tcc = buff.getTime() ;
						}
					}
				}
				
				if(iscc) {
					cc++;
					if(!isps && cc+(tcc/20) > 19) {
						spskillon();
						spskillen();
						ARSystem.playSound((Entity)player, "c25sp");
						if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
							for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
								buff.setTime(0);
							}
						}
						if(Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
							for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
								buff.setTime(0);
							}
						}
					}
				} else {
					cc = 0;
				}
			}
			if(psopen) {
				scoreBoardText.add("&c ["+Main.GetText("c25:sk0")+ "]&f : "+ cc + " / 20");
			}
		}
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
		} else {
			if(e.getDamager() instanceof Player) {
				if(Rule.c.get(e.getDamager()) != null) {
					if(Rule.c.get(e.getDamager()).number == 5) {
						skill("c5_p4");
						skill("c25_p");
						return true;
					}
				}
			}
		}
		return true;
	}
}
