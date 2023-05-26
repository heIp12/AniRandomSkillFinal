package ars.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import Main.Main;
import ars.ARSystem;
import ars.PlayerInfo;
import ars.Rule;
import chars.c3.c124satory;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_Satory extends GUIBase{
	PlayerInfo info;
	boolean targetopen = false;
	
	public G_Satory(Player p) {
		super(p);
		name = "Satory Select";
		line = 3;
		page = new int[]
			{
					   0, 1, 2, 3, 4, 5, 6, 7, 0
			};
		line = 1;
		info = Rule.playerinfo.get(player);
		InvCreate(line,0);
	}
	
	public ItemStack gui1(){
		return ItemCreate.Name(ItemCreate.Item(339),"§f"+Text.get("main:tp1"));
	}
	public ItemStack gui2(){
		return ItemCreate.Name(ItemCreate.Item(339),"§f"+Text.get("main:tp2"));
	}
	public ItemStack gui3(){
		return ItemCreate.Name(ItemCreate.Item(339),"§f"+Text.get("main:tp3"));
	}
	public ItemStack gui4(){
		return ItemCreate.Name(ItemCreate.Item(339),"§f"+Text.get("main:tp4"));
	}
	public ItemStack gui5(){
		return ItemCreate.Name(ItemCreate.Item(339),"§f"+Text.get("main:tp5"));
	}
	public ItemStack gui6(){
		return ItemCreate.Name(ItemCreate.Item(339),"§f"+Text.get("main:tp6"));
	}
	public ItemStack gui7(){
		return ItemCreate.Name(ItemCreate.Item(339),"§f"+Text.get("main:tp7"));
	}
	
	public void click1(boolean right,boolean shift) {
		if(Rule.c.get(player) !=null) {
			if(Rule.c.get(player) instanceof c124satory) {
				((c124satory)Rule.c.get(player)).type = 1;
				player.closeInventory();
			}
		}
	}
	
	public void click2(boolean right,boolean shift) {
		if(Rule.c.get(player) !=null) {
			if(Rule.c.get(player) instanceof c124satory) {
				((c124satory)Rule.c.get(player)).type = 2;
				player.closeInventory();
			}
		}
	}
	
	public void click3(boolean right,boolean shift) {
		if(Rule.c.get(player) !=null) {
			if(Rule.c.get(player) instanceof c124satory) {
				((c124satory)Rule.c.get(player)).type = 3;
				player.closeInventory();
			}
		}
	}
	
	public void click4(boolean right,boolean shift) {
		if(Rule.c.get(player) !=null) {
			if(Rule.c.get(player) instanceof c124satory) {
				((c124satory)Rule.c.get(player)).type = 4;
				player.closeInventory();
			}
		}
	}
	
	public void click5(boolean right,boolean shift) {
		if(Rule.c.get(player) !=null) {
			if(Rule.c.get(player) instanceof c124satory) {
				((c124satory)Rule.c.get(player)).type = 5;
				player.closeInventory();
			}
		}
	}
	
	public void click6(boolean right,boolean shift) {
		if(Rule.c.get(player) !=null) {
			if(Rule.c.get(player) instanceof c124satory) {
				((c124satory)Rule.c.get(player)).type = 6;
				player.closeInventory();
			}
		}
	}
	
	public void click7(boolean right,boolean shift) {
		if(Rule.c.get(player) !=null) {
			if(Rule.c.get(player) instanceof c124satory) {
				((c124satory)Rule.c.get(player)).type = 7;
				player.closeInventory();
			}
		}
	}
	
	public void click0(boolean right,boolean shift) {}
}