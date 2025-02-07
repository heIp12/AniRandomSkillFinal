package item.list1;

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
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item079 extends itemBase{
	public Item079(Player p){
		super(p);
		itemCode = 79;
		setcooldown = 20;
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.potion(player, 1, 100, 3);
		ARSystem.potion(player, 8, 100, 7);
		return false;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 136) {
			Rule.c.get(player).setStack(6015);
		}
	}
}
