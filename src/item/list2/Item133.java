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

public class Item133 extends itemBase{
	float damage = 2f;
	int lv = 0;
	
	public Item133(Player p){
		super(p);
		itemCode = 133;
		setcooldown = 8f;
	}

	@Override
	protected void onTick() {
		if(AMath.random(5) <= 1) {
			List<itemBase> items = ARSystem.playerItem.get(player).items;
			if(items.size() > 1) {
				for(itemBase it : items) {
					if(it == this) continue;
					if(it.itemCode == 132 || it.itemCode == 133) {
						if(it.getValue() < 2000) {
							value += 2000;
						} else {
							value += it.getValue();
						}
						if(value >= 200000) {
							damage = -6974;
							lv = 999;
							setcooldown = 1f;
						} else if(value >= 100000) {
							damage = Integer.MAX_VALUE -1000000;
							lv = 8;
							setcooldown = 0.5f;
						} else if(value >= 50000) {
							damage = 99999f;
							lv = 7;
							setcooldown = 4f;
						} else if(value >= 30000) {
							damage = 50f;
							lv = 6;
							setcooldown = 6f;
						} else if(value >= 25000) {
							damage = 30f;
							lv = 5;
							setcooldown = 7f;
						} else if(value >= 20000) {
							damage = 10f;
							lv = 4;
						} else if(value >= 15000) {
							damage = 6f;
							lv = 3;
						} else if(value >= 10000) {
							damage = 4f;
							lv = 2;
						} else if(value >= 8000) {
							damage = 3f;
							lv = 1;
						}
						String lvs = "";
						if(lv >= 1) {
							lvs ="§7§l (§4+"+lv+"§7§l)";
						}
						ARSystem.playSound(player, "c147kang");
						ARSystem.playerItem.get(player).removes.add(it);
						
						List<String> lore = new ArrayList<String>();
						lore.add(Text.get("item:t") + value);
						lore.add(Text.get("item:132_t1") + damage);
						lore.add(Text.get("item:133_lore2"));
						
						if(item == null) getItem();
						if(value >= 10000) {
							item = ItemCreate.Lore(item, "§e§l『§6§l"+Text.get("item:"+itemCode)+lvs+"§e§l』", lore);
						} else if(value >= 5000) {
							item = ItemCreate.Lore(item, "§4§l[§c§l"+Text.get("item:"+itemCode)+lvs+"§4§l]", lore);
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
		if(damage > -1) {
			e.setDamage(e.getDamage() + damage);
		} else {
			e.setDamage(damage);
			e.setCancelled(true);
			LivingEntity en = (LivingEntity)e.getEntity();
			if(en instanceof Player) {
				Skill.quit(en);
				((Player)en).kickPlayer("§4차단됨:\n§f당신은 관리자에 의해 서버에서 차단되었습니다!");
			} else {
				Skill.quit(en);
			}
		}
		if(isCooldown()) {
			LivingEntity en = (LivingEntity)e.getEntity();
			en.setNoDamageTicks(0);
			en.damage(e.getDamage() , player);
		}
		return super.onAttack(e);
	}
}
