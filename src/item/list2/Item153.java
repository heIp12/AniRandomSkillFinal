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

public class Item153 extends itemBase{
	
	public Item153(Player p){
		super(p);
		itemCode = 153;
		setcooldown = 5;
	}
	
	@Override
	public boolean skillCast() {
		ARSystem.playSound((Entity)player, "item153");
		delay(()->{
			if(AMath.random(0,100) <= 3) {
				ARSystem.playSoundAll("item153b");
				ARSystem.heal(player, 1000);
				ARSystem.giveBuff(player, new Nodamage(player), 200);
				ARSystem.giveBuff(player, new PowerUp(player), 200, 2);
				ARSystem.giveBuff(player, new NoCC(player), 200);
				for(int i = 0;i<10;i++) Rule.c.get(player).cooldown[i] = 0;
			} else {
				ARSystem.playSound((Entity)player, "item153a");
			}
		},20);
		return false;
	}
	
}