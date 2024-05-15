package chars.ca;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_AdvSelect;
import ars.gui.G_HeddenSelect;
import ars.gui.G_Lvup;
import ars.gui.G_Nitory;
import ars.gui.G_Supply;
import ars.gui.solo.G_MapSelect;
import ars.gui.solo.G_MobSelect;
import buff.Buff;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.FixedDealEvent;
import mode.MNormal;
import types.BuffType;
import types.box;
import util.AMath;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.MagicSpellVar;
import util.Map;
import util.ULocal;

public class c0001nb extends c00main{
	int time = 0;
	
	public c0001nb(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = -1001;
		load();
		text();
		delay(()->{
			player.setFlySpeed(0.2f);
			player.setAllowFlight(true);
		},40);
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c-1001s1");
		skill("c-1001s1");
		skill("c-1001p1");
		for(int i =0; i<40;i++) {
			delay(()->{
				player.teleport(player.getLocation().clone().add(0,0.1,0));
			},i);
		}
		for(Entity e : ARSystem.box(player, new Vector(16,16,16), box.ALL)) {
			LivingEntity en = (LivingEntity)e;
			ARSystem.giveBuff(en, new Stun(en), 120);
			ARSystem.giveBuff(en, new Silence(en), 160);
		}
		ARSystem.giveBuff(player, new Stun(player), 180);
		ARSystem.giveBuff(player, new Silence(player), 180);
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c-1001s3");
		new G_MobSelect(player);
		return true;
	}
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c-1001s3");
		new G_Lvup(this, new ItemStack[] {
				ItemCreate.Name(ItemCreate.Item(381, 0), "c-1001:t1"),
				ItemCreate.Name(ItemCreate.Item(277, 0), "c-1001:t2"),
				ItemCreate.Name(ItemCreate.Item(277, 0), "c-1001:t3"),
				ItemCreate.Name(ItemCreate.Item(54, 0), "c-1001:t4"),
				ItemCreate.Name(ItemCreate.Item(54, 0), "c-1001:t5"),
				ItemCreate.Name(ItemCreate.Item(395, 0), "c-1001:t6"),
				ItemCreate.Name(ItemCreate.Item(395, 0), "c-1001:t7"),
				ItemCreate.Name(ItemCreate.Item(322, 0), "c-1001:t8"),
				ItemCreate.Name(ItemCreate.Item(368, 0), "c-1001:t9")
				});
		
		return true;
	}
	
	@Override
	public boolean tick() {
		time++;
		return true;
	}
	

	@Override
	public void select(String i) {
		ARSystem.playSound(player, "c-1001click");
		if(i.contains("c-1001:t")) {
			String num = i.replace("c-1001:t", "");
			if(num.equals("1")) {
				Map.sizeM(-1);
			} else if(num.equals("2")) {
				MNormal.RandomEvent(AMath.random(9));
			} else if(num.equals("3")) {
				MNormal.RandomEvent(AMath.random(11));
			} else if(num.equals("4")) {
				for(Player p : Rule.c.keySet()) {
					new G_Supply(p);
				}
			} else if(num.equals("5")) {
				for(Player p : Rule.c.keySet()) {
					new G_Nitory(p);
				}
			} else if(num.equals("6")) {
				new G_MapSelect(player);
			} else if(num.equals("7")) {
				new G_HeddenSelect(player);
			} else if(num.equals("8")) {
				for(Player p : Rule.c.keySet()) {
					ARSystem.heal(p, 1000);
					for(Buff b : Rule.buffmanager.getBuffs(p).getBuff()) {
						b.stop();
					}
					for(PotionEffect pe : p.getActivePotionEffects()) {
						p.removePotionEffect(pe.getType());
					}
				}
			} else if(num.equals("9")) {

				for(Player p : Rule.c.keySet()) {
					ARSystem.giveBuff(p, new Nodamage(p), 60);
					ARSystem.giveBuff(p, new Noattack(p), 60);
					p.teleport(player);
				}
			}
		}
	}
	
	@Override
	public boolean remove(Entity caster) {
		ARSystem.playSoundAll("c-1001remove");
		return super.remove(caster);
	}
	
	@Override
	public boolean skill9() {
		ARSystem.playSound((Entity)player, "c-1db");
		return true;
	}
	
	void error(Entity e) {
		if(time > 10) {
			time = 0;
			if(AMath.random(10) <= 1) ARSystem.playSound((Entity)player,"c-1001db1");
			ARSystem.playSound((Entity)player,"c-1001hit");
			ARSystem.spellLocCast(player, ULocal.lookAt(player.getLocation().clone(), e.getLocation()), "c-1001p");
			if(e.getLocation().distance(player.getLocation()) <= 4) {
				e.setVelocity(ULocal.lookAt(player.getLocation().clone(), e.getLocation()).getDirection().multiply(2.5));
			}
		}
	}
	
	@Override
	public boolean fixeddamage(FixedDealEvent e) {
		e.setDamage(0);
		e.setCancelled(true);
		error(e.getCaster());
		return super.fixeddamage(e);
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			e.setDamage(0);
			e.setCancelled(true);
			error(e.getDamager());
			return false;
		} else {
			if(Rule.c.get(e.getEntity()) != null && Rule.c.get(e.getEntity()).number%1000 == 2) {
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return super.entitydamage(e, isAttack);
	}
	
}
