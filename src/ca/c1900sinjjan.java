package ca;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c000humen;
import c.c00main;
import event.Skill;
import types.box;
import util.AMath;
import util.MSUtil;
import util.Map;
import util.Text;

public class c1900sinjjan extends c00main{
	int sk3 = 0;
	
	public c1900sinjjan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1019;
		load();
		text();
		ARSystem.playSound(player, "c19select");
		if(Map.lastplay == 4) {
			skillmult += 3;
		}
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c19s0");
		skill("c"+number+"_s2");
		int i = AMath.random(10);
		if(i == 1) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore2"));
			ARSystem.heal(player, 999);
		}
		if(i == 2) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore3"));
			cooldown[1] = cooldown[2] = cooldown[3] = 0;
		}
		if(i == 3) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore4"));
			for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
				((LivingEntity)e).damage(8,player);
			}
		}
		if(i == 4) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore5"));
			ARSystem.RandomPlayer().damage(20,player);
		}
		if(i == 5) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore6"));
			ARSystem.RandomPlayer().teleport(Map.randomLoc());
		}
		if(i == 6) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore7"));
			Player p = ARSystem.RandomPlayer();
			Skill.remove(p, p);
		}
		if(i == 7) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore8"));
			ARSystem.heal(ARSystem.RandomPlayer(),999);
		}
		if(i == 8) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore9"));
			Player p = ARSystem.RandomPlayer();
			Rule.c.put((Player) p, new c1900sinjjan(p, plugin, null));
		}
		if(i == 9) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore10"));
			ARSystem.giveBuff(player, new Silence(player), 200);
		}
		if(i == 10) {
			player.sendTitle(Main.GetText("c1019:sk2"), Main.GetText("c1019:sk2_lore11"));
			Player p = ARSystem.RandomPlayer();
			Rule.c.put(p, new c000humen(p, plugin, null));
		}
		return true;
	}

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c19sp");
		ARSystem.potion(player, 1, 60, 3);
		sk3 = 60;
		return true;
	}

	@Override
	protected boolean skill9(){
		String s = ""+AMath.random(3);
		if(s.equals("1")) s= "";
		player.getWorld().playSound(player.getLocation(), "c19db"+s, 1, 1);

		return false;
	}
	
	@Override
	public boolean tick() {
		if(sk3>0) {
			sk3--;
		}
		return false;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(sk3 > 0) {
				e.setDamage(e.getDamage()*0.99);
			}
		}
		return true;
	}
}