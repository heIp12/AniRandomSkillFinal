package c2;

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
import buff.Install;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import c.c000humen;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c8606iriya extends c00main{
	Install b;
	LivingEntity target;
	int sk1;
	
	public c8606iriya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 6086;
		load();
		text();
		c = this;
	}
	
	@Override
	public boolean skill1() {
		if(b.getValue() > 20) {
			b.setValue(b.getValue()-20);
			ARSystem.playSound((Entity)player, "c6086s1");
			skill("c6086_s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(b.getValue() > 4) {
			b.setValue(b.getValue()-4);
			
			if(AMath.random(3) == 2)ARSystem.playSound((Entity)player, "c6086s2");
			if(target == null) {
				skill("c6086_s2");
				skill("c6086_s22");
			} else {
				cooldown[1] = 0;
				skill("c6086_s2");
				player.setVelocity(player.getLocation().clone().subtract(target.getLocation()).multiply(-0.4).toVector());
				target.setNoDamageTicks(0);
				target.damage(4,player);
				if(AMath.random(3) == 2) ARSystem.playSound((Entity)target, "0attack4");
				sk1 = 0;
				target = null;
			}
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			sk1 = 20;
			target.damage(2,player);
			this.target = target;
			ARSystem.addBuff(target, new Stun(target), 20);
		}
	}
	
	@Override
	public boolean skill5() {
		Buff b = Rule.buffmanager.selectBuff(player, "Install");
		b.setTime(2);
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c86db");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(sk1 > 0) {
			sk1--;
			if(target != null && sk1%2 == 0) ARSystem.spellLocCast(player, target.getLocation().add(new Vector(0.5-0.1*AMath.random(10),0,0.5-0.1*AMath.random(10))), "c6086_p");
		} else {
			target = null;
		}
		if(tk%20 == 0) {
			if(b == null) b = (Install)Rule.buffmanager.selectBuff(player, "Install");
			if(b != null) {
				scoreBoardText.add("&c ["+Main.GetText("c86:t")+"] : " + AMath.round(b.getValue(),1));
			}
		}
		return true;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*5);
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*0.5);
		}
		return true;
	}
}
