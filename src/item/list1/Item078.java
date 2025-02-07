package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
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
import buff.Rampage;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item078 extends itemBase{
	boolean itemcopy = true;
	public Item078(Player p){
		super(p);
		itemCode = 78;
	}
	
	@Override
	protected void onStart() {
		List<itemBase> items = ARSystem.playerItem.get(player).items;
		if(items.size() > 1 && items.get(items.size()-2).itemCode != 78 && items.get(items.size()-2).value <= 3000) {
			itemcopy = false;
			ARSystem.playSound((Entity)player, "item78");
			ARSystem.addItem(player, items.get(items.size()-2).itemCode);
		}
	}
	@Override
	public void onAddItem(itemBase item) {
		if(itemcopy && item.itemCode != 78 && item.value <= 3000) {
			itemcopy = false;
			ARSystem.playSound((Entity)player, "item78");
			delay(()->{
				ARSystem.addItem(player, item.itemCode);
			},0);
		}
	}
	
}
