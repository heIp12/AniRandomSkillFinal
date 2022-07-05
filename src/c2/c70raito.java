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
import buff.Wound;
import c.c000humen;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c70raito extends c00main{
	boolean ps = false;
	HashMap<Player,Integer> players = new HashMap<>();
	
	public c70raito(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 70;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(ps == true) {
			invskill = new InvSkill(player) {
				
				@Override
				public void Start(String st) {
					player.closeInventory();
					if(Rule.c.get(Bukkit.getPlayer(st)) != null) {
						players.put(Bukkit.getPlayer(st),50);
						Bukkit.getPlayer(st).sendTitle(Main.GetText("c70:t1"), "",5,1,5);
					}
				}
			};
			Set<Player> Player = ((HashMap<Player, c00main>) Rule.c.clone()).keySet();
			for(Player p : players.keySet()) {
				Player.remove(p);
			}
			Inventory.getlist(invskill,player,Player);
			invskill.openInventory(player);
			
			delay(new Runnable() {
				@Override
				public void run() {
					if(player.getOpenInventory().getTitle().equals(invskill.inventory.getTitle())) {
						player.closeInventory();
					}
				}
			},200);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c"+AMath.random(GetChar.getCount())+"db");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.potion((LivingEntity) player, 14, 60, 0);
		return true;
	}
	
	@Override
	public boolean tick() {
		if(tk%10 == 0 && AMath.random(60) == 4 && !ps) {
			ps = true;
			player.sendTitle("[Death Note]",Main.GetText("c70:t2"));
		}
		if(tk%20 == 0) {
			Player remove = null;
			boolean all = true;
			for(Player p :Rule.c.keySet()) {
				if(p != player && players.get(p) == null) all = false;
			}
			for(Player p :players.keySet()) {
				if(players.get(p) > 0) {
					players.put(p,players.get(p)-1);
					if(players.get(p) == 0) {
						Skill.remove(p, player);
						delay(()->{
							if(Rule.c.get(p) != null) {
								ARSystem.playSoundAll("c70t1");
							} else {
								kill();
								score+=200;
								if(player instanceof Player && ARSystem.AniRandomSkill != null) {
									if(ARSystem.AniRandomSkill.playerkill.get(player) == null) {
										ARSystem.AniRandomSkill.playerkill.put(player,0);
									}
									ARSystem.AniRandomSkill.playerkill.put(player, ARSystem.AniRandomSkill.playerkill.get(player)+1);
								}
							}
						},20);
					}
				} else {
					remove = p;
				}
			}
			if(remove != null) players.remove(remove);
			
			if(all && !isps && Rule.c.size() > 1 && skillCooldown(0)) {
				spskillen();
				spskillon();
				ARSystem.playSoundAll("c70sp");
				for(Player p :Rule.c.keySet()) {
					ARSystem.giveBuff(p,new TimeStop(p), 480);
					if(p != player) {
						delay(()->{
							Rule.c.put(p, new c000humen(p, plugin, null));
							Skill.remove(p, player);
						},480);
					}
				}
			}
			for(Player p :players.keySet()) {
				if(players.get(p) > 0) {
					scoreBoardText.add("&c ["+p.getName()+"] : "+ players.get(p));
				}
			}
			
		}
		bgm();
		return true;
	}
	
	void bgm() {
		if(ps) {
			String s = Bgm.bgmcode;
			boolean bgm = false;
			if(s.equals("bc4")) {
				Bgm.setBgm("r1");
				bgm = true;
			}
			if(s.equals("bc43")) {
				Bgm.setBgm("r2");
				bgm = true;
			}
			if(s.equals("bc44")) {
				Bgm.setBgm("r3");
				bgm = true;
			}
			if(s.equals("bc36")) {
				Bgm.setBgm("r4");
				bgm = true;
			}
			if(s.equals("bc8")) {
				Bgm.setBgm("r5");
				bgm = true;
			}
			if(s.equals("bc26")) {
				Bgm.setBgm("r6");
				bgm = true;
			}
			if(s.equals("bc9") || s.equals("bc39") || s.equals("bc999")) {
				Bgm.setBgm("r7");
				bgm = true;
			}
			if(s.equals("bc2") || s.equals("bc47") || s.equals("bc62")) {
				Bgm.setBgm("r8");
				bgm = true;
			}
			if(s.equals("bc21") || s.equals("bc30") || s.equals("69") || s.equals("60")) {
				Bgm.setBgm("r9");
				bgm = true;
			}
			if(s.equals("bc13")) {
				Bgm.setBgm("r11");
				bgm = true;
			}
			if(s.equals("bc12")) {
				Bgm.setBgm("r12");
				bgm = true;
			}
			if(bgm == true) {
				Rule.playerinfo.get(player).tropy(70,1);
				
				for(int i=0; i<10;i++) {
					ARSystem.spellLocCast(player, Map.randomLoc().add(0,1,0), "c70_s");
				}
			}
			
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			
		}
		return true;
	}
	
}
