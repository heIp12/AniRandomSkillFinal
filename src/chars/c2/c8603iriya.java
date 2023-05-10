package chars.c2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Install;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c8603iriya extends c00main{
	Install b;
	boolean oon = false;
	public c8603iriya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 3086;
		load();
		text();
		c = this;
	}
	
	@Override
	public boolean skill1() {
		if(b.getValue() > 15) {
			b.setValue(b.getValue()-15);
			ARSystem.playSound((Entity)player, "c3086s1");
			skill("c3086_s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(b.getValue() > 10) {
			b.setValue(b.getValue()-10);
			ARSystem.playSound((Entity)player, "c3086s2");
			skill("c3086_s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(b.getValue() > 30) {
			b.setValue(b.getValue()-30);
			ARSystem.playSound((Entity)player, "0ice",2);
			skill("c3086_s3");
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill5() {
		Buff b = Rule.buffmanager.selectBuff(player, "Install");
		b.setTime(2);
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c86db");
		return true;
	}
	
	int pattan = 2;
	int pattantime = 100;
	int delay = 0;
	
	public void pattan1(LivingEntity target) {
		if(target != null) {
			Location loc = ULocal.lookAt(player.getLocation(), target.getLocation().clone().add(new Vector(1-0.2*AMath.random(10),1-0.2*AMath.random(10),1-0.2*AMath.random(10))));
			if(AMath.random(3) == 2) loc = ULocal.lookAt(player.getLocation(), target.getLocation().add(target.getVelocity().multiply(1.5)));
			if(delay <= 0 && player.isOnGround()) {
				player.setVelocity(new Vector(0,1,0));
				delay = 10;
			}
			if(delay <= 0) {
				player.teleport(loc);
				if(tk%5 == 0) {
					ARSystem.playSound((LivingEntity)player, "0katana3",2);
					skill("c3086_s1-"+(1+AMath.random(3)));
					if(AMath.random(8) == 2) skill("c3086_s3");
				}
			}
		}
		delay--;
	}
	public void pattan2(LivingEntity target) {
		if(target != null) {
			if(delay <= 0) {
				Location loc = ULocal.lookAt(player.getLocation(), target.getLocation().clone());
				double range = target.getLocation().distance(player.getLocation());
				if(range < 6) {
					if(range < 3) {
						delay = 10;
						player.setVelocity(loc.clone().add(0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10)).getDirection().multiply(3).setY(1.5));
						if(!player.isOnGround()) player.setVelocity(loc.getDirection().multiply(-4).setY(-0.5));
						ARSystem.giveBuff(player, new Nodamage(player), 8);
					} else {
						delay = 3;
						player.teleport(loc);
						player.setVelocity(loc.getDirection().multiply(-1).setY(0.2));
						if(!player.isOnGround()) player.setVelocity(loc.getDirection().multiply(-1).setY(-0.5));
						ARSystem.giveBuff(player, new Nodamage(player), 2);
					}
				}
				else if(range > 8) {
					delay = 2;
					player.setVelocity(loc.clone().add(0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10)).getDirection().multiply(0.6).setY(0));
					if(!player.isOnGround()) player.setVelocity(loc.getDirection().multiply(0.6).setY(-0.5));
					ARSystem.giveBuff(player, new Nodamage(player), 1);
				} else {
					delay = 10;
					player.teleport(loc);
					skill("c3086_s4");
				}
			}
			if(delay <= 0) {
				if(tk%5 == 0) {
					ARSystem.playSound((LivingEntity)player, "0katana3",2);
					skill("c3086_s1-"+(1+AMath.random(3)));
					if(AMath.random(8) == 2) skill("c3086_s3");
				}
			}
		}
		delay--;
	}
	public void pattan3(LivingEntity target) {
		if(target != null) {
			if(delay <= 0) {
				Location loc = ULocal.lookAt(player.getLocation(), target.getLocation().clone());
				double range = target.getLocation().distance(player.getLocation());
				if(range < 15) {
					delay = 20;
					if(player.isOnGround()) {
						player.setVelocity(loc.getDirection().multiply(-1).setY(1.5));
						ARSystem.giveBuff(player, new Nodamage(player), 10);
						delay(()->{
							Location loc2 = ULocal.lookAt(player.getLocation(), target.getLocation().clone());
							loc2.setPitch(loc2.getPitch()-20);
							for(int i = 0; i<3;i++) {
								delay(()->{
									loc2.setPitch(loc2.getPitch()+10);
									player.teleport(loc2);
									ARSystem.playSound((LivingEntity)player, "0katana3",2);
									skill("c3086_s1-"+(1+AMath.random(3)));
								},i);
							}
						},10);
					} else {
						delay = 4;
						player.teleport(loc);
						ARSystem.playSound((LivingEntity)player, "0katana3",2);
						skill("c3086_s1-"+(1+AMath.random(3)));
					}
				}
				else if(range > 15 && player.isOnGround()) {
					if(AMath.random(5) == 3) {
						delay = 20;
						ARSystem.giveBuff(player, new Stun(player), 20);
						player.teleport(loc);
						skill("c3086_s5");
						ARSystem.playSound((LivingEntity)player, "0katana2",0.5f);
					} else {
						delay = 10 + AMath.random(10);
						Location loc2 = ULocal.lookAt(player.getLocation(), target.getLocation().clone());
						loc2.setYaw(loc2.getYaw()+20);
						for(int i = 0; i<3;i++) {
							delay(()->{
								loc2.setYaw(loc2.getYaw()-10);
								player.teleport(loc2);
								ARSystem.playSound((LivingEntity)player, "0katana3",2);
								skill("c3086_s1-"+(1+AMath.random(3)));
							},i*3);
						}
						delay(()->{
							player.teleport(loc);
							ARSystem.playSound((LivingEntity)player, "0katana3",2);
							skill("c3086_s1-"+(1+AMath.random(3)));
						},10);
					}
				}
			}
		}
		delay--;
	}
	public void pattan4(LivingEntity target) {
		if(delay <= 0) {
			if(target != null && AMath.random(3) == 2) {
				delay = 5;
				Location ll = player.getLocation();
				ll.setYaw(ll.getYaw() + 10 - AMath.random(20));
				Location loc = ULocal.lookAt(ll, target.getLocation().clone());
				player.teleport(loc);
				player.setVelocity(loc.getDirection().multiply(1).setY(0));
				if(AMath.random(3) == 2 && player.isOnGround()) player.setVelocity(loc.getDirection().multiply(1).setY(1));
				ARSystem.giveBuff(player, new Nodamage(player), 2);
			} else {
				delay = 5+AMath.random(5);
				Location loc = player.getLocation();
				loc.setYaw(AMath.random(360));
				player.teleport(loc);
				player.setVelocity(loc.getDirection().multiply(1).setY(0));
				if(player.getLocation().add(player.getLocation().getDirection()).getBlock().isEmpty()) {
					if(player.isOnGround()) player.setVelocity(loc.getDirection().multiply(1).setY(1.5));
					delay = 10;
				} else {
					if(AMath.random(3) == 2 && player.isOnGround()) player.setVelocity(loc.getDirection().multiply(1).setY(1));
					if(AMath.random(3) == 2 && !player.isOnGround()) player.setVelocity(loc.getDirection().multiply(3).setY(-2));
				}
				ARSystem.giveBuff(player, new Nodamage(player), 5);
			}
		}
		delay--;
	}
		
	int rpt = 0;
	int attackT = 0;
	@Override
	public boolean tick() {
		if(isps) {
			if(oon == false) {
				oon = true;
				hp*=3;
				player.setMaxHealth(hp);
				player.setHealth(hp);
			}
			cooldown[1] = cooldown[2] = cooldown[3] = cooldown[5] = cooldown[6] = cooldown[9] = 300;
			LivingEntity target = (LivingEntity) ARSystem.boxSOne(player, new Vector(100,100,100), box.TARGET);
			double range = -1;
			if(target != null) player.getLocation().distance(target.getLocation());
			if(attackT > 100 && 100 <= AMath.random(attackT/5) && range >= 14) {
				pattan = 4;
				if(pattantime > 20) pattantime = 20;
			}
			if(pattantime > 0) {
				pattantime--;
				if(pattan == 1) pattan1(target);
				if(pattan == 2) pattan2(target);
				if(pattan == 3) pattan3(target);
				if(pattan == 4) pattan4(target);
			} else {
				pattantime = AMath.random(20)*10;
				if(range <= 14) pattan = 1+AMath.random(2);
				if(range > 14) pattan = AMath.random(2);
				if(AMath.random(5) == 3) pattan = AMath.random(3);
				if(rpt != pattan) ARSystem.playSound((Entity)player, "c3086s"+pattan);
				if(AMath.random(3) == 1) {
					pattan = 4;
					pattantime = AMath.random(20);
					if(AMath.random(8) == 4) pattantime = AMath.random(200)+100;
				}
				rpt = pattan;
			}
		} else {
			if(tk%20 == 0) {
				if(b == null) b = (Install)Rule.buffmanager.selectBuff(player, "Install");
				if(b != null) {
					scoreBoardText.add("&c ["+Main.GetText("c86:t")+"] : " + AMath.round(b.getValue(),1));
				}
			}
		}
		attackT++;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
			attackT = 0;
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.7);
			if(isps) {
				if(e.getDamager().getLocation().distance(player.getLocation()) > 8 && AMath.random(10) <= 7) {
					e.setDamage(0);
					ARSystem.playSound(player, "0miss",2);
					ARSystem.spellLocCast(player, ULocal.lookAt(player.getLocation().clone().add(0,1,0), e.getDamager().getLocation()), "c3086p");
				}
				if(isps) {
					e.setDamage(e.getDamage() * 0.75);
				}
			}
		}
		return true;
	}
}
