package item.up.list1;

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
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem048 extends upitemBase{
	public UpItem048(Player p){
		super(p);
		itemCode = 48;
		setcooldown = 14;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 108) {
			Rule.c.get(player).setcooldown[4] = 5;
			setcooldown = 3;
		}
	}
	

	@Override
	public boolean skillCast(){
		ARSystem.spellCast(player, "item48");
		ARSystem.playSound((Entity)player, "c108p");
		return false;
	}
}
