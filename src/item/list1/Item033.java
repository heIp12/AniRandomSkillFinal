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
import buff.Ice;
import buff.NoHeal;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item033 extends itemBase{
	public Item033(Player p){
		super(p);
		itemCode = 33;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		LivingEntity en = (LivingEntity)e.getEntity();
		if(Rule.buffmanager.GetBuffTime(en, "noheal") < 10) {
			ARSystem.giveBuff(en, new NoHeal(en), 10);
		}

		if(Rule.c.get(player) != null && Rule.c.get(player).number == 149) {
			e.setDamage(e.getDamage() * 3);
		}
		return super.onAttack(e);
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 149) {
			Rule.c.get(player).setcooldown[0] = -100000;
		}
	}
	
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(Rule.c.get(player).number == 149 && e.getNewSlot() == 2 && Rule.c.get(player).cooldown[3] <= 0) {
			Rule.c.get(player).cooldown[3] = 4;
			player.setVelocity(player.getLocation().getDirection().multiply(2));
			return false;
		}
		return super.onSkill(e);
	}
}
