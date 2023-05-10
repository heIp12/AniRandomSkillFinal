package chars.c;

import java.util.List;

import org.bukkit.Location;
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
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import chars.c2.c57riri;
import types.box;

import util.AMath;
import util.MSUtil;

public class c10bell extends c00main{
	int count = 0;
	int Damagecount = 0;
	
	public c10bell(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 10;
		load();
		text();
	}
	
	public void ps(){
		Damagecount++;
		delay(()->{if(Damagecount>0)Damagecount--;},140);
	}
	
	@Override
	public boolean skill1() {
		ps();
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ps();
		skill("c"+number+"_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ps();
		if(player.isSneaking() && player.getHealth() > 2 + (Damagecount*0.3f)) {
			cooldown[3] = 0.2f*coolm;
			player.setHealth(player.getHealth()-(1 + (Damagecount*0.3f)));
		}
		if(MSUtil.isbuff(player, "c10_spd")) {
			if(skillCooldown(0)) {
				count++;
				if(count >= 2) {
					Rule.playerinfo.get(player).tropy(10,1);
				}
				spskillen();
				skill("c"+number+"_sp");
				skill("c"+number+"_spd");
			} else {
				skill("c"+number+"_s3");
			}
		} else {
			skill("c"+number+"_s3");
		}
		return true;
	}
	
	@Override
	public boolean tick() {;
		if(tk%20 ==0) {
			scoreBoardText.add("&c ["+Main.GetText("c10:ps")+ "]&f : " + Damagecount*10 + "%");
		}
		return true;
	}
	
	@Override
	public void PlayerSpCast(Player p) {
		if(Rule.c.get(p).number == 10) return;
		
		if(!MSUtil.isbuff(player, "c10_spd")) {
			spskillon();
			skill("c"+number+"_spd");
		}
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage() * 2);
			if(ARSystem.isGameMode("lobotomy")) ARSystem.heal(player, e.getDamage()*0.1);
			e.setDamage(e.getDamage() + e.getDamage()*(Damagecount*0.1f));
		} else {
			cooldown[0]-=0.5;
			cooldown[1]-=0.5;
			cooldown[2]-=0.5;
			cooldown[3]-=0.5;
		}
		return true;
	}
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c57riri) {
					is = "riri";
					break;
				}
			}
		}
		
		if(is.equals("riri")) {
			ARSystem.playSound((Entity)player, "c57c1");
		} else {
			ARSystem.playSound((Entity)player, "c10db");
		}
		
		return true;
	}
}
