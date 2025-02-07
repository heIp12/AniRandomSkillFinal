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
import types.BuffType;
import types.MapType;

import util.AMath;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class MTeam extends ModeBase{
	HashMap<String,List<Player>> team;
	boolean end = true;
	boolean king = false;
	List<Player> hero = new ArrayList<Player>();
	
	public MTeam(){
		super();
		modeName = "team";
		disPlayName = Text.get("main:mode5");
		isOne = true;
	}
	
	void teamCreate(String name,String color,int code) {
		team.put(name,getPlayers(""+code));
		Rule.team.teamCreate(name);
		Rule.team.getTeam(name).setTeamWin(true);
		Rule.team.getTeam(name).setTeamColor(color);
	}
	
	@Override
	public void PlayerDeathEvent(Player p, Entity killer) {
		if(getBool("16")) {
			for(TeamInfo team : Rule.team.getTeams()) {
				if(team.getPlayer().size() > 0 && team.getPlayer().get(0) == p) {
					for(Player pl : team.getPlayer()) {
						if(Rule.c.get(pl) != null && pl != p) Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{Skill.quit(pl);},2);
					}
					team.Remove();
				}
			}
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
			TeamInfo team1 = null;
			int teamcount = 0;
			for(TeamInfo team : Rule.team.getTeams()) {
				if(team.getPlayer().size() > 0) {
					team1 = team;
					teamcount++;
				}
			}
			if(teamcount == 1) {
				if(team1.isTeamWin()) {
					Player win = (Player) Rule.c.keySet().toArray()[0];
					String teamname = team1.getTeamName();
					String name = "§e§lTeam : " + teamname +" Win!!";
					String names = "";
					for(Player pl : Rule.team.getTeam(win).get(0).getPlayer()) {
						names+=pl.getName()+",";
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
		},20);
	}
	
	@Override
	public void option() {
		team = new HashMap<>();
		teamCreate("RED","c",2);
		teamCreate("BLUE","9",3);
		if(getBool("4"))teamCreate("GREEN","a",5);
		if(getBool("6"))teamCreate("YELLOW","e",7);
		if(getBool("8"))teamCreate("PINK","d",9);
		if(getBool("10"))teamCreate("PUPPLE","5",11);
		if(getBool("12"))teamCreate("BLACK","8",13);
		if(getBool("14"))teamCreate("WHITE","l",15);
		
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
		if(getBool("16")) {
			for(TeamInfo team : Rule.team.getTeams()) {
				if(team.getPlayer().size() > 0) {
					Player king = team.getPlayer().get(0);
					king.sendMessage("§a§l[ARSystem] §f"+Text("team_16_Msg1"));
					for(Player p : team.getPlayer()) {
						if(king != p) {
							p.sendMessage("§a§l[ARSystem] §f"+Text("team_16_Msg2").replace("{player}", king.getName()));
							p.sendTitle("",Text("team_16_Msg2").replace("{player}", king.getName()),0,100,0);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void firstTick() {
		if(getBool("19")) {
			for(TeamInfo team : Rule.team.getTeams()) {
				if(team.getPlayer().size() > 0) {
					for(Player p : team.getPlayer()) {
						p.teleport(team.getPlayer().get(0));
					}
				}
			}
		}
	}
	
	public void tick(int time) {
		if(getBool("17")) {
			for(TeamInfo team : Rule.team.getTeams()) {
				if(team.getPlayer().size() > 0) {
					Player player = null;
					for(Player p : team.getPlayer()) {
						if(Rule.c.get(p) != null) {
							if(player != null) {
								player = null;
								break;
							}
							player = p;
						}
					}
					if(player != null && !hero.contains(player)) {
						Bukkit.broadcastMessage("§a§l[ARSystem] §f"+ Text("team_17_Msg1").replace("{player}", player.getName()).replace("{team}", "§"+team.getTeamColor()+"["+team.getTeamName() +"]"));
						hero.add(player);
						ARSystem.heal(player, 10000);
						String s = get("18");
						try {
							for(String st : s.split(",")) {
								int i = Integer.parseInt(st);
								ARSystem.addItem(player, i);
							}
						} catch(NumberFormatException e) {
							Bukkit.broadcastMessage("§a§l[ARSystem] §f Item Surch Error");
						}
					}
				}
			}
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
