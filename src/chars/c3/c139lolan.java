package chars.c3;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Barrier;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import chars.c.c39sakuya;
import chars.c2.c69himi;
import mode.MLoboTomy;
import types.box;

import util.AMath;
import util.ItemCreate;
import util.ULocal;

public class c139lolan extends c00main{
	int light = 3;
	int maxlight = 3;
	int tick = 0;
	
	int card[] = new int[3];
	
	public c139lolan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 139;
		load();
		text();
		c = this;
		((Barrier)Rule.buffmanager.selectBuff(player, "barrier")).SetEffect("c139_b");
		Rule.playerinfo.get(player).tropy(139, 1);
		
		if(ARSystem.isGameMode("lobotomy")) {
			delay(()->{
				if(MLoboTomy.level > 5) {
					Rule.c.put(player, new c1393lolan(player, plugin, null));
				} else if(MLoboTomy.level > 3) {
					Rule.c.put(player, new c1392lolan(player, plugin, null));
				} else if(MLoboTomy.level > 1) {
					Rule.c.put(player, new c1391lolan(player, plugin, null));
				}
			},100);
		}
	}
	
	@Override
	public void setStack(float f) {
		s_damage = f;
	}

	@Override
	public boolean skill1() {
		if(light < 1) {
			cooldown[1] = 0;
			return false;
		}
		light -= 1;
		ARSystem.playSound((Entity)player, "c139set");
		player.sendTitle("§c§l《 》", "",0,10,20);
		delay(()->{
			DamageBeam(AMath.random(2)+1, "c139_a2", 5, 2, "v");
		},10);
		
		delay(()->{
			DamageRange(AMath.random(4), "c139_a3", new Vector(5,5,5), "h");
		},20);
		return true;
	}

	public void DamageRange(float damage,String effect,Vector size,String sound) {
		ARSystem.playSound((Entity)player, "c139diceroll");
		player.sendTitle("§c§l《"+damage+"》", "",0,10,20);
		delay(()->{
			skill(effect);
			for(Entity e : ARSystem.box(player, size ,box.TARGET)) {

					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(damage,player);
					if(damage > 8) ARSystem.playSound(en,"c139b"+sound);
					else ARSystem.playSound(en,"c139"+sound);

			}
		},5);
	}
	public void DamageBeam(float damage,String effect,float range, float size,String sound) {
		ARSystem.playSound((Entity)player, "c139diceroll");
		player.sendTitle("§c§l《"+damage+"》", "",0,10,20);
		delay(()->{
			skill(effect);
			for(Entity e : ARSystem.PlayerBeamV(player, range, size ,box.TARGET)) {

					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(damage,player);
					if(damage > 8) ARSystem.playSound(en,"c139b"+sound);
					else ARSystem.playSound(en,"c139"+sound);

			}
		},5);
	}
	
	@Override
	public boolean skill2() {
		if(light < 1) {
			cooldown[2] = 0;
			return false;
		}
		light -= 1;
		ARSystem.playSound((Entity)player, "c139diceroll");
		float damage = AMath.random(5);
		player.sendTitle("§c§l《"+damage+"》", "",0,10,20);
		Rule.buffmanager.selectBuffValue(player, "barrier", damage);
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(light < 3) {
			cooldown[3] = 0;
			return false;
		}
		light -= 3;
		ARSystem.playSound((Entity)player, "c139set");
		player.sendTitle("§c§l《 》", "",0,10,20);
		delay(()->{
			DamageRange(AMath.random(4)+1, "c139_a1", new Vector(5,5,5), "s");
		},10);
		player.sendTitle("§c§l《 》", "",0,10,20);
		delay(()->{
			DamageRange(AMath.random(4)+1, "c139_a1", new Vector(5,5,5), "s");
		},20);
		player.sendTitle("§c§l《 》", "",0,10,20);
		delay(()->{
			DamageBeam(AMath.random(3), "c139_a2", 5, 2, "v");
		},30);
		return true;
	}


	@Override
	public boolean skill4() {
		player.teleport(player.getLocation().clone().add(player.getLocation().getDirection().multiply(-4f)));
		ARSystem.playSound((Entity)player, "c139s4");
		light+=1;
		if(light > maxlight) light = maxlight;
		Rule.buffmanager.selectBuffValue(player, "barrier", 0);
		ARSystem.giveBuff(player, new Stun(player), 40);
		ARSystem.giveBuff(player, new Silence(player), 40);
		return true;
	}

	@Override
	public boolean tick() {
		if(light < maxlight) {
			tick++;
			if(tick > 200) {
				tick = 0;
				light++;
			}
		} else {
			tick = 0;
		}
		if(tk%20 == 0) {
			String s = "&e&l";
			for(int i = 0; i< maxlight; i++) {
				if(i < light) s+= "●";
				else s+= "○";
			}
			scoreBoardText.add("&c ["+Main.GetText("c139:ps")+ "] " + s);
		}
		if(tk%2 == 0 && s_damage >= 11 && player.getHealth() >= 1 && skillCooldown(0)) {
			ARSystem.giveBuff(player, new TimeStop(player), 40);
			delay(()->{
				spskillon();
				spskillen();
				Rule.c.put(player, new c1391lolan(player, plugin, this));
			},0);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.c.get(e.getEntity()) != null && Rule.c.get(e.getEntity()).number%1000 == 139) {
				s_damage -= e.getDamage();
			}
			if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() < 1) {
				light = maxlight;
				ARSystem.playSound(player, "c139kill");
				if(ARSystem.AniRandomSkill != null && Rule.c.get(e.getEntity()) != null) {
					s_damage += ARSystem.AniRandomSkill.time *0.2f;
				}
			}
		} else {
			s_damage += e.getDamage()*0.5f;
		}
		return true;
	}
	
	@Override
	public boolean skill9(){
		if(Rule.buffmanager.GetBuffTime(player,"panic") > 0) ARSystem.playSound((Entity)player, "c139db"+(AMath.random(5)+9));
		else ARSystem.playSound((Entity)player, "c139db"+AMath.random(3));
		return true;
	}
}
