
package chars.ca;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;
import util.Map;


public class c0300remuru extends c00main{
	int ticks = 0;
	int killtick = 0;
	int ps = 0;
	
	public c0300remuru(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1003;
		Rule.buffmanager.selectBuffValue(player, "plushp",10);
		load();
		text();
		ARSystem.playSound(player, "c3select");
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player,"c1003s2");
		for(int j=0;j<5;j++) {
			delay(()->{
				ARSystem.spellCast(player, "c1003_s1");
			},5+j*1);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.addBuff(player, new Stun(player) , 40);
		ARSystem.playSound((Entity)player,"c1003s3");
		delay(()->{skill("c1003_s2");},25);
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player,"c1003s1");
		ARSystem.spellCast(player, "c1003_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c3_s3");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.addBuff(target, new Stun(target), 4);
			target.setNoDamageTicks(0);
			target.damage(1,player);
		}
		if(n.equals("2")) {
			ARSystem.addBuff(target, new Stun(target), 30);
			ARSystem.addBuff(target, new Silence(target), 30);
			double hp = target.getMaxHealth()*0.25;
			player.sendTitle("","HP: "+Rule.buffmanager.GetBuffValue(player, "plushp")+"(+"+hp+")",0,40,20);
			Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) hp);
			if(target.getHealth() - hp <= 1) {
				Skill.remove(target, player);
			} else {
				target.setHealth(target.getHealth()-hp);
			}
		}
	}
	
	@Override
	public boolean tick() {
		if(ps > 0) {
			ps--;
		}
		if(killtick> 0) {
			killtick--;
		}
		if(MSUtil.isbuff(player, "c3_s3") && player.isSneaking()) {
			player.setVelocity(new Vector(0,0.01,0));
		}
		if(MSUtil.isbuff(player, "c3_s3") && !player.isSneaking()) {
			player.setVelocity(player.getLocation().getDirection().multiply(0.5));
		}
		if(isps && tk%5 == 1 && killtick <= 0) {
			Entity e = ARSystem.boxRandom(player, new Vector(999,999,999), box.TARGET);
			if(e != null) ARSystem.spellLocCast(player, e.getLocation(), "c1003_sp");
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c1003:ps")+ "]&f : "+ AMath.round(ps*0.05,2));
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c1003:sk0")+ "]&f : "+ AMath.round(killtick*0.05,2));
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player) {
			if(killtick <= 0 && !isps) {
				killtick = 60;
			} else if(!isps){
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c3m");
				player.setVelocity(new Vector(0,10,0));
				delay(()->{ARSystem.giveBuff(player, new TimeStop(player), 140);},10);
			}
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {

		if(isAttack) {

		}
		if(!isAttack) {
			if(ps > 0) {
				e.setDamage(e.getDamage()*0.4);
			}
			if(Rule.buffmanager.OnBuffValue(player, "plushp")) {
				skill("c3_sound1");
			}
		}
		return true;
	}
}
