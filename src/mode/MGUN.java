package mode;

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

public class MGUN extends ModeBase{
	
	public MGUN(){
		super();
		modeName = "gun";
		disPlayName = Text.get("main:mode7");
		isOne = true;
	}
	@Override
	public void option() {
		Map.mapType = MapType.BIG;
		Map.getMapinfo(107);
		for(Player p : ARSystem.getReadyPlayer()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode7"));
			int nb = 18;
			int cd = Integer.parseInt(Main.GetText("c"+nb+":type").replace(" tp",""));
			if(cd != 3) {
				while(cd != 3) {
					nb = AMath.random(GetChar.getCount());
					cd = Integer.parseInt(Main.GetText("c"+nb+":type").replace(" tp",""));
				}
				Rule.c.put(p, GetChar.get(p,Rule.gamerule, ""+nb));
			}
		}
	}
	
	public void tick(int time) {
		if(time%15 == 0) {
			Map.sizeM(7);
		}
		if(time%50 == 0) {
			for(Player p : Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg10"));
				ARSystem.potion(p, 24, 100, 1);
			}
		}
	}
}
