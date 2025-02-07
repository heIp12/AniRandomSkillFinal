package item.list1;

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
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item037 extends itemBase{
	public Item037(Player p){
		super(p);
		itemCode = 37;
		setcooldown = 40;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(Rule.c.get(e.getEntity()) != null && Rule.c.get(player).number == 39) {
			LivingEntity en = (LivingEntity)e.getEntity();
			ARSystem.addBuff(en, new Stun(en), 2);
			ARSystem.addBuff(en, new Silence(en), 2);
			if(AMath.random(20) <= 3) {
				Location lc = en.getLocation();
				lc.setYaw(AMath.random(360));
				lc.setPitch(30-AMath.random(120));
				ARSystem.spellLocCast(player, lc, "c39_s3");
			}
		}
		return super.onAttack(e);
	}
	

	@Override
	public boolean skillCast(){
		ARSystem.spellCast(player, "item37");
		ARSystem.playSound((Entity)player ,"0timer",0.5f,2);
		for(Entity et : ARSystem.box(player, new Vector(10,8,10), box.TARGET)) {
			delay(()->{
				LivingEntity en = (LivingEntity)et;
				ARSystem.giveBuff(en, new TimeStop(en), 140);
			},10);
		}
		return false;
	}
}
