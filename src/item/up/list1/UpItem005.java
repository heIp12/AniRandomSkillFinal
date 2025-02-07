package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.Rule;
import chars.c.c16saki;
import util.ItemCreate;
import util.Text;

public class UpItem005 extends upitemBase{
	public UpItem005(Player p){
		super(p);
		itemCode = 5;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 16) {
			((c16saki)Rule.c.get(player)).luck+= 14;
		}
	}
	
	@Override
	public void onTick() {
		Rule.c.get(player).cooldown[1] -= 0.06f;
	}

}
