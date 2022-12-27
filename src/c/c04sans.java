package c;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.Holo;
import types.modes;
import util.AMath;

public class c04sans extends c00main{
	private int skillcount = 0;
	int count = 0;
	
	public c04sans(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 4;
		load();
		text();
	}
	@Override
	public void setStack(float f) {
		skillcount = (int) f;
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c4a");
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skillcount++;
		if(skillcount == 7) {
			spskillon();
		}
		if(skillcount > 7) {
			skill("c"+number+"_sp");
			spskillen();
			skillcount = 0;
		} else {
			skill("c"+number+"_s3");
		}
		return true;
	}
	@Override
	public boolean tick() {
		if(tk%20==0&&psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c4:sk0")+ "]&f : " + skillcount +" / 8");
		}
		return true;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			if(Rule.c.get(e.getDamager()) != null) {
				if(Rule.c.get(e.getDamager()) instanceof c03remuru) {
					return true;
				}
			}
				
			if(AMath.random(100) > (e.getDamage()*1.4)) {
				Location loc = player.getLocation().clone().add(0,1,0);
				loc.setPitch(0);
				ARSystem.spellLocCast(player, loc, "c4_s0_e");
				loc.setYaw(loc.getYaw()+180);
				ARSystem.spellLocCast(player, loc, "c4_s0_e");
				
				ARSystem.playSound((Entity)player, "c4db");
				ARSystem.potion(player, 14, 5, 1);
				player.setNoDamageTicks(5);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() + 2);
		}
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(e.getCause() != DamageCause.ENTITY_ATTACK) {
			if(AMath.random(100) > (e.getDamage()*1.4)) {
				cooldown[3]-=1;
				skill("c4_s0");
				player.setNoDamageTicks(2);
				e.setDamage(0);
				count++;
				if(count >= 50) {
					Rule.playerinfo.get(player).tropy(4,1);
				}
				return false;
			}
		}
		return true;
	}
}
