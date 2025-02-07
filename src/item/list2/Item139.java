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
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.FixedDealEvent;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
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

public class Item139 extends itemBase{
	int damage = 0;
	public Item139(Player p){
		super(p);
		itemCode = 139;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		damage += e.getDamage();
		if(damage >= player.getMaxHealth()*0.5) {
			e.setDamage(e.getDamage() * 0.2);
			damage = 0;
			ARSystem.giveBuff(player, new Nodamage(player), 60);
			ARSystem.giveBuff(player, new Noattack(player), 60);
			ARSystem.giveBuff(player, new Silence(player), 60);
			ARSystem.playSound((Entity)player, "item139");
			for(Entity ey : ARSystem.box(player, new Vector(8,8,8), box.ALL)){
				LivingEntity en = (LivingEntity)ey;
				ARSystem.giveBuff(en, new Nodamage(en), 60);
				ARSystem.giveBuff(en, new Noattack(en), 60);
				ARSystem.giveBuff(en, new Silence(en), 60);
				en.setVelocity(ULocal.lookAt(player.getLocation().clone(), en.getLocation()).getDirection().multiply(5));
			}
		}
		return super.onHit(e);
	}
	
	@Override
	public String getActionbar() {
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		
		return "§c§l<§6"+n+" : §e" + AMath.round(damage,2) + " / " + AMath.round(player.getMaxHealth()/2,2) + "§c§l>";
	}
}