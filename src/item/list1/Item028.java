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

import ars.ARSystem;
import ars.Rule;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;

public class Item028 extends itemBase{
	public Item028(Player p){
		super(p);
		itemCode = 28;
		setcooldown = 35;
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.giveBuff(player, new Stun(player), 15);
		ARSystem.giveBuff(player, new Silence(player), 15);
		ARSystem.spellCast(player, "item28");
		for(Entity ey : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
			LivingEntity en = (LivingEntity)ey;
			ARSystem.giveBuff(en, new Stun(en), 15);
			ARSystem.giveBuff(en, new Silence(en), 15);
			delay(()->{
				if(en instanceof Player) {
					ARSystem.spellCast((Player)en, "item28e");
				}
				en.setNoDamageTicks(0);
				en.damage(10,player);
				ARSystem.heal(player, 6);
				ARSystem.giveBuff(en, new PowerUp(en), 60, -0.5f);
			},10);
		}
		return false;
	}
}
