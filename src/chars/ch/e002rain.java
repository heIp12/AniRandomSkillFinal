package chars.ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Fix;
import buff.Nodamage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.BuffType;
import types.TargetMap;
import types.box;
import util.GetChar;
import util.Holo;
import util.Map;
import util.Text;
import util.ULocal;

public class e002rain extends c00main{
	public List<LivingEntity> damage = new ArrayList<LivingEntity>(); 
	public int death = -1;
	boolean start = false;
	double hhp = 0;
	int sk3 = 0;
	
	TargetMap<String, Double> list = new TargetMap<>();
	

	public e002rain(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 902;
		load();
		text();
		hhp = p.getMaxHealth();
		
		ARSystem.playSound((Entity)p, "rainselect");
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "rains1");
		skill("c902_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "rains2");
		skill("c902_s2");
		if(player.isSneaking()) {
			player.teleport(ULocal.offset(player.getLocation(), new Vector(15,0,0)));
		} else {
			player.setVelocity(player.getLocation().getDirection().multiply(3));
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "rains3");
		skill("c902_s3");
		sk3 = 20;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(Rule.buffmanager.selectBuffType(target, BuffType.BUFF) != null) {
				for(Buff buff : Rule.buffmanager.selectBuffType(target, BuffType.DEBUFF)) {
					if(buff.getTime() > 0) {
						t(19);
						Buff bf = buff.clone();
						buff.stop();
						bf.target = player;
						ARSystem.giveBuff(target, bf, bf.getTime(), bf.getValue());
					}
				}
			}
			target.damage(target.getMaxHealth()*0.9, player);
		}
		super.makerSkill(target, n);
	}
	
	@Override
	public boolean firsttick() {
		if(isps) return false;
		if(start) {
			if(Rule.buffmanager.selectBuffType(player, BuffType.DEBUFF) != null) {
				for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.DEBUFF)) {
					if(buff.getTime() > 0) {
						t(3);
						buff.stop();
					}
				}
			}
		}
		if(player.getPotionEffect(PotionEffectType.POISON) != null) {
			t(10);
			player.removePotionEffect(PotionEffectType.POISON);
		}
		if(player.getPotionEffect(PotionEffectType.WITHER) != null) {
			t(10);
			player.removePotionEffect(PotionEffectType.WITHER);
		}
		if(player.getPotionEffect(PotionEffectType.BLINDNESS) != null) {
			t(9);
			player.removePotionEffect(PotionEffectType.BLINDNESS);
			ARSystem.potion(player, 16, 10000, 1);
		}
		if(player.getPotionEffect(PotionEffectType.CONFUSION) != null) {
			t(18);
			player.removePotionEffect(PotionEffectType.CONFUSION);
		}
		return false;
	}
	
	
	@Override
	public boolean tick() {
		if(Rule.c.size() == 2 && skillCooldown(0)) {
			spskillon();
			spskillen();
			ARSystem.playSoundAll("rainsp");
			ARSystem.giveBuff(player, new TimeStop(player), 100);
			delay(()->{
				((Fix)Rule.buffmanager.selectBuff(player, "fix")).stop = true;
				Skill.quit(player);
			},100);
		}
		start = true;
		ARSystem.giveBuff(player, new Fix(player), 0);
		if(tk%10 == 0) {
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p).number == 1023) {
					Skill.quit(p);
					l(15);
				}
			}
		}
		if(player.getMaxHealth() != hhp) {
			player.setMaxHealth(hhp);
			t(21);
		}
		
		if(player.getRemainingAir() <= 0) {
			player.setRemainingAir(100);
			t(20);
		}
		if(player.getHealth() < player.getMaxHealth()*0.5) {
			ARSystem.potion(player, 10, 40, 3);
			t(17);
		}
		for(String key : list.get().keySet()) {
			list.add(key, -0.05);
			if(list.get(key) <= 0) {
				list.removeAdd(key);
				Holo.create(player.getLocation(), key, 40, new Vector(0, 0.05, 0));
			}
		}
		
		if(sk3 > 0) {
			for(Entity e : ARSystem.box(player, new Vector(12,999,12), box.TARGET)) {
				if(e.getLocation().getBlockY() > player.getLocation().getBlockY() +5) {
					e.teleport(ULocal.BoxNear(player.getLocation().add(new Vector(12, 5, 12)), player.getLocation().add(new Vector(-12, -1, -12)), e.getLocation()));
					e.setVelocity(new Vector(0, -10 ,0));
				}
				ARSystem.giveBuff((LivingEntity)e, new Silence((LivingEntity)e), 40);
			}
			sk3--;
		}
		list.removes();
		return true;
	}
	
	public void t(int n) {
		list.add(Text.get("c902:g"+n), list.get().size() * 0.4f);
	}
	public void l(int n) {
		String tx =  Text.get("c902:g"+n);
		if(n == 11) {
			for(int i =0; i < death; i ++) {
				tx = Text.get("c902:g12")+tx;
			}
			ARSystem.playSound((Entity)player, "raing11");
		}
		if(n == 16) {

			ARSystem.playSoundAll("raing16");
		}
		list.add(tx, list.get().size() * 0.4f);
		for(Player p : Bukkit.getOnlinePlayers()) {
			AdvManager.set(p, 399, 0 , "§f§l"+player.getName() +"§a("+ Text.get("c902:name1") +") §f§l" + tx);
		}
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.LAVA) {
			t(4);
			e.setDamage(0);
			e.setCancelled(true);
		}
		return super.damage(e);
	}
	
	@Override
	public boolean remove(Entity caster) {
		l(13);
		return false;
	}
	
	@Override
	public void WinEvent(event.WinEvent e) {
		e.setCancelled(true);
		l(16);
	}
	
	@Override
	public boolean death(PlayerDeathEvent e) {
		if(e.getEntity() == player) {
			e002rain rain = new e002rain(player,plugin, null);
			Rule.c.put(player, rain);
			rain.death = death+1;
			l(11);
		}
		return false;
	}
	
	@Override
	public boolean skill6() {
		if(player.isSneaking()) {
			Rule.playerinfo.get(player).addcradit(-10,Main.GetText("main:msg105"));
			((Fix)Rule.buffmanager.selectBuff(player, "fix")).stop = true;
			ARSystem.Death(player,player);
		} else {
			player.sendMessage("§a§l[ARSystem]§f§l : "+ Main.GetText("main:msg21"));
		}
		return false;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() <= 2) {
				t(8);
				e.setDamage(10);
			}
		} else {
			LivingEntity de = (LivingEntity)e.getDamager();
			if(e.getDamager().getLocation().distance(player.getLocation()) > 10) {
				t(2);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			} else {
				if(!damage.contains(de)) {
					damage.add(de);
					t(6);
					e.setDamage(0);
					e.setCancelled(true);
					return false;
				} else {
					t(7);
					e.setDamage(e.getDamage() * 0.05f);
				}
			}
			
			if(player.getHealth() - e.getDamage() <= 1) {
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.heal(player, 100);
				death++;
				l(11);
				return false;
			}
		}
		return true;
	}
	
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof e002rain) {
					is = "rain";
					break;
				}
			}
		}
		
		if(is.equals("rain")) {
			ARSystem.playSound((Entity)player, "rainsubaru");
		} else {
			ARSystem.playSound((Entity)player, "raindb");
		}
		
		return true;
	}
}
