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

import Main.Main;
import ars.Rule;
import event.Skill;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class UpItem007 extends upitemBase{
	public UpItem007(Player p){
		super(p);
		itemCode = 7;
		setcooldown = 3;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		Entity en = e.getEntity();
		if(Rule.c.get(en) != null && Main.GetText("c"+Rule.c.get(e.getEntity()).getCode()+":tag").indexOf("tg4") != -1) {
			e.setDamage(e.getDamage() * 0.6);
		} else {
			if(en instanceof Player) {
				e.setDamage(e.getDamage() * 5);
			} else {
				e.setDamage(e.getDamage() * 1.8);
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
			Rule.c.get(player).setcooldown[1] *= 0.9;
			Rule.c.get(player).setcooldown[2] *= 0.9;
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
