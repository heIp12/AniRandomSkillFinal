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

public class MAdv extends ModeBase{
	
	public MAdv(){
		super();
		modeName = "adv";
		disPlayName = Text.get("main:mode9");
	}
	
	@Override
	public void option() {
		for(Player p : ARSystem.getReadyPlayer()) {
			if(Rule.pick && GetChar.advList().contains(Rule.playerinfo.get(p).playerc)) {
				Rule.c.put(p,GetChar.getAdv(p, Rule.gamerule, ""+ Rule.playerinfo.get(p).playerc, null));
			} else {
				int i = GetChar.advList().get(AMath.random(GetChar.advList().size()-1));
				Rule.c.put(p,GetChar.getAdv(p, Rule.gamerule, ""+i , null));
			}
		}
	}
	
	@Override
	public void firstTick() {
		for(Player p : Rule.c.keySet()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode9"));
		}

	}
}
