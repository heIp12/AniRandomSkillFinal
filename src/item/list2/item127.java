package item.list2;

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
import buff.Buff;
import buff.Curse;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import event.Skill;
import event.WinEvent;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class item127 extends itemBase{
	public item127(Player p){
		super(p);
		itemCode = 127;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		double v = player.getHealth()/player.getMaxHealth();
		if(v < 0.4) v = 0.4;
		e.setDamage(e.getDamage() * v);
		ARSystem.heal(player, 1);
		return super.onHit(e);
	}
	
	@Override
	protected void onTick() {
		if(Rule.buffmanager.isBuff(player, "wound")) {
			Wound wd = (Wound)Rule.buffmanager.selectBuff(player, "wound");
			for(int i = 0; i< wd.getTime()/wd.getDelay();i++) {
				delay(()->{
					ARSystem.heal(player, wd.getValue());
				},i*wd.getDelay());
			}
			Rule.buffmanager.selectBuffTime(player, "wound", 0);
			Rule.buffmanager.selectBuffValue(player, "wound", 0);
		}
		if(Rule.buffmanager.isBuff(player, "curse")) {
			double h = player.getMaxHealth() - player.getHealth();
			ARSystem.heal(player, h * Rule.buffmanager.GetBuffValue(player, "curse"));
			Rule.buffmanager.selectBuffTime(player, "curse", 0);
			Rule.buffmanager.selectBuffValue(player, "curse", 0);
			
		}
	}

}
