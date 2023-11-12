package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.Text;
import util.ULocal;

public class c39sakuya extends c00main{
	List<Location> loc = new ArrayList<>();
	int sk2 = 0;
	boolean s22 = false;
	Location s2;
	
	int s4 = 0;
	boolean sps = false;
	
	public c39sakuya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 39;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		if(!player.isSneaking() && !sps) {
			skill("c39_s1");
			ARSystem.playSound((Entity)player, "c39a"+(AMath.random(2)+1));
		} else {
			ARSystem.playSound((Entity)player, "0sword2",2);
			Location l = player.getLocation();
			for(Location lc : loc) {
				if(l.distance(lc) <= 0.1) {
					cooldown[1] = 0;
					return false;
				}
			}
			loc.add(player.getLocation());
			cooldown[1] *= 0.5f;
		}
		if(sps) cooldown[1] = 0;
		return true;
	}
	
	@Override
	public boolean skill2() {
		sk2 = 30;
		player.setGameMode(GameMode.SPECTATOR);
		ARSystem.giveBuff(player, new Silence(player), 20);
		s2 = player.getLocation();
		s22 = player.isSneaking();
		ARSystem.playSound((Entity)player, "c39s3");
		if(s22) {
			cooldown[2] *= 0.5;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(loc.size() <= 0) cooldown[3] = 0;
		ARSystem.playSound((Entity)player, "c39a");
		if(player.isSneaking()) {
			cooldown[3]*=2;
			for(Location l : loc) {
				ARSystem.spellLocCast(player, l, "c39_s32");
				delay(()->{l.getWorld().playSound(l, "0sword", 2, 0.1f);},AMath.random(5));
			}
		} else {
			for(Location l : loc) {
				ARSystem.spellLocCast(player, l, "c39_s3");
				delay(()->{l.getWorld().playSound(l, "0sword", 1.4f, 0.1f);},AMath.random(5));
			}
		}
		loc.clear();
		return true;
	}
	LivingEntity s4t;
	Location s4l;
	
	@Override
	public boolean skill4() {
		List<Entity> e = ARSystem.PlayerBeamBox(player, 10, 3, box.TARGET);
		if(e.size() > 0) {
			s4t = (LivingEntity)e.get(0);
			s4 = 200;
			s4l = s4t.getLocation();
			ARSystem.spellLocCast(player, s4l, "c39_s4e");
			ARSystem.playSound((Entity)player, "c39s4");
			if(player.isSneaking()) {
				s4 = 100;
				cooldown[4]*= 0.5;
			}
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {

		}
	}

	@Override
	public boolean tick() {
		if(sk2 > 0) {
			sk2--;
			if(sk2 <= 0) {
				player.teleport(s2);
				Entity e = ARSystem.boxSOne(player, new Vector(8,4,8), box.TARGET);
				if(e != null) {
					player.teleport(e);
					Location l = e.getLocation();
					l.setPitch(0);
					l.setYaw(0);
					for(int i = 0; i < 10; i++) {
						l.setYaw(i*36);
						Location loc = ULocal.lookAt(ULocal.offset(l, new Vector(2,-0.5 + i%2,0)),l);
						if(!s22) {
							ARSystem.spellLocCast(player, loc, "c39_s1");
						} else {
							this.loc.add(loc);
						}
					}
					ARSystem.giveBuff((LivingEntity)e, new Stun((LivingEntity)e), 10);
					ARSystem.giveBuff((LivingEntity)e, new Silence((LivingEntity)e), 10);
				}
				player.setGameMode(GameMode.ADVENTURE);
			}
		}
		if(s4 > 0) {
			String t = ""+AMath.round(s4*0.05,2);
			while(t.length() < 4) t+= "0";
			player.sendTitle(Text.get("c39:sk4"), t +"(s)",0,10,0);
			s4--;
			if(s4%20 == 0) ARSystem.playSound(player, "0timer");
			if(s4 <= 0) {
				s4t.teleport(s4l);
				skill("c39_s4r");
				s4t = null;
			}
		}
		int speed = 0;
		for(int i =0; i< 10; i++) if(cooldown[i] > 0) speed++;
		if(speed > 0) {
			ARSystem.potion(player, 1, speed, speed-1);
		}
		
		if(tk%10 == 0) {
			List<Entity> en = ARSystem.box(player, new Vector(5, 5, 5), box.TARGET);
			if(en.size() > 2 && skillCooldown(0)) {
				cooldown[3] = 0;
				spskillon();
				spskillen();
				ARSystem.playSound((Entity)player, "c39sp");
				skill("c39_sp");
				for(Entity e : en) {
					LivingEntity le = (LivingEntity)e;
					ARSystem.giveBuff(le, new TimeStop(le), 300);
					ARSystem.giveBuff(le, new Timeshock(le), 60);
				}
				for(int i = 0 ;i<10;i++) {
					delay(()->{
						ARSystem.playSound((Entity)player, "0timer");
					},30+i*30);
				}
				sps = true;
				delay(()->{
					sps = false;
				},300);
			}
		}
		return true;
	}



	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getEntity() == s4t) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		} else {
			if(e.getDamager() == s4t) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(e.getDamage() >= AMath.random(100)) {
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.giveBuff(player, new TimeStop(player), 20);
				return false;
			}
		}
		return true;
	}
}
