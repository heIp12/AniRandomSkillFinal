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
import ars.gui.G_Saito;
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

public class c142miyaco extends c00main{
	int p = 0;
	boolean ps = false;
	boolean pa = false;
	int s1 = 0;
	Location lc = player.getLocation().clone();
	
	int bh = 0;
	int bhc = 0;
	
	List<Entity> splist;
	
	public c142miyaco(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 142;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		if(bh > 0 && player.isSneaking() && skillCooldown(0)) {
			spskillon();
			spskillen();
			s_score -= 300;
			splist = new ArrayList<Entity>();
			ARSystem.playSound((Entity)player, "c142sp");
			ARSystem.giveBuff(player, new TimeStop(player), 20);
			delay(()->{
				s1 = 30010;
			},20);
			lc = player.getLocation().clone();
			return false;
		}
		if(ps) {
			ARSystem.playSound((Entity)player, "c142s11");
			ps = false;
			s1 = 3006;
			lc = player.getLocation().clone();
			if(lc.getPitch() < -45) lc.setPitch(-45);
		} else {
			s1 = 5;
			ARSystem.playSound((Entity)player, "c142s1");
			lc = player.getLocation().clone();
			if(lc.getPitch() < -15) lc.setPitch(-15);
		}
		pa = true;
		return true;
	}

	@Override
	public boolean skill2() {
		if(!player.isOnGround()) {
			cooldown[2] = 0;
			return false;
		}
		pa = true;
		if(ps) {
			ps = false;
			ARSystem.playSound((Entity)player, "c142s22");
			delay(()->{
				skill("c142_s2-2");
				ARSystem.playSound((Entity)player, "0explod" , 0.8f, 1);
			},10);
			ARSystem.giveBuff(player, new Stun(player), 20);
			ARSystem.giveBuff(player, new Silence(player), 20);
			for(Entity e : ARSystem.box(player, new Vector(8,3,8), box.TARGET)){
				LivingEntity en = (LivingEntity)e;
				ARSystem.giveBuff(en, new Stun(en), 16);
				ARSystem.giveBuff(en, new Silence(en), 80);
				delay(()->{
					en.setNoDamageTicks(0);	
					en.damage(6,player);
					en.setVelocity(new Vector(0,2.4f,0));
					cooldown[1] = 0.1f;
					delay(()->{
						en.setNoDamageTicks(0);
					},0);
				},20);
			}
		} else {
			ARSystem.playSound((Entity)player, "c142s2");
			delay(()->{
				skill("c142_s2");
				ARSystem.playSound((Entity)player, "0attack4" , 0.8f, 2);
			},10);
			ARSystem.giveBuff(player, new Stun(player), 10);
			ARSystem.giveBuff(player, new Silence(player), 10);
			for(Entity e : ARSystem.box(player, new Vector(6,2,6), box.TARGET)){
				LivingEntity en = (LivingEntity)e;
				delay(()->{
					en.setNoDamageTicks(0);	
					en.damage(2,player);
					ARSystem.giveBuff(en, new Silence(en), 20);
					en.setVelocity(new Vector(0,1.45f,0));
					cooldown[1] = 0.1f;
					delay(()->{
						en.setNoDamageTicks(0);
					},0);
				},10);
			}
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		pa = true;
		ARSystem.giveBuff(player, new Silence(player), 14);
		Location loc = player.getLocation();
		loc.setPitch(-75);
		player.setVelocity(loc.getDirection().multiply(0.45f));
		delay(()->{
			skill("c142_s3");
			ARSystem.playSound((Entity)player, "c142a2");
			player.setVelocity(loc.getDirection().multiply(0.45f));
		},2);
		delay(()->{
			skill("c142_s3");
			ARSystem.playSound((Entity)player, "c142a2");
			player.setVelocity(loc.getDirection().multiply(0.45f));
		},6);
		delay(()->{
			skill("c142_s3");
			delay(()->{ARSystem.playSound((Entity)player, "c142a3");},2);
			player.setVelocity(loc.getDirection().multiply(0.45f));
		},10);
		if(ps) {
			ps = false;
			delay(()->{
				ARSystem.playSound((Entity)player, "c142a4");
				player.setVelocity(new Vector(0,0.3f,0));
				skill("c142_s3-2");
				delay(()->{
					player.setVelocity(new Vector(0,-3,0));
				},2);
			},14);
		}
		return true;
	}
	int skt = 0;
	@Override
	public boolean tick() {
		if(tk%20 == 0  && skt <= 0 && AMath.random(10) <= 2) {
			for(Entity e : ARSystem.box(player, new Vector(12,8,12), box.ALL)) {
				if(Rule.c.get(e) != null && Rule.c.get(e).number%1000 == 42) {
					skt = 100;
					ARSystem.playSound((Entity)player, "c142sikidb");
					ARSystem.giveBuff(player, new Fascination(player, (LivingEntity)e), 60, 0.1f);
					break;
				}
			}
		}
		if(skt > 0) skt--;
		if(tk%5 == 0 && bh <= 0) {
			if((bhc <= 0 && (score-200) >= 200) || (bhc == 1 && (score-200) >= 700)  || (bhc == 2 && (score-200) >= 1500)) {
				bhc++;
				if(bhc == 3) {
					Rule.playerinfo.get(player).tropy(142, 1);
				}
				bh = 400;
				ARSystem.playSound((Entity)player, "c142a1");
				skill("c142_ps");
				cooldown[0] = 0;
			}
		}
		if(bh > 0) {
			bh--;
			if(bh%4 == 0) {
				ARSystem.heal(player, 0.2f);
				skill("c142_p");
			}
		}
		if(!ps) {
			p+= skillmult+sskillmult;
			if(p >= 160) {
				ps = true;
				p = 0;
			}
		}
		if(tk%20 == 0) {
			if(bh > 0) {
				scoreBoardText.add("&c ["+Main.GetText("c142:t1")+"] : " + AMath.round(bh*0.05,2));
			}
			if(ps) {
				scoreBoardText.add("&a ["+Main.GetText("c142:ps")+"]");
			} else {
				scoreBoardText.add("&c ["+Main.GetText("c142:ps")+"] : " + AMath.round(p*0.05,2) + " / 8");
			}
		}if(s1 > 10000) {
			s1--;
			if(s1%1000 >= 900) {
				s1 = 0;
			}
			player.setVelocity(lc.getDirection().multiply(3f));
			List<Entity> ens = ARSystem.box(player, new Vector(5,4,5), box.TARGET);
			if(ens.size() > 0) {
				ens = ARSystem.box(player, new Vector(8,6,8), box.TARGET);
				s1 = 0;
				skill("c142_s1-2");
				ARSystem.playSound((Entity)player, "0attack2");
				cooldown[1] = 0;
				for(Entity e : ens){
					LivingEntity en = (LivingEntity)e;
					Location loc = ULocal.lookAt(player.getLocation().clone(),e.getLocation());
					ARSystem.giveBuff(en, new Silence(en), 200);
					loc.setPitch(15);
					splist.add(en);
					for(int i =0; i< 100; i++) {
						int l = i;
						delay(()->{
							if(en instanceof Player && ((Player) en).getGameMode() == GameMode.SPECTATOR) {
								
							} else {
								en.setVelocity(loc.getDirection().multiply(3).setY(-0.12f));
								ARSystem.spellLocCast(player, en.getLocation(), "c142_sp");
								if(l%2 == 0) {
									for(Entity es : ARSystem.locEntity(en.getLocation(), new Vector(4,4,4), player)) {
										LivingEntity nn = (LivingEntity)es;
										if(!splist.contains(nn)) {
											splist.add(nn);
											ARSystem.giveBuff(nn, new Stun(nn), 400);
											nn.setNoDamageTicks(0);
											nn.damage(5,player);
											ARSystem.playSound((Entity)player, "0explod" , 0.8f,0.25f);
										}
									}
								}
							}
						},i*2);
					}
					delay(()->{
						en.setNoDamageTicks(0);
						en.damage(10,player);
						if(en instanceof Player && ((Player) en).getGameMode() != GameMode.SPECTATOR) {
							ARSystem.giveBuff(en, new Stun(en), 400);
						}
					},200);
					
					cooldown[1] = 0.1f;
					delay(()->{
						en.setNoDamageTicks(0);
					},0);
				}
			}
		} else if(s1 > 1000) {
			s1--;
			if(s1%1000 >= 900) {
				s1 = 0;
			}
			player.setVelocity(lc.getDirection().multiply(1.2f));
			List<Entity> ens = ARSystem.box(player, new Vector(4,4,4), box.TARGET);
			if(ens.size() > 0) {
				s1 = 0;
				skill("c142_s1-2");
				ARSystem.playSound((Entity)player, "0attack2");
				for(Entity e : ens){
					LivingEntity en = (LivingEntity)e;
					en.damage(3,player);
					en.setVelocity(player.getLocation().getDirection().multiply(2.2f));
					cooldown[1] = 0.1f;
					delay(()->{
						en.setNoDamageTicks(0);
					},0);
				}
			}
		} else if(s1 > 0) {
			s1--;
			player.setVelocity(lc.getDirection().multiply(0.75f));
			List<Entity> ens = ARSystem.box(player, new Vector(4,4,4), box.TARGET);
			if(ens.size() > 0) {
				s1 = 0;
				skill("c142_s1");
				ARSystem.playSound((Entity)player, "0attack");
				for(Entity e : ens){
					LivingEntity en = (LivingEntity)e;
					en.damage(2,player);
					en.setVelocity(player.getLocation().getDirection().multiply(0.8f));
					delay(()->{
						en.setNoDamageTicks(0);
					},0);
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() <= 1 && pa) {
				pa = false;
				LivingEntity en = (LivingEntity)e.getEntity();
				ARSystem.spellCast(player, en, "c142_tp");
				delay(()->{
					en.setNoDamageTicks(0);
					en.damage(1,player);
					ARSystem.spellCast(player, en, "c142_tp");
					if(cooldown[3] > 0) cooldown[3] -= 0.5f;
				},3);
			}
		} else {
			if(bh > 0) {
				e.setDamage(e.getDamage() * 0.67f);
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player && Rule.c.get(p) != null && Rule.c.get(p).number%1000 == 42) {
			ARSystem.playSound(player, "c142siki");
			ARSystem.playSound(p, "c142siki");
		}
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			target.setVelocity(new Vector(0,0.65f,0));
			cooldown[1] = 0.1f;
			delay(()->{
				target.setNoDamageTicks(0);
			},0);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(3,player);
			target.setVelocity(player.getLocation().getDirection().multiply(1.45f).setY(-3));
			cooldown[1] = 0.1f;
			delay(()->{
				target.setNoDamageTicks(0);
			},0);
		}
	}
	
}
