package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class UpItem092 extends upitemBase{
	c00main c = null;
	public UpItem092(Player p){
		super(p);
		itemCode = 92;
	}
	
	@Override
	protected void onStart() {
		c = Rule.c.get(player);
		if(Rule.c.get(player).number == 102) {
			Rule.c.get(player).setStack(14);
		}
		Rule.c.put(player, new c000humen(player, Rule.gamerule, null));
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(!quest) {
			questComplete();
			delay(()->{
				Rule.c.put(player, c);
				c.load();
				ARSystem.addItem(player, 93);
				c = null;
			},0);
		}
		return super.onHit(e);
	}
	
	@Override
	public void itemRemove() {
		if(c != null) {
			delay(()->{
				Rule.c.put(player, c);
				c.load();
			},0);
		}
	}
}
