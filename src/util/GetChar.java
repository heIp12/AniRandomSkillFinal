package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import chars.c.c000humen;
import chars.c.c001humen2;
import chars.c.c001nb;
import chars.c.c00main;
import chars.c.c01minato;
import chars.c.c02kirito;
import chars.c.c03remuru;
import chars.c.c04sans;
import chars.c.c05touma;
import chars.c.c06watson;
import chars.c.c07shana;
import chars.c.c08yuuki;
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c11yasuo;
import chars.c.c12conan;
import chars.c.c13nagisa;
import chars.c.c14rocklee;
import chars.c.c15tina;
import chars.c.c16saki;
import chars.c.c17shou;
import chars.c.c18misogi;
import chars.c.c19sinjjan;
import chars.c.c20kurumi;
import chars.c.c21abigeil;
import chars.c.c22byakuya;
import chars.c.c23madoka;
import chars.c.c24sinobu;
import chars.c.c25Accelerator;
import chars.c.c26ribai;
import chars.c.c27ray;
import chars.c.c28enma;
import chars.c.c29guren;
import chars.c.c30siro;
import chars.c.c31ichigo;
import chars.c.c32sanji;
import chars.c.c33meliodace;
import chars.c.c34natu;
import chars.c.c35miruk;
import chars.c.c36kaneki;
import chars.c.c37subaru;
import chars.c.c38hajime;
import chars.c.c39sakuya;
import chars.c.c40megumin;
import chars.c.c41zanitu;
import chars.c.c42nanaya;
import chars.c.c43yuno;
import chars.c.c44izuna;
import chars.c.c45momo;
import chars.c.c46zero;
import chars.c.c47ren;
import chars.c.c48yoshino;
import chars.c.c49aria;
import chars.c2.c100kuroko;
import chars.c2.c50sayaka;
import chars.c2.c51zenos;
import chars.c2.c52toby;
import chars.c2.c53cabuto;
import chars.c2.c54patel;
import chars.c2.c55yaya;
import chars.c2.c56enju;
import chars.c2.c57riri;
import chars.c2.c58nao;
import chars.c2.c59nero;
import chars.c2.c60gil;
import chars.c2.c61tyana;
import chars.c2.c62shinon;
import chars.c2.c63micoto;
import chars.c2.c64hisoka;
import chars.c2.c65cohina;
import chars.c2.c66akame;
import chars.c2.c67akad;
import chars.c2.c68origami;
import chars.c2.c69himi;
import chars.c2.c70raito;
import chars.c2.c71diabolo;
import chars.c2.c72plan;
import chars.c2.c73nyaruco;
import chars.c2.c74ainz;
import chars.c2.c75gon;
import chars.c2.c76naohumi;
import chars.c2.c77aladin;
import chars.c2.c78ruichi;
import chars.c2.c79kate;
import chars.c2.c80aqit;
import chars.c2.c81saitama;
import chars.c2.c82maple;
import chars.c2.c83sora;
import chars.c2.c84siro;
import chars.c2.c85maka;
import chars.c2.c8601iriya;
import chars.c2.c8602iriya;
import chars.c2.c8603iriya;
import chars.c2.c8604iriya;
import chars.c2.c8605iriya;
import chars.c2.c8606iriya;
import chars.c2.c86iriya;
import chars.c2.c87rou;
import chars.c2.c88week;
import chars.c2.c89kazuma;
import chars.c2.c90arabe;
import chars.c2.c91satoru;
import chars.c2.c92susu;
import chars.c2.c93bakugo;
import chars.c2.c94yukari;
import chars.c2.c95aya;
import chars.c2.c96iki;
import chars.c2.c97sorao;
import chars.c2.c98kanna;
import chars.c2.c99giroro;
import chars.c3.c101aris;
import chars.c3.c102sid;
import chars.c3.c103diablo;
import chars.c3.c104rica;
import chars.c3.c105suya;
import chars.c3.c106ryel;
import chars.c3.c107nemurin;
import chars.c3.c108suwaco;
import chars.c3.c109kirua;
import chars.c3.c110seiya;
import chars.c3.c111artorya;
import chars.c3.c112hanekawa;
import chars.c3.c113shimakaze;
import chars.c3.c114prenda;
import chars.c3.c115stunk;
import chars.c3.c116yumira;
import chars.c3.c117kanade;
import chars.c3.c118cotory;
import chars.ca.c0100minato;
import chars.ca.c0200kirito;
import chars.ca.c0202kirito;
import chars.ca.c0300remuru;
import chars.ca.c0400sans;
import chars.ca.c0500touma;
import chars.ca.c0700shana;
import chars.ca.c0702shana;
import chars.ca.c0800yuuki;
import chars.ca.c0900youmu;
import chars.ca.c100001;
import chars.ca.c1100yasuo;
import chars.ca.cc1180Ai;
import chars.ca.c1300nagisa;
import chars.ca.c1500tina;
import chars.ca.c1700shou;
import chars.ca.c1800misogi;
import chars.ca.c1900sinjjan;
import chars.ca.c2000kurumi;
import chars.ca.c2100abigeil;
import chars.ca.c2200byakuya;
import chars.ca.c2300madoka;
import chars.ca.c2400sinobu;
import chars.ca.c2600ribai;
import chars.ca.c3000siro;
import chars.ca.c3900sakuya;
import chars.ca.c4000megumin;
import chars.ca.c4200touno;
import chars.ca.c4400izuna;
import chars.ca.c4800yoshino;
import chars.ca.c5000sayaka;
import chars.ca.c5600enju;
import chars.ca.c6500cohina;
import chars.ca.c6600akame;
import chars.ca.c6800origami;
import chars.ca.c7200plan;
import chars.ca.cc1000kuroko;
import chars.ch.h001chruno;
import chars.ch.h002claw;
import chars.ch.h003rentaro;
import chars.ch.h004alice;
import chars.ch.h005mery;
import mode.ModeBase;
/**
 * 캐릭터 추가 방법
 * chars.c숫자 패키지에 c00main 상속받아 캐릭터 생성
 * 일반 캐릭터면 getChar get(...) 메소드에 캐릭터 등록, count 늘려줌
 * 어벤이면 getAdv에 캐릭터 등록, adv에 {어벤번호,그 캐릭 어벤 갯수} 넣어줌
 * 어벤 번호는 (일반+1000) 어벤 수마다 1000이 늘어남
 * @author user
 *
 */
public class GetChar {
	static int count = 118;
	public static HashMap<String,List<Integer>> charban;
	/**
	 * {어벤번호,그 번호 어벤 갯수}
	 */
	public static int[][] adv = {
			{1,1},{2,2},{3,1},{4,1},{5,1},
			{7,2},{8,1},{9,1},{11,1},{13,1},
			{15,1},{17,1},{18,1},{19,1},{20,1},{22,1},{26,1},
			{30,1},{39,1},{40,1},{42,1},{44,1},{48,1},
			{50,1},{56,1},{65,1},{66,1},{72,1},{100,1}};
	
	static public int getCount() {
		return count;
	}
	static public void setCount(int i) {
		count = i;
	}
	
	static public c00main get(Player p,Plugin pl,String name) {
		return get(p,pl,name,null);
	}
	
	static public ItemStack getColor(int i) {
		if(i == 1118) return ItemCreate.Item(277,1501);
		if(i > 1000) i%=1000;
		return ItemCreate.Item(277,i+500);
	}
	static public ItemStack getNoColor(int i) {
		if(i > 1000) i%=1000;
		return ItemCreate.Item(277,i);
	}
	
	
	/**
	 * 각 게임몯에서 밴 당할 캐릭터들 기본값
	*/
	static public void BanLoad() {
		if(charban == null) charban = new HashMap<>();
		charban.put("normal",new ArrayList<>());
		charban.put("player3",ca(new int[]{12,28,37,43,53,59,81,107}));
		charban.put("arena",ca(new int[]{12,17,24,28,37,38,40,43,46,53,58,59,70,79,81,90,92,95,105,107,118}));
		charban.put("mirror",ca(new int[]{3,12,17,25,28,37,43,52,53,59,74,89,92,112,118}));
		charban.put("teammatch",ca(new int[]{12,28,35,37,43,53,58,79,90}));
		charban.put("team",ca(new int[] {43,53,58,79}));
		charban.put("lobotomy",ca(new int[]{12,16,17,18,23,28,30,35,37,46,53,59,70,78,79,81,90,95,100,107}));
		
		for(int i=0;i<count;i++) {
			if((Boolean)Rule.Var.Load("#ARS.ban.normal"+(i+1))) charban.get("normal").add(i+1);
		}
	}
	static private ArrayList<Integer> ca(int[] ints) {
		ArrayList<Integer> list = new ArrayList<>();
		for(int i : ints) list.add(i);
		return list;
	}
	static public void BanSave() {
		for(String key : charban.keySet()) {
			for(int num : charban.get(key)) Rule.Var.Save("#ARS.ban."+key+num, true);
		}
	}
	public static boolean isBan(int i) {
		if(charban.get("normal").contains(i+1) && ARSystem.ban) return false;
		if(ARSystem.AniRandomSkill.player < 3 && charban.get("player3").contains(i+1)) return false;
		if(ARSystem.AniRandomSkill != null) {
			for (ModeBase bm : ARSystem.AniRandomSkill.modes) {
				if(charban.get(bm.getModeName()) != null && charban.get(bm.getModeName()).contains(i+1)) return false;
			}
		}
		return true;
	}	
	static public c00main get(Player p,Plugin pl,String name,c00main crt) {
		int i = 0;
		if(p != null) {
			if(!name.contains("86")) {
				if(!Rule.playerinfo.get(p).abchar && AMath.random(1000) <= Integer.parseInt(Main.GetText("general:hidden_chance"))) {
					return getHiden(p);
				} else if(Rule.playerinfo.get(p).abchar && AMath.random(1000) <= Integer.parseInt(Main.GetText("general:hidden_code_chance"))) {
					return getHiden(p);
				}
			}
			for(int[] k : adv) {
				if(name.equals(""+k[0])) {
					i = Integer.parseInt(Main.GetText("general:adv_chance"));
					if((""+Rule.playerinfo.get(p).playerc).equals(""+k[0]) || Rule.playerinfo.get(p).playerc == 0) {
						i = Integer.parseInt(Main.GetText("general:adv_pick_chance"));
						break;
					}
				}
			}
			if(i > 0) i *=  Float.parseFloat(Main.GetText("general:adv_code_mult"));
			
			int i2 = 0;
			if(name.equals("23") || name.equals("118")) i2 = Integer.parseInt(Main.GetText("general:adv_pick_chance"));
			
			if(name.equals("23") && Rule.playerinfo.get(p).isTropy(23, 1)&& AMath.random(500) <= i2) {
				return new c2300madoka(p,pl,crt);
			}
			if(name.equals("118") && Rule.playerinfo.get(p).isTropy(118, 1)&& AMath.random(100) <= i2) {
				return new cc1180Ai(p,pl,crt);
			}
		}
		if(AMath.random(100) <= i) {
			return getAdv(p,pl,name,crt);
		}
		
		
		if(name.equals("-1")) return new c001nb(p,pl,crt);
		if(name.equals("1")) return new c01minato(p,pl,crt);
		if(name.equals("2")) return new c02kirito(p,pl,crt);
		if(name.equals("3")) return new c03remuru(p,pl,crt);
		if(name.equals("4")) return new c04sans(p,pl,crt);
		if(name.equals("5")) return new c05touma(p,pl,crt);
		if(name.equals("6")) return new c06watson(p,pl,crt);
		if(name.equals("7")) return new c07shana(p,pl,crt);
		if(name.equals("8")) return new c08yuuki(p,pl,crt);
		if(name.equals("9")) return new c09youmu(p,pl,crt);
		if(name.equals("10")) return new c10bell(p,pl,crt);
		if(name.equals("11")) return new c11yasuo(p,pl,crt);
		if(name.equals("12")) return new c12conan(p,pl,crt);
		if(name.equals("13")) return new c13nagisa(p,pl,crt);
		if(name.equals("14")) return new c14rocklee(p,pl,crt);
		if(name.equals("15")) return new c15tina(p,pl,crt);
		if(name.equals("16")) return new c16saki(p,pl,crt);
		if(name.equals("17")) return new c17shou(p,pl,crt);
		if(name.equals("18")) return new c18misogi(p,pl,crt);
		if(name.equals("19")) return new c19sinjjan(p,pl,crt);
		if(name.equals("20")) return new c20kurumi(p,pl,crt);
		if(name.equals("21")) return new c21abigeil(p,pl,crt);
		if(name.equals("22")) return new c22byakuya(p,pl,crt);
		if(name.equals("23")) return new c23madoka(p,pl,crt);
		if(name.equals("24")) return new c24sinobu(p,pl,crt);
		if(name.equals("25")) return new c25Accelerator(p,pl,crt);
		if(name.equals("26")) return new c26ribai(p,pl,crt);
		if(name.equals("27")) return new c27ray(p,pl,crt);
		if(name.equals("28")) return new c28enma(p,pl,crt);
		if(name.equals("29")) return new c29guren(p,pl,crt);
		if(name.equals("30")) return new c30siro(p,pl,crt);
		if(name.equals("31")) return new c31ichigo(p,pl,crt);
		if(name.equals("32")) return new c32sanji(p,pl,crt);
		if(name.equals("33")) return new c33meliodace(p,pl,crt);
		if(name.equals("34")) return new c34natu(p,pl,crt);
		if(name.equals("35")) return new c35miruk(p,pl,crt);
		if(name.equals("36")) return new c36kaneki(p,pl,crt);
		if(name.equals("37")) return new c37subaru(p,pl,crt);
		if(name.equals("38")) return new c38hajime(p,pl,crt);
		if(name.equals("39")) return new c39sakuya(p,pl,crt);
		if(name.equals("40")) return new c40megumin(p,pl,crt);
		if(name.equals("41")) return new c41zanitu(p,pl,crt);
		if(name.equals("42")) return new c42nanaya(p,pl,crt);
		if(name.equals("43")) return new c43yuno(p,pl,crt);
		if(name.equals("44")) return new c44izuna(p,pl,crt);
		if(name.equals("45")) return new c45momo(p,pl,crt);
		if(name.equals("46")) return new c46zero(p,pl,crt);
		if(name.equals("47")) return new c47ren(p,pl,crt);
		if(name.equals("48")) return new c48yoshino(p,pl,crt);
		if(name.equals("49")) return new c49aria(p,pl,crt);
		if(name.equals("50")) return new c50sayaka(p,pl,crt);
		if(name.equals("51")) return new c51zenos(p,pl,crt);
		if(name.equals("52")) return new c52toby(p,pl,crt);
		if(name.equals("53")) return new c53cabuto(p,pl,crt);
		if(name.equals("54")) return new c54patel(p,pl,crt);
		if(name.equals("55")) return new c55yaya(p,pl,crt);
		if(name.equals("56")) return new c56enju(p,pl,crt);
		if(name.equals("57")) return new c57riri(p,pl,crt);
		if(name.equals("58")) return new c58nao(p,pl,crt);
		if(name.equals("59")) return new c59nero(p,pl,crt);
		if(name.equals("60")) return new c60gil(p,pl,crt);
		if(name.equals("61")) return new c61tyana(p,pl,crt);
		if(name.equals("62")) return new c62shinon(p,pl,crt);
		if(name.equals("63")) return new c63micoto(p,pl,crt);
		if(name.equals("64")) return new c64hisoka(p,pl,crt);
		if(name.equals("65")) return new c65cohina(p,pl,crt);
		if(name.equals("66")) return new c66akame(p,pl,crt);
		if(name.equals("67")) return new c67akad(p,pl,crt);
		if(name.equals("68")) return new c68origami(p,pl,crt);
		if(name.equals("69")) return new c69himi(p,pl,crt);
		if(name.equals("70")) return new c70raito(p,pl,crt);
		if(name.equals("71")) return new c71diabolo(p,pl,crt);
		if(name.equals("72"))return new c72plan(p,pl,crt);
		if(name.equals("73")) return new c73nyaruco(p,pl,crt);
		if(name.equals("74")) return new c74ainz(p,pl,crt);
		if(name.equals("75")) return new c75gon(p,pl,crt);
		if(name.equals("76")) return new c76naohumi(p,pl,crt);
		if(name.equals("77")) return new c77aladin(p,pl,crt);
		if(name.equals("78")) return new c78ruichi(p,pl,crt);
		if(name.equals("79")) return new c79kate(p,pl,crt);
		if(name.equals("80")) return new c80aqit(p,pl,crt);
		if(name.equals("81")) return new c81saitama(p,pl,crt);
		if(name.equals("82")) return new c82maple(p,pl,crt);
		if(name.equals("83")) return new c83sora(p,pl,crt);
		if(name.equals("84")) return new c84siro(p,pl,crt);
		if(name.equals("85")) return new c85maka(p,pl,crt);
		if(name.equals("86")) return new c86iriya(p,pl,crt);
		if(name.equals("87")) return new c87rou(p,pl,crt);
		if(name.equals("88")) return new c88week(p,pl,crt);
		if(name.equals("89")) return new c89kazuma(p,pl,crt);
		if(name.equals("90")) return new c90arabe(p,pl,crt);
		if(name.equals("91")) return new c91satoru(p,pl,crt);
		if(name.equals("92")) return new c92susu(p,pl,crt);
		if(name.equals("93")) return new c93bakugo(p,pl,crt);
		if(name.equals("94")) return new c94yukari(p,pl,crt);
		if(name.equals("95")) return new c95aya(p,pl,crt);
		if(name.equals("96")) return new c96iki(p,pl,crt);
		if(name.equals("97")) return new c97sorao(p,pl,crt);
		if(name.equals("98")) return new c98kanna(p,pl,crt);
		if(name.equals("99")) return new c99giroro(p,pl,crt);
		if(name.equals("100")) return new c100kuroko(p,pl,crt);
		if(name.equals("101")) return new c101aris(p,pl,crt);
		if(name.equals("102")) return new c102sid(p,pl,crt);
		if(name.equals("103")) return new c103diablo(p,pl,crt);
		if(name.equals("104")) return new c104rica(p,pl,crt);
		if(name.equals("105")) return new c105suya(p,pl,crt);
		if(name.equals("106")) return new c106ryel(p,pl,crt);
		if(name.equals("107")) return new c107nemurin(p,pl,crt);
		if(name.equals("108")) return new c108suwaco(p,pl,crt);
		if(name.equals("109")) return new c109kirua(p,pl,crt);
		if(name.equals("110")) return new c110seiya(p,pl,crt);
		if(name.equals("111")) return new c111artorya(p,pl,crt);
		if(name.equals("112")) return new c112hanekawa(p,pl,crt);
		if(name.equals("113")) return new c113shimakaze(p,pl,crt);
		if(name.equals("114")) return new c114prenda(p,pl,crt);
		if(name.equals("115")) return new c115stunk(p,pl,crt);
		if(name.equals("116")) return new c116yumira(p,pl,crt);
		if(name.equals("117")) return new c117kanade(p,pl,crt);
		if(name.equals("118")) return new c118cotory(p,pl,crt);
		
		if(name.equals("86f1")) return new c8601iriya(p,pl, crt);
		if(name.equals("86f2")) return new c8602iriya(p,pl, crt);
		if(name.equals("86f3")) return new c8603iriya(p,pl, crt);
		if(name.equals("86f4")) return new c8604iriya(p,pl, crt);
		if(name.equals("86f5")) return new c8605iriya(p,pl, crt);
		if(name.equals("86f6")) return new c8606iriya(p,pl, crt);
		
		if(name.equals("000")) return new c100001(p,pl, crt);
		if(name.equals("no")) return new c000humen(p,pl, crt);
		if(name.equals("no2")) return new c001humen2(p,pl,crt);

		if(name.equals("⑨")) return new h001chruno(p,pl, crt);
		if(name.equals("백색자정의시련")) return new h002claw(p,pl, crt);
		if(name.equals("신인류창조계획")) return new h003rentaro(p,pl, crt);
		if(name.equalsIgnoreCase("NeverLeftWithoutSayingGoodbye")) return new h004alice(p, pl, crt);
		if(name.equals("들켜버렸다~♪알아버렸다~♪메리의비밀~☆")) return new h005mery(p,pl, crt);
		
		return getAdv(p,pl,name,crt);
	}
	
	static public c00main getAdv(Player p, Plugin pl, String name, c00main crt) {
		try {
			int n = Integer.parseInt(name);
			for(int[] k : adv) {
				if(k[0] == n) {
					n+= AMath.random(k[1])*1000;
					break;
				}
			}
			name = ""+n;
			if(name.equals("1021")) return new c2100abigeil(p,pl, new c21abigeil(p, pl, crt));
			if(name.equals("1024")) return new c2400sinobu(p,pl, new c24sinobu(p, pl, crt));
			if(name.equals("1068")) return new c6800origami(p,pl, crt);
			if(name.equals("1011")) return new c1100yasuo(p,pl, crt);
			if(name.equals("1015")) return new c1500tina(p,pl, crt);
			if(name.equals("1056")) return new c5600enju(p,pl, crt);
			if(name.equals("1020")) return new c2000kurumi(p,pl, crt);
			if(name.equals("1048")) return new c4800yoshino(p,pl, crt);
			if(name.equals("1022")) return new c2200byakuya(p,pl, crt);
			if(name.equals("1026")) return new c2600ribai(p,pl, crt);
			if(name.equals("1030")) return new c3000siro(p,pl, crt);
			if(name.equals("1065")) return new c6500cohina(p,pl, crt);
			if(name.equals("1039")) return new c3900sakuya(p,pl, crt);
			if(name.equals("1040")) return new c4000megumin(p,pl, crt);
			if(name.equals("1044")) return new c4400izuna(p,pl, crt);
			if(name.equals("1050")) return new c5000sayaka(p,pl, crt);
			if(name.equals("1072")) return new c7200plan(p,pl, crt);
			if(name.equals("1042")) return new c4200touno(p,pl, crt);
			if(name.equals("1013")) return new c1300nagisa(p,pl, crt);
			if(name.equals("1019")) return new c1900sinjjan(p,pl, crt);
			if(name.equals("1017")) return new c1700shou(p,pl, crt);
			if(name.equals("1018")) return new c1800misogi(p,pl, crt);
			if(name.equals("1007")) return new c0700shana(p,pl, crt);
			if(name.equals("1009")) return new c0900youmu(p,pl, crt);
			if(name.equals("1004")) return new c0400sans(p,pl, crt);
			if(name.equals("1003")) return new c0300remuru(p,pl, crt);
			if(name.equals("2002")) return new c0202kirito(p,pl, crt);
			if(name.equals("2007")) return new c0702shana(p,pl, crt);
			if(name.equals("1002")) return new c0200kirito(p,pl, crt);
			if(name.equals("1001")) return new c0100minato(p,pl, crt);
			if(name.equals("1066")) return new c6600akame(p,pl, crt);
			if(name.equals("1008")) return new c0800yuuki(p,pl, crt);
			if(name.equals("1005")) return new c0500touma(p,pl, crt);
			if(name.equals("1100")) return new cc1000kuroko(p,pl, crt);
			if(name.equals("1118") && Rule.playerinfo.get(p).isTropy(118, 1)) return new cc1180Ai(p,pl, crt);
			if(name.equals("1023") && Rule.playerinfo.get(p).isTropy(23, 1)) return new c2300madoka(p,pl, crt);
		} catch (Exception e) {
			
		}
		return new c001nb(p,pl,crt);
	}
	static public c00main getHiden(Player p) {
		int z = AMath.random(5);
		if(z == 1) return new h001chruno(p, Rule.gamerule, null);
		if(z == 2) return new h002claw(p, Rule.gamerule, null);
		if(z == 3) return new h003rentaro(p, Rule.gamerule, null);
		if(z == 4) return new h004alice(p, Rule.gamerule, null);
		return new h005mery(p, Rule.gamerule, null);
	}
	public static ArrayList<Integer> advList() {
		ArrayList<Integer> list = new ArrayList<>();
		for(int[] i : adv) {
			list.add(i[0]);
		}
		return list;
	}

}
