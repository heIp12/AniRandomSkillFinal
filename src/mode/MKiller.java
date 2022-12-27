package mode;

import org.bukkit.entity.Player;
import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import util.AMath;
import util.GetChar;
import util.Map;

public class MKiller {
	public static void tick(int time) {
		if(time == 0) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode8"));
				int nb = Rule.c.get(p).getCode();
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
		if(time%10 == 0) {
			Map.sizeM(7);
		}
	}
}
