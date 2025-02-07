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
import buff.Ice;
import buff.NoCC;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Nodie;
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
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c149lanser extends c00main{
	float damage = 1.3f;
	boolean p = true;
	
	boolean s1p = false;
	
	int s3 = 0;
	
	int spc = 0;
	
	public c149lanser(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 149;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		s1p = false;
		if(AMath.random(33) == 2) s1p = true;
		
		if(s1p) {
			ARSystem.playSound((Entity)player, "c149s12");
		} else {
			ARSystem.playSound((Entity)player, "c149s1");
		}
		skill("c149_s1");
		return true;
	}

	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c149s21");
		ARSystem.giveBuff(player, new Nodamage(player), 20);
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.giveBuff(player, new Silence(player), 20);
		for(int i =0; i<10; i++) {
			skill("c149_s2e");
			delay(()->{
				ARSystem.playSound((Entity)player, "0slash3", 1.4f,0.5f);
				skill("c149_s2");
				for(Entity e : ARSystem.PlayerBeamBox(player, 6, 3, box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(0.6,player);
					delay(()->{
						en.setVelocity(new Vector(0,0,0));
					},0);
				}
			},2*i);
		}
		delay(()->{
			ARSystem.playSound((Entity)player, "0slash", 0.8f,1.f);
			ARSystem.playSound((Entity)player, "c149s2");
			skill("c149_s2-2");
			for(Entity e : ARSystem.PlayerBeamBox(player, 7, 4, box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				en.setNoDamageTicks(0);
				en.damage((en.getMaxHealth()-en.getHealth())*0.25f,player);
				delay(()->{
					en.setVelocity(player.getLocation().getDirection().multiply(2));
				},0);
			}
		},20);

		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c149s3");
		skill("c149_s3e");
		ARSystem.giveBuff(player, new Stun(player), 80);
		ARSystem.giveBuff(player, new Silence(player), 80);
		delay(()->{
			s3 = 60;
		},20);
		return true;
	}
	
	@Override
	public boolean skill4() {
		for(Entity e : ARSystem.box(player, new Vector(6,6,6), box.TARGET)) {
			if(e.getLocation().distance(player.getLocation()) <= 2) {
				if(skillCooldown(0)) {
					spc++;
					if(spc >= 3) Rule.playerinfo.get(player).tropy(149, 1);
					spskillon();
					spskillen();
					ARSystem.playSound((Entity)player, "c149sp");
					damage += 0.3f;
					if(Rule.c.get(e) != null) {
						int n = Rule.c.get(e).number%1000;
						if(n == 16 || n == 4 || n == 75 || n ==89 || n ==146 || n == 150 || (n== 153 && Rule.c.get(player).cooldown[0] <= 0)) {
							if(n == 153) {
								Rule.c.get(player).cooldown[0] = Rule.c.get(player).setcooldown[0];
								ARSystem.playSound(player, "c153sp");
								Rule.c.get(player).spskillen();
							}
							skill("c149_sp2");		
						} else {
							skill("c149_sp");
						}
					} else {
						skill("c149_sp");
					}
					for(int i =0; i<40; i++) {
						delay(()->{
							ARSystem.spellLocCast(player, e.getLocation(), "c149_sptg");
						},i);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(s3 > 0) {
			s3--;
			player.sendTitle("","§c [거리] : " + AMath.round(64-(s3*1),1),0,10,0);
			if(player.isSneaking() || s3 <= 0) {
				ARSystem.playSound((Entity)player, "c149s31");
				ARSystem.spellLocCast(player, ULocal.offset(player.getLocation().clone(), new Vector(64-(s3*1),0,0)), "c149_s3");
				ARSystem.giveBuff(player, new PowerUp(player), 130, Math.max(0.3,3 - (s3*0.05)));
				ARSystem.potion(player, 1, 70, 3 - s3/20);
				Rule.buffmanager.selectBuffTime(player, "stun", 0);
				Rule.buffmanager.selectBuffTime(player, "silence", 0);
				s3 = 0;
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(3,player);
			if(s1p) {
				Skill.remove(target, player);
			}
		}
		if(n.equals("2")) {
			double hp = target.getHealth();
			target.setNoDamageTicks(0);
			target.damage(3,player);
			ARSystem.giveBuff(target, new NoHeal(target), 300000);
			if(Rule.c.get(target) != null) {
				Rule.c.get(target).skillmult *= 0.7f;
				Rule.c.get(target).sskillmult *= 0.7f;
			}
			Rule.buffmanager.selectBuffTime(target, "nodamage", 0);
			delay(()->{
				if(hp-2 <= target.getHealth()) {
					double h = target.getHealth();
					target.setNoDamageTicks(0);
					target.damage(7,player);
					Rule.buffmanager.selectBuffTime(target, "timestop", 0);
					Rule.buffmanager.selectBuffTime(target, "reflect", 0);
					Rule.buffmanager.selectBuffValue(target, "barrier", 0);
					delay(()->{
						if(h-6 <= target.getHealth()) {
							double h2 = target.getHealth();
							target.setNoDamageTicks(0);
							target.damage(15,player);
							Rule.buffmanager.selectBuffTime(target, "nodie", 0);
							delay(()->{
								if(h2-13 <= target.getHealth()) {
									double h3 = target.getHealth();
									target.setNoDamageTicks(0);
									target.damage(999,player);
									delay(()->{
										if(h3-990 <= target.getHealth()) {
											target.setNoDamageTicks(0);
											Skill.remove(target, player);
										}
									},1);
								}
							},1);
						}
					},1);
				}
			},1);
		}
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player) {
			ARSystem.playSound((Entity)player , "c149die");
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.buffmanager.GetBuffTime((LivingEntity)e.getEntity(), "noheal") < 100) {
				ARSystem.giveBuff((LivingEntity)e.getEntity(), new NoHeal((LivingEntity)e.getEntity()), 300);
			}
		} else {
			if(s3 > 0) {
				s3 = 0;
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.giveBuff(player, new Nodie(player), 10);
				ARSystem.giveBuff(player, new Nodamage(player), 10);
				ARSystem.playSound(player, "c149s31");
				ARSystem.playSound(e.getDamager(), "c149s31");
				ARSystem.spellLocCast(player, e.getDamager().getLocation(), "c149_s3");
				ARSystem.giveBuff(player, new PowerUp(player), 130, 3);
				ARSystem.potion(player, 1, 70, 3);
				Rule.buffmanager.selectBuffTime(player, "stun", 0);
				Rule.buffmanager.selectBuffTime(player, "silence", 0);
				return false;
			}
			e.setDamage(e.getDamage() * damage);
			if(p && player.getHealth() - e.getDamage() > -2 && player.getHealth() - e.getDamage() < 1) {
				e.setDamage(0);
				p = false;
				ARSystem.giveBuff(player, new Nodie(player), 40);
			}
		}
		return true;
	}
}
