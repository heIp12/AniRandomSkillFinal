package item.list1;

import java.util.ArrayList;
import java.util.List;

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
import buff.PowerUp;
import buff.Sleep;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item035 extends itemBase{
	public Item035(Player p){
		super(p);
		itemCode = 35;
		cooldown = 30;
		setcooldown = 150;
	}
	
	
	@Override
	public boolean skillCast(){
		ARSystem.playSoundAll("item35");
		ARSystem.spellCast(player, "item35");
		return false;
	}
	
}
