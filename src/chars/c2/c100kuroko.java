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
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
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
import buff.Airborne;
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
import util.Pair;
import util.Text;

public class c100kuroko extends c00main{
	int s1 = 0;
	int ps = 20;
	int ps2 = 0;
	int count = 0;
	
	LivingEntity killer = null;
	boolean iskill = false;
	List<Pair<Integer, Integer>> p = new ArrayList<>();
	int p2 = 0;
	
	int time = 0;
	int air = 0;
	
	public c100kuroko(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 100;
		load();
		text();
		c = this;
		time = Text.getI("c100:time");
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c100s1");
		List<Entity> en = ARSystem.PlayerBeamBox(player, 10, 2, box.TARGET);
		if(en.size() > 0) {
			if(s1 > 0) {
				for(int i = 0; i< s1; i++) {
					delay(()->{ARSystem.spellCast(player, en.get(0), "c100_s12");},i*2);
				}
				s1 = 0;
			} else {
				ARSystem.spellCast(player, en.get(0), "c100_s12");
				p.add(new Pair<>(time,4));
			}
			cooldown[1] *= 1+p2*0.02;
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(killer != null && ps2 >= 0 && Map.inMap(killer.getLocation())) {
			p.add(new Pair<>(time,5));
			ps2 = 0;
			player.teleport(killer);
			killer.setNoDamageTicks(0);
			killer.damage(3,player);
			if(iskill)ARSystem.playSound((Entity)player, "c100db");
			killer = null;
			iskill = false;
			count++;
			if(count <= 3) Rule.playerinfo.get(player).tropy(100,1);
			cooldown[2] *= 1+p2*0.02;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c100s1");

		if(!player.isSneaking()) {
			player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(10)).add(0,0.5,0));
			if(BlockUtil.isAirbone(player.getLocation(), 2)) {
				ARSystem.giveBuff(player, new Airborne(player), 4);
				player.setVelocity(new Vector(0,0.1,0));

				if(BlockUtil.isAirbone(player.getLocation(), 5)) {
					p.add(new Pair<>(time,12));
				} else {
					p.add(new Pair<>(time,4));
				}
			} else {
				player.setVelocity(new Vector(0,0,0));
				p.add(new Pair<>(time,2));
			}
			player.setFallDistance(0);
		} else {
			List<Entity> target = ARSystem.box(player, new Vector(2,2,2), box.ALL);
			if(target.size() <= 0) {
				cooldown[3] = 0;
				return true;
			}
			for(Entity e : target) {
				killer = (LivingEntity)e;
				ps2 = 60;
				e.teleport(e.getLocation().add(player.getLocation().getDirection().multiply(15)).add(0,0.5,0));
				e.setVelocity(new Vector(0,0,0));
			}
			p.add(new Pair<>(time,16));
			cooldown[3] = setcooldown[3] * 8;
		}
		cooldown[3] *= 1+p2*0.02;
		return true;
	}
	
	@Override
	public boolean skill4() {		
		LivingEntity target = null;
		Location loc = player.getLocation().clone();
		for(int i=0;i<14;i++) {
			loc.add(loc.getDirection());
			for (LivingEntity e : player.getWorld().getLivingEntities()) {
				if(e.getLocation().distance(loc) <= 6 && e != player) {
					if(ARSystem.isTarget(e, player, box.TARGET)) target = e;
				}
			}
			if(target != null) i = 30;
		}
		if(target != null) {
			ARSystem.playSound((Entity)player, "c100p");
			Location ploc = player.getLocation();
			loc = ULocal.lookAt(target.getLocation(),ploc);
			loc = loc.add(loc.getDirection().multiply(1.5));
			loc = ULocal.lookAt(loc, target.getLocation());
			player.teleport(loc);
			loc = target.getLocation();
			target.damage(4,player);
			LivingEntity e = target;
			if(Rule.buffmanager.OnBuffTime(e, "stun")) {
				Rule.buffmanager.selectBuffTime(e, "stun",0);
			}
			delay(()->{
				Location el = e.getLocation().clone();
				e.setVelocity(player.getLocation().getDirection().multiply(3.5));
				delay(()->{
					if(e.getLocation().distance(el) <= 7) {
						if(Rule.c.get(e) != null && Rule.c.get(e).s_kill >=2 && skillCooldown(0)) {
							spskillon();
							spskillen();
							Location l = ploc;
							ARSystem.playSound((Entity)player, "c100s1");
							e.teleport(l);
							ARSystem.giveBuff(e, new Stun(e), 200);
							ARSystem.giveBuff(e, new Silence(e), 200);
							ARSystem.giveBuff(e, new Noattack(e), 200);
							ARSystem.playSound((Entity)player, "c100sp");
							for(int i=0;i<10;i++) {
								delay(()->{
									ARSystem.playSound((Entity)player, "c100s2");
									Location eloc = e.getLocation();
									eloc.setYaw(AMath.random(360));
									eloc.setPitch(-10+AMath.random(20));
									eloc = ULocal.offset(eloc, new Vector(2,0,0));
									eloc = ULocal.lookAt(eloc, e.getLocation());
									player.teleport(eloc);
									e.setNoDamageTicks(0);
									e.damage(1,player);
									ARSystem.spellLocCast(player, e.getLocation(), "c100_sk0");
								},10 + i*4);
							}
							delay(()->{
								ARSystem.playSound((Entity)player, "c100s1");
								e.teleport(e.getLocation().add(0,2,0));
								Location eloc = e.getLocation();
								eloc = ULocal.offset(eloc, new Vector(-2,0,0));
								player.teleport(eloc);
								ARSystem.spellLocCast(player, e.getLocation(), "c100_sk01");
							},50);
							
							for(int i=0;i<3;i++) {
								delay(()->{
									ARSystem.playSound((Entity)player, "c100s2");
									Location eloc = e.getLocation();
									eloc.setYaw(AMath.random(360));
									eloc.setPitch(-10+AMath.random(20));
									eloc = ULocal.offset(eloc, new Vector(2,0,0));
									eloc = ULocal.lookAt(eloc, e.getLocation());
									e.teleport(eloc);
									player.teleport(ULocal.offset(eloc,new Vector(-1,0,0)));
									e.setNoDamageTicks(0);
									e.damage(3,player);
								},60 + i*5);
							}
							for(int i=0;i<2;i++) {
								delay(()->{
									ARSystem.playSound((Entity)player, "c100s1");
									Location eloc = e.getLocation();
									eloc.setYaw(AMath.random(360));
									eloc.setPitch(-10+AMath.random(20));
									eloc = ULocal.offset(eloc, new Vector(0,-4,0));
									eloc = ULocal.lookAt(eloc, e.getLocation());
									e.teleport(eloc);
									player.teleport(ULocal.offset(eloc,new Vector(-1,0,0)));
									e.setNoDamageTicks(0);
									e.damage(4,player);
								},75 + i*5);
							}
							delay(()->{
								ARSystem.playSound((Entity)player, "c100s2");
								player.teleport(ULocal.lookAt(e.getLocation().add(0,2,0),e.getLocation()));
								player.setFallDistance(0);
							},85);
							delay(()->{
								ARSystem.playSound((Entity)player, "c100s1");
								player.teleport(ULocal.lookAt(e.getLocation().add(0,2,0),e.getLocation()));
								ARSystem.spellLocCast(player, e.getLocation(), "c100_sk02");
								e.setNoDamageTicks(0);
								e.damage(22,player);
								player.setFallDistance(0);
							},100);
						} else {
							e.setNoDamageTicks(0);
							e.damage(4*(p2*0.01),player);
							ARSystem.giveBuff(e, new Stun(e), 60);
							ARSystem.giveBuff(e, new Silence(e), 60);
						}
					}
				},6);
			},3);
			cooldown[4] *= 1+p2*0.02;
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	
	@Override
	public boolean teleportevent(PlayerTeleportEvent e) {
		if(e.getPlayer() != player && e.getFrom().distance(e.getTo()) >= 3 && e.getFrom().distance(player.getLocation()) < 8) {
			if(Rule.c.get(e.getPlayer()) != null) {
				LivingEntity en = (LivingEntity) e.getPlayer();
				killer = en;
				ps2 = 40;
			}
		}
		return super.teleportevent(e);
	}

	List<Pair<Integer, Integer>> removes = new ArrayList<>();
	@Override
	public boolean tick() {
		if(BlockUtil.isAirbone(startLoc, 1)) {
			air++;
		} else {
			air = 0;
		}
		if(ps > 0 && s1 < 8) {
			ps-=Math.max(1,(int)(skillmult+sskillmult));
			if(ps <= 0) {
				ps = 60;
				s1++;
			}
		}
		if(ps2 > 0) {
			ps2--;
			player.sendTitle(killer.getName(), AMath.round(ps2*0.05, 2)+"(s)",0,4,0);
			if(ps2 == 0) killer = null;
		}
		removes.clear();
		p2 = 0;
		for(Pair<Integer,Integer> p : p) {
			p.setKey(p.getKey()-Math.max(1,(int)(skillmult+sskillmult)/2));
			p2 += p.getValue();
			if(p.getKey() <= 0) removes.add(p);
		}
		for(Pair<Integer,Integer> p : removes) this.p.remove(p);
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c100:sk1")+ "] : " + s1+"("+AMath.round(ps*0.05, 1)+")");
			scoreBoardText.add("&c ["+Main.GetText("c100:t1")+ "] : " + p2+"%");
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e != player && p != player) {
			killer = (LivingEntity)e;
			ps2 = 60;
			iskill = true;
		}
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			ARSystem.addBuff(target, new Stun(target), 4);
		}
		super.makerSkill(target, n);
	}
	
	
	@Override
	public boolean skill9(){
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c63micoto) {
					is = "micoto";
					break;
				}
			}
		}
		
		if(is.equals("micoto")) {
			ARSystem.playSound((Entity)player, "c100m");
		} else {
			ARSystem.playSound((Entity)player, "c100db");
		}
		return true;
	}
}
