package buff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import c.c00main;
import types.BuffType;

public class Cindaella extends Buff{
	Location loc;
	c00main rep;
	int issnake;
	
	public Cindaella(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.POWERUP);
		buffName = "cinda-ella";
		onlyone = true;
		isScore = true;
		isText = false;
		color = "§b";
		order = 10000;
	}
	public void save(c00main c00main) {
		rep = c00main;
	}
	
	@Override
	public void last() {
		if(target instanceof Player && ((Player) target).getGameMode() != GameMode.SPECTATOR) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.showPlayer(((Player)target));
				((Player)target).showPlayer(p);
			}
			if(Rule.c.get(target) != null) {
				float d = Rule.c.get((Player) target).s_damage;
				Rule.c.put((Player) target, rep.ccset(Rule.c.get(target)));
				target.setMaxHealth(Rule.c.get(target).getHp());
				Rule.c.get((Player) target).s_damage = d;
				ARSystem.playSound(target, "c57s3");
			}
		}
	}
	
	@Override
	public boolean onTicks() {
		if(target instanceof Player && ((Player) target).getGameMode() == GameMode.SPECTATOR) {
			tick++;
		}
		if(issnake > 0) issnake --;
		if(target instanceof Player) {
			if(issnake == 0 && ((Player) target).isSneaking() && target.getLocation().getPitch() > 85) {
				stop();
			}
		}

		return false;
	}
}
