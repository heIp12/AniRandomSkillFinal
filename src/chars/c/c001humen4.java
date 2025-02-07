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

public class c001humen4 extends c00main{
	long time = 0;
	Location lc;
	
	public c001humen4(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 10003;
		load();
		text();
		skill("removemyall");
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound(player, "0money2");
		Rule.playerinfo.get(player).gold += 50;
		return true;
	}
	
	@Override
	public boolean tick() {
		return super.tick();
	}
}
