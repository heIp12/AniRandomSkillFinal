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
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;

import util.AMath;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c52toby extends c00main{
	double mana = 30;
	double mana2 = 20;
	int sk1 = 0;
	boolean sk2 = false;
	int sk3 = 0;
	boolean sk4 = false;
	int count = 0;
	
	List<Entity> camui = null;
	Location camuiloc = null;
	
	public c52toby(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 52;
		load();
		text();
		
	}
	
	@Override
	public void setStack(float f) {
		mana = mana2 = f;
	}
	

	@Override
	public boolean skill1() {
		if(sk2) {
			cooldown[1] = 0;
			return false;
		}
		if(mana > 1) {
			mana -= 1;
			ARSystem.addBuff(player, new Stun(player), 10);
			sk1++;
			if(sk1 > 2) {
				sk1 = 0;
				skill("c52s2");
				ARSystem.playSound((Entity)player, "c52s12");
			} else {
				ARSystem.playSound((Entity)player, "c52s1");
				skill("c52s1");
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(!sk2 && mana >= 1) {
			ARSystem.playSound((Entity)player, "c52s2");
			delay(()->{
				sk2 = true;
				ARSystem.giveBuff(player, new Nodamage(player), 20);
				ARSystem.giveBuff(player, new Noattack(player), 20);
				cooldown[2] = 0;
			},20);
		} else if(sk2){
			sk2 = false;
			Rule.buffmanager.selectBuffTime(player, "nodamage",0);
			Rule.buffmanager.selectBuffTime(player, "noattack",0);
			ARSystem.playSound((Entity)player, "c52s52");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(sk2) {
			cooldown[3] = 0;
			return false;
		}
		if(mana > 8) {
			mana-=8;
			ARSystem.playSound((Entity)player, "c52s3");
			ARSystem.giveBuff(player, new Stun(player), 20);
			delay(()->{ sk3 = 60; },20);
			return true;
		}
		cooldown[3] = 0;
		return true;
	}
	
	int tick = 0;
	@Override
	public boolean skill4() {
		
		if(sk2) {
			cooldown[4] = 0;
			return false;
		}
		if(mana2 > 5 && !sk4) {
			mana2 -= 5;
			sk4 = true;
			List<ABlock> ablock = new ArrayList<ABlock>();
			for(ABlock ab : aliveblock.Main.Aliveblock) {
				if(ab.getName().equals("camui")){
					ablock.add(ab);
				}
			}
			for(ABlock ab : ablock) {
				ab.removeBlock(20);
				aliveblock.Main.Aliveblock.remove(ab);
			}
			if(AMath.random(100) <= count*5 && skillCooldown(0)) {
				spskillen();
				spskillon();
				ARSystem.playSound((Entity)player, "c52sp");
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				delay(()->{
					List<Entity> entitys = ARSystem.box(player, new Vector(200,200,200),box.ALL);
					if(entitys.size() > 4) 	Rule.playerinfo.get(player).tropy(52,1);
					
					camuiloc = player.getLocation();
					String loc = player.getLocation().getWorld().getName();
					loc+=","+player.getLocation().getBlockX()+","+(player.getLocation().getBlockY()-1)+","+player.getLocation().getBlockZ();
					camui = entitys;
					camui.add(player);
					for(Entity e : entitys) {
						if(ARSystem.isGameMode("lobotomy") && entitys instanceof Player) ARSystem.giveBuff((LivingEntity) e, new Nodamage((LivingEntity) e), 200);
						ARSystem.giveBuff((LivingEntity) e, new Stun((LivingEntity) e), 20);
					}
					
					if(entitys.size() > 0) {
						ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
						Bukkit.dispatchCommand(console, "ab spawn camui "+loc+ " camui 10 c");
					}
				},20);
			} else {
				ARSystem.playSound((Entity)player, "c52s4");
				ARSystem.giveBuff(player, new TimeStop(player), 20);
				delay(()->{
					List<Entity> entitys = ARSystem.box(player, new Vector(8,8,8),box.TARGET);
					if(entitys.size() > 4) 	Rule.playerinfo.get(player).tropy(52,1);
					camuiloc = player.getLocation().clone().add(0,1,0);
					String loc = player.getLocation().getWorld().getName();
					loc+=","+player.getLocation().getBlockX()+","+(player.getLocation().getBlockY()-1)+","+player.getLocation().getBlockZ();
					camui = entitys;
					camui.add(player);
					for(Entity e : entitys) ARSystem.giveBuff((LivingEntity) e, new Stun((LivingEntity) e), 20);
					
					if(entitys.size() > 0) {
						ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
						Bukkit.dispatchCommand(console, "ab spawn camui "+loc+ " camui 10 c");
					}
				},20);
			}
			cooldown[4] = 0;
		}
		else if(sk4) {
			camui();
			return true;
		}
		cooldown[4] = 0;
		return true;
	}
	
	public void camui() {
		sk4 = false;
		camui = null;
		camuiloc = null;
		cooldown[4] = setcooldown[4];
		ARSystem.playSound((Entity)player, "c52s2");
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		Bukkit.dispatchCommand(console, "ab remove camui 10");
		
	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player && sk4) {
			camui();
		}
	}

	@Override
	public boolean tick() {
		tick++;
		if(tick%400 == 0) count++;
		
		if(sk4) {
			if(tk%40==0) {
				for(Entity e : camui) {
					if(e !=player) {
						((LivingEntity)e).damage(1,player);
					}
				}
			}
			if(tk%20==0) {
				mana2-=1;
			}
			if(mana2 <= 0) {
				camui();
			}
			if(camui != null && camui.size() > 0) {
				for(Entity e : camui) {
					if(e.getLocation().distance(camuiloc) > 8) {
						e.teleport(camuiloc.clone().add(0,1,0));
						if(e != player) {
							((LivingEntity)e).setNoDamageTicks(0);
							((LivingEntity)e).damage(8,player);
						}
					}
				}
			}
		}
		if(sk3 > 0) {
			sk3--;
			ARSystem.potion(player, 14, 4, 0);
			player.teleport(player.getLocation().add(player.getLocation().getDirection().clone().multiply(1.4)));
			if(player.isSneaking()) sk3 = 0;
			
			if(sk3 == 0) {
				ARSystem.playSound((Entity)player, "c52s32");
				cooldown[3] = setcooldown[3];
			}
		}
		if(tk%20 == 0) {
			if(!sk2) {
				if(ARSystem.isGameMode("zombie")) {
					mana+= 0.4 * (skillmult + sskillmult);
					if(!sk4) mana2+= 0.3 * (skillmult + sskillmult);
				} else {
					mana+= 0.8 * (skillmult + sskillmult);
					if(!sk4) mana2+= 0.6 * (skillmult + sskillmult);
				}
				if(mana > 30) mana = 30;
				if(mana2 > 20) mana2 = 20;
			} else {
				mana-=2;
				if(mana < 2) {
					sk2 = false;
					Rule.buffmanager.selectBuffTime(player, "nodamage",0);
					Rule.buffmanager.selectBuffTime(player, "noattack",0);
					ARSystem.playSound((Entity)player, "c52s52");
					cooldown[2] = setcooldown[2];
				} else {
					ARSystem.addBuff(player, new Nodamage(player), 20);
					ARSystem.addBuff(player, new Noattack(player), 20);
				}
			}
			scoreBoardText.add("&c ["+Main.GetText("c52:t1")+ "] : "+ AMath.round(mana,1));
			scoreBoardText.add("&c ["+Main.GetText("c52:t2")+ "] : "+ AMath.round(mana2,1));
			if(psopen) {
				scoreBoardText.add("&c ["+Main.GetText("c52:sk0")+ "] : "+ count*5 + "%");
			}
		}

		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*4);
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.3);
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		if(AMath.random(2) == 1) {
			ARSystem.playSound((Entity)player,"c52db");
		} else {
			ARSystem.playSound((Entity)player,"c52db2");
		}
		return true;
	}
}
