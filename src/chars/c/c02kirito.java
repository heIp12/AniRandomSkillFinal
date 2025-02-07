package chars.c;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodie;
import buff.TimeStop;
import chars.c2.c62shinon;
import chars.c3.c133yukina;
import event.Skill;
import event.WinEvent;
import types.BuffType;
import types.box;

import util.ULocal;
import util.AMath;
import util.Holo;
import util.MSUtil;
import util.Map;

public class c02kirito extends c00main{
	private long millitime = 0;
	private boolean pson = false;
	int healcount = 0;
	int skillcount = 0;
	int spcount = 0;
	int s2 = 0;
	int s3 = 0;
	int s4 = 0;
	
	List<Entity> en = new ArrayList<>();
	int stack = 0;
	@Override
	public void setStack(float f) {
		stack = (int)f;
	}
	
	public c02kirito(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2;
		load();
		text();
	}
	public void sp(){
		spcount++;
		if(spcount > 5) {
			skill("c2_sp2");
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);;
				((LivingEntity)e).damage(1,player);
			}
		}
		delay(()->{if(spcount>0)spcount--;},17);
	}
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		ARSystem.playSound((Entity)player, "c2a3");
		for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
			((LivingEntity)e).setNoDamageTicks(0);;
			((LivingEntity)e).damage(3,player);
			ARSystem.potion((LivingEntity) e, 2, 60, 2);
		}
		if(pson) {
			sp();
			cooldown[4] -= 3;
			cooldown[2] -= 3;
			cooldown[3] -= 3;
			skillcount++;
			if(skillcount > 200) {
				Rule.playerinfo.get(player).tropy(2,1);
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		en.clear();
		ARSystem.playSound((Entity)player, "c2a4");
		s2 = 20;
		if(pson) {
			sp();
			cooldown[1] -= 3;
			cooldown[4] -= 3;
			cooldown[3] -= 3;
			skillcount++;
			if(skillcount > 200) {
				Rule.playerinfo.get(player).tropy(2,1);
			}
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		s3++;
		if(s3 == 1) {
			ARSystem.playSound((Entity)player, "c2a");
			skill("c2_s3_ia");
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);;
				((LivingEntity)e).damage(2,player);
			}
		}
		if(s3 == 2) {
			ARSystem.playSound((Entity)player, "c2a2");
			skill("c2_s32_ia");
			for(Entity e : ARSystem.box(player, new Vector(5,4,5), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);;
				((LivingEntity)e).damage(3,player);
			}
		}
		if(s3 == 3) {
			s3 = 0;
			ARSystem.playSound((Entity)player, "c2h");
			skill("c2_s33_ia");
			skill("c2_s33_ia2");
			for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);;
				((LivingEntity)e).damage(4,player);
			}
		}

		if(pson) {
			sp();
			cooldown[1] -= 3;
			cooldown[2] -= 3;
			cooldown[4] -= 3;
			skillcount++;
			if(skillcount > 200) {
				Rule.playerinfo.get(player).tropy(2,1);
			}
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		millitime = System.currentTimeMillis();
		ARSystem.playSound((Entity)player, "c2s");
		s4 = 60;
		
		if(pson) {
			sp();
			cooldown[1] -= 3;
			cooldown[2] -= 3;
			cooldown[3] -= 3;
			skillcount++;
			if(skillcount > 200) {
				Rule.playerinfo.get(player).tropy(2,1);
			}
		}
		return true;
	}

	@Override
	public boolean skill5() {
		if(player.isSneaking()) {
			player.stopSound("");
		} else {

		}
		return true;
	}
	
	public boolean skill0(EntityDamageByEntityEvent ev) {
		if(stack == 1000 ||
				(Rule.c.size() <= 2 && Rule.c.get(ev.getDamager()) != null && player.getHealth() <= 3 && (System.currentTimeMillis()-millitime)/1000.0 < 0.1 && !pson)) {
			stack = 0;
			if(skillCooldown(0)) {
				ev.setDamage(0);
				ev.setCancelled(true);
				ARSystem.giveBuff(player, new Nodie(player), 200);
				WinEvent event = new WinEvent(player);
				Bukkit.getPluginManager().callEvent(event);
				if(!event.isCancelled()) {
					spskillen();
					spskillen("최후의 스타버스트 스트림");
					
					Map.getMapinfo(1011);
					Location loc = Map.getCenter();
					loc.setY(4);
					loc.setPitch(0);
					player.teleport(loc);
					player.setGameMode(GameMode.SPECTATOR);

					LivingEntity e = player;
					for(Player p : Rule.c.keySet()) {
						if(p != player) {
							e = (LivingEntity)p;
						}
					}
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(Rule.c.get(p) != null && p != player) Rule.c.put(p, new c000humen(p, plugin, null));
						if(Rule.c.get(p) != null && p != player) ARSystem.giveBuff(p, new TimeStop(p), 800);
						if(p != player) p.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(5,0,5-AMath.random(0,100)*0.1)),loc));
						for(Player pl : Bukkit.getOnlinePlayers()) {
							if(e == p || p == player) {
								pl.showPlayer(p);
								continue;
							}
							pl.hidePlayer(p);
						}
					}
					
					LivingEntity en = e;
					Location lc = loc.clone();
					lc.setPitch(0);
					en.teleport(lc);
					delay(()->{
						ARSystem.playSoundAll("c2spc");
					},10);
					ARSystem.spellLocCast(player, loc, "c2_exsp");
					delay(()->{
						en.teleport(ULocal.offset(lc.clone(), new Vector(-0.3,0,0)));
						delay(()->{
							en.teleport(ULocal.offset(lc.clone(), new Vector(-0.5,0,0)));
							delay(()->{
								en.teleport(ULocal.offset(lc.clone(), new Vector(-0.8,0,0)));
								delay(()->{
									en.teleport(ULocal.offset(lc.clone(), new Vector(-1.2,0,0)));
									delay(()->{
										en.teleport(ULocal.offset(lc.clone(), new Vector(-1.5,0,0)));
									},60);
								},60);
							},79);
						},41);
					},155);
					delay(()->{
						ARSystem.spellCast(player,en,"c2_exse");
						ARSystem.potion(en, 14, 120, 1);
					},650);
					delay(()->{
						Skill.win(player);
						tpsdelay(()->{
							ARSystem.playSoundAll("c2select");
						},40);
					},720);

					

				}
			}
		}
		else if((System.currentTimeMillis()-millitime)/1000.0 <= 0.15 && !pson && skillCooldown(0)) {
			ARSystem.playSound((Entity)player, "c2f");
			setcooldown[1] -= 1;
			setcooldown[2] -= 1;
			setcooldown[3] -= 1;
			setcooldown[4] -= 1;
			spskillon();
			spskillen();
			pson = true;
		} else {
			player.sendTitle("" + AMath.round( (double)((System.currentTimeMillis()-millitime)/1000.0),2), "",30,20,10);
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(battleTime < 80) {
			if(tk%10 == 0) ARSystem.heal(player, 0.6 + 0.2*s_kill);
		}
		if(tk%20==0 && isps) {
			scoreBoardText.add("&c ["+Main.GetText("c2:sk0")+ "]&f : "+ spcount + " / 6");
		}
		if(s4 > 0) s4--;
		if(s2 > 0) {
			s2--;
			Location loc = player.getLocation();
			skill("c2_s1_ia2");
			loc.setPitch(0);
			player.setVelocity(loc.getDirection().multiply(0.8));
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				if(!en.contains(e)) {
					en.add(e);
					((LivingEntity)e).setNoDamageTicks(0);;
					((LivingEntity)e).damage(4,player);
				}
			}
		}
		if(tk%3 == 1 && isps && stack == 52 && player.isSneaking() && Rule.buffmanager.selectBuffType(player, BuffType.SILENCE).size() <= 0) {
			if(tk%20 == 0) Holo.create(player.getLocation(), "§a매크로 사용중",80,new Vector(0,0.1,0));
			if(cooldown[1] <= 0) {
				cooldown[1] = setcooldown[1];
				skill1();
			} else if(cooldown[3] <= 0) {
				cooldown[3] = setcooldown[3];
				skill3();
			} else if(cooldown[2] <= 0) {
				cooldown[2] = setcooldown[2];
				skill2();
			} else if(cooldown[4] <= 0) {
				cooldown[4] = setcooldown[4];
				skill4();
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			if(e.getEntity() instanceof Player) {
				if(s4 > 0) {
					s4 = 0;
					ARSystem.playSound((Entity)player, "c2g");
					player.setVelocity(player.getLocation().getDirection().setY(0).multiply(-4));
					e.setDamage(e.getDamage() * 0.1);
					skill0(e);
					return false;
				}
			}
		} else {
			if(isps) {
				e.setDamage(e.getDamage() * 0.5f);
			}
		}
		return true;
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
				if(Rule.c.get(e) instanceof c47ren){
					is = "ren";
					break;
				}
				if(Rule.c.get(e) instanceof c62shinon){
					is = "sinon";
					break;
				}
				if(Rule.c.get(e) instanceof c07shana){
					is = "shana";
					break;
				}
				if(Rule.c.get(e) instanceof c133yukina){
					is = "yukina";
					break;
				}
			}
		}
		
		if(is.equals("yuuki")) {
			ARSystem.playSound((Entity)player, "c2yuki");
		} else if(is.equals("ren")) {
			ARSystem.playSound((Entity)player, "c2ren");
		} else if(is.equals("sinon")) {
			ARSystem.playSound((Entity)player, "c2sinon");
		} else if(is.equals("shana")) {
			ARSystem.playSound((Entity)player, "c2shana");
		} else if(is.equals("yukina")) {
			ARSystem.playSound((Entity)player, "c2yukina");
		}else  {
			ARSystem.playSound((Entity)player, "c2db");
		}
		
		return true;
	}
}
