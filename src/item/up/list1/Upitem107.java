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

public class Upitem107 extends upitemBase{
	public Upitem107(Player p){
		super(p);
		itemCode = 107;
	}
	
	
	@Override
	protected void onTick() {
		if(timer%10 == 0) {
			for(Entity e : ARSystem.box(player, new Vector(10,10,10), box.TARGET)){
				if(ARSystem.playerItem.get(e) != null) {
					boolean dp = false;
					for(itemBase it : ARSystem.playerItem.get(e).items) {
						if(it.getCode() == 107) {
							dp = true;
						}
					}
					if(!dp) {
						for(itemBase it : ARSystem.playerItem.get(e).items) {
							if(it.getCode() != 107) {
								ARSystem.playerItem.get(e).removes.add(it);
								ARSystem.addItem((Player)e, 107);
								for(int i = 0; i<10; i++) Rule.c.get(e).setcooldown[i] *= 1.3f;
								break;
							}
						}
					}
				}
			}
		}
	}
}
