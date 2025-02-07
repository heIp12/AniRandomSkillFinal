package item.etc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
import buff.Airborne;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import item.list1.itemBase;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10013 extends itemBase{
	public Item10013(Player p){
		super(p);
		itemCode = 100013;
		setcooldown = 30;
	}
	
	@Override
	public boolean skillCast(){
		c00main ch = Rule.c.get(player);
		if(ARSystem.winstop < 0) ARSystem.winstop = 220;
		double hp = player.getHealth();
		if(Map.mapid == 1011) {
			ARSystem.playSoundAll("onemore");
			for(Player p : Rule.c.keySet()) {
				p.setGameMode(GameMode.ADVENTURE);
				Rule.c.get(p).delayEvent.clear();
				Rule.buffmanager.clear();
				ARSystem.spellCast(p, "removeall");
			}
		} else {
			ARSystem.playSound(player,"onemore");
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
			Rule.c.put(player, ch);
			if(Map.mapid == 1011) {
				for(Player p : Rule.c.keySet()) {
					p.setGameMode(GameMode.ADVENTURE);
					Rule.c.get(p).delayEvent.clear();
					Rule.buffmanager.clear();
					ARSystem.spellCast(p, "removeall");
				}
			}
			player.setGameMode(GameMode.ADVENTURE);
			player.setHealth(Math.min(hp,player.getMaxHealth()));
		},200);
		return false;
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1403);
		item = ItemCreate.Name(item, "§c§l【§e§l⸎§c§l】§a"+Text.get("item:"+itemCode));
		return item;
	}
}
