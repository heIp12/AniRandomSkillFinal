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

public class Item104 extends itemBase{
	float damage = 1.1f;
	
	public Item104(Player p){
		super(p);
		itemCode = 104;
		setcooldown = 10f;
	}

	@Override
	protected void onTick() {
		if(timer%20 == 0 && AMath.random(5) <= 1) {
			List<itemBase> items = ARSystem.playerItem.get(player).items;
			if(items.size() > 1) {
				for(itemBase it : items) {
					if(it.itemCode != 104 && it.getValue() < 10000) {
						value += it.getValue()/2;
						damage += it.getValue()*0.0001;
						ARSystem.playSound(player, "item104");
						ARSystem.playerItem.get(player).removes.add(it);
						if(value >= 5000) {
							ARSystem.playerItem.get(player).removes.add(this);
							ARSystem.addItem(player, 105);
						} else {
							List<String> lore = new ArrayList<String>();
							lore.add(Text.get("item:t") + value);
							lore.add(Text.get("item:104_t1") +(int)((damage-1)*100)+ Text.get("item:104_t2"));
							
							String color = "a";
							if(item == null) getItem();
							if(value >= 10000) {
								item = ItemCreate.Lore(item, "§e§l『§6§l"+Text.get("item:"+itemCode)+"§e§l』", lore);
							} else if(value >= 5000) {
								item = ItemCreate.Lore(item, "§4§l[§c§l"+Text.get("item:"+itemCode)+"§4§l]", lore);
							} else  {
								if(value >= 3000) {
									color = "4";
								} else if(value >= 2500) {
									color = "5";
								} else if(value >= 2000) {
									color = "e";
								} else if(value >= 1500) {
									color = "b";
								}
								int j = 0;
								
								item = ItemCreate.Lore(item, "§f§l["+itemCode+"]§"+color+Text.get("item:"+itemCode), lore);
							}
						}
						break;
					}
				}
			}
		}
		super.onTick();
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * damage);
		return super.onAttack(e);
	}
}
