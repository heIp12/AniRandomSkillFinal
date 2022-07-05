package c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c39sakuya extends c00main{
	int time = 0;
	int time2 = 0;
	int count = 0;

	public c39sakuya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 39;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		if(AMath.random(10) <= 1) cooldown[1] = 0;
		count++;
		if(count < 3) {
			skill("c39_s1");
		} else {
			count = 0;
			skill("c39_s01");
		}
		if(time > 0) cooldown[1] = setcooldown[1]/2;
		return true;
	}
	@Override
	public boolean skill2() {
		if(player.getNearbyEntities(300, 300, 300).size() < 300) {
			if(AMath.random(10) <= 1) cooldown[2] = 0;
			skill("c39_s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(AMath.random(10) <= 1) cooldown[3] = 0;
		
		skill("c39_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(AMath.random(10) <= 1) cooldown[4] = 0;
		time = 100;
		if(!spben && AMath.random(100)<=10 && skillCooldown(0)) {
			spskillen();
			spskillon();
			
			time2 = 300;
			time = 0;
			skillmult += 1;
			skill("c39_sp");

			Rule.playerinfo.get(player).tropy(39,1);
			
		} else {
			skill("c39_s4");
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(time > 0) {
			time--;
			skill("c39_s4_stop");
		}
		if(time2 > 0) {
			time2--;
			skill("c39_s4_stop2");
		}
		if(time%30 == 1) ARSystem.playSound((Entity)player, "0timer");
		if(time2%30 == 1) ARSystem.playSoundAll("0timer");
		
		if(time2 == 1) {
			skillmult -= 1;
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*5);
		} else {

		}
		return true;
	}
}
