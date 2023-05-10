package chars.c;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.Stun;
import buff.Wound;
import event.Skill;

import util.ULocal;
import util.MSUtil;

public class c13nagisa extends c00main{
	int ticks = 0;
	int count = 0;
	float mult = 1.5f;
	float ps = 0;
	
	int attack_time = 0;
	int sk4 = 0;
	LivingEntity sk4t;
	int tick = 0;
	
	public c13nagisa(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 13;
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		inv();
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		inv();
		skill("c"+number+"_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(isps) {
			inv();
			for(int i=0;i<3;i++) {
			 delay(()->{
				 skill("c13_s3");
				 ARSystem.playSound((Entity)player, "c13a", 1.2f);
			 },i*1);
			}
		} else {
			ARSystem.playSound((Entity)player, "0miss", 1.2f);
			ARSystem.potion(player, 14, 160, 1);
			ARSystem.potion(player, 1 , 160, 2);
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		inv();
		if(sk4 > 0) {
			player.teleport(ULocal.offset(sk4t.getLocation(), new Vector(-2,0,0)));
		} else {
			skill("c"+number+"_s4");
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(4,player);
			Wound w = new Wound(target);
			
			if(isback(target.getLocation(), player.getLocation(), 0.5f)) {
				w.setValue(0.5);
				w.setDelay(target,20,0);
			} else {
				w.setValue(0.25);
				w.setDelay(target,20,0);
			}
			ARSystem.giveBuff(target, w, 200);
		}
		if(n.equals("3")) {
			ARSystem.addBuff(target,new Stun(target), 60);
			ARSystem.addBuff(target,new Silence(target), 60);
			if(ps >= 9) {
				if(sk4 <= 0) {
					sk4 = 20;
					sk4t = target;
					ARSystem.playSound((Entity)player, "c13kill");
					cooldown[4] = 0;
					delay(()->{cooldown[4] = setcooldown[4]-1; },20);
				}
			}
		}
	}
	
	boolean isback(Location target, Location attaker,float size) {
		Location lc = target.clone();
		Location plc = attaker.clone();
		lc.setPitch(0);
		plc.setPitch(0);
		float targetFaceAngle = lc.clone().getDirection().angle(new Vector(1, 1, 1));
		float diffAngle = lc.toVector().subtract(plc.toVector()).angle(new Vector(1, 1, 1));
		float diff = Math.abs(targetFaceAngle - diffAngle);
		if(diff <= size) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean tick() {
		if(isps) {
			tick++;
			if(tick > 600) Skill.remove(player, player);
		}
		ticks++;
		if(ps > 0) ps--;
		if(sk4 > 0) sk4--;
		if(attack_time > 0) {
			attack_time--;
			if(attack_time == 0) {
				ARSystem.playSound((Entity)player, "0miss", 1.2f);
				ARSystem.potion(player, 14, 10000, 1);
				ARSystem.potion(player, 1 , 10000, 2);
			}
		}
		if(ticks%20==0) {
			if(!isps) {
				boolean ps = true;
				for(Player p : Rule.c.keySet()) {
					if(p == player) continue;
					int nb = Rule.c.get(p).getCode();
					int cd = Integer.parseInt(Main.GetText("c"+nb+":type").replace(" tp",""));
					if(cd == 2) {
						ps = false;
						break;
					}
				}
				if(ps) {
					spskillen();
					spskillon();
					attack_time = 40;
					ARSystem.playSound((Entity)player, "c13sp");
				}
			}
		}
		ticks%=20;
		return false;
	}
	
	void inv() {
		tick = 0;
		if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			player.removePotionEffect(PotionEffectType.SPEED);
			ps = 10;
		}
		if(isps) {
			attack_time = 100;
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			inv();
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
			if(ps > 0) {
				e.setDamage(e.getDamage()*2);
			}
			if(e.getEntityType() != EntityType.ARMOR_STAND) {

				if(isback(e.getEntity().getLocation(),player.getLocation(), 0.5f) && ((LivingEntity)e.getEntity()).getNoDamageTicks() <= 0) {
					((LivingEntity)e.getEntity()).setNoDamageTicks(0);
					e.setDamage(e.getDamage()*mult);
					float damage = (float) (Math.round(e.getFinalDamage()*10)/10.0);
					
					player.sendTitle("§4Critical!","§cDamage : " + damage,10,10,20);
					ARSystem.spellLocCast(player, e.getEntity().getLocation(), "bload");
					if(e.getEntityType() == EntityType.PLAYER) {
						count++;
						if(count > 7) {
							Rule.playerinfo.get(player).tropy(13,1);
						}
					}
				}
			}
		} else {
			if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
				e.setDamage(e.getDamage() * 0.8);
			}
		}
		return true;
	}
}
