package ars.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Main.Main;
import ars.ARSystem;
import ars.PlayerInfo;
import ars.Rule;
import util.AMath;
import util.GUIBase;
import util.GetChar;
import util.ItemCreate;
import util.MSUtil;
import util.Text;

public class G_CharInfo2 extends GUIBase{
	PlayerInfo info;
	int j = 0;
	public G_CharInfo2(Player p,int j) {
		super(p);
		name = "Char Info";
		line = 1;
		page = new int[]
				{
					1, 2, 3, 4, 5, 6, 7, 8, 9
				};
		this.j = j;
		info = Rule.playerinfo.get(p);
		InvCreate(1, 0);
	}
	
	static public String getType(String text) {
		String txt = "";
		String option[] = text.split(" ");
		for(int i=1;i<option.length;i++) {
			txt += Main.GetText("main:"+option[i])+", ";
		}
		return txt.substring(0,txt.length()-2);

	}
	
	public ItemStack gui1() {
		ItemStack is = GetChar.getColor(j);
		ItemMeta meta = is.getItemMeta();
		if(Main.GetText("c"+j+":name2") != null) {
			meta.setDisplayName("§f§l["+j+"]"+Main.GetText("c"+j+":name1").replace("-", "") +" "+ Main.GetText("c"+j+":name2"));
		} else {
			meta.setDisplayName("§f§l["+j+"]");
		}
		List<String> lore = new ArrayList<String>();
		int n = 1;
		
		lore.add("§e§lAni : §6"+Main.GetText("ani:"+Main.GetText("c"+j+":ani")));
		lore.add("§4§lHp : §c" +Main.GetText("c"+j+":hp"));
		lore.add("§7Tag:§8§l "+ getType(Main.GetText("c"+j+":type")));
		
		lore.add("§a§l"+Main.GetText("main:info51"));
		while(Main.GetText("c"+j+":info"+n) != null) {
			String text = Main.GetText("c"+j+":info"+n);
			if(text.contains("✧") || text.contains("✦")) {
				lore.add("§e§l" + text);
			} else {
				lore.add("§7" + text);
			}
			n++;
		}
		if(n == 1) lore.add("§7"+Main.GetText("main:cmderror3"));
		
		n = 1;
		lore.add("§b§l"+Main.GetText("main:info52"));
		while(Main.GetText("c"+j+":help"+n) != null) {
			String text = Main.GetText("c"+j+":help"+n);
			lore.add("§f" + text);
			n++;
		}
		if(n == 1) lore.add("§7"+Main.GetText("main:cmderror3"));

		n = 1;
		lore.add("§c§l"+Main.GetText("main:info53"));
		while(Main.GetText("c"+j+":kill"+n) != null) {
			String text = Main.GetText("c"+j+":kill"+n);
			lore.add("§6" + text);
			n++;
		}
		if(n == 1) lore.add("§7"+Main.GetText("main:cmderror3"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack gui2() {
		ItemStack is = ItemCreate.Name(ItemCreate.Item(340), "§d§l[Passive] §f" + Text.get("c"+j+":ps"));
		
		return ItemCreate.Lore(is, getLine("c"+j+":ps_lore"));
	}
	public ItemStack gui3() {
		ItemStack is = null;
		if(info.spopen[j]) {
			is = ItemCreate.Item(426);
		} else {
			is = ItemCreate.Item(102);
		}
		ItemMeta meta = is.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setUnbreakable(true);
		if(info.spopen[j] || Main.GetText("general:spopen").equalsIgnoreCase("true")) {
			meta.setDisplayName("§c§l[Spacial Skill]"+"§a§l " +Main.GetText("c"+j+":sk0"));
		} else {
			meta.setDisplayName("§c§l[Spacial Skill] " +Main.GetText("c"+j+":sk0"));
		}
		List<String> lore = new ArrayList<String>();
		lore.add("§e=====================================");
		if(info.spopen[j] || Main.GetText("general:spopen").equalsIgnoreCase("true")) {
			for(String str : getLine("c"+j+":sk0")) {
				lore.add(str);
			}
		} else {
			for(String str : getLine("c"+j+":sk0")) {
				String s = "";
				for(char c : str.toCharArray()) {
					s+="a";
				}
				lore.add("§f§k"+s);
			}
			lore.add("§7"+Main.GetText("main:msg11"));
			lore.add("§7"+Main.GetText("main:msg12"));
			lore.add("§c"+info.getSpopencradit()+Main.GetText("main:msg13"));
		}
		meta.setLore(lore);
		
		is.setItemMeta(meta);
		return is;
	}
	public ItemStack gui4() {
		if(Text.get("c"+j+":sk1") == null) return gui0();
		ItemStack is = ItemCreate.Name(ItemCreate.Item(339), "§6§l[Skill1] §f" + Text.get("c"+j+":sk1"));
		return ItemCreate.Lore(is, getLine("c"+j+":sk1"));
	}
	public ItemStack gui5() {
		if(Text.get("c"+j+":sk2") == null) return gui0();
		ItemStack is = ItemCreate.Name(ItemCreate.Item(339), "§6§l[Skill2] §f" + Text.get("c"+j+":sk2"));
		return ItemCreate.Lore(is, getLine("c"+j+":sk2"));
	}
	public ItemStack gui6() {
		if(Text.get("c"+j+":sk3") == null) return gui0();
		ItemStack is = ItemCreate.Name(ItemCreate.Item(339), "§6§l[Skill3] §f" + Text.get("c"+j+":sk3"));
		return ItemCreate.Lore(is, getLine("c"+j+":sk3"));
	}
	public ItemStack gui7() {
		if(Text.get("c"+j+":sk4") == null) return gui0();
		ItemStack is = ItemCreate.Name(ItemCreate.Item(339), "§6§l[Skill4] §f" + Text.get("c"+j+":sk4"));
		return ItemCreate.Lore(is, getLine("c"+j+":sk4"));
	}
	public ItemStack gui8() {
		if(Text.get("c"+j+":sk5") == null) return gui0();
		ItemStack is = ItemCreate.Name(ItemCreate.Item(339), "§6§l[Skill5] §f" + Text.get("c"+j+":sk5"));
		return ItemCreate.Lore(is, getLine("c"+j+":sk5"));
	}
	
	public List<String> getLine(String str){
		List<String> line = new ArrayList<String>();
		if(Text.get(str+"_cooldown") != null) {
			line.add("§a§lCooldown : §c" + AMath.round(Float.parseFloat(Text.get(str+"_cooldown"))*0.1,2));
		}
		for(String s : Text.getLine(str+"_lore",1)) line.add("§7"+s);
		return line;
	}
	
	public ItemStack gui9() {
		return ItemCreate.Name(ItemCreate.Item(403),"Back");
	}
	
	public void click3(boolean right, boolean shift) {
		if(info.getSpopencradit() <= info.getCradit() && !(boolean)Rule.Var.Load(player.getName()+".Sp.c"+j)) {
			info.addcradit(-info.getSpopencradit(),Main.GetText("main:msg101")+" "+Main.GetText("c"+j+":sk0"));
			info.setAddcradit(info.getAddcradit() + 1);
			Rule.Var.open(player.getName()+".Sp.c"+j,true);
			info.save();
			info.load();
			new G_CharInfo2(player,j);
			ARSystem.playSound(player,"0event");
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
		}
	}
	
	public void click9(boolean right, boolean shift) {
		new G_CharInfo(player);
	}
}