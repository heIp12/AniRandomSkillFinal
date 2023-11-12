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
import ars.gui.G_Nitory;
import ars.gui.G_Supply;
import buff.Barrier;
import buff.Bubble;
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

public class c120nitory extends c00main{
	TargetMap<LivingEntity, Double> target;
	int s1 = 0;
	
	int spcount = 0;
	
	public c120nitory(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 120;
		load();
		text();
		c = this;
		target = new TargetMap<>();
	}
	
	@Override
	public void setStack(float f) {
		s_score = (int)f;
	}
	

	@Override
	public boolean skill1() {
		s1 = 20;
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c120s2");
		skill("c120_s2");
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c120s3");
		ARSystem.potion(player, 14, 120, 1);
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c120s4");
		for(LivingEntity e : target.get().keySet()) {
			e.setNoDamageTicks(0);
			e.damage(target.get(e),player);
			ARSystem.spellCast(player, e, "c120_s4");
		}
		target.clear();
		return true;
	}
	
	@Override
	public boolean skill5() {
		List<Entity> entitys = ARSystem.PlayerBeamBox(player, 20, 10, box.TARGET);
		
		if((score-200) >= 100 + (spcount*200) && ((player.isSneaking() && entitys.size() > 0 && Rule.c.get(entitys.get(0)) != null) || !player.isSneaking())) {
			s_score -= (400 + (spcount*200));
			spcount++;
			spskillon();
			spskillen();
			
			if(player.isSneaking()) {
				ARSystem.playSound((Entity)player, "c120sp2");
				new G_Nitory((Player)entitys.get(0));
			} else {
				ARSystem.playSound((Entity)player, "c120sp");
				new G_Nitory(player);
				
			}
		}

		return true;
	}
	@Override
	public boolean tick() {
		for(LivingEntity e : target.get().keySet()) {
			if(target.get(e) > 0 && !e.isDead()) {
				scoreBoardText.add("&c ["+e.getName()+ "] : &f" + AMath.round(target.get(e),2));
			}
		}
		if(isps) scoreBoardText.add("&c ["+Text.get("c120:p0")+ "] : &f" + (100 + (spcount*200)));
		if(s1 > 0) {
			s1--;
			skill("c120_s1");
			ARSystem.playSound((Entity)player, "minecraft:weather.rain",2,0.2f);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			int id = target.getLocation().clone().add(0,1,0).getBlock().getTypeId();

			ARSystem.spellCast(player, target, "c120se");
			target.setVelocity(player.getLocation().getDirection().multiply(0.5));
			float damage = (float) ((skillmult+sskillmult)*0.1f);
			
			if(Rule.buffmanager.GetBuffTime(target, "bubble") > 0) {
				((Bubble)Rule.buffmanager.selectBuff(target, "bubble")).Movement.add(player.getLocation().getDirection().multiply(0.08));
				damage+= 0.2f;
			}
			
			if(id == 8 || id == 9) {
				this.target.add(target,damage*3);
			} else {
				this.target.add(target,damage);
			}
		}
		if(n.equals("2")) {
			ARSystem.giveBuff(target, new Bubble(target), 60);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {

		}
		return true;
	}
}
