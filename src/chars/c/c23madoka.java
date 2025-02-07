package chars.c;

import java.util.ArrayList;

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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Nodamage;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import manager.Bgm;
import types.BuffType;
import util.MSUtil;
import util.Map;

public class c23madoka extends c00main{
	int ticks = 0;
	int stack = 0;
	int s2size = 0;
	double ch = 0.33;
	double damage = 1.0;
	boolean start = false;
	Location loc;
	
	public c23madoka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 23;
		damage = 1.0;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		damage = (int) f*0.01;
	}
	
	@Override
	public boolean skill1() {
		if(ticks > 0) {
			cooldown[1] = 0;
			return false;
		}
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(ticks > 0) {
			cooldown[2] = 0;
			return false;
		}
		skill("c"+number+"_s2");
		stack = 0;
		ticks = 190;
		loc = player.getLocation();
		delay(new Runnable() {
			
			@Override
			public void run() {
				skill("c23_s2_i2");
				ArrayList<Player> pls = new ArrayList<Player>();
				for(Player p : Rule.c.keySet() ) {
					double hp = p.getHealth() / p.getMaxHealth();
					if(hp < ch*damage) {
						ARSystem.giveBuff(p, new TimeStop(p), 200);
						pls.add(p);
					}
				}
				
				if(pls.size() >= s2size) {
					spskillon();
					spskillen();
					sp();
				} else {
					for(Player p : pls) {
						Skill.remove(p,player);
					}
				}
			}
		},185);
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(ticks > 0) {
			cooldown[3] = 0;
			return false;
		}
		ARSystem.potion(player,1,60,2);
		ARSystem.potion(player,8,40,4);
		ARSystem.playSound((Entity)player, "c23s3");
		return true;
	}
	
	public void sp(){
		Bgm.setBgm("c23");
		start = false;
		ARSystem.giveBuff(player, new Nodamage(player), 160);
		ARSystem.playSoundAll("c23sp2");
		skill("c23_spb");
		delay(()->{
			WinEvent event = new WinEvent(player);
			Bukkit.getPluginManager().callEvent(event);
			if(!event.isCancelled() && skillCooldown(0)) {
				skill("c23_spa");
				for(Player p : Rule.c.keySet() ) {
					ARSystem.giveBuff(p, new TimeStop(p), 160);
				}
			}
		},120);
	}


	@Override
	public boolean firsttick() {
		if(start) {
			if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time < 10 && Rule.buffmanager.GetBuffTime(player, "timestop") > 0) {
				return false;
			}
			if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
				for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
					buf(buff);
				}
			}
			if(Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
				for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
					if(!buff.getName().equals("silence")) {
						buf(buff);
					}
				}
			}
		}
		
		return false;
	}
	
	public void buf(Buff bf) {
		if(bf.getTime() > 0 && Map.inMap(player)) {
			double val = bf.getTime()/20;
			ARSystem.heal(player, val);
			skillmult += val*0.01;
			damage += val*0.04;
			ARSystem.addBuff(player, new Nodamage(player), bf.getTime()/4);
			bf.setTime(0);
		}
	}
	
	@Override
	public boolean tick() {
		if(s2size == 0) {
			s2size = Rule.c.size()/2;
			if(s2size < 2) s2size = 2;
			if(Rule.c.size() >= 25) {
				ch = 0.11;
			} else if(Rule.c.size() >= 20) {
				ch = 0.17;
			} else if(Rule.c.size() >= 15) {
				ch = 0.22;
			} else if(Rule.c.size() >= 10) {
				ch = 0.26;
			}
		}
		if(ticks > 0) {
			player.teleport(loc);
			ticks--;
		}
		if(!start) start = true;
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c23:ps")+ "]&f : "+ Math.round((damage-1)*100)+"%");
			scoreBoardText.add("&c ["+Main.GetText("c23:sk2")+ "]&f : "+ Math.round((ch*damage)*100)+"%");
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c23:sk0")+ "]&f : "+ s2size);
			if(Math.round((ch*damage)*100) >= 100) {
				Rule.playerinfo.get(player).tropy(23,1);
			}
		}
		return true;
	}
	
	
	@Override
	public void PlayerSpCast(Player p) {
		if(Rule.c.get(p).number == 50) {
			int id = Map.mapid;
			ARSystem.giveBuff(p, new Nodamage(player), 200);
			delay(()->{
			ARSystem.playSoundAll("c1023p3");
				delay(()->{
					Map.getMapinfo(id);
					for(Player pl : Rule.c.keySet()) {
						pl.teleport(Map.randomLoc(pl));
					}
					Skill.remove(p, player);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m killall");
				},160);
			},80);
		}
	}
	
	
	
	boolean majo = true;
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() * damage);
			if(ARSystem.E_sterEgg && Rule.c.get(e.getEntity()) != null) {
				int n = Rule.c.get(e.getEntity()).number;
				if(n%1000 == 21 || n%1000 == 46 || n == 1050) {
					if(majo) {
						majo = false;
						ARSystem.playSound((Entity)player, "c23majo");
					}
					e.setDamage(e.getDamage() * 2 + ((LivingEntity)e.getEntity()).getMaxHealth()*0.33);
				}
			}
		} else {

		}
		return true;
	}
}
