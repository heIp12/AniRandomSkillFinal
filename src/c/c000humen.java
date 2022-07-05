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
import ars.Rule;
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
	}

}
