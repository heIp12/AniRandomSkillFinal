package c;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import net.minecraft.server.v1_12_R1.Entity;
import types.modes;
import util.AMath;
import util.MSUtil;

public class c0700shana extends c00main{

	public c0700shana(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1007;
		load();
		text();
		ARSystem.playSound(player, "c7select");
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		for(int i=0;i<10;i++) {
			delay(new Runnable() {
				@Override
				public void run() {
					skill("c7_ps");
					ARSystem.heal(player,1);
				}
			}, i);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}

	@Override
	public void PlayerDeath(Player p, org.bukkit.entity.Entity e) {
		if(e == player && !isps && !spben) {
			spskillon();
			spskillen();
		}
	}
	
	@Override
	protected boolean skill9() {
		player.getWorld().playSound(player.getLocation(), "c7db", 1, 1);
		return false;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			delay(new Runnable() {
				@Override
				public void run() {
					((LivingEntity)e.getEntity()).setNoDamageTicks(0);
				}
			}, 3);
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() + 2);
			if(ARSystem.gameMode == modes.LOBOTOMY) ARSystem.heal(player, 1);
			skill("c7_ps");
			ARSystem.spellCast(player, e.getEntity(), "c1007p");
			if(isps &&  e.getEntity() != null && e.getEntity() instanceof LivingEntity) {
				ARSystem.addBuff((LivingEntity) e.getEntity(), new Silence((LivingEntity) e.getEntity()), 2);
			}
		} else {

	
		}
		return true;
	}
}
