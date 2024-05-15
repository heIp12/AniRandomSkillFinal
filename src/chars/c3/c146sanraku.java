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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
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
import buff.Ice;
import buff.NoCC;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Nodie;
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
import event.FixedDealEvent;
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

public class c146sanraku extends c00main{
	int nodamage = 0;
	int stack = 0;
	
	int sk3 = 0;
	int sk4 = 0;
	
	int s = 0;
	int st = 0;
	int sk43 = 0;
	
	int p = 0;
	float s2c = 0;
	
	Location s2loc;
	
	TargetMap<LivingEntity, Double> target = new TargetMap<>();
	TargetMap<LivingEntity, Double> ps = new TargetMap<>();
	
	public c146sanraku(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 146;
		load();
		text();
		c = this;
		setStack(0);
		delay(()->{
			ARSystem.playSound(player, "c146rhi"+AMath.random(2));
		},100);
	}

	@Override
	public void setStack(float f) {
		if(f < 0) f = 0;
		stack = (int)f;
		player.setWalkSpeed(Math.min(0.25f + (f*0.02f),1));
		if(0.25f+ f*0.02f >= 1) Rule.playerinfo.get(player).tropy(146, 1);
		skillmult = 1+stack*0.05;
		Rule.buffmanager.selectBuffValue(player, "buffac",2f + stack*0.3f);
		
		if(isps) {
			skillmult = 6;
			player.setHealth(1);
			player.setMaxHealth(1);
		}
		
		if(!isps && stack >= 20) {
			ARSystem.giveBuff(player, new Nodie(player), 60);
			spskillon();
			spskillen();
			ARSystem.playSound(player, "c146sp");
			ARSystem.playSound((Entity)player, "c146sp");
			nodamage = 60;
			skillmult = 6;
			player.setHealth(1);
			player.setMaxHealth(1);
			cooldown[3] = cooldown[4] = 0;
		}
	}
	
	
	@Override
	public boolean skill1() {
		s = 1;
		st = 6;
		return true;
	}

	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c146s2");
		s2loc = player.getLocation();
		player.setVelocity(player.getLocation().getDirection().multiply(1.4f + (stack*0.05f)));
		s2c = setcooldown[2];
		delay(()->{
			s = 2;
			st = 8;
		},1);
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c146s3");
		sk3 = 8;
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c146s4");
		sk4 = 10;
		return true;
	}
	
	@Override
	public boolean skill5() {
		int rad = AMath.random(4);
		Location lc = player.getLocation();
		lc.setPitch(0);
		lc.setYaw(lc.getYaw()+180);
		ARSystem.spellLocCast(player, ULocal.offset(lc, new Vector(-2,0.1,0)), "c146r-1");
		if(rad == 1) {
			delay(()->{skill("c146r1");},1);
			ARSystem.playSound((Entity)player, "c146ris1");
		}
		else if(rad == 2) {
			delay(()->{skill("c146r2");},1);
			ARSystem.playSound((Entity)player, "c146ris2");
		}
		else if(rad == 3) {
			ARSystem.playSound((Entity)player, "c146ris3");
		}
		else if(rad == 4) {
			delay(()->{skill("c146r3");},1);
			ARSystem.playSound((Entity)player, "c146ris4");
		}
		return true;
	}

	@Override
	public boolean firsttick() {
		if(inGame) {
			if(player.getOpenInventory().getType() != InventoryType.CHEST) {
				if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
					for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
						if(buff.getTime() > 100 && rb <= 0) {
							rbhp();
						}
					}
				}
			}
		}
		return false;
	}
	boolean isSkill() {
		int count = 0;
		for(Entity e : ARSystem.box(player, new Vector(8, 6, 8), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			if(Rule.c.get(e) != null) {
				count++;
			}
		}
		if(count >= 3) return true;
		if(AMath.random(10) <= 5) return false;
		if(player.getOpenInventory().getType() == InventoryType.CHEST) return false;
		if(nodamage+sk43+sk4+sk3 > 0) return false;
		if(Rule.buffmanager.selectBuffType(player, BuffType.SILENCE).size() > 0 && p > 0) return true;
		if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC).size() > 0 && p > 0) return true;
		if(cooldown[3] <= 0 || p <= 0 || cooldown[4] <= 0) return false;
		if(ARSystem.box(player, new Vector(8,8,8), box.TARGET).size() <= 0) return false;
		return true;
	}
	
	void rbhp() {
		if(player.getHealth() > 1) {
			ARSystem.giveBuff(player, new Nodie(player), 5);
			rb = 1200;
			ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(), new Vector(0,0,0)), "c146r-1");
			delay(()->{skill("c146sk");},1);
			ARSystem.playSound((Entity)player, "c146rtp");
			Location lc = player.getLocation();
			Location tpl = Map.randomLoc(player);
			int rd = 0;
			while(tpl.distance(lc) > 15+rd*(0.01) || !Map.inMap(tpl.clone().add(0,20 - rd*0.007,0)) || !BlockUtil.isAirbone(tpl.clone().add(0,20- rd*0.007,0), 2)) {
				tpl = Map.randomLoc(player);
				rd++;
				if(rd >= 3000) rd = 0;
			}
			tpl.setPitch(80);
			player.teleport(tpl.clone().add(0,10,0));
			ARSystem.giveBuff(player, new Airborne(player), (int) (Rule.buffmanager.GetBuffValue(player, "buffac")*40));
			cooldown[2] = cooldown[3] = cooldown[4] = 0;
			nodamage = 50;
			ARSystem.playSound(player, "c146rtp");
			delay(()->{
				for(Buff b : Rule.buffmanager.selectBuffType(player, BuffType.SILENCE)) {
					b.setTime(0);
				}
				for(Buff b : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
					b.setTime(0);
				}
			},1);
		}
	}
	
	float lastcr = 3;
	int rb = 80;
	@Override
	public boolean tick() {
		if(isBattle() && AMath.random(600) <= 1 && rb <= 0) {
			rb = 100;
			Location lc = player.getLocation();
			lc.setPitch(0);
			ARSystem.spellLocCast(player, ULocal.offset(lc, new Vector(0,0.1,1.5)), "c146r-1");
			
			if(AMath.random(100) > 10) {
				ARSystem.playSound((Entity)player, "c146rsk1");
				delay(()->{
					skill("c146sk");
					skill("c146sk1");
				},1);
			} else {
				rb = 500;
				ARSystem.playSound((Entity)player, "c146rsk2");
				delay(()->{
					skill("c146sk");
					skill("c146sk2");
				},1);
			}
		}
		if(rb <= 0) {
			if(isSkill()) {
				rbhp();
			}
		}
		if(maptick > 3) {
			player.teleport(Map.randomLoc());
			ARSystem.playSound((Entity)player, "c146rgate");

			Location lc = player.getLocation();
			lc.setPitch(0);
			lc = ULocal.offset(lc,new Vector(0,0.1,1.5));
			lc.setYaw(lc.getYaw()-90);
			ARSystem.spellLocCast(player, lc, "c146r-1");
			ARSystem.potion(player, 9, 200, 2);
			delay(()->{skill("c146sk");},1);
		}
		
		if(st > 0) {
			st--;
			if(st <= 0 && s == 1) {
				ARSystem.playSound((Entity)player, "c146s1");
				skill("c146_s1");
				for(Entity en : ARSystem.PlayerBeamBox(player, 5, 3, box.TARGET)) {
					LivingEntity ee = (LivingEntity)en;
					damage(ee, 1, 20);
				}
			}
		}
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c146:ps")+"] : " + stack);

			if(!isps) scoreBoardText.add("&c ["+Main.GetText("c146:t1")+"] : " + (20+stack)*10+"%");
			else scoreBoardText.add("&c ["+Main.GetText("c146:t1")+"] : " + AMath.round(lastcr*100,0)+"%");
		}
		if(sk43 > 0) {
			sk43--;
			player.sendTitle("§f§l《 ☇ 》", "§a§l"+ AMath.round(nodamage*0.05, 2),0,4,0);
		}
		else if(nodamage > 0) {
			nodamage--;
			player.sendTitle("§e§l《 ☇ 》", "§a§l"+ AMath.round(nodamage*0.05, 2),0,4,0);
		}
		else if(sk4 > 0) {
			sk4--;
			player.sendTitle("§4§l《⚔》", "§a§l"+ AMath.round(sk4*0.05, 2),0,4,0);
		}
		else if(sk3 > 0) {
			player.sendTitle("§c§l《⚔》", "§a§l"+ AMath.round(sk3*0.05, 2),0,4,0);
			sk3--;
		}
		if(p > 0) {
			scoreBoardText.add("&c ["+Main.GetText("c146:t2")+"] : " + AMath.round(p *0.05, 2));
			p--;
		}
		if(battleTime > 800 && !isps) {
			battleTime = 600;
			setStack(stack-1);
		}
		if(rb > 0) rb--;
		if(BlockUtil.isAirbone(player.getLocation(), 2)) {
			if(s2c > 0.1) cooldown[2] = s2c;
		} else {
			s2c = cooldown[2];
		}
		
		if(tk%2 == 0) {
			for(Entity e : ARSystem.box(player, new Vector(8,5,8), box.TARGET)) {
				LivingEntity en = (LivingEntity) e;
				if(!bph(en)) {
					if(ps.get(en) == null || ps.get(en) <= 0) {
						ps.add(en, 60);
						ARSystem.giveBuff(en, new Fascination(en,player), 120,-0.2);
					}
				}
			}
		}
		for(LivingEntity db : ps.get().keySet()) {
			ps.add(db, -0.05);
			if(ps.get(db) <= 0) {
				ps.removeAdd(db);
			}
		}
		if(rbt > 0) rbt--;
		target.addAll(-0.05f);
		target.removes();
		return true;
	}
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(sk43 > 0) {
			player.getInventory().setHeldItemSlot(7);
			return true;
		}
		int key = e.getNewSlot()+1;
		if(st > 0 && key < 5 && key > 0) {
			st = 0;
			if(s == 1) {
				if(key == 1) {
					cooldown[1] = setcooldown[1]*2;
					ARSystem.playSound((Entity)player, "c146s11");
					Location lc = player.getLocation();
					for(int i = 0; i < 10; i++) {
						delay(()->{
							player.teleport(lc);
						},i);
						if(i%2 == 1) delay(()->{
							skill("c146_s1_1");
							for(Entity en : ARSystem.PlayerBeamBox(player, 5, 3, box.TARGET)) {
								LivingEntity ee = (LivingEntity)en;
								damage(ee, 0.4f, 20);
							}
						},i);
					}
				}
				if(key == 2) {
					cooldown[1] = setcooldown[1]*8;
					ARSystem.playSound((Entity)player, "c146s12");
					List<Entity> nc = new ArrayList<Entity>();
					Location lc = player.getLocation();
					for(int i =0; i < 12; i++) {
						delay(()->{
							ARSystem.spellLocCast(player, player.getLocation().clone().add(lc.getDirection().multiply(1.5)), "c146_s1_2");
							player.setVelocity(lc.getDirection().multiply(1.2 + stack*0.04));
							for(Entity en : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
								if(!nc.contains(en)) {
									nc.add(en);
									LivingEntity ee = (LivingEntity)en;
									damage(ee, 2, 40);
								}
							}
						},i);
						delay(()->{
							player.setVelocity(new Vector(0,0.2,0));
						},20);
					}
				}
				if(key == 3 && isps) {
					ARSystem.playSound((Entity)player, "c146s13");
					cooldown[1] = setcooldown[1] * 25;
					skill("c146_s1_3");
					
				}
			}
			if(s == 2) {
				Location lc = s2loc;
				if(key == 1) {
					ARSystem.playSound((Entity)player, "c146s21");
					skill("c146_s2_1");
					for(Entity en : ARSystem.box(player, new Vector(3,2,3), box.TARGET)) {
						LivingEntity ee = (LivingEntity)en;
						damage(ee, 1, 20);
					}
					lc.setYaw(lc.getYaw()-90);
				}
				if(key == 2) {
					ARSystem.playSound((Entity)player, "c146s21");
					skill("c146_s2_2");
					for(Entity en : ARSystem.box(player, new Vector(3,2,3), box.TARGET)) {
						LivingEntity ee = (LivingEntity)en;
						damage(ee, 1, 20);
					}
					lc.setYaw(lc.getYaw()+90);
				}
				if(key == 3) {
					ARSystem.playSound((Entity)player, "c146s23");
					lc.setYaw(lc.getYaw()+180);
				}
				lc.setPitch(0);
				Location lcc = player.getLocation();
				lcc.setPitch(lc.getPitch());
				lcc.setYaw(lc.getYaw());
				player.teleport(lcc);
				player.setVelocity(lc.getDirection().multiply(1.9f + (stack*0.03f)));
			}
			if(s == 4) {
				if(key == 1) {
					ARSystem.playSound((Entity)player, "c146s41");
					skill("c146_s4_1");
					skill("c146_s4_2");
					for(Entity en : ARSystem.PlayerBeamBox(player, 5, 3, box.TARGET)) {
						LivingEntity ee = (LivingEntity)en;
						damage(ee, 4, 500);
					}
				}
				if(key == 2) {
					ARSystem.playSound((Entity)player, "c146s42");
					cooldown[1] = cooldown[2] = cooldown[3] = 0;
					nodamage = 20;
					player.setVelocity(player.getLocation().getDirection().multiply(-(2 + Math.min(stack*0.2f,5f))));
				}
				if(key == 3) {
					ARSystem.playSound((Entity)player, "c146s43");
					sk43 = 10;
				}
			}
			
			player.getInventory().setHeldItemSlot(7);
			return true;
		}
		return super.key(e);
	}

	int rbt = 0;
	LivingEntity rba = null;
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			damage(target, 5, 500);
		}
		if(n.equals("2")) {
			rbt = 60;
			rba = target;
			target.setNoDamageTicks(0);
			target.damage(5,player);
		}
		if(n.equals("3")) {
			rbt = 60;
			rba = target;
			target.setNoDamageTicks(0);
			target.damage(20,player);
		}
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(isps && sk4 > 0) {
			ARSystem.playSound((Entity)player, "0swordgard", 0.8f);
			nodamage = 16;
			if(isps) nodamage*=0.6;
			st = 10;
			sk4 = 0;
			s = 4;
			this.target.add((LivingEntity)caster, 100);
			setStack(stack+2);
			return false;
		}
		rm = true;
		Location lc = player.getLocation();
		ARSystem.playSound((Entity)player, "c146quit");
		ARSystem.playSound(player, "c146quit");
		tpsdelay(()->{
			ARSystem.playSound(player, "c146rquit");
		},100);
		return super.remove(caster);
	}
	
	@Override
	public boolean fixeddamage(FixedDealEvent e) {
		e.setCancelled(true);
		player.setNoDamageTicks(0);
		player.damage(e.getDamage(),e.getCaster());
		
		return false;
	}
	
	boolean isback(Location target, Location attaker,float size) {
		Location lc = target.clone();
		Location plc = attaker.clone();
		lc.setPitch(0);
		plc.setPitch(0);
		float targetFaceAngle = lc.clone().getDirection().angle(new Vector(1, 1, 1));
		float diffAngle = lc.toVector().subtract(plc.toVector()).angle(new Vector(1, 1, 1));
		float diff = Math.abs(targetFaceAngle - diffAngle);
		if(diff <= size) {
			return true;
		}
		return false;
	}
	
	void damage(LivingEntity e, float damage,int crt) {
		if(isps) Rule.buffmanager.selectBuffTime(e, "nodamage", 0);
		
		int rt = crt;
		if(isps) rt = crt + (int)(e.getMaxHealth() - player.getMaxHealth())*2;
		if(isback(e.getLocation(), player.getLocation(), 0.5f)) rt *= 3;
		
		if(AMath.random(100) <= rt) {
			Holo.create(e.getLocation().clone().add(AMath.random(20)*0.1-1,AMath.random(20)*0.1-1,AMath.random(20)*0.1-1), "§4§l§nCritical!",30, new Vector(0, 0, 0));
			ARSystem.heal(player, 0.5f);
			if(isps) {
				lastcr = (3+ (int)(e.getMaxHealth() - player.getMaxHealth())*0.3f);
				damage *= lastcr;
				if(ARSystem.isGameMode("lobotomy") && damage > e.getMaxHealth()-2) {
					damage = (float)(e.getMaxHealth() - 2);
				}
			} else {
				damage *= (2+stack*0.1);
			}
			if(bph(e)) setStack(stack+1);
		}
		e.setNoDamageTicks(0);
		e.damage(damage, player);
	}
	
	boolean bph(LivingEntity target) {
		if(this.target.get().get(target) != null || target.getHealth() >= stack) {
			this.target.add(target, 10f);
			return true;
		}
		return false;
	}
	
	boolean rm = false;
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player && bph(p)) {
			int r = AMath.random(6);
			ARSystem.playSound(player, "c146rkill"+r);
			ARSystem.playSound((Entity)player, "c146rkill"+r);
		} else if(p == player && !rm) {
			ARSystem.playSound(player, "c146rdeath");
			ARSystem.playSound((Entity)player, "c146rdeath");
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(sk43 > 0) {
				e.setDamage(0);
				e.setCancelled(true);
				return true;
			}
			LivingEntity target = (LivingEntity)e.getEntity();
			if(!bph(target)) {
				if(AMath.random(100) < 30 + Math.min(70,(stack -target.getHealth())*5)) setStack(stack - 1);
				e.setDamage(e.getDamage() * 0.1f);
			}
			if(e.getEntity() == rba && rbt > 0 && rba.getHealth() - e.getDamage() < 1) {
				rba = null;
				rbt = 0;
				ARSystem.playSound(player, "c146rdoya");
				ARSystem.playSound((Entity)player, "c146rdoya");
			}
		} else {
			if(sk43 > 0) {
				sk43 = 14;
				ARSystem.playSound((Entity)player, "0swordgard", 2f,0.2f);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(nodamage > 0) {
				ARSystem.playSound((Entity)player, "0swordgard", 2f,0.4f);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			else if(sk4 > 0) {
				ARSystem.playSound((Entity)player, "0swordgard", 0.8f);
				nodamage = 16;
				if(isps) nodamage*=0.6;
				st = 10;
				sk4 = 0;
				s = 4;
				this.target.add((LivingEntity)e.getDamager(), 10);
				setStack(stack+1);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			} else if(sk3 > 0 && e.getDamage() <= 15) {
				ARSystem.playSound((Entity)player, "0swordgard", 1.4f);
				if(isps) {
					e.setDamage(0);
					e.setCancelled(true);
				}
				else e.setDamage(e.getDamage()*0.05f);
				nodamage = 8;
				if(isps) nodamage*=0.6;
				cooldown[3] = 0;
				sk3 = 0;
				if(stack == 19 && !isps) {
					e.setDamage(0);
					e.setCancelled(true);
				}
				setStack(stack+1);
				for(int i =0; i<10;i++) if(cooldown[i] > 0) cooldown[i] -=0.2;
				
			}
			if(!isps && p <= 0 && e.getDamage() <= 12 && player.getHealth() - e.getDamage() < 1f) {
				ARSystem.playSound((Entity)player, "0swordgard", 0.5f);
				player.setHealth(1.5f);
				p = 160;
				nodamage = 10;
				if(isps) nodamage*=0.6;
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			
			if(e.getDamage() >= 50 && e.getDamager().getLocation().distance(player.getLocation()) >= 50 && rb <= 0) {
				rbhp();
				nodamage = 10;
				if(isps) nodamage*=0.6;
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return true;
	}
}
