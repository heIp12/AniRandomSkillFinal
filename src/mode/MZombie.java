package mode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import chars.ca.c100001;
import manager.AdvManager;
import manager.Bgm;
import util.AMath;
import util.MSUtil;
import util.Map;
import util.Text;

public class MZombie extends ModeBase{
	public MZombie(){
		super();
		modeName = "zombie";
		disPlayName = Text.get("main:mode3");
		isOnlyOne = true;
	}
	@Override
	public void option() {
		Rule.team.teamRemove("buri");
		Rule.team.teamCreate("buri");
		Rule.team.getTeam("buri").setTeamColor("6");
		Rule.team.getTeam("buri").setTeamName("buris");
	}
	
	
	@Override
	public void firstTick() {
		for(Player pl :Rule.c.keySet()) {
			AdvManager.set(pl, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode3"));
			AdvManager.set(pl, 388, 0,  Main.GetText("main:msg32"));
		}
		Player p = ARSystem.RandomPlayer();
		Rule.c.put(p, new c100001(p, Rule.gamerule, null));
	}
	
	public void tick(int time) {
		if(time == 180) {
			int i = 0;
			int br = 0;
			String st = "";
			for(Player play :Rule.c.keySet()) {
				if(Rule.c.get(play).getCode() != 100001) {
					st += play.getName() + " ";
					i++;
				} else {
					br++;
				}
			}
			
			for(Player play :Rule.c.keySet()) {
				if(Rule.c.get(play).getCode() != 100001) {
					Rule.playerinfo.get(play).addcradit((3*br),Main.GetText("main:msg103"));
					play.sendTitle(" Win ", Main.GetText("main:msg41"),40,20,40);
				} else {
					play.sendTitle(" Lose ", Main.GetText("main:msg43"),40,20,40);
				}
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, new Runnable() {
				public void run() {
					ARSystem.GameStop();
				}
			}, 40);
			
		}
	}
	
}
