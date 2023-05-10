package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c28enma extends c00main{
	int ticks = 0;
	int delay = 0;
	boolean passive = false;
	Player deathPlayer;
	List<Player> playlist = new ArrayList<Player>();
	Player istarget;
	
	public c28enma(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 28;
		load();
		text();
		p.setGameMode(GameMode.SPECTATOR);
	}
	
	@Override
	public void info() {
		super.info();
		if(s_kill >= 3) Rule.playerinfo.get(player).tropy(28,1);
	}
	
	public void death() {
		istarget = null;
		invskill = new InvSkill(player) {
			
			@Override
			public void Start(String st) {
				deathPlayer.closeInventory();
				if(Rule.c.get(Bukkit.getPlayer(st)) != null) {
					istarget = Bukkit.getPlayer(st);
					target(Bukkit.getPlayer(st));
					deathPlayer.sendTitle(Main.GetText("c28:t3"), Main.GetText("c28:t2"),5,20,15);
					ARSystem.playSoundAll("c28s1");
				} else {
					istarget = ARSystem.RandomPlayer(player);
					target(istarget);
					ARSystem.playSoundAll("c28s1");
				}
			}
		};
		Set<Player> ps = ((HashMap<Player, c00main>) Rule.c.clone()).keySet();
		ps.remove(player);
		for(Player p : playlist) {
			ps.remove(p);
		}
		
		Inventory.getlist(invskill,player,ps);
		
		deathPlayer.sendTitle(Main.GetText("c28:ps"), Main.GetText("c28:t1"),5,15,20);
		ARSystem.playSound(deathPlayer, "c28s0");
		delay(new Runnable() {
			@Override
			public void run() {
				invskill.openInventory(deathPlayer);
			}
		},40);
		
		delay(new Runnable() {
			@Override
			public void run() {
				if(deathPlayer.getOpenInventory().getTitle().equals(invskill.inventory.getTitle())) {
					deathPlayer.closeInventory();
					ARSystem.playSoundAll("c28s1");
					target(ARSystem.RandomPlayer(player));
				} else if(istarget == null) {
					istarget = ARSystem.RandomPlayer(player);
					ARSystem.playSoundAll("c28s1");
					target(istarget);
				}
			}
		},240);
	}
	public void target(Player p) {
		player.sendTitle(Main.GetText("c28:t4"), p.getName(),5,15,0);
		playlist.add(p);
	}
	@Override
	public boolean skill1() {
		if(deathPlayer != null) {
			death();
		} else {
			cooldown[1] = 0;
		}

		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.potion(player, 14, 60, 0);
		ARSystem.potion(player, 1, 60, 1);
		Map.playeTp(player);
		return true;
	}
	
	
	@Override
	public void PlayerDeath(Player p,Entity e) {
		deathPlayer = p;
		cooldown[1] -= 10;
		if(p instanceof Player && playlist.contains(p)) {
			playlist.remove(e);
		}
		if(!passive) {
			passive = true;
			player.setGameMode(GameMode.ADVENTURE);
			Map.playeTp(player);
			ARSystem.giveBuff(player, new Nodamage(player), 60);
		}
	}

	@Override
	public boolean tick() {
		if(delay > 0) delay--;
		
		if(tk%20==0) {
			for(Player p : playlist) {
				scoreBoardText.add("&c ["+Main.GetText("c28:sk1")+ "]&f : "+ p.getName());
			}	
		}
		if(delay <= 0 && ticks%10==0 && player.getGameMode() != GameMode.SPECTATOR) {
			for(Entity e : player.getNearbyEntities(5, 5, 5)) {
				if(e instanceof Player && ((Player) e).getGameMode() != GameMode.SPECTATOR && playlist.contains(e)) {
					playlist.remove(e);
					ARSystem.spellCast(player, e, "look1");
					ARSystem.spellCast(player, e, "look2");
					Location loc = e.getLocation();
					loc.setPitch(0);
					e.teleport(loc);
					ARSystem.giveBuff(player, new Nodamage(player), 20);
					ARSystem.giveBuff(player, new Silence(player), 20);
					ARSystem.spellCast(player, e, "c28_s1");
					
					ARSystem.giveBuff((LivingEntity) e, new TimeStop((LivingEntity) e), 300);
					player.setGameMode(GameMode.SPECTATOR);
					player.setSpectatorTarget(e);
					ARSystem.playSound((Entity)player,"c28s2");
					delay(new Runnable() {
						@Override
						public void run() {
							ARSystem.playSoundAll("c28s3");
							delay = 100;
						}
					},260);
					
					delay(new Runnable() {
						@Override
						public void run() {
							ARSystem.playSoundAll("c28s4");
							player.setGameMode(GameMode.ADVENTURE);
							player.teleport(e);
							Skill.remove(e,player);
							delay = 100;
						}
					},300);
				}
			}
		}
		ticks++;
		
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() > 1) {
				e.setDamage(0);
				return false;
			}
		} else {

		}
		return true;
	}
}
