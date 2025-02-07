package item.list1;

import java.util.ArrayList;
import java.util.List;

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
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class Item073 extends itemBase{
	float damage = 0;
	public Item073(Player p){
		super(p);
		itemCode = 73;
		setcooldown = 0.5f;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(damage > 0.5 && isCooldown()) {
			LivingEntity en = (LivingEntity)e.getEntity();
			en.setNoDamageTicks(0);
			en.damage(damage,player);
			damage = 0;
		}
		return super.onAttack(e);
	}
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		damage += e.getDamage() * 0.3;
		if(damage > 5) damage = 5;
		return super.onHit(e);
	}
	
	@Override
	public String getActionbar() {
		if(cooldown > 0) {
			return super.getActionbar();
		}
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		
		return "§c§l<§6"+n+" : §e" +AMath.round(damage,1) +"§c§l>";
	}
}
