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

public class MSupply extends ModeBase{
	Location loc;
	String code = "";
	
	public MSupply(){
		super();
		modeName = "supply";
		disPlayName = Text.get("main:mode10");
	}
	
	@Override
	public void option() {
		ARSystem.opCommand("as despawn ItemBox");
	}
	@Override
	public void firstTick() {
		for(Player p : Rule.c.keySet()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode10"));
		}
	}
	public void tick(int time) {
		if((time+1)%50 == 0) {
			loc = Map.randomLoc();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ ARSystem.opCommand("as despawn ItemBox");});
			Bukkit.broadcastMessage("§a§l[ARSystem] §f"+ Text.get("main:mode10-1") + " §c["+loc.getBlockX()+ ","+loc.getBlockY()+ ","+loc.getBlockZ()+"]");
			randomCode();
		}
		
		if(loc != null &&time > 40 &&(time+41)%50 == 0) {
			if(!Map.inMap(loc)) {
				loc = Map.randomLoc();
			}
			Bukkit.broadcastMessage("§a§l[ARSystem] §f"+ Text.get("main:mode10-2") + " §c["+loc.getBlockX()+ ","+loc.getBlockY()+ ","+loc.getBlockZ()+"]");
			Player p = ARSystem.RandomOnlinePlayer();
			boolean isop = p.isOp();
			p.setOp(true);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ p.performCommand("as despawn ItemBox");});
			p.performCommand("as cmd ItemBox ars supply "+code);
			if(!isop) p.setOp(false);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				p.setOp(true);
				p.performCommand("as setloc ItemBox "+(loc.getX()+0.5)+","+(loc.getY()-1)+","+(loc.getZ()+0.5));
				p.performCommand("as spawn ItemBox");
				p.performCommand("as setloc ItemBox "+(loc.getX()+0.5)+","+(loc.getY()-1)+","+(loc.getZ()+0.5));
				if(!isop) p.setOp(false);
			},10);
		}
		
	}
	
	public void isSupply(Player p, String code) {
		if(code.equals(this.code) && Rule.c.get(p) != null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ ARSystem.opCommand("as despawn ItemBox");});
			ARSystem.giveBuff(p, new TimeStop(p), 200);
			randomCode();
			new G_Supply(p);
		} else {
			Skill.quit(p);
		}
	}
	
	void randomCode() {
		code = "★" + AMath.random(9) + "" + (char)('A'+AMath.random(50))+ "" + AMath.random(9)+ "" + AMath.random(9);
	}
	
	@Override
	public void end() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ ARSystem.opCommand("as despawn ItemBox");});
	}

}
