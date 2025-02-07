package item.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
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
import buff.Ice;
import buff.TimeStop;
import types.TargetMap;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item032 extends itemBase{
	TargetMap<LivingEntity, Double> tm;
	
	public Item032(Player p){
		super(p);
		itemCode = 32;
		tm = new TargetMap<>();
	}
	
	
	@Override
	protected void onTick() {
		for(LivingEntity e : tm.get().keySet()) {
			if(e.getHealth() <= tm.get(e)) {
				tm.removeAdd(e);
				ARSystem.playSound(e, "item32");
				ARSystem.giveBuff(e, new TimeStop(e), 80);
				ARSystem.spellCast(player, e, "item32");
				delay(()->{
					ARSystem.spellCast(player, e, "bload");
					e.setNoDamageTicks(0);
					e.damage(999,player);
				},81);
				if(Rule.c.get(e) != null) {
					double m = Rule.c.get(e).skillmult + Rule.c.get(e).sskillmult - 1;
					Rule.c.get(player).skillmult += m*0.5;
				}
			}
		}
		tm.removes();
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		LivingEntity en = (LivingEntity)e.getEntity();
		if(e.getDamage() < 200 && !en.isDead() && !(en instanceof Player && ((Player)en).getGameMode() == GameMode.SPECTATOR)) tm.add(en, e.getDamage()*0.5);
		return super.onAttack(e);
	}
}
