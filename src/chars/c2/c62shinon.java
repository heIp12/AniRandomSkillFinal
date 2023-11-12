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
import org.bukkit.entity.Damageable;
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
import chars.c.c00main;
import chars.c.c02kirito;
import chars.c.c08yuuki;
import chars.c.c47ren;
import chars.ca.c0200kirito;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c62shinon extends c00main{
	boolean gun;
	int targets = 0;
	int delay = 0;
	List<Entity> target;
	
	public c62shinon(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 62;
		load();
		text();
		c = this;
		target = new ArrayList<Entity>();
	}
	

	@Override
	public boolean skill1() {
		if(!gun) {
			gun = true;
			skill("c62_s1e");
			ARSystem.giveBuff(player, new Silence(player), 50);
			ARSystem.giveBuff(player, new Stun(player), 50);
			skill("c62_s1");
			delay(()->{
				ARSystem.playSound((Entity)player, "c62s11");
			},30);
			targets = 10;
			cooldown[1] = 0;
			delay = 30;
		} else if(target.size() > 0){
			gun = false;
			ARSystem.playSound((Entity)player, "c62s14");
			ARSystem.giveBuff(player, new Silence(player), 10);
			ARSystem.giveBuff(player, new Stun(player), 10);
			delay(()->{
				ARSystem.playSound((Entity)player, "0gun");
				((LivingEntity) target.get(0)).damage(7,player);
				ARSystem.spellCast(player, target.get(0), "c62_s1");
				ARSystem.playerAddRotate(player,0,(float) -25);
			},10);
			delay(()->{
				ARSystem.playSound((Entity)player, "c62s15");
				skill("c62_s1e2");
			},20);
		} else {
			skill("c62_s1e2");
			gun = false;
			cooldown[1] = 2f;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c62_s2");
		ARSystem.playSound((Entity)player, "0gun4");
		ARSystem.playerAddRotate(player,0,(float) -10);
		ARSystem.playSound((Entity)player, "c62s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.potion(player, 14, 200, 0);
		ARSystem.playSound(player, "c62s3");
		return true;
	}
	
	boolean ev = false;
	boolean ev2 = true;
	Player tg;
	@Override
	public boolean tick() {
		if(ev && ev2) {
			ev2 = false;
			ARSystem.playSoundAll("c62e");
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new TimeStop(p), 400);
			}
			tpsdelay(()->{
				Skill.win(player.getName() +" | " + tg.getName());
			},380);
		} else {
			if(Rule.c.size() == 2) {
				ev = false;
				for(Player p : Rule.c.keySet()) {
					if(Rule.c.get(p) instanceof c0200kirito) {
						ev = true;
						tg = p;
						
						p.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(2)));
						Location loc = p.getLocation();
						loc.setYaw(loc.getYaw()+180);
						p.teleport(loc);
					}
				}
			}
		}
		if(gun) {
			ARSystem.giveBuff(player, new Stun(player), 2);
			if(delay <= 0) {
				target.clear();
				Location loc = player.getLocation().clone();
				loc.add(loc.getDirection().multiply(3));
				for(int i=0;i<97;i++) {
					loc.add(loc.getDirection());
					if(!loc.clone().add(0,1,0).getBlock().isEmpty()) {
						i = 100;
					} else {
						for (LivingEntity e : player.getWorld().getLivingEntities()) {
							if(e.getLocation().distance(loc) <= 3) {
								if(ARSystem.isTarget(e, player) && !target.contains(e)) {
									target.add(e);
									ARSystem.potion(e, 24, 5, 5);
									if(targets <= 0) {
										targets = 40;
										ARSystem.playSound(player, "c62s1"+(1+AMath.random(2)));
									}
								}
							}
						}
					}
				}
				
				if(targets > 0) targets--;
			} else {
				delay--;
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			double hp = target.getMaxHealth() - target.getHealth();

			target.damage(3 + (hp*0.2),player);
			ARSystem.giveBuff(target, new Stun(target), 20);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() + e.getDamage() * (e.getEntity().getLocation().distance(player.getLocation())*0.07));
			
			if(e.getEntity().getLocation().distance(player.getLocation()) >= 80 && skillCooldown(0)) {
				spskillon();
				spskillen();
				e.setDamage(999);
				ARSystem.playSoundAll("c62sp");
				Rule.playerinfo.get(player).tropy(62,1);
				
			}
		} else {

		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c08yuuki) {
					is = "yuuki";
					break;
				}
				if(Rule.c.get(e) instanceof c02kirito){
					is = "kirito";
					break;
				}
				if(Rule.c.get(e) instanceof c47ren){
					is = "ren";
					break;
				}
			}
		}
		
		if(is.equals("ren")) {
			ARSystem.playSound((Entity)player, "c62ren");
		} else if(is.equals("kirito")) {
			ARSystem.playSound((Entity)player, "c62kirito");
		} else {
			ARSystem.playSound((Entity)player, "c62db");
		}
		
		return true;
	}
}
