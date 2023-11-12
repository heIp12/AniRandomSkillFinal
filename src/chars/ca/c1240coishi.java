package chars.ca;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Dancing;
import buff.Fascination;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Sleep;
import buff.TimeStop;
import chars.c.c00main;
import chars.c3.c123rin;
import event.Skill;
import types.BuffType;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;
import util.Text;


public class c1240coishi extends c00main{
	public class coishiTime{
		public float cooldown;
		public int count;
	}
	HashMap<Player,coishiTime> p = new HashMap<>();
	int sk1 = 0;
	int t = 0;
	Location sk1l;
	
	public c1240coishi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1124;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		List<Entity> e = ARSystem.PlayerBeamBox(player, 80, 3, box.TARGET);
		if(e.size() > 0) {
			sk1l = player.getLocation();
			LivingEntity target = (LivingEntity)e.get(0);
			sk1 = 60;
			Location loc = target.getLocation();
			loc.setPitch(0);
			loc = ULocal.offset(loc, new Vector(2,0,0));
			loc = ULocal.lookAt(loc, target.getLocation());
			player.teleport(loc);
			ARSystem.giveBuff(target, new Fascination(target,player), 20,0.2);
			delay(()->{
				ARSystem.giveBuff(target, new Sleep(target), 20, 1);
				ARSystem.giveBuff(target, new Rampage(target), 40);
			},20);
			ARSystem.playSound((Entity)player, "c1124s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c1124s2");
		for(Entity e : ARSystem.box(player, new Vector(15, 5, 15), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			ARSystem.giveBuff(en, new Fascination(en, player), 40, 0.3);
		}
		skill("c1124_s2");
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c1124s3");
		skill("c1124_s3");
		for(Entity e : ARSystem.box(player, new Vector(15, 5, 15), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			double damage = en.getHealth();
			ARSystem.giveBuff(en, new Fascination(en, player), 40, 0.3);
			delay(()->{ARSystem.giveBuff(en, new Dancing(en), 40);},40);
			delay(()->{ARSystem.giveBuff(en, new Rampage(en), 40);},80);
			delay(()->{ARSystem.giveBuff(en, new Sleep(en), 80,2.5);},120);
			delay(()->{
				en.damage((damage - en.getHealth())*2,player);
			},200);
		}
		return true;
	}

	@Override
	public boolean tick() {
		t++;
		if(tk%20 == 0 ) scoreBoardText.add("&c ["+Main.GetText("c1124:ps")+ "] : &f" +AMath.round(((400-t)*0.05f),1));
		if(sk1 > 0) {
			sk1--;
			if(player.isSneaking()) {
				sk1 = 0;
				player.teleport(sk1l);
				ARSystem.playSound((Entity)player, "c1124s12");
			}
		}
		for(Player p : Rule.c.keySet()) {
			if(p == player) continue;
			double range = p.getLocation().distance(player.getLocation());
			if(range < 30) t = 0;
			if(range < 10) {
				p.showPlayer(player);
			} else {
				p.hidePlayer(player);
			}
		}
		if(t > 400) {
			Skill.remove(player, player);
		}
		
		for(Player pl : p.keySet()) {
			if(p.get(pl).cooldown > 0) p.get(pl).cooldown -= 0.05f;
		}
		
		List<Player> e = ARSystem.PlayerOnlyBeamBox(player, 9, 1, box.TARGET);
		if(e.size() > 0) {
			Entity en = e.get(0);
			float y = Math.abs(Math.abs((en.getLocation().getYaw()-180%180) - (player.getLocation().getYaw()))-180);
			float pc = Math.abs(en.getLocation().getPitch() - (player.getLocation().getPitch()*-1));
			if(y+pc < 22 && en instanceof Player) {
				Player pl = (Player)en;
				if(Rule.buffmanager.GetBuffTime(pl, "rampage") <= 0) {
					if(!p.containsKey(pl)) {
						p.put(pl, new coishiTime());
					}
					if(p.get(pl).cooldown <= 0) {
						p.get(pl).cooldown = 3;
						p.get(pl).count++;
						if(p.get(pl).count > 5) {
							p.get(pl).count = 0;
							p.get(pl).cooldown = 30;
							spskillon();
							spskillen();
							player.setGameMode(GameMode.SPECTATOR);
							player.teleport(en);
							ARSystem.playSound(en, "c1124sp");
							
							Location l = en.getLocation();
							l.setPitch(15);
							en.teleport(l);
							ARSystem.spellCast(player, en, "c1124_sp");
							delay(()->{
								ARSystem.spellCast(player, en, "c1124_sp2");
								delay(()->{
									ARSystem.spellCast(player, en, "c1124_sp3");
									delay(()->{
										ARSystem.spellCast(player, en, "c1124_sp4");
									},20);
								},70);
								
							},100);
							ARSystem.giveBuff((LivingEntity)en, new TimeStop((LivingEntity)en), 240);
							delay(()->{
								ARSystem.spellCast(player, en, "bload");
							},200);
							delay(()->{
								if(Rule.c.get(en) instanceof cc1180Ai) {
									ARSystem.giveBuff((LivingEntity)en, new TimeStop((LivingEntity)en), 80);
									ARSystem.playSound(en, "c1124ai"+AMath.random(2));
									delay(()->{
										Skill.remove(en, player);
									},80);
								} else {
									Skill.remove(en, player);
								}
								
							},240);
							delay(()->{
								player.setGameMode(GameMode.ADVENTURE);
								player.teleport(en);
							},260);
						} else {
							ARSystem.playSound(pl, "c1124p"+p.get(pl).count);
							ARSystem.playSound(player, "c1124p"+p.get(pl).count);
						}
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new Fascination(target,player), 20, 0.3);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(e.getDamager() instanceof LivingEntity) {
				for(Buff b : Rule.buffmanager.getBuffs((LivingEntity)e.getDamager()).getBuff()) {
					if(b.istype(BuffType.DEBUFF)) {
						e.setDamage(e.getDamage() * 0.3);
						break;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c123rin) {
					is = "o";
					break;
				}
			}
		}
		
		if(is.equals("o")) {
			ARSystem.playSound((Entity)player, "c1124orin");
		} else {
			ARSystem.playSound((Entity)player, "c1124db");
		}
		
		return true;
	}
	@Override
	public String getBgm() {
		return "c124-2";
	}
}
