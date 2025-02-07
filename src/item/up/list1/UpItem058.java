package item.up.list1;

import java.util.ArrayList;
import java.util.List;

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
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.TimeStop;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem058 extends upitemBase{
	public UpItem058(Player p){
		super(p);
		itemCode = 58;
		setcooldown = 50;
	}
	
	
	@Override
	public boolean skillCast(){
		ARSystem.playSound((Entity)player, "item58");
		Location lc = player.getLocation().clone();
		lc.setYaw(0);
		ARSystem.spellLocCast(player,lc, "item58");
		lc.setYaw(45);
		ARSystem.spellLocCast(player,lc, "item58");
		return false;
	}
}
