package chars.ca;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Lvup;
import buff.Buff;
import buff.Curse;
import buff.Exposure;
import buff.Fascination;
import buff.NoHeal;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.Wound;
import chars.c.c00main;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.ItemCreate;
import util.MSUtil;
import util.Map;
import util.Text;
import util.ULocal;

public class c1134siro extends c00main{
	boolean start = false;
	List<LivingEntity> mobs = new ArrayList<LivingEntity>();
	int p = 0;
	
	public c1134siro(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1134;
		load();
		text();
		c = this;
		delay(()->{
			ARSystem.playSoundAll("c1134p");
			for(int i = 0;i <30; i++) {
				Location l = Map.randomLoc();
				l.setY(Map.loc_l.getY()-3);
				int o = 0;
				while(!Map.inMap(l)) {
					l = Map.randomLoc();
					l.setY(Map.loc_l.getY()-3);
					o++;
					if(o > 300) {
						l = player.getLocation();
						break;
					}
				}
				mobs.add((LivingEntity)Map.spawnMMOwner("c134_1", l, player.getUniqueId()));
			}
		},100);
		skill("c1134p");
		skill("c1134p_foot");
		this.p = 100;
	}
	
	@Override
	public boolean skill1() {
		for(LivingEntity e : mobs) {
			ARSystem.heal(e, 8);
			if(AMath.random(10) <= 3) {
				ARSystem.giveBuff(e, new Fascination(e,ARSystem.RandomPlayer(player)), 60, 0.5);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(player.getPassenger() != null) player.removePassenger(player.getPassenger());
		int range = 12;
		player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(range)).add(0,0.5,0));
		ARSystem.playSound((Entity)player, "c1134s2");
		return true;
	}

	@Override
	public boolean skill3() {
		List<Entity> en = ARSystem.PlayerBeamBox(player, 20, 5, box.TARGET);
		if(en.size() > 0) {
			skill("c1134_s3");
			ARSystem.playSound((Entity)player, "c1134s3");
			LivingEntity target = (LivingEntity) en.toArray()[0];
			ARSystem.giveBuff(target, new Stun(target), 60);
			ARSystem.giveBuff(target, new Silence(target), 200);
			ARSystem.giveBuff(target, new Panic(target), 160);
			ARSystem.giveBuff(target, new Fascination(target,player), 120, 0.2f);
			ARSystem.giveBuff(target, new Exposure(target), 200, 3);
		} else {
			cooldown[3] = 0;
		}
				
		return true;
	}
	
	@Override
	public boolean firsttick() {
		if(start) {
			for(Buff buff : Rule.buffmanager.getBuffs(player).getBuff()) {
				buff.stop();
			}
		}
		return false;
	}
	
	public boolean tick() {
		if(player.isSneaking()) {
			if(!BlockUtil.isPathable(ULocal.offset(player.getLocation(), new Vector(1,0,0)).getBlock())){
				player.setVelocity(new Vector(0,0.5,0));
			}
			if(!BlockUtil.isPathable(ULocal.offset(player.getLocation(), new Vector(0,3,0)).getBlock())|| !BlockUtil.isPathable(ULocal.offset(player.getLocation(), new Vector(0,2,0)).getBlock())){
				Location l = player.getLocation();
				l.setPitch(0);
				player.setVelocity(ULocal.offset(l, new Vector(0.6,1,0)).getDirection());
			}
		}
		if(tk% 10 == 0) {
			for(LivingEntity e : mobs) {
				if(!Map.inMap(e.getLocation())) {
					e.teleport(Map.randomLoc());
				}
			}
		}
		if(p <= 0 && mobs.size() < 30) {
			Location l = Map.randomLoc();
			l.setY(Map.loc_l.getY()-3);
			while(!Map.inMap(l)) {
				l = Map.randomLoc();
				l.setY(Map.loc_l.getY()-3);
			}
			mobs.add((LivingEntity)Map.spawnMMOwner("c134_1", l, player.getUniqueId()));
		}
		if(!start) {
			start = true;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&e[LV ] &f999");
		}
		if(!MSUtil.isbuff(player, "c1134p")) {
			skill("c1134p");
			skill("c1134p_foot");
		}
		player.setFallDistance(0);
		if(p > 0) p--;
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player) {
			ARSystem.heal(player, 2000);
		}
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			
		} else {
			
		}
		return true;
	}
}
