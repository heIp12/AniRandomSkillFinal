package item.etc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Item;
import ars.gui.G_Select;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import mode.MKagerou;
import types.BuffType;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10002 extends itemBase{
	public List<Integer> bans = new ArrayList<>();
	public int c1 = -9999;
	public int c2 = -9999;
	public int c3 = -9999;
	public boolean end = false;
	
	int startChar;
	public Item10002(Player p){
		super(p);
		setcooldown = 0;
		itemCode = 100002;
		itemtable = false;
		if(MKagerou.cBan) {
			for(String s : Text.get("item:100002_ban").split(",")) {
				bans.add(Integer.parseInt(s));
			}
			for(int i =0; i<GetChar.getCount(); i++) {
				if(!GetChar.isBan(i)) {
					bans.add(i);
				}
			}
		}
	}
	
	@Override
	protected void onTick() {
		if(!end) {
			if(timer%5 == 0 && (c1 < -999 || c2 < -999) && startChar != Rule.c.get(player).number) {
				if(c1 < -999) {
				    c1 = Rule.c.get(player).number;
				    Rule.c.put(player,GetChar.get(player, Rule.gamerule, ""+startChar));
				} else if(c2 < -999) {
					c2 = Rule.c.get(player).number;
					Rule.c.put(player,GetChar.get(player, Rule.gamerule, ""+startChar));
					end = true;
					for(int i = 0; i<1000; i++) {
						c3 = AMath.random(GetChar.getCount());
						if(c1 != c3 && c1 != c2 && !bans.contains(c3)) {
							return;
						}
					}
				}
			}
			if(timer%10 == 0 &&(c1 < -999 || c2 < -999)) {
				if(player.getOpenInventory().getType() == InventoryType.CRAFTING) {
					new G_Select(player, bans);
				}
			}
		}
	}
	
	@Override
	protected void onStart() {
		startChar = Rule.c.get(player).number;
	}
	
	
	@Override
	public boolean skillCast() {
		if(player.isSneaking()) {
			Rule.c.get(player).invskill = new InvSkill(player) {
				
				@Override
				public void Start(String st) {
					player.closeInventory();
					String c = "";
					if(st.equals(getName(c1))) {
						c = ""+c1;
						c1 = -9999;
					} else if(st.equals(getName(c2))) {
						c = ""+c2;
						c2 = -9999;
					} else if(st.equals(getName(c3))) {
						c = ""+c3;
						c3 = -9999;
					}
					
					if(!c.equals("")) {
						Rule.c.put(player, GetChar.get(player, Rule.gamerule, c));
						if(MKagerou.spBan) Rule.c.get(player).setcooldown[0] = -100000;
						if(Rule.c.get(player).number == 85) Rule.c.get(player).setcooldown[4] *= 3;
					}
				}
			};
			
			String t = "item:100001_";
			org.bukkit.inventory.Inventory inv = new CraftInventoryCustom(null, 17, player.getName()+" : Skill");
			if(c1 > 0) inv.setItem(2, ItemCreate.Name(GetChar.getColor(c1),getName(c1)));
			if(c2 > 0) inv.setItem(4, ItemCreate.Name(GetChar.getColor(c2),getName(c2)));
			if(c3 > 0) inv.setItem(6, ItemCreate.Name(GetChar.getColor(c3),getName(c3)));
			
			Rule.c.get(player).invskill.setInventory(inv);
			Rule.c.get(player).invskill.openInventory(player);
		} else {
			return true;
		}
		return false;
	}
	
	@Override
	public ItemStack getItem() {
		item = ItemCreate.Item(277);
		List<String> lore = new ArrayList<String>();
		lore.add(Text.get("item:100002_lore1") + getName(c1));
		lore.add(Text.get("item:100002_lore2") + getName(c2));
		lore.add(Text.get("item:100002_lore3") + getName(c3));
		item = ItemCreate.Lore(item, "§c§l【§e§l⸎§c§l】§a"+Text.get("item:"+itemCode), lore);
		return item;
	}
	
	String getName(int j){
		if(j <= 0) {
			return "§fNone";
		}
		return "§f"+Text.get("c"+(j)+":name1")+ " "+Text.get("c"+(j)+":name2");
	}
}
