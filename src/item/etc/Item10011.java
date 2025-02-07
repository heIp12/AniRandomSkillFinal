package item.etc;

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
import buff.Airborne;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import item.list1.itemBase;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10011 extends itemBase{
	public Item10011(Player p){
		super(p);
		itemCode = 100011;
		setcooldown = 30;
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.giveBuff(player, new Stun(player), 5);
		ARSystem.giveBuff(player, new Silence(player), 5);
		ARSystem.spellCast(player, "item100011");
		for(Entity ey : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
			LivingEntity en = (LivingEntity)ey;
			delay(()->{
				en.setNoDamageTicks(0);
				en.damage(Math.max(15,en.getMaxHealth() * 0.15f),player);
				Rule.buffmanager.selectBuffAddValue(player, "barrier", 4);
			},10);
		}
		
		return false;
	}
	
	@Override
	public void kill(LivingEntity death, LivingEntity killer) {
		if(cooldown > 0) {
			cooldown -= 6;
		}
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1401);
		item = ItemCreate.Name(item, "§c§l【§e§l⸎§c§l】§a"+Text.get("item:"+itemCode));
		return item;
	}
}
