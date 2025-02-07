package item.craft;

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
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item1007 extends itemBase{
	public Item1007(Player p){
		super(p);
		itemCode = 1007;
	}
	
	@Override
	protected void onStart() {
		ARSystem.playSound(player, "item1007");
		Rule.c.put(player, GetChar.get(player, Rule.gamerule, "boy"));
	}
	
	@Override
	protected void onTick() {
		ARSystem.spellCast(player, "item1007");
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage()*1.5);
		return super.onAttack(e);
	}
}