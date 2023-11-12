package chars.c;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Curse;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import event.Skill;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;
import util.Text;

public class c17shou extends c00main{
	List<Entity> getab = new ArrayList<Entity>();
	boolean ab[] = new boolean[8];
	int abcount = 0;
	
	int sk[] = new int[5];
	int sel = 1;
	
	int skill = 0;
	
	float damage = 1;

	LivingEntity target = null;
	int targettime = 0;
	
	
	public c17shou(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 17;
		load();
		text();
		
	}
	
	@Override
	public boolean skill1() {
		damage = 1;
		skill("c17_s1-"+sel);
		if(sel == 2) player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0.2));
		if(sel == 3) ARSystem.heal(player, 3);
		skill = sel;
		return true;
	}
	
	@Override
	public boolean skill2() {
		damage = 1;
		LivingEntity target;
		int range = 5;
		if(ab[3]) range = 10;
		try {
			target = (LivingEntity)ARSystem.PlayerBeamBox(player, 5, range, box.TARGET).get(0);
		} catch(Exception e) {
			cooldown[2] = 0;
			return true;
		}
		player.teleport(ULocal.lookAt(ULocal.offset(target.getLocation(), new Vector(3,0,0)), target.getLocation()));
		
		int turn = 0;
		int delay = 0;
		ARSystem.playSound((Entity)player, "c17j");
		while(turn < sk.length) {
			int sks = sk[turn];
			int cc = 0;
			for(int i=1;i<3;i++) if(turn+i < sk.length && sk[turn+i] == sks) cc++;
			int c = cc+1;
			turn+=cc;
			int d = 25;
			if(isps) d = 15;
			int tu = turn;
			delay(()->{
				
				String s = "";
				int count = 0;
				for(int i=0;i<sk.length;i++) {
					if(count <= 0) {
						count = 0;
						for(int j=1;j<3;j++) {
							if(j+i<sk.length && sk[i] == sk[i+j]) {
								count++;
							} else {
								break;
							}
						}
						if(count == 0) s+= "§a";
						if(count == 1) s+= "§e";
						if(count == 2) s+= "§c";
						if(tu-count == i) s +="【";
					} else {
						count--;
					}
					s += Text.get("c17:p"+sk[i]);
					if(tu == i) s +="】";
				}
				player.sendTitle("",s);
				
				ARSystem.giveBuff(target, new Stun(target), 30);
				ARSystem.giveBuff(player, new Stun(player), 30);
				ARSystem.giveBuff(player, new Silence(player), 30);
				if(ab[6]) ARSystem.giveBuff(target, new Nodamage(target), 0);
				if(ab[7]) ARSystem.giveBuff(player, new Nodamage(player), 30);
				
				player.teleport(ULocal.lookAt(ULocal.offset(target.getLocation(), new Vector(3,0,0)), target.getLocation()));
				
				if(sks == 3) {
					damage += 0.5f*c*c;
					ARSystem.playSound((Entity)player, "0miss");
				} else {
					skill("c17_s2-"+sks+""+c);
					skill = sks*10+c;
				}
			},d*delay++);
			turn++;
		}
		return true;
	}

	@Override
	public boolean skill3() {
		String s = "";
		sk[0] = sel;
		for(int i=1;i<sk.length;i++) {
			sk[i] = AMath.random(5);
			if(i > 0 && isps && AMath.random(10) <= 4) {
				sk[i] = sk[i-1];
			}
		}
		int count = 0;
		for(int i=0;i<sk.length;i++) {
			if(count <= 0) {
				count = 0;
				for(int j=1;j<3;j++) {
					if(j+i<sk.length && sk[i] == sk[i+j]) {
						count++;
					} else {
						break;
					}
				}

				if(count == 0) s+= "§a";
				if(count == 1) s+= "§e";
				if(count == 2) s+= "§c";
			} else {
				count--;
			}
			s += " " + Text.get("c17:p"+sk[i]);
		}
		player.sendTitle("",s);
		return true;
	}

	@Override
	public boolean skill4() {
		int select = AMath.random(5);
		while(sel == select) select = AMath.random(5);
		sel = select;
		player.sendTitle(Text.get("c17:p"+sel),"");
		return true;
	}
	@Override
	public boolean tick() {
		if(targettime > 0) {
			targettime--;
			if(targettime == 0) {
				target = null;
			}
		}
		if(tk%20 == 0) {
			String s = "";
			for(int i = 1; i <ab.length; i++) {
				if(ab[i]) s += "["+Main.GetText("main:tp"+i)+"]";
			}
			scoreBoardText.add(s);
		}
	
		if(tk%10==0) {
			if(abcount > 3 || ARSystem.AniRandomSkill == null) {
				if(!isps) {
					spskillen();
					spskillon();
					sk = new int[sk.length+2];
					skill3();
					for(int i=1; i<ab.length;i++) {
						if(!ab[i]) {
							if(i == 4) {
								setcooldown[2] *= 0.7;
							}
							if(i == 5) {
								sk = new int[sk.length+1];
								skill3();
							}
						}
						ab[i] = true;
					}
					abcount = 7;
					ARSystem.heal(player, 22);
					Rule.playerinfo.get(player).tropy(17,1);
					ARSystem.playSoundAll("c17sp");
				}
			}
			for(Entity e : player.getNearbyEntities(8, 8, 8)) {
				if(e instanceof Player && ! getab.contains(e)) {
					if(Rule.c.get(e) != null && ((Player)e).getGameMode() == GameMode.ADVENTURE) {
						int nb = Rule.c.get(e).getCode();
						ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 100);
						boolean ok = true;
						int cd = Integer.parseInt(Main.GetText("c"+nb+":type").replace(" tp",""));
						for(int i = 1 ; i<ab.length;i++) {
							if(ab[cd]) {
								ok = false;
							}
						}
						if(ok && !isps) {
							if(abcount == 0) ARSystem.playSound(player, "c17p");
							ab[cd] = true;
							if(cd == 4) {
								setcooldown[2] *= 0.7;
							}
							if(cd == 5) {
								sk = new int[sk.length+1];
								skill3();
							}
							abcount++;
						}
						getab.add(e);
						skill("c"+number+"_p");
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(skill == 1) {
				Wound curse = new Wound(target);
				curse.setDelay(player, 40, 0);
				ARSystem.giveBuff(target, curse, 400, 0.5);
				target.setNoDamageTicks(0);
				target.damage(1,player);
				ARSystem.spellCast(player, target, "bload");
			} else if(skill == 2) {
				target.setNoDamageTicks(0);
				target.damage(3,player);
				ARSystem.heal(target, 2);
				ARSystem.spellCast(player, target, "c17_s2-2e");
			} else if(skill == 4) {
				target.setNoDamageTicks(0);
				target.damage(5,player);
				target.setVelocity(player.getLocation().getDirection().multiply(2));
				ARSystem.spellCast(player, target, "c17_s2-4e");
			} else if(skill == 5) {
				if(target.getHealth() <= 6) {
					Skill.remove(target, player);
					Rule.buffmanager.selectBuffAddValue(player, "barrier", (float) target.getMaxHealth());
					ARSystem.spellCast(player, target, "c17_s2-5e");
				}
			}
			if(skill < 20) {
				if(skill == 11) {
					target.setNoDamageTicks(0);
					target.damage(2,player);
					ARSystem.spellCast(player, target, "bload");
				}
				if(skill == 12) {
					target.setNoDamageTicks(0);
					target.damage(2,player);
					ARSystem.spellCast(player, target, "bload");
				}
				if(skill == 13) {
					target.setNoDamageTicks(0);
					target.damage(15,player);
					ARSystem.spellCast(player, target, "bload");
				}
			} else if(skill < 30) {
				if(skill == 21) {
					ARSystem.heal(player, 1);
					target.setNoDamageTicks(0);
					target.damage(1,player);
					ARSystem.spellCast(player, target, "c17_s2-2e");
				}
				if(skill == 22) {
					ARSystem.heal(player, 1);
					target.setNoDamageTicks(0);
					target.damage(1.5,player);
					ARSystem.spellCast(player, target, "c17_s2-2e");
				}
				if(skill == 23) {
					ARSystem.heal(player, 2);
					target.setNoDamageTicks(0);
					target.damage(2.5,player);
					ARSystem.spellCast(player, target, "c17_s2-2e");
				}
			} else if(skill < 50) {
				if(skill == 41) {
					target.setNoDamageTicks(0);
					target.damage(1,player);
					ARSystem.spellCast(player, target, "c17_s2-4e");
				}
				if(skill == 42) {
					target.setNoDamageTicks(0);
					target.damage(3,player);
					ARSystem.spellCast(player, target, "c17_s2-4e");
					target.teleport(target.getLocation().clone().add(player.getLocation().getDirection().multiply(0.2)));
				}
				if(skill == 43) {
					target.setNoDamageTicks(0);
					target.damage(1.5,player);
					ARSystem.spellCast(player, target, "c17_s2-4e");
				}
			} else if(skill < 60) {
				double hp = target.getMaxHealth() - target.getHealth();
				if(skill == 51) {
					target.setNoDamageTicks(0);
					target.damage(hp * 0.2f,player);
					ARSystem.spellCast(player, target, "c17_s2-5e");
				}
				if(skill == 52) {
					target.setNoDamageTicks(0);
					target.damage(hp * 0.3f,player);
					ARSystem.spellCast(player, target, "c17_s2-5e");
				}
				if(skill == 53) {
					target.setNoDamageTicks(0);
					target.damage(hp * 0.35f,player);
					ARSystem.spellCast(player, target, "c17_s2-5e");
					target.teleport(target.getLocation().clone().add(new Vector(0,0.2,0)));
				}
			}
			target.setNoDamageTicks(0);
			target.damage(1,player);
		}
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player,"c17db"+AMath.random(4));
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			target = (LivingEntity) e.getEntity();
			targettime = 10;
			e.setDamage(e.getDamage()*damage);
			if(ab[2]) e.setDamage(e.getDamage()*1.3);
			if(isps) {
				ARSystem.heal(player,e.getDamage()*0.25);
			}
		} else {
			if(ab[1]) e.setDamage(e.getDamage()*0.7);
		}
		return true;
	}
}
