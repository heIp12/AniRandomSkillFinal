package c;

import java.util.List;

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
import c2.c6800origami;
import c2.c68origami;
import event.Skill;
import types.box;
import util.AMath;
import util.MSUtil;

public class c20kurumi extends c00main{
	int ticks = 0;
	double shdow;
	float mult = 1.5f;
	int bullets = 0;
	int tan = 0;
	double time = 0;
	int count;
	
	public c20kurumi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 20;
		MSUtil.setvar(player, "c13", 0);
		load();
		text();
		shdow = 120 + (Rule.buffmanager.GetBuffTime(player, "silence")/20);
	}
	
	@Override
	public void setStack(float f) {
		shdow = (int) f;
	}
	
	@Override
	public boolean skill1() {
		if(tan == 0) skill("c"+number+"_s1");
		if(tan == 1) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 2));
			skill("c"+number+"_s_alrf");
		}
		if(tan == 2) {
			ARSystem.heal(player,10);
			skill("c"+number+"_s_dalret");
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
			shdow -= 5;
			count+=5;
			player.sendTitle("§c< 5 >", "§6"+Main.GetText("c20:s5") ,10,0,5);
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
		if(bullets == 1) to+=5;
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

	@Override
	public boolean tick() {
		ticks++;
		if(player.hasPotionEffect(PotionEffectType.SPEED) && skillmult == 1.0) {
			skillmult = 1.5;
			Rule.buffmanager.selectBuffValue(player, "buffac",0.5f);
		}
		if(!player.hasPotionEffect(PotionEffectType.SPEED) && skillmult == 1.5) {
			skillmult = 1.0;
			Rule.buffmanager.selectBuffValue(player, "buffac",0);
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c20:ps")+ "]&f : "+ shdow);
			scoreBoardText.add("&c ["+Main.GetText("c20:sk3")+ "]&f : "+ Main.GetText("c20:s"+(bullets+1)));
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c20:sk0")+ "]&f : "+ Math.round(time) + " /20");
		}
		if(ticks%20==0) {
			if(shdow < 10&&!spben) {
				time+= (1*skillmult);
			} else {
				time = 0;
			}
			if(time >= 20 && !isps) {
				time = 0;
				shdow+=60;
				ARSystem.playSoundAll("c20sp");
				spskillon();
				spskillen();
			}
			if(isps) {
				Entity e = ARSystem.boxRandom(player, new Vector(999,999,999),box.TARGET);
				if(e != null) {
					Location loc = e.getLocation().clone();
					loc.setYaw(AMath.random(360));
					ARSystem.spellLocCast(player, loc.add(0,1,0), "c20_shdow");
					ARSystem.playSound(e, "c20sht");
				}
			}
			
			shdow-= (1*skillmult);
			if(shdow <= 0) {
				Skill.death(player, player);
			}
		}
		ticks%=20;
		return false;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(!(e.getEntity() instanceof ArmorStand)) {
				shdow+=(e.getFinalDamage()*2);
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
