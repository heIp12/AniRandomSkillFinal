package ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c3800hajime extends c00main{
	Location loc;
	float cooldownc[] = new float[4];
	
	private enum Enchent{
		 EXPLO
		,DRAIN
		,DOUBLE
		,TRIPLE
		,CRIT
		,STEN
	}
	
	List<gun> mygun = new ArrayList<gun>();
	int gun = 0;
	int guncount = 0;
	
	int replay = 1;
	int tick = 0;
	int heal = 0;
	
	int en = 0;

	public c3800hajime(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 38;
		load();
		text();
		loc = player.getLocation();
	}
	

	@Override
	public boolean skill1() {
		if(!mygun.isEmpty()) {
			mygun.get(gun).shot();
			cooldown[1] = (float) mygun.get(gun).speed;
			if(cooldown[1] < 0.4) {
				cooldown[1] = 0.4f;
			}
			if(mygun.get(gun).speed < 0.1) cooldown[1] = 0.2f;
			if(mygun.get(gun).speed < 0.05) cooldown[1] = 0.1f;
			
			cooldownc[gun] = cooldown[1];
		}
		return true;
	}
	@Override
	public boolean skill2() {
		if(!mygun.isEmpty()) {
			skill("c38_s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(!mygun.isEmpty() && heal > 0) {
			heal--;
			skill("c38_s3");
			ARSystem.heal(player, 10000);
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(!mygun.isEmpty()) {
			gun+=1;
			if(gun > guncount-1) {
				gun = 0;
			}
			cooldown[1] = cooldownc[gun];
			player.sendTitle("§c ["+Main.GetText("c38:t1"+gun)+ "]", "" ,0,20,0);
		}
		return true;
	}

	@Override
	public boolean skill5() {
		if(!mygun.isEmpty()) {
			ARSystem.playSound((Entity)player, "c38b");
			mygun.get(gun).reload();
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(tk%20==0) {
			for(int i=0; i < guncount;i++) {
				scoreBoardText.add("&c ["+Main.GetText("c38:t1"+i)+ "]");
				scoreBoardText.add(mygun.get(i).getinfo(1));
				scoreBoardText.add(mygun.get(i).getinfo(2));
				if(mygun.get(i).getinfo(3) != "") {
					scoreBoardText.add(mygun.get(i).getinfo(3));
				}
			}
		}
		if(tk%20==0 && heal > 0) {
			scoreBoardText.add("&a "+Main.GetText("c38:sk3")+ " : " + heal);
		}
		if(tk%2==0) {
			for(int i = 0; i < cooldownc.length; i++) {
				if(cooldownc[i] > 0) {
					cooldownc[i] -= 0.1*(skillmult+sskillmult);
					if(cooldownc[i] < 0) {
						cooldownc[i] = 0;
					}
				}
			}
		}
		return true;
	}
	
	public void wepone() {
		int e = 5;
		e+= AMath.random(5);
		if(AMath.random(10) == 1) {
			e = 14;
			ARSystem.playSound((Entity)player,"c38_p2");
		} else {
			ARSystem.playSound((Entity)player,"c38_p");
		}
		en = e;
		gun = guncount;
		mygun.add(new gun(guncount++));
		enchent();
		if(guncount >= 3) {
			Rule.playerinfo.get(player).tropy(38,1);
		}
	}
	
	public void enchent() {
		invskill = new InvSkill(player) {
			int b = 0;
			@Override
			public void End() {
				if(b == 0) {
					b = 1;
					openInventory(player);
					b = 0;
				}
				b=0;
				
			}
			
			@Override
			public void Start(String st) {
				if(Integer.parseInt(st.substring(0, 1)) != 0) {
					en--;
				}
				
				mygun.get(gun).upgrad(Integer.parseInt(st.substring(0, 1)));
				
				if(en > 0) {
					org.bukkit.inventory.Inventory inv = this.inventory;
					List<String> s = new ArrayList<String>();
					ItemStack is = new ItemStack(267, 1);
					s.clear();
					ItemMeta im = is.getItemMeta();
					im.setDisplayName("§c["+Main.GetText("c38:t1"+gun)+ "]");
					s.add("§f" + Main.GetText("c38:t21") + en);
					s.add("§f" +mygun.get(gun).getinfo(1));
					s.add("§f" +mygun.get(gun).getinfo(2));
					s.add("§f" +mygun.get(gun).getinfo(3));
					im.setLore(s);
					is.setItemMeta(im);
					inv.setItem(1, is);
					this.setInventory(inv);
					b = 1;
					openInventory(player);
				} else {
					Rule.buffmanager.selectBuffTime(player, "timestop",0);
					b = 1;
					player.closeInventory();
				}
			}
		};
		org.bukkit.inventory.Inventory inv = new CraftInventoryCustom(null, 9,"CREATE");
		List<String> s = new ArrayList<String>();
		ItemStack is = new ItemStack(267, 1);
		s.clear();
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("0§c["+Main.GetText("c38:t1"+gun)+ "]");
		s.add("§f" + Main.GetText("c38:t21") + en);
		s.add(mygun.get(gun).getinfo(1));
		s.add(mygun.get(gun).getinfo(2));
		s.add(mygun.get(gun).getinfo(3));
		im.setLore(s);
		is.setItemMeta(im);
		inv.setItem(1, is);
		
		s.clear();
		im.setDisplayName("1§f:"+Main.GetText("c38:t6"));
		s.add("§a§l"+Main.GetText("c38:t1")+": +1");
		s.add("§c§l"+Main.GetText("c38:t2")+": 50%");
		s.add("§c§l"+Main.GetText("c38:t3")+": -20%");
		s.add("§a§l"+Main.GetText("c38:t5")+": -30%");
		im.setLore(s);
		is.setItemMeta(im);
		inv.setItem(3, is);
		
		s.clear();
		im = is.getItemMeta();
		im.setDisplayName("2§f:"+Main.GetText("c38:t7"));
		s.add("§c§l"+Main.GetText("c38:t1")+": -30%");
		s.add("§a§l"+Main.GetText("c38:t2")+": 30%");
		s.add("§a§l"+Main.GetText("c38:t3")+": 30%");
		s.add("§c§l"+Main.GetText("c38:t5")+": 30%");
		im.setLore(s);
		is.setItemMeta(im);
		inv.setItem(4, is);
		
		s.clear();
		im = is.getItemMeta();
		im.setDisplayName("3§f:"+Main.GetText("c38:t8"));
		s.add("§c§l"+Main.GetText("c38:t1")+": -20%");
		s.add("§a§l"+Main.GetText("c38:t2")+": 10%");
		s.add("§a§l"+Main.GetText("c38:t3")+": 50%");
		s.add("§c§l"+Main.GetText("c38:t5")+": 10%");
		im.setLore(s);
		is.setItemMeta(im);
		inv.setItem(5, is);
		
		s.clear();
		im = is.getItemMeta();
		im.setDisplayName("4§f:"+Main.GetText("c38:t9"));
		s.add("§a§l"+Main.GetText("c38:t20"));
		im.setLore(s);
		is.setItemMeta(im);
		inv.setItem(6, is);
		invskill.setInventory(inv);

		invskill.openInventory(player);
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player || ARSystem.gameMode == modes.LOBOTOMY) {
			if(guncount < 3) {
				ARSystem.giveBuff(player, new TimeStop(player), 400);
				wepone();
			}
		}
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			mygun.get(0).damage(target);
		}
		if(n.equals("2")) {
			mygun.get(1).damage(target);
		}
		if(n.equals("3")) {
			mygun.get(2).damage(target);
		}
		if(n.equals("4")) {
			mygun.get(3).damage(target);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
		} else {
			if(!isps && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time < 30 && ((LivingEntity)e.getEntity()).getHealth() <= e.getDamage()+1) {
				spskillon();
				spskillen();
				heal = 3;
				ARSystem.giveBuff(player, new TimeStop(player), 480);
				player.setMaxHealth(30);
				player.setHealth(player.getMaxHealth());
				ARSystem.playSoundAll("c38sp");
				delay(new Runnable() {
					
					@Override
					public void run() {
						Map.playeTp(player);
						wepone();
					}
				},80);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
	private class gun {
		double damage = 2.5;
		int code = 0;
		
		double speed = 1.2;
		int bullet = 12;
		int mybullet = 12;
		double sp = 0.3;
		
		List<c3800hajime.Enchent> E = new ArrayList<c3800hajime.Enchent>();
		
		gun(int code){
			this.code = code;
		}
		
		public String getinfo(int i) {
			String s = "";
			if(i == 1) {
				s+= "§a"+Main.GetText("c38:t2") + ": §f" + AMath.round(speed,2) +" ";
				s+= "§b"+Main.GetText("c38:t3") +": §f"+ mybullet + " / " + bullet;
			}
			if(i == 2) {
				s+= "§c"+Main.GetText("c38:t1") + ": §f" + AMath.round(damage,2) + " ";
				s+= "§c"+Main.GetText("c38:t5") + ": §f" + AMath.round(sp,2) + " ";
			}
			if(i==3) {
				if(E.isEmpty()) return "";
				s+= "§e"+Main.GetText("c38:t4") +": §f";
				for(int k=0;k<E.size();k++) s+= " "+Main.GetText("c38:t"+ E.get(k));
			}
			return s;
		}
		
		public void upgrad(int i) {
			if(i == 1) {
				if(damage >= 3) {
					damage+=2;
				} else if(damage > 1) {
					damage+=1;
				} else {
					damage += 0.45;
				}
				sp*=0.7;
				if(damage > 10 && speed > 2.5) {
					damage += 2;
					speed += 1;
					sp*=0.5;
				}
				mybullet*=0.8;
				bullet*=0.8;
				speed*=1.5;
			}
			if(i == 2) {
				sp*=1.4;
				damage*=0.7;
				mybullet*=1.3;
				bullet*=1.3;
				speed*= 0.7;
				if(damage < 2 && speed < 1) {
					bullet*=1.2;
					mybullet*=1.2;
					damage*=0.8;
					speed*=0.8;
					sp*=1.5;
				}
			}
			if(i == 3) {
				sp*=1.1;
				damage*=0.8;
				mybullet*=1.5;
				bullet*=1.5;
				speed*=0.9;
				if(mybullet > 15) {
					mybullet*=1.5;
					bullet*=1.5;
					speed*=0.8;
					sp*=1.2;
				}
			}
			if(i == 4 && AMath.random(10) <= 5) {
				while(E.size() < c3800hajime.Enchent.values().length) {
					c3800hajime.Enchent ec = c3800hajime.Enchent.values()[AMath.random(c3800hajime.Enchent.values().length)-1];
					if(E.indexOf(ec) == -1) {
						E.add(ec);
						break;
					}
				}
			}
			
			if(damage < 0.1) damage = 0.1;
			
			if(sp > 30) sp = 30;
			if(mybullet < 1) mybullet = 1;
			if(bullet < 1) bullet = 1;
		}
		
		public void shot() {
			int n = 1;
			if(speed < 0.4/2) n = 2;
			if(speed < 0.4/3) n = 3;
			if(speed < 0.4/4) n = 4;
			if(speed < 0.4/5) n = 5;
			if(speed < 0.4/6) n = 6;
			if(speed < 0.4/7) n = 7;
			if(speed < 0.4/8) n = 8;
			if(speed < 0.4/9) n = 9;
			for(int j =0; j<n;j++) {
				
				delay(new Runnable() {
					
					@Override
					public void run() {
						if(mybullet > 0) {
							mybullet--;
							String type = "gun";
							int shot = 1;
		
							if(E.indexOf(c3800hajime.Enchent.TRIPLE) > -1 ){
								shot *= 3;
							}
							if(E.indexOf(c3800hajime.Enchent.DOUBLE) > -1 ){
								shot *= 2;
							}
							if(shot > 5) {
								ARSystem.playSound((Entity)player, "0gun5",(float) (2-(speed*0.5)));
							} else {
								ARSystem.playSound((Entity)player, "0gun2",(float) (2-(speed*0.5)));
							}
							for(int i=0;i<shot;i++) {
								Location l = player.getLocation();
								if(shot > 1) l.add(0.2+0.1*shot-AMath.random(10+shot*5)*0.02,0.2+0.1*shot-AMath.random(10+shot*5)*0.02,0.2+0.1*shot-AMath.random(10+shot*5)*0.02);
								if(sp > 0.01) {
									l.setPitch((float) (l.getPitch() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
									l.setYaw((float) (l.getYaw() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
								}
								ARSystem.spellLocCast(player, l, "c38_s-"+type+code);
							}
							ARSystem.playerAddRotate(player,0,(float) -damage*shot*2);
						}
					}
				},(int) (j*(8.0/n)));
					
			}
		}
		
		public void damage(LivingEntity e) {
			double damage = this.damage;
			if(E.indexOf(c3800hajime.Enchent.DRAIN) > -1 ){
				ARSystem.heal(player, damage);
			}
			if(E.indexOf(c3800hajime.Enchent.EXPLO) > -1 ){
				ARSystem.spellLocCast(player,e.getLocation(), "c38_ex");
				for(Entity p : e.getNearbyEntities(5, 5, 5)) {
					if(p != player && p instanceof LivingEntity) {
						((LivingEntity)p).damage(damage,player);
					}
				}
			}
			if(E.indexOf(c3800hajime.Enchent.CRIT) > -1 ){
				if(AMath.random(10)<=3) {
					damage*=2;
				}
			}
			if(E.indexOf(c3800hajime.Enchent.STEN) > -1 ){
				ARSystem.giveBuff(player, new Silence(player), (int) (damage/(speed)));
				ARSystem.giveBuff(player, new Stun(player), (int) (damage/(speed)));
				
			}
			e.setNoDamageTicks(0);
			e.damage(damage, player);
		}
		
		public void reload(){
			mybullet = bullet;
		}
	}
}
