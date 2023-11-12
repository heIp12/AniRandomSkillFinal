package chars.ca;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.shaded.org.apache.commons.stat.correlation.Covariance;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import event.WinEvent;
import types.box;

import util.AMath;
import util.MSUtil;
import util.Map;
import util.Text;
import util.ULocal;

public class c1600saki extends c00main{
	String set[] = {"一","二","三","四","①","②","③","④","일","이","삼","사"};
	List<String> dack = new ArrayList<>();
	
	String hand[] = new String[11];
	int pescore[] = {1,1,2,1,1,2,2,2,3,6};
	
	int loc = 0;
	
	public c1600saki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1016;
		load();
		text();
		reset();
	}
	
	public void reset() {
		for(int i=0; i<12;i++) {
			for(int j=0;j<4;j++) {
				dack.add(set[i]);
			}
		}
		
		for(int i=0;i<hand.length;i++) {
			String p = dack.get(AMath.random(0,dack.size()-1));
			hand[i] = p;
			dack.remove(p);
		}
		
		rep();
		String s = "";
		for(int i=0;i<hand.length;i++) s+= hand[i];
		s = "";
		for(String ss : dack) s+= ss;
	}
	int getNumber(String s) {
		for(int i=0;i<set.length;i++) {
			if(set[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}
	void rep() {
		for(int i=0; i<hand.length;i++) {
			for(int j=i+1; j < hand.length; j++) {
				if(getNumber(hand[i]) > getNumber(hand[j])) {
					String s = hand[i];
					hand[i] = hand[j];
					hand[j] = s;
				}
			}
		}
	}
	int Number(String s) {
		for(int i=0;i<set.length;i++) {
			if(set[i].equals(s)) {
				return i%4+1;
			}
		}
		return -1;
	}
	int Color(String s) {
		for(int i=0;i<set.length;i++) {
			if(set[i].equals(s)) {
				return i/4+1;
			}
		}
		return -1;
	}
	
	int isType(int i) {
		if(i+3 < hand.length) {
			if(hand[i].equals(hand[i+1]) && hand[i].equals(hand[i+2])&& hand[i].equals(hand[i+3])) return 4; //깡
		}
		if(i+2 < hand.length) {
			if(hand[i].equals(hand[i+1]) && hand[i].equals(hand[i+2])) return 1; //퐁
			if(Color(hand[i]) == Color(hand[i+1]) && Color(hand[i]) == Color(hand[i+2])) {
				if(Number(hand[i]) == Number(hand[i+1])-1 && Number(hand[i+1]) == Number(hand[i+2])-1) {
					return 2; //치
				}
			}
		}
		if(i+1 < hand.length && hand[i].equals(hand[i+1])) return 3; //머리
		return 0;
	}
	void handTitle() {
		String s = "";
		int no = 0;
		for(int i=0;i<hand.length;i++) {
			if(no == 0) {
				int type = isType(i);
				if(type == 0) {
					s+="§7";
				}
				if(type == 4) {
					no=3;
					s+="§4§l";
				}
				if(type == 3) {
					no=1;
					s+="§e";
				}
				if(type == 2) {
					no=2;
					s+="§6";
				}
				if(type == 1) {
					no=2;
					s+="§c";
				}
				if(i > 0) s+=" ";
			} else {
				no--;
			}
			if(loc == i) s+="[";
			s+= hand[i];
			if(loc == i) s+="]";
		}
		player.sendTitle("", s,0,20,0);
	}
	
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c16get");
		
		if(dack.size() == 0) {
			ARSystem.playSound((Entity)player, "c1016get");
			reset();
		} else {
			String p = dack.get(AMath.random(0,dack.size()-1));
			hand[loc] = p;
			dack.remove(p);
			rep();
			handTitle();
		}
		return true;
	}
		
	@Override
	public boolean skill2() {
		if(!player.isSneaking()) {
			loc++;
			if(loc >= hand.length) loc = 0;
		} else {
			loc--;
			if(loc < 0) loc = hand.length-1;
		}
		handTitle();
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(dack.size() == 0) {
			reset();
		} else {
			for(int i=0;i< hand.length-3;i++) {
				int c = 0;
				for(int j=i;j<i+4;j++) {
					if(hand[i].equals(hand[j])) {
						c++;
					}
				}
				if(c > 3) {
					ARSystem.playSound((Entity)player, "c1016pk");
					String p = dack.get(AMath.random(0,dack.size()-1));
					hand[i] = p;
					dack.remove(p);
					rep();
					handTitle();
					i = 999;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		
		
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
		if(((LivingEntity)e.getEntity()).getNoDamageTicks() <= 0) {
			delay(new Runnable() {
				double damage = e.getFinalDamage();
					@Override
					public void run() {
						ARSystem.heal(player, damage/2);
					}
				},20);
			}
		}
		return true;
	}
}
