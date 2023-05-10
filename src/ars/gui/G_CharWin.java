package ars.gui;

import java.util.ArrayList;
import java.util.List;

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

public class G_CharWin extends GUIBase{
	PlayerInfo info;
	public G_CharWin(Player p) {
		super(p);
		name = "Wins";
		int count = GetChar.getCount();
		line = 6;
		count = Math.max(count,this.line*9);
		page = new int[count + (count/8) + 18];
		info = Rule.playerinfo.get(player);
		int j = 0;
		
		for(int i=0;j<=count;i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			page[i] = j;
			
			if(j == 0) {
				ItemRep(0, ItemCreate.Lore(GetChar.getNoColor(0),"§aRandom",new String[] {"§7" + Main.GetText("main:info15")}));
				j++;
				continue;
			}

			int kill = Rule.Var.Loadint(player.getName()+".c"+(j%1000)+"Kill");
			int play = Rule.Var.Loadint(player.getName()+".c"+(j%1000)+"Play");
			int win = Rule.Var.Loadint(player.getName()+".c"+(j%1000)+"Win");

			ItemStack is = null;
			if(play > 0) {
				is =  GetChar.getColor(j);
			} else {
				is =  GetChar.getNoColor(j);
			}
			
			ItemMeta meta = is.getItemMeta();
			if(Main.GetText("c"+(j)+":name2") != null) {
				meta.setDisplayName("§f§l["+j+"]"+Main.GetText("c"+j+":name1").replace("-", "") +" "+ Main.GetText("c"+j+":name2"));
			} else {
				meta.setDisplayName("§f§l["+j+"]");
			}
			List<String> lore = new ArrayList<String>();
			lore.add("§a=====================================");
			if(play > 0) {
				lore.add("§e" + Main.GetText("main:info2") + " §f" + play);
				lore.add("§e" + Main.GetText("main:info3") + " §f" + win);
				lore.add("§e" + Main.GetText("main:info4") + " §f" + kill);
				lore.add("§e" + Main.GetText("main:info14") + " §c" + (Math.round(info.n(win,info.max(player.getName()+".c","Win"))*1000)/10.0f) +"%");
			} else {
				lore.add("§7"+Main.GetText("main:cmderror3"));
			}
			lore.add("§a=============["+Main.GetText("main:info10")+"]==============");
			win = Rule.Var.Loadint("ARSystem.c"+(j%1000)+"Win");
			lore.add("§e" + Main.GetText("main:info2") + " §f" + Rule.Var.Loadint("ARSystem.c"+(j%1000)+"Play"));
			lore.add("§e" + Main.GetText("main:info3") + " §f" + win);
			lore.add("§e" + Main.GetText("main:info4") + " §f" + Rule.Var.Loadint("ARSystem.c"+(j%1000)+"Kill"));
			lore.add("§e" + Main.GetText("main:info14") + " §c" + (Math.round(info.n(win,info.max("ARSystem.c","Win"))*1000)/10.0f) +"%");
			lore.add("§7" + Main.GetText("main:info15"));
			meta.setLore(lore);
			
			is.setItemMeta(meta);
			
			ItemRep(j, is);
			j++;
		}
		ScrollInvCreate(6,0);
	}
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		int click = page[clickLocal];
		if(shift) {
			info.playerc = click;
			Rule.Var.setInt(player.getName()+".info.c", click);
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:info22"));
			ARSystem.playSound(player,"0click2");
		} else {
			ARSystem.playSound(player,"0click");
		}
		return false;
	}
}