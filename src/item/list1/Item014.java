package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import buff.PowerUp;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class Item014 extends itemBase{
	float damage = 0;
	public Item014(Player p){
		super(p);
		itemCode = 14;
		setcooldown = 20;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(cooldown <= 0) {
			damage += e.getDamage();
			if(damage > 20 && isCooldown()) {
				damage = 0;
				ARSystem.giveBuff(player, new PowerUp(player), 100, 1);
				ARSystem.spellCast(player, "item14");
				ARSystem.playSound((Entity)player, "item14");
			}
		}
		return super.onAttack(e);
	}
	@Override
	public String getActionbar() {
		if(cooldown > 0) {
			return super.getActionbar();
		}
		return "§c§l<§6"+itemName+" : §e" + AMath.round(damage,1) + "/20§c§l>";
	}
}
