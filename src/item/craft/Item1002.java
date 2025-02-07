package item.craft;

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
import buff.NoHeal;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item1002 extends itemBase{
	public Item1002(Player p){
		super(p);
		itemCode = 1002;
	}
	
	@Override
	protected void onTick() {
		ARSystem.spellLocCast(player,player.getLocation(), "item1002e");
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		LivingEntity en = (LivingEntity)e.getEntity();
		ARSystem.addBuff(en, new NoHeal(en), 100);
		e.setDamage(e.getDamage()*0.5);
		ARSystem.fixedDamage(en, player, e.getDamage());
		for(int i = 0; i< 60; i++) {
			delay(()->{
				ARSystem.fixedDamage(en, player, en.getMaxHealth()*0.001);
			},i);
		}
		delay(()->{
			if(en.getHealth()/en.getMaxHealth() <= 0.33) {
				ARSystem.playSound((Entity)player, "item1002");
				ARSystem.playSound(en, "item1002");
				ARSystem.giveBuff(en, new TimeStop(en), 100);
				ARSystem.spellCast(player, en, "item1002");
				delay(()->{
					ARSystem.playSound((Entity)en, "0boom2", 2);
					Skill.remove(en, player);
				},40);
			}
		},0);
		return super.onAttack(e);
	}

}
