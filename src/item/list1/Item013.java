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

import ars.ARSystem;
import ars.Rule;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class Item013 extends itemBase{
	public Item013(Player p){
		super(p);
		itemCode = 13;
	}
	double hp = 0;

	@Override
	protected void onStart() {
		hp = player.getMaxHealth()*0.2 + 1;
		player.setMaxHealth(player.getMaxHealth()+ hp);
		if(Rule.c.get(player) != null) {
			Rule.c.get(player).hp = (float)player.getMaxHealth();
		}
		ARSystem.heal(player,hp);
		if(Rule.c.get(player).number == 48) {
			Rule.c.get(player).setcooldown[1] = -10000;
		}
	}
	
	@Override
	public void itemRemove() {
		if(player.getHealth() > player.getMaxHealth() - hp) {
			player.setHealth(player.getMaxHealth() - hp);
		}
		player.setMaxHealth(player.getMaxHealth() - hp);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number == 48) {
			e.setDamage(e.getDamage() + ((LivingEntity)e.getEntity()).getMaxHealth()*0.025);
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number == 48 && AMath.random(100) <= 25) {
			ARSystem.spellCast(player, "c48_s3");
			ARSystem.heal(player, 2);
		}
		return super.onHit(e);
	}
	
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(Rule.c.get(player).number == 48 && e.getNewSlot() == 0 && Rule.c.get(player).cooldown[1] <= 0) {
			Rule.c.get(player).cooldown[1] = 5;
			ARSystem.potion(player, 1, 40, 1);
			return false;
		}
		return super.onSkill(e);
	}
	
	@Override
	public boolean skillCast(){
		if(Rule.c.get(player).number == 48) {
			ARSystem.spellCast(player, "item3-2");
			return false;
		}
		return true;
	}
}
