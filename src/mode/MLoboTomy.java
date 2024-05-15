package mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Lobotomy;
import buff.ChoSan;
import buff.Exposure;
import buff.Fascination;
import buff.Ice;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Reflect;
import buff.Silence;
import buff.Sleep;
import buffs.Buff;
import chars.c.c00main;
import chars.c.c38hajime;
import manager.AdvManager;
import manager.Bgm;
import types.MapType;
import types.box;
import util.AMath;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;


public class MLoboTomy extends ModeBase{
	public class lobobuff {
		lobobuff(float hp,float damage,float lastcooldown) {
			this.hp = hp;
			this.damage = damage;
			this.lastcooldown = lastcooldown;
		}
		float hp = 1;
		float damage = 1;
		float cooldown[] = new float[] {1,1,1,1,1,1,1,1,1,1};
		float lastcooldown = 1;
	}
	
	public static int rating = 0;
	public static int count = 0;
	public static int level = 0;
	public double hpmult = 1;
	public static HashMap<String,Integer> cr = new HashMap<>();
	public static HashMap<Player,c00main> rep = new HashMap<>();
	
	static List<LivingEntity> mobs = new ArrayList<LivingEntity>();
	static List<LivingEntity> etcmobs = new ArrayList<LivingEntity>();
	static List<LivingEntity> vilager = new ArrayList<LivingEntity>();
	
	static List<String> buff = new ArrayList<String>();
	static List<String> debuff = new ArrayList<String>();
	
	static Player bast = null;
	static int sleep = 5;
	static int stageTime = 0;
	int time = 0;
	static boolean timer = false;
	int mobcount = 0;
	int villager_Death = 0;
	float v = 1;
	static String itemmsg = "";
	static float dmg = 1;
	
	public List<LivingEntity> love = new ArrayList<>();
	
	public MLoboTomy(){
		super();
		isSecret = true;
		isOnlyOne = true;
		modeName = "lobotomy";
		disPlayName = Text.get("main:mode666");
		onbuff.put(1, new lobobuff(1.5f,2f,0.5f));
		onbuff.put(2, new lobobuff(1.2f,2f,1f));
		onbuff.put(4, new lobobuff(1f,1f,0.6f));
		onbuff.put(5, new lobobuff(3f,2f,1f));
		onbuff.put(6, new lobobuff(1f,2f,1f));
		onbuff.put(7, new lobobuff(1.8f,3f,1f));
		onbuff.put(8, new lobobuff(1.8f,2f,1f));
		onbuff.put(9, new lobobuff(1.5f,2f,1f));
		onbuff.put(10, new lobobuff(1.3f,1.4f,1f));
		onbuff.put(11, new lobobuff(1.4f,1.4f,0.75f));
		onbuff.put(13, new lobobuff(1.5f,1.4f,1f));
		onbuff.put(14, new lobobuff(1.8f,1.4f,1f));
		onbuff.put(15, new lobobuff(1f,2.5f,0.5f));
		onbuff.put(16, new lobobuff(2f,1f,1f));
		onbuff.put(19, new lobobuff(1.5f,1.4f,1f));
		onbuff.put(20, new lobobuff(1f,2f,0.75f));
		onbuff.put(22, new lobobuff(1.3f,1.8f,1f));
		onbuff.put(24, new lobobuff(1f,1.5f,1.5f));
		onbuff.put(26, new lobobuff(1f,1.1f,0.5f));
		onbuff.put(27, new lobobuff(1.5f,2f,1f));
		onbuff.put(29, new lobobuff(1.5f,2f,0.4f));
		onbuff.put(30, new lobobuff(1.5f,2.2f,1f));
		onbuff.put(31, new lobobuff(1f,2.4f,0.3f));
		onbuff.put(32, new lobobuff(2f,1.5f,1f));
		onbuff.put(34, new lobobuff(1.5f,1.4f,1f));
		onbuff.put(36, new lobobuff(1.5f,1f,0.5f));
		onbuff.put(38, new lobobuff(1f,1.8f,1f));
		onbuff.put(39, new lobobuff(1.7f,2f,1f));
		onbuff.put(40, new lobobuff(1f,2f,2f));
		onbuff.put(41, new lobobuff(1f,1.5f,0.5f));
		onbuff.put(42, new lobobuff(1.6f,1.3f,0.5f));
		onbuff.put(44, new lobobuff(1.6f,1.5f,0.5f));
		onbuff.put(45, new lobobuff(0.5f,1.25f,0.4f));
		onbuff.put(47, new lobobuff(1.3f,2f,1f));
		onbuff.put(48, new lobobuff(1.6f,1.3f,0.8f));
		onbuff.put(49, new lobobuff(1.5f,1f,0.7f));
		onbuff.put(50, new lobobuff(1.6f,1.7f,0.7f));
		onbuff.put(51, new lobobuff(2f,2f,1f));
		onbuff.put(52, new lobobuff(2f,2f,1f));
		onbuff.put(56, new lobobuff(2f,2f,1f));
		onbuff.put(57, new lobobuff(0.6f,1f,2.5f));
		onbuff.put(58, new lobobuff(1.5f,1.4f,1f));
		onbuff.put(59, new lobobuff(1.5f,2f,1f));
		onbuff.put(60, new lobobuff(1f,1.8f,1f));
		onbuff.put(61, new lobobuff(1f,2f,1f));
		onbuff.put(62, new lobobuff(1f,2.5f,1f));
		onbuff.put(63, new lobobuff(1.5f,1.5f,1f));
		onbuff.put(64, new lobobuff(1f,1.5f,0.4f));
		onbuff.put(65, new lobobuff(1.7f,1.5f,1f));
		onbuff.put(66, new lobobuff(1.7f,1f,1f));
		onbuff.put(68, new lobobuff(1f,1.2f,0.8f));
		onbuff.put(69, new lobobuff(1f,1.8f,1.2f));
		onbuff.put(71, new lobobuff(1.5f,1f,1f));
		onbuff.put(72, new lobobuff(1.5f,1.5f,1f));
		onbuff.put(75, new lobobuff(2f,1f,1f));
		onbuff.put(76, new lobobuff(2f,1.4f,1f));
		onbuff.put(77, new lobobuff(1f,0.8f,1.2f));
		onbuff.put(80, new lobobuff(1.5f,1.2f,1f));
		onbuff.put(82, new lobobuff(1f,1.7f,1f));
		onbuff.put(83, new lobobuff(1f,1.9f,1f));
		onbuff.put(84, new lobobuff(1f,2f,1f));
		onbuff.put(85, new lobobuff(1.7f,1.5f,1f));
		onbuff.put(86, new lobobuff(1.4f,1.6f,0.75f));
		onbuff.put(87, new lobobuff(1.5f,1.4f,0.35f));
		onbuff.put(88, new lobobuff(1.7f,1.4f,1f));
		onbuff.put(93, new lobobuff(1f,3f,1f));
		onbuff.put(94, new lobobuff(1.2f,1.5f,0.5f));
		onbuff.put(95, new lobobuff(1.7f,1f,1f));
		onbuff.put(96, new lobobuff(1.2f,3f,1f));
		onbuff.put(97, new lobobuff(1.2f,1.4f,1f));
		onbuff.put(98, new lobobuff(1.7f,1.7f,1f));
		onbuff.put(99, new lobobuff(1f,2f,1f));
		onbuff.put(100, new lobobuff(1.8f,3f,1f));
		onbuff.put(101, new lobobuff(1.7f,1.6f,1f));
		onbuff.put(102, new lobobuff(1f,1.3f,0.7f));
		onbuff.put(104, new lobobuff(1.5f,1.8f,1f));
		onbuff.put(105, new lobobuff(2f,1.45f,1f));
		onbuff.put(106, new lobobuff(1.4f,1.5f,1f));
		onbuff.put(108, new lobobuff(1f,2f,1f));
		onbuff.put(109, new lobobuff(1f,1.5f,1f));
		onbuff.put(110, new lobobuff(1f,0.75f,1f));
		onbuff.put(111, new lobobuff(1.5f,1.7f,1f));
		onbuff.put(112, new lobobuff(1.5f,1f,1f));
		onbuff.put(113, new lobobuff(1f,2f,1f));
		onbuff.put(114, new lobobuff(1.4f,1.5f,0.8f));
		onbuff.put(116, new lobobuff(1.6f,1f,1f));
		onbuff.put(117, new lobobuff(1f,1.7f,1f));
		onbuff.put(118, new lobobuff(1.4f,1f,0.6f));
		onbuff.put(119, new lobobuff(1f,2f,1f));
		onbuff.put(120, new lobobuff(0.75f,1f,1f));
		onbuff.put(121, new lobobuff(1f,0.75f,1f));
		onbuff.put(122, new lobobuff(1.5f,0.75f,1f));
		onbuff.put(123, new lobobuff(1.5f,1.6f,0.7f));
		onbuff.put(124, new lobobuff(1.5f,1f,0.8f));
		onbuff.put(125, new lobobuff(1f,2f,1f));
		onbuff.put(126, new lobobuff(1.5f,2f,1f));
		onbuff.put(127, new lobobuff(1.7f,2f,1f));
		onbuff.put(130, new lobobuff(1.4f,1.2f,0.6f));
		onbuff.put(131, new lobobuff(1f,1.5f,1f));
		onbuff.put(132, new lobobuff(1f,1.3f,1f));
		onbuff.put(133, new lobobuff(1.6f,1f,0.7f));
		onbuff.put(135, new lobobuff(1f,2.5f,0.7f));
		onbuff.put(139, new lobobuff(1.2f,1.25f,0.75f));
		onbuff.put(140, new lobobuff(1.2f,1.4f,0.8f));
		onbuff.put(141, new lobobuff(1f,1.5f,1f));
		onbuff.put(142, new lobobuff(1f,2f,1f));
		onbuff.put(143, new lobobuff(1f,2f,1f));
		onbuff.put(145, new lobobuff(1f,2f,1f));
		onbuff.put(146, new lobobuff(1f,0.75f,1f));
		onbuff.put(148, new lobobuff(1f,1.5f,1f));
		onbuff.put(149, new lobobuff(1.8f,2f,1f));
		onbuff.put(150, new lobobuff(1.2f,1f,0.8f));
	}
	
	@Override
	public void option() {
		Map.mapType = MapType.NORMAL;
		ARSystem.selectGameMode.remove(modeName);
		Map.getMapinfo(1004);
		Rule.team.teamCreate("H");
		Rule.team.getTeam("H").setTeamColor("7");
		for(Player p : Bukkit.getOnlinePlayers()) {
			Rule.team.teamJoin("H", p);
		}
		Bgm.cbgm = false;
		Bgm.rep = false;
		
		level = count = 0;
		buff.clear();
		etcmobs.clear();
		cr.clear();
		debuff.clear();
		timer = false;
		bast = null;
		dmg = 1;
		villager_Death = 0;
		itemmsg = "";
		v = 1;
	}
	static int[] onsp = {1,5,8,10,11,13,14,15,26,29,31,32,38,39,40,41,42,44,47,48,49,55,56,57,61,62,65,66,67,68,73,76,77,80,86,88,93,94,96,
			99,101,105,106,109,110,112,114,116,120,121,124,126,127,130,131,132,133,136,137,141,142,143,145,146,149,150};
	static HashMap<Integer,lobobuff> onbuff = new HashMap<>();

	public static void charRep(Player p) {
		if(Rule.c.get(p).number == 57 || Rule.c.get(p).number == 86) return;
		List<Integer> onsp = new ArrayList<>() ;
		for(int i : MLoboTomy.onsp) onsp.add(i);
		Rule.c.get(p).frist_damage += (dmg-1);
		Rule.c.get(p).hp*=1.15;
		
		if(buff.contains("b3")) {
			Rule.c.get(p).skillmult += 0.15;
		}
		if(buff.contains("b4")) {
			float rt = (AMath.random(0, 110)+40)*0.01f;
			Rule.c.get(p).frist_damage *= rt;
			p.sendTitle("§f"+Text.get("lobo:b4"), (int)(rt*100) + "%");
		}
		if(buff.contains("b5")) {
			Rule.c.get(p).hp*=1.4;
		}
		if(buff.contains("b7")) {
			Rule.c.get(p).skillmult += 0.8;
		}
		if(buff.contains("b8")) {
			Rule.c.get(p).hpCost(8, true);
			Rule.c.get(p).skillmult += 0.5;
			Rule.c.get(p).frist_damage += 0.3;
		}
		if(buff.contains("b9") && p == bast) {
			ARSystem.giveBuff(p, new ChoSan(p), 999999999);
		}
		if(buff.contains("b12")) {
			Rule.c.get(p).skillmult += 1;
			Rule.c.get(p).frist_damage += 0.5;
		}
		if(buff.contains("b21")) {
			ARSystem.giveBuff(p, new Exposure(p), 200000, -2);
		}
		if(buff.contains("b22") && p == bast) {
			
			Rule.c.get(p).hpCost(p.getMaxHealth()*0.8, true);
			Rule.buffmanager.selectBuffAddValue(p, "barrier", (int)p.getMaxHealth()*5);
		}
		if(buff.contains("b23")) {
			Rule.c.get(p).skillmult -= 0.8;
			Rule.c.get(p).frist_damage *= 1.50;
		}
		if(timer) {
			Rule.c.get(p).hp*=0.8;
		}
		if(debuff.contains("o51")) {
			if(!Rule.buffmanager.isBuff(p, "exposure")) {
				ARSystem.giveBuff(p, new Exposure(p), 200000, 5);
			} else {
				Rule.buffmanager.selectBuffAddValue(p, "exposure", 5);
			}
		}
		Rule.team.teamJoin("H", p);
		
		int code = Rule.c.get(p).number%1000;
		if(!onsp.contains(code)) {
			Rule.c.get(p).setcooldown[0] = -10000;
		}
		if(onbuff.containsKey(code)) {
			Rule.c.get(p).hp*=onbuff.get(code).hp;
			for(int i = 0; i<10; i++) {
				Rule.c.get(p).setcooldown[i]*=onbuff.get(code).lastcooldown * onbuff.get(code).cooldown[i];
			}
			
			Rule.c.get(p).frist_damage*=onbuff.get(code).damage;
		}
		
		p.setMaxHealth(Rule.c.get(p).hp);
		p.setHealth(Rule.c.get(p).hp);
		

		if(level == 9 && count > 3) {
			if(Rule.c.get(p).frist_damage >= 4) {
				Rule.c.get(p).frist_damage *= 0.55;
			} else {
				Rule.c.get(p).frist_damage *= 0.75;
			}
			if(count > 5) {
				Rule.c.get(p).frist_defence *= 2f;
			} else if(count > 4) {
				Rule.c.get(p).frist_defence *= 1.5f;
			}
		}
		
	}
	@Override
	public void firstTick() {
		ARSystem.playSoundAll("0select2");
		ARSystem.AniRandomSkill.mob = true;
		text(394,Text.get("lobo:start"));
		hpmult = Math.max(0.6f, 0.3+0.2*cr.size());
		if(hpmult > 2) {
			hpmult -= (cr.size()-8)*0.1;
		}
		
		text(394,Text.get("lobo:rep") +"§7§l ["+ (int)(hpmult*100)+"%]");
		if(vilager.size() < 3) {
			vilager.add(Map.spawnMM("human", Map.randomLoc()));
		}

		for(Player p : Rule.c.keySet()) {
			int code = Rule.c.get(p).number%1000;
			cr.put(p.getName(),code);
		}
	}
	
	public void buff() {
		if(buff.contains("b1") && (stageTime == 111 || stageTime == 77)) {
			for(Player p : Rule.c.keySet()) {
				ARSystem.heal(p, p.getMaxHealth());
			}
			ARSystem.playSoundAll("fairy");
		}
		if(buff.contains("b3") && stageTime == 66) {
			for(Player p : Rule.c.keySet()) {
				Rule.c.get(p).skillmult += 0.35;
			}
			ARSystem.playSoundAll("wellcheers");
		}
		if(buff.contains("b6") && time%5 == 0) {
			ARSystem.playSoundAll("theresia");
			for(Player p : Rule.c.keySet()) {
				if(time%50 == 0) {
					ARSystem.giveBuff(p, new Panic(p), 100);
				} else {
					if(Rule.buffmanager.GetBuffTime(p, "panic") > 0) {
						Rule.buffmanager.selectBuffAddTime(p, "panic", -300);
					}
				}
			}
		}
		if(buff.contains("b11") && time%11 == 0) {
			Player t = null;
			float pw = 0;
			
			for(Player p : Rule.c.keySet()) {
				if(Rule.buffmanager.GetBuffTime(p, "panic") > pw) {
					t = p;
					pw = Rule.buffmanager.GetBuffTime(p, "panic");
				}
			}
			if(t != null) {
				ARSystem.playSound(t,"healbullet");
				Rule.buffmanager.selectBuffTime(t, "panic", 0);
				ARSystem.addBuff(t, new Nodamage(t), 40);
			}
		}
		if(buff.contains("b12") && time%50 == 0) {
			for(Player p : Rule.c.keySet()) {
				boolean d = false;
				for(int i =0; i<10; i++) {
					if(Rule.c.get(p).cooldown[i] > 0) {
						d = true;
						Rule.c.get(p).hpCost(Rule.c.get(p).setcooldown[i], true);
					}
				}
				if(d) ARSystem.spellCast(p,p, "bload");
			}
		}
		if(buff.contains("b13")) {
			for(Player p : Rule.c.keySet()) {
				if(time%39 == 0) {
					if(p.getHealth() < p.getMaxHealth()) {
						ARSystem.spellCast(p,p, "bload");
						Rule.c.get(p).hpCost((p.getMaxHealth()-p.getHealth())*3, true);
					}
				}
				if(time%1 == 0) {
					ARSystem.heal(p, 1);
				}
			}
		}
		if(buff.contains("b14")) {
			if(mobcount > mobs.size()) {
				int c = mobs.size() - mobcount;
				for(Player p : Rule.c.keySet()) {
					for(int i =0; i<10; i++) {
						if(Rule.c.get(p).cooldown[i] > 0) {
							Rule.c.get(p).cooldown[i] -= 1;
						}
					}
					Rule.c.get(p).skillmult += 0.02f;
				}
			}
		}
		if(buff.contains("b16")&& time%64 == 0) {
			ARSystem.playSoundAll("snowquine");
			for(LivingEntity e : mobs) {
				ARSystem.giveBuff(e, new Ice(e, bast), 140);
			}
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new Nodamage(p), 140);
			}
		}
		if(buff.contains("b17")) {
			for(Player p : Rule.c.keySet()) {
				if(Rule.buffmanager.GetBuffTime(p, "panic") > 0) {
					Rule.buffmanager.selectBuffAddTime(p, "panic", -60);
				}
			}
		}
		if(buff.contains("b20")&& time%2 == 0) {
			if(time%8 == 0) {
				Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
				p.damage(p.getHealth() *0.4 + Math.min(0.4, 0.05f * Rule.c.keySet().size()),NpcPlayer.npc(Map.randomLoc()));
			}
			for(Player p : Rule.c.keySet()) {
				ARSystem.heal(p, 1.5);
			}
		}
	}

	int havenTimer = 0;
	public void debuff() {
		if(debuff.contains("o11") && time%10 == 0) {
			if(AMath.random(2) <= 1) {
				Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
				if(AMath.random(2) <= 1) {
					 ARSystem.giveBuff(p, new Silence(p), 100);
				} else {
					p.teleport(Map.randomLoc());
				}
				ARSystem.playSound(p,"defult");
			} else {
				LivingEntity mob = mobs.get(AMath.random(mobs.size())-1);
				ARSystem.playSound(mob,"defult");
				mob.teleport(Map.randomLoc());
			}
		}

		if(debuff.contains("o12") && time%44-Math.min(22,level*5) == 0) {
			Location l = Map.randomLoc();
			ARSystem.playSoundAll("spiderpop");
			for(int i =0; i<3*v; i++) {
				LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(Map.randomLoc(), EntityType.SPIDER);
				e.setMaxHealth((6 + level*2)*hpmult);
				e.setHealth((6 + level*2)*hpmult);
				e.setCustomName("§f"+Text.get("lobo:o12"));
				mobs.add(e);
			}
		}
		if(debuff.contains("o13") && time%33 == 0) {
			Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
			ARSystem.playSound((Entity)p,"sakura");
			ARSystem.giveBuff(p, new Fascination(p, mobs.get(AMath.random(mobs.size())-1)), 60 + 10*level , 0.2+ 0.1*level);
		}
		if(debuff.contains("o15") && time%25 == 0) {
			Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
			ARSystem.playSound((Entity)p,"dream");
			ARSystem.giveBuff(p, new Sleep(p), 100 , 1.5+ 0.3*cr.size());
		}
		if(debuff.contains("o21") && time%120 == 0) {
			String txt = Text.get("lobo:"+level+"-"+(count-1));
			if(txt == null) txt = Text.get("lobo:"+level+"-"+count);
			String[] s = txt.split(",");
			String[] mob = s[AMath.random(s.length)-1].split(":");
			spawn(mob[0] , Integer.parseInt(mob[1]),true);
			ARSystem.playSoundAll("babycry");
		}
		
		if(debuff.contains("o22") && time%5 == 0) {
			if(AMath.random(5)<=1) {
				Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
				ARSystem.spellLocCast(NpcPlayer.npc(Map.randomLoc()), p.getLocation().add((30-AMath.random(60))*0.1,0,(30-AMath.random(60))*0.1), "lobo_prj");
			} else {
				ARSystem.spellLocCast(NpcPlayer.npc(Map.randomLoc()), Map.randomLoc(), "lobo_prj");
			}
		}
		
		if(debuff.contains("o23") && time%(80-Math.min(60,cr.size()*5)) == 0) {
			Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
			etcmobs.add(Map.spawnMM("helper", p.getLocation()));
		}
		if(debuff.contains("o25") && time%39 == 0) {
			etcmobs.add(Map.spawnMM("bunny", Map.randomLoc()));
		}
		if(debuff.contains("o31") && time%88-Math.min(63,cr.size()*3) == 0) {
			Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
			ARSystem.playSoundAll("redshoo");
			ARSystem.giveBuff(p, new PowerUp(p), 400, 3);
			ARSystem.giveBuff(p, new Rampage(p), 400, 2);
			String name = p.getCustomName();
			p.setCustomName(Text.get("lobo:o31_t"));
			ARSystem.giveBuff(p, new Fascination(p, ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1])), 400, 2);
			double hp = p.getMaxHealth();
			p.setMaxHealth(p.getMaxHealth()*5);
			p.setHealth(p.getHealth()*5);
			Rule.team.teamQuit("H", p);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				p.setHealth(p.getHealth()/5);
				p.setMaxHealth(hp);
				Rule.team.teamJoin("H", p);
				p.setCustomName(name);
			},400);
			
		}
		if(debuff.contains("o33") && time%77 == 0) {
			ARSystem.playSoundAll("train");
			Location l = Map.getCenter().clone().add((50-AMath.random(100))*0.2,0,(50-AMath.random(100))*0.2);
			l.setY(Map.loc_f.getY()+2.5f);
			if(AMath.random(10) <= 3) l = ((Player)Rule.c.keySet().toArray()[AMath.random(mobs.size())-1]).getLocation();
			if(AMath.random(15) <= 1) { l.setX(111);l.setZ(262);l.setPitch(0);l.setY(56);l.setYaw(0); }
			ARSystem.spellLocCast(NpcPlayer.npc(l), l, "lobo_trin");
		}
		if(debuff.contains("o34")) {
			boolean pt = false;
			boolean not = false;
			for(LivingEntity m : etcmobs) {
				if(m.getCustomName().contains("파고드는")) {
					for(Player p : Rule.c.keySet()) {
						for(Entity e : ARSystem.PlayerBeamBox(p, 50, 3, box.ALL)) {
							if(e == m) {
								havenTimer = 0;
							}
						}
					}
					pt = true;
					if(havenTimer <= 10) {
						havenTimer++;
					} else if(havenTimer > 10){
						if(AMath.random(10) <= 2) {
							m.teleport(Map.randomLoc());
						}
						ARSystem.playSoundAll("heven");
						for(Player p : Rule.c.keySet()) {
							p.damage(1,m);
						}
					}
				} else {
					not = true;
					havenTimer = 0;
				}
			}
			if(!pt && (not || etcmobs.size() <= 0)) etcmobs.add(Map.spawnMM("haven", Map.randomLoc()));
			
		}
		if(debuff.contains("o35") && time%39-Math.min(20,level*5) == 0) {
			Location l = Map.getCenter();
			ARSystem.playSoundAll("shark");
			if(AMath.random(3) <= 1) {
				l.setX(111);l.setZ(262);l.setPitch(0);
				if(AMath.random(3) <= 1) {
					l.setY(50);l.setYaw(180);
				} else {
					l.setY(56);l.setYaw(0);
				}
			} else {
				l = Map.getCenter().clone().add((50-AMath.random(100))*0.2,0,(50-AMath.random(100))*0.2);
				l.setY(Map.loc_f.getY()+1.5f);
				l.setYaw(AMath.random(360));
			}
			ARSystem.spellLocCast(NpcPlayer.npc(l), l, "shark");
		}
		if(debuff.contains("o43") && mobcount > mobs.size()) {
			int count =  mobcount - mobs.size();
			ARSystem.playSoundAll("c1139s12");
			for(int i =0; i < count; i++) {
				Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
					Location l = Map.getCenter().clone().add((50-AMath.random(100))*0.15,0,(50-AMath.random(100))*0.15);
					l.setY(Map.loc_f.getY()+1.5f);
					if(AMath.random(3) <= 1) l = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]).getLocation();
					l.setYaw(AMath.random(360));
					ARSystem.spellLocCast(NpcPlayer.npc(l),l,"lobo_matan");
				},i*4);
			}
		}
		if(debuff.contains("o45") && time%8 == 0) {
			ARSystem.spellLocCast(NpcPlayer.npc(Map.randomLoc()), Map.randomLoc(), "apple");
		}
		if(debuff.contains("o51") && stageTime%123 == 0) {
			double hp = 0;
			Player f = null;
			for(Player p : Rule.c.keySet()) {
				if(p.getHealth() > hp) {
					hp = p.getHealth();
					f = p;
				}
			}
			ARSystem.playSound((Entity)f, "0swrod5", 0.2f, 3);
			f.setHealth(1);
		}
		if(debuff.contains("o52") && time%3 == 0) {
			ARSystem.playSoundAll("bluestar");
			for(Player p : Rule.c.keySet()) {
				if(Rule.buffmanager.GetBuffTime(p, "panic") > 0) {
					p.damage(5,NpcPlayer.npc(Map.randomLoc()));
				} else {
					p.damage(1,NpcPlayer.npc(Map.randomLoc()));
				}
			}
		}
		if(Bgm.bgmcode.equals("m5")) {
			if(time%3 == 0) {
				for(Player p : Rule.c.keySet()) {
					ARSystem.giveBuff(p, new Silence(p), 20, 0);
				}
			}
			if(time%10 == 0) {
				for(LivingEntity e : mobs) {
					ARSystem.heal(e, e.getMaxHealth()*0.15 + 8);
					ARSystem.potion(e, 22, 40, level/2);
				}
			}
		}
		if(debuff.contains("o54") && time%3 == 0) {
			List<Entity> dh = new ArrayList<>();
			
			if(love.size() <= 3) {
				Entity e = vilager.get(AMath.random(vilager.size())-1);
				int i = 0;
				while(love.contains(e)) {
					if(AMath.random(3) <= 2) {
						e = vilager.get(AMath.random(vilager.size())-1);
					} else if(AMath.random(3) <= 2) {
						e = mobs.get(AMath.random(mobs.size())-1);
					} else {
						e = ARSystem.RandomPlayer();
					}
					i++;
					if(i > 1000) break;
				}
				love.add((LivingEntity)e);
			}
			
			for(LivingEntity e : love) {
				if(vilager.contains(e)) {
					if(AMath.random(10) <= 2) {
						Entity t = ARSystem.boxSOne(e, new Vector(30, 8, 30), box.TARGET);
						if(t != null) ARSystem.giveBuff(e, new Fascination(e, (LivingEntity)t), 40, 3);
					}
					ARSystem.potion(e, 1, 200, 4);
				}
				ARSystem.playSound(e, "slimegirl");
				ARSystem.spellCast(NpcPlayer.npc(Map.randomLoc()), e, "lobo_slime");
				for(Entity en : ARSystem.box(e, new Vector(5,5,5), box.ALL)) {
					if(!love.contains(en) && AMath.random(10) <= 2) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
							love.add((LivingEntity)en);
						},20);
					}
					((LivingEntity)en).damage(3 + cr.size()/2, NpcPlayer.npc(e.getLocation()));
				}
				if(e.getHealth() < 1 || e.isDead() || (e instanceof Player && ((Player) e).getGameMode() != GameMode.ADVENTURE)) {
					dh.add(e);
				}
			}
			for(Entity d : dh) {
				love.remove(d);
			}
			boolean all = true;
			for(Player p : Rule.c.keySet()) {
				if(!love.contains(p)) {
					all = false;
				}
			}
			if(all && cr.size() >= 3) {
				ARSystem.playSoundAll("slimegirl");
				for(Player p : Rule.c.keySet()) {
					for(int i =0; i< 20; i++) {
						Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
							ARSystem.spellCast(p, p, "bload");
							ARSystem.spellCast(p ,p, "lobo_slime");
							Rule.c.get(p).hpCost(0.5, false);
						},i);
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
						ARSystem.Death(p, NpcPlayer.npc(p.getLocation()));
					},20);
				}
			}
		}
		if(debuff.contains("o55") && time%14 == 0) {
			ARSystem.playSoundAll("blackswan");
			for(LivingEntity e : mobs) {
				ARSystem.spellLocCast(NpcPlayer.npc(e.getLocation()), e.getLocation(), "blackswan");
				Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
					ARSystem.giveBuff(e, new Reflect(e), 40, 2);
				},20);
			}
		}
	}
	
	public void addbuff() {
		int rd = 5;
		if(level == 2) rd = 11;
		if(level == 3) rd = 17;
		if(level > 3) rd = 23;
		
		int a,b,c;
		a=b=c=AMath.random(rd);
		
		while(buff.contains("b"+a)) a = AMath.random(rd);
		while(buff.contains("b"+b) || a == b) b = AMath.random(rd);
		while(buff.contains("b"+c) || a == c || b == c) c = AMath.random(rd);
		new G_Lobotomy(bast,item(a,b,c), new int[]{a,b,c});
	}

	public void removedebuff() {
		int r = 40-Math.min(30,level*7);
		if(level >= 9) r = 30;
		if(debuff.size() > 0 && AMath.random(100) <= r) {
			int rd = AMath.random(debuff.size())-1;
			text(393,Text.get("lobo:"+debuff.get(rd)) + Text.get("lobo:o2"));

			debuff.remove(rd);
		}
	}
	private ItemStack[] item(int a,int b,int c) {
		ItemStack[] i = new ItemStack[3];
		i[0] = ItemCreate.Lore(ItemCreate.Item(277), "§f"+Main.GetText("lobo:b"+a), Text.getLine("lobo:b"+a+"_lore", 1));
		i[1] = ItemCreate.Lore(ItemCreate.Item(277), "§f"+Main.GetText("lobo:b"+b), Text.getLine("lobo:b"+b+"_lore", 1));
		i[2] = ItemCreate.Lore(ItemCreate.Item(277), "§f"+Main.GetText("lobo:b"+c), Text.getLine("lobo:b"+c+"_lore", 1));
		return i;
	}
	
	static public void addbuff(String st) {
		buff.add("b"+st);
		if(st.equals("10")) {
			for(LivingEntity v : vilager) {
				v.remove();
			}
		}
		text(392,Text.get("lobo:b_1") +"§a§l《§f§l"+Text.get("lobo:b"+st) + "§a§l》§f"+Text.get("lobo:b_2"));
		itemmsg += Text.get("lobo:b"+st) +"\n";
		Bukkit.broadcastMessage(Text.get("lobo:b"+st));
		for(String s : Text.getLine("lobo:b"+st+"_lore",1)) {
			Bukkit.broadcastMessage("§7§l"+ s);
			itemmsg += "§7§l"+ s +"\n";
		}
	}
	
	public void adddebuff() {
		int max = level;
		if(level > 5) max = 5;
		if(level >= 9) max = 8;
		
		if(level > 0) {
			if(debuff.size() < max && AMath.random(100) <= 10+ (max - debuff.size())*15) {
				int df = AMath.random(Math.min(max, 5));
				int dl = AMath.random(5);
				while(debuff.contains("o"+df+dl) || (level > 8 && df == 2 && dl == 1)) {
					df = AMath.random(Math.min(max, 5));
					dl = AMath.random(5);
				}

				if(df == 5 && df == dl) {
					hpmult +=0.5;
				}
				if(df == 5 && dl == 4) {
					love.clear();
				}
				if(df == 5 && dl == 3 && !Bgm.bgmcode.equals("m5")) {
					Bgm.setBgm("m5");
					text(393,Text.get("lobo:o"+df+dl) + Text.get("lobo:o1"));
				} else if(!(df == 5 && dl == 3)){
					debuff.add("o"+df+dl);
					text(393,Text.get("lobo:o"+df+dl) + Text.get("lobo:o1"));
				}
			}
		}
	}

	public static void adddebuff(String st) {
		debuff.add("o"+st);
		if(st.equals("51")) {
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new Exposure(p), 200000, 5);
			}
		}
		text(393,Text.get("lobo:o"+st) + Text.get("lobo:o1"));
		
	}
	public void tick(int time) {
		this.time = time;
		repBgm();
		if(sleep > 0) {
			sleep--;
			return;
		}
		stageTime++;

		if(mobs.size() <= 0) {
			String txt = Text.get("lobo:"+level+"-"+count);
			if(txt == null) {
				ARSystem.opCommand("c removeall");
				level++;
				count = 0;
				sleep = 10;
				stageTime = -50;
				txt = Text.get("lobo:"+level+"-"+count);
				if(txt == null) {
					endGame();
					sleep = 10000;
				} else {
					//팀장 뽑기
					if(bast == null) {
						Player p = null; int sc = -10000;
						for(Player l : Rule.c.keySet()) {
							if(Rule.c.get(l).score > sc) {
								sc = Rule.c.get(l).score;
								p = l;
							}
						}
						bast = p;
						sleep+=5;
						text(394,"§7§l§n"+ bast.getName()+"§f"+Text.get("lobo:team"));
						Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
							addbuff();
						},100);
					} else {
						addbuff();
					}
				}
				removedebuff();
				removedebuff();
			} else {
				if(count == 0 && level > 0) nextGame();
				removedebuff();
				adddebuff();
				if(level == 9) {
					for(int i = 0; i<Math.max(1, count/2.3);i++) removedebuff();
					for(int i = 0; i<Math.max(1, count/2.3);i++) adddebuff();
				}
				if(level >= 2 && count == 2 && count == 4) {
					for(Player p : Rule.c.keySet()) {
						if(Rule.c.get(p) instanceof c38hajime) {
							if(Rule.c.get(p).isps) ((c38hajime)Rule.c.get(p)).wepone();
						}
					}
				}
				dango = true;
				rvv.clear();
				for(LivingEntity en : vilager) if(en.getHealth() / en.getMaxHealth() < 0.8|| en.isDead()) rvv.add(en);
				for(LivingEntity en : rvv) {
					villager_Death++;
					if(villager_Death == 30) text(393,"§f"+Text.get("lobo:dango1"));
					if(villager_Death == 80) text(393,"§f"+Text.get("lobo:dango2"));
					if(villager_Death == 150) text(393,"§f"+Text.get("lobo:dango3"));
					
					vilager.remove(en);
					en.remove();
				}
				
				int c = 5 + (int)(level*1.4);
				if(buff.contains("b10")) c-= 5;
				float pw = 0;
				if(count == 0 && level == 0) {
					pw = 50;
					Bukkit.broadcastMessage(Text.get("lobo:t2") + AMath.round(pw,2) +"%");
				} else {
					if(vilager.size() == 0) {
						pw = -20;
						if(level >= 9) pw = -10;
						if(dmg <= 0.5f) pw *= 0.3;
						Bukkit.broadcastMessage(Text.get("lobo:t4") + AMath.round(pw,2) +"%");
						
					} else if(vilager.size() == c) {
						pw = 5;
						if(level > 6) {
							pw = 10;
						}
						Bukkit.broadcastMessage(Text.get("lobo:t2") + AMath.round(pw,2) +"%");
					} else {
						pw = (4f * (float)vilager.size()/(float)c);
						Bukkit.broadcastMessage(Text.get("lobo:t1") + AMath.round(pw,2) +"%");
					}
				}
				dmg += pw*0.01;
				for(Player p : Rule.c.keySet()) {
					Rule.c.get(p).frist_damage += pw*0.01;
				}
				while(vilager.size() < c) {
					vilager.add(Map.spawnMM("human", Map.randomLoc()));
				}
				for(LivingEntity e : etcmobs) {
					e.remove();
				}
				etcmobs.clear();
				for(String s : txt.split(",")) {
					String[] mob = s.split(":");
					spawn(mob[0] , Integer.parseInt(mob[1]),true);
				}
				count++;
				if(buff.contains("b2")) {
					for(Player p : Rule.c.keySet()) {
						if(Rule.buffmanager.GetBuffValue(p, "barrier") < 12) {
							Rule.buffmanager.selectBuffValue(p, "barrier", 12);
						}
					}
				}
				if(buff.contains("b18")) {
					for(Player p : Rule.c.keySet()) {
						ARSystem.heal(p, p.getMaxHealth() * 0.2);
						ARSystem.giveBuff(p, new Nodamage(p), 200);
					}
				}

				if(debuff.contains("o14")) {
					Location l = Map.randomLoc();
					for(int i =0; i<1*v; i++) {
						LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(Map.randomLoc(), EntityType.ZOMBIE);
						e.setMaxHealth((20 + level*5)*hpmult);
						e.setHealth((20 + level*5)*hpmult);
						e.setCustomName("§f"+Text.get("lobo:o14"));
						ARSystem.potion(e, 1, 10000, level);
					}
				}
				if(debuff.contains("o24")) {
					Location l = Map.randomLoc();
					for(int i =0; i<1*v; i++) {
						LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(Map.randomLoc(), EntityType.VEX);
						e.setMaxHealth((18 + level*8)*hpmult);
						e.setHealth((18 + level*8)*hpmult);
						e.setCustomName("§f"+Text.get("lobo:o24"));
					}
				}
				if(debuff.contains("o44")) {
					Location l = Map.randomLoc();
					for(int i =0; i<2*v; i++) {
						LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(Map.randomLoc(), EntityType.EVOKER);
						e.setMaxHealth((200 + level*20)*hpmult);
						e.setHealth((200 + level*20)*hpmult);
						e.setCustomName("§f"+Text.get("lobo:o44"));
						ARSystem.potion(e, 11, 20000, 3);
					}
				}
			}
			if(level == 9 && count > 3) {
				for(Player p : Rule.c.keySet()) {
					if(Rule.c.get(p).frist_damage >= 4) {
						Rule.c.get(p).frist_damage *= 0.55;
					} else {
						Rule.c.get(p).frist_damage *= 0.75;
					}
					if(count > 5) {
						Rule.c.get(p).frist_defence *= 2f;
					} else if(count > 4) {
						Rule.c.get(p).frist_defence *= 1.5f;
					}
				}
			}
			String s = "§7";
			for(String o : debuff) s += " "+ Text.get("lobo:"+o) +"§f§l,§7";
			if(debuff.size() <=0) s = "§a -";

			s = "["+level+"-"+(count+1)+"]§c§l" +"\n"+ Text.get("lobo:team1") +bast.getName()  
			+"\n§c" + Text.get("lobo:t5") + villager_Death
			+"\n" + Text.get("lobo:t3")+ (int)(dmg*100) +"%" + "\n§a=-=-=-=-=-=-=-=-=-=-=-=-=-=-=§f\n" + itemmsg + "\n§a=-=-=-=-=-=-=-=-=-=-=-=-=-=-=§f\n" + Text.get("lobo:o3") + s + "\n";
			for(Player p : Bukkit.getOnlinePlayers()) 
				p.spigot().sendMessage(Text.hover(p,"§a§l"+Text.get("lobo:t0"), s));
			
		}
		v = 1;
		if(buff.contains("b18")) v +=0.3;
		if(debuff.contains("o32")) v +=0.5;
		for(LivingEntity e : Map.mm.getAllMythicEntities()) {
			if(!mobs.contains(e) && !etcmobs.contains(e) && !vilager.contains(e) && !e.isDead() && e.getHealth() >= 1) {
				e.setMaxHealth(e.getMaxHealth() *hpmult);
				e.setHealth(e.getMaxHealth());
				mobs.add(e);
			}
		}
		buff();
		debuff();
		moblist();
		for(Player p : Rule.c.keySet()) {
			if(rep.get(p) != Rule.c.get(p)) {
				rep.put(p, Rule.c.get(p));
				charRep(p);
			}
		}
	}
	
	public LivingEntity spawn(String name, int size,boolean msg) {
		LivingEntity en = null;
		size *= v;
		
		for(int i = 0; i < size; i++) {
			if(name.equals("dango")){
				if(villager_Death >= 30) {
					if(villager_Death >= 80) {
						en = Map.spawnMM("dango2" , Map.randomLoc());
						if(villager_Death >= 150) {
							en.setMaxHealth(en.getMaxHealth()*2);
							en.setHealth(en.getMaxHealth());
							ARSystem.potion(en, 10, 100000, 8);
							
							Reflect rep = new Reflect(en);
							rep.setValue(0.2);
							rep.SetNoDamage(true);
							rep.SetTargetEffect("c25_s2_e");
							rep.setDelay(60);
							ARSystem.giveBuff(en, rep, 100000);
						} else {
							en.setMaxHealth(en.getMaxHealth());
							en.setHealth(en.getMaxHealth());
							ARSystem.potion(en, 10, 100000, 4);
						}
					} else {
						en = Map.spawnMM(name, Map.randomLoc());
						en.setMaxHealth(en.getMaxHealth() * 1.5);
						en.setMaxHealth(en.getHealth());
						ARSystem.potion(en, 10, 100000, 2);
						ARSystem.potion(en, 11, 100000, 1);
					}
				}
			} else if(debuff.contains("o41")) {
				if(name.equals("c")) {
					en = Map.spawnMM("c2", Map.randomLoc());
				} else if(name.equals("sn")) {
					en = Map.spawnMM("sn2", Map.randomLoc());
				} else if(name.equals("food")) {
					en = Map.spawnMM("food3", Map.randomLoc());
				} else if(name.equals("food2")) {
					en = Map.spawnMM("food4", Map.randomLoc());
				} else {
					en = Map.spawnMM(name, Map.randomLoc());
				}
			} else {
				en = Map.spawnMM(name, Map.randomLoc());
			}
			if(en != null) {
				en.setMaxHealth(AMath.round(en.getMaxHealth() * hpmult,2));
				en.setHealth(en.getMaxHealth());
				mobs.add(en);
			}
		}
		if(en != null && msg) text(394,en.getCustomName() + "§f"+Text.get("lobo:exit"+AMath.random(2)));
		return en;
	}
	
	public static void nextGame() {
		if(buff.contains("b4")) {
			ARSystem.playSoundAll("promise");
		}
		if(buff.contains("b22")) {
			ARSystem.playSoundAll("girlkiss");
		}
		
		for(String pl : cr.keySet()) {
			Player p = Bukkit.getPlayer(pl);
			if(p == null) continue;
			Rule.c.put(p,GetChar.get(p, Rule.gamerule, "" + cr.get(pl)));
			charRep(p);
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			Rule.team.teamJoin("H", p);
		}
		stageTime = 0;
	}
	
	public static void text(int i ,String s) {
		for(Player pl : Bukkit.getOnlinePlayers()) {
			AdvManager.set(pl, i, 0, s);
		}
		System.out.println("Lobotomy :"+s);
	}
	
	public void repBgm() {
		String s = Bgm.bgmcode;
		rating = mobs.size()+(level*4) + debuff.size()*2;
		if(level >= 9) rating = 1000;
		if(!s.equals("m5")){
			if(rating >= 100) {
				if(!s.equals("m4")) Bgm.setBgm("m4");
			} else if(rating >=30) {
				if(!s.equals("m3")) Bgm.setBgm("m3");
			} else if(rating >=10) {
				if(!s.equals("m2")) Bgm.setBgm("m2");
			} else if(rating < 10) {
				if(!s.equals("m1")) Bgm.setBgm("m1");
			}
		}
	}
	

	boolean dango = false;
	List<LivingEntity> rmv = new ArrayList<LivingEntity>();
	List<LivingEntity> rvv = new ArrayList<LivingEntity>();
	public void moblist() {
		rvv.clear();
		rmv.clear();
		mobcount = mobs.size();
		
		for(LivingEntity en : mobs) if(en.getHealth() < 1 || en.isDead()) rmv.add(en);
		for(LivingEntity en : vilager) if(en.getHealth() < 1 || en.isDead()) rvv.add(en);
		
		for(LivingEntity en : rmv) mobs.remove(en);
		for(LivingEntity en : rvv) {
			villager_Death++;
			if(villager_Death == 30) text(393,"§f"+Text.get("lobo:dango1"));
			if(villager_Death == 80) text(393,"§f"+Text.get("lobo:dango2"));
			if(villager_Death == 150) text(393,"§f"+Text.get("lobo:dango3"));
			vilager.remove(en);
		}

		for(Player p : Bukkit.getOnlinePlayers()) p.setLevel(mobs.size());


		int c = 5 + (int)(level*1.4);
		if(buff.contains("b10")) c-= 5;
		int size = vilager.size();

		for(LivingEntity en : vilager) if(en.getHealth()/en.getMaxHealth() <= 0.8 || en.isDead()) size--;
		for(LivingEntity en : rmv) {
			if(!Map.inMap(en.getLocation())) {
				en.teleport(Map.randomLoc());
			}
		}
		
		if(dango && c - size >= 5 && mobcount > 0) {
			dango = false;
			if(level >= 3) {
				int count = 1;
				if(level > 5) count = 2;
				if(level > 7) count = 3;
				spawn("dango" , count ,true);
			}
		}
	}
	
	public static void dieMsg(Player name) {
		if(AMath.random(2) <= 1) {
			text(394,Text.get("lobo:death1") +"§7§l§n"+ name.getName()+"§f" + Text.get("lobo:death1-2"));
		} else {
			text(394,"§7§l§n"+ name.getName()+"§f" + Text.get("lobo:death2"));
		}
		
		for(Player p : Rule.c.keySet()) {
			ARSystem.addBuff(p, new Panic(p), 60+(level*20));
		}
		if(name == bast) {
			for(Player p : Rule.c.keySet()) {
				ARSystem.addBuff(p, new Panic(p), 30+(level*30));
			}
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
			if(Rule.c.size() < 1) {
				text(394,Text.get("lobo:fail"));
				ARSystem.GameStop();
			} else {
				if(buff.contains("b7") && bast == name) {
					for(Player p : Rule.c.keySet()) {
						Rule.c.get(p).skillmult -= 1.2;
					}
				}
				if(buff.contains("b15")) {
					Player t=null;double h = 2;
					for(Player p : Rule.c.keySet()) {
						if(p.getHealth()/p.getMaxHealth() < h) {
							t = p;
							h = p.getHealth()/p.getMaxHealth();
						}
					}
					ARSystem.heal(t, 9999);
					Rule.c.get(t).frist_damage *= 1.25;
					Bukkit.broadcastMessage(Text.get("lobo:ad") + t.getName());
				}
				if(buff.contains("b19") &&!timer && Rule.c.size() == 2) {
					ARSystem.playSoundAll("warptime");
					count = 0;
					timer = true;
					sleep = 10;
					for(Player p : Rule.c.keySet()) {
						ARSystem.giveBuff(p, new Nodamage(p), 400);
					}
					
					ARSystem.opCommand("mm m killall");
					ARSystem.opCommand("killall monster");
					ARSystem.opCommand("killall animals");
					ARSystem.opCommand("killall villager");
					Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
					nextGame();
					},140);
				}
				

				if(debuff.contains("o42")) {
					ARSystem.playSoundAll("queenbeespawn");
					ARSystem.spellLocCast(NpcPlayer.npc(name.getLocation()),name.getLocation(), "lobo_bee");
					if(cr.size() > 2) ARSystem.spellLocCast(NpcPlayer.npc(name.getLocation()),name.getLocation(), "lobo_bee");
					if(cr.size() > 4) ARSystem.spellLocCast(NpcPlayer.npc(name.getLocation()),name.getLocation(), "lobo_bee");
					if(cr.size() > 8) ARSystem.spellLocCast(NpcPlayer.npc(name.getLocation()),name.getLocation(), "lobo_bee");
					if(cr.size() > 13) ARSystem.spellLocCast(NpcPlayer.npc(name.getLocation()),name.getLocation(), "lobo_bee");
					
				}
			}
		},0);
	}
	
	public void endGame() {
		text(394,Text.get("lobo:end"));
		for(Player p : rep.keySet()) {
			Rule.playerinfo.get(p).tropy(0, 40);
		}
		ARSystem.GameStop();
	}

}
