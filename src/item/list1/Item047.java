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
import buff.Nodamage;
import buff.TimeStop;
import chars.c.c38hajime;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item047 extends itemBase{
	public Item047(Player p){
		super(p);
		itemCode = 47;
		cooldown = 70;
		setcooldown = 300;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 47) {
			((c38hajime)Rule.c.get(player)).plusUp += 1;
		}
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		Entity en = ARSystem.boxSOne(player, new Vector(15,5,15), box.TARGET);
		if(e.getNewSlot() == 4 && player.isSneaking() && en != null && isCooldown()) {
			ARSystem.spellCast(player, en, "item47");
			return false;
		}
		return super.onSkill(e);
	}

	
	@Override
	public void onDeath(Player p, Entity e) {
		if(e == player && Rule.c.get(e).number == 47) {
			cooldown *= 0.25;
		}
	}
	
	@Override
	public boolean skillCast(){
		Entity en = ARSystem.boxSOne(player, new Vector(15,5,15), box.TARGET);
		if(en == null) return true;
		ARSystem.spellCast(player, en, "item47");
		return false;
	}
}
