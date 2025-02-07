package chars.ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Exposure;
import buff.MapVoid;
import buff.NoCC;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.Bgm;
import types.BuffType;
import types.box;

import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class h002claw extends c00main{
	int s1 = 0;
	int sk2 = 0;
	boolean s1cast = false;
	List<LivingEntity> en;
	List<LivingEntity> target = new ArrayList<LivingEntity>();
	int nodamage = 0;
	int cs = 0;
	public h002claw(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 998;
		load();
		text();
		if(p != null) ARSystem.playSound((Entity)player, "clawselect");
	}
	
	int skill1 = 0;
	int skill1_tick = 0;
	
	@Override
	public boolean skill1() {
		if(s1cast) {
			cooldown[1] = 0;
			return false;
		}
		s1 = 0;
		Entity en = null;
		try {
			en = ARSystem.PlayerBeamBox(player, 10, 2, box.TARGET).get(0);
		} catch (Exception e) {}
		if(en == null) {
			cooldown[1] = 0;
		} else {
			s1cast = true;
			s1(en);
		}
		return true;
	}
	
	public void s1(Entity e) {
		if(e == null) {
			s1cast = false;
			return;
		}
		ARSystem.giveBuff(player, new Exposure(player), 20 , -5);
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.giveBuff(player, new Silence(player), 20);
		Location f = player.getLocation().clone();
		player.teleport(e.getLocation().clone().add(ULocal.lookAt(f.clone(), e.getLocation()).getDirection()));
		ARSystem.spellLocCast(player, f, "claw_s1e");
		s1++;
		List<Entity> nexts = ARSystem.boxS(player, new Vector(10,6,10), box.TARGET);
		Entity next = null;
		for(Entity ey : nexts) {
			if(ey != e) {
				next = ey;
			}
		}
		
		LivingEntity en = (LivingEntity)e;
		skill("claw_s1"+(s1%2+1));
		if(next == null) {
			ARSystem.playSound(en, "claws13");
			ARSystem.spellCast(player, en, "bload");
			en.setNoDamageTicks(0);
			en.damage(10,player);
		} else {
			ARSystem.playSound(en, "claws1"+AMath.random(2));
			en.setNoDamageTicks(0);
			en.damage(6,player);
			s1cast = false;
		}
		Entity n = next;
		delay(()->{
			s1(n);
			cooldown[1] += 0.4*(skillmult+sskillmult);
		},10);
	}
	
	@Override
	public boolean skill2() {
		if(s1cast) {
			cooldown[2] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "clawsk");
		ARSystem.giveBuff(player, new Stun(player), 20);
		delay(()->{ARSystem.playSound((Entity)player, "claws2");},10);
		delay(()->{sk2 = 120;},20);
		
		en = new ArrayList<>();
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(s1cast) {
			cooldown[3] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "clawsk");
		ARSystem.giveBuff(player, new Stun(player), 100);
		
		invskill = new InvSkill(player) {
			
			@Override
			public void Start(String st) {
				player.closeInventory();
				if(Rule.c.get(Bukkit.getPlayer(st)) != null) {
					target.add(Bukkit.getPlayer(st));
				}
			}
		};
		Set<Player> Player = ((HashMap<Player, c00main>) Rule.c.clone()).keySet();
		List<Entity> removes = new ArrayList<Entity>();
		Player.remove(player);
		for(LivingEntity p : target) {
			if(Rule.c.get(p) == null) {
				removes.add(p);
			}
			Player.remove(p);
		}
		for(Entity e : removes) target.remove(e);
		Inventory.getlist(invskill,player,Player);
		
		if(Player.size() <= 0 && target.size() > 2 && getScore() >= 500 && player.getHealth() <= 10 && skillCooldown(0)) {
			sp();
		} else {
			Location loc = player.getLocation();
			loc.setPitch(25);
			player.teleport(loc);
			skill("claw_sp_ia1");
			
			invskill.openInventory(player);
			
			delay(()-> {
				ARSystem.giveBuff(player, new Exposure(player), 20 , -3);
				if(player.getOpenInventory().getTitle().equals(invskill.inventory.getTitle())) {
					player.closeInventory();
				}
				int i = 0;
				ARSystem.giveBuff(player, new Stun(player), 30* target.size());
				ARSystem.giveBuff(player, new Silence(player), 30* target.size());
				for(LivingEntity e : target) {
					int n = i;
					delay(()->{
						ARSystem.playSound((Entity)player, "clawsp1");
						ARSystem.spellCast((Player)e, "clawtarget");
						delay(()->{
							Location f = player.getLocation().clone();
							Location lc = e.getLocation().clone().add(ULocal.lookAt(f.clone(), e.getLocation()).getDirection());
							lc.setYaw(ULocal.lookAt(f.clone(), e.getLocation()).getYaw());
							lc.setPitch(ULocal.lookAt(f.clone(), e.getLocation()).getPitch());
							player.teleport(lc);
							ARSystem.spellLocCast(player, f, "claw_s1e");
							ARSystem.playSound((Entity)player, "clawtp2");
	
							LivingEntity en = (LivingEntity)e;
							skill("claw_s1"+(s1%2+1));
							
							ARSystem.playSound(en, "claws13");
							ARSystem.spellCast(player, e, "claw_s2");
							ARSystem.spellCast(player, en, "bload");
							MSUtil.buffoff((Player)e, "clawtarget");
							if(n == target.size()) {
								en.setNoDamageTicks(0);
								en.damage(10,player);
							} else {
								en.setNoDamageTicks(0);
								en.damage(3,player);
							}
						},10*target.size());
					},i*10);
					i++;
				}
			},80);
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(s1cast) {
			cooldown[4] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "clawsk");
		for(int i =0; i<60; i++) {
			delay(()->{
				ARSystem.giveBuff(player, new Stun(player), 10);
				ARSystem.giveBuff(player, new Silence(player), 10);
				ARSystem.heal(player, 1);
			},i);
		}
		delay(()->{
			ARSystem.playSound((Entity)player, "claws4");
			Rule.buffmanager.getBuffs(player).buffClear();
			ARSystem.giveBuff(player, new NoCC(player), 200);
		},60);
		return true;
	}
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(e.getNewSlot() == 5 && skillCooldown(4)) skill4();
		return super.key(e);
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			String s = Bgm.bgmcode;
			if(!s.equals("bc998") && Bgm.rep) {
				Bgm.setlockBgm("c998");
			}
		}
		if(sk2 > 0) {
			Location loc = player.getLocation();
			loc.setPitch(0);
			loc = loc.add(player.getLocation().getDirection().clone().multiply(2));
			if(!loc.clone().add(0,1,0).getBlock().isEmpty()) {
				sk2 = 0;
			} else {
				sk2--;
				player.setVelocity(player.getLocation().getDirection().setY(0.001).multiply(2));
				for(Entity e : ARSystem.box(player, new Vector(6,6,6), box.TARGET)) {
					if(!en.contains(e)) {
						en.add((LivingEntity) e);
						ARSystem.playSound(e, "claws12");
						ARSystem.spellCast(player, e, "claw_s2");
						ARSystem.spellCast(player, e, "bload");
						((LivingEntity)e).setNoDamageTicks(0);
						((LivingEntity)e).damage(10,player);
					}
				}
			}
		}
		if(nodamage > 0) nodamage--;
		if(cs > 0) cs--;
		return true;
	}

	void sp(){
		target = new ArrayList<>();
		spskillen();
		spskillon();
		ARSystem.heal(player, 10000);
		ARSystem.giveBuff(player, new MapVoid(player), 600);
		Location loc = player.getLocation();
		loc.setPitch(25);
		player.teleport(loc);
		skill("claw_sp_ia1");
		int time = Rule.c.size();
		
		ARSystem.addBuff(player, new TimeStop(player), 20+ time*4);
		delay(()->{
			int i = 0;
			for(Player p : Rule.c.keySet()) {
				if(p == player) continue;
				i++;
				delay(()->{
					ARSystem.giveBuff(p, new Stun(p), 400);
					ARSystem.playSound((Entity)p, "clawsp1");
					ARSystem.spellCast(p, "clawtarget");
				},i*4);
			}
		},20);
		delay(()->{
			ARSystem.addBuff(player, new Silence(player), 200);
			ARSystem.addBuff(player, new Nodamage(player), 200);
			int i = 0;
			for(Player p : Rule.c.keySet()) {
				if(p == player) continue;
				i++;
				delay(()->{
					for(LivingEntity e : target) {
						e.teleport(p);
						ARSystem.addBuff(e, new Silence(e), 20);
						ARSystem.addBuff(e, new Noattack(e), 20);
					}
					MSUtil.buffoff(p, "clawtarget");
					Location f = player.getLocation().clone();
					player.teleport(p);
					target.add(p);
					p.showPlayer(player);
					if(AMath.random(5) <= 3) player.hidePlayer(p);
					for(Player pl : Rule.c.keySet()) {
						if(pl != player) {
							p.hidePlayer(pl);
						}
					}
					ARSystem.playSound((Entity)p, "clawtp1");
					ARSystem.spellLocCast(player, f, "claw_s1e");
				},4*i);
			}
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.ALL)) {
					if(!target.contains(e)) {
						target.add((LivingEntity) e);
					}
				}
				for(int j =0; j<20;j++) {
					delay(()->{
						Map.Random(0,-1);
						Location locl = Map.randomLoc();
						player.teleport(locl);
						for(LivingEntity e : target) {
							e.teleport(locl.clone().add(new Vector(1.5-(AMath.random(30)*0.1),0,1.5-(AMath.random(30)*0.1))));
							ARSystem.addBuff(e, new Silence(e), 20);
							ARSystem.addBuff(e, new Noattack(e), 20);
							delay(()->{
								ARSystem.playSoundAll("clawspe",1);
							},5);
						}
						
						ARSystem.playSoundAll("clawtp2",1);
						delay(()->{
							skill("claw_sp2");
						},5);
					},j*10);
				}
				int maps = Map.lastplay;
				if(ARSystem.isGameMode("lobotomy")) maps = 1004;
				int map = maps;
				delay(()->{
					Map.getMapinfo(map);
					Location locl = Map.randomLoc();
					
					player.teleport(locl);
					skill("claw_sp2");
					ARSystem.playSoundAll("clawspe2",1);

					for(LivingEntity e : target) {
						if(e != player) {
							
							e.teleport(locl.clone().add(new Vector(1.5-(AMath.random(30)*0.1),0,1.5-(AMath.random(30)*0.1))));
							if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
								for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
									buff.setTime(0);
								}
							}
							if(Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
								for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
									buff.setTime(0);
								}
							}
							delay(()->{
								
								if(Rule.buffmanager.selectBuffType(e, BuffType.HEADCC) != null) {
									for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
										buff.setTime(0);
									}
								}
								if(Rule.buffmanager.selectBuffType(e, BuffType.CC) != null) {
									for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
										buff.setTime(0);
									}
								}
								e.setNoDamageTicks(0);
								e.damage(999,player);
							},5);
						}
					}
				},206);
			},4*time);
		},60+ time*4);
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
			
		} else {
			if(nodamage > 0) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			} else {
				nodamage = 10;
			}
			if(Rule.c.get(e.getDamager()) != null) {
				int n = Rule.c.get(e.getDamager()).number%10000;
				if(n == 1139 || n == 2139 || n%1000 == 86 || n < 900) {
					e.setDamage(e.getDamage() * 0.7);
				} else if(n > 1000) {
					e.setDamage(e.getDamage() * 0.4);
				}
			} else {
				LivingEntity en = (LivingEntity)e.getDamager();
				if(en.getHealth() < 100) {
					e.setDamage(e.getDamage()* 0.7);
				} else if(en.getHealth() < 300) {
					e.setDamage(e.getDamage()* 0.4);
				}
			}
			if(e.getDamage() > 30 && cs <= 0) {
				cs = 400;
				ARSystem.playSound((Entity)player, "clawp");
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
