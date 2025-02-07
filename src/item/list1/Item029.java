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
import buff.Ice;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item029 extends itemBase{
	public Item029(Player p){
		super(p);
		itemCode = 29;
		setcooldown = 3;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(AMath.random(10) <= 3 && isCooldown()) {
			LivingEntity en = (LivingEntity)e.getEntity();
			ARSystem.giveBuff(en, new Ice(en, player), 40);
		}
		return super.onAttack(e);
	}
}
