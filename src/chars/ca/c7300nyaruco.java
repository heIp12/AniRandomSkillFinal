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
import util.Text;

public class c7300nyaruco extends c00main{
	int attime = 0;
	int at = 0;
	int maxat = 20;
	int t = 0;
	boolean type = false;

	@Override
	public void setStack(float f) {
		at = (int)f;
		super.setStack(f);
	}
	
	public c7300nyaruco(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1073;
		load();
		text();
		c = this;
		ARSystem.playSound(player, "c73select");
	}

	@Override
	public boolean skill1() {
		List<Entity> entitys = ARSystem.PlayerBeamBox(player, 14, 3, box.TARGET);
		if(entitys != null && entitys.size() > 0) {
			LivingEntity en = (LivingEntity) entitys.get(0);
			if(en != null) {
				player.setVelocity(ULocal.lookAt(player.getLocation().clone(), en.getLocation()).getDirection().multiply(2.5f));
				ARSystem.giveBuff(en, new Noattack(en), 30);
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c73s2");
		at+= 5;
		if(isps) at+=5;
		ARSystem.potion(player, 1, 20, 9);
		if(at > maxat) at = maxat;
		return true;
	}
	
	@Override
	public boolean skill3() {
		type= !type;
		if(type) player.sendTitle(Text.get("c1073:t1"), "");
		else player.sendTitle(Text.get("c1073:t2"), "");
		return true;
	}

	
	
	@Override
	public boolean tick() {
		t++;
		if(t%40 == 0 && at < maxat) at+= (skillmult +sskillmult);
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.getTime() >= 60 && !isps) {
			spskillen();
			spskillon();
			ARSystem.playSound((Entity)player, "c73sp");
			maxat = 100;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c [Attack] : "+ at +" / " + maxat);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() >= 1 && at >= 3) {
				int k = at;
				at = 0;
				LivingEntity en = (LivingEntity)e.getEntity();
				if(!type) ARSystem.playSound((Entity)player, "c73s12");
				else delay(()->{ARSystem.playSound((Entity)player, "c73s11");},10);
				ARSystem.giveBuff(en, new TimeStop(en), 40);
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				Location ll = player.getLocation();
				ll.setPitch(0);
				player.teleport(ll);
				en.teleport(ULocal.lookAt(ULocal.offset(player.getLocation().clone(), new Vector(3,0,0)), player.getLocation()));
				delay(()->{
					skill("c73_s1");
					delay(()->{ARSystem.playSound(en, "0explod" , 0.4f, 0.8f);},10);
				},20);
				delay(()->{
					int speed = 3;
					if(isps) speed = 1;
					for(int i=0; i<k;i++) {
						delay(()->{
							ARSystem.giveBuff(player, new Stun(player), 4);
							ARSystem.giveBuff(player, new Nodamage(player), 4);
							ARSystem.playSound(en, "0sword" , 0.5f + AMath.random(10)*0.1f, 0.25f);
							en.setNoDamageTicks(0);
							en.damage(0.9,player);
							if(isps) ARSystem.heal(player, 0.3f);
							if(type) {
								en.setVelocity(ULocal.lookAt(player.getLocation().clone(), en.getLocation()).getDirection().multiply(2).setY(0.45f));
								ARSystem.spellCast(player,en ,"c1073_tg");
							} else {
								Location l = en.getLocation();
								l.setPitch(0);
								en.teleport(l);
								ARSystem.giveBuff(en, new Stun(en), 4);
								ARSystem.giveBuff(en, new Silence(en), 4);
								player.teleport(ULocal.lookAt(ULocal.offset(en.getLocation().clone(), new Vector(2,0,0)), en.getLocation()));
								skill("c1073_t"+AMath.random(2));
								ARSystem.spellCast(player,en ,"c1073_tg2");
							}
						},i*speed);
					}
				},40);
			}
		} else {
			
		}
		return true;
	}
	
}
