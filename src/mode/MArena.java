package mode;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import Main.Main;
import ars.ARSystem;
import ars.Rule;
import ars.TeamInfo;
import buff.Noattack;
import buff.Nodamage;
import chars.c.c001humen2;
import event.Skill;
import manager.AdvManager;
import types.MapType;
import util.AMath;
import util.GetChar;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class MArena extends ModeBase{
	List<Player> team1 = null;
	List<Player> team2 = null;
	TeamInfo p1;
	TeamInfo p2;
	int team1count = 0;
	int team2count = 0;
	
	float p1p = 0;
	float p2p = 0;
	
	public boolean oneGame = true;
	
	World world = Bukkit.getWorld("world");
	int time = 0;
	public MArena(){
		super();
		modeName = "arena";
		isOne = true;
		disPlayName = Text.get("main:mode11");
	}
	
	TeamInfo teamCreate(String name,String color) {
		Rule.team.teamCreate(name);
		Rule.team.getTeam(name).setTeamWin(true);
		Rule.team.getTeam(name).setTeamColor(color);
		return Rule.team.getTeam(name);
	}
	
	
	@Override
	public void option() {
		team1count = 0;
		team2count = 0;
		ARSystem.gameMode2 = false;
		Map.getMapinfo(17);
		Map.mapType = MapType.NORMAL;
		for(Player p : ARSystem.getReadyPlayer()) {
			Rule.c.put(p, new c001humen2(p, Rule.gamerule, null));
		}
		ARSystem.AniRandomSkill.time = 0;
		
		
		if(getBool("1")) {
			List<Player> p = ARSystem.RandomPlayers();
			team1 = p.subList(0,p.size()/2);
			p = ARSystem.RandomPlayers();
			for(Player pl : team1) p.remove(pl);
			team2 = p;
		} else {
			team1 = getPlayers("2");
			team2 = getPlayers("3");
			if(team1.size() <= 0) {
				List<Player> pl = ARSystem.RandomPlayers();
				for(Player p : pl.subList(0,pl.size()/2)) {
					if(!team2.contains(p)) {
						team1.add(p);
					}
				}
			}
			if(team2.size() <= 0) {
				for(Player p : ARSystem.RandomPlayers()) {
					if(!team1.contains(p)) {
						team2.add(p);
					}
				}
			}
			
			oneGame = !getBool("4");
		}
		int i = 1;
		while(Text.get("mode:arena_name_"+i) != null) i++;
		i-=1;
		i = AMath.random((i+1)/2)*2;
		p1 = teamCreate(Text.get("mode:arena_name_"+(i-1)),"c");
		p2 = teamCreate(Text.get("mode:arena_name_"+i),"9");
		
		
		String s = "";
		for(Player p : team1) {
			if(team2.contains(p)) team2.remove(p);
			s+=p.getName() +", ";
		}
		Bukkit.broadcastMessage("§"+p1.getTeamColor()+"["+p1.getTeamName()+"] §7" + s);
		
		s = "";
		for(Player p : team2) {
			if(team1.contains(p)) team1.remove(p);
			s+=p.getName() +", ";
		}
		if(!getBool("4") && !getBool("5")) {
			team1 = team1.subList(0, 1);
			team2 = team2.subList(0, 1);
		}
		Bukkit.broadcastMessage("§"+p2.getTeamColor()+"["+p2.getTeamName()+"] §7" + s);
		super.option();
	}
	
	@Override
	public void firstTick() {
		for(Player p : Rule.c.keySet()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode11"));
		}
		ARSystem.winstop = 10000000;

		playerSelect();
		
	}
	
	void win(List<Player> team) {
		String s = "";
		for(Player e : team) {
			s += e.getDisplayName()+" , ";
		}
		Skill.win(s);
	}
	
	public void playerSelect() {
		if(p1p == 0 || p2p == 0) Rule.Var.setInt("info.Shop.money", (int) (Rule.Var.Loadint("info.Shop.money")+ (p1p*p2p)*70));	
		
		p1p = p2p = 0;
		NpcPlayer.npc(Map.getCenter()).performCommand("c removeall");
		
		
		if(getBool("5")) {
			for(Player p : team1) {
				if(team2.contains(p)) team2.remove(p);
				p1.Join(p);
				team1count++;
			}
			for(Player p : team2) {
				if(team1.contains(p)) team1.remove(p);
				p2.Join(p);
				team2count++;
			}
		} else {
			if(team1.size() > team1count) {
				Player p = team1.get(team1count); 
				if(team2.contains(p)) team2.remove(p);
				p1.Join(p);
				team1count++;
			}
			
			if(team2.size() > team2count) {
				Player p = team2.get(team2count); 
				if(team1.contains(p)) team1.remove(p);
				p2.Join(p);
				team2count++;
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(Player pl : Bukkit.getOnlinePlayers()) {
				p.showPlayer(pl);
			}
		}
		
		for(Player pl : Rule.c.keySet()) {
			for(Player p : p2.getPlayer()) {
				p.hidePlayer(pl);
			}
		}
		for(Player pl : Rule.c.keySet()) {
			for(Player p : p1.getPlayer()) {
				p.hidePlayer(pl);
			}
		}
		
		for(Player p : Rule.c.keySet()) {
			if(Rule.c.get(p) instanceof c001humen2) {
				if(!p1.getPlayer().contains(p) && !p2.getPlayer().contains(p)) {
					((c001humen2)Rule.c.get(p)).be(true);
					((c001humen2)Rule.c.get(p)).players(p1, p2);
				}
			}
		}
		
		if(getBool("6")) {
			for(Player p : p1.getPlayer()) {
				int i = Rule.playerinfo.get(p).playerc;
				if(i == 0) i = AMath.random(GetChar.getCount());
				Rule.c.put(p, GetChar.get(p, Rule.gamerule, ""+ i));
			}
			for(Player p : p2.getPlayer()) {
				int i = Rule.playerinfo.get(p).playerc;
				if(i == 0) i = AMath.random(GetChar.getCount());
				Rule.c.put(p, GetChar.get(p, Rule.gamerule, ""+ i));
			}
		} else {
			for(Player p : p1.getPlayer()) ARSystem.chaRrep(p);
			for(Player p : p2.getPlayer()) ARSystem.chaRrep(p);
		}
		for(Player p : Rule.c.keySet()) Rule.c.get(p).inGame = true;
		
		//msg
		String name1 = "";
		for(Player p : p1.getPlayer()) {
			int code = Rule.c.get(p).getCode();
			name1 += "["+code+"]"+Main.GetText("c"+code+":name2") + " , ";
		}
		String name2 = "";

		for(Player p : p2.getPlayer()) {
			int code = Rule.c.get(p).getCode();
			name2 += "["+code+"]"+Main.GetText("c"+code+":name2") + " , ";
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!p1.getPlayer().contains(p) && !p2.getPlayer().contains(p)) {
				p.sendMessage("§"+p1.getTeamColor()+"["+p1.getTeamName() + "] "+name1);
				p.sendMessage("§"+p2.getTeamColor()+"["+p2.getTeamName() + "] "+name2);
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
			for(Player p : Bukkit.getOnlinePlayers()) {
				for(Player pl : Bukkit.getOnlinePlayers()) {
					if(Rule.team.getTeam(pl).size() > 0) {
						p.showPlayer(pl);
						pl.showPlayer(p);
					}
				}
			}
		},140);
	}
	float p(float p,float p2) {
		if(p == 0) return 0;
		if(p2 == 0) return 1;
		return p/p2;
	}
	
	public void tick(int time) {
		if(time > 0) {
			if(p1.getPlayer().size() <= 0 && team1count >= team1.size()) {
				p1.QuitAll();
				if(oneGame) {
					win(team2);
					return;
				}
				playerSelect();
			}
			if(p2.getPlayer().size() <= 0 && team2count >= team2.size()) {
				p2.QuitAll();
				if(oneGame) {
					win(team1);
					return;
				}
				playerSelect();
			}
		}
	}
	
	
	@Override
	public void PlayerDeathEvent(Player p, Entity killer) {
		if(p1.getPlayer().contains(p)) {
			p1.Quit(p);
			if(getBool("4")) team2count--;
			if(p1.getPlayer().size() <= 0) {
				for(Player pl : Rule.c.keySet()) {
					if(Rule.c.get(p) instanceof c001humen2) {
						((c001humen2)Rule.c.get(pl)).win(1);
					}
				}
				if(oneGame || team1count >= team1.size()) {
					win(team2);
					return;
				}
				for(Player pl : p2.getPlayer()) {
					Rule.c.put(pl, new c001humen2(pl, Rule.gamerule, null));
				}
				playerSelect();
			}

			
		}
		if(p2.getPlayer().contains(p)) {
			p2.Quit(p);
			if(getBool("4")) team1count--;
			
			if(p2.getPlayer().size() <= 0) {
				for(Player pl : Rule.c.keySet()) {
					if(Rule.c.get(pl) instanceof c001humen2) {
						((c001humen2)Rule.c.get(pl)).win(2);
					}
				}
				if(oneGame || team2count >= team2.size()) {
					win(team1);
					return;
				}
				for(Player pl : p1.getPlayer()) {
					Rule.c.put(pl, new c001humen2(pl, Rule.gamerule, null));
				}
				playerSelect();
			}
		}
	}
	
	public void tick2() {
		for(Player p : Rule.c.keySet()) {
			if(p1.getPlayer().contains(p) || p2.getPlayer().contains(p)) {
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
					loc = ULocal.BoxNear(new Location(world,-416.5,63,30.5), new Location(world,-367.5,100,81.5), p.getLocation(),true);
					loc.setY(p.getLocation().getY());
					if(loc.getY() < 69) loc.setY(69);
					p.teleport(loc);
				}
			}
		}
	}

}
