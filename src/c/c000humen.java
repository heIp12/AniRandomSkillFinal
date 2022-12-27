package c;

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
import c2.c62shinon;
import types.box;
import util.AMath;
import util.GetChar;
import util.MSUtil;
import util.MagicSpellVar;

public class c000humen extends c00main{
	
	public c000humen(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 10000;
		load();
		text();
		ARSystem.playSound(player, "humendb1");
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "humendb"+(AMath.random(5)+1));
		return true;
	}
}
