package ars;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import Main.Main;
import util.AMath;
import util.Holo;

public class Team {
	HashMap<String,TeamInfo> player;
	Team(){
		player = new HashMap<String,TeamInfo>();
	}
	
	public void reload() {
		for(TeamInfo team : player.values()) {
			team.QuitAll();
		}
		player.clear();
	}
	public void teamCreate(String name) {
		player.put(name, new TeamInfo(name));
	}
	public void teamRemove(String name) {
		if(player.get(name) != null) {
			player.get(name).Remove();
			player.remove(name);
		}
	}
	public void teamJoin(String name,Player p) {
		player.get(name).Join(p);
	}
	public void teamQuit(String name,Player p) {
		player.get(name).Quit(p);
	}
	
	public TeamInfo getTeam(String name) {
		return player.get(name);
	}
	
	public List<TeamInfo> getTeam(Player player){
		List<TeamInfo> team = new ArrayList<TeamInfo>();
		for(String s : this.player.keySet()) {
			if(this.player.get(s).teamPlayer.contains(player)) {
				team.add(this.player.get(s));
			}
		}
		return team;
	}
	
	public List<TeamInfo> getTeams() {
		List<TeamInfo> team = new ArrayList<TeamInfo>();
		if(player != null) {
			for(String s : this.player.keySet()) {
				team.add(this.player.get(s));
			}
		}
		return team;
	}
	
	public Location getTeamLocation(Player player){
		List<TeamInfo> team = getTeam(player);
		Location loc = null;
		if(team.size() > 0) {
			for(TeamInfo t : team) {
				if(t.getTeamSpawn() != null) {
					loc = t.getTeamSpawn();
				}
			}
		}
		return loc;
	}
	
	public boolean allTeam(List<Player> p) {
		boolean allteam = true;
		for(Player pla : p) {
			for(Player pl : p) {
				boolean isteam = false;
				for(String s : player.keySet()) {
					if(player.get(s).isTeam(pla, pl)) {
						isteam = true;
					}
				}
				if(!isteam) allteam = false;
			}
		}
		return allteam;
	}
	
	public boolean isTeamAttack(Player p ,Player p2) {
		for(String s : player.keySet()) {
			if(!player.get(s).isTeamAttack() && player.get(s).isTeam(p, p2)) {
				return true;
			}
		}
		return false;
	}
	public boolean isTeam(Player p ,Player p2) {
		for(String s : player.keySet()) {
			if(player.get(s).isTeam(p, p2)) {
				return true;
			}
		}
		return false;
	}
	public boolean isTeam(Player p) {
		for(String s : player.keySet()) {
			if(player.get(s).getPlayer().contains(p)) return true;
		}
		return false;
	}
	public String getTeamName(Player p) {
		for(String s : player.keySet()) {
			if(player.get(s).getPlayer().contains(p)) {
				return s;
			}
		}
		return null;
	}
	public int isTeamSize(String team,boolean isAlive) {
		if(isAlive) {
			int i = 0;
			for(Player p : player.get(team).getPlayer()) {
				if(Rule.c.get(p) != null) i++;
			}
			return i;
		}
		return player.get(team).getPlayer().size();
	}
}
