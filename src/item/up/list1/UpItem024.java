package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Nitory;
import chars.c3.c122yuyuco;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem024 extends upitemBase{
	Location loc;
	int time = 0;
	boolean ison = false;
	
	public UpItem024(Player p){
		super(p);
		itemCode = 24;
		loc = player.getLocation();
	}
	
	@Override
	public void onTick() {
		if(loc.distance(player.getLocation()) <= 10) {
			time++;
			if(!ison && time > 100) {
				ison = true;
				Rule.c.get(player).sskillmult += 1.5;
			}
		} else {
			loc = player.getLocation();
			time = 0;
			if(ison) {
				ison = false;
				Rule.c.get(player).sskillmult -= 1.5;
			}
		}
		if(timer%100 == 0 && Rule.c.get(player).number == 122) {
			ARSystem.spellLocCast(player, ((c122yuyuco)Rule.c.get(player)).loc, "item24");
			ARSystem.spellLocCast(player, ((c122yuyuco)Rule.c.get(player)).loc, "item24");
		}
		if(Rule.c.get(player).number == 122) {
			for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
				if(BlockUtil.isAirbone(e.getLocation(), 1)) {
					e.setVelocity(e.getVelocity().clone().add(ULocal.lookAt(e.getLocation().clone(), ((c122yuyuco)Rule.c.get(player)).loc).getDirection().multiply(0.08)));
				} else {
					e.setVelocity(e.getVelocity().clone().add(ULocal.lookAt(e.getLocation().clone(), ((c122yuyuco)Rule.c.get(player)).loc).getDirection().multiply(0.02)));
				}
			}
		}
	}
	
	@Override
	public String getActionbar() {
		if(time > 200) {
			return "§c§l<§6"+itemName+" : §a" +AMath.round(loc.distance(player.getLocation()),1) +"§c§l>";
		}
		return "§c§l<§6"+itemName+" : §e" +AMath.round(loc.distance(player.getLocation()),1)+ ":"+ AMath.round((200-time)*0.05f,1) +"§c§l>";
	}
}
