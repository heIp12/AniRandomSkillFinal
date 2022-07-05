package c;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import c2.c62shinon;
import types.box;
import types.modes;
import util.Local;
import util.MSUtil;

public class c02kirito extends c00main{
	private long millitime = 0;
	private boolean pson = false;
	int healcount = 0;
	int skillcount = 0;
	int spcount = 0;
	int s2 = 0;
	int s3 = 0;
	int s4 = 0;
	
	List<Entity> en = new ArrayList<>();
	
	public c02kirito(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2;
		load();
		text();
	}
	public void sp(){
		spcount++;
		if(spcount > 5) {
			skill("c2_sp2");
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);;
				((LivingEntity)e).damage(3,player);
			}
		}
		delay(()->{if(spcount>0)spcount--;},15);
	}
	@Override
	public boolean skill1() {
		heal();
		skill("c"+number+"_s1");
		ARSystem.playSound((Entity)player, "c2a3");
		for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
			((LivingEntity)e).setNoDamageTicks(0);;
			((LivingEntity)e).damage(2,player);
			ARSystem.potion((LivingEntity) e, 2, 60, 2);
		}
		if(pson) {
			sp();
			cooldown[4] -= 3;
			cooldown[2] -= 3;
			cooldown[3] -= 3;
			skillcount++;
			if(skillcount > 200) {
				Rule.playerinfo.get(player).tropy(2,1);
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		heal();
		en.clear();
		ARSystem.playSound((Entity)player, "c2a4");
		s2 = 20;
		if(pson) {
			sp();
			cooldown[1] -= 3;
			cooldown[4] -= 3;
			cooldown[3] -= 3;
			skillcount++;
			if(skillcount > 200) {
				Rule.playerinfo.get(player).tropy(2,1);
			}
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		heal();
		s3++;
		if(s3 == 1) {
			ARSystem.playSound((Entity)player, "c2a");
			skill("c2_s3_ia");
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);;
				((LivingEntity)e).damage(1,player);
			}
		}
		if(s3 == 2) {
			ARSystem.playSound((Entity)player, "c2a2");
			skill("c2_s32_ia");
			for(Entity e : ARSystem.box(player, new Vector(5,4,5), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);;
				((LivingEntity)e).damage(2,player);
			}
		}
		if(s3 == 3) {
			s3 = 0;
			ARSystem.playSound((Entity)player, "c2h");
			skill("c2_s33_ia");
			skill("c2_s33_ia2");
			for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);;
				((LivingEntity)e).damage(3,player);
			}
		}

		if(pson) {
			sp();
			cooldown[1] -= 3;
			cooldown[2] -= 3;
			cooldown[4] -= 3;
			skillcount++;
			if(skillcount > 200) {
				Rule.playerinfo.get(player).tropy(2,1);
			}
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		heal();
		millitime = System.currentTimeMillis();
		ARSystem.playSound((Entity)player, "c2s");
		s4 = 60;
		
		if(pson) {
			sp();
			cooldown[1] -= 3;
			cooldown[2] -= 3;
			cooldown[3] -= 3;
			skillcount++;
			if(skillcount > 200) {
				Rule.playerinfo.get(player).tropy(2,1);
			}
		}
		return true;
	}
	
	void heal() {
		healcount++;
		if(healcount > 1) {
			healcount = 0;
			ARSystem.heal(player, 1);
		}
	}
	
	public boolean skill0(EntityDamageByEntityEvent e) {
		if((System.currentTimeMillis()-millitime)/1000.0 < 0.25 && !pson &&!spben) {
			if(skillCooldown(0)) {
				ARSystem.playSound((Entity)player, "c2f");
				setcooldown[1] -= 1;
				setcooldown[2] -= 1;
				setcooldown[3] -= 1;
				setcooldown[4] -= 1;
				spskillon();
				spskillen();
				pson = true;
			}
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(tk%20==0 && isps) {
			scoreBoardText.add("&c ["+Main.GetText("c2:sk0")+ "]&f : "+ spcount + " / 6");
		}
		if(s4 > 0) s4--;
		if(s2 > 0) {
			s2--;
			Location loc = player.getLocation();
			loc.setPitch(0);
			ARSystem.spellLocCast(player, Local.offset(loc, new Vector(2,1,0)), "c2_s1_ia2");
			player.setVelocity(loc.getDirection().multiply(0.8));
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				if(!en.contains(e)) {
					en.add(e);
					((LivingEntity)e).setNoDamageTicks(0);;
					((LivingEntity)e).damage(5,player);
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 0.5);
			if(e.getEntity() instanceof Player) {
				if(s4 > 0) {
					s4 = 0;
					ARSystem.playSound((Entity)player, "c2g");
					player.setVelocity(player.getLocation().getDirection().setY(0).multiply(-4));
					e.setDamage(0);
					e.setCancelled(true);
					skill0(e);
					return false;
				}
			}
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 1.5);
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c08yuuki) {
					is = "yuuki";
					break;
				}
				if(Rule.c.get(e) instanceof c47ren){
					is = "ren";
					break;
				}
				if(Rule.c.get(e) instanceof c62shinon){
					is = "sinon";
					break;
				}
			}
		}
		
		if(is.equals("yuuki")) {
			ARSystem.playSound((Entity)player, "c2yuki");
		} else if(is.equals("ren")) {
			ARSystem.playSound((Entity)player, "c2ren");
		} else if(is.equals("sinon")) {
			ARSystem.playSound((Entity)player, "c2sinon");
		}else  {
			ARSystem.playSound((Entity)player, "c2db");
		}
		
		return true;
	}
}
