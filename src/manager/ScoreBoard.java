package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import ars.Rule;
import ars.TeamInfo;

public class ScoreBoard {
	  static List<String> text = new ArrayList<String>();

	  public static void createScoreboard(Player player, List<String> txt)
	  {
		 Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		  for(TeamInfo t :Rule.team.getTeams()) {
			  try {
				  Team tm = t.getTeam();
				  board.registerNewTeam(tm.getName());
				  board.getTeam(tm.getName()).setPrefix(tm.getPrefix());
				  board.getTeam(tm.getName()).setColor(tm.getColor());
				  for(OfflinePlayer pl : tm.getPlayers()) {
					  board.getTeam(tm.getName()).addPlayer(pl);
				  }
			  } catch (Exception e) {
				  
			  }
		  }
		  
		  Objective o = board.registerNewObjective("Scoreboard", "dummy");
		  o.setDisplayName("§a§lA§ani§lR§aandom§lS§akill");
		  o.setDisplaySlot(DisplaySlot.SIDEBAR);
		  text = txt;
		    
		  int size = text.size();
		  String f = "";
		  for (String s : text)
		  {
			  f = s;
			  o.getScore(ChatColor.translateAlternateColorCodes('&', f)).setScore(--size);
		  }
		  player.setScoreboard(board);
	  }
}
