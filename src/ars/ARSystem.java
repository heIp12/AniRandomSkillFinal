package ars;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedLocationSpell;

import Main.Main;
import aliveblock.ABlock;
import buff.Buff;
import manager.Bgm;
import manager.BuffManager;
import manager.EntityBuffManager;
import manager.Holo;
import net.minecraft.server.v1_12_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutPosition.EnumPlayerTeleportFlags;
import types.BuffType;
import types.MapType;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.MSUtil;
import util.Map;

public class ARSystem {
	public static ARSinfo AniRandomSkill;
	static boolean chars[] = null;
	static boolean charban[] = new boolean[GetChar.getCount()+1];
	
	public static boolean ban = false;
	public static int time = 12;
	public static int starttime = 30;
	public static modes gameMode = modes.NORMAL;
	public static boolean gameMode2 = true;
	public static int Gamemode = 1;
	public static int serverOne = 0;
	
	static public void banload() {
		for(int i=0;i<charban.length;i++) {
			charban[i] = (Boolean)Rule.Var.Load("#ARS.ban."+(i+1));
		}
	}
	static public void bansave() {
		for(int i=0;i<charban.length;i++) {
			Rule.Var.Save("#ARS.ban."+(i+1), charban[i]);
		}
	}
	static public void Start(int mapc) {
		int i = 0;
		int max = GetChar.getCount();
		Rule.c.clear();
		Rule.team.reload();
		killall();
		int gm = Gamemode;
		gameMode2 = true;
		
		if(Rule.buffmanager != null) Rule.buffmanager.clear();
		Rule.buffmanager = new BuffManager();
		
		if(aliveblock.Main.Aliveblock != null) {
			for(ABlock block : aliveblock.Main.Aliveblock) {
				block.removeBlock(5);
			}
			aliveblock.Main.Aliveblock.clear();
		}
		Bgm.rep = true;
		if(gm == 1) {
			gameMode = modes.NORMAL;
			if(AMath.random(100) <= Integer.parseInt(Main.GetText("general:mode_chance"))) {
				gm = AMath.random(modes.size-1)+1;
			}
		}
		
		if(gm == 2){
			gameMode = modes.ONE;
		} else if(gm == 3){
			gameMode2 = false;
			gameMode = modes.ZOMBIE;
			Rule.team.teamRemove("buri");
			Rule.team.teamCreate("buri");
			Rule.team.getTeam("buri").setTeamColor("6");
			Rule.team.getTeam("buri").setTeamName("buris");
		} else if(gm == 4){
			gameMode2 = false;
			gameMode = modes.TEAMMATCH;
		} else if(gm == 5){
			gameMode2 = true;
			gameMode = modes.TEAM;
		} else if(gm == 6){
			gameMode = modes.KANNA;
		} else if(gm == 7){
			gameMode = modes.GUN;
		} else if(gm == 8){
			gameMode = modes.KILLER;
		} else if(gm == 666){
			gameMode = modes.LOBOTOMY;
		}
		
		Map.randomMap(mapc);

        AniRandomSkill = new ARSinfo(time);

		Bgm.randomBgm();
		AniRandomSkill.player = 0;
		Map.playerTpall();
		float score = 0;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(Rule.playerinfo.get(player).gamejoin) {
				AniRandomSkill.player++;
				score += Rule.playerinfo.get(player).getScore();
			}
		}
		
		if(score != 0) {
			score /= AniRandomSkill.player;
			AniRandomSkill.gamescore = score;
		}
		
		
		Bukkit.broadcastMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:info25")+" "+Math.round(score));
		
		chars = new boolean[GetChar.getCount()+1];
		
    	int server = serverOne;
    	if(gm == 2 && server == 0) {
	    	while(true) {
		        server = (int) (Math.random()*max);
				if(listset(server)) {
					break;
				}
	    	}
    	} else {
    		server--;
    	}
		
		if(AniRandomSkill.player > 1 || (Bukkit.getOnlinePlayers().size() == 1 && AniRandomSkill.player == 1 )) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(Rule.playerinfo.get(player).gamejoin) {
					Rule.playerinfo.get(player).addcradit(1,Main.GetText("main:msg106"));
					if(gameMode == modes.KANNA) {
						Rule.c.put(player,GetChar.get(player, Rule.gamerule, "98"));
					}
					else if(gameMode == modes.ONE) {
						Rule.c.put(player,GetChar.get(player, Rule.gamerule, ""+(server+1)));
					}
					else if(Rule.pick && listset(+Rule.playerinfo.get(player).playerc-1)) {
						Rule.c.put(player, GetChar.get(player, Rule.gamerule, ""+Rule.playerinfo.get(player).playerc));
					} else {
						int j = 0;
						while(j < 1000) {
							i = (int) (Math.random()*max);
							
							if(Rule.playerinfo.get(player).playchar == i) continue;
							if(Rule.playerinfo.get(player).playerc > 0 && AMath.random(100) <= Integer.parseInt(Main.GetText("general:pick_chance"))) {
								i = Rule.playerinfo.get(player).playerc-1;
							}
							if(!chars[i] && listset(i)) {
								chars[i] = true;
								Rule.c.put(player, GetChar.get(player, Rule.gamerule, ""+(i+1)));
								break;
								
							}
						}
						
						if(j > 999) {
							Rule.c.put(player, GetChar.get(player,Rule.gamerule, ""+0));
						}
					}
				} else {
					player.setGameMode(GameMode.SPECTATOR);
				}
			}
		} else {
			AniRandomSkill = null;
			System.out.println("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror6"));
			for(Player player : Bukkit.getOnlinePlayers()) {
				MSUtil.resetbuff(player);
				player.setGameMode(GameMode.ADVENTURE);
				player.setMaxHealth(20);
				player.setHealth(20);
			}
			ARSystem.AniRandomSkill = null;
			Rule.c.clear();
			Map.loby();
		}
		if(AniRandomSkill != null) AniRandomSkill.start();
	}
	
	static public void chaRrep(Player p) {
		int i = 0;
		int max = GetChar.getCount();
		int j = 0;
		while(j < 1000) {
			i = (int) (Math.random()*max);
			if(Rule.playerinfo.get(p).playchar == i) continue;
			if(Rule.playerinfo.get(p).playerc > 0 && AMath.random(100) <= Integer.parseInt(Main.GetText("general:pick_chance"))) {
				i = Rule.playerinfo.get(p).playerc-1;
			}
			if(!chars[i] && listset(i)) {
				chars[i] = true;
				Rule.playerinfo.get(p).playchar = i;
				Rule.c.put(p, GetChar.get(p, Rule.gamerule, ""+(i+1)));
				break;
			}
			j++;
		}
		if(j > 999) {
			Rule.c.put(p, GetChar.get(p,Rule.gamerule, ""+0));
		}
	}
	
	static public boolean listset(int chars) {
		chars += 1;
		if(ban) {
			if(charban[chars-1] == true) return false;
		}
		if(AniRandomSkill.player < 3) {
			if(chars==12) return false;
			if(chars==28) return false;
			if(chars==37) return false;
			if(chars==43) return false;
			if(chars==53) return false;
			if(chars==59) return false;
			if(chars==81) return false;
		}
		if(gameMode == modes.ONE) {
			if(chars==3) return false;
			if(chars==12) return false;
			if(chars==17) return false;
			if(chars==25) return false;
			if(chars==28) return false;
			if(chars==37) return false;
			if(chars==43) return false;
			if(chars==52) return false;
			if(chars==53) return false;
			if(chars==59) return false;
			if(chars==74) return false;
			if(chars==89) return false;
			if(chars==92) return false;
		}
		if(gameMode == modes.TEAMMATCH) {
			if(chars==12) return false;
			if(chars==28) return false;
			if(chars==35) return false;
			if(chars==37) return false;
			if(chars==43) return false;
			if(chars==53) return false;
			if(chars==58) return false;
			if(chars==79) return false;
			if(chars==90) return false;
		}
		if(gameMode == modes.TEAM) {
			if(chars==43) return false;
			if(chars==53) return false;
			if(chars==58) return false;
			if(chars==79) return false;
		}
		if(gameMode == modes.LOBOTOMY) {
			if(chars==12) return false;
			if(chars==16) return false;
			if(chars==18) return false;
			if(chars==23) return false;
			if(chars==28) return false;
			if(chars==30) return false;
			if(chars==35) return false;
			if(chars==37) return false;
			if(chars==46) return false;
			if(chars==53) return false;
			if(chars==59) return false;
			if(chars==70) return false;
			if(chars==78) return false;
			if(chars==79) return false;
			if(chars==81) return false;
			if(chars==90) return false;
			if(chars==95) return false;
			if(chars==100) return false;
		}
		return true;
	}
	
	static public void Death(Player p,Entity e) {
		p.setGameMode(GameMode.SPECTATOR);
		p.setMaxHealth(40);
		p.setHealth(40);
		p.performCommand("c removemyall");
		p.setWalkSpeed(0.2f);
		for(PotionEffect potion :p.getActivePotionEffects()) {
			p.removePotionEffect(potion.getType());
		}
		
		MSUtil.resetbuff(p);
		String n = "no";
		if(Rule.c.get(e) != null) {
			int number = Rule.c.get(e).number;
			n = "§a(No."+number+")§f§l"+ Main.GetText("c"+number+":name1")+" "+ Main.GetText("c"+number+":name2");
		} else {
			n = "Monster";
		}
		p.sendTitle("§c§l【Killer】", e.getName() +" §7§l["+n+"]");
		if(e instanceof Player && ARSystem.AniRandomSkill != null) {
			if(ARSystem.AniRandomSkill.playerkill.get((Player)e) == null) {
				ARSystem.AniRandomSkill.playerkill.put((Player) e,0);
			}
			ARSystem.AniRandomSkill.playerkill.put((Player) e, ARSystem.AniRandomSkill.playerkill.get((Player)e)+1);
		}
		
		if(Rule.buffmanager.selectBuffType(p, BuffType.HEADCC) != null && Rule.buffmanager.getHashMap().get(p) != null) {
			for(Buff buff : Rule.buffmanager.getHashMap().get(p).getBuff()) {
				buff.setTime(0);
				buff.stop();
			}
		}
		
		if(Rule.c.get(p) != null) {
			Rule.c.get(p).info();
			Rule.removePlayers.add(p);
		}
		
		for(Player playr: Rule.c.keySet()) {
			Rule.c.get(playr).PlayerDeath(p,e);
		}
		if(Map.mapType == MapType.BIG && Boolean.parseBoolean(Main.GetText("general:bigmap_death"))) Map.sizeM(-1);
		
		if(Rule.c.get(e) != null && Rule.playerinfo.get(p).getScore() < 100 && e != p) {
			int j = 1;
			int i = Rule.c.get(e).getCode();
			p.sendMessage("§c§l"+Main.GetText("main:info53"));
			p.sendMessage(n);
			while(Main.GetText("c"+i+":kill"+j) != null) {
				String text = Main.GetText("c"+i+":kill"+j);
				p.sendMessage("§6" + text);
				j++;
			}
			if(j == 1) p.sendMessage("§7"+Main.GetText("main:cmderror3"));
		}
		
		if(Rule.playerinfo.get(e) != null) {
			p.performCommand("c death"+Rule.playerinfo.get(e).kille);
		}
	}
	
	static public void Stop() {
		if(gameMode == modes.LOBOTOMY) {
			if(Rule.c.size() == 0) gameEnd();
		} else if((gameMode == modes.TEAM || gameMode == modes.TEAMMATCH) && AniRandomSkill != null) {
			List<Player> players = new ArrayList<Player>();
			for(Player p :Rule.c.keySet()) {
				players.add(p);
			}
			if(Rule.team.allTeam(players)) {
				for(Player p :Rule.c.keySet()) {
					if(Rule.buffmanager.selectBuffType(p, BuffType.HEADCC) != null) {
						for(Buff buff : Rule.buffmanager.getHashMap().get(p).getBuff()) {
							buff.stop();
						}
					}
				}
				if(Rule.c.keySet().size() == 0) {
					gameEnd();
					return;
				}
				Player win = (Player) Rule.c.keySet().toArray()[0];
				String team = Rule.team.getTeam(win).get(0).getTeamName();
				String name = "§e§lTeam : " + team +" Win!!";
				String names = "";
				for(Player p : Rule.team.getTeam(win).get(0).getPlayer()) {
					names+=p.getName()+",";
				}
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.sendTitle(name, "" +names,40,20,40);
					MSUtil.resetbuff(player);
					player.setGameMode(GameMode.ADVENTURE);
					player.setMaxHealth(20);
					player.setHealth(20);
				}
				gameEnd();
			}
		} else if(Rule.c.size() == 1 && AniRandomSkill != null) {
			for(Player p : Rule.c.keySet()) {
				if(Rule.buffmanager.selectBuffType(p, BuffType.HEADCC) != null) {
					for(Buff buff : Rule.buffmanager.getHashMap().get(p).getBuff()) {
						buff.stop();
					}
				}
			}

			Player win = (Player) Rule.c.keySet().toArray()[0];
			Rule.c.get(win).info();
			Rule.playerinfo.get(win).addcradit((AniRandomSkill.player-1)*3,Main.GetText("main:msg103"));
			
			int number = Rule.c.get(win).getCode();
			Rule.Var.addInt(win.getName()+".c"+(number%1000)+"Win",1);
			Rule.Var.addInt("ARSystem.c"+(number%1000)+"Win",1);
			win.performCommand("c removeall");
			String name = "§e§l[No."+(number%1000)+"]§b"+Main.GetText("c"+number+":name1")+" "+Main.GetText("c"+number+":name2");
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.sendTitle(name + " Win ", win.getName()+ " | " + (Rule.c.get(win).score%10000) + " Score",40,20,40);
				MSUtil.resetbuff(player);
				player.setGameMode(GameMode.ADVENTURE);
				player.setMaxHealth(20);
				player.setHealth(20);
			}
			Rule.playerinfo.get(win).save();
			gameEnd();
		}
		if(Rule.c.size() == 0) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				for(Player player : Bukkit.getOnlinePlayers()) {
					MSUtil.resetbuff(player);
					player.setGameMode(GameMode.ADVENTURE);
					player.setMaxHealth(20);
					player.setHealth(20);
				}
				killall();
				ARSystem.AniRandomSkill = null;
				if(Rule.buffmanager != null) Rule.buffmanager.clear();
				Rule.buffmanager = new BuffManager();
				Rule.removePlayers.clear();
				Rule.c.clear();
				Map.loby();
			},0);
		}
	}
	
	static public void gameEnd() {
		HashMap<Player, Integer> mvp = ARSystem.AniRandomSkill.playerkill;
		Player mvps = null;
		int mvpk = 0;
		if(mvp != null && mvp.size() > 0) {
			for(Player p : mvp.keySet()) {
				if(mvpk < mvp.get(p)) {
					mvps = p;
					mvpk = mvp.get(p);
				}
			}
			String nm = "§e§l[MVP]§b"+Main.GetText("c"+ ARSystem.AniRandomSkill.startplayer.get(mvps)+":name1")+" "+Main.GetText("c"+ARSystem.AniRandomSkill.startplayer.get(mvps)+":name2");
			String nn = mvps.getName()+ " | " + mvpk + " Kill";
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.sendTitle(nm, nn, 40, 20, 40);
				}
			},60);
		}
		killall();
		ARSystem.AniRandomSkill = null;
		Rule.c.clear();
		Map.loby();
		if(Rule.buffmanager != null) Rule.buffmanager.clear();
		Rule.buffmanager = new BuffManager();
		if(aliveblock.Main.Aliveblock != null) {
			for(ABlock block : aliveblock.Main.Aliveblock) {
				block.removeBlock(5);
			}
			aliveblock.Main.Aliveblock.clear();
		}
	}
	
	static public void GameStop() {
		RandomPlayer().performCommand("c removeall");
		if(Rule.buffmanager != null) Rule.buffmanager.clear();
		Rule.buffmanager = new BuffManager();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.sendTitle(" GameSet ","TimeOver" ,40,20,40);
			MSUtil.resetbuff(player);
			player.setGameMode(GameMode.ADVENTURE);
			player.setMaxHealth(20);
			player.setHealth(20);
		}
		killall();
		ARSystem.AniRandomSkill = null;
		Rule.c.clear();
		Map.loby();
		if(aliveblock.Main.Aliveblock != null) {
			for(ABlock block : aliveblock.Main.Aliveblock) {
				block.removeBlock(5);
			}
			aliveblock.Main.Aliveblock.clear();
		}
	}
	
	static public int getPlayerCount() {
		return Rule.c.size();
	}
	
	static public void heal(LivingEntity e,double i) {
		if(Rule.c.get(e) != null && Rule.buffmanager.isBuff(e, "noheal")) {
			Holo.create(e.getLocation(),"§4§l[✘]No Heal!",10,new Vector(0,0.05,0));
			return;
		}
		if(e.getMaxHealth() - e.getHealth() > i) {
			e.setHealth(e.getHealth()+i);
		} else {
			e.setHealth(e.getMaxHealth());
		}
	}
	
	static public void overheal(LivingEntity e,double i) {
		if(Rule.c.get(e) != null && Rule.buffmanager.isBuff(e, "noheal")) {
			Holo.create(e.getLocation(),"§4§l[✘]No Heal!",10,new Vector(0,0.05,0));
			return;
		}
		double damage = e.getMaxHealth() - e.getHealth();
		if(damage > i) {
			e.setHealth(e.getHealth()+i);
		} else {
			if(Rule.c.get(e) != null) {
				Rule.buffmanager.selectBuffAddValue(e, "plushp",(float) (i-damage));
			}
			e.setHealth(e.getMaxHealth());
		}
	}
	
	static public void damage(Player e,double i) {
		if(e.getHealth() > 0) {
			e.setHealth(e.getHealth()-i);
		} else {
			e.damage(2,e);
		}
	}
	
	static public void playSound(Entity entity,String s) {
		entity.getWorld().playSound(entity.getLocation(), s, 1, 1);
	}
	static public void playSound(Player entity,String s) {
		entity.playSound(entity.getLocation(), s, 10000, 1);
	}
	static public void playSound(Entity entity,String s,float pitch,float size) {
		entity.getWorld().playSound(entity.getLocation(), s, size, pitch);
	}
	static public void playSound(Entity entity,String s,float pitch) {
		entity.getWorld().playSound(entity.getLocation(), s, 2, pitch);
	}
	static public void playSound(Player entity,String s,float pitch) {
		entity.playSound(entity.getLocation(), s, 10000, pitch);
	}
	static public void playSound(Player entity,Player e,String s) {
		entity.playSound(entity.getLocation(), s, 10000, 1);
		e.playSound(e.getLocation(), s, 10000, 1);
	}
	static public void playSound(Player entity,Entity e,String s) {
		entity.playSound(entity.getLocation(), s, 10000, 1);
		e.getWorld().playSound(e.getLocation(), s, 1, 1);
	}
	
	static public void playSound(List<Entity> entitys,String s) {
		for(Entity entity : entitys) {
			entity.getWorld().playSound(entity.getLocation(), s, 10000, 1);
		}
	}
	public static void playSound(Player[] entitys, String s) {
		for(Player entity : entitys) {
			entity.playSound(entity.getLocation(), s, 10000, 1);
		}
	}
	public static void playSoundAll(String s) {
		for(Player entity : Bukkit.getOnlinePlayers()) {
			entity.playSound(entity.getLocation(), s, 10000, 1);
		}
	}
	public static void playSoundAll(String s,float f) {
		for(Player entity : Bukkit.getOnlinePlayers()) {
			entity.playSound(entity.getLocation(), s, 10000, f);
		}
	}
	public static void potion(LivingEntity e,int i,int j,int k) {
		if(e.getPotionEffect(PotionEffectType.getById(i)) != null) {
			e.removePotionEffect(PotionEffectType.getById(i));
		}
		e.addPotionEffect(new PotionEffect(PotionEffectType.getById(i),j,k));
	}
	static public void add(List<Entity> entitys,Entity e){
		if(entitys.indexOf(e) == -1) {
			entitys.add(e);
		}
	}
	static public void add(List<Entity> entitys,List<Entity> e){
		for(Entity es : e) {
			if(entitys.indexOf(es) == -1) {
				entitys.add(es);
			}
		}
	}
	
	static public boolean isTarget(Entity target,Entity caster) {
		if(target == caster) return false;
		if(!(target instanceof LivingEntity)) return false;
		if(target instanceof Player && ((Player) target).getGameMode() == GameMode.SPECTATOR) return false;
		if(target instanceof ArmorStand) return false;
		if(target instanceof Player && caster instanceof Player && Rule.team.isTeam((Player)target, (Player)caster)) return false;
		
		return true;
	}
	
	static public boolean isTarget(Entity target,Entity caster,types.box box) {
		if(!(target instanceof LivingEntity)) return false;
		if(target == caster && box != box.MYALL) return false;
		if(target instanceof Player && ((Player) target).getGameMode() == GameMode.SPECTATOR) return false;
		if(target instanceof ArmorStand) return false;
		if(box != box.ALL && box != box.MYALL) {
			if(box == box.TARGET && target instanceof Player && caster instanceof Player && Rule.team.isTeam((Player)target, (Player)caster)) return false;
			if(box == box.TEAM) {
				if(target instanceof Player && caster instanceof Player && Rule.team.isTeam((Player)target, (Player)caster)) {
					return true;
				} else {
					return false;
				}
			}
		}
			
		return true;
	}
	static public void playerRotate(Player player,float yaw,float pitch){
		Location l = player.getLocation();
		Set<EnumPlayerTeleportFlags> sn = new HashSet<EnumPlayerTeleportFlags>();
		sn.add(EnumPlayerTeleportFlags.X);
		sn.add(EnumPlayerTeleportFlags.Y);
		sn.add(EnumPlayerTeleportFlags.Z);
		PacketPlayOutPosition pl = new PacketPlayOutPosition(0,0,0,yaw,pitch,sn,1);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(pl);
	}
	
	static public void playerAddRotate(Player player,float yaw,float pitch){
		Location l = player.getLocation();
		Set<EnumPlayerTeleportFlags> sn = new HashSet<EnumPlayerTeleportFlags>();
		sn.add(EnumPlayerTeleportFlags.X);
		sn.add(EnumPlayerTeleportFlags.Y);
		sn.add(EnumPlayerTeleportFlags.Z);
		sn.add(EnumPlayerTeleportFlags.Y_ROT);
		sn.add(EnumPlayerTeleportFlags.X_ROT);
		PacketPlayOutPosition pl = new PacketPlayOutPosition(0,0,0,yaw,pitch,sn,1);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(pl);
	}
	
	static public void playerAddLocation(Player player,Vector v){
		Location l = player.getLocation();
		Set<EnumPlayerTeleportFlags> sn = new HashSet<EnumPlayerTeleportFlags>();
		sn.add(EnumPlayerTeleportFlags.X);
		sn.add(EnumPlayerTeleportFlags.Y);
		sn.add(EnumPlayerTeleportFlags.Z);
		sn.add(EnumPlayerTeleportFlags.Y_ROT);
		sn.add(EnumPlayerTeleportFlags.X_ROT);
		PacketPlayOutPosition pl = new PacketPlayOutPosition(v.getX(),v.getY(),v.getZ(),0,0,sn,1);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(pl);
	}
	
	static public List<Entity> locEntity(Location loc,Vector range,Entity caster) {
		List<Entity> e = new ArrayList<Entity>();
		Collection<Entity> e2 = loc.getWorld().getNearbyEntities(loc, range.getX(), range.getY(), range.getZ());
		for(Entity entity : e2) {
			if(entity instanceof LivingEntity) {
				if(caster != entity && !(entity instanceof ArmorStand) && !(entity instanceof Player && ((Player)entity).getGameMode() == GameMode.SPECTATOR)) {
					e.add(entity);
				}
			}
		}
		return e;
	}
	
	static public String code(String str) {
		Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String s = "" + simpleDateFormat.format(nowDate);
        if(str != null) s= str;
        
        long a = Integer.parseInt(s);
        a = a* ((a%10)+1) * (a%1000/10);
        a = a* (a%1000);

        return Long.toHexString(a).substring(0,7).toUpperCase();
	}
	
	static public void code(Player p, String s) {
		Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String ss = "" + simpleDateFormat.format(nowDate);
        long a = Integer.parseInt(ss);
        int b = (int)a;
        a = a* ((a%10)+1) * (a%1000/10);
        a = a* (a%1000);
        int i = Rule.Var.Loadint(p.getName()+".info.CodeDay");
        try {
	        if(s.equals(Long.toHexString(a).substring(0,7).toUpperCase())) {
	        	if(b > i) {
	        		if(AMath.random(20) == 1) Rule.playerinfo.get(p).tropy(0, 25);
	        		if(AMath.random(20) == 1) Rule.playerinfo.get(p).tropy(0, 24);
	        		if(AMath.random(20) == 1) Rule.playerinfo.get(p).tropy(0, 23);
	        		if(AMath.random(20) == 1) Rule.playerinfo.get(p).tropy(0, 22);
		        	Rule.Var.setInt(p.getName()+".info.CodeDay",b);
					p.sendMessage("§a§l[ARSystem] : §a§l "+Main.GetText("main:msg25") + " + Point 300");
					Rule.playerinfo.get(p).addcradit(300, Main.GetText("main:msg108"));
					ARSystem.playSound(p,"0event3");
	        	} else {
	        		p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror10"));
	        	}
				return;
	        }
        } catch(Exception e) {
        	
        }
        
        if(s.equals("I'mSuperStar")) {
        	if(!(boolean)Rule.Var.Load(p.getName()+".info.Code1")) {
        		Rule.Var.open(p.getName()+".info.Code1", true);
        		p.sendMessage("§a§l[ARSystem] : §a§l "+Main.GetText("main:msg25") + " + Point 3000");
        		Rule.playerinfo.get(p).addcradit(3000, Main.GetText("main:msg108"));
        		Rule.playerinfo.get(p).tropy(0, 31);
        		ARSystem.playSound(p,"0event3");
        	} else {
        		p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror10"));
        	}
			return;
        }
        
        if(s.equals("heIpGames")) {
        	if(!(boolean)Rule.Var.Load(p.getName()+".info.Code2")) {
        		Rule.Var.open(p.getName()+".info.Code2", true);
        		p.sendMessage("§a§l[ARSystem] : §a§l "+Main.GetText("main:msg25") + " + Point 2000");
        		Rule.playerinfo.get(p).addcradit(2000, Main.GetText("main:msg108"));
        		Rule.playerinfo.get(p).tropy(0, 14);
        		ARSystem.playSound(p,"0event3");
        	} else {
        		p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror10"));
        	}
			return;
        }
        
        if(s.equals("YukI")) {
        	if(!(boolean)Rule.Var.Load(p.getName()+".info.Code3")) {
        		Rule.Var.open(p.getName()+".info.Code3", true);
        		p.sendMessage("§a§l[ARSystem] : §a§l "+Main.GetText("main:msg25") + " + Point 1000");
        		Rule.playerinfo.get(p).addcradit(1000, Main.GetText("main:msg108"));
        		ARSystem.playSound(p,"0event3");
        	} else {
        		p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror10"));
        	}
			return;
        }
        
        if(s.equals("SayYooHoo")) {
        	if(!(boolean)Rule.Var.Load(p.getName()+".info.Code4")) {
        		Rule.Var.open(p.getName()+".info.Code4", true);
        		p.sendMessage("§a§l[ARSystem] : §a§l "+Main.GetText("main:msg25") + " + Point 1000");
        		Rule.playerinfo.get(p).addcradit(1000, Main.GetText("main:msg108"));
        		ARSystem.playSound(p,"0event3");
        	} else {
        		p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror10"));
        	}
			return;
        }
        
        if(s.equals("Rocent")) {
        	if(!(boolean)Rule.Var.Load(p.getName()+".info.Code5")) {
        		Rule.Var.open(p.getName()+".info.Code5", true);
        		p.sendMessage("§a§l[ARSystem] : §a§l "+Main.GetText("main:msg25") + " + Point 48");
        		Rule.playerinfo.get(p).addcradit(48, Main.GetText("main:msg108"));
        		Rule.playerinfo.get(p).tropy(0, 26);
        		ARSystem.playSound(p,"0event3");
        	} else {
        		p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror10"));
        	}
			return;
        }
        
        if(s.equals("Hezult")) {
        	if(!(boolean)Rule.Var.Load(p.getName()+".info.Code6")) {
        		Rule.Var.open(p.getName()+".info.Code6", true);
        		p.sendMessage("§a§l[ARSystem] : §a§l "+Main.GetText("main:msg25") + " + Point 193");
        		Rule.playerinfo.get(p).addcradit(52, Main.GetText("main:msg108"));
        		Rule.playerinfo.get(p).tropy(0, 27);
        		ARSystem.playSound(p,"0event3");
        	} else {
        		p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror10"));
        	}
			return;
        }
        if(s.equals("CA00T7")) {
        	if(!(boolean)Rule.Var.Load(p.getName()+".info.Code7")) {
        		Rule.Var.open(p.getName()+".info.Code7", true);
        		p.sendMessage("§a§l[ARSystem] : §a§l "+Main.GetText("main:msg25") + " + Point 777");
        		Rule.playerinfo.get(p).addcradit(777, Main.GetText("main:msg108"));
        		Rule.playerinfo.get(p).tropy(0, 28);
        		ARSystem.playSound(p,"0event3");
        	} else {
        		p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror10"));
        	}
			return;
        }
       p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror9"));
       return;
	}
	
	static public Player RandomPlayer() {
		return (Player) Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1];
	}
	public static Player RandomPlayer(Player player) {
		Player p = (Player) Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1];
		for(int i = 0; i <1000; i++) {
			if(p == player && isTarget(p, player)) {
				p = (Player) Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1];
			} else {
				break;
			}
		}
		return p;
	}
	
	static public List<Entity> box(Entity et, Vector vt,types.box box) {
		List<Entity> entity = new ArrayList<Entity>();

		entity = et.getNearbyEntities(vt.getX(),vt.getY(),vt.getZ());

		if(entity == null || entity.size() <= 0) return entity;
		
		List<Entity> en = new ArrayList<Entity>();
		
		for(Entity e : entity) {
			if(!isTarget(e, et,box)) {
				en.add(e);
			}
		} 
		for(Entity e : en) {
			entity.remove(e);
		}
		return entity;
	}
	
	static public Entity boxRandom(Entity et, Vector vt,types.box box) {
		List<Entity> entity = new ArrayList<Entity>();

		entity = et.getNearbyEntities(vt.getX(),vt.getY(),vt.getZ());
		if(entity == null || entity.size() <= 0) return null;
		
		List<Entity> en = new ArrayList<Entity>();
		if(entity == null) return null;
		
		for(Entity e : entity) {
			if(!isTarget(e, et,box)) {
				en.add(e);
			}
		} 
		for(Entity e : en) {
			entity.remove(e);
		}
		if(entity.size() <= 0) return null;
		
		return entity.get(AMath.random(entity.size())-1);
	}
	
	static public void giveBuff(LivingEntity e, Buff buff,int time, double value) {
		if(Rule.buffmanager.getHashMap().get(e) == null) {
			Rule.buffmanager.getHashMap().put(e, new EntityBuffManager(e));
		}
		if(buff.OnlyOne()) {
			Buff b = Rule.buffmanager.selectBuff(e, buff.getName());

			if(b == null) {
				buff.setTime(time);
				buff.setValue(value);
				Rule.buffmanager.getHashMap().get(e).getBuff().add(buff);
			} else {
				b.setValue(value);
				b.setTime(time);
			}
		} else {
			buff.setTime(time);
			buff.setValue(value);
			Rule.buffmanager.getHashMap().get(e).getBuff().add(buff);
		}
	}
	
	static public void giveBuff(LivingEntity e, Buff buff,int time) {
		if(Rule.buffmanager.getHashMap().get(e) == null) {
			Rule.buffmanager.getHashMap().put(e, new EntityBuffManager(e));
		}
		if(buff.OnlyOne()) {
			Buff b = Rule.buffmanager.selectBuff(e, buff.getName());
			
			if(b == null) {
				buff.setTime(time);
				Rule.buffmanager.getHashMap().get(e).getBuff().add(buff);
			} else {
				b.setTime(time);
			}
		} else {
			buff.setTime(time);
			Rule.buffmanager.getHashMap().get(e).getBuff().add(buff);
		}
	}
	
	static public void addBuff(LivingEntity e, Buff buff,int time, double value) {
		if(Rule.buffmanager.getHashMap().get(e) == null) {
			Rule.buffmanager.getHashMap().put(e, new EntityBuffManager(e));
		}
		if(buff.OnlyOne()) {
			Buff b = null;

			b = Rule.buffmanager.selectBuff(e, buff.getName());

			if(b == null) {
				giveBuff(e,buff,time,value);
			} else {
				b.addTime(time);
				b.addValue(value);
			}
		} else {
			buff.setTime(time);
			buff.setValue(value);
			Rule.buffmanager.getHashMap().get(e).getBuff().add(buff);
		}
	}
	
	static public void addBuff(LivingEntity e, Buff buff,int time) {
		if(Rule.buffmanager.getHashMap().get(e) == null) {
			Rule.buffmanager.getHashMap().put(e, new EntityBuffManager(e));
		}
		if(buff.OnlyOne()) {
			Buff b = null;
			b = Rule.buffmanager.selectBuff(e, buff.getName());

			if(b == null) {
				giveBuff(e,buff,time);
			} else {
				b.addTime(time);
			}
		} else {
			buff.setTime(time);
			Rule.buffmanager.getHashMap().get(e).getBuff().add(buff);
		}
	}

	
	static public List<Entity> boxS(Entity et, Vector vt,types.box box) {
		List<Entity> entity = et.getNearbyEntities(vt.getX(),vt.getY(),vt.getZ());
		Entity[] p = new Entity[entity.size()];
		int count = 0;
		
		for(Entity e : entity) {
			if(isTarget(e, et,box)) {
				p[count++] = e;
			}
		} 
		if(p.length == 0) return null;
		if(p[0] == null) return null;
		if(count == 1) return entity;
		
		for(int i = 0; i < entity.size(); i++) {
			for(int j = 0; j < i; j++) {
				if(p[i].getLocation().distance(et.getLocation()) < p[j].getLocation().distance(et.getLocation())) {
					Entity ps;
					ps = p[i];
					p[i] = p[j];
					p[j] = ps;
				}
			}
		}
		entity.clear();
		for(Entity e : p) entity.add(e);
		return entity;
	}
	
	static public Entity boxSOne(Entity et, Vector vt,types.box box) {
		List<Entity> entity = et.getNearbyEntities(vt.getX(),vt.getY(),vt.getZ());
		Entity[] p = new Entity[entity.size()];
		int count = 0;
		for(Entity e : entity) {
			if(isTarget(e, et, box)) {
				p[count++] = e;
			}
		} 
		if(p.length == 0) return null;
		if(p[0] == null) return null;
		if(count == 1) return p[0];
		
		for(int i = 0; i < count; i++) {
			for(int j = 0; j < i; j++) {
				if(p[i].getLocation().distance(et.getLocation()) < p[j].getLocation().distance(et.getLocation())) {
					Entity ps;
					ps = p[i];
					p[i] = p[j];
					p[j] = ps;
				}
			}
		}
		return p[0];
	}
	static public void spellCast(Player p,String name) {
		Spell spell = MagicSpells.getSpellByInternalName(name);
		if(spell != null) {
			spell.cast(p);
		}
	}
	static public void spellCast(Player p,Entity e,String name) {
		Spell spell = MagicSpells.getSpellByInternalName(name);
		if(spell != null && spell instanceof TargetedEntitySpell) {
			((TargetedEntitySpell)spell).castAtEntity(p, (LivingEntity)e, 1);
		}
	}
	static public void spellLocCast(Player p,Location e,String name) {
		Spell spell = MagicSpells.getSpellByInternalName(name);
		if(spell != null && spell instanceof TargetedLocationSpell) {
			((TargetedLocationSpell)spell).castAtLocation(p, e, 1);
		}
	}
	static public void killall() {
		 for (World world : Bukkit.getWorlds()){
	        	for(org.bukkit.entity.Entity entity : world.getEntities()) {
	        		 if ((entity.getType() != EntityType.PLAYER) && 
	        			(entity.getType() != EntityType.ITEM_FRAME) &&
	        			(entity.getType() != EntityType.PAINTING)) {
		        		entity.remove();
	        		}
	        	}
	     }
	}

}
