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
import chars.c2.c58nao;
import util.AMath;
import util.BlockUtil;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_Menu extends GUIBase{

	PlayerInfo info;
	boolean targetopen = false;
	
	public G_Menu(Player p) {
		super(p);
		name = "Menu";
		line = 5;
		if(Rule.ishelp(p)) {
			page = new int[]
					{
							   0,50, 0, 0, 0, 0, 0, 0, 0,
							   0, 1, 0, 2, 3, 6, 5, 4, 0,
							   0, 0, 0, 0, 0, 0, 0, 0, 0,
							   0, 13, 0, 7, 8, 0, 0,11,12,
							   0, 0, 0, 0, 0, 0, 0, 0, 0
					};
		}
		else if(p.isOp()) {
			page = new int[]
					{
							   0,50, 0, 0, 0, 0, 0, 0, 0,
							   0, 1, 0, 2, 3, 6, 5, 4, 0,
							   0, 0, 7, 0, 0, 0, 0, 0, 0
					};
			line = 3;
		} else {
			page = new int[]
				{
						   0,50, 0, 0, 0, 0, 0, 0, 0,
						   0, 1, 0, 2, 3, 6, 5, 4, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0
				};
			line = 3;
		}
		info = Rule.playerinfo.get(player);
		InvCreate(line,0);
	}
	
	public ItemStack gui1(){
		int playerc = info.playerc;
		ItemStack is = null;
		if(info.gamejoin) {
			is =  GetChar.getColor(playerc);
		} else {
			is =  GetChar.getNoColor(playerc);
		}
		ItemMeta meta = is.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setUnbreakable(true);
		meta.setDisplayName("§f"+player.getName() + Main.GetText("main:info1"));
			
		List<String> lore = new ArrayList<String>();
		lore.add("§e=====================================");
		if(playerc != 0) lore.add("§f"+Main.GetText("main:info16")+"§l"+Main.GetText("c"+playerc+":name1").replace("-", "") +" "+ Main.GetText("c"+playerc+":name2"));
		lore.add("§7"+Main.GetText("main:info6")+" "+ info.name);
		lore.add("§7"+Main.GetText("main:info23")+" "+ Main.GetText("main:ke"+info.kille));
		lore.add("§7"+Main.GetText("main:info5")+" "+ info.getcradit());
		lore.add("§7"+Main.GetText("main:info24")+" "+  info.getScore());
		lore.add("§e=====================================");
		lore.add("§7"+Main.GetText("main:info21")+" "+ info.max(player.getName()+".c","Time")+"(s)");
		lore.add("§7"+Main.GetText("main:info2")+" "+ info.max(player.getName()+".c","Play"));
		lore.add("§7"+Main.GetText("main:info3")+" "+ info.max(player.getName()+".c","Win"));
		lore.add("§7"+Main.GetText("main:info4")+" "+ info.max(player.getName()+".c","Kill"));
		lore.add("§e=====================================");
		lore.add("§7"+Main.GetText("main:info19"));
		lore.add("§7"+Main.GetText("main:info20"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack gui2(){
		ItemStack item = ItemCreate.Item(340, 0);
		item = ItemCreate.Name(item,"§f"+Text.get("main:info7"));
		return item;
	}
	public ItemStack gui3(){
		ItemStack item = ItemCreate.Item(386, 0);
		item = ItemCreate.Name(item,"§f"+Text.get("main:info50"));
		return item;
	}
	public ItemStack gui4(){
		ItemStack item = ItemCreate.Item(339, 0);
		List<String> list = new ArrayList<String>();
		list.add("§e=====================================");
		list.add("§7"+Text.get("main:shop12"));
		return ItemCreate.Lore(item,"§f"+Text.get("main:shop11"),list);
	}
	public void click4(boolean right,boolean shift) {
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 0) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(Rule.c.get(p) instanceof c58nao) continue;
				p.hidePlayer(player);
				player.hidePlayer(p);
				p.showPlayer(player);
				player.showPlayer(p);
			}
			for(int i=0;i<300;i++) {
				if(!BlockUtil.isPathable(player.getLocation().getBlock().getType()))
					player.teleport(player.getLocation().add(0,1,0));
			}
			if(!BlockUtil.isPathable(player.getLocation().clone().add(0,1,0).getBlock().getType())) {
				player.teleport(Map.randomLoc(player));
			}
		}
	}
	
	public ItemStack gui5(){
		ItemStack item = ItemCreate.Item(426, 0);
		item = ItemCreate.Name(item,"§f"+Text.get("main:info8"));
		return item;
	}
	public ItemStack gui6(){
		ItemStack item = ItemCreate.Item(266, 0);
		item = ItemCreate.Name(item,"§f"+Text.get("main:info9"));
		return item;
	}
	public ItemStack gui7(){
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return gui0();
		ItemStack item = ItemCreate.Item(339, 0);
		item = ItemCreate.Name(item,"§f"+Text.get("main:info35"));
		return item;
	}
	
	public ItemStack gui8(){
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return gui0();
		ItemStack item = ItemCreate.Item(403, 0);
		item = ItemCreate.Name(item,"§fAdv Select");
		return item;
	}
	
	public ItemStack gui11(){
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return gui0();
		ItemStack item = ItemCreate.Item(422, 0);
		item = ItemCreate.Name(item,"§fHedden Select");
		return item;
	}
	
	public ItemStack gui12(){
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return gui0();
		ItemStack item = ItemCreate.Item(339, 0);
		item = ItemCreate.Lore(item,"§fTarget Open", new String[] {"쉬프트 클릭으로 대상을 선택하여 인벤토리를 열어줄수 있다","true시 대상에게 인벤토리를 띄워주고","false시 자신이 선택한 캐릭터로 변경시킨다"});
		return item;
	}
	
	public ItemStack gui13(){
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return gui0();
		ItemStack item = ItemCreate.Item(426, 0);
		item = ItemCreate.Name(item,"§fSupply Selct");
		return item;
	}
	
	public ItemStack gui50(){
		ItemStack item = ItemCreate.Item(293, 1400 + AMath.random(42));
		item = ItemCreate.Name(item,"§f"+Text.get("main:shop19"));
		return item;
	}
	
	public void click0(boolean right,boolean shift) {}
	public void click1(boolean right,boolean shift) {
		ARSystem.playSound(player,"0click");
		if(shift) {
			info.gamejoin = !info.gamejoin;
			Rule.Var.Save(player.getName()+".gamejoin", info.gamejoin);
			if(info.gamejoin) {
				player.sendMessage("§a§l[ARSystem] : §a" + Main.GetText("main:cmd12"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c" + Main.GetText("main:cmd13"));
			}
			if(info.gamejoin) {
				ItemRep(1,gui1());
			} else {
				ItemRep(1,gui1());
			}
		} else if(ARSystem.AniRandomSkill == null){
			for(int k =0; k<100;k++) {
				String s = Main.GetText("tropy:c"+info.playerTrophy+"_"+k);
				if(s != null) {
					if(s.contains("[Effect]")) {
						if(MSUtil.isbuff(player, "ty"+info.playerTrophy)) {
							MSUtil.buffoff(player, "ty"+info.playerTrophy);
						} else {
							MSUtil.resetbuff(player);
							player.performCommand("c ty"+info.playerTrophy);
						}
					}
				}
			}
		}
		InvCreate(line,scroll);
	}
	public void click2(boolean right,boolean shift) {
		new G_CharWin(player);
	}
	public void click3(boolean right,boolean shift) {
		new G_CharInfo(player);
	}

	public void click5(boolean right,boolean shift) {
		new G_Shop(player);
	}
	public void click6(boolean right,boolean shift) {
		new G_Tropy(player);
	}
	public void click7(boolean right,boolean shift) {
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return;
		new G_Option(player);
	}
	
	public void click8(boolean right,boolean shift) {
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return;
		if(targetopen) {
			new G_AdvSelect(Rule.playerinfo.get(player).target);
		} else {
			new G_AdvSelect(player);
		}
	}
	
	public void click11(boolean right,boolean shift) {
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return;
		if(targetopen) {
			new G_HeddenSelect(Rule.playerinfo.get(player).target);
		} else {
			new G_HeddenSelect(player);
		}
	}
	
	public void click12(boolean right,boolean shift) {
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return;
		if(shift) {
			new G_TargetSelect(player);
		} else {
			targetopen = !targetopen;
			player.sendMessage(""+targetopen);
		}
	}
	
	public void click13(boolean right,boolean shift) {
		if(!(player.isOp() || Rule.ishelp(player) || Rule.oplist.contains(player))) return;
		new G_Supply(Rule.playerinfo.get(player).target);
	}
	
	public void click50(boolean right,boolean shift) {
		new G_Imj(player);
	}
}