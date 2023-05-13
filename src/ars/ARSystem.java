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
import mode.MNormal;
import mode.ModeBase;
import net.minecraft.server.v1_12_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutPosition.EnumPlayerTeleportFlags;
import types.BuffType;
import types.GameModes;
import types.MapType;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.MSUtil;
import util.Map;

public class ARSystem {
	public static ARSinfo AniRandomSkill;
	static boolean chars[] = null;
	
	public static boolean ban = false;
	public static int time = 12;
	public static int starttime = 30;
	public static List<ModeBase> gameMode = new ArrayList<>();
	public static List<String> selectGameMode = new ArrayList<>();
	public static boolean gameMode2 = true;
	public static int serverOne = 0;
	
	static public void Start(int mapnumer) {
		int i = 0;
		int max = GetChar.getCount();
		Bgm.bgmlock = false;
		Bgm.rep = true;
		
		Rule.c.clear();
		Rule.team.reload();
		killall();
		gameMode2 = true;
		
		if(Rule.buffmanager != null) Rule.buffmanager.clear();
		Rule.buffmanager = new BuffManager();
		
		if(aliveblock.Main.Aliveblock != null) {
			for(ABlock block : aliveblock.Main.Aliveblock) {
				block.removeBlock(5);
			}
			aliveblock.Main.Aliveblock.clear();
		}
		gameMode.clear();
		
		
		for(String str : selectGameMode) gameMode.add(GameModes.getGameModes(str));
		for(ModeBase mb : gameMode) mb.initialize();
		for(String str : selectGameMode) {
			boolean add = true;
			for(ModeBase mb : gameMode) {
				if(mb.getModeName().equals(str)) {
					add = false;
				}
			}
			if(add) gameMode.add(GameModes.getGameModes(str));
		}
		
        AniRandomSkill = new ARSinfo(time,gameMode);

		Bgm.randomBgm();
		AniRandomSkill.player = 0;
		float score = 0;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(Rule.playerinfo.get(player).gamejoin) {
				AniRandomSkill.player++;
				score += Rule.playerinfo.get(player).getScore();
			}
		}
		
        for(ModeBase mb : gameMode) {
			mb.option();
		}
        
		if(Map.mapid == 0) {
			Map.randomMap(mapnumer);
		}
		
		Map.playerTpall();
		
		if(score != 0) {
			score /= AniRandomSkill.player;
			AniRandomSkill.gamescore = score;
		}
		
		Bukkit.broadcastMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:info25")+" "+Math.round(score));
		chars = new boolean[GetChar.getCount()+1];
		
		if(AniRandomSkill.player > 1 || (Bukkit.getOnlinePlayers().size() == 1 && AniRandomSkill.player == 1 )) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(Rule.c.get(player) == null) {
					if(Rule.playerinfo.get(player).gamejoin) {
						Rule.playerinfo.get(player).addcradit(1,Main.GetText("main:msg106"));
						if((Rule.pick && Rule.playerinfo.get(player).playerc > 0) && GetChar.isBan(Rule.playerinfo.get(player).playerc-1)) {
							Rule.c.put(player, GetChar.get(player, Rule.gamerule, ""+Rule.playerinfo.get(player).playerc));
						} else {
							int j = 0;
							while(j < 1000) {
								i = (int) (Math.random()*max);
								
								if(Rule.playerinfo.get(player).playchar == i) continue;
								if(Rule.playerinfo.get(player).playerc > 0 && AMath.random(100) <= Integer.parseInt(Main.GetText("general:pick_chance"))) {
									i = Rule.playerinfo.get(player).playerc-1;
								}
								if(!chars[i] && GetChar.isBan(i)) {
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
			if(!chars[i] && GetChar.isBan(i)) {
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
	
	static public void redyMode(String modename) {
		if(selectGameMode.contains(modename)){
			selectGameMode.remove(modename);
		} else {
			List<String> lists = new ArrayList<String>();
			for(String list : selectGameMode) lists.add(list);
			for(String list : lists) {
				if(GameModes.getGameModes(list).IsOnlyOne()){
					selectGameMode.remove(list);
				}
			}
			if(GameModes.getGameModes(modename).IsOne()) {
				for(String list : lists) {
					if(GameModes.getGameModes(list).IsOne()){
						selectGameMode.remove(list);
					}
				}
			}
			if(GameModes.getGameModes(modename).IsOnlyOne()) {
				selectGameMode.clear();
			}
			selectGameMode.add(modename);
		}
	}
	
	static public boolean isGameMode(String name) {
		if(AniRandomSkill == null) return false;
		for(ModeBase bm : AniRandomSkill.modes) {
			if(name.equals(bm.getModeName())) return true;
		}
		return false;
	}

	static public void addGameMode(ModeBase mode) {
		if(AniRandomSkill == null) return;
		AniRandomSkill.addMode(mode);
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
	
	static public List<Player> getReadyPlayer() {
		List<Player> players = new ArrayList<>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(Rule.playerinfo.get(player).gamejoin) {
				players.add(player);
			}
		}
		return players;
	}
	
	static public void Stop() {
		if(Rule.c.size() == 1 && AniRandomSkill != null) {
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
		if(AniRandomSkill != null) {
			for(ModeBase mb : AniRandomSkill.modes) {
				mb.end();
			}
		}
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
	
	static public void opCommand(String str) {
		Player p = ARSystem.RandomOnlinePlayer();
		boolean isop = p.isOp();
		ARSystem.RandomOnlinePlayer().performCommand(str);
		if(!isop) p.setOp(false);
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
	static public Player RandomOnlinePlayer() {
		return (Player) Bukkit.getOnlinePlayers().toArray()[AMath.random(Bukkit.getOnlinePlayers().size())-1];
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
	static public List<Entity> PlayerBeamBox(Player player,float rangeblock, float size, types.box box){
		List<Entity> entity = new ArrayList<Entity>();
		Location loc = player.getLocation().clone();
		for(float i=0;i<rangeblock;i++) {
			loc.add(loc.getDirection());
			if(!loc.clone().add(0,1,0).getBlock().isEmpty()) {
				i = rangeblock+1;
			} else {
				for (LivingEntity e : player.getWorld().getLivingEntities()) {
					if(e.getLocation().distance(loc) <= size && e != player) {
						if(isTarget(e, player ,box) && !entity.contains(e)) {
							entity.add(e);
						}
					}
				}
			}
		}
		return entity;
	}
	static public List<Player> PlayerOnlyBeamBox(Player player,float rangeblock, float size, types.box box){
		List<Player> entity = new ArrayList<Player>();
		Location loc = player.getLocation().clone();
		for(float i=0;i<rangeblock;i++) {
			loc.add(loc.getDirection());
			if(!loc.clone().add(0,1,0).getBlock().isEmpty()) {
				i = rangeblock+1;
			} else {
				for (Player e : Bukkit.getOnlinePlayers()) {
					if(e.getLocation().distance(loc) <= size && e != player && e.getGameMode() != GameMode.SPECTATOR ) {
						if(isTarget(e, player ,box) && !entity.contains(e)) {
							entity.add(e);
						}
					}
				}
			}
		}
		return entity;
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
