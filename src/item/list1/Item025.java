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
import chars.c3.c122yuyuco;
import util.ItemCreate;
import util.Text;

public class Item025 extends itemBase{
	public Item025(Player p){
		super(p);
		itemCode = 25;
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(p != player) {
			ARSystem.potion(player, 1, 160, 4);
		}
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number%1000 == 123) {
			e.setDamage(e.getDamage()+0.5f);
		}
		return super.onAttack(e);
	}

}
