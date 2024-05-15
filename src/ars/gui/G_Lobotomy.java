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
	ItemStack[] str;
	int[] ints;
	boolean stop = false;
	public G_Lobotomy(Player c,ItemStack[] str,int[] i) {
		super(c);
		name = "Lobo Box";
		line = 1;

		this.str = str.clone();
		page = new int[]
				{
						   0, 0, 1, 0, 2, 0, 3, 0, 0
				};
		

		for(int o = 0; o < str.length;o++) {
			ItemRep(o+1, str[o]);
		}
		
		info = Rule.playerinfo.get(player);
		ints = i;
		InvCreate(line,0);
	}
	
	public void click0(boolean right,boolean shift) {}
	
	public void click1(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints[0]);
	}
	public void click2(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints[1]);
	}
	public void click3(boolean right,boolean shift) {
		end();
		MLoboTomy.addbuff(""+ints[2]);
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
			});
		}
		super.Close(e);
	}
}