package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import util.ItemCreate;
import util.Text;

public class Item017 extends itemBase{
	public Item017(Player p){
		super(p);
		itemCode = 17;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * 0.7f);
		return true;
	}
	

}
