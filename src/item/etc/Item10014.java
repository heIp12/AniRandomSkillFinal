package item.etc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import ars.gui.G_AdvSelect;
import ars.gui.G_Nitory;
import ars.gui.G_RareShop;
import ars.gui.G_Supply;
import ars.gui.solo.G_MapSelect;
import buff.Airborne;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.Bgm;
import mode.MEvent;
import mode.MQb;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10014 extends itemBase{
	public Item10014(Player p){
		super(p);
		itemCode = 100014;
		setcooldown = 30;
	}
	
	@Override
	public boolean skillCast(){
		String txt = "";
		String item = "";
		boolean win = false;
		
		if(AMath.random(100) <= 60) {
			item = Text.get("item:100014_i1");
			if(AMath.random(100) <= 50) {
				Rule.playerinfo.get(player).gold += 500;
				item += 500;
			} else if(AMath.random(50) <= 25) {
				Rule.playerinfo.get(player).gold += 1000;
				item += 1000;
			} else if(AMath.random(25) <= 23) {
				Rule.playerinfo.get(player).gold += 3000;
				item += 3000;
			} else {
				Rule.playerinfo.get(player).gold += 999999;
				item += 999999;
				win = true;
			}
		} else if(AMath.random(40) <= 20) {
			if(AMath.random(2) == 1) {
				item = Text.get("item:100014_i3");
				if(AMath.random(10) <= 7) {
					item += "10%";
					Rule.c.get(player).skillmult += 0.1f;
				} else if(AMath.random(20) <= 19){
					item += "30%";
					Rule.c.get(player).skillmult += 0.3f;
				} else {
					item += "500%";
					Rule.c.get(player).skillmult += 5f;
					win = true;
				}
			} else {
				item = Text.get("item:100014_i2");
				if(AMath.random(10) <= 7) {
					item += "10%";
					Rule.c.get(player).frist_damage += 0.1f;
				} else if(AMath.random(20) <= 19){
					item += "30%";
					Rule.c.get(player).frist_damage += 0.3f;
				} else {
					item += "500%";
					Rule.c.get(player).frist_damage += 5f;
					win = true;
				}
			}
		} else if(AMath.random(20) <= 10) {
			if(AMath.random(45) <= 1) {
				int code = 100009+AMath.random(4);
				ARSystem.addItem(player, code);
				item = Text.get("item:"+code);
				win = true;
			} else if(AMath.random(10) <= 7) {
				int code = ItemList.getValueCode(500, 2000);
				if(AMath.random(10) <= 1) {
					item = Text.get("item2:"+code);
					code += 10000;
					win = true;
				} else {
					item = Text.get("item:"+code);
				}
				ARSystem.addItem(player, code);
			} else if(AMath.random(10) <= 9){
				int code = ItemList.getValueCode(2500, 3000);
				ARSystem.addItem(player, code);
				item = Text.get("item:"+code);
			} else if(AMath.random(10) <= 6){
				int code = ItemList.getValueCode(5000, 5000);
				ARSystem.addItem(player, code);
				item = Text.get("item:"+code);
				win = true;
			} else {
				int code = ItemList.getValueCode(5000, 10000);
				ARSystem.addItem(player, code);
				item = Text.get("item:"+code);
				win = true;
			}
		} else if(AMath.random(10) <= 5) {
			if(AMath.random(10) <= 6) {
				item = Text.get("item:100014_i4");
				int damage = AMath.random(10);
				if(AMath.random(20) <= 5) {
					damage = Integer.MAX_VALUE;
					win = true;
				}
				player.damage(damage);
				item += damage;
			} else if(AMath.random(3) <= 1) {
				item = Text.get("item:100014_i5");
				new G_Supply(player);
				win = true;
			} else {
				item = Text.get("item:100014_i6");
				txt = Text.get("item:100014_t2");
				txt = "§a§l[ARSystem] "+txt.replace("{name}", player.getName()).replace("{item}", item);
				Skill.quit(player);
				String t = txt;
				Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
					player.kickPlayer(t);
				},10);
				win = true;
			}
		} else {
			win = true;
			if(AMath.random(13) <= 1) {
				item = Text.get("item:100014_i19");
			} else if(AMath.random(12) <= 1) {
				item = Text.get("item:100014_i18");
				Rule.c.put(player,GetChar.getHiden(player));
			} else if(AMath.random(11) <= 1) {
				item = Text.get("item:100014_i16");
				Rule.c.get(player).setStack(100);
			} else if(AMath.random(10) <= 1) {
				item = Text.get("item:100014_i7");
				ARSystem.addGameMode(new MQb());
			} else if(AMath.random(9) <= 1) {
				item = Text.get("item:100014_i8");
				new G_MapSelect(player);
			} else if(AMath.random(8) <= 1) {
				item = Text.get("item:100014_i9");
				new G_Nitory(player);
			} else if(AMath.random(7) <= 1) {
				item = Text.get("item:100014_i15");
				Map.spawn("helper", player.getLocation(),1);
			} else if(AMath.random(6) <= 1) {
				item = Text.get("item:100014_i17");
				Bgm.setBgm("r"+AMath.random(12));
			}  else if(AMath.random(5) <= 1) {
				item = Text.get("item:100014_i10");
				new G_AdvSelect(player);
			} else if(AMath.random(4) <= 1) {
				item = Text.get("item:100014_i11");
				player.setMaxHealth(player.getMaxHealth()+ 300);
				Rule.c.get(player).hp = (float) player.getMaxHealth();
				ARSystem.heal(player, 300);
			} else if(AMath.random(3) <= 1) {
				item = Text.get("item:100014_i12");
				new G_RareShop(player);
			} else if(AMath.random(2) <= 1) {
				item = Text.get("item:100014_i13");
				ARSystem.overheal(player, 10000);
			} else {
				item = Text.get("item:100014_i14");
				Rule.buffmanager.selectBuffValue(player, "barrier",444);
			}
		}
		
		if(win) {
			txt = Text.get("item:100014_t2");
		} else {
			txt = Text.get("item:100014_t1");
		}
		txt = "§a§l[ARSystem] "+txt.replace("{name}", player.getName()).replace("{item}", item);
		
		if(win) {
			Bukkit.broadcastMessage(txt);
			ARSystem.playSoundAll("itemupgrad",1);
		} else {
			player.sendMessage(txt);
		}

		ARSystem.removeItem(player, itemCode);
		return false;
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setType(Material.CHEST);
		item = ItemCreate.Name(item, "§c§l【§e§l⸎§c§l】§a"+Text.get("item:"+itemCode).replace("??", MEvent.name));
		return item;
	}
}
