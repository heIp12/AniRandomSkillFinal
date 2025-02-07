package item.craft;

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
import buff.NoHeal;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.Holo;
import util.HoloMove;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item1003 extends itemBase{
	public Item1003(Player p){
		super(p);
		itemCode = 1003;
		setcooldown = 30;
	}
	
	@Override
	protected void onTick() {
		if(timer%15 == 0) {
			HoloMove.create(player.getLocation().add(5-AMath.random(20)*0.5,5-AMath.random(12)*0.5,5-AMath.random(20)*0.5),
				"§4§l"+Text.get("item:1003t"+AMath.random(10)),200,AMath.random(4),4);
		}
	}
	

	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() + 5);
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() - 5);
		return super.onHit(e);
	}
	
	@Override
	public boolean skillCast(){
		for(Entity en : ARSystem.box(player, new Vector(50,50,50), box.TARGET)){
			en.teleport(ULocal.offset(player.getLocation().clone(), new Vector(2,0,0)));
			ARSystem.giveBuff((LivingEntity)en, new Silence((LivingEntity)en), 100);
		}
		return false;
	}
}
