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
import buff.TimeStop;
import event.Skill;
import manager.AdvManager;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c46zero extends c00main{
	List<LivingEntity> entity = new ArrayList<LivingEntity>();
    boolean map[] = new boolean[4];
    int tick = 0;
    int tropy = 0;
    
	public c46zero(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 46;
		load();
		text();
		map[0] = map[1] = map[2] = map[3] = false;
	}
	

	@Override
	public boolean skill1() {
		skill("c46_s1");
		
		tropy++;
		if(tropy >= 30) {
			Rule.playerinfo.get(player).tropy(46,1);
		}
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c46_s2");
		tropy++;
		if(tropy >= 30) {
			Rule.playerinfo.get(player).tropy(46,1);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c46_s3");
		tropy++;
		if(tropy >= 30) {
			Rule.playerinfo.get(player).tropy(46,1);
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		tick++;
		if(tick%4== 0) {
			Location loc = player.getLocation().clone();
			loc.setY(0);
			for(int i =0; i<4; i++) {
				if(!map[i] && Map.getMapZ(i).distance(loc) < 5) {
					map[i] = true;
				}
			}
			if(!isps && map[0]&& map[1]&& map[2]&& map[3]&&skillCooldown(0)) {
				spskillen();
				spskillon();
				ARSystem.giveBuff(player, new TimeStop(player), 200);
				ARSystem.playSoundAll("c46sp");
				delay(
				()->{
					Rule.team.reload();
					for(Player p : Rule.c.keySet()) {
						if(p != player) {
							Rule.c.put(p, new c000humen(p, plugin, null));
						}
					}
				},200);
			}
		}
		return true;
	}

	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(target instanceof Player) {
				if(Rule.c.get(target) != null) {
					for(int k=0;k<100;k++) {
						int i = AMath.random(5);
						if(Rule.c.get(target).setcooldown[i] > 0 && Rule.c.get(target).cooldown[i] <= 0) {
							((Player)target).getInventory().setHeldItemSlot(i-1);
							break;
						}	
						
					}
				}
			}
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {


		} else {
			if(!entity.contains(e.getDamager())) {
				entity.add((LivingEntity) e.getDamager());
				e.setDamage(e.getDamage()*0.1f);
			}
		}
		return true;
	}
}
