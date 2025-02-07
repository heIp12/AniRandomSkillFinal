package item.list2;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class item131 extends itemBase{
	Player tg;
	public item131(Player p){
		super(p);
		itemCode = 131;
	}
	@Override
	public boolean skillCast() {
		List<Player> list = ARSystem.PlayerOnlyBeamBox(player, 15, 4, box.TARGET);
		if(list.size() <= 0) return true;
		tg = list.get(0);
		player.sendTitle(""+tg.getName(), "" , 0, 20, 0);
		return false;
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(tg == p) {
			ARSystem.playerItem.get(player).removes.add(this);
		}
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(tg != null && e.getDamager() != tg) {
			e.setDamage(0);
			e.setCancelled(true);
		}
		return super.onHit(e);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(tg != null && (e.getEntity() == tg)) {
			e.setDamage(e.getDamage()*1.5f);
		}
		return super.onAttack(e);
	}
	
	@Override
	protected void onTick() {
		if(timer%40 == 0) {
			ARSystem.spellCast(player, "item131");
		}
		super.onTick();
	}
}
