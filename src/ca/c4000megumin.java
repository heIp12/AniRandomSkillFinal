package ca;

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
import buff.Silence;
import buff.Stun;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c4000megumin extends c00main{
	
	int c = 0;
	public c4000megumin(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1040;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		return true;
	}

	@Override
	public boolean tick() {
		if(!isps) {
			spskillen();
			spskillon();
			ARSystem.playSoundAll("c40s4");
			delay(()->{
				ARSystem.playSoundAll("c40e41");
				for(Player p :Bukkit.getOnlinePlayers()) {
					Skill.remove(p, p);
				}
				ARSystem.GameStop();
			},40);
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
