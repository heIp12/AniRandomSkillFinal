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
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Ice;
import buff.PowerUp;
import buff.Silence;
import buff.Sleep;
import buff.Stun;
import chars.c3.c105suya;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item031 extends itemBase{
	
	public Item031(Player p){
		super(p);
		itemCode = 31;
		setcooldown = 10;
	}

	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 105) {
			((c105suya)Rule.c.get(player)).itemlv1+=1;
			((c105suya)Rule.c.get(player)).itemlv2+=1;
		}
	}
	
	@Override
	protected void onTick() {
		if(Rule.buffmanager.GetBuffTime(player, "sleep") > 0) {
			for(int i =0; i<10; i++) {
				if(Rule.c.get(player).cooldown[i] > 0) Rule.c.get(player).cooldown[i] -= 0.025;
			}
		}
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.giveBuff(player, new Sleep(player), 40, 0.2);
		return false;
	}
	
}
