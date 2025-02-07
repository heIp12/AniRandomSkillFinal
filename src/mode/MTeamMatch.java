package mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSinfo;
import ars.ARSystem;
import ars.Rule;
import ars.TeamInfo;
import buff.Buff;
import buff.Nodamage;
import buff.Silence;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import mode.MTeam.TeamComparator;
import types.BuffType;
import types.MapType;

import util.AMath;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class MTeamMatch extends ModeBase{
	HashMap<String,List<Player>> team = new HashMap<>();
	
	public MTeamMatch(){
		super();
		modeName = "teammatch";
		disPlayName = Text.get("main:mode4");
		isOne = true;
	}

	void teamCreate(String name,String color,int code) {
		team.put(name,getPlayers(""+code));
		Rule.team.teamCreate(name);
		Rule.team.getTeam(name).setTeamWin(true);
		Rule.team.getTeam(name).setTeamColor(color);
		
	}
	
	boolean end;
	@Override
	public void option() {
		ARSystem.gameMode2 = false;
		end = true;
		Map.mapType = MapType.TEAMMATCH;
		Map.getMapinfo(1003);
		
		teamCreate("RED","c",2);
		teamCreate("BLUE","9",3);
		
		String local = Main.GetText("map:map1003t1");
		Location loc = new Location(Map.loc_f.getWorld(),Integer.parseInt(local.split(",")[0]),Integer.parseInt(local.split(",")[1]),Integer.parseInt(local.split(",")[2]));
		Rule.team.getTeam("RED").setTeamSpawn(loc);
		local = Main.GetText("map:map1003t2");
		
		loc = new Location(Map.loc_f.getWorld(),Integer.parseInt(local.split(",")[0]),Integer.parseInt(local.split(",")[1]),Integer.parseInt(local.split(",")[2]));
		Rule.team.getTeam("BLUE").setTeamSpawn(loc);

		

		List<Player> players = ARSystem.getReadyPlayer();
		if(getBool("1")) Collections.shuffle(players);
		
		for(Player p : players) {
			boolean next = false;
			for(String s : team.keySet()) {
				if(team.get(s).contains(p)) {
					next = true;
					Rule.team.teamJoin(s, p);
					break;
				}
			}
			if(next) continue;
			List<TeamInfo> teams = Rule.team.getTeams();
	        Collections.sort(teams, new TeamComparator());
	        teams.get(0).Join(p);
			
		}
	}
	
	public void tick(int time) {
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
		
		if(time >= 70 && time%20 == 0) {
			for(Player p : Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg10"));
				ARSystem.potion(p, 24, 100, 1);
			}
		}
		if(Map.mapType == MapType.BIG &&time >= 60 && time%Integer.parseInt(Main.GetText("general:bigmap_time_value")) == 0) {
			if(Boolean.parseBoolean(Main.GetText("general:bigmap_time"))) {
				ARSystem.playSoundAll("0select2");
				Map.sizeM(-1);
			}
		}
	}
	@Override
	public void end() {
		List<Player> players = new ArrayList<Player>();
		for(Player p :Rule.c.keySet()) {
			players.add(p);
		}
		if(Rule.team.allTeam(players)) {
			for(Player p :Rule.c.keySet()) {
				if(Rule.buffmanager.selectBuffType(p, BuffType.HEADCC) != null) {
					for(Buff buff : Rule.buffmanager.getHashMap().get(p).getBuff()) {
						buff.stop();
					}
				}
			}
			if(Rule.c.keySet().size() == 0) {
				ARSystem.gameEnd();
				return;
			}
			Player win = (Player) Rule.c.keySet().toArray()[0];
			String team = Rule.team.getTeam(win).get(0).getTeamName();
			String name = "§e§lTeam : " + team +" Win!!";
			String names = "";
			for(Player p : Rule.team.getTeam(win).get(0).getPlayer()) {
				names+=p.getName()+",";
			}
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.sendTitle(name, "" +names,40,20,40);
				MSUtil.resetbuff(player);
				player.setGameMode(GameMode.ADVENTURE);
				player.setMaxHealth(20);
				player.setHealth(20);
			}
			if(end) {
				end = false;
				ARSystem.gameEnd();
			}
		}
	}
	
	public class TeamComparator implements Comparator<TeamInfo> {
		@Override
		public int compare(TeamInfo f1, TeamInfo f2) {
			if (f1.getPlayer().size() > f2.getPlayer().size()) {
				return 1;
			} else if (f1.getPlayer().size() < f2.getPlayer().size()) {
				return -1; 
			}
			return AMath.random(3)-2;
		}
	}
}
