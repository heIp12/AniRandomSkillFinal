package ars.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
import types.ItemList;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_Item extends GUIBase{
	PlayerInfo info;
	boolean targetopen = false;
	List<Integer> itemcode;
	int size = 0;
	boolean stop = false;
	int code = 0;
	int up,down;
	
	public G_Item(Player p,int siz,int up,int down) {
		super(p);
		if(Rule.playerinfo.get(player).itemcount <= ARSystem.playerItem.get(player).items.size()) return;
		if(ARSystem.AniRandomSkill != null) code = ARSystem.AniRandomSkill.getGamecode();
		
		name = "Item Box";
		line = 3;
		if(Rule.c.get(p) == null) {
			stop = true;
			p.closeInventory();
		}
		itemcode = new ArrayList<>();
		this.up = up;
		this.down = down;
		size = siz;
		if(Rule.playerinfo.get(player).abchar && AMath.random(10) <= 3) size += 1;
		if(size > 9) size = 9;
		if(size == 1) {
			page = new int[]
					{
							   0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 1, 1, 1, 1, 1, 1, 1, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if(size == 2) {
			page = new int[]
					{
						       0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 0, 0, 1, 0, 2, 0, 0, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if(size == 3) {
			page = new int[]
					{
					    	   0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 0, 1, 0, 2, 0, 3, 0, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 4) {
			page = new int[]
					{
						       0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 1, 0, 2, 0, 3, 0, 4, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 5) {
			page = new int[]
					{
							   0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 0, 1, 2, 3, 4, 5, 0, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 6) {
			page = new int[]
					{
				    	       0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 1, 2, 3, 0, 4, 5, 6, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 7) {
			page = new int[]
					{
				    	       0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 1, 2, 3, 4, 5, 6, 7, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 8) {
			page = new int[]
					{
				    	       0, 0, 0, 0, 0, 0, 0, 0,99,
							   1, 2, 3, 4, 0, 5, 6, 7, 8,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 9) {
			page = new int[]
					{
				    	       0, 0, 0, 0, 0, 0, 0, 0,99,
							   1, 2, 3, 4, 5, 6, 7, 8, 9,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		}
		
		for(int i =0; i < size; i++) {
			int code = -1;
			int o = 0;
			while(code == -1) {
				code = ItemList.getValueCode(up, down);
				for(int it : itemcode) {
					if(it == code) {
						code = -1;
						break;
					}
				}
				
				o++;
				if(o > 10000) {
					System.out.println(" " + up +"," + down);
					System.out.println("item such error");
					code = -1;
					break;
				}
			}
			o = 0;
			itemcode.add(code);
			
		}
		info = Rule.playerinfo.get(player);
		InvCreate(line,0);
		ARSystem.giveBuff(player, new TimeStop(player), 200);
	}
	
	public G_Item(Player p,List<Integer> items) {
		super(p);
		if(Rule.playerinfo.get(player).itemcount <= ARSystem.playerItem.get(player).items.size()) return;
		if(ARSystem.AniRandomSkill != null) code = ARSystem.AniRandomSkill.getGamecode();
		
		name = "Item Box";
		line = 3;
		if(Rule.c.get(p) == null) {
			stop = true;
			p.closeInventory();
		}
		itemcode = items;
		
		size = items.size();
		if(size > 9) size = 9;
		if(size == 1) {
			page = new int[]
					{
							   0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 1, 1, 1, 1, 1, 1, 1, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if(size == 2) {
			page = new int[]
					{
						       0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 0, 0, 1, 0, 2, 0, 0, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if(size == 3) {
			page = new int[]
					{
					    	   0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 0, 1, 0, 2, 0, 3, 0, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 4) {
			page = new int[]
					{
						       0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 1, 0, 2, 0, 3, 0, 4, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 5) {
			page = new int[]
					{
							   0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 0, 1, 2, 3, 4, 5, 0, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 6) {
			page = new int[]
					{
				    	       0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 1, 2, 3, 0, 4, 5, 6, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 7) {
			page = new int[]
					{
				    	       0, 0, 0, 0, 0, 0, 0, 0,99,
							   0, 1, 2, 3, 4, 5, 6, 7, 0,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 8) {
			page = new int[]
					{
				    	       0, 0, 0, 0, 0, 0, 0, 0,99,
							   1, 2, 3, 4, 0, 5, 6, 7, 8,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		} else if (size == 9) {
			page = new int[]
					{
				    	       0, 0, 0, 0, 0, 0, 0, 0,99,
							   1, 2, 3, 4, 5, 6, 7, 8, 9,
							   0, 0, 0, 0, 0, 0, 0, 0,98
					};
		}
		
		info = Rule.playerinfo.get(player);
		InvCreate(line,0);
		ARSystem.giveBuff(player, new TimeStop(player), 200);
	}
	
	public ItemStack gui1(){
		return ItemList.getItem(itemcode.get(0)).getItem();
	}
	
	public ItemStack gui2(){
		return ItemList.getItem(itemcode.get(1)).getItem();
	}
	public ItemStack gui3(){
		return ItemList.getItem(itemcode.get(2)).getItem();
	}
	public ItemStack gui4(){
		return ItemList.getItem(itemcode.get(3)).getItem();
	}
	public ItemStack gui5(){
		return ItemList.getItem(itemcode.get(4)).getItem();
	}
	public ItemStack gui6(){
		return ItemList.getItem(itemcode.get(5)).getItem();
	}

	public ItemStack gui7(){
		return ItemList.getItem(itemcode.get(6)).getItem();
	}

	public ItemStack gui8(){
		return ItemList.getItem(itemcode.get(7)).getItem();
	}

	public ItemStack gui9(){
		return ItemList.getItem(itemcode.get(8)).getItem();
	}
	public ItemStack gui98(){
		if(Rule.c.get(player) == null || Rule.c.get(player).number != 153 || Rule.c.get(player).cooldown[0] > 0) return gui0();
		return ItemCreate.Name(ItemCreate.Item(381), Text.get("main:rr"));
	}
	public void click98(boolean right,boolean shift) {
		if(Rule.c.get(player) != null && Rule.c.get(player).number == 153 && Rule.c.get(player).cooldown[0] <= 0) {
			Rule.c.get(player).cooldown[0] = Rule.c.get(player).setcooldown[0];
			ARSystem.playSound(player, "c153sp");
			Rule.c.get(player).spskillen();
			end();
			new G_Item(player, size+1, up, down);
		}
	}
	
	public ItemStack gui99(){
		return ItemCreate.Name(ItemCreate.Item(166), Text.get("item:no"));
	}
	
	public void click0(boolean right,boolean shift) {}
	public void click1(boolean right,boolean shift) {
		ARSystem.addItem(player, itemcode.get(0));
		end();
	}
	public void click2(boolean right,boolean shift) {
		ARSystem.addItem(player, itemcode.get(1));
		end();
	}
	public void click3(boolean right,boolean shift) {
		ARSystem.addItem(player, itemcode.get(2));
		end();
	}
	public void click4(boolean right,boolean shift) {
		ARSystem.addItem(player, itemcode.get(3));
		end();
	}
	public void click5(boolean right,boolean shift) {
		ARSystem.addItem(player, itemcode.get(4));
		end();
	}
	public void click6(boolean right,boolean shift) {
		ARSystem.addItem(player, itemcode.get(5));
		end();
	}
	public void click7(boolean right,boolean shift) {
		ARSystem.addItem(player, itemcode.get(6));
		end();
	}
	public void click8(boolean right,boolean shift) {
		ARSystem.addItem(player, itemcode.get(7));
		end();
	}
	public void click9(boolean right,boolean shift) {
		ARSystem.addItem(player, itemcode.get(8));
		end();
	}
	
	public void click99(boolean right,boolean shift) {
		end();
	}
	
	void end(){
		stop = true;
		player.closeInventory();
		ARSystem.giveBuff(player, new TimeStop(player), 1);
	}	
	
	@Override
	public void Close(InventoryCloseEvent e) {

		if(ARSystem.AniRandomSkill != null &&  code != ARSystem.AniRandomSkill.getGamecode()) {
			stop = true;
			player.closeInventory();
			return;
		}
		if(!stop && e.getInventory().getTitle().contains(name)) {
			stop = true;
			player.closeInventory();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				stop = false;
				new G_Item(player, itemcode);
			},15+ AMath.random(10));
		}
		super.Close(e);
	}
}