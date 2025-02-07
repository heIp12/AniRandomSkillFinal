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
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c001humen3;
import chars.c.c001humen4;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item071 extends itemBase{
	public Item071(Player p){
		super(p);
		itemCode = 71;
	}
	
	@Override
	protected void onStart() {
		if(AMath.random(10) <= 6) {
			Rule.c.put(player, new c001humen3(player, Rule.gamerule, null));
		} else {
			Skill.TimeLoop(player);
		}
		ARSystem.playSound((Entity)player, "0timer");	
	}
}
