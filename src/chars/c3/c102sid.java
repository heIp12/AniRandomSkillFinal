package chars.c3;

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

public class c102sid extends c00main{
	boolean pvp = false;
	int stack = 0;
	double damage = 0;
	int damagetick = 0;
	
	public c102sid(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 102;
		load();
		text();
		c = this;
	}

	@Override
	public void setStack(float f) {
		stack = (int) f;
	}
	
	@Override
	public boolean skill1() {
		skill("c102_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		List<Entity> entitys = ARSystem.box(player, new Vector(8,8,8), box.ALL);
		if(entitys.size() > 0) {
			ARSystem.playSound((Entity)player, "c102s2"+AMath.random(3));
			for(Entity en : entitys) {
				ARSystem.giveBuff((LivingEntity) en, new Silence((LivingEntity) en), 60);
				if(pvp) if(cooldown[1] > 0) cooldown[1] -= 1.5;
			}
			if(!pvp && entitys.size() > 1) {
				stack+=3;
				Rule.playerinfo.get(player).tropy(102, 1);
			}
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(damagetick > 0 && damage >= 1) {
			ARSystem.playSound((Entity)player, "c102s3"+AMath.random(3));
			ARSystem.heal(player, damage*2);
			if(!pvp) stack++;
			damagetick = 0;
			cooldown[3] = 0;
			player.setVelocity(player.getLocation().getDirection().multiply(-1.5).setY(0.5));
		}
		return true;
	}
	

	@Override
	public boolean tick() {
		if(damagetick > 0) damagetick--;
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c102:p")+ "] : "+ stack);
		}
		if(!isps && stack >= 20 && skillCooldown(0)) {
			pvp = true;
			ARSystem.giveBuff(player, new TimeStop(player), 400);
			ARSystem.playSoundAll("c102sp");
			skill("c102_sp1");
			delay(()->{
				skill("c102_sp2");
				delay(()->{
					skill("c102_sp4");
				},40);
			},100);
			delay(()->{
				skill("c102_sp3");
				delay(()->{
					skill("c102_sp5");
				},40);
			},200);
			delay(()->{
				for(Player p : Bukkit.getOnlinePlayers()) {
					ARSystem.giveBuff(p, new Stun(p), 30);
					ARSystem.spellLocCast(player, p.getLocation(), "c102_room");
				}
				delay(()->{
					ARSystem.playSoundAll("c102sp2");
				},10);
				delay(()->{
					ARSystem.playSoundAll("c102sp2");
				},30);
			},360);
			delay(()->{
				for(int i = 0; i<5;i++) {
					int j = i;
					delay(()->{
						ARSystem.playSoundAll("0explod",1.5f - (0.2f*j));
					},i*5);
				}
				for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
					((LivingEntity)e).setHealth(1);
					((LivingEntity)e).setMaxHealth(1);
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(999,player);
				}
			},400);
		}
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() * (1+stack*0.1));
			if(pvp == false) {
				pvp = true;
				ARSystem.playSound((Entity)player, "c102ps");
				ARSystem.giveBuff(player, new Nodamage(player), 100);
			}
		} else {
			damage = e.getDamage();
			damagetick = 8;
			if(pvp) {
				for(int i=0;i<stack;i++) {
					e.setDamage(e.getDamage() * 0.9);
				}
			}
		}
		return true;
	}
	
}
