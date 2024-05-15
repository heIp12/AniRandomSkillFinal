package chars.c3;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Lvup;
import buff.Barrier;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import chars.c.c39sakuya;
import chars.c2.c69himi;
import chars.ca.c1394matan;
import types.box;

import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class c1391lolan extends c00main{
	int light = 5;
	int maxlight = 5;
	int tick = 0;
	int power = 0;
	public int p[] = new int[]{1,3,5};
	
	public c1391lolan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		picksound = false;
		number = 1139;
		load();
		text();
		c = this;
		((Barrier)Rule.buffmanager.selectBuff(player, "barrier")).SetEffect("c139_b");
		ARSystem.playSound((Entity)player, "c139sp1", 1, 2);
		if(ch != null) ccset(ch);
		inGame = true;
		ARSystem.giveBuff(p, new TimeStop(p), 100);
		ARSystem.giveBuff(p, new Nodamage(p), 40);
		ARSystem.giveBuff(p, new Noattack(p), 40);
		if(AMath.random(Integer.parseInt(Text.get("c139:mt1"))) <= 1) {
			new G_Lvup(this, new ItemStack[] {ItemCreate.Name(ItemCreate.Item(293, 290), "c139:s1"),ItemCreate.Name(ItemCreate.Item(293, 296), "c139:s7")});
		} else {
			new G_Lvup(this, new ItemStack[] {ItemCreate.Name(ItemCreate.Item(293, 290), "c139:s1"),ItemCreate.Name(ItemCreate.Item(293, 291), "c139:s2")});
		}
	}
	@Override
	public void select(String i) {
		if(i.equals("c139:s1")) {
			sskillmult += 0.3;
			player.setMaxHealth(player.getMaxHealth() * 1.15);
			player.setHealth(player.getHealth()* 1.15);
			ARSystem.potion(player, 1, 10000, 1);
			p[0] = 1;
		}
		else if(i.equals("c139:s2")) {
			power+=2;
			p[0] = 2;
		} else if(i.equals("c139:s7")) {
			spskillen("동화 - 『마탄의 사수』");
			Rule.c.put(player, new c1394matan(player, plugin, c));
			tpsdelay(()->{
				player.setMaxHealth(20);
				player.setHealth(20);
			},5);
		}
	}
	@Override
	public void setStack(float f) {
		s_damage = f;
	}

	@Override
	public boolean skill1() {
		if(light < 2) {
			cooldown[1] = 0;
			return false;
		}
		light -= 2;
		ARSystem.playSound((Entity)player, "c139set");

		player.sendTitle("§c§l《 》", "",0,10,20);
		delay(()->{
			DamageRange(AMath.random(3), "c139_a4", new Vector(4,4,4), "s","s1");
		},10);
		
		delay(()->{
			DamageRange(AMath.random(3), "c139_a5", new Vector(5,5,5), "s","s1");
		},25);
		return true;
	}

	public void DamageRange(float damag,String effect,Vector size,String sound,String type) {
		float damage = damag + power;
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
				if(type.equals("s1")) {
					Rule.buffmanager.selectBuffValue(player, "barrier", 5);
				}
				if(type.equals("s2")) {
					if(en.getHealth() - damage < 1) {
						ARSystem.heal(player, 5);
					}
				}
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
		s3 = 40;
		s3a = true;
		
		ARSystem.playSound((Entity)player, "c139set");
		return true;
	}
	

	int s3 = 0;
	boolean s3a = false;
	@Override
	public boolean skill3() {
		if(light < 4) {
			cooldown[3] = 0;
			return false;
		}
		light -= 4;
		ARSystem.playSound((Entity)player, "c139set");

		player.sendTitle("§c§l《 》", "",0,10,20);
		delay(()->{
			DamageRange(AMath.random(6), "c139_a5", new Vector(5,5,5), "s","s2");
		},10);
		
		delay(()->{
			DamageRange(AMath.random(8)+2, "c139_a4", new Vector(4,4,4), "s","s2");
		},25);
		return true;
	}


	@Override
	public boolean skill4() {
		player.teleport(player.getLocation().clone().add(player.getLocation().getDirection().multiply(-4f)));
		ARSystem.playSound((Entity)player, "c139s4");
		light+=2;
		if(light > maxlight) light = maxlight;
		Rule.buffmanager.selectBuffValue(player, "barrier", 0);
		ARSystem.giveBuff(player, new Stun(player), 40);
		s3 = 0;
		s3a = false;
		ARSystem.giveBuff(player, new Silence(player), 40);
		return true;
	}

	@Override
	public boolean tick() {
		if(s3 > 0) {
			s3--;
			if(s3 <=0) s3a = false;
		}
		if(light < maxlight) {
			tick++;
			if(tick > 160) {
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
			scoreBoardText.add("&c ["+Main.GetText("c1139:ps")+ "] " + s);
			if(power > 0) scoreBoardText.add("&c ["+Main.GetText("c3139:t2")+ "] " + power);
		}
		if(p[0] == 2 && battleTime > 400 && tk%20 == 0) {
			hpCost(0.5, true);
		}
		if(tk%2 == 0) {
			if(s_damage >= 40 && player.getHealth() >= 1 && skillCooldown(0)) {
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				delay(()->{
					spskillon();
					spskillen();
					Rule.c.put(player, new c1392lolan(player, plugin, this));
				},0);
			}
		}
		return true;
	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			s_damage += p.getMaxHealth()*0.2;
		}
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
			if(s3 > 0) {
				e.setDamage(0);
				e.setCancelled(true);
				if(s3a) {
					s3a = false;
					delay(()->{
						DamageRange(AMath.random(4)+1, "c139_a5", new Vector(4,4,4), "s","");
					},10);
				}
				return false;
			}
			s_damage += e.getDamage()*0.5f;
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		if(Rule.buffmanager.GetBuffTime(player,"panic") > 0) ARSystem.playSound((Entity)player, "c139db"+(AMath.random(5)+9));
		else ARSystem.playSound((Entity)player, "c139db"+(AMath.random(2)+3));
		return true;
	}
}
