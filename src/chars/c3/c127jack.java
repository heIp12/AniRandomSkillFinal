package chars.c3;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Curse;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import types.box;

import util.AMath;
import util.ULocal;

public class c127jack extends c00main{
	Location target;
	int targettick = 0;
	int skd = 0;
	int sk2 = 0;
	boolean sk3 = false;
	List<Entity> targets;
	Location sk2l;
	
	int p = 0;
	boolean ps = false;
	
	
	boolean t = false;
	public c127jack(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 127;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c127s1");
		Location loc = player.getLocation();
		delay(()->{
			player.setVelocity(new Vector(0,0.4,0));
			delay(()->{
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
				skd = 20;
				player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0.2));
				
				delay(()->{
					ARSystem.playSound((Entity)player, "0katana",2);
					skill("c127_s1-1");
					ARSystem.playSound((Entity)player, "c127s12");
					for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
						damage(e,2);
						t = true;
					}
					delay(()->{
						ARSystem.playSound((Entity)player, "0katana2",2);
						skill("c127_s1-2");
						for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
							 damage(e,2);
							 t = true;
						}
						delay(()->{
							if(t) {
								player.setVelocity(new Vector(0,0.4,0));
								delay(()->{
									player.setVelocity(ULocal.lookAt(player.getLocation(), loc).getDirection().multiply(2).setY(0.2));
								},2);
							}
						},3);
					},4);
				},4);
			},2);
		},8);
		return true;
	}
	void damage(Entity e, double damage){
		float m = 1.5f;
		if(ps) m = 2.5f;
		if(targettick > 0 && target.distance(e.getLocation()) <= 6) damage *= m;
		if(player.getWorld().getTime()%36000 > 12500 && player.getWorld().getTime()%36000 < 22500) damage*= m;
		if(Rule.c.get(e) != null && Main.GetText("c"+Rule.c.get(e).getCode()+":tag").indexOf("tg1") != -1) damage *= m;
		
		if(sk3) {
			sk3 = false;
			if(((LivingEntity)e).getHealth() - damage < 1) {
				cooldown[1] = cooldown[2] = 0;
			} else {
				Curse cs = new Curse((LivingEntity) e);
				cs.setCaster(player);
				ARSystem.giveBuff((LivingEntity) e, cs, 60 , 1);
			}
		}
		((LivingEntity)e).setNoDamageTicks(0);
		((LivingEntity)e).damage(damage,player);
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c127s2");
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		sk2 = 10;
		skd = 10;
		targets = new ArrayList<>();
		sk2l = player.getLocation();
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c127s3");
		sk3 = true;
		return true;
	}
	
	@Override
	public boolean skill4() {
		player.damage(2,player);
		target = player.getLocation();
		targettick = 160;
		
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0 && target!=null) {
			scoreBoardText.add("&c ["+Main.GetText("c127:ps")+ "] : &f" + AMath.round(player.getLocation().distance(target),2) +"[6~12]");
		}
		if(target != null && targettick > 0) {
			ARSystem.spellLocCast(player, target, "c127_p");
			if(skd <= 0  && player.getLocation().distance(target) >= 6 && player.getLocation().distance(target) <= 12) {
				p = 40;
				ARSystem.potion(player, 14, 20, 1);
			}
		}
		if(sk2 > 0) {
			if(sk2%2 == 0) {
				ARSystem.playSound((Entity)player, "0katana2",1.2f);
			}
			sk2--;
			skill("c127_s2");
			player.setVelocity(sk2l.getDirection().multiply(1.4));
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				if(!targets.contains(e)) {
					targets.add(e);
					damage(e,1);
					delay(()->{damage(e,1);},4);
					delay(()->{damage(e,1);},8);
				}
			}
		}
		
		if(p > 0) p--;
		if(skd > 0) skd--;
		if(targettick > 0) {
			targettick--;
			if(targettick == 0) {
				target = null;
			}
		}

		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			target = e.getEntity().getLocation();
			targettick = 160;
			double range = 300;
			for(Player p : Rule.c.keySet()) {
				if(p.getGameMode() != GameMode.SPECTATOR) {
					if(p.getLocation().distance(e.getEntity().getLocation()) < range && p != player && p != e.getEntity()) {
						range = p.getLocation().distance(e.getEntity().getLocation());
					}
				}
			}
			if(p > 0 && range >= 50 && skillCooldown(0)) {
				ARSystem.giveBuff(player, new TimeStop(player), 200);
				ARSystem.giveBuff((LivingEntity) e.getEntity(), new TimeStop((LivingEntity) e.getEntity()), 200);
				spskillon();
				spskillen();
				player.teleport(ULocal.lookAt(ULocal.offset(e.getEntity().getLocation(), new Vector(3,0,0)), e.getEntity().getLocation()));
				ARSystem.playSound((Entity)player, "c127sp");
				delay(()->{
					player.teleport(ULocal.offset(e.getEntity().getLocation(), new Vector(-2,0,0)));
					ARSystem.giveBuff((LivingEntity) e.getEntity(), new Stun((LivingEntity) e.getEntity()), 60);
					ARSystem.giveBuff((LivingEntity) e.getEntity(), new Silence((LivingEntity) e.getEntity()), 60);
					ARSystem.giveBuff(player, new Stun(player), 40);
					ARSystem.giveBuff(player, new Silence(player), 40);
					ARSystem.giveBuff(player, new Nodamage(player), 40);
					ps = true;
					for(int i=0;i<10;i++) {
						delay(()->{
							ARSystem.playSound((Entity)player, "0katana3",2f);
							ARSystem.spellCast(player, e.getEntity(), "c127_sp_e1");
							damage(e.getEntity(), 0.1);
						},i*2);
					}
					delay(()->{
						player.teleport(ULocal.lookAt(ULocal.offset(e.getEntity().getLocation(), new Vector(-1,0,0)), e.getEntity().getLocation()));
					},20);
						delay(()->{
							ARSystem.playSound((Entity)player, "0katana4",1.4f);
							ARSystem.spellCast(player, e.getEntity(), "c127_sp_e2");
							damage(e.getEntity(), 1);
						},20);
						delay(()->{
							ARSystem.playSound((Entity)player, "0katana5",2f);
							ARSystem.spellCast(player, e.getEntity(), "c127_sp_e2");
							damage(e.getEntity(), 6);
						},25);
					delay(()->{
						ps = false;
					},40);
				},200);
			}
		} else {
			if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				e.setDamage(e.getDamage()* 0.1);
			}
		}
		return true;
	}

}
