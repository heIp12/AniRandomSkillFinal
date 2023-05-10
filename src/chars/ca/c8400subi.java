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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.comphenix.protocol.wrappers.EnumWrappers.Particle;
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
import buff.Rampage;
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

public class c8400subi extends c00main{
	boolean sk3 = false;
	double damage = 0;
	
	int tk = 0;
	int count = 0;
	int type = -1;
	int r = 1;
	
	public c8400subi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1084;
		load();
		text();
		c = this;
		skill("c1084_p");
	}
	@Override
	public void setStack(float f) {
		if(f > 1000000) {
			type = (int)(f/1000000)-1;
			count = (int)(f%100000/1000);
			damage = f%1000;
		} else {
			damage = f;
		}
	}

	@Override
	public boolean skill1() {
		if(!player.isSneaking()) {
			ARSystem.playSound((Entity)player, "c1084s12");
			Rule.buffmanager.selectBuffValue(player, "barrier",0);
			cooldown[1] = 1f;
			type = -1;
		} else if(tk <= 0) {
			ARSystem.playSound((Entity)player, "c1084s13");
			delay(()->{
				r++;
				if(r > 2) r = 1;
				if(type < 2) {
					if(type == 0) skill("c1084_s1-"+type);
					if(type == 1) {
						ARSystem.playSound((Entity)player, "0gun5");
						skill("c1084_s1-"+type+""+r);
					}
				} else {
					for(int i=0;i<count;i++) {
						delay(()->{
							r++;
							if(r > 2) r = 1;
							if(type%2 ==1) {
								skill("c1084_s1-"+type+""+r);
								if(type == 3) {
									ARSystem.playSound((Entity)player, "0gun4",0.5f + (count*0.5f));
								} else {
									ARSystem.playSound((Entity)player, "0gun2",1 + (count*0.05f));
								}
							} else {
								skill("c1084_s1-"+type);
								if(type == 2) {
									ARSystem.playSound((Entity)player, "0sword",0.5f + (count*0.5f));
								} else {
									ARSystem.playSound((Entity)player, "0katana4",1 + (count*0.05f));
								}
							}
						},Math.max(1,(int)(60.0/(count*1.0)))*i);
					}
				}
			},20);
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	@Override
	public boolean skill2() {
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		ARSystem.playSound((Entity)player, "c1084s2");
		Rule.buffmanager.selectBuffValue(player, "barrier",30);
		return true;
	}
	
	@Override
	public boolean skill3() {
		sk3 = !sk3;
		return true;
	}
	
	@Override
	public boolean tick() {
		if(sk3) {
			player.setFallDistance(0);
			if(player.isSneaking()) {
				player.setVelocity(new Vector(0,0.001,0));
			} else {
				player.setVelocity(player.getLocation().getDirection());
			}
		}
		scoreBoardText.add("&a ["+Main.GetText("c1084:sk1")+ "] : &b"+ Main.GetText("c1084:s"+type) +" &c["+(damage*count)+"]");
		if(tk == 0) {
			damage/=count;
			damage = AMath.round(damage, 2);
			if(damage < 2) damage = 2;
			player.sendTitle(Main.GetText("c1084:p1"), "");
		}
		tk--;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(damage,player);
		}
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(tk > 0) {
				damage += e.getDamage();
				count++;
				if(count == 3) type+=2;
				if(tk <= 10) tk += 10;
				e.setDamage(e.getDamage() * 0.8);
			} 
			
			if(type == -1) {
				count = 1;
				tk = 20;
				damage = e.getDamage()*2;
				type = 0;
				if(e.getDamager().getLocation().distance(player.getLocation()) > 5) {
					type = 1;
				}
				if(damage < 8) {
					type+=2;
				}
				e.setDamage(e.getDamage() * 0.8);
			}
			if(e.getDamage() > 10) {
				e.setDamage(10);
			}
		}
		
		return true;
	}
}
