package chars.c;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import chars.c2.c68origami;
import chars.ca.c6800origami;
import event.Skill;
import types.box;
import util.AMath;
import util.MSUtil;
import util.Map;
import util.Pair;

public class c20kurumi extends c00main{
	int ticks = 0;
	double shdow;
	float mult = 1.5f;
	int bullets = 0;
	int tan = 0;
	double time = 0;
	int count;
	
	Queue<Pair<Location, Double>> locals = new LinkedList<>();
	Queue<Pair<Location, Double>> back = new LinkedList<>();
	
	public c20kurumi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 20;
		if(p != null) MSUtil.setvar(player, "c13", 0);
		load();
		text();
		shdow = 130 + (Rule.buffmanager.GetBuffTime(player, "silence")/20);
	}
	
	@Override
	public void setStack(float f) {
		shdow = (int) f;
	}
	
	@Override
	public boolean skill1() {
		if(tan == 0) skill("c"+number+"_s1");
		if(tan == 1) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 4));
			skill("c"+number+"_s_alrf");
		}
		if(tan == 2) {
			skill("c"+number+"_s_dalret");
			back.clear();
			back.addAll(locals);
			locals.clear();
			int size = back.size();
			ARSystem.giveBuff(player, new Nodamage(player), size);
			ARSystem.giveBuff(player, new Silence(player), size);
			ARSystem.giveBuff(player, new Stun(player), size);
			
			for(int i=0;i<size;i++) {
				Location loc = back.element().getKey();
				double hp = back.element().getValue();
				tpsdelay(()->{
					player.teleport(loc);
					if(hp >= 1 && hp < player.getMaxHealth()) {
						player.setHealth(hp);
					}
				},size-i);
				tpsdelay(()->{
					ARSystem.heal(player,4);
				},size);
				back.poll();
			}
		}
		if(tan == 3) {skill("c"+number+"_s_zain");}
		if(tan == 4) {skill("c"+number+"_s_hat");}
		tan = 0;
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_e"+(bullets+1));
		tan = bullets+1;
		if(tan == 1) {
			shdow -= 12;
			count+=12;
			player.sendTitle("§c < 12 >", "§6"+Main.GetText("c20:s5") ,10,0,5);
		}
		if(tan == 2) {
			shdow -= 6;
			count+=6;
			player.sendTitle("§c< 6 >", "§6"+Main.GetText("c20:s5") ,10,0,5);
		}
		if(tan == 3) {
			shdow -= 14;
			count+=14;
			player.sendTitle("§c< 14 >", "§20"+Main.GetText("c20:s5") ,10,0,5);
		}
		if(tan == 4) {
			shdow -= 8;
			count+=8;
			player.sendTitle("§c< 8 >", "§6"+Main.GetText("c20:s5") ,10,0,5);
		}
		if(count >=150) {
			Rule.playerinfo.get(player).tropy(20,1);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		bullets++;
		if(bullets > 3) {
			bullets = 0;
		}
		Skillmsg();
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(tan == 0) skill("c"+number+"_s1-2");
		return true;
	}
	public void Skillmsg() {
		String one = Main.GetText("c20:s"+(bullets+1));
		String to = "§6"+Main.GetText("c20:s4") + " : §c";
		if(bullets == 0) to+=12;
		if(bullets == 1) to+=6;
		if(bullets == 2) to+=14;
		if(bullets == 3) to+=8;
		
		int lk = bullets+1;
		int rk = bullets-1;
		if(lk == 4) {
			lk = 0;
		}
		if(rk == -1) {
			rk = 3;
		}
		
		one = "§7"+Main.GetText("c20:s"+(rk+1))+"§a>>" +"§f§l" + one +"§a>>§f"+ Main.GetText("c20:s"+(lk+1));
		player.sendTitle(one, to ,0,20,0);
	}
	int tks = 0;
	@Override
	public boolean tick() {
		if(tk%2 == 0) {
			locals.add(new Pair<Location,Double>(player.getLocation(),player.getHealth()));
			if(locals.size() > 100) locals.poll();
		}
		
		ticks++;
		if(player.hasPotionEffect(PotionEffectType.SPEED) && skillmult == 1.0) {
			skillmult = 2;
			Rule.buffmanager.selectBuffValue(player, "buffac",2f);
		}
		if(!player.hasPotionEffect(PotionEffectType.SPEED) && skillmult == 2) {
			skillmult = 1.0;
			Rule.buffmanager.selectBuffValue(player, "buffac",0);
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c20:ps")+ "]&f : "+ shdow);
			scoreBoardText.add("&c ["+Main.GetText("c20:sk3")+ "]&f : "+ Main.GetText("c20:s"+(bullets+1)));
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c20:sk0")+ "]&f : "+ Math.round(time) + " /30");
		}
		if(ticks%20==0) {
			if(shdow < 10) {
				time+= (1*skillmult);
			} else {
				time = 0;
			}
			if(time >= 30 && !isps) {
				time = 0;
				shdow+=60;
				ARSystem.playSoundAll("c20sp");
				spskillon();
				spskillen();
				for(int i=0; i<100; i++) {
					ARSystem.spellLocCast(player, Map.randomLoc().add(0,1,0), "c20_shdow");
				}
			}
			shdow-= (1*skillmult);
			if(shdow <= 0) {
				Skill.death(player, player);
			}
		}
		if(isps && tks%60==0) {
			for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
				((LivingEntity)e).setNoDamageTicks(0);
				((LivingEntity)e).damage(1,player);
			}
		}
		tks++;
		ticks%=20;
		return false;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(!(e.getEntity() instanceof ArmorStand)) {
				shdow+=(e.getFinalDamage()*2);
			}
			if(e.getEntity() instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) e.getEntity();
				if(entity.getHealth() - e.getDamage() <= 1) {
					shdow += entity.getMaxHealth();
				}
			}
		} else {

		}
		return true;
	}
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c6800origami || Rule.c.get(e) instanceof c68origami) {
					is = "origami";
					break;
				}

			}
		}
		
		if(is.equals("origami")) {
			ARSystem.playSound((Entity)player, "c20origami");
		} else {
			ARSystem.playSound((Entity)player, "c20db");
		}
		
		return true;
	}
}
