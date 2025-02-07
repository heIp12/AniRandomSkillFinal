package chars.ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
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

import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c4600zero extends c00main{
    boolean start = true;
	public c4600zero(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1046;
		picksound = false;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player,"c1046s1");
		skill("c1046_s1");
		
		return true;
	}
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player,"c1046s2");
		skill("removeall");
		for(c00main c : Rule.c.values()) {
			if(c != this) c.delayEvent.clear();
		}
		ARSystem.opCommand("mm m killall");
		return true;
	}
	
	@Override
	public boolean skill3() {
		List<Entity> en = ARSystem.PlayerBeamBox(player, 10, 5, box.TARGET);
		if(en.size() >= 1) {
			skill("c1046_s3");
			for(Entity e : en) {
				e.setVelocity(new Vector(0,0.4,0));
				delay(()->{
					e.setVelocity(ULocal.lookAt(player.getLocation().clone(), e.getLocation()).getDirection().multiply(2.5));
				},2);
			}
	
			ARSystem.playSound((Entity)player,"c1046s3");
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(start) {
			start = false;
			ARSystem.playSoundAll("c1046select");
			for(Player p : Rule.c.keySet()) if(p != player) ARSystem.giveBuff(p, new Silence(p), 400);
		}
		if(isBattleTime() > 1200 && !isps) {
			spskillon();
			spskillen();
			ARSystem.playSoundAll("c1046sp");
			ARSystem.giveBuff(player, new Silence(player), 660);
			ARSystem.potion(player, 24, 660, 1);
			ARSystem.potion(player, 2, 660, 1);
			player.performCommand("tm anitext all SUBTITLE true 100 c1046:t1/"+player.getName());
			delay(()->{
				player.performCommand("tm anitext all SUBTITLE true 80 c1046:t2/"+player.getName());
				delay(()->{
					player.performCommand("tm anitext all SUBTITLE true 40 c1046:t3/"+player.getName());
					delay(()->{
						player.performCommand("tm anitext all SUBTITLE true 20 c1046:t4/"+player.getName());
						delay(()->{
							player.performCommand("tm anitext all SUBTITLE true 60 c1046:t5/"+player.getName());
							delay(()->{
								player.performCommand("tm anitext all SUBTITLE true 60 c1046:t6/"+player.getName());
								delay(()->{
									player.performCommand("tm anitext all SUBTITLE true 50 c1046:t7/"+player.getName());
									for(Player p : Bukkit.getOnlinePlayers()) {
										ARSystem.potion(p, 9, 400, 10);
									}
									delay(()->{
										player.performCommand("tm anitext all SUBTITLE true 25 c1046:t8/"+player.getName());
									},70);
								},70);
							},80);
						},30);
					},60);
				},120);
			},120);
			delay(()->{
				for(Player p : Bukkit.getOnlinePlayers()) {
					ARSystem.potion(p, 15, 100, 1);
				}
				ARSystem.playSoundAll("noise", 0.2f);
			},640);
			delay(()->{
				ARSystem.GameStop();
			},660);
		}
		return true;
	}

	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setVelocity(new Vector(0,-0.2,0));
			for(int i = 0; i<15;i++) {
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(0.1,player);
				},30+i*2);
			}
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {


		} else {
			if(e.getDamager().getLocation().distance(player.getLocation()) >= 20) {
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.giveBuff(((LivingEntity)e.getDamager()), new Silence(((LivingEntity)e.getDamager())), 200);
				ARSystem.playSound(e.getDamager(), "c1046p");
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player,"c1046db"+AMath.random(2));
		return true;
	}
}
