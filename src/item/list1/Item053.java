package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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

public class Item053 extends itemBase{
	Entity target;
	public Item053(Player p){
		super(p);
		itemCode = 53;
		setcooldown = 40;
	}
	
	@Override
	public void tick() {
		Entity e = ARSystem.boxSOne(player, new Vector(3,3,3), box.ALL);
		if(e != null) target = e;
		
		super.tick();
	}
	
	@Override
	public String getActionbar() {
		if(cooldown > 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		if(target == null) return "§c§l<§6"+n+" : §4§l?§c§l>";
		return "§c§l<§6"+n+" : §4§l" +target.getName() +"§c§l>";
	}
	
	@Override
	public boolean skillCast(){
		if(target == null) return true;
		ARSystem.playSound((Entity)player, "item53");
		ARSystem.playSound(target, "item53");
		delay(()->{
			ARSystem.spellCast(player, target, "item53");
			ARSystem.playSound((Entity)player, "boom2");
			LivingEntity en = (LivingEntity)target;
			en.setNoDamageTicks(0);
			en.damage(10,player);
			for(Entity ey : ARSystem.box(target, new Vector(8,8,8), box.ALL)) {
				if(ARSystem.isTarget(ey, player,box.TARGET)) {
					en = (LivingEntity)ey;
					en.setNoDamageTicks(0);
					en.damage(10,player);
				}
			}
			target = null;
		},120);
		return false;
	}
}
