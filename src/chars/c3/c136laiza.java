package chars.c3;

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
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
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
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.Ice;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c.c45momo;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import mode.MLoboTomy;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Pair;
import util.Text;

public class c136laiza extends c00main{
	List<Location> loc = new ArrayList<Location>();
	int stack = 0;
	
	HashMap<String,Integer> potion = new HashMap<>();
	int item = 1;
	float cr = 1;
	int crt = 0;
	
	public c136laiza(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 136;
		load();
		text();
		c = this;
		ARSystem.potion(p, 1, 100000, 0);
		if(ARSystem.AniRandomSkill != null) {
			delay(()->{
				if(Rule.c.size() <= 2 || (Rule.c.size() > 5 && Map.mapid < 100)) stack += 4;
				if(Rule.c.size() >= 15) stack += (Rule.c.size()/2)*2;
			},Math.max(0,ARSystem.AniRandomSkill.time*-20)+20);
		}
	}

	@Override
	public void setStack(float f) {
		if(stack > 1000) {
			potion.put("i"+((int)f)/1000, ((int)f)%1000);
		} else {
			stack = (int)f;
		}
	}

	@Override
	public boolean skill3() {
		boolean ok = true;
		Location lc = player.getLocation().clone();
		lc.setY(0);
		for(Location l : loc) {
			if(l.distance(lc) < 10) {
				ok = false;
			}
		}
		if(ok) {
			ARSystem.playSound(player, "c136s1"+AMath.random(3));
			stack++;
			loc.add(lc);
			ARSystem.giveBuff(player, new Stun(player), 10);
			if(AMath.random(10) <= 1) {
				stack++;
				player.sendTitle("","§f럭키! 재료를 하나 더 얻었다!",20,20,10);
			}
		} else {
			cooldown[3] = 0;
			player.sendTitle("","§f여기서는 더이상 채집할수 없어!",20,20,10);
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(stack >= 2) {
			List<Entity> e = ARSystem.box(player, new Vector(15,15,15), box.TARGET);
			for(Entity en : e) {
				if(Rule.c.get(en) != null) {
					int nb = Rule.c.get(en).number;
					if(nb == 122 || nb == 45) {
						Location loc = player.getLocation();
						loc.setPitch(20);
						player.teleport(loc);
						ARSystem.playSound(player, "c136s2start");
						ARSystem.playSound(player, "c136s2"+AMath.random(3));
						stack -=2;
						cr*=0.99;
						skill("c136_p");
						ARSystem.giveBuff(player, new Stun(player), 60);
						ARSystem.giveBuff(player, new Silence(player), 60);
						delay(()->{
							int item = 100;
							if(potion.containsKey("i"+item)) {
								potion.put("i"+item,potion.get("i"+item)+1);
							} else {
								potion.put("i"+item, 1);
							}
							player.sendTitle("§f"+Text.get("c136:i"+(item))+"을 만들었다!", "§f"+Text.get("c136:i"+(item)+"_lore"),20,20,10);
							hp+=1;
							player.setMaxHealth(player.getMaxHealth() +1);
							ARSystem.heal(player, 1);
							ARSystem.playSound(player, "c136c"+item);
							stack += 2;
						},60);
						return true;
					}
				}
			}
			crt++;
			if(crt >= 10) Rule.playerinfo.get(player).tropy(136, 1);
			Location loc = player.getLocation();
			loc.setPitch(20);
			player.teleport(loc);
			ARSystem.playSound(player, "c136s2start");
			ARSystem.playSound(player, "c136s2"+AMath.random(3));
			stack -=2;
			cr*=0.99;
			skill("c136_p");
			ARSystem.giveBuff(player, new Stun(player), 60);
			ARSystem.giveBuff(player, new Silence(player), 60);
			
			delay(()->{
				if(AMath.random(5) <= 1 && skillCooldown(0)) {
					spskillon();
					spskillen();
					int item;
					ARSystem.playSound(player, "c136sp");
					switch(AMath.random(3)) {
						case 1:
							item = AMath.random(4)+6;
							if(potion.containsKey("i"+item)) {
								potion.put("i"+item,potion.get("i"+item)+1);
							} else {
								potion.put("i"+item, 1);
							}
							if(item == 6) potion.put("i"+item, 1);
							delay(()->{player.sendTitle("§f"+Text.get("c136:i"+(item))+"을 만들었다!", "§f"+Text.get("c136:i"+(item)+"_lore"),20,20,10);
							},60);
							break;
						case 2:
							item = AMath.random(6);
							int c = AMath.random(6)+4;
							if(potion.containsKey("i"+item)) {
								potion.put("i"+item,potion.get("i"+item)+c);
							} else {
								potion.put("i"+item, c);
							}
							delay(()->{player.sendTitle("§f"+Text.get("c136:i"+(item))+"을 "+c+"개 만들었다!", "§f"+Text.get("c136:i"+(item)+"_lore"),20,20,10);},60);
							break;
						case 3:
							int i;
							for(i =1; i < 6; i++) {
								if(potion.containsKey("i"+i)) {
									potion.put("i"+i,potion.get("i"+i)+2);
								} else {
									potion.put("i"+i, 2);
								}
							}
							delay(()->{player.sendTitle("§f포션을 모두 만들었다!","",20,20,10);},60);
							break;
					}
				} else {
					int item = AMath.random(6);
					if(AMath.random(10) <= 2) item = 1;
					if(potion.containsKey("i"+item)) {
						potion.put("i"+item,potion.get("i"+item)+1);
					} else {
						potion.put("i"+item, 1);
					}
					player.sendTitle("§f"+Text.get("c136:i"+(item))+"을 만들었다!", "§f"+Text.get("c136:i"+(item)+"_lore"),20,20,10);
					hp+=1;
					player.setMaxHealth(player.getMaxHealth() +1);
					ARSystem.heal(player, 1);
					ARSystem.playSound(player, "c136c"+item);
				}
			},60);
		} else {
			ARSystem.playSound(player, "c136s2er");
			player.sendTitle("","§f소재를 더 채집해야 해!",20,20,10);
		}
		return true;
	}
	
	@Override
	public boolean skill1() {
		if(potion.get("i"+item) > 0) {
			potion.put("i"+item,potion.get("i"+item)-1);
			if(player.isSneaking()) {
				skill("c136_s"+item);
				ARSystem.playSound(player, "c136s3"+AMath.random(2));
			} else {
				if(item == 1) {
					ARSystem.playSound(player, "c136prself");
					ARSystem.playSound((Entity)player, "0explod");
					skill("c136_s11");
					player.damage(8);
				} else if(item == 2) {
					ARSystem.playSound((Entity)player,"0ice");
					ARSystem.giveBuff(player, new Stun(player), 80);
					ARSystem.giveBuff(player, new Nodamage(player), 80);
					if(ARSystem.isGameMode("lobotomy")) {
						for(Entity e : ARSystem.box(player, new Vector(8,5,8), box.TEAM)) {
							LivingEntity en = (LivingEntity)e;
							ARSystem.giveBuff(en, new Stun(en), 80);
							ARSystem.giveBuff(en, new Nodamage(en), 80);
						}
					}
					skill("c136_s24");
				} else if(item == 3) {
					ARSystem.playSound((Entity)player, "0heal",1.6f);
					ARSystem.heal(player, player.getMaxHealth()*0.3 + 3);
					if(ARSystem.isGameMode("lobotomy")) {
						for(Entity e : ARSystem.box(player, new Vector(8,5,8), box.TEAM)) {
							LivingEntity en = (LivingEntity)e;
							ARSystem.heal(en, en.getMaxHealth()*0.3 + 3);
						}
					}
				} else if(item == 4) {
					ARSystem.playSound((Entity)player, "block.lava.ambient",2f,2f);
					for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.MYALL)){
						LivingEntity en = (LivingEntity)e;
						Wound w = new Wound(en);
						w.setValue(1);
						w.setDelay(NpcPlayer.player,40,0);
						ARSystem.giveBuff(en, w, 400);
					}
				} else if(item == 5) {
					ARSystem.playSound((Entity)player, "0heal",1.6f);
					ARSystem.giveBuff(player, new PowerUp(player), 200, 1);
					player.damage(2);
					if(ARSystem.isGameMode("lobotomy")) {
						for(Entity e : ARSystem.box(player, new Vector(8,5,8), box.TEAM)) {
							LivingEntity en = (LivingEntity)e;
							ARSystem.giveBuff(en, new PowerUp(en), 200, 1);
						}
					}
				} else if(item == 6) {
					ARSystem.playSound((Entity)player, "0win",1.6f);
					player.setFallDistance(0);
					player.setVelocity(player.getLocation().getDirection().multiply(2));
				} else if(item == 7) {
					ARSystem.playSound(player, "c136prself");
					ARSystem.playSound((Entity)player, "0explod");
					skill("c136_s71");
					player.damage(15);
				} else if(item == 8) {
					ARSystem.playSound((Entity)player,"0ice");
					ARSystem.giveBuff(player, new Stun(player), 200);
					ARSystem.giveBuff(player, new Nodamage(player), 200);
					skill("c136_s81");
				} else if(item == 9) {
					for(int i =0;i<30;i++) {
						skill("c136_s92");
					}
					for(Entity e : ARSystem.box(player, new Vector(10,5,10), box.TARGET)) {
						ARSystem.spellLocCast(player, e.getLocation(), "c136_s92");
					}
				} else if(item == 10) {
					skill("c136_s103");
				} else if(item == 100) {
					ARSystem.playSound((Entity)player, "0heal");
					ARSystem.heal(player, 1);
				} 
			}
			player.sendTitle("", "§a< "+Text.get("c136:i"+(item)) +"("+potion.get("i"+item)+") >" ,0,20,0);
		} else {
			player.sendTitle("", "§4< "+Text.get("c136:i"+(item)) +"("+potion.get("i"+item)+") >" ,0,20,0);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		int it = item;
		if(player.isSneaking()) {
			item--;
			while(!potion.containsKey("i"+item) || potion.get("i"+item) <= 0) {
				item--;
				if(item <= 0) {
					item = 6;
					while(!potion.containsKey("i"+item) || potion.get("i"+item) <= 0) {
						item--;
						if(item <= 0) {
							item = it;
							break;
						}
					}
					break;
				}
			}
		} else {
			item++;
			while(!potion.containsKey("i"+item) || potion.get("i"+item) <= 0) {
				item++;
				if(item > 20) {
					item = 1;
					while(!potion.containsKey("i"+item) || potion.get("i"+item) <= 0) {
						item++;
						if(item > 110) {
							item = it;
							break;
						}
					}
					break;
				}
			}
		}
		
		player.sendTitle("§f< "+Text.get("c136:i"+(item)) +"("+potion.get("i"+item)+") >", "§f"+Text.get("c136:i"+(item)+"_lore"),0,20,0);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.heal(target, target.getMaxHealth()*0.3 + 3);
		}
		if(n.equals("2")) {
			Wound w = new Wound(target);
			w.setValue(1);
			w.setDelay(player,40,0);
			ARSystem.giveBuff(target, w, 400);
		}
		if(n.equals("3")) {
			ARSystem.giveBuff(target, new PowerUp(target), 200, 1);
			target.damage(2,player);
		}
		if(n.equals("4")) {
			ARSystem.addBuff(target, new Ice(target,player), 80, 2);
		}
		if(n.equals("5")) {
			ARSystem.addBuff(target, new Ice(target,player), 400, 2);
		}
		if(n.equals("6")) {
			if(Rule.c.get(target) != null) {
				Rule.c.get(target).sskillmult += 0.1f;
				int nb = Rule.c.get(target).number;
				if(nb == 122) {
					c122yuyuco yk = (c122yuyuco)Rule.c.get(target);
					yk.hp +=5;
					target.setMaxHealth(yk.hp);
					ARSystem.heal(target, 5);
					yk.stack += 3;
					yk.upgrad();
				} else if(nb == 45) {
					c45momo mm = (c45momo)Rule.c.get(target);
					for(int i=0; i<10;i++) {
						Location l = target.getLocation();
						l.setYaw(36*i);
						l.add(l.getDirection().multiply(1.5f));
						ARSystem.playSound(target, "c45s1");
						if(mm.sp) {
							ARSystem.spellLocCast((Player)target, l.clone().add(0,1.5,0), "c45_flower2");
						} else {
							ARSystem.spellLocCast((Player)target, l.clone().add(0,1.5,0), "c45_flower");
						}
					}
				}
			}
		}
		if(n.equals("7")) {
			Wound w = new Wound(target);
			w.setValue(1);
			w.setDelay(player,40,0);
			w.setEffect("c136_s102");
			ARSystem.giveBuff(target, w, 20000);
		}
	}
	
	public boolean tick() {
		if(tk%20 == 0) {
			scoreBoardText.add("&e["+Text.get("c136:t1")+"] &f" + stack);
			scoreBoardText.add("&e["+Text.get("c136:t2")+"] &f" + AMath.round(cr*100, 2)+"%");
			if(ARSystem.isGameMode("lobotomy") && AMath.random(8) <= 1) {
				stack += Math.max(1,MLoboTomy.level/4);
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			for(int i =0; i < cr;i++) {
				e.setDamage(e.getDamage()*cr);
			}
		}
		return true;
	}

}
