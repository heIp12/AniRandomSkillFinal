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
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_UMenu extends GUIBase{
	PlayerInfo info;
	boolean targetopen = false;
	Player target;
	
	public G_UMenu(Player p,Player target) {
		super(p);
		name = "UserMenu";
		line = 3;
		page = new int[]
			{
					   0, 0, 0, 0, 0, 0, 0, 0, 0,
					   0, 1, 2, 0, 0, 0, 0, 0, 0,
					   0, 0, 0, 0, 0, 0, 0, 0, 0
			};
		line = 3;
		this.target = target;
		info = Rule.playerinfo.get(target);
		InvCreate(line,0);
	}
	
	public ItemStack gui1(){
		ItemStack is = Rule.playerinfo.get(target).getHead().clone();
		SkullMeta meta = (SkullMeta)is.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("§e=====================================");
		lore.add("§7"+Main.GetText("main:info6")+" "+ info.name);
		lore.add("§7"+Main.GetText("main:info23")+" "+ Main.GetText("main:ke"+info.kille));
		lore.add("§7"+Main.GetText("main:info5")+" "+ info.getcradit());
		lore.add("§7"+Main.GetText("main:info24")+" "+  info.getScore());
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack gui2(){
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
		if(playerc > 0) meta.setDisplayName("§f"+"§f"+Main.GetText("main:info16")+"§l"+Main.GetText("c"+playerc+":name1").replace("-", "") +" "+ Main.GetText("c"+playerc+":name2"));
			
		List<String> lore = new ArrayList<String>();

		lore.add("§7"+Main.GetText("main:info21")+" "+ info.max(target.getName()+".c","Time")+"(s)");
		lore.add("§7"+Main.GetText("main:info2")+" "+ info.max(target.getName()+".c","Play"));
		lore.add("§7"+Main.GetText("main:info3")+" "+ info.max(target.getName()+".c","Win"));
		lore.add("§7"+Main.GetText("main:info4")+" "+ info.max(target.getName()+".c","Kill"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	
	public void click0(boolean right,boolean shift) {}
}