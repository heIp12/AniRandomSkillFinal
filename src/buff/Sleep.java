package buff;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import event.Skill;
import types.BuffType;
import util.AMath;
import util.Holo;

public class Sleep extends Buff{
	Location loc;
	boolean hitcancle = true;
	boolean silence = true;
	
	public Sleep(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.SILENCE);
		bufftype.add(BuffType.HEADCC);
		bufftype.add(BuffType.STUN);
		buffName = "sleep";
		onlyone = true;
		color = "§a";
	}
	
	public void isOne(boolean hitcancle) {
		this.hitcancle = hitcancle;
	}
	public void isSilence(boolean silence) {
		this.silence = silence;
	}
	public boolean onTicks() {
		if(!(target instanceof Player)) {
			if(loc != null) target.teleport(loc);
			loc = target.getLocation();
		}
		target.setVelocity(new Vector(0,0,0));
		
		if(tick%10 == 0 && tick > 0) {
			ARSystem.spellCast(ARSystem.RandomPlayer(), target, "sleep");
			if(!target.hasPotionEffect(PotionEffectType.BLINDNESS)) {
				target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100000,1));
			}
		}
		return false;
	}
	@Override
	public void last() {
		tick = 0;
		target.removePotionEffect(PotionEffectType.BLINDNESS);
		super.last();
	}
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		e.setCancelled(true);
		return false;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		return true;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(tick > 0) {
			e.setDamage(e.getDamage() * value);
			if(hitcancle) {
				stop();
			}
		}
		return true;
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(tick > 0 && silence) e.setCancelled(true);
		return false;
	}
}
