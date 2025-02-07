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
import event.FixedDealEvent;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item1015 extends itemBase{
	public Item1015(Player p){
		super(p);
		itemCode = 1015;
	}

	@Override
	protected void onTick() {
		ARSystem.spellLocCast(player,player.getLocation(), "item1015e");
	}
	@Override
	public void fixedDamage(FixedDealEvent e) {
		if((e.getTarget() instanceof LivingEntity) && ((LivingEntity)e.getTarget()).getMaxHealth() >= 100) {
			if(e.getTarget() != player) e.setDamage(12345);
		} else {
			if(e.getTarget() != player) Skill.quit((LivingEntity)e.getTarget());
			if(e.getCaster() != player) Skill.quit((LivingEntity)e.getCaster());
		}
		e.setCancelled(true);
	}
	
	@Override
	public boolean onRemove(Entity caster) {
		if(ARSystem.isGameMode("lobotomy") && ((LivingEntity)caster).getMaxHealth() >= 100) {
			((LivingEntity)caster).setNoDamageTicks(0);
			((LivingEntity)caster).damage(999, player);
		} else {
			if(caster != player) Skill.quit((LivingEntity)caster);
		}
		return false;
	}
}
