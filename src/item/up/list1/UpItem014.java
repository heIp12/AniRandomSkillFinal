package item.up.list1;

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

public class UpItem014 extends upitemBase{
	float damage = 0;
	public UpItem014(Player p){
		super(p);
		itemCode = 14;
		setcooldown = 20;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(damage >= 0) {
			damage += e.getDamage();
			if(damage > 20) {
				damage = -10000;
				ARSystem.spellCast(player, "item14");
				ARSystem.playSound((Entity)player, "item14");
			}
		}
		if(damage <= -10000) {
			e.setDamage(e.getDamage() * 2.5);
		}
		return super.onAttack(e);
	}
	
	@Override
	public String getActionbar() {
		if(cooldown > 0) {
			return super.getActionbar();
		}
		if(damage <= -10000) return "§c§l<§6"+itemName+"§c§l>";
		return "§c§l<§6"+itemName+" : §e" + AMath.round(damage,1) + "/20§c§l>";
	}
}
