package item.list1;

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
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item095 extends itemBase{
	int tick = 0;
	public Item095(Player p){
		super(p);
		itemCode = 95;
		setcooldown = 16;
	}
	
	
	@Override
	protected void onTick() {
		if(tick > 0 && !player.isSneaking()) {
			tick = 0;
			ARSystem.spellCast(player, "item95-2");
			ARSystem.playSound(player, "item95b");
		}
		super.onTick();
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.spellCast(player, "item95");
		ARSystem.playSound(player, "item95");
		tick = 120;
		return false;
	}
	
}
