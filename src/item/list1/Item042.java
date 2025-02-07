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

import ars.ARSystem;
import ars.Rule;
import chars.c2.c75gon;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class Item042 extends itemBase{
	boolean ison = true;
	public Item042(Player p){
		super(p);
		itemCode = 42;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 75) {
			((c75gon)Rule.c.get(player)).lose = 5;
		}
	}
	
	@Override
	protected void onTick() {
		if(Rule.c.get(player).number == 75) {
			((c75gon)Rule.c.get(player)).ps--;
		}
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(Rule.c.get(player).number == 75) {
			e.setCancelled(true);
			
			return true;
		}
		if(ison && e.getNewSlot() < 5) {
			ARSystem.spellCast(player, "item42");
			ison = false;
			Rule.c.get(player).setcooldown[e.getNewSlot()+1] *= 0.5;
			ARSystem.playSound((Entity)player ,"item42");
			for(int i = 0; i<10; i++) {
				if(i != e.getNewSlot()+1) {
					Rule.c.get(player).setcooldown[i]*=1.8;
				}
			}
			
			
		}
		return super.onSkill(e);
	}
}
