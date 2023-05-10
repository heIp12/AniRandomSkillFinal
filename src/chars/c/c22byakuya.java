package chars.c;

import java.util.List;

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
import event.Skill;
import types.box;

import util.ULocal;
import util.MSUtil;

public class c22byakuya extends c00main{
	int ticks = 0;
	int flower = 12;

	
	public c22byakuya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 22;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		flower = (int) f;
	}
	
	@Override
	public boolean skill1() {
		if(flower >= 14) {
			flower-=14;
			skill("c"+number+"_s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.TARGET);
		Player p = null;
		String is = "";
		player.setFallDistance(0);
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c31ichigo) {
					is = "ichigo";
					p = (Player) e;
					break;
				}
			}
		}
		if(is.equals("ichigo")) {
			cooldown[2] = 0.1f;
			Location loc = p.getLocation();
			loc = ULocal.offset(loc,new Vector(-1,0,0));
			player.teleport(loc);
			ARSystem.playSound((Entity)player, "0miss");
		} else {
			if(flower >= 1) {
				flower--;
				skill("c"+number+"_s2");
			}
			
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(flower >= 100 && skillCooldown(0)) {
			spskillen();
			spskillon();
			flower-=100;
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 2));
			player.addPotionEffect(new PotionEffect(PotionEffectType.getById(5),400, 2));
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			player.setHealth(player.getMaxHealth());
			if(ARSystem.isGameMode("lobotomy")) {
				for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
					((LivingEntity)e).damage(333,player);
				}
			} else {
				skill("c"+number+"_sp");
			}
		}
		else if(flower >= 40) {
			flower-=40;
			skill("c"+number+"_s3");
		}
		return true;
	}

	@Override
	public boolean skill4() {
		if(player.getHealth() > 2) {
			player.setHealth(player.getHealth()-1);
			flower+=3+(skillmult + sskillmult);
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			if(flower >= 200) Rule.playerinfo.get(player).tropy(22,1);
			if(flower < 1000) flower+=(skillmult + sskillmult)*2;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c22:t")+ "]&f : "+ flower);
		}
		return false;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
		} else {

		}
		return true;
	}
}
