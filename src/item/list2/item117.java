package item.list2;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class item117 extends itemBase{
	boolean damage5 = false;
	
	public item117(Player p){
		super(p);
		itemCode = 117;
		ARSystem.playSound(player, "item116");
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		int damage = (int)e.getDamage();
		if(damage >= 5 && damage <= 6 && !damage5) {
			damage5 = true;
			e.setDamage(0);
			e.setCancelled(true);
		}
		if(damage5) {
			questComplete();
			ARSystem.heal(player, 10);
			Rule.c.get(player).skillmult += 0.2;
			ARSystem.playerItem.get(player).removes.add(this);
			ARSystem.giveBuff(player, new Nodamage(player), 40);
			ARSystem.addItem(player, 118);
			e.setDamage(0);
			e.setCancelled(true);
		}
		return super.onHit(e);
	}
	
	@Override
	public String getActionbar() {
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		String s = "";
		if(!damage5) s+= " 5~6";
		
		return "§c§l<§6"+n+" : §c["+s+" ]§c§l>";
	}
}
