package c;

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
import buff.Stun;
import types.modes;
import util.MSUtil;

public class c14rocklee extends c00main{
	int ticks = 0;
	int spbuff = 0;
	float mult = 1.5f;
	
	public c14rocklee(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 14;
		MSUtil.setvar(player, "c14", 0);
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		spbuff = (int) f;
	}
	
	@Override
	public boolean skill1() {
		if(!isps) {
			skill("c"+number+"_s1");
		} else {
			skill("c"+number+"_s1sp");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(!isps) {
			skill("c"+number+"_s2");
		} else {
			skill("c"+number+"_s2sp");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}

	@Override
	public boolean tick() {
		ticks++;
		if(tk%20==0&&psopen && spbuff <= 100) {
			scoreBoardText.add("&c ["+Main.GetText("c14:sk0")+ "]&f : " + (1.0*spbuff/4)+"/ 25");
		}
		if(ticks%5==0) {
			if(MSUtil.isbuff(player, "c14_s3")) {
				if(isps) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 4));
					if(ticks%20 == 0) {
						ARSystem.heal(player,1);
					}
				} else {
					if(ticks%20 == 0) {
						if(spbuff < 100) {
							if(player.getHealth() > 1) {
								player.setHealth(player.getHealth()-1);
							} else {
								Rule.playerinfo.get(player).tropy(14,1);
								player.damage(3,player);
							}
						}
					}
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
				}
				spbuff++;
				if(spbuff > 100 && !isps) {
					spskillon();
					spskillen();
					skill("c"+number+"_sp");
				}
			}
		}
		ticks%=20;
		return false;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
			if(MSUtil.isbuff(player, "c14_s3")) {
				skill("c"+number+"_at");
				delay(new Runnable() {
					@Override
					public void run() {
						((LivingEntity)e.getEntity()).setNoDamageTicks(0);
					}
				}, 4);
			}
		} else {
			if(player.getHealth() > 5 && player.getHealth() - e.getFinalDamage() <= 0) {
				e.setCancelled(true);
				player.setHealth(5);
				ARSystem.giveBuff(player, new Nodamage(player), 20);
				return false;
			}
		}
		return true;
	}
}
