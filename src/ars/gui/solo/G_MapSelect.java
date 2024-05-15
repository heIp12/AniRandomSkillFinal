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
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_MapSelect extends GUIBase{
	PlayerInfo info;
	
	public G_MapSelect(Player p) {
		super(p);
		name = "Map Select";
		int count1 = Integer.parseInt(Main.GetText("map:map"));
		int count2 = Integer.parseInt(Main.GetText("map:map_"));

		int count = count1+count2;
		line = 6;
		page = new int[Math.max(count,this.line*9) + (Math.max(count,this.line*9)/8) + 18];
		info = Rule.playerinfo.get(player);
		int j = 0;
		
		for(int i=0;j<count1;i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			page[i] = j;
			ItemStack item = ItemCreate.Item(395);
			ItemRep(j, ItemCreate.Name(item,"§f"+Text.get("map:map"+(j+1))));
			j++;
		}

		for(int i=count1+2;j<count;i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			page[i] = 100+j-count1;
			ItemStack item = ItemCreate.Item(386);
			ItemRep(100+j-count1, ItemCreate.Name(item,"§f"+Text.get("map:map"+(j+101-count1))));
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
		if(!(!ARSystem.isGameMode("lobotomy") || Rule.ishelp(player))) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tm anitext "+player.getName()+" SUBTITLE true 40 main:lb10/main:lb9");
			return false;
		}
		
		int click = page[clickLocal];
		player.closeInventory();
		Map.getMapinfo(click+1);
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(Map.randomLoc(p));
		}
		return false;
	}
}