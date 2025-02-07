package mob;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Bload;
import buff.NoCC;
import buff.Nodamage;
import buff.TimeStop;
import event.Skill;
import manager.Bgm;
import types.box;
import util.AMath;
import util.Holo;
import util.NpcPlayer;
import util.ULocal;

public class M_Heal extends MobBase {
	public M_Heal(LivingEntity mob) {
		super(mob);
		ARSystem.giveBuff(mob, new Bload(mob), 10000, 1.5);
	}
}
