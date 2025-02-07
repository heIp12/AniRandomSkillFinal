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
import chars.c.c29guren;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item008 extends itemBase{
	public Item008(Player p){
		super(p);
		itemCode = 8;
	}
	
	@Override
	protected void onTick() {
		if(Rule.c.get(player).number == 29) {
			((c29guren)Rule.c.get(player)).mana += 10;
		}
	}
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(Rule.buffmanager.GetBuffTime((LivingEntity)e.getEntity(), "silence") > 0) {
			e.setDamage(e.getDamage() * 1.3f);
		}
		return super.onAttack(e);
	}
}
