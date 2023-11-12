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
import org.bukkit.event.player.PlayerItemHeldEvent;
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
import chars.c.c40megumin;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c89kazuma extends c00main{
	c00main stil = null;
	int sk1 = 0;
	int sk4 = 0;
	int ps = 80;
	int count = 0;
	
	TargetMap<c00main,Double> charlist;
	
	public c89kazuma(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 89;
		load();
		text();
		c = this;
		charlist = new TargetMap<>();
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c89s3");
		sk1 = 33;
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c89s1");
		ARSystem.potion(player, 1, 40, 4);
		ARSystem.potion(player, 14, 10, 1);
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c89s2");
		skill("c89s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		LivingEntity target = null;
		Location loc = player.getLocation().clone();
		loc.add(loc.getDirection().multiply(3));
		for(int i=0;i<27;i++) {
			loc.add(loc.getDirection());
			if(!loc.clone().add(0,1,0).getBlock().isEmpty()) {
				i = 30;
			} else {
				for (LivingEntity e : player.getWorld().getLivingEntities()) {
					if(e.getLocation().distance(loc) <= 6 && e != player) {
						if(Rule.c.get(e) != null) target = e;
					}
				}
			}
			if(target != null) i = 30;
		}
		
		if(target != null && Rule.c.get(target) != null) {
			if(AMath.random(100) <= 5 && skillCooldown(0)) {
				spskillon();
				spskillen();
				Rule.c.get(target).cooldown[1] = 120;
				Rule.c.get(target).cooldown[2] = 120;
				Rule.c.get(target).cooldown[3] = 120;
				Rule.c.get(target).cooldown[4] = 120;
				
				int type = 0;
				String s = Main.GetText("c"+Rule.c.get(target).getCode()+":tag");
				if(s.indexOf("tg2") != -1) type = 1;
				if(s.indexOf("tg1") != -1) type = 2;
				
				if(Rule.c.get(target) instanceof c40megumin){
					type = 3;
				}

				if(type==1) {
					ARSystem.playSoundAll("c89sp2");
					ARSystem.giveBuff(target, new Noattack(target), 200);
					ARSystem.giveBuff(target, new Silence(target), 200);

				}
				if(type == 2) {
					ARSystem.playSoundAll("c89sp");
					ARSystem.giveBuff(target, new Panic(target), 600);
					ARSystem.giveBuff(target, new Silence(target), 600);
					player.teleport(ULocal.lookAt(player.getLocation(), target.getLocation()));
					player.setGameMode(GameMode.SPECTATOR);
					ARSystem.giveBuff(player, new Silence(player), 140);
					skill("c89_sp1");
					Location local = player.getLocation();
					delay(()->{
						player.setGameMode(GameMode.ADVENTURE);
						player.teleport(local);
					},140);

				}
				if(type == 3) {
					ARSystem.playSoundAll("c89spm");
					ARSystem.giveBuff(target, new Stun(target), 400);
					ARSystem.giveBuff(target, new Silence(target), 400);
					player.teleport(ULocal.lookAt(player.getLocation(), target.getLocation()));
					ARSystem.giveBuff(player, new Stun(player), 400);
					ARSystem.giveBuff(player, new Silence(player), 400);

				}
			} else {
				ARSystem.playSound((Entity)player, "c89s4");
				int number = Rule.c.get(target).number;
				while(number%1000 == 5 || number%1000 == 16 || number%1000 == 24 || number%1000 == 57 || number%1000 == 86 || number%1000 == 52 || (number < 1000 && number >= 900)) number = AMath.random(GetChar.getCount());
				
				try {
					stil = GetChar.get(null, Rule.gamerule, ""+number);
				} catch (Exception e) {
					number = AMath.random(GetChar.getCount());
					while(number%1000 == 5 || number%1000 == 16 || number%1000 == 24 || number%1000 == 57 || number%1000 == 86 ||number%1000 == 52 || (number < 1000 && number >= 900)) number = AMath.random(GetChar.getCount());
					stil = GetChar.get(null, Rule.gamerule, ""+number);
				}
				stil.player = player;
				charlist.add(stil, 10);
				sk4 = 200;
			}
			count++;
			if(count>= 15)Rule.playerinfo.get(player).tropy(89,1);
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(stil != null && sk4 > 0) {
			try {
				if(e.getNewSlot()+1 == 1) stil.skill1();
				if(e.getNewSlot()+1 == 2) stil.skill2();
				if(e.getNewSlot()+1 == 3) stil.skill3();
				if(e.getNewSlot()+1 == 4) stil.skill4();
				if(e.getNewSlot()+1 == 5) stil.skill5();
			} catch(Exception ex) {
				
			}
			if(e.getNewSlot()+1 <= 5) {
				sk4 = 0;
				stil = null;
			}
			e.setCancelled(true);
			return true;
		} else {
			return super.key(e);
		}
	}
	
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		for(c00main c : charlist.get().keySet()) {
			try {
				c.makerSkill(target, n);
			} catch (Exception e) {
				
			}
		}
	}
	
	@Override
	public boolean remove(Entity caster) {
		boolean b = true;
		for(c00main c : charlist.get().keySet()) {
			try {
				if(!c.remove(caster)) b = false;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return b;

	}
	
	@Override
	public boolean tick() {
		for(c00main c : charlist.get().keySet()) {
			charlist.add(c, -0.05);
			if(charlist.get(c) <= 0) {
				charlist.removeAdd(c);
			}
		}
		if(sk4 > 0 && stil != null) {
			sk4--;
			try {
				if(sk4 == 0) stil = null;
			} catch(Exception ex) {
				
			}
		}
		if(sk1 > 0 && tk%2 == 0) {
			sk1--;
			Entity entity = ARSystem.boxSOne(player, new Vector(3,3,3), box.TARGET);
			if(entity == null) {
				sk1 = 0;
			} else {
				((LivingEntity)entity).setNoDamageTicks(0);
				((LivingEntity)entity).damage(1,player);
				ARSystem.heal(player, 1);
				ARSystem.giveBuff(player, new Stun(player),2);
				ARSystem.giveBuff((LivingEntity) entity, new Silence((LivingEntity) entity), 2);
			}
		}
		if(stil != null) {
			if(tk%20 == 0) {
				scoreBoardText.add("&c ["+Main.GetText("c89:sk4")+"] : " + Main.GetText("c"+stil.number+":name2"));
			}
		}
		for(c00main c : charlist.get().keySet()) {
			try {
				c.ticks();
			} catch (Exception e) {
				
			}
		}
		charlist.removes();
		return true;
	}
	
	@Override
	public void delayLoop(double time) {
		// TODO Auto-generated method stub
		super.delayLoop(time);
		for(c00main c : charlist.get().keySet()) {
			try {
				c.delayLoop(time);
			} catch (Exception e) {
				
			}
		}
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c89db"+AMath.random(2));
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {

		boolean b = true;
		for(c00main c : charlist.get().keySet()) {
			try {
				if(!c.entitydamage(e, isAttack)) b = false;
			} catch (Exception ee) {
				
			}
		}

		
		if(isAttack) {
			
		} else {
			if(player.getHealth() - e.getDamage() < 1 && AMath.random(100) <= ps) {
				ps-=5;
				ARSystem.playSound((Entity)player, "0miss");
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return b;
	}
}
