package chars.c;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import types.box;
import util.Holo;
import util.MSUtil;
import util.ULocal;

public class c15tina extends c00main{
	int ticks = 0;
	int count = 0;
	
	int stack = 0;
	
	
	@Override
	public void setStack(float f) {
		stack = (int)f;
	}
	
	public c15tina(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 15;
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		if(stack == 52) {
			List<Player> tg = ARSystem.PlayerOnlyBeamBox(player, 150, 10, box.TARGET);
			Location lc = player.getLocation();
			if(tg.size() > 0) {
				lc = ULocal.lookAt(lc.clone(), tg.get(0).getLocation());
			}
			player.teleport(lc);
			ARSystem.playSound((Entity)player, "c15a",1, 2);
			delay(()->{skill("c15_s1_2");},1);
			Holo.create(player.getLocation(), "§a매크로 사용중", 80,new Vector(0,0.1,0));
			for(int i =0; i< 5; i++) {
				delay(()->{
					skill("c15_s1_3");
				},2+i*1);
			}
		} else {
			skill("c"+number+"_s1");
		}
		if(night()) {
			cooldown[1]*=0.7;
		}
		return true;
	}
	public boolean night() {
		if(player.getWorld().getTime()%36000 > 12500 && player.getWorld().getTime()%36000 < 22500) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean tick() {
		if(ticks%19==0) {
			if(night()) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2000, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1));
			}
		}
		if(tk%20==0&&psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c15:sk0")+ "]&f : " +count +" / 4");
		}
		ticks%=20;
		return false;
	}
		
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
		if(night()) {
			cooldown[2]*=0.7;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		count++;

		if(count > 3&& skillCooldown(0)) {
			count = 0;
			spskillen();
			spskillon();
			skill("c"+number+"_sp");
		}
		
		
		if(night()) {
			cooldown[3]*=0.7;
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player && cooldown[1] >= setcooldown[1]-0.5 && Rule.c.size() <= 2) {
			Rule.playerinfo.get(player).tropy(15,1);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(night()) {
				e.setDamage(e.getDamage()*1.5f);
			}
		} else {

		}
		return true;
	}
}
