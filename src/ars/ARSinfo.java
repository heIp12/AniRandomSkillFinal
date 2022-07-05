package ars;


import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import Main.Main;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import c.c100001;
import manager.AdvManager;
import manager.Bgm;
import types.MapType;
import types.modes;
import util.AMath;
import util.MSUtil;
import util.Map;

public class ARSinfo {
	public int time = 0;
	public int player = 0;
	public float gamescore = 0;
	public boolean mob = false;
	
	public HashSet<Player> chageplayer = new HashSet<Player>();
	public HashMap<Player,Integer> startplayer = new HashMap<Player,Integer>();
	public HashMap<Player,Integer> playerkill = new HashMap<Player,Integer>();
	
	public int count = 0;
	
	ARSinfo(int timer){
		time-= timer;
	}
	
	public void start() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(Player pl : Bukkit.getOnlinePlayers()) {
				if(!Rule.team.isTeamAttack(pl, p)) {
					p.hidePlayer(pl);
				}
			}
		}
	}
	
	public void reset() {
		time = 0;
		
	}
	
	public void Time() {
		time++;
		if(time == 0) {
			for(Player p : Rule.c.keySet()) {
				startplayer.put(p,Rule.c.get(p).getCode());
			}
			if(AMath.random(100) <= Rule.urf && ARSystem.gameMode != modes.LOBOTOMY) {
				ARSystem.playSoundAll("0select2");
				for(Player p :Rule.c.keySet()) {
					double cool = AMath.random(40) * 0.05;
					cool = Math.round(cool*100)/100.0;
					Rule.c.get(p).sskillmult+=cool;
					AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg3") +" +"+(cool*100)+"% ");
				}
			}
			if(AMath.random(100) <= 2 && ARSystem.gameMode != modes.LOBOTOMY) {
				ARSystem.playSoundAll("0select2");
				mob = true;
				for(Player p :Rule.c.keySet()) {
					AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg53") +" Adv");
				}
			}
			if(ARSystem.gameMode == modes.ZOMBIE) {
				Rule.team.teamCreate("buri");
				Player p = ARSystem.RandomPlayer();
				Rule.c.put(p, new c100001(p, Rule.gamerule, null));
				for(Player pl :Rule.c.keySet()) {
					AdvManager.set(pl, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode3"));
					AdvManager.set(pl, 388, 0,  Main.GetText("main:msg32"));
				}
			}
			if(ARSystem.gameMode == modes.ONE) {
				for(Player pl :Rule.c.keySet()) {
					AdvManager.set(pl, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode2"));
				}
			}
			if(Rule.pick) {
				for(Player pl :Rule.c.keySet()) {
					AdvManager.set(pl, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode0"));
				}
			}
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				for(Player pl : Bukkit.getOnlinePlayers()) {
					p.showPlayer(pl);
				}
			}
		}
		if(mob) {
			for(int i=0; i<1+time/50; i++) {
				Location loc = Map.randomLoc();
				if(loc.getWorld().getLivingEntities().size() < 200) {
					Zombie en = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
					en.setMaxHealth(1+ time/3);
					ARSystem.potion(en, 5, 100000, 0+time/50);
					ARSystem.potion(en, 1, 100000, 0+time/30);
					ARSystem.potion(en, 12, 100000, 0);
				} else {
					for(LivingEntity e : loc.getWorld().getLivingEntities()) {
						if(e instanceof Player) {
							e.damage(1);
						} else {
							e.damage(5);
						}
					}
				}
			}
		}
		if(ARSystem.gameMode == modes.LOBOTOMY) {
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
						spawn("food", 3 + Rule.c.size()/2);
						mobcount = 999;
					}
					if(count == 2) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
						spawn("bard", 3+ Rule.c.size()/2);
						for(Player pl : Rule.c.keySet()) {
							Rule.playerinfo.get(pl).tropy(0 , 32);
						}
						mobcount = 999;
					}
					if(count == 3) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
						spawn("bard", Rule.c.size());
						spawn("c", Rule.c.size());
						mobcount = 999;
					}
					if(count == 4) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
						spawn("bard",2+ Rule.c.size());
						spawn("c", 3+ Rule.c.size());
						spawn("sn", 3);
						mobcount = 999;
					}
					if(count == 5) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb4/main:lb3");
						spawn("food2",3);
						spawn("food", 3 + Rule.c.size()/2);
						mobcount = 999;
					}
					if(count == 6) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
						spawn("dango",1 + Rule.c.size()/5);
						spawn("c", 3);
						spawn("sn", Rule.c.size());
						for(Player pl : Rule.c.keySet()) {
							Rule.playerinfo.get(pl).tropy(0 , 33);
						}
						mobcount = 999;
					}
					if(count == 7) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb4/main:lb3");
						spawn("food2",5 + Rule.c.size()/3);
						spawn("food", 3 + Rule.c.size()*2);
						mobcount = 999;
					}
					if(count == 8) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
						spawn("co",2);
						spawn("c", 3+ Rule.c.size()/2);
						spawn("bard",2+ Rule.c.size());
						mobcount = 999;
					}
					if(count == 9) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
						spawn("sn", Rule.c.size());
						spawn("co", 3);
						spawn("dango",4 + Rule.c.size()/3);
						spawn("c", 5+ Rule.c.size()/2);
						spawn("bard",5+ Rule.c.size());
						spawn("food", 5 + Rule.c.size()*2);
						mobcount = 999;
					}
					if(count == 10) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
						spawn("dango",8);
						spawn("c", 8+ Rule.c.size());
						spawn("sn", Rule.c.size()*2);
						spawn("co", 3);
						spawn("bard",8+ Rule.c.size());
						spawn("food2", 3 + Rule.c.size()/2);
						for(Player pl : Rule.c.keySet()) {
							Rule.playerinfo.get(pl).tropy(0 , 34);
							mobcount = 999;
						}
					}
					if(count == 11) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb6/main:lb5");
						spawn("dango",12);
						spawn("co", 2 + Rule.c.size()/2);
						spawn("c", 8+ Rule.c.size());
						spawn("bard",12+ Rule.c.size());
						spawn("food2", 3 + Rule.c.size()*2);
						mobcount = 999;
					}
					if(count == 12) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext all SUBTITLE true 40 main:lb8/main:lb9");
						spawn("whitenight",1);
						spawn("sado", 11);
						mobcount = 999;
					}
				}
				if(count == 12 && time%60 == 0 && wn()) {
					ARSystem.playSoundAll("by1");
					spawn("sado", 3 + Rule.c.size()/3);
					spawn("sado3", 1 + Rule.c.size()/5);
					for(int i =0; i< AMath.random(5); i++) {
						if(AMath.random(13) == 2) spawn("co",1);
						if(AMath.random(3) == 2) spawn("sn",1);
						if(AMath.random(10) == 2) spawn("dango",1);
						if(AMath.random(5) == 2) spawn("c",1);
						if(AMath.random(3) == 2) spawn("bard",1);
						if(AMath.random(8) == 2) spawn("food2",1);
						if(AMath.random(2) == 2) spawn("food",1);
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
			return;
		}
		if(ARSystem.gameMode == modes.ZOMBIE) {
			if(time == 180) {
				int i = 0;
				int br = 0;
				String st = "";
				for(Player play :Rule.c.keySet()) {
					if(Rule.c.get(play).getCode() != 100001) {
						st += play.getName() + " ";
						i++;
					} else {
						br++;
					}
				}
				
				for(Player play :Rule.c.keySet()) {
					if(Rule.c.get(play).getCode() != 100001) {
						Rule.playerinfo.get(play).addcradit((3*br),Main.GetText("main:msg103"));
						play.sendTitle(" Win ", Main.GetText("main:msg41"),40,20,40);
					} else {
						play.sendTitle(" Lose ", Main.GetText("main:msg43"),40,20,40);
					}
				}
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, new Runnable() {
					public void run() {
						ARSystem.GameStop();
					}
				}, 40);
				
			}
		}
		
		
		if(ARSystem.gameMode != modes.ZOMBIE) {
			if(Map.mapType == MapType.BIG &&time >= 60 && time%20 == 0) {
				ARSystem.playSoundAll("0select2");
				Map.sizeM();
			}
			
			if(Map.mapType == MapType.NORMAL &&time >= 120 && time%30 == 0) {
				ARSystem.playSoundAll("0select2");
				if(time <= 180) {
					RandomEvent(AMath.random(3));
				} else if(time <= 240)  {
					RandomEvent(AMath.random(4));
				} else if(time <= 300) {
					RandomEvent(3+AMath.random(2));
				} else {
					RandomEvent(6);
				}
			}
		}
		if(ARSystem.gameMode == modes.TEAMMATCH) {
			if(time >= 60 && time%20 == 0 && time <= 120) {
				for(Player p : Rule.c.keySet()) {
					AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg10"));
					ARSystem.potion(p, 24, 100, 1);
				}
			}
			if(time >= 121 && time%10 == 0) {
				Player pls = ARSystem.RandomPlayer();
				for(Player p :Rule.c.keySet()) {
					AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg7"));
				}
				ARSystem.Death(ARSystem.RandomPlayer(),ARSystem.RandomPlayer());
			}
		}
		if(ARSystem.gameMode == modes.TEAM) {
			if(time >= 70 && time%20 == 0) {
				for(Player p : Rule.c.keySet()) {
					AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg10"));
					ARSystem.potion(p, 24, 100, 1);
				}
			}
			if(Map.mapType == MapType.BIG &&time >= 60 && time%20 == 0) {
				ARSystem.playSoundAll("0select2");
				Map.sizeM();
			}
			
			if(Map.mapType == MapType.NORMAL &&time >= 120 && time%30 == 0) {
				ARSystem.playSoundAll("0select2");
				if(time <= 180) {
					RandomEvent(AMath.random(7));
				} else if(time <= 240)  {
					RandomEvent(AMath.random(8));
				} else if(time <= 300) {
					RandomEvent(8+AMath.random(2));
				} else {
					RandomEvent(10);
				}
			}
		}

	}
	public void spawn(String name,int count) {
		for(int i = 0; i < count; i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
				Location loc = Map.randomLoc();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn "+name+" 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
			},i);
		}
	}
	int mobcount = 0;
	public boolean MapMob() {
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
	public boolean wn() {
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
	public void RandomEvent(int i) {
		if(i == 1) {
			for(Player p :Rule.c.keySet()) {
				Rule.c.get(p).sskillmult+=0.25;
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg3") +" +25% ");
			}
		}
		if(i == 2) {
			for(Player p :Rule.c.keySet()) {
				for(int j = 0; j<10; j++) {
					Rule.c.get(p).cooldown[j] = 0;
				}
				AdvManager.set(p, 388, 0, Main.GetText("main:msg2") +" "+ Main.GetText("main:msg4"));
			}
		}
		if(i == 3) {
			for(Player p :Rule.c.keySet()) {
				if(!Rule.buffmanager.OnBuffTime(p, "stun") && !Rule.buffmanager.OnBuffTime(p, "timestop")) {
					Map.playeTp(p);
				}
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg5"));
			}
		}
		
		if(i == 4) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg54"));
			}
			spawn("food",15);
		}

		if(i == 5) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg53"));
			}
			spawn("zombie",20);
		}
		
		if(i == 6) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg52"));
			}
			spawn("sn",15);
		}
		
		if(i == 7) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg51"));
			}
			spawn("bard",15);
		}
		
		if(i == 8) {
			Player pls = ARSystem.RandomPlayer();
			for(Player p :Rule.c.keySet()) {
				p.teleport(pls);
				ARSystem.giveBuff(p, new Silence(p), 40);
				ARSystem.giveBuff(p, new Nodamage(p), 40);
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg6"));
			}
		}
		
		if(i == 9) {
			Player pls = ARSystem.RandomPlayer();
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg7"));
			}
			ARSystem.Death(ARSystem.RandomPlayer(),ARSystem.RandomPlayer());
		}
		if(i == 10) {
			Player pls = ARSystem.RandomPlayer();
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg7"));
			}
			ARSystem.GameStop();
		}
	}
	
	
	public int getTime() {
		return time;
	}
}
