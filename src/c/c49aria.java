package c;

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
import buff.Silence;
import event.Skill;
import manager.AdvManager;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c49aria extends c00main{
	HashMap<LivingEntity,Float> entity = new HashMap<LivingEntity,Float>();
	HashMap<LivingEntity,Integer> hit = new HashMap<LivingEntity,Integer>();
	boolean ps = false;
	LivingEntity target;
	int tropy = 0;
	
	public c49aria(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 49;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		skill("c49_s1");
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c49_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		cooldown[1] = 0;
		cooldown[2] = 0;
		skill("c49_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.spellCast(player, target, "c49_s4");
		ARSystem.giveBuff(player, new Nodamage(player), 40);
		return true;
	}

	@Override
	public boolean tick() {
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			target = (LivingEntity) e.getEntity();
			if(e.getEntity().getLocation().distance(player.getLocation()) > 8 && ps) {
				ps = false;
				ARSystem.giveBuff(target, new Silence(target), 60);
			} else if(e.getEntity().getLocation().distance(player.getLocation()) < 8 && !ps) {
				ps = true;
				e.setDamage(e.getDamage()*2);
			}
			if(isps) {
				if(hit.get(e.getEntity())== null) {
					hit.put(target,1);
				} else {
					hit.put(target,1 + hit.get(target));
					if(hit.get(target) > 8) {
						Skill.remove(target, player);
						tropy++;
						if(tropy >= 2) {
							Rule.playerinfo.get(player).tropy(49,1);
						}
					}
				}
			}
			if(entity.get(e.getEntity())== null) {
				entity.put(target, (float) e.getDamage());
			} else {
				entity.put(target, (float) e.getDamage() + entity.get(target));
				if(!isps &&entity.get(target) >= 12) {
					spskillon();
					spskillen();
					ARSystem.spellCast(player, target, "c49_sp");
				}
			}
		} else {

		}
		return true;
	}
}
