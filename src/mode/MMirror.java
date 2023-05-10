package mode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import util.AMath;
import util.GetChar;
import util.Map;
import util.Text;

public class MMirror extends ModeBase{
	
	public MMirror(){
		super();
		modeName = "mirror";
		disPlayName = Text.get("main:mode2");
	}
	
	@Override
	public void option() {
		int i = AMath.random(GetChar.getCount());
		while(!GetChar.isBan(i)) {
			i = AMath.random(GetChar.getCount());
		}
		if(ARSystem.serverOne > 0) i = ARSystem.serverOne;
		
		for(Player p : ARSystem.getReadyPlayer()) {
			Rule.c.put(p,GetChar.get(p, Rule.gamerule, ""+i));
		}
	}
	@Override
	public void firstTick() {
		for(Player p : Rule.c.keySet()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode2"));
		}
	}
}
