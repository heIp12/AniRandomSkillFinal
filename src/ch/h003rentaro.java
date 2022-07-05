package ch;

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
import c.c00main;
import c.c15tina;
import c2.c5600enju;
import c2.c56enju;
import manager.Bgm;
import types.box;
import types.modes;
import util.AMath;

public class h003rentaro extends c00main{
	double[] cool = new double[10];
	String[] key = {"411","422","441","442","424","412","432","444","414","421"};
	String cm = "";
	int cmd = 0;
	int sk4 = 0;
	
	public h003rentaro(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 997;
		load();
		text();
		ARSystem.playSound((Entity)player, "rentaroselect");
	}
	
	int skill1 = 0;
	int skill1_tick = 0;
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "rentaroa1");
		skill("rentaro_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "rentaroa2");
		skill("rentaro_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "rentaros3");
		ARSystem.giveBuff(player, new Nodamage(player), 20);
		return true;
	}
	
	@Override
	public boolean skill4() {
		cm = "4";
		title();
		sk4 = 20;
		return true;
	}
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(sk4 > 0 && cm.length() < 3) {
			int i = 1+e.getNewSlot();
			if(i < 5) {
				cm += i;
				title();
				if(cm.length() == 3) {
					sk4 = 0;
					cmd();
				}
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
					s += "§6<<"+Main.GetText("c997:t"+(i+1)) +">>";
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
	
	void cmd() {
		for(int i=0; i< key.length;i++) {
			if(key[i].equals(cm)) {
				if(cool[i] <= 0) {
					cmd++;
					if(isps) {
						for(int j=0; j< key.length;j++) {
							if(cool[j]>0) cool[j]-=1;
						}
					}
					skill("rentarop"+(i+1));
					String c = Main.GetText("c997:sk4_lore"+(2+i));
					c = c.substring(c.length()-2, c.length());
					cool[i] = Integer.parseInt(c.replace(" ", ""));
				}
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
		if(tk%20 == 0) {
			String s = Bgm.bgmcode;
			if(!s.equals("bc15")&& Bgm.rep) {
				Bgm.setBgm("c997");
			}
		}
		player.setFallDistance(0);
		for(int i =0; i < cool.length;i++) {
			if(cool[i] > 0) cool[i] -= 0.05 * (skillmult + sskillmult);
		}
		if(tk%20 == 0) {
			for(int i =0; i < cool.length;i++) {
				if(cool[i] > 0) scoreBoardText.add("&c ["+Main.GetText("c997:t"+(i+1))+ "]&f : " + AMath.round(cool[i],2));
			}
		}
		if(sk4 > 0) {
			sk4--;
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
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 4);
			if(ARSystem.gameMode == modes.LOBOTOMY) ARSystem.heal(player, e.getDamage()*0.03);
			if(e.getDamage() >= 2) {
				ARSystem.giveBuff((LivingEntity) e.getEntity(), new Silence((LivingEntity) e.getEntity()), 10);
				ARSystem.giveBuff((LivingEntity) e.getEntity(), new Noattack((LivingEntity) e.getEntity()), 10);
				ARSystem.spellLocCast(player, e.getEntity().getLocation().add(2-(AMath.random(40)*0.1),2-(AMath.random(40)*0.1),2-(AMath.random(40)*0.1)), "rentaro_p");
				ARSystem.spellLocCast(player, e.getEntity().getLocation().add(2-(AMath.random(40)*0.1),2-(AMath.random(40)*0.1),2-(AMath.random(40)*0.1)), "rentaro_p");
				ARSystem.spellLocCast(player, e.getEntity().getLocation().add(2-(AMath.random(40)*0.1),2-(AMath.random(40)*0.1),2-(AMath.random(40)*0.1)), "rentaro_p2");
			}
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 0.8);
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c56enju || Rule.c.get(e) instanceof c5600enju) {
					is = "enju";
					break;
				}

			}
		}
		
		if(is.equals("enju")) {
			ARSystem.playSound((Entity)player, "rentaroenju");
		} else {
			ARSystem.playSound((Entity)player, "rentarodb");
		}
		return true;
	}
}
