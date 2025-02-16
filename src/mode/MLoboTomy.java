package mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Lobotomy;
import buff.Exposure;
import buff.Panic;
import buff.Reflect;
import buff.Silence;
import buff.TimeStop;
import chars.c.c00main;
import chars.c.c38hajime;
import manager.AdvManager;
import manager.Bgm;
import mob.M_LBDefence2;
import mob.M_ROCKREE;
import mode.lobobuff.LoboBuffBase;
import mode.lobobuff.RB_b047;
import types.GameModes;
import types.LoboBuffs;
import types.MapType;
import types.MobBuffs;
import util.AMath;
import util.GetChar;
import util.ItemCreate;
import util.Map;
import util.Text;


public class MLoboTomy extends ModeBase{
	public class lobobuff {
		lobobuff(float hp,float damage,float lastcooldown) {
			this.hp = hp;
			this.damage = damage;
			this.lastcooldown = lastcooldown;
		}
		public lobobuff Cooldown(int skill,int cooldown) {
			this.cooldown[skill] = cooldown;
			return this;
		}
		float hp = 1;
		float damage = 1;
		float cooldown[] = new float[] {1,1,1,1,1,1,1,1,1,1};
		float lastcooldown = 1;
	}
	
	public static int rating = 0;
	public static int count = 0;
	public static int level = 0;
	public static double hpmult = 1;
	public static HashMap<Integer,List<Integer>> upgrad = new HashMap<>();
	public static HashMap<Integer,List<Integer>> mobupgrad = new HashMap<>();
	public static HashMap<String,Integer> cr = new HashMap<>();
	public static HashMap<Player,c00main> rep = new HashMap<>();
	
	public static List<LivingEntity> mobs = new ArrayList<LivingEntity>();
	public static List<LivingEntity> etcmobs = new ArrayList<LivingEntity>();
	public static List<LivingEntity> vilager = new ArrayList<LivingEntity>();
	
	public static List<LoboBuffBase> buff = new ArrayList<LoboBuffBase>();
	public static List<LoboBuffBase> debuff = new ArrayList<LoboBuffBase>();
	
	public static int banDebuff = 0;
	
	public static Player bast = null;
	public static int sleep = 5;
	static int stageTime = 0;
	int time = 0;
	int mobcount = 0;
	public int humanDelect = 0;
	int villager_Death = 0;
	public float v = 1;
	public float mobCountValue = 0;
	static String itemmsg = "";
	static public float dmg = 1;
	static int buffDelay = 0;

	public int upcount;
	private boolean humanSpawn;
	public static int humanMax = 0;
	public static int difficulty = 3;
	
	public MLoboTomy(){
		super();
		if(!(boolean)Rule.Var.Load("System.info.mode.loboclear")) isSecret = true;
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
		onbuff.put(15, new lobobuff(1f,2f,0.5f));
		onbuff.put(16, new lobobuff(2f,1f,1f));
		onbuff.put(19, new lobobuff(1.5f,1.4f,1f));
		onbuff.put(20, new lobobuff(1f,2f,0.75f));
		onbuff.put(22, new lobobuff(1.3f,1.8f,1f));
		onbuff.put(24, new lobobuff(1f,1.5f,1.5f));
		onbuff.put(26, new lobobuff(1f,1.1f,0.5f));
		onbuff.put(27, new lobobuff(1.5f,2f,1f));
		onbuff.put(29, new lobobuff(1.5f,2f,0.4f));
		onbuff.put(30, new lobobuff(1.5f,2.2f,1f));
		onbuff.put(31, new lobobuff(0.4f,2.4f,0.3f));
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
		onbuff.put(58, new lobobuff(1.5f,0.6f,1f));
		onbuff.put(59, new lobobuff(1.5f,2f,1f));
		onbuff.put(60, new lobobuff(1f,1.8f,1f));
		onbuff.put(61, new lobobuff(1f,2f,1f));
		onbuff.put(62, new lobobuff(1f,2.5f,1f));
		onbuff.put(63, new lobobuff(1.5f,1.5f,1f).Cooldown(0, 1000));
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
		onbuff.put(140, new lobobuff(1.2f,1.4f,0.8f).Cooldown(0, 1000));
		onbuff.put(141, new lobobuff(1f,1.5f,1f));
		onbuff.put(142, new lobobuff(1f,2f,1f));
		onbuff.put(143, new lobobuff(1f,2f,1f));
		onbuff.put(145, new lobobuff(1f,2f,1f));
		onbuff.put(146, new lobobuff(1f,0.75f,1f));
		onbuff.put(148, new lobobuff(1f,1.5f,1f));
		onbuff.put(149, new lobobuff(1.8f,2f,1f));
		onbuff.put(150, new lobobuff(1.2f,1f,0.8f));
		onbuff.put(151, new lobobuff(1.3f,0.6f,0.8f).Cooldown(0, 20));
		onbuff.put(152, new lobobuff(1.3f,0.6f,0.8f).Cooldown(0, 20));
		onbuff.put(154, new lobobuff(1.5f,1.4f,1f));
		onbuff.put(155, new lobobuff(1.3f,1.2f,0.7f));
		onbuff.put(156, new lobobuff(1.4f,1.8f,2f));
		onbuff.put(157, new lobobuff(1.2f,2f,1f));
		onbuff.put(158, new lobobuff(1f,1.4f,0.6f));
		onbuff.put(159, new lobobuff(1.2f,1.6f,0.8f));
		onbuff.put(160, new lobobuff(0.7f,1.6f,1f));
	}
	
	@Override
	public void option() {
		Map.mapType = MapType.NORMAL;
		if(!(boolean)Rule.Var.Load("System.info.mode.loboclear")) ARSystem.selectGameMode.remove(modeName);
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
		bast = null;
		dmg = 1;
		villager_Death = 0;
		itemmsg = "";
		humanDelect = 0;
		mobCountValue = 0;
		sleep = 0;
		v = 1;
		buffDelay = 0;
		upcount = 3;
		int j = 1;
		for(int i=0;i<1000;i++) {
			if(Text.get("lobo:b"+j) != null) {
				int n = Text.getI("lobo:b"+j+"v");
				if(upgrad.get(n) == null) upgrad.put(n, new ArrayList<Integer>());
				upgrad.get(n).add(j);
				j++;
			} else {
				if(j < 100) {
					j = 101;
				} else {
					break;
				}
			}
		}
		j = 1;
		for(int i=0;i<1000;i++) {
			if(Text.get("lobo:o"+j) != null) {
				int n = Text.getI("lobo:o"+j+"v");
				if(mobupgrad.get(n) == null) mobupgrad.put(n, new ArrayList<Integer>());
				mobupgrad.get(n).add(j);
				j++;
			} else {
				if(j < 100) {
					j = 101;
				} else {
					break;
				}
			}
		}
		if(nobgmList == null) {
			nobgmList = new ArrayList<String>();
			nobgmList.add("m0");
		}

		/*
		System.out.println("===== [ Load Item ] ========\n\n");
		for(Integer i : upgrad.keySet()) {
			System.out.println(Text.get("lobo:lv"+i) + " :  " + upgrad.get(i).size());
		}
		System.out.println("\\n===== [ Load Debuff ] ========\n\n");
		for(Integer i : mobupgrad.keySet()) {
			System.out.println(Text.get("lobo:lv"+i) + " :  " + mobupgrad.get(i).size());
		}
		*/
		banDebuff = getInt("1");
		humanSpawn = getBool("2");
		difficulty = getInt("3");
	}
	static int[] onsp = {1,5,8,10,11,13,14,15,24,26,29,31,32,38,39,40,41,42,44,47,48,49,55,56,57,61,62,63,65,66,67,68,73,76,77,80,86,88,93,94,96,
			99,101,105,106,109,110,112,114,116,120,121,124,126,127,130,131,132,133,136,137,140,141,142,143,145,146,149,150,151,154,156,157,158,159,160};
	static HashMap<Integer,lobobuff> onbuff = new HashMap<>();

	public static void charRep(Player p) {
		ARSystem.removeItemAll(p);
		if(Rule.c.get(p).number == 57 || Rule.c.get(p).number == 86) return;
		List<Integer> onsp = new ArrayList<>() ;
		for(int i : MLoboTomy.onsp) onsp.add(i);
		Rule.c.get(p).frist_damage += (dmg-1);
		if(difficulty < 3) Rule.c.get(p).hp += 5;
		Rule.c.get(p).hp*=1.15;
		for(LoboBuffBase bf : buff) bf.onPlayerChar(p);

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
		

		if(level == 9) {
			if(Rule.c.get(p).frist_damage >= 4) {
				Rule.c.get(p).frist_damage *= 0.55;
			} else {
				Rule.c.get(p).frist_damage *= 0.75;
			}
		}
		
	}
	@Override
	public void firstTick() {
		ARSystem.winstop = 100000000;
		ARSystem.playSoundAll("0select2");
		ARSystem.AniRandomSkill.mob = true;
		text(394,Text.get("lobo:start"));
		if(banDebuff != 0) Bukkit.broadcastMessage("§a§l[§c§l"+Text.get("lobo:ban") +"§a§l] §f: " + Text.get("lobo:o"+banDebuff));
		Bukkit.broadcastMessage("§a§l[§c§l"+Text.get("mode:lobotomy_3") +"§a§l] §f: " + difficulty);
		delay(()->{
			hpmult = Math.max(0.6f, 0.3+0.2*cr.size());
			if(hpmult > 2) hpmult -= (cr.size()-8)*0.1;
			if(difficulty < 2) hpmult *= 0.5f; 
			else if(difficulty > 3) hpmult *= 1 +( (difficulty-3)*0.1);
			
			text(394,Text.get("lobo:rep") +"§7§l ["+ (int)(hpmult*100)+"%]");
		},20);
		if(vilager.size() < 3 && humanSpawn) {
			vilager.add(Map.spawnMM("human", Map.randomLoc()));
		}

		for(Player p : Rule.c.keySet()) {
			int code = Rule.c.get(p).number%1000;
			cr.put(p.getName(),code);
		}
	}
	
	public void addbuff(Player player) {
		int rd = 1;
		if(level == 2) rd = 2;
		if(level == 3) rd = 3;
		if(level > 3) {
			rd = 4;
			if(level > 6) rd = 5;
			else if(level > 4 && AMath.random(5) <= 2) rd = 5;
			
		}
		List<Integer> ints = new ArrayList<>();
		for(int i =0; i < upcount; i++) {
			int size = 0;
			int code = 0;
			do {
				size++;
				code = getRandomBf(rd);			
				if(size > 200) {
					break;
				}
				
			} while(bfcontains(code) || ints.contains(code));
			ints.add(code);
		}
		
		new G_Lobotomy(player,item(ints), ints);
	}

	private List<ItemStack> item(List<Integer> list) {
		List<ItemStack> i = new ArrayList<ItemStack>();
		for(Integer it : list) {
			i.add(ItemCreate.Lore(ItemCreate.Item(277), "§f"+LoboBuffs.getName("b"+it), Text.getLine("lobo:b"+it+"_lore", 1)));
		}
		
		return i;
	}
	
	
	int getRandomBf(int i) {
		int size = 0;
		int r = AMath.random(i);
		if(banDebuff == 0 && AMath.random(300) <= level) r = 6;
		for(int j=0;j<30;j++) {
			size = upgrad.get(r).size();
			if(size<=0) {
				r = AMath.random(i);
			} else {
				break;
			}
		}
		if(size <= 0) return -1;
		return upgrad.get(r).get(AMath.random(size)-1);
	}

	
	@Override
	public void EntityDeathEvent(Entity p, Entity killer) {
		if(!(p instanceof Player)) {
			for(LoboBuffBase bf : buff) bf.onEntityDie((LivingEntity)p,(LivingEntity)killer);
			for(LoboBuffBase bf : debuff) bf.onEntityDie((LivingEntity)p,(LivingEntity)killer);
		}
	}
	
	public void removedebuff() {
		int r = 50-Math.max(40, level*5 + (count*3));
		if(level >= 9) r = 30;
		if(debuff.size() > 0 && AMath.random(100) <= r) {
			int rd = AMath.random(debuff.size())-1;
			text(393,debuff.get(rd).getName() + Text.get("lobo:ot2"));
			debuff.get(rd).onRemove();
			debuff.remove(rd);
		}
	}
	static public void addbuff(String st,int delay) {
		if(buffDelay <= 0) {
			buffDelay = delay;
			addbuff(st);
		}
	}
	static public void addbuff(String st) {
		buff.add(LoboBuffs.get("b"+st));
		int n = Integer.parseInt(st);
		for(int i = 1; i < upgrad.size()+1;i++) {
			for(int j = 0; j < upgrad.get(i).size(); j++) {
				if(upgrad.get(i).get(j) == n) {
					upgrad.get(i).remove(j);	
					break;
				}
			}
		}
		text(392,Text.get("lobo:b_1") +"§a§l《§f§l"+LoboBuffs.getName("b"+st) + "§a§l》§f"+Text.get("lobo:b_2"));
		itemmsg += LoboBuffs.getName("b"+st) +"\n";
		Bukkit.broadcastMessage(LoboBuffs.getName("b"+st));
		for(String s : Text.getLine("lobo:b"+st+"_lore",1)) {
			Bukkit.broadcastMessage("§7§l"+ s);
			itemmsg += "§7§l"+ s +"\n";
		}
	}
	
	public void adddebuff() {
		int max = level;
		if(level > 5) max = 5;
		if(level >= 9) max = 8;
		if(difficulty > 3) max += (difficulty-2)/2;
		if(level > 0) {
			if((debuff.size() < max || AMath.random(100) <= 5) && AMath.random(100) <= 15+ (max - debuff.size())*15) {
				int df = AMath.random(Math.min(max, 5));
				int dl = AMath.random(mobupgrad.get(df).size())-1;
				int code = mobupgrad.get(df).get(dl);
				while(dbfcontains(mobupgrad.get(df).get(dl)) || (level > 8 && code == 6) || banDebuff == code) {//태아벤
					df = AMath.random(Math.min(max, 5));
					dl = AMath.random(mobupgrad.get(df).size())-1;
					code = mobupgrad.get(df).get(dl);
				}
				
				

				debuff.add(LoboBuffs.get("o"+code));
			}
		}
	}
	boolean dbfcontains(Integer s) {
		for(LoboBuffBase bf : debuff) {
			if(bf.getId() == s) {
				return true;
			}
		}
		return false;
	}
	boolean bfcontains(Integer s) {
		for(LoboBuffBase bf : buff) {
			if(bf.getId() == s) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isdebuff(int i) {
		for(LoboBuffBase db : debuff) {
			if(db.getId() == i) {
				return true;
			}
		}
		 return false;
	}
	public static void adddebuff(String st) {
		debuff.add(LoboBuffs.get("o"+st));
		if(st.equals("51")) {
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new Exposure(p), 20000, 5);
			}
		}
		text(393,LoboBuffs.getName("o"+st) + Text.get("lobo:ot1"));
		
	}
	public void tick(int time) {
		for(int i = 0;i<20;i++) {
			delay(()->{
				for(LoboBuffBase bf : buff) bf.onTick();
				for(LoboBuffBase bf : debuff) bf.onTick();
			},i);
		}
		this.time = time;
		repBgm();
		if(buffDelay > 0) buffDelay--;
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

				for(LoboBuffBase bf : buff) bf.onNextLevel();
				for(LoboBuffBase bf : debuff) bf.onNextLevel();
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

						if(bast == null) bast = ARSystem.RandomPlayer();
						Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
							addbuff(bast);
						},100);
					} else {

						if(bast == null) bast = ARSystem.RandomPlayer();
						addbuff(bast);
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
				if(level >= 2 && (count == 2 || count == 4)) {
					for(Player p : Rule.c.keySet()) {
						if(Rule.c.get(p) instanceof c38hajime) {
							if(Rule.c.get(p).isps) ((c38hajime)Rule.c.get(p)).wepone();
						}
					}
				}
				dango = true;
				if(humanSpawn) {
					rvv.clear();
					for(LivingEntity en : vilager) if(en.getHealth() / en.getMaxHealth() < 0.8 || en.isDead()) rvv.add(en);
					for(LivingEntity en : rvv) {
						villager_Death++;
						if(villager_Death == 30) text(393,"§f"+Text.get("lobo:dango1"));
						if(villager_Death == 80) text(393,"§f"+Text.get("lobo:dango2"));
						if(villager_Death == 150) text(393,"§f"+Text.get("lobo:dango3"));
						
						vilager.remove(en);
						en.remove();
					}
					
					humanMax = 5 + (int)(level*1.4);
					humanMax -= humanDelect;
					float pw = 0;
					if(count == 0 && level == 0) {
						pw = 50;
						if(difficulty < 3) pw += 25 *(3-difficulty);
						else if(difficulty > 3) pw -= 10 *(difficulty-3);
						Bukkit.broadcastMessage(Text.get("lobo:t2") + AMath.round(pw,2) +"%");
					} else {
						if(vilager.size() == 0) {
							pw = -20;
							if(level >= 9) pw = -10;
							if(dmg <= 0.5f) pw *= 0.3;
							if(difficulty > 4) pw *= 2;
							if(difficulty > 9) pw *= 5;
							Bukkit.broadcastMessage(Text.get("lobo:t4") + AMath.round(pw,2) +"%");
							
						} else if(vilager.size() == humanMax) {
							pw = 8;
							if(level > 6) {
								pw = 20;
							}
							if(difficulty < 3) pw += 8 *(3-difficulty);
							else if(difficulty > 3 && level > 1) pw += 2 *(difficulty-3);
							Bukkit.broadcastMessage(Text.get("lobo:t2") + AMath.round(pw,2) +"%");
						} else {
							pw = (7f * (float)vilager.size()/(float)humanMax);
							Bukkit.broadcastMessage(Text.get("lobo:t1") + AMath.round(pw,2) +"%");
						}
					}
					dmg += pw*0.01;
					for(Player p : Rule.c.keySet()) {
						Rule.c.get(p).frist_damage += pw*0.01;
					}
					while(vilager.size() < humanMax) {
						vilager.add(Map.spawnMM("human", Map.randomLoc()));
					}
				} else {
					if(count == 0 && level == 0) {
						float pw = 100;
						if(difficulty < 3) pw += 35 *(3-difficulty);
						else if(difficulty > 3) pw -= 15 *(difficulty-3);
						Bukkit.broadcastMessage(Text.get("lobo:t2") +  AMath.round(pw,2) + "%");
						dmg+=pw*0.01;
					}
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

				for(LoboBuffBase bf : buff) bf.onNextStage();
				for(LoboBuffBase bf : debuff) bf.onNextStage();
			}
			
			String s = "§7";
			for(LoboBuffBase o : debuff) s += " "+o.getName() +"§f§l,§7";
			if(debuff.size() <=0) s = "§a -";
			String bastname = "-";
			if(bast != null) bastname = bast.getName();
			
			s = "["+level+"-"+(count+1)+"]§c§l" +"\n"+ Text.get("lobo:team1") +bastname  
			+"\n§c" + Text.get("lobo:t5") + villager_Death
			+"\n" + Text.get("lobo:t3")+ (int)(dmg*100) +"%" + "\n§a=-=-=-=-=-=-=-=-=-=-=-=-=-=-=§f\n" + itemmsg + "\n§a=-=-=-=-=-=-=-=-=-=-=-=-=-=-=§f\n" + Text.get("lobo:ot3") + s + "\n";
			for(Player p : Bukkit.getOnlinePlayers()) 
				p.spigot().sendMessage(Text.hover(p,"§a§l"+Text.get("lobo:t0"), s));
			
		}
		v = 1;
		v+= mobCountValue;
		for(LivingEntity e : Map.mm.getAllMythicEntities()) {
			if(Rule.buffmanager.getBuffs(e) != null && Rule.buffmanager.isBuff(e,"timestop")) continue;
			if(!mobs.contains(e) && !etcmobs.contains(e) && !vilager.contains(e) && !e.isDead() && e.getHealth() >= 1) {
				addMob(e);
			}
		}

		for(LoboBuffBase bf : buff) bf.onTime(stageTime);
		for(LoboBuffBase bf : debuff) bf.onTime(stageTime);
		moblist();
		for(Player p : Rule.c.keySet()) {
			if(rep.get(p) != Rule.c.get(p)) {
				rep.put(p, Rule.c.get(p));
				charRep(p);
			}
		}
		if(level >= 1 && count >= 1 && (bast == null || !bast.isOnline())) {
			bast = ARSystem.RandomPlayer();
			text(394,"§7§l§n"+ bast.getName()+"§f"+Text.get("lobo:team"));
		}
	}
	
	public LivingEntity spawn(String name, int size,boolean msg) {
		LivingEntity en = null;
		size *= v;
		
		for(int i = 0; i < size; i++) {
			if(name.equals("dango") && humanSpawn){
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
			} else {
				boolean isspawn = true;
				for(LoboBuffBase b: buff) {
					en = b.onEntityCreate(name,size);
					if(en != null) {
						isspawn = false;
						break;
					}
				}
				if(isspawn) {
					for(LoboBuffBase b: debuff) {
						en = b.onEntityCreate(name,size);
						if(en != null) {
							isspawn = false;
							break;
						}
					}
				}
				
				if(isspawn){
					en = Map.spawnMM(name, Map.randomLoc());
				}
			}
			
			if(en != null) {
				en.setMaxHealth(AMath.round(en.getMaxHealth() * hpmult,2));
				en.setHealth(en.getMaxHealth());
				for(LoboBuffBase b: buff) b.onEntitySpawn(en);
				for(LoboBuffBase b: debuff) b.onEntitySpawn(en);
				mobs.add(en);
				if(level > 3) {
					Rule.mobmanager.Add(new M_LBDefence2(en,level));
					int rd = level*2;
					if(difficulty > 2) {
						if(difficulty > 4) rd *= (difficulty-5);
						if(AMath.random(200) <= rd) MobBuffs.get("lbhp", en);
						if(AMath.random(250) <= rd) MobBuffs.get("lbmaxhp", en);
						if(AMath.random(400) <= rd) MobBuffs.get("lbdefence", en);
					}
				}
			}
		}
		if(en != null && msg) text(394,en.getCustomName() + "§f"+Text.get("lobo:exit"+AMath.random(2)));
		return en;
	}
	
	public static void nextGame() {
		for(String pl : cr.keySet()) {
			Player p = Bukkit.getPlayer(pl);
			if(p == null) continue;
			Rule.c.put(p,GetChar.get(p, Rule.gamerule, "" + cr.get(pl)));
			//charRep(p);
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
	public List<String> nobgmList;
	public void repBgm() {
		String s = Bgm.bgmNameCode;
		rating = mobs.size()+(level*4) + debuff.size()*2;
		if(level >= 9) rating = 1000;
		if(!nobgmList.contains(s)){
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
		if(humanSpawn) {
			for(LivingEntity en : rvv) {
				villager_Death++;
				if(villager_Death == 30) text(393,"§f"+Text.get("lobo:dango1"));
				if(villager_Death == 80) text(393,"§f"+Text.get("lobo:dango2"));
				if(villager_Death == 150) text(393,"§f"+Text.get("lobo:dango3"));
				vilager.remove(en);
			}
		}
		for(Player p : Bukkit.getOnlinePlayers()) p.setLevel(mobs.size());


		humanMax = 5 + (int)(level*1.4);
		humanMax -= humanDelect;
		int size = vilager.size();

		for(LivingEntity en : vilager) if(en.getHealth()/en.getMaxHealth() <= 0.8 || en.isDead()) size--;
		for(LivingEntity en : rmv) {
			if(!Map.inMap(en.getLocation())) {
				en.teleport(Map.randomLoc());
			}
		}
		
		if(dango && humanMax - size >= 5 && mobcount > 0) {
			dango = false;
			if(level >= 3) {
				int count = 1;
				if(level > 5) count = 2;
				if(level > 7) count = 3;
				spawn("dango" , count ,true);
			}
		}
		if(Rule.c.size() < 1) {
			text(394,Text.get("lobo:fail"));
			for(LoboBuffBase m : debuff) {
				m.onRemove();
			}
			for(LoboBuffBase m : buff) {
				m.onRemove();
			}
			ARSystem.GameStop();
		}
	}
	
	public void addMob(LivingEntity e) {
		mobs.add(e);
		e.setMaxHealth(e.getMaxHealth() *hpmult);
		e.setHealth(e.getMaxHealth());
	}
	
	public static void dieMsg(Player name) {
		if(AMath.random(2) <= 1) {
			text(394,Text.get("lobo:death1") +"§7§l§n"+ name.getName()+"§f" + Text.get("lobo:death1-2"));
		} else {
			text(394,"§7§l§n"+ name.getName()+"§f" + Text.get("lobo:death2"));
		}
		if(Map.mapid == 1004) {
			for(Player p : Rule.c.keySet()) {
				ARSystem.addBuff(p, new Panic(p), 60+(level*20));
			}
			if(name == bast) {
				for(Player p : Rule.c.keySet()) {
					ARSystem.addBuff(p, new Panic(p), 30+(level*30));
				}
			}
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
			if(Rule.c.size() > 1) {
				for(LoboBuffBase bf : buff) bf.onPlayerDie(name);
				for(LoboBuffBase bf : debuff) bf.onPlayerDie(name);
			}
		},5);
	}
	
	public void endGame() {
		if(!(boolean)Rule.Var.Load("System.info.mode.loboclear")) {
			Rule.Var.Save("System.info.mode.loboclear",true);
			GameModes.set();
		}
		text(394,Text.get("lobo:end"));
		boolean win = true;
		for(LoboBuffBase b : buff) if(b instanceof RB_b047) win = false;
		
		String s = "";
		String s2 = "";
		for(LoboBuffBase o : debuff) s += "\n§7"+o.getName();
		for(LoboBuffBase o : buff) s2 += "\n§7"+o.getName();
		if(debuff.size() <=0) s = "§a -";
		String bastname = "-";
		if(bast != null) bastname = bast.getName();

		String teams = "";
		for(String p : cr.keySet()) {
			teams += p+ "§c["+cr.get(p)+"] §c§l";
		}
		
		s = "[Level "+difficulty+" Clear]§4§l" +"\n"+ Text.get("lobo:team1") +bastname +"\n§c§l"+ Text.get("lobo:team2") +teams
		+"\n§c" + Text.get("lobo:t5") + villager_Death
		+"\n" + Text.get("lobo:t3")+ (int)(dmg*100) +"%"
		+"\n§e"+ Text.get("main:s10") +" : "+ARSystem.AniRandomSkill.time +
		
		"\n§a=-=-=-=-=-=-=-=-=-=-=-=-=-=-=§f" + s2 + "\n§a=-=-=-=-=-=-=-=-=-=-=-=-=-=-=§f\n" + s + "\n";
		for(String p : cr.keySet()) {
			if(Rule.Var.Load(p+".info.loboLv") == null) {
				Rule.Var.Save(p+".info.loboLv",0);
			}
			if(Rule.Var.Loadint(p+".info.loboLv") <= difficulty) {
				Rule.Var.Save(p + ".info.loboC",s);
				Rule.Var.Save(p + ".info.loboLv", difficulty);
			}
			Rule.playerinfo.get(Bukkit.getPlayer(p)).tropy(0, 42);
		}

		if(win && difficulty >=2) {
			Bgm.setForceBgm("m0");
			Map.getMapinfo(1011);
			Location center = Map.getCenter();
			center.setY(4);
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.stopSound("", SoundCategory.MASTER);
				ARSystem.giveBuff(p, new TimeStop(p), 410);
				for(Player pl : Bukkit.getOnlinePlayers()) {
					p.hidePlayer(pl);
				}
			}
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.teleport(center.clone());
			}
			ARSystem.spellLocCast(ARSystem.RandomPlayer(), center.clone(), "lbend");
			delay(()->{
				for(Player p : rep.keySet()) {
					Rule.playerinfo.get(p).tropy(0, 40);
					if(difficulty == 10) {
						Rule.playerinfo.get(p).tropy(0, 41);
					}
				}
				ARSystem.GameStop();
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendTitle("§c§l『§e§lWin§c§l』", "", 100, 0, 100);
					}
				},60);
			},480);

		} else {
			Bgm.setForceBgm("m0");
			Map.getMapinfo(1011);
			Location center = Map.getCenter();
			center.setY(4);
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.stopSound("", SoundCategory.MASTER);
				ARSystem.giveBuff(p, new TimeStop(p), 410);
				for(Player pl : Bukkit.getOnlinePlayers()) {
					p.hidePlayer(pl);
				}
			}
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.teleport(center.clone());
			}
			ARSystem.spellLocCast(ARSystem.RandomPlayer(), center.clone(), "lbend2");
			delay(()->{
				for(Player p : rep.keySet()) {
					Rule.playerinfo.get(p).tropy(0, 35);
				}
				ARSystem.GameStop();
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendTitle("§c§l『§e§lWin§c§l』", "", 100, 0, 100);
					}
				},60);
			},480);
		}
	}

}
