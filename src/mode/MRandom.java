package mode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import types.GameModes;
import util.AMath;
import util.GetChar;
import util.Map;
import util.Text;

public class MRandom extends ModeBase{
	
	public MRandom(){
		super();
		modeName = "random";
		disPlayName = Text.get("main:mode12");
		isOnlyOne = true;
	}
	
	@Override
	public void initialize() {
		ARSystem.redyMode("normal");
		if(AMath.random(100) <= 30) {
			while(true) {
				ModeBase mb = ((ModeBase)GameModes.getModList().toArray()[AMath.random(GameModes.getModList().size())-1]);
				String name = mb.modeName;
				if(!mb.IsSecret() && !name.equals("random")&& !name.equals("normal")) {
					ARSystem.redyMode(name);
					break;
				}
			}
		}
	}
	
	@Override
	public void end() {
		ARSystem.selectGameMode.clear();
		ARSystem.redyMode("random");
	}
}
