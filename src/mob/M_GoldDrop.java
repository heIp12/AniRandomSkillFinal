package mob;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.NoCC;
import buff.Nodamage;
import buff.TimeStop;
import event.Skill;
import manager.Bgm;
import util.AMath;
import util.Holo;
import util.NpcPlayer;
import util.ULocal;

public class M_GoldDrop extends MobBase {
	int gold = 100;
	public M_GoldDrop(LivingEntity mob,int gold) {
		super(mob);
		this.gold = gold;
	}


	
	@Override
	public void onDeath(LivingEntity killer) {
		if(Rule.playerinfo.get(killer) != null) {
			Holo.create(entity.getLocation().clone().add(0,0.5,0),"§e§l+" + gold, 40, new Vector(0,0.1,0));
			Rule.playerinfo.get(killer).gold += gold;
			gold = 0;
		}
	}
}
