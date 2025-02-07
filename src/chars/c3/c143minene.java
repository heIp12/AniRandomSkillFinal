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

public class c143minene extends c00main{
	int spt = 0;
	int s1 = 0;
	Location lc;
	public c143minene(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 143;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c143s1");
		s1 = 12;
		lc = player.getLocation();
		lc.setPitch(0);
		return true;
	}

	@Override
	public boolean skill2() {
		if(s1 > 0) {
			cooldown[2] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "c143s2");
		skill("c143_s2");
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(s1 > 0) {
			cooldown[3] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "c143s3");
		player.setVelocity(new Vector(0,1,0));
		delay(()->{
			skill("c143_s3");
		},5);
		return true;
	}
	
	@Override
	public boolean tick() {
		if(spt > 0) {
			spt--;
			player.setFallDistance(0);
			player.setVelocity(player.getLocation().getDirection().multiply(3));
		}
		if(s1 > 0) {
			s1--;
			player.setVelocity(lc.getDirection());
			if(s1%3 == 0) {
				ARSystem.spellLocCast(player, ULocal.offset( player.getLocation().clone(),new Vector(0,0,-1.7)), "c143_s1");
				ARSystem.spellLocCast(player, ULocal.offset( player.getLocation().clone(),new Vector(0,0,1.7)), "c143_s1");
			}
			if(s1 <= 0) {
				player.setVelocity(new Vector(0,0.25,0));
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC).size() > 0 && skillCooldown(0)) {
				spskillon();
				spskillen();
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.playSoundAll("c143sp");
				for(Buff b : Rule.buffmanager.getBuffs(player).getBuff()) b.setTime(0);
				player.teleport(player.getLocation().clone().add(new Vector(0,2,0)));
				for(int i =0; i<3;i++) {
					delay(()->{
						skill("c143_s3");
					},1*2);
				}
				delay(()->{
					ARSystem.giveBuff(player, new Nodamage(player), 140);
					ARSystem.giveBuff(player, new NoCC(player), 100);
					delay(()->{
						spt = 40;
					},20);
				},5);
				return false;
			}
		}
		return true;
	}
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player&& s_kill >= 1) {
			skill("c143_p");
			ARSystem.playSound((Entity)player, "0boom3", 1 , 0.75f);
			
			tpsdelay(()->{ARSystem.playSoundAll("c143a");},20);
			for(Entity en : ARSystem.box(player, new Vector(12,12,12), box.TARGET)) {
				LivingEntity ln = (LivingEntity)en;
				ln.setNoDamageTicks(0);
				ln.damage(5 + s_damage, player);
			}
		}
		if(e == player && s_kill >= 3) {
			Rule.playerinfo.get(player).tropy(143, 1);
		}
	}
}
