package item.up.list1;

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
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Text;
import util.ULocal;

public class UpItem076 extends upitemBase{
	int time = 0;
	public UpItem076(Player p){
		super(p);
		itemCode = 76;
		setcooldown = 30;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(time > 0) {
			time = 100;
			if(Rule.c.get(player).number != 23) {
				if(Rule.c.get(player).number == 151) {
					ARSystem.giveBuff(player, new Panic(player), 40);
				} else {
					ARSystem.giveBuff(player, new Panic(player), 200);
				}
			}
		}
		return super.onAttack(e);
	}
	
	@Override
	protected void onTick() {
		if(time > 0) {
			time--;
			if(time <= 0) {
				Rule.c.get(player).skillmult -= 3;
			}
		}
	}
	

	@Override
	public void itemRemove() {
		if(time > 0) {
			Rule.c.get(player).skillmult -= 3;
		}
		super.itemRemove();
	}
	
	@Override
	public String getActionbar() {
		if(time <= 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		
		return "§c§l<§6"+n+" : §4§l" +AMath.round(time*0.05,1) +"§c§l>";
	}
	
	@Override
	public boolean skillCast(){
		time = 200;
		Rule.c.get(player).skillmult += 3;
		if(Rule.c.get(player).number != 23) {
			ARSystem.giveBuff(player, new Panic(player), 200);
		}
		ARSystem.playSound((Entity)player, "minecraft:entity.enderdragon.growl",0.8f);
		return false;
	}
}
