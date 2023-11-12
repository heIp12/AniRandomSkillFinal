package chars.ca;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Noattack;
import buff.Nodamage;
import buff.Reflect;
import buff.Silence;
import chars.c.c00main;
import types.BuffType;


public class c1100yasuo extends c00main{
	int ticks = 0;
	int count = 0;
	
	public c1100yasuo(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1011;
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		count++;
		if(count == 40) {
			Rule.playerinfo.get(player).tropy(11,1);
		}
		if(isps) {
			skill("c"+number+"_s1-1");
		} else {
			skill("c"+number+"_s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c1011_s2");
		ARSystem.giveBuff(player, new Reflect(player), 30, 1);
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c1011_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c"+number+"_s4");
		if(cooldown[1] < cooldown[4]) cooldown[1] = cooldown[4];
		if(cooldown[2] < cooldown[4]) cooldown[2] = cooldown[4];
		if(cooldown[3] < cooldown[4]) cooldown[3] = cooldown[4];
		boolean airbone = false;
		for(Entity entity : player.getNearbyEntities(40, 40, 40)) {
			if(entity.getFallDistance() > 1.5 && entity.getType() != EntityType.ARMOR_STAND) {
				if(entity instanceof Player && ((Player) entity).getGameMode() == GameMode.SPECTATOR) {
					
				} else {
					airbone = true;
				}
			}
		}
		if(!airbone) {
			cooldown[4] = 0;
		}
		delay(()->{player.setFallDistance(0);},20);
		return true;
	}

	@Override
	public boolean tick() {
		ticks++;
		if(isps && Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
			for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
				buff.setTime(0);
			}
		}
		if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
			for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
				buff.setTime(0);
			}
		}
		if(ticks >= 80) {
			ticks = 0;
			if(Rule.buffmanager.GetBuffValue(player, "barrier")< player.getMaxHealth()/2) {
				Rule.buffmanager.selectBuffAddValue(player, "barrier",1);
			}
		}
		return true;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(!isps&& e.getDamage() >= 100) {
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c1011sp");
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.giveBuff(player, new Nodamage(player), 200);
				return false;
			}
		}
		return true;
	}
}
