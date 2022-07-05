package ars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class TeamInfo {
	List<Player> teamPlayer = new ArrayList<Player>();
	boolean teamWin = false;
	boolean teamAttack = false;
	String teamColor = "f";
	String teamName = "";
	Location teamSpawn = null;
	org.bukkit.scoreboard.Team team;
	
	public TeamInfo(String name){
		teamName = name;
		if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam(name) != null) {
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam(name).unregister();
		}
		team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(name);
		team.setCanSeeFriendlyInvisibles(true);
	}
	
	public void Remove(){
		Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName).unregister();
		QuitAll();
	}
	
	public void QuitAll(){
		if(team != null) {
			for(Player p : teamPlayer) {
				try {
					team.removePlayer(p);
				} catch (IllegalStateException e) {
					
				}
			}
			teamPlayer.clear();
		}
	}
	
	public void Join(Player p){
		p.setCustomName(null);
		p.setDisplayName(null);
		if(!teamPlayer.contains(p)) {
			teamPlayer.add(p);
			team.addPlayer(p);
		}
	}
	public void Quit(Player p){
		if(teamPlayer.contains(p)) {
			teamPlayer.remove(p);
			team.removePlayer(p);
		}
	}
	
	public boolean isTeam(Player p ,Player p2) {
		if(!teamPlayer.isEmpty() && teamPlayer.contains(p) && teamPlayer.contains(p2)) {
			return true;
		}
		return false;
	}
	
	public boolean isTeamWin() {return teamWin;}
	public boolean isTeamAttack() {return teamAttack;}
	public String getTeamColor() {return teamColor;}
	public String getTeamName() {return teamName;}
	public List<Player> getPlayer() {return teamPlayer;}
	public Location getTeamSpawn() {return teamSpawn;}
	public int getTeamCount() {return teamPlayer.size();}
	public org.bukkit.scoreboard.Team getTeam() {return team;}
	
	public void setTeamWin(boolean teamWin) {this.teamWin =  teamWin;}
	public void setTeamAttack(boolean teamAttack) {this.teamAttack =  teamAttack;}
	public void setTeamColor(String teamColor) {
		this.teamColor = teamColor;
		team.setColor(ChatColor.getByChar(teamColor));
		team.setPrefix(ChatColor.getByChar(teamColor)+"["+teamName+"] ");
	}
	public void setTeamName(String teamName) {this.teamName =  teamName;}
	public void setTeamSpawn(Location teamSpawn) {this.teamSpawn = teamSpawn;}
}
