package manager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import util.AMath;
import util.Text;

public class Bgm {
	private static BossBar bgmbar;
	private static String bgmName = "";
	public static String bgmcode = "";
	public static String bgmNameCode = "";
	private static int bgmTime = 0;
	private static int bgmCount = -1;
	private static int bgmFrstTime = 0;
	private static int[] bgmlist = new int[10];
	public static boolean bgmlock = false;
	
	public static boolean rep = true;
	
    public static boolean cbgm = true;
	

	public static ArrayList<String> nextbgm = new ArrayList<String>();
	
	
	static public void tick() {
		if(bgmTime <= 0) {
			bgmlock = false;
			if(nextbgm.size() <= 0) {
				randomBgm();
			} else {
				setBgm(nextbgm.get(0));
				nextbgm.remove(0);
			}
			Rule.saveRank();
			Rule.Var.saveAll();
		} else {
			garaoke();
			bgmTime--;
			if(bgmTime%5 == 0) {
				bgmbar.setProgress((double)bgmTime/(double)bgmFrstTime);
			}
		}
	}
	
	public static void randomBgm() {
		if(bgmCount == -1) { bgmCount = Integer.parseInt(Main.GetText("bgm:bgmcount")); };
		
		LocalTime now = LocalTime.now();
		if(now.getHour() == 1 && now.getMinute() >= 1 && now.getMinute() <= 3 && AMath.random(33) == 1) {
			setForceBgm("yareyare");
			return;
		}

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
			bgmbar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID);
		}
		
		bgmName = Main.GetText("bgm:"+name);
		bgmcode = Main.GetText("bgm:"+name+ "n");
		if(bgmcode == null) bgmcode = name;
		bgmNameCode = name;
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
	public static void setBgm(String name, float f) {
		if(!bgmlock) {
			float pc = Math.min(Math.max(f,0.5f),2);
			getBgm(name);
			bgmTime = (int) (Integer.parseInt(Main.GetText("bgm:"+name+ "t"))/pc);
			bgmFrstTime = bgmTime;
			if(f != 1) {
				bgmbar.setTitle("§a§l~ ♪ "+bgmName+" x"+AMath.round(f,1)+" ♬ ~");
			}
			for(Player p :Bukkit.getOnlinePlayers()) {
				p.stopSound("",SoundCategory.VOICE);
				p.playSound(p.getLocation(), bgmcode,SoundCategory.VOICE, 10000, f);
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
	
	static List<Integer> otime = new ArrayList<>();
	public static void garaoke() {
		if(Text.get("bgm:"+bgmcode+"_o1") == null) return;
		
		if(bgmTime == bgmFrstTime) {
			bgmFrstTime+=1;
			otime.clear();
			int i = 1;
			String s = Text.get("bgm:"+bgmcode+"_ot"+i).replace(" ", "");
			int lastTime = 10000000;
			
			while(s != null) {
				String[] sp = s.split(",");
				int c = 0;
				for(String t : sp) {
					if(t.contains("x")) {
						String t2[] = t.split("x");
						for(int j = 0; j < Integer.parseInt(t2[1]); j++) {
							c++;
							int timer = Integer.parseInt(t2[0]);
							otime.add(lastTime+timer + c*100000);
							lastTime+=timer;
						}
					} else {
						if(t.length() > 0) {
							c++;
							int timer = Integer.parseInt(t);
							otime.add(lastTime+timer+c*100000);
							lastTime+= timer;
						}
					}
				}
				lastTime+= 10000000;
				i++;
				s = Text.get("bgm:"+bgmcode+"_ot"+i);
			}
		}
		
		int time = bgmFrstTime - bgmTime;
		int code = 0;
		for(int i : otime) {
			if(i%100000 < time%100000) {
				code = i;
			} else {
				break;
			}
		}
		int number = code/10000000;
		int ns = code%10000000/100000;
		
		String t1 = "";
		t1 = Text.get("bgm:"+bgmcode+"_o"+number);
		if(t1 != null) {
			String t2 = "";
			t2 = Text.get("bgm:"+bgmcode+"_o"+(number+1));
			
			if(t2 == null) t2 = "";
			String color = "a";
			if(t1.contains("§") || t1.contains("&")) {
				color = t1.substring(1,2);
				t1 = t1.substring(2);
			}
			if(t2.contains("§") || t2.contains("&")) t2 = t2.substring(2);
			ns = Math.min(ns,t1.length());
			
			t1 = "§"+color + t1.substring(0,ns) +"§7"+ t1.substring(ns,t1.length());
			
			for(int i = 0; i <100; i++) {
				if(t2.contains("§"))t2 = t2.substring(0,t2.indexOf("§")) + t2.substring(t2.indexOf("§")+2,t2.length());
				else break;
			}
			t2 = "§7" + t2;
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(!(Rule.c.get(p) != null && ARSystem.AniRandomSkill != null && p.getGameMode() != GameMode.SPECTATOR)) {
					p.sendTitle(t1, t2,0,100,0);
				}
			}
		}
	}
}
