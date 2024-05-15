package chars.c3;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
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
import buff.Wound;
import chars.c.c00main;
import chars.c.c39sakuya;
import chars.c2.c69himi;
import chars.ca.c1394matan;
import event.Skill;
import types.box;

import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class c1392lolan extends c00main{
	int light = 6;
	int maxlight = 6;
	int tick = 0;
	int power = 0;
	public int p[] = new int[]{1,3,5};
	public int life = 600;
	
	public c1392lolan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2139;
		load();
		text();
		c = this;
		((Barrier)Rule.buffmanager.selectBuff(player, "barrier")).SetEffect("c139_b");
		ARSystem.playSound((Entity)player, "c139sp2", 1, 2);
		if(ch != null) {
			ccset(ch);
			this.p = ((c1391lolan)ch).p;
		}
		inGame = true;
		ARSystem.giveBuff(p, new TimeStop(p), 100);
		ARSystem.giveBuff(p, new Nodamage(p), 40);
		ARSystem.giveBuff(p, new Noattack(p), 40);
		
		if(AMath.random(Integer.parseInt(Text.get("c139:mt2"))) <= 1) {
			new G_Lvup(this, new ItemStack[] {ItemCreate.Name(ItemCreate.Item(293, 293), "c139:s3"),ItemCreate.Name(ItemCreate.Item(293, 296), "c139:s7")});
		} else {
			new G_Lvup(this, new ItemStack[] {ItemCreate.Name(ItemCreate.Item(293, 293), "c139:s3"),ItemCreate.Name(ItemCreate.Item(293, 292), "c139:s4")});
		}
	}
	
	@Override
	public void select(String i) {
		if(i.equals("c139:s3")) {
			p[1] = 3;
		}
		else if(i.equals("c139:s4")) {
			power+=8;
			p[1] = 4;
		} else if(i.equals("c139:s7")) {
			spskillen("동화 - 『마탄의 사수』");
			Rule.c.put(player, new c1394matan(player, plugin, c));
		}
		if(p[0] == 1) {
			player.setMaxHealth(player.getMaxHealth() * 1.3);
			player.setHealth(player.getHealth()* 1.3);
			ARSystem.potion(player, 1, 10000, 1);
		}
		else if(p[0] == 2) {
			power += 2;
		}
	}
	
	@Override
	public void setStack(float f) {
		s_damage = f;
	}

	@Override
	public boolean skill1() {
		light +=1;
		if(light > maxlight) light = maxlight;
		ARSystem.playSound((Entity)player, "c139set");

		player.sendTitle("§c§l《 》", "",0,10,20);
		delay(()->{
			player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(0.2));
			DamageBeam(AMath.random(5)+1, "c139_a7", 3, 3, "v","");
		},10);
		
		delay(()->{
			player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(0.2));
			DamageBeam(AMath.random(2), "c139_a7", 3, 3, "v","");
		},20);
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(light < 2) {
			cooldown[2] = 0;
			return false;
		}
		light -= 2;
		ARSystem.playSound((Entity)player, "c139diceroll");
		player.setVelocity(player.getLocation().getDirection().multiply(3).setY(0.2));
		delay(()->{
			player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(0.2));
			DamageBeam(AMath.random(3)+3, "c139_a8", 4, 2, "v", "s2");
		},10);
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
		Rule.buffmanager.selectBuffValue(player, "barrier", 3);
		player.sendTitle("§c§l《 》", "",0,10,20);
		sk3 = false;
		delay(()->{
			DamageBeam(AMath.random(4)+4, "c139_a9", 4, 3, "v","s3");
		},10);
		
		delay(()->{
			player.setVelocity(player.getLocation().getDirection().multiply(0.5).setY(0.2));
			DamageBeam(AMath.random(4)+4, "c139_a9", 4, 3, "v","s3");
		},20);
		
		delay(()->{
			if(sk3) {
				sk3 = false;
				player.setVelocity(player.getLocation().getDirection().multiply(0.7f).setY(0.2));
				DamageBeam(AMath.random(4)+4, "c139_a9", 4, 3, "v","s3");
			}
		},40);
		
		return true;
	}
	boolean sk3 = false;

	@Override
	public boolean skill4() {
		player.teleport(player.getLocation().clone().add(player.getLocation().getDirection().multiply(-4f)));
		ARSystem.playSound((Entity)player, "c139s4");
		light+=2;
		if(light > maxlight) light = maxlight;
		Rule.buffmanager.selectBuffValue(player, "barrier", 0);
		ARSystem.giveBuff(player, new Stun(player), 40);
		ARSystem.giveBuff(player, new Silence(player), 40);
		return true;
	}
	
	public void DamageBeam(float damag,String effect,float range, float size,String sound,String type) {
		float damage = damag + power;
		ARSystem.playSound((Entity)player, "c139diceroll");
		player.sendTitle("§c§l《"+damage+"》", "",0,10,20);
		delay(()->{
			skill(effect);
			for(Entity e : ARSystem.PlayerBeamV(player, range, size ,box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				en.setNoDamageTicks(0);
				en.damage(damage,player);
				if(p[1] == 3) {
					if(en.hasPotionEffect(PotionEffectType.SLOW)) {
						en.setNoDamageTicks(0);
						en.damage(1,player);
					}
					if(AMath.random(2) == 1)ARSystem.potion(en, 2, 60, 1);
				}
				if(damage > 8) ARSystem.playSound(en,"c139b"+sound);
				else ARSystem.playSound(en,"c139"+sound);
				if(type.equals("s2")) {
					for(int i=0;i<10;i++) if(cooldown[i] > 0) cooldown[i] -= 2f;
				}
				if(type.equals("s3")) {
					if(en.getHealth() / en.getMaxHealth() <= 0.5f) {
						sk3 = true;
					}
					Wound w = new Wound(en);
					w.setValue(1);
					w.setDelay(player,60,0);
					ARSystem.giveBuff(en, w, 180);
				}
			}
		},5);
	}
	@Override
	public boolean tick() {
		if(light < maxlight) {
			tick++;
			if(tick > 100) {
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
			scoreBoardText.add("&c ["+Main.GetText("c2139:ps")+ "] " + s);
			if(power > 0) scoreBoardText.add("&c ["+Main.GetText("c3139:t2")+ "] " + power);
		}
		if(p[0] == 2 && battleTime > 400 && tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c139:s2")+ "] " + AMath.round(battleTime*0.05, 2) + " / 20");
			hpCost(0.5, true);
		}
		if(p[1] == 4) {
			if(tk%20 == 0)	scoreBoardText.add("&c ["+Main.GetText("c139:s4")+ "] " + AMath.round(life*0.05, 2));
			life--;
			if(life <= 0) {
				Skill.remove(player, player);
			}
		}
		if(tk%2 == 0) {
			if(s_damage >= 100 && player.getHealth() >= 1 && skillCooldown(0)) {
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				delay(()->{
					spskillon();
					spskillen();
					Rule.c.put(player, new c1393lolan(player, plugin, this));
				},0);
			}
		}
		return true;
	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			s_damage += p.getMaxHealth()*0.4;
		}
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.c.get(e.getEntity()) != null && Rule.c.get(e.getEntity()).number%1000 == 139) {
				s_damage -= e.getDamage();
			}
			if(p[1] == 4) e.setDamage(e.getDamage() * 0.7f);
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
	protected boolean skill9() {
		if(Rule.buffmanager.GetBuffTime(player,"panic") > 0) ARSystem.playSound((Entity)player, "c139db"+(AMath.random(5)+9));
		else ARSystem.playSound((Entity)player, "c139db6");
		return true;
	}
	@Override
	public String getBgm() {
		return "c139-1";
	}
}
