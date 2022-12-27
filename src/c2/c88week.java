package c2;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.events.SpellTargetEvent;
import com.nisovin.magicspells.materials.MagicBlockMaterial;
import com.nisovin.magicspells.util.BlockUtils;

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
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import c.c000humen;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c88week extends c00main{
	Location loc;
	int speed = 100;
	int addspeed = 2;
	int st = 1200;
	int maxst = 1200;
	int maxspeed = 1200;
	int sdelay = 0;
	int spsound = 0;
	boolean ds = false;
	boolean[] skills = new boolean[13];
	int scount = 0;
	
	@Override
	public void setStack(float f) {
		maxspeed = (int) f;
		maxst = (int) f;
	}
	
	public c88week(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 88;
		load();
		text();
		c = this;
		for(int i=0;i<skills.length;i++)skills[i] = false;
		if(Map.lastplay == 106) {
			maxst = st = 2000;
			maxspeed = 2000;
			addspeed = 5;
		}
	}

	@Override
	public boolean skill1() {
		return true;
	}
	
	@Override
	public boolean skill2() {
		return true;
	}
	
	public void skon(){
		int i = 0;
		for(int j=0;j<100;j++) {
			i = AMath.random(skills.length)-1;
			if(!skills[i]) j = 1000;
		}
		skills[i] = true;
		if(i == 0) return;
		player.sendTitle("", Main.GetText("c88:s"+(1+i)),10,0,10);
		
		if(i == 0) {
			st += 200;
			ARSystem.heal(player, 5);
			delay(()->{skills[0] = true;},600);
		}
		if(i == 1) {
			maxspeed += 300;
			addspeed += 5;
			delay(()->{
				maxspeed -= 300;
				addspeed -= 5;
			},200);
			delay(()->{
				skills[1] = true;
			},600 - (int)(60*(skillmult * sskillmult)));
		}
		if(i == 2) {
			maxspeed += 200;
			delay(()->{
				maxspeed -= 200;
			},100);
			delay(()->{
				skills[2] = true;
			},500- (int)(50*(skillmult * sskillmult)));
		}
		if(i == 3) {
			maxspeed += 300;
			delay(()->{
				maxspeed -= 300;
			},200);
			delay(()->{
				skills[3] = true;
			},400- (int)(40*(skillmult * sskillmult)));
		}
		if(i == 4) {
			st += 500;
			ARSystem.heal(player, 10);
			delay(()->{
				skills[4] = true;
			},400 - (int)(40*(skillmult * sskillmult)));
		}
		if(i == 5) {
			maxspeed += 200;
			delay(()->{
				maxspeed -= 200;
			},160);
			delay(()->{
				skills[5] = true;
			},400 - (int)(40*(skillmult * sskillmult)));
		}
		if(i == 6) {
			addspeed += 5;
			delay(()->{
				addspeed -= 5;
			},300);
			delay(()->{
				skills[6] = true;
			},600 - (int)(60*(skillmult * sskillmult)));
		}
		if(i == 7) {
			st += 300;
			ARSystem.heal(player, 7);
			delay(()->{
				skills[7] = true;
			},600 - (int)(60*(skillmult * sskillmult)));
		}
		if(i == 8) {
			addspeed += 5;
			delay(()->{
				addspeed -= 5;
			},300);
			delay(()->{
				skills[8] = true;
			},600 - (int)(60*(skillmult * sskillmult)));
		}
		if(i == 9) {
			st += 300;
			ARSystem.heal(player, 5);
			delay(()->{
				skills[9] = true;
			},500 - (int)(50*(skillmult * sskillmult)));
		}
		if(i == 10) {
			st += 500;
			ARSystem.heal(player, 7);
			delay(()->{
				skills[10] = true;
			},600 - (int)(60*(skillmult * sskillmult)));
		}
		if(i == 11) {
			maxspeed += 300;
			delay(()->{
				maxspeed -= 300;
			},300);
			delay(()->{
				skills[11] = true;
			},600 - (int)(60*(skillmult * sskillmult)));
		}
		if(i == 12) {
			maxspeed += 200;
			delay(()->{
				maxspeed -= 200;
			},200);
			delay(()->{
				skills[12] = true;
			},600 - (int)(60*(skillmult * sskillmult)));
		}
		if(i == 13) {
			addspeed += 7;
			delay(()->{
				addspeed -= 7;
			},100);
			delay(()->{
				skills[13] = true;
			},600 - (int)(60*(skillmult * sskillmult)));
		}
		if(st > maxst) st = maxst;
	}

	boolean effect = false;
	@Override
	public boolean tick() {
		if(loc == null) loc = player.getLocation();
		if(spsound > 0) spsound--;
		if(!ds && player.isSprinting() && st > 300) {
			ds = true;
			player.setSprinting(false);
			speed = 400;
			scount = 0;
			sdelay = 0;
		}
		if(ds) {
			sdelay--;
			if(sdelay <= 0 && spsound <= 0) {
				scount++;
				sdelay = 20 + AMath.random(100);
				if(scount == 1) {
					ARSystem.playSound(player, "c88p1");
				}
				if(scount == 2) {
					ARSystem.playSound(player, "c88p2");
				}
				if(scount > 2) {
					ARSystem.playSound(player, "c88s"+AMath.random(10));
				}
			}
			int rd = 100 - (int)(5*(skillmult * sskillmult));
			if(rd < 10) rd = 10;
			if(AMath.random(rd) == 1) skon();
			if(speed < maxspeed) {
				speed += addspeed;
			} else {
				speed = maxspeed;
			}
			float pm = Math.abs(player.getLocation().getPitch() - loc.getPitch());
			if(pm > 2) {
				st -= pm*3;
				if(isps) st += pm*2;
				speed*= (0.98 -(pm*0.0025));
			}
			player.setVelocity(player.getLocation().getDirection().multiply(speed*0.0008).setY(-2));
			Location local = player.getLocation().clone().add(player.getLocation().getDirection().multiply(1).setY(0));
			if(!BlockUtils.isPathable(local.getBlock()) && BlockUtils.isPathable(local.add(0,1,0).getBlock()) && BlockUtils.isPathable(local.add(0,2,0).getBlock())) {
				player.setVelocity(player.getLocation().getDirection().multiply(speed*0.0015).setY(0.5));
			} else if(!BlockUtils.isPathable(local.getBlock()) && !BlockUtils.isPathable(local.add(0,1,0).getBlock())) {
				speed-=addspeed;
				speed*= 0.85;
				speed-=1;
			}

			loc = player.getLocation();
			st -= speed*0.001;
			if(isps) st += speed*0.0008;
			if(st <= 0 || speed < 0) {
				ds = false;
			}
			for(Entity e : ARSystem.box(player, new Vector(2+(speed*0.001),1+(speed*0.001),2+(speed*0.001)), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);
				((LivingEntity)e).damage(speed*0.0003,player);
			}
			for(int i=0; i < speed/200;i++) {
				skill("c88_e");
			}
			int sound = 10;
			sound -= speed/200;
			if(sound < 4) sound = 4;
			
			if(effect) {
				for(int i=0; i < speed/200;i++) {
					skill("c88_e2");
				}
				if(tk%sound == 0) ARSystem.playSound((Entity)player,"minecraft:entity.horse.step_wood",(float) (0.5 + speed/1000.f));
			} else {
				if(tk%sound == 0) ARSystem.playSound((Entity)player,"minecraft:entity.horse.step",(float) (0.5 + speed/1000.f));
			}
			if(speed > 2200 && !isps) {
				Rule.playerinfo.get(player).tropy(88,1);
				spsound = 240;
				player.stopSound("");
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c88sp");
				delay(()->{
					maxst = 2200;
					st += 500;
					addspeed += 2;
					maxspeed += 50;
				},40);
				delay(()->{
					st += 500;
					addspeed += 3;
					maxspeed += 150;
					effect = true;
				},80);
				delay(()->{
					st += 500;
					addspeed += 5;
					maxspeed += 250;
				},120);
				delay(()->{
					if(Map.lastplay == 106) {
						st = 5000;
						addspeed += 30;
						maxspeed += 3000;
						player.setMaxHealth(player.getMaxHealth() * 6);
						player.setHealth(player.getMaxHealth());
					} else {
						st = 2200;
						addspeed += 10;
						maxspeed += 600;
						player.setMaxHealth(player.getMaxHealth() * 3);
						player.setHealth(player.getMaxHealth());
					}
					s_score += 500;
				},240);
			}
		} else {
			if(st < maxst) {
				st += 3;
				if(ARSystem.gameMode == modes.LOBOTOMY) st+= 17;
			} else {
				st = maxst;
			}
		}
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c88:t1")+"] : " + speed +" / " + maxspeed);
			scoreBoardText.add("&c ["+Main.GetText("c88:t3")+"] : " + (addspeed*25));
			scoreBoardText.add("&c ["+Main.GetText("c88:t2")+"] : " + st);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 3);
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 0.7);
		}
		return true;
	}
}
