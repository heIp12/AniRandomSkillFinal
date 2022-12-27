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

public class c72plan extends c00main{
	int sk1 = 0;
	int sk2 = 0;
	boolean sk3 = false;
	int sp = 0;
	
	List<Player> move;
	HashMap<Player,Location> moves;
	
	public c72plan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 72;
		load();
		text();
		c = this;
		if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[3] *= 0.4;
		if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[1] *= 0.3;
	}
	public void sk0() {
		spskillen();
		spskillon();
		ARSystem.playSoundAll("c72sp");
		if(ARSystem.gameMode == modes.LOBOTOMY) {
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new Nodamage(p), 200);
			}
			delay(()->{
				for(Player p : Rule.c.keySet()) {
					ARSystem.heal(p, 1000);
				}
				ARSystem.playSoundAll("c72sp1");
			},180);
		} else {
			
			for(Player p : Rule.c.keySet()) {
				p.sendTitle("Don't Move!!!", "");
			}
	
			moves = new HashMap<>();
			move = new ArrayList<>();
			delay(()->{
				sp=160;
				for(Player p : Rule.c.keySet()) {
					moves.put(p, p.getLocation());
				}
			},20);
		}
	}

	@Override
	public boolean skill1() {
		if(sk1 == 0) {
			sk1 = 60;
			skill("c72_s1-1");
			ARSystem.playSound((Entity)player, "c72s11");
			cooldown[1] = 0;
		} else {
			sk1 = 0;
			skill("c72_s1-2");
			ARSystem.playSound((Entity)player, "c72s12");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		sk2 = 60;
		ARSystem.playSound((Entity)player, "c72s21");
		delay(()->{
			ARSystem.playSound((Entity)player, "c72s22");
			for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)){
				LivingEntity en = (LivingEntity) e;
				en.setNoDamageTicks(0);
				if(sk3) {
					delay(()->{en.damage(6*2,player);},2);
					ARSystem.heal(player, 6*2);
				} else {
					en.damage(6,player);
				}
			}
			sk3 = false;
		},60);
		return true;
	}
	@Override
	public void TargetSpell(SpellTargetEvent e, boolean mycaster) {
		if(mycaster && e.getSpell().getName().equals("c72_s01_a")) {
			if(sk3) {
				ARSystem.heal(player, 3*2.5);
				sk3 = false;
			}
		}
		if(mycaster && e.getSpell().getName().equals("c72_s02_a")) {
			if(sk3) {
				ARSystem.heal(player, 6*2.5);
				sk3 = false;
			}
		}
	}
	
	@Override
	public boolean skill3() {
		sk3= true;
		ARSystem.playSound((Entity)player, "c72s3");
		cooldown[1] = 0;
		return true;
	}
	
	@Override
	public boolean tick() {
		if(player.getHealth() / player.getMaxHealth() >=1 && AMath.random(3000) == 1 && skillCooldown(0)) {
			sk0();
			Rule.playerinfo.get(player).tropy(72,1);
		}
		if(sp>0) {
			sp--;
			for(Player p : Rule.c.keySet()) {
				if(p != player &&moves.get(p).distance(p.getLocation()) > 0.2 && !move.contains(p)) {
					move.add(p);
				}
			}
			for(Player p : move) {
				ARSystem.giveBuff(p, new TimeStop(p), 2);
			}
			if(sp == 0) {
				ARSystem.playSoundAll("c72sp1");
				for(Player p : move) {
					ARSystem.spellCast(player, p, "c72_sp_r");
				}
				for(int i=0;i<14;i++) {
					delay(()->{skill("c72_sp0");},i);
				}
				delay(()->{
					for(Player p : move) {
						Rule.buffmanager.selectBuffTime(p, "timestop",0);
						p.setNoDamageTicks(0);
						p.damage(111,player);
					}
				},15);
			}
			
		}
		if(sk1 > 0) {
			sk1--;
			if(sk1==0) cooldown[1] = setcooldown[1];
		}
		if(sk2 > 0) {
			sk2--;
			for(Entity e : ARSystem.box(player, new Vector(16,10,16), box.TARGET)) {
				Location loc = e.getLocation();
				loc = Local.lookAt(loc, player.getLocation());
				if(loc.distance(player.getLocation()) < 5) {
					e.setVelocity(e.getVelocity().add(loc.getDirection().multiply(0.3)));
					ARSystem.giveBuff((LivingEntity)e, new Silence((LivingEntity)e), 10);
				} else if(loc.distance(player.getLocation()) < 8)  {
					e.setVelocity(e.getVelocity().add(loc.getDirection().multiply(0.1)));
				} else {
					e.setVelocity(e.getVelocity().add(loc.getDirection().multiply(0.01)));
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
		} else {
			if(e.getDamager().getLocation().distance(player.getLocation()) > 8) {
				e.setDamage(0);
				ARSystem.playSound((Entity)player, "0miss");
			}
		}
		return true;
	}
	
}
