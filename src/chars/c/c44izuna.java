package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Airborne;
import buff.NoHeal;
import buff.Timeshock;
import chars.c2.c62shinon;
import chars.c2.c83sora;
import chars.c2.c84siro;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c44izuna extends c00main{

	Location loc;
	boolean left = true;
	
	float down = 0;
	int ps = 0;
	
	int sp = 0;
	int tropy = 0;
	
	public c44izuna(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 44;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0));
		return true;
	}
	@Override
	public boolean skill2() {
		if(ps> 5) {
			ps-=5;
			skill("c44_s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.OnBuffTime(player, "stun")) {
			Rule.buffmanager.selectBuffTime(player, "stun",0);
		}
		return true;
	}
	
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new NoHeal(target), 100);
		}
	}
	
	@Override
	public boolean tick() {
		if(loc == null) loc = player.getLocation();
		
		double yaw = Math.abs(loc.getYaw() - player.getLocation().getYaw());
		if(yaw > 180) yaw -= 360;
		
		if(yaw > 30 || (sp > 0 && yaw != 0) || (!player.isOnGround() && yaw > 15)) {
			if(loc.getYaw() >= player.getLocation().getYaw() && left || (sp > 0 && left)) {
				left = false;
				ps++;
				skill("c44_s1_i");
				skill("c44_s1_a");
			} else if(loc.getYaw() <= player.getLocation().getYaw() && !left || (sp > 0 && !left )){
				ps++;
				left = true;
				skill("c44_s1_i2");
				skill("c44_s1_a");
			}
			tropy++;
			if(tropy > 500) Rule.playerinfo.get(player).tropy(44, 1);
		}
		
		if(player.getLocation().getPitch() >= 75) {
			down = 5;
		}
		if(player.getLocation().getPitch() <= -50 && down > 0 && skillCooldown(5)) {
			skill("c44_s4");
			player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
			delay(()->{
				ARSystem.giveBuff(player, new Airborne(player),20);
			} ,14);
			ps+= 5;
		}
		
		if(tk%5 == 0 && ps > 0) ps--;
		if(ps >= 30 && !player.isOnGround() && player.getHealth() <= player.getMaxHealth()/2 && skillCooldown(0) && sp <= 0 ) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c44sp");
			ARSystem.giveBuff(player, new Airborne(player),200);
			for(Entity e : ARSystem.box(player, new Vector(15,15,15), box.ALL)) {
				ARSystem.giveBuff((LivingEntity) e, new Timeshock((LivingEntity) e), 200);
			}
			sp = 200;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c44:ps")+ "] : "+ ps);
		}
		if(sp > 0) sp--;
		if(down > 0) down--;
		loc = player.getLocation();
		return true;
	}

	int attck = 0;
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			attck++;
			if(attck >= 3) {
				attck = 0;
				ARSystem.heal(player, 1);
			}
			if(!e.getEntity().isOnGround()) {
				if(Rule.buffmanager.isBuff((LivingEntity) e.getEntity(), "airborne")) {
					Rule.buffmanager.selectBuffAddValue((LivingEntity) e.getEntity(), "airborne", 4);
				} else {
					ARSystem.giveBuff((LivingEntity) e.getEntity(), new Airborne((LivingEntity) e.getEntity()), 4);
				}
			}
		} else {

		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c83sora) {
					is = "zero";
					break;
				}
				if(Rule.c.get(e) instanceof c84siro){
					is = "zero";
					break;
				}
			}
		}
		
		if(is.equals("zero")) {
			ARSystem.playSound((Entity)player, "c44zero");
		} else {
			ARSystem.playSound((Entity)player, "c44db");
		}
		
		return true;
	}
}
