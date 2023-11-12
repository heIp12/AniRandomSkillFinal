package chars.c;

import java.util.List;

import org.bukkit.entity.Entity;
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
import chars.c2.c62shinon;
import chars.c3.c133yukina;
import types.box;
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
		if(remit < 5 && tk%3 == 0) remit++;
		
		if(tk%20==0&&psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c7:ps")+ "]&f : " + remit +" / 5");
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
			if(remit > 0) {
				delay(()->{((LivingEntity)e.getEntity()).setNoDamageTicks(0);},0);
				remit--;
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
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c02kirito) {
					is = "kirito";
					break;
				}
			}
		}
		
		if(is.equals("kirito")) {
			ARSystem.playSound((Entity)player, "c7kirito");
		} else {
			ARSystem.playSound((Entity)player, "c7db");
		}
		
		return true;
	}
}
