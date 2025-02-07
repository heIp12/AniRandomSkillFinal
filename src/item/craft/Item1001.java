package item.craft;

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
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item1001 extends itemBase{
	
	public Item1001(Player p){
		super(p);
		itemCode = 1001;
		setcooldown = 8f;
	}
	
	@Override
	protected void onStart() {
		ARSystem.playSound((Entity)player, "item1001");
		player.setWalkSpeed(player.getWalkSpeed()*1.8f);
		if(Rule.c.get(player).number == 2) {
			Rule.c.get(player).setStack(1000);
		}
	}

	@Override
	protected void onTick() {
		if(timer%40 == 0) {
			ARSystem.spellCast(player, "item1001-1");
			ARSystem.spellCast(player, "item1001-2");
		}
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage()+0.5);
		return super.onAttack(e);
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(Rule.c.get(player).cooldown[e.getNewSlot()+1] <= 0 && Rule.c.get(player).setcooldown[e.getNewSlot()+1] > 0) {
			for(int i =0; i<10; i++) {
				if(Rule.c.get(player).cooldown[i] > 0) Rule.c.get(player).cooldown[i] -= 0.5;
			}
		}
		if(e.getNewSlot() == 4 && player.isSneaking() && isCooldown()) {
			skillCast();
			return false;
		}
		return super.onSkill(e);
	}

	
	@Override
	public boolean skillCast(){
		ARSystem.spellCast(player, "item1001");
		for(int i =0; i<10; i++) {
			if(Rule.c.get(player).cooldown[i] > 0) Rule.c.get(player).cooldown[i] -= 2;
		}
		return false;
	}
}
