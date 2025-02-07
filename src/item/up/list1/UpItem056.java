package item.up.list1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.TimeStop;
import chars.c.c16saki;
import chars.c2.c81saitama;
import chars.ca.c8100saitama;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Pair;
import util.Text;
import util.ULocal;

public class UpItem056 extends upitemBase{
	double hp = 0;
	public UpItem056(Player p){
		super(p);
		itemCode = 56;
		setcooldown = 50;
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 56) {
			((c81saitama)Rule.c.get(player)).sp -= 4000;
		}
	}
	
	@Override
	public void tick() {
		for(int i =0; i<10; i++) if(Rule.c.get(player).cooldown[i] > 0) timer = 0;
		if(!quest && timer > 600) {
			Rule.c.get(player).skillmult+=0.5;
			hp =player.getMaxHealth();
			player.setMaxHealth(player.getMaxHealth()* 2);
			ARSystem.heal(player, player.getMaxHealth()*0.35);
			questComplete();
		}
		super.tick();
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(quest) {
			e.setDamage(e.getDamage()* 1.3f);
		}
		return super.onAttack(e);
	}
	@Override
	public String getActionbar() {
		if(timer > 600) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		return "§c§l<§6"+n+" : §4§l" +AMath.round((600-timer)*0.05, 1) +"§c§l>";
	}
	
	@Override
	public void itemRemove() {
		player.setHealth(Math.max(1,player.getHealth() - hp));
		player.setMaxHealth(player.getMaxHealth() - hp);
		Rule.c.get(player).hp -= hp;
	}
}
