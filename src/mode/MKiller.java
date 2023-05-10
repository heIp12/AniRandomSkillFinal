package mode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import types.MapType;
import util.AMath;
import util.GetChar;
import util.Map;
import util.Text;

public class MKiller extends ModeBase{
	
	public MKiller(){
		super();
		modeName = "killer";
		disPlayName = Text.get("main:mode8");
		isOne = true;
	}
	
	@Override
	public void option() {
		Map.mapType = MapType.BIG;
		Map.getMapinfo(102);
		Bukkit.getWorld("world").setTime(18000);
		
		for(Player p : ARSystem.getReadyPlayer()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode8"));
			int nb = 18;
			int cd = Integer.parseInt(Main.GetText("c"+nb+":type").replace(" tp",""));
			if(cd != 2) {
				while(cd != 2) {
					nb = AMath.random(GetChar.getCount());
					cd = Integer.parseInt(Main.GetText("c"+nb+":type").replace(" tp",""));
				}
				Rule.c.put(p, GetChar.get(p,Rule.gamerule, ""+nb));
			}
		}
	}
	
	
	public void tick(int time) {
		if(time%10 == 0) {
			Map.sizeM(7);
		}
	}
}
