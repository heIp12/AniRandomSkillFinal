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
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;

import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c4200touno extends c00main{
	int cr = 1;
	int p_tick = 0;
	
	public c4200touno(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1042;
		load();
		text();
		
	}
	

	@Override
	public boolean skill1() {
		skill("c1042_s1");
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c1042_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c1042_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.giveBuff(player, new Silence(player), 40);
		skill("c1042_s4");
		return false;
	}
	
	@Override
	public boolean tick() {
		if(p_tick >40) {
			 p_tick--;
		}
		return true;
	}


	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
			if(p_tick <=0 && AMath.random(50) == cr) {
				ARSystem.giveBuff((LivingEntity) e.getEntity(), new TimeStop((LivingEntity) e.getEntity()), 40);
				delay(()->{
					ARSystem.playSound(e.getEntity(), "c1042p");
				},20);
				delay(()->{
					ARSystem.playSound(e.getEntity(), "0bload");
					Skill.remove(e.getEntity(), player);
				},40);
			}
		} else {

		}
		return true;
	}
}
