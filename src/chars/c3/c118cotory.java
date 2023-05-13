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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c10bell;
import chars.c.c30siro;
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

public class c118cotory extends c00main{
	int p = 0;
	int tr = 0;
	TargetMap<LivingEntity, Double> nodamage = new TargetMap<>();
	public c118cotory(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 118;
		load();
		text();
		c = this;
	}
	@Override
	public void setStack(float f) {
		p = (int)f;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c118sk1");
		if(player.isSneaking()) {
			makerSkill(player, "1");
		} else {
			ARSystem.spellCast(player,player,"c118_s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c118sk2");
		tpsdelay(()->{
			skill("c118_s2");
			for(Entity e : ARSystem.box(player, new Vector(8,5,8), box.ALL)) {
				nodamage.set((LivingEntity)e,60);
				LivingEntity target = (LivingEntity)e;
				for(Buff b : Rule.buffmanager.selectBuffType(target, BuffType.DEBUFF)) {
					b.setTime(0);
					nodamage.set(target,60);
					p+=10;
					s_damage += 5;
				}
			}
			for(Buff b : Rule.buffmanager.selectBuffType(player, BuffType.DEBUFF)) {
				b.setTime(0);
				p+=2;
				s_damage += 2;
			}
		},20);
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(p >= 50 && skillCooldown(0)) {
			spskillon();
			spskillen();
			tr = 0;
			Bgm.setForceBgm("c118-2");
			skill("c118_spe");
			Location loc = player.getLocation();
			loc.setYaw(AMath.random(360));
			loc.setPitch(0);
			loc= ULocal.offset(loc, new Vector(6,0,0));
			ARSystem.spellLocCast(player, loc, "c118_sp");
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		nodamage.set(target,60);
		if(n.equals("1") && target.getHealth() != target.getMaxHealth()) {
			double p = 1.2 - (target.getHealth() / target.getMaxHealth());
			if(p > 1) p = 1;
			ARSystem.heal(target, p*10);
			if(target != player) this.p += p*10;
			s_damage += p*5;
			skill("c118_s1e");
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
		}
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0 && psopen) scoreBoardText.add("&c ["+Main.GetText("c118:p1")+ "] : &f" + p +" / 50");
		if(p >= 100) {
			Rule.playerinfo.get(player).tropy(118, 1);
		}
		for(LivingEntity e : nodamage.get().keySet()) {
			nodamage.add(e, -1);
			if(nodamage.get(e) <= 0) {
				nodamage.removeAdd(e);
			}
		}
		if(Bgm.bgmcode.equals("c118sp2")) {
			ARSystem.giveBuff(player, new Stun(player), 10);
			ARSystem.giveBuff(player, new Silence(player), 10);
			ARSystem.giveBuff(player, new Nodamage(player), 10);
			boolean beam = false;
			if(Bgm.getTime() <= 15) {
				beam = (Bgm.getTime()*20)%40==0;
				if(tk%10 == 0 && AMath.random(100) < 20) {
					for(Entity e : ARSystem.box(player, new Vector(50,8,50), box.TARGET)) {
						LivingEntity target = (LivingEntity)e;
						ARSystem.giveBuff(target, new Fascination(target, player), 60, 0.3);
					}
				}
			} else {
				beam = (Bgm.getTime()*20)%100==0;
				if(tk%10 == 0 && AMath.random(100) < 20) {
					for(Entity e : ARSystem.box(player, new Vector(50,8,50), box.TARGET)) {
						LivingEntity target = (LivingEntity)e;
						ARSystem.giveBuff(target, new Fascination(target, player), 60, 0.1);
					}
				}
			}
			if(beam) {
				Location loc = player.getLocation();
				loc.setYaw(AMath.random(360));
				loc.setPitch(0);
				loc= ULocal.offset(loc, new Vector((AMath.random(6)*0.3)+5,0,0));
				ARSystem.spellLocCast(player, loc, "c118_sp");
			}
			if(Bgm.getTime() <= 0.1) {
				skill("removemyall");
				Bgm.bgmlock = false;
				Bgm.randomBgm();
			}
			nodamage.removes();
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
		} else {
			if(nodamage.get((LivingEntity) e.getDamager()) != null && nodamage.get((LivingEntity) e.getDamager()) > 0) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
