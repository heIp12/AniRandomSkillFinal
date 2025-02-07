package item.list1;

import java.util.ArrayList;
import java.util.List;

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
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.TimeStop;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item060 extends itemBase{
	Entity e;
	int tick = 0;
	int tick2 = 0;
	
	public Item060(Player p){
		super(p);
		itemCode = 60;
		setcooldown = 10;
	}
	
	@Override
	protected void onTick() {
		if(!quest && tick > 0) {
			tick--;
			if(e != null && (e.isDead() || (e instanceof Player && ((Player)e).getGameMode() != GameMode.ADVENTURE))) {
				questComplete();
			}
			if(tick <= 0) e = null;
		}
		if(quest && timer%2 == 0 && tick2 <= 0) {
			List<Entity> el = ARSystem.PlayerBeamBox(player, 50, 1, box.ALL);
			for(Entity e : el) {
				List<Entity> el2 = ARSystem.PlayerBeamBox(e, 50, 1, box.ALL);
				for(Entity en : el2) {
					if(en == player) {
						tick2 = 80;
						ARSystem.playSound(en, "minecraft:entity.endermen.stare");
						ARSystem.giveBuff(player, new PowerUp(player), 100, 1);
					}
				}
			}
		}
		if(tick2 > 0) tick2--;
	}
	
	@Override
	public boolean skillCast(){
		List<Entity> en = ARSystem.PlayerBeamBox(player, 50, 2, box.ALL);
		if(en.size() <= 0) return true;
		this.e = en.get(0);
		Location lc = this.e.getLocation().clone();
		Location lc2 = player.getLocation().clone();
		lc2.setPitch(lc.getPitch());
		lc2.setYaw(lc.getYaw());
		lc.setPitch(player.getLocation().getPitch());
		lc.setYaw(player.getLocation().getYaw());
		player.teleport(lc);
		this.e.teleport(lc2);
		tick = 20;
		ARSystem.spellLocCast(player,player.getLocation(), "item60");
		ARSystem.spellCast(player, this.e, "item60");
		ARSystem.playSound(this.e, "minecraft:entity.endermen.teleport");
		ARSystem.playSound((Entity)player, "minecraft:entity.endermen.teleport");
		return false;
	}
}
