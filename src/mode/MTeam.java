package mode;

import java.util.ArrayList;
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
	public MTeam(){
		super();
		modeName = "team";
		disPlayName = Text.get("main:mode5");
		isOne = true;
	}
	@Override
	public void option() {
		Rule.team.teamCreate("RED");
		Rule.team.teamCreate("BLUE");
		Rule.team.teamCreate("YELLOW");
		Rule.team.teamCreate("GREEN");
		Rule.team.getTeam("RED").setTeamWin(true);
		Rule.team.getTeam("BLUE").setTeamWin(true);
		Rule.team.getTeam("YELLOW").setTeamWin(true);
		Rule.team.getTeam("GREEN").setTeamWin(true);
		Rule.team.getTeam("RED").setTeamColor("c");
		Rule.team.getTeam("BLUE").setTeamColor("9");
		Rule.team.getTeam("YELLOW").setTeamColor("e");
		Rule.team.getTeam("GREEN").setTeamColor("a");
		
		List<Player> gameplayers = new ArrayList<Player>();
		List<Player> gameplayers2 = new ArrayList<Player>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(Rule.playerinfo.get(p).gamejoin) {
				gameplayers.add(p);
				gameplayers2.add(p);
			}
		}
		int blue = 0;
		int red = 0;
		int yellow = 0;
		int green = 0;
		
		for(Player p : gameplayers) {
			if(Rule.playerinfo.get(p).team != null) {
				if(Rule.playerinfo.get(p).team.equals("red")) {
					Rule.team.teamJoin("RED", p);
					gameplayers2.remove(p);
					red++;
				}
				if(Rule.playerinfo.get(p).team.equals("blue")) {
					Rule.team.teamJoin("BLUE", p);
					gameplayers2.remove(p);
					blue++;
				}
				if(Rule.playerinfo.get(p).team.equals("green")) {
					Rule.team.teamJoin("GREEN", p);
					gameplayers2.remove(p);
					green++;
				}
				if(Rule.playerinfo.get(p).team.equals("yellow")) {
					Rule.team.teamJoin("YELLOW", p);
					gameplayers2.remove(p);
					yellow++;
				}
			}
		}
		
		for(Player p : gameplayers) {
			if(gameplayers2.contains(p)) {
				if(blue < red && blue < green && blue < yellow) {
					Rule.team.teamJoin("BLUE", p);
					blue++;
				} else if(red < green && red < yellow) {
					Rule.team.teamJoin("RED", p);
					red++;
				}  else if(yellow < green) {
					Rule.team.teamJoin("YELLOW", p);
					yellow++;
				} else {
					Rule.team.teamJoin("GREEN", p);
					green++;
				}
			}
		}
	}
	
	public void tick(int time) {
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
			ARSystem.gameEnd();
		}
	}
}
