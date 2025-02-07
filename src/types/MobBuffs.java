package types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.structure.Mirror;
import org.bukkit.entity.LivingEntity;

import ars.Rule;
import mob.MM_C;
import mob.MM_C2;
import mob.MM_Co;
import mob.MM_Dango;
import mob.MM_Kuroha;
import mob.MM_Magicgirl;
import mob.MM_NoTouch;
import mob.MM_Sado1;
import mob.MM_Sado2;
import mob.MM_Sado3;
import mob.MM_WhiteNight;
import mob.MM_alune;
import mob.M_Barrar;
import mob.M_Heal;
import mob.M_IIZEN;
import mob.M_LBDefence;
import mob.M_LBHp;
import mob.M_LBMaxHp;
import mob.M_NODEI;
import mob.M_ROCKREE;
import mob.MobBase;
import mode.MAdv;
import mode.MArena;
import mode.MGUN;
import mode.MHeal;
import mode.MItem;
import mode.MKagerou;
import mode.MKanna;
import mode.MKiller;
import mode.MLoboTomy;
import mode.MMirror;
import mode.MNormal;
import mode.MQb;
import mode.MRandom;
import mode.MSupply;
import mode.MTeam;
import mode.MTeamMatch;
import mode.MURF;
import mode.MZombie;
import mode.MZombieAdv;
import mode.ModeBase;
import mode.lobobuff.LoboBuffBase;
import mode.lobobuff.RB_b001;
import mode.lobobuff.RB_b002;
import mode.lobobuff.RB_b003;
import mode.lobobuff.RB_b004;
import mode.lobobuff.RB_b005;
import mode.lobobuff.RB_b006;
import mode.lobobuff.RB_b007;
import mode.lobobuff.RB_b008;
import mode.lobobuff.RB_b009;
import mode.lobobuff.RB_b010;
import mode.lobobuff.RB_b011;
import mode.lobobuff.RB_b012;
import mode.lobobuff.RB_b013;
import mode.lobobuff.RB_b014;
import mode.lobobuff.RB_b015;
import mode.lobobuff.RB_b016;
import mode.lobobuff.RB_b017;
import mode.lobobuff.RB_b018;
import mode.lobobuff.RB_b019;
import mode.lobobuff.RB_b020;
import mode.lobobuff.RB_b021;
import mode.lobobuff.RB_b022;
import mode.lobobuff.RB_b023;
import mode.lobobuff.RB_b024;
import mode.lobobuff.RB_b025;
import mode.lobobuff.RB_b026;
import mode.lobobuff.RB_b027;
import mode.lobobuff.RB_b028;
import mode.lobobuff.RB_b029;
import mode.lobobuff.RB_b030;
import mode.lobobuff.RB_b031;
import mode.lobobuff.RB_b032;
import mode.lobobuff.RB_b033;
import mode.lobobuff.RB_b034;
import mode.lobobuff.RB_b035;
import mode.lobobuff.RB_b036;
import mode.lobobuff.RB_b037;
import mode.lobobuff.RB_b038;
import mode.lobobuff.RB_b039;
import mode.lobobuff.RB_b040;
import mode.lobobuff.RB_b041;
import mode.lobobuff.RB_b042;
import mode.lobobuff.RB_b043;
import mode.lobobuff.RB_b044;
import mode.lobobuff.RB_b045;
import mode.lobobuff.RB_b046;
import mode.lobobuff.RB_b047;
import mode.lobobuff.RB_b048;
import mode.lobobuff.RB_b049;
import mode.lobobuff.RB_b101;
import mode.lobobuff.RB_b102;
import mode.lobobuff.RB_b103;
import mode.lobobuff.RB_b105;
import mode.lobobuff.RB_b106;
import mode.lobodebuff.RB_db001;
import mode.lobodebuff.RB_db002;
import mode.lobodebuff.RB_db003;
import mode.lobodebuff.RB_db004;
import mode.lobodebuff.RB_db005;
import mode.lobodebuff.RB_db006;
import mode.lobodebuff.RB_db007;
import mode.lobodebuff.RB_db008;
import mode.lobodebuff.RB_db009;
import mode.lobodebuff.RB_db010;
import mode.lobodebuff.RB_db011;
import mode.lobodebuff.RB_db012;
import mode.lobodebuff.RB_db013;
import mode.lobodebuff.RB_db014;
import mode.lobodebuff.RB_db015;
import mode.lobodebuff.RB_db016;
import mode.lobodebuff.RB_db017;
import mode.lobodebuff.RB_db018;
import mode.lobodebuff.RB_db019;
import mode.lobodebuff.RB_db020;
import mode.lobodebuff.RB_db021;
import mode.lobodebuff.RB_db022;
import mode.lobodebuff.RB_db023;
import mode.lobodebuff.RB_db024;
import mode.lobodebuff.RB_db025;
import mode.lobodebuff.RB_db026;
import mode.lobodebuff.RB_db027;
import mode.lobodebuff.RB_db028;
import mode.lobodebuff.RB_db029;
import mode.lobodebuff.RB_db030;
import mode.lobodebuff.RB_db101;
import util.Text;

public class MobBuffs {
	public static void get(String s,LivingEntity entity) {
		switch(s) {
			case "barrar":
				Rule.mobmanager.Add(new M_Barrar(entity));
				break;
			case "heal":
				Rule.mobmanager.Add(new M_Heal(entity));
				break;
			case "iizen":
				Rule.mobmanager.Add(new M_IIZEN(entity));
				break;
			case "rockree":
				Rule.mobmanager.Add(new M_ROCKREE(entity));
				break;
			case "nodie":
				Rule.mobmanager.Add(new M_NODEI(entity));
				break;
			case "magic1":
				Rule.mobmanager.Add(new MM_Magicgirl(entity));
				break;
			case "noclick":
				Rule.mobmanager.Add(new MM_NoTouch(entity));
				break;
			case "c":
				Rule.mobmanager.Add(new MM_C(entity));
				break;
			case "c2":
				Rule.mobmanager.Add(new MM_C2(entity));
				break;
			case "dango":
				Rule.mobmanager.Add(new MM_Dango(entity));
				break;
			case "co":
				Rule.mobmanager.Add(new MM_Co(entity));
				break;
			case "alune":
				Rule.mobmanager.Add(new MM_alune(entity));
				break;
			case "sado1":
				Rule.mobmanager.Add(new MM_Sado1(entity));
				break;
			case "sado2":
				Rule.mobmanager.Add(new MM_Sado2(entity));
				break;
			case "sado3":
				Rule.mobmanager.Add(new MM_Sado3(entity));
				break;
			case "wn":
				Rule.mobmanager.Add(new MM_WhiteNight(entity));
				break;
			case "kuroha":
				Rule.mobmanager.Add(new MM_Kuroha(entity,null));
				break;
			case "lbhp":
				Rule.mobmanager.Add(new M_LBHp(entity));
				break;
			case "lbdefence":
				Rule.mobmanager.Add(new M_LBDefence(entity));
				break;
			case "lbmaxhp":
				Rule.mobmanager.Add(new M_LBMaxHp(entity));
				break;
				
				
			default:
				Bukkit.broadcastMessage("§c§lcNot Mob buff : " + s);
				
		}
	}
}