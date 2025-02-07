package mode.lobodebuff;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import buff.Panic;
import mode.MLoboTomy;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class RB_db009 extends LoboBuffBase{
	int cooldown = 0;
	
	public RB_db009() {
		id = 9;
		debuff = true;
		cooldown = 10;
	}
	
	@Override
	public void onTime(int time) {
		cooldown--;
		if(cooldown <= 0 && AMath.random(30) <= 1) {
			ARSystem.playSoundAll("walllady");
			for(Player p : Rule.c.keySet()) {
				ARSystem.spellLocCast(NpcPlayer.npc(p.getLocation()), p.getLocation(), "walllady");
				ARSystem.giveBuff(p, new Panic(p), 80 * getlobo().level);
				ItemStack item = ItemCreate.Item(279, 194);
				p.getInventory().setItemInMainHand(item);
				delay(()->{
					p.getInventory().clear();
				},60);
			}
			
			delay(()->{
				MLoboTomy.debuff.remove(this);
				getlobo().text(393,getName() + Text.get("lobo:ot2"));
			},5);	
		}
	}
}
