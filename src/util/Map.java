package util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import types.MapType;
import types.modes;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Map {
	static Location loc_f;
	static Location loc_l;
	static String name;
	static int mapCount = 0;
	static int mapCount2 = 0;
	
	public static int maphuman = 10;
	
	public static String Version = "1.48C";
	public static MapType mapType = MapType.NORMAL;
	public static World world = Bukkit.getWorld("world");
	public static int lastplay = 0;
	
	public static int mapid = 0;
	
	static public void randomMap() {
		mapCount = Integer.parseInt(Main.GetText("map:map"));
		mapCount2 = Integer.parseInt(Main.GetText("map:map_"));
		
		getMapinfo(AMath.random(mapCount));
		int i = 0;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(Rule.playerinfo.get(player).gamejoin) {
				i++;
			}
		}
		if(ARSystem.gameMode == modes.LOBOTOMY) {
			mapType = MapType.NORMAL;
			ARSystem.Gamemode = 1;
			getMapinfo(1004);
			Rule.team.teamCreate("H");
			Rule.team.getTeam("H").setTeamColor("7");
			for(Player p : Bukkit.getOnlinePlayers()) {
				Rule.team.teamJoin("H", p);
			}
			Bgm.rep = false;
		}
		else if(ARSystem.gameMode == modes.TEAMMATCH) {
			mapType = MapType.TEAMMATCH;
			getMapinfo(1003);
			Rule.team.teamCreate("RED");
			Rule.team.teamCreate("BLUE");
			String local = Main.GetText("map:map1003t1");
			Location loc = new Location(loc_f.getWorld(),Integer.parseInt(local.split(",")[0]),Integer.parseInt(local.split(",")[1]),Integer.parseInt(local.split(",")[2]));
			Rule.team.getTeam("RED").setTeamSpawn(loc);
			local = Main.GetText("map:map1003t2");
			loc = new Location(loc_f.getWorld(),Integer.parseInt(local.split(",")[0]),Integer.parseInt(local.split(",")[1]),Integer.parseInt(local.split(",")[2]));
			Rule.team.getTeam("BLUE").setTeamSpawn(loc);
			Rule.team.getTeam("RED").setTeamWin(true);
			Rule.team.getTeam("BLUE").setTeamWin(true);
			Rule.team.getTeam("RED").setTeamColor("c");
			Rule.team.getTeam("BLUE").setTeamColor("9");
			
			List<Player> gameplayers = new ArrayList<Player>();
			List<Player> gameplayers2 = new ArrayList<Player>();
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(Rule.playerinfo.get(p).gamejoin) {
					gameplayers.add(p);
					gameplayers2.add(p);
				}
			}
			int blue = 0;
			int red = 0;
			
			for(Player p : gameplayers) {
				if(Rule.playerinfo.get(p).team != null) {
					if(Rule.playerinfo.get(p).team.equals("red")) {
						Rule.team.teamJoin("RED", p);
						gameplayers2.remove(p);
						red++;
					}
					if(Rule.playerinfo.get(p).team.equals("blue")) {
						Rule.team.teamJoin("BLUE", p);
						gameplayers2.remove(p);
						blue++;
					}
				}
			}
			
			for(Player p : gameplayers) {
				if(gameplayers2.contains(p)) {
					if(blue < red) {
						Rule.team.teamJoin("BLUE", p);
						blue++;
					} else {
						Rule.team.teamJoin("RED", p);
						red++;
					}
				}
			}
			
		} else {
			if(ARSystem.gameMode == modes.TEAM) {
				Rule.team.teamCreate("RED");
				Rule.team.teamCreate("BLUE");
				Rule.team.teamCreate("YELLOW");
				Rule.team.teamCreate("GREEN");
				Rule.team.getTeam("RED").setTeamWin(true);
				Rule.team.getTeam("BLUE").setTeamWin(true);
				Rule.team.getTeam("YELLOW").setTeamWin(true);
				Rule.team.getTeam("GREEN").setTeamWin(true);
				Rule.team.getTeam("RED").setTeamColor("c");
				Rule.team.getTeam("BLUE").setTeamColor("9");
				Rule.team.getTeam("YELLOW").setTeamColor("e");
				Rule.team.getTeam("GREEN").setTeamColor("a");
				
				List<Player> gameplayers = new ArrayList<Player>();
				List<Player> gameplayers2 = new ArrayList<Player>();
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(Rule.playerinfo.get(p).gamejoin) {
						gameplayers.add(p);
						gameplayers2.add(p);
					}
				}
				int blue = 0;
				int red = 0;
				int yellow = 0;
				int green = 0;
				
				for(Player p : gameplayers) {
					if(Rule.playerinfo.get(p).team != null) {
						if(Rule.playerinfo.get(p).team.equals("red")) {
							Rule.team.teamJoin("RED", p);
							gameplayers2.remove(p);
							red++;
						}
						if(Rule.playerinfo.get(p).team.equals("blue")) {
							Rule.team.teamJoin("BLUE", p);
							gameplayers2.remove(p);
							blue++;
						}
						if(Rule.playerinfo.get(p).team.equals("green")) {
							Rule.team.teamJoin("GREEN", p);
							gameplayers2.remove(p);
							green++;
						}
						if(Rule.playerinfo.get(p).team.equals("yellow")) {
							Rule.team.teamJoin("YELLOW", p);
							gameplayers2.remove(p);
							yellow++;
						}
					}
				}
				
				for(Player p : gameplayers) {
					if(gameplayers2.contains(p)) {
						if(blue < red && blue < green && blue < yellow) {
							Rule.team.teamJoin("BLUE", p);
							blue++;
						} else if(red < green && red < yellow) {
							Rule.team.teamJoin("RED", p);
							red++;
						}  else if(yellow < green) {
							Rule.team.teamJoin("YELLOW", p);
							yellow++;
						} else {
							Rule.team.teamJoin("GREEN", p);
							green++;
						}
					}
				}
			}
			Random(i);
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(Main.GetText("map:map_1"), name, 40, 80, 80);
		}
	}
	
	static public void Random(int playcount) {
		if(playcount < maphuman) {
			int map = AMath.random(mapCount);
			for(int k=0; k<100;k++) {
				if(lastplay == map) {
					map = AMath.random(mapCount);
					break;
				}
			}
			lastplay = map;
			getMapinfo(lastplay);
			mapType = MapType.NORMAL;
		} else {
			int map = 100+AMath.random(mapCount2);
			for(int k=0; k<100;k++) {
				if(lastplay == map) {
					map = 100+AMath.random(mapCount2);
					break;
				}
			}
			lastplay = map;
			getMapinfo(lastplay);
			mapType = MapType.BIG;
		}
	}
	
	static public void sizeM() {
		if (world.getWorldBorder().getSize() >= maphuman) {
			int size = (loc_l.getBlockX() - loc_f.getBlockX());
			if(size < (loc_l.getBlockZ() - loc_f.getBlockZ())) size = (loc_l.getBlockZ() - loc_f.getBlockZ());
			size = size/(Rule.c.size()*2);
			if(size < 5) size = 5;
			Location loff_ = loc_f.clone();
			loff_.setY(loc_l.getY());
			for(Player p : Bukkit.getOnlinePlayers()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg9") +" -"+(size*2));
			}
			if(loff_.distance(loc_l) > 15) {
				loc_f.add(size,0,size);
				loc_l.add(-size,0,-size);
				size = (loc_l.getBlockX() - loc_f.getBlockX());
				if(size < (loc_l.getBlockZ() - loc_f.getBlockZ())) size = (loc_l.getBlockZ() - loc_f.getBlockZ());
				world.getWorldBorder().setSize(size,1);

			}
		}
	}
	
	static public boolean inMap(Player p) {
		Location loc = p.getLocation();
		boolean ismap = true;
		
		if(loc.getX() < loc_f.getX()) ismap = false;
		if(loc.getY() < loc_f.getY()) ismap = false;
		if(loc.getZ() < loc_f.getZ()) ismap = false;
		if(loc.getX()-1 > loc_l.getX()) ismap = false;
		if(loc.getY()-1 > loc_l.getY()) ismap = false;
		if(loc.getZ()-1 > loc_l.getZ()) ismap = false;
		if(ismap == false) {
			BaseComponent[] component = new ComponentBuilder("§c§l[NoMap]").create();
			p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,component);
		}
		return ismap;
	}
	
	static public Location getMapZ(int i) {
		Location loc_a = loc_f.clone();
		Location loc_b = loc_l.clone();
		loc_a.setY(0);
		loc_b.setY(0);
		
		if(i == 1) {
			return loc_a;
		}
		if(i == 2) {
			loc_a.setX(loc_b.getX());
			return loc_a;
		}
		if(i == 3) {
			return loc_b;
		}
		loc_a.setZ(loc_b.getZ());
		return loc_a;
	}
	
	static public void getMapinfo(int i) {
		name = Main.GetText("map:map"+i);
		loc_f = new Location(world,Text.getTextint("map:map"+i+"x1"),Text.getTextint("map:map"+i+"y1"),Text.getTextint("map:map"+i+"z1"));
		loc_l = new Location(world,Text.getTextint("map:map"+i+"x2"),Text.getTextint("map:map"+i+"y2"),Text.getTextint("map:map"+i+"z2"));
		if(i >= 100 && i <= 999) {
			int x = (loc_f.getBlockX() + ((loc_l.getBlockX() - loc_f.getBlockX())/2));
			int z = (loc_f.getBlockZ() + ((loc_l.getBlockZ() - loc_f.getBlockZ())/2));
			world.getWorldBorder().setCenter(x, z);
			world.getWorldBorder().setSize(500,0);
			int size = (loc_l.getBlockX() - loc_f.getBlockX());
			if(size < (loc_l.getBlockZ() - loc_f.getBlockZ())) size = (loc_l.getBlockZ() - loc_f.getBlockZ());
			world.getWorldBorder().setSize(size,10);
		}else {
			world.getWorldBorder().setCenter(0, 0);
			world.getWorldBorder().setSize(10000,0);
		}
	}
	
	static public void playerTpall() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			playeTp(p);
		}
	}
	static public void playeTp(Player p) {
		if(Rule.team.getTeamLocation(p) != null) {
			Location loc = Rule.team.getTeamLocation(p);
			loc.setYaw(AMath.random(360));
			p.teleport(loc);
		} else {
			for(int i =0; i<10000;i++) {
				Location ploc = loc_f.clone();
				Location plocs = loc_l.clone().subtract(loc_f.clone());
				ploc.add(AMath.random(plocs.getBlockX()), AMath.random(plocs.getBlockY()/2), AMath.random(plocs.getBlockZ()));
				for(int j =0; j<10000;j++) {
					if(ploc.getBlock().isEmpty() && ploc.getY() > loc_f.getBlockY()) {
						ploc.add(0, -1, 0);
					} else {
						break;
					}
				}
				Location plocf = ploc.clone();
				plocf.setY(0);
				if(world.getWorldBorder().getCenter().distance(plocf) < world.getWorldBorder().getSize()) {
					ploc.add(0, 1, 0);
					if(ploc.getBlock().isEmpty() && ploc.clone().add(0,1,0).getBlock().isEmpty()&& !ploc.clone().add(0,-1,0).getBlock().isEmpty()) {
						ploc.setYaw(AMath.random(360)-180);
						for (Entity e : p.getPassengers()) {
							p.removePassenger(e);
						}
						p.teleport(ploc);
						break;
					}
				}
			}
		}
	}
	
	static public void hide(Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{	
			for(Player pl : Bukkit.getOnlinePlayers()) {
				p.hidePlayer(pl);
			}
		},10);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{	
			for(Player pl : Bukkit.getOnlinePlayers()) {
				p.showPlayer(pl);
			}
		},20);
	}
	
	static public Location randomLoc(Player p) {
		Location loc = null;
		if(Rule.team.getTeamLocation(p) != null) {
			loc = Rule.team.getTeamLocation(p);
			loc.setY(AMath.random(360));
			return loc;
		} else {
			loc = randomLoc();
		}
		return loc;
	}
	static public Location randomLoc() {
		Location loc = null;

		boolean While = true;
		while(While) {
			Location ploc = loc_f.clone();
			Location plocs = loc_l.clone().subtract(loc_f.clone());
			ploc.add(AMath.random(plocs.getBlockX()), AMath.random(plocs.getBlockY()/2), AMath.random(plocs.getBlockZ()));
			
			while(true) {
				if(ploc.getBlock().isEmpty() && ploc.getY() > 0) {
					ploc.add(0, -1, 0);
				} else {
					break;
				}
			}
			ploc.add(0, 1, 0);
			if(ploc.getBlock().isEmpty()) {
				return ploc;
			}
		}
		
		return loc;
	}
	static public void loby() {
		getMapinfo(0);
		playerTpall();
		Player player = null;
		Rule.team.reload();
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			player = pl;
		}
		if(player != null) {
			ARSystem.spellLocCast(player, new Location(world,-7,33.5,-108), "heIp");
		}
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn sendback 1 world,-10.5,30,-52.5");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn map5 1 world,11,30,-9.5");
		Holo.create(new Location(world,-5.2,31,-61), Main.GetText("main:loby1"));
		Holo.create(new Location(world,-5.2,30.6,-61), Main.GetText("main:loby2"));
		Holo.create(new Location(world,-5.2,30.2,-61), Main.GetText("main:loby3"));
		Holo.create(new Location(world,-5.2,29.8,-61), Main.GetText("main:loby4"));
		Holo.create(new Location(world,-5.2,29.4,-61), Main.GetText("main:loby5"));
		
		Holo.create(new Location(world,-16.8,32.2,-58), Main.GetText("main:loby6"));
		Holo.create(new Location(world,-16.8,32.2,-61), Main.GetText("main:loby7"));
		
		Holo.create(new Location(world,-17.8,32.2,-95.5),"§fVersion : §l"+Version);
		Holo.create(new Location(world,-17.8,31.8,-95.5), Main.GetText("main:loby8") + "https://cafe.naver.com/helpgames");
		
		Holo.create(new Location(world,-3.2,32.4,-95.5),Main.GetText("main:loby9"));
		Holo.create(new Location(world,-3.2,32.1,-95.5),"heIp12");
		Holo.create(new Location(world,-3.2,31.4,-95.5),Main.GetText("main:loby10"));
		Holo.create(new Location(world,-3.2,31.1,-95.5),"Teddy_Dear");
		
		Holo.create(new Location(world,-16.8,31.5,-57.2), "§41");
		Holo.create(new Location(world,-16.8,31,-57.2), "§c2");
		Holo.create(new Location(world,-16.8,30.5,-57.2), "§c3");
		Holo.create(new Location(world,-16.8,30,-57.2), "§64");
		Holo.create(new Location(world,-16.8,29.5,-57.2), "§e5");
		
		Holo.create(new Location(world,-16.8,31.5,-58.2), getplayer(0));
		Holo.create(new Location(world,-16.8,31,-58.2), getplayer(1));
		Holo.create(new Location(world,-16.8,30.5,-58.2), getplayer(2));
		Holo.create(new Location(world,-16.8,30,-58.2), getplayer(3));
		Holo.create(new Location(world,-16.8,29.5,-58.2), getplayer(4));
		
		Holo.create(new Location(world,-16.8,31.5,-59.2), getScore(0));
		Holo.create(new Location(world,-16.8,31,-59.2), getScore(1));
		Holo.create(new Location(world,-16.8,30.5,-59.2), getScore(2));
		Holo.create(new Location(world,-16.8,30,-59.2), getScore(3));
		Holo.create(new Location(world,-16.8,29.5,-59.2), getScore(4));
		
		Holo.create(new Location(world,-16.8,31.5,-60.2), "§41");
		Holo.create(new Location(world,-16.8,31,-60.2), "§c2");
		Holo.create(new Location(world,-16.8,30.5,-60.2), "§c3");
		Holo.create(new Location(world,-16.8,30,-60.2), "§64");
		Holo.create(new Location(world,-16.8,29.5,-60.2), "§e5");
		
		Holo.create(new Location(world,-16.8,31.5,-61.2), getplayer(5));
		Holo.create(new Location(world,-16.8,31,-61.2), getplayer(6));
		Holo.create(new Location(world,-16.8,30.5,-61.2), getplayer(7));
		Holo.create(new Location(world,-16.8,30,-61.2), getplayer(8));
		Holo.create(new Location(world,-16.8,29.5,-61.2), getplayer(9));
		
		Holo.create(new Location(world,-16.8,31.5,-62.2), getScore(5));
		Holo.create(new Location(world,-16.8,31,-62.2), getScore(6));
		Holo.create(new Location(world,-16.8,30.5,-62.2), getScore(7));
		Holo.create(new Location(world,-16.8,30,-62.2), getScore(8));
		Holo.create(new Location(world,-16.8,29.5,-62.2), getScore(9));
		
		for(int i =1; i<=30;i++) {
			if(Main.GetText("main:update"+i) != null) {
				Holo.create(new Location(world,-10.5,36-(i*0.2),-98.5), Main.GetText("main:update"+i));
			}
		}
	}
	
	public static String getplayer(int i) {
		if(Rule.Score1[i] == null) return "§f-";
		if(Rule.Score1[i] == null) return "§f-";
		return "§l"+Rule.Score1[i];
	}
	
	public static String getScore(int i) {
		if(Rule.Score2[i] == 0) return "§l-";
		if(Rule.Score2[i] == 0) return "§l-";
		return "§a§l"+Rule.Score2[i];
	}
}
