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
import chars.c3.c133yukina;
import chars.ca.c2400sinobu;
import chars.ca.c6700akad;
import chars.ca.c7202plan;
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

public class Item1009 extends itemBase{
	public Item1009(Player p){
		super(p);
		itemCode = 1009;
		setcooldown = 10;
	}
	
	@Override
	protected void onTick() {
		ARSystem.spellCast(player, "item1009e");
		for(Player p : Rule.c.keySet()) {
			if(Rule.c.get(p) != null && Rule.c.get(p).number == 133) {
				((c133yukina)Rule.c.get(p)).onSp();
			}
		}
		
		if(Rule.c.get(player) != null) {
			int i = Rule.c.get(player).number;
			if(i%1000 == 72) {
				Rule.c.put(player, new c7202plan(player, Rule.gamerule, Rule.c.get(player)));
			} else if(i%1000 == 67) {
				Rule.c.put(player, new c6700akad(player, Rule.gamerule, Rule.c.get(player)));
			} else if(i%1000 == 24) {
				Rule.c.put(player, new c2400sinobu(player, Rule.gamerule, Rule.c.get(player)));
				Rule.c.get(player).hp += 20;
				player.setMaxHealth(Rule.c.get(player).hp);
				player.setHealth(Rule.c.get(player).hp);
				Rule.c.get(player).frist_defence *= 0.5;
			} else {
				return;
			}
			ARSystem.playerItem.get(player).remove(this);
		}
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(Rule.buffmanager.GetBuffValue(player, "plushp") > e.getDamage()) {
			Rule.buffmanager.selectBuff(player, "plushp").addValue(-e.getDamage());
			ARSystem.spellLocCast(player, player.getLocation(), "item1009");
			e.setDamage(0);
			return false;
		} else if(Rule.buffmanager.GetBuffValue(player, "plushp") > 0) {
			e.setDamage(e.getDamage() - Rule.buffmanager.GetBuffValue(player, "plushp"));
			Rule.buffmanager.selectBuff(player, "plushp").setValue(0);
			ARSystem.spellLocCast(player, player.getLocation(), "item1009");
		}
		return super.onHit(e);
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		ARSystem.overheal(player, e.getDamage()*1.5);
		return super.onAttack(e);
	}	
	@Override
	public boolean skillCast() {
		for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			en.setNoDamageTicks(0);
			en.damage(en.getMaxHealth()*0.2 + Rule.buffmanager.GetBuffValue(player, "plushp"),player);
			ARSystem.spellCast(player, en, "bload");
		}
		return false;
	}
}