package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import buff.Nodamage;
import buff.TimeStop;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item044 extends itemBase{
	int kill = 0;
	public Item044(Player p){
		super(p);
		itemCode = 44;
		setcooldown = 10;
	}
	@Override
	public void onDeath(Player p, Entity e) {
		if(!quest && e == player && kill > 0) {
			setcooldown = 4;
			questComplete();
		}
	}

	@Override
	public boolean skillCast(){
		ARSystem.playSound((Entity)player, "item44");
		ARSystem.spellCast(player, "item44");
		player.teleport(ULocal.offset(player.getLocation(), new Vector(8,0,0)));
		ARSystem.spellCast(player, "item44");
		kill = 20;
		if(quest) Rule.buffmanager.getBuffs(player).buffClear();
		return false;
	}
	
	@Override
	protected void onTick() {
		if(kill > 0) kill--;
		super.onTick();
	}
}
