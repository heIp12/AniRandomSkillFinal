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
import util.ItemCreate;
import util.Text;

public class Item026 extends itemBase{
	boolean ison = true;
	public Item026(Player p){
		super(p);
		itemCode = 26;
	}
	
	@Override
	public void onTick() {
		if(ison && player.getHealth()/player.getMaxHealth() <= 0.2) {
			ison = false;
			ARSystem.playSound((Entity)player, "0heal",0.8f,1);
			delay(()->{
				ARSystem.heal(player, player.getMaxHealth());
			},20);
		}
	}
	

}
