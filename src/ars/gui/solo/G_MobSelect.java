package ars.gui.solo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

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
import util.ULocal;

public class G_MobSelect extends GUIBase{
	PlayerInfo info;
	List<String> mobs = new ArrayList<>();
	
	public G_MobSelect(Player p) {
		super(p);
		name = "Mob Select";
		int count = Map.mm.getMobNames().size();
		
		line = 6;
		page = new int[Math.max(count,this.line*9) + (Math.max(count,this.line*9)/8) + 18];
		info = Rule.playerinfo.get(player);
		int j = 0;
		mobs.clear();
		for(String s : Map.mm.getMobNames()) mobs.add(s);
		
		for(int i=0;j<count;i++) {
			if((i+1)%9 == 0) {
				page[i] = 0;
				continue;
			}
			page[i] = j;
			ItemStack item = ItemCreate.Item(383);
			ItemRep(j, ItemCreate.Name(item,"§f"+Map.mm.getMythicMob(mobs.get(j)).getDisplayName()));
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
		Map.spawn(mobs.get(click), ULocal.offset(player.getLocation(), new Vector(8,0,0)), 1);
		return false;
	}
}