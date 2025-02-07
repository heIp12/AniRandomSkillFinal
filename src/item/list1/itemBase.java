package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import event.FixedDealEvent;
import manager.AdvManager;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class itemBase {
	public int itemCode;
	protected String itemName;
	public int timer = 0;
	protected ItemStack item;
	protected int value = 1000;
	protected Player player;
	
	protected boolean many = true;
	protected boolean itemtable = true;
	
	protected boolean isStart = true;
	boolean isBreaking = true;
	
	public float setcooldown = 5;
	public float cooldown = 0;
	
	protected boolean quest = false;
	public boolean notcooldown = false;
	
	public itemBase(Player p) {
		player = p;
		delay(()->{
			set();
		},0);
	}
	public void set() {
		value = Integer.parseInt(Text.get("item:"+itemCode+"_value"));
		itemName = Text.get("item:"+itemCode);
	}
	public boolean isBreaking() {
		return isBreaking;
	}
	public void tick() {
		timer = timer + 1;
		if(cooldown > 0) cooldown -= 0.05;
		if(isStart && (ARSystem.AniRandomSkill == null || ARSystem.time > 0)) {
			isStart = false;
			onStart();
		}
		onTick();
	}
	protected boolean isCooldown() {
		if(notcooldown) {
			notcooldown = false;
			return true;
		}
		if(cooldown <= 0) {
			cooldown = setcooldown;
			return true;
		}
		return false;
	}
	
	public int getCode() { return itemCode; }
	protected void onTick() {}
	protected void onStart() {}
	public void itemRemove() {}
	public void onAddItem(itemBase item) {}
	public boolean onDamageEvent(EntityDamageEvent e){ return true; }
	public boolean onHit(EntityDamageByEntityEvent e){ return true; }
	public boolean onAttack(EntityDamageByEntityEvent e){ return true; }
	public boolean onRemove(Entity caster){ return true; }
	public void kill(LivingEntity death, LivingEntity killer) {}
	public void fixedDamage(FixedDealEvent e) {}
	
	public boolean onSkill(PlayerItemHeldEvent e) {
		return true;
	}
	
	public boolean onMove(PlayerMoveEvent e){ return true; }
	public void onDeath(Player p ,Entity e){}
	
	public boolean skillCast() {
		return true;
	}
	
	public ItemStack getItem() {
		if(item == null) {
			int code = itemCode+1;
			if(code >= 1000) code -= 800;
			item = ItemCreate.Item(258,code);
			List<String> lore = new ArrayList<String>();
			lore.add(Text.get("item:t") + value);
			lore.addAll(Text.getLine("item:"+itemCode+"_lore", 1));
			
			String color = "a";
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
				
				item = ItemCreate.Lore(item, "§f§l["+itemCode+"]§"+color+Text.get("item:"+itemCode), lore);
			}
		}
		
		return item;
	}
	
	public String getActionbar() {
		if(cooldown > 0) {
			String n = itemName.split(" ")[0];
			if(n.length() > 3) n = n.substring(0,2);
			
			return "§c§l<§6"+n+" : §e" + AMath.round(cooldown,1) + "§c§l>";
		}
		return "";
	}
	
	protected void delay(Runnable event,int delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, event, delay);
	}
	
	public int getValue() {
		return value;
	}
	
	public void questComplete() {
		if(!quest) {
			ARSystem.playSound((Entity)player, "itemquest");
			ARSystem.playSound(player, "itemquest",1,10000);
			quest = true;
			for(Player pl : Bukkit.getOnlinePlayers()) {
				AdvManager.set(pl, 277, 1, player.getName() + " : " + Text.get("item:"+itemCode) + Text.get("item:quest"));
			}
		}
	}
	
	public boolean getIsMany() { return many; }
	public boolean getIsItemTable() { return itemtable; }
	
	public boolean onKey_F(PlayerSwapHandItemsEvent e) {
		if(isCooldown()) {
			boolean isCast = skillCast();
			if(isCast) {
				cooldown = 0;
			} else {
				return true;
			}
		}
		return false;
	}
}
