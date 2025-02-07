package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
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
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class UpItem102 extends upitemBase{
	public UpItem102(Player p){
		super(p);
		itemCode = 102;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(e.getDamage() >= 5) {
			ARSystem.spellLocCast(player,player.getLocation(), "item102");
			for(Entity ey : ARSystem.box(player, new Vector(12,12,12), box.TARGET)) {
				ey.teleport(Map.randomLoc());
			}
			ARSystem.playSound((Entity)player, "0boom2");
		}
		return super.onHit(e);
	}
}
