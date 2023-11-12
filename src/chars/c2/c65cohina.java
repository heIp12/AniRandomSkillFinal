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
import event.Skill;
import manager.AdvManager;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c65cohina extends c00main{
	double crit = 2;
	
	int sk2 = 0;
	int sk4 = 0;
	
	int sk = 0;
	int sks = 0;
	
	int cr = 0;
	
	boolean enjudb = false;
	boolean enjua = false;
	
	public c65cohina(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 65;
		load();
		text();
		c = this;
	}

	void sk(int i){
		if(isps) {
			if(sk <=10 && sks != i) {
				skill("c65_sp");
				ARSystem.playSound((Entity)player, "0katana2");
				for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(3,player);
				}
			}
		}
		sks = i;
		sk = 0;
	}

	@Override
	public boolean skill1() {
		sk(1);
		ARSystem.playSound((Entity)player, "c65s1");
		skill("c65_s1");
		for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
			((LivingEntity)e).setNoDamageTicks(0);
			((LivingEntity)e).damage(1,player);
		}
		delay(()->{player.setVelocity(player.getLocation().getDirection().setY(0).multiply(1.5));},2);
		delay(()->{
			skill("c65_s1");
			for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
				player.setVelocity(new Vector(0,0,0));
				((LivingEntity)e).setNoDamageTicks(0);
				((LivingEntity)e).damage(2,player);
			}
		},6);
		return true;
	}

	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c65s2");
		sk2 = 10;
		ARSystem.potion(player, 14, 10, 0);
		return true;
	}

	@Override
	public boolean skill3() {
		sk(3);
		ARSystem.playSound((Entity)player, "c65s3");
		skill("c65_s3");
		for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
			((LivingEntity)e).setNoDamageTicks(0);
			((LivingEntity)e).damage(5,player);
			ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 20);
			Location loc = e.getLocation();
			loc = ULocal.lookAt(loc, player.getLocation());
			e.setVelocity(loc.getDirection().multiply(-1.5));
		}
		return true;
	}

	@Override
	public boolean skill4() {
		if(isps) {
			cooldown[4] = 0;
			return true;
		}
		sk(4);
		ARSystem.playSound((Entity)player, "c65s4");
		sk4 = 200;
		ARSystem.heal(player, 2);
		return true;
	}

	@Override
	public boolean tick() {
		sk++;
		if(sk4>0)sk4--;
		if(sk2>0) {
			sk2--;
			player.setVelocity(player.getLocation().getDirection().setY(0).multiply(1));
			if(sk2==0) {
				sk(2);
				player.setVelocity(new Vector(0,0.4,0));
				skill("c65_s2");
				for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(3,player);
					ARSystem.giveBuff((LivingEntity) e, new Stun((LivingEntity) e), 20);
				}
			}
		}
		Entity e = ARSystem.boxSOne(player, new Vector(8,8,8), box.TARGET);
		if(e != null) {
			double range = AMath.round(e.getLocation().distance(player.getLocation()),2);
			float rangep = 1;
			if(sk4 > 0 || isps) {
				rangep = 2;
			}
			if(range <= 4+rangep && range >= 4-rangep) {
				player.sendTitle("§4<§c "+e.getName()+" §4>", "§c"+Text.get("c65:p1") +" : " + range,0,10,0);
			} else {
				player.sendTitle("< "+e.getName()+" >", Text.get("c65:p1") +" : " + range,0,10,0);
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c65:ps")+ "] : "+ AMath.round(crit*100,0) +"%");
			if(sk4>0) {
				scoreBoardText.add("&c ["+Main.GetText("c65:sk4")+ "] : "+ AMath.round(sk4*0.05,0) );
			}
		}
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			double range = e.getEntity().getLocation().distance(player.getLocation());
			float rangep = 1;
			if(sk4 > 0 || isps) {
				rangep = 2;
				e.setDamage(e.getDamage() * 0.75f);
			}
			if(range <= 4+rangep && range >= 4-rangep) {
				cr++;
				e.setDamage(e.getDamage() * crit);
				cooldown[3]-=6;
				crit+=0.1;
				if(crit >= 3) {
					Rule.playerinfo.get(player).tropy(65,1);
				}
				ARSystem.playSound((Entity)player, "0bload");
				if(sk4 > 0) {
					ARSystem.heal(player, e.getDamage());
					cooldown[1]-=1;
					cooldown[2]-=1;
					cooldown[3]-=1;
					cooldown[4]-=1;
				}
				if(cr >= 5 && !isps) {
					spskillon();
					spskillen();
					ARSystem.playSound((Entity)player, "c65sp");
					sk4 = 20000;
				}
				player.sendTitle("§4Critical!!", "§c<"+AMath.round(e.getDamage(),1)+">");
			} else {
				cr = 0;
			}
		} else {
			if(Rule.c.get(e.getDamager()) != null && Rule.c.get(e.getDamager()) instanceof c56enju && !enjua) {
				enjua = true;
				e.setDamage(0);
				ARSystem.giveBuff(player, new Nodamage(player), 60);
				ARSystem.giveBuff(player, new Silence(player), 60);
				ARSystem.giveBuff((LivingEntity) e.getDamager(), new Nodamage((LivingEntity) e.getDamager()), 60);
				ARSystem.giveBuff((LivingEntity) e.getDamager(), new Silence((LivingEntity) e.getDamager()), 60);
				ARSystem.playSound((Entity)player, "c65enju");
			}
			if(sk4 > 0) {
				e.setDamage(e.getDamage()*0.7);
			} else if(cooldown[4] > 0) {
				e.setDamage(e.getDamage()*1.5);
			}
		}
		return true;
	}

	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(ARSystem.getPlayerCount() == 3) {
			String is = "";
			for(Entity ee : Rule.c.keySet()) {
				if(Rule.c.get(ee) != null) {
					if(Rule.c.get(ee) instanceof c56enju) {
						is = "enju";
						break;
					}
				}
			}
			if(is.equals("enju")) {
				ARSystem.playSoundAll("c65enju2");
			}
		}
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c56enju) {
					is = "enju";
					break;
				}

			}
		}
		
		if(is.equals("enju") && !enjudb) {
			ARSystem.playSound((Entity)player, "c65enju3");
			enjudb = true;
		} else if(is.equals("enju")) {
			ARSystem.playSound((Entity)player, "c65enju4");
		} else {
			ARSystem.playSound((Entity)player, "c65db");
		}
		return true;
	}
}
