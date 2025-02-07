package ars.gui.solo;

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
import ars.gui.G_AdvSelect;
import ars.gui.G_HeddenSelect;
import ars.gui.G_Nitory;
import ars.gui.G_RareShop;
import ars.gui.G_Select;
import ars.gui.G_Supply;
import chars.c2.c58nao;
import util.AMath;
import util.BlockUtil;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_SoloMenu extends GUIBase{

	PlayerInfo info;
	
	public G_SoloMenu(Player p) {
		super(p);
		if(!(Bukkit.getOnlinePlayers().size() <= 1 || Rule.ishelp(p) || Rule.oplist.contains(player.getName()))) {
			return;
		}
		name = "Cheat Menu";
		line = 5;
		page = new int[]
				{
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
						   0, 1, 2, 3, 0, 0, 8, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
						   0, 4, 5, 6, 7, 9,10, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0
				};
		line = 3;
		info = Rule.playerinfo.get(player);
		ScrollInvCreate(line,0);
	}

	
	public ItemStack gui1(){
		ItemStack item = GetChar.getColor(1);
		return ItemCreate.Name(item,Text.get("main:solo1"));
	}
	public ItemStack gui2(){
		ItemStack item = GetChar.getColor(1118);
		return ItemCreate.Name(item,Text.get("main:solo2"));
	}
	public ItemStack gui3(){
		ItemStack item = GetChar.getColor(994-500);
		return ItemCreate.Name(item,Text.get("main:solo3"));
	}

	public ItemStack gui4(){
		ItemStack item = ItemCreate.Item(54);
		return ItemCreate.Name(item,Text.get("main:solo4"));
	}
	public ItemStack gui5(){
		ItemStack item = ItemCreate.Item(54);
		return ItemCreate.Name(item,Text.get("main:solo5"));
	}
	public ItemStack gui6(){
		ItemStack item = ItemCreate.Item(340);
		return ItemCreate.Name(item,Text.get("main:solo6"));
	}
	public ItemStack gui7(){
		ItemStack item = ItemCreate.Item(383);
		return ItemCreate.Name(item,Text.get("main:solo7"));
	}
	public ItemStack gui8(){
		ItemStack item = ItemCreate.Item(373);
		return ItemCreate.Name(item,Text.get("main:solo8"));
	}
	public ItemStack gui9(){
		ItemStack item = ItemCreate.Item(54);
		return ItemCreate.Name(item,Text.get("main:solo9"));
	}
	public ItemStack gui10(){
		ItemStack item = ItemCreate.Item(54);
		return ItemCreate.Name(item,Text.get("main:solo10"));
	}
	
	public void click0(boolean right,boolean shift) {}
	public void click1(boolean right,boolean shift) {
		new G_Select(player, null);
	}

	public void click2(boolean right,boolean shift) {
		new G_AdvSelect(player);
	}
	public void click3(boolean right,boolean shift) {
		new G_HeddenSelect(player);
	}

	public void click4(boolean right,boolean shift) {
		new G_Supply(player);
	}
	public void click5(boolean right,boolean shift) {
		new G_Nitory(player);
	}
	public void click6(boolean right,boolean shift) {
		new G_MapSelect(player);
	}
	public void click7(boolean right,boolean shift) {
		new G_MobSelect(player);
	}
	public void click8(boolean right,boolean shift) {
		if(Rule.c.get(player) != null) {
			new G_Stats(player);
		} else {
			player.sendMessage("Not Select");
		}
	}
	public void click9(boolean right,boolean shift) {
		new G_ItemSelect(player,10000);
	}
	public void click10(boolean right,boolean shift) {
		new G_RareShop(player);
	}
}