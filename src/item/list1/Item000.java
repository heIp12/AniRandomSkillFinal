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
import ars.Rule;
import util.ItemCreate;
import util.Text;

public class Item000 extends itemBase{
	public Item000(Player p){
		super(p);
		itemCode = 0;
		setcooldown = 2;
	}
	
	@Override
	protected void onTick() {
		if(Rule.c.get(player) != null && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time%100 == 97 && isCooldown()) {
			ARSystem.playSound((Entity)player, "item0");
		}
		if(Rule.c.get(player) != null && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 10 && ARSystem.AniRandomSkill.time%100 == 0 && isCooldown()) {
			ARSystem.heal(player, player.getMaxHealth());
			Rule.buffmanager.getBuffs(player).buffClear();
			for(int i =0; i<10; i++) {
				if(Rule.c.get(player).cooldown[i] > 0) Rule.c.get(player).cooldown[i] = 0;
			}
		}
	}

}
