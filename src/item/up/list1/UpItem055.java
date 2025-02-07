package item.up.list1;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import chars.c.c20kurumi;
import types.box;
import util.Pair;

public class UpItem055 extends upitemBase{
	boolean istarget = true;
	Queue<Pair<Location, Double>> locals = new LinkedList<>();
	Queue<Pair<Location, Double>> back = new LinkedList<>();
	
	public UpItem055(Player p){
		super(p);
		itemCode = 55;
		setcooldown = 10;
	}
	
	@Override
	public void tick() {
		locals.add(new Pair<Location,Double>(player.getLocation(),player.getHealth()));
		if(locals.size() > 100) locals.poll();
		while(back.size() > 0) {
			Pair<Location,Double> pr = back.poll();
			delay(()->{
				player.teleport(pr.getKey());
				player.setHealth(pr.getValue());
				if(Rule.c.get(player) != null && Rule.c.get(player).number == 20) {
					ARSystem.giveBuff(player, new Nodamage(player), 4);
				}
			},back.size());
		}
		super.tick();
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 20) {
			for(int i =0; i<10; i++) Rule.c.get(player).setcooldown[i] *= 0.5;
			setcooldown = 4;
		}
	}
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		if(Rule.c.get(player).number == 20) {
			((c20kurumi)Rule.c.get(player)).shdow += e.getDamage();
		}
		return super.onAttack(e);
	}
	
	@Override
	public boolean skillCast(){
		back.clear();
		back.addAll(locals);
		locals.clear();
		ARSystem.spellCast(player, "item55");
		ARSystem.playSound((Entity)player, "c20shot");
		return false;
	}
}
