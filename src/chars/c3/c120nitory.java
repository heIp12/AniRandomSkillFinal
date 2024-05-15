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
	boolean s1 = false;
	int w = 250;
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
		s1 = !s1;
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(w > 50) {
			w-= 50;
		} else {
			cooldown[2] = 0;
			return true;
		}
		ARSystem.playSound((Entity)player, "c120s2");
		skill("c120_s2");
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c120s3");
		player.setVelocity(player.getLocation().getDirection().multiply(1.4f));
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
		List<Entity> entitys = ARSystem.PlayerBeamBox(player, 20, 10, box.ALL);
		
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
	
	int wt = 0;
	
	@Override
	public boolean tick() {
		if(player.getRemainingAir() <= 40) {
			player.setVelocity(player.getLocation().getDirection().multiply(1.2f));
			player.setRemainingAir(40);
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c [물] : &f" + w*0.1 +"l");
		}
		int id = player.getLocation().getBlock().getTypeId();

		if(ARSystem.isGameMode("lobotomy") && w < 250 && !s1 && tk%2 == 1) {
			w++;
		}
		if(id == 8 || id == 9) {
			w+= 2+(skillmult+sskillmult);
			if(w > 250) w = 250;
			wt++;
			if(wt > 400) Rule.playerinfo.get(player).tropy(120, 1);
		} else {
			wt = 0;
		}
		for(LivingEntity e : target.get().keySet()) {
			if(target.get(e) > 0 && !e.isDead()) {
				scoreBoardText.add("&c ["+e.getName()+ "] : &f" + AMath.round(target.get(e),2));
			}
		}
		if(isps) scoreBoardText.add("&c ["+Text.get("c120:p0")+ "] : &f" + (100 + (spcount*200)));
		if(s1 && Rule.buffmanager.selectBuffType(player, BuffType.SILENCE).size() == 0 && w > 0) {
			if(tk%2 == 0) w--;
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
			float damage = (float) ((skillmult+sskillmult)*0.2f);
			
			if(Rule.buffmanager.GetBuffTime(target, "bubble") > 0) {
				((Bubble)Rule.buffmanager.selectBuff(target, "bubble")).Movement.add(player.getLocation().getDirection().multiply(0.08));
				damage+= 0.5f;
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
