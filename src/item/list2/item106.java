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

public class item106 extends itemBase{
	public item106(Player p){
		super(p);
		itemCode = 106;
		setcooldown = 25;
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.giveBuff(player, new Stun(player), 15);
		ARSystem.giveBuff(player, new Silence(player), 15);
		ARSystem.spellCast(player, "item106");
		for(Entity ey : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
			for(Buff b : Rule.buffmanager.getBuffs((LivingEntity)ey).getBuff()) {
				b.setTime(0);
			}
		}
		return false;
	}
}
