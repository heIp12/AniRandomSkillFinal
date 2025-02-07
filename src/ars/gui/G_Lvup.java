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
import buff.Barrier;
import buff.Buff;
import buff.PowerUp;
import buff.Reflect;
import buff.TimeStop;
import chars.c.c00main;
import chars.c3.c134siro;
import types.BuffType;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_Lvup extends GUIBase{
	PlayerInfo info;
	boolean targetopen = false;
	int item = 11;
	c00main siro;
	ItemStack[] str;
	public G_Lvup(c00main c,ItemStack[] str) {
		super(c.player);
		siro = c;
		name = "Lv Up";
		line = 1;


		
		this.str = str.clone();
		
		if(str.length == 1) {
			page = new int[]
					{
							   0, 0, 0, 0, 1, 0, 0, 0, 0
					};
		} else if(str.length == 2) {
			page = new int[]
					{
							   0, 0, 1, 0, 0, 0, 2, 0, 0
					};
		} else if(str.length == 3) {
			page = new int[]
					{
							   0, 0, 1, 0, 2, 0, 3, 0, 0
					};
		} else if(str.length == 4) {
			page = new int[]
					{
							   0, 1, 0, 2, 0, 3, 0, 4, 0
					};
		} else if(str.length == 5) {
			page = new int[]
					{
							   1, 0, 2, 0, 3, 0, 4, 0, 5
					};
		} else if(str.length == 6) {
			page = new int[]
					{
							   0, 1, 2, 3, 0, 4, 5, 6, 0
					};
		} else if(str.length == 7) {
			page = new int[]
					{
							   0, 1, 2, 3, 4, 5, 6, 7, 0
					};
		} else if(str.length == 8) {
			page = new int[]
					{
							   1, 2, 3, 4, 0, 5, 6, 7, 8
					};
		} else if(str.length == 9) {
			page = new int[]
					{
							   1, 2, 3, 4, 5, 6, 7, 8, 9
					};
		}

		info = Rule.playerinfo.get(player);
		for(int i = 0; i < str.length;i++) {
			ItemRep(i+1, rename(str[i].clone()));
		}
		if(Rule.c.get(player) == null) player.closeInventory();
		InvCreate(line,0);
	}
	
	ItemStack rename(ItemStack it) {
		ItemStack im = it.clone();
		if(Main.Text.get(im.getItemMeta().getDisplayName()) == null) return ItemCreate.Name(im,"§f"+ im.getItemMeta().getDisplayName());
		return ItemCreate.Name(im,"§f"+ Main.Text.get(im.getItemMeta().getDisplayName()));
	}
	
	public void click0(boolean right,boolean shift) {}
	
	public void click1(boolean right,boolean shift) {
		end();
		siro.select(str[0].getItemMeta().getDisplayName());
	}
	public void click2(boolean right,boolean shift) {
		end();
		siro.select(str[1].getItemMeta().getDisplayName());
	}
	public void click3(boolean right,boolean shift) {
		end();
		siro.select(str[2].getItemMeta().getDisplayName());
	}
	public void click4(boolean right,boolean shift) {
		end();
		siro.select(str[3].getItemMeta().getDisplayName());
	}

	public void click5(boolean right,boolean shift) {
		end();
		siro.select(str[4].getItemMeta().getDisplayName());
	}

	public void click6(boolean right,boolean shift) {
		end();
		siro.select(str[5].getItemMeta().getDisplayName());
	}

	public void click7(boolean right,boolean shift) {
		end();
		siro.select(str[6].getItemMeta().getDisplayName());
	}

	public void click8(boolean right,boolean shift) {
		end();
		siro.select(str[7].getItemMeta().getDisplayName());
	}

	public void click9(boolean right,boolean shift) {
		end();
		siro.select(str[8].getItemMeta().getDisplayName());
	}
	
	void end(){
		player.closeInventory();
		ARSystem.giveBuff(player, new TimeStop(player), 1);
	}
}