package mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import util.Pair;
import util.Text;

public class MHeal extends ModeBase{
	Location loc;
	boolean spawn = false;
	List<Pair<Location, Boolean>> heal = new ArrayList<>();
	
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
		heal.clear();
		for(int i = 0; i< getInt("2");i++) {
			heal.add(new Pair<>(Map.randomLoc(),false));
		}
		
		Player p = NpcPlayer.npc(loc);
		int i = 0;
		for(Pair<Location,Boolean> pr : heal) {
			i++;
			int n = i;
			Location loc = pr.getKey();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				p.performCommand("as despawn heal"+n);
				p.performCommand("as setloc heal"+n+" "+(loc.getX()+0.5)+","+(loc.getY()-1.4)+","+(loc.getZ()+0.5));
				p.performCommand("as spawn heal"+n);
				p.performCommand("as setloc heal"+n+" "+(loc.getX()+0.5)+","+(loc.getY()-1.4)+","+(loc.getZ()+0.5));
			},5);
		}
	}
	
	public void tick(int time) {
		if(heal.size() > 0) {
			for(int i =0; i<5;i++) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
					int j = 0;
					for(Pair<Location,Boolean> pr : heal) {
						j++;
						int n = j;
						Location loc = pr.getKey();
						if(pr.getValue()) {
							for(Player p : Rule.c.keySet()) {
								if(p.getLocation().distance(loc.clone().add(0,1,0)) < 1.6) {
									if(p.getHealth() < p.getMaxHealth()) {
										pr.setValue(false);
										ARSystem.spellLocCast(p, loc, "heal");
										ARSystem.playSound((Entity)p, "0heal");
										Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ 
											p.performCommand("as despawn heal"+n+"-2");
										});
										ARSystem.heal(p, Math.max(getInt("4"), (p.getMaxHealth() - p.getHealth())*0.01*getInt("3")));
										
									}
								}
							}
						}
					}
				},4*i);
			}
		}
		if(heal.size() > 0 && time > 10 &&(time+11)%getInt("1") == 0) {
			int i = 0;
			for(Pair<Location,Boolean> pr : heal) {
				i++;
				if(getBool("5") && !Map.inMap(pr.getKey())) {
					pr.setKey(Map.randomLoc());
				}
				if(!pr.getValue()) {
					Location loc = pr.getKey();
					Player p = NpcPlayer.npc(loc);
					p.setOp(true);

					int n = i;
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ 
						p.performCommand("as despawn heal"+n+"-2");
					});
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
						p.setOp(true);
						p.performCommand("as setloc heal"+n+"-2 "+(loc.getX()+0.5)+","+(loc.getY()+0.5)+","+(loc.getZ()+0.5));
						p.performCommand("as spawn heal"+n+"-2");
						p.performCommand("as setloc heal"+n+"-2 "+(loc.getX()+0.5)+","+(loc.getY()+0.5)+","+(loc.getZ()+0.5));
						loc.getWorld().playSound(loc, "0healspawn", 1, 1);
						pr.setValue(true);
					},10);
				}
			}
		}
	}
	
	@Override
	public void end() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{ 
			for(int i = 0; i < heal.size(); i++) {
				ARSystem.opCommand("as despawn heal"+(i+1));
			}
		});
	}

}
