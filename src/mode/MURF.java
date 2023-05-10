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

public class MURF extends ModeBase{
	
	public MURF(){
		super();
		modeName = "urf";
		disPlayName = Text.get("main:msg3");
	}
	
	@Override
	public void firstTick() {
		ARSystem.playSoundAll("0select2");
		for(Player p :Rule.c.keySet()) {
			double cool = AMath.random(40) * 0.05;
			cool = Math.round(cool*100)/100.0;
			Rule.c.get(p).sskillmult+=cool;
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg3") +" +"+(cool*100)+"% ");
		}
	}
}
