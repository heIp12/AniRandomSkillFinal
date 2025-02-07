package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import buff.Boom;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import chars.c.c01minato;
import types.box;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item003 extends itemBase{
	public Item003(Player p){
		super(p);
		itemCode = 3;
		setcooldown = 15;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 1) {
			Rule.c.get(player).hp += 4;
			player.setMaxHealth(Rule.c.get(player).hp);
			ARSystem.heal(player, 4);
			setcooldown = 1;
		}
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number != 1 && isCooldown()) {
			delay(()->{
				LivingEntity en = (LivingEntity)e.getEntity();
				en.damage(4 , player);
				ARSystem.spellCast(player, en, "item3");
				ARSystem.playSound(en, "0boom3",1,0.5f);
			},60);
		}
		return true;
	}
	
	@Override
	public boolean skillCast() {
		if(Rule.c.get(player).number == 1 && ((c01minato)Rule.c.get(player)).na > 0) {
			ARSystem.playSound((Entity)player, "c1n2");
			if(((c01minato)Rule.c.get(player)).na  > 140) {
				ARSystem.spellCast(player, "item3e1");
			} else {
				ARSystem.spellCast(player, "item3e2");
			}
			Rule.c.get(player).cooldown[1] = Rule.c.get(player).setcooldown[1];
			((c01minato)Rule.c.get(player)).na = 0;
			return false;
		}
		return true;
	}
}
