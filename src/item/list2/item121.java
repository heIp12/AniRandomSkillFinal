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
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
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

public class item121 extends itemBase{
	int time = 0;
	
	public item121(Player p){
		super(p);
		itemCode = 121;
		setcooldown = 90;
		cooldown = 70;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number%1000 == 8) {
			setcooldown *= 0.5;
			cooldown *= 0.5;
		}
		super.onStart();
	}
	
	@Override
	public boolean skillCast() {
		if(Rule.c.get(player).number%1000 == 8) {
			ARSystem.giveBuff(player, new Silence(player), 60);
			ARSystem.giveBuff(player, new Stun(player), 60);
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			ARSystem.spellCast(player, "c1008_sp");
		} else {
			ARSystem.giveBuff(player, new Silence(player), 60);
			ARSystem.giveBuff(player, new Stun(player), 60);
			time = 140;
			ARSystem.spellCast(player, "c8_sp");
		}
		return false;
	}
	
	@Override
	protected void onTick() {
		if(time > 0) {
			time--;
		}
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(time > 0) {
			e.setDamage(e.getDamage() * 0.4);
		}
		return super.onAttack(e);
	}

	
	@Override
	public String getActionbar() {
		if(time <= 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		return "§c§l<§6"+n+" : §4§l" +AMath.round(time*0.05, 1) +"§c§l>";
	}
	
}
