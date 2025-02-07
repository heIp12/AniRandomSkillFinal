package item.craft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import ars.gui.G_Supply;
import ars.gui.solo.G_ItemSelect;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import event.FixedDealEvent;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item1016 extends itemBase{
	public Item1016(Player p){
		super(p);
		itemCode = 1016;
	}

	@Override
	protected void onTick() {
		ARSystem.spellLocCast(player,player.getLocation(), "item1016e");
		if(timer%20 == 0) {
			for(itemBase item : ARSystem.playerItem.get(player).items) {
				if(item.getValue() <= 2500 && ItemList.getItem(item.itemCode+10000) != null) {
					delay(()->{ARSystem.playerItem.get(player).remove(item);},0);
					ARSystem.playSound(player, "c147kang");
					ARSystem.addItem(player, (item.getCode() + 10000));
				}
			}
		}
	}
}
