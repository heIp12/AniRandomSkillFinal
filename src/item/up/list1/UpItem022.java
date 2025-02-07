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
import buff.Drop;
import buff.Nodamage;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem022 extends upitemBase{
	public UpItem022(Player p){
		super(p);
		itemCode = 22;
		setcooldown = 30;
	}
	
	@Override
	protected void onTick() {
		if(timer%10 == 0) {
			Entity en = ARSystem.boxSOne(player, new Vector(5,2,5),box.TARGET);
			if(en != null && isCooldown()) {
				ARSystem.spellCast(player, en, "item22");
				ARSystem.giveBuff((LivingEntity)en, new Drop((LivingEntity)en), 40);
			}
		}
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(Rule.c.get(player).number == 119 && e.getNewSlot() == 3 && Rule.c.get(player).cooldown[4] <= 0) {
			if(cooldown > 0) cooldown -=5;
			return false;
		}
		
		return true;
	}
}
