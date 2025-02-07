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

public class Item067 extends itemBase{
	public Item067(Player p){
		super(p);
		itemCode = 67;
	}
	
	@Override
	public void onStart() {
		player.setMaxHealth(player.getMaxHealth() + 4);
		ARSystem.heal(player, 4);
		Rule.c.get(player).hp += 4;
	}
	
	@Override
	public void itemRemove() {
		player.setHealth(player.getHealth() - 4);
		player.setMaxHealth(player.getMaxHealth() - 4);
		Rule.c.get(player).hp -= 4;
	}
}
