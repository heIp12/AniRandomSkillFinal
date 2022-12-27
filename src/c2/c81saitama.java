package c2;

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
import buff.Timeshock;
import buff.Wound;
import c.c000humen;
import c.c00main;
import c.c44izuna;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c81saitama extends c00main{

	public c81saitama(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 81;
		load();
		text();
		c = this;
		p.setGameMode(GameMode.SPECTATOR);
	}

	@Override
	public boolean skill1() {
		skill("c81_s1");
		ARSystem.playSound((Entity)player, "c81a");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c81_s2");
		player.setVelocity(player.getLocation().getDirection().multiply(5));
		ARSystem.playSound((Entity)player, "c81s");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			int c = 0;
			for(Entity e : Rule.c.keySet()) {
				if(((Player)e).getGameMode() == GameMode.SPECTATOR) {
					c++;
				}
			}
			if(ARSystem.AniRandomSkill != null) {
			boolean ok = (ARSystem.AniRandomSkill.getTime() >= 180 || ARSystem.gameMode == modes.ONE || (Rule.c.size()-c < 1 && c > 0));
				if(ARSystem.AniRandomSkill != null && !isps && ok && ARSystem.AniRandomSkill.getTime() >= 5) {
					spskillon();
					spskillen();
					ARSystem.playSoundAll("c81sel");
					player.setGameMode(GameMode.SURVIVAL);
					Rule.playerinfo.get(player).tropy(81,1);
				} else if(!isps && Rule.c.size() <= 2){
					ARSystem.playSoundAll("c81s");
					Skill.remove(player, player);
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(99999);
		}
		
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c51zenos) {
					is = "zanos";
					break;
				}
			}
		}
		
		if(is.equals("zanos")) {
			ARSystem.playSound((Entity)player, "c81zanos");
		} else {
			ARSystem.playSound((Entity)player, "c81db");
		}
		
		return true;
	}
}