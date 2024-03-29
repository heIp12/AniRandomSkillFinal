package mode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import types.MapType;
import util.GetChar;
import util.Map;
import util.Text;

public class MKanna extends ModeBase{
	
	public MKanna(){
		super();
		modeName = "kanna";
		disPlayName = Text.get("main:mode6");
		isOnlyOne = true;
	}
	
	@Override
	public void option() {
		Map.mapType = MapType.NORMAL;
		Map.getMapinfo(1005);
		Rule.team.teamCreate("K");
		Rule.team.getTeam("K").setTeamColor("7");
		for(Player p : ARSystem.getReadyPlayer()) {
			Rule.team.teamJoin("K", p);
			Rule.c.put(p,GetChar.get(p, Rule.gamerule, "98"));
		}
	}

	public void tick(int time) {
		if(time == 0) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode6"));
			}
		}
		if(!isBoom() && time > 0) {
			Map.spawn("boom", Map.getCenter(), 1);
			if(time > 45) {
				Map.spawn("boom", time/45);
			}
		}
		
	}
	public static boolean isBoom() {
		for(LivingEntity e : Bukkit.getWorld("world").getLivingEntities()) {
			if(e instanceof Creeper && e.getCustomName() != null && e.getCustomName().equals("Boom")) {
				return true;
			}
		}
		return false;
	}
}
