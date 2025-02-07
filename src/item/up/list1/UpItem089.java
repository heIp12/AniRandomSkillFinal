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

public class UpItem089 extends upitemBase{
	int tick = 0;
	public UpItem089(Player p){
		super(p);
		itemCode = 89;
		setcooldown = 60;
	}
	
	@Override
	protected void onTick() {
		if(tick > 0) tick--;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(tick > 0) {
			ARSystem.heal(player, e.getDamage()*10);
		}
		return super.onAttack(e);
	}
	

	@Override
	public boolean skillCast(){
		ARSystem.spellCast(player, "item89");
		ARSystem.playSound((Entity)player, "item89");
		tick = 200;
		return false;
	}
}
