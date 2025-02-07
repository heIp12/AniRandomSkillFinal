package chars.c;

import java.util.List;

import org.bukkit.Bukkit;
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
import chars.c2.c62shinon;
import types.box;
import util.AMath;
import util.GetChar;
import util.MSUtil;
import util.MagicSpellVar;
import util.Map;
import util.Text;

public class c001humen3 extends c00main{
	long time = 0;
	Location lc;
	
	public c001humen3(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 10002;
		load();
		text();
		delay(()->{
			ARSystem.removeItemAll(p);
			skill("removeall");
			ARSystem.killall();
		},0);
	}
	
	@Override
	public boolean skill1() {
		if(time != 0 || Map.mapid != 1001) {
			ARSystem.playSound((Entity)player, "0gun4");
			ARSystem.spellCast(player, "item1012");
			delay(()->{
				ARSystem.playerAddRotate(player,0,(float) -20);
			},2);
			if(Map.mapid == 1001) Bukkit.broadcastMessage(player.getName() + " : " + AMath.round( (double)((System.currentTimeMillis()- time)/1000.0),2));
		}
		return true;
	}
	
	public void SetTime(long i) {
		time = i;
	}
	
	@Override
	public boolean tick() {
		if(lc != null) {
			player.teleport(lc);
		}
		return super.tick();
	}

	public void SetLoc(Location lc) {
		this.lc = lc;
	}
}
