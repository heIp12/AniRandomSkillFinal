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
import buff.Barrier;
import buff.Buff;
import buff.PowerUp;
import buff.Reflect;
import buff.TimeStop;
import chars.c.c00main;
import chars.c3.c134siro;
import mode.MLoboTomy;
import types.BuffType;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_Lobotomy extends GUIBase{
	PlayerInfo info;
	boolean targetopen = false;
	int item = 11;
	List<ItemStack> str;
	List<Integer> ints;
	boolean stop = false;
	public G_Lobotomy(Player c,List<ItemStack> str,List<Integer> i) {
		super(c);
		name = "Lobo Box";
		line = 1;

		this.str = str;
		int size = str.size();
		if(size == 1) {
			page = new int[]
					{
							   0, 1, 1, 1, 1, 1, 1, 1, 0
					};
		} else if(size == 2) {
			page = new int[]
					{
							   0, 0, 0, 1, 0, 2, 0, 0, 0
					};
		} else if(size == 3) {
			page = new int[]
					{
							   0, 0, 1, 0, 2, 0, 3, 0, 0
					};
		} else if (size == 4) {
			page = new int[]
					{
							   0, 1, 0, 2, 0, 3, 0, 4, 0
					};
		} else if (size == 5) {
			page = new int[]
					{
							   0, 0, 1, 2, 3, 4, 5, 0, 0
					};
		} else if (size == 6) {
			page = new int[]
					{
							   0, 1, 2, 3, 0, 4, 5, 6, 0
					};
		} else if (size == 7) {
			page = new int[]
					{
							   0, 1, 2, 3, 4, 5, 6, 7, 0
					};
		} else if (size == 8) {
			page = new int[]
					{
							   1, 2, 3, 4, 0, 5, 6, 7, 8
					};
		} else if (size == 9) {
			page = new int[]
					{
							   1, 2, 3, 4, 5, 6, 7, 8, 9
					};
		}

		for(int o = 0; o < str.size();o++) {
			ItemRep(o+1, str.get(o));
		}
		
		info = Rule.playerinfo.get(player);
		ints = i;
		player.closeInventory();
		InvCreate(line,0);
	}
	
	public void click0(boolean right,boolean shift) {}
	
	public void click1(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints.get(0),1);
	}
	public void click2(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints.get(1),1);
	}
	public void click3(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints.get(2),1);
	}
	public void click4(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints.get(3),1);
	}
	public void click5(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints.get(4),1);
	}
	public void click6(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints.get(5),1);
	}
	public void click7(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints.get(6),1);
	}
	public void click8(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints.get(7),1);
	}
	public void click9(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints.get(8),1);
	}
	
	void end(){
		stop = true;
		player.closeInventory();
		ARSystem.giveBuff(player, new TimeStop(player), 1);
	}
	
	@Override
	public void Close(InventoryCloseEvent e) {
		if(!stop) {
			stop = true;
			player.closeInventory();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				new G_Lobotomy(player, str, ints);
			},20+AMath.random(40));
		}
		super.Close(e);
	}
}