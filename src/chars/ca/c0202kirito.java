package chars.ca;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Airborne;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import chars.c.c00main;
import chars.c.c08yuuki;
import chars.c.c15tina;
import chars.c.c47ren;
import chars.c2.c56enju;
import chars.c2.c62shinon;
import manager.Bgm;
import types.box;

import util.AMath;

public class c0202kirito extends c00main{
	double[] cool = new double[8];
	String[] key = {"3323","3112","121212","3333","1233","2323","1231321","32112321123"};
	String cm = "";
	int cmd = 0;
	int sk4 = 0;
	
	public c0202kirito(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2002;
		load();
		text();
		ARSystem.playSound(player, "c2select");
	}
	
	int skill1 = 0;
	int skill1_tick = 0;
	
	@Override
	public boolean skill1() {
		if(sk4 == 0) {
			sk4 = 60;
			cm = "1";
			title();
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(sk4 == 0) {
			sk4 = 60;
			cm = "2";
			title();
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(sk4 == 0) {
			sk4 = 60;
			cm = "3";
			title();
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		cooldown[4] = 0;
		return true;
	}
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(sk4 > 0) {
			int i = 1+e.getNewSlot();
			if(i < 4) {
				cm += i;
				title();
				if(iscmd(cm) != -1) {
					sk4 = 0;
					cmd();
				}
			}
			if(i == 4&& skillCooldown(4)) {
				sk4 = 0;
				cool[AMath.random(7)] = 0;
				ARSystem.playSound((Entity)player, "c2g");
				cm = "";
			}
			if(i != 8) e.setCancelled(true);
			return true;
		} else {
			return super.key(e);
		}
	}
	
	void title() {
		String s = "";
		String k = "";
		for(int i=0; i< key.length;i++) {
			if(key[i].equals(cm)) {
				if(cool[i] <= 0) {
					s += "§6<<"+Main.GetText("c2002:t"+(i+1)) +">>";
				} else {
					for(int p=0; p< key.length;p++) {
						if(cool[p] <= 0) {
							s += " §a"+key[p];
						}
					}
				}
			} else if(key[i].contains(cm)) {
				if(cool[i] <= 0) {
					s += " §a"+key[i];
				} else {
					k += " §c"+key[i];
				}
			}
		}
		
		player.sendTitle(cm, s+k);
	}
	
	int iscmd(String s) {
		for(int i=0; i< key.length;i++) {
			if(key[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}
	
	void cmd() {
		if(iscmd(cm) != -1) {
			int	i = iscmd(cm);
			
			if(cool[i] <= 0) {
				if(i == 0) {
					skill("c2002_s1");
					ARSystem.playSound((Entity)player, "c2002bp");
					cool[i] = 10;
				}
				if(i == 1) {
					skill("c2002_s2");
					ARSystem.playSound((Entity)player, "c2002em");
					cool[i] = 8;
				}
				if(i == 2) {
					skill("c2002_s3");
					ARSystem.playSound((Entity)player, "c2h");
					cool[i] = 14;
				}
				if(i == 3) {
					ARSystem.playSound((Entity)player, "c2002heal");
					ARSystem.heal(player,14);
					cool[i] = 20;
				}
				if(i == 4) {
					ARSystem.playSound((Entity)player, "c2d");
					ARSystem.giveBuff(player, new Nodamage(player), 40);
					cool[i] = 12;
				}
				if(i == 5) {
					ARSystem.playSound((Entity)player, "c2a2");
					cool[i] = 15;
					skill("c2_s33_ia");
					skill("c2_s33_ia2");
					for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
						ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 60);
						((LivingEntity)e).setNoDamageTicks(0);
						((LivingEntity)e).damage(3,player);
						((LivingEntity)e).setNoDamageTicks(0);
						((LivingEntity)e).damage(2,player);
					}
				}
				if(i == 6) {
					skill("c2002_st1");
					ARSystem.giveBuff(player, new Nodamage(player), 100);
					ARSystem.potion(player, 1, 200, 3);
					cool[i] = 80;
				}
				if(i == 7 && skillCooldown(0)) {
					spskillen();
					spskillon();
					ARSystem.giveBuff(player, new Nodamage(player), 300);
					ARSystem.potion(player, 1, 200, 8);
					skill("c2002_sp1");
					cool[i] = setcooldown[0];
				}
				String c = Main.GetText("c997:sk4_lore"+(2+i));
				c = c.substring(c.length()-2, c.length());
				cm = "";
				ARSystem.playSound((Entity)player, "c2g");
			}
		}
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(player, new Airborne(player), 60);
			ARSystem.giveBuff(target, new Airborne(target), 60);
			target.setNoDamageTicks(0);
			target.damage(3,player);
		}
		if(n.equals("2")) {
			ARSystem.giveBuff(target, new Stun(target), 20);
			target.setNoDamageTicks(0);
			target.damage(3,player);
		}
	}
	
	
	@Override
	public boolean tick() {
		for(int i =0; i < cool.length;i++) {
			if(cool[i] > 0) cool[i] -= 0.05 * (skillmult + sskillmult);
		}
		if(tk%20 == 0) {
			for(int i =0; i < cool.length;i++) {
				if(cool[i] > 0) scoreBoardText.add("&c ["+Main.GetText("c2002:t"+(i+1))+ "]&f : " + AMath.round(cool[i],2));
			}
		}
		if(sk4 > 0) {
			sk4--;
			if(sk4 == 0) {
				ARSystem.playSound((Entity)player, "c2g");
				cm = "";
			}
		}
		if(cmd > 20 && !isps) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "rentaroselect");
		}
		return true;
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage() * 4);
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage() * 0.6);
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c08yuuki) {
					is = "yuuki";
					break;
				}
				if(Rule.c.get(e) instanceof c47ren){
					is = "ren";
					break;
				}
				if(Rule.c.get(e) instanceof c62shinon){
					is = "sinon";
					break;
				}
			}
		}
		
		if(is.equals("yuuki")) {
			ARSystem.playSound((Entity)player, "c2yuki");
		} else if(is.equals("ren")) {
			ARSystem.playSound((Entity)player, "c2ren");
		} else if(is.equals("sinon")) {
			ARSystem.playSound((Entity)player, "c2sinon");
		}else  {
			ARSystem.playSound((Entity)player, "c2db");
		}
		
		return true;
	}
}
