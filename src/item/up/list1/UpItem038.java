package item.up.list1;

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

public class UpItem038 extends upitemBase{
	public UpItem038(Player p){
		super(p);
		itemCode = 38;
		setcooldown = 30;
		cooldown = 30;
	}
	
	@Override
	public void onTick() {
		if(isCooldown()) {
			ARSystem.playSound((Entity)player ,"item38");
			ARSystem.removeItem(player, itemCode);
			ARSystem.addItem(player, ItemList.getValueCode(5000, 5000));
		}
	}
}
