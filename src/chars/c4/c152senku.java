package chars.c4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Item;
import ars.gui.G_Lvup;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Medusa;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import manager.Bgm;
import mode.MItem;
import types.BuffType;
import types.ItemList;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ItemCreate;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c152senku extends c00main{
	int stack = 0;
	boolean start = false;
	boolean p = false;
	Location lc;
	itemBase selitem = null;
	List<ItemStack> item;
	
	int itemcount = 3;
	int sp = 0;
	Player s4 = null;
	
	boolean s1select = false;
	itemBase lastItem;
	int tropy = 0;
	
	@Override
	public void setStack(float f) {
		stack = (int)f;
	}
	
	public c152senku(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 152;
		load();
		text();
		c = this;
		inGame = true;
	}

	@Override
	public boolean skill1() {
		if(player.isSneaking()) {
			s1select = true;
			item = new ArrayList<>();
			for(itemBase i : ARSystem.playerItem.get(player).items) {
				item.add(i.getItem());
			}
			if(item.size() >= 1) {
				new G_Lvup(this, item.toArray(new ItemStack[item.size()]));
			}
		} else {
			if(lastItem == null || !ARSystem.playerItem.get(player).items.contains(lastItem)) return true;
			if(!lastItem.skillCast()) {
				cooldown[1] = lastItem.setcooldown;
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(ARSystem.isGameMode("item") && player.isSneaking()) {
			Location loc = player.getLocation().clone();
			loc = ULocal.offset(loc, new Vector(2,0,0));
			loc = ULocal.lookAt(loc, player.getLocation());
			MItem.spawn("shop6", loc);
			MItem.repCode();
			cooldown[2] -= 10;
		} else {
			for(itemBase item : ARSystem.playerItem.get(player).items) {
				if(item.cooldown > 0) item.cooldown *= 0.3;
			}
			ARSystem.playSound((Entity)player, "c152s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(!player.isSneaking()) {
			if(stack >= 3) {
				try {
					ARSystem.giveBuff(player, new TimeStop(player), 80);
					new G_Item(player, itemcount, Math.min(2000,stack*100), Math.min(2000,stack*200));
				} catch (Exception e) {
					ARSystem.giveBuff(player, new TimeStop(player), 80);
					new G_Item(player, itemcount, 1000, Math.min(2000,stack*200));
				}
				stack -= Math.min(stack,10);
				ARSystem.playSound((Entity)player, "c152s31");
			} else {
				cooldown[3] = 0;
			}
		} else {
			item = new ArrayList<>();
			for(itemBase i : ARSystem.playerItem.get(player).items) {
				if(i.getValue() <= 4000) {
					item.add(i.getItem());
				}
			}
			s4 = null;
			s1select = false;
			selitem = null;
			if(item.size() > 1) {
				new G_Lvup(this, item.toArray(new ItemStack[item.size()]));
				
			} 
			cooldown[3] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		List<Player> en = ARSystem.PlayerOnlyBeamBox(player, 10, 2, box.ALL);
		if(en.size() <= 0 || (Rule.c.get(en.get(0)) != null && Rule.c.get(en.get(0)).getCode()%1000 == 152)) {
			cooldown[4] = 0;
			return false;
		}
		s4 = en.get(0);
		item = new ArrayList<>();
		for(itemBase i : ARSystem.playerItem.get(player).items) {
			if(i.itemCode != 61) item.add(i.getItem());
		}
		selitem = null;
		if(item.size() >= 1) {
			new G_Lvup(this, item.toArray(new ItemStack[item.size()]));
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	
	@Override
	public void select(String i) {
		for(itemBase item : ARSystem.playerItem.get(player).items) {
			if(item.getItem().getItemMeta().getDisplayName().equals(i)){
				if(s1select) {
					lastItem = item;
					s1select = false;
					return;
				}
				if(s4 != null) {
					item.itemRemove();
					ARSystem.playerItem.get(player).items.remove(item);
					ARSystem.addItem(s4, item.getCode());
					ARSystem.playSound((Entity)player, "c152s4");
					s4 = null;
					player.closeInventory();
					return;
				} else if(selitem == null) {
					selitem = item;
					this.item.remove(item);
					new G_Lvup(this, this.item.toArray(new ItemStack[this.item.size()]));
					return;
				} else if(item != selitem){
					int value = selitem.getValue();
					int value2 = item.getValue();
					selitem.itemRemove();
					item.itemRemove();
					ARSystem.playerItem.get(player).items.remove(selitem);
					ARSystem.playerItem.get(player).items.remove(item);
					if(value > value2) {
						int o = value;
						value = value2;
						value2 = o;
					}
					sp += value + value2;
					if(value >= 3000 && value2 >= 3000 && AMath.random(100) <= 30 &&skillCooldown(0)) {
						spskillon();
						spskillen();
						tropy++;
						if(tropy >= 3) {
							Rule.playerinfo.get(this.player).tropy(152,1);
						}
						new G_Item(player, itemcount, 5000, 5000);
						ARSystem.playSound((Entity)player, "c152sp");
						player.sendMessage("§a§l"+Text.get("c152:t2")+" : §f" + value2+"~"+ Math.min(5000,5000));
					} else {
						ARSystem.playSound((Entity)player, "c152s32");
						new G_Item(player, itemcount, value2, Math.min(3000,value+value2));
						player.sendMessage("§a§l"+Text.get("c152:t2")+" : §f" + value2+"~"+ Math.min(3000,value+value2));
					}
					return;
				} else {
					cooldown[3] = 0;
				}
			}
		}
	}

	@Override
	public boolean tick() {
		if(!start) {
			start = true;
			ARSystem.giveBuff(player, new Medusa(player), 400 ,10);
			p = true;
		}
		if(p && Rule.buffmanager.GetBuffTime(player, "medusa") <= 0) {
			p = false;
			if(ARSystem.isGameMode("item")) {
				ARSystem.addItem(player, ItemList.getValueCode(2000, 3000));
			} else {
				ARSystem.addItem(player, ItemList.getValueCode(1500, 2000));
			}
			ARSystem.playSound((Entity)player, "c152p");
			lc = player.getLocation();
			stack = 5 + (Rule.c.size()*2);
		}
		
		if(lc != null && lc.distance(player.getLocation()) >= 1) {
			if(AMath.random(Math.max(1,(int)(26-(skillmult+sskillmult)))) <= 1) stack++;
			lc = player.getLocation();
		}
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c152:t")+ "] : "+ stack);
		}

		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			
		}
		return true;
	}
	
}
