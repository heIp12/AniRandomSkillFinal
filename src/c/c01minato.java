package c;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import types.box;
import types.modes;
import util.AMath;
import util.Local;
import util.MSUtil;
import util.MagicSpellVar;

public class c01minato extends c00main{
	private Location[] lc = new Location[3];
	private Entity[] et = new Entity[3];
	private Location loc = player.getLocation();
	int na = 0;
	float cm = 0;
	
	int stack = 0;
	
	public c01minato(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1;
		load();
		text();
		for(int i = 1; i<4;i++) {
			player.performCommand("as create "+p.getName()+"c1_"+i);
			player.performCommand("as set "+p.getName()+"c1_"+i);
			player.performCommand("as item "+p.getName()+"c1_"+i+" 279:1");
			player.performCommand("as group "+p.getName()+"c1_"+i+" games");
			player.performCommand("as despawn "+p.getName()+"c1_"+i);
		}
	}
	
	@Override
	public boolean skill1() {
		if(na <= 0) {
			na = 200;
			cooldown[1] = 0;
			ARSystem.playSound((Entity)player, "c1n");
		} else {
			ARSystem.playSound((Entity)player, "c1n2");
			skill("c1_s1_e");
			if(na > 140) {
				skill("c1_s1_e3");
			} else {
				skill("c1_s1_e4");
			}
			na = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(player.isSneaking()) {
			skill("c"+number+"_s2_pd");
			skill("c"+number+"_s2_p");
		} else {
			skill("c"+number+"_s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c1a");
		if(lc[0] == null) {
			lc[0] = player.getLocation();
			setloc(1);
		} else if(lc[1] == null) {
			lc[1] = player.getLocation();
			setloc(2);
		} else if(lc[2] == null) {
			lc[2] = player.getLocation();
			setloc(3);
		} else {
			lc[0] = lc[1];
			et[0] = et[1];
			lc[1] = lc[2];
			et[1] = et[2];
			lc[2] = player.getLocation();
			et[2] = null;
			
			player.performCommand("as setloc "+player.getName()+"c1_1 "+lc[0].getX()+","+lc[0].getY()+","+lc[0].getZ());
			player.performCommand("as setloc "+player.getName()+"c1_2 "+lc[1].getX()+","+lc[1].getY()+","+lc[1].getZ());
			player.performCommand("as setloc "+player.getName()+"c1_3 "+lc[2].getX()+","+lc[2].getY()+","+lc[2].getZ());
		}
		return true;
	}
	public void setloc(int i) {
		player.performCommand("as setloc "+player.getName()+"c1_"+i+" "+lc[i-1].getX()+","+lc[i-1].getY()+","+lc[i-1].getZ());
		player.performCommand("as spawn "+player.getName()+"c1_"+i);
	}
	@Override
	public boolean skill4() {
		if(lc[0] !=null) {
			Location l = player.getLocation().clone();
			delay(()->{ARSystem.spellLocCast(player, l, "c1_se");},2);
			if(et[0] == null) {
				player.teleport(lc[0]);
			} else {
				player.teleport(et[0].getLocation());
			}
			lc[0] = lc[1];
			et[0] = et[1];
			lc[1] = lc[2];
			et[1] = et[2];
			lc[2] = null;
			et[2] = null;
		
			for(int i=0; i<3;i++) {
				if(lc[i] == null) {
					player.performCommand("as despawn "+player.getName()+"c1_"+(i+1));
				}
			}
			if(lc[0] != null) {
				player.performCommand("as setloc "+player.getName()+"c1_1 "+lc[0].getX()+","+lc[0].getY()+","+lc[0].getZ());
			}
			if(lc[1] != null) {
				player.performCommand("as setloc "+player.getName()+"c1_2 "+lc[1].getX()+","+lc[1].getY()+","+lc[1].getZ());
			}
			stack++;
			delay(()->{if(stack>0)stack--;},60);
		}
		return true;
	}
	public String getLocString(Location lc) {
		if(lc == null) return "&cX";
		return "X: "+lc.getBlockX()+" Y: "+lc.getBlockY()+" Z: "+lc.getBlockZ();
	}
	
	@Override
	public boolean tick() {
		if(na > 0) {
			na--;
			if(na > 100) {
				skill("c1_s1_ia");
			} else {
				skill("c1_s1_ia2");
			}
			if(na == 0) {
				cooldown[1] = setcooldown[1];
			}
		}
		if(tk%20==0) {
			for(int i = 1; i<4;i++) {
				if(et[i-1] == null) {
					scoreBoardText.add("&c ["+Main.GetText("c1:sk3")+i+ "]&f : " + getLocString(lc[i-1]));
				} else {
					scoreBoardText.add("&c ["+Main.GetText("c1:sk3")+i+ "]&f : " + et[i-1].getName());
				}
				
			}
			if(na > 0) {
				if(na > 100) {
					scoreBoardText.add("&c ["+Main.GetText("c1:sk1")+ "]&f : " + AMath.round(na*0.05,0) +" / 5");
				} else {
					scoreBoardText.add("&4 ["+Main.GetText("c1:sk1")+ "]&f : " + AMath.round(na*0.05,0) +" / 5");
				}
			}
			for(int i = 0; i<3;i++) {
				if(et[i]!=null && et[i].isDead()) {
					player.performCommand("as setloc "+player.getName()+"c1_"+(i+1)+" "+lc[i].getX()+","+lc[i].getY()+","+lc[i].getZ());
					lc[i] = et[i].getLocation();
					et[i] = null;
				}
			}
		}
		double cool = player.getLocation().distance(loc)/15;
		
		cm+=player.getLocation().distance(loc);
		if(cm > 500) {
			Rule.playerinfo.get(player).tropy(1,1);
		}
		if(cooldown[1] > 0) cooldown[1]-=cool;
		if(cooldown[2] > 0) cooldown[2]-=cool;
		if(cooldown[3] > 0) cooldown[3]-=cool;
		if(cooldown[4] > 0) cooldown[4]-=cool;
		loc = player.getLocation();
		return true;
	}
	
	public boolean skill0(EntityDamageByEntityEvent e) {
		e.setCancelled(true);
		spskillen();
		Location targetloc = e.getEntity().getLocation();
		

		if(e.getEntity() instanceof LivingEntity) {
			ARSystem.giveBuff((LivingEntity) e.getEntity(), new Silence((LivingEntity) e.getEntity()), 80);
			ARSystem.giveBuff((LivingEntity) e.getEntity(), new Stun((LivingEntity) e.getEntity()), 80);
		}
		
		e.getEntity().teleport(targetloc);
		ARSystem.giveBuff((LivingEntity) e.getDamager(), new Nodamage((LivingEntity) e.getDamager()), 60);

		
		delay(()->{
			Location loc = targetloc.clone().add(new Vector(4-AMath.random(8),AMath.random(5)*0.2,4-AMath.random(8)).multiply(8));
			ARSystem.spellLocCast(player, loc, "c1_se");
			loc = Local.lookAt(loc, e.getEntity().getLocation());
			e.getDamager().teleport(loc);
		},0);
		delay(()->{
			Location loc = targetloc.clone().add(new Vector(4-AMath.random(8),AMath.random(5)*0.2,4-AMath.random(8)).multiply(6));
			ARSystem.spellLocCast(player, loc, "c1_se");
			loc = Local.lookAt(loc, e.getEntity().getLocation());
			e.getDamager().teleport(loc);
		},3);
		delay(()->{
			Location loc = targetloc.clone().add(new Vector(4-AMath.random(8),AMath.random(5)*0.2,4-AMath.random(8)).multiply(4));
			ARSystem.spellLocCast(player, loc, "c1_se");
			loc = Local.lookAt(loc, e.getEntity().getLocation());
			e.getDamager().teleport(loc);
		},6);
		delay(()->{
			Location loc = targetloc.clone().add(new Vector(3-AMath.random(6),AMath.random(2),3-AMath.random(6)).multiply(2));
			ARSystem.spellLocCast(player, loc, "c1_se");
			loc = Local.lookAt(loc, e.getEntity().getLocation());
			e.getDamager().teleport(loc);
		},9);
		delay(()->{
			Location loc = targetloc.clone().add(new Vector(2,5,0));
			ARSystem.spellLocCast(player, loc, "c1_se");
			loc = Local.lookAt(loc, e.getEntity().getLocation());
			e.getDamager().teleport(loc);
		},12);
		delay(()->{
			Location loc = targetloc.clone().add(new Vector(0,10,4));
			ARSystem.spellLocCast(player, loc, "c1_se");
			loc = Local.lookAt(loc, e.getEntity().getLocation());
			e.getDamager().teleport(loc);
		},14);
		delay(()->{
			Location loc = targetloc.clone().add(new Vector(-6,15,0));
			ARSystem.spellLocCast(player, loc, "c1_se");
			loc = Local.lookAt(loc, e.getEntity().getLocation());
			e.getDamager().teleport(loc);
		},16);
		delay(()->{
			Location loc = targetloc.clone().add(new Vector(0,20,-3));
			ARSystem.spellLocCast(player, loc, "c1_se");
			loc = Local.lookAt(loc, e.getEntity().getLocation());
			e.getDamager().teleport(loc);
		},18);
		delay(()->{
			Location loc = targetloc.clone().add(new Vector(0,30,0));
			ARSystem.spellLocCast(player, loc, "c1_se");
			loc = Local.lookAt(loc, e.getEntity().getLocation());
			e.getDamager().teleport(loc);
			ARSystem.spellCast(player, e.getEntity(), "c1_sp_r");
		},20);
		delay(()->{
			for(Entity en : ARSystem.box(player, new Vector(10,20,10), box.TARGET)){
				((LivingEntity)en).damage(30,player);
				ARSystem.spellLocCast(player, targetloc.clone().add(new Vector(3,8,3)), "c1_se");
				ARSystem.spellLocCast(player, targetloc.clone().add(new Vector(-3,8,3)), "c1_se");
				ARSystem.spellLocCast(player, targetloc.clone().add(new Vector(3,8,-3)), "c1_se");
				ARSystem.spellLocCast(player, targetloc.clone().add(new Vector(-3,8,-3)), "c1_se");
				ARSystem.spellLocCast(player, targetloc.clone().add(new Vector(0,12,0)), "c1_se");
			}
		},30);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(lc[0] == null) {
				et[0] = target;
				lc[0] = target.getLocation();
				setloc(1);
			} else if(lc[1] == null) {
				et[1] = target;
				lc[1] = target.getLocation();
				setloc(2);
			} else if(lc[2] == null) {
				et[2] = target;
				lc[2] = target.getLocation();
				setloc(3);
			} else {
				lc[0] = lc[1];
				et[0] = et[1];
				lc[1] = lc[2];
				et[1] = et[2];
				lc[2] = target.getLocation();
				et[2] = target;
				
				player.performCommand("as setloc "+player.getName()+"c1_1 "+lc[0].getX()+","+lc[0].getY()+","+lc[0].getZ());
				player.performCommand("as setloc "+player.getName()+"c1_2 "+lc[1].getX()+","+lc[1].getY()+","+lc[1].getZ());
				player.performCommand("as setloc "+player.getName()+"c1_3 "+lc[2].getX()+","+lc[2].getY()+","+lc[2].getZ());
			}
		}
	}
	
	@Override
	public void TargetSpell(SpellTargetEvent e,boolean mycaster) {
		if(mycaster && e.getSpell().getName().equals("c1_s1_p3")) {
			e.setCancelled(false);
		}
		if(mycaster && e.getSpell().getName().equals("c1_s1_p24")) {
			e.setCancelled(false);
		}
		if(e.getTarget() instanceof Player && ((Player)e.getTarget()).getGameMode() == GameMode.SPECTATOR) return;

	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 4);
			if(e.getDamager() instanceof Player) {
				if(stack > 2 && !spben) {
					stack = 0;
					if(skillCooldown(0)) {
						spskillon();
						skill0(e);
						return false;
					}
				}
			}
		}
		return true;
	}
}
