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
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
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
import chars.c.c06watson;
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c.c45momo;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c137mlim extends c00main{
	int damage = 0;
	boolean sk1 = false;
	double hps = 0;
	int heald = 0;
	boolean ok = false;
	
	int p = 0;
	
	int st = 0;
	Player rimuru;
	public c137mlim(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 137;
		load();
		text();
		c = this;
		hps = player.getHealth();
	}

	@Override
	public boolean skill1() {
		sk1 = true;
		skill("c137_s1");
		if(Rule.c.size() <= 2) {
			cooldown[1] *= 0.75f;
		}
		return true;
	}

	@Override
	public boolean skill2() {
		float j = 1.2f;
		if(ok) {
			if(rimuru != null) {
				ARSystem.playSound((Entity)rimuru, "c137c4mlim");
			}
			j *=5;
		}
		
		ARSystem.playSound((Entity)player, "c137s2");
		skill("c137_s2");
		player.setVelocity(player.getLocation().getDirection().multiply(j));
		return true;
	}
	

	@Override
	public boolean skill3() {
		skill("c137_s3");
		for(int i =0;i<10;i++) {
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(5,8,5), box.TARGET)) {
					makerSkill((LivingEntity)e, "2");
				}
			},i*2);
		}
		ARSystem.giveBuff(player, new Stun(player), 30);
		ARSystem.playSound((Entity)player, "c137s3");
		return true;
	}

	@Override
	public boolean skill4() {
		invskill = new InvSkill(player) {
			@Override
			public void Start(String st) {
				player.closeInventory();
			}
		};

		Set<Player> Player = ((HashMap<Player, c00main>) Rule.c.clone()).keySet();
		Player.remove(player);
		Inventory.getlistC(invskill,player,Player);
		invskill.openInventory(player);
		
		for(Player p : Rule.c.keySet()) {
			if(p != player) ARSystem.addBuff(p, new Silence(p), 2);
		}
		return true;
	}

	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(Rule.c.get(target) != null && Rule.c.get(target).isBattleTime() > 600) {
				target.setNoDamageTicks(0);
				target.damage(20 + (damage*5),player);
				st++;
				if(st >= 2) Rule.playerinfo.get(player).tropy(137, 1);
				if(sk1) {
					ARSystem.playSound((Entity)player, "c137s12");
					sk1 = false;
				}
			} else {
				target.setNoDamageTicks(0);
				target.damage(2+ (damage*0.5),player);
				if(sk1) {
					ARSystem.playSound((Entity)player, "c137s11");
					sk1 = false;
				}
			}
		}
		if(n.equals("2")) {
			if(target.hasPotionEffect(PotionEffectType.GLOWING)) {
				ARSystem.giveBuff(player, new PowerUp(player), 200, 2);
			}
			target.setNoDamageTicks(0);
			target.damage(0.5,player);
			
			ARSystem.giveBuff(target, new Stun(target), 2);
		}
		if(n.equals("3")) {
			target.setNoDamageTicks(0);
			target.damage(damage*4+6,player);
		}
		super.makerSkill(target, n);
	}
	
	@Override
	public boolean tick() {
		if(p > 0) p--;
		if(player != null) player.setFallDistance(0);
		if(psopen) scoreBoardText.add("&c ["+Main.GetText("c137:sk0")+ "] :  "+ ((4*damage)+6));
		boolean allafk = true;
		for(Player p : Rule.c.keySet()) {
			if(Rule.c.get(p).isBattleTime() > 600 && p != player) {
				ARSystem.potion(p, 24, 10, 10);
				if(this.p <= 0) {
					ARSystem.playSound((Entity)player, "c137p");
					this.p = 200;
				}
			}
			if(Rule.c.get(p).isBattleTime() >= 100 && p != player) allafk = false;
		}
		if(tk%4 == 0) {
			if(isBattleTime() > 600) {
				skillmult -= 0.01;
			}
		}
		if(tk%10 == 0 && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 20 && Rule.c.size() > 2) {
			if(allafk && skillCooldown(0)) {
				cooldown[0] = 2000;
				for(Player p :Rule.c.keySet()) {
					if(Rule.c.get(p) instanceof c137mlim) {
						Rule.c.get(p).cooldown[0] += 15;
					}
				}
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c137sp");
				ARSystem.giveBuff(player, new Silence(player), 200);
				ARSystem.giveBuff(player, new Stun(player), 200);
				ARSystem.giveBuff(player, new Nodamage(player), 200);
				delay(()->{
					for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
						ARSystem.spellCast(player, e, "c137_sp");
					}
				},150);
				delay(()->{
					damage += 1;
					cooldown[0] = setcooldown[0];
				},210);
				ARSystem.heal(player, player.getMaxHealth()*0.2f);
				heald = 20;
			}
		}
		if(tk%2 == 0) {
			ok = false;
			for(Entity e : ARSystem.PlayerBeamBox(player, 200, 15, box.TARGET)) {
				if(Rule.c.get(e) != null && Rule.c.get(e).isBattleTime() > 600) {
					if(Rule.c.get(e).number%1000 == 3) rimuru = (Player)e;
					if(cooldown[2] > 0) cooldown[2] -= 0.15f;
					player.sendTitle(" ",e.getName(),0,4,0);
					ok = true;
					break;
				}
			}
		}
		if(hps >= player.getHealth()) {
			hps = player.getHealth();
		} else {
			if(heald <= 0) {
				heald = 80;
				ARSystem.playSound(player, "c137heal");
			}
			hps = player.getHealth();
		}
		heald--;
		return true;
	}
	
	boolean death = false;
	@Override
	public boolean remove(Entity caster) {
		if(!death) {
			death = true;
			ARSystem.giveBuff(player, new TimeStop(player), 200);
			ARSystem.playSound((Entity)player, "c137death");
			tpsdelay(()->{
				Skill.quit(player);
			},70);
		}
		return false;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player && e == player) {
			ARSystem.playSound((Entity)player, "c137kill");
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.c.size() <= 2) {
				e.setDamage(e.getDamage()*2);
			}
		} else {
			if(player.getHealth()- e.getDamage() < 1) {
				death = true;
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.giveBuff(player, new TimeStop(player), 200);
				ARSystem.playSound((Entity)player, "c137death");
				tpsdelay(()->{
					Skill.quit(player);
				},70);
			}
		}
		return true;
	}
	
	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player,"c137db"+AMath.random(3));
		return true;
	}

}
