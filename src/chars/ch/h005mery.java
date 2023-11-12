package chars.ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.MapVoid;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.MapType;
import types.box;

import util.AMath;
import util.MSUtil;
import util.Map;

public class h005mery extends c00main{
	int sk2 = 0;
	int sk3 = 0;
	int sp = 0;
	List<LivingEntity> en;
	List<LivingEntity> target;
	
	public h005mery(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 995;
		load();
		text();
		if(ARSystem.gameMode2 && Map.mapid != 1009) {
			Map.mapType = MapType.NORMAL;
			player.performCommand("tm anitext all SUBTITLE true 60 c995:p2/c995:p");
			Map.getMapinfo(1009);
			player.performCommand("as gdespawn ib");
			player.performCommand("as gspawn ib");
			Location loc = new Location(player.getWorld(),-4,13.5,-3);
			loc.setYaw(180);
			ARSystem.spellLocCast(player, loc, "heIp");
			for(Player pla : Bukkit.getOnlinePlayers()) {
				Map.playeTp(pla);
			}
		} else {

		}
		ARSystem.playSound((Entity)player, "marryselect");
	}
	
	int skill1 = 0;
	int skill1_tick = 0;
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "marrys1");
		skill("merry_s1");
		skill("merry_s1-3");
		skill("merry_s1-2");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "marrys2");
		Location loc = player.getLocation();
		loc.setPitch(0);
		float yaw = loc.getYaw();
		while(yaw < 0) yaw+=360;
		if(yaw > 360) yaw%=360;
		if(yaw >= 45 && yaw <= 135) yaw = 90;
		else if(yaw >= 135 && yaw <= 225) yaw = 180;
		else if(yaw >= 225 && yaw <= 315) yaw = 270;
		else yaw = 0;
		loc.setYaw(yaw);
		ARSystem.spellLocCast(player, loc, "merry_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "marrys3");
		for(int i = AMath.random(5); i>0; i--) skill("merry_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		return true;
	}
	
	public void sp() {
		skill("merry_sp");
		Bgm.setForceBgm("c995-2");
		Map.getMapinfo(1010);
		ARSystem.potion(player, 14, 10000, 1);
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p != player) {
				if(Rule.c.get(p) != null) {
					Rule.c.put(p,new c000humen(player, plugin, null));
				}
				ARSystem.giveBuff(p, new TimeStop(p), 400);
				ARSystem.potion(p, 14, 10000, 1);
			} else {
				ARSystem.giveBuff(p, new Silence(p), 400);
				player.setGameMode(GameMode.SPECTATOR);
			}
			p.teleport(new Location(player.getWorld(),-42.5,9,38.8,0,-15));
		}
		
		for(int i = 0; i<7;i++) {
			int j = i;
			delay(()->{
				for(Player p : Bukkit.getOnlinePlayers()) ARSystem.playSound(p,"bell",(float) (1.1 - (0.1*j)));
			},80+40*i);
		}
		delay(()->{
			ARSystem.playSoundAll("dollout");
		},360);
		delay(()->{
			for(int i = 0; i<10;i++) {
				for(Player p : Rule.c.keySet()) p.damage(0.1,player);
			}
		},360);
		delay(()->{
			Bgm.bgmlock = false;
			Skill.win(player);
		},370);
	}
	
	@Override
	public boolean firsttick() {
		if(!isps) {
			boolean tal = true;
			for(Player p :Rule.c.keySet()) {
				if(p.getGameMode() != GameMode.SPECTATOR && !Map.inMap(p)) {
					p.teleport(Map.randomLoc(p));
					if(p != player) {
						if(tal) {
							tal = false;
							sp++;
						}
						if(sp == 5 && !isps) {
							spskillon();
							spskillen();
							WinEvent event = new WinEvent(player);
							Bukkit.getPluginManager().callEvent(event);
							if(!event.isCancelled()) {
								sp();
							}
						}
					}
				}
			}
		} else {
			
		}
		return super.firsttick();
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c995:sk0")+ "]&f : " + sp +" / 5");
			String s = Bgm.bgmcode;
			if(!s.equals("bc995") && Bgm.rep) {
				Bgm.setlockBgm("c995");
			}
		}
		
		return true;
	}

	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player) {
			ARSystem.playSoundAll("marrydeath");
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(e.getDamage()*0.7 > 5) {
				e.setDamage(5);
			} else {
				player.teleport(Map.randomLoc(player));
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "marrydb");
		cooldown[9] = 2;
		if(ARSystem.boxSOne(player, new Vector(12,6,12), box.TARGET) == null) {
			delay(()->{
				ARSystem.playSound((Entity)player, "marrydb2");
			},40);
			cooldown[9] = 4;
		}
		return true;
	}
}
