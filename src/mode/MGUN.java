package mode;

import org.bukkit.entity.Player;
import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import util.AMath;
import util.GetChar;
import util.Map;

public class MGUN {
	public static void tick(int time) {
		if(time == 0) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode7"));
				int nb = Rule.c.get(p).getCode();
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
