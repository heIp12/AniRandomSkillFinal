package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ars.ARSystem;
import ars.Rule;
import event.FixedDealEvent;
import item.list1.itemBase;
import manager.AdvManager;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class upitemBase extends itemBase {
	
	public upitemBase(Player p) {
		super(p);
	}

	@Override
	public void set() {
		value = Integer.parseInt(Text.get("item2:"+itemCode+"_value"));
		itemName = Text.get("item2:"+itemCode);
	}
	
	@Override
	public ItemStack getItem() {
		if(item == null) {
			int code = itemCode+1;
			if(code >= 1000) code -= 800;
			item = ItemCreate.Item(258,code);
			item.addEnchantment(Enchantment.DURABILITY, 1);
			ItemMeta im = item.getItemMeta();
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(im);
			List<String> lore = new ArrayList<String>();
			lore.add(Text.get("item:t") + value);
			lore.addAll(Text.getLine("item2:"+itemCode+"_lore", 1));

			item = ItemCreate.Lore(item, "§c§l《§e§l⚒§c§l》§c§n"+Text.get("item2:"+itemCode), lore);
			
		}
		
		return item;
	}
}
