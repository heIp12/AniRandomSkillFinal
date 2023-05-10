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

public class G_CharBan extends GUIBase{
	PlayerInfo info;
	public G_CharBan(Player p,int scroll) {
		super(p);
		name = "Ban List";
		int count = GetChar.getCount();
		line = 6;
		count = Math.max(count,this.line*9);
		page = new int[count + (count/8) + 18];
		info = Rule.playerinfo.get(player);
		int j = 1;
		
		for(int i=0;j<=count;i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			page[i] = j;
			
			ItemStack is;
			if(info.spopen[j]) {
				is = GetChar.getColor(j);
			} else {
				is = GetChar.getNoColor(j);
			}

			ItemMeta meta = is.getItemMeta();
			
			if(Main.GetText("c"+j+":name2") != null) {
				if(GetChar.charban.get("normal").contains(j)) {
					meta.setDisplayName("§c§l["+j+"]"+Main.GetText("c"+j+":name1").replace("-", "") +" "+ Main.GetText("c"+j+":name2"));
				} else {
					meta.setDisplayName("§a§l["+j+"]"+Main.GetText("c"+j+":name1").replace("-", "") +" "+ Main.GetText("c"+j+":name2"));
				}
			} else {
				meta.setDisplayName("§f§l["+j+"]");
			}
			is.setItemMeta(meta);
			ItemRep(j, is);
			j++;
		}
		this.scroll = scroll;
		ScrollInvCreate(6,scroll);
	}
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		if(page[clickLocal] == 0) return false;
		int click = page[clickLocal];
		if(GetChar.charban.get("normal").contains(click)) {
			GetChar.charban.get("normal").remove((Object)click);
			Rule.Var.Save("#ARS.ban.normal"+click, false);
		} else {
			GetChar.charban.get("normal").add(click);
		}
		String name = "§f["+(click)+"]"+Main.GetText("c"+(click)+":name1").replace("-", "") +" "+ Main.GetText("c"+(click)+":name2");
		Bukkit.broadcastMessage("§a§l[ARSystem] :§f ("+player.getName()+") Ban : " +name+ " §e§l: " + GetChar.charban.get("normal").contains(click));
		new G_CharBan(player,scroll);
		return false;
	}
}