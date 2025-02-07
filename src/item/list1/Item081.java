package item.list1;

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

public class Item081 extends itemBase{
	public Item081(Player p){
		super(p);
		itemCode = 81;
		setcooldown = 30;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		LivingEntity en = (LivingEntity)e.getEntity();
		e.setDamage(e.getDamage() + en.getHealth()*0.08);
		return super.onAttack(e);
	}

	@Override
	public boolean skillCast(){
		Entity ey = ARSystem.boxSOne(player, new Vector(12,8,12), box.TARGET);
		if(ey == null) return true;
		LivingEntity en = (LivingEntity)ey;
		en.setNoDamageTicks(0);
		en.damage(5,player);
		ARSystem.potion(en, 2, 60, 1);
		ARSystem.potion(player, 1, 60, 1);
		return false;
	}
}
