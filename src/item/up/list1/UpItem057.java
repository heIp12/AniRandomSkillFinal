package item.up.list1;

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

public class UpItem057 extends upitemBase{
	public UpItem057(Player p){
		super(p);
		itemCode = 57;
		setcooldown = 30;
	}
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 18) {
			setcooldown = 15;
		}
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.playSound((Entity)player, "item57");
		for(Entity ey : ARSystem.box(player, new Vector(20,20,20), box.TARGET)) {
			LivingEntity en = (LivingEntity)ey;
			ARSystem.spellLocCast(player, ULocal.lookAt(player.getLocation().clone(), en.getLocation()), "item57");
			en.setHealth(Math.min(player.getHealth(),en.getMaxHealth()));
		}
		return false;
	}
}
