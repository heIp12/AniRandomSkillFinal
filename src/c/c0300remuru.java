
package c;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import event.Skill;
import types.box;
import types.modes;
import util.Local;
import util.MSUtil;
import util.Map;


public class c0300remuru extends c00main{
	int ticks = 0;
	
	public c0300remuru(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1003;
		Rule.buffmanager.selectBuffValue(player, "plushp",10);
		load();
		text();
		setcooldown[1]*=10;
		setcooldown[2]*=10;
		setcooldown[3]*=10;
		setcooldown[4]*=10;
		hp*=0.02;
		player.setMaxHealth(hp);
		player.setHealth(hp);
		ARSystem.playSound(player, "c3select");
	}
	
	@Override
	public boolean skill1() {
 		int i = 0;
		for(Entity e : ARSystem.box(player, new Vector(200,200,200), box.TARGET)) {
			i++;
			delay(()->{
				int damage = 100;
				if(ARSystem.gameMode == modes.LOBOTOMY) damage = 10;
				if(!isps) damage = 1;
				LivingEntity entity = (LivingEntity) e;
				ARSystem.spellLocCast(player, entity.getLocation(), "c1003_s1");
				if(entity.getHealth() <= damage) {
					player.sendTitle("","HP: "+Rule.buffmanager.GetBuffValue(player, "plushp")+"(+"+entity.getMaxHealth()*3+")",0,40,20);
					Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) (entity.getMaxHealth()*3));
					Skill.remove(entity,player);
					skill("c3_sound2");
				} else {
					entity.setNoDamageTicks(0);
					entity.damage(damage,player);
				}
			},i*4);
		}
		for(int j=0;j<20;j++) {
			delay(()->{
				ARSystem.spellLocCast(player, Map.randomLoc(), "c1003_s1");
				ARSystem.spellLocCast(player, Map.randomLoc(), "c1003_s1");
			},j*3);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.addBuff(player, new Nodamage(player) , 40);
		Rule.buffmanager.selectBuffAddValue(player, "plushp",5);
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c1003_s3");
		ticks = 20;
		return true;
	}
	
	@Override
	public boolean tick() {
		ticks++;

		if(MSUtil.isbuff(player, "c1003_s3") && player.isSneaking()) {
			player.setVelocity(new Vector(0,0.01,0));
		}
		if(MSUtil.isbuff(player, "c1003_s3") && !player.isSneaking()) {
			player.setVelocity(player.getLocation().getDirection().multiply(0.5));
		}
		
		
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.getTime() >= 240 && !isps) {
			spskillon();
			spskillen();
			hp*=50;
			if(ARSystem.gameMode == modes.LOBOTOMY) hp*=0.05;
			player.setMaxHealth(hp);
			player.setHealth(hp);
			setcooldown[1]*=0.1;
			setcooldown[2]*=0.1;
			setcooldown[3]*=0.1;
			setcooldown[4]*=0.1;
			if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[1]*=3;
			if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[2]*=4;
			if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[3]*=5;
		}
		ticks%=20;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {

		if(isAttack) {

		}
		if(!isAttack) {
			if(Rule.buffmanager.OnBuffValue(player, "plushp")) {
				skill("c3_sound1");
			}
		}
		return true;
	}
}
