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
import buff.Cindaella;
import buff.Curse;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c66akame extends c00main{
	int sk3 = 0;
	int count = 0;
	
	public c66akame(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 66;
		load();
		text();
		c = this;
	}
	
	public void cuers(LivingEntity e) {
		Curse cs = new Curse(e);
		cs.setCaster(player);
		if(isps) {
			ARSystem.giveBuff(e, cs,60,1);
		} else {
			ARSystem.giveBuff(e, cs,200,0.3);
		}
		count++;
		if(count >= 10 && !isps) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c66sp");
		}
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c66s1");
		skill("c66_s1");
		player.setVelocity(player.getLocation().getDirection().multiply(2));
		delay(()->{
			for(Entity e : ARSystem.box(player, new Vector(3,3,3), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);
				((LivingEntity)e).damage(4,player);
				cuers((LivingEntity) e);
				player.setVelocity(player.getLocation().getDirection().setY(0).multiply(4));
			}
		},5);

		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c66s2");
		skill("c66_s2");
		for(Entity e : ARSystem.box(player, new Vector(8,3,8), box.TARGET)) {
			((LivingEntity)e).setNoDamageTicks(0);
			((LivingEntity)e).damage(6,player);
			cuers((LivingEntity) e);
			ARSystem.potion(player, 14, 60, 0);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c66s3");
		skill("c66_s3");
		player.setVelocity(player.getLocation().getDirection().setY(-0.5).multiply(-2));
		sk3 = 60;
		return true;
	}

	@Override
	public boolean tick() {
		if(sk3>0)sk3--;
		if(tk%20 == 0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c66:sk0")+ "] : "+ count +"/ 10");
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(s_kill > 3) {
			Rule.playerinfo.get(player).tropy(66,1);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(sk3 > 0 && e.getEntity().getLocation().distance(player.getLocation()) <= 8) {
				sk3 = 0;
				cuers((LivingEntity)e.getEntity());
				Wound w = new Wound((LivingEntity) e.getEntity());
				w.setEffect("c66_s3e");
				w.setValue(2);
				w.setDelay(player,20,0);
				ARSystem.giveBuff((LivingEntity) e.getEntity(), w, 100);
			}
		} else {
			
		}
		return true;
	}
	
}
