package ars.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import util.Var;

public class G_Shop extends GUIBase{
	PlayerInfo info;
	HashMap<Integer,Integer> Money = new HashMap<>();
	int click = 0;
	public G_Shop(Player p) {
		super(p);
		name = "Shop";
		line = 6;
		scroll = 2;
		page = new int[]
				{
				   0,101,  0,  0,  0,  0,  0,  0,  0,
				   0, 99, 99, 99, 99, 99, 99,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				  99, 99, 99, 99, 99, 99, 99, 99,  0,
				   0,102,  0,  0,  0,  0,  0,  0,  0, //게임관련
				   0,  7,  8, 10, 11, 99, 99,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				  99, 99, 99, 99, 99, 99, 99, 99,  0,
				   0,103,  0,  0,  0,  0,  0,  0,  0, //칭호
				   0, 21, 22, 23, 24, 25, 26,  0,  0,
				   0, 27, 28, 99, 99, 99, 99,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				  99, 99, 99, 99, 99, 99, 99, 99,  0,
				   0,104,  0,  0,  0,  0,  0,  0,  0, //칭호테두리
				   0, 41, 42, 43, 44, 45, 46,  0,  0,
				   0, 47, 48, 49, 50, 99, 99,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				  99, 99, 99, 99, 99, 99, 99, 99,  0,
				   0,105,  0,  0,  0,  0,  0,  0,  0, //사망이펙트
				   0, 61, 62, 63, 64, 65, 66,  0,  0,
				   0, 67, 68, 99, 99, 99, 99,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				  99, 99, 99, 99, 99, 99, 99, 99,  0,
				   0,106,  0,  0,  0,  0,  0,  0,  0, //이모티콘
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				   0,  0,  0,  0,  0,  0,  0,  0,  0,
				  99, 99, 99, 99, 99, 99, 99, 99,  0
				};

		if(AMath.random(10) <= 1) {
			List<Integer> list = new ArrayList<Integer>();
			for(int i = 0; i< page.length;i++) if(page[i] == 99) list.add(i);
			page[list.get(AMath.random(list.size())-1)] = 98;
		}
		Money.putAll(ca(new int[][] {{7,100},{11,200} }));
		Money.putAll(ca(new int[][] {{21,5000},{22,1000},{23,2000},{24,1000},{25,500},{26,3000},{27,5000},{28,50000}}));
		Money.putAll(ca(new int[][] {{41,100},{42,200},{43,500},{44,500},{45,500},{46,1000},{47,1000},{48,1000},{49,2000},{50,10000}}));
		Money.putAll(ca(new int[][] {{61,500},{62,500},{63,500},{64,500},{65,500},{66,2000},{67,3000},{68,1000}}));
		
		int j = 24*9+1;
		for(int i =0; i<42; i++){
			if(j%9 == 7) j+=3;
			Money.put(201+i,1000);
			ItemStack item = ItemCreate.Item(293,1401+i);
			page[j] = 201+i;
			items[201+i] = ItemCreate.Name(item, "§f"+Text.get("imj:"+(i+1)));
			j++;
		}
		
		{
			List<Integer> list = new ArrayList<Integer>();
			for(int i : Money.keySet()) list.add(i);
			
			List<Integer> items = new ArrayList<>();
			for(int i = 0; items.size() < AMath.DayRandom(17, 5)+1;i++) {
				int num = AMath.DayRandom(i+AMath.DayRandom(19, 100), list.size()-1);
				if(!items.contains(num)) {
					items.add(num);
					page[9+items.size()] = list.get(num);
					Money.put(list.get(num), (int)(Money.get(list.get(num)) * 0.7));
				}
			}
		}
		for(int i =0; i<42; i++) {
			guiCreateEnd(201+i);
		}
		for(int i = 1; i<7;i++) {
			ItemRep(i, gui99());
		}
		info = Rule.playerinfo.get(player);
		ScrollInvCreate(line,scroll);
	}

	
	@Override
	protected void guiCreateEnd(int i) {
		if(Money.get(i) != null) {
			List<String> lores = items[i].getItemMeta().getLore();
			if(lores == null) lores = new ArrayList<>();
			lores.add("§d<Cost> §f"+Money.get(i)+" Point");
			items[i] = ItemCreate.Lore(items[i], lores);
		}
	}
	
	@Override
	public boolean ClickFrist(int clickLocal, boolean right, boolean shift) {
		click = page[clickLocal];
		ARSystem.playSound(player,"0click");
		if(click > 200 && click < 300) {
			if(!(boolean) Rule.Var.Load(player.getName()+":imj."+(click-200))) {
				if(Buy(Money.get(click))) {
					Rule.Var.open(player.getName()+":imj."+(click-200), true);
				}
			} else {

				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
			}
			return true;
		}
		
		return super.ClickFrist(clickLocal, right, shift);
	}
	
	private HashMap<Integer,Integer> ca(int[][] ints) {
		HashMap<Integer,Integer> list = new HashMap<>();
		for(int i[] : ints) list.put(i[0],i[1]);
		return list;
	}
	public String t(String txt) { return Text.get("main:"+txt);}
	public String j() { return "§e=====================================";}
	public int getCradit() {
		return Rule.playerinfo.get(player).getCradit();
	}
	public boolean Buy(int i) {
		if(getCradit() >= i) {
			Rule.playerinfo.get(player).addcradit(-i,Main.GetText("main:msg107"));
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			Rule.Var.setInt("info.Shop.money", Rule.Var.Loadint("info.Shop.money")+(int)(i*0.1));	
			player.closeInventory();
			ARSystem.playSound(player,"0event");
			return true;
		}
		player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
		return false;
	}
	public ItemStack gui0(){
		ItemStack item = ItemCreate.Item(101);
		return ItemCreate.Name(item," ");
	}
	public ItemStack gui99(){
		ItemStack item = ItemCreate.Item(160,8);
		return ItemCreate.Name(item," ");
	}
	public ItemStack gui98(){
		ItemStack item = ItemCreate.Item(160,8);
		return ItemCreate.Name(item,"§fHentai!");
	}
	public void click98(boolean right,boolean shift) {
		Rule.playerinfo.get(player).addcradit(50,"DoKoMiTenDaYo!");
		info.tropy(0, 37);
		ARSystem.playSound(player, "0doko");
		player.closeInventory();
	}
	public void click0(boolean right,boolean shift) {}
	public ItemStack gui101(){
		ItemStack item = ItemCreate.Item(347,0);
		return ItemCreate.Lore(item,"§f"+t("shop_i1"),new String[] {j(),"§a"+t("shop_i2"),"§7"+t("shop4")});
	}
	public ItemStack gui102(){
		ItemStack item = ItemCreate.Item(386,0);
		return ItemCreate.Lore(item,"§f"+t("shop6"),new String[] {j(),"§7"+t("shop4")});
	}
	public ItemStack gui103(){
		ItemStack item = ItemCreate.Item(386,0);
		return ItemCreate.Lore(item,"§f"+t("shop2"),new String[] {j(),"§7"+t("shop4"),"§7"+t("shop5")});
	}
	public ItemStack gui104(){
		ItemStack item = ItemCreate.Item(386,0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:msg2"),new String[] {j(),"§7"+t("shop4"),"§7"+t("shop5")});
	}
	public ItemStack gui105(){
		ItemStack item = ItemCreate.Item(386,0);
		return ItemCreate.Lore(item,"§f"+t("shop3"),new String[] {j(),"§7"+t("shop4"),"§7"+t("shop5")});
	}
	public ItemStack gui106(){
		ItemStack item = ItemCreate.Item(321,0);
		return ItemCreate.Lore(item,"§f"+t("shop19"),new String[] {j(),"§7"+t("shop4")});
	}
	public ItemStack gui107(){
		ItemStack item = ItemCreate.Item(347,0);
		return ItemCreate.Name(item,"§f"+t("shop_i1"));
	}
	public ItemStack gui7(){
		ItemStack item = ItemCreate.Item(339, 0);
		List<String> list = new ArrayList<String>();
		list.add(j());
		list.add("§7"+t("shop8"));
		list.add("§7"+t("shop9"));
		if(Money.get(click) != null) list.add("§a§l " +Money.get(click)+" Point");
		return ItemCreate.Lore(item,"§f"+t("shop7"),list);
	}
	
	public void click7(boolean right,boolean shift) {
		if(ARSystem.AniRandomSkill != null && !ARSystem.AniRandomSkill.chageplayer.contains(player)) {
			if(ARSystem.AniRandomSkill.time < 0 && Rule.c.get(player) != null) {
				if(Buy(Money.get(click))) {
					ARSystem.AniRandomSkill.chageplayer.add(player);
					ARSystem.chaRrep(player);
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror8"));
			}
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror5"));
		}
	}
	
	public ItemStack gui8(){
		ItemStack item = ItemCreate.Item(339, 0);
		List<String> list = new ArrayList<String>();
		list.add(j());
		list.add("§7"+t("shop15"));
		list.add("§7"+t("shop16"));
		list.add("§7"+t("shop14"));
		list.add("§c§l"+ Rule.playerinfo.get(player).abchar);
		
		return ItemCreate.Lore(item,"§f"+t("shop13"),list);
	}
	public void click8(boolean right,boolean shift) {
		Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String ss = "" + simpleDateFormat.format(nowDate);
        long a = Integer.parseInt(ss);
        int b = (int)a;
        int i = Rule.Var.Loadint(player.getName()+".info.CodeDay");
        if(b > i) {
        	player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror11"));
        } else {
        	Rule.playerinfo.get(player).abchar = !Rule.playerinfo.get(player).abchar;
        	ItemRep(click, gui8());
        	ScrollInvCreate(line, scroll);
        }
	}

	public ItemStack gui11(){
		ItemStack item = ItemCreate.Item(322);
		try {
			String n = (String)Rule.Var.Load("info.Shop.money_recode_Name");
			int cradit = Rule.Var.Loadint("info.Shop.money_recode");
			return ItemCreate.Lore(item,"§e§l"+Main.GetText("main:shop17"), new String[] {
					"§e"+Main.GetText("main:shop18") ,
					"§a1 : " + Rule.Var.Loadint("info.Shop.money"),
					"§f2 : " + AMath.round(Rule.Var.Loadint("info.Shop.money")*0.05 +100,0),
					"§f§l<"+Main.GetText("main:shop20") + ">"
					,"§e" + n + " [§a" +cradit+"§e]"});
		}catch (Exception e) {
			return ItemCreate.Lore(item,"§e§l"+Main.GetText("main:shop17"), new String[] {"§e"+Main.GetText("main:shop18") ,"§a1 : " + Rule.Var.Loadint("info.Shop.money"),"§f2 : " + AMath.round(Rule.Var.Loadint("info.Shop.money")*0.05 +100,0)});	
		}
	}
	public void click11(boolean right,boolean shift) {
		int cradit = 0;
		if(Buy(Money.get(click))) {
			Rule.Var.setInt("info.Shop.money", Rule.Var.Loadint("info.Shop.money")+(int)(Money.get(11)*0.6));
			if(AMath.random(100) <= 20) {
				cradit = 0;
				player.sendMessage("§a§l[ARSystem] : §4 0 Point");
			}
			else if(AMath.random(100) <= 70) {
				cradit = 50;
				player.sendMessage("§a§l[ARSystem] : §4 +"+cradit+" Point");
			}
			else if(AMath.random(100) <= 60) {
				cradit = 100;
				player.sendMessage("§a§l[ARSystem] : §c +"+cradit+" Point");
			}
			else if(AMath.random(100) <= 10) {
				cradit = 100*AMath.random(8)+200;
				ARSystem.playSound(player,"0event3");
				if( cradit > Rule.Var.Loadint("info.Shop.money_recode")) {
					Rule.Var.setInt("info.Shop.money_recode", cradit);
					Rule.Var.Save("info.Shop.money_recode_Name", player.getName());
				}
				Bukkit.broadcastMessage("§a§l[ARSystem] : "+player.getName()+" §f§l +"+cradit+" Point");
			}
			else if(AMath.random(100) <= 2) {
				cradit = (int) (Rule.Var.Loadint("info.Shop.money") * 0.05f)+100;
				ARSystem.playSound(player,"0event3");
				if( cradit > Rule.Var.Loadint("info.Shop.money_recode")) {
					Rule.Var.setInt("info.Shop.money_recode", cradit);
					Rule.Var.Save("info.Shop.money_recode_Name", player.getName());
				}
				Bukkit.broadcastMessage("§a§l[ARSystem] : "+player.getName()+" §e§l +"+cradit+" Point");
			}
			else if(AMath.random(120) <= 1) {
				cradit = Rule.Var.Loadint("info.Shop.money");
				ARSystem.playSoundAll("0event3");
				if( cradit > Rule.Var.Loadint("info.Shop.money_recode")) {
					Rule.Var.setInt("info.Shop.money_recode", cradit);
					Rule.Var.Save("info.Shop.money_recode_Name", player.getName());
				}
				Bukkit.broadcastMessage("§a§l[ARSystem] : "+player.getName()+" §a§l +"+cradit+" Point");
			}
			else if(AMath.random(100) <= 90) {
				cradit = 200;
				player.sendMessage("§a§l[ARSystem] : §f +"+cradit+" Point");
			}
			else if(AMath.random(1000) <= 5) {
				cradit = -20000;
				Rule.playerinfo.get(player).tropy(0, 39);
				Bukkit.broadcastMessage("§a§l[ARSystem] : "+player.getName()+" §4§l "+cradit+" Point");
			}
			else {
				cradit = -100;
				Rule.playerinfo.get(player).tropy(0, 38);
				player.sendMessage("§a§l[ARSystem] : "+player.getName()+" §c§l "+cradit+" Point");
			}
			Rule.Var.setInt("info.Shop.money", Rule.Var.Loadint("info.Shop.money")-cradit);	

			if(Rule.Var.Loadint("info.Shop.money") <= 5000) {
				Rule.Var.setInt("info.Shop.money", 5000);
			}
			Rule.playerinfo.get(player).addcradit(cradit, Main.GetText("main:shop17"));
		}
	}
	public ItemStack gui10(){
		ItemStack item = ItemCreate.Item(266, 0);
		List<String> list = new ArrayList<String>();
		list.add(j());
		return ItemCreate.Lore(item,"§f"+t("info18"),list);
	}
	public void click10(boolean right,boolean shift) {
		info.log();
		player.closeInventory();
	}
	//칭호
	public ItemStack gui21(){
		ItemStack item = ItemCreate.Item(339);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:c0-6"),new String[] {j()});
	}
	public void click21(boolean right,boolean shift) {
		if(!info.trophy[0][6]) {
			if(Buy(Money.get(click))) {
				info.tropy(0, 6);
			}
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
		}
	}
	public ItemStack gui22(){
		ItemStack item = ItemCreate.Item(339, 0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:c0-7"),new String[] {j()});
	}
	public void click22(boolean right,boolean shift) {
		if(!info.trophy[0][7]) {
			if(Buy(Money.get(click))) {
				info.tropy(0, 7);
			}
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
		}
	}
	public ItemStack gui23(){
		ItemStack item = ItemCreate.Item(339, 0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:c0-8"),new String[] {j()});
	}
	public void click23(boolean right,boolean shift) {
		if(!info.trophy[0][8]) {
			if(Buy(Money.get(click))) {
				info.tropy(0, 8);
			}
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
		}
	}
	public ItemStack gui24(){
		ItemStack item = ItemCreate.Item(339, 0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:c0-9"),new String[] {j()});
	}
	public void click24(boolean right,boolean shift) {
		if(!info.trophy[0][9]) {
			if(Buy(Money.get(click))) {
				info.tropy(0, 9);
			}
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
		}
	}
	public ItemStack gui25(){
		ItemStack item = ItemCreate.Item(339, 0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:c0-10"),new String[] {j()});
	}
	public void click25(boolean right,boolean shift) {
		if(!info.trophy[0][10]) {
			if(Buy(Money.get(click))) {
				info.tropy(0, 10);
			}
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
		}
	}
	public ItemStack gui26(){
		ItemStack item = ItemCreate.Item(339, 0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:c0-11"),new String[] {j()});
	}
	public void click26(boolean right,boolean shift) {
		if(!info.trophy[0][11]) {
			if(Buy(Money.get(click))) {
				info.tropy(0, 11);
			}
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
		}
	}
	public ItemStack gui27(){
		ItemStack item = ItemCreate.Item(339, 0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:c0-12"),new String[] {j()});
	}
	public void click27(boolean right,boolean shift) {
		if(!info.trophy[0][12]) {
			if(Buy(Money.get(click))) {
				info.tropy(0, 12);
			}
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
		}
	}
	public ItemStack gui28(){
		ItemStack item = ItemCreate.Item(339, 0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:c0-13"),new String[] {j()});
	}
	public void click28(boolean right,boolean shift) {
		if(!info.trophy[0][13]) {
			if(Buy(Money.get(click))) {
				info.tropy(0, 13);
			}
		} else {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
		}
	}
	// 테두리
	public ItemStack gui41(){
		ItemStack item = ItemCreate.Item(339, 0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t0"),new String[] {j()});
	}
	public void click41(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 0;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	public ItemStack gui42(){
		ItemStack item = ItemCreate.Item(339, 0);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t1"),new String[] {j()});
	}
	public void click42(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 1;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	public ItemStack gui43(){
		ItemStack item = ItemCreate.Item(339);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t2"),new String[] {j()});
	}
	public void click43(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 2;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	public ItemStack gui44(){
		ItemStack item = ItemCreate.Item(339);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t3"),new String[] {j()});
	}
	public void click44(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 3;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	public ItemStack gui45(){
		ItemStack item = ItemCreate.Item(339);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t4"),new String[] {j()});
	}
	public void click45(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 4;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	public ItemStack gui46(){
		ItemStack item = ItemCreate.Item(339);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t5"),new String[] {j()});
	}
	public void click46(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 5;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	public ItemStack gui47(){
		ItemStack item = ItemCreate.Item(339);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t6"),new String[] {j()});
	}
	public void click47(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 6;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	public ItemStack gui48(){
		ItemStack item = ItemCreate.Item(339);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t7"),new String[] {j()});
	}
	public void click48(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 7;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	public ItemStack gui49(){
		ItemStack item = ItemCreate.Item(339);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t8"),new String[] {j()});
	}
	public void click49(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 8;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	public ItemStack gui50(){
		ItemStack item = ItemCreate.Item(339);
		return ItemCreate.Lore(item,"§f"+Text.get("tropy:t9"),new String[] {j()});
	}
	public void click50(boolean right,boolean shift) {
		if(Buy(Money.get(click))) {
			info.playertdr = 9;
			Rule.Var.setInt(player.getName()+".info.td", info.playertdr);
			info.name = ""+Main.GetText("tropy:t"+info.playertdr).replace(" ", Main.GetText("tropy:c"+info.playerTrophy));

		}
	}
	
	// 킬이펙트
	public ItemStack gui61(){
		ItemStack item = ItemCreate.Item(377);
		return ItemCreate.Lore(item,"§f"+t("ke1"),new String[] {j()});
	}
	public void click61(boolean right,boolean shift) {
		if(info.kille==1) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
			return;
		}
		else if(Buy(Money.get(click))) {
			info.kille = 1;
			Rule.Var.setInt(player.getName()+".info.ke",info.kille);
		}
	}
	public ItemStack gui62(){
		ItemStack item = ItemCreate.Item(377);
		return ItemCreate.Lore(item,"§f"+t("ke2"),new String[] {j()});
	}
	public void click62(boolean right,boolean shift) {
		if(info.kille==2) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
			return;
		}
		else if(Buy(Money.get(click))) {
			info.kille = 2;
			Rule.Var.setInt(player.getName()+".info.ke",info.kille);
		}
	}
	public ItemStack gui63(){
		ItemStack item = ItemCreate.Item(377);
		return ItemCreate.Lore(item,"§f"+t("ke3"),new String[] {j()});
	}
	public void click63(boolean right,boolean shift) {
		if(info.kille==3) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
			return;
		}
		else if(Buy(Money.get(click))) {
			info.kille = 3;
			Rule.Var.setInt(player.getName()+".info.ke",info.kille);
		}
	}
	public ItemStack gui64(){
		ItemStack item = ItemCreate.Item(377);
		return ItemCreate.Lore(item,"§f"+t("ke4"),new String[] {j()});
	}
	public void click64(boolean right,boolean shift) {
		if(info.kille==4) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
			return;
		}
		else if(Buy(Money.get(click))) {
			info.kille = 4;
			Rule.Var.setInt(player.getName()+".info.ke",info.kille);
		}
	}
	public ItemStack gui65(){
		ItemStack item = ItemCreate.Item(377);
		return ItemCreate.Lore(item,"§f"+t("ke5"),new String[] {j()});
	}
	public void click65(boolean right,boolean shift) {
		if(info.kille==5) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
			return;
		}
		else if(Buy(Money.get(click))) {
			info.kille = 5;
			Rule.Var.setInt(player.getName()+".info.ke",info.kille);
		}
	}
	public ItemStack gui66(){
		ItemStack item = ItemCreate.Item(377);
		return ItemCreate.Lore(item,"§f"+t("ke6"),new String[] {j()});
	}
	public void click66(boolean right,boolean shift) {
		if(info.kille==6) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
			return;
		}
		else if(Buy(Money.get(click))) {
			info.kille = 6;
			Rule.Var.setInt(player.getName()+".info.ke",info.kille);
		}
	}
	public ItemStack gui67(){
		ItemStack item = ItemCreate.Item(377);
		return ItemCreate.Lore(item,"§f"+t("ke7"),new String[] {j()});
	}
	public void click67(boolean right,boolean shift) {
		if(info.kille==7) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
			return;
		}
		else if(Buy(Money.get(click))) {
			info.kille = 7;
			Rule.Var.setInt(player.getName()+".info.ke",info.kille);
		}
	}
	public ItemStack gui68(){
		ItemStack item = ItemCreate.Item(377);
		return ItemCreate.Lore(item,"§f"+t("ke8"),new String[] {j()});
	}
	public void click68(boolean right,boolean shift) {
		if(info.kille==8) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
			return;
		}
		else if(Buy(Money.get(click))) {
			info.kille = 8;
			Rule.Var.setInt(player.getName()+".info.ke",info.kille);
		}
	}
}