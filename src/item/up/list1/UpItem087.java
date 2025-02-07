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

public class UpItem087 extends upitemBase{
	public UpItem087(Player p){
		super(p);
		itemCode = 87;
		setcooldown = 2;
	}
	@Override
	public boolean skillCast(){
		ARSystem.playSound((Entity)player, "item87");
		ARSystem.spellCast(player, player, "item87");
		double hp = player.getMaxHealth()-player.getHealth();
		ARSystem.heal(player, hp);
		for(int i =0; i< 5; i++) {
			Rule.c.get(player).delay(()->{
				Rule.c.get(player).hpCost(hp*0.19f, true);
			}, i*80);
		}
		return false;
	}
}
