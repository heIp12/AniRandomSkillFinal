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

public class MHeal extends ModeBase{
	Location loc;
	boolean spawn = false;
	String code = "";
	
	public MHeal(){
		super();
		modeName = "heal";
		disPlayName = Text.get("main:mode13");
	}
	
	@Override
	public void option() {
		ARSystem.opCommand("as despawn heal2");
	}
	@Override
	public void firstTick() {
		for(Player p : Rule.c.keySet()) {
			AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode13"));
		}
		loc = Map.randomLoc();
		
		Player p = ARSystem.RandomOnlinePlayer();
		boolean isop = p.isOp();
		p.setOp(true);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
			p.performCommand("as despawn heal");
			p.performCommand("as setloc heal "+(loc.getX()+0.5)+","+(loc.getY()-1.4)+","+(loc.getZ()+0.5));
			p.performCommand("as spawn heal");
			p.performCommand("as setloc heal "+(loc.getX()+0.5)+","+(loc.getY()-1.4)+","+(loc.getZ()+0.5));
			if(!isop) p.setOp(false);
		},5);
	}
	public void tick(int time) {
		if(loc != null) {
			for(int i =0; i<5;i++) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
					if(spawn) {
						for(Player p : Rule.c.keySet()) {
							if(p.getLocation().distance(loc.clone().add(0,1,0)) < 1.6) {
								if(p.getHealth() < p.getMaxHealth()) {
									spawn = false;
									ARSystem.spellLocCast(p, loc, "heal");
									ARSystem.playSound((Entity)p, "0heal");
									Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ 
										p.performCommand("as despawn heal2");
									});
									ARSystem.heal(p, Math.max(5, (p.getMaxHealth() - p.getHealth())*0.4));
									
								}
							}
						}
					}
				},4*i);
			}
		}
		if(loc != null &&time > 10 &&(time+11)%25 == 0) {
			if(!Map.inMap(loc)) {
				loc = Map.randomLoc();
			}

			
			if(!spawn) {
				Player p = ARSystem.RandomOnlinePlayer();
				boolean isop = p.isOp();
				p.setOp(true);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ 
					p.performCommand("as despawn heal2");
				});
				if(!isop) p.setOp(false);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
					p.setOp(true);
					p.performCommand("as setloc heal2 "+(loc.getX()+0.5)+","+(loc.getY()+0.5)+","+(loc.getZ()+0.5));
					p.performCommand("as spawn heal2");
					p.performCommand("as setloc heal2 "+(loc.getX()+0.5)+","+(loc.getY()+0.5)+","+(loc.getZ()+0.5));
					loc.getWorld().playSound(loc, "0healspawn", 1, 1);
					spawn = true;
					if(!isop) p.setOp(false);
				},10);
			}
		}
	}
	
	@Override
	public void end() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ ARSystem.opCommand("as despawn heal");});
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ ARSystem.opCommand("as despawn heal");});
	}

}
