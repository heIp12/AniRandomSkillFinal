package chars.c2;

import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.Stun;
import chars.c.c00main;
import chars.c.c15tina;
import chars.ch.h003rentaro;
import types.box;

import util.AMath;

public class c56enju extends c00main{
	int p = 0;
	int count = 0;
	
	public c56enju(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 56;
		load();
		text();
		if(ARSystem.isGameMode("zombie")) {
			setcooldown[1] *= 1.5;
			setcooldown[2] *= 1.5;
			setcooldown[3] *= 1.5;
		}
	}
	

	@Override
	public boolean skill1() {
		skill("c56_s1");
		ARSystem.playSound((Entity)player, "c56s1");
		p = 20;
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c56_s2");
		ARSystem.playSound((Entity)player, "c56s2");
		p = 20;
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c56_s3");
		ARSystem.playSound((Entity)player, "c56s3");
		p = 20;
		return true;
	}
	
	@Override
	public boolean skill4() {
		cooldown[1] = cooldown[2] = cooldown[3] = 0;
		ARSystem.giveBuff(player, new Stun(player), 40);
		skill("c56_s4");
		ARSystem.playSound((Entity)player, "c56s3");
		return true;
	}

	@Override
	public boolean tick() {
		if(pt > 0) pt--;
		if(tk%20 == 0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c56:sk0")+ "] : "+ count +" / 14");
		}
		player.setFallDistance(0);
		if(p>0) {
			p--;
			if(player.isSneaking()) {
				if(cooldown[4] > 0) {
					cooldown[4] -= 5;
					if(isps) {
						cooldown[4] -=10;
					}
				}
				player.setSneaking(false);
				p = 0;
				ARSystem.playSound((Entity)player, "c56p"+AMath.random(4));
				skill("c56_p");
				count++;
				if(count >= 14 && !isps) {
					spskillen();
					spskillon();
					ARSystem.playSound((Entity)player, "c56sp");
				}
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(s_kill > 2) {
			Rule.playerinfo.get(player).tropy(56,1);
		}
	}
	int pt = 0;
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(player.getLocation().getY() > e.getEntity().getLocation().getY() && ARSystem.isTarget(e.getEntity(),player)) {
				((LivingEntity)e.getEntity()).setVelocity(new Vector(0,-0.4,0));
				((LivingEntity)e.getEntity()).setNoDamageTicks(0);
				if(pt <= 0) {
					pt = 1;
					if(!ARSystem.gameMode2) {
						((LivingEntity)e.getEntity()).damage(1,player);
					} else {
						((LivingEntity)e.getEntity()).damage(3,player);
					}
				}
				skill("c56_p2");
				if(isps) {
					ARSystem.giveBuff(((LivingEntity)e.getEntity()), new Stun(((LivingEntity)e.getEntity())), 4);
					ARSystem.giveBuff(((LivingEntity)e.getEntity()), new Silence(((LivingEntity)e.getEntity())), 4);
				}
			}
		} else {
			if(!player.isOnGround()) {
				if(!ARSystem.gameMode2) {
					e.setDamage(e.getDamage()*0.7);
				} else {
					e.setDamage(e.getDamage()*0.4);
				}
			}
			if(player.isOnGround()) p = 0;
			
		}
		return true;
	}
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c15tina) {
					is = "tina";
					break;
				}
				if(Rule.c.get(e) instanceof h003rentaro) {
					is = "rentaro";
					break;
				}
			}
		}
		
		if(is.equals("rentaro")) {
			ARSystem.playSound((Entity)player, "c56rentaro");
		} else if(is.equals("tina")) {
			ARSystem.playSound((Entity)player, "c56tina");
		} else {
			ARSystem.playSound((Entity)player, "c56db");
		}
		return true;
	}
}
