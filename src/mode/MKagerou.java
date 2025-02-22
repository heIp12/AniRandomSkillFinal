package mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.MSUtil;
import ars.ARSystem;
import ars.Rule;
import ars.TeamInfo;
import buff.ArmorUp;
import buff.Exposure;
import buff.Fascination;
import buff.Medusa;
import buff.NoCC;
import buff.NoHeal;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c001humen4;
import event.FixedDealEvent;
import item.etc.Item10002;
import item.list1.itemBase;
import manager.Bgm;
import mob.MM_Kuroha;
import mob.MM_NoTouch;
import mob.M_GoldDrop;
import mode.MTeamMatch.TeamComparator;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import types.box;
import util.ULocal;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class MKagerou extends ModeBase{
	public static int count = 0;
	static Location local = null;
	static public boolean spBan = true;
	static public boolean cBan = true;
	LivingEntity mary = null;
	List<NPC> npcs = new ArrayList<NPC>();
	List<Integer> npcSpawn = new ArrayList<>();
	public static int npcid = -1;
	int tick = 0;

	HashMap<String,List<Player>> team = new HashMap<>();
	int lifecool = 0;
	int mobSpawnTimer = 200;
	int ayanoTime = 30000;
	
	HashMap<String, Location> locs = new HashMap<>();
	HashMap<Player, Integer> playerCode = new HashMap<>();
	public static HashMap<Player, Integer> playerLife = new HashMap<>();
	private BossBar npcbar1;
	private BossBar npcbar2;
	
	
	public MKagerou(){
		super();
		if(!(boolean)Rule.Var.Load("System.info.mode.kagerou")) isSecret = true;
		isOnlyOne = true;
		modeName = "kagerou";
		disPlayName = Text.get("main:mode17");
	}

	
	void teamCreate(String name,String color,int code) {
		team.put(name,getPlayers(""+code));
		Rule.team.teamCreate(name);
		Rule.team.getTeam(name).setTeamWin(true);
		Rule.team.getTeam(name).setTeamColor(color);
	}
	
	@Override
	public void option() {
		Map.getMapinfo(1013);
		for(Player p : Bukkit.getOnlinePlayers()) {
			Map.playeTp(p);
		}
		ARSystem.gameMode2 = false;
		playerLife.clear();
		playerCode.clear();
		spBan = getBool("10");
		cBan = getBool("11");
		
		locs.put("team1_Center", new Location(Map.getCenter().getWorld(),-1127,40,843));
		locs.put("team2_Center", new Location(Map.getCenter().getWorld(),-1065,40,751));
		
		locs.put("team1_Mob", new Location(Map.getCenter().getWorld(),-1120,41,757));
		locs.put("team2_Mob", new Location(Map.getCenter().getWorld(),-1070,41,838));
		Bgm.cbgm = false;
		npcid = -1;

		teamCreate(Text.get("kage:team1"),"c",2);
		teamCreate(Text.get("kage:team2"),"b",3);
		
		Rule.team.getTeam(Text.get("kage:team1")).setTeamSpawn(new Location(Map.getCenter().getWorld(),-1135,40,851,-160,0));
		Rule.team.getTeam(Text.get("kage:team2")).setTeamSpawn(new Location(Map.getCenter().getWorld(),-1057,40,743,-20,0));
		
		List<Player> players = new ArrayList<Player>();
		for(Player p : ARSystem.getReadyPlayer()) {
			Rule.c.put(p, new c001humen4(p, Rule.gamerule, null));
			Rule.mobmanager.Add(new M_GoldDrop(p,getInt("7")));
			if(!getBool("4")) {
				p.sendTitle("", Text.get("kage:start"),10,80,10);
				p.sendMessage("§a§l[ARSystem] §f: " + Text.get("kage:start"));
			}
			delay(()->{
				ARSystem.addItem(p, 100002);
				ARSystem.addItem(p, 100001);
				if(getBool("4")) {
					for(itemBase it : ARSystem.playerItem.get(p).items) {
						if(it instanceof Item10002) {
							int n = AMath.random(GetChar.getCount());
							while(((Item10002)it).bans.contains(n)) n = AMath.random(GetChar.getCount());
							((Item10002)it).bans.add(n);
							((Item10002)it).c1 = n;
							while(((Item10002)it).bans.contains(n)) n = AMath.random(GetChar.getCount());
							((Item10002)it).bans.add(n);
							((Item10002)it).c2 = n;
							while(((Item10002)it).bans.contains(n)) n = AMath.random(GetChar.getCount());
							((Item10002)it).bans.add(n);
							((Item10002)it).c3 = n;
							((Item10002)it).end = true;
						}
					}
				}
			},100);
			players.add(p);
		}
		
		if(getBool("1")) Collections.shuffle(players);
		int i = 0;
		
		
		for(Player p : players) {
			boolean next = false;
			for(String s : team.keySet()) {
				if(team.get(s).contains(p)) {
					next = true;
					Rule.team.teamJoin(s, p);
					break;
				}
			}
			if(next) continue;
			List<TeamInfo> teams = Rule.team.getTeams();
	        Collections.sort(teams, new TeamComparator());
	        teams.get(0).Join(p);
		}
		
		
		float r1 = Rule.team.getTeam(Text.get("kage:team1")).getPlayer().size();
		float r2 = Rule.team.getTeam(Text.get("kage:team2")).getPlayer().size();
		if(r1 == r2) {
			r1 = r2 = getInt("8");
		} else if(r1 - r2 < 0) {
			if(r1/r2 < 0.15) {
				r2 = Math.max(1, getInt("8")*0.2f);
			} else if(r1/r2 < 0.35) {
				r2 = Math.max(1, getInt("8")*0.4f);
			} else if(r1/r2 < 0.55) {
				r2 = Math.max(1, getInt("8")*0.6f);
			} else if(r1/r2 < 0.75) {
				r2 = Math.max(1, getInt("8")*0.8f);
			}
			r1 = getInt("8");
		} else {
			if(r2/r1 < 0.15) {
				r1 = Math.max(1, getInt("8")*0.2f);
			} else if(r2/r1 < 0.35) {
				r1 = Math.max(1, getInt("8")*0.4f);
			} else if(r2/r1 < 0.55) {
				r1 = Math.max(1, getInt("8")*0.6f);
			} else if(r2/r1 < 0.75) {
				r1 = Math.max(1, getInt("8")*0.8f);
			}
			r2 = getInt("8");
		}
		
		if(Rule.c.keySet().size() > 15) {
			r1 = (int)Math.max(1,r1*0.1);
			r2 = (int)Math.max(1,r2*0.1);
		} else if(Rule.c.keySet().size() > 9) {
			r1 = (int)Math.max(1,r1*0.5);
			r2 = (int)Math.max(1,r2*0.5);
		} else if(Rule.c.keySet().size() > 5) {
			r1 = (int)Math.max(1,r1*0.7);
			r2 = (int)Math.max(1,r2*0.7);
		}
		
		for(Player p : Rule.team.getTeam(Text.get("kage:team1")).getPlayer()) {
			playerLife.put(p,(int)r1);
		}
		for(Player p : Rule.team.getTeam(Text.get("kage:team2")).getPlayer()) {
			playerLife.put(p,(int)r2);
		}
		
		Bgm.nextbgm.clear();
		if(AMath.random(10) <= 5) {
			Bgm.setBgm("mk24");
			if(AMath.random(10) <= 1) Bgm.nextbgm.add("mkn24");
		} else {
			Bgm.setBgm("mk25");
			if(AMath.random(10) <= 1) Bgm.nextbgm.add("mkn25");
		}
		if(getBool("12")) {
			Bgm.nextbgm.clear();
			for(Player p : Rule.c.keySet()) {
				Rule.playerinfo.get(p).gold += getInt("13");
			}
		}
		
		ARSystem.AniRandomSkill.time = -20;

		npcs.clear();
		for(int n = 0; n< 11; n++) {
			CitizensAPI.getNPCRegistry().getById(n).spawn(Map.getCenter().clone().add(0,-40,0));
			CitizensAPI.getNPCRegistry().getById(n).despawn();
			npcs.add(CitizensAPI.getNPCRegistry().getById(n));
		}
	}
	
	@Override
	public void end() {
		Bgm.cbgm = true;
		Bgm.nextbgm.clear();
		Bgm.randomBgm();
		for(int n = 0; n< 11; n++) {
			CitizensAPI.getNPCRegistry().getById(n).despawn();
		}


		if(npcbar1 != null) npcbar1.removeAll();
		if(npcbar2 != null) npcbar2.removeAll();
	}
	
	@Override
	public void firstTick() {
		ARSystem.winstop = 10000000;
		ARSystem.E_sterEgg = false;
		for(Player p : Bukkit.getOnlinePlayers()) {
			int i = 1;
			for(String s : Text.getLine("kage:o",1)){
				String name = s;
				String lore = "";
				for(String s2 : Text.getLine("kage:o"+i+"-",1)) {
					lore += s2+"\n";
				}
				p.spigot().sendMessage(Text.hover(p,name, lore));
				i++;
			}
		}

		
		
	}
	
	@Override
	public void Interact(PlayerInteractEntityEvent e) {
		Entity en = e.getRightClicked();
		if(npcid > 0 && en == npcs.get(npcid).getEntity()) {
			ARSystem.playSound(en, "mknpc0"+npcid+""+AMath.random(2));
		}
	}
	
	public void ticks() {
		TeamInfo team1 = Rule.team.getTeam(Text.get("kage:team1"));
		TeamInfo team2 = Rule.team.getTeam(Text.get("kage:team2"));
		
		tick++;
		int r1=0,r2=0;
		for(Player p : playerLife.keySet()) {
			if(team1.isTeam(p)) {
				r1 += playerLife.get(p);
			} else {
				r2 += playerLife.get(p);
			}
		}
		for(Player p : Rule.c.keySet()) {
			Rule.c.get(p).scoreText.add("§f[" + r1 +"] §c"+team1.getTeamName()+" §e§lvs §b"+team2.getTeamName()+" §f[" + r2+"]");
			Rule.c.get(p).scoreText.add(" §b§l[My  Life] : §f" + playerLife.get(p));
		}
		if(tick%4 == 0 && getBool("5")) {
			spawnGard(locs.get("team1_Center"),team1);
			spawnGard(locs.get("team2_Center"),team2);
		}
	}
	
	float npcget = 0.5f;
	public void npc() {
		TeamInfo team1 = Rule.team.getTeam(Text.get("kage:team1"));
		TeamInfo team2 = Rule.team.getTeam(Text.get("kage:team2"));
		int power1 = 0;
		int power2 = 0;
		Location center = npcs.get(npcid).getEntity().getLocation();
		center.setY(40);
		for(Player p : Rule.c.keySet()) {
			Location lc = p.getLocation().clone();
			lc.setY(40);
			if(center.distance(lc) <= 8) {
				if(team1.isTeam(p)) {
					power1 += 1;
				} else if(team2.isTeam(p)) {
					power2 += 1;
				}
			}
		}
		if(power1 <= 0 || power2 <= 0) {
			if(getBool("12")) {
				power1 *= 3;
				power2 *= 3;
			}
			if(power1 > 0) {
				npcget += 0.02 + power1*0.01;
			} else if(power2 > 0) {
				npcget -= 0.02 + power2*0.01;
			}
		}
		if(npcbar1 != null && npcbar2 != null) {
			npcbar1.setProgress(Math.min(1,Math.max(npcget,0)));
			npcbar2.setProgress(Math.min(1,Math.max(1-npcget,0)));
		}
		if(npcget >= 1) {
			npcget(team1);
		} else if(npcget <= 0) {
			npcget(team2);
		}
	}
	
	void npcget(TeamInfo team){
		ARSystem.playSoundAll("itemupgrad", 1.4f);
		Bukkit.broadcastMessage("§a§l[ARSystem] " + Text.get("kage:join").replace("{name}", Text.get("kage:c"+npcid)).replace("{team}", team.getTeamName()));
		npcs.get(npcid).despawn();
		for(Player p : team.getPlayer()) {
			ARSystem.addItem(p, 100100+npcid);
		}
		npcid = -1;
		if(getBool("12")){
			Bgm.nextbgm.clear();
			Bgm.setTime(20);
		}
		
		for(Player p :Bukkit.getOnlinePlayers()) {
			npcbar1.removePlayer(p);
			npcbar2.removePlayer(p);
		}
	}
	
	
	public void spawnGard(Location loc,TeamInfo team) {
		int defenceTime = 120;

		if(getBool("12")) defenceTime = 30;
		
		for(Entity e : ARSystem.box(loc, NpcPlayer.npc(local), new Vector(30,30,30), box.ALL)) {
			if(Rule.c.get(e) != null) {
				Location l = e.getLocation().clone();
				l.setY(40);
				if(l.distance(loc) <= 21) {
					Player p = (Player)e;
					if(team.isTeam(p)) {
						if(l.distance(loc) <= 10) {
							ARSystem.heal(p, 0.05);
							if((ARSystem.AniRandomSkill.time-defenceTime)*0.002 < 1) {
								ARSystem.heal(p, 0.2);
								ARSystem.giveBuff(p, new ArmorUp(p), 5, 1 - Math.max(0,ARSystem.AniRandomSkill.time-defenceTime)*0.002);
							}
						}
					} else {
						if(ARSystem.AniRandomSkill.time < defenceTime) {
							ARSystem.giveBuff(p, new NoHeal(p), 20);
							ARSystem.giveBuff(p, new ArmorUp(p), 20, -1);
							ARSystem.fixedDamage(p, NpcPlayer.npc(loc), 0.6);
							Rule.c.get(p).hpCost(0.6, true);
						}
						for(int i =0; i<3; i++) ARSystem.spellLocCast(NpcPlayer.npc(loc), p.getLocation(), "azami_snake2");
						
						p.setNoDamageTicks(0);
						if((ARSystem.AniRandomSkill.time-defenceTime)*0.002 < 1) {
							ARSystem.fixedDamage(p, NpcPlayer.npc(loc), 0.3);
							Rule.c.get(p).hpCost(0.3, true);
						}
						ARSystem.fixedDamage(p, NpcPlayer.npc(loc), 0.1);
						Rule.c.get(p).hpCost(0.1, true);
					}
				}
			}
		}
	}
	
	public void tick(int time) {
		TeamInfo team1 = Rule.team.getTeam(Text.get("kage:team1"));
		TeamInfo team2 = Rule.team.getTeam(Text.get("kage:team2"));
		if(getBool("9")) {
			if(time > 10 && npcid == -1 && Bgm.getTime() <= 1 && Bgm.nextbgm.size() <= 0) {
				if(npcSpawn.size() != 5) {
					int code = AMath.random(10)-1;
					while(npcSpawn.contains(code)) code = AMath.random(10)-1;
					npcid = code;
					npcSpawn.add(code);
					
					Location lc = Map.randomLoc();
					while(lc.distance(locs.get("team1_Center")) < 30 || lc.distance(locs.get("team2_Center")) < 30 || lc.getY() > 45) {
						lc = Map.randomLoc();
					}
					npcs.get(code).spawn(lc);
					npcget = 0.5f;
					
					String[] bgms = Text.get("kage:c"+npcid+"_m").split(",");
					String bgmcode = bgms[AMath.random(bgms.length)-1];
					Bgm.setBgm("mk"+bgmcode);
					if(AMath.random(5) == 1) Bgm.nextbgm.add("mkn"+bgmcode);
					
					if(npcbar1==null || npcbar2==null) {
						npcbar1 = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
						npcbar2 = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
					}
					
					npcbar1.setTitle("§a§l"+Text.get("kage:c"+npcid)+ Text.get("kage:title").replace("{team}", Text.get("kage:team1")));
					npcbar2.setTitle("§a§l"+Text.get("kage:c"+npcid)+ Text.get("kage:title").replace("{team}", Text.get("kage:team1")));
					Bukkit.broadcastMessage("§a§l[ARSystem] " + Text.get("kage:spawn").replace("{name}", Text.get("kage:c"+npcid)));
					
					for(Player p :Bukkit.getOnlinePlayers()) {
						npcbar1.removePlayer(p);
						npcbar2.removePlayer(p);
						if(!npcbar1.getPlayers().contains(p) && team1.getPlayer().contains(p)) {
							npcbar1.addPlayer(p);
						} else if(!npcbar2.getPlayers().contains(p) && team2.getPlayer().contains(p)) {
							npcbar2.addPlayer(p);
						}
					}
				} else {
					npcid = 11;
					npcSpawn.add(11);
					Location loc = Map.getCenter();
					loc.setY(40);
					LivingEntity en = Map.spawnMM("kuroha", loc);
					Rule.mobmanager.Add(new MM_Kuroha(en, loc));
					npcget = 0.5f;
					
					String[] bgms = Text.get("kage:c"+npcid+"_m").split(",");
					String bgmcode = bgms[AMath.random(bgms.length)-1];
					Bgm.setBgm("mk"+bgmcode);
					Bgm.nextbgm.add("mkn"+bgmcode);
				}
			}
			
			if(Bgm.nextbgm.size() == 0 && Bgm.getTime() <= 1) {
				if(npcid != -1) {
					String[] bgms = Text.get("kage:c"+npcid+"_m").split(",");
					String bgmcode = bgms[AMath.random(bgms.length)-1];
					Bgm.setBgm("mk"+bgmcode);
					if(AMath.random(2) == 1)Bgm.nextbgm.add("mkn"+bgmcode);
				} else {
					Bgm.setBgm("mk26");
				}
			}
			if(npcid != -1 && npcid != 11) npc();
		} else {
			int bgmcode = AMath.random(23);
			Bgm.setBgm("mk"+bgmcode);
			if(AMath.random(2) == 1)Bgm.nextbgm.add("mkn"+bgmcode);
		}
		for(int i = 0; i<20; i++) {
			delay(()->{
				ticks();
			},i);
		}
		
		for(Player p : Rule.c.keySet()) {
			int n = Rule.c.get(p).number;
			if(n < 900 && n > 0) playerCode.put(p, n);
		}
		
		List<Player> removes = new ArrayList<Player>();
		int life1=0,life2=0;
		for(Player p : playerLife.keySet()) {
			if(!p.isOnline()) removes.add(p);
			if(team1.isTeam(p)) {
				life1+=playerLife.get(p);
			} else {
				life2+=playerLife.get(p);
			}
		}
		if(time > 10) {
			if(life1 == 0 && !team1.isTeamAlive() && team1.getPlayer().size() > 0) {
				win(team2,Text.get("kage:team2"));
			} else if(life2 == 0 && !team2.isTeamAlive() && team2.getPlayer().size() > 0) {
				win(team1,Text.get("kage:team1"));
			}
		}
		
		for(Player p : removes) {
			playerCode.remove(p);
			playerLife.remove(p);
		}
		if(time%5 == 0) {
			for(Player p : playerLife.keySet()) {
				Rule.playerinfo.get(p).gold += (20 + (playerLife.size()/2)*10);
			}
		}
		if((time+30)%60 == 0) {
			double hp = 0.3f;
			hp += (time/60)*0.15;
			int count = 3;
			count+=time/180;
			
			jgSpawn("kage2",hp,(int) (500*hp*(getInt("6")*0.01)));
			for(int i =0; i<count; i++) {
				jgSpawn("kage1",hp,(int) (140*hp*(getInt("6")*0.01)));
				jgSpawn("kage0",hp,(int) (140*hp*(getInt("6")*0.01)));
			}
		}
	}
	
	public void jgSpawn(String type,double hp,int gold) {
		Location lc1 = locs.get("team1_Mob").clone().add(new Vector(20-AMath.random(40),0,20-AMath.random(40)));
		while(!Map.inMap(lc1)) lc1 = locs.get("team1_Mob").clone().add(new Vector(20-AMath.random(40),0,20-AMath.random(40)));
		Location lc2 = locs.get("team2_Mob").clone().add(new Vector(20-AMath.random(40),0,20-AMath.random(40)));
		while(!Map.inMap(lc2)) lc2 = locs.get("team2_Mob").clone().add(new Vector(20-AMath.random(40),0,20-AMath.random(40)));
		
		
		LivingEntity e = Map.spawnMM(type,lc1);
		e.setMaxHealth(e.getMaxHealth()*hp);
		Rule.mobmanager.Add(new M_GoldDrop(e,gold));
		
		e = Map.spawnMM(type,lc2);
		e.setMaxHealth(e.getMaxHealth()*hp);
		Rule.mobmanager.Add(new M_GoldDrop(e,gold));
	}
	
	public void win(TeamInfo team,String teamName) {
		String teamname = team.getTeamName();
		String name = "§e§lTeam : " + teamname +" Win!!"; 
		String names = "";
		for(Player pl : Rule.team.getTeam(teamName).getPlayer()) {
			names+=pl.getName()+",";
		}
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.sendTitle(name, "" +names,40,20,40);
			MSUtil.resetbuff(player);
			player.setGameMode(GameMode.ADVENTURE);
			player.setMaxHealth(20);
			player.setHealth(20);
		}
		ARSystem.gameEnd();
	}

	List<Entity> kill = new ArrayList<>();
	@Override
	public void EntityDeathEvent(Entity p, Entity killer) {
		if(p == killer && kill.contains(p)) return;
		kill.add(p);
		if(p instanceof Player) delay(()->{kill.remove(p);},20);

	}
	
	@Override
	public void PlayerDeathEvent(Player p, Entity killer) {
		TeamInfo team1 = Rule.team.getTeam(Text.get("kage:team1"));
		TeamInfo team2 = Rule.team.getTeam(Text.get("kage:team2"));
		int time = 60 + (tick/40);

		ARSystem.giveBuff(p, new TimeStop(p), time);
		Holo.create(p.getLocation(), p.getName() + " Respawn : " + AMath.round(time*0.05,2) ,time , new Vector(0,0,0));
		p.sendTitle("Respawn",""+ AMath.round(time*0.05,2)+"(s)",0,time,0);
		if(playerLife.get(p) > 0) {
			delay(()->{
				playerLife.put(p,playerLife.get(p)-1);
				if(playerCode.get(p) == null) {
					Rule.c.put(p, new c001humen4(p, Rule.gamerule, null));
				} else {
					Rule.c.put(p, GetChar.get(p,Rule.gamerule,""+playerCode.get(p)));
					if(getBool("10")) Rule.c.get(p).setcooldown[0] = -100000;
				}
				Rule.mobmanager.Add(new M_GoldDrop(p,getInt("7")));
				ARSystem.giveBuff(p, new Nodamage(p), 120);
				ARSystem.giveBuff(p, new NoCC(p), 120);
				if(team1.isTeam(p)) {
					p.teleport(team1.getTeamSpawn());
				} else {
					p.teleport(team2.getTeamSpawn());
				}
			},time);
		}
	}
	
	public class TeamComparator implements Comparator<TeamInfo> {
		@Override
		public int compare(TeamInfo f1, TeamInfo f2) {
			if (f1.getPlayer().size() > f2.getPlayer().size()) {
				return 1;
			} else if (f1.getPlayer().size() < f2.getPlayer().size()) {
				return -1; 
			}
			return AMath.random(3)-2;
		}
	}
}
