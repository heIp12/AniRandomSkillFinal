package ars.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Main.Main;
import ars.ARSystem;
import ars.PlayerInfo;
import ars.Rule;
import buff.Buff;
import buff.NoCC;
import buff.Nodie;
import buff.PowerUp;
import buff.TimeStop;
import item.list1.itemBase;
import types.BuffType;
import types.ItemList;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_ItemShop extends GUIBase{
	static public List<Integer> items = new ArrayList<>();
	static public List<Integer> sold = new ArrayList<>();
	PlayerInfo info;
	boolean targetopen = false;
	
	public G_ItemShop(Player p) {
		super(p);
		name = "Item Shop";
		line = 5;

		page = new int[]
				{
						   0,51, 0,52, 0,53, 0,54, 0,
						   0, 1, 0, 2, 0, 3, 0, 4, 0,
						   0,55, 0,56, 0,57, 0,58, 0,
						   0, 5, 0, 6, 0, 7, 0, 8, 0,
						   0, 0, 0, 0, 0, 0, 0, 0,99
				};
		
		info = Rule.playerinfo.get(player);
		if(Rule.c.get(player) == null) player.closeInventory();
		
		if(items.size() == 0) {
			for(int i =1; i<9; i++) {
				int up = 500,down = 500;
				if(i > 2 && i < 4){
					up = down = 1000;
				} else if(i > 3 && i < 5) {
					up = down = 1500;
					if(AMath.random(10) <= 3) {
						up=1500;down=2500;
					}
				} else if(i > 4 && i < 7) {
					up = down = 2000;
					if(AMath.random(10) <= 3) {
						up=2000;down=3000;
					}
				} else if(i == 7) {
					up = down = 2500;
					if(AMath.random(10) <= 3) {
						up=2600;
						down=3500;
					}
				} else if(i == 8) {
					up = 2600;
					down = 3500;
				}
				int a = ItemList.getValueCode(up, down);
				while(items.contains(a) && a != -1) a = ItemList.getValueCode(up, down);
				items.add(a);
			}
			
		}
		for(int i=51;i < 59;i++) {
			ItemRep(i, gui0());
		}
		
		for(int i=1;i < 9;i++) {
			if(items.get(i-1) == -1) {
				ItemRep(i, gui0());
				continue;
			}
			itemBase it = ItemList.orignal.get(items.get(i-1));
			ItemRep(i, it.getItem());
			if(sold.contains(i)) ItemRep(i+50, ItemCreate.Name(ItemCreate.Item(258, 249)," "));
		}

		InvCreate(line,0);
	}
	
	public ItemStack gui99() {
		if(Rule.c.get(player) == null || Rule.c.get(player).number != 152) return gui0(); 
		return ItemCreate.Name(ItemCreate.Item(277),Text.get("c152:t3"));
	}
	
	public void click99(boolean right, boolean shift) {
		if(Rule.c.get(player).number == 152 && Rule.playerinfo.get(player).gold >= 300) {
			Rule.playerinfo.get(player).gold -= 300;
			items.clear();
			sold.clear();
			new G_ItemShop(player);
		}
	}
	
	
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		int click = page[clickLocal];
		if(click == 99) return true;
		if(click > 0 && click < 10 && !sold.contains(click)) {
			int gold = ItemList.orignal.get(items.get(click-1)).getValue();
			if(gold <= Rule.playerinfo.get(player).gold) {
				Rule.playerinfo.get(player).gold -= gold;
				ARSystem.addItem(player, items.get(click-1));
				sold.add(click);
				ARSystem.playSound(player,"item87");
				ARSystem.playSound(player,"itemsel"+AMath.random(3));
				if(sold.size() >= 8 && (boolean)Rule.Var.Load("System.mode.item.4")) {
					items.clear();
					sold.clear();
				}
			} else {
				ARSystem.playSound(player,"itemno");
				player.sendMessage("§a§l[ARSystem] §f"+ (gold-Rule.playerinfo.get(player).gold) +Text.get("main:mode15-1"));
			}
		}
		new G_ItemShop(player);
		return false;
	}
}