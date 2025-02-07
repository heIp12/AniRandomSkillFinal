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
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import types.ItemList;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item038 extends itemBase{
	public Item038(Player p){
		super(p);
		itemCode = 38;
		setcooldown = 60;
		cooldown = 60;
	}
	
	@Override
	public void onTick() {
		if(isCooldown()) {
			ARSystem.playSound((Entity)player ,"item38");
			ARSystem.removeItem(player, itemCode);
			ARSystem.addItem(player, ItemList.getValueCode(2000, 5000));
		}
	}
}
