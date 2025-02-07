package mode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSinfo;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Supply;
import buff.Nodamage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.MapType;

import util.AMath;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class MEvent extends ModeBase{
	static public String name = "";
	
	public MEvent(){
		super();
		modeName = "event";
		disPlayName = Text.get("main:mode16");
		isSecret = true;
	}
	
	@Override
	public void option() {
		name = get("1");
	}
	
	@Override
	public void firstTick() {
		for(Player p : Rule.c.keySet()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode16"));
		}
	}
	
	public void tick(int time) {
		if(time%getInt("2") == 0 && time != 0) {
			for(Player p : Rule.c.keySet()) {
				ARSystem.addItem(p, 100014);
			}
		}
	}

	@Override
	public void PlayerDeathEvent(Player p, Entity killer) {
		if(getBool("3") && Rule.c.get(killer) != null) {
			ARSystem.addItem(p, 100014);
		}
	}
}
