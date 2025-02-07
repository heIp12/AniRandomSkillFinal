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

public class Item1008 extends itemBase{
	List<LivingEntity> en = new ArrayList<LivingEntity>();
	
	public Item1008(Player p){
		super(p);
		itemCode = 1008;
		setcooldown = 20;
	}
	
	@Override
	protected void onTick() {
		ARSystem.spellCast(player, "item1008e");
	}
	@Override
	public boolean skillCast() {
		ARSystem.playSound((Entity)player, "item1008");
		for(LivingEntity e : en) {
			ARSystem.playSound(e, "0boom3");
			ARSystem.playSound(e, "item1008");
			e.setNoDamageTicks(0);
			e.damage(15,player);
			ARSystem.spellCast(player, e, "item1008");
		}
		return false;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(!en.contains(e.getEntity())) {
			en.add((LivingEntity)e.getEntity());
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(player.getHealth() - e.getDamage() <= 1) {
			e.setDamage(0);
			e.setCancelled(true);
			ARSystem.giveBuff(player, new TimeStop(player), 200);
			delay(()->{
				ARSystem.giveBuff(player, new Nodamage(player), 0);
				ARSystem.giveBuff(player, new TimeStop(player), 0);
				ARSystem.spellCast(player, "item1008-2");
				delay(()->{
					for(Entity ee : ARSystem.box(player, new Vector(25,25,25), box.TARGET)) {
						LivingEntity en = (LivingEntity)ee;
						en.setNoDamageTicks(0);
						en.damage(999,player);
						ARSystem.spellCast(player, en, "item1008");
					}
					delay(()->{
						ARSystem.Death(player, player);
					},0);
				},1);
				ARSystem.playSoundAll("0boom4",0.8f);
			},150);
			ARSystem.playSoundAll("item1008b");
		}
		return super.onHit(e);
	}
}