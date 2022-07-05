package util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import com.nisovin.magicspells.BuffManager;
import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Spell;
import com.nisovin.magicspells.events.MagicSpellsLoadedEvent;
import com.nisovin.magicspells.events.MagicSpellsLoadingEvent;
import com.nisovin.magicspells.spells.BuffSpell;

public class MSUtil {
	Plugin plugin;
	static BuffManager bf = MagicSpells.getBuffManager();
	
	static public void loading(MagicSpellsLoadingEvent e) {
		bf = e.getPlugin().getBuffManager();
	}
	
	static public Spell getSpell(String name) {
		return MagicSpells.getSpellByInternalName(name);
	}
	
	static public boolean isbuff(Player p,String name) {
		if(getSpell(name) instanceof BuffSpell) {
			return ((BuffSpell)getSpell(name)).isActiveAndNotExpired(p);
		}
		return false;
	}
	
	static public void buffoff(Player p,String name) {
		if(getSpell(name) instanceof BuffSpell) {
			((BuffSpell)getSpell(name)).turnOff(p);
			((BuffSpell)getSpell(name)).unloadPlayerEffectTracker(p);
		}
	}
	
	static public void resetbuff(Player p) {
		List<BuffSpell> bl = new ArrayList<BuffSpell>();
		
		if(bf.getActiveBuffs(p) != null) {
			for(BuffSpell spell : bf.getActiveBuffs(p)) {
				bl.add(spell);
			}
			for(BuffSpell spell : bl) {
				spell.turnOff(p);
			}
			
		}
		for(PotionEffect potion :p.getActivePotionEffects()) {
			p.removePotionEffect(potion.getType());
		}
	}
	
	static public double getvar(Player p,String name) {
		return MagicSpells.getVariableManager().getValue(name, p);
	}
	
	static public void setvar(Player p,String name,double num) {
		MagicSpells.getVariableManager().set(name, p,num);
	}
}
