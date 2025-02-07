package chars.ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
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
import buff.Buff;
import buff.MapVoid;
import buff.MindControl;
import buff.Noattack;
import buff.Nodamage;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.MapType;
import types.box;

import util.AMath;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class h007anduin extends c00main{

	int db = 1;
	
	public h007anduin(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 993;
		load();
		text();
		ARSystem.playSound((Entity)player, "anduinselect");
	}
	
	@Override
	public boolean skill1() {
		List<Entity> en = ARSystem.PlayerBeamBox(player, 20, 5, box.TARGET);
		if(en.size() > 0) {
			LivingEntity e = (LivingEntity)en.get(0);
			if(!player.isSneaking()) {
				skill("canduinsc-1");
				ARSystem.playSound((Entity)player, "anduins12");
				ARSystem.spellCast(player, e, "canduins1-1");
			} else {
				if(e.getHealth() <= 3) {
					ARSystem.playSound((Entity)player, "anduins11");
					delay(()->{
						skill("canduinsc-2");
						ARSystem.spellCast(player, e, "canduins1-2");
					},15);
				} else {
					cooldown[1] = 0;
				}
				
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		int r = AMath.random(3);
		skill("canduins2-"+r);
		if(r == 1) {
			ARSystem.playSound((Entity)player, "anduins21s1");
			delay(()->{
				ARSystem.playSound((Entity)player, "anduins21a1");
			},40);
		}
		else if(r == 2) {
			ARSystem.playSound((Entity)player, "anduins22s"+AMath.random(9));
			delay(()->{
				ARSystem.playSound((Entity)player, "anduins22a"+AMath.random(9));
			},60);
		}
		else if(r == 3) {
			ARSystem.playSound((Entity)player, "anduins23s"+AMath.random(3));
			delay(()->{
				ARSystem.playSound((Entity)player, "anduins23a1");
			},80);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "anduins3");
		skill("canduins3");
		ARSystem.heal(player, 1000);
		return true;
	}
	
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c993:t0")+ "] &e "+ Main.GetText("c993:t"+db));
		}
		
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			ARSystem.playSound((Entity)player, "anduins3");
		}
	}

	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(3,player);
		}
		if(n.equals("2")) {
			Skill.remove(target, player);
		}
		super.makerSkill(target, n);
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() > 1) {
				LivingEntity en = (LivingEntity)e.getEntity();
				if(en.getMaxHealth() - (e.getDamage()*0.25) > 1) {
					en.setMaxHealth(en.getMaxHealth() - e.getDamage()*0.25f);
					player.setMaxHealth(player.getMaxHealth() + e.getDamage()*0.25f);
					ARSystem.heal(player, e.getDamage()*0.25);
				}
				if(Rule.c.get(e.getEntity()) != null) {
					for(int i =0; i<10; i++) {
						if(cooldown[i] > 0) cooldown[i] -= 0.5f;
						Rule.c.get(e.getEntity()).cooldown[i] += 0.5f;
					}
				}
			}
		} else {

		}
		return true;
	}
	
	@Override
	public boolean skill9(){
		if(player.isSneaking()) {
			db++;
			if(db > 6) db = 1;
			player.sendTitle("", Main.GetText("c993:t"+db),0,20,20);
			cooldown[9] = 0;
		} else {
			ARSystem.playSound((Entity)player, "anduindb"+db);
		}
		return true;
	}
}
