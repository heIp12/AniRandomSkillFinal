package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import util.AMath;
import util.ItemCreate;
import util.Text;

public class Item039 extends itemBase{
	public Item039(Player p){
		super(p);
		itemCode = 39;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(AMath.random(10) <= 4) {
			LivingEntity en = (LivingEntity) e.getDamager();
			en.setNoDamageTicks(0);
			en.damage(2,player);
		}
		return true;
	}
	

}
