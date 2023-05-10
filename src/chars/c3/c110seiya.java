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
import util.Text;

public class c110seiya extends c00main{
	boolean ps = true;
	int damage_Stack = 0;
	float rotate = 0;
	Location loc = player.getLocation();
	boolean snk = false;
	boolean jump = false;
	
	@Override
	public void setStack(float f) {
		damage_Stack = (int) f;
		player.setMaxHealth(Text.getD("c110:hp") + (int) f);
		skillmult = 1 + (f *0.05);
	}
	
	public c110seiya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 110;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(ps) {
			ARSystem.playSound((Entity)player, "c110p");
			ps = false;
		}
		
		player.setVelocity(player.getLocation().getDirection().multiply(1).setY(0));
		ARSystem.playSound((Entity)player, "c110s1");
		delay(()->{
			skill("c110_s1");
			player.setVelocity(player.getLocation().getDirection().multiply(-0.5).setY(0));
		},10);
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(ps) {
			ARSystem.playSound((Entity)player, "c110p");
			ps = false;
		}
		
		ARSystem.playSound((Entity)player, "c110s2");
		skill("c110_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		player.setVelocity(player.getLocation().getDirection().multiply(0.5).setY(0));
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(4+(damage_Stack * 0.04),player);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(3+(damage_Stack * 0.03),player);
		}
		if(n.equals("3")) {
			target.setNoDamageTicks(0);
			if(Rule.buffmanager.getBuffs(target) != null && Rule.buffmanager.getBuffs(target).getBuff() != null) {
				for(Buff buff : Rule.buffmanager.getBuffs(target).getBuff()) {
					if(buff.getName().equals("stun") || buff.getName().equals("silence")) continue;
					buff.stop();
				}
			}
			ARSystem.giveBuff(target, new Stun(target), 200);
			ARSystem.giveBuff(target, new Silence(target), 200);
			target.damage(10+ damage_Stack*1,player);
		}
	}
	
	@Override
	public boolean tick() {
		if(ps) {
			if(snk && player.isSneaking()) {
				snk = false;
				damage_Stack++;
				if(damage_Stack>= 1000) Rule.playerinfo.get(player).tropy(110, 1);
			} else if(!snk && !player.isSneaking()){
				snk = true;
			} else if(jump && player.isOnGround()) {
				jump = false;
				skillmult += 0.012;
			} else if(!jump && !player.isOnGround()) {
				jump = true;
			} else {
				rotate += Math.abs(loc.getYaw() - player.getLocation().getYaw());
				if(rotate > 1800) {
					rotate -= 1800;
					player.setMaxHealth(player.getMaxHealth()+1);
					ARSystem.heal(player, 2);
				}
			}
			if(player != null) {
				loc = player.getLocation();
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c110:ps")+ "] : &f" + damage_Stack +"%");
		}
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(ps) {
			ARSystem.playSound((Entity)player, "c110p");
			ps = false;
		}
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.3);
			LivingEntity target = ((LivingEntity)e.getEntity());
			if(target.getHealth() - e.getDamage() <= 1 && skillCooldown(0)) {
				spskillon();
				spskillen();
				ARSystem.giveBuff(target, new Stun(target), 200);
				ARSystem.giveBuff(target, new Silence(target), 200);
				Location loc = target.getLocation().clone();
				loc.setPitch(45);
				loc.setYaw(0);
				loc = ULocal.offset(loc, new Vector(1.2,0.2,0));
				loc.setYaw(ULocal.lookAt(loc, target.getLocation()).getYaw());
				player.teleport(loc);
				ARSystem.giveBuff(player, new Stun(player), 220);
				ARSystem.giveBuff(player, new Nodamage(player), 220);
				ARSystem.giveBuff(player, new Silence(player), 220);
				ARSystem.playSound((Entity)player, "c110sp");
				delay(()->{
					for(int i = 0; i<5;i++) {
						int j = AMath.random(2);
						delay(()->{
							ARSystem.playSound((Entity)player, "c110sp"+j);
							for(int k=0; k<5; k++) delay(()->{skill("c110_sp"+j);},k*5);
						},40*i);
					}
					delay(()->{
						player.teleport(Map.randomLoc());
						ps = true;
					},200);
				},40);
			}
		} else {

		}
		return true;
	}
	
}
