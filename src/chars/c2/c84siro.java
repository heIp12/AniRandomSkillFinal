package chars.c2;

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

public class c84siro extends c00main{
	Player zero = null;
	int sk1 = 0;
	LivingEntity taget = null;
	int sk1c = 0;
	int sk3 = 0;
	
	public c84siro(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 84;
		load();
		text();
		c = this;
		
	}

	@Override
	public boolean skill1() {
		skill("c84_s1");
		ARSystem.playSound((Entity)player, "c84s1");
		return true;
	}
	@Override
	public boolean skill2() {
		float size = 8;
		if(player.isSneaking()) size = 5;
		Map.spawn("c84", player.getLocation().clone().add(player.getLocation().getDirection().multiply(size)),1);
		return true;
	}
	
	@Override
	public boolean skill3() {
		sk3 = 60;
		skill("c84_s3");
		ARSystem.playSound((Entity)player, "c84s3");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(taget != target) sk1c = 0;
			
			taget = target;
			sk1 = 40;
			sk1c++;
			if(sk1c > 3) sk1c = 3;
			for(int i=0;i<sk1c;i++) {
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(1,player);
					if(target.getLocation().distance(player.getLocation()) <= 7) {
						for(int s=0;s<10;s++) cooldown[s] -=0.3;
					}
				},i);
			}
		}

	}
	
	@Override
	public boolean tick() {
		if(sk1 > 0) {
			sk1--;
			if(sk1 <= 0) {
				sk1c = 0;
				taget = null;
			}
		}
		if(sk3 > 0) {
			sk3--;
			if(sk3 == 0) {
				Rule.buffmanager.selectBuffValue(player, "barrier", 6 );
			}
		}
		if(tk%20 == 0) {
			if(player.isSprinting()) {
				player.damage(1, player);
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(zero != null && p == zero) {
			zero = null;
			delay(()->{
				Skill.death(player, e);
				ARSystem.Stop();
			},10);
		}
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) e.setDamage(e.getDamage()+2.f);
			if(player.getLocation().getBlock().getLightLevel() <= 8) {
				e.setDamage(e.getDamage() * 2.f);
			}
		} else {
			if(isps) e.setDamage(e.getDamage()*0.5f);
			if(sk3 > 0) {
				e.setDamage(0);
				return false;
			}
		}
		
		return true;
	}

	public void skill0(Player p) {
		spskillon();
		spskillen();
		zero = p;
		setcooldown[1]*=0.5;
		setcooldown[2]*=0.5;
		setcooldown[3]*=0.5;
		setcooldown[4]*=0.5;
		
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c44izuna) {
					is = "izuna";
					break;
				}
			}
		}
		
		if(is.equals("izuna")) {
			ARSystem.playSound((Entity)player, "c84izuna");
			Rule.playerinfo.get(player).tropy(84,1);
		} else {
			ARSystem.playSound((Entity)player, "c84db");
		}
		
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		// TODO Auto-generated method stub
		if(e.getEntity() == player) {
			if(Rule.buffmanager.GetBuffTime(player, "nodamage") > 0 || sk3 > 0) {
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return super.damage(e);
	}

	public void sp(Player p,Player target) {
		spskillon();
		spskillen();
		for(Player pl: Bukkit.getOnlinePlayers()) {
			pl.stopSound("");
		}
		for(Player pl : Rule.c.keySet()) {
			ARSystem.giveBuff(pl, new TimeStop(pl), 400);
		}
		Bgm.setBgm("c83");
		ARSystem.playSoundAll("c83sp3");
		
		Map.getMapinfo(1011);
		Location loc = Map.getCenter();
		loc.setY(4);
		loc.setPitch(0);
		
		int j = 0;
		
		for(Player e : Rule.c.keySet()) {
			if(p != player) Rule.c.put(p, new c000humen(p, plugin, null));
			ARSystem.giveBuff(e, new TimeStop(e), 600);
			int o = (j+1)/2;
			if(j%2==1) o*=-1;
			p.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(1,0,o)),loc));
			j++;
		}
		target.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(-2,0,0)), loc));
		
		player.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(5,0,0)),loc));
		p.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(5,0,-1)),loc));
		ARSystem.potion(p, 14, 600, 1);
		ARSystem.potion(player, 14, 600, 1);
		ARSystem.spellLocCast(player,ULocal.offset(loc, new Vector(5,0,0)), "c84_sp2");
		ARSystem.spellLocCast(player,ULocal.offset(loc, new Vector(5,0,-1)), "c84_sp1");

		delay(()->{
			ARSystem.playSoundAll("c83sp2");
			
			ARSystem.spellLocCast(player,ULocal.offset(loc, new Vector(5,0,1)), "c84_sp3");
			ARSystem.spellLocCast(player,ULocal.offset(loc, new Vector(5,0,-2)), "c84_sp3");
			ARSystem.spellLocCast(player,ULocal.offset(loc, new Vector(5,0,2)), "c84_sp5");
			ARSystem.spellLocCast(player,ULocal.offset(loc, new Vector(5,0,-3)), "c84_sp5");
			ARSystem.spellLocCast(player,ULocal.offset(loc, new Vector(5,0,3)), "c84_sp4");
			ARSystem.spellLocCast(player,ULocal.offset(loc, new Vector(5,0,-4)), "c84_sp4");
			for(int i=0; i<8;i++) {
				ARSystem.spellLocCast(player,ULocal.offset(loc, new Vector(4,0,-4+i)), "c84_sp6");
			}
			delay(()->{
				skill("c83_spp");
				for(Player pl : Rule.c.keySet()) {
					if(pl != player && pl != target && pl != p) {
						Skill.quit(pl);
					}
				}
			},100);
		},220);
		delay(()->{
			for(int i=0; i<18;i++) {
				int k = i;
				delay(()->{
					Location locs = target.getLocation();
					locs.setYaw(k*20);
					ARSystem.spellLocCast(player,ULocal.offset(locs, new Vector(4,0,0)), "c84_sp"+(AMath.random(4)+2));
				},i);
			}
			delay(()->{
				skill("c83_spp");
			},70);
		},340);
		delay(()->{
			tpsdelay(()->{
				if(AMath.random(2) == 1) {
					ARSystem.playSoundAll("c83sp1");
				} else {
					ARSystem.playSoundAll("c84sp1");
				}
			},40);
			Skill.win(player.getName() +" | " + p.getName());
		},440);
	}
}
