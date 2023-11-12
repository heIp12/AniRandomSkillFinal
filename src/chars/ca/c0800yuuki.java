package chars.ca;

import java.util.List;

import org.bukkit.Bukkit;
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
import chars.c.c00main;
import chars.c.c02kirito;
import chars.c.c47ren;
import types.box;

import util.AMath;
import util.MSUtil;

public class c0800yuuki extends c00main{
	double cooldowns = skillmult;
	int sk3 = 0;
	int sk4 = 0;
	int sk3c = 0;
	
	public c0800yuuki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1008;
		load();
		text();
		ARSystem.playSound(player, "c8select");
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
		sk4 = 20;
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "0katana5");
		sk3 = 10;
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
				if(Rule.c.get(e) instanceof c47ren){
					is = "ren";
					break;
				}
			}
		}
		
		if(is.equals("kirito")) {
			ARSystem.playSound((Entity)player, "c8kirito");
		} else if(is.equals("ren")) {
			ARSystem.playSound((Entity)player, "c8ren");
		} else  {
			ARSystem.playSound((Entity)player, "c8db");
		}
		
		return true;
	}
	
	@Override
	public boolean tick() {
		if(sk4 > 0) {
			sk4--;
			player.setVelocity(player.getLocation().getDirection().multiply(0.8));
		}
		if(sk3 > 0) sk3--;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			for(int i=0; i<cooldown.length;i++) cooldown[i] -= e.getDamage()*0.3f;
		} else {
			for(int i=0; i<cooldown.length;i++) cooldown[i] -= e.getDamage()*0.3f;
			if(sk3 > 0) {
				ARSystem.playSound((Entity)player, "c8a");
				ARSystem.playSound((Entity)player, "0katana6");
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.heal(player, 11);
				LivingEntity en = (LivingEntity)e.getDamager();
				for(int i=0;i<3;i++) ARSystem.spellLocCast(player, en.getLocation().add(0,1,0), "c1008_s4");
				ARSystem.addBuff(en, new Stun(en), 20);
				ARSystem.addBuff(en, new Silence(en), 20);
				if(sk3 >= 5) for(int i=0; i<cooldown.length;i++) cooldown[i] *= 0.2f;
				sk3c++;
				if(sk3c >= 3) {
					sk3c = 0;
					if(skillCooldown(0)) {
						player.teleport(e.getDamager());
						spskillen();
						spskillon();
						ARSystem.giveBuff(player, new Silence(player), 60);
						ARSystem.giveBuff(player, new Stun(player), 60);
						ARSystem.giveBuff(player, new Nodamage(player), 100);
						skill("c1008_sp");
					}
				}
				sk3 = 0;
				ARSystem.addBuff(player, new Nodamage(player), 10);
			}
		}
		return true;
	}
}
