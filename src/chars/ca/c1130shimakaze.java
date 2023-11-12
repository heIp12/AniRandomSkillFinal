package chars.ca;

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
import org.bukkit.event.entity.EntityDamageEvent;
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
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.NoHeal;
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
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c1130shimakaze extends c00main{
	int sk1 = 30;
	int sk3tick = 0;
	
	HashMap<Player,Float> times = new HashMap<>();

	public c1130shimakaze(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1113;
		load();
		text();
		c = this;
		ARSystem.playSound(player, "c113select",1f);
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "0gun5",0.5f);
		
		boolean isair = false;
		for(Entity e : ARSystem.box(player, new Vector(30, 60, 30), box.TARGET)) {
			if(BlockUtil.isAirbone(e.getLocation(), 3)) {
				isair = true;
				break;
			}
		}
		if(isair) {
			skill("c1113_s1-2");
		} else {
			skill("c1113_s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		LivingEntity tg = null;
		boolean isair = false;
		for(Entity e : ARSystem.box(player, new Vector(30, 60, 30), box.TARGET)) {
			if(BlockUtil.isAirbone(e.getLocation(), 3)) {
				isair = true;
				tg = (LivingEntity)e;
				break;
			}
		}
		if(isair) {
			LivingEntity en = tg;
			for(int i =0; i<8; i++) {
				delay(()->{
						en.setNoDamageTicks(0);
						en.damage(1,player);
						ARSystem.playSound((Entity)player, "0gun5",2f);
						ARSystem.spellLocCast(player, en.getLocation(), "c1113_s2");
				},i*2);
			}
		} else {
			for(int i =0; i<5; i++) {
				delay(()->{
					Entity e = ARSystem.boxSOne(player, new Vector(20, 20, 20), box.TARGET);
					if(e != null) {
						LivingEntity en = (LivingEntity)e;
						en.setNoDamageTicks(0);
						en.damage(1,player);
						ARSystem.playSound((Entity)player, "0gun5",2f);
						ARSystem.spellLocCast(player, en.getLocation(), "c1113_s2");
					}
				},i*3);
			}
		}
		return true;
	}
	
	int sk3 = 1;
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c113s3");
		boolean isair = false;
		for(Entity e : ARSystem.box(player, new Vector(50, 80, 50), box.TARGET)) {
			if(BlockUtil.isAirbone(e.getLocation(), 3)) {
				isair = true;
				break;
			}
		}
		if(isair) {
			for(int i=1; i<5; i++) {
				Location l = player.getLocation();
				l.setPitch(0);
				ARSystem.spellLocCast(player, ULocal.offset(l, new Vector(-1, 0, -i)), "c1113_s3-2");
				ARSystem.spellLocCast(player, ULocal.offset(l, new Vector(-1, 0, i)), "c1113_s3-2");
			}
		} else {
			for(int i=1; i<4; i++) {
				Location l = player.getLocation();
				l.setPitch(0);
				ARSystem.spellLocCast(player, ULocal.offset(l, new Vector(-1, 0, -i)), "c1113_s3");
				ARSystem.spellLocCast(player, ULocal.offset(l, new Vector(-1, 0, i)), "c1113_s3");
			}
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(player.getLocation().getBlock().getTypeId() == 8 || player.getLocation().getBlock().getTypeId() == 9) {
			if(player.isSneaking()) {
				player.setVelocity(player.getLocation().getDirection().multiply(1).setY(0.02));
				skill("c1113p");
			}
		}
		
		for(Player p : Rule.c.keySet()) {
			if(!times.containsKey(p)) {
				times.put(p, 0f);
			}
			if(p == player) continue;
			if(BlockUtil.isAirbone(p.getLocation(), 1)) {
				times.put(p,times.get(p)+0.05f);
				if(times.get(p) >= 8 && skillCooldown(0)) {
					times.put(p, 0f);
					spskillon();
					spskillen();
					Location l = player.getLocation();
					l.setPitch(0);
					l = ULocal.offset(l, new Vector(4,4,0));
					l.setPitch(75);
					p.teleport(l);
					
					l = player.getLocation();
					l.setPitch(-35);
					player.teleport(l);
					ARSystem.playSound((Entity)player, "c1113sp");
					ARSystem.giveBuff(p, new Timeshock(p), 100);
					ARSystem.giveBuff(player, new Stun(player), 100);
					ARSystem.giveBuff(player, new Silence(player), 100);
					ARSystem.giveBuff(player, new Nodamage(player), 100);
					delay(()->{
						skill("c1113_sp");
					},20);
					delay(()->{
						p.setNoDamageTicks(0);
						p.damage(10,player);
						ARSystem.playSound((Entity)player, "0gun5",0.4f);
						ARSystem.spellLocCast(player, p.getLocation(), "c1113_sp4");
					},100);
				}
			} else {
				times.put(p,0f);
			}
		}
		return true;
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(BlockUtil.isAirbone(e.getEntity().getLocation(), 3)) {
				e.setDamage(e.getDamage()*3);
			}
		} else {

		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound(player, "c113db",1f);
		return true;
	}
}
