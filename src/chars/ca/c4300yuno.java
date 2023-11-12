package chars.ca;

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
import buff.Nodamage;
import buff.Panic;
import buff.Wound;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c4300yuno extends c00main{
	int tick = 0;
	

	
	public c4300yuno(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1043;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		skill("c1043_s1");
		ARSystem.playSound((Player)player, "c1043s1");
		tick = 60;
		
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c1043_s2");
		ARSystem.playSound((Player)player, "c1043s2");
		for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
			makerSkill((LivingEntity)e,"1");
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.damage(8,player);
			ARSystem.giveBuff(target, new Panic(target), 160);
		}
	}
	
	

	@Override
	public boolean tick() {
		if(tick > 0) {
			tick--;
			player.setVelocity(player.getLocation().getDirection().setY(0).multiply(1.2));
			Entity e = ARSystem.boxSOne(player, new Vector(4,4,4), box.TARGET);
			if(e != null) {
				LivingEntity en = (LivingEntity)e;
				en.damage(3,player);
				Wound w = new Wound(en);
				w.setEffect("bload");
				w.setDelay(player, 20, 0);
				ARSystem.giveBuff(en, w, 200,1);
				tick = 0;
			}
		}
		return true;
	}
	
	

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			
		}
		return true;
	}
}
