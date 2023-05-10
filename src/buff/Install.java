package buff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import chars.c.c00main;
import chars.c2.c86iriya;
import types.BuffType;
import util.AMath;

public class Install extends Buff{
	c86iriya rep;
	int issnake;
	int tk = 200;
	public Install(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.POWERUP);
		buffName = "Install";
		onlyone = true;
		isScore = true;
		isText = false;
		color = "§b";
		order = 10000;
	}
	public void save(c86iriya c00main,float Mp) {
		rep = c00main;
		value = Mp;
	}
	
	@Override
	public void last() {
		if(target instanceof Player && ((Player) target).getGameMode() != GameMode.SPECTATOR) {
			if(Rule.c.get(target) != null) {
				double hp = target.getHealth()/target.getMaxHealth();
				Rule.c.put((Player) target, rep.ccset(Rule.c.get(target)));
				Rule.c.get(target).setStack((float) value);
				ARSystem.playSound(target, "c86sc");
				target.setHealth(target.getMaxHealth() * hp);
				Rule.buffmanager.selectBuffValue(target, "barrier", (float) 0);
			}
		}
	}
	
	@Override
	public boolean onTicks() {
		if(target instanceof Player && ((Player) target).getGameMode() == GameMode.SPECTATOR) {
			tick++;
		}
		if(tk <= 0 && AMath.random(40) == 2) {
			ARSystem.playSound((Player)target, "c86p"+AMath.random(13));
			tk = 100+AMath.random(200);
		}
		tk--;
		if(tick%5 == 0) {
			value-=2;
			if(value <= 0) {
				stop();
			}
		}
		return false;
	}
}
