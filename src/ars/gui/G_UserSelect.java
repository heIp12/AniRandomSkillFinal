package ars.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
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

public class G_UserSelect extends GUIBase{
	public G_ModeSeting mode;
	
	List<Player> players = new ArrayList<Player>();
	List<Player> playerz = null;
	boolean stop = false;
	public G_UserSelect(Player p) {
		super(p);
		name = "target";
		int count = Bukkit.getOnlinePlayers().size();
		this.line = 3;
		
		page = new int[90];
		int j = 0;
		playerz = ARSystem.getPlayers();
		for(int i = 0; i< page.length; i++) page[i] = 0;
		for(int i=0 ;j < playerz.size();i++) {
			if(i%9 == 8) {
				page[i] = 0;
				continue;
			}
			page[i] = j+1;
			itemSet(j+1, playerz.get(j));

			j++;
		}
		ScrollInvCreate(3,0);
	}
	
	public void itemSet(int i,Player player) {
		ItemStack is = Rule.playerinfo.get(player).getHead().clone();
		if(is == null) return;
		if(players.contains(player)) {
			is = ItemCreate.Name(is, "§a"+player.getName());
		} else {
			is = ItemCreate.Name(is, "§c"+player.getName());
		}
		ItemRep(i,is);
		if(inv != null) InvRep(i,is);
	}
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		int click = page[clickLocal];
		if(click != 0) {
			Player p = playerz.get(click-1);
			if(players.contains(p)){
				players.remove(p);
			} else {
				players.add(p);
			}
			itemSet(clickLocal,p);
		}
		return false;
	}
	
	@Override
	public void Close(InventoryCloseEvent e) {
		if(e.getInventory().getName().equals(inv.getName()) && mode != null && !stop) {
			stop = true;
			mode.playerSeting(players);
			mode = null;
		}
	}
	
	public ItemStack gui996() {
		// TODO Auto-generated method stub
		return super.gui0();
	}

	public void click996(boolean right, boolean shift) {}
}