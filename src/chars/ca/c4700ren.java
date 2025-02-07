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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Airborne;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import chars.c.c00main;
import chars.c.c02kirito;
import chars.c.c08yuuki;
import chars.c2.c62shinon;
import event.Skill;
import manager.AdvManager;
import types.box;

import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c4700ren extends c00main{

	int bullet = 50;
	int stack = 0;
	
	int pt = 10;
	
	Vector s3v;
	int delay = 0;
	int s3 = 0;
	int s4 = 0;
	int pc = 0;
	
	boolean targetmob = false;
	
	public c4700ren(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1047;
		load();
		text();
		bullet = 50;
		player.setWalkSpeed(player.getWalkSpeed()*1.8f);
	}
	
	
	@Override
	public void setStack(float f) {
		if(f == 0) {
			bullet = 0;
			spskillon();
			spskillen(buffText);
		} else {
			stack = (int)f;
			bullet = stack;
		}
	}

	@Override
	public boolean skill1() {
		if(pc > 0) {
			List<Entity> entitys = ARSystem.PlayerBeamBox(player, 8, 3, box.ALL);
			if(entitys != null && entitys.size() > 0) {
				LivingEntity en = (LivingEntity) entitys.get(0);
				Location lc = en.getLocation().clone();
				lc = ULocal.offset(ULocal.lookAt(en.getLocation(), player.getLocation()),new Vector(2,0,0));
				player.teleport(lc);
				ARSystem.spellCast(player, "c1047_s11");
				ARSystem.playSound((Entity)player, "0slash");
				player.setVelocity(player.getLocation().getDirection().multiply(1.2));
				cooldown[1] *= 0.6;
			} else {
				cooldown[1] = 0;
			}
		} else {
			pt = 1;
			delay(()->{
				pt = 10;
			},20);
		}
		return true;
	}
	
	void shrot(){
		stack++;
		float sp = 10f;
		Location l = player.getLocation();

		l.setPitch((float) (l.getPitch() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
		l.setYaw((float) (l.getYaw() - (sp/2) + AMath.random((int) (sp*100))/100.0f ));
		
		ARSystem.playSound((Entity)player, "c47gun",(0.5f + (1.5f-(1.5f/bullet+1))));
		ARSystem.spellLocCast(player, l, "c1047_s1");
		ARSystem.playerAddRotate(player,(float) AMath.random(9)-5,(float) -4);
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c47s2",(float) 1);
		ARSystem.potion(player, 1, 10, 20);
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c47s3",(float) 1);
		s3v = player.getLocation().getDirection().setY(0);
		s3 = 40;
		return true;
	}
	
	@Override
	public boolean skill4() {
		List<Entity> entitys = ARSystem.PlayerBeamBox(player, 5, 3, box.ALL);
		if(entitys != null && entitys.size() > 0) {
			LivingEntity en = (LivingEntity) entitys.get(0);
			if(en != null) {
				ARSystem.playSound((Entity)player, "c47s4",(float) 1);
				delay = 20;
				ARSystem.giveBuff(player, new Nodamage(player), 10);
				ARSystem.addBuff(player, new Silence(player), 10);
				
				ARSystem.giveBuff(en, new Noattack(en), 20);
				for(int i = 0; i< 10; i++) {
					int j = i;
					float yaw = ULocal.lookAt(player.getLocation(),en.getLocation()).getYaw();
					tpsdelay(()->{
						Location local = en.getLocation().clone();
						if(j < 5) {
							local.setYaw(yaw);
							local.setPitch(-18*j);
						} else {
							local.setYaw(yaw+180);
							local.setPitch(-90+(18*(j-5)));
						}
						player.teleport(ULocal.lookAt(ULocal.offset(en.getLocation(), local.clone().getDirection().multiply(2)),en.getLocation()));

						if(pc > 0) {
							if(j%2 == 0) {
								ARSystem.playSound((Entity)player, "0slash", 1.5f);
								skill("c1047_s4");
							}
						} else {
							shrot();
						}
					},i);
				}
			}
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill5() {
		targetmob = !targetmob;
		return super.skill5();
	}
	int s = 0;
	@Override
	public void kill(LivingEntity p, LivingEntity e) {
		if(e == player) {
			ARSystem.spellLocCast(player, ULocal.lookAt(p.getLocation().clone(), player.getLocation()),"c1047_p");
		}
		if(e == player && sp > 0) {
			sp = 0;
			List<Entity> ens = ARSystem.box(player, new Vector(20,20,20), box.TARGET);
			Entity en = null;
			for(Entity et : ens) {
				if(et != p) {
					en = et;
				}
			}
			
			if(en != null) {
				s++;
				String t = "c47sp";
				if(s > 1 && AMath.random(2) == 1) t= "c47sp2";
				ARSystem.playSound((Entity)en, t);
				ARSystem.playSound(player, t);
				ARSystem.spellCast(player, en, "c1047_sp");
				delay = 20;
				delay(()->{
					sp = 20;
				},20);
			}
		}
	}
	int sp = 0;
	@Override
	public boolean tick() {
		if(pc > 0) pc --;
		if(delay > 0) delay--;
		if(sp > 0) {
			ARSystem.giveBuff(player, new Silence(player), 4);
			ARSystem.giveBuff(player, new Stun(player), 2);
			skill("c47_sp1");
			ARSystem.playSound((Entity)player, "0gun", 1.5f + AMath.random(5)*0.1f);
			for(Entity en : ARSystem.box(player, new Vector(10,10,10), box.TARGET)) {
				((LivingEntity)en).setNoDamageTicks(0);
				((LivingEntity)en).damage(2,player);
			}
			sp--;
		}
		if(s3 > 0) {
			s3--;
			player.setVelocity(s3v.multiply(1.2));
			List<Entity> el = ARSystem.PlayerBeamBox(player, 5, 2, box.TARGET);
			Entity e = null;
			if(el.size() > 0) e = ARSystem.PlayerBeamBox(player, 5, 2, box.TARGET).get(0);
			
			if(e != null) {
				s3 = 0;
				if(stack >= 50 && pc <= 0 && skillCooldown(0)) {
					s3 = 0;
					stack -= 50;
					spskillon();
					spskillen();
					sp = 20;
					s = 0;
					ARSystem.playSound((Entity)player, "c47sp2");
				} else {
					s3 = 0;
					delay = 20;
					LivingEntity en = (LivingEntity)e;
					en.teleport(en.getLocation().clone().add(0,3,0));
					player.teleport(ULocal.lookAt(player.getLocation().clone(), en.getLocation()));
					if(pc > 0) {
						ARSystem.playSound(en, "0attack", 0.5f, 2);
						en.setNoDamageTicks(0);
						en.damage(12,player);
						ARSystem.giveBuff(en, new Airborne(en), 20);
						ARSystem.giveBuff(en, new Silence(en), 20);
						en.setVelocity(player.getLocation().getDirection().multiply(2));
					} else {
						ARSystem.giveBuff(player, new Stun(player), 20);
						ARSystem.giveBuff(player, new Silence(player), 20);
						ARSystem.giveBuff(en, new Stun(en), 20);
						ARSystem.giveBuff(en, new Silence(en), 20);
						ARSystem.playSound(en, "0attack", 0.5f, 2);
						for(int i = 0; i<8; i++) {
							delay(()->{
								player.teleport(ULocal.lookAt(player.getLocation().clone(), en.getLocation()));
								bullet--;
								shrot();
							},2*i);
						}
					}
				}
			}
		} else if (delay <= 0 && pc <= 0){
			Entity e = null;
			if(targetmob) {
				e = ARSystem.boxSOne(player, new Vector(10,8,10), box.TARGET);
			} else {
				e = ARSystem.boxSPlayerOne(player, new Vector(10,8,10), box.TARGET);
			}
			if(e != null) {
				Location r = ULocal.lookAt(player.getLocation(), e.getLocation());
				ARSystem.playerRotate(player, r.getYaw(), r.getPitch());
				
				if(e != null && pc <= 0 && tk%pt == 0){
					bullet--;
					shrot();
					if(bullet < 0) {
						cooldown[1] = cooldown[3] = cooldown[4] = 0;
						pc = 200;
						bullet = 50;
						ARSystem.playSound((Entity)player, "c47s42",(float) 1);
					}
				}
			}
		}
		
		scoreBoardText.add("&c ["+Main.GetText("c1047:ps")+ "] : "+ bullet);
		if(psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c1047:sk0")+ "] : "+ stack +" / 50");
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {


		} else {
			if((sp > 0 ||player.hasPotionEffect(PotionEffectType.SPEED)) && AMath.random(100) <= 70) {
				ARSystem.playSound((Entity)player, "0miss");
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			} else if(sp > 0) {
				e.setDamage(e.getDamage() * 0.2);
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
		}
	}
	
	
	@Override
	public boolean skill9(){
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
				if(Rule.c.get(e) instanceof c62shinon){
					is = "kirito";
					break;
				}
			}
		}
		
		if(is.equals("yuuki")) {
			ARSystem.playSound((Entity)player, "c47yuuki");
		} else if(is.equals("kirito")) {
			ARSystem.playSound((Entity)player, "c47kirito");
		} else if(is.equals("sinon")) {
			ARSystem.playSound((Entity)player, "c47sinon");
		}else {
			ARSystem.playSound((Entity)player, "c1047db");
		}
		
		return true;
	}
	
	@Override
	public String getBgm() {
		// TODO Auto-generated method stub
		return "c47-2";
	}
}
