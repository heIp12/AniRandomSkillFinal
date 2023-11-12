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
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c44izuna;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
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

public class c81saitama extends c00main{

	int sp = 0;
	
	public c81saitama(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 81;
		load();
		text();
		c = this;
		if(p != null) p.setGameMode(GameMode.SPECTATOR);
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
		if(player.getGameMode() != GameMode.SPECTATOR) {
			sp++;
			if(sp > 600) {
				sp = -99999;
				spskillon();
				spskillen();
				ARSystem.giveBuff(player, new TimeStop(player), 160);
				ARSystem.playSoundAll("c81sp");
				delay(()->{
					Skill.quit(player);
				},150);
			}
		}
		if(tk%20 == 0) {
			int c = 0;
			for(Entity e : Rule.c.keySet()) {
				if(((Player)e).getGameMode() == GameMode.SPECTATOR) {
					c++;
				}
			}
			if(ARSystem.AniRandomSkill != null) {
				boolean ok = (ARSystem.AniRandomSkill.getTime() >= 180 || ARSystem.isGameMode("mirror"));
				if(!ok) {
					int size = 0;
					for(Player p : Rule.c.keySet()) {
						if(p.getGameMode() != GameMode.SPECTATOR && Rule.c.get(p).number != 81) size++;
					}
					if(size <= 1 && ARSystem.AniRandomSkill.getTime() < 180) {
						ARSystem.playSoundAll("c81s");
						Skill.remove(player, player);
						return false;
					}
				}
				if(ARSystem.AniRandomSkill != null && !isps && ok && ARSystem.AniRandomSkill.getTime() >= 5) {
					spskillon();
					
					for(Player p: Rule.c.keySet()) {
						Rule.c.get(p).PlayerSpCast(player);
					}
					String n = Main.GetText("c"+number+":name1") + " ";
					if(n.equals("-")) {
						n = "";
					}
					n +=Main.GetText("c"+number+":name2");
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						AdvManager.set(p, 399, 0 , "§f§l"+player.getName() +"§a("+ n +") §f§l" + Main.GetText("c"+number+":ps"));
					}
					String na = "c"+number+":ps"+"/&c!!!";
					
					player.performCommand("tm anitext "+player.getName()+" SUBTITLE true 40 "+na);
					
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
