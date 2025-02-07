package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Upitem124 extends upitemBase{
	float hp = 0;
	public Upitem124(Player p){
		super(p);
		itemCode = 124;
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(e == player && p != player) {
			hp +=2;
			Rule.c.get(player).hp +=4;
			player.setMaxHealth(Rule.c.get(player).hp);
			ARSystem.heal(player, 4);
		}
	}
	
	@Override
	public void itemRemove() {
		player.setHealth(Math.max(1, player.getMaxHealth()-hp));
		Rule.c.get(player).hp -=hp;
		player.setMaxHealth(Rule.c.get(player).hp);
	}
	
}
