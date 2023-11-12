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
import chars.c3.c131saito;
import types.BuffType;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_Saito extends GUIBase{
	PlayerInfo info;
	
	public G_Saito(Player p) {
		super(p);
		name = "Saito Select";
		line = 3;

		page = new int[]
				{
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
						   0, 0, 1, 0, 2, 0, 3, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0
				};
		InvCreate(line,0);
		ARSystem.giveBuff(player, new TimeStop(player), 200);
	}
	
	public ItemStack gui1(){
		ItemStack item = ItemCreate.Item(267, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c131:p1"),Text.getLine("c131:p1_lore", 1));
		return item;
	}
	
	public ItemStack gui2(){
		ItemStack item = ItemCreate.Item(292, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c131:p2"),Text.getLine("c131:p2_lore", 1));
		return item;
	}
	public ItemStack gui3(){
		ItemStack item = ItemCreate.Item(359, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c131:p3"),Text.getLine("c131:p3_lore", 1));
		return item;
	}
	
	public void click0(boolean right,boolean shift) {}
	public void click1(boolean right,boolean shift) {
		if(Rule.c.get(player) instanceof c131saito) {
			((c131saito)Rule.c.get(player)).setChar(1);
			Rule.c.get(player).setcooldown[1] *= 0.7;
			Rule.c.get(player).setcooldown[2] *= 0.9;
			Rule.c.get(player).setcooldown[4] *= 0.7;
		}
		end();
	}
	public void click2(boolean right,boolean shift) {
		if(Rule.c.get(player) instanceof c131saito) {
			((c131saito)Rule.c.get(player)).setChar(2);
			Rule.c.get(player).setcooldown[1] *= 1.5;
			Rule.c.get(player).setcooldown[2] *= 1.5;
			Rule.c.get(player).setcooldown[3] *= 0.8;
			Rule.c.get(player).setcooldown[4] *= 2;
		}
		end();
	}
	public void click3(boolean right,boolean shift) {
		if(Rule.c.get(player) instanceof c131saito) {
			((c131saito)Rule.c.get(player)).setChar(3);
			Rule.c.get(player).setcooldown[1] *= 1.2;
			Rule.c.get(player).setcooldown[2] *= 0.8;
			Rule.c.get(player).setcooldown[3] *= 0.7;
			Rule.c.get(player).setcooldown[4] *= 1.6;
		}
		end();
	}
	
	void end(){
		player.closeInventory();
		ARSystem.giveBuff(player, new TimeStop(player), 1);
		for(int i=0;i<10;i++) Rule.c.get(player).cooldown[i] = 0;
	}
}