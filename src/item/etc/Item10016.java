package item.etc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import buff.Airborne;
import buff.Panic;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import item.list1.itemBase;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item10016 extends itemBase{
	public Item10016(Player p){
		super(p);
		itemCode = 100015;
		setcooldown = 30;
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.playSound((Entity)player,"0what");
		ARSystem.giveBuff(player, new TimeStop(player), 160);
		
		ItemStack item = ItemCreate.Item(276, 1023);
		player.getInventory().setItemInMainHand(item);
		delay(()->{
			player.getInventory().clear();
		},160);
		return false;
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1404);
		item = ItemCreate.Name(item, "§c§l【§e§l⸎§c§l】§a"+Text.get("item:"+itemCode));
		return item;
	}
}
