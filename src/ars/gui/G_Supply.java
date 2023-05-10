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
import buff.Buff;
import buff.PowerUp;
import buff.TimeStop;
import types.BuffType;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_Supply extends GUIBase{
	PlayerInfo info;
	boolean targetopen = false;
	
	public G_Supply(Player p) {
		super(p);
		name = "Supply";
		line = 3;

		page = new int[]
				{
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0
				};
		page[10] = page[13] = page[16] = AMath.random(7);
		while(page[10] == page[13]) page[13] = AMath.random(7);
		while(page[13] == page[16] || page[10] == page[16]) page[16] = AMath.random(7);
		info = Rule.playerinfo.get(player);
		if(Rule.c.get(player) == null) player.closeInventory();
		InvCreate(line,0);
	}
	
	public ItemStack gui1(){
		ItemStack item = ItemCreate.Item(347, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("main:supply1"),Text.getLine("main:supply1-", 1));
		return item;
	}
	
	public ItemStack gui2(){
		ItemStack item = ItemCreate.Item(288, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("main:supply2"),Text.getLine("main:supply2-", 1));
		return item;
	}
	public ItemStack gui3(){
		ItemStack item = ItemCreate.Item(277, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("main:supply3"),Text.getLine("main:supply3-", 1));
		return item;
	}
	public ItemStack gui4(){
		ItemStack item = ItemCreate.Item(374, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("main:supply4"),Text.getLine("main:supply4-", 1));
		return item;
	}
	public ItemStack gui5(){
		ItemStack item = ItemCreate.Item(322, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("main:supply5"),Text.getLine("main:supply5-", 1));
		return item;
	}
	public ItemStack gui6(){
		ItemStack item = ItemCreate.Item(267, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("main:supply6"),Text.getLine("main:supply6-", 1));
		return item;
	}
	public ItemStack gui7(){
		ItemStack item = ItemCreate.Item(426, 0);
		item = ItemCreate.Lore(item,"§f"+Text.get("main:supply7"),Text.getLine("main:supply7-", 1));
		return item;
	}
	
	public void click0(boolean right,boolean shift) {}
	public void click1(boolean right,boolean shift) {
		Rule.c.get(player).skillmult += 1;
		Rule.buffmanager.selectBuffValue(player, "buffac",1f);
		end();
	}
	public void click2(boolean right,boolean shift) {
		for(int i =0; i<10; i++) {
			Rule.c.get(player).setcooldown[i] *= 0.5f;
		}
		end();
	}
	public void click3(boolean right,boolean shift) {
		int r = AMath.random(5)-1;
		for(int i =0; i<4; i++) {
			if(i == r) {
				int c = Rule.c.get(player).getCode();
				if(Text.get("c"+c+":sk"+i) != null) {
					player.sendMessage("§a§l[ARSystem] §fBuff : " + Text.get("c"+c+":sk"+i));
					Rule.c.get(player).setcooldown[i] *= 0.08f;
				} else {
					player.sendMessage("§a§l[ARSystem] §cFind : No Skill : skill"+i );
				}
			} else {
				Rule.c.get(player).setcooldown[i] *= 1.5f;
			}
		}
		end();
	}
	public void click4(boolean right,boolean shift) {
		ARSystem.heal(player, 10000);
		for(int i =0; i<10; i++) {
			Rule.c.get(player).cooldown[i] = 0;
		}
		for(Buff buff : Rule.buffmanager.getBuffs(player).getBuff()) {
			buff.setTime(0);
		}
		end();
	}
	public void click5(boolean right,boolean shift) {
		ARSystem.overheal(player, player.getMaxHealth());
		end();
	}
	public void click6(boolean right,boolean shift) {
		ARSystem.giveBuff(player, new PowerUp(player), 300, 4);
		end();
	}
	public void click7(boolean right,boolean shift) {
		if(GetChar.advList().contains(Rule.playerinfo.get(player).playerc)) {
			Rule.c.put(player,GetChar.getAdv(player, Rule.gamerule, ""+ Rule.playerinfo.get(player).playerc, null));
		} else {
			int i = GetChar.advList().get(AMath.random(GetChar.advList().size()-1));
			Rule.c.put(player,GetChar.getAdv(player, Rule.gamerule, ""+i , null));
		}
		end();
	}
	
	void end(){
		player.closeInventory();
		player.teleport(Map.randomLoc(player));
		ARSystem.giveBuff(player, new TimeStop(player), 1);
	}
}