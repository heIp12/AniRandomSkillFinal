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
import ars.Rule;
import util.ItemCreate;
import util.Text;

public class UpItem011 extends upitemBase{
	public UpItem011(Player p){
		super(p);
		itemCode = 11;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number%1000 == 11) {
			for(int i = 0;i < 10; i++)Rule.c.get(player).setcooldown[i] *= 0.5;
		}
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(e == player) {
			ARSystem.playSound((Entity)player, "item11",1,2);
			for(int i =0; i<50;i++) {
				delay(()->{
					ARSystem.spellCast(player, "item11");
					ARSystem.heal(player, player.getMaxHealth()*0.02);
				},i);
			}
		}
	}

}
