package ars;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import Main.Main;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import ca.c100001;
import ca.c4000megumin;
import manager.AdvManager;
import manager.Bgm;
import mode.Kagerou;
import mode.LoboTomy;
import mode.MGUN;
import mode.MKanna;
import mode.MKiller;
import mode.MNormal;
import mode.MZombie;
import mode.ZombieAdv;
import types.MapType;
import types.modes;
import util.AMath;
import util.MSUtil;
import util.Map;

public class ARSinfo {
	public int time = 0;
	public int player = 0;
	public float gamescore = 0;
	public boolean mob = false;
	
	public HashSet<Player> chageplayer = new HashSet<Player>();
	public HashMap<Player,Integer> startplayer = new HashMap<Player,Integer>();
	public HashMap<Player,Integer> playerkill = new HashMap<Player,Integer>();
	boolean isstart = false;
	
	ARSinfo(int timer){
		time-= timer;
	}
	List<Player> skip;
	public void skip(Player p) {
		if(!skip.contains(p)) {
			if(Boolean.parseBoolean(Main.GetText("general:skip_death_count")) || !Boolean.parseBoolean(Main.GetText("general:skip_death_count")) && Rule.c.get(p) != null) {
				skip.add(p);
				int m = 0;
				m = Math.round(Rule.c.size() / Bukkit.getOnlinePlayers().size());
				if(m < 2) m = 2;
				int count = 0;
				for(Player player : skip) {
					if(Rule.c.get(player) != null) {
						count+= m;
					} else {
						count++;
					}
				}
				Bukkit.broadcastMessage("§a§l[ARSystem] : §c§lSkip : §e§l" + AMath.round(((float)count)/m,1) +" §f§l/ §a§l"  + (Rule.c.size()/2+1));
				if(count/m >= Rule.c.size()/2+1) {
					Rule.c.put(ARSystem.RandomPlayer(), new c4000megumin(p, Rule.gamerule, null));
				}
			}
		}
	}
	
	public void start() {
		isstart = false;
		for(Player p : Rule.c.keySet()) {
			for(Player pl : Rule.c.keySet()) {
				if(!Rule.team.isTeamAttack(pl, p)) {
					p.hidePlayer(pl);
				}
			}
		}
		skip = new ArrayList<>();
	}
	
	public void reset() {
		time = 0;
	}
	
	public void Time() {
		if(time >= 0 && !isstart) {
			isstart = true;
			first();
		}
		time++;
		
		if(mob) {
			ZombieAdv.tick(time);
		}
		if(ARSystem.gameMode == modes.LOBOTOMY) {
			LoboTomy.tick(time);
			return;
		}
		if(ARSystem.gameMode == modes.KAGEROU) {
			Kagerou.tick(time);
			return;
		}
		if(ARSystem.gameMode == modes.KANNA) {
			MKanna.tick(time);
			return;
		}
		if(ARSystem.gameMode == modes.GUN) {
			MGUN.tick(time);
			return;
		}
		if(ARSystem.gameMode == modes.KILLER) {
			Bukkit.getWorld("world").setTime(18000);
			MKiller.tick(time);
			return;
		}
		if(ARSystem.gameMode == modes.ZOMBIE) {
			MZombie.tick(time);
			return;
		}
		if(ARSystem.gameMode == modes.TEAMMATCH) {
			if(time >= 60 && time%20 == 0 && time <= 120) {
				for(Player p : Rule.c.keySet()) {
					AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg10"));
					ARSystem.potion(p, 24, 100, 1);
				}
			}
			if(time >= 121 && time%10 == 0) {
				for(Player p :Rule.c.keySet()) {
					AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg7"));
				}
				ARSystem.Death(ARSystem.RandomPlayer(),ARSystem.RandomPlayer());
			}
			return;
		}
		
		if(ARSystem.gameMode == modes.TEAM || ARSystem.gameMode == modes.NORMAL) {
			MNormal.tick(time);
		}
	}
	
	public void first() {
		for(Player p : Rule.c.keySet()) {
			startplayer.put(p,Rule.c.get(p).getCode());
		}
		if(AMath.random(100) <= Rule.urf && ARSystem.gameMode != modes.LOBOTOMY) {
			ARSystem.playSoundAll("0select2");
			for(Player p :Rule.c.keySet()) {
				double cool = AMath.random(40) * 0.05;
				cool = Math.round(cool*100)/100.0;
				Rule.c.get(p).sskillmult+=cool;
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg3") +" +"+(cool*100)+"% ");
			}
		}
		if(AMath.random(100) <= 2 && ARSystem.gameMode != modes.LOBOTOMY) {
			ARSystem.playSoundAll("0select2");
			mob = true;
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg53") +" Adv");
			}
		}
		if(ARSystem.gameMode == modes.ZOMBIE) {
			Player p = ARSystem.RandomPlayer();
			Rule.c.put(p, new c100001(p, Rule.gamerule, null));
			for(Player pl :Rule.c.keySet()) {
				AdvManager.set(pl, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode3"));
				AdvManager.set(pl, 388, 0,  Main.GetText("main:msg32"));
			}
		}
		if(ARSystem.gameMode == modes.ONE) {
			for(Player pl :Rule.c.keySet()) {
				AdvManager.set(pl, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode2"));
			}
		}
		if(Rule.pick) {
			for(Player pl :Rule.c.keySet()) {
				AdvManager.set(pl, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode0"));
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(Player pl : Bukkit.getOnlinePlayers()) {
				p.showPlayer(pl);
			}
		}
		
	}
	public int getTime() {
		return time;
	}
}
