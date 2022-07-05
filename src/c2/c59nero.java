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
import buff.Cindaella;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Holo;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c59nero extends c00main{
	int sk1 = 0;
	int sk3 = 0;
	int sp = 0;
	boolean ps = false;
	boolean sirosp = false;
	
	public c59nero(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 59;
		load();
		text();
		c = this;
	}
	

	@Override
	public boolean skill1() {
		if(isps) {
			sk1++;
			ARSystem.playSound((Entity)player, "c59s1"+sk1);
			if(sk1 >= 3) {
				skill("c59_s12");
				sk1 = 0;
			} else {
				skill("c59_s1");
			}
		} else {
			skill("c59_s1");
			ARSystem.playSound((Entity)player, "c59s11");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c59_s2");
		ARSystem.playSound((Entity)player, "c59s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c59_s3");
		sk3 = 160;
		ARSystem.playSound((Entity)player, "c59s3");
		return true;
	}
	
	public void skill0(){
		spskillon();
		spskillen();
		Rule.playerinfo.get(player).tropy(59,1);
		
		ps = true;
		setcooldown[1]/=2;
		ARSystem.playSoundAll("c59sp");
		s_score += 1000;
		
		ARSystem.giveBuff(player, new TimeStop(player), 275);

		delay(new Runnable() {
			@Override
			public void run() {
				skill("c59_spe2");
				player.performCommand("tm anitext all SUBTITLE true 44 c59:t1/"+player.getName());
			}
		},0);
		delay(new Runnable() {
			@Override
			public void run() {
				skill("c59_spe2");
				player.performCommand("tm anitext all SUBTITLE true 72 c59:t2/"+player.getName());
			}
		},44);
		delay(new Runnable() {
			@Override
			public void run() {
				skill("c59_spe2");
				player.performCommand("tm anitext all SUBTITLE true 67 c59:t3/"+player.getName());
			}
		},116);
		delay(new Runnable() {
			@Override
			public void run() {
				skill("c59_spe2");
				player.performCommand("tm anitext all SUBTITLE true 90 c59:t4/"+player.getName());
			}
		},183);
		
		delay(new Runnable() {
			@Override
			public void run() {
				ARSystem.heal(player,100);
				player.performCommand("tm anitext all SUBTITLE true 20 c59:t6/c59:t5");
				if(ARSystem.gameMode2) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						Map.getMapinfo(1002);
						Map.playeTp(p);
					}
				}
			}
		},273);
		
	}
	
	@Override
	public void PlayerSpCast(Player p) {
		if(Rule.c.get(p).getCode() == 30) {
			sirosp = true;
		}
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c59:sk0")+ "] : "+ AMath.round(sp*0.05,0) +" / 5");
		}
		if(!isps) {
			if(AMath.random(800) == 2) {
				player.setNoDamageTicks(1);
				player.damage(1);
			}
			if(AMath.random(1000) == 5) {
				ARSystem.addBuff(player, new Stun(player), 20);
			}
			if(player.getHealth() < 3 && !sirosp) {
				sp++;
				if(!ARSystem.gameMode2) sp+=2;
				if(sp > 100 && !isps) {
					skill0();
				}
			}
		} else {
			if(tk%20 == 0) {
				ARSystem.heal(player, 1);
				skill("c59_spe");
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(sk3 > 0 && !isps) ARSystem.heal(player,e.getDamage()*0.3);
			if(sk3 > 0 && isps) ARSystem.heal(player,e.getDamage()*1.2);
		} else {
			if(ps && player.getHealth() - e.getDamage() <= 1) {
				ps = false;
				e.setDamage(0);
				ARSystem.addBuff(player, new Nodamage(player), 60);
				player.setHealth(player.getMaxHealth()/2);
				skill("c59_p");
			}
		}
		return true;
	}
}
