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

public class G_Tropy extends GUIBase{
	PlayerInfo info;
	public G_Tropy(Player p) {
		super(p);
		name = "Tropys";
		int count = 0;
		this.line = 6;
		info = Rule.playerinfo.get(player);
		
		List<String> line = new ArrayList<>();
		for(int i = 0; i <= GetChar.getCount(); i++) {
			for(int k = 1; k < 100; k++) {
				if(info.trophy[i][k]) {
					count++;
					line.add(i+"-"+k);
				}
			}
		}
		count = Math.max(count,this.line*9);
		page = new int[count + (count/8) + 18];
		int j = 1;
		
		for(int i=0;j<line.size();i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			page[i] = j;
			
			ItemStack is;
			if(line.get(j).split("-")[0].equals("0")) {
				is = ItemCreate.Item(426) ;
			} else {
				is = ItemCreate.Item(277, Integer.parseInt(line.get(j).split("-")[0]));
			}
			ItemMeta meta = is.getItemMeta();
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName("§f"+Main.GetText("tropy:c"+line.get(j)));
			lore.clear();
			lore.add("§e=====================================");
			lore.add("§0"+line.get(j));
			for(int k = 1; k < 100; k++) {
				if(Main.GetText("tropy:c"+line.get(j)+"_"+k) != null) {
					lore.add("§f"+Main.GetText("tropy:c"+line.get(j)+"_"+k));
				} else {
					break;
				}
			}
			lore.add("§e=====================================");
			lore.add("§7"+Main.GetText("tropy:msg1"));
			boolean a = false;
			for(String ss : lore) if(ss.contains("[Effect]")) a= true;
			if(a) {
				lore.add("§7"+Main.GetText("tropy:msg5"));
				lore.add("§7"+Main.GetText("tropy:msg6"));
			}
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
		if(click != 0) {
			if(shift) {
				String n = items[click].getItemMeta().getLore().get(1).replace("§0", "");
				Rule.Var.Save(player.getName()+".info.tp",n);
				info.playerTrophy = n;
				info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("tropy:msg3") + " : " + info.name);
				ARSystem.playSound(player,"0click2");
			} else {
				ARSystem.playSound(player,"0click");
			}
		}
		return false;
	}
}