package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import types.box;

import util.AMath;
import util.MSUtil;

public class c09youmu extends c00main{
	Location loc;
	int sk3count;
	int s3c;
	int tick = 0;
	List<Entity> et = new ArrayList<Entity>();
	boolean isbuff = false;
	HashMap<Entity,Integer> attack = new HashMap<Entity,Integer>();
	
	public c09youmu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 9;
		load();
		text();
		sk3count = 3;
	}
	
	@Override
	public void setStack(float f) {
		sk3count = (int) f;
	}
	
	@Override
	public void info() {
		super.info();
		if(ARSystem.AniRandomSkill != null && s_kill == ARSystem.AniRandomSkill.player-1) {
			Rule.playerinfo.get(player).tropy(9,1);
		}
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(sk3count > 0) {
			loc = player.getLocation();
			sk3count--;
			skill("c"+number+"_s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(loc != null) {
			player.teleport(loc);
			skill("c"+number+"_s3");
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c"+number+"_s4");
		return true;
	}
	
	@Override
	public boolean tick() {
		tick++;
		s3c+= (skillmult + sskillmult);
		if(tk%20 ==0) {
			scoreBoardText.add("&c ["+Main.GetText("c9:sk2")+ "]&f : " + sk3count);
		}
		if(sk3count < 5 && s3c > 160) {
			s3c = 0;
			sk3count++;
		}
		if(!isbuff&&MSUtil.isbuff(player, "c9_s4_d")) {
			et.clear();
			isbuff = true;
		}
		if(isbuff&&!MSUtil.isbuff(player, "c9_s4_d")) {
			isbuff = false;
			for(Entity e : et) {
				if(sk3count < 5) {
					ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 100);
					sk3count++;
				}
			}
			if(et.size() >= 3) {
				spskillen();
				ARSystem.playSoundAll("c9sp");
				int i = 0;
				Location loc = player.getLocation();

				
				for(LivingEntity e : player.getWorld().getLivingEntities()) {
					if (e != player && ARSystem.isTarget(e, player,box.TARGET)) {
						i++;
						ARSystem.giveBuff(e, new Stun(e), 20);
						delay(new Runnable() {
							LivingEntity entitys = e;
							@Override
							public void run() {
								ARSystem.spellLocCast(player,entitys.getLocation(),"c9_sp");
								ARSystem.giveBuff(player, new Silence(player), 10);
								ARSystem.giveBuff(player, new Stun(player), 10);
								ARSystem.giveBuff(player, new Nodamage(player), 10);
								delay(()->{
									for(int i=0;i<4;i++)ARSystem.spellLocCast(player,entitys.getLocation(),"c9_s1_ia");
								},5);
								delay(()->{
									for(int i=0;i<4;i++)ARSystem.spellLocCast(player,entitys.getLocation(),"c9_s2_ia");
									for(int i=0; i<3;i++) {
										delay(()->{
											e.setNoDamageTicks(0);
											e.damage(8,player);
										},4*i);
									}
								},60);
								for(int i=1; i<4;i++) {
									delay(()->{
										e.setNoDamageTicks(0);
										e.damage(20,player);
										ARSystem.spellLocCast(player,entitys.getLocation(),"c9_sp2_ia");
									},60+200*i);
								}
							}
						}, 1+(i*5));
					}
				}
				i++;
				
				delay(new Runnable() {
					Location lc = loc;
					@Override
					public void run() {
						player.teleport(lc);
					}
				}, i*5);
				
			}
		}
		tick%=200;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getEntity() instanceof LivingEntity) {
				if(attack.get(e.getEntity()) == null) {
					attack.put(e.getEntity(),1);
				} else {
					attack.put(e.getEntity(), attack.get(e.getEntity())+1);
					if(attack.get(e.getEntity()) > 3) {
						attack.put(e.getEntity(),0);
						ARSystem.spellCast(player,e.getEntity(),"c9_p0");
					}
				}
			}
		} else {
			if(MSUtil.isbuff(player, "c9_s4_d")) {
				e.setCancelled(true);
				ARSystem.add(et,e.getDamager());
				if(et.size() >= 3 && !isps) {
					spskillon();
				}
				return false;
			}
		}
		return true;
	}
}
