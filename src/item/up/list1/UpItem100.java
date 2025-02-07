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
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class UpItem100 extends upitemBase{
	int stack = 0;
	int tick = 0;
	int tk = 0;
	public UpItem100(Player p){
		super(p);
		itemCode = 100;
		setcooldown = 0.5f;
	}
	
	@Override
	protected void onTick() {
		if(tick > 0) {
			tick--;
			if(tick == 0) {
				tk = stack*40;
				Rule.c.get(player).skillmult -= 0.1*stack;
				stack = 0;
			}
		}
		if(tk > 0) tk--;
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(tk > 0) {
			e.setCancelled(true);
			return false;
		}
		if(e.getNewSlot() == 4 && player.isSneaking() && isCooldown()) {
			boolean sk = skillCast();
			if(sk) cooldown = 0;
			return sk;
		}
		return super.onSkill(e);
	}


	@Override
	public String getActionbar() {
		if(tk < 0 && tick < 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		if(tk > 0) return  "§c§l<§6"+n+" : §4§l" +AMath.round(tk*0.05, 1)+"§c§l>";
		return "§c§l<§6"+n+" : §c["+stack+"]:" +AMath.round(tick*0.05, 1) +"§c§l>";
	}
	
	@Override
	public boolean skillCast(){
		if(tk > 0 || !Rule.c.get(player).hpCost(0.3, false)) return true;
		ARSystem.playSound((Entity)player, "item100");
		tick = 160;
		Rule.c.get(player).skillmult += 0.1;
		stack++;
		return false;
	}
	
	@Override
	public void itemRemove() {
		if(stack > 0) {
			Rule.c.get(player).skillmult -= 0.1*stack;
		}
	}
}
