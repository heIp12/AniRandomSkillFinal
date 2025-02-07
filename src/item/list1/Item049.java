package item.list1;

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
import buff.Nodamage;
import buff.TimeStop;
import types.ItemList;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item049 extends itemBase{
	int type = 0;
	Player target;
	Location loc;
	float damage = 0;
	int i = 0;
	public Item049(Player p){
		super(p);
		itemCode = 49;
		if(p != null && p != NpcPlayer.player) {
			delay(()->{
				setType(p);
			},AMath.random(10)*20 + 40);
		}
	}
	
	void setType(Player p) {
		type = AMath.random(4);
		if(AMath.random(10) <= (i*2)-1) type = 5;
		damage = 0;
		loc = player.getLocation();
		while((type == 1 || type==4) && Rule.c.size() < 2) {
			type = AMath.random(4);
			if(AMath.random(20) <= (i*2)-1) type = 5;
		}
		
		String t = "";
		
		if(type == 1 || type == 4) {
			target = ARSystem.RandomPlayer(p);
			t+= target.getName();
		}
		
		if(type == 3) {
			loc = Map.randomLoc();
			t += AMath.round(loc.getX(),1)+" , "+AMath.round(loc.getZ(),1);
		}
		quest = false;
		i++;
		if(p != null) {
			p.sendTitle(Text.get("item:49"), t + " " + Text.get("item:49t"+type),60,80,20);
			p.sendMessage(Text.get("item:49")+" : "+ t + " " + Text.get("item:49t"+type));
		}
	}
	
	@Override
	protected void onTick() {
		if(type == 3) {
			if(!Map.inMap(loc)) loc = Map.randomLoc();
			Location lc = loc.clone();
			lc.setY(0);
			Location pl = player.getLocation().clone();
			pl.setY(0);
			if(pl.distance(lc) <= 1) comp();
		}
		if(type == 2) {
			if(loc.distance(player.getLocation()) > 1) {
				loc = player.getLocation();
				damage = 0;
			}
			damage += 0.05;
			if(damage >= 30) {
				comp();
			}
			
		}
		super.onTick();
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(type == 4 && e.getDamager() == target) {
			damage += e.getDamage();
			if(damage >= 8) {
				comp();
			}
		}
		return super.onHit(e);
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(type == 1) {
			if(p == target) {
				if(e == player) {
					comp();
				} else {
					
				}
			}
		}
	}
	
	@Override
	public String getActionbar() {
		if(type == 1) {
			return "§c§l<§6"+Text.get("item:49t11")+" : §a" + target.getName() +"§c§l>";
		} else if(type == 2) {
			return "§c§l<§6"+Text.get("item:49t21")+" : §a" +AMath.round(30.0-damage,1) +"§c§l>";
		} else if(type == 3) {
			return "§c§l<§6"+Text.get("item:49t31")+" : §a" +AMath.round(loc.getX(),1)+","+AMath.round(loc.getZ(),1) +"§c§l>";
		} else if(type == 4) {
			return "§c§l<§6"+Text.get("item:49t41")+" : §a" +AMath.round(8-damage,1) +"§c§l>";
		} else if(type == 5) {
			return "§c§l<§6"+Text.get("item:49t51")+"§c§l>";
		}
		return super.getActionbar();
	}

	void comp() {
		type = 0;
		questComplete();
		ARSystem.addItem(player, ItemList.getValueCode(0, 2500));
		delay(()->{
			if(Rule.c.get(player) != null) {
				setType(player);
			}
		},100);
	}
}
