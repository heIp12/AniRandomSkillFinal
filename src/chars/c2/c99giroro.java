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
import event.Skill;
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

public class c99giroro extends c00main{
	int ps = 0;
	boolean psc = false;
	int sk3 = 0;
	Player target = null;
	
	public c99giroro(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 99;
		load();
		text();
		c = this;
		if(ARSystem.isGameMode("lobotomy")) {
			setcooldown[1] *= 0.25;
			setcooldown[2] *= 0.4;
			setcooldown[3] *= 0.4;
		}
	}
	
	void shrot(){
		float sp = 8f;
		if(ps > 0) sp = 4f;
		Location l = player.getLocation();

		l.setPitch((float) (l.getPitch() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
		l.setYaw((float) (l.getYaw() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
		
		ARSystem.spellLocCast(player, l, "c99_s1");
		ARSystem.playerAddRotate(player,0,(float) -1);
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c99s1");
		shrot();
		if(!isps) {
			delay(()->{shrot();}, 2);
			delay(()->{shrot();}, 4);
			delay(()->{shrot();}, 6);
			delay(()->{shrot();}, 8);
			if(ps > 0) {
				delay(()->{shrot();}, 3);
				delay(()->{shrot();}, 5);
				delay(()->{shrot();}, 7);
			}
		} else {
			delay(()->{shrot();},2);
			if(ps > 0) {
				delay(()->{shrot();}, 4);
			}
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(isps) {
			hpCost(2, true);
			ARSystem.playSound((Entity)player, "c99s12");
		} else {
			ARSystem.playSound((Entity)player, "c99s2");
		}
		skill("c99_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c99s3");
		if(isps) {
			sk3 = 40;
			ARSystem.giveBuff(player, new Nodamage(player), 16);
		} else {
			player.setVelocity(player.getLocation().getDirection().multiply(0.8).setY(0));
			if(ps > 0) {
				ARSystem.giveBuff(player, new Nodamage(player), 20);
			} else {
				ARSystem.giveBuff(player, new Nodamage(player), 10);
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			if(isps) {
				target.damage(14,player);
			} else {
				target.damage(6,player);
			}
			ARSystem.giveBuff(target, new Stun(target), 40);
		}
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(target == p && e != player && !isps) {
			spskillon();
			spskillen();
			ARSystem.playSound(player, "c99sp");
		}
		int id = player.getLocation().getBlock().getTypeId();
		if((id == 8 || id == 9 ) && p == player) {
			Rule.playerinfo.get(player).tropy(99,1);
		}
	}
	
	@Override
	public boolean tick() {
		int id = player.getLocation().getBlock().getTypeId();
		if((id == 8 || id == 9 ) && ps < 340) {
			ps = 400;
		}
		if(!psc && ps > 0) {
			psc = true;
			skillmult += 0.5;
		}
		if(psc && ps <= 0) {
			psc = false;
			skillmult -= 0.5;
		}
		if(tk%20 == 0) {
			if(psopen && target != null) scoreBoardText.add("&c ["+Main.GetText("c99:sk0")+"] : " + target.getName());
			scoreBoardText.add("&c ["+Main.GetText("c99:ps")+"] : " + AMath.round(ps * 0.05, 2));
		}
		if(sk3 > 0) {
			sk3--;
			if(player.isSneaking()) {
				player.setVelocity(new Vector(0,0.1,0));
			} else {
				player.setVelocity(player.getLocation().getDirection().multiply(1.2));
			}
		}
		if(ps > 0) {
			ps--;
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
			if(e.getEntity() instanceof Player) target = (Player)e.getEntity();
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.7);
		}
		return true;
	}
}
