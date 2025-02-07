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
import ars.Rule;
import buff.PowerUp;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item036 extends itemBase{
	public Item036(Player p){
		super(p);
		itemCode = 36;
		setcooldown = 40;
	}
	

	@Override
	public boolean skillCast(){
		ARSystem.playSound((Entity)player ,"item36");
		ARSystem.spellCast(player, "item36");
		
		if(Rule.c.get(player).number == 113) {
			ARSystem.spellLocCast(player,ULocal.offset(player.getLocation(), new Vector(0,0,-1)),  "item36");
			ARSystem.spellLocCast(player,ULocal.offset(player.getLocation(), new Vector(0,0,1)),  "item36");
		}
		return false;
	}
}
