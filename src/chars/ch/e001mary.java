package chars.ch;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Fascination;
import buff.Medusa;
import buff.NoCC;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c00main;
import util.GetChar;
import util.Map;

public class e001mary extends c00main{
	int count = 0;
	int size = 0;
	HashMap<Player,Integer> code;
	
	public e001mary(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 901;
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c901s1");
		skill("c901_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c901s2");
		ARSystem.giveBuff(player, new NoCC(player), 100);
		skill("c901_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c901s3");
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		ARSystem.heal(player, 10);
		return true;
	}

	@Override
	public boolean tick() {
		if(player.isSneaking() && player.getGameMode() == GameMode.ADVENTURE) skill("c901_s3");
		if(code == null) {
			code = new HashMap<>();
			size = Rule.c.size()-3;
			for(Player p : Rule.c.keySet()) {
				code.put(p, Rule.c.get(p).getCode());
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		count++;
		if(count >= size && p!=player &&skillCooldown(0)) {
			count = 0;
			spskillon();
			spskillen();
			skill("c901_sp");
			player.setGameMode(GameMode.SPECTATOR);
			ARSystem.playSoundAll("c901sp");
			skillmult += 0.2;
			delay(()->{
				player.setGameMode(GameMode.ADVENTURE);
				ARSystem.playSoundAll("c901sp2");
				for(Player play : code.keySet()) {
					if(player != play)
					Rule.c.put(play, GetChar.get(play, plugin, ""+code.get(play)));
					play.teleport(Map.randomLoc());
				}
				player.teleport(Map.randomLoc());
			},170);
		}
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.addBuff(target, new Medusa(target), 2);
		}
		if(n.equals("2")) {
			ARSystem.addBuff(target, new Silence(target), 80);
			ARSystem.addBuff(target, new Fascination(target,player), 80,-0.5);
		}
		if(n.equals("3")) {
			ARSystem.addBuff(target, new Noattack(target), 80);
			if(Rule.c.get(target) != null) {
				for(int i =0; i< 10; i++) Rule.c.get(target).cooldown[i] += 4;
			}
		}
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(0);
			e.setCancelled(true);
		} else {

		}
		return true;
	}
}
