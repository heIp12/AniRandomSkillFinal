package item.list1;

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
import util.Text;
import util.ULocal;

public class Item083 extends itemBase{
	float damage = 0;
	int tick = 0;
	Location lc = player.getLocation();
	
	public Item083(Player p){
		super(p);
		itemCode = 83;
		setcooldown = 30;
	}
	
	@Override
	protected void onTick() {
		if(tick > 0) {
			player.teleport(lc);
			player.setVelocity(new Vector(0,0.01,0));
			
			tick--;
			if(tick == 0 && damage > 0) {
				if(!quest && damage >= 30) {
					questComplete();
					setcooldown = 10;
					cooldown = 10;
				}
				ARSystem.spellCast(player, "item83-2");
				for(Entity e : ARSystem.PlayerBeamBox(player, 12, 6, box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					if(quest) {
						en.damage(12 + damage*2,player);
						Rule.buffmanager.selectBuffAddValue(player, "barrier", 2.4f + damage*0.4f);
					} else {
						en.damage(12,player);
					}
				}
			}
		}
		super.onTick();
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(tick > 0) {
			if(damage == 0) {
				ARSystem.playSound(player, "item83b");
			}
			damage += e.getDamage();
			e.setDamage(0);
			e.setCancelled(true);
		}
		return super.onHit(e);
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(tick > 0) {
			e.setCancelled(true);
			return false;
		}
		if(e.getNewSlot() == 4 && player.isSneaking() && isCooldown()) {
			skillCast();
			return false;
		}
		return super.onSkill(e);
	}


	@Override
	public String getActionbar() {
		if(tick <= 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		
		return "§c§l<§6"+n+" : §4§l" +AMath.round(damage,1) +"§c§l>";
	}
	
	@Override
	public boolean skillCast(){
		if(tick > 0) return true;
		tick = 40;
		damage = 0;
		lc = player.getLocation();
		ARSystem.spellCast(player, "item83");
		ARSystem.playSound(player, "item83");
		return false;
	}
}
