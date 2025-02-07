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
import buff.Panic;
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
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item10015 extends itemBase{
	public Item10015(Player p){
		super(p);
		itemCode = 100015;
		setcooldown = 60;
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.playSoundAll("0what");
		ARSystem.spellLocCast(NpcPlayer.npc(player.getLocation()), player.getLocation(), "item100014");
		for(Player p : Rule.c.keySet()) {
			ARSystem.giveBuff(p, new TimeStop(p), 160);
			for(Player pl : Bukkit.getOnlinePlayers()) {
				if(pl.getGameMode() == GameMode.SPECTATOR) {
					pl.setSpectatorTarget(player);
				}
			}
		}
		return false;
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1404);
		item = ItemCreate.Name(item, "§c§l【§e§l⸎§c§l】§a"+Text.get("item:"+itemCode));
		return item;
	}
}
