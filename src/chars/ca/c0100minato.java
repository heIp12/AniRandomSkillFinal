package chars.ca;

import java.util.ArrayList;
import java.util.List;

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
import buff.TimeStop;
import chars.c.c00main;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;
import util.MagicSpellVar;
import util.Map;

public class c0100minato extends c00main{
	private List<Location> loc = new ArrayList<Location>();
	private Location loc2;
	private Location lastloc = null;
	private Location s1loc;
	
	private int count = 0;
	
	public c0100minato(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1001;
		load();
		text();
		loc2 = player.getLocation();
		ARSystem.playSound(player, "c1select");
	}
	
	@Override
	public void setStack(float f) {
		for(int i=0;i<f;i++) {
			loc.add(Map.randomLoc());
		}
	}
	
	@Override
	public boolean skill1() {
		s1loc = player.getLocation();
		ARSystem.playSound((Entity)player, "c1n");
		loc.add(player.getLocation());
		skill("c1001_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		lastloc = player.getLocation();
		loc.add(lastloc);
		cooldown[3]-=1;
		skill("c1_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		loc.add(player.getLocation());
		ARSystem.spellLocCast(player, lastloc, "c1001_se3");
		player.teleport(lastloc);
		skill("c1001_s3");
		return true;
	}
	
	
	
	@Override
	public void TargetSpell(SpellTargetEvent e,boolean mycaster) {
		if(mycaster && e.getSpell().getName().equals("c1001_s1_a")) {
			cooldown[3]-=1;
			lastloc = e.getTarget().getLocation();
			loc.add(lastloc);
			player.teleport(e.getTarget());
			delay(()->{
				ARSystem.spellLocCast(player, s1loc, "c1001_se3");
				player.teleport(s1loc);
			},10);
		}
		
	}
	
	@Override
	public boolean tick() {
		if(count > 0) {
			int size = (int) (8.0*((1.0*count)/(loc.size()+1.0)));
			if(size < 1) size = 1;
			
			if(tk%size == 0) {
				Location local = loc.get(count);
				ARSystem.spellLocCast(player, local, "c1001_se4");
				player.teleport(local);
				if(ARSystem.boxSOne(player, new Vector(100,100,100), box.TARGET) != null) {
					local = ULocal.lookAt(local, ARSystem.boxSOne(player, new Vector(100,100,100), box.TARGET).getLocation());
					player.teleport(local);
				}
				skill("c1001_sp");
				count--;
				ARSystem.giveBuff(player, new Nodamage(player), 10);
				ARSystem.giveBuff(player, new Silence(player), 10);
				ARSystem.giveBuff(player, new Stun(player), 10);
			}
		}
		if(loc.size() > 10) {
			int i = 100-loc.size();
			if(i < 1) i = 1;
			if(AMath.random(i) <= 1 && AMath.random(20) == 1 && skillCooldown(0)) {
				if(loc.size() < 30) setStack(15);
				
				spskillen();
				spskillon();
				for(Player p : Rule.c.keySet()) {
					loc.add(p.getLocation());
				}
				Rule.playerinfo.get(player).tropy(1,2);
				ARSystem.playSoundAll("c1db");
				count = loc.size();
				ARSystem.giveBuff(player, new TimeStop(player), 60);
				delay(()->{setStack(1);},60);
			}
		}
		if(tk%20==0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c1001:t1")+ "]&f : " + loc.size());
		}
		double cool = player.getLocation().distance(loc2)/15;
		if(cooldown[1] > 0) cooldown[1]-=cool;
		if(cooldown[2] > 0) cooldown[2]-=cool;
		if(cooldown[3] > 0) cooldown[3]-=cool;
		if(cooldown[4] > 0) cooldown[4]-=cool;
		loc2 = player.getLocation();
		
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			
		}
		return true;
	}
}
