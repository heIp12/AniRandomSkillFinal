package ars.gui.solo;

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
import item.list1.itemBase;
import types.ItemList;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_ItemSelect extends GUIBase{
	PlayerInfo info;
	int value = 3000;
	int[] code;
	
	public G_ItemSelect(Player p,int value) {
		super(p);
		this.value = value;
		name = "item Select";
		List<itemBase> items = ItemList.getItems();
		int count = items.size();
		line = 6;
		page = new int[Math.max(count,this.line*9) + (Math.max(count,this.line*9)/8) + 18];
		info = Rule.playerinfo.get(player);
		int j = 0;
		code = new int[1000];
		
		for(int i=0;j<count;i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			page[i] = j;
			code[j] = items.get(j).itemCode;
			ItemRep(j, items.get(j).getItem());
			j++;
		}
		for(int i = 1; i< page.length; i++) {
			if(page[i] == 0) {
				page[i] = 996;
			}
		}
		
		ScrollInvCreate(6,0);
	}
	
	public ItemStack gui996() {
		// TODO Auto-generated method stub
		return super.gui0();
	}

	public void click996(boolean right, boolean shift) {}
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		if(!(!ARSystem.isGameMode("lobotomy") || Rule.ishelp(player) || Rule.oplist.contains(player.getName()))) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext "+player.getName()+" SUBTITLE true 40 lobo:lb10/lobo:lb9");
			return false;
		}
		
		int click = page[clickLocal];
		
		if(ItemList.getItem(code[click]).getValue() > value) {
			player.sendMessage("§a§l[ARSystem] §f: " + Text.get("item:itemselecterror") + value);
		} else {
			ARSystem.addItem(player, code[click]);
			player.closeInventory();
		}
		return false;
	}
}