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
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item009 extends itemBase{
	public Item009(Player p){
		super(p);
		itemCode = 9;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(e.getEntity().getLocation().distance(e.getDamager().getLocation()) > 10) {
			int rd = (int)(e.getEntity().getLocation().distance(e.getDamager().getLocation())/10);
			if(AMath.random(100) <= rd*10) {
				ARSystem.playSound((Entity)player, "0miss");
				ARSystem.spellLocCast(player, ULocal.lookAt(player.getLocation().clone().add(0,1,0), e.getDamager().getLocation()), "c3086p");
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return super.onHit(e);
	}
}
