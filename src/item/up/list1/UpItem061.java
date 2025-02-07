package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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

public class UpItem061 extends upitemBase{
	public boolean rep = false;
	public UpItem061(Player p){
		super(p);
		itemCode = 61;
	}
	
	@Override
	protected void onStart() {
		if(cooldown <= 0) {
			new G_Item(player, 9, 1000, 5000);
		}
		if(Rule.c.get(player).number == 57) {
			Rule.c.get(player).skillmult += 0.4f;
		}
	}
	
	@Override
	protected void onTick() {
		if(rep && Rule.buffmanager.GetBuffTime(player, "timestop") <= 0) {
			rep = false;
			delay(()->{
				ARSystem.playerItem.get(player).items.clear();
				ARSystem.playSound(player,"itemupgrad",1,100000);
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				delay(()->{
					Bukkit.broadcastMessage("§a§l[ARSystem] : §e§l"+Text.get("item:itemup") +"§c§l"+ Text.get("item:105"));
					ARSystem.addItem(player, 105);
				},40);
			},0);
		}
		super.onTick();
	}
}
