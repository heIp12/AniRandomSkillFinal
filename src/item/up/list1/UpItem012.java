package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import util.AMath;
import util.ItemCreate;
import util.Text;

public class UpItem012 extends upitemBase{
	public UpItem012(Player p){
		super(p);
		itemCode = 12;
	}
	@Override
	public void onDeath(Player p, Entity e) {
		if(e == player) {
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			ARSystem.spellCast(player, "item12");
			ARSystem.playSound((Entity)player, "item12");
			ARSystem.potion(player, 1, 100, 4);
			delay(()->{
				ARSystem.potion(player, 14, 100, 4);
			},2);
			for(int i = 0; i<10; i++) Rule.c.get(player).cooldown[i] = 0;
		}
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number == 13 && AMath.random(10) <= 7) {
			for(int i =0; i<10; i++) if(Rule.c.get(player).cooldown[i] > 0) Rule.c.get(player).cooldown[i] -= 2;
			ARSystem.playSound((Entity)player, "0miss");
			e.setDamage(0);
			e.setCancelled(true);
		}
		return super.onHit(e);
	}
}
