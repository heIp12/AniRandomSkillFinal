package item.craft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import ars.gui.G_HeddenSelect;
import ars.gui.G_Item;
import ars.gui.G_Supply;
import ars.gui.solo.G_ItemSelect;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
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

public class Item1011 extends itemBase{
	LivingEntity e;
	public Item1011(Player p){
		super(p);
		itemCode = 1011;
		setcooldown = 20;
	}
	
	@Override
	public boolean skillCast() {
		try {
		e = ARSystem.PlayerOnlyBeamBox(player, 20, 2, box.TARGET).get(0);
		} catch(Exception e) {
			cooldown = 0;
			return true;
		}
		if(e == null) {
			cooldown = 0;
			return true;
		} else {
			ARSystem.playSound((Entity)player, "item1011");
			ARSystem.spellCast(player, e, "item1011");
			delay(()->{
				ARSystem.spellCast(player, e, "item1011_2");
				Skill.death(e, player);
			},10);
		}
		return false;
	}
}