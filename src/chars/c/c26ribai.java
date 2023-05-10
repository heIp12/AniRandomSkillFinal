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
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import ars.Rule;
import event.Skill;

import util.MSUtil;

public class c26ribai extends c00main{
	int ticks = 0;
	int gas = 200;
	int maxgas = 200;
	int timer = 18;
	
	public c26ribai(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 26;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		gas = (int) f;
	}
	
	@Override
	public void info() {
		super.info();
		if(s_kill >= 3) {
			Rule.playerinfo.get(player).tropy(26,1);
		}
	}
	@Override
	public boolean skill1() {
		if(gas >= 20) {
			skill("c"+number+"_s1");
			gas-=20;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(gas >= 80) {
			if(isps) {
				skill("c"+number+"_s2p");
			} else {
				skill("c"+number+"_s2");
			}
			gas-=80;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(gas >= 10) {
			if(isps) {
				skill("c"+number+"_s3p");
			} else {
				skill("c"+number+"_s3");
			}
			gas-=10;
			if(ARSystem.isGameMode("zombie")) {
				gas-=30;
			}
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		gas = maxgas;
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.giveBuff(player, new Silence(player), 20);
		skill("c"+number+"_s4");
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p,Entity e) {
		if(timer > 0 && !isps && p != player) {
			timer = 0;
			spskillon();
			spskillen();
			skillmult += 0.5;
			maxgas = 400;
			gas = 400;
			ARSystem.giveBuff(player, new TimeStop(player), 40);
			skill("c"+number+"_sp");
			ARSystem.playSound((Entity)player, "c26sp");
		}
		if(e == player) {
			cooldown[4] -= 10;
		}
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			if(timer > 0) timer--;
			if(psopen && timer > 0) {
				scoreBoardText.add("&c ["+Main.GetText("c26:sk0")+ "]&f : "+ timer);
			}
			scoreBoardText.add("&c ["+Main.GetText("c26:t")+ "]&f : "+ gas+" / "+maxgas);
		}
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			Location lc = e.getEntity().getLocation().clone();
			Location plc = e.getDamager().getLocation().clone();
			lc.setPitch(0);
			plc.setPitch(0);
			
			float targetFaceAngle = lc.clone().getDirection().angle(new Vector(1, 1, 1));
			float diffAngle = lc.toVector().subtract(plc.toVector()).angle(new Vector(1, 1, 1));
			float diff = Math.abs(targetFaceAngle - diffAngle);
			if(diff <= 0.3) {
				e.setDamage(e.getDamage() * 2);
			}
			
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
			double damage = player.getLocation().distance(e.getEntity().getLocation());
			if(damage > 5) damage = 5;
			e.setDamage(e.getDamage() * (2.5 - (damage * 0.4 )));
			if(damage < 2) {
				ARSystem.spellCast(player, e.getEntity(), "bload");
				player.sendTitle("§4Critical!","§cDamage : " + (Math.round(e.getDamage()*10)/10.0) + " x"+(Math.round((2.5 - (damage * 0.4 ))*10)/10.0),10,10,20);
			}
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage() * 0.7);
		}
		return true;
	}
}
