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

public class G_AdvSelect extends GUIBase{
	PlayerInfo info;
	List<Integer> count = new ArrayList<Integer>();
	
	public G_AdvSelect(Player p) {
		super(p);
		name = "Adv Select";
		for(int j = 0; j< GetChar.adv.length; j++) {
			for(int k = 1; k < GetChar.adv[j][1]+1; k++) {
				count.add(GetChar.adv[j][0] + (k*1000));
			}
		}
		count.add(1021);
		count.add(1023);
		count.add(1024);
		count.add(1040);
		count.add(1068);
		count.add(1118);
		count.add(2030);
		line = 6;
		page = new int[Math.max(count.size(),this.line*9) + (Math.max(count.size(),this.line*9)/8) + 18];
		info = Rule.playerinfo.get(player);
		int j = 0;
		
		for(int i=0;j<count.size();i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			page[i] = j;
			ItemStack item = GetChar.getColor(count.get(j));
			ItemRep(j, ItemCreate.Name(item,"§f"+Text.get("c"+count.get(j)+":name1")+ " "+Text.get("c"+count.get(j)+":name2")));
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
		Rule.c.put(Rule.playerinfo.get(player).target, GetChar.get(Rule.playerinfo.get(player).target, Rule.gamerule, ""+count.get(click)));
		return false;
	}
}