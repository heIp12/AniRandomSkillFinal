package item.list2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.ItemList;
import types.TargetMap;
import types.box;
import util.AMath;
import util.CustemInv;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item152 extends itemBase{
	
	public Item152(Player p){
		super(p);
		itemCode = 152;
		setcooldown = 10;
	}
	
	@Override
	public boolean skillCast() {
		List<itemBase> items = new ArrayList<itemBase>();
		for(itemBase item : ARSystem.playerItem.get(player).items) {
			if(item.getValue() <= 2500 && ItemList.getItem(item.itemCode+10000) != null) {
				items.add(item);
			}
		}

		
		List<ItemStack> itemlist = new ArrayList<ItemStack>();
		for(itemBase i : items) {
			itemlist.add(i.getItem());
		}
		for(int i = 0; i< 9; i++) itemlist.add(ItemCreate.Item(0));
		
		CustemInv cst = new CustemInv();
		cst.Create(player.getName()+" : Skill", itemlist);
		
		Rule.c.get(player).invskill = new InvSkill(player) {
			@Override
			public void Start(String st) {
				player.closeInventory();
				for(itemBase i : items) {
					if(i.getItem().getItemMeta().getDisplayName().equals(st)) {
						ARSystem.playerItem.get(player).remove(i);
						
						int rd = AMath.random(10);
						if(rd > 4) {
							if(Rule.c.get(player).number == 153 && Rule.c.get(player).cooldown[0] <= 0) {
								rd = 0;
								Rule.c.get(player).spskillen();
								Rule.c.get(player).cooldown[0] = Rule.c.get(player).setcooldown[0];
								ARSystem.playSoundAll("c153sp");
							}
						}
						if(rd <= 4) {
							ARSystem.playSound(player, "c147kang");
							ARSystem.addItem(player, (i.getCode() + 10000));
						} else {
							ARSystem.playSound((Entity)player, "item152");
						}
						break;
					}
				}
			}
		};

		int rd = AMath.random(20);
		if(rd <= 5) {
			if(Rule.c.get(player).number == 153 && Rule.c.get(player).cooldown[0] <= 0) {
				rd = 10;
				Rule.c.get(player).spskillen();
				Rule.c.get(player).cooldown[0] = Rule.c.get(player).setcooldown[0];
				ARSystem.playSoundAll("c153sp");
			}
		}
		if(rd <= 5) {
			player.sendMessage("§a§l[ARSystem] §f: "+Text.get("item:"+getCode()) + Text.get("item:remove"));
			ARSystem.playerItem.get(player).remove(this);
			ARSystem.playSound((Entity)player, "lock.glass.break", 0.2f, 2);
		}
		

		Rule.c.get(player).invskill.setInventory(cst.getInv());
		Rule.c.get(player).invskill.openInventory(player);
		return false;
	}
}