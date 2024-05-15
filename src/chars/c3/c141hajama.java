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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
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
import buff.Airborne;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.NoHeal;
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
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c141hajama extends c00main{

	int sk1t = 3;
	int sk1 = 0;
	int sk1d = 0;
	
	int sk2 = 3;
	int sk2max = 3;
	int sk2t = 0;
	LivingEntity sk2e;
	int sk2tt = 0;
	int sk2ttt = 0;
	
	LivingEntity spe;
	int spt = 0;
	
	int stack = 0;
	
	public c141hajama(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 141;
		load();
		text();
		c = this;
	}

	@Override
	public void setStack(float f) {
		if((int)f == 52) stack = 52;
		else sk2 = (int)f;
	}
	
	@Override
	public boolean skill1() {
		if(sk2tt > 0) {
			cooldown[1] = 0;
			return true;
		}
		if(stack == 52 && sk2 <= 0 && BlockUtil.isAirbone(player.getLocation(), 2) && cooldown[4] == 0) {
			Entity tg = ARSystem.boxSOne(player, new Vector(8,4,8), box.TARGET);
			if(tg != null) {
				player.teleport(ULocal.lookAt(player.getLocation().clone(), tg.getLocation()));
				cooldown[4] = setcooldown[4];
				skill4();
			}
			return false;
		}
		if(stack == 52 && player.isOnGround() && cooldown[3] == 0) {
			Entity tg = ARSystem.boxSOne(player, new Vector(8,4,8), box.TARGET);
			if(tg != null) {
				player.teleport(ULocal.lookAt(player.getLocation().clone(), tg.getLocation()));
				cooldown[3] = setcooldown[3];
				skill3();
				cooldown[1] = 2f;
				delay(()->{
					skill2();
					delay(()->{
						skill2();
					},15);
				},20);
			}
			return false;
		}
		if(sk1 == 0) {
			if(BlockUtil.isAirbone(player.getLocation(), 2)) {
				sk1t = 4;
			} else  {
				sk1t = 3;
			}
		}
		if(sk1 < sk1t-1) {
			sk1d = 30;
			sk1++;
			skill("c141_s1-" + (sk1%2));
			if(sk1t >= 4) {
				ARSystem.giveBuff(player, new Airborne(player), 4);
				player.setVelocity(player.getLocation().getDirection().multiply(0.2).setY(0));
			} else {
				player.setVelocity(player.getLocation().getDirection().multiply(0.6).setY(0));
			}
			ARSystem.playSound((Entity)player, "0slash4", 0.6f);
		} else {
			skill("c141_s1-2");
			sk1t = 0;
			cooldown[1]*= 15;
			ARSystem.playSound((Entity)player, "0sword3");
			if(stack == 52 && player.isSneaking()) {
				Entity tg = ARSystem.boxSOne(player, new Vector(8,8,8), box.TARGET);
				if((BlockUtil.isAirbone(tg.getLocation(), 2) && sk2 >= 3) || (BlockUtil.isAirbone(tg.getLocation(), 2) &&BlockUtil.isAirbone(player.getLocation(), 2) && sk2 >= 0)) {
					delay(()->{
						cooldown[2] = setcooldown[2];
						skill2();
						delay(()->{
							if(sk2tt >= 0) {
								delay(()->{
									if(sk2tt >= 0) {
										delay(()->{
											if(sk2tt >= 0) {
												cooldown[2] = setcooldown[2];
												cooldown[1] = 0;
												skill2();
											}
										},2);
									}
								},3);
							}
						},5);
					},5);
				}
			}
		}
		
		return true;
	}

	@Override
	public boolean skill2() {
		if(sk2tt <= 0 && sk2 > 0) {
			ARSystem.playSound((Entity)player, "c141s2");
			sk2e = null;
			sk2tt = 30;
			sk2 -=1;
			ARSystem.giveBuff(player, new Stun(player), 30);
			if(stack == 52 && player.isSneaking()) {
				Entity tg = ARSystem.boxSOne(player, new Vector(10,12,10), box.TARGET);
				player.teleport(ULocal.lookAt(player.getLocation().clone(), tg.getLocation()));
				Holo.create(player.getLocation(), "§a매크로 사용중",100,new Vector(0,0.1,0));
			}
			skill("c141_s2");
			player.setFallDistance(0);
		} else if(sk2tt > 0){
			player.setFallDistance(0);
			sk2tt = 0;
			if(sk2e != null) cooldown[1] = 0;
			
			boolean sf = player.isSneaking() && sk2e != null;
			if(sk2e != null && stack == 52 && !BlockUtil.isAirbone(player.getLocation(), 2)&& BlockUtil.isAirbone(sk2e.getLocation(), 2)) {
				sf = false;
			}
			if(sf) {
				if(Rule.buffmanager.OnBuffTime(sk2e, "stun")) {
					Rule.buffmanager.selectBuffTime(sk2e, "stun",0);
				}
				LivingEntity se = sk2e;
				delay(()->{
					ARSystem.giveBuff(se, new Airborne(se), 8);
					se.teleport(ULocal.lookAt(ULocal.offset(player.getLocation().clone(), new Vector(1,0,0)), player.getLocation()));
				},0);
				ARSystem.playSound((Entity)player, "c141s22");
			} else {
				if(Rule.buffmanager.OnBuffTime(player, "stun")) {
					Rule.buffmanager.selectBuffTime(player, "stun",0);
				}
				ARSystem.playSound((Entity)player, "c141s21");
				skill("c141_s2m");
			}
			sk2e = null;
		} else {
			cooldown[2] = 0.2f;
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(sk2tt > 0) {
			cooldown[3] = 0;
			return true;
		}
		if(player.isOnGround()) {
			skill("c141_s3");
			ARSystem.playSound((Entity)player, "c141s3");
			ARSystem.playSound((Entity)player, "0slash3", 0.7f, 2);
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	@Override
	public boolean skill4() {
		if(sk2tt > 0) {
			cooldown[4] = 0;
			return true;
		}
		player.setFallDistance(0);
		player.setVelocity(new Vector(0,-2,0));
		skill("c141_s4");
		skill("c141_s4-e");
		ARSystem.playSound((Entity)player, "c141s4");
		ARSystem.playSound((Entity)player, "0hit2");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(sk2 <= sk2max-1) {
			sk2t+= skillmult+sskillmult;
			if(sk2t > 160) {
				sk2t = 0;
				sk2++;
			}
		}
		if(sk1d > 0) {
			sk1d--;
			if(sk1d <= 0) {
				sk1 = 0;
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c141:sk2")+ "] : " +sk2 +" / " + sk2max+ " ("+AMath.round(sk2t*0.05, 1)+")");
			if(psopen && spe != null) scoreBoardText.add("&c ["+Main.GetText("c141:sk0")+ "] : " + spe.getName()+" (" +spt+")");
		}
		if(sk2tt > 0) {
			sk2tt-=1;
			if(sk2tt <= 0) {
				sk2e = null;
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getEntity() == spe && BlockUtil.isAirbone(e.getEntity().getLocation(), 1)) {
				if(((LivingEntity)e.getEntity()).getNoDamageTicks() <= 0) spt++;
			} else {
				spe = (LivingEntity)e.getEntity();
				spt = 1;
			}
		} else {
			
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			sk2 = sk2max;
		}
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(0.75f,player);
			target.setVelocity(new Vector(0,0.1,0));
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			if(Rule.buffmanager.OnBuffTime(player, "stun")) {
				Rule.buffmanager.selectBuffTime(player, "stun",0);
			}
			delay(()->{
				target.setVelocity(player.getLocation().getDirection().multiply(2).setY(0));
			},0);
		}
		if(n.equals("3")) {
			sk2e = target;
			ARSystem.giveBuff(target, new Stun(target), 30);
			ARSystem.giveBuff(target, new Noattack(target), 6);
			sk1 = 0;
			if(spe == target && spt >= 12 && skillCooldown(0)) {

				Rule.playerinfo.get(player).tropy(141, 1);
				ARSystem.playSound((Entity)player, "c141sp");
				ARSystem.playSound((Entity)player, "c141sp1");
				spt = 0;
				spskillon();
				spskillen();
				cooldown[1] = cooldown[2] = cooldown[3] = cooldown[4] = 0;
				skillmult += 2;
				delay(()->{
					skillmult -=2;
				},160);
				if(sk2max < sk2) sk2 = sk2max;
				sk2max+=1;
				Location l = player.getLocation();
				l.setPitch(0);
				player.teleport(l);
				ARSystem.giveBuff(player, new Stun(player), 40);
				ARSystem.giveBuff(player, new Nodamage(player), 60);
				ARSystem.giveBuff(target, new Stun(target), 35);
				ARSystem.giveBuff(target, new Silence(target), 60);
				target.teleport(ULocal.lookAt(ULocal.offset(player.getLocation().clone(), new Vector(1,0,0)), player.getLocation()));
				for(int i=0; i<8; i++) {
					int o = i;
					delay(()->{
						skill("c141_sp1");
						ARSystem.playSound((Entity)player, "0slash5", 0.5f, 1.2f);
					},i*2);
				}
				delay(()->{
					skill("c141_sp");
				},10);
				delay(()->{
					ARSystem.playSound((Entity)player, "c141sp2");
				},30);
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(6,player);
					if(Rule.buffmanager.OnBuffTime(player, "stun")) {
						Rule.buffmanager.selectBuffTime(player, "stun",0);
					}
					delay(()->{
						target.setVelocity(player.getLocation().getDirection().multiply(3));
					},0);
					ARSystem.playSound((Entity)player, "c141sp3", 0.5f, 2);
					spt = 0;
					delay(()->{
						if(target.getHealth() / target.getMaxHealth() <= 0.33f) {
							Skill.remove(target, player);
						}
						spt = 0;
					},20);
				},40);
			} else {
				ARSystem.spellCast(player, sk2e, "c141_s2-2");
				target.setNoDamageTicks(0);
				target.damage(1,player);
			}
		}
		if(n.equals("4")) {
			ARSystem.giveBuff(target, new Silence(target), 120);
			target.setNoDamageTicks(0);
			target.damage(4,player);
			target.setVelocity(new Vector(0,1.35f,0));
		}
		if(n.equals("5")) {
			target.setNoDamageTicks(0);
			target.damage(3,player);
			target.setVelocity(new Vector(0,-2,0));
		}
	}
	
}
