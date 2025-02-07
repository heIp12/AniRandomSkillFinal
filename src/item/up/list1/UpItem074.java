package item.up.list1;

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

public class UpItem074 extends upitemBase{
	float damage = 0;
	public UpItem074(Player p){
		super(p);
		itemCode = 74;
	}
	
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		double power = (1.2-player.getHealth()/player.getMaxHealth())*0.5;
		e.setDamage(e.getDamage() * (1+Math.min((1.2-player.getHealth()/player.getMaxHealth())*2,2f)));
		return super.onAttack(e);
	}
	
	@Override
	public String getActionbar() {
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		
		return "§c§l<§6"+n+" : §e" +AMath.round(Math.min((1.2-player.getHealth()/player.getMaxHealth())*200,200),1) +"%§c§l>";
	}
}
