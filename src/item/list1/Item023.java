package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Item;
import ars.gui.G_Nitory;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class Item023 extends itemBase{
	boolean ison = true;
	public Item023(Player p){
		super(p);
		itemCode = 23;
	}
	
	@Override
	public void onTick() {
		if(ison && timer >= 600) {
			ison = false;
			new G_Nitory(player);
			delay(()->{
				if(Rule.c.get(player) != null) {
					Rule.c.get(player).s_score += 500;
					if(Rule.c.get(player).number == 120 && AMath.random(10) <= 5) {
						new G_Item(player, 2, 0, 3000);
					}
				}
			},200);
		}
	}
}
