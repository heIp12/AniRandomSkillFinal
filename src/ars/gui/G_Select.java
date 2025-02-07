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
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_Select extends GUIBase{
	PlayerInfo info;
	
	public G_Select(Player p,List<Integer> ban) {
		super(p);
		name = "Character Select";
		int count = GetChar.getCount();
		line = 6;
		page = new int[Math.max(count,this.line*9) + (Math.max(count,this.line*9)/8) + 18];
		info = Rule.playerinfo.get(player);
		int j = 0;

		
		for(int i=0;j<count;i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}

			page[i] = j;
			if(ban.contains(j+1)) {
				ItemRep(j, ItemCreate.Name(ItemCreate.Item(166),"§c"+Text.get("c"+(j+1)+":name1")+ " "+Text.get("c"+(j+1)+":name2")));				
			} else {
				ItemStack item = GetChar.getColor(j+1);
				ItemRep(j, ItemCreate.Name(item,"§f"+Text.get("c"+(j+1)+":name1")+ " "+Text.get("c"+(j+1)+":name2")));
			}
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
		if(items[click].getTypeId() == 166) {
			
		} else {
			Rule.c.put(Rule.playerinfo.get(player).target, GetChar.get(Rule.playerinfo.get(player).target, Rule.gamerule, ""+(click+1)));
			player.closeInventory();
		}
		return false;
	}
}