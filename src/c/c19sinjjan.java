
package c;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import buff.Stun;
import buff.TimeStop;
import types.box;
import types.modes;
import util.AMath;
import util.MSUtil;
import util.Text;

public class c19sinjjan extends c00main{
	List<Entity> sk3 = new ArrayList<Entity>();
	boolean sk3on = false;
	int ticks = 0;
	int attack = 0;
	int damage = 1;
	int count = 0;
	
	public c19sinjjan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 19;
		load();
		text();
		
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
		return true;
	}

	@Override
	public boolean skill3() {
		List<Entity> el = ARSystem.box(player,new Vector(10,6,10),box.TARGET);
		boolean istarget = false;
		
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				String s = Main.GetText("c"+Rule.c.get(e).getCode()+":tag");
				if(s.indexOf("tg1") != -1) {
					istarget = true;
					String st = ""+AMath.random(3);
					if(st.equals("1")) st= "";
					ARSystem.playSound((Entity)player, "c19ht"+st);
					ARSystem.giveBuff((LivingEntity) e, new Stun((LivingEntity) e), 60);
					ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 60);
					break;
				}
			}
		}
		if(!istarget) {
			cooldown[3] = 0;
		}
		return true;
	}

	@Override
	protected boolean skill9(){
		count++;
		if(count >= 20) {
			Rule.playerinfo.get(player).tropy(19,1);
		}
		String s = ""+AMath.random(3);
		if(s.equals("1")) s= "";
		player.getWorld().playSound(player.getLocation(), "c"+number+"db"+s, 1, 1);

		return false;
	}
	
	@Override
	public boolean tick() {
		ticks++;
		if(MSUtil.isbuff(player, "c19_s2_1")) {
			sk3on = true;
			List<Entity> el = ARSystem.box(player, new Vector(12,4,12),box.TARGET);
			for(Entity et : el) {
				if(sk3.indexOf(et) == -1) {
					sk3.add(et);
				}
			}
			for(Entity et : sk3) {
				ARSystem.giveBuff((LivingEntity) et, new Stun((LivingEntity) et), 1);
			}
		} else if(sk3on){
			sk3on = false;
			for(Entity et : sk3) {
				((LivingEntity)et).setNoDamageTicks(0);
				((LivingEntity)et).damage(4, player);
				
			}
			sk3.clear();
		}
		ticks%=10;
		return false;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 0.3);
			if(player.getNoDamageTicks() <= 0) {
				attack++;
			}
			if(damage > 1) {
				e.setDamage(e.getDamage()*damage);
				damage = 1;
			}
			if(attack == 11) {
				spskillon();
			}
			if(attack >= 12&& skillCooldown(0)&&!spben) {
				spskillen();
				ARSystem.playSound((Entity)player, "c19sp");
				attack = 0;
				ARSystem.heal(player,9999);
				cooldown[1] = 0;
				cooldown[2] = 0;
				cooldown[3] = 0;
				damage = 2;
			}
			if(player.isSprinting()) {
				if(AMath.random(2) == 2) {
					ARSystem.playSound((Entity)player, "c19p");
				} else {
					ARSystem.playSound((Entity)player, "c19ho");
				}
				e.setDamage(e.getDamage()/2);
				player.setSprinting(false);
			}
		}
		return true;
	}
}
