package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.PowerUp;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem015 extends upitemBase{
	public UpItem015(Player p){
		super(p);
		itemCode = 15;
		setcooldown = 0.1f;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		int rd = 100;
		if(Rule.c.get(player).number == 60) rd = 200;
		
		if(AMath.random(100) <= rd && isCooldown()) {
			for(;rd > 0; rd -= 100) {
				Location loc = player.getLocation();
				loc = ULocal.offset(loc, new Vector(-2,1+AMath.random(100)*0.05,4-AMath.random(160)*0.05));
				loc = ULocal.lookAt(loc, e.getEntity().getLocation());
				ARSystem.spellLocCast(player, loc, "c60_p2_"+AMath.random(9));
				ARSystem.spellLocCast(player, loc, "c60_p1"+AMath.random(3));	
			}
		}
		return super.onAttack(e);
	}
}
