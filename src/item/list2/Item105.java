package item.list2;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import ars.ARSystem;
import ars.Rule;
import chars.ca.c2300madoka;
import item.list1.itemBase;
import types.box;
import util.AMath;
import util.Text;

public class Item105 extends itemBase{
	public Item105(Player p){
		super(p);
		itemCode = 105;
		setcooldown = 20;
	}


	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * 2);
		ARSystem.heal(player, e.getDamage() * 0.2);
		if(Rule.c.get(e.getEntity()) != null && isCooldown()) {
			cooldown = 0.5f;
			int i = AMath.random(6);
			int n = Rule.c.get(e.getEntity()).number;
			int j = 0;
			while(Text.get("c"+n+":sk"+i) == null) {
				i = AMath.random(6);
				j++;
				if(j > 100) break;
			}
			Rule.c.get(e.getEntity()).cooldown[i] += 1;
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean skillCast() {
		List<Player> tg = ARSystem.PlayerOnlyBeamBox(player, 20, 3, box.TARGET);
		if(tg.size() <= 0) {
			return true;
		} else {
			if(Rule.c.get(tg.get(0)) != null && Rule.c.get(tg.get(0)).number == 23) {
				ARSystem.playSoundAll("item105a");
				delay(()->{
					ARSystem.playSoundAll("item105b");
					Rule.c.put(tg.get(0), new c2300madoka(tg.get(0), Rule.gamerule, null));
				},70);
			} else {
				ARSystem.playSound((Entity)player, "item105");
				ARSystem.addItem(tg.get(0), 27);
				ARSystem.playerItem.get(tg.get(0)).items.get(ARSystem.playerItem.get(tg.get(0)).items.size()-1).timer = 1000;
			}
		}
		return false;
	}
}
