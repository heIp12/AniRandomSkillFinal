package ars.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
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
import util.Map;
import util.Text;

public class G_Option extends GUIBase{
	PlayerInfo info;
	public G_Option(Player p) {
		super(p);
		name = "Option";
		page = new int[]
			{
			   0,11, 0,12, 0,13, 0, 0, 0,
			   0, 1, 2, 3, 4, 5, 0, 6, 0,
			   0, 0, 0, 0, 0, 0, 0, 0, 0
			};
		
		info = Rule.playerinfo.get(player);
		InvCreate(3,0);
		
	}
	
	public ItemStack gui1(){
		ItemStack is = ItemCreate.Item(340);
		return ItemCreate.Name(is,"§f"+Main.GetText("main:info36"));
	}
	
	public ItemStack gui2(){
		ItemStack is = ItemCreate.Item(391);
		return ItemCreate.Name(is,"§f"+Main.GetText("main:mode0") + ": " + Rule.pick);
	}
	public ItemStack gui3(){
		ItemStack is = ItemCreate.Item(449);
		return ItemCreate.Name(is,"§f"+Main.GetText("main:cmd11") + ": " + Rule.auto);
	}
	public ItemStack gui4(){
		ItemStack is = ItemCreate.Item(442);
		return ItemCreate.Lore(is,"§f"+Main.GetText("main:info41") + ": " + ARSystem.time, new String[]{"§7"+Main.GetText("main:info37"),"§7"+Main.GetText("main:info38")});
	}
	public ItemStack gui5(){
		ItemStack is = ItemCreate.Item(166);
		return ItemCreate.Name(is,"§f"+Main.GetText("main:info42") + ": " + ARSystem.ban);
	}
	public ItemStack gui6(){
		ItemStack is = ItemCreate.Item(267);
		return ItemCreate.Name(is,"§f"+Main.GetText("main:cmd2"));
	}
	
	public void click0(boolean right,boolean shift) {}
	
	public void click1(boolean right,boolean shift) {
		new G_OpModes(player);
	}
	public void click2(boolean right,boolean shift) {
		Rule.pick = !Rule.pick;
		Rule.Var.Save("info.option.pick", Rule.pick);
		Bukkit.broadcastMessage("§a§l[ARSystem] :§c§l " + Main.GetText("main:mode0")+" : " + Rule.pick);
		new G_Option(player);
	}
	public void click3(boolean right,boolean shift) {
		Rule.auto =! Rule.auto;
		Rule.Var.Save("info.option.auto", Rule.auto);
		new G_Option(player);
	}
	
	public void click4(boolean right,boolean shift) {
		if(right) {
			if(shift) ARSystem.time+=4;
			ARSystem.time++;
		} else {
			if(shift) ARSystem.time-=4;
			ARSystem.time--;
			if(ARSystem.time < 0) {
				ARSystem.time = 0;
			}
		}

		Rule.Var.setInt("info.option.time", ARSystem.time);
		new G_Option(player);
	}
	public void click5(boolean right,boolean shift) {
		ARSystem.ban = !ARSystem.ban;
		Rule.Var.Save("info.option.ban", ARSystem.ban);
		new G_Option(player);
	}
	
	public void click6(boolean right,boolean shift) {
		Map.mapid = 0;
		ARSystem.Start(-1);
	}
	
	public ItemStack gui11(){
		ItemStack is;
		if(ARSystem.selectGameMode.contains("mirror")) {
			if(ARSystem.serverOne == 0) {
				is = ItemCreate.Item(166);
			} else {
				is = GetChar.getColor(ARSystem.serverOne);
			}
			String n = "§f"+Main.GetText("main:mode2") +" : ["+ARSystem.serverOne+"]" + Main.GetText("c"+ARSystem.serverOne+":name1")+" "+ Main.GetText("c"+ARSystem.serverOne+":name2");
			return ItemCreate.Lore(is,n,new String[]{"§7"+Main.GetText("main:info37"),"§7"+Main.GetText("main:info38")});
		}
		else {
			return gui0();
		}
	}
	
	public void click11(boolean right,boolean shift) {
		if(ARSystem.selectGameMode.contains("mirror")) {
			if(right) {
				if(shift) ARSystem.serverOne+=4;
				ARSystem.serverOne++;
				if(ARSystem.serverOne >= GetChar.getCount()) {
					ARSystem.serverOne = GetChar.getCount();
				}
			} else {
				if(shift) ARSystem.serverOne-=4;
				ARSystem.serverOne--;
				if(ARSystem.serverOne < 0) {
					ARSystem.serverOne = 0;
				}
			}
			new G_Option(player);
		}
	}
	
	public ItemStack gui12(){
		if(Rule.auto) {
			ItemStack is = ItemCreate.Item(269);
			return ItemCreate.Lore(is, "§f"+Main.GetText("main:info40") + ": " + ARSystem.starttime, new String[] {"§7"+Main.GetText("main:info37"),"§7"+Main.GetText("main:info38")});
		} else {
			return gui0();
		}
	}
	
	public void click12(boolean right,boolean shift) {
		if(Rule.auto) {
			if(right) {
				if(shift) ARSystem.starttime+=4;
				ARSystem.starttime++;
			} else {
				if(shift) ARSystem.starttime-=4;
				ARSystem.starttime--;
				if(ARSystem.starttime < 5) {
					ARSystem.starttime = 5;
				}
			}
			Rule.Var.setInt("info.option.starttime",ARSystem.starttime);
			new G_Option(player);
		}
	}
	
	public ItemStack gui13(){
		if(ARSystem.ban) {
			return ItemCreate.Name(ItemCreate.Item(340),"§cBanList");
		} else {
			return gui0();
		}
	}
	
	public void click13(boolean right,boolean shift) {
		if(ARSystem.ban) {
			new G_CharBan(player,0);
		}
	}
}