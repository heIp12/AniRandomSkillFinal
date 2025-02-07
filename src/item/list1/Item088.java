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
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item088 extends itemBase{
	public Item088(Player p){
		super(p);
		itemCode = 88;
	}
	
	@Override
	protected void onTick() {
		for(Entity p : ARSystem.box(player, new Vector(50,50,50), box.TARGET)) {
			Location lc = ULocal.lookAt(player.getLocation().clone(), p.getLocation());
			lc = ULocal.offset(lc, new Vector(1,0,0));
			ARSystem.spellLocCast(player, lc, "item88");
		}
		super.onTick();
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		LivingEntity en = (LivingEntity)e.getEntity();
		double hp = en.getHealth()/en.getMaxHealth();
		if(hp < 0.8) {
			ARSystem.heal(player, e.getDamage() * ((1.0-hp)*0.5f +0.2f));
		}

		if(Rule.c.get(player).number%1000 == 44) {
			ARSystem.heal(player, 1);
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number%1000 == 44) {
			e.setDamage(e.getDamage() * 0.75f);
		}
		return super.onHit(e);
	}
	

	
}
