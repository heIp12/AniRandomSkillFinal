package item.list1;

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
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item097 extends itemBase{
	int tick = 0;
	int count = 0;
	public Item097(Player p){
		super(p);
		itemCode = 97;
		setcooldown = 7;
	}
	


	@Override
	public boolean skillCast(){
		if(AMath.random(3) > 1) {
			ARSystem.playSound((Entity)player, "item97c");
		} else if(AMath.random(3) == 1) {
			ARSystem.playSound((Entity)player, "item97b");
		} else {
			ARSystem.playSound((Entity)player, "item97");
		}
		ARSystem.spellCast(player, "item97");
		for(int i =0; i<10; i++) {
			Rule.c.get(player).cooldown[i] += 7;
			if(Rule.c.get(player).setcooldown[i] > 0) Rule.c.get(player).setcooldown[i] *= 0.95;
		}
		count++;
		if(!quest && count >= 10 && AMath.random(100) < count) {
			questComplete();
			delay(()->{
				ARSystem.playerItem.get(player).removes.add(this);
			},0);
			delay(()->{
				ARSystem.addItem(player, 98);
			},60);
		}
		return false;
	}
}
