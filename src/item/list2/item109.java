package item.list2;

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
import buff.Nodie;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
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

public class item109 extends itemBase{
	boolean on = true;
	public item109(Player p){
		super(p);
		itemCode = 109;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(on && player.getHealth()- e.getDamage() <= 1) {
			e.setDamage(0);
			e.setCancelled(true);
			ARSystem.giveBuff(player, new Nodie(player), 20);
			on = false;
		}
		return super.onHit(e);
	}
	
}
