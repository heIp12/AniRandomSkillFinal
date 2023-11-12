package chars.c;

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
import buff.TimeStop;
import chars.c2.c62shinon;
import chars.c2.c63micoto;
import event.Skill;
import manager.AdvManager;
import types.BuffType;
import types.box;
import util.AMath;
import util.Holo;
import util.MSUtil;
import util.Text;
import util.ULocal;

public class c25Accelerator extends c00main{
	int cc = 0;
	int count;
	int fly = 400;
	boolean white = false;
	
	boolean touma = false;
	
	public c25Accelerator(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 25;
		load();
		text();

		if(p != null) {
			((Barrier)Rule.buffmanager.selectBuff(player, "barrier")).SetEffect("c25_p");
			Rule.buffmanager.selectBuffValue(player, "barrier",50);
		}
	}
	
	@Override
	public boolean skill1() {
		ARSystem.giveBuff(player, new Stun(player), 20);
		if(white) {
			delay(()->{
				skill("c"+number+"_sps1");
			},10);
			ARSystem.playSound((Entity)player, "c1025s1");
		}
		else if(isps) {
			skill("c"+number+"_s01");
			for(int i = AMath.random(9)-1; i< 18; i++) {
				delay(new Runnable() {
					@Override
					public void run() {
						skill("c"+number+"_s01_i");
						skill("c"+number+"_s01_ii");
					}
				},i);
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
				count++;
				if(count >= 100) {
					Rule.playerinfo.get(player).tropy(25,1);
				}
			}
		}
		return true;
	}
	
		
	
	@Override
	public boolean skill2() {
		if(white) {
			Reflect rep = new Reflect(player);
			rep.SetEffect("c25_s2p");
			rep.setValue(3);
			rep.SetNoDamage(true);
			rep.SetTargetEffect("c25_s2_e");
			rep.setDelay(0);
			ARSystem.giveBuff(player, rep, 100);
			ARSystem.playSound((Entity)player, "c1025db");
		} else {
			Reflect rep = new Reflect(player);
			rep.SetEffect("c25_s2p");
			rep.setValue(1);
			rep.SetNoDamage(true);
			rep.SetTargetEffect("c25_s2_e");
			rep.setDelay(2);
			ARSystem.giveBuff(player, rep, 100);
			ARSystem.playSound((Entity)player, "c25s2");
		}
		return true;
	}

	
	@Override
	public boolean skill3() {
		if(white) {
			skill("c"+number+"_s32");
		} else {
			if(fly > 0) {
				skill("c"+number+"_s3");
			}
		}
		if(ARSystem.isGameMode("zombie")) {
			cooldown[3]+= 0.3;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(player.isOnGround()) {
			if(fly < 400) fly++;
		} else {
			if(fly > 0) fly--;
		}
		if(!white && isps && tk%2==0) {
			skill("c25_ps");
		}
		if(tk%20 == 0) scoreBoardText.add("&c ["+Main.GetText("c25:sk3")+ "]&f : "+ AMath.round(fly*0.05, 2));
		if(tk%20 == 0) {
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
					if(Rule.team.getTeamName(player) != null && Rule.team.isTeamSize(Rule.team.getTeamName(player), true) >= 2) {
						skill("c25_ssp1");
						Rule.buffmanager.selectBuffValue(player, "barrier",500);
						player.setMaxHealth(100);
						player.setHealth(100);
						ARSystem.giveBuff(player, new Nodamage(player), 120);
						ARSystem.giveBuff(player, new Silence(player), 120);
						delay(()->{skill("c25_ssp");},40);
						setcooldown[1] *= 0.3;
						ARSystem.playSoundAll("c1025select");
						spskillon();
						for(Player p: Rule.c.keySet()) {
							Rule.c.get(p).PlayerSpCast(player);
						}
						String n = Main.GetText("c"+number+":name1") + " ";
						if(n.equals("-")) {
							n = "";
						}
						n +=Main.GetText("c"+number+":name2");
						
						for(Player p : Bukkit.getOnlinePlayers()) {
							AdvManager.set(p, 399, 0 , "§f§l"+player.getName() +"§a("+ n +") §f§l" + Main.GetText("c"+number+":ssk"));
						}
						String na = "c"+number+":ssk"+"/&c!!!";
						player.performCommand("tm anitext "+player.getName()+" SUBTITLE true 40 "+na);
						white = true;
					} else {
						spskillon();
						spskillen();
						ARSystem.playSound((Entity)player, "c25sp");
					}
				}
			} else {
				cc = 0;
			}
			if(psopen) {
				scoreBoardText.add("&c ["+Main.GetText("c25:sk0")+ "]&f : "+ cc + " / 20");
			}
		}
		
		if(tk% 10 == 0) {
			for(Entity e : ARSystem.box(player, new Vector(5, 5, 5), box.TARGET)) {
				touma(e);
			}
		}
		return true;
	}
	
	void touma(Entity e) {
		if(!touma && e != null && Rule.c.get(e) != null && Rule.c.get(e).number%1000 == 5) {
			touma = true;
			ARSystem.giveBuff(player, new TimeStop(player), 100);
			ARSystem.giveBuff((LivingEntity)e, new TimeStop((LivingEntity)e), 100);
			e.teleport(ULocal.lookAt(e.getLocation(), player.getLocation()));
			player.teleport(ULocal.lookAt(player.getLocation(), e.getLocation()));
			ARSystem.playSound((Entity)player, "c25touma");
			delay(()->{
				Skill.remove(player, e);
			},90);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			touma(e.getEntity());
		} else {
			if(Rule.buffmanager.GetBuffValue(player, "barrier") > e.getDamage()) {
				Rule.buffmanager.selectBuff(player, "barrier").addValue(-e.getDamage());
				e.setDamage(0);
			} else if(Rule.buffmanager.GetBuffValue(player, "barrier") > 0) {
				e.setDamage(e.getDamage() - Rule.buffmanager.GetBuffValue(player, "barrier"));
				Rule.buffmanager.selectBuff(player, "barrier").setValue(0);
			}
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
	
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c63micoto) {
					is = "micoto";
					break;
				}
			}
		}
		
		if(is.equals("micoto")) {
			ARSystem.playSound((Entity)player, "c25micoto");
		} else {
			ARSystem.playSound((Entity)player, "c25db");
		}
		
		return true;
	}
}
