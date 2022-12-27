package util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import Main.Main;
import ars.Rule;
import c.c000humen;
import c.c001nb;
import c.c00main;
import c.c01minato;
import c.c02kirito;
import c.c03remuru;
import c.c04sans;
import c.c05touma;
import c.c06watson;
import c.c07shana;
import c.c08yuuki;
import c.c09youmu;
import c.c10bell;
import c.c11yasuo;
import c.c12conan;
import c.c13nagisa;
import c.c14rocklee;
import c.c15tina;
import c.c16saki;
import c.c17shou;
import c.c18misogi;
import c.c20kurumi;
import c.c21abigeil;
import c.c22byakuya;
import c.c23madoka;
import c.c24sinobu;
import c.c25Accelerator;
import c.c26ribai;
import c.c27ray;
import c.c28enma;
import c.c29guren;
import c.c30siro;
import c.c31ichigo;
import c.c32sanji;
import c.c33meliodace;
import c.c34natu;
import c.c35miruk;
import c.c36kaneki;
import c.c37subaru;
import c.c38hajime;
import c.c39sakuya;
import c.c40megumin;
import c.c41zanitu;
import c.c42nanaya;
import c.c43yuno;
import c.c44izuna;
import c.c45momo;
import c.c46zero;
import c.c47ren;
import c.c48yoshino;
import c.c49aria;
import c2.c100kuroko;
import c2.c50sayaka;
import c2.c51zenos;
import c2.c52toby;
import c2.c53cabuto;
import c2.c54patel;
import c2.c55yaya;
import c2.c56enju;
import c2.c57riri;
import c2.c58nao;
import c2.c59nero;
import c2.c60gil;
import c2.c61tyana;
import c2.c62shinon;
import c2.c63micoto;
import c2.c64hisoka;
import c2.c65cohina;
import c2.c66akame;
import c2.c67akad;
import c2.c68origami;
import c2.c69himi;
import c2.c70raito;
import c2.c71diabolo;
import c2.c72plan;
import c2.c73nyaruco;
import c2.c74ainz;
import c2.c75gon;
import c2.c76naohumi;
import c2.c77aladin;
import c2.c78ruichi;
import c2.c79kate;
import c2.c80aqit;
import c2.c81saitama;
import c2.c82maple;
import c2.c83sora;
import c2.c84siro;
import c2.c85maka;
import c2.c8601iriya;
import c2.c8602iriya;
import c2.c8603iriya;
import c2.c8604iriya;
import c2.c8605iriya;
import c2.c8606iriya;
import c2.c86iriya;
import c2.c87rou;
import c2.c88week;
import c2.c89kazuma;
import c2.c90arabe;
import c2.c91satoru;
import c2.c92susu;
import c2.c93bakugo;
import c2.c94yukari;
import c2.c95aya;
import c2.c96iki;
import c2.c97sorao;
import c2.c98kanna;
import c2.c99giroro;
import ca.c0100minato;
import ca.c0200kirito;
import ca.c0202kirito;
import ca.c0300remuru;
import ca.c0400sans;
import ca.c0700shana;
import ca.c0702shana;
import ca.c0800yuuki;
import ca.c0900youmu;
import ca.c100001;
import ca.c1100yasuo;
import ca.c1300nagisa;
import ca.c1500tina;
import ca.c1800misogi;
import ca.c1900sinjjan;
import ca.c19sinjjan;
import ca.c2000kurumi;
import ca.c2100abigeil;
import ca.c2200byakuya;
import ca.c2300madoka;
import ca.c2400sinobu;
import ca.c2600ribai;
import ca.c3000siro;
import ca.c3800hajime;
import ca.c3900sakuya;
import ca.c4000megumin;
import ca.c4200touno;
import ca.c4800yoshino;
import ca.c5000sayaka;
import ca.c5600enju;
import ca.c6600akame;
import ca.c6800origami;
import ca.c7200plan;
import ch.e001mary;
import ch.h001chruno;
import ch.h002claw;
import ch.h003rentaro;
import ch.h004alice;

public class GetChar {
	static int count = 100;
	static public int getCount() {
		return count;
	}
	static public void setCount(int i) {
		count = i;
	}
	static public c00main get(Player p,Plugin pl,String name) {

		return get(p,pl,name,null);
	}
	
	static public c00main get(Player p,Plugin pl,String name,c00main crt) {
		int i = 0;
		if(p != null) {
			i = Integer.parseInt(Main.GetText("general:adv_chance"));
			if(!name.contains("86")) {
				if(!Rule.playerinfo.get(p).abchar && AMath.random(1000) <= Integer.parseInt(Main.GetText("general:hidden_chance"))) {
					int z = AMath.random(4);
					if(z == 1) return new h001chruno(p,pl, crt);
					if(z == 2) return new h002claw(p,pl, crt);
					if(z == 3) return new h003rentaro(p,pl, crt);
					if(z == 4) return new h004alice(p,pl, crt);
				} else if(Rule.playerinfo.get(p).abchar && AMath.random(1000) <= Integer.parseInt(Main.GetText("general:hidden_code_chance"))) {
					int z = AMath.random(4);
					if(z == 1) return new h001chruno(p,pl, crt);
					if(z == 2) return new h002claw(p,pl, crt);
					if(z == 3) return new h003rentaro(p,pl, crt);
					if(z == 4) return new h004alice(p,pl, crt);
				}
			}
			int[] c = {1,2,3,4,7,9,13,19,20,23,30,18,42,56,26,48,39,40,50,15,8,66,11,22,72};
			for(int k : c) {
				if(Rule.playerinfo.get(p).playerc == k && name.equals(""+k) ) i = Integer.parseInt(Main.GetText("general:adv_pick_chance"));
			}
			
			if(Rule.playerinfo.get(p).abchar) i *=  Float.parseFloat(Main.GetText("general:adv_code_mult"));
		}
		
		if(name.equals("-1")) return new c001nb(p,pl,crt);
		if(name.equals("1") && AMath.random(100) <= i) {
			return new c0100minato(p,pl,crt);
		} else if(name.equals("1")) {
			return new c01minato(p,pl,crt);
		}
		if(name.equals("2") && AMath.random(100) <= i) {
			if(AMath.random(100) <= 50) {
				return new c0200kirito(p,pl,crt);
			} else {
				return new c0202kirito(p,pl,crt);
			}
		} else if(name.equals("2")) {
			return new c02kirito(p,pl,crt);
		}
		
		if(name.equals("3") && AMath.random(100) <= i) {
			return new c0300remuru(p,pl,crt);
		} else if(name.equals("3")) {
			return new c03remuru(p,pl,crt);
		}
		if(name.equals("4") && AMath.random(100) <= i) {
			return new c0400sans(p,pl,crt);
		} else if(name.equals("4")) {
			return new c04sans(p,pl,crt);
		}
		if(name.equals("5")) return new c05touma(p,pl,crt);
		if(name.equals("6")) return new c06watson(p,pl,crt);
		
		if(name.equals("7") && AMath.random(100) <= i) {
			if(AMath.random(100) <= 50) {
				return new c0700shana(p,pl,crt);
			} else {
				return new c0702shana(p,pl,crt);
			}
		} else if(name.equals("7")) {
			return new c07shana(p,pl,crt);
		}
		
		
		if(name.equals("8") && AMath.random(100) <= i) {
			return new c0800yuuki(p,pl,crt);
		} else if(name.equals("8")) {
			return new c08yuuki(p,pl,crt);
		}
		if(name.equals("9") && AMath.random(100) <= i) {
			return new c0900youmu(p,pl,crt);
		} else if(name.equals("9")) {
			return new c09youmu(p,pl,crt);
		}
		
		if(name.equals("10")) return new c10bell(p,pl,crt);
		if(name.equals("11") && AMath.random(100) <= i) {
			return new c1100yasuo(p,pl,crt);
		} else if(name.equals("11")) {
			return new c11yasuo(p,pl,crt);
		}
		if(name.equals("12")) return new c12conan(p,pl,crt);
		
		if(name.equals("13") && AMath.random(100) <= i) {
			return new c1300nagisa(p,pl,crt);
		} else if(name.equals("13")) {
			return new c13nagisa(p,pl,crt);
		}
		
		
		if(name.equals("14")) return new c14rocklee(p,pl,crt);
		
		if(name.equals("15") && AMath.random(100) <= i) {
			return new c1500tina(p,pl,crt);
		} else if(name.equals("15")) {
			return new c15tina(p,pl,crt);
		}
		
		if(name.equals("16")) return new c16saki(p,pl,crt);
		if(name.equals("17")) return new c17shou(p,pl,crt);
		if(name.equals("18") && AMath.random(100) <= i) {
			return new c1800misogi(p,pl,crt);
		} else if(name.equals("18")) {
			return new c18misogi(p,pl,crt);
		}
		if(name.equals("19") && AMath.random(100) <= i) {
			return new c1900sinjjan(p,pl,crt);
		} else if(name.equals("19")) {
			return new c19sinjjan(p,pl,crt);
		}
		if(name.equals("20") && AMath.random(100) <= i) {
			return new c2000kurumi(p,pl,crt);
		} else if(name.equals("20")) {
			return new c20kurumi(p,pl,crt);
		}
		
		if(name.equals("21")) return new c21abigeil(p,pl,crt);
		if(name.equals("22") &&  AMath.random(100) <= i) {
			return new c2200byakuya(p,pl,crt);
		}else if(name.equals("22")) {
			return new c22byakuya(p,pl,crt);
		}

		if(name.equals("23") && Rule.playerinfo.get(p).isTropy(23, 1)&& AMath.random(500) <= i) {
			return new c2300madoka(p,pl,crt);
		}else if(name.equals("23")) {
			return new c23madoka(p,pl,crt);
		}

		if(name.equals("24")) return new c24sinobu(p,pl,crt);
		if(name.equals("25")) return new c25Accelerator(p,pl,crt);
		if(name.equals("26") && AMath.random(100) <= i) {
			return new c2600ribai(p,pl,crt);
		} else if(name.equals("26")) {
			return new c26ribai(p,pl,crt);
		}
		if(name.equals("27")) return new c27ray(p,pl,crt);
		if(name.equals("28")) return new c28enma(p,pl,crt);
		if(name.equals("29")) return new c29guren(p,pl,crt);
		
		if(name.equals("30") && AMath.random(100) <= i) {
			return new c3000siro(p,pl,crt);
		} else if(name.equals("30")) {
			return new c30siro(p,pl,crt);
		}
		if(name.equals("31")) return new c31ichigo(p,pl,crt);
		if(name.equals("32")) return new c32sanji(p,pl,crt);
		if(name.equals("33")) return new c33meliodace(p,pl,crt);
		if(name.equals("34")) return new c34natu(p,pl,crt);
		if(name.equals("35")) return new c35miruk(p,pl,crt);
		if(name.equals("36")) return new c36kaneki(p,pl,crt);
		if(name.equals("37")) return new c37subaru(p,pl,crt);
		if(name.equals("38") && AMath.random(100) <= i) {
			return new c3800hajime(p,pl,crt);
		} else if(name.equals("38")) {
			return new c38hajime(p,pl,crt);
		}
		if(name.equals("39") && AMath.random(100) <= i) {
			return new c3900sakuya(p,pl,crt);
		} else if(name.equals("39")) {
			return new c39sakuya(p,pl,crt);
		}
		if(name.equals("40") && AMath.random(100) <= i) {
			return new c4000megumin(p,pl,crt);
		} else if(name.equals("40")) {
			return new c40megumin(p,pl,crt);
		}
		
		if(name.equals("41")) return new c41zanitu(p,pl,crt);
		if(name.equals("42") && AMath.random(100) <= i) {
			return new c4200touno(p,pl,crt);
		} else if(name.equals("42")) {
			return new c42nanaya(p,pl,crt);
		}
		if(name.equals("43")) return new c43yuno(p,pl,crt);
		if(name.equals("44")) return new c44izuna(p,pl,crt);
		if(name.equals("45")) return new c45momo(p,pl,crt);
		if(name.equals("46")) return new c46zero(p,pl,crt);
		if(name.equals("47")) return new c47ren(p,pl,crt);
		if(name.equals("48") && AMath.random(100) <= i) {
			return new c4800yoshino(p,pl,crt);
		} else if(name.equals("48")) {
			return new c48yoshino(p,pl,crt);
		}
		if(name.equals("49")) return new c49aria(p,pl,crt);
		if(name.equals("50") && AMath.random(100) <= 0) { // 확률
			return new c5000sayaka(p,pl,crt);
		} else if(name.equals("50")) {
			return new c50sayaka(p,pl,crt);
		}
		if(name.equals("51")) return new c51zenos(p,pl,crt);
		if(name.equals("52")) return new c52toby(p,pl,crt);
		if(name.equals("53")) return new c53cabuto(p,pl,crt);
		if(name.equals("54")) return new c54patel(p,pl,crt);
		if(name.equals("55")) return new c55yaya(p,pl,crt);
		if(name.equals("56") && AMath.random(100) <= i) {
			return new c5600enju(p,pl,crt);
		} else if(name.equals("56")) {
			return new c56enju(p,pl,crt);
		}
		if(name.equals("57")) return new c57riri(p,pl,crt);
		if(name.equals("58")) return new c58nao(p,pl,crt);
		if(name.equals("59")) return new c59nero(p,pl,crt);
		if(name.equals("60")) return new c60gil(p,pl,crt);
		if(name.equals("61")) return new c61tyana(p,pl,crt);
		if(name.equals("62")) return new c62shinon(p,pl,crt);
		if(name.equals("63")) return new c63micoto(p,pl,crt);
		if(name.equals("64")) return new c64hisoka(p,pl,crt);
		if(name.equals("65")) return new c65cohina(p,pl,crt);
		if(name.equals("66") && AMath.random(100) <= i) {
			return new c6600akame(p,pl,crt);
		} else if(name.equals("66")) {
			return new c66akame(p,pl,crt);
		}
		if(name.equals("67")) return new c67akad(p,pl,crt);
		if(name.equals("68")) return new c68origami(p,pl,crt);
		if(name.equals("69")) return new c69himi(p,pl,crt);
		if(name.equals("70")) return new c70raito(p,pl,crt);
		if(name.equals("71")) return new c71diabolo(p,pl,crt);
		if(name.equals("72") && AMath.random(100) <= i) {
			return new c7200plan(p,pl,crt);
		} else if(name.equals("72")) {
			return new c72plan(p,pl,crt);
		}
		
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
		if(name.equals("1038")) return new c3800hajime(p,pl, crt);
		if(name.equals("1039")) return new c3900sakuya(p,pl, crt);
		if(name.equals("1040")) return new c4000megumin(p,pl, crt);
		if(name.equals("1050")) return new c5000sayaka(p,pl, crt);
		if(name.equals("1072")) return new c7200plan(p,pl, crt);
		if(name.equals("1042")) return new c4200touno(p,pl, crt);
		if(name.equals("1013")) return new c1300nagisa(p,pl, crt);
		if(name.equals("1019")) return new c1900sinjjan(p,pl, crt);
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
		
		if(name.equals("1023") && Rule.playerinfo.get(p).isTropy(23, 1)) return new c2300madoka(p,pl, crt);
		
		if(name.equals("86f1")) return new c8601iriya(p,pl, crt);
		if(name.equals("86f2")) return new c8602iriya(p,pl, crt);
		if(name.equals("86f3")) return new c8603iriya(p,pl, crt);
		if(name.equals("86f4")) return new c8604iriya(p,pl, crt);
		if(name.equals("86f5")) return new c8605iriya(p,pl, crt);
		if(name.equals("86f6")) return new c8606iriya(p,pl, crt);
		
		if(name.equals("000")) return new c100001(p,pl, crt);
		if(name.equals("no")) return new c000humen(p,pl, crt);
		
		
		if(name.equals("귀찮아서나중에만듬")) return new e001mary(p,pl, crt);
		if(name.equals("⑨")) return new h001chruno(p,pl, crt);
		if(name.equals("백색자정의시련")) return new h002claw(p,pl, crt);
		if(name.equals("신인류창조계획")) return new h003rentaro(p,pl, crt);
		if(name.equalsIgnoreCase("NeverLeftWithoutSayingGoodbye")) return new h004alice(p, pl, crt);
		return new c001nb(p,pl,crt);
	}
}
