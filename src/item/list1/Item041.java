package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import buff.TimeStop;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class Item041 extends itemBase{
	public Item041(Player p){
		super(p);
		itemCode = 41;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		ARSystem.fixedDamage((LivingEntity)e.getEntity(), player, e.getDamage()*0.1f);
		return super.onAttack(e);
	}

}
