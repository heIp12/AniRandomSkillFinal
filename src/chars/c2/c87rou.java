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

public class c87rou extends c00main{
	boolean room = false;
	Location room_Loc;
	LivingEntity heart = null;
	List<LivingEntity> entitys = new ArrayList<>();
	int count = 0;
	
	int tick = 0;
	
	public c87rou(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 87;
		load();
		text();
		c = this;
		
	}
	
	@Override
	public boolean hpCost(double d, boolean death) {
		if(heart == null) {
			return super.hpCost(d, death);
		} else {
			if(heart.getHealth() - d < 1) {
				heart.damage(d+1,player);
			} else {
				heart.setHealth(heart.getHealth() - d);
			}
			if(((Player)heart).getGameMode() == GameMode.SPECTATOR) {
				heart = null;
				tick = 0;
			}
			return true;
		}
	}

	@Override
	public boolean skill1() {
		if(room && entitys.size() > 0) {
			ARSystem.playSound((Entity)player, "c87s1");
			LivingEntity target = entitys.get(AMath.random(entitys.size())-1);
			ARSystem.playSound((Entity)target, "c87s12");
			if(Rule.c.get(target) != null) {
				Rule.c.get(target).cooldown[AMath.random(4)] = 60;
				ARSystem.giveBuff(target, new Silence(target), 100);
				ARSystem.potion(target, 2, 100, 100);
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(room && entitys.size() > 0) {
			hpCost(2,true);
			ARSystem.playSound((Entity)player, "c87s2");
			skill("c87_s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(room) {
			hpCost(1,true);
			ARSystem.playSound((Entity)player, "c87s3");
			Location loc = player.getLocation().subtract(room_Loc);
			player.teleport(room_Loc.clone().subtract(loc));
			for(LivingEntity e : entitys) {
				loc = e.getLocation().subtract(room_Loc);
				e.teleport(room_Loc.clone().subtract(loc));
			}
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(room && entitys.size() > 0) {
			hpCost(2,true);
			ARSystem.playSound((Entity)player, "c87s4");
			skill("c87_s4");
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(entitys.contains(target)) {
			if(n.equals("1")) {
				ARSystem.giveBuff(target, new NoHeal(target), 400);
				for(int i = 0; i<3;i++) {
					delay(()->{
						target.setNoDamageTicks(0);
						target.damage(2,player);
					},3*i);
				}
			}
			if(n.equals("2")) {
				ARSystem.playSound((Entity)player, "c87s42");
				target.setNoDamageTicks(0);
				target.damage(target.getMaxHealth()*0.35,player);
			}
		}
	}
	
	@Override
	public boolean tick() {
		if(tk%20==0&&psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c87:sk0")+ "]&f : " +AMath.round(tick*0.05,2));
		}
		if(tick > 0) {
			tick--;
			if(tick == 0) {
				spskillen();
				spskillon();
				ARSystem.giveBuff(heart, new TimeStop(heart), 80);
				ARSystem.giveBuff(player, new TimeStop(player), 80);
				ARSystem.playSound(heart, "c87sp");
				ARSystem.playSound((Entity)player, "c87sp");
				delay(()->{
					ARSystem.spellLocCast(player, heart.getLocation(), "bload");
					Skill.remove(heart, player);
					ARSystem.heal(player, 200);
				},70);
			}
		}
		if(player.isSneaking() && !room) {
			room = true;
			skill("c87_room");
			ARSystem.playSound((Entity)player, "c87p");
			room_Loc = player.getLocation();
		}
		else if((!player.isSneaking() || player.getLocation().distance(room_Loc)>6) && room) {
			room = false;
			skill("c87_room_Remove");
		}
		if(room) {
			entitys = new ArrayList<LivingEntity>();
			for(Entity e : player.getWorld().getNearbyEntities(room_Loc, 6, 6, 6)) {
				if(ARSystem.isTarget(e, player, box.TARGET)) {
					ARSystem.spellLocCast(player, e.getLocation(), "c87_e");
					entitys.add((LivingEntity) e);
				}
			}
			
		}
		if(room && tk%10 == 0) {
			hpCost(0.25,true);
		}
		
		if(tk%20 == 0 && heart != null) {
			scoreBoardText.add("&c ["+Main.GetText("c87:ps")+"] : " + heart.getName());
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c87:sk0")+"] : " + AMath.round(tick,2));
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
			if(e.getDamage() < 2 && heart == null && entitys.contains(e.getEntity())) {
				if(Rule.c.get(e.getEntity()) != null) {
					String s = Main.GetText("c"+Rule.c.get(e.getDamager()).getCode()+":tag");
					if(s.indexOf("tg4") != -1) {
						heart = (LivingEntity) e.getEntity();
						ARSystem.playSound((Entity)heart, "c87p2");
						tick = 600;
						count++;
						if(count >=2)Rule.playerinfo.get(player).tropy(87,1);
					}
				}
			}
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.25);
			if(room && !entitys.contains(e.getDamager())) {
				e.setDamage(0);
			}
		}
		return true;
	}
}
