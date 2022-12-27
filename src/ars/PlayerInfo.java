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
import c2.c58nao;
import manager.AdvManager;
import types.modes;
import util.BlockUtil;
import util.GetChar;
import util.MSUtil;
import util.Map;

public class PlayerInfo {
	Player player;
	int cradit;
	int spopencradit;
	int addcradit;
	int page = 0;
	int invtype = 0;
	int charban = 1;
	public String name;
	public boolean gamejoin = true;
	public boolean abchar = false;
	public String team;
	ItemStack head;
	
	
	List<String> craditlog = new ArrayList<String>();
	
	public boolean spopen[] = new boolean[GetChar.getCount()+1];
	boolean trophy[][] = new boolean[500][100];
	public String playerTrophy = "";
	int playertdr = 0;
	public int playerc;
	int kille;
	public int playchar = 0;
	Inventory spinv;
	
	PlayerInfo(Player p){
		player = p;
		load();
		player.getInventory().clear();
	}
	public void log() {
		player.sendMessage("§a§l==============================");
		for(String s: craditlog) {
			player.sendMessage(s);
		}
	}
	public int getcradit() { return cradit; }
	
	public boolean isTropy(int i, int j) {
		return trophy[i][j];
	}
	
	public void addcradit(int i,String s) {
		cradit += i;
		LocalTime now = LocalTime.now();
		Rule.savePoint(player.getName(), cradit, 0);
		s= "["+now.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"] §f"+s;
		if(i > 0) {
			craditlog.add("§e§l[ARSLOG]§7"+s +" §a+"+i+" ["+cradit+"]");
		} else {
			craditlog.add("§e§l[ARSLOG]§7"+s +" §c"+i+" ["+cradit+"]");
		}
		
		if(craditlog.size() > 50) {
			craditlog.remove(0);
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
	
	public void open(Player p,String s) {
		if(page < 0) page = 0;
		if(s != null) {
			invtype = Integer.parseInt(s);
		}
		p.openInventory(spinv);
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
		cradit = Rule.Var.Loadint(player.getName()+".info.Cradit");
		addcradit = Rule.Var.Loadint(player.getName()+".info.CraditOpen");
		spopencradit = 500 + addcradit*100;
		getinv();
		
		for(int i = 0;i < 500; i++) {
			for(int j = 1; j < 100; j++) {
				if(Main.GetText("tropy:c"+i+"-"+j) != " ") {
					trophy[i][j] = (boolean)Rule.Var.Load(player.getName()+".tropy."+i+"-"+j);
				} else {
					break;
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
			Rule.Var.Save(player.getName()+".info.tp",gamejoin);
			cradit = 1250;
			Rule.Var.setInt(player.getName()+".info.Cradit", cradit);
			trophy[0][1] = true;
			Rule.Var.Save(player.getName()+".tropy.0-1",true);
		}
		playerc = Rule.Var.Loadint(player.getName()+".info.c");
		if(playerc == 0) playerc = 2;
		
		if(Main.GetText("tropy:t"+playertdr) != null && Main.GetText("tropy:c"+playerTrophy) != null) {
			name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
		} else {
			playerTrophy = "0-1";
			Rule.Var.Save(player.getName()+".info.tp","0-1");
			gamejoin = true;
			Rule.Var.Save(player.getName()+".info.tp",gamejoin);
			cradit = 1250;
			Rule.Var.setInt(player.getName()+".info.Cradit", cradit);
			trophy[0][1] = true;
			Rule.Var.Save(player.getName()+".tropy.0-1",true);
		}
		if(!gamejoin) {
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:msg23"));
			player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:msg24"));
		}
	}
	
	public void save(){
		Rule.Var.setInt(player.getName()+".info.Cradit", cradit);
		Rule.Var.setInt(player.getName()+".info.CraditOpen", addcradit);
		Rule.Var.Save(player.getName()+".team", team);
	}
	
	public void tropy(int i,int j) {
		if(!trophy[i][j]) {
			trophy[i][j] = true;
			Rule.Var.Save(player.getName()+".tropy."+i+"-"+j,true);
			AdvManager.set(player, 426, 0 , "§f§l"+Main.GetText("tropy:msg4")+" "+Main.GetText("tropy:c"+i+"-"+j));
		}
	}
	
	public void getinv() {
		if(invtype == 7) createInventoryCInfo();
		if(invtype == 6) createInventoryTeam();
		if(invtype == 5) createInventoryOption();
		if(invtype == 4) createInventoryShop();
		if(invtype == 3) createInventoryTropy();
		if(invtype == 1) createInventoryInfo();
		if(invtype == 0) createInventory();
	}
	
	public void spClick(InventoryClickEvent e) {
		int slot = e.getSlot()+1;
		if(slot == 46 && page > 0) {
			if(e.isShiftClick()) {
				page -= 5;
				if(page < 0) {
					createInventoryMain();
					open(player, "2");
				}
			} else {
				page--;
			}
			getinv();
			open(player,null);
			return;
		} else if(slot == 46){
			createInventoryMain();
			open(player, "2");
		}
		
		if(slot == 54 && page < 44) {
			if(e.isShiftClick()) {
				page += 5;
			} else {
				page++;
			}
			getinv();
			open(player,null);
			return;
		}
		
		if(invtype == 0) {
			inv1(e);
		}
		if(invtype == 1) {
			inv2(e);
		}
		if(invtype == 2) {
			inv3(e);
		}
		if(invtype == 3) {
			inv4(e);
		}
		if(invtype == 4) {
			inv5(e);
		}
		if(invtype == 5) {
			inv6(e);
		}
		if(invtype == 6) {
			inv7(e);
		}
		if(invtype == 7) {
			inv8(e);
		}
	}
	private void inv1(InventoryClickEvent e) {
		int slot = (e.getSlot()+1) + (page*45);
		if(slot < GetChar.getCount()+1) {
			if(!spopen[slot] && e.isShiftClick()) {
				if(spopencradit <= cradit) {
					addcradit(-spopencradit,Main.GetText("main:msg101")+" "+Main.GetText("c"+slot+":sk0"));
					addcradit+=1;
					Rule.Var.open(player.getName()+".Sp.c"+slot,true);
					save();
					load();
					open(player,"0");
					ARSystem.playSound(player,"0event");
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
				}
			} else if (e.isLeftClick() && Bukkit.getOnlinePlayers().size() == 1) {
				Rule.c.put(player,GetChar.get(player, Rule.gamerule, ""+ slot));
			}
		}
	}
	private void inv2(InventoryClickEvent e) {
		int slot = (e.getSlot()+1) + (page*45);
		if(slot < GetChar.getCount()+1) {
			if(e.isShiftClick()) {
				playerc = slot;
				Rule.Var.setInt(player.getName()+".info.c", slot);
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:info22"));
				ARSystem.playSound(player,"0click2");
			} else {
				ARSystem.playSound(player,"0click");
			}
		}
	}
	private void inv3(InventoryClickEvent e) {
		int slot = (e.getSlot()+1);
		ARSystem.playSound(player,"0click");
		if(slot == 11 && e.isShiftClick()) {
			gamejoin = !gamejoin;
			Rule.Var.Save(player.getName()+".gamejoin", gamejoin);
			if(gamejoin) {
				player.sendMessage("§a§l[ARSystem] : §a" + Main.GetText("main:cmd12"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c" + Main.GetText("main:cmd13"));
			}
			createInventoryMain();
			open(player,null);
		} else if(slot == 11 && e.isLeftClick() && ARSystem.AniRandomSkill == null) {
			for(int k =0; k<100;k++) {
				String s = Main.GetText("tropy:c"+playerTrophy+"_"+k);
				if(s != null) {
					if(s.contains("[Effect]")) {
						if(MSUtil.isbuff(player, "ty"+playerTrophy)) {
							MSUtil.buffoff(player, "ty"+playerTrophy);
						} else {
							MSUtil.resetbuff(player);
							player.performCommand("c ty"+playerTrophy);
						}
					}
				}
			}
		}
		if(slot == 13) {
			createInventoryCInfo();
			open(player, null);
		}
		if(slot == 14) {
			createInventoryInfo();
			open(player, null);
		}
		if(slot == 15) {
			createInventory();
			open(player, null);
		}
		if(slot == 16) {
			createInventoryTropy();
			open(player, null);
		}
		if(slot == 17) {
			createInventoryShop();
			open(player, null);
		}
		if(slot == 21 && (player.isOp() || Rule.ishelp(player))) {
			createInventoryOption();
			open(player, null);
		}
	}
	private void inv4(InventoryClickEvent e) {
		int slot = (e.getSlot()+1) + (page*45);
		if(e.getCurrentItem().getTypeId() != 0) {
			if(e.isShiftClick()) {
				String n = e.getCurrentItem().getItemMeta().getLore().get(1).replace("§0", "");
				Rule.Var.Save(player.getName()+".info.tp",n);
				playerTrophy = n;
				name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("tropy:msg3") + " : " + name);
				ARSystem.playSound(player,"0click2");
			} else {
				ARSystem.playSound(player,"0click");
			}
		}
	}
	private void inv5(InventoryClickEvent e) {
		int slot = e.getSlot()+1;
		int cradi = cradit;
		if(slot == 2 && e.isShiftClick()) {
			if(cradit >= 100 && (ARSystem.gameMode == modes.NORMAL || ARSystem.gameMode == modes.TEAM || ARSystem.gameMode == modes.TEAMMATCH || ARSystem.gameMode == modes.ZOMBIE)) {
				if(ARSystem.AniRandomSkill != null && !ARSystem.AniRandomSkill.chageplayer.contains(player)) {
					if(ARSystem.AniRandomSkill.time < 0 && Rule.c.get(player) != null) {
						addcradit(-100,Main.GetText("main:msg107"));
						ARSystem.AniRandomSkill.chageplayer.add(player);
						ARSystem.chaRrep(player);
						player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
					} else {
						player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror8"));
					}
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror5"));
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 3 && e.isShiftClick()) {
			if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 0) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(Rule.c.get(p) instanceof c58nao) continue;
					p.hidePlayer(player);
					player.hidePlayer(p);
					p.showPlayer(player);
					player.showPlayer(p);
				}
				for(int i=0;i<300;i++) {
					if(!BlockUtil.isPathable(player.getLocation().getBlock().getType()))
						player.teleport(player.getLocation().add(0,1,0));
				}
				if(!BlockUtil.isPathable(player.getLocation().clone().add(0,1,0).getBlock().getType())) {
					player.teleport(Map.randomLoc(player));
				}
				createInventoryMain();
				open(player, "2");
			}
		}
		if(slot == 4 && e.isShiftClick()) {
			Date nowDate = new Date();
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	        String ss = "" + simpleDateFormat.format(nowDate);
	        long a = Integer.parseInt(ss);
	        int b = (int)a;
	        int i = Rule.Var.Loadint(player.getName()+".info.CodeDay");
	        if(b > i) {
	        	player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror11"));
	        } else {
	        	abchar = !abchar;
	        }
	        createInventoryMain();
			open(player, "2");
		}
		if(slot == 9 && e.isShiftClick()) {
			log();
			player.closeInventory();
		}
		if(slot == 11 && e.isShiftClick()) {
			if(cradit >= 5000) {
				if(!trophy[0][6]) {
					addcradit(-5000,Main.GetText("main:msg107"));
					tropy(0, 6);
					createInventoryMain();
					open(player, "2");
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 12 && e.isShiftClick()) {
			if(cradit >= 1000) {
				if(!trophy[0][7]) {
					addcradit(-1000,Main.GetText("main:msg107"));
					tropy(0, 7);
					createInventoryMain();
					open(player, "2");
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 13 && e.isShiftClick()) {
			if(cradit >= 2000) {
				if(!trophy[0][8]) {
					addcradit(-2000,Main.GetText("main:msg107"));
					tropy(0, 8);
					createInventoryMain();
					open(player, "2");
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 14 && e.isShiftClick()) {
			if(cradit >= 1000) {
				if(!trophy[0][9]) {
					addcradit(-1000,Main.GetText("main:msg107"));
					tropy(0, 9);
					createInventoryMain();
					open(player, "2");
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 15 && e.isShiftClick()) {
			if(cradit >= 500) {
				if(!trophy[0][10]) {
					addcradit(-500,Main.GetText("main:msg107"));
					tropy(0, 10);
					createInventoryMain();
					open(player, "2");
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 16 && e.isShiftClick()) {
			if(cradit >= 5000) {
				if(!trophy[0][11]) {
					addcradit(-5000,Main.GetText("main:msg107"));
					tropy(0, 11);
					createInventoryMain();
					open(player, "2");
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 17 && e.isShiftClick()) {
			if(cradit >= 5000) {
				if(!trophy[0][12]) {
					addcradit(-5000,Main.GetText("main:msg107"));
					tropy(0, 12);
					createInventoryMain();
					open(player, "2");
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 18 && e.isShiftClick()) {
			if(cradit >= 50000) {
				if(!trophy[0][13]) {
					addcradit(-50000,Main.GetText("main:msg107"));
					tropy(0, 13);
					createInventoryMain();
					open(player, "2");
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
				} else {
					player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror4"));
				}
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 29 && e.isShiftClick()) {
			if(cradit >= 100) {
				addcradit(-100,Main.GetText("main:msg107"));
				playertdr = 0;
				Rule.Var.setInt(player.getName()+".info.td", playertdr);
				name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		
		if(slot == 30 && e.isShiftClick()) {
			if(playertdr==1) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 200) {
				addcradit(-200,Main.GetText("main:msg107"));
				playertdr = 1;
				Rule.Var.setInt(player.getName()+".info.td", playertdr);
				name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		
		if(slot == 31 && e.isShiftClick()) {
			if(playertdr==2) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 500) {
				addcradit(-500,Main.GetText("main:msg107"));
				playertdr = 2;
				Rule.Var.setInt(player.getName()+".info.td", playertdr);
				name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 32 && e.isShiftClick()) {
			if(playertdr==3) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 500) {
				addcradit(-500,Main.GetText("main:msg107"));
				playertdr = 3;
				Rule.Var.setInt(player.getName()+".info.td", playertdr);
				name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 33 && e.isShiftClick()) {
			if(playertdr==4) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 500) {
				addcradit(-500,Main.GetText("main:msg107"));
				playertdr = 4;
				Rule.Var.setInt(player.getName()+".info.td", playertdr);
				name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 34 && e.isShiftClick()) {
			if(playertdr==5) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 1000) {
				addcradit(-1000,Main.GetText("main:msg107"));
				playertdr = 5;
				Rule.Var.setInt(player.getName()+".info.td", playertdr);
				name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 35 && e.isShiftClick()) {
			if(playertdr==6) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 1000) {
				addcradit(-1000,Main.GetText("main:msg107"));
				playertdr = 6;
				Rule.Var.setInt(player.getName()+".info.td", playertdr);
				name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 36 && e.isShiftClick()) {
			if(playertdr==7) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 1000) {
				addcradit(-1000,Main.GetText("main:msg107"));
				playertdr = 7;
				Rule.Var.setInt(player.getName()+".info.td", playertdr);
				name = ""+Main.GetText("tropy:t"+playertdr).replace(" ", Main.GetText("tropy:c"+playerTrophy));
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		
		if(slot == 47 && e.isShiftClick()) {
			if(kille==1) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 500) {
				addcradit(-500,Main.GetText("main:msg107"));
				kille = 1;
				Rule.Var.setInt(player.getName()+".info.ke",kille);
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 48 && e.isShiftClick()) {
			if(kille==2) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 500) {
				addcradit(-500,Main.GetText("main:msg107"));
				kille = 2;
				Rule.Var.setInt(player.getName()+".info.ke",kille);
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 49 && e.isShiftClick()) {
			if(kille==3) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 500) {
				addcradit(-500,Main.GetText("main:msg107"));
				kille = 3;
				Rule.Var.setInt(player.getName()+".info.ke",kille);
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 50 && e.isShiftClick()) {
			if(kille==4) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 500) {
				addcradit(-500,Main.GetText("main:msg107"));
				kille = 4;
				Rule.Var.setInt(player.getName()+".info.ke",kille);
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(slot == 51 && e.isShiftClick()) {
			if(kille==5) {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror7"));
				return;
			}
			if(cradit >= 500) {
				addcradit(-500,Main.GetText("main:msg107"));
				kille = 5;
				Rule.Var.setInt(player.getName()+".info.ke",kille);
				createInventoryMain();
				open(player, "2");
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:shop10"));
			} else {
				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("main:cmderror2"));
			}
		}
		if(cradi == cradit) {
			ARSystem.playSound(player,"0click");
		} else {
			ARSystem.playSound(player,"0event");
		}
	}
	
	private void inv6(InventoryClickEvent e) {
		int slot = e.getSlot()+1;
		if(slot == 11) {
			if(e.isRightClick()) {
				ARSystem.Gamemode++;
				if(ARSystem.Gamemode > ARSystem.gameMode.size) {
					ARSystem.Gamemode = 1;
				}
			}
			if(e.isLeftClick()) {
				ARSystem.Gamemode--;
				if(ARSystem.Gamemode < 1) {
					ARSystem.Gamemode = ARSystem.gameMode.size;
				}
			}
			Bukkit.broadcastMessage("§a§l[ARSystem] :§c§l " + Main.GetText("main:info36")+" : " + Main.GetText("main:mode" + ARSystem.Gamemode));
		}
		if(slot == 12) {
			Rule.pick = !Rule.pick;
			Bukkit.broadcastMessage("§a§l[ARSystem] :§c§l " + Main.GetText("main:mode0")+" : " + Rule.pick);
		}
		if(slot == 2 && ARSystem.Gamemode == 2) {
			if(e.isRightClick()) {
				if(e.isShiftClick()) ARSystem.serverOne+=4;
				ARSystem.serverOne++;
				if(ARSystem.serverOne >= GetChar.getCount()) {
					ARSystem.serverOne = GetChar.getCount();
				}
			}
			if(e.isLeftClick()) {
				if(e.isShiftClick()) ARSystem.serverOne-=4;
				ARSystem.serverOne--;
				if(ARSystem.serverOne < 0) {
					ARSystem.serverOne = 0;
				}
			}
		}
		if(slot == 2 && (ARSystem.Gamemode == 4 || ARSystem.Gamemode == 5)) {
			createInventoryTeam();
			open(player,null);
			return;
		}
		if(slot == 13) Rule.auto =! Rule.auto;
		if(slot == 4 && Rule.auto) {
			if(e.isRightClick()) {
				if(e.isShiftClick()) ARSystem.starttime+=4;
				ARSystem.starttime++;
			}
			if(e.isLeftClick()) {
				if(e.isShiftClick()) ARSystem.starttime-=4;
				ARSystem.starttime--;
				if(ARSystem.starttime < 5) {
					ARSystem.starttime = 5;
				}
			}
		}
		if(slot == 14) {
			if(e.isRightClick()) {
				if(e.isShiftClick()) ARSystem.time+=4;
				ARSystem.time++;
			}
			if(e.isLeftClick()) {
				if(e.isShiftClick()) ARSystem.time-=4;
				ARSystem.time--;
				if(ARSystem.time < 0) {
					ARSystem.time = 0;
				}
			}
		}
		if(slot == 15) ARSystem.ban = !ARSystem.ban;
		if(slot == 6 && ARSystem.ban) {
			if(e.getAction() == InventoryAction.DROP_ONE_SLOT) {
				ARSystem.charban[charban] = !ARSystem.charban[charban];
				Bukkit.broadcastMessage("§a§l[ARSystem] :§c§l "+Main.GetText("main:info42")+" §f["+(charban+1)+"] " + Main.GetText("c"+(charban+1)+":name1")+" "+ Main.GetText("c"+(charban+1)+":name2") + " : " + ARSystem.charban[(charban)]);
			}
			if(e.isRightClick()) {
				if(e.isShiftClick()) charban+=4;
				charban++;
				if(charban > GetChar.getCount()-1) charban = GetChar.getCount()-1;
			}
			if(e.isLeftClick()) {
				if(e.isShiftClick()) charban-=4;
				charban--;
				if(charban < 0) charban = 0;
				
			}
			
		}
		
		
		if(slot == 17) ARSystem.Start(-1);
		createInventoryOption();
		open(player, null);
	}
	String lastteam = null;
	
	private void inv7(InventoryClickEvent e) {
		int slot = e.getSlot()+1;
		if(e.getCurrentItem() != null && e.getCurrentItem().getTypeId() != 0 &&e.getCurrentItem().getItemMeta() != null) {
			Player player = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().split(" : ")[0]);
			if(Rule.playerinfo.get(player) != null) {
			String team = Rule.playerinfo.get(player).team;
				if(e.isLeftClick()) {
					if(team == null) {
						Rule.playerinfo.get(player).team = "red";
					} else if(team.equals("red")) {
						Rule.playerinfo.get(player).team = "blue";
					} else if(team.equals("blue")) {
						Rule.playerinfo.get(player).team = "yellow";
					} else if(team.equals("yellow")) {
						Rule.playerinfo.get(player).team = "green";
					} else if(team.equals("green")) {
						Rule.playerinfo.get(player).team = null;
					}
				}
				if(e.isRightClick()){
					if(team == null) {
						Rule.playerinfo.get(player).team = "green";
					} else if(team.equals("red")) {
						Rule.playerinfo.get(player).team = null;
					} else if(team.equals("blue")) {
						Rule.playerinfo.get(player).team = "red";
					} else if(team.equals("yellow")) {
						Rule.playerinfo.get(player).team = "blue";
					} else if(team.equals("green")) {
						Rule.playerinfo.get(player).team = "yellow";
					}
				}
				if(e.isShiftClick()) {
					Rule.playerinfo.get(player).team = lastteam;
				}
				lastteam = Rule.playerinfo.get(player).team;
				createInventoryTeam();
				open(this.player,null);
				Bukkit.broadcastMessage("§a§l[ARSystem] : §cTeam Join §7"+player.getName() +" §f: "+lastteam);
			}
		} else {
			if(e.isShiftClick()) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					Rule.playerinfo.get(p).team = null;
				}
				Bukkit.broadcastMessage("§a§l[ARSystem] : §cOnline Player Team Reset");
			}
		}
	}
	
	private void inv8(InventoryClickEvent e) {
		int slot = (e.getSlot()+1) + (page*45);
		if(e.getCurrentItem().getTypeId() != 0) {
			if(e.isShiftClick()) {
				String n = e.getCurrentItem().getItemMeta().getLore().get(1).replace("§0", "");

				player.sendMessage("§a§l[ARSystem] : §c§l "+Main.GetText("tropy:msg3") + " : " + name);
				ARSystem.playSound(player,"0click2");
			} else {
				ARSystem.playSound(player,"0click");
			}
		}
		createInventoryCInfo();
		open(player,"7");
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
	
	public void createInventoryShop() {
		invtype = 4;

		spinv = new CraftInventoryCustom(null, 54, player.getName() +" : Info");
		ItemStack is = null;
		List<String> lore = new ArrayList<String>();
		ItemMeta meta;
		
		is = new ItemStack(345, 1);
		lore.clear();
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:shop6"));
		lore.add("§e=====================================");
		lore.add("§7"+ Main.GetText("main:shop4"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(0, is);
		
		is = new ItemStack(403, 1);
		lore.clear();
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:shop2"));
		lore.add("§e=====================================");
		lore.add("§7"+ Main.GetText("main:shop4"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(9, is);
		
		is = new ItemStack(426, 1);
		lore.clear();
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:msg2"));
		lore.add("§e=====================================");
		lore.add("§7"+ Main.GetText("main:shop4"));
		lore.add("§7"+ Main.GetText("main:shop5"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(27, is);
		
		is = new ItemStack(378, 1);
		lore.clear();
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:shop3"));
		lore.add("§e=====================================");
		lore.add("§7"+ Main.GetText("main:shop4"));
		lore.add("§7"+ Main.GetText("main:shop5"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(45, is);
		// game
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:shop7"));
		lore.add("§e=====================================");
		lore.add("§7§l"+Main.GetText("main:shop8"));
		lore.add("§7§l"+Main.GetText("main:shop9"));
		lore.add("§a§l 100 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(1, is);
		
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:shop11"));
		lore.add("§e=====================================");
		lore.add("§7§l"+Main.GetText("main:shop12"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(2, is);
		
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:shop13"));
		lore.add("§e=====================================");
		lore.add("§7§l"+Main.GetText("main:shop15"));
		lore.add("§7§l"+Main.GetText("main:shop16"));
		lore.add("§7§l"+Main.GetText("main:shop14"));
		lore.add("§c§l"+ abchar);
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(3, is);
		
		
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f"+Main.GetText("main:info18"));
		is.setItemMeta(meta);
		spinv.setItem(8, is);
		
		// trophy
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:c0-6"));
		lore.add("§e=====================================");
		lore.add("§a§l 5000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(10, is);
		
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:c0-7"));
		lore.add("§e=====================================");
		lore.add("§a§l 1000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(11, is);
		
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:c0-8"));
		lore.add("§e=====================================");
		lore.add("§a§l 2000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(12, is);
		
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:c0-9"));
		lore.add("§e=====================================");
		lore.add("§a§l 1000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(13, is);
		
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:c0-10"));
		lore.add("§e=====================================");
		lore.add("§a§l 500 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(14, is);
		
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:c0-11"));
		lore.add("§e=====================================");
		lore.add("§a§l 5000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(15, is);
		
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:c0-12"));
		lore.add("§e=====================================");
		lore.add("§a§l 5000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(16, is);
		
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:c0-13"));
		lore.add("§e=====================================");
		lore.add("§a§l 50000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(17, is);
		
		// trophy2
		lore.clear();
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:t0"));
		lore.add("§e=====================================");
		lore.add("§a§l 100 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(28, is);
		
		lore.clear();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:t1"));
		lore.add("§e=====================================");
		lore.add("§a§l 200 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(29, is);
		
		lore.clear();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:t2"));
		lore.add("§e=====================================");
		lore.add("§a§l 500 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(30, is);
		
		lore.clear();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:t3"));
		lore.add("§e=====================================");
		lore.add("§a§l 500 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(31, is);
		
		lore.clear();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:t4"));
		lore.add("§e=====================================");
		lore.add("§a§l 500 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(32, is);
		
		
		lore.clear();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:t5"));
		lore.add("§e=====================================");
		lore.add("§a§l 1000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(33, is);
		
		lore.clear();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:t6"));
		lore.add("§e=====================================");
		lore.add("§a§l 1000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(34, is);
		
		lore.clear();
		meta.setDisplayName("§f§l"+Main.GetText("tropy:t7"));
		lore.add("§e=====================================");
		lore.add("§a§l 1000 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(35, is);
		// effect
		
		lore.clear();
		is = new ItemStack(377, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:ke1"));
		lore.add("§e=====================================");
		lore.add("§a§l 100 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(46, is);
		
		lore.clear();
		is = new ItemStack(377, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:ke2"));
		lore.add("§e=====================================");
		lore.add("§a§l 500 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(47, is);
		
		lore.clear();
		is = new ItemStack(377, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:ke3"));
		lore.add("§e=====================================");
		lore.add("§a§l 500 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(48, is);
		
		lore.clear();
		is = new ItemStack(377, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:ke4"));
		lore.add("§e=====================================");
		lore.add("§a§l 500 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(49, is);
		
		lore.clear();
		is = new ItemStack(377, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:ke5"));
		lore.add("§e=====================================");
		lore.add("§a§l 500 Point");
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(50, is);
		
		//
		lore.clear();
		meta.setLore(lore);
		is = new ItemStack(160,1,(short) 7);
		meta.setDisplayName("§f");
		is.setItemMeta(meta);
	
		for(int i = 0; i < 54; i++) {
			if(spinv.getItem(i) == null) {
				spinv.setItem(i, is);
			}
		}
	}
	
	public void createInventoryTropy() {
		invtype = 3;
		List<String> l = new ArrayList<String>();
		
		spinv = new CraftInventoryCustom(null, 54, player.getName() +" : Info");
		
		int list = 0;
		
		ItemStack is = null;
		List<String> lore = new ArrayList<String>();
			for(int i = 0; i <= GetChar.getCount(); i++) {
				for(int k = 1; k < 100; k++) {
					if(trophy[i][k]) {
						l.add(""+i+"-"+k);
					}
				}
			}

		for(int i = 0; i < 45;i++) {
			if(i+(page*45) < l.size() && l.get(i+(page*45)) != null) {
				is = new ItemStack(426, 1);
				ItemMeta meta = is.getItemMeta();
				
				meta.setDisplayName("§f"+Main.GetText("tropy:c"+l.get(i+(page*45))));
				lore.clear();
				lore.add("§e=====================================");
				lore.add("§0"+l.get(i+(page*45)));
				for(int k = 1; k < 100; k++) {
					if(Main.GetText("tropy:c"+l.get(i+(page*45))+"_"+k) != null) {
						lore.add("§f"+Main.GetText("tropy:c"+l.get(i+(page*45))+"_"+k));
					} else {
						break;
					}
				}
				lore.add("§e=====================================");
				lore.add("§7"+Main.GetText("tropy:msg1"));
				boolean a = false;
				for(String ss : lore) if(ss.contains("[Effect]")) a= true;
				if(a) {
					lore.add("§7"+Main.GetText("tropy:msg5"));
					lore.add("§7"+Main.GetText("tropy:msg6"));
				}
				meta.setLore(lore);
				is.setItemMeta(meta);
				spinv.setItem(list, is);
				
				list++;
			}
		}
		
		lore.clear();
		is = new ItemStack(339, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:info12"));
		lore.add("§e=====================================");
		lore.add("§a§l"+ (page) +Main.GetText("main:info13"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(45, is);
		
		lore.clear();
		
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:info11"));
		lore.add("§e=====================================");
		lore.add("§a§l"+ (page+2) +Main.GetText("main:info13"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(53, is);
	}
	
	public void createInventoryMain() {
		invtype = 2;

		spinv = new CraftInventoryCustom(null, 27, player.getName() +" : Info");
		
		ItemStack is = null;
		if(gamejoin) {
			is = new ItemStack(278, 1, (short) playerc);
		} else {
			is = new ItemStack(277, 1, (short) playerc);
		}
		ItemMeta meta = is.getItemMeta();
		
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setUnbreakable(true);
		meta.setDisplayName("§f"+player.getName() + Main.GetText("main:info1"));
			
		List<String> lore = new ArrayList<String>();
		lore.add("§e=====================================");
		try {
			lore.add("§f"+Main.GetText("main:info16")+"§l"+Main.GetText("c"+playerc+":name1").replace("-", "") +" "+ Main.GetText("c"+playerc+":name2"));
		} catch(Exception e) {
			
		}
		lore.add("§7"+Main.GetText("main:info6")+" "+ name);
		lore.add("§7"+Main.GetText("main:info23")+" "+ Main.GetText("main:ke"+kille));
		lore.add("§7"+Main.GetText("main:info5")+" "+ cradit);
		lore.add("§7"+Main.GetText("main:info24")+" "+  getScore());
		lore.add("§e=====================================");
		lore.add("§7"+Main.GetText("main:info21")+" "+ max(player.getName()+".c","Time")+"(s)");
		lore.add("§7"+Main.GetText("main:info2")+" "+ max(player.getName()+".c","Play"));
		lore.add("§7"+Main.GetText("main:info3")+" "+ max(player.getName()+".c","Win"));
		lore.add("§7"+Main.GetText("main:info4")+" "+ max(player.getName()+".c","Kill"));
		lore.add("§e=====================================");
		lore.add("§7"+Main.GetText("main:info19"));
		lore.add("§7"+Main.GetText("main:info20"));
		meta.setLore(lore);
			
		is.setItemMeta(meta);
		spinv.setItem(10, is);

		
		is = new ItemStack(386, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f"+Main.GetText("main:info50"));
		is.setItemMeta(meta);
		spinv.setItem(12, is);
		
		is = new ItemStack(340, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f"+Main.GetText("main:info7"));
		is.setItemMeta(meta);
		spinv.setItem(13, is);
		
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f"+Main.GetText("main:info17"));
		is.setItemMeta(meta);
		spinv.setItem(14, is);
		
		is = new ItemStack(426, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f"+Main.GetText("main:info9"));
		is.setItemMeta(meta);
		spinv.setItem(15, is);
		
		is = new ItemStack(266, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f"+Main.GetText("main:info8"));
		is.setItemMeta(meta);
		spinv.setItem(16, is);
		

		if(player.isOp() || Rule.ishelp(player)) {
			is = new ItemStack(339, 1);
			meta = is.getItemMeta();
			meta.setDisplayName("§f"+Main.GetText("main:info35"));
			is.setItemMeta(meta);
			spinv.setItem(20, is);
		}
		
		is = new ItemStack(160,1,(short) 7);
		meta.setDisplayName("§f");
		is.setItemMeta(meta);
	
		for(int i = 0; i < 27; i++) {
			if(spinv.getItem(i) == null) {
				spinv.setItem(i, is);
			}
		}
	}

	public void createInventoryTeam() {
		invtype = 6;

		spinv = new CraftInventoryCustom(null, 54, player.getName() +" : Info");
		
		ItemStack is = null;
		is = new ItemStack(340, 1);
		ItemMeta meta = is.getItemMeta();
		int i = 0;
		List<String> lore = new ArrayList<String>();
		lore.add("§c"+Main.GetText("main:info37"));
		lore.add("§f"+Main.GetText("main:info46"));
		lore.add("§f"+Main.GetText("main:info47"));
		lore.add("§f"+Main.GetText("main:info48"));
		lore.add("§f"+Main.GetText("main:info49"));
		for(Player p : Bukkit.getOnlinePlayers()) {
			is = new ItemStack(397,1,(short) 3);
			SkullMeta sk = (SkullMeta)is.getItemMeta();
			sk.setOwningPlayer(p);
			String tm = Rule.playerinfo.get(p).team;
			if(tm == null) tm = "No";
			sk.setDisplayName(p.getName() +" : " + tm);
			sk.setLore(lore);
			is.setItemMeta(sk);
			spinv.setItem(i++, is);
		}
	}
	
	public void createInventoryOption() {
		invtype = 5;

		spinv = new CraftInventoryCustom(null, 27, player.getName() +" : Info");
		
		ItemStack is = null;
		is = new ItemStack(340, 1);
		ItemMeta meta = is.getItemMeta();	
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setUnbreakable(true);
		List<String> lore = new ArrayList<String>();
		is.setItemMeta(meta);
		
		lore.clear();
		meta.setDisplayName("§f"+Main.GetText("main:info36"));
		lore.add("§7"+Main.GetText("main:info36")+" : " + Main.GetText("main:mode" + ARSystem.Gamemode));
		lore.add("§7"+Main.GetText("main:info37"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(10, is);
		
		if(ARSystem.Gamemode == 2) {
			lore.clear();
			if(ARSystem.serverOne == 0) {
				is = new ItemStack(166,1);
			} else {
				is = new ItemStack(278,1, (short) ARSystem.serverOne);
				meta.setDisplayName("§f"+Main.GetText("main:mode2") +" : ["+ARSystem.serverOne+"]" + Main.GetText("c"+ARSystem.serverOne+":name1")+" "+ Main.GetText("c"+ARSystem.serverOne+":name2"));
			}
			lore.clear();
			lore.add("§7"+Main.GetText("main:info37"));
			lore.add("§7"+Main.GetText("main:info38"));
			meta.setLore(lore);
			is.setItemMeta(meta);
			spinv.setItem(1, is);
		}
		if(ARSystem.Gamemode == 4 || ARSystem.Gamemode == 5) {
			lore.clear();

			meta.setDisplayName("§fTeam Select");
			is = new ItemStack(35,1,(short) 14);
			lore.clear();
			meta.setLore(lore);
			is.setItemMeta(meta);
			spinv.setItem(1, is);
		}
		
		lore.clear();
		is = new ItemStack(391,1);
		meta.setDisplayName("§f"+Main.GetText("main:mode0") + ": " + Rule.pick);
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(11, is);
	
		lore.clear();
		is = new ItemStack(449,1);
		meta.setDisplayName("§f"+Main.GetText("main:cmd11") + ": " + Rule.auto);
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(12, is);
		
		if(Rule.auto) {
			lore.clear();
			is = new ItemStack(269,1);
			meta.setDisplayName("§f"+Main.GetText("main:info40") + ": " + ARSystem.starttime);
			lore.add("§7"+Main.GetText("main:info37"));
			lore.add("§7"+Main.GetText("main:info38"));
			meta.setLore(lore);
			is.setItemMeta(meta);
			spinv.setItem(3, is);
		}
		lore.clear();
		is = new ItemStack(442,1);
		meta.setDisplayName("§f"+Main.GetText("main:info41") + ": " + ARSystem.time);
		lore.add("§7"+Main.GetText("main:info37"));
		lore.add("§7"+Main.GetText("main:info38"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(13, is);
		
		lore.clear();
		is = new ItemStack(166,1);
		meta.setDisplayName("§f"+Main.GetText("main:info42") + ": " + ARSystem.ban);
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(14, is);
		
		if(ARSystem.ban) {
			lore.clear();
			if(ARSystem.charban[charban]) {
				is = new ItemStack(277,1,(short) (charban+1));
				lore.add("§c"+Main.GetText("main:info44"));
			} else {
				is = new ItemStack(278,1,(short) (charban+1));
				lore.add("§a"+Main.GetText("main:info45"));
			}
			meta.setDisplayName("§f["+(charban+1)+"] " + Main.GetText("c"+(charban+1)+":name1")+" "+ Main.GetText("c"+(charban+1)+":name2"));
		
			lore.add("§7"+Main.GetText("main:info37"));
			lore.add("§7"+Main.GetText("main:info38"));
			lore.add("§7"+Main.GetText("main:info43"));
			meta.setLore(lore);
			is.setItemMeta(meta);
			spinv.setItem(5, is);
		}
		
		lore.clear();
		is = new ItemStack(267,1);
		meta.setDisplayName("§f"+Main.GetText("main:cmd2"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(16, is);
		
		
		
		lore.clear();
		meta.setLore(lore);
		is = new ItemStack(160,1,(short) 7);
		meta.setDisplayName("§f");
		is.setItemMeta(meta);
	
		
		for(int i = 0; i < 27; i++) {
			if(spinv.getItem(i) == null) {
				spinv.setItem(i, is);
			}
		}
	}
	
	public void createInventoryCInfo() {
		invtype = 7;
		int number = page * 45;
		
		spinv = new CraftInventoryCustom(null, 54, player.getName() +" : Info List");
		int list = 0;
		
		for(int j = 1; j< 46; j++) {
			ItemStack is;
			int i = j + number;
			if(i <= GetChar.getCount()) {
				is = new ItemStack(278, 1, (short) i);

				ItemMeta meta = is.getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				meta.setUnbreakable(true);
				
				if(Main.GetText("c"+i+":name2") != null) {
					meta.setDisplayName("§f§l["+i+"]"+Main.GetText("c"+i+":name1").replace("-", "") +" "+ Main.GetText("c"+i+":name2"));
				} else {
					meta.setDisplayName("§f§l["+i+"]");
				}
				List<String> lore = new ArrayList<String>();
				int n = 1;
				lore.add("§a§l"+Main.GetText("main:info51"));
				while(Main.GetText("c"+i+":info"+n) != null) {
					String text = Main.GetText("c"+i+":info"+n);
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
				while(Main.GetText("c"+i+":help"+n) != null) {
					String text = Main.GetText("c"+i+":help"+n);
					lore.add("§f" + text);
					n++;
				}
				if(n == 1) lore.add("§7"+Main.GetText("main:cmderror3"));

				n = 1;
				lore.add("§c§l"+Main.GetText("main:info53"));
				while(Main.GetText("c"+i+":kill"+n) != null) {
					String text = Main.GetText("c"+i+":kill"+n);
					lore.add("§6" + text);
					n++;
				}
				if(n == 1) lore.add("§7"+Main.GetText("main:cmderror3"));
				
				meta.setLore(lore);
				
				is.setItemMeta(meta);
				spinv.setItem(list, is);
				list++;
			}
		}
		List<String> lore = new ArrayList<String>();
		
		ItemStack is = new ItemStack(339, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:info12"));
		lore.add("§e=====================================");
		lore.add("§a§l"+ (page) +Main.GetText("main:info13"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(45, is);
		
		lore.clear();
		
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:info11"));
		lore.add("§e=====================================");
		lore.add("§a§l"+ (page+2) +Main.GetText("main:info13"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(53, is);
	}
	
	public void createInventoryInfo() {
		invtype = 1;
		int number = page * 45;
		
		spinv = new CraftInventoryCustom(null, 54, player.getName() +" : Info List");
		int list = 0;
		
		for(int j = 1; j< 46; j++) {
			ItemStack is;
			int i = j + number;
			if(i <= GetChar.getCount()) {
				int kill = Rule.Var.Loadint(player.getName()+".c"+(i%1000)+"Kill");
				int play = Rule.Var.Loadint(player.getName()+".c"+(i%1000)+"Play");
				int win = Rule.Var.Loadint(player.getName()+".c"+(i%1000)+"Win");
				
				if(play > 0) {
					is = new ItemStack(278, 1, (short) i);
				} else {
					is = new ItemStack(277, 1, (short) i);
				}
				ItemMeta meta = is.getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				meta.setUnbreakable(true);
				
				if(Main.GetText("c"+i+":name2") != null) {
					meta.setDisplayName("§f§l["+i+"]"+Main.GetText("c"+i+":name1").replace("-", "") +" "+ Main.GetText("c"+i+":name2"));
				} else {
					meta.setDisplayName("§f§l["+i+"]");
				}
				List<String> lore = new ArrayList<String>();
				lore.add("§a=====================================");
				if(play > 0) {
					lore.add("§e" + Main.GetText("main:info2") + " §f" + play);
					lore.add("§e" + Main.GetText("main:info3") + " §f" + win);
					lore.add("§e" + Main.GetText("main:info4") + " §f" + kill);
					lore.add("§e" + Main.GetText("main:info14") + " §c" + (Math.round(n(win,max(player.getName()+".c","Win"))*1000)/10.0f) +"%");
				} else {
					lore.add("§7"+Main.GetText("main:cmderror3"));
				}
				lore.add("§a=============["+Main.GetText("main:info10")+"]==============");
				win = Rule.Var.Loadint("ARSystem.c"+(i%1000)+"Win");
				lore.add("§e" + Main.GetText("main:info2") + " §f" + Rule.Var.Loadint("ARSystem.c"+(i%1000)+"Play"));
				lore.add("§e" + Main.GetText("main:info3") + " §f" + win);
				lore.add("§e" + Main.GetText("main:info4") + " §f" + Rule.Var.Loadint("ARSystem.c"+(i%1000)+"Kill"));
				lore.add("§e" + Main.GetText("main:info14") + " §c" + (Math.round(n(win,max("ARSystem.c","Win"))*1000)/10.0f) +"%");
				lore.add("§7" + Main.GetText("main:info15"));
				meta.setLore(lore);
				
				is.setItemMeta(meta);
				spinv.setItem(list, is);
				list++;
			}
		}
		List<String> lore = new ArrayList<String>();
		
		ItemStack is = new ItemStack(339, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:info12"));
		lore.add("§e=====================================");
		lore.add("§a§l"+ (page) +Main.GetText("main:info13"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(45, is);
		
		lore.clear();
		
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:info11"));
		lore.add("§e=====================================");
		lore.add("§a§l"+ (page+2) +Main.GetText("main:info13"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(53, is);
	}
	
	public void createInventory() {
		invtype = 0;
		int number = page * 45;
		
		spinv = new CraftInventoryCustom(null, 54, player.getName() +" : Special Skill List");
		int list = 0;
		
		for(int j = 1; j< 46; j++) {
			ItemStack is;
			int i = j + number;
			if(i < GetChar.getCount()+1) {
				if(spopen[i]) {
					is = new ItemStack(278, 1, (short) i);
				} else {
					is = new ItemStack(277, 1, (short) i);
				}
				ItemMeta meta = is.getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				meta.setUnbreakable(true);
				if(spopen[i]) {
					meta.setDisplayName("§f§l["+i+"]"+Main.GetText("c"+i+":name1").replace("-", "") +" "+ Main.GetText("c"+i+":name2") +" : §a§l" +Main.GetText("c"+i+":sk0"));
				} else {
					meta.setDisplayName("§f§l["+i+"]"+Main.GetText("c"+i+":name1").replace("-", "") +" "+ Main.GetText("c"+i+":name2") +" : §c§l" +Main.GetText("c"+i+":sk0"));
				}
				List<String> lore = new ArrayList<String>();
				lore.add("§e=====================================");
				if(spopen[i]) {
					for(int k=0;k<100;k++) {
						if(Main.GetText("c"+i+":sk0_lore"+k)!= null) {
							if(Main.GetText("c"+i+":sk0_lore"+k).indexOf(":") != -1) {
								lore.add("§a§l"+Main.GetText("c"+i+":sk0_lore"+k));
							} else {
								lore.add("§f"+Main.GetText("c"+i+":sk0_lore"+k));
							}
						}
					}
				} else {
					lore.add("§7"+Main.GetText("main:msg11"));
					lore.add("§7"+Main.GetText("main:msg12"));
					lore.add("§c"+spopencradit+Main.GetText("main:msg13"));
				}
				meta.setLore(lore);
				
				is.setItemMeta(meta);
				spinv.setItem(list, is);
				list++;
			}
		}
		List<String> lore = new ArrayList<String>();
		
		ItemStack is = new ItemStack(339, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:info12"));
		lore.add("§e=====================================");
		lore.add("§a§l"+ (page) +Main.GetText("main:info13"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(45, is);
		
		lore.clear();
		
		is = new ItemStack(339, 1);
		meta = is.getItemMeta();
		meta.setDisplayName("§f§l"+Main.GetText("main:info11"));
		lore.add("§e=====================================");
		lore.add("§a§l"+ (page+2) +Main.GetText("main:info13"));
		meta.setLore(lore);
		is.setItemMeta(meta);
		spinv.setItem(53, is);
	}
	public ItemStack getHead() {
		return head;
	}
	
}
