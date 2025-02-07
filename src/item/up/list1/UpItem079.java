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
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem079 extends upitemBase{
	public UpItem079(Player p){
		super(p);
		itemCode = 79;
		setcooldown = 10;
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.potion(player, 1, 100, 5);
		ARSystem.potion(player, 8, 100, 9);
		return false;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 136) {
			Rule.c.get(player).setStack(6099);
		}
	}
}
