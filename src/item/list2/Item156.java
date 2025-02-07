package item.list2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
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
import ars.gui.G_HeddenSelect;
import ars.gui.G_Item;
import ars.gui.G_Supply;
import ars.gui.solo.G_ItemSelect;
import buff.Buff;
import buff.Dancing;
import buff.Medusa;
import buff.NoCC;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
import types.TargetMap;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item156 extends itemBase{
	Entity target;
	
	public Item156(Player p){
		super(p);
		itemCode = 156;
	}
	
	
	@Override
	protected void onTick() {
		if(target != null) {

			if(Rule.c.get(player).isBattleTime() > 100 || target.isDead() || ((target instanceof Player) && ((Player) target).getGameMode() == GameMode.SPECTATOR)) {
				target = null;
				Rule.c.get(player).skillmult -=1;
			} else if(!isLookingAt(ULocal.lookAt(target.getLocation().clone(),player.getLocation()),player.getLocation(), 100)) {
				ARSystem.playSound(player, "item156");
				Rule.c.get(player).skillmult -=1;
				target = null;
				cooldown = 8;
			}
		}
	}
	
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(target != null) {
			e.setDamage(e.getDamage()*0.4);
		}
		return super.onHit(e);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(target == null && cooldown <= 0) {
			Rule.c.get(player).skillmult +=1;
		}
		if(cooldown <= 0) target = e.getEntity();
		return super.onAttack(e);
	}
	
	@Override
	public void itemRemove() {
		if(target != null) {
			Rule.c.get(player).skillmult -=1;
		}
	}
	
	@Override
	public String getActionbar() {
		if(target == null) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		return "§c§l<§e"+Text.get("item:156")+"§c§l>";
	}
	
    public static boolean isLookingAt(Location viewer, Location target, float size) {
        Location myLoc = target;
        float myYaw = normalizeYaw(myLoc.getYaw());
        
        float backYaw = normalizeYaw(myYaw + 180);
        
        float viewerYaw = normalizeYaw(viewer.getYaw());

        float yawDifference = Math.abs(normalizeYaw(viewerYaw - backYaw));
        
        return yawDifference <= size;
    }
    
    private static float normalizeYaw(float yaw) {
        yaw %= 360;
        if (yaw > 180) {
            yaw -= 360;
        } else if (yaw < -180) {
            yaw += 360;
        }
        return yaw;
    }
	
}