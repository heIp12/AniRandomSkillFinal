package chars.c;

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
import buff.Nodamage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import manager.AdvManager;

import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c34natu extends c00main{
	double damage = 0.5;
	double damage2 = 0;
	int timer = 0;
	int time = 0;
	Location loc = null;
	
	public c34natu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 34;
		load();
		text();
		loc = player.getLocation();
	}
	
	@Override
	public void setStack(float f) {
		damage = (int) f;
	}

	@Override
	public boolean skill1() {
		cooldown[1] *= damage;
		damage2 = damage;
		damage = 0.5;
		skill("c34_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		cooldown[2] *= damage;
		ARSystem.potion(player, 2, 20, 5);
		damage2 = damage;
		damage = 0.5;
		skill("c34_s2");
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player && e == player && ARSystem.getPlayerCount() == 3 && skillCooldown(0)) {
			spskillon();
			spskillen();
			ARSystem.giveBuff(player, new TimeStop(player), 170);
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			ARSystem.giveBuff(player, new Silence(player), 100);
			skill("c34_sp");
		}
	}
	
	@Override
	public boolean tick() {
		if(tk%20==0) {
			scoreBoardText.add("&c ["+Main.GetText("c34:ps")+ "]&f : "+ Math.round(damage*100)/100.0);
		}
		int t= (int) (100 - (10 * (skillmult + sskillmult-1)));
		if(t < 5) t = 5;
		if(loc.distance(player.getLocation()) == 0) {
			time++;
			if(time == t) skill("c34_p");
			if(time > t) {
				timer++;
				if(timer >= 1200) {
					Rule.playerinfo.get(player).tropy(34,1);
				}
				if(damage < 2) {
					damage += 0.01 *(skillmult + sskillmult);
					damage = Math.round(damage*1000)/1000.0;
				}
			}
		} else {
			if(time > t) {
				ARSystem.playSound((Entity)player, "c34p2");
			}
			if(MSUtil.isbuff(player,"c34_p")) {
				MSUtil.buffoff(player, "c34_p");
			}
			time = 0;
		}
		loc = player.getLocation();
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() * damage2);
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
		} else {

		}
		return true;
	}
}
