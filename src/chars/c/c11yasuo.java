package chars.c;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;


public class c11yasuo extends c00main{
	int ticks = 0;
	int count = 0;
	
	public c11yasuo(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 11;
		load();
		text();
		if(ARSystem.isGameMode("lobotomy")) setcooldown[1] *= 0.5;
		if(ARSystem.isGameMode("lobotomy")) setcooldown[4] *= 0.3;
	}
	
	@Override
	public boolean skill1() {
		count++;
		if(count == 40) {
			Rule.playerinfo.get(player).tropy(11,1);
		}
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
		ARSystem.giveBuff(player, new Nodamage(player), 30);
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c"+number+"_s4");
		boolean airbone = false;
		boolean allairbone = false;
		if(ARSystem.getPlayerCount() < 3) {
			allairbone = true;
		}
		else {
			for(Player p :Rule.c.keySet()) {
				if (((LivingEntity)p).isOnGround() && p != player) {
					allairbone = true;
				} 
			}
		}
		if(!allairbone && skillCooldown(0) ) {
			spskillon();
			spskillen();
			s_score+= 300*ARSystem.getPlayerCount();
			
			ARSystem.giveBuff(player, new Silence(player), 160);
			ARSystem.giveBuff(player, new Nodamage(player), 160);
			
			skill("c"+number+"_sp");
			for(Entity entity : player.getNearbyEntities(500, 500, 500)) {
				if(entity != null && entity instanceof LivingEntity) {
					ARSystem.giveBuff((LivingEntity) entity, new Silence((LivingEntity) entity), 160);
					ARSystem.giveBuff((LivingEntity) entity, new Noattack((LivingEntity) entity), 160);
				}
			}
		} else {
			for(Entity entity : player.getNearbyEntities(40, 40, 40)) {
				if(entity.getFallDistance() > 1 && entity.getType() != EntityType.ARMOR_STAND) {
					if(entity instanceof Player && ((Player) entity).getGameMode() == GameMode.SPECTATOR) {
						
					} else {
						airbone = true;
					}
				}
			}
			if(!airbone) {
				cooldown[4] = 0;
			} else {
				Rule.buffmanager.selectBuffValue(player, "barrier",(float) (player.getMaxHealth()/4));
			}
		}
		return true;
	}

	@Override
	public boolean tick() {
		ticks++;
		
		if(ticks >= 80) {
			ticks = 0;
			if(Rule.buffmanager.GetBuffValue(player, "barrier")< player.getMaxHealth()/4) {
				Rule.buffmanager.selectBuffAddValue(player, "barrier",1);
			}
		}
		return true;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {

		}
		return true;
	}
}
