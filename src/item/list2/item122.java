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

public class item122 extends itemBase{
	float[] cd = new float[10];
	public item122(Player p){
		super(p);
		itemCode = 122;
	}
	
	@Override
	protected void onStart() {
		for(int i = 0; i<10; i++) {
			if(Rule.c.get(player).setcooldown[i] >= 40) {
				Rule.c.get(player).setcooldown[i]*= 0.5;
				cd[i] = Rule.c.get(player).setcooldown[i];
			} else {
				cd[i] = 0;
			}
		}
	}
	
	@Override
	public void itemRemove() {
		for(int i = 0; i<10; i++) {
			Rule.c.get(player).setcooldown[i]+= cd[i];
		}
	}
}
