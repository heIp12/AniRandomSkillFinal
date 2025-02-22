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
import chars.ca.c100001;
import chars.ca.c4000megumin;
import manager.AdvManager;
import manager.Bgm;
import mode.MKagerou;
import mode.MLoboTomy;
import mode.MGUN;
import mode.MKanna;
import mode.MKiller;
import mode.MNormal;
import mode.MZombie;
import mode.ModeBase;
import mode.MZombieAdv;
import types.MapType;
import util.AMath;
import util.MSUtil;
import util.Map;

public class ARSinfo {
	public int time = 0;
	public int player = 0;
	public float gamescore = 0;
	public boolean mob = false;
	int allTeam = 0;
	
	public HashSet<Player> chageplayer = new HashSet<Player>();
	public HashMap<Player,Integer> startplayer = new HashMap<Player,Integer>();
	public HashMap<Player,Integer> playerkill = new HashMap<Player,Integer>();
	boolean isstart = false;
	
	public List<ModeBase> modes;
	List<Player> skip;

	int gamecode = 0;

	public int getGamecode() { return gamecode; }
	
	ARSinfo(int timer,List<ModeBase> modes){
		time-= timer;
		this.modes = modes;
		gamecode = 100000+AMath.random(899999);
	}

	public void addMode(ModeBase mode) {
		modes.add(mode);
	}
	public void PlayerDeath(Player p,Entity killer) {
		for(ModeBase md : modes) md.PlayerDeathEvent(p,killer);
	}
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
		for(ModeBase mb : modes) {
			mb.start();
		}
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
		List<Player> players = new ArrayList<>();
		for(Player p : Rule.c.keySet()) {
			players.add(p);
		}
		if(time >= 0) {
			if(ARSystem.winstop <= 0 && Rule.c.size() > 1 && ARSystem.gameMode2 && Rule.team.allTeam(players) && Map.mapid != 1011) {
				allTeam++;
				if(allTeam>=10) {
					allTeam = 0;
					Rule.team.reload();
					ARSystem.playSoundAll("0click2",0.2f);
					Bukkit.broadcastMessage("§a§l[ARSystem] : §c§lTeam Remove");
				}
			}
			
			if(!isstart) {
				isstart = true;
				first();
			}
			for(ModeBase mb : modes) {
				if(Map.mapid != 1011) mb.tick(time);
			}
		}
		time++;
	}
	
	public void Tick() {
		for(ModeBase mb : modes) {
			mb.tick2();
		}
	}
	
	public void first() {
		for(Player p : Rule.c.keySet()) {
			startplayer.put(p,Rule.c.get(p).getCode());
		}
		for(ModeBase mb : modes) {
			mb.firstTick();
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
