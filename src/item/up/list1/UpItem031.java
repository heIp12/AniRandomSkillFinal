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

public class UpItem031 extends upitemBase{
	
	public UpItem031(Player p){
		super(p);
		itemCode = 31;
		setcooldown = 10;
	}

	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 105) {
			((c105suya)Rule.c.get(player)).itemlv1+=3;
			((c105suya)Rule.c.get(player)).itemlv2+=3;
		}
	}
	
	@Override
	protected void onTick() {
		if(Rule.buffmanager.GetBuffTime(player, "sleep") > 0) {
			for(int i =0; i<10; i++) {
				if(Rule.c.get(player).cooldown[i] > 0) Rule.c.get(player).cooldown[i] -= 0.1;
			}
		}
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.giveBuff(player, new Sleep(player), 100, 0.05);
		return false;
	}
	
}
