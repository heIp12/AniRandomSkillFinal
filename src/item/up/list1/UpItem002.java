package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import util.Holo;
import util.ItemCreate;
import util.Text;

public class UpItem002 extends upitemBase{
	public UpItem002(Player p){
		super(p);
		itemCode = 2;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(e.getEntity().getLocation().distance(e.getDamager().getLocation()) > 20) {
			e.setDamage(e.getDamage() * 3);
			Holo.create(e.getEntity().getLocation(), "§c§l<HeadShot>", 100 , new Vector(0,0,0));
		}
		return true;
	}
}
