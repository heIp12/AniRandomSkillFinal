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
import ars.gui.G_Item;
import ars.gui.G_ItemShop;
import ars.gui.G_RareShop;
import buff.MapVoid;
import buff.Nodamage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c001humen2;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.MapType;

import util.AMath;
import util.Map;
import util.NpcPlayer;
import util.Pair;
import util.Text;
import util.ULocal;

public class MItem extends ModeBase{
	static float time = 0;
	static String code = "";
	static String code2 = "";
	
	public MItem(){
		super();
		modeName = "item";
		disPlayName = Text.get("main:mode15");
	}
	@Override
	public void option() {
		for(Player p : Rule.c.keySet()) AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode15"));
		G_ItemShop.items.clear();
		G_ItemShop.sold.clear();
		super.option();
	}

	@Override
	public void firstTick() {
		if(getBool("8")) {
			for(Player p : Rule.c.keySet()) {
				new G_Item(p,getInt("9"),getInt("10"),getInt("11"));
			}
		}
		rt = getInt("15")*-1;
		Player p = NpcPlayer.npc(Map.getCenter());
		for(int i = 1; i<=5; i++) {
			p.performCommand("as despawn shop"+i);
		}
		
		if(getBool("16")) {
			String s = get("17");
			try {
				for(String st : s.split(",")) {
					int i = Integer.parseInt(st);
					for(Player pl : Rule.c.keySet()) {
						ARSystem.addItem(pl, i);
					}
				}
			} catch(NumberFormatException e) {
				Bukkit.broadcastMessage("§a§l[ARSystem] §f Item Surch Error");
			}
		}
	}
	int rt = -10;
	@Override
	public void tick(int time) {
		if(getBool("14")) {
			rt++;
			if(AMath.random(500) < rt) {
				Player p = ARSystem.RandomPlayer();
				for(Player player : Rule.c.keySet()) {
					if(Rule.c.get(player).number == 153 && Rule.c.get(player).cooldown[0] <= 0 && AMath.random(10) <= 2) {
						Rule.c.get(player).spskillen();
						Rule.c.get(player).cooldown[0] = Rule.c.get(player).setcooldown[0];
						ARSystem.playSoundAll("c153sp");
						p = player;
						break;
					}
				}
				repCode2();	
				Location loc = ULocal.offset(p.getLocation().clone(),new Vector(1,0,0));
				ARSystem.spellLocCast(NpcPlayer.npc(Map.getCenter()),loc, "rareshop");
				Bukkit.broadcastMessage("§a§l[ARSystem] §a" +p.getName() +"§f"+Text.get("main:mode15-3"));
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
					spawn("rare",loc.add(0,1,0));
				},100);
				rt = getInt("15")*-1;
			}
		}
		if(getBool("1")) {
			if(time == getInt("2")) {
				Bukkit.broadcastMessage("§a§l[ARSystem] §f" +Text.get("main:mode15-2"));
				G_ItemShop.items.clear();
				G_ItemShop.sold.clear();
				repCode();
				for(int i =1 ;i<= getInt("3"); i++) {
					Location lc = Map.randomLoc();
					lc.setYaw(AMath.random(8)*45);
					if(i == 1) {
						Location lcc = Map.getCenter();
						lcc.setY(Map.loc_f.getY());
						for(int o =0; o<500; o++) {
							if(lcc.distance(lc) <= 30+(0.05*o)) {
								break;
							} else {
								lc = Map.randomLoc();
							}
						}
					}
					spawn("shop"+i,lc);
				}
			}
			if(time>1 && getBool("12") && time%getInt("13") == 0) {
				Location f = Map.loc_f.clone();
				Location l = Map.loc_l.clone();
				int rd = AMath.random(4);
				Location lc = Map.getCenter();
				
				if(rd == 1 || rd == 3) {
					lc.setX(AMath.random(f.getBlockX()+10000,l.getBlockX()+10000)-10000);
					if(rd == 1) lc.setZ(f.getZ());
					if(rd == 3) lc.setZ(l.getZ());
				}
				else if(rd == 2 || rd == 4) {
					lc.setZ(AMath.random(f.getBlockZ()+10000,l.getBlockZ()+10000)-10000);
					if(rd == 2) lc.setX(f.getX());
					if(rd == 4) lc.setX(l.getX());
				}
				lc.setY(Map.getCenter().getY());
				lc = ULocal.lookAt(lc, Map.getCenter());
				lc.setY(l.getY()-5);
				lc.setYaw(lc.getYaw() + AMath.random(50)-25);
				
				int i = AMath.random(3);
				if(time > 60 && i < 2) i = AMath.random(3);
				if(time > 120 && i < 3) i = 1+AMath.random(2);
				f.setY(0);
				l.setY(0);
				ARSystem.playSoundAll("itemfly");
				if(f.distance(l) > 250) {
					ARSystem.spellLocCast(NpcPlayer.npc(lc), lc, "coinfly"+(i*10+1));
				} else if(f.distance(l) > 50){
					ARSystem.spellLocCast(NpcPlayer.npc(lc), lc, "coinfly"+i);
				} else {
					ARSystem.spellLocCast(NpcPlayer.npc(lc), lc, "coinfly0");
				}
			}
		}
	}
	
	public static void spawn(String name, Location loc) {
		Player p = NpcPlayer.npc(Map.getCenter());
		p.performCommand("as despawn "+name);
		p.performCommand("as setloc "+name+" "+(loc.getX()+0.5)+","+(loc.getY()-1)+","+(loc.getZ()+0.5)+","+loc.getYaw());
		p.performCommand("as spawn "+name);
		p.performCommand("as setloc "+name+" "+(loc.getX()+0.5)+","+(loc.getY()-1)+","+(loc.getZ()+0.5)+","+loc.getYaw());
	}
	
	public static void repCode() {
		Player p = NpcPlayer.npc(Map.getCenter());
		code = "＄" + AMath.random(9) + "" + (char)('A'+AMath.random(50))+ "" + AMath.random(9)+ "" + AMath.random(9);
		for(int i =1; i<=6; i++) p.performCommand("as cmd shop"+i+" ars shop "+code);
	}

	public static void repCode2() {
		Player p = NpcPlayer.npc(Map.getCenter());
		code2 = "＄" + AMath.random(9) + "" + (char)('A'+AMath.random(50))+ "" + AMath.random(9)+ "" + AMath.random(9);
		for(int i =1; i<=6; i++) p.performCommand("as cmd rare ars shop "+code2);
	}
	
	public static void isCode(String s,Player p) {
		if(s.equals(code)) {
			repCode();
			ARSystem.playSound(p,"itemopen"+AMath.random(2));
			new G_ItemShop(p);
		} else if(s.equals(code2)){
			repCode2();
			NpcPlayer.npc(Map.getCenter()).performCommand("as despawn rare");
			new G_RareShop(p);
		} else {
			Skill.quit(p);
		}
	}
	
	@Override
	public void end() {
		Player p = NpcPlayer.npc(Map.getCenter());
		for(int i = 1; i<=5; i++) {
			p.performCommand("as despawn shop"+i);
		}
		p.performCommand("as despawn rare");
	}
}
