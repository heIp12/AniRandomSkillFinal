package mode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSinfo;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import chars.c.c000humen;
import chars.c.c001humen2;
import chars.c.c001nb;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.MapType;

import util.AMath;
import util.GetChar;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class MArena extends ModeBase{
	Player p1;
	Player p2;
	
	float p1p = 0;
	float p2p = 0;
	
	public boolean oneGame = true;
	
	World world = Bukkit.getWorld("world");
	int time = 0;
	public MArena(){
		super();
		modeName = "arena";
		isOnlyOne = true;
		disPlayName = Text.get("main:mode11");
	}
	
	@Override
	public void option() {
		ARSystem.gameMode2 = false;
		Map.getMapinfo(17);
		for(Player p : ARSystem.getReadyPlayer()) {
			Rule.c.put(p, new c001humen2(p, Rule.gamerule, null));
		}
		ARSystem.AniRandomSkill.time = 0;
		super.option();
	}
	@Override
	public void firstTick() {
		for(Player p : Rule.c.keySet()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode11"));
		}
	}
	public void playerSelect() {
		if(p1p == 0 || p2p == 0) Rule.Var.setInt("info.Shop.money", (int) (Rule.Var.Loadint("info.Shop.money")+ (p1p*p2p)*70));	
		if(p2 != null) {
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p) instanceof c001humen2) {
					((c001humen2)Rule.c.get(p)).win(1);
				}
			}
			if(oneGame) {
				Skill.win(p2);
				return;
			}
			Rule.c.put(p2, new c001humen2(p2, Rule.gamerule, null));
		}
		if(p1 != null) {
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p) instanceof c001humen2) {
					((c001humen2)Rule.c.get(p)).win(2);
				}
			}
			if(oneGame) {
				Skill.win(p1);
				return;
			}
			Rule.c.put(p1, new c001humen2(p1, Rule.gamerule, null));
		}
		p1p = p2p = 0;
		ARSystem.RandomPlayer().performCommand("c removeall");
		p1 = ARSystem.RandomPlayer();
		while(p2 == null || p2 == p1) p2 = ARSystem.RandomPlayer();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(Player pl : Bukkit.getOnlinePlayers()) {
				p.showPlayer(pl);
			}
		}
		for(Player pl : Rule.c.keySet()) {
			p2.hidePlayer(pl);
		}
		for(Player pl : Rule.c.keySet()) {
			p1.hidePlayer(pl);
		}
		
		for(Player p : Rule.c.keySet()) {
			if(Rule.c.get(p) instanceof c001humen2) {
				if(p != p1 && p != p2) {
					((c001humen2)Rule.c.get(p)).be(true);
					((c001humen2)Rule.c.get(p)).players(p1, p2);
				}
			}
		}
		
		
		ARSystem.chaRrep(p1);
		ARSystem.chaRrep(p2);
		Rule.c.get(p1).inGame = true;
		Rule.c.get(p2).inGame = true;
		int j = Rule.c.get(p1).getCode();
		String name1 = Main.GetText("c"+j+":name1").replace("-", "") +" "+ Main.GetText("c"+j+":name2");
		j = Rule.c.get(p2).getCode();
		String name2 = Main.GetText("c"+j+":name1").replace("-", "") +" "+ Main.GetText("c"+j+":name2");
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p != p1 && p != p2) {
				p.sendTitle(Text.get("main:mode11-1"),
						"§a§l"+p1.getName()+"§c[§f"+name1+"§c]" +" §e§lvs §f§l" + p2.getName()+"§c[§f"+name2+"§c]");
			}
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p) instanceof c001humen2) {
					if(p != p1 && p != p2) {
						int i =((c001humen2)Rule.c.get(p)).be(false);
						if(i > 0) {
							p1p += i;
						} else {
							p2p -= i;
						}
					}
				}
			}
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p) instanceof c001humen2) {
					if(p != p1 && p != p2) {
						((c001humen2)Rule.c.get(p)).be2(p1p,p2p);
					}
				}
			}
			p1.showPlayer(p2);
			p2.showPlayer(p1);
		},140);
	}
	float p(float p,float p2) {
		if(p == 0) return 0;
		if(p2 == 0) return 1;
		return p/p2;
	}
	
	public void tick(int time) {
		if(Rule.c.size() == 1) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				for(Player pl : Bukkit.getOnlinePlayers()) {
					if(p2 != null) {
						if(Rule.c.get(p) instanceof c001humen2) {
							((c001humen2)Rule.c.get(p)).win(1);
						}
						Rule.c.put(p2, new c001humen2(p2, Rule.gamerule, null));
					}
					if(p1 != null) {
						if(Rule.c.get(p) instanceof c001humen2) {
							((c001humen2)Rule.c.get(p)).win(2);
						}
						Rule.c.put(p1, new c001humen2(p1, Rule.gamerule, null));
					}
					p.showPlayer(pl);
				}
			}
			Skill.win((Player)Rule.c.keySet().toArray()[0]);
			return;
		}
		if(time > 0) {
			if(Rule.c.get(p1) == null) {
				p1 = null;
				playerSelect();
			}
			if(Rule.c.get(p2) == null) {
				p2 = null;
				playerSelect();
			}
		}
	}
	
	public void tick2() {
		for(Player p : Rule.c.keySet()) {
			if(p == p1 || p == p2) {
				if(!ULocal.BoxIn(new Location(world,-415,63,32), new Location(world,-369,80,79), p.getLocation())){
					Location loc = p.getLocation();
					loc = ULocal.BoxNear(new Location(world,-414.9,63.1,32.1), new Location(world,-369.1,79.9,78.9), p.getLocation());
					loc.setYaw(loc.getYaw() + 180);
					p.teleport(loc);
				}
			} else {
				ARSystem.giveBuff(p, new Nodamage(p), 40);
				ARSystem.giveBuff(p, new Noattack(p), 40);
				if(ULocal.BoxIn(new Location(world,-416,63,31), new Location(world,-368,100,81), p.getLocation())){
					Location loc = p.getLocation();
					loc = ULocal.BoxNear(new Location(world,-416.2,63,30.8), new Location(world,-367.8,100,81.2), p.getLocation(),true);
					loc.setY(p.getLocation().getY());
					if(loc.getY() < 69) loc.setY(69);
					p.teleport(loc);
				}
			}
		}
	}

}
