package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.PowerUp;
import chars.c3.c126hera;
import chars.c3.c129nana;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c45momo extends c00main{

	int count = 0;
	int tick = 0;
	boolean sp = false;
	
	public c45momo(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 45;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		count++;
		if(count >= 30) {
			Rule.playerinfo.get(player).tropy(45,1);
		}
		if(sp) {
			skill("c45_sp1");
		} else {
			skill("c45_s1");
		}
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c45_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c45_s3");
		return true;
	}

	@Override
	public boolean tick() {
		tick++;
		if(psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c45:sk0")+ "] : "+ (tick/20) +" / 20");
		}
		if(tk%20==0) scoreBoardText.add("&c ["+Main.GetText("c45:ps")+ "] : "+ count+" / "+(count*5) +"%");
		
		if(tick%400 == 0) {
			if(Rule.buffmanager.GetBuffValue(player, "barrier") < count*2) {
				Rule.buffmanager.selectBuffValue(player, "barrier",count*2);
			}
		}
		if(tick > 400) {
			if(count == 0 && !sp) {
				sp = true;
				spskillon();
				spskillen();
				setcooldown[2] *= 0.5;
				ARSystem.playSound(player, "c45sp");
				count+=5;
				ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(),new Vector(0.5,0,0)),"c45_flower2");
				ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(),new Vector(0.25,0,0.75)),"c45_flower2");
				ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(),new Vector(0.25,0,-0.75)),"c45_flower2");
				ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(),new Vector(0.1,0,1.5)),"c45_flower2");
				ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(),new Vector(0.1,0,-1.5)),"c45_flower2");
			}
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() + e.getDamage()*(count*0.05));
		} else {

		}
		return true;
	}
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c129nana) {
					is = "n";
					break;
				}

			}
		}

		if(is.equals("n")) {
			ARSystem.playSound((Entity)player, "c129nana");
		} else {
			ARSystem.playSound((Entity)player, "c45db");
		}
		return true;
	}
}
