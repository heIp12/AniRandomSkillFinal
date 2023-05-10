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

public class c85maka extends c00main{
	boolean pp = false;
	int p = 0;
	int sk1 = 0;
	int sk2 = 0;
	List<LivingEntity> entitys;
	
	@Override
	public void setStack(float f) {
		p = (int)f;
	}
	
	public c85maka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 85;
		load();
		text();
		c = this;
		
	}

	@Override
	public boolean skill1() {
		if(sk1 > 1) {
			sk1--;
			delay(()->{
				float cooldown1 = cooldown[1];
				cooldown[1] = 0;
				int ss = sk1;
				delay(()->{
					if(ss == sk1) {
						cooldown[1] = cooldown1;
						sk1 = 4;
					}
				},2);
			},6);
		}
		if(sk1 == 0) sk1 = 4;
		skill("c85_s1");
		ARSystem.playSound((Entity)player, "0katana4");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c85_s2");
		sk2 = 40;
		ARSystem.giveBuff(player, new Silence(player), 30);
		ARSystem.playSound((Entity)player, "c85s3");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c85_s3");
		ARSystem.giveBuff(player, new Silence(player), 30);
		ARSystem.playSound((Entity)player, "c85s2");
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(pp) {
			ARSystem.giveBuff(player, new TimeStop(player), 15);
			ARSystem.giveBuff(player, new Silence(player), 25);
			pp = false;
			p = 0;
			skill("c85_s4");
			entitys = new ArrayList<>();
			Rule.buffmanager.selectBuffValue(player, "barrier", 0);
			delay(()->{
				List<LivingEntity> e = new ArrayList<>();
				for(LivingEntity en : entitys) {
					if(en != null && en.isValid() && Rule.c.get(en) != null) {
						e.add(en);
					}
				}
				if(e.size() > 0) {
					entitys = new ArrayList<>();
					spskillon();
					spskillen();
					for(LivingEntity en : e) {
						en.teleport(ULocal.offset(player.getLocation(), new Vector(-5,0,0)));
						Rule.c.put((Player) en, new c000humen((Player) en, plugin, null));
						ARSystem.addBuff(en,new TimeStop(en), 300);
						delay(()->{ARSystem.addBuff(en,new TimeStop(en), 300);},20);
						delay(()->{ARSystem.addBuff(en,new TimeStop(en), 300);},40);
						delay(()->{ARSystem.addBuff(en,new TimeStop(en), 300);},60);
					}
					player.teleport(ULocal.lookAt(player.getLocation(), ULocal.offset(player.getLocation(), new Vector(-5,0,0))));
					skill("c85_sp");
					ARSystem.giveBuff(player, new TimeStop(player), 120);
					ARSystem.giveBuff(player, new Silence(player), 25);
				}
			},40);
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(!entitys.contains(target)) {
				entitys.add(target);
				Skill.remove(target, player);
				ARSystem.playSound(target,"0slash",2);
				if(entitys.size() > 3) Rule.playerinfo.get(player).tropy(85,1);
			}
		}
	}
	
	@Override
	public boolean tick() {
		if(sk2 > 0) {
			if(sk2%5 == 0) {
				for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(1,player);
				}
			}
			sk2--;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c85:ps")+"] : " + p);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(pp) e.setDamage(e.getDamage()*1.5f);
			p += e.getDamage()*4;
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
			if(p >= 100) {
				p = 100;
				if(!pp) {
					pp = true;
					ARSystem.playSound((Entity)player, "c85p");
					ARSystem.heal(player, 99999);
					Rule.buffmanager.selectBuffValue(player, "barrier", (float) player.getMaxHealth());
					if(ARSystem.isGameMode("lobotomy")) Rule.buffmanager.selectBuffValue(player, "barrier", (float) player.getMaxHealth() * 3);
				}
			}
			
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.5);
			if(sk2 > 0 && e.getDamager().getLocation().distance(player.getLocation()) >= 5) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
