package chars.c2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
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
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.BuffType;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c69himi extends c00main{
	boolean ps = false;
	int count = 0;
	int pt = 0;
	
	List<Location> local = new ArrayList<>();
	
	public c69himi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 69;
		load();
		text();
		c = this;
	}
	void sp(){
		if(count > 60) {
			Rule.playerinfo.get(player).tropy(69,1);
		}
		if(AMath.random(100) <= 1 && !isps) {
			spskillon();
			spskillen();
			ARSystem.playSoundAll("c69sp");
			skillmult+=1;
		}
	}
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c69s");
		if(ARSystem.isGameMode("lobotomy") && AMath.random(3) == 1) {
			count++;
		} else {
			count++;
		}
		Location loc = player.getLocation();
		
		for(int i =0; i < 1+count/5;i++) {
			delay(()->{
				ARSystem.spellLocCast(player, loc, "c69_s3");
				ARSystem.spellLocCast(player, loc.clone().add(new Vector(5-AMath.random(10),0,5-AMath.random(10)).multiply(0.2)), "c69_s1_i1");
				for(Location locs : local) {
					locs.setYaw(loc.getYaw());
					ARSystem.spellLocCast(player, locs.clone().add(new Vector(5-AMath.random(10),0,5-AMath.random(10)).multiply(0.2)), "c69_s1_i1");
				}
			},i*3);
		}
		sp();
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c69s");
		if(ARSystem.isGameMode("lobotomy") && AMath.random(3) == 1) {
			count++;
		} else {
			count++;
		}
		Location loc = player.getLocation();
		
		for(int i =0; i < 1+count/5;i++) {
			delay(()->{
				ARSystem.spellLocCast(player, loc.clone().add(new Vector(5-AMath.random(10),0,5-AMath.random(10)).multiply(0.2)), "c69_s2_i1");
				ARSystem.spellLocCast(player, loc, "c69_s3");
				for(Location locs : local) {
					locs.setYaw(loc.getYaw());
					ARSystem.spellLocCast(player, locs.clone().add(new Vector(5-AMath.random(10),0,5-AMath.random(10)).multiply(0.2)), "c69_s2_i1");
				}
			},i*3);
		}
		sp();
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c69s3");
		if(ARSystem.isGameMode("lobotomy") && AMath.random(3) == 1) {
			count++;
		} else {
			count++;
		}
		local.add(player.getLocation());
		sp();
		return true;
	}
	int tick = 0;
	
	@Override
	public boolean tick() {
		if(tk%5 == 0) {
			for(Location loc : local) {
				ARSystem.spellLocCast(player, loc, "c69_s3");
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c69:ps")+ "] : "+ (1+count/5));
		}
		if(isps && AMath.random(10) == 3) {
			if(AMath.random(2) == 2) {
				Location loc = Map.randomLoc();
				loc.setYaw(AMath.random(360));
				ARSystem.spellLocCast(player, loc, "c69_s3");
				ARSystem.spellLocCast(player, loc.add(0,1,0), "c69_s1_i1");
			} else {
				for(int i=0;i<AMath.random(3);i++) {
					Location loc = Map.randomLoc();
					loc.setYaw(AMath.random(360));
					ARSystem.spellLocCast(player, loc, "c69_s3");
					ARSystem.spellLocCast(player, loc.add(0,1,0), "c69_s2_i1");
				}
			}
		}
		tick++;
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
