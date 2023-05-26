package ars;


import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Skull;

import Main.Main;
import ars.gui.G_Menu;
import chars.c2.c58nao;
import manager.AdvManager;
import util.BlockUtil;
import util.GetChar;
import util.MSUtil;
import util.Map;
import util.Text;

public class PlayerInfo {
	Player player;
	private int cradit;
	private int spopencradit;
	private int addcradit;
	int page = 0;
	int invtype = 0;
	int charban = 1;
	public String name;
	public boolean gamejoin = true;
	public boolean abchar = false;
	public String team;
	ItemStack head;
	public Player target;
	
	public long LastImgTime = 0;

	public int MainImg = 0;
	
	List<String> craditlog = new ArrayList<String>();
	
	public boolean spopen[] = new boolean[GetChar.getCount()+1];
	public boolean trophy[][] = new boolean[500][100];
	public String playerTrophy = "";
	public int playertdr = 0;
	public int playerc;
	public int kille;
	public int playchar = 0;
	Inventory spinv;
	
	PlayerInfo(Player p){
		player = p;
		target = p;
		load();
		player.getInventory().clear();
	}
	public void log() {
		player.sendMessage("§a§l==============================");
		for(String s: craditlog) {
			player.sendMessage(s);
		}
	}
	public int getcradit() { return getCradit(); }
	
	public boolean isTropy(int i, int j) {
		return trophy[i][j];
	}
	
	public void addcradit(int i,String s) {
		setCradit(getCradit() + i);
		LocalTime now = LocalTime.now();
		Rule.savePoint(player.getName(), getCradit(), 0);
		s= "["+now.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"] §f"+s;
		if(i > 0) {
			craditlog.add("§e§l[ARSLOG]§7"+s +" §a+"+i+" ["+getCradit()+"]");
		} else {
			craditlog.add("§e§l[ARSLOG]§7"+s +" §c"+i+" ["+getCradit()+"]");
		}
		
		if(craditlog.size() > 50) {
			craditlog.remove(0);
		}

		if(max(player.getName()+".c","Win") > 0) {
			tropy(0,1);
		}
		if(max(player.getName()+".c","Win") > 4) {
			tropy(0,2);
		}
		if(max(player.getName()+".c","Win") > 100) {
			tropy(0,3);
		}
		if(max(player.getName()+".c","Kill") > 100) {
			tropy(0,4);
		}
		
		Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String ss = "" + simpleDateFormat.format(nowDate);
        long a = Integer.parseInt(ss);
        int b = (int)a;
        int time = Rule.Var.Loadint(player.getName()+".info.CodeDay");
        if(b > time) {
        	abchar = false;
        }
	}
	
	public void open(Player p) {
		new G_Menu(player);
	}
	
	public float getScore(){
		float win = max(player.getName()+".c","Win");
		float play = max(player.getName()+".c","Play");
		float time = max(player.getName()+".c","Time");
		float rating = Math.round((win/play)*time)/10.f;
		if(rating > 100) {
			rating = 100 + (Math.round((win/play)*(time/5.0))/10.f);
		}
		if(rating > 300) {
			rating = 300 + (Math.round((win/play)*(time/10.0))/10.f);
		}
		if(rating > 500) {
			rating = 500 + (Math.round((win/play)*(time/40.0))/10.f);
		}
		if(rating > 1000) {
			rating = 1000 + (Math.round((win/play)*(time/200.0))/10.f);
		}
		
		if(rating < 10) rating = 10;
		return rating;
	}
	
	public void load(){
		for(int i = 1;i < GetChar.getCount()+1; i++) {
			if(Rule.Var.Load(player.getName()+".Sp.c"+i) != null) {
				spopen[i] = (boolean)Rule.Var.Load(player.getName()+".Sp.c"+i);
			} else {
				spopen[i] = false;
			}
		}
		head = (ItemStack) Rule.Var.getItemStack(player.getName()+".head");
		if(head == null) {
			head = new ItemStack(397, 1, (short) 3);
			SkullMeta meta =  (SkullMeta) head.getItemMeta();
			meta.setDisplayName(""+player.getName());
			meta.setOwner(player.getName());
			head.setItemMeta(meta);
			Rule.Var.Save(player.getName()+".head", head);
		}
		setCradit(Rule.Var.Loadint(player.getName()+".info.Cradit"));
		setAddcradit(Rule.Var.Loadint(player.getName()+".info.CraditOpen"));
		setSpopencradit(500 + getAddcradit()*100);
		
		for(int i = 0;i < 500; i++) {
			for(int j = 1; j < 100; j++) {
				if(Main.GetText("tropy:c"+i+"-"+j) != null) {
					trophy[i][j] = (boolean)Rule.Var.Load(player.getName()+".tropy."+i+"-"+j);
				} else {
					trophy[i][j] = false;
				}
			}
		}
		gamejoin = (boolean)Rule.Var.Load(player.getName()+".gamejoin");
		try {
			team = (String) Rule.Var.Load(player.getName()+".team");
		} catch(Exception e) {
			team = null;
		}
		try {
			playerTrophy = (String) Rule.Var.Load(player.getName()+".info.tp");
		} catch(Exception e) {
			playerTrophy = "0-1";
		}
		
		
		playertdr = Rule.Var.Loadint(player.getName()+".info.td");
		kille = Rule.Var.Loadint(player.getName()+".info.ke");
		if(kille == 0) {
			kille = 1;
			Rule.Var.setInt(player.getName()+".info.ke",1);
		}
		
		if(!trophy[0][1]) {
			playerTrophy = "0-1";
			Rule.Var.Save(player.getName()+".info.tp","0-1");
			gamejoin = true;
			Rule.Var.Save(player.getName()+".info.gamejoin",gamejoin);
			setCradit(Text.getI("general:money"));
			Rule.Var.setInt(player.getName()+".info.Cradit", getCradit());
			trophy[0][1] = true;
			Rule.Var.Save(player.getName()+".tropy.0-1",true);
		}
		playerc = Rule.Var.Loadint(player.getName()+".info.c");
		
		if(Main.GetText("tropy:t"+playertdr) != null && Main.GetText("tropy:c"+playerTrophy) != null) {
			name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
		} else {
			playerTrophy = "0-1";
			Rule.Var.Save(player.getName()+".info.tp","0-1");
			gamejoin = true;
			Rule.Var.Save(player.getName()+".info.gamejoin",gamejoin);
			setCradit(Text.getI("general:money"));
			Rule.Var.setInt(player.getName()+".info.Cradit", getCradit());
			trophy[0][1] = true;
			Rule.Var.Save(player.getName()+".tropy.0-1",true);
		}
		if(!gamejoin) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:msg23"));
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:msg24"));
		}
	}
	
	public void save(){
		Rule.Var.setInt(player.getName()+".info.Cradit", getCradit());
		Rule.Var.setInt(player.getName()+".info.CraditOpen", getAddcradit());
		Rule.Var.Save(player.getName()+".team", team);
		Rule.Var.Save(player.getName()+".info.gamejoin",gamejoin);
	}
	
	public void tropy(int i,int j) {
		if(!trophy[i][j]) {
			trophy[i][j] = true;
			Rule.Var.Save(player.getName()+".tropy."+i+"-"+j,true);
			AdvManager.set(player, 426, 0 , "§f§l"+Main.GetText("tropy:msg4")+" "+Main.GetText("tropy:c"+i+"-"+j));
		}
	}
	public int max(String name, String s) {
		int number = 0;
		for(int i = 0; i < GetChar.getCount()+1; i++) {
			number += Rule.Var.Loadint(name+(i%1000)+s);
		}
		return number;
	}
	
	public double n(float a,float b) {
		try{
			return a/b;
		} catch (ArithmeticException e) {
			return 0;
		}
	}
	
	public ItemStack getHead() {
		return head;
	}
	public int getSpopencradit() {
		return spopencradit;
	}
	public void setSpopencradit(int spopencradit) {
		this.spopencradit = spopencradit;
	}
	public int getCradit() {
		return cradit;
	}
	public void setCradit(int cradit) {
		this.cradit = cradit;
	}
	public int getAddcradit() {
		return addcradit;
	}
	public void setAddcradit(int addcradit) {
		this.addcradit = addcradit;
	}
}
