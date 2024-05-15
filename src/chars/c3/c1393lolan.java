package chars.c3;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import buff.Ice;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c001nb;
import chars.c.c00main;
import chars.c.c39sakuya;
import chars.c2.c69himi;
import chars.ca.c1394matan;
import event.Skill;
import event.WinEvent;
import manager.Bgm;
import types.box;

import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class c1393lolan extends c00main{
	int light = 8;
	int maxlight = 8;
	int tick = 0;
	int card[] = new int[3];
	int sk8 = 0;
	int power = 0;
	int nd = 0;
	public int p[] = new int[]{0,0,0};
	int life = 0;
	int attack = 0;
	
	boolean iskill = false;
	
	boolean sk[] = {false,false,false,false,false,false,false,false,false};
	public c1393lolan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 3139;
		load();
		text();
		c = this;
		((Barrier)Rule.buffmanager.selectBuff(player, "barrier")).SetEffect("c139_b");
		ARSystem.playSound((Entity)player, "c139sp3", 1, 2);
		card[0] = 1;
		card[1] = 2;
		card[2] = 3;
		ItemRep();
		if(ch != null) {
			ccset(ch);
			this.p = ((c1392lolan)ch).p;
			life = ((c1392lolan)ch).life + 200;
			ARSystem.giveBuff(p, new TimeStop(p), 100);
			ARSystem.giveBuff(p, new Nodamage(p), 40);
			ARSystem.giveBuff(p, new Noattack(p), 40);
			if(AMath.random(Integer.parseInt(Text.get("c139:mt3"))) <= 1) {
				new G_Lvup(this, new ItemStack[] {ItemCreate.Name(ItemCreate.Item(293, 294), "c139:s5"),ItemCreate.Name(ItemCreate.Item(293, 296), "c139:s7")});
			} else {
				new G_Lvup(this, new ItemStack[] {ItemCreate.Name(ItemCreate.Item(293, 294),"c139:s5"),ItemCreate.Name(ItemCreate.Item(293, 295), "c139:s6")});
			}
		}
		inGame = true;
	}
	
	@Override
	public void select(String i) {
		if(i.equals("c139:s5")) {
			p[2] = 5;
		}
		else if(i.equals("c139:s6")) {
			power+=3;
			p[2] = 6;
		}
		else if(i.equals("c139:s7")) {
			spskillen("동화 - 『마탄의 사수』");
			Rule.c.put(player, new c1394matan(player, plugin, c));
			tpsdelay(()->{
				player.setMaxHealth(60);
				player.setHealth(60);
			},5);
		}
		if(p[0] == 1) {
			player.setMaxHealth(player.getMaxHealth() * 1.3);
			player.setHealth(player.getHealth()* 1.3);
			ARSystem.potion(player, 1, 10000, 1);
		}
		else if(p[0] == 2) {
			power += 2;
		}
		if(p[1] == 4) {
			power += 8;
		}
	}
	void ItemRep() {
		player.getInventory().setItem(0, ItemCreate.Name(ItemCreate.Item(293, 252 + card[0]),"&a"));
		player.getInventory().setItem(2, ItemCreate.Name(ItemCreate.Item(293, 252 + card[1]),"&a"));
		player.getInventory().setItem(4, ItemCreate.Name(ItemCreate.Item(293, 252 + card[2]),"&a"));
	}
	
	@Override
	public void setStack(float f) {
		power = (int)f;
	}
	
	boolean skillCast(int i) {
		if(i == 1) {
			for(int j=0;j<4;j++) if(cooldown[j] > 0) cooldown[j] -=1;
			ARSystem.playSound((Entity)player, "c139set");
			player.sendTitle("§c§l《 》", "",0,10,20);
			delay(()->{
				ARSystem.giveBuff(player, new Stun(player), 4);
				ARSystem.giveBuff(player, new Silence(player), 10);
				DamageRange(AMath.random(3)+1, "c139_s1", new Vector(3,5,3), "s","m");
			},5);
			
			delay(()->{
				ARSystem.giveBuff(player, new Stun(player), 4);
				ARSystem.giveBuff(player, new Silence(player), 10);
				DamageRange(AMath.random(2)+2, "c139_s1-2", new Vector(3,5,3), "h","m");
			},10);
			return true;
		}
		else if (i == 2) {
			ARSystem.playSound((Entity)player, "c139set");

			player.sendTitle("§c§l《 》", "",0,10,20);

			player.setVelocity(player.getLocation().getDirection().multiply(1.4).setY(0));
			delay(()->{
				ARSystem.giveBuff(player, new Silence(player), 10);
				DamageRange(AMath.random(3)+1, "c139_s2", new Vector(5,3,5), "h","");
				player.setVelocity(player.getLocation().getDirection().multiply(1.4).setY(0));
			},10);
			
			delay(()->{
				ARSystem.giveBuff(player, new Silence(player), 10);
				DamageRange(AMath.random(3)+1, "c139_s2-1", new Vector(5,3,5), "h","");
			},20);
			
			delay(()->{
				DamageBeam(AMath.random(3)+1, "c139_s2-2", 8, 5, "s", "");
				Location lc = player.getLocation().clone();
				lc.setPitch(0);
				player.teleport(ULocal.offset(lc, new Vector(6,0,0)));
				ARSystem.giveBuff(player, new Stun(player), 10);
				ARSystem.giveBuff(player, new Silence(player), 10);
			},30);
			return true;
		}
		else if(i == 3) {
			if(light >= 1) {
				light +=2;
				if(light > maxlight) light = maxlight;
				ARSystem.playSound((Entity)player, "c139set");
				player.sendTitle("§c§l《 》", "",0,10,20);
				delay(()->{
					ARSystem.giveBuff(player, new Stun(player), 10);
					ARSystem.giveBuff(player, new Silence(player), 10);
					int damage = AMath.random(7)+1;
					DamageBeam(damage, "c139_s3", 5, 3, "h","b");
					Rule.buffmanager.selectBuffValue(player, "barrier", damage);
				},5);
				return true;
			}
		}
		else if(i == 4) {
			if(light >= 2) {
				light -=2;
				player.sendTitle("§c§l《 》", "",0,10,20);
				ARSystem.playSound((Entity)player, "c139set");
				delay(()->{
					player.setVelocity(player.getLocation().getDirection().multiply(1.4).setY(0));
				},5);
				delay(()->{
					DamageBeam(AMath.random(2)+3, "c139_s4", 6, 4, "v","cc");
					ARSystem.giveBuff(player, new Silence(player), 10);
				},10);
				delay(()->{
					player.setVelocity(player.getLocation().getDirection().multiply(1.4).setY(0));
				},15);
				delay(()->{
					DamageBeam(AMath.random(2)+3, "c139_s4", 5, 3, "v","cc");
					ARSystem.giveBuff(player, new Silence(player), 10);
				},20);
				return true;
			}
		}
		else if(i == 5) {
			if(light >= 2) {
				light +=1;
				player.sendTitle("§c§l《 》", "",0,10,20);
				ARSystem.playSound((Entity)player, "c139set");
				if(light > maxlight) light = maxlight;
				ARSystem.giveBuff(player, new Nodamage(player), 10);
				ARSystem.giveBuff(player, new Stun(player), 40);
				ARSystem.giveBuff(player, new Silence(player), 40);
				delay(()->{
					DamageBeam(AMath.random(6)+4, "c139_s5", 6, 7, "s", "m");
				},10);
				return true;
			}
		}
		else if(i == 6) {
			if(light >= 2) {
				light -=2;
				player.sendTitle("§c§l《 》", "",0,10,20);
				ARSystem.playSound((Entity)player, "c139set");
				Location lc = player.getLocation();
				lc.setPitch(0);
				player.teleport(lc);
				delay(()->{
					ARSystem.giveBuff(player, new Stun(player), 10);
					ARSystem.giveBuff(player, new Silence(player), 10);
					DamageBeam(AMath.random(7)+1, "c139_s6", 16, 4, "v","s");
				},5);
				delay(()->{
					ARSystem.giveBuff(player, new Stun(player), 10);
					ARSystem.giveBuff(player, new Silence(player), 10);
					DamageBeam(AMath.random(7)+1, "c139_s6-2", 16, 4, "v","s");
				},10);
				delay(()->{
					ARSystem.giveBuff(player, new Stun(player), 10);
					ARSystem.giveBuff(player, new Silence(player), 10);
					DamageBeam(AMath.random(11)+4, "c139_s6-3", 30, 5, "h","s");
				},30);
				return true;
			}
		}
		else if(i == 7) {
			if(light >= 2) {
				light -=2;
				player.sendTitle("§c§l《 》", "",0,10,20);
				ARSystem.playSound((Entity)player, "c139set");
				delay(()->{
					ARSystem.giveBuff(player, new Silence(player), 15);
					ARSystem.giveBuff(player, new Stun(player), 15);
					DamageBeam(AMath.random(3)+4, "c139_s7", 3, 5, "s", "h");
				},8);
				delay(()->{
					ARSystem.giveBuff(player, new Silence(player), 10);
					ARSystem.giveBuff(player, new Stun(player), 10);
					DamageBeam(AMath.random(3)+4, "c139_s7-2", 3, 5, "s", "h");
				},16);
				return true;
			}
		}
		else if(i == 8) {
			if(light >= 3) {
				light -=3;
				sk8 = 60;
				return true;
			}
		}
		else if(i == 9) {
			if(light >= 4) {
				light -=4;
				player.sendTitle("§c§l《 》", "",0,10,20);
				ARSystem.playSound((Entity)player, "c139set");
				if(light > maxlight) light = maxlight;
				ARSystem.giveBuff(player, new Stun(player), 20);
				ARSystem.giveBuff(player, new Silence(player), 20);
				delay(()->{
					DamageBeam(AMath.random(13)+7, "c139_s9", 7, 5, "h", "d");
				},10);
				return true;
			}
		}
		return false;
	}
	
	public void DamageRange(float damag,String effect,Vector size,String sound,String type) {
		float damage = damag + power;
		ARSystem.playSound((Entity)player, "c139diceroll");
		player.sendTitle("§c§l《"+damage+"》", "",0,10,20);
		skill(effect);
		delay(()->{
			for(Entity e : ARSystem.box(player, size ,box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				en.setNoDamageTicks(0);
				en.damage(damage,player);
				if(p[1] == 3) {
					if(en.hasPotionEffect(PotionEffectType.SLOW)) {
						en.setNoDamageTicks(0);
						en.damage(1,player);
					}
					ARSystem.potion(en, 2, 60, 1);
				}
				if(damage > 8) ARSystem.playSound(en,"c139b"+sound);
				else ARSystem.playSound(en,"c139"+sound);
				if(type.equals("m")) {
					ARSystem.giveBuff(en, new Stun(en), 40);
					ARSystem.giveBuff(en, new Silence(en), 20);
				}
			}
		},5);
	}
	
	public void DamageBeam(float damag,String effect,float range, float size,String sound,String type) {
		float damage = damag + power;
		ARSystem.playSound((Entity)player, "c139diceroll");
		player.sendTitle("§c§l《"+damage+"》", "",0,10,20);
		skill(effect);
		delay(()->{
			for(Entity e : ARSystem.PlayerBeamV(player, range, size ,box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				if(type.equals("m")) {
					ARSystem.giveBuff(en, new Stun(en), 20);
					ARSystem.giveBuff(en, new Silence(en), 20);
					delay(()->{
						en.setNoDamageTicks(0);
						en.damage(damage,player);
						if(damage > 8) ARSystem.playSound(en,"c139b"+sound);
						else ARSystem.playSound(en,"c139"+sound);
					},20);
					return;
				}
				en.setNoDamageTicks(0);
				en.damage(damage,player);
				if(p[1] == 3) {
					if(en.hasPotionEffect(PotionEffectType.SLOW)) {
						en.setNoDamageTicks(0);
						en.damage(1,player);
					}
					ARSystem.potion(en, 2, 60, 1);
				}
				if(damage > 8) ARSystem.playSound(en,"c139b"+sound);
				else ARSystem.playSound(en,"c139"+sound);
				if(type.equals("b")) {
					if(Rule.buffmanager.OnBuffTime(en, "stun")) {
						Rule.buffmanager.selectBuffTime(en, "stun",0);
					}
					en.setVelocity(player.getLocation().getDirection().multiply(2));
				}
				if(type.equals("s")) {
					ARSystem.giveBuff(en, new Stun(en), 20);
				}
				if(type.equals("d")) {
					if(damage >= 10) {
						if(Rule.c.get(en) != null) {
							for(int i=0;i<10;i++) Rule.c.get(en).cooldown[i] += 5;
						}
					}
				}
				if(type.equals("cc")) {
					if(Rule.c.get(en) != null) {
						for(int i=0;i<10;i++) Rule.c.get(en).cooldown[i] += 1;
					}
				}
				if(type.equals("h")) {
					power++;
					delay(()->{
						power--;
					},400);
				}
			}
		},5);
	}
	@Override
	public boolean skill1() {
		if(skillCast(card[0])) {
			sk[card[0]-1] = true;
			ARSystem.giveBuff(player, new Silence(player), 14);
			skill("c139_c"+card[0]);
			int cards = card[0];
			while(cards == card[0] || cards == card[1] || cards == card[2]) cards = AMath.random(9);
			card[0] = cards;
			ItemRep();
		} else {
			cooldown[1] = 0;
		}
		return true;
	}

	
	@Override
	public boolean skill2() {
		if(skillCast(card[1])) {
			sk[card[1]-1] = true;
			ARSystem.giveBuff(player, new Silence(player), 14);
			skill("c139_c"+card[1]);
			int cards = card[1];
			while(cards == card[0] || cards == card[1] || cards == card[2]) cards = AMath.random(9);
			card[1] = cards;
			ItemRep();
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(skillCast(card[2])) {
			sk[card[2]-1] = true;
			ARSystem.giveBuff(player, new Silence(player), 14);
			skill("c139_c"+card[2]);
			int cards = card[2];
			while(cards == card[0] || cards == card[1] || cards == card[2]) cards = AMath.random(9);
			card[2] = cards;
			ItemRep();
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	int sk4 = 0;
	@Override
	public boolean skill4() {
		player.teleport(player.getLocation().clone().add(player.getLocation().getDirection().multiply(-4f)));
		ARSystem.playSound((Entity)player, "c139s4");
		light+=3;
		if(light > maxlight) light = maxlight;
		ARSystem.heal(player, (player.getMaxHealth() - player.getHealth()) * 0.5f);
		Rule.buffmanager.selectBuffValue(player, "barrier", 0);
		ARSystem.giveBuff(player, new Stun(player), 40);
		ARSystem.giveBuff(player, new Silence(player), 40);
		sk4 = 40;
		
		card[0] = AMath.random(9);
		card[1] = card[0];
		while(card[0] == card[1]) card[1] = AMath.random(9);
		card[2] = card[1];
		while(card[0] == card[2] || card[1] == card[2]) card[2] = AMath.random(9);
		ItemRep();
		return true;
	}

	int sps() {
		int i = 0;
		if(Rule.c.size() == 2) i++;
		boolean sk0 = true;
		for(int s=0;s<9;s++) if(!sk[s]) sk0 = false;
		if(sk0) i++;
		if(iskill) i++;
		if(power > 100000 && Rule.c.size() == 2) i+= 5;
		return i;
	}
	
	public boolean sp(Player target) {
		target.setGameMode(GameMode.SPECTATOR);

		Rule.playerinfo.get(player).tropy(139, 2);
		delay(()->{
			ARSystem.playSoundAll("c139sp2");
			skill("c139_i");
		},130);
		delay(()->{
			ARSystem.playSoundAll("c139sp3");
		},210);
		delay(()->{
			ARSystem.playSoundAll("c139sp4");
			skill("c139_sp0e");
			delay(()->{
				ARSystem.giveBuff(player, new TimeStop(player), 0);
				player.setGameMode(GameMode.SPECTATOR);
				skill("c139_sp0e2");
				skill("c139_sp0");
			},10);
		},260);

		delay(()->{
			Location loc = Map.getCenter();
			loc.setY(4);
			loc.setPitch(0);
			ARSystem.playSound((Entity)player, "c139set");
			target.setGameMode(GameMode.ADVENTURE);
			target.removePotionEffect(PotionEffectType.INVISIBILITY);
			target.teleport(loc);
			ARSystem.giveBuff(target, new TimeStop(target), 600);
			{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(5,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp0");
			}
			delay(()->{
				ARSystem.playSound((Entity)player, "c139diceroll");
				ARSystem.playSoundAll("c139sp5");
			},20);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(5,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp1");
				target.teleport(ULocal.offset(loc.clone(), new Vector(-0.1,0,0)));
			},60);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(5,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp2");
				target.teleport(ULocal.offset(loc.clone(), new Vector(-0.2,0,0)));
			},80);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(5,0,0)), lc);
				ARSystem.spellLocCast(player, lc.clone(), "c139_spm");
			},100);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.offset(lc, new Vector(-2,0,0));
				ARSystem.spellLocCast(player, lc, "c139_sp4");
				target.teleport(ULocal.offset(loc.clone(), new Vector(-0.3,0,0)));
			},110);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(-1,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp5");
				Location l = ULocal.offset(loc.clone(), new Vector(0,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},130);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(-1,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp6");
				Location l = ULocal.offset(loc.clone(), new Vector(0.1,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},150);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.offset(lc, new Vector(2,0,0));
				ARSystem.spellLocCast(player, lc, "c139_sp7");
				Location l = ULocal.offset(loc.clone(), new Vector(0,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},200);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.offset(lc, new Vector(-2,0,0));
				lc.setYaw(lc.getYaw()+180);
				ARSystem.spellLocCast(player, lc, "c139_sp8");
				Location l = ULocal.offset(loc.clone(), new Vector(-0.1,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},210);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.offset(lc, new Vector(3,0,0));
				ARSystem.spellLocCast(player, lc, "c139_sp9");
				Location l = ULocal.offset(loc.clone(), new Vector(0,0,0));
				target.teleport(l);
			},220);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(1.5,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp10");
				Location l = ULocal.offset(loc.clone(), new Vector(-0.2,0,0));
				target.teleport(l);
			},240);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(1,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp11");
				Location l = ULocal.offset(loc.clone(), new Vector(-0.5,0,0));
				target.teleport(l);
			},260);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(1,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp12");
				Location l = ULocal.offset(loc.clone(), new Vector(-1,0,0));
				target.teleport(l);
			},280);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.offset(lc, new Vector(-5,0,0));
				lc.setYaw(lc.getYaw()+180);
				ARSystem.spellLocCast(player, lc, "c139_sp13");
				Location l = ULocal.offset(loc.clone(), new Vector(-1,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},310);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(-5,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp3");

				Location l = ULocal.offset(loc.clone(), new Vector(-0.4,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},330);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.offset(lc, new Vector(3,0,0));
				ARSystem.spellLocCast(player, lc, "c139_sp13");
				Location l = ULocal.offset(loc.clone(), new Vector(-0.5,0,0));
				target.teleport(l);
			},350);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.offset(lc, new Vector(-3,0,0));
				lc.setYaw(lc.getYaw()+180);
				ARSystem.spellLocCast(player, lc, "c139_sp13");
				Location l = ULocal.offset(loc.clone(), new Vector(0,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},370);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(-1.5,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp14");
				Location l = ULocal.offset(loc.clone(), new Vector(0.2,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},390);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(-0.1,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp15");
				Location l = ULocal.offset(loc.clone(), new Vector(0.4,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},410);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(2,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_sp16");
				Location l = ULocal.offset(loc.clone(), new Vector(0.5,0,0));
				l.setYaw(l.getYaw()+180);
				target.teleport(l);
			},430);
			delay(()->{
				Location lc = loc.clone();
				lc = ULocal.lookAt(ULocal.offset(lc, new Vector(0.5,0,0)), lc);
				ARSystem.spellLocCast(player, lc, "c139_spd");
				ARSystem.potion(target, 14, 100, 100);
			},460);
			delay(()->{
				Skill.win(player);
				tpsdelay(()->{
					ARSystem.playSoundAll("c139db1");
				},60);
			},500);
		},330);
		return true;
	}
	@Override
	public boolean tick() {
		if(nd > 0) nd--;
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
			scoreBoardText.add("&c [Hp] " + AMath.round(player.getHealth(),2) + " / " + player.getMaxHealth());
			scoreBoardText.add("&c ["+Main.GetText("c3139:t1")+ "] " + s);
			if(power > 0) scoreBoardText.add("&c ["+Main.GetText("c3139:t2")+ "] " + power);
		}
		if(sk8 > 0) sk8--;
		if(sk4 > 0) sk4--;
		if(p[0] == 2 && battleTime > 400 && tk%20 == 0) {
			hpCost(0.5, true);
			scoreBoardText.add("&c ["+Main.GetText("c139:s2")+ "] " + AMath.round(battleTime*0.05, 2) + " / 20");
		}
		if(p[1] == 4) {
			if(tk%20 == 0)	scoreBoardText.add("&c ["+Main.GetText("c139:s4")+ "] " + AMath.round(life*0.05, 2));
			life--;
			if(life <= 0) {
				Skill.remove(player, player);
			}
		}
		if(p[2]== 5) {
			if(tk%20 == 0)	scoreBoardText.add("&c ["+Main.GetText("c139:s5")+ "] " + attack +" / 4");
		}
		
		if(tk%20 == 0) {
			if(sps() >= 3 && skillCooldown(0)) {
				spskillon();
				spskillen();
				WinEvent event = new WinEvent(player);
				Bukkit.getPluginManager().callEvent(event);
				ARSystem.playSoundAll("c139sp1");
				
				if(!event.isCancelled()) {
					Map.getMapinfo(1011);
					Bgm.setForceBgm("c139-3");
					Location loc = Map.getCenter();
					loc.setY(4);
					loc.setPitch(0);
					ARSystem.giveBuff(player, new TimeStop(player), 270);
					
					Player ps = player;
					for(Player pl : Rule.c.keySet()) if(pl != player) ps = pl;
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(Rule.c.get(p) != null && p != player) Rule.c.put(p, new c000humen(p, plugin, null));
						if(Rule.c.get(p) != null) ARSystem.giveBuff(p, new TimeStop(p), 400);
						if(p != player) p.teleport(loc);
						for(Player pl : Bukkit.getOnlinePlayers()) {
							if(p == player || pl == player || ps == pl || p == ps) continue;
							p.hidePlayer(pl);
						}
					}
					player.teleport(loc);
					sp(ps);
				}
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			iskill = true;
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() < 1) {
				light = maxlight;
				ARSystem.playSound(player, "c139kill");
				power++;
				delay(()->{
					power--;
				},600);
			}
			nd = 30;
			if(p[2] == 5) {
				attack++;
				if(attack >= 4) {
					attack = 0;
					ARSystem.giveBuff(((LivingEntity)e.getEntity()), new Ice(((LivingEntity)e.getEntity()),player), 60);
				}
			}
		} else {
			attack = 0;
			if(p[2] == 6) e.setDamage(e.getDamage() + 2);
			if(nd > 0) e.setDamage(e.getDamage() * 0.65f);
			if(p[1] == 4) e.setDamage(e.getDamage() * 0.7f);
			s_damage += e.getDamage();
			if(sk8 > 0) {
				sk8 = 0;
				LivingEntity en = ((LivingEntity)e.getDamager());
				ARSystem.giveBuff(en, new Stun(en), 70);
				ARSystem.giveBuff(en, new Silence(en), 70);
				Location loc = en.getLocation().clone();
				loc.setPitch(0);
				en.teleport(loc);
				
				player.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(5,0,0)), loc));

				ARSystem.giveBuff(player, new Stun(player), 0);
				ARSystem.giveBuff(player, new Silence(player), 20);
				ARSystem.giveBuff(player, new Nodamage(player), 40);
				delay(()->{
					ARSystem.giveBuff(player, new Stun(player), 10);
					ARSystem.giveBuff(player, new Silence(player), 10);
					
					Location lc = ULocal.offset(loc.clone(), new Vector(-3,0,0));
					lc.setYaw(lc.getYaw() + 180);
					player.teleport(lc);
					DamageRange(AMath.random(6)+3, "c139_s8", new Vector(7,5,7), "s","");
					ARSystem.playSound((Entity)player, "c139duelsword");
				},10);
				delay(()->{
					ARSystem.giveBuff(player, new Stun(player), 30);
					ARSystem.giveBuff(player, new Silence(player), 30);
					

					player.teleport(ULocal.offset(loc.clone(), new Vector(3,0,0)));
					DamageRange(AMath.random(6)+3, "c139_s8", new Vector(7,5,7), "s","");
					ARSystem.playSound((Entity)player, "c139duelsword");
				},20);
				delay(()->{
					ARSystem.giveBuff(player, new Stun(player), 10);
					ARSystem.giveBuff(player, new Silence(player), 10);

					Location lc = ULocal.offset(loc.clone(), new Vector(-3,0,0));
					lc.setYaw(lc.getYaw() + 180);
					player.teleport(lc);
					DamageRange(AMath.random(6)+3, "c139_s8", new Vector(7,5,7), "s","");
					ARSystem.playSound((Entity)player, "c139duelswordstrong");
					delay(()->{skill("c139_s8e0");},10);
				},40);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(sk4 > 0) {
				sk4 = 0;
				LivingEntity en = ((LivingEntity)e.getDamager());
				ARSystem.giveBuff(en, new Stun(en), 70);
				ARSystem.giveBuff(en, new Silence(en), 70);
				Location loc = en.getLocation().clone();
				loc.setPitch(0);
				en.teleport(loc);
				
				player.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(5,0,0)), loc));

				ARSystem.giveBuff(player, new Stun(player), 0);
				ARSystem.giveBuff(player, new Silence(player), 20);
				ARSystem.giveBuff(player, new Nodamage(player), 20);
				delay(()->{
					ARSystem.giveBuff(player, new Stun(player), 10);
					ARSystem.giveBuff(player, new Silence(player), 10);
					
					Location lc = ULocal.offset(loc.clone(), new Vector(-3,0,0));
					lc.setYaw(lc.getYaw() + 180);
					player.teleport(lc);
					DamageRange(1+AMath.random(7), "c139_s8", new Vector(7,5,7), "s","");
					ARSystem.playSound((Entity)player, "c139duelsword");
				},10);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c139sp5");
		return true;
	}
	@Override
	public String getBgm() {
		return "c139-2";
	}
}
