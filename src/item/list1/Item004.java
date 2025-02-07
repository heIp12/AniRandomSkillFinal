package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import chars.c2.c75gon;
import util.ItemCreate;
import util.Text;

public class Item004 extends itemBase{
	public Item004(Player p){
		super(p);
		itemCode = 4;
		setcooldown = 10;
	}

	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number%1000 == 1) {
			setcooldown = 2;
			Rule.c.get(player).setcooldown[1] -= 0.5;
		}
	}
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(isCooldown()) {
			ARSystem.spellCast(player, e.getEntity(), "item4");
		}
		return true;
	}
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		if(cooldown > 0) {
			cooldown -= e.getFrom().distance(e.getTo())/10;
		}
		return super.onMove(e);
	}
}
