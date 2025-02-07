package item.list2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
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
import ars.gui.G_HeddenSelect;
import ars.gui.G_Item;
import ars.gui.G_Supply;
import ars.gui.solo.G_ItemSelect;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item134 extends itemBase{
	public Item134(Player p){
		super(p);
		itemCode = 134;
		setcooldown = 10;
	}
	
	@Override
	protected void onStart() {
		ARSystem.playSound(player, "item138s"+AMath.random(2));
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(p == player) {
			ARSystem.playSound(player, "item138d1");
		}
	}
	
	@Override
	public boolean skillCast() {
		Rule.c.get(player).invskill = new InvSkill(player) {
			
			@Override
			public void Start(String st) {
				player.closeInventory();
				if(st.equals("1")) {
					ARSystem.giveBuff(player, new Nodamage(player), 20);
					ARSystem.playSound(player, "item138a"+AMath.random(3));
				} else if(st.equals("2")) {
					ARSystem.giveBuff(player, new PowerUp(player), 100, 0.3);
					ARSystem.playSound(player, "item138b"+AMath.random(3));
				} else if(st.equals("3")) {
					ARSystem.heal(player,6);
					ARSystem.playSound(player, "item138c"+AMath.random(3));
				}
			}
		};

		org.bukkit.inventory.Inventory inv = new CraftInventoryCustom(null, 17, player.getName()+" : Skill");
		inv.setItem(2, ItemCreate.Lore(ItemCreate.Item(279,1524), "1", new String[]{Text.get("item:134_t1")}));
		inv.setItem(4, ItemCreate.Lore(ItemCreate.Item(279,1525), "2", new String[]{Text.get("item:134_t2")}));
		inv.setItem(6, ItemCreate.Lore(ItemCreate.Item(279,1526), "3", new String[]{Text.get("item:134_t3")}));
		
		Rule.c.get(player).invskill.setInventory(inv);
		Rule.c.get(player).invskill.openInventory(player);
		return false;
	}
	
	
}