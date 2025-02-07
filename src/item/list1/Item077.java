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

public class Item077 extends itemBase{
	int time = 0;
	public Item077(Player p){
		super(p);
		itemCode = 77;
		setcooldown = 10;
	}
	
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(e.getNewSlot() == 4 && player.isSneaking() && isCooldown()) {
			for(Entity ey : ARSystem.PlayerBeamBox(player, 20, 5, box.TARGET)) {
				if(Rule.c.get(ey) != null) {
					if(ARSystem.playerItem.get(ey).items.size() > 0) {
						ARSystem.playSound((Entity)player, "item77");
						ARSystem.playerItem.get(player).removes.add(this);
						ARSystem.addItem(player, ARSystem.playerItem.get(ey).items.get(0).itemCode);
						ARSystem.playerItem.get(ey).removes.add(ARSystem.playerItem.get(ey).items.get(0));
					}
				}
			}
			return false;
		}
		return super.onSkill(e);
	}
	
	@Override
	public boolean skillCast(){
		for(Entity ey : ARSystem.PlayerBeamBox(player, 20, 5, box.TARGET)) {
			if(Rule.c.get(ey) != null) {
				if(ARSystem.playerItem.get(ey).items.size() > 0) {
					ARSystem.playSound((Entity)player, "item77");
					ARSystem.playerItem.get(player).removes.add(this);
					ARSystem.addItem(player, ARSystem.playerItem.get(ey).items.get(0).itemCode);
					ARSystem.playerItem.get(ey).removes.add(ARSystem.playerItem.get(ey).items.get(0));
				}
			}
		}
		return false;
	}
}
