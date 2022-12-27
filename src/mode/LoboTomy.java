package mode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import manager.Bgm;
import util.AMath;
import util.MSUtil;
import util.Map;

public class LoboTomy {
	public static int count = 0;
	
	public static void tick(int time) {
		String s = Bgm.bgmcode;

		if(count < 1 && !s.equals("m1")) {
			Bgm.setBgm("m1");
		} else if(count>=1 &&count < 6 && !s.equals("m2")) {
			Bgm.setBgm("m2");
		} else if(count>=6 &&count < 12 && !s.equals("m3")) {
			Bgm.setBgm("m3");
		} else if(count == 12 && !s.equals("m4")) {
			Bgm.setBgm("m4");
		}
		
		if(time >= 5 && time%20 == 0) {
			if(time%100 == 0) {
				for(Player pl : Bukkit.getOnlinePlayers()) {
					AdvManager.set(pl, 267, 0,  Main.GetText("main:msg2") +" 환상체 : "+ mobcount +" 마리");
				}
			}
			if(!MapMob()) {
				for(Player p : Rule.c.keySet()) {
					ARSystem.heal(p, 1000);
				}
				count++;
				for(Player pl :Rule.c.keySet()) {
					AdvManager.set(pl, 267, 0,  Main.GetText("main:msg2") +" "+ count +" Wave");
				}
				if(count == 1) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb2/main:lb1");
					Map.spawn("food", 3 + Rule.c.size()/2);
					mobcount = 999;
				}
				if(count == 2) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
					Map.spawn("bard", 3+ Rule.c.size()/2);
					for(Player pl : Rule.c.keySet()) {
						Rule.playerinfo.get(pl).tropy(0 , 32);
					}
					mobcount = 999;
				}
				if(count == 3) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
					Map.spawn("bard", Rule.c.size());
					Map.spawn("c", Rule.c.size());
					mobcount = 999;
				}
				if(count == 4) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
					Map.spawn("bard",2+ Rule.c.size());
					Map.spawn("c", 3+ Rule.c.size());
					Map.spawn("sn", 3);
					mobcount = 999;
				}
				if(count == 5) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb4/main:lb3");
					Map.spawn("food2",3);
					Map.spawn("food", 3 + Rule.c.size()/2);
					mobcount = 999;
				}
				if(count == 6) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
					Map.spawn("dango",1 + Rule.c.size()/5);
					Map.spawn("c", 3);
					Map.spawn("sn", Rule.c.size());
					for(Player pl : Rule.c.keySet()) {
						Rule.playerinfo.get(pl).tropy(0 , 33);
					}
					mobcount = 999;
				}
				if(count == 7) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb4/main:lb3");
					Map.spawn("food2",5 + Rule.c.size()/3);
					Map.spawn("food", 3 + Rule.c.size()*2);
					mobcount = 999;
				}
				if(count == 8) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
					Map.spawn("co",2);
					Map.spawn("c", 3+ Rule.c.size()/2);
					Map.spawn("bard",2+ Rule.c.size());
					mobcount = 999;
				}
				if(count == 9) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
					Map.spawn("sn", Rule.c.size());
					Map.spawn("co", 3);
					Map.spawn("dango",4 + Rule.c.size()/3);
					Map.spawn("c", 5+ Rule.c.size()/2);
					Map.spawn("bard",5+ Rule.c.size());
					Map.spawn("food", 5 + Rule.c.size()*2);
					mobcount = 999;
				}
				if(count == 10) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
					Map.spawn("dango",8);
					Map.spawn("c", 8+ Rule.c.size());
					Map.spawn("sn", Rule.c.size()*2);
					Map.spawn("co", 3);
					Map.spawn("bard",8+ Rule.c.size());
					Map.spawn("food2", 3 + Rule.c.size()/2);
					for(Player pl : Rule.c.keySet()) {
						Rule.playerinfo.get(pl).tropy(0 , 34);
						mobcount = 999;
					}
				}
				if(count == 11) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
					Map.spawn("dango",12);
					Map.spawn("co", 2 + Rule.c.size()/2);
					Map.spawn("c", 8+ Rule.c.size());
					Map.spawn("bard",12+ Rule.c.size());
					Map.spawn("food2", 3 + Rule.c.size()*2);
					mobcount = 999;
				}
				if(count == 12) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb8/main:lb9");
					Map.spawn("whitenight",1);
					Map.spawn("sado", 11);
					mobcount = 999;
				}
			}
			if(count == 12 && time%60 == 0 && wn()) {
				ARSystem.playSoundAll("by1");
				Map.spawn("sado", 3 + Rule.c.size()/3);
				Map.spawn("sado3", 1 + Rule.c.size()/5);
				for(int i =0; i< AMath.random(5); i++) {
					if(AMath.random(13) == 2) Map.spawn("co",1);
					if(AMath.random(3) == 2) Map.spawn("sn",1);
					if(AMath.random(10) == 2) Map.spawn("dango",1);
					if(AMath.random(5) == 2) Map.spawn("c",1);
					if(AMath.random(3) == 2) Map.spawn("bard",1);
					if(AMath.random(8) == 2) Map.spawn("food2",1);
					if(AMath.random(2) == 2) Map.spawn("food",1);
				}
				for(Player p : Rule.c.keySet()) {
					p.damage(5);
				}
			}
			if(count == 13) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.sendTitle(Main.GetText("Main:mode666"),""+Main.GetText("Main:lb11") ,40,20,40);
					MSUtil.resetbuff(player);
					player.setGameMode(GameMode.ADVENTURE);
					player.setMaxHealth(20);
					player.setHealth(20);
				}
				for(Player player : Rule.c.keySet()) {
					Rule.playerinfo.get(player).tropy(0 , 35);
				}
				ARSystem.gameEnd();
			}
		}
	}
	

	static int mobcount = 0;
	public static boolean MapMob() {
		boolean is = false;
		int count = 0;
		for(LivingEntity e : Bukkit.getWorld("world").getLivingEntities()) {
			if(e instanceof Husk || e instanceof Pig) {
				is = true;
				count++;
			}
		}
		mobcount = count;
		return is;
	}
	public static boolean wn() {
		boolean is = false;
		for(LivingEntity e : Bukkit.getWorld("world").getLivingEntities()) {
			if(e.getCustomName() != null && e.getCustomName().equals("whitenight")) {
				System.out.println("Wn");
				is = true;
				break;
			}
		}
		
		return is;
	}
}
