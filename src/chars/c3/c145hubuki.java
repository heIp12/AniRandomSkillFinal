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
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
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
import ars.gui.G_Saito;
import buff.Airborne;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.Ice;
import buff.NoCC;
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
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c.c39sakuya;
import chars.c.c45momo;
import chars.c2.c60gil;
import chars.c2.c69himi;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c145hubuki extends c00main{
	int s1 = 3;
	int s1t = 0;
	int s2 = 0;
	int p = 0;
	
	public c145hubuki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 145;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		if(s1 > 0 && player.isSneaking()) {
			s1--;
			skill("c145_s1");
		} else {
			skill("c145_s1_2");
		}
		return true;
	}

	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c145s2");
		ARSystem.potion(player, 1, 14, 10);
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c145s3");
		delay(()->{
			s2 = 10;
		},10);
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c145s4");
		ARSystem.giveBuff(player, new Stun(player), 40);
		ARSystem.giveBuff(player, new Silence(player), 40);
		skill("c145_s4");
		delay(()->{
			player.teleport(ULocal.offset(player.getLocation().clone(),new Vector(-1,2,0)));
		},20);
		return true;
	}

	@Override
	public boolean tick() {
		if(s1 < 3) {
			s1t+= skillmult + sskillmult;
			if(s1t >= 160) {
				s1++;
				s1t = 0;
			}
		}
		if(s2 > 0) {
			s2--;
			Location lc = player.getLocation();
			lc.setPitch(0);
			player.setVelocity(lc.getDirection().multiply(1.5));
			skill("c145_s3");
			for(Entity en : ARSystem.box(player, new Vector(5,4,5), box.TARGET)) {
				LivingEntity e = (LivingEntity)en;
				ARSystem.giveBuff(e, new Ice(e, player), 40);
				ARSystem.playSound(e, "0ice", 1 , 0.25f);
			}
			if(s2 <= 0) {
				player.setVelocity(new Vector(0,0,0));
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c145:ps")+"] : " + s1+ " ("+AMath.round((160-s1t)*0.05,2)+")");
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			target.setVelocity(player.getLocation().getDirection().multiply(0.85f));
		}
		if(n.equals("2")) {
			double hp = target.getHealth();
			target.setNoDamageTicks(0);
			target.damage(7,player);
			ARSystem.playSound((Entity)target, "0ice");
			ARSystem.giveBuff(target, new Ice(target,player), 140);
			delay(()->{
				if(target.getHealth() >= hp && (!(target instanceof Player)||(target instanceof Player && ((Player)target).getGameMode() != GameMode.SPECTATOR)) &&skillCooldown(0)) {
					spskillon();
					spskillen();
					p++;
					if(p >= 2) Rule.playerinfo.get(player).tropy(145, 1);
					ARSystem.playSound((Entity)player, "c145sp");
					ARSystem.giveBuff(player, new Stun(player), 80);
					ARSystem.giveBuff(player, new Silence(player), 80);
					ARSystem.giveBuff(player, new Nodamage(player), 100);
					skill("c145_sp1");
					Location loc = player.getLocation();
					loc = ULocal.lookAt(loc, target.getLocation());
					player.teleport(loc);
					Location lc = loc;
					lc.setPitch(0);
					
					delay(()->{
						player.teleport(ULocal.offset(player.getLocation().clone(),new Vector(0,2,0)));
					},20);
					delay(()->{
						player.teleport(ULocal.offset(lc,new Vector(2,0,0)));
						ARSystem.spellLocCast(player, lc, "c145_sp2");
						ARSystem.spellLocCast(player, lc, "c145_sp3");
						if(target != null) {
							for(int i =0; i<100; i++) {
								delay(()->{
									ARSystem.spellLocCast(player, target.getLocation(), "c145_tg");
								},i);
							}
						}
					},40);
				}
			},0);
		}
		if(n.equals("3")) {
			ARSystem.fixedDamage(target, player, 16);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			
		}
		return true;
	}
}
