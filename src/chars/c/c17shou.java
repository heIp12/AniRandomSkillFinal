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
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;
import util.Text;

public class c17shou extends c00main{
	List<Entity> getab = new ArrayList<Entity>();
	int ab[] = new int[8];
	int abselect = 0;
	int abcount = 0;
	int spell[] = new int[21];
	int spellcount = 0;
	
	int ticks = 0;
	int select = 0;
	int set = -1;
	
	LivingEntity target = null;
	int targettime = 0;
	
	float mult = 1;
	
	public c17shou(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 17;
		load();
		text();
		for(int i=0; i<8;i++) {
			ab[i] = -2;
		}
		abcount++;
		ab[0]= AMath.random(7);
		select = ab[0]+1;
		if(ARSystem.AniRandomSkill == null) for(int i=0;i<8;i++) {
			ab[i] = i+1;
			abcount = 7;
		}
		if(ARSystem.isGameMode("lobotomy")) sskillmult += 1;
	}
	
	@Override
	public boolean skill1() {
		if(select > 0) skill(select-1);
		return true;
	}
	
	public boolean skill(int i) {
		skill("c"+number+"_s"+i);
		if(i == 4) sk4();
		if(i == 5) sk5();
		if(i == 6) sk6();
		if(i == 7) sk7();
		return true;
	}
	
	int i = 0;
	
	@Override
	public boolean skill2() {
		if(spellcount == 0) {
			cooldown[2] = 0;
			return false;
		}
		i = 0;
		
		int delay = 0;
		int c = 0;
		for(int j=0; j<spellcount;j++) {
			delay(()->{
				skill20();
				if(target == null) {
					target = (LivingEntity) ARSystem.boxSOne(player, new Vector(5,5,5), box.TARGET);
					targettime = 10;
					if(target == null) {
						targettime = 0;
						i = 999;
					}
				}
			},delay);
			delay += Integer.parseInt(Main.GetText("c17:s"+spell[j]+"_delay"));
			c += Integer.parseInt(Main.GetText("c17:s"+spell[j]+"_cooldown"));
		}
		cooldown[2] = setcooldown[2]+(c*0.1f);
		return true;
	}
	
	void skill20(){
		if(targettime > 0) {
			if(i >= 999) return;
			int skill = spell[i];
			if(skill == 1) {
				Location loc = target.getLocation();
				loc.setPitch(0);
				player.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(2,0,0)),target.getLocation()));
				targettime = 10;
				delay(()->{
					ARSystem.playSound(target, "0attack4");
					ARSystem.spellLocCast(player, loc, "c17_y1_e");
					ARSystem.giveBuff(target, new Silence(target), 20);
					target.setNoDamageTicks(0);
					target.damage(2,player);
				},3);
			}
			if(skill == 2) {
				targettime = 10;
				for(int j=0;j<3;j++) {
					delay(()->{
						ARSystem.playSound(target, "0katana4",1.4f);
						skill("c17_y_s2");
					},j);
				}
			}
			if(skill == 3) {
				targettime = 10;
				Location loc = player.getLocation();
				player.teleport(loc);
				player.setVelocity(loc.getDirection().multiply(-1).setY(0));
				delay(()->{
					ARSystem.playSound(target, "0sword2");
					skill("c17_y_s3");
				},2);
			}
			if(skill == 4) {
				Location loc = ULocal.offset(player.getLocation(), new Vector(2,0,0));
				ARSystem.spellLocCast(player, loc, "0bload");
				for(Entity e : loc.getWorld().getNearbyEntities(loc, 3, 3, 3)){
					if(ARSystem.isTarget(e, player, box.TARGET)) {
						ARSystem.playSound(target, "0attack4", 2 - (i *0.1f));
						target.setNoDamageTicks(0);
						target.damage(i,player);
						target = (LivingEntity) e;
						targettime = 10;
						ARSystem.heal(player, i/2);
					}
				}
			}
			if(skill == 5) {
				targettime = 4;
				mult *= (0.02 + AMath.random(0, 149)*0.02);
				player.sendTitle("§cX"+mult, "");
				skill("c17_y5");

			}
			if(skill == 6) {
				Location loc = ULocal.offset(player.getLocation(), new Vector(2,0,0));
				ARSystem.spellLocCast(player, loc, "c17_y6_e");
				ARSystem.playSound(target, "0attack4",0.4f);
				for(Entity e : loc.getWorld().getNearbyEntities(loc, 3, 3, 3)){
					if(ARSystem.isTarget(e, player, box.TARGET)) {
						target.setNoDamageTicks(0);
						target.damage(4,player);
						target = (LivingEntity) e;
						targettime = 10;
						delay(()->{
							target.teleport(ULocal.lookAt(target.getLocation(), player.getLocation()));
							target.setVelocity(player.getLocation().getDirection().multiply(1));
						},2);
					}
				}

			}
			if(skill == 7) {
				Location loc = target.getLocation();
				loc.setPitch(0);
				loc.setYaw(AMath.random(0,360));
				ARSystem.spellLocCast(player, loc, "c17_y7_e");
				ARSystem.giveBuff(player, new Nodamage(player), 20);
				player.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(2.5,0,0)),target.getLocation()));
				targettime = 10;
				delay(()->{
					ARSystem.playSound(target, "0attack2");
					target.setNoDamageTicks(0);
					target.damage(2,player);
				},3);
			}
			i++;
		} else {
			if(i != 999) {
				try {
					skill(spell[i]);
				} catch (Exception e) {
					
				}
				i++;
			}
		}
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(e.getPlayer() == player) {
			try {
				String msg = e.getMessage().replace(" ", "");
				short o;
				for(int i=0; i<msg.length(); i++) o = Short.parseShort(""+msg.charAt(i));
				if(!isps && msg.length() <= abcount)  msg = msg.substring(0, abcount);
				if(msg.length() > 21) msg = msg.substring(0, 21);
				abselect = 0;
				
				for(int j=0;j<msg.length();j++) {
					boolean issk = false;
					for(int ii : ab) {
						if(ii == Integer.parseInt(""+msg.charAt(j))) {
							issk = true;
						}
					}
					if(issk) spell[abselect++] = Integer.parseInt(""+msg.charAt(j));
				}
				spellcount = abselect;
				return false;
			} catch(NumberFormatException nfe) {
				
			}
		}
		return true;
	}
	
	public void Skillmsg() {
		String one = Main.GetText("main:tp"+(ab[abselect]));
		String to = "";
		
		if(set > -1) {
			to = "§cSave : "+Main.GetText("main:tp"+(set-1));
		}
		if(abcount > 1) {
			int lk = abselect+1;
			int rk = abselect-1;
			if(lk == abcount) {
				lk = 0;
			}
			if(rk == -1) {
				rk = abcount-1;
			}
			lk = ab[lk];
			rk = ab[rk];
			one = "§7"+Main.GetText("main:tp"+rk)+"§a>>" +"§f§l" + one +"§a>>§f"+ Main.GetText("main:tp"+lk);
		}
		
		player.sendTitle(one, to ,0,20,0);
	}
	public void sk4() {
		List<Entity> entitys = ARSystem.box(player, new Vector(8, 4, 8),box.TARGET);
		for(Entity e : entitys) {
			if(Rule.buffmanager.OnBuffValue((LivingEntity) e, "barrier")) {
				Rule.buffmanager.selectBuffAddValue(player, "barrier",(float) (Rule.buffmanager.GetBuffValue((LivingEntity) e, "barrier")*0.8));
				Rule.buffmanager.selectBuffValue((LivingEntity) e, "barrier",0);
			}
			if(Rule.buffmanager.OnBuffValue((LivingEntity) e, "plushp")) {
				Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) (Rule.buffmanager.GetBuffValue((LivingEntity) e, "plushp")*0.8));
				Rule.buffmanager.selectBuffValue((LivingEntity) e, "plushp",0);
			}
			target = (LivingEntity) e;
			targettime = 10;
		}
	}
	public void sk5() {
		
		LivingEntity e = ((LivingEntity)ARSystem.boxRandom(player, new Vector(5,5,5),box.TARGET));
		if(e != null) {
			e.setNoDamageTicks(0);
			e.damage(AMath.random(0, 10),player);
			ARSystem.spellCast(player, e, "c17_s5-2");
		}
		target = e;
		targettime = 10;
	}
	public void sk6() {
		cooldown[1]+=2;
		List<Entity> e = ARSystem.box(player, new Vector(8, 5, 8),box.TARGET);
		for(Entity entity : e) {
			ARSystem.giveBuff((LivingEntity) entity, new Silence((LivingEntity) entity), 60);
			ARSystem.giveBuff((LivingEntity) entity, new Stun((LivingEntity) entity), 40);
			target = (LivingEntity) entity;
			targettime = 10;
		}
	}
	public void sk7() {
		cooldown[1]+=3;
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		ARSystem.giveBuff(player, new Stun(player), 60);
		ARSystem.giveBuff(player, new Silence(player), 60);
		delay(()->{
			List<Entity> entitys = ARSystem.box(player, new Vector(7, 5, 7),box.TARGET);
			for(Entity en : entitys) {
				((LivingEntity)en).damage(7,player);
			}
		},62);
	}
	
	@Override
	public boolean skill3() {
		if(isps) {
			if(abcount*3 > spellcount) {
				spell[spellcount++] = select-1;
			}
		} else {
			if(abcount > spellcount) {
				spell[spellcount++] = select-1;
			}
		}
		return true;
	}

	@Override
	public boolean skill4() {
		if(player.isSneaking()) {
			if(abcount > 0) {
				abselect--;
				if(abselect <= -1) {
					abselect = abcount-1;
				}
				select = (ab[abselect]+1);
				Skillmsg();
			}
		} else {
			if(abcount > 0) {
				abselect++;
				if(abselect >= abcount) {
					abselect = 0;
				}
				select = (ab[abselect]+1);
				Skillmsg();
			}
		}
		return true;
	}
	
	@Override
	public boolean skill5() {
		if(player.isSneaking()) {
			spellcount--;
		} else {
			spellcount = 0;
		}
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
		if(abcount > 7) {
			abcount = 7;
		}


		scoreBoardText.add("&c ["+Main.GetText("c17:s3")+ "]&f : "+abcount);
		if(tk%20==0&& abcount > 0) {

			scoreBoardText.add("&c ["+Main.GetText("c17:s1")+ "]&f : "+Main.GetText("main:tp"+(select-1)));
			
			if(spellcount > 0) {
				String n = "";
				String n2 = "";
				String n3 = "";
				String n4 = "";
				for(int i=0;i<spellcount;i++) {
					if(n3.length() > 25) {
						n4+=Main.GetText("main:tp"+spell[i])+">";
					} else if(n2.length() > 25) {
						n3+=Main.GetText("main:tp"+spell[i])+">";
					} else if(n.length() > 15) {
						n2+=Main.GetText("main:tp"+spell[i])+">";
					} else {
						n+=Main.GetText("main:tp"+spell[i])+">";
					}
				}
				scoreBoardText.add("&c ["+Main.GetText("c17:s2")+ "]&f : "+ n);
				if(!n2.equals("")) scoreBoardText.add("&f>"+ n2);
				if(!n3.equals("")) scoreBoardText.add("&f>"+ n3);
				if(!n4.equals("")) scoreBoardText.add("&f>"+ n4);
			}
			String n = "";
			for(int i = 0; i < spellcount;i++) {
				n += Main.GetText("main:tp"+spell[i]);
			}
		}
		ticks++;

		if(ticks%10==0) {
			if(abcount > 3) {
				if(!isps) {
					spskillen();
					spskillon();
					for(int i=1; i<ab.length;i++) {
						ab[i-1] = i;
					}
					abcount = 7;
					hp = 50;
					player.setMaxHealth(50);
					ARSystem.heal(player, 22);
					Rule.playerinfo.get(player).tropy(17,1);
				}
			}
			for(Entity e : player.getNearbyEntities(8, 8, 8)) {
				if(e instanceof Player && ! getab.contains(e)) {
					if(Rule.c.get(e) != null && ((Player)e).getGameMode() == GameMode.ADVENTURE) {
						int nb = Rule.c.get(e).getCode();
						ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 100);
						player.setMaxHealth(player.getMaxHealth()-1);
						boolean ok = true;
						int cd = Integer.parseInt(Main.GetText("c"+nb+":type").replace(" tp",""));
						
						for(int i = 0 ; i<ab.length;i++) {
							if(ab[i] == cd) {
								ok = false;
							}
						}
						
						if(ok && !isps) {
							if(abcount == 0) ARSystem.playSound(player, "c17fs");
							ab[abcount++] = cd;
						}
						
						getab.add(e);
						skill("c"+number+"_p");
					}
				}
			}
		}
		ticks%=60;
		return false;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player,"c17db"+AMath.random(4));
		return true;
	}
	
	@Override
	public void TargetSpell(SpellTargetEvent e, boolean mycaster) {
		if(mycaster) {
			target = (LivingEntity) e.getTarget();
			targettime = 10;
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			target = (LivingEntity) e.getEntity();
			targettime = 10;
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
			if(isps) {
				ARSystem.heal(player,e.getDamage()*0.25);
			}
			if(mult > 1) {
				e.setDamage(e.getDamage()*mult);
				mult = 1;
			}
		} else {

		}
		return true;
	}
}
