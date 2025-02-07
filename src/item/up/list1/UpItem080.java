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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem080 extends upitemBase{
	public UpItem080(Player p){
		super(p);
		itemCode = 80;
		setcooldown = 8;
	}

	@Override
	public boolean skillCast(){
		ARSystem.potion(player, 14, 100, 0);
		return false;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			e.setDamage(e.getDamage()*0.5f);
		}
		return super.onHit(e);
	}
}
