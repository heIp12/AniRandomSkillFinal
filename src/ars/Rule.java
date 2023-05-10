package ars;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import Main.Main;
import aliveblock.ABlock;
import ars.gui.G_CharInfo;
import ars.gui.G_Menu;
import ars.gui.G_Tropy;
import ars.gui.G_UMenu;
import buff.Buff;
import chars.c.c001humen2;
import chars.c.c00main;
import chars.c.c16saki;
import chars.c.c38hajime;
import manager.AdvManager;
import manager.Bgm;
import manager.BuffManager;
import manager.ScoreBoard;
import mode.MArena;
import mode.MLoboTomy;
import mode.MSupply;
import mode.MZombie;
import mode.ModeBase;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import types.GameModes;
import util.GetChar;
import util.Map;
import util.Text;
import util.Var;

public class Rule extends JavaPlugin{
	String ver;
	private timeSystem cooldown;
	public static Plugin gamerule;
	public static Team team;
	public static BuffManager buffmanager;
	
	public static HashMap<Player, c00main> c;
	public static HashMap<Player, PlayerInfo> playerinfo;
	public static boolean auto = true;
	public static boolean pick = false;
	int starttimer = 30;
	public static Var Var;
	public static float urf = 5;
	
	public static String[] Score1 = new String[10];
	public static int[] Score2 = new int[10];
    public static long startTime = System.currentTimeMillis();
    
    static public List<Player> oplist = new ArrayList<Player>();
    
    public static List<Player> removePlayers = new ArrayList<Player>();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Event(this), this);
		Bukkit.getWorld("world").getWorldBorder().setCenter(0, 0);
		Bukkit.getWorld("world").getWorldBorder().setSize(10000,0);
		System.out.println("Start : Gamerule");
		c = new HashMap<Player,c00main>();
		playerinfo = new HashMap<Player,PlayerInfo>();
		cooldown = new timeSystem(this);
		Map.getMapinfo(0);
		gamerule = this;
		
		team = new Team();
		Var = api.Rule.var;
		buffmanager = new BuffManager();
		ARSystem.killall();
		
		for(int i = 0; i < Score1.length; i++) {
			if(!(Var.Load("info.rk"+i+".Name") instanceof Boolean && (boolean)Var.Load("info.rk"+i+".Name") == false)) {
				Score1[i] = (String) Var.Load("info.rk"+i+".Name");
				if(i < 5) {
					Score2[i] = Rule.Var.Loadint(Score1[i]+".info.Cradit");
				} else {
					Score2[i] = Var.Loadint("info.rk"+i+".Score");
				}
			}
		}
		GetChar.BanLoad();
		startTime = System.currentTimeMillis();
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(playerinfo.get(p) == null)
					playerinfo.put(p, new PlayerInfo(p));
				}
				ARSystem.selectGameMode.add("random");
				if(Rule.Var.Loadint("info.Shop.money") <= 5000) {
					Rule.Var.setInt("info.Shop.money", 5000);
				}
				Map.loby();
			}
		},0);
	}
	
	@Override
	public void onDisable() {
		ARSystem.killall();
		GetChar.BanSave();
		saveRank();
		Var.saveAll();
		System.out.println("Stop : Gamerule");
		Bgm.Exit();
		cooldown.stop();
		for(ABlock block : aliveblock.Main.Aliveblock) {
			block.remove();
		}
		aliveblock.Main.Aliveblock.clear();
	}
	
	public static void saveRank() {
		for(int i = 0; i < Score1.length; i++) {
			if(Score1[i] != null) {
				Var.Save("info.rk"+i+".Name", Score1[i]);
				Var.setInt("info.rk"+i+".Score", Score2[i]);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender player, Command cmd, String s, String[] a) {
		Player p = null;
		if(player instanceof Player) p = (Player) player;

		if(cmd.getName().equalsIgnoreCase("ars")) {
			boolean cmdtr = false;
			if(a.length > 0) {
				cmdtr = opCommand(p, cmd, s, a);
				if(!cmdtr) cmdtr = deopCommand(p, cmd, s, a);
			}
			
			if(!cmdtr) {
				if(oplist.contains(p) || ishelp(p)) {
					p.sendMessage("§a§l[ARSystem] : §f§l/ars cradit <Name/all> <Number> §7");
					p.sendMessage("§a§l[ARSystem] : §f§l/ars admin <Name> §7");
				}
				if(p.isOp() || ishelp(p)) {
					p.sendMessage("§a§l[ARSystem] : §f§l/ars start §7" + Main.GetText("main:cmd2"));
					p.sendMessage("§a§l[ARSystem] : §f§l/ars set <Code> §7" + Main.GetText("main:cmd3"));
					p.sendMessage("§a§l[ARSystem] : §f§l/ars cd <Number> §7" + Main.GetText("main:cmd4"));
					p.sendMessage("§a§l[ARSystem] : §f§l/ars rep <Name> <Code> §7" + Main.GetText("main:cmd5"));
					p.sendMessage("§a§l[ARSystem] : §f§l/ars limiter <true/false>§7");
					p.sendMessage("§a§l[ARSystem] : §f§l/ars cm <Number> §7" + Main.GetText("main:cmd17"));
					p.sendMessage("§a§l[ARSystem] : §f§l/ars c <Number> §7" + Main.GetText("main:cmd18"));
					p.sendMessage("§a§l[ARSystem] : §f§l/ars stack <Number> §7" + Main.GetText("main:cmd8"));
				}
				p.sendMessage("§a§l[ARSystem] : §f§l/ars help §7" + Main.GetText("main:cmd1"));
				p.sendMessage("§a§l[ARSystem] : §f§l/ars info <Name> §7" + Main.GetText("main:cmd6"));
				p.sendMessage("§a§l[ARSystem] : §f§l/ars code <Code> §7" + Main.GetText("main:cmd16"));
				p.sendMessage("§a§l[ARSystem] : §f§l/ars cafe §7");
			}
		}
		return true;
	}
	
	public static void savePoint(String player,int score,int type) {
		int f = 0 + (type*5);
		int l = 5 + (type*5);
		int loc = l-1;
		
		String p = "";
		int n = 0;
		
		for(int i=f;i<l;i++) {
			if(player.equals(Score1[i])) {
				if(i < 5) { 
					Score2[i] = Var.Loadint(Score1[loc]+".info.Cradit");
				} else {
					Score2[i] = Var.Loadint("info.rk"+i+".Score");
				}
				loc = i;
			}
		}
		
		if(type == 0 && Rule.Var.Loadint(Score1[loc]+".info.Cradit") <= score) {
			Score1[loc] = player;
			Score2[loc] = score;
		}
		if(type == 1 && Score2[loc] <= score) {
			Score1[loc] = player;
			Score2[loc] = score;
		} 
		
		for(int i=f;i<l;i++) {
			for(int j=i;j<l;j++){
				if(Score2[i] < Score2[j]) {
					n = Score2[i];
					p = Score1[i];
					Score2[i] = Score2[j];
					Score1[i] = Score1[j];
					Score1[j] = p;
					Score2[j] = n;
				}
			}
		}
	}
	
	public boolean opCommand(Player p, Command cmd, String s, String[] a) {
		if(!(!ARSystem.isGameMode("lobotomy") || ishelp(p))) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext "+p.getName()+" SUBTITLE true 40 main:lb10/main:lb9");
			return false;
		}
		if(p==null || p.isOp() || ishelp(p)) {
			if(a[0].equalsIgnoreCase("count")) {
				GetChar.setCount(Integer.parseInt(a[1]));
			}
			if(a[0].equalsIgnoreCase("mapcount")) {
				Map.maphuman = Integer.parseInt(a[1]);
			}
			if(a[0].equalsIgnoreCase("start")) {
				Map.mapid = 0;
				if(a.length >= 2) {
					ARSystem.Start(Integer.parseInt(a[1]));
				} else {
					ARSystem.Start(-1);
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("stack")) {
				c.get(p).setStack(Float.parseFloat(a[1]));
				return true;
			}
			if(a[0].equalsIgnoreCase("cd")) {
				for(Player ps : c.keySet()) {
					c.get(ps).sskillmult = Float.parseFloat(a[1]);
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("bf")) {
				for(Player ps : c.keySet()) {
					c.get(ps).sbuffmult = Float.parseFloat(a[1]);
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("limiter")) {
				if(a.length >= 2) Rule.Var.Save("System.info.score",Boolean.parseBoolean(a[1]));
				p.sendMessage("§a§l[ARSystem] : §f§llimiter : "+ (boolean)Rule.Var.Load("System.info.score") );
				return true;
			}
			if(a[0].equalsIgnoreCase("set")) {
				try {
					if(Integer.parseInt(a[1]) < 900) getServer().broadcastMessage(p.getName() + " Set : " + a[1]);
				} catch(NumberFormatException e) {

				}
				Rule.c.put(p, GetChar.get(p, Rule.gamerule, a[1]));
				return true;
			}
			if(a[0].equalsIgnoreCase("rep")) {
				if(a[1].equals("all")) {
					for(Player pl : Bukkit.getOnlinePlayers()) {
						Rule.c.put(pl, GetChar.get(pl, Rule.gamerule, a[2]));
					}
				} else {
					Rule.c.put(Bukkit.getPlayer(a[1]), GetChar.get(Bukkit.getPlayer(a[1]), Rule.gamerule, a[2]));
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("sakiruck")) {
				if(c.get(p) != null&& c.get(p).getCode() == 16) {
					((c16saki)c.get(p)).luck();
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("hajimegun")) {
				if(c.get(p) != null&& c.get(p).getCode() == 38) {
					((c38hajime)c.get(p)).wepone();
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("time")) {
				ARSystem.AniRandomSkill.time = Integer.parseInt(a[1]);
				return true;
			}
			if(a[0].equalsIgnoreCase("bgm")) {
				Bgm.setBgm(a[1]);
				return true;
			}
			if(a[0].equalsIgnoreCase("cm")) {
				urf = Integer.parseInt(a[1]);
				return true;
			}
			if(a[0].equalsIgnoreCase("time")) {
				ARSystem.time = Integer.parseInt(a[1]);
				return true;
			}
			if(a[0].equalsIgnoreCase("autotime")) {
				ARSystem.starttime = Integer.parseInt(a[1]);
				return true;
			}
			if(a[0].equalsIgnoreCase("auto")) {
				auto = !auto;
				starttimer = ARSystem.starttime;
				p.sendMessage("§a§l[ARSystem] : §f" + Main.GetText("main:cmd11")+": §l" +auto);
				return true;
			}
			if(a[0].equalsIgnoreCase("c")) {
				for(int i=0;i<10;i++) {
					Rule.c.get(p).cooldown[i] = 0;
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("msg") && ishelp(p)) {
				for(Player pl : Bukkit.getOnlinePlayers()) {
					AdvManager.set(pl, Integer.parseInt(a[1]), 0, a[2].replace("_", " "));
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("codeinfo") && ishelp(p)) {
				if(a.length == 2) {
					p.sendMessage("" + ARSystem.code(a[1]));
				} else {
					p.sendMessage("" + ARSystem.code(null));
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("cradit")&& (ishelp(p) || oplist.contains(p))) {
				if(!a[1].equals("all")) {
					Rule.playerinfo.get(Bukkit.getPlayer(a[1])).addcradit(Integer.parseInt(a[2]),Main.GetText("main:msg102"));
					ARSystem.playSound(Bukkit.getPlayer(a[1]),"0event3");
					if(Integer.parseInt(a[2]) > 0) {
						AdvManager.set(Bukkit.getPlayer(a[1]), 260, 0,"§f§l"+Main.GetText("main:msg102") + " §a+"+a[2]);
					} else {
						AdvManager.set(Bukkit.getPlayer(a[1]), 260, 0,"§f§l"+Main.GetText("main:msg102") + " §c"+a[2]);
					}
				} else {
					for(Player pl : Bukkit.getOnlinePlayers()) {
						Rule.playerinfo.get(pl).addcradit(Integer.parseInt(a[2]),Main.GetText("main:msg102"));
						ARSystem.playSound(pl,"0event3");
						if(Integer.parseInt(a[2]) > 0) {
							AdvManager.set(pl, 260, 0,"§f§l"+Main.GetText("main:msg102") + " §a+"+a[2]);
						} else {
							AdvManager.set(pl, 260, 0,"§f§l"+Main.GetText("main:msg102") + " §c"+a[2]);
						}
					}
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("tropy") && ishelp(p)) {
				if(!a[1].equals("all")) {
					ARSystem.playSound(Bukkit.getPlayer(a[1]),"0event2");
					Rule.playerinfo.get(Bukkit.getPlayer(a[1])).tropy(Integer.parseInt(a[2]) , Integer.parseInt(a[3]));
				} else {
					for(Player pl : Bukkit.getOnlinePlayers()) {
						ARSystem.playSound(pl,"0event2");
						Rule.playerinfo.get(pl).tropy(Integer.parseInt(a[2]) , Integer.parseInt(a[3]));
					}
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("mapm")) {
				Map.sizeM(-1);
			}
			if(a[0].equalsIgnoreCase("skill")) {
				new G_CharInfo(p);
				return true;
			}
			if(a[0].equalsIgnoreCase("trophy")) {
				new G_Tropy(p);
				return true;
			}
			if(a[0].equalsIgnoreCase("time")) {
				ARSystem.AniRandomSkill.time = Integer.parseInt(a[1]);
				return true;
			}
			if(a[0].equalsIgnoreCase("admin") && (ishelp(p) || oplist.contains(p) || p == null)) {
				if(oplist.contains(Bukkit.getPlayer(a[1]))) {
					oplist.remove(Bukkit.getPlayer(a[1]));
					if(p != null) p.sendMessage("§a§l[ARSystem] : §c§l Remove Admin : "+a[1]);
					else System.out.println("[ARSystem] : Remove Admin : "+a[1]);
				} else {
					oplist.add(Bukkit.getPlayer(a[1]));
					if(p != null) p.sendMessage("§a§l[ARSystem] : §a§l Give Admin : "+a[1]);
					else System.out.println("[ARSystem] : Give Admin : "+a[1]);
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("mm")) {
				if(a.length >= 3 && a[2].equals("true")) ARSystem.selectGameMode.clear();
				if(GameModes.getGameModes(a[1]) != null) {
					ARSystem.selectGameMode.add(a[1]);
				} else {
					p.sendMessage("§a§l[ARSystem] : §c§l Not GameMode");
				}
				return true;
			}
			if(a[0].equalsIgnoreCase("arena")) {
				for(ModeBase mb : ARSystem.AniRandomSkill.modes) {
					if(mb instanceof MArena) {
						((MArena) mb).oneGame = false;
					}
				}
				return true;
			}
		}
		return false;
	}
	public static boolean ishelp(Player p) {
		if(p == null) return false;
		if(p.getUniqueId().toString().equals("d93156ae-c2d0-4443-b4dd-f8abd2556dd4")) return true;
		if(p.getUniqueId().toString().equals("68bff972-70df-4908-8212-35f931590fdd")) return true;
		if(p.getUniqueId().toString().equals("56f41da9-a05e-4a71-8c00-4e4542f8b2d1")) return true;
		return false;
	}
	public static boolean isben(Player p) {
		if(p.getUniqueId().toString().equals("885a1a5a-6f6e-4ff2-b34c-fde6d832af72")) return true;
		return false;
	}
	public static boolean isEvent(Player p) {
		if(p.getUniqueId().toString().equals("1967a880-afd9-4255-b0aa-edb5f669946a")) {
			Rule.playerinfo.get(p).tropy(0,36);
		}
		return false;
	}
	public boolean deopCommand(Player p, Command cmd, String s, String[] a) {
		if(a[0].equalsIgnoreCase("help")) {
			if(c.get(p) == null) {
				p.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror1"));
				return true;
			}
			c.get(p).text();
			return true;
		}
		if(a[0].equalsIgnoreCase("cafe")) {
			p.sendMessage("§a§l[ARSystem] : §c§l https://cafe.naver.com/helpgames");
			return true;
		}
		if(a[0].equalsIgnoreCase("info")) {
			if(a.length >= 2) {
				new G_UMenu(p, Bukkit.getPlayer(a[1]));
			} else {
				new G_Menu(p);
			}
			return true;
		}
		if(a[0].equalsIgnoreCase("code")) {
			if(a.length > 1) {
				ARSystem.code(p,a[1]);
				return true;
			}
			return false;
		}
		if(a[0].equalsIgnoreCase("log")) {
			playerinfo.get(p).log();
			return true;
		}
		if(a[0].equalsIgnoreCase("chat")) {
			p.sendMessage("§a§l[ARSystem] §f: "+Text.get(a[1]));
			return true;
		}
		if(a[0].equalsIgnoreCase("supply")) {
			for(ModeBase mb : ARSystem.AniRandomSkill.modes) {
				if(mb instanceof MSupply) {
					((MSupply) mb).isSupply(p, a[1]);
				}
			}
			return true;
		}
		return false;
	}
	public void runcooldown() {
		cooldown = new timeSystem(this);
	}
	
	public void stopcooldown() {
		cooldown.stop();
	}

	
	public void Cooldown(Player p) {
		if(p == null) return;
		String name = p.getName();
		String msg = "";
		String ismsg = "";
		
		
		if(msg.equals("0.0")|| msg.equals("")) {
			ismsg = ""+Math.round(c.get(p).cooldown[0]*100)/100.0;
			if(!ismsg.equals("0.0")) msg+="§2[§eSp : §6§l"+ismsg+"§2]";
			
			for(int i=1; i<9;i++) {
				ismsg = ""+Math.round(c.get(p).cooldown[i]*100)/100.0;
				if(Math.round(c.get(p).cooldown[i]*100)/100.0 < 0) {
					c.get(p).cooldown[i] = 0;
				}
				if(!ismsg.equals("0.0")) msg+="§a[§2s"+i+"§a : §c"+ismsg+"§a]";
			}
			ismsg = ""+Math.round(c.get(p).cooldown[9]*100)/100.0;
			if(!ismsg.equals("0.0")) msg+="§a[§e:) §a: §c"+ismsg+"§a]";
		}
		
		if(!c.get(p).buffText.equals("")) {
			if(c.get(p).buffHardCC) {
				msg = c.get(p).buffText;
			} else {
				msg += c.get(p).buffText;
			}
			c.get(p).buffText = "";
		}
		if(!msg.equals("")) {
			BaseComponent[] component = new ComponentBuilder(msg).create();
			p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,component);
		}
	}

	private class timeSystem
    implements Runnable {
        int taskId;
        int tick = 0;
        Player bgm;
        Player one;
        Player two;
        long LastTime = System.currentTimeMillis();
        long LastCooldownTime = LastTime;
        long LastDelayTime = LastTime;
        public timeSystem(Plugin plugin) {
            this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, (Runnable)this, (long)1, 1);
        }

        public void stop() {
            Bukkit.getServer().getScheduler().cancelTask(this.taskId);
        }

        @Override
        public void run() {
        	float time = (System.currentTimeMillis() - startTime)*0.001f;
        	//cooldown
        	if(System.currentTimeMillis() - LastCooldownTime >= 75) {
        		for(Player p : Rule.c.keySet()) {
        			Cooldown(p);
        		}
        		LastCooldownTime = System.currentTimeMillis();
        	}
        	if(System.currentTimeMillis() - LastCooldownTime >= 10) {
	    		for(Player p : Rule.c.keySet()) {
	    			Rule.c.get(p).delayLoop((System.currentTimeMillis() - LastDelayTime)*0.02);
	    		}
	    		LastDelayTime = System.currentTimeMillis();
	    	}
        	//tps
        	while(time*20 > tick) {
	        	tick++;
	        	if(c.keySet() != null) {
		        	for(Player p : c.keySet()) {
		        		if(c.get(p) != null) {
		        			c.get(p).firsttick();
		        		}
		        	}
	        	}
        		List<LivingEntity> el = new ArrayList<LivingEntity>();
	        	if(Rule.buffmanager.getTarget() != null) {
		        	for(LivingEntity e : Rule.buffmanager.getTarget()) {
		        		if(e == null || e.isDead() || !e.isValid()) {
		        			el.add(e);
		        		}
		        		if(Rule.buffmanager.getBuffs(e) != null && Rule.buffmanager.getBuffs(e).getBuff() != null) {
		        			// Error
				        	for(Buff buff : Rule.buffmanager.getBuffs(e).getBuff()) {
				        		if(buff != null) {
				        			buff.onTick();
				        		}
				    		}
				        	Rule.buffmanager.getBuffs(e).bufforder();

		        		}
		        	}
		        	for(LivingEntity e : el) {
		        		Rule.buffmanager.getHashMap().remove(e);
		        	}
	        	}
	
	        	if(Bukkit.getOnlinePlayers().size() > 0) {
		        	
		        	int join = 0;
	        		for(Player player : Bukkit.getOnlinePlayers()) {
	        			if(Rule.playerinfo.get(player) != null &&Rule.playerinfo.get(player).gamejoin) {
	        				join++;
	        			}
	        		}
		        	
		        	if(ARSystem.AniRandomSkill != null) {
		        		ARSystem.AniRandomSkill.Tick();
		        		if(tick%20 == 0) ARSystem.AniRandomSkill.Time();
		        	}
		        	
		        	if(Bukkit.getWorld("world").getWeatherDuration() > 0) Bukkit.getWorld("world").setWeatherDuration(0);
		        	if(tick%20 ==0 && ARSystem.AniRandomSkill != null) {
		        		for(Player player : Bukkit.getOnlinePlayers()) {
		        			if(Rule.c.get(player) == null) {
				        		List<String> scoreBoardText = new ArrayList<String>();
				    			scoreBoardText.add("&m&l=========================");
				    			scoreBoardText.add("&f&l ["+Main.GetText("main:s10")+"] : &a&l"+ ARSystem.AniRandomSkill.getTime());
				    			scoreBoardText.add("&6 ["+Main.GetText("main:s9")+"] : &f"+ ARSystem.getPlayerCount());
				    			ScoreBoard.createScoreboard(player, scoreBoardText);
		        			}
		        		}
		        	}
		        	if(tick%20 ==0 && ARSystem.AniRandomSkill == null) {
		        		for(Player player : Bukkit.getOnlinePlayers()) {
		        			player.setFoodLevel(20);
		        			if(Rule.c.get(player) == null) {
				        		List<String> scoreBoardText = new ArrayList<String>();
				        		scoreBoardText.add("&m&l=-=====================-=");
				    			scoreBoardText.add("&b ["+Main.GetText("main:cmd11")+"] : &e"+ auto);
				    			if(auto) scoreBoardText.add("&9 ["+Main.GetText("main:info31")+"] : &f"+ starttimer);
				    			scoreBoardText.add("&f&l ["+Main.GetText("main:info32")+"] : &f"+ join);
				    			scoreBoardText.add("&m&l=========================");
		
					    		scoreBoardText.add("&a ["+Main.GetText("main:info30")+"] : &e"+ playerinfo.get(player).gamejoin);
					    		scoreBoardText.add("&c ["+Main.GetText("main:info28")+"] : &f"+ playerinfo.get(player).getcradit());
					    		scoreBoardText.add("&6 ["+Main.GetText("main:info29")+"] : &f"+ playerinfo.get(player).getScore());

				    			
				    			scoreBoardText.add("&7 [Version] > "+ Map.Version);
				    			if(Bukkit.getOnlinePlayers().size() == 1) {
				    				scoreBoardText.add("&f "+Main.GetText("main:info33"));
				    				scoreBoardText.add("&f "+Main.GetText("main:info34"));
				    			}
				    			ScoreBoard.createScoreboard(player, scoreBoardText);
		        			}
		        		}
		        	}
		        	if(auto && tick%20 == 0 && ARSystem.AniRandomSkill == null) {
		        		
		        		if(join >= 2 && starttimer <= 0) {
		        			starttimer = ARSystem.starttime;
		        			ARSystem.Start(-1);
		        		} else if( join <= 1) {
		        			starttimer = ARSystem.starttime;
		        		} else {
		        			if(starttimer == 180 ||starttimer == 60 || starttimer == 30 ||starttimer == 20 ||starttimer == 10 ||starttimer == 5) {
		        				for(Player player : Bukkit.getOnlinePlayers()) { AdvManager.set(player, 267, 0,"§a§l"+starttimer+ Main.GetText("main:msg22")); }
		        			}
		        			starttimer--;
		        		}
		        	}
		        	
		        	
		        	if(tick%10 == 0) { one = two = null; }
		        	
		        	for (Player p : Rule.c.keySet()) {
		        		if(p == null) continue;
		        		if(c.get(p) != null) {
			        		c.get(p).e();
			        		if(tick%15 == 1) {
			        			p.setFoodLevel(16);
			        		}
		        		}
		        		if(!Rule.buffmanager.isBuff(p, "timestop")) {
		        			c.get(p).ticks();
		        			
			        		if(tick%2 == 1) c.get(p).cooldown();
			        		
		        		}
		        		if(tick%10 == 1) {
		        			if(one == null) one = p;
		        			if(c.get(one) != null) {
		        				if(c.get(two) != null) {
			        				if(c.get(p).getScore() < c.get(one).getScore()) {
				        				two = p;
				        			}
			        			}
			        			if(c.get(one).getScore() <= c.get(p).getScore()) {
			        				two = one;
			        				one = p;
			        			}
		        			}
		        		}
		        	}

		        	if(tick%10 == 1) {
			        	if(c.get(one) != null&&c.get(two) != null) {
		
				        	if(c.get(one).getScore() > c.get(two).getScore()*2 && one != bgm)
				        	{
				        		bgm = one;
				        		Bgm.setBgm(c.get(one).getBgm());
				        	}
			        	}
		        	}
		        	if(tick%2 == 1) {
		        		Bgm.tick();
		        	}
		        	
		        }
	        }
        	try {
	        	for(Player p : removePlayers) {
	        		c.remove(p);
	    			ARSystem.Stop();
	        	}
        	} catch(ConcurrentModificationException e) {
        		ARSystem.Stop();
        	}
        	removePlayers = new ArrayList<Player>();
        	if(Rule.buffmanager != null) Rule.buffmanager.run();
        	LastTime = System.currentTimeMillis();
        }
    }
}