package chars.c;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import chars.c2.c61tyana;
import chars.ca.c2100abigeil;
import event.Skill;
import types.box;
import util.AMath;
import util.GetChar;
import util.MSUtil;

public class c21abigeil extends c00main{
	int ticks = 0;
	int p = 0;
	boolean p1 = false;
	boolean p2 = false;
	int ps = 0;
	int sk3 = 0;
	int fanic = 0;
	
	int playercount = 0;
	
	HashMap<Entity,Integer> fear = new HashMap<Entity,Integer>();
	Entity witch = null;
	int count = 0;
	
	public c21abigeil(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 21;
		load();
		text();
	}
	public void passive() {
		p++;
		if(count == 20) Rule.playerinfo.get(player).tropy(21,1);
		
		int fr = 0;

		if(p == 15 && !p1) {
			p1 = true;
			ps++;
		}
		if(fanic > playercount*10 + 50 && !p2) {
			p2 = true;
			ps++;
		}
		
		if(ps == 1) {
			ps++;
			player.setMaxHealth(player.getMaxHealth() +20);
			Rule.buffmanager.selectBuffAddValue(player, "plushp",15);
			skill("c21_b1");
		}
		if(ps == 3) {
			ps++;
			Rule.c.put(player, new c2100abigeil(player,Rule.gamerule, this));
		}
	}
	@Override
	public boolean skill1() {
		count++;
		passive();
		ARSystem.playSound((Entity)player, "c21s");
		for(Entity e : ARSystem.box(player, new Vector(6,6,6), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			addFear(e, 3);
			ARSystem.addBuff(en,new Panic(en), 60);
			ARSystem.addBuff(en,new Stun(en), fear.get(e)*4);
			ARSystem.spellCast(player, en, "c21_s");
		}
		for(Entity e : ARSystem.box(player, new Vector(6,6,6), box.TEAM)) {
			LivingEntity en = (LivingEntity)e;
			ARSystem.heal(en, 10);
			ARSystem.spellCast(player, en, "c21_s");
		}
		ARSystem.heal(player, 10);
		return true;
	}
	
	@Override
	public boolean skill2() {
		passive();
		ARSystem.playSound((Entity)player, "c21s2");
		skill("c21_s2");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			addFear(target, 6);
			ARSystem.addBuff(target,new Panic(target), 120);
			ARSystem.addBuff(target,new Rampage(target), 120);
		}
		if(n.equals("2")) {
			target.damage(111,player);
		}
	}
	
	@Override
	public boolean skill3() {
		passive();
		witch = ARSystem.RandomPlayer();
		sk3 = 1000;
		while(witch == player) {
			witch = ARSystem.RandomPlayer();
			if(Rule.c.size() == 1) {
				break;
			}
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle("",witch.getName() + Main.GetText("c21:t1"));
		}
		return true;
	}

	public void sp(Entity e) {
		spskillon();
		spskillen();
		if(Rule.c.get(e) != null && Rule.c.get(e) instanceof c61tyana) {
			ARSystem.playSound(e, "c61kami2");
		}
		if(p > 9) {
			skill("c21_b1");
			delay(new Runnable() {
				@Override
				public void run() {
					skill("c21_b1");
				}
			},280);
				
		}
		
		if(Rule.c.get(e) != null) {
			ARSystem.giveBuff((LivingEntity) e, new Noattack((LivingEntity) e), 300);
			ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 300);
			ARSystem.giveBuff((LivingEntity) e, new Stun((LivingEntity) e), 300);
		}
		ARSystem.giveBuff(player, new Nodamage(player), 300);
		ARSystem.giveBuff(player, new Silence(player), 300);
		ARSystem.giveBuff(player, new Stun(player), 300);
		
		ARSystem.spellCast(player, e, "look1");
		ARSystem.spellCast(player, e, "look2");
		ARSystem.playSound((Entity)player, "c21sp");
		
		skill("c21_sp1e");
		delay(()->{
			skill("c21_spe2");
		},60);
		delay(()->{
			
		},120);
		delay(()->{
			skill("c21_spd");
		},190);
		delay(()->{
			
		},240);
		delay(()->{
			skill("c21_spe4");
		},260);
	}

	@Override
	public boolean tick() {
		if(playercount == 0) playercount = Rule.c.size();
		if(tk%20 == 0) {
			if(psopen){
				scoreBoardText.add("&c ["+Main.GetText("c21:ps")+ "]&f : "+ fanic +" / " + (playercount*10 + 50));
				for(Entity p : fear.keySet()) {
					if(fear.get(p) > 0) {
						scoreBoardText.add("&c ["+Main.GetText("c21:sk0")+ "] : "+p.getName() +":"+ fear.get(p));
					}
					if(p.isDead() || p instanceof Player && ((Player) p).getGameMode() != GameMode.ADVENTURE) {
						fear.put(p, 0);
					}
				}
			}
		}
		if(witch != null) {
			if(tk%10 == 0) ARSystem.potion((LivingEntity)witch, 24, 20, 20);
			
			if(sk3 > 0) {
				sk3 -= 1*(skillmult+sskillmult);
				if(sk3 == 0) {
					witch = null;
					ARSystem.playSoundAll("c21s3");
					delay(()->{
						for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
							((LivingEntity)e).damage(5,player);
							addFear(e, 7);
							ARSystem.addBuff((LivingEntity) e,new Panic((LivingEntity) e), 100);
						}
					},20);
				}
			}
			if(tk%20 == 0) {
				scoreBoardText.add("&c ["+Main.GetText("c21:sk3")+ "]&f : "+ witch.getName() +" : "+AMath.round(sk3/20,1)+"(s)");
			}
			if(witch.isDead() || witch instanceof Player && ((Player) witch).getGameMode() != GameMode.ADVENTURE) {
				witch = null;
				sk3 = 0;
				ARSystem.playSoundAll("c21s3");
				for(Player pl : Rule.c.keySet()) {
					for(int i =0; i < Rule.c.get(pl).cooldown.length;i++) {
						Rule.c.get(pl).cooldown[i] = 0;
					}
				}
			}
		}
		return false;
	}
	
	
	public void addFear(Entity e,int i) {
		fanic+=i;
		if(e instanceof LivingEntity) {
			if(fear.get(e) == null) {
				fear.put(e,0);
			} else {
				fear.put(e, fear.get(e)+i);
				if(fear.get(e) >= 40) {
					fear.put(e,0);
					if(skillCooldown(0)) sp(e);
				}
			}
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {

		}
		return true;
	}
}
