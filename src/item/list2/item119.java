package item.list2;

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
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class item119 extends itemBase{
	Location lc;
	int tk = 0;
	
	public item119(Player p){
		super(p);
		itemCode = 119;
		lc = player.getLocation().clone();
		setcooldown = 60;
	}
	@Override
	public boolean skillCast() {
		tk = 200;
		ARSystem.playSound((Entity)player, "item119");
		lc = player.getLocation().clone();
		lc.setY(lc.getBlockY());
		while(BlockUtil.isPathable(lc.getBlock())) {
			lc = lc.add(0,-1,0);
			if(lc.getY() < Map.loc_f.getY()) {
				lc = player.getLocation().clone();
				break;
			}
		}
		return false;
	}
	@Override
	protected void onTick() {
		if(tk > 0) {
			if(tk%20 == 0) ARSystem.spellLocCast(player, lc, "item119");
			if(tk%2 == 0) {
				for(Entity e : ARSystem.box(lc,player, new Vector(10,10,10), box.MYALL)){
					Location l = e.getLocation().clone();
					l.setY(lc.getY());
					if(l.distance(lc) < 6) {
						ARSystem.spellLocCast(player, e.getLocation(), "item119e2");
						ARSystem.heal((LivingEntity)e, 0.2);
					}
				}
			}
			tk--;
		}
		super.onTick();
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(tk > 0) {
			Location l = player.getLocation().clone();
			l.setY(lc.getY());
			if(l.distance(lc) < 6) {
				e.setDamage(e.getDamage() * 0.1);
			}
		}
		return super.onHit(e);
	}
	
	
	@Override
	public String getActionbar() {
		if(tk <= 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		return "§c§l<§6"+n+" : §4§l" +AMath.round(tk*0.05, 1) +"§c§l>";
	}
	
}
