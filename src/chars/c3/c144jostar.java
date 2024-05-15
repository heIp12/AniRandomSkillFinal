package chars.c3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Style;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Saito;
import buff.Airborne;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.NoCC;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c.c39sakuya;
import chars.c.c45momo;
import chars.c2.c60gil;
import chars.c2.c69himi;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ItemCreate;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c144jostar extends c00main{
	
	int lv = 1;
	int p = 0;
	Entity pt;
	
	int s1 = 5;
	int s1t = 0;
	
	Horse h;
	
	int s2 = 0;
	int s2p = 0;
	
	int s3 = 0;
	
	int spc = 0;
	
	@Override
	public void setStack(float f) {
		s1 = (int)f;
		s2 = (int)f;
	}
	
	public c144jostar(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 144;
		load();
		text();
		c = this;
	}

	boolean ishorse() {
		if(h!= null && h.getHealth() > 0 && h.getPassenger() == player) return true;
		return false;
	}

	@Override
	public boolean skill1() {
		if(s1 > 0) {
			if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
				if(h != null && h.hasPotionEffect(PotionEffectType.INVISIBILITY)) h.removePotionEffect(PotionEffectType.INVISIBILITY);
			}
			s1--;
			if(ishorse()&& lv >= 2) {
				ARSystem.playSound((Entity)player, "c144s11");
				skill("c144_s1-2");		
			} else {
				ARSystem.playSound((Entity)player, "c144s1");
				skill("c144_s1");
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}

	@Override
	public boolean skill2() {
		if((s2 >= 5 && lv < 3) || (s2 > 9 && lv == 3)) {
			if(lv < 3) {
				lv += s2/5;
				if(lv > 3) {
					sskillmult += 0.25f;
					lv = 3;
				}
			} else {
				lv = 4;
			}
			ARSystem.playSound((Entity)player, "c144act"+lv);
			if(h != null) {
				h.setMaxHealth(7 + (lv*5));
				ARSystem.heal(h, (s2/5)*5);
				h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15f + 0.05*lv);
			}
		}
		s2 = 0;
		if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			if(h != null && h.hasPotionEffect(PotionEffectType.INVISIBILITY)) h.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		if(ishorse()) {
			ARSystem.playSound((Entity)player, "c144s21");
			s1+=2;
			if(s1 > 5) s1 = 5;
			cooldown[2]*= 0.2;
		} else {
			ARSystem.playSound((Entity)player, "c144s2");
			delay(()->{

				s2p = 10;
				Location lo = player.getLocation();
				lo.setPitch(-15);
				player.setVelocity(player.getLocation().getDirection().multiply(2f));
				s2 = 0;
				delay(()->{
					for(Entity e : ARSystem.box(player, new Vector(8,3,8), box.TARGET)) {
						Location lc = player.getLocation();
						lc.setPitch(0);
						e.setVelocity(lc.getDirection().multiply(-2));
					}
				},8);
				delay(()->{
					ARSystem.playSound((Entity)player, "c144horse");
					ARSystem.playSound((Entity)player, "c144horse2");
					if(h != null) h.remove();
					
					h = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
					h.setAge(30000);
					h.setBreed(false);
					h.setStyle(Style.WHITEFIELD);
					h.setOwner(player);
					h.addPassenger(player);
					h.getInventory().setItem(0, ItemCreate.Item(329));
					h.setMaxHealth(7 + (lv*5));
					h.setHealth(7 + (lv*5));
					h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15f + 0.05*lv);
					cooldown[2] = 5;
				},10);
			},15);
			
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			if(h != null && h.hasPotionEffect(PotionEffectType.INVISIBILITY)) h.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		if(ishorse() && lv >= 3) {
			ARSystem.playSound((Entity)player, "c144s31");
			ARSystem.potion(h, 14, 100, 1);
			ARSystem.potion(player, 14, 100, 1);
		} else {
			ARSystem.playSound((Entity)player, "c144s3");
			s3 = 100;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			if(h != null && h.hasPotionEffect(PotionEffectType.INVISIBILITY)) h.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		if(ishorse()&& lv >= 4) {
			ARSystem.playSound((Entity)player, "c144s41");
			skill("c144_s4");
		} else {
			ARSystem.playSound((Entity)player, "c144s4");
			ARSystem.heal(player, 8);
			s1 = 5;
			s1t = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill5() {
		if(lv >=4) {
			if(!isps) {
				Rule.playerinfo.get(player).tropy(144, 1);
				spc = 200;
				ARSystem.giveBuff(h, new TimeStop(h), 140);
				ARSystem.giveBuff(h, new Nodamage(h), 100);
				ARSystem.giveBuff(player, new TimeStop(player), 140);
				ARSystem.giveBuff(player, new Silence(player), 140);
				player.teleport(player);
				ARSystem.playSound((Entity)player, "c144sp1");
				spskillon();
				spskillen();
				h.setMaxHealth(40 + player.getMaxHealth());
				h.setHealth(40 + player.getMaxHealth());
				player.setMaxHealth(h.getHealth());
				player.setHealth(h.getHealth());
				h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5f);
				
				for(Entity e : ARSystem.box(player, new Vector(16,8,16), box.TARGET)) {
					if(e == h) continue;
					LivingEntity en = (LivingEntity)e;
					ARSystem.giveBuff(en, new Silence(en), 200);
					ARSystem.giveBuff(en, new Stun(en), 200);
				}
				delay(()->{
					player.teleport(player.getLocation().clone().add(0,1.5,0));
					ARSystem.playSound((Entity)player, "c144sp2");

					LivingEntity en = null;
					List<Entity> tg = ARSystem.boxS(player, new Vector(16,12,16), box.TARGET);
					for(Entity e : tg) {
						if(e != player && e != h) {
							en = (LivingEntity)e;
						}
					}
					if(en != null) {
						ARSystem.giveBuff(en, new Stun(en), 200);
						ARSystem.giveBuff(en, new Silence(en), 200);
						player.teleport(ULocal.lookAt(player.getLocation().clone(),en.getLocation()));
						ARSystem.spellLocCast(player, ULocal.lookAt(player.getLocation(), en.getLocation()), "c144_sp");
					} else {
						skill("c144_sp");
					}
					
				},80);
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(h != null && h == target) return;
		if(n.equals("1")) {
			s2++;
			if(s2 > 10) s2 = 10;
			ARSystem.addBuff(target, new Stun(target), 8);
			if(ishorse() && lv >= 2){
				target.setNoDamageTicks(0);
				target.damage(target.getMaxHealth()*0.1f,player);
				if(lv > 2) {
					ARSystem.addBuff(target, new Silence(target), 8);
				}
			} else {
				target.setNoDamageTicks(0);
				target.damage(2,player);
			}
		}
		if(n.equals("2")) {
			if(Rule.buffmanager.GetBuffTime(target, "stun") >= 24) {
				target.damage(30);
			} else {
				target.damage(15);
			}
		}
		if(n.equals("3")) {
			float damage = 1;
			if(target.getHealth() - damage >= 1) {
				target.setHealth(target.getHealth() - damage);
			} else {
				Skill.remove(target, player);
			}
		}
		if(n.equals("4")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			target.setVelocity(ULocal.lookAt(player.getLocation().clone(), target.getLocation()).getDirection().multiply(2));
		}
	}
	
	double php = player.getHealth();
	Location lc = player.getLocation();
	@Override
	public boolean tick() {
		int id = player.getLocation().getBlock().getTypeId();
		if(id == 8 || id == 9) {
			s2p = 4;
		}
		if(isps) {
			if(h == null || h.getHealth() < 1) {
				Skill.remove(player, player);
			} else {
				if(player.getHealth() - php > 0) {
					ARSystem.heal(h, player.getHealth() - php);
				}
				player.setMaxHealth(h.getMaxHealth());
				player.setHealth(h.getHealth());
				php = player.getHealth();
			}
		}
		if(s1 < 5) {
			s1t+=skillmult+ sskillmult;
			if(s1t > 100) {
				s1t = 0;
				s1++;
			}
		}
		if(s3 > 0) {
			s3--;
			if(tk%5 == 0) {
				int i = 1;
				if(AMath.random(2) == 1) i = -1;
				
				ARSystem.spellLocCast(player, ULocal.offset(player.getLocation().clone(), new Vector(0,2-(AMath.random(20)*0.1),i)), "c144_s3");
			}
		}
		if(s2p > 0) s2p--;
		if(spc > 0) spc--;
		else if(isps){
			LivingEntity en = null;
			List<Entity> tg = ARSystem.boxS(player, new Vector(5,5,5), box.TARGET);
			if(tg.size() > 0) {
				for(Entity e : tg) {
					if(e != player && e != h) {
						en = (LivingEntity)e;
					}
				}
				if(en != null) {
					spc = 60;
					ARSystem.spellLocCast(player, ULocal.lookAt(player.getLocation(), en.getLocation()), "c144p");
				}
			}
		}
		if(h != null && !Map.inMap(h.getLocation())) {
			h.teleport(ULocal.BoxNear(Map.loc_f.clone().add(2,0,2), Map.loc_l.clone().add(-2,0,-2), h.getLocation()));
		}
		if(h != null && h.getHealth() < 1) {
			h = null;
			cooldown[2] = setcooldown[2];
		}
		
		if(h != null && h.getHealth() > 0 && !ishorse()) {
			if(Rule.buffmanager.GetBuffTime(player, "timestop") <= 0) h.addPassenger(player);
		} else {
			if(!ishorse() && s2p <= 0) {
				player.setVelocity(new Vector(0,-3,0));
				if(!player.isOnGround() && lc.getY() < player.getLocation().getY()) {
					player.teleport(lc);
				}
			}
			lc = player.getLocation();
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&a ["+Main.GetText("c144:t2")+lv+ "]");
			scoreBoardText.add("&c ["+Main.GetText("c144:t1")+ "] :  "+ s2);
			scoreBoardText.add("&c ["+Main.GetText("c144:sk1")+ "] :  "+ s1 +" (" +AMath.round((100 -s1t)*0.05, 2) +")");
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(h!= null && e.getEntity() == h) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(pt != e.getEntity()) {
				pt = e.getEntity();
				p = 0;
			} else {
				p++;
			}
			e.setDamage(e.getDamage() + e.getDamage()*0.05*p);
		} else {
			if(ishorse()) {
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.giveBuff(player, new Nodamage(player), 4);
				return false;
			}
			if(s3 > 0) {
				player.teleport(ULocal.lookAt(player.getLocation().clone(),e.getDamager().getLocation()));
				skill1();
				e.setDamage(e.getDamage() * 0.7f);
			}
		}
		return true;
	}
}
