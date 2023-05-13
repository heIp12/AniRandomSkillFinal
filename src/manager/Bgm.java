package manager;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import Main.Main;
import ars.Rule;
import util.AMath;

public class Bgm {
	private static BossBar bgmbar;
	private static String bgmName = "";
	public static String bgmcode = "";
	private static double bgmTime = 0;
	private static int bgmCount = -1;
	private static double bgmFrstTime = 0;
	private static int[] bgmlist = new int[10];
	public static boolean bgmlock = false;
	
	public static boolean rep = true;
	
	static public void tick() {
		if(bgmTime <= 0) {
			bgmlock = false;
			randomBgm();
			Rule.saveRank();
			Rule.Var.saveAll();
		} else {
			bgmTime--;
			if(bgmTime%5 == 0) {
				bgmbar.setProgress(bgmTime/bgmFrstTime);
				if(!bgmbar.getTitle().equals("§a§l~ ♪ "+bgmName+" ♬ ~")) {
					bgmbar.setTitle("§a§l~ ♪ "+bgmName+" ♬ ~");
				}
			}
		}
	}
	
	public static void randomBgm() {
		if(bgmCount == -1) { bgmCount = Integer.parseInt(Main.GetText("bgm:bgmcount")); };
		int i = 0;
		i = AMath.random(bgmCount);
		while(true) {
			boolean play = true;
			
			for(int name : bgmlist) {
				if(i == name) {
					play = false;
				}
			}
			if(play) {
				for(int j=1;j<bgmlist.length;j++) {
					bgmlist[j-1] = bgmlist[j];
				}
				bgmlist[bgmlist.length-1] = i;
				break;
			}
			i = AMath.random(bgmCount);
		}
		getBgm("game"+i);
		for(Player p :Bukkit.getOnlinePlayers()) {
			p.stopSound("",SoundCategory.VOICE);
			p.playSound(p.getLocation(), bgmcode,SoundCategory.VOICE, 10000, 1);
		}
	}
	public static void Exit() {
		bgmbar.removeAll();
	}
	public static void getBgm(String name) {
		if(bgmbar==null) {
			bgmbar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID, BarFlag.CREATE_FOG);
		}
		
		bgmName = Main.GetText("bgm:"+name);
		bgmcode = Main.GetText("bgm:"+name+ "n");
		if(bgmcode == null) bgmcode = name;
		bgmTime = Integer.parseInt(Main.GetText("bgm:"+name+ "t"));
		bgmFrstTime = bgmTime;
		
		bgmbar.setTitle("§a§l~ ♪ "+bgmName+" ♬ ~");
		
		for(Player p :Bukkit.getOnlinePlayers()) {
			bgmbar.addPlayer(p);
		}
	}
	
	public static void setBgm(String name) {
		if(!bgmlock) {
			getBgm(name);
			for(Player p :Bukkit.getOnlinePlayers()) {
				p.stopSound("",SoundCategory.VOICE);
				p.playSound(p.getLocation(), bgmcode,SoundCategory.VOICE, 10000, 1);
			}
		}
	}
	public static void setlockBgm(String name) {
		if(!bgmlock) {
			bgmlock = true;
			getBgm(name);
			for(Player p :Bukkit.getOnlinePlayers()) {
				p.stopSound("",SoundCategory.VOICE);
				p.playSound(p.getLocation(), bgmcode,SoundCategory.VOICE, 10000, 1);
			}
		}
	}
	public static void setForceBgm(String name) {
		bgmlock = true;
		getBgm(name);
		for(Player p :Bukkit.getOnlinePlayers()) {
			p.stopSound("",SoundCategory.VOICE);
			p.playSound(p.getLocation(), bgmcode,SoundCategory.VOICE, 10000, 1);
		}
	}
	
	public static double getTime() {
		return bgmTime*0.05;
	}
	public static double getPlayTime() {
		return (bgmFrstTime-bgmTime)*0.05;
	}
	public static void setTime(int tick) {
		bgmTime = tick;
	}
}
