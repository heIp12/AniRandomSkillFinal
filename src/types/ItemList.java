package types;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.structure.Mirror;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.TimeStop;
import item.craft.Item1001;
import item.craft.Item1002;
import item.craft.Item1003;
import item.craft.Item1004;
import item.craft.Item1005;
import item.craft.Item1006;
import item.craft.Item1007;
import item.craft.Item1008;
import item.craft.Item1009;
import item.craft.Item1010;
import item.craft.Item1011;
import item.craft.Item1012;
import item.craft.Item1013;
import item.craft.Item1014;
import item.craft.Item1015;
import item.craft.Item1016;
import item.etc.Item10001;
import item.etc.Item10002;
import item.etc.Item10010;
import item.etc.Item10011;
import item.etc.Item10012;
import item.etc.Item10013;
import item.etc.Item10014;
import item.etc.Item10015;
import item.etc.Item10016;
import item.etc.Item10100;
import item.etc.Item10101;
import item.etc.Item10102;
import item.etc.Item10103;
import item.etc.Item10104;
import item.etc.Item10105;
import item.etc.Item10106;
import item.etc.Item10107;
import item.etc.Item10108;
import item.etc.Item10109;
import item.etc.Item10110;
import item.list1.*;
import item.list2.Item101;
import item.list2.Item102;
import item.list2.Item103;
import item.list2.Item104;
import item.list2.Item105;
import item.list2.Item132;
import item.list2.Item133;
import item.list2.Item134;
import item.list2.Item135;
import item.list2.Item136;
import item.list2.Item137;
import item.list2.Item138;
import item.list2.Item139;
import item.list2.Item140;
import item.list2.Item141;
import item.list2.Item142;
import item.list2.Item143;
import item.list2.Item144;
import item.list2.Item145;
import item.list2.Item146;
import item.list2.Item147;
import item.list2.Item148;
import item.list2.Item149;
import item.list2.Item150;
import item.list2.Item151;
import item.list2.Item152;
import item.list2.Item153;
import item.list2.Item154;
import item.list2.Item155;
import item.list2.Item156;
import item.list2.Item157;
import item.list2.Item158;
import item.list2.Item159;
import item.list2.Item160;
import item.list2.item106;
import item.list2.item107;
import item.list2.item108;
import item.list2.item109;
import item.list2.item110;
import item.list2.item111;
import item.list2.item112;
import item.list2.item113;
import item.list2.item114;
import item.list2.item115;
import item.list2.item116;
import item.list2.item117;
import item.list2.item118;
import item.list2.item119;
import item.list2.item120;
import item.list2.item121;
import item.list2.item122;
import item.list2.item123;
import item.list2.item124;
import item.list2.item125;
import item.list2.item126;
import item.list2.item127;
import item.list2.item128;
import item.list2.item129;
import item.list2.item130;
import item.list2.item131;
import item.up.list1.*;
import mode.MAdv;
import mode.MArena;
import mode.MGUN;
import mode.MHeal;
import mode.MKagerou;
import mode.MKanna;
import mode.MKiller;
import mode.MLoboTomy;
import mode.MMirror;
import mode.MNormal;
import mode.MRandom;
import mode.MSupply;
import mode.MTeam;
import mode.MTeamMatch;
import mode.MURF;
import mode.MZombie;
import mode.MZombieAdv;
import mode.ModeBase;
import util.AMath;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class ItemList {
	static private HashMap<Integer,Class<? extends itemBase>> items; // 일반 아이템 원본
	static public HashMap<Integer,itemBase> upgraditem; // 강화 아이템 견본
	static public HashMap<String,itemBase> creates; // 조합식
	static public HashMap<Integer,itemBase> create; // 조합 아이템 견본
	static public HashMap<Integer, itemBase> orignal; // 일반 아이템 견본
	static public HashMap<Integer,List<Integer>> value; // 가격 테이블
	
	public static void set() {
		orignal = new HashMap<>();
		creates = new HashMap<>();
		create = new HashMap<>();
		upgraditem = new HashMap<>();
		
		items = new HashMap<>();
		value = new HashMap<>();
		items.put(0,Item000.class);
		items.put(1,Item001.class);
		items.put(2,Item002.class);
		items.put(3,Item003.class);
		items.put(4,Item004.class);
		items.put(5,Item005.class);
		items.put(6,Item006.class);
		items.put(7,Item007.class);
		items.put(8,Item008.class);
		items.put(9,Item009.class);
		items.put(10,Item010.class);
		items.put(11,Item011.class);
		items.put(12,Item012.class);
		items.put(13,Item013.class);
		items.put(14,Item014.class);
		items.put(15,Item015.class);
		items.put(16,Item016.class);
		items.put(17,Item017.class);
		items.put(18,Item018.class);
		items.put(19,Item019.class);
		items.put(20,Item020.class);
		items.put(21,Item021.class);
		items.put(22,Item022.class);
		items.put(23,Item023.class);
		items.put(24,Item024.class);
		items.put(25,Item025.class);
		items.put(26,Item026.class);
		items.put(27,Item027.class);
		items.put(28,Item028.class);
		items.put(29,Item029.class);
		items.put(30,Item030.class);
		items.put(31,Item031.class);
		items.put(32,Item032.class);
		items.put(33,Item033.class);
		items.put(34,Item034.class);
		items.put(35,Item035.class);
		items.put(36,Item036.class);
		items.put(37,Item037.class);
		items.put(38,Item038.class);
		items.put(39,Item039.class);
		items.put(40,Item040.class);
		items.put(41,Item041.class);
		items.put(42,Item042.class);
		items.put(43,Item043.class);
		items.put(44,Item044.class);
		items.put(45,Item045.class);
		items.put(46,Item046.class);
		items.put(47,Item047.class);
		items.put(48,Item048.class);
		items.put(49,Item049.class);
		items.put(50,Item050.class);
		items.put(51,Item051.class);
		items.put(52,Item052.class);
		items.put(53,Item053.class);
		items.put(54,Item054.class);
		items.put(55,Item055.class);
		items.put(56,Item056.class);
		items.put(57,Item057.class);
		items.put(58,Item058.class);
		items.put(59,Item059.class);
		items.put(60,Item060.class);
		items.put(61,Item061.class);
		items.put(62,Item062.class);
		items.put(63,Item063.class);
		items.put(64,Item064.class);
		items.put(65,Item065.class);
		items.put(66,Item066.class);
		items.put(67,Item067.class);
		items.put(68,Item068.class);
		items.put(69,Item069.class);
		items.put(70,Item070.class);
		items.put(71,Item071.class);
		items.put(72,Item072.class);
		items.put(73,Item073.class);
		items.put(74,Item074.class);
		items.put(75,Item075.class);
		items.put(76,Item076.class);
		items.put(77,Item077.class);
		items.put(78,Item078.class);
		items.put(79,Item079.class);
		items.put(80,Item080.class);
		items.put(81,Item081.class);
		items.put(82,Item082.class);
		items.put(83,Item083.class);
		items.put(84,Item084.class);
		items.put(85,Item085.class);
		items.put(86,Item086.class);
		items.put(87,Item087.class);
		items.put(88,Item088.class);
		items.put(89,Item089.class);
		items.put(90,Item090.class);
		items.put(91,Item091.class);
		items.put(92,Item092.class);
		items.put(93,Item093.class);
		items.put(94,Item094.class);
		items.put(95,Item095.class);
		items.put(96,Item096.class);
		items.put(97,Item097.class);
		items.put(98,Item098.class);
		items.put(99,Item099.class);
		items.put(100,Item100.class);
		items.put(101,Item101.class);
		items.put(102,Item102.class);
		items.put(103,Item103.class);
		items.put(104,Item104.class);
		items.put(105,Item105.class);
		items.put(106,item106.class);
		items.put(107,item107.class);
		items.put(108,item108.class);
		items.put(109,item109.class);
		items.put(110,item110.class);
		items.put(111,item111.class);
		items.put(112,item112.class);
		items.put(113,item113.class);
		items.put(114,item114.class);
		items.put(115,item115.class);
		items.put(116,item116.class);
		items.put(117,item117.class);
		items.put(118,item118.class);
		items.put(119,item119.class);
		items.put(120,item120.class);
		items.put(121,item121.class);
		items.put(122,item122.class);
		items.put(123,item123.class);
		items.put(124,item124.class);
		items.put(125,item125.class);
		items.put(126,item126.class);
		items.put(127,item127.class);
		items.put(128,item128.class);
		items.put(129,item129.class);
		items.put(130,item130.class);
		items.put(131,item131.class);
		items.put(132,Item132.class);
		items.put(133,Item133.class);
		items.put(134,Item134.class);
		items.put(135,Item135.class);
		items.put(136,Item136.class);
		items.put(137,Item137.class);
		items.put(138,Item138.class);
		items.put(139,Item139.class);
		items.put(140,Item140.class);
		items.put(141,Item141.class);
		items.put(142,Item142.class);
		items.put(143,Item143.class);
		items.put(144,Item144.class);
		items.put(145,Item145.class);
		items.put(146,Item146.class);
		items.put(147,Item147.class);
		items.put(148,Item148.class);
		items.put(149,Item149.class);
		items.put(150,Item150.class);
		items.put(151,Item151.class);
		items.put(152,Item152.class);
		items.put(153,Item153.class);
		items.put(154,Item154.class);
		items.put(155,Item155.class);
		items.put(156,Item156.class);
		items.put(157,Item157.class);
		items.put(158,Item158.class);
		items.put(159,Item159.class);
		items.put(160,Item160.class);
		
		//조합템
		items.put(1001,Item1001.class);
		items.put(1002,Item1002.class);
		items.put(1003,Item1003.class);
		items.put(1004,Item1004.class);
		items.put(1005,Item1005.class);
		items.put(1006,Item1006.class);
		items.put(1007,Item1007.class);
		items.put(1008,Item1008.class);
		items.put(1009,Item1009.class);
		items.put(1010,Item1010.class);
		items.put(1011,Item1011.class);
		items.put(1012,Item1012.class);
		items.put(1013,Item1013.class);
		items.put(1014,Item1014.class);
		items.put(1015,Item1015.class);
		items.put(1016,Item1016.class);

		//강화템
		items.put(10000,UpItem000.class);
		items.put(10001,UpItem001.class);
		items.put(10002,UpItem002.class);
		items.put(10003,UpItem003.class);
		items.put(10004,UpItem004.class);
		items.put(10005,UpItem005.class);
		items.put(10006,UpItem006.class);
		items.put(10007,UpItem007.class);
		items.put(10008,UpItem008.class);
		items.put(10009,UpItem009.class);
		items.put(10010,UpItem010.class);
		items.put(10011,UpItem011.class);
		items.put(10012,UpItem012.class);
		items.put(10013,UpItem013.class);
		items.put(10014,UpItem014.class);
		items.put(10015,UpItem015.class);
		items.put(10016,UpItem016.class);
		items.put(10017,UpItem017.class);
		items.put(10018,UpItem018.class);
		items.put(10019,UpItem019.class);
		items.put(10020,UpItem020.class);
		items.put(10021,UpItem021.class);
		items.put(10022,UpItem022.class);
		items.put(10023,UpItem023.class);
		items.put(10024,UpItem024.class);
		items.put(10025,UpItem025.class);
		items.put(10026,UpItem026.class);
		items.put(10027,UpItem027.class);
		items.put(10029,UpItem029.class);
		items.put(10030,UpItem030.class);
		items.put(10031,UpItem031.class);
		items.put(10033,UpItem033.class);
		items.put(10036,UpItem036.class);
		items.put(10037,UpItem037.class);
		items.put(10038,UpItem038.class);
		items.put(10039,UpItem039.class);
		items.put(10040,UpItem040.class);
		items.put(10041,UpItem041.class);
		items.put(10042,UpItem042.class);
		items.put(10043,UpItem043.class);
		items.put(10044,UpItem044.class);
		items.put(10045,UpItem045.class);
		items.put(10046,UpItem046.class);
		items.put(10048,UpItem048.class);
		items.put(10050,UpItem050.class);
		items.put(10051,UpItem051.class);
		items.put(10052,UpItem052.class);
		items.put(10053,UpItem053.class);
		items.put(10054,UpItem054.class);
		items.put(10055,UpItem055.class);
		items.put(10056,UpItem056.class);
		items.put(10057,UpItem057.class);
		items.put(10058,UpItem058.class);
		items.put(10059,UpItem059.class);
		items.put(10060,UpItem060.class);
		items.put(10061,UpItem061.class);
		items.put(10062,UpItem062.class);
		items.put(10063,UpItem063.class);
		items.put(10064,UpItem064.class);
		items.put(10065,UpItem065.class);
		items.put(10066,UpItem066.class);
		items.put(10067,UpItem067.class);
		items.put(10068,UpItem068.class);
		items.put(10069,UpItem069.class);
		items.put(10070,UpItem070.class);
		items.put(10072,UpItem072.class);
		items.put(10073,UpItem073.class);
		items.put(10074,UpItem074.class);
		items.put(10075,UpItem075.class);
		items.put(10076,UpItem076.class);
		items.put(10077,UpItem077.class);
		items.put(10079,UpItem079.class);
		items.put(10080,UpItem080.class);
		items.put(10084,UpItem084.class);
		items.put(10085,UpItem085.class);
		items.put(10086,UpItem086.class);
		items.put(10087,UpItem087.class);
		items.put(10088,UpItem088.class);
		items.put(10089,UpItem089.class);
		items.put(10090,UpItem090.class);
		items.put(10091,UpItem091.class);
		items.put(10092,UpItem092.class);
		items.put(10094,UpItem094.class);
		items.put(10095,UpItem095.class);
		items.put(10096,UpItem096.class);
		items.put(10097,UpItem097.class);
		items.put(10099,UpItem099.class);
		items.put(10100,UpItem100.class);
		items.put(10101,UpItem101.class);
		items.put(10102,UpItem102.class);
		items.put(10103,UpItem103.class);
		items.put(10107,Upitem107.class);
		items.put(10108,Upitem108.class);
		items.put(10109,Upitem109.class);
		items.put(10110,Upitem110.class);
		items.put(10111,Upitem111.class);
		items.put(10112,Upitem112.class);
		items.put(10113,Upitem113.class);
		items.put(10114,Upitem114.class);
		items.put(10120,Upitem120.class);
		items.put(10122,Upitem122.class);
		items.put(10123,Upitem123.class);
		items.put(10124,Upitem124.class);
		items.put(10125,Upitem125.class);
		items.put(10136,UpItem136.class);
		items.put(10137,UpItem137.class);
		items.put(10140,UpItem140.class);
		items.put(10141,UpItem141.class);
		items.put(10142,UpItem142.class);
		items.put(10145,UpItem145.class);
		items.put(10147,UpItem147.class);
		items.put(10148,UpItem148.class);
		items.put(10150,UpItem150.class);
		items.put(10151,UpItem151.class);
		items.put(10154,UpItem154.class);
		items.put(10155,UpItem155.class);
		items.put(10159,UpItem159.class);

		items.put(100001,Item10001.class);
		items.put(100002,Item10002.class);
		items.put(100010,Item10010.class);
		items.put(100011,Item10011.class);
		items.put(100012,Item10012.class);
		items.put(100013,Item10013.class);
		items.put(100014,Item10014.class);
		items.put(100015,Item10015.class);
		items.put(100016,Item10016.class);
		items.put(100100,Item10100.class);
		items.put(100101,Item10101.class);
		items.put(100102,Item10102.class);
		items.put(100103,Item10103.class);
		items.put(100104,Item10104.class);
		items.put(100105,Item10105.class);
		items.put(100106,Item10106.class);
		items.put(100107,Item10107.class);
		items.put(100108,Item10108.class);
		items.put(100109,Item10109.class);
		items.put(100110,Item10110.class);
		//아이템 등록
		for(int i : items.keySet()) {
			Class<? extends itemBase> key = items.get(i);
			try {
				//강화 아이템
				if(i >= 10000 && i < 100000) {
					Constructor<? extends upitemBase> item = (Constructor<? extends upitemBase>) key.getConstructor(Player.class);
					upitemBase it = item.newInstance(NpcPlayer.npc(Map.getCenter()));
					it.set();
					it.getItem();
					
					upgraditem.put(10000+it.getCode(), it);
				} else {
					Constructor<? extends itemBase> item = key.getConstructor(Player.class);
					itemBase it = item.newInstance(NpcPlayer.npc(Map.getCenter()));
					it.set();
					String t = Text.get("item:"+it.getCode()+"_up");
	
					if(t == null) { // 일반 아이템
						orignal.put(it.getCode(), it);
					} else { // 조합 아이템
						create.put(it.getCode(), it);
						creates.put(t.replace(" ", ""),it);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("[ARSystem] : Not item Class "+ key.getName() + "\n" + e.getCause() + "\n" + e.getLocalizedMessage());
			}
		}
		
		//가격 테이블 등록
		for(itemBase item : orignal.values()) {
			if(!item.getIsItemTable()) continue;
			if(value.get(item.getValue()) == null){
				value.put(item.getValue(), new ArrayList<Integer>());
			}
			value.get(item.getValue()).add(item.getCode());
		}
		
		//조합 아이템 테이블 등록
		for(itemBase item : creates.values()) {
			if(!item.getIsItemTable()) continue;
			if(value.get(item.getValue()) == null){
				value.put(item.getValue(), new ArrayList<Integer>());
			}
			value.get(item.getValue()).add(item.getCode());
		}
	}
	
	public static List<itemBase> getItems(){
		List<itemBase> item = new ArrayList<>();
		for(itemBase it : orignal.values()) item.add(it);
		for(itemBase it : creates.values()) item.add(it);
		return item;
	}
	
	public static itemBase getItem(int i){
		if(orignal.get(i) != null) return orignal.get(i);
		if(create.get(i) != null) return create.get(i);
		if(upgraditem.get(i) != null) return upgraditem.get(i);
		return null;
	}
	
	public static void upgrad(List<itemBase> items,Player player) {
		for(String t : creates.keySet()) {

			List<itemBase> cost = new ArrayList<itemBase>();
			boolean istrue = true;
			
			for(String code : t.split(",")) {
				int itemcode = Integer.parseInt(code);

				boolean istrue2 = false;
				for(itemBase it : items) {
					if(it.getCode() == itemcode && !cost.contains(it)) {
						cost.add(it);
						istrue2 = true;
						break;
					}
				}
				if(istrue2 == false) {
					istrue = false;
					break;
				}
			}
			
			if(istrue == false) {
				continue;
			} else {
				for(itemBase item : cost) {
					ARSystem.removeItem(player, item.getCode());
				}
				ARSystem.playSound(player,"itemupgrad",1,100000);
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
					int code = creates.get(t).getCode();
					Bukkit.broadcastMessage("§a§l[ARSystem] : §e§l"+Text.get("item:itemup") +"§c§l"+ Text.get("item:"+code));
					ARSystem.addItem(player,code);
				},40);
			}
		}
		return;
	}
	
	public static int getValueCode(int up, int down) {
		List<Integer> itemcodes = new ArrayList<>();
		for(int val : value.keySet()) {
			if(val >= up && val <= down) {
				itemcodes.addAll(value.get(val));
			}
		}
		if(itemcodes.size() <= 0) return -1;
		
		return itemcodes.get(AMath.random(itemcodes.size())-1);
	}
	
	public static itemBase getItem(int code,Player p) {
		if(items == null) set();
		if(!items.containsKey(code)) {
			System.out.println("[ARSystem] : Not item "+ code +" : Item code Error");
			return null;
		}
		try {
			Constructor<? extends itemBase> item = items.get(code).getConstructor(Player.class);
			return item.newInstance(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("[ARSystem] : Not item "+ code +"Item Error \n" + e);
		}
		return null;
	}
	
}