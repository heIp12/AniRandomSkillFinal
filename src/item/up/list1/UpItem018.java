package item.up.list1;

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
import buff.Nodamage;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem018 extends upitemBase{
	public UpItem018(Player p){
		super(p);
		itemCode = 18;
		setcooldown = 10;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 83) {
			setcooldown = 5;
		}
		super.onStart();
	}
	
	@Override
	protected void onTick() {
		if(timer%10 == 0) {
			Entity en = ARSystem.boxSOne(player, new Vector(3,2,3),box.TARGET);
			if(en != null && isCooldown()) {
				ARSystem.spellCast(player, "item18");
				((LivingEntity)en).damage(8,player);
				Location l = ULocal.lookAt(player.getLocation(), en.getLocation());
				l.setPitch(0);
				ARSystem.giveBuff(player, new Nodamage(player), 40);
				for(int i=0; i<20; i++) {
					delay(()->{
						en.teleport(en.getLocation().clone().add(l.getDirection().multiply(0.5)));
					},i);
				}
			}
		}
	}
	
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(e.getNewSlot() == 5 && player.isSneaking() && isCooldown() && Rule.c.get(player).number == 83) {
			player.setSneaking(false);
			ARSystem.spellCast(player, "c83_m");
			ARSystem.giveBuff(player, new Nodamage(player), 40);
			for(int i =0;i<8;i++) ARSystem.spellLocCast(player, ULocal.offset(player.getLocation().clone(), new Vector((AMath.random(30)-15)*0.3,0,(AMath.random(30)-15)*0.3)), "c83_s1-1");
			return false;
		}
		return true;
	}
}
