package chars.c;

import org.bukkit.GameMode;
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
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;

import util.AMath;
import util.MSUtil;
import util.Map;

public class c27ray extends c00main{
	int ticks = 0;
	int time = 0;
	int count = 0;
	Location loc;
	boolean isstart = false;
	
	public c27ray(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 27;
		load();
		text();
		if(player != null) loc = player.getLocation();
	}

	@Override
	public boolean skill1() {
		count++;
		if(isps) {
			ARSystem.spellLocCast(player, player.getLocation(), "c27_spi"+AMath.random(3));
		} else {
			if(player.isSneaking()) {
				skill("c"+number+"_s1_1");
			} else {
				skill("c"+number+"_s1");
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		count++;
		if(isps) {
			for(int i = 0; i < 4; i++) {
				ARSystem.spellLocCast(player, player.getLocation().add(new Vector(-3+AMath.random(6),-3+AMath.random(6),-3+AMath.random(6))), "c27_spi"+AMath.random(3));
			}
		} else {
			skill("c"+number+"_s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		count++;
		if(isps) {
			for(int i = 0; i < 15; i++) {
				ARSystem.spellLocCast(player, Map.randomLoc(player), "c27_spi"+AMath.random(3));
			}
		} else {
			skill("c"+number+"_s3");
			Rule.buffmanager.selectBuffValue(player, "barrier",5);
		}
		return true;
	}

	public void sp() {
		ARSystem.giveBuff(player, new TimeStop(player), 400);
		delay(new Runnable() {
			@Override
			public void run() {
				ARSystem.playSoundAll("c27sp1");
			}
		},0);
		delay(new Runnable() {
			@Override
			public void run() {
				ARSystem.playSoundAll("c27sp2");
				skill("c27_s3stop");
			}
		},100);
		delay(new Runnable() {
			@Override
			public void run() {
				skill("c27_sp");
				ARSystem.playSoundAll("c27sp3");
			}
		},220);
		
		delay(new Runnable() {
			@Override
			public void run() {
				Map.playeTp(player);
				for(int i = 0; i < 50; i++) {
					ARSystem.spellLocCast(player, Map.randomLoc(), "c27_spi"+AMath.random(3));
				}
			}
		},380);
	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player && count <= 6) {
			Rule.playerinfo.get(player).tropy(27,1);
		}
	}

	@Override
	public boolean tick() {
		if(!isstart){
			isstart = true;
			skill("c27");
			delay(()->{skill("c27_s3");},2 );
		}
		if(isps && player.getLocation().distance(loc) > 1) {
			loc = player.getLocation();
			ARSystem.spellLocCast(player,player.getLocation().add(new Vector(-2+AMath.random(4),-1,-2+AMath.random(4))), "c27_spi"+AMath.random(3));
		}
		if(tk%20==0 && !isps && player.getHealth()/player.getMaxHealth() <= 0.20) {
			time++;
			if(psopen&& !isps) {
				scoreBoardText.add("&c [Time]&f : "+ time + " / 15");
			}
			if(time > 14&& !isps) {
				time = 0;
				spskillon();
				spskillen();
				sp();
			}
		}
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
			if(!isps) e.setDamage(e.getDamage() * (2.0-player.getHealth()/player.getMaxHealth()));
		} else {

		}
		return true;
	}
}
