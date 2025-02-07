package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class Item019 extends itemBase{
	public Item019(Player p){
		super(p);
		itemCode = 19;
		setcooldown = 10;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		delay(()->{
			if(player.getHealth()/player.getMaxHealth() <= 0.5 && isCooldown()) {
				ARSystem.playSound((Entity)player, "0heal", 0.5f,2);
				Rule.buffmanager.selectBuffAddValue(player, "barrier", 14);
				delay(()->{
					Rule.buffmanager.selectBuffAddValue(player, "barrier", -14);
					if(Rule.buffmanager.GetBuffValue(player, "barrier") <= 0) {
						Rule.buffmanager.selectBuffValue(player, "barrier", 0);
					}
				},100);
			}
		},0);
		return super.onHit(e);
	}
	
	@Override
	protected void onTick() {
		if(timer%80 == 0 && Rule.c.get(player).number%1000 == 86) {
			ARSystem.playSound((Entity)player, "c86p"+AMath.random(13));
			Rule.buffmanager.selectBuffAddValue(player, "barrier", 1);
		}
		if(timer%2 == 0 && Rule.c.get(player).number%1000 == 86) {
			if(Rule.buffmanager.GetBuffTime(player, "Install") > 0) {
				Rule.buffmanager.selectBuffAddValue(player, "Install", 1);
			}
		}
	}
}
