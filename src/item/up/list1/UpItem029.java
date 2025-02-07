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
import buff.Ice;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class UpItem029 extends upitemBase{
	public UpItem029(Player p){
		super(p);
		itemCode = 29;
		setcooldown = 3;
	}
	
	
	@Override
	public void kill(LivingEntity death, LivingEntity killer) {
		if(killer == player) {
			for(Entity e : ARSystem.box(player, new Vector(12,8,12), box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				ARSystem.giveBuff(en, new Ice(en, player), 100);
			}
		}
		super.kill(death, killer);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(isCooldown()) {
			LivingEntity en = (LivingEntity)e.getEntity();
			ARSystem.giveBuff(en, new Ice(en, player), 40);
		}
		return super.onAttack(e);
	}
}
