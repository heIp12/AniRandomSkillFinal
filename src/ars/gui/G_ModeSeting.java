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
import mode.ModeBase;
import types.GameModes;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_ModeSeting extends GUIBase{
	PlayerInfo info;
	String mode;
	int click = 0;
	public G_ModeSeting(Player p,String mode) {
		super(p);
		name = "Modes Setting : " + mode;
		this.mode = mode;
		int count = GameModes.getModList().size();
		page = new int[27];
		info = Rule.playerinfo.get(player);
		line = 6;
		int i = 1;
		while(Text.get("mode:"+mode+"_"+i) != null) {
			page[i-1] = i;
			List<String> lore = new ArrayList<>();
			ItemStack item = ItemCreate.Name(ItemCreate.Item(340),"§a§l"+Text.get("mode:"+mode+"_"+i));
			boolean itemCreate = true;
			
			lore.add("§c§lvalue : §f" + Rule.Var.Load("System.mode."+mode+"."+i));
			if(Text.get("mode:"+mode+"_"+i+"_if") != null) {
				String ss = Text.get("mode:"+mode+"_"+i+"_if").replace(" ", "");
				for(String s : ss.split(",")) {
					try {
						Boolean bool = Boolean.parseBoolean(s.split("=")[1]);
						int code = Integer.parseInt(s.split("=")[0]);
						
						if(items[code] != null) {
							if(item.getTypeId() != 403) item.setTypeId(339);
							if(items[code].getTypeId() != 160) items[code].setTypeId(403);
						}
						if((boolean)Rule.Var.Load("System.mode."+mode+"."+code) != bool) {
							itemCreate = false;
						}
					} catch(Exception e) {
						System.out.print("Mode Seting Error : " + mode + " " + s);
					}
				}
			}
			
			String type = Text.get("mode:"+mode+"_"+i+"_type").replace(" ", "");
			if(type.contains("int")) {
				int value = Integer.parseInt(type.split(":")[1]);
				lore.add("§a "+(value)+Text.get("mode:t2")+" §c"+(value*-1));
				lore.add(Text.get("mode:t3"));
			} else if(type.contains("bool")) {
				lore.add("§aTrue "+Text.get("mode:t2")+" §cFalse");
			} else if(type.contains("chat")) {
				lore.add(Text.get("mode:t4"));
			} else if(type.contains("players")) {
				lore.add(Text.get("mode:t5"));
			}
			for(String s : Text.getLine("mode:"+mode+"_"+i+"_help",1)) {
				lore.add("§7"+s);
			}
			
			if(itemCreate) {
				item = ItemCreate.Lore(item, lore);
				ItemRep(i,item);
			} else {
				ItemRep(i,gui0());
			}
			
			i++;
		}
		if(i == 1) {
			page[13] = 1;
			items[1] = ItemCreate.Name(ItemCreate.Item(277),Text.get("mode:t"));
		}
		InvCreate(3,0);
	}
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		int click = page[clickLocal];
		if(items[click].getTypeId() != 160 && items[click].getTypeId() != 0) {
			String type = Text.get("mode:"+mode+"_"+click+"_type").replace(" ", "");
			if(type.contains("int")) {
				String[] spt = type.split(":");
				int value = Integer.parseInt(spt[1]);
				if(right) value *= -1;
				if(shift) value *= 10;
				int next = Rule.Var.Loadint("System.mode."+mode+"."+click) + value;
				
				if(type.split(":").length >= 3) {
					int min = Integer.parseInt(spt[2].split("~")[0]);
					int max = Integer.parseInt(spt[2].split("~")[1]);
					if(next < min) next = min;
					if(next > max) next = max;
				}
				
				Rule.Var.setInt("System.mode."+mode+"."+click,next);
			} else if(type.contains("bool")) {
				if(!right) {
					Rule.Var.Save("System.mode."+mode+"."+click, true);
				} else {
					Rule.Var.Save("System.mode."+mode+"."+click, false);
				}
			} else if(type.contains("chat")) {
				ARSystem.modeChat = this;
				ARSystem.modeChatPlayer = player;
				this.click = click;
				player.closeInventory();
				return false;
			} else if(type.contains("players")) {
				G_UserSelect user = new G_UserSelect(player);
				user.mode = this;
				this.click = click;
				return false;
			}
			if(mode.equalsIgnoreCase("lobotomy") && click == 1) {
				Bukkit.broadcastMessage("§c§l[<§a"+mode+"§c§l>] "+player.getName()+" >>§f"+Text.get("mode:"+mode+"_"+click) +" : " + Text.get("lobo:o"+Rule.Var.Load("System.mode."+mode+"."+click)));
			} else {
				Bukkit.broadcastMessage("§c§l[<§a"+mode+"§c§l>] "+player.getName()+" >>§f"+Text.get("mode:"+mode+"_"+click) +" : " + Rule.Var.Load("System.mode."+mode+"."+click));
			}
			new G_ModeSeting(player,mode);
			
		}
		return false;
	}
	public void playerSeting(List<Player> players) {
		Rule.Var.Save("System.mode."+mode+"."+click, players);
		Bukkit.broadcastMessage("§c§l[<§a"+mode+"§c§l>] §f"+Text.get("mode:"+mode+"_"+click) +" : " + Rule.Var.Load("System.mode."+mode+"."+click));
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{new G_ModeSeting(player,mode);},0);
	}
	
	public void chatOption(String s) {
		Rule.Var.Save("System.mode."+mode+"."+click, s);
		Bukkit.broadcastMessage("§c§l[<§a"+mode+"§c§l>] §f"+Text.get("mode:"+mode+"_"+click) +" : " + Rule.Var.Load("System.mode."+mode+"."+click));

		Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{new G_ModeSeting(player,mode);},0);
	}
}