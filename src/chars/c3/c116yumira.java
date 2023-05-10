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

public class c116yumira extends c00main{
	int s1_delay = 10;
	int s1 = 1;
	
	public c116yumira(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 116;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		for(int i =0; i < s1; i++) {
			delay(()->{
				ARSystem.playSound((Entity)player, "0sword");
				if(isps) {
					player.setVelocity(player.getLocation().getDirection().multiply(0.8).setY(0));
				} else {
					player.setVelocity(player.getLocation().getDirection().multiply(0.5).setY(0));
				}
				skill("c116_s1-"+AMath.random(3));
			},i*s1_delay);
		}
		s1 = 1;
		return true;
	}
	
	@Override
	public boolean skill2() {
		LivingEntity target = null;
		Location loc = player.getLocation().clone();
		for(int i=0;i<8;i++) {
			loc.add(loc.getDirection());
			for (LivingEntity e : player.getWorld().getLivingEntities()) {
				if(e.getLocation().distance(loc) <= 5 && e != player) {
					if(ARSystem.isTarget(e, player, box.TARGET)) target = e;
				}
			}
			if(target != null) i = 30;
		}
		if(target != null) {
			Location ploc = player.getLocation();
			loc = ULocal.lookAt(target.getLocation(),ploc);
			loc = loc.add(loc.getDirection().multiply(1.5));
			loc = ULocal.lookAt(loc, target.getLocation());
			player.teleport(loc);
			loc = target.getLocation();
			target.setVelocity(player.getLocation().getDirection().multiply(3.5));
			LivingEntity e = target;
			if(isps) {
				skill("c116_s2_e2");
				ARSystem.playSound((Entity)player, "c116s4");
				for(Buff b : Rule.buffmanager.selectBuffType(target, BuffType.BUFF)) {
					b.setTime(0);
				}
				e.setNoDamageTicks(0);
				e.damage(12,player);
			} else {
				skill("c116_s2_e");
				ARSystem.playSound((Entity)player, "c116s2");
				delay(()->{
					e.setNoDamageTicks(0);
					e.damage(8,player);
				},100);
			}
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		Location loc = player.getLocation();
		loc.setPitch(0);
		List<LivingEntity> targets = new ArrayList<>();
		for(int i = 0; i<8; i++) {
			player.teleport(loc);
			for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
				if(!targets.contains(e)) targets.add((LivingEntity)e);
			}
			loc.add(loc.getDirection().multiply(1.5));
		}
		for(int i = 0; i<4;i++) {
			delay(()->{
				for(LivingEntity e : targets) {
					e.setNoDamageTicks(0);
					e.damage(1,player);
					delay(()->{
						ARSystem.playSound(e, "0attack",0.5f);
						e.setVelocity(new Vector((AMath.random(10)-5)*0.05,0,(AMath.random(10)-5)*0.05));
						if(e instanceof Player) {
							ARSystem.playerRotate((Player)e, AMath.random(100)-200, AMath.random(30)-15);
							ARSystem.spellCast(player, e, "c116_s3e");
						}
					},4);
				}
			},20+14*i);
		}
		ARSystem.playSound((Entity)player, "c116s3");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			if(!isps &&s1 < 3) s1++;
			if(isps &&s1 < 5) s1++;
		}
	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player && ARSystem.AniRandomSkill != null) {
			if(s_kill == ARSystem.AniRandomSkill.player-1){
				Rule.playerinfo.get(player).tropy(116, 1);
			}
		}
	}
	
	@Override
	public boolean tick() {

		if(tk%20 == 0 && psopen) scoreBoardText.add("&c ["+Main.GetText("c116:sk1")+ "] : &f"  + s1);

		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
			ARSystem.giveBuff((LivingEntity) e.getEntity(), new Stun((LivingEntity) e.getEntity()), 2);
			ARSystem.giveBuff((LivingEntity) e.getEntity(), new NoHeal((LivingEntity) e.getEntity()), 100);
		} else {
			if(player.getHealth() - e.getDamage() > 0 && player.getHealth() - e.getDamage() < 2 && !isps) {
				spskillon();
				spskillen();
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				ARSystem.playSound((Entity)player, "c116sp");
				s1_delay = 4;
				ARSystem.heal(player, 1000);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
