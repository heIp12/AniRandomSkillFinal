package chars.c;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import buff.Exposure;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import types.box;
import ars.Rule;
import util.AMath;
import util.ULocal;

public class c26ribai extends c00main{
	
	float damage = 1.5f;
	float maxdamage = 1.5f;
	int s3 = 0;
	int noattack = 0;
	int nc = 2;
	
	int fly = 0;
	int flyto = 0;
	
	public c26ribai(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 26;
		load();
		text();
	}
	
	@Override
	public void info() {
		super.info();
		if(s_kill >= 3) {
			Rule.playerinfo.get(player).tropy(26,1);
		}
	}
	
	@Override
	public boolean skill1() {
		skill("c26_s1");
		ARSystem.playSound((Entity)player, "c26a", 1, 2);
		return true;
	}

	int hit = 0;
	@Override
	public boolean skill2() {
		if(fly < 10) {
			cooldown[2] = 0;
			return false;
		}
		Location loc = player.getLocation().clone();
		loc.setPitch(0);
		
		if(player.isSneaking()) {
			loc = ULocal.offset(loc, new Vector(3,2,0));
			Location locs = player.getLocation().clone();
			hit = 0;
			ARSystem.playSound((Entity)player, "c26s2", 1, 2);

			loc.setPitch(90);
			for(int i=0; i< 10; i++) {
				loc.setPitch(loc.getPitch() - 18);
				Location lc = loc.clone();
				int l = i;
				delay(()->{
					player.teleport(ULocal.offset(lc, new Vector(-4.5,0,0)));
					if(l%2 == 1) {
						skill("c26_s2-e1");
						List<Entity> ens = ARSystem.box(player, new Vector(1,4.5,1), box.TARGET);
						if(ens.size() > 0) {
							hit++;
							for(Entity en : ens) {
								LivingEntity e = (LivingEntity)en;
								e.setVelocity(new Vector(0,0.5,0));
								e.setNoDamageTicks(0);
								e.damage(1,player);
							}
						}
					}
				},i);
				delay(()->{
					Location ls = player.getLocation();
					ls.setPitch(-45);
					ls.setYaw(lc.getYaw()+180);
					player.teleport(ls);
				},11);
			}
		} else {
			loc = ULocal.offset(loc, new Vector(0,0,3));
			Location locs = player.getLocation().clone();
			hit = 0;
			ARSystem.playSound((Entity)player, "c26s2", 1, 2);
			
			for(int i=0; i< 10; i++) {
				loc.setYaw(loc.getYaw() + 18);
				Location lc = loc.clone();
				int l = i;
				delay(()->{
					player.teleport(ULocal.offset(lc, new Vector(0,0,-3)));
					if(l%2 == 1) {
						skill("c26_s2-e");
						List<Entity> ens = ARSystem.box(player, new Vector(4.5,2,4.5), box.TARGET);
						if(ens.size() > 0) {
							hit++;
							for(Entity en : ens) {
								LivingEntity e = (LivingEntity)en;
								e.setNoDamageTicks(0);
								e.damage(1,player);
							}
						}
					}
				},i);
			}
		}
		loc.setPitch(loc.getPitch()*-1);
		Location lcc = loc;
		delay(()->{
			player.sendTitle("§c§l"+hit+" Hit!!", "",0,40,20);
			player.teleport(lcc);
			player.teleport(player);
			skill3();
			if(hit >= 5) {
				player.sendTitle("§4§l"+hit+" Hit!!", "",0,40,20);
				for(int i =0;i<5;i++) ARSystem.spellLocCast(player, lcc, "c26_s2-e2");
				List<Entity> ens = ARSystem.box(player, new Vector(6,2,6), box.TARGET);
				if(ens.size() > 0) {
					cooldown[2] = 0;
					if(cooldown[4] > 0) cooldown[4] -= 10;
					for(Entity en : ens) {
						LivingEntity e = (LivingEntity)en;
						e.setNoDamageTicks(0);
						ARSystem.spellCast(player, e, "bload");
						e.damage(5,player);
						if(Rule.buffmanager.GetBuffTime(e, "exposure") > 0 && skillCooldown(0)) {
							ARSystem.giveBuff(e, new Exposure(e), 200 , 9);
							spskillon();
							spskillen();
							player.teleport(ULocal.lookAt(player.getLocation().clone(), e.getLocation()));
							ARSystem.playSound((Entity)player,"c26sp");
							ARSystem.giveBuff(e, new TimeStop(e), 40);
							ARSystem.giveBuff(player, new TimeStop(player), 40);
							ARSystem.giveBuff(player, new Nodamage(player), 40);
							ARSystem.giveBuff(e, new Stun(e), 40);
							delay(()->{
								Rule.buffmanager.selectBuffTime(e, "timestop", 0);
								Rule.buffmanager.selectBuffTime(e, "nodamage", 0);
								for(int i=0;i<40;i++) {
									int k = i;
									float yaw = ULocal.lookAt(player.getLocation(),en.getLocation()).getYaw();
									tpsdelay(()->{
										Location local = en.getLocation().clone();
										int j = k%20;
										if(j > 15) j-=20;
										if(j < 5) {
											local.setYaw(yaw);
											local.setPitch(-18*j);
										} else {
											local.setYaw(yaw+180);
											local.setPitch(-90+(18*(j-5)));
										}
										ARSystem.spellLocCast(player, player.getLocation(), "c26_sp");
										e.setNoDamageTicks(0);
										ARSystem.spellCast(player, e, "bload");
										e.damage(1,player);
										damage = 1;
										player.teleport(ULocal.lookAt(ULocal.offset(en.getLocation(), local.clone().getDirection().multiply(2)),en.getLocation()));
									},i);
								}
							},60);
						}
					}
				}
			}
		},11);
		
		return true;
	}
	


	int s3c = 20+AMath.random(50);
	@Override
	public boolean skill3() {
		if(s3c < s3) return false;
		ARSystem.playSound((Entity)player, "c26s3", 1.3f - Math.min(0.02f*13,0.02f*s3), 1);
		player.setSneaking(true);
		delay(()->{
			player.setSneaking(false);
			if(s3 == s3c-1) {
				player.sendTitle("§c§l픽", "",0,40,20);
				ARSystem.playSound((Entity)player, "entity.creeper.hurt", 0.5f, 2);
				cooldown[3] = 1f;
			}
			if(s3 == s3c) {
				player.setHealth(4);
				player.setMaxHealth(4);
				skill("c26_exp");
				for(int i =0;i < 20; i++) {
					int k = i;
					delay(()->{
						player.sendTitle("§c§l"+ AMath.round(5 - (k*0.25),2),"§f§l노후된 장비는 안전사고를 유발할수도 있습니다!",2,3,2);
						ARSystem.playSound((Entity)player, "entity.creeper.primed", (float) (0.5+ (k*0.07f)), 2);
					},i*5);
				}
				delay(()->{
					ARSystem.playSound((Entity)player, "0explod", 1, 2);
					skill("c26_s3-6");
					for(Entity en : ARSystem.box(player, new Vector(8,8,8), box.ALL)) {
						LivingEntity e = (LivingEntity)en;
						e.setNoDamageTicks(0);
						e.damage(100,player);
					}
					hpCost(100, true);
				},100);
			} else if(s3 >= 20) {
				ARSystem.playSound((Entity)player, "0explod", 0.3f, 0.2f);
				skill("c26_s3-5");
			}
			else if(s3 <= 4) {
				skill("c26_s3-1");
			} else if(s3 <= 8) {
				cooldown[3] = 0.5f;
				skill("c26_s3-2");
			} else if(s3 <= 12) {
				cooldown[3] = 1.5f;
				skill("c26_s3-3");
			} else {
				cooldown[3] = 2.f;
				skill("c26_s3-4");
			}
			s3++;
		},1);
		return true;
	}
	
	@Override
	public boolean skill4() {
		s3 = 0;
		noattack = 0;
		nc = 2;
		damage = maxdamage;
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.giveBuff(player, new Silence(player), 20);
		skill("c"+number+"_s4");
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p,Entity e) {
		if(e == player) {
			if(cooldown[4] > 0) cooldown[4] -= 60;
		}
	}

	@Override
	public boolean tick() {
		float speed = 0.3f;
		if(s3 <= 6) speed = 0.4f;
		if(s3 <= 3) speed = 0.5f;
		if(player.getVelocity().clone().setY(0).distance(new Vector(0,0,0)) >= speed) {
			fly++;
			if(s3 <= 6) fly+=1;
			if(s3 <= 3) fly+=1;
			flyto = 3;
		} else if(flyto <= 0){
			fly = 0;
		}
		if(flyto > 0) flyto--;
		
		if(damage != maxdamage && nc > 0) {
			noattack++;
			if(noattack > 160) {
				nc--;
				noattack = 0;
				damage = maxdamage;
				ARSystem.playSound((Entity)player, "0katana2");
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c [Damage]&f : "+ AMath.round(damage,2));
			if(s3 < 20) {
				scoreBoardText.add("&c ["+Main.GetText("c26:ps")+"]&f : "+ Main.GetText("c26:t"+(Math.min((s3/4)+1,4))));
			} else {
				scoreBoardText.add("&c ["+Main.GetText("c26:ps")+"]&f : "+ Main.GetText("c26:t5"));
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(3,player);
			float dg = damage;
			delay(()->{
				ARSystem.potion(target, 24, 30, 1);
				ARSystem.giveBuff(target, new Exposure(target), 30 , AMath.round(3*dg,1));
			},60);
		}
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			noattack = 0;
			double d = e.getDamage();
			e.setDamage(e.getDamage() * damage);
			damage -= (0.05f*d);
			if(damage <= 0.1f) {
				damage = 0.1f;
			}
			
		} else {
			
		}
		return true;
	}
}