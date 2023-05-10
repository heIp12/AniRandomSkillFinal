package chars.c;

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

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.TimeStop;
import buff.Timeshock;
import chars.ca.c2400sinobu;

import util.AMath;
import util.MSUtil;
import util.Text;
import util.ULocal;
import util.Var;

public class c24sinobu extends c00main{
	boolean passive;
	boolean ps;
	
	int time = 120;
	int ticks = 0;
	int heal = 0;
	float damage = 1;
	
	int shdow = -1;
	public LivingEntity target;
	
	public c24sinobu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 24;
		load();
		text();
		passive = true;
		ps = false;
		Rule.buffmanager.selectBuffTime(player, "silence",0);
	}
	
	@Override
	public boolean skill1() {
		if(shdow == -1) {
			skill("c"+number+"_s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
		
	
	@Override
	public boolean skill2() {
		if(shdow == -1) {
			skill("c"+number+"_s2");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}

	
	@Override
	public boolean skill3() {
		if(shdow == -1 && player.getLocation().getBlock().getLightLevel() < 12) {
			shdow = 15;
			ARSystem.potion(player, 14, 300, 0);
			ARSystem.giveBuff(player, new Nodamage(player), 300);
			ARSystem.giveBuff(player, new Noattack(player), 300);
			cooldown[3] = 0;
			ARSystem.playSound((Entity)player, "c24s3");
		}
		else if(player.getLocation().getBlock().getLightLevel() < 12){
			shdow = -1;
			Rule.buffmanager.selectBuffTime(player, "nodamage",0);
			Rule.buffmanager.selectBuffTime(player, "noattack",0);
			ARSystem.potion(player, 14, 0, 0);
			ARSystem.playSound((Entity)player, "c24s3");
			
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1") && Rule.buffmanager.GetBuffTime(player, "timestop") <= 0) {
			target.setNoDamageTicks(0);
			target.damage(4,player);


			if(player.getMaxHealth() - player.getHealth() < 4) {
				heal += 4 - (player.getMaxHealth() - player.getHealth());
			}
			ARSystem.heal(player, 4);
			
		}
	}
	public void event(Player target) {
		ARSystem.giveBuff(target, new TimeStop(target), 100);
		ARSystem.giveBuff(player, new TimeStop(player), 100);
		ARSystem.giveBuff(this.target, new TimeStop(this.target), 100);
		Location loc = target.getLocation();
		loc.setPitch(0);
		loc = ULocal.offset(loc, new Vector(-2,-2,0));
		player.teleport(loc);
		player.setGameMode(GameMode.ADVENTURE);
		for(int i=0;i<20;i++) {
			Location locs = loc.clone().add(0,0.1*i,0);
			delay(()->{
				player.teleport(locs);
			},i);
		}
		delay(()->{
			ARSystem.spellCast(player, target, "c24_move");
		},20);
		for(int i = 0; i<3;i++) {
			delay(()->{
				skill("c"+number+"_s1");
			},40+10*i);
		}
		Location lo = this.target.getLocation();
		loc.setPitch(0);
		loc = ULocal.offset(loc, new Vector(-2,-2,0));
		delay(()->{
			player.teleport(target);
			Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) Rule.buffmanager.GetBuffValue(target, "plushp"));
			skillmult += 1;
			player.setMaxHealth(player.getMaxHealth()+12);
			player.setHealth(player.getMaxHealth());
			Rule.c.put(target, new c000humen(target, plugin, null));
			ARSystem.spellLocCast(player, lo, "c24_move");
			heal += Rule.buffmanager.GetBuffValue(player, "plushp");
		},80);
		
		for(int i=20;i>0;i--) {
			Location locs = loc.clone().add(0,0.1*i,0);
			delay(()->{
				player.teleport(locs);
			},100-i);
		}
		delay(()->{
			player.setGameMode(GameMode.SPECTATOR);
			player.teleport(this.target);
		},100);
	}

	@Override
	public boolean tick() {
		ticks++;
		if(!passive && heal >=15) {
			spskillen();
			spskillon();
			Rule.Var.open(player.getName()+".c"+(number%1000)+"Sp",true);
			Rule.playerinfo.get(player).tropy(24,1);
			Rule.c.put(player, new c2400sinobu(player,Rule.gamerule, this));
		}
		if(tk%20 == 0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c24:sk0")+ "]&f : "+ heal + " / 15");
		}
		if(ticks < 20 && ARSystem.getPlayerCount() < 4) {
			ticks+=20;
		}
		if(tk%20 == 0 && shdow > 0) {
			shdow--;
			scoreBoardText.add("&c ["+Main.GetText("c24:sk3")+ "]&f : "+ shdow);
		}
		if(shdow == 0) {
			shdow = -1;
			cooldown[3] = setcooldown[3];
			Rule.buffmanager.selectBuffTime(player, "nodamage",0);
			Rule.buffmanager.selectBuffTime(player, "noattack",0);
			ARSystem.potion(player, 14, 0, 0);
			if(player.getLocation().getBlock().getLightLevel() > 11) player.damage(999,player);
			ARSystem.playSound((Entity)player, "c24s3");
		}
		if(ticks%10==0 && passive) {
			for(Entity e : player.getNearbyEntities(8, 8, 8)) {
				if(e instanceof Player) {
					if(Rule.c.get(e) != null && ((Player)e).getGameMode() == GameMode.ADVENTURE) {
						passive = false;
						player.setGameMode(GameMode.SPECTATOR);
						ARSystem.playSound((Entity)player, "c24p");
						ps = true;
						target = (LivingEntity) e;
						break;
					}
				}
			}
		}
		if(!passive && ps && Rule.c.get(target) == null) {
			ps = false;
			player.teleport(target);
			player.setGameMode(GameMode.ADVENTURE);
			ARSystem.giveBuff(player, new Nodamage(player), 50);
			ARSystem.giveBuff(player, new Silence(player), 50);
			ARSystem.playSound((Entity)player, "c24p2");
		}
		if(!passive && ps && time <= 0) {
			ps = false;
			player.teleport(target);
			player.setGameMode(GameMode.ADVENTURE);
			ARSystem.giveBuff(player, new Nodamage(player), 50);
			ARSystem.giveBuff(player, new Silence(player), 50);
			ARSystem.playSound((Entity)player, "c24p2");
			target = null;
		}
		if(time > 0 && !passive && ps && ARSystem.getPlayerCount() < 3) {
			ticks = 40;
		}
		if(!passive && ps && ticks%20 == 0 && time > 0) {
			time--;
			ARSystem.heal((Player) target,1);
			player.setMaxHealth(player.getMaxHealth()+0.25);
			player.setHealth(player.getMaxHealth());
			skillmult += 0.01;
			damage +=0.0005;
			scoreBoardText.add("&c ["+Main.GetText("c24:ps")+ "]&f : "+ target.getName());
		}
		ticks%=40;
		return false;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
			if(Rule.c.get(e.getEntity()) != null) {
				String s = Main.GetText("c"+Rule.c.get(e.getEntity()).getCode()+":tag");
				if(s.indexOf("tg4") == -1) {
					e.setDamage(e.getDamage()*1.5);
				}
				e.setDamage(e.getDamage() * damage);
			}

		} else {

		}
		return true;
	}
}
