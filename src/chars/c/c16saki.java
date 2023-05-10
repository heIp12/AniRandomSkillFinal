package chars.c;

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
import event.Skill;
import event.WinEvent;
import types.box;

import util.AMath;
import util.MSUtil;
import util.Text;

public class c16saki extends c00main{
	String set[] = {"㊊","一","二","三","四"};
	String dack[] = new String[set.length*4];
	boolean decks[] = new boolean[set.length*4];
	boolean pe[] = new boolean[9];
	String text = "";
	int pescore[] = {5,6,3,10,20,4,0,6,2};
	int score = 0;
	String info = "";
	int count = 0;
	
	String handf= "";
	String hand = "";
	String drow = "";
	int handcount = 11;
	boolean iskang = false;
	String kanf = "";
	int kan = 0;
	public String handColor() {
		if(ARSystem.isGameMode("lobotomy")) pescore = new int[]{9,12,6,20,18,8,2,12,4};
		String str = hand;
		
		for(String s :set) {
			if(kanf.indexOf(s) > -1) {
				str =str.replace(s + s + s, "§d§l "+s + s + s);
			}
			else if(str.indexOf(s + s + s+ s) > -1) {
				str = str.replace(s + s + s+ s, "§4 "+s + s + s+ s);
			}
			else if(str.indexOf(s + s + s) > -1) {
				str = str.replace(s + s + s, "§c "+s + s + s);
			}
			else if(str.indexOf(s + s) > -1) {
				str = str.replace(s + s, "§6 "+s + s);
			} else {
				str = str.replace(s , "§f "+s);
			}
			
		}
		return str;
	}

	public void reset() {
		kanf = hand = handf = drow = info = "";
		kan = score = 0;
		iskang = false;
		int i = 0;
		dack[i++] = dack[i++] = dack[i++] = dack[i++] = set[0];
		dack[i++] = dack[i++] = dack[i++] = dack[i++] = set[1];
		dack[i++] = dack[i++] = dack[i++] = dack[i++] = set[2];
		dack[i++] = dack[i++] = dack[i++] = dack[i++] = set[3];
		dack[i++] = dack[i++] = dack[i++] = dack[i] = set[4];
		for(i=0; i<decks.length;i++){
			decks[i] = true;
		}
		for(i=0; i<pe.length;i++){
			pe[i] = false;
		}
		if(AMath.random(100) <= 8) {
			luck();
		}
	}
	
	public void luck() {
		decks[0] = false;
		decks[1] = false;
		decks[2] = false;
		decks[3] = false;
		skill("c"+number+"ps");
	}
	

	public void drow() {
		if(hand.length() < handcount) {
			int i = AMath.random(decks.length)-1;
			while(!decks[i]) {
				i = AMath.random(decks.length)-1;
			}
			decks[i] = false;
			hand += dack[i];
			drow = dack[i];
			if(handf.indexOf(dack[i]) == -1) {
				handf+=dack[i];
			}
			handset();
		}
	}
	
	public void handset() {
		String s = ""+hand;
		String f = ""+hand;
		
		s = s.substring(s.length()-1);
		hand = hand.substring(0, hand.length()-1);

		if(hand.indexOf(s) > -1) {
			hand = hand.replaceFirst(s, s+s);
		} else {
			hand = f;
		}
		if(hand.length() == handcount) {
			score();
			deal();
		}
		
	}

	public boolean kan() {
		for(String s : set) {
			int count = 0;
			for(char c : hand.toCharArray()) {
				if(s.indexOf(c) > -1) {
					count++;
				}
			}
			if(count > 3) {
				char[] chars = hand.toCharArray();
				chars[hand.lastIndexOf(s)] = ' ';
				hand="";
				kanf += s;
				for(char c :chars) {
					hand+=c;
				}
				hand = hand.replace(" ", "");

				if(hand.length() < handcount) {
					int i = AMath.random(decks.length)-1;
					while(!decks[i]) {
						i = AMath.random(decks.length)-1;
					}
					decks[i] = false;
					hand += dack[i];
					drow = dack[i];
					if(handf.indexOf(dack[i]) == -1) {
						handf+=dack[i];
					}
					kan++;
					if(drow.equals(set[0])) {
						iskang = true;
					}
					if(kan == 1) {
						skill("c"+number+"kang1");
					}
					if(kan == 2) {
						skill("c"+number+"kang2");
					}
					if(kan == 3) {
						skill("c"+number+"kang3");
					}
					handset();
				}
				return true;
			}
		}
		return false;
	}
	public static String bubble(String val) {
		String retur ="";
		char[] arr = val.toCharArray();
		
		int j=0;
		while(j<val.length()-1)
		{
			int k=0;
			while(k<val.length()-1-j)
			{
				if(arr[k]<arr[k+1])
				{
					char tmp;
			 
					tmp = arr[k];
					arr[k] = arr[k+1];
					arr[k+1] = tmp;
				}
				k++;
			}
			j++;
		}
		for(char c :arr) {
			retur+=c;
		}
		return retur;
	}
	
	public void score() {
		String counts = "";
		boolean danil = true;
		for(int i=0; i<pe.length;i++){
			pe[i] = false;
		}
		
		for(char s : handf.toCharArray()) {
			int count = 0;
			for(char c : hand.toCharArray()) {
				if((""+s).indexOf(c) > -1) {
					count++;
				}
			}
			if(count > 0) {
				counts+=count;
			}
		}
		String countjr = bubble(counts);


		if(hand.indexOf(set[0]) > -1) {
			danil = false;
		}
		
		
		if(counts.indexOf("1") == -1) {
			pe[8] = true;
			if(danil) {
				pe[0] = true;
			}
			
			if(counts.substring(0, 1).equals("4") || kanf.indexOf(hand.substring(0,1)) > -1) {
				pe[1] = true;
			}
			if(countjr.equals("3332")|| countjr.equals("443") ) {
				pe[2] = true;
			}
			
			if(kan > 2) {
				pe[3] = true;
			}
			
			if(drow.equals(set[0]) && kan == 0) {
				pe[4] = true;
			} else if(kan == 0) {
				pe[5] = true;
			}
			if(kan > 0 && !iskang) {
				pe[7] = true;
			}
		}

	}
	
	public c16saki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 16;
		load();
		text();
		reset();
	}
	
	public void deal() {
		score = 0;
		info = "";
		if(pe[0]&&pe[1]&&pe[2]&&pe[3]&&pe[7]&&!iskang) {
			spskillon();
			info = "32000!";
			return;
		}
		if(pe[8] == true) {
			score += 2;
			info +=  Main.GetText("c16:text9")+"("+pescore[8]+")";
			for(int i=0; i< pe.length-1;i++) {
				if(pe[i]==true) {
					score += pescore[i];
					info += " "+ Main.GetText("c16:text"+(i+1))+"("+pescore[i]+")";
				}
			}
		} else {
			info = "§c✖";
		}
	}
	
	public void attack(Entity e) {
		int count=1;
		int delay = 20;
		score = 2;
		
		if(e instanceof Player) {
			Player p = (Player)e;
			if(pe[8]) {
				p.sendTitle(Main.GetText("c16:text"+9), handColor(),5,20,5);
				if(p == player) skill("c"+number+"tumo");
				for(int i=0; i< pe.length-1;i++) {
					if(pe[i]) {
						text = ""+i;
						score+=pescore[i];
	
						delay(new Runnable() {
							Entity ent = e;
							int t = Integer.parseInt(text);
							int s = score;
							@Override
							public void run() {
								ARSystem.spellCast(player, e, "c16_e");
								if(s > 8) ARSystem.spellCast(player, e, "c16_e2");
								if(s > 12) ARSystem.spellCast(player, e, "c16_e2");
								skill("c16p"+t);
								ARSystem.giveBuff((LivingEntity) e, new TimeStop((LivingEntity) e), 32);
								p.sendTitle(Main.GetText("c16:text"+(t+1)),"Score : "+s,5,5,20);
							}
						}, count*delay);
						count++;
					}
				}
				if(p != player) {
					delay(new Runnable() {
						Entity ent = e;
						int fscore = score;
						int scores = score;
						@Override
						public void run() {
							double hp = ((LivingEntity)ent).getHealth();
							double sb = 0;
							if(Rule.c.get(ent) != null) {
								if(Rule.buffmanager.GetBuffValue((LivingEntity) ent, "barrier") > 0) {
									if(Rule.buffmanager.GetBuffValue(player, "barrier") - scores > 0) {
										Rule.buffmanager.selectBuffAddValue((LivingEntity) ent, "barrier",-scores);
										scores = 0;
									} else {
										scores-= Rule.buffmanager.GetBuffValue((LivingEntity) ent, "barrier");
										Rule.buffmanager.selectBuffValue((LivingEntity) ent, "barrier",0);
									}
								}
							}
							
							if(((LivingEntity)ent).getHealth()-scores > 0) {
								((LivingEntity)ent).setHealth(((LivingEntity)ent).getHealth()-scores);
								if(player.getMaxHealth() > player.getHealth()+fscore) {
									player.setHealth(player.getHealth()+fscore);
								} else {
									if(ent instanceof Player && ((Player) ent).getGameMode() != GameMode.SPECTATOR) {
										Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) (fscore - (player.getMaxHealth() -player.getHealth())));
										player.setHealth(player.getMaxHealth());
									}
								}
								player.sendTitle("","HP: +"+scores,0,30,10);
							} else {
								Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) (fscore));
								Skill.remove(ent, player);
							}
						}
					}, count*delay);
				}
			}
		} else {
			for(int i=0; i< pe.length-1;i++) {
				if(pe[i]) {
					text = ""+i;
					score+=pescore[i];
					count++;
				}
			}
			delay(new Runnable() {
				Entity ent = e;
				int fscore = score;
				int scores = score;
				@Override
				public void run() {
					ARSystem.spellCast(player, e, "c16_e");
					if(scores > 8) ARSystem.spellCast(player, e, "c16_e2");
					if(scores > 12) ARSystem.spellCast(player, e, "c16_e2");
					
					if(((LivingEntity)ent).getHealth()-scores > 0) {
						((LivingEntity)ent).setHealth(((LivingEntity)ent).getHealth()-scores);
						if(player.getMaxHealth() > player.getHealth()+fscore) {
							player.setHealth(player.getHealth()+fscore);
						} else {
							Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) (fscore - (player.getMaxHealth() -player.getHealth())));
							player.setHealth(player.getMaxHealth());
						}
						player.sendTitle("","HP: "+Rule.buffmanager.GetBuffValue(player, "plushp"),0,30,10);
					} else {
						Skill.remove(ent, player);
					}
				}
			}, count*delay);
		}
		if(e == player) {
			delay(new Runnable() {
				@Override
				public void run() {
					skill("c16_es");
					skill("c16_es2");
				}
			},(count*delay)+40);
		}
	}
	
	@Override
	public boolean skill1() {
		double c = skillmult + sskillmult;
		ARSystem.playSound((Entity)player, "c16get");
		for(int i = 0; i < c; i++) {
			if(hand.length() != handcount) {
				drow();
				player.sendTitle(drow, handColor(), 10, 30, 0);
			} else {
				deal();
				player.sendTitle(info, handColor(), 10, 30, 0);
			}
		}
		return true;
	}
		
	@Override
	public boolean skill2() {
		if (kan()) {
			player.sendTitle("§a"+kanf +"§b-> §f"+drow , handColor(), 10, 30, 0);
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(hand.length() > 4) {
			skill("c"+number+"deck");
			skill("c"+number+"drop");
			reset();
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		List<Entity> entity = ARSystem.box(player,new Vector(6,6,6),box.TARGET);
		if(isps) {
			entity = ARSystem.box(player,new Vector(1000,1000,1000),box.MYALL);
			spskillen();
			for(Player p : Bukkit.getOnlinePlayers()) {
				ARSystem.giveBuff(p, new TimeStop(p), 200);
			}
			for(Entity e : entity) {
				ARSystem.spellCast(player, e, "c16_e");
				ARSystem.spellCast(player, e, "c16_e");
				ARSystem.spellCast(player, e, "c16_e");
				ARSystem.spellCast(player, e, "c16_e2");
				ARSystem.spellCast(player, e, "c16_e2");
				ARSystem.spellCast(player, e, "c16_e2");
				
			}
			ARSystem.playSoundAll("c16sp");
			player.performCommand("tm anitext all SUBTITLE true 90 c16:text10/§aScore : §c§kaaaaa");
			delay(()->{
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.sendTitle("§d"+Main.GetText("c16:text8"),"§fScore : §kaaaaa",10,40,0);
				}
			},140);
			delay(()->{
					ARSystem.playSoundAll("c16sp2");
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendTitle("§4§l《§4"+Main.GetText("c16:text8")+"§4§l》","§dScore : 32000!!!",0,10,40);
					}
					delay(()->{
						WinEvent event = new WinEvent(player);
						Bukkit.getPluginManager().callEvent(event);
						if(!event.isCancelled()) {
							Skill.win(player);
						}
					},40);
			},160);
			
		}
		else if(hand.length() == handcount && entity.size() > 0 && pe[8]) {
			skill("c"+number+"deck");
			s_score+= score;
			attack(player);
			for(Entity e : entity) {
				if (e instanceof ArmorStand) {
					
				} else {
					if(pe[7]) {
						count++;
						if(count > 5) {
							Rule.playerinfo.get(player).tropy(16,1);
						}
					}
					attack(e);
				}
			}
			reset();
		} else {
			cooldown[4] = 0;
		}
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
