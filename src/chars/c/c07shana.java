package chars.c;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.TimeStop;
import net.minecraft.server.v1_12_R1.Entity;

import util.AMath;
import util.MSUtil;

public class c07shana extends c00main{
	private int attack = 0;
	int remit = 0;
	
	public c07shana(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 7;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		attack = (int) f;
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(remit > 0) remit--;
		
		if(tk%20==0&&psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c7:sk0")+ "]&f : " + attack +" / 50");
		}
		if(MSUtil.isbuff(player, "c7_s3_b")) {
			for(org.bukkit.entity.Entity entity : player.getNearbyEntities(15, 15, 15)) {
				ARSystem.giveBuff((LivingEntity) entity, new Silence((LivingEntity) entity), 20);
			}
		}
		return true;
	}
	
	public boolean skill0(EntityDamageByEntityEvent e){
		e.setDamage(0);
		ARSystem.giveBuff(player, new TimeStop(player), 420);
		skill("c"+number+"_sp");
		delay(new Runnable(){@Override public void run() {
			Rule.buffmanager.selectBuffTime(player, "timestop",0);
			skill("c"+number+"_spat");
			
		}}, 420);
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage() + 2);
			
			
			if(remit <= 0) {
				tpsdelay(()->{
					((LivingEntity)e.getEntity()).setNoDamageTicks(0);
				},0);
				remit = 3;
				if(skillmult+sskillmult > 2) remit = 2;
				if(skillmult+sskillmult > 4) remit = 1;
				if(skillmult+sskillmult > 6) remit = 0;
				ARSystem.spellCast(player, e.getEntity(), "c7_p");
			}
			
			skill("c"+number+"_ps");
			attack+=1;
			if(attack > 49 && !isps ) {
				spskillon();
				skill("c"+number+"_spe");
			}
			if(attack >= 100) {
				Rule.playerinfo.get(player).tropy(7,1);
			}
			
		} else {
			if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() < 1 && attack >= 49) {
				if(skillCooldown(0)) {
					((LivingEntity)e.getEntity()).setNoDamageTicks(600);
					spskillen();
					s_score+=5000;
					skill0(e);
					return false;
				}
			}
		}
		return true;
	}
}
