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
import ars.Rule;
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.TimeStop;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item064 extends itemBase{
	public Item064(Player p){
		super(p);
		itemCode = 64;
	}

	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(AMath.random(100) <= 5) {
			e.setDamage(0);
			e.setCancelled(true);
			ARSystem.playSound((Entity)player,"0miss");
		}
		return super.onHit(e);
	}
}
