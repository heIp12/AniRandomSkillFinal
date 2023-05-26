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
import types.BuffType;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_Nitory extends GUIBase{
	PlayerInfo info;
	boolean targetopen = false;
	int item = 11;
	public G_Nitory(Player p) {
		super(p);
		name = "Nitory Box";
		line = 3;

		page = new int[]
				{
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0
				};
		page[10] = page[13] = page[16] = AMath.random(item);
		while(page[10] == page[13]) page[13] = AMath.random(item);
		while(page[13] == page[16] || page[10] == page[16]) page[16] = AMath.random(item);
		info = Rule.playerinfo.get(player);
		if(Rule.c.get(player) == null) player.closeInventory();
		InvCreate(line,0);
		ARSystem.giveBuff(player, new TimeStop(player), 200);
	}
	
	public ItemStack gui1(){
		ItemStack item = ItemCreate.Item(54, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p1"),Text.getLine("c120:p1-", 1));
		return item;
	}
	
	public ItemStack gui2(){
		ItemStack item = ItemCreate.Item(401, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p2"),Text.getLine("c120:p2-", 1));
		return item;
	}
	public ItemStack gui3(){
		ItemStack item = ItemCreate.Item(401, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p3"),Text.getLine("c120:p3-", 1));
		return item;
	}
	public ItemStack gui4(){
		ItemStack item = ItemCreate.Item(322, 1);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p4"),Text.getLine("c120:p4-", 1));
		return item;
	}
	public ItemStack gui5(){
		ItemStack item = ItemCreate.Item(322, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p5"),Text.getLine("c120:p5-", 1));
		return item;
	}
	public ItemStack gui6(){
		ItemStack item = ItemCreate.Item(267, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p6"),Text.getLine("c120:p6-", 1));
		return item;
	}
	public ItemStack gui7(){
		ItemStack item = ItemCreate.Item(441, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p7"),Text.getLine("c120:p7-", 1));
		return item;
	}
	
	public ItemStack gui8(){
		ItemStack item = ItemCreate.Item(166, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p8"),Text.getLine("c120:p8-", 1));
		return item;
	}

	public ItemStack gui9(){
		ItemStack item = ItemCreate.Item(288, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p9"),Text.getLine("c120:p9-", 1));
		return item;
	}

	public ItemStack gui10(){
		ItemStack item = ItemCreate.Item(341);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p10"),Text.getLine("c120:p10-", 1));
		return item;
	}

	public ItemStack gui11(){
		ItemStack item = ItemCreate.Item(166, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("c120:p11"),Text.getLine("c120:p11-", 1));
		return item;
	}
	
	public void click0(boolean right,boolean shift) {}
	public void click1(boolean right,boolean shift) {
		new G_Supply(player);
	}
	public void click2(boolean right,boolean shift) {
		ARSystem.spellCast(player, "c120_sp1");
		end();
	}
	public void click3(boolean right,boolean shift) {
		ARSystem.spellCast(player, "c120_sp2");
		ARSystem.spellCast(player, "c120_sp22");
		end();
	}
	public void click4(boolean right,boolean shift) {
		ARSystem.spellCast(player, "c120_sp3");
		end();
	}
	public void click5(boolean right,boolean shift) {
		ARSystem.potion(player, 1, 1200, 3);
		end();
	}
	public void click6(boolean right,boolean shift) {
		Reflect rep = new Reflect(player);
		rep.setDelay(10);
		rep.SetNoDamage(false);
		ARSystem.giveBuff(player, new Reflect(player), 1200 , 0.7f);
		end();
	}
	public void click7(boolean right,boolean shift) {
		player.setMaxHealth(player.getMaxHealth() + 10);
		ARSystem.heal(player, 10);
		end();
	}
	public void click8(boolean right,boolean shift) {
		ARSystem.spellCast(player, "c120_sp8");
		end();
	}
	public void click9(boolean right,boolean shift) {
		Rule.c.get(player).skillmult += 1.5;
		end();
	}
	public void click10(boolean right,boolean shift) {
		ARSystem.giveBuff(player, new Barrier(player), 2400, 20);
		end();
	}
	public void click11(boolean right,boolean shift) {
		ARSystem.spellCast(player, "c120_sp11");
		end();
	}
	void end(){
		player.closeInventory();
		ARSystem.giveBuff(player, new TimeStop(player), 1);
	}
}