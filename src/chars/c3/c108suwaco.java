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

public class c108suwaco extends c00main{
	int p = 0;
	int time = 0;
	int sk4 = 0;
	float damage = 0;
	Location tloc;
	int tr = 0;
	
	int count = 0;
	Entity target = null;
	
	@Override
	public void setStack(float f) {
		p = (int) f;
	}
	
	public c108suwaco(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 108;
		load();
		text();
		c = this;

		if(ARSystem.isGameMode("lobotomy")) setcooldown[0] = 400;
	}

	@Override
	public boolean skill1() {
		if(sk4 > 0) {
			cooldown[1] = 0;
			return false;
		}
		List<Entity> entitys = ARSystem.PlayerBeamBox(player, 20, 5, box.ALL);
		if(entitys != null && entitys.size() > 0) {
			LivingEntity en = (LivingEntity) entitys.get(0);
			if(en != null) {
				ARSystem.playSound((Entity)player, "c108s1");
				ARSystem.spellCast(player, en, "c108_s1");
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(sk4 > 0) {
			cooldown[2] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "c108s2");
		skill("c108_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(sk4 > 0) {
			cooldown[3] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "c108s3");
		skill("c108_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c108s4");
		ARSystem.giveBuff(player, new Stun(player), 400);
		ARSystem.potion(player, 14, 400, 1);
		skill("c108_s4");
		sk4 = 400;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(8,player);
			ARSystem.giveBuff(target, new Stun(target), 100);
			ARSystem.giveBuff(target, new Silence(target), 100);
		}
		if(n.equals("2")) {
			target.teleport(tloc);
			target.setNoDamageTicks(0);
			target.damage(damage,player);
			damage*=1.02;
		}
	}
	
	@Override
	public boolean tick() {
		time++;
		if(time%(110 - Math.min(90,10.0*(skillmult+sskillmult))) == 0) {
			if(p < 8) p++;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c108:ps")+ "] : &f" + p);
			if(psopen && target !=null)	scoreBoardText.add("&c ["+Main.GetText("c108:sk0")+ "] &f" + target.getName() +" &f: " + count +"/10");
		}
		if(sk4>0 && sk4%6 == 0) ARSystem.heal(player, 1);
		if(player.isSneaking() && sk4 > 0) {
			skill("c108_s4_remove");
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			sk4 = 0;
			Rule.buffmanager.selectBuff(player, "stun").setTime(0);
		}
		if(sk4 > 0) sk4--;
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
			if(p > 0) {
				p--;
				ARSystem.spellCast(player, e.getEntity(), "c108_p");
				ARSystem.playSound(e.getEntity(), "c108p");
				tr++;
				if(tr == 30) {
					Rule.playerinfo.get(player).tropy(108, 1);
				}
			}
			if(e.getDamage() >= 2 && e.getEntity() == target){
				count++;
				if(count >=10 && skillCooldown(0)) {
					damage = 0.01f;
					spskillon();
					spskillen();
					ARSystem.playSound(player, "c108sp");
					ARSystem.spellCast(player, target, "c108_sp");
					ARSystem.giveBuff(((LivingEntity)e.getEntity()), new Stun((LivingEntity)e.getEntity()), 600);
					ARSystem.giveBuff(((LivingEntity)e.getEntity()), new Silence((LivingEntity)e.getEntity()), 600);
					tloc = e.getEntity().getLocation();
				}
			} else {
				target = e.getEntity();
				count = 0;
			}
		} else {
			if(sk4 > 0 && e.getDamage() > 8) e.setDamage(0);
		}
		return true;
	}
	
}
