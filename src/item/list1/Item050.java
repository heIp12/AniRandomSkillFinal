package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.TimeStop;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item050 extends itemBase{
	int cd = 0;
	float damage = 1.8f;
	double minhp = 999;
	
	boolean c = false;
	public Item050(Player p){
		super(p);
		itemCode = 50;
		setcooldown = 50;
	}
	
	@Override
	public void itemRemove() {
		player.setHealth(player.getHealth()*0.5);
		player.setMaxHealth(player.getMaxHealth()*0.5);
		Rule.c.get(player).hp = (float)player.getMaxHealth();
		super.itemRemove();
	}
	
	@Override
	protected void onStart() {
		player.setMaxHealth(player.getMaxHealth()*2);
		Rule.c.get(player).hp = (float)player.getMaxHealth();
		ARSystem.heal(player, player.getMaxHealth()*0.5f);
		damage = 1.8f;
		

		if(Rule.c.get(player).number == 15 || Rule.c.get(player).number == 56 || Rule.c.get(player).number == 65) {
			c = true;
			setcooldown = 30;
		}
		super.onStart();
	}
	
	@Override
	protected void onTick() {
		if(cd > 0) {
			cd--;
			for(int i =0; i<10; i++) if(Rule.c.get(player).cooldown[i] > 0) Rule.c.get(player).cooldown[i] -= 0.4;
		}

		if(!c) {
			if(timer%60 == 0 && Rule.c.get(player) != null) {
				if(Rule.c.get(player).hpCost(player.getMaxHealth()*0.04, true)) {
					if(player.getHealth() > player.getMaxHealth()*0.96) {
						player.setHealth(player.getMaxHealth()*0.96f);
					}
					player.setMaxHealth(player.getMaxHealth()*0.96f);
					Rule.c.get(player).hp = (float)player.getMaxHealth();
				}
			}
			
			if(player.getHealth() <= minhp) {
				minhp = player.getHealth();
			} else {
				if(minhp < 1) {
					ARSystem.Death(player, player);
				} else {
					player.setHealth(minhp);
				}
			}
		} else {
			if(timer > 2400) {
				ARSystem.Death(player, player);
			}
		}
		super.onTick();
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage()*damage);
		damage -= 0.01* e.getDamage();
		return super.onAttack(e);
	}
	
	
	@Override
	public String getActionbar() {
		if(cooldown > 0) {
			return super.getActionbar();
		}
		if(c) {
			return "§c§l<§6damage : §e" +AMath.round(damage*100,1)+":"+AMath.round((2400-timer)*0.05,1) +"§c§l>";
		}
		return "§c§l<§6damage : §e" +AMath.round(damage*100,1) +"§c§l>";
	}
	

	@Override
	public boolean skillCast(){
		ARSystem.spellCast(player, "item50");
		if(!c) {
			ARSystem.giveBuff(player, new Panic(player), 100);
			ARSystem.giveBuff(player, new Dancing(player), 100);
		}
		cd = 100;
		return false;
	}
}
