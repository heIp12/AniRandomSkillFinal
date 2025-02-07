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
import ars.Rule;
import buff.Nodamage;
import buff.TimeStop;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem046 extends upitemBase{
	boolean ison = false;
	int kill = 0;
	public UpItem046(Player p){
		super(p);
		itemCode = 46;
		setcooldown = 1;
	}
	

	@Override
	public boolean skillCast(){
		if(!Rule.c.get(player).hpCost(0.5, false)) return true;
			ARSystem.playSound((Entity)player, "item46");
			ARSystem.potion(player, 1, 100, 2);
			Rule.c.get(player).skillmult += 0.5;
			delay(()->{
				if(Rule.c.get(player) != null )Rule.c.get(player).skillmult -= 0.5;
			},100);
		return false;
	}
}
