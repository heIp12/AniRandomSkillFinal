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

public class Item132 extends itemBase{
	float damage = 0.1f;
	int lv = 0;
	
	public Item132(Player p){
		super(p);
		itemCode = 132;
		setcooldown = 10f;
	}

	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 30) {
			ARSystem.playSound((Entity)player, "c30p");
			ARSystem.playerItem.get(player).removes.add(this);
			ARSystem.addItem(player, 133);
		}
		if(Rule.c.get(player).number == 86) {
			ARSystem.playSound((Entity)player, "c3086s1");
			ARSystem.playerItem.get(player).removes.add(this);
			ARSystem.addItem(player, 133);
		}
	}
	
	@Override
	protected void onTick() {
		if(AMath.random(5) <= 1) {
			List<itemBase> items = ARSystem.playerItem.get(player).items;
			if(items.size() > 1) {
				for(itemBase it : items) {
					if(it == this) continue;
					
					if(it.itemCode == 132) {
						value += it.getValue();
						if(value < 4000){
							if(value > 2000) {
								value = 4000;
								damage = 1.5f;
								lv = 3;
							} else if(value > 1000) {
								value = 2000;
								damage = 0.8f;
								lv = 2;
							} else if(value > 500) {
								value = 1000;
								damage = 0.4f;
								lv = 1;
							}
						}
						String lvs = "";
						if(lv >= 1) {
							lvs ="§7§l (§4+"+lv+"§7§l)";
						}
						ARSystem.playSound(player, "c147kang");
						ARSystem.playerItem.get(player).removes.add(it);
						if(value > 4000) {
							ARSystem.playerItem.get(player).removes.add(this);
							ARSystem.addItem(player, 133);
						} else {
							List<String> lore = new ArrayList<String>();
							lore.add(Text.get("item:t") + value);
							lore.add(Text.get("item:132_t1") + damage);
							lore.add(Text.get("item:132_lore2"));
							
							String color = "a";
							if(item == null) getItem();
							if(value >= 10000) {
								item = ItemCreate.Lore(item, "§e§l『§6§l"+Text.get("item:"+itemCode)+lvs+"§e§l』", lore);
							} else if(value >= 5000) {
								item = ItemCreate.Lore(item, "§4§l[§c§l"+Text.get("item:"+itemCode)+lvs+"§4§l]", lore);
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
								
								item = ItemCreate.Lore(item, "§f§l["+itemCode+"]§"+color+Text.get("item:"+itemCode)+lvs, lore);
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
		e.setDamage(e.getDamage() + damage);
		return super.onAttack(e);
	}
}
