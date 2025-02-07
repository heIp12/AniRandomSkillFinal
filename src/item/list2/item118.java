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

public class item118 extends itemBase{
	boolean damage7 = false;
	
	public item118(Player p){
		super(p);
		itemCode = 118;
		ARSystem.playSound(player, "item116");
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		int damage = (int)e.getDamage();
		if(damage >= 7 && damage <= 8 && !damage7) {
			damage7 = true;
			e.setDamage(0);
			e.setCancelled(true);
		}
		
		if(damage7) {
			ARSystem.heal(player, 50);
			Rule.buffmanager.selectBuffAddValue(player, "barrier", 10);
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			ARSystem.playerItem.get(player).removes.add(this);
			
			ARSystem.playSound(player,"itemupgrad",1,100000);
			ARSystem.giveBuff(player, new TimeStop(player), 40);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
				Bukkit.broadcastMessage("§a§l[ARSystem] : §e§l"+Text.get("item:itemup") +"§c§l"+ Text.get("item:1011"));
				ARSystem.addItem(player,1011);
			},40);
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
		if(!damage7) s+= " 7~8";
		
		return "§c§l<§6"+n+" : §c["+s+" ]§c§l>";
	}
}
