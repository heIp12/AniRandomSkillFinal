package c;

import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.TimeStop;
import c2.c70raito;
import event.Skill;
import util.AMath;
import util.MSUtil;

public class c12conan extends c00main{
	int ticks = 0;
	int damage = 2;
	int count = 0;
	
	public c12conan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 12;
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		count++;
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		count++;
		skill("c"+number+"_s2");
        ticks += 100;
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p,Entity e) {
		skill("c12buff");
		ticks = 0;
	}

	@Override
	public boolean tick() {
		ticks++;
		
		if(ticks >= 400) {
			ticks = 0;
			Set<Player> ob = Rule.c.keySet();
			Player[] play = ob.toArray(new Player[ob.size()]);
			play[AMath.random(play.length)-1].damage(damage, play[AMath.random(play.length)-1]);
			s_score+=damage*10;
			damage++;
		}
		if(tk%20==0) {
			scoreBoardText.add("&c ["+Main.GetText("c12:ps")+ "]&f : " + ticks/20 +" / 50");
		}
		if(ARSystem.getPlayerCount() == 2 && player.getGameMode() == GameMode.ADVENTURE &&skillCooldown(0)) {
			spskillen();
			spskillon();
			s_score+=50000000;
			if(count==0) {
				Rule.playerinfo.get(player).tropy(12,1);
				
			}
			skill("c"+number+"_sp");
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new TimeStop(p), 300);
				if(p != player) {
					Rule.c.get(p).player.performCommand("c c12_spd");
					Rule.c.get(p).player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 280, 0));
				}
				if(Rule.c.get(p) != null && Rule.c.get(p) instanceof c70raito) {
					delay(()->{
						ARSystem.playSoundAll("c70t5");
					},50);
					delay(()->{
						ARSystem.playSoundAll("c70t3");
					},120);
					delay(()->{
						ARSystem.playSoundAll("c70t4");
					},290);
				}
				if(p!=player) {
					delay(new Runnable() {
						@Override
						public void run() {
							s_score-=50000000;
							Skill.remove(p, player);
						}
					}, 280);
				}
			}
		}
		if(MSUtil.getvar(player, "c12") == 6) {
			MSUtil.setvar(player, "c12", 0);
			Player killer = player;
			int kill = -1;
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p).s_kill > kill) {
					killer = p;
					kill = Rule.c.get(p).s_kill;
				}
			}
			if(kill == -1) {
				kill = 0;
			}
			for(LivingEntity p : Rule.c.keySet()) {
				killer.setNoDamageTicks(0);
				killer.damage((kill+1)*3,p);
				s_score+=(kill+1)*5;
			}
		}
		return true;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {

		}
		return true;
	}
}
