package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import util.AMath;
import util.ItemCreate;
import util.Text;

public class UpItem039 extends upitemBase{
	public UpItem039(Player p){
		super(p);
		itemCode = 39;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		LivingEntity en = (LivingEntity) e.getDamager();
		if(AMath.random(10) <= 2) {
			en.setNoDamageTicks(0);
			en.damage(4,player);
		} else if(AMath.random(10) <= 5) {
			en.setNoDamageTicks(0);
			en.damage(2,player);
		} else if(AMath.random(10) <= 7) {
			en.setNoDamageTicks(0);
			en.damage(1,player);
		}
		return true;
	}
	

}
