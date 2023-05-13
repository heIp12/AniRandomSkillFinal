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

public class c101aris extends c00main{
	int sk3 = 0;
	float ps = 0;
	LivingEntity en;
	int tr = 0;
	
	@Override
	public void setStack(float f) {
		if( 1111 == (int)f) {
			sp();
		}
	}
	public void sp() {
		if(!isps) {
			spskillon();
			spskillen();
			for(int i=1; i<10; i++) cooldown[i] = 0;
			setcooldown[1] = setcooldown[2] = setcooldown[3] = 2;
		}
	}
	
	public c101aris(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 101;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		
		skill("c101_s1-2");
		ARSystem.playSound((Entity)player, "0slash", 1.5f);
		for(Entity en : ARSystem.box(player, new Vector(5,3,5), box.TARGET)) {
			((LivingEntity)en).setNoDamageTicks(0);
			((LivingEntity)en).damage(2,player);
		}
		delay(()->{
			skill("c101_s1-3");
			ARSystem.playSound((Entity)player, "0slash", 1.6f);
			for(Entity en : ARSystem.box(player, new Vector(5,3,5), box.TARGET)) {
				((LivingEntity)en).setNoDamageTicks(0);
				((LivingEntity)en).damage(2,player);
			}
		},2);
		if(isps) {
			delay(()->{
				skill("c101_s1-1");
				ARSystem.playSound((Entity)player, "0slash", 1.7f);
				for(Entity en : ARSystem.box(player, new Vector(5,3,5), box.TARGET)) {
					((LivingEntity)en).setNoDamageTicks(0);
					((LivingEntity)en).damage(2,player);
				}
			},4);
		}
		
		return true;
	}
	
	@Override
	public boolean skill2() {
		List<Entity> entitys = ARSystem.PlayerBeamBox(player, 10, 5, box.ALL);
		if(entitys != null && entitys.size() > 0) {
			LivingEntity en = (LivingEntity) entitys.get(0);
			if(en != null) {
				this.en = en;
				cooldown[3] = 0.5f;
				ARSystem.addBuff(player, new Silence(player), 10);
				ARSystem.playSound((Entity)player, "c101s2");
				ARSystem.addBuff(en, new Stun(en), 20);
				ARSystem.addBuff(en, new Silence(en), 20);
				ARSystem.giveBuff(en, new Noattack(en), 20);
				for(int i = 0; i< 10; i++) {
					int j = i;
					float yaw = ULocal.lookAt(player.getLocation(),en.getLocation()).getYaw();
					tpsdelay(()->{
						Location local = en.getLocation().clone();
						if(j < 5) {
							local.setYaw(yaw);
							local.setPitch(-18*j);
						} else {
							local.setYaw(yaw+180);
							local.setPitch(-90+(18*(j-5)));
						}
						player.teleport(ULocal.lookAt(ULocal.offset(en.getLocation(), local.clone().getDirection().multiply(2)),en.getLocation()));
					},i);
				}
				if(isps) {
					delay(()->{
						skill("c101_s1-1");
						ARSystem.playSound((Entity)player, "0slash", 2);
						for(Entity ent : ARSystem.box(player, new Vector(5,3,5), box.TARGET)) {
							((LivingEntity)ent).setNoDamageTicks(0);
							((LivingEntity)ent).damage(3,player);
						}
					},10);
				}
				return true;
			}
			tr++;
			if(tr >=20) Rule.playerinfo.get(player).tropy(101, 1);
		}
		cooldown[2] = 0;
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(en != null) {
			player.setVelocity(ULocal.lookAt(player.getLocation(), en.getLocation()).getDirection().multiply(new Vector(2,0,2)));
			ARSystem.playSound((Entity)player, "c101s3");
			if(cooldown[1] > 0) {
				cooldown[1] = 0;
				setcooldown[1] *= 1.08;
				setcooldown[2] *= 1.08;
				setcooldown[3] *= 1.08;
			}
		} else {
			player.setVelocity(player.getLocation().getDirection().multiply(1.4).setY(0));
			ARSystem.playSound((Entity)player, "c101s3");
		}
		return true;
	}
	

	@Override
	public boolean tick() {
		if(sk3 > 0) sk3--;
		if(tk%20 == 0) {
			if(en != null) scoreBoardText.add("&c ["+Main.GetText("c101:sk3")+ "] : "+ en.getName());
			scoreBoardText.add("&c ["+Main.GetText("c101:ps")+ "] : "+ (ps>0));
		}
		if(ps%10 == 0) {
			if(ps > 0) {
				for(int i=0;i<10;i++) {
					if(cooldown[i] > 0) cooldown[i] -= 0.25f;
				}
			} else {
				for(int i=0;i<10;i++) {
					if(cooldown[i] > 0) cooldown[i] += 0.25f;
				}
			}
		}
		if(en != null) {
			if(en instanceof Player && Rule.c.get(en) == null) en = null;
			else if(en.isDead()) en = null;
		}
		ps--;
		
		for(int i =1; i<10; i++) {
			if(cooldown[i] > 15 && skillCooldown(0)) {
				sp();
			}
		}
		if(isps && tk%10 == 0) {
			if(!MSUtil.isbuff(player, "c101_sp")) {
				skill("c101_sp");
			}
		}
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			en = (LivingEntity) e.getEntity();
			ps = 100;
			if(isps) e.setDamage(e.getDamage() + 1);
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*5);
		} else {
			ps = 100;
			if(isps&&AMath.random(100) <= 60) {
				ARSystem.playSound((Entity)player, "0miss", 1.8f);
				e.setDamage(0);
				e.setCancelled(true);
			}
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.5);
		}
		return true;
	}
	
}
