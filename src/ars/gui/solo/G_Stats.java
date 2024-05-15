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
import ars.gui.G_AdvSelect;
import ars.gui.G_HeddenSelect;
import ars.gui.G_Nitory;
import ars.gui.G_Select;
import ars.gui.G_Supply;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;
import util.ULocal;

public class G_Stats extends GUIBase{
	Player player;
	List<String> mobs = new ArrayList<>();
	int reptype = 0;
	
	public G_Stats(Player p) {
		super(p);
		name = "Stats";
		line = 5;
		page = new int[]
				{
						   0, 1, 2, 3, 4, 5, 0, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
						   0, 10, 11, 12, 0, 13, 14, 15, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0
				};
		line = 4;
		player = p;
		ScrollInvCreate(line,0);
	}

	
	public ItemStack gui1(){
		ItemStack item = ItemCreate.Item(267);
		return ItemCreate.Name(item,Text.get("main:solo8-1"));
	}
	public ItemStack gui2(){
		ItemStack item = ItemCreate.Item(442);
		return ItemCreate.Name(item,Text.get("main:solo8-2"));
	}
	public ItemStack gui3(){
		ItemStack item = ItemCreate.Item(377);
		return ItemCreate.Name(item,Text.get("main:solo8-3"));
	}
	public ItemStack gui4(){
		ItemStack item = ItemCreate.Item(260);
		return ItemCreate.Name(item,Text.get("main:solo8-4"));
	}
	public ItemStack gui5(){
		ItemStack item = ItemCreate.Item(449);
		return ItemCreate.Name(item,Text.get("main:solo8-5"));
	}
	
	public ItemStack gui10(){
		ItemStack item = ItemCreate.Item(327);
		return ItemCreate.Name(item,"+100");
	}
	public ItemStack gui11(){
		ItemStack item = ItemCreate.Item(327);
		return ItemCreate.Name(item,"+10");
	}
	public ItemStack gui12(){
		ItemStack item = ItemCreate.Item(327);
		return ItemCreate.Name(item,"+1");
	}
	public ItemStack gui13(){
		ItemStack item = ItemCreate.Item(326);
		return ItemCreate.Name(item,"-1");
	}
	public ItemStack gui14(){
		ItemStack item = ItemCreate.Item(326);
		return ItemCreate.Name(item,"-10");
	}
	public ItemStack gui15(){
		ItemStack item = ItemCreate.Item(326);
		return ItemCreate.Name(item,"-100");
	}
	
	public void click0(boolean right,boolean shift) {}
	public void click1(boolean right,boolean shift) {
		reptype = 0;
	}
	public void click2(boolean right,boolean shift) {
		reptype = 1;
	}
	public void click3(boolean right,boolean shift) {
		reptype = 2;
	}
	public void click4(boolean right,boolean shift) {
		reptype = 3;
	}
	public void click5(boolean right,boolean shift) {
		Rule.c.get(player).setStack(9999);
		player.closeInventory();
	}

	public void click10(boolean right,boolean shift) {
		Rep(100);
	}
	public void click11(boolean right,boolean shift) {
		Rep(10);
	}
	public void click12(boolean right,boolean shift) {
		Rep(1);
	}
	public void click13(boolean right,boolean shift) {
		Rep(-1);
	}
	public void click14(boolean right,boolean shift) {
		Rep(-10);
	}
	public void click15(boolean right,boolean shift) {
		Rep(-100);
	}
	
	public void Rep(int i) {
		if(reptype == 0) {
			Rule.c.get(player).frist_damage += i*0.01;
			Bukkit.broadcastMessage(Text.get("main:solo8-1") +" : " + AMath.round(Rule.c.get(player).frist_damage*100,2) +"%");
		}
		if(reptype == 1) {
			Rule.c.get(player).frist_defence += i*0.01;
			Bukkit.broadcastMessage(Text.get("main:solo8-2") +" : " + AMath.round(Rule.c.get(player).frist_defence*100,2) +"%");
		}
		if(reptype == 2) {
			Rule.c.get(player).skillmult += i*0.01;
			Bukkit.broadcastMessage(Text.get("main:solo8-3") +" : " + AMath.round(Rule.c.get(player).skillmult*100,2) +"%");
		}
		if(reptype == 3) {
			Rule.c.get(player).hp += i;
			if(i > 0) {
				player.setMaxHealth(Rule.c.get(player).hp);
				player.setHealth(Rule.c.get(player).hp);
			} else {
				player.setHealth(Rule.c.get(player).hp);
				player.setMaxHealth(Rule.c.get(player).hp);
			}
			Bukkit.broadcastMessage(Text.get("main:solo8-4") +" : " + AMath.round(Rule.c.get(player).hp,2) );
		}
	}
}