package item.list2;

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
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class item111 extends itemBase{
	public item111(Player p){
		super(p);
		itemCode = 111;
		setcooldown = 3;
	}

	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(e.getNewSlot() == 0) {
			if(Rule.c.get(player).cooldown[1] <= 0) {
				Rule.c.get(player).cooldown[1] = setcooldown;
				if(Rule.c.get(player).number == 2) {
					Rule.c.get(player).skill1();
					Location l = player.getLocation();
					if(AMath.random(2) <= 1) l.setYaw(l.getYaw()+25);
					else l.setYaw(l.getYaw()-25);
					ARSystem.spellLocCast(player,l, "item111");
				} else {
					ARSystem.spellCast(player, "item111");
				}
			}
			player.getInventory().setHeldItemSlot(7);
			e.setCancelled(true);
			return true;
		}
		return super.onSkill(e);
	}
	
}
