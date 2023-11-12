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
import chars.ca.c8400subi;
import chars.ch.e001mary;
import chars.ch.e002rain;
import chars.ch.e003mix;
import chars.ch.h001chruno;
import chars.ch.h002claw;
import chars.ch.h003rentaro;
import chars.ch.h004alice;
import chars.ch.h005mery;
import chars.ch.h006zagara;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_HeddenSelect extends GUIBase{
	PlayerInfo info;
	Player target;
	public G_HeddenSelect(Player p) {
		super(p);
		name = "Char Select";
		line = 2;
		page = new int[]
				{
						   1,2,3,4,5,6,101,102,103,
						   51,0,0,0,0,0,0,0,0
				};

		info = Rule.playerinfo.get(player);
		InvCreate(line,0);
		target = Rule.playerinfo.get(player).target;
	}
	
	public ItemStack gui1(){
		ItemStack item = GetChar.getColor(999-500);
		return ItemCreate.Name(item,"§a§l"+Text.get("c999:name1")+ " "+Text.get("c999:name2"));
	}
	public ItemStack gui2(){
		ItemStack item = GetChar.getColor(998-500);
		return ItemCreate.Name(item,"§a§l"+Text.get("c998:name1")+ " "+Text.get("c998:name2"));
	}
	public ItemStack gui3(){
		ItemStack item = GetChar.getColor(997-500);
		return ItemCreate.Name(item,"§a§l"+Text.get("c997:name1")+ " "+Text.get("c997:name2"));
	}
	public ItemStack gui4(){
		ItemStack item = GetChar.getColor(996-500);
		return ItemCreate.Name(item,"§a§l"+Text.get("c996:name1")+ " "+Text.get("c996:name2"));
	}
	public ItemStack gui5(){
		ItemStack item = GetChar.getColor(995-500);
		return ItemCreate.Name(item,"§a§l"+Text.get("c995:name1")+ " "+Text.get("c995:name2"));
	}
	public ItemStack gui6(){
		ItemStack item = GetChar.getColor(994-500);
		return ItemCreate.Name(item,"§a§l"+Text.get("c994:name1")+ " "+Text.get("c994:name2"));
	}
	public ItemStack gui101(){
		ItemStack item = GetChar.getColor(901-500);
		return ItemCreate.Name(item,"§a§l"+Text.get("c901:name1")+ " "+Text.get("c901:name2"));
	}
	public ItemStack gui102(){
		ItemStack item = GetChar.getColor(902-500);
		return ItemCreate.Name(item,"§a§l"+Text.get("c902:name1")+ " "+Text.get("c902:name2"));
	}
	public ItemStack gui103(){
		ItemStack item = GetChar.getColor(903-500);
		return ItemCreate.Name(item,"§a§l"+Text.get("c903:name1")+ " "+Text.get("c903:name2"));
	}
	public ItemStack gui51(){
		ItemStack item = GetChar.getColor(84);
		return ItemCreate.Name(item,"§a§l"+Text.get("c1084:name1")+ " "+Text.get("c1084:name2"));
	}
	
	public void click0(boolean right,boolean shift) {}
	public void click1(boolean right,boolean shift) {
		Rule.c.put(target, new h001chruno(target, Rule.gamerule, null));
	}
	public void click2(boolean right,boolean shift) {
		Rule.c.put(target, new h002claw(target, Rule.gamerule, null));
	}
	public void click3(boolean right,boolean shift) {
		Rule.c.put(target, new h003rentaro(target, Rule.gamerule, null));
	}
	public void click4(boolean right,boolean shift) {
		Rule.c.put(target, new h004alice(target, Rule.gamerule, null));
	}
	public void click5(boolean right,boolean shift) {
		Rule.c.put(target, new h005mery(target, Rule.gamerule, null));
	}
	public void click6(boolean right,boolean shift) {
		Rule.c.put(target, new h006zagara(target, Rule.gamerule, null));
	}
	public void click51(boolean right,boolean shift) {
		Rule.c.put(target, new c8400subi(target, Rule.gamerule, null));
	}
	public void click101(boolean right,boolean shift) {
		Rule.c.put(target, new e001mary(target, Rule.gamerule, null));
	}
	public void click102(boolean right,boolean shift) {
		Rule.c.put(target, new e002rain(target, Rule.gamerule, null));
	}
	public void click103(boolean right,boolean shift) {
		Rule.c.put(target, new e003mix(target, Rule.gamerule, null));
	}
}