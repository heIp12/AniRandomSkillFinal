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
import types.ItemList;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_UserItem extends GUIBase{
	PlayerInfo info;
	
	public G_UserItem(Player p) {
		super(p);
		name = "User Items";
		int count = Bukkit.getOnlinePlayers().size()*9+18;
		line = 6;
		page = new int[count];
		info = Rule.playerinfo.get(player);
		
		for(int i=0; i<count;i++) {
			if(i/9 < Bukkit.getOnlinePlayers().toArray().length) {
				Player pl = (Player)Bukkit.getOnlinePlayers().toArray()[i/9];
				if(i%9 == 0) {
					ItemRep(9+i, Rule.playerinfo.get(pl).getHead());
				} else {
					int j = i%9-1;
					if(ARSystem.playerItem.get(pl) != null && ARSystem.playerItem.get(pl).items.size() > j && j > -1) {
						ItemRep(9+i, ARSystem.playerItem.get(pl).items.get(j).getItem());
					} else {
						ItemRep(9+i, gui0());
					}
				}
			}
			if(i < 9) ItemRep(i, gui0());
			if(i < page.length) page[i] = i;
		}
		ScrollInvCreate(Math.min(6, Bukkit.getOnlinePlayers().size()+1),0);
	}
	
	public ItemStack gui996() {
		// TODO Auto-generated method stub
		return super.gui0();
	}

	public void click996(boolean right, boolean shift) {}
}