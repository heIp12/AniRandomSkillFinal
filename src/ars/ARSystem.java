package ars;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
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
import ars.gui.G_ModeSeting;
import buff.Buff;
import event.FixedDealEvent;
import event.Skill;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import item.list1.itemBase;
import manager.Bgm;
import manager.BuffManager;
import manager.EntityBuffManager;
import manager.ItemManager;
import mode.MEvent;
import mode.MItem;
import mode.MNormal;
import mode.ModeBase;
import net.minecraft.server.v1_12_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutPosition.EnumPlayerTeleportFlags;
import types.BuffType;
import types.GameModes;
import types.ItemList;
import types.MapType;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

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
	public static HashMap<Player,ItemManager> playerItem = new HashMap<>();
	static Random rand;
	public static G_ModeSeting modeChat;
	public static Player modeChatPlayer;
	public static int winstop = 0;
	public static boolean E_sterEgg = false;
	
	static public void addItem(Player p, int code) {
		if(playerItem.get(p) == null) return;
		
		if(code < 0 || (!ItemList.orignal.keySet().contains(code) && code < 200)) {
			p.sendMessage("§a§l[ARSystem] §f Item Surch Error");
			return;
		}
		if(playerItem.get(p).items.size() < Rule.playerinfo.get(p).itemcount) {
			itemBase item = ItemList.getItem(code,p);
			if(item == null) {
				p.sendMessage("§a§l[ARSystem] §f Item Surch Error");
				return;
			}

			if(code == 100014) {
				p.sendMessage("§a§l[ARSystem] §f: "+Text.get("item:100014").replace("??", MEvent.name) + Text.get("item:set"));
			} else {
				if(code >= 10000 && code < 100000) {
					p.sendMessage("§a§l[ARSystem] §f: " + Text.get("item2:"+(code%10000)) + Text.get("item:set"));
				} else {
					p.sendMessage("§a§l[ARSystem] §f: "+Text.get("item:"+code) + Text.get("item:set"));
				}
			}
			playerItem.get(p).items.add(item);
			playerItem.get(p).onAddItem(item);
			ItemList.upgrad(playerItem.get(p).items,p);
		} else {
			p.sendMessage("§a§l[ARSystem] §f "+ Text.get("item:setno"));
		}
	}
	
	static public void removeItem(Player p, int code) {
		itemBase item = null;
		for(itemBase it : playerItem.get(p).items) {
			if(it.getCode() == code) {
				item = it;
				break;
			}
		}
		if(item != null) {
			p.sendMessage("§a§l[ARSystem] §f: "+ item.getItem().getItemMeta().getDisplayName() + Text.get("item:remove"));
			item.itemRemove();
			playerItem.get(p).items.remove(item);
		}
	}
	static public void removeItemAll(Player p) {
		playerItem.get(p).Remove();
		p.sendMessage("§a§l[ARSystem] §f: " + Text.get("item:removeAll"));
	}
	
	static public void Start(int mapnumer) {
		int i = 0;
		int max = GetChar.getCount();
		Bgm.bgmlock = false;
		Bgm.rep = true;
		winstop = 0;
		
		Rule.c.clear();
		Rule.team.reload();
		killall();
		gameMode2 = true;
		remete.clear();
		Skill.remete.clear();
		E_sterEgg = true;
		
		if(Rule.buffmanager != null) Rule.buffmanager.clear();
		Rule.buffmanager = new BuffManager();
		Rule.mobmanager.Reset();
		
		if(aliveblock.Main.Aliveblock != null) {
			for(ABlock block : aliveblock.Main.Aliveblock) {
				block.removeBlock(5);
			}
			aliveblock.Main.Aliveblock.clear();
		}
		playerItem.clear();
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

		if(ARSystem.AniRandomSkill != null) {
			for(ModeBase m : ARSystem.AniRandomSkill.modes) {
				m.end();
			}
		}
		
        AniRandomSkill = new ARSinfo(time,gameMode);

		if(Bgm.nextbgm.size() <= 0) {
			Bgm.randomBgm();
		} else {
			Bgm.setBgm(Bgm.nextbgm.get(0));
			Bgm.nextbgm.remove(0);
		}
		
		AniRandomSkill.player = 0;
		float score = 0;
		for(Player player : RandomPlayers()) {
			player.setCustomName(player.getName());
			player.setDisplayName(player.getName());
			player.setPlayerListName(player.getName());
			
			Rule.playerinfo.get(player).gold = 0;
			if(isGameMode("item") &&(boolean)Rule.Var.Load("System.mode.item."+1)) {
				Rule.playerinfo.get(player).gold += Rule.Var.Loadint("System.mode.item."+5);
			}
			if(Rule.playerinfo.get(player).gamejoin) {
				AniRandomSkill.player++;
				score += Rule.playerinfo.get(player).getScore();
				playerItem.put(player, new ItemManager(player));
				Rule.playerinfo.get(player).itemcount = 8;
			}
		}
		
        for(ModeBase mb : gameMode) {
			mb.option();
		}
        
		if(Map.mapid == 0) {
			Map.randomMap(mapnumer);
		}
		
		Map.playerTpall();
		for(Entity e : ARSystem.RandomOnlinePlayer().getWorld().getEntities()){
			if(e instanceof ArmorStand) {
				e.remove();
			}
		}
		if(score != 0) {
			score /= AniRandomSkill.player;
			AniRandomSkill.gamescore = score;
		}
		
		Bukkit.broadcastMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:info25")+" "+Math.round(score));
		chars = new boolean[GetChar.getCount()+1];
		
		if(AniRandomSkill.player >= Text.getI("general:start_player") || (Bukkit.getOnlinePlayers().size() == 1 && AniRandomSkill.player == 1 )) {
			for(Player player : RandomPlayers()) {
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
		p.getInventory().clear();
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
			Location loc = p.getLocation();
			if(isGameMode("item")) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
					if(Rule.playerinfo.get(p).gold > 0 && Rule.c.size() > 2) {
						int gold = Rule.playerinfo.get(p).gold + Rule.Var.Loadint("System.mode.item."+7);
						Rule.playerinfo.get(p).gold = 0;
						int i = 0;
						while(gold > 0) {
							i++;
							int gd = 0;
							if(gold > 1000) {
								gd=1000;
							} else if(gold > 100) {
								gd=100;
							} else if(gold > 10) {
								gd=10;
							}
							gold-=gd;
							String g = ""+gd;
							Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
								ARSystem.spellLocCast(NpcPlayer.npc(loc), loc.clone().add(0,1,0), "coin"+g);
							},i*2);
							if(i > 50) {
								break;
							}
						}
					}
				
				},10);
			}
			if(Rule.playerinfo.get(e) != null) p.performCommand("c death"+Rule.playerinfo.get(e).kille);
			
		}
		
		for(Player playr: Rule.c.keySet()) {
			ARSystem.playerItem.get(playr).onDeath(p,e);
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
		
		if(AniRandomSkill != null) AniRandomSkill.PlayerDeath(p, e);
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
	static public List<Player> getPlayers() {
		List<Player> players = new ArrayList<>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			players.add(player);
		}
		return players;
	}
	static public void Stop() {
		if(winstop <= 0) {
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
			} else if(Rule.c.size() == 0) {
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
		NpcPlayer.npc(Map.getCenter()).performCommand("c removeall");
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
	static public void damageText(Location e,String s,double damage) {
		int val = (int) AMath.round(damage,0);
		if(val > 20) val = 20;
		if(val <= 0) val = 1;
		
		double vector1 = (0.01*(21-val)) - AMath.random(21-val)*0.02;
		double vector3 =(0.01*(21-val)) -  AMath.random(21-val)*0.02;
		if(val > 10) vector1 = vector3 = 0;
		double vector2 = 0.55 - (val*0.05);
		if(vector2 <= 0.01) vector2 = 0.01;
		e = e.add(new Vector(0.5-AMath.random(100)*0.01,0.2-AMath.random(40)*0.01,0.5-AMath.random(100)*0.01));
		Holo.create(e,s+" "+ AMath.round(damage,2),(int)Math.min(2+((long)damage*4),200),new Vector(vector1,vector2,vector3));
	}
	static HashMap<Player,Integer> remete = new HashMap<>();
	static public FixedDealEvent fixedDamage(LivingEntity target,Player caster, double damage) {
		if(remete.get(caster) == null || remete.get(caster) <= 0) remete.put(caster, 0);
		boolean ok = false;
		remete.put(caster, remete.get(caster)+1);
		if(Rule.c.get(caster) != null) damage *= Rule.c.get(caster).frist_damage;
		FixedDealEvent e = new FixedDealEvent(caster, target, (float)damage);
		if(ARSystem.playerItem.get(caster) != null) for(itemBase item : ARSystem.playerItem.get(caster).items) item.fixedDamage(e);
		if(ARSystem.playerItem.get(target) != null) for(itemBase item : ARSystem.playerItem.get(target).items) item.fixedDamage(e);
			
		if(Rule.c.get(caster) != null) {
			Rule.c.get(caster).fixeddamage(e);
		}
		if(Rule.c.get(target) != null) Rule.c.get(target).fixeddamage(e);
		if(!e.isCancelled()) {
			if(remete.get(caster) >= 5) {
				target.damage(e.getDamage(),caster);
			} else {
				if(target.getHealth() - e.getDamage() >= 1) {
					target.setHealth(target.getHealth() - e.getDamage());
					damageText(target.getLocation(),"§2§l☣ ",e.getDamage());
					if(Rule.c.get(caster) != null) {
						if(target instanceof Player) {
							Rule.c.get(caster).s_damage += e.getDamage();
						} else {
							Rule.c.get(caster).s_damage += e.getDamage()*0.2f;
						}
					}
				} else {
					e.isDeath = true;
					if(Rule.c.get(caster) != null) Rule.c.get(caster).fixeddamage(e);
					if(Rule.c.get(target) != null) Rule.c.get(target).fixeddamage(e);
					if(!e.isCancelled()) {
						Skill.remove(target, caster);
					}
				}
			}
		}
		remete.put(caster, remete.get(caster)-1);
		return e;
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
	
	static public List<Player> RandomPlayers(){
		List<Player> players = new ArrayList<>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			players.add(p);
		}
		Collections.shuffle(players);
		return players;
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
		if(rand == null) rand = new Random(Rule.openTime);
		return (Player) Rule.c.keySet().toArray()[rand.nextInt(Rule.c.size())];
	}
	
	static public Player RandomOnlinePlayer() {
		if(rand == null) rand = new Random(Rule.openTime);
		return (Player) Bukkit.getOnlinePlayers().toArray()[rand.nextInt(Bukkit.getOnlinePlayers().size())];
	}
	public static Player RandomPlayer(Player player) {
		if(rand == null) rand = new Random(Rule.openTime);
		Player p = (Player) Rule.c.keySet().toArray()[rand.nextInt(Rule.c.size())];
		for(int i = 0; i <1000; i++) {
			if(p == player || isTarget(p, player)) {
				p = (Player) Rule.c.keySet().toArray()[rand.nextInt(Rule.c.size())];
			} else {
				break;
			}
		}
		return p;
	}
	static public List<Entity> PlayerBeamV(Entity player,float rangeblock, float size, types.box box){
		List<Entity> entity = new ArrayList<Entity>();
		Location loc = player.getLocation().clone();
		for(float i=0;i<rangeblock;i++) {
			loc.add(loc.getDirection());
			for (LivingEntity e : player.getWorld().getLivingEntities()) {
				if(e.getLocation().distance(loc) <= size && e != player) {
					if(isTarget(e, player ,box) && !entity.contains(e)) {
						entity.add(e);
					}
				}
			}
		}
		return entity;
	}
	
	static public List<Entity> PlayerBeamBox(Entity player,float rangeblock, float size, types.box box){
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
	static public List<Player> PlayerOnlyBeamBox(Entity player,float rangeblock, float size, types.box box){
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
	static public List<Entity> box(Location loc,Entity caster, Vector vt,types.box box) {
		List<Entity> entity = new ArrayList<Entity>();
		for(Entity e : loc.getWorld().getNearbyEntities(loc,vt.getX(),vt.getY(),vt.getZ())) {
			entity.add(e);
		}

		if(entity == null || entity.size() <= 0) return entity;
		
		List<Entity> en = new ArrayList<Entity>();
		
		for(Entity e : entity) {
			if(!isTarget(e, caster ,box)) {
				en.add(e);
			}
		} 
		for(Entity e : en) {
			entity.remove(e);
		}
		return entity;
	}
	
	static public List<Entity> boxS(List<Entity> entity,Location loc) {
		if(entity == null) return new ArrayList<Entity>();
		if(entity.size() <= 1) return entity;
		
		Entity[] p = new Entity[entity.size()];
		for(int i =0; i < p.length; i++) p[i] = entity.get(i);
		
		for(int i = 0; i < entity.size(); i++) {
			for(int j = 0; j < i; j++) {
				if(p[i].getLocation().distance(loc) < p[j].getLocation().distance(loc)) {
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
		List<Entity> entity = box(et,vt,box);
		Entity[] p = new Entity[entity.size()];
		for(int i =0; i < p.length; i++) p[i] = entity.get(i);
		
		if(p.length == 0) return new ArrayList<Entity>();
		if(p[0] == null) return new ArrayList<Entity>();
		if(entity.size() == 1) return entity;
		
		for(int i = 0; i < entity.size(); i++) {
			for(int j = 0; j < i; j++) {
				if(p[i].getLocation().distance(et.getLocation()) > p[j].getLocation().distance(et.getLocation())) {
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
	
	static public Entity boxSOne(Entity et, Vector vt,types.box box,String remove) {
		List<Entity> entity = et.getNearbyEntities(vt.getX(),vt.getY(),vt.getZ());
		Entity[] p = new Entity[entity.size()];
		int count = 0;
		for(Entity e : entity) {
			if(isTarget(e, et, box) && !e.getName().equals(remove)) {
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
	
	static public Entity boxSPlayerOne(Entity et, Vector vt,types.box box) {
		List<Entity> entity = et.getNearbyEntities(vt.getX(),vt.getY(),vt.getZ());
		Entity[] p = new Entity[entity.size()];
		int count = 0;
		for(Entity e : entity) {
			if(isTarget(e, et, box) && (e instanceof Player)) {
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

	public static String getloc(Location loc,Location ploc) {
		Location local = ULocal.lookAt(ploc, loc);
		float yaw = local.getYaw() - ploc.getYaw();
		if (yaw > 180) {
			yaw -= 360;
		} else if (yaw < -180) {
			yaw += 360;
		}
		String locstr = "";
		if(yaw > -22.5 && yaw < 22.5) {
			locstr = "↑";
		}
		else if(yaw >= 22.5 && yaw < 67.5) {
			locstr = "↗";
		}
		else if(yaw >= 67.5 && yaw < 112.5) {
			locstr = "→";
		}
		else if(yaw >= 112.5 && yaw > 157.5) {
			locstr = "↘";
		}
		else if(yaw <= -22.5 && yaw > -67.5) {
			locstr = "↖";
		}
		else if(yaw <= -67.5 && yaw > -112.5) {
			locstr = "←";
		}
		else if(yaw <= -112.5 && yaw > -157.5) {
			locstr = "↙";
		} else {
			locstr = "↓";
		}
		
		return locstr;
	}
}
