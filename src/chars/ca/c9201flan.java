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
import org.bukkit.event.entity.EntityDamageEvent;
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
import util.BlockUtil;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c9201flan extends c00main{
	public LivingEntity owner;
	Entity last = null;
	
	List<Entity> target = new ArrayList<Entity>();
	Location loc;
	int s3t = 0;

	List<LivingEntity> sk1tg;
	public c9201flan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2092;
		load();
		text();
		c = this;
	}
	

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c2092s2");
		ARSystem.giveBuff(player,new Stun(player), 10);
		skill("c2092_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		s3t = 0;
		player.setVelocity(new Vector(0,-5,0));
		Location loc = player.getLocation();
		for(int i=0;i<100;i++) {
			if(BlockUtil.isPathable(loc.getBlock())) {
				loc.add(0,-0.5,0);
			} else {
				loc.add(0,0.5,0);
				break;
			}
		}
		player.teleport(loc);
		delay(()->{
			ARSystem.playSound((Entity)player, "c2092s1");
			ARSystem.giveBuff(player,new Stun(player), 10);
			skill("c2092_s2");
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(5,3,5), box.TARGET)){
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(5,player);
					e.setVelocity(new Vector(0,2,0));
				}
			},10);
		},2);
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c2092s3");
		ARSystem.giveBuff(player,new Stun(player), 10);
		delay(()->{
			loc = player.getLocation();
			s3t = 30;
			skill("c2092_s3");
			target.clear();
		},10);
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c2092s4");
		skill("c2092_s4");
		return true;
	}
	
	public void Attack() {
		if(last != null) {
			Location loc = ULocal.lookAt(player.getLocation(),last.getLocation());
			ARSystem.spellLocCast(player, loc, "c1092_s2");
		}
	}

	@Override
	public boolean tick() {
		if(s3t > 0) {
			s3t--;
			player.setVelocity(loc.getDirection().multiply(1.2f));
			for(Entity e : ARSystem.box(player,new Vector(5,5,5), box.TARGET)){
				if(!target.contains(e)) {
					target.add(e);
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(8,player);
				}
			}
		}
		return true;
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getEntity() == owner) {
				e.setDamage(0);
				e.setCancelled(true);
			} else {
				last = e.getEntity();
			}
		} else {

		}
		return true;
	}
}
