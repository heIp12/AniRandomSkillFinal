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
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import util.AMath;
import util.GetChar;
import util.MSUtil;
import util.MagicSpellVar;

public class c001nb extends c00main{
	int time = 0;
	
	public c001nb(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = -1;
		load();
		text();
		ARSystem.giveBuff(player, new TimeStop(player), 0);
		ARSystem.giveBuff(player, new Silence(player), 0);
		ARSystem.giveBuff(player, new Noattack(player), 400000);
		ARSystem.giveBuff(player, new Stun(player), 400000);
		Rule.buffmanager.selectBuffValue(player, "barrier",500000);
	}
	
	@Override
	public boolean skill1() {
		Rule.c.put((Player) player, GetChar.get((Player) player, Rule.gamerule,""+ AMath.random(10)));
		return true;
	}
	@Override
	public boolean skill2() {
		Rule.c.put((Player) player, GetChar.get((Player) player, Rule.gamerule,""+ (AMath.random(10)+10)));
		return true;
	}
	@Override
	public boolean skill3() {
		Rule.c.put((Player) player, GetChar.get((Player) player, Rule.gamerule,""+ (AMath.random(10)+20)));
		return true;
	}
	@Override
	public boolean skill4() {
		Rule.c.put((Player) player, GetChar.get((Player) player, Rule.gamerule,""+ (AMath.random(10)+30)));
		return true;
	}
	@Override
	public boolean skill5() {
		Rule.c.put((Player) player, GetChar.get((Player) player, Rule.gamerule,""+ (AMath.random(10)+40)));
		return true;
	}
	@Override
	public boolean tick() {
		time++;
		if(time%300 == 0) {
			ARSystem.playSound((Entity)player, "c-1db1");
		}
		player.setVelocity(new Vector(0,0,0));
		return true;
	}

}
