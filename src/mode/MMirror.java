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
		String code = get("1");
		
		if(code.equals("0")) {
			int i = AMath.random(GetChar.getCount());
			while(!GetChar.isBan(i)) {
				i = AMath.random(GetChar.getCount());
			}
			code = ""+i;
			for(Player p : ARSystem.getReadyPlayer()) {
				Rule.c.put(p,GetChar.get(p, Rule.gamerule, ""+code));
			}
		} else {
			for(Player p : ARSystem.getReadyPlayer()) {
				Rule.c.put(p,GetChar.get(p, Rule.gamerule, ""+code));
			}
		}
		
	}
	@Override
	public void firstTick() {
		for(Player p : Rule.c.keySet()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode2"));
		}
	}
}
