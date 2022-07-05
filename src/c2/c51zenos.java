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
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Holo;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c51zenos extends c00main{
	int tick = 0;
	int tick2 = 0;
	int tick3 = 0;
	
	int attack = 0;
	int upgradtick = 1;
	boolean sk[] = new boolean[4];
	
	public c51zenos(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 51;
		load();
		text();
		for(int i=0; i<sk.length;i++) {
			sk[i] = false;
		}
		if(ARSystem.gameMode == modes.ZOMBIE) {
			setcooldown[2] *= 1.5;
			setcooldown[4] *= 2;
		}
	}
	

	@Override
	public boolean skill1() {
		if(sk[0]) {
			skill("c51_s1-2");
		} else {
			skill("c51_s1");
		}
		delay(()->{
			ARSystem.giveBuff(player, new Stun(player), 11);
			ARSystem.giveBuff(player, new Silence(player), 11);
		},10);
		return true;
	}
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c51s2");
		if(sk[1]) {
			ARSystem.potion(player, 1, 140, 2);
			tick = 140;
		} else {
			ARSystem.potion(player, 1, 100, 2);
			tick = 100;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(!sk[2]) {
			ARSystem.giveBuff(player, new Stun(player), 30);
			ARSystem.giveBuff(player, new Silence(player), 30);
			skill("c51_s3");
		} else {
			skill("c51_s3-2");
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(sk[3]) {
			tick2 = 4;
			tick3 = 20;
			attack = 0;
		}
		tick2 += 10;
		ARSystem.playSound((Entity)player, "c51s4");
		return true;
	}
	
	public void upgrad() {
		boolean all = true;
		ARSystem.heal(player, 6);
		for(int i=0; i<sk.length;i++) {
			if(!sk[i]) {
				all = false;
			}
		}
		if(!all) {
			while(true) {
				int i = AMath.random(4)-1;
				if(!sk[i]) {
					sk[i] = true;
					player.sendTitle("§c"+Main.GetText("c51:ps"),"§e"+Main.GetText("c51:sk"+(i+1)));
					return;
				}
			}
		}
	}

	@Override
	public boolean tick() {
		if(tk%20==0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c51:sk0")+ "] : "+ attack +" / 8");
		}
		if(tk%20 == 0) {
			String n = "";
			for(int i=0; i<sk.length;i++) {
				if(sk[i]) {
					n+= Main.GetText("c51:sk"+(i+1))+",";
				}
			}
			scoreBoardText.add("&c ["+Main.GetText("c51:ps")+ "] : "+ n);
		}
		if(tick> 0) tick--;
		if(tick2 > 0) {
			tick2--;
			if(player.isSneaking()) {
				player.setVelocity(player.getLocation().getDirection().multiply(0.1));
			} else {
				player.setVelocity(player.getLocation().getDirection());
			}
		}
		if(tick3 > 0) tick3--;
		if(ARSystem.gameMode2) {
			if(upgradtick%700-100*(skillmult + sskillmult) == 0) {
				upgrad();
			}
		} else {
			if(upgradtick%400-60*(skillmult + sskillmult) == 0) {
				upgrad();
			}
		}
		upgradtick++;
		return true;
	}

	@Override
	public void TargetSpell(SpellTargetEvent e, boolean mycaster) {
		if(mycaster) {
			if(sk[2] && e.getSpell().getName().equals("c51_s31") && Rule.c.get(e.getTarget()) != null) {
				Rule.buffmanager.selectBuffValue(e.getTarget(), "barrier",0);
			}
		}
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player && upgradtick < 600) {
			Rule.playerinfo.get(player).tropy(51,1);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(tick3 > 0) {
				attack++;
				e.setDamage(e.getDamage()*2);
				Holo.create(e.getEntity().getLocation(), "§e<Critical>", 40, new Vector(0, -0.1, 0));
				if(attack >= 8 && skillCooldown(0)) {
					spskillon();
					spskillen();
					ARSystem.spellCast(player, e.getEntity(), "c51_sp");
				}
			}
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
		} else {
			if(!sk[1]&& tick > 0 && AMath.random(100) <= 70) {
				e.setCancelled(true);
				ARSystem.playSound((Entity)player, "0miss");
				Holo.create(e.getEntity().getLocation(), "§e<miss>", 10, new Vector(0, -0.1, 0));
				return false;
			}
			if(sk[1]&& tick > 0 && AMath.random(100) <= 50) {
				e.setCancelled(true);
				ARSystem.playSound((Entity)player, "0miss");
				Holo.create(e.getEntity().getLocation(), "§e<miss>", 10, new Vector(0, -0.1, 0));
				return false;
			}
		}
		return true;
	}
}
