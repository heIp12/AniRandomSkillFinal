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
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.TimeStop;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item059 extends itemBase{
	float damage = 0.8f;
	public Item059(Player p){
		super(p);
		itemCode = 59;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * damage);
		damage += 0.01*(AMath.random(13)-5);
		return super.onAttack(e);
	}
	
	@Override
	public String getActionbar() {
		if(cooldown > 0) {
			return super.getActionbar();
		}
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		
		return "§c§l<§6"+n+" : §e" +AMath.round(damage*100,1) +"§c§l>";
	}
}
