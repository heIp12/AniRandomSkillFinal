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
		if(!player.isSneaking()) {
			skill("c49_s3");
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(target != null) {
			ARSystem.spellCast(player, target, "c49_s4");
			ARSystem.giveBuff(player, new Nodamage(player), 40);
		} else {
			cooldown[4] = 0;
		}
		return true;
	}

	@Override
	public boolean tick() {
		dt = true;
		if(target != null  && target.getLocation().distance(player.getLocation()) > 15) target = null;
		if(tk%20 == 0 && target != null) scoreBoardText.add("&c ["+Main.GetText("c49:sk3")+ "] : "+ target.getName() + " : " + AMath.round(target.getLocation().distance(player.getLocation()),2));
		
		return true;
	}
	int i =0;
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.damage(1,player);
			if(i == 1) {
				int size = 1;
				if(isps) size= 2;
				for(int i=0;i<10;i++) if(cooldown[i] > 0)cooldown[i] -= 0.5*size;
				ARSystem.heal(player, 2);
				i = 0;
			}
		}
		if(n.equals("2")) {
			target.damage(2,player);
			if(i == 0) {
				int size = 1;
				if(isps) size= 2;
				for(int i=0;i<10;i++) if(cooldown[i] > 0)cooldown[i] -= 0.5*size;
				ARSystem.heal(player, 2);
				i = 1;
			}
		}
	}
	boolean dt = false;
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			target = (LivingEntity)e.getEntity();
			if(isps) {
				if(dt) {
					dt = false;
					target.setNoDamageTicks(0);
					target.damage(0.5f,player);
				}
				if(hit.get(e.getEntity())== null) {
					hit.put(target,1);
				} else {
					hit.put(target,1 + hit.get(target));
					player.sendTitle(target.getName(),hit.get(target)+ " / 30");
					if(hit.get(target) >= 30) {
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
