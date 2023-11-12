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

public class c130yuzi extends c00main{
	int sp = 0;
	int sp2 = 0;
	LivingEntity target;
	int s2 = 0;
	int p =0;
	
	public c130yuzi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 130;
		load();
		text();
		c = this;
	}

	@Override
	public void setStack(float f) {
		sp = (int)f;
	}

	@Override
	public boolean skill1() {
		target = (LivingEntity)ARSystem.boxSOne(player, new Vector(8,8,8), box.TARGET);
		if(target != null) {
			s2s = 0;
			ARSystem.playSound((Entity)player, "c130s1");
			ARSystem.spellCast(player, target, "c130_s1");
			
			delay(()->{
				target.setNoDamageTicks(0);
				target.damage(3,player);
				ARSystem.spellCast(player, target, "c130_s1-2");
				ARSystem.giveBuff(target, new Stun(target), 10);
				player.sendTitle("§c§l【§4§l ! §c§l】", "",0,20,0);
				s2 = 20;
			},5+AMath.random(10));
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	float s2s = 0;
	@Override
	public boolean skill2() {
		if(s2 > 0) {
			Rule.buffmanager.selectBuffTime(target, "stun", 0);
			if(s2 >= Math.min(19, 17 + sp) && skillCooldown(0)) {
				spskillon();
				spskillen();
				ARSystem.playSound((Entity)player, "c130sp"+AMath.random(3));
				ARSystem.spellCast(player, target, "c130_sp");
				Vector v = player.getLocation().getDirection().multiply(5);
				v.setY(v.getY()*0.03);
				target.setVelocity(v);
				target.setNoDamageTicks(0);
				target.damage(6 + sp*3,player);
				skillmult+=0.1;
				sp++;
				sp2 = 400;
				cooldown[0] = (float) (setcooldown[0] * (skillmult+sskillmult));
			} else {
				ARSystem.playSound((Entity)player, "c130s2");
				target.setNoDamageTicks(0);
				float damage = 6 - Math.max(10,s2)*0.5f;
				damage *= Math.max(0.5, 1 - (s2s*0.15));
				target.damage(damage,player);
				ARSystem.spellCast(player, target, "c130_s2");
				Vector v = player.getLocation().getDirection().multiply(2);
				v.setY(v.getY()*0.03);
				target.setVelocity(v);
			}
			s2s++;
		} else {
			cooldown[2] = 2f;
		}
		return true;
	}
	@Override
	public boolean skill3() {
		List<Entity> entitys = ARSystem.PlayerBeamBox(player, 20, 10, box.TARGET);
		if(entitys != null && entitys.size() > 0) {
			LivingEntity en = (LivingEntity) entitys.get(0);
			if(en != null) {
				player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(0));
			}
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	public boolean tick() {
		if(s2 > 0) {
			s2--;
		}
		if(sp2 > 0) {
			scoreBoardText.add("&c ["+Main.GetText("c130:sk0")+ "] : "+ AMath.round(sp2*0.05, 2) +" : [" + sp +"]");
			sp2--;
			if(sp2 <= 0) {
				sp = 0;
			}
		}
		if(p > 0) p--;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(p > 0) {
				ARSystem.heal(player, e.getDamage()*0.2);
			}
		} else {
			delay(()->{
				ARSystem.heal(player, e.getDamage()*0.33);
				p = 20;
			},20);
		}
		return true;
	}

}
