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
import buff.Buff;
import buff.Ice;
import buff.MapVoid;
import buff.NoCC;
import buff.NoHeal;
import buff.Nodie;
import buff.PowerUp;
import buff.TimeStop;
import item.list1.itemBase;
import types.BuffType;
import types.ItemList;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;

public class G_RareShop extends GUIBase{
	static public List<Integer> items = new ArrayList<>();
	static public List<Integer> sold = new ArrayList<>();
	PlayerInfo info;
	boolean targetopen = false;
	
	int item1 = 0;
	int item1value = 0;
	int item1type = 0;
	int item2 = 0;
	int item2value = 0;
	int item2type = 0;
	
	int citem[][] = {{3,90,122},{1,69,111},{124,103,5},{1,81,57,109,124},{17,53,50}};
	
	public G_RareShop(Player p) {
		super(p);
		name = "Item Shop";
		line = 5;

		page = new int[]
				{
						   0, 0, 0, 0, 0, 0, 0, 0,99,
				   	   	   0, 0, 0, 0, 0, 0, 0, 0, 0,
				   	   	   0, 0, 3, 0, 0, 0, 0, 0, 0,
					   	   0, 0, 0, 0, 0, 4, 0, 0, 0,
						   0, 0, 0, 0, 1, 0, 0, 0, 0,
						   0, 0, 0, 0, 0, 0, 0, 0, 0,
				};
		ARSystem.giveBuff(player, new TimeStop(player), 200);
		ARSystem.giveBuff(player, new MapVoid(player), 200);
		info = Rule.playerinfo.get(player);
		if(Rule.c.get(player) == null) player.closeInventory();
		InvCreate(line,0);
		getItem();
		Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
			InvRep(40, ItemCreate.Name(ItemCreate.Item(279,1516),"§a"));
		},10);
		Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
			InvRep(40, ItemCreate.Name(ItemCreate.Item(279,1510),"§a"));
			InvRep(11, ItemCreate.Name(ItemCreate.Item(279,1515),""));
			InvRep(23, ItemCreate.Name(ItemCreate.Item(279,1515),""));
			Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
				InvRep(11, ItemCreate.Name(ItemCreate.Item(279,1514),""));
				InvRep(23, ItemCreate.Name(ItemCreate.Item(279,1514),""));
			},2);
			Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
				InvRep(11, ItemCreate.Name(ItemCreate.Item(279,1513),""));
				InvRep(23, ItemCreate.Name(ItemCreate.Item(279,1513),""));
			},4);
			Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
				InvRep(11, ItemCreate.Name(ItemCreate.Item(279,1512),""));
				InvRep(23, ItemCreate.Name(ItemCreate.Item(279,1512),""));
			},6);
			Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
				InvRep(11, ItemCreate.Name(ItemCreate.Item(279,1511),""));
				InvRep(23, ItemCreate.Name(ItemCreate.Item(279,1511),""));
			},8);
			Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
				ItemStack is1 = ItemList.getItem(item1).getItem().clone();
				ItemStack is2 = ItemList.getItem(item2).getItem().clone();
				
				item1value *= getValue(item1type);
				item2value *= getValue(item2type);
				if(item1type == 6) item1value = 100 - item1value;
				if(item2type == 6) item2value = 100 - item2value;
				
				List<String> lore = is1.getItemMeta().getLore();
				lore.add("§c§l§n"+Text.get("item:rare"+item1type).replace("{%d}",""+item1value));
				is1 = ItemCreate.Lore(is1, lore);

				lore = is2.getItemMeta().getLore();
				lore.add("§c§l§n"+Text.get("item:rare"+item2type).replace("{%d}",""+item2value));
				is2 = ItemCreate.Lore(is2, lore);
				InvRep(20, is1);
				InvRep(32, is2);
			},10);
		},12);
	}
	
	
	public void getItem() {
		List<itemBase> items = ARSystem.playerItem.get(player).items;
		List<List<String>> isItem = new ArrayList<>();
		for(String t : ItemList.creates.keySet()) {
			
			List<String> nocost = new ArrayList<String>();
			List<itemBase> cost = new ArrayList<itemBase>();
			boolean istrue = true;
			int i = 0;
			
			for(String code : t.split(",")) {
				int itemcode = Integer.parseInt(code);
				boolean ok = false;
				for(itemBase it : items) {
					if(it.getCode() == itemcode && !cost.contains(it)) {
						cost.add(it);
						i++;
						ok = true;
						break;
					}
				}
				if(!ok) {
					nocost.add(code);
				}
			}
			if(i >= (int)((t.split(",").length+1)/2)) {
				String ts = "";
				for(String s : nocost) ts+= " "+ s;
				isItem.add(nocost);
			}
		}
		if(Rule.c.get(player).number == 153 && Rule.c.get(player).cooldown[0] <= 0 && AMath.random(10) <= 4) {
			Rule.c.get(player).spskillen();
			Rule.c.get(player).cooldown[0] = Rule.c.get(player).setcooldown[0];
			ARSystem.playSoundAll("c153sp");
			item1 = ItemList.getValueCode(5000, 15000);
			item1value = 2;
			item2 = ItemList.getValueCode(10000, 15000);
			item2value = 7;
		} else if(isItem.size() > 0) {
			List<String> item = isItem.get(AMath.random(isItem.size())-1);
			item1 = Integer.parseInt(item.get(AMath.random(item.size())-1));
			if(item.size() == 1) {
				item1value = 10;
			} else {
				item1value = 5;
			}
		} else {
			if(Text.get("c"+(Rule.c.get(player).number%1000)+":item_my") != null && AMath.random(10) <= 5) {
				item1 = Integer.parseInt(Text.get("c"+(Rule.c.get(player).number%1000)+":item_my"));
				item1value = 5;
			} else if(Text.get("c"+(Rule.c.get(player).number%1000)+":item") != null && AMath.random(10) <= 7) {
				String[] n = Text.get("c"+(Rule.c.get(player).number%1000)+":item").split(",");
				item1 = Integer.parseInt(n[AMath.random(n.length)-1]);
				item1value = 5;
			} else {
				item1 = ItemList.getValueCode(3000, 3000);
				item1value = 4;
			}
			if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time <= 30) {
				item1value -= 2;
			}
			if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 120) {
				item1value += 3;
			}
		}
		
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 270) {
			item2 = ItemList.getValueCode(10000, 15000);
			item2value = 5;
		} else if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 210) {
			item2 = ItemList.getValueCode(5000, 15000);
			item2value = 5;
		} else if(AMath.random(10) <= 3) {
			item2 = ItemList.getValueCode(5000, 5000);
			item2value = 8;
		} else if(AMath.random(10) <= 2) {
			item2 = 45;
			item2value = 3;
		} else {
			item2 = ItemList.getValueCode(2000, 3000);
			item2value = 2;
		}
		
		
		
		item1type = AMath.random(9);
		item2type = AMath.random(9);
		
		if(Rule.mvp == player) {
			item1value *= 1.2;
			item2value *= 1.2;
		}
		if(Rule.c.get(player).getScore() >= 500) {
			item1value *= 1.2;
			item2value *= 1.2;
		}
		if(Rule.c.get(player).getScore() <= 200 && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 100) {
			item1value *= 0.6;
			item2value *= 0.6;
		} else if(Rule.c.get(player).getScore() <= 200 && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time <= 30) {
			item1value *= 0.5;
			item2value *= 0.5;
		}
		
		if(AMath.random(10) <= 1) item1value = 1;
		else if(AMath.random(10) <= 1) item2value = 1;
		else if(AMath.random(10) <= 1) item1type = item2type = 10;
	}
	
	public int getValue(int i) {
		if(i == 1) return 5;
		if(i == 2) return 4;
		if(i == 3) return 8;
		if(i == 4) return 5;
		if(i == 5) return 2;
		if(i == 6) return 4;
		if(i == 7) return AMath.random(1,3);
		if(i == 8) return AMath.random(1,2);
		if(i == 9) return 8;
		if(i == 10) return 0;
		return AMath.random(1,3);
	}
	
	public ItemStack gui1() {
		return ItemCreate.Name(ItemCreate.Item(279,1517),"§a");
	}
	
	public ItemStack gui3() {
		return gui0();
	}
	public ItemStack gui4() {
		return gui0();
	}

	public ItemStack gui0() {
		return ItemCreate.Item(0);
	}
	public void click3(boolean right, boolean shift) {
		if(cost(1)) {
			ARSystem.addItem(player, item1);
			end();
		}
	}

	public void click4(boolean right, boolean shift) {
		if(cost(2)) {
			ARSystem.addItem(player, item2);
			end();
		}
	}
	
	boolean cost(int type) {
		int t = 0;
		int v = 0;
		if(type == 1) {
			v = item1value;
			t = item1type;
		} else {
			v = item2value;
			t = item2type;
		}
		if(t == 1) {
			Rule.c.get(player).skillmult = ((Rule.c.get(player).skillmult + Rule.c.get(player).sskillmult))*(1 - (v*0.01));
			return true;
		}
		if(t == 2) {
			if(player.getMaxHealth() * (1 -(v*0.01)) <= 1) {
				return false;
			}
			player.setMaxHealth(player.getMaxHealth() * (1 -(v*0.01)));
			Rule.c.get(player).hp = (float)player.getMaxHealth();
			return true;
		}
		if(t == 3) {
			Rule.c.get(player).frist_defence += v*0.01;
			return true;
		}
		if(t == 4) {
			Rule.c.get(player).frist_damage -= v*0.01;
			return true;
		}
		if(t == 5) {
			for(int i = 0; i <10; i++) {
				Rule.c.get(player).setcooldown[i] -= 100000;
				int o = i;
				int g = ARSystem.AniRandomSkill.getGamecode();
				Rule.c.get(player).delay(()->{
					if(Rule.c.get(player) != null && ARSystem.AniRandomSkill != null && g == ARSystem.AniRandomSkill.getGamecode()) {
						Rule.c.get(player).setcooldown[o] += 100000;
					}
				},20*v);
			}
			return true;
		}
		if(t == 6) {
			int g = ARSystem.AniRandomSkill.getGamecode();
			Rule.c.get(player).delay(()->{
				if(Rule.c.get(player) != null && ARSystem.AniRandomSkill != null && g == ARSystem.AniRandomSkill.getGamecode()) {
					ARSystem.removeItemAll(player);
				}
			},20*v);
			return true;
		}
		if(t == 7) {
			Rule.c.get(player).hpCost(v, true);
			return true;
		}
		if(t == 8) {
			ARSystem.giveBuff(player, new Ice(player,player), v*20 , 1);
			return true;
		}
		if(t == 9) {
			ARSystem.giveBuff(player, new NoHeal(player), v*20 , 1);
			return true;
		}
		if(t == 10) return true;
		return false;
	}
	
	@Override
	public void click0(boolean right, boolean shift) {
		
	}

	
	public void click99(boolean right,boolean shift) {
		end();
	}
	
	public ItemStack gui99(){
		return ItemCreate.Name(ItemCreate.Item(166), Text.get("item:no"));
	}
	
	void end(){
		player.closeInventory();
		ARSystem.giveBuff(player, new TimeStop(player), 1);
		if(!Map.inMap(player.getLocation())) {
			player.teleport(Map.randomLoc());
		}
	}	
}