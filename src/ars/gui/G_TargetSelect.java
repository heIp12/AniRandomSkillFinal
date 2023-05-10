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

public class G_TargetSelect extends GUIBase{
	
	public G_TargetSelect(Player p) {
		super(p);
		name = "target";
		int count = Rule.playerinfo.size();
		this.line = 3;
		
		page = new int[90];
		int j = 0;
		
		for(int i=0;j<Bukkit.getOnlinePlayers().size();i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			page[i] = j+1;
			

			ItemRep(j+1, Rule.playerinfo.get(Bukkit.getOnlinePlayers().toArray()[j]).getHead());
			j++;
		}
		ScrollInvCreate(3,0);
	}
	
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		int click = page[clickLocal];
		if(click != 0) {
			Rule.playerinfo.get(player).target =  (Player) Bukkit.getOnlinePlayers().toArray()[click-1];
			new G_Menu(player);
		}
		return false;
	}
}