package util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Minecart;
import buff.Silence;
import event.Skill;
import types.MapType;
import manager.AdvManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Map {
	public static Location loc_f;
	public static Location loc_l;
	static String name;
	static String type;
	static int mapCount = 0;
	static int mapCount2 = 0;
	
	public static int maphuman = 10;
	
	public static String Version = "1.89";
	public static MapType mapType = MapType.NORMAL;
	public static World world = Bukkit.getWorld("world");
	public static int lastplay = 0;
	
	public static int mapid = 0;
	
	static public void randomMap(int map) {
		mapCount = Integer.parseInt(Main.GetText("map:map"));
		mapCount2 = Integer.parseInt(Main.GetText("map:map_"));
		
		getMapinfo(AMath.random(mapCount));
		int i = 0;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(Rule.playerinfo.get(player).gamejoin) {
				i++;
			}
		}

		Random(i,map);
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(Main.GetText("map:map_1"), name, 40, 80, 80);
		}
	}
	
	static public void Random(int playcount,int maps) {
		if(playcount < maphuman) {
			int map = AMath.random(mapCount);
			if(maps != -1) map = maps;
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
	
	static public void sizeM(int Msize) {
		if (world.getWorldBorder().getSize() >= maphuman) {
			int size = (loc_l.getBlockX() - loc_f.getBlockX());
			if(Msize < 0) {
				if(size < (loc_l.getBlockZ() - loc_f.getBlockZ())) size = (loc_l.getBlockZ() - loc_f.getBlockZ());
				size = size/(Rule.c.size()*Integer.parseInt(Main.GetText("general:bigmap_count_size")));
				if(size < 5) size = 5;
				if(size > 50) size = 50;
			} else {
				size = Msize;
			}
			Location loff_ = loc_f.clone();
			loff_.setY(loc_l.getY());
			
			if(loff_.distance(loc_l) > 30) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg9") +" -"+(size*2));
				}
				
				int local = AMath.random(size*2)-size;
				loc_f.add(local,0,local);
				loc_l.add(local,0,local);
				
				loc_f.add(size,0,size);
				loc_l.add(-size,0,-size);
				size = (loc_l.getBlockX() - loc_f.getBlockX());
				if(size < (loc_l.getBlockZ() - loc_f.getBlockZ())) size = (loc_l.getBlockZ() - loc_f.getBlockZ());
				int x = (loc_f.getBlockX() + ((loc_l.getBlockX() - loc_f.getBlockX())/2));
				int z = (loc_f.getBlockZ() + ((loc_l.getBlockZ() - loc_f.getBlockZ())/2));
				world.getWorldBorder().setCenter(x, z);
				int sx = Math.abs(getCenter().getBlockX() - loc_l.getBlockX());
				int sz = Math.abs(getCenter().getBlockZ() - loc_l.getBlockZ());
				int min = sx;
				if(sz < min) min = sz;
				
				world.getWorldBorder().setSize(sz*2,1);

			}
		}
	}
	
	static public boolean inMap(Player p) {
		boolean ismap = inMap( p.getLocation());
		if(ismap == false) {
			BaseComponent[] component = new ComponentBuilder("§c§l[NoMap]").create();
			p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,component);
		}
		if(type != null) {
			if(!ismap && type.contains("falldamage")) {
				Map.playeTp(p);
				ARSystem.giveBuff(p, new Silence(p), 20);
				p.setFallDistance(0);
				if(p.getHealth() - p.getMaxHealth()/5 > 1) {
					p.setHealth(p.getHealth() - p.getMaxHealth()/5);
				} else {
					Skill.death(p, p);
				}
			}
		}
		return ismap;
	}
	
	static public boolean inMap(Location loc) {
		boolean ismap = true;
		if(loc.getX() < loc_f.getX()) ismap = false;
		if(loc.getY() < loc_f.getY()) ismap = false;
		if(loc.getZ() < loc_f.getZ()) ismap = false;
		if(loc.getX()-1 > loc_l.getX()) ismap = false;
		if(loc.getY()-1 > loc_l.getY()) ismap = false;
		if(loc.getZ()-1 > loc_l.getZ()) ismap = false;
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
		mapid = i;
		name = Main.GetText("map:map"+i);
		type = Main.GetText("map:map"+i+"type");
		loc_f = new Location(world,Text.getI("map:map"+i+"x1"),Text.getI("map:map"+i+"y1"),Text.getI("map:map"+i+"z1"));
		loc_l = new Location(world,Text.getI("map:map"+i+"x2"),Text.getI("map:map"+i+"y2"),Text.getI("map:map"+i+"z2"));
		if(mapid == 9) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				spawn("daizin"+AMath.random(2), 1);
			},40);
		}
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
			loc.setYaw(AMath.random(360));
			return loc;
		} else {
			loc = randomLoc();
		}
		return loc;
	}
	static public Location randomLoc() {
		Location loc = null;

		boolean While = true;
		int count = 0;
		while(While) {
			Location ploc = loc_f.clone();
			Location plocs = loc_l.clone().subtract(loc_f.clone());
			if(count > 1000) break;
			if(count > 300 ) {
				ploc.add(AMath.random(plocs.getBlockX()), AMath.random(plocs.getBlockY()), AMath.random(plocs.getBlockZ()));
			} else if(count > 100) {
				ploc.add(AMath.random(plocs.getBlockX()), AMath.random((int)(plocs.getBlockY()*0.8)), AMath.random(plocs.getBlockZ()));
			} else {
				ploc.add(AMath.random(plocs.getBlockX()), AMath.random(plocs.getBlockY()/2), AMath.random(plocs.getBlockZ()));
			}
			ploc.setYaw(AMath.random(360));
			for(int i = ploc.getBlockY(); i > 0 ;i--) {
				if(ploc.getBlock().isEmpty() && ploc.getY() >= loc_f.getY()) {
					ploc.add(0, -1, 0);
				} else {
					break;
				}
			}
			if(BlockUtil.isPathable(ploc.clone().add(0,1,0).getBlock()) && !BlockUtil.isPathable(ploc.getBlock()) && inMap(ploc)) {
				ploc.add(0, 1, 0);
				return ploc;
			}
			count++;
		}
		return loc;
	}

	static public void loby() {
		getMapinfo(0);
		playerTpall();
		ARSystem.killall();
		Player player = null;
		Rule.team.reload();
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			player = pl;
		}
		if(player != null) {
			Location loc = new Location(world,-7,33.5,-108);
			loc.setYaw(270);
			ARSystem.spellLocCast(player, loc, "heIp");
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
		Holo.create(new Location(world,-3.2,31.1,-95.5),"Teddy_Dear , nocksa , LARS0131");
		
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

	public static void UniqeMap(Player player) {
		if(type != null) {
			if(type.contains("minecart")) {
				Location loc = player.getLocation();
				if(loc.getBlock().getTypeId() == 27 && !player.isSneaking()) {
					Vector vtr = null;
					if(loc.clone().add(1,0,0).getBlock().getTypeId() == 28) vtr = new Vector(-1,0,0);
					if(loc.clone().add(-1,0,0).getBlock().getTypeId() == 28) vtr = new Vector(1,0,0);
					if(loc.clone().add(0,0,1).getBlock().getTypeId() == 28) vtr = new Vector(0,0,-1);
					if(loc.clone().add(0,0,-1).getBlock().getTypeId() == 28) vtr = new Vector(0,0,1);
					if(vtr != null){
						ARSystem.addBuff(player, new Minecart(player, vtr), 60);
					}
					
				}
			}
		}
	}

	public static Location getCenter() {
		Location l1 = loc_f.clone();
		Location l2 = loc_l.clone();
		l2.subtract(l1);
		return loc_f.clone().add(l2.multiply(0.5));
	}
	
	public static void spawn(String name,int count) {
		for(int i = 0; i < count; i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
				Location loc = Map.randomLoc();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn "+name+" 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
			},i);
		}
	}
	public static void spawn(String name,Location loc,int count) {
		if(count == 1) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn "+name+" 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
			return;
		}
		for(int i = 0; i < count; i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn "+name+" 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
			},i);
		}
	}
}
