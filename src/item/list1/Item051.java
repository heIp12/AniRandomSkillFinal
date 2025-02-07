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
import buff.Nodamage;
import buff.TimeStop;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item051 extends itemBase{
	public Item051(Player p){
		super(p);
		itemCode = 51;
	}

	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(AMath.random(4) <= 1) {
			Holo.create(e.getEntity().getLocation(), "§c[x2]", 30,new Vector(0,0.15,0));
			e.setDamage(e.getDamage()*2);
		}
		return super.onAttack(e);
	}
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 146) {
			Rule.c.get(player).setStack(10);
		}
	}
}
