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
import buff.TimeStop;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class UpItem040 extends upitemBase{
	Entity target = null;
	public UpItem040(Player p){
		super(p);
		itemCode = 40;
		setcooldown = 5;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		target = e.getEntity();
		return super.onAttack(e);
	}

	@Override
	public boolean skillCast(){
		if(target == null) return true;
		for(int i = 0; i<5; i++) ARSystem.spellLocCast(player, target.getLocation(), "item40");
		player.teleport(target);
		return false;
	}
	
	@Override
	public String getActionbar() {
		if(cooldown > 0) {
			return super.getActionbar();
		}
		if(target == null) return "§c§l<§6"+itemName+" : §e?§c§l>";
		return "§c§l<§6"+itemName+" : §e" + target.getName() +"§c§l>";
	}
}
