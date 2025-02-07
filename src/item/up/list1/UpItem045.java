package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import buff.Ice;
import buff.NoHeal;
import buff.TimeStop;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem045 extends upitemBase{
	Entity en = null;
	int timer = 0;
	boolean on = false;
	
	public UpItem045(Player p){
		super(p);
		itemCode =45;
	}

	@Override
	protected void onTick() {
		if(on) {
			if(en.isDead() || (en instanceof Player && ((Player) en).getGameMode() != GameMode.ADVENTURE)) {
				on = false;
				timer = 0;
				en = null;
			} else {
				ARSystem.spellCast(player, en, "item45-2");
				if(en.getLocation().distance(player.getLocation()) >= 5) {
					Location lc = ULocal.lookAt(player.getLocation().clone(), en.getLocation());
					lc = lc.add(lc.getDirection().multiply(4.6));
					lc.setYaw(en.getLocation().getYaw());
					lc.setPitch(en.getLocation().getPitch());
					en.teleport(lc);
					ARSystem.spellCast(player, en, "item45-2");
				}
			}
			return;
		}
		Entity e = ARSystem.boxSOne(player, new Vector(5,5,5), box.ALL);
		if(e != en) {
			en = e;
			timer = 0;
		} else if(en != null){
			ARSystem.spellCast(player, en, "item45");
			timer++;
			if(timer >= 40) {
				Rule.buffmanager.selectBuffAddValue(player, "barrier", 10);
				ARSystem.playSound((Entity)player, "item45");
				on = true;
			}
		}
		super.onTick();
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() == en) {
			e.setDamage(e.getDamage() * 0.2f);
		}
		return super.onHit(e);
	}
}
