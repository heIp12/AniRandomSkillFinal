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

import Main.Main;
import ars.Rule;
import event.Skill;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item007 extends itemBase{
	public Item007(Player p){
		super(p);
		itemCode = 7;
		setcooldown = 3;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		Entity en = e.getEntity();
		if(Rule.c.get(en) != null && Main.GetText("c"+Rule.c.get(e.getEntity()).getCode()+":tag").indexOf("tg4") != -1) {
			e.setDamage(e.getDamage() * 0.3);
		} else {
			if(en instanceof Player) {
				e.setDamage(e.getDamage() * 2.5);
			} else {
				e.setDamage(e.getDamage() * 1.4);
			}
			if(isCooldown()) Holo.create(en.getLocation(), Text.get("item:7t"), 30 ,new Vector(0,0,0));
		}
		return super.onAttack(e);
	}
	
	@Override
	public String getActionbar() {
		return "";
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 24) {
			Rule.c.get(player).setcooldown[1] *= 0.5;
			Rule.c.get(player).setcooldown[2] *= 0.5;
			Rule.c.get(player).setcooldown[3] = -10000;
			Rule.c.get(player).setcooldown[0] = -10000;
		}
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * 0.7);
		return super.onHit(e);
	}
}
