package item.up.list1;

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

public class UpItem026 extends upitemBase{
	boolean ison = true;
	public UpItem026(Player p){
		super(p);
		itemCode = 26;
	}

	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(ison &&player.getHealth()- e.getDamage() <= 1) {
			ison = false;
			ARSystem.playSound((Entity)player, "0heal",0.8f,1);
			ARSystem.heal(player, player.getMaxHealth());
			e.setCancelled(true);
			e.setDamage(0);
		}
		return super.onHit(e);
	}

}
