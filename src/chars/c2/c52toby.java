package chars.c2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.InvSkill;
import util.Inventory;
import util.Map;
import util.ULocal;

public class c52toby extends c00main{
	double mana = 30;
	double mana2 = 20;
	int sk1 = 0;
	boolean sk2 = false;
	int sk3 = 0;
	int sk4 = 0;
	List<Player> sp = new ArrayList<Player>();
	

	public c52toby(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 52;
		load();
		text();
		
	}
	
	@Override
	public void setStack(float f) {
		mana = mana2 = f;
	}

	@Override
	public boolean skill1() {
		if(mana2 > 2) {
			mana2-=2;
			ARSystem.playSound((Entity)player, "c52s1");
			skill("c52s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(!sk2 && mana >= 2) {
			mana -= 2;
			ARSystem.playSound((Entity)player, "c52s2");
			sk2 = true;
			ARSystem.giveBuff(player, new Noattack(player), 30);
		} else if(sk2){
			sk2 = false;
			Rule.buffmanager.selectBuffTime(player, "noattack",0);
			ARSystem.playSound((Entity)player, "c52s52");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(sk2) {
			cooldown[3] = 0;
			return false;
		}
		if(mana2 > 5 && player.isOnGround()) {
			mana2-=5;
			ARSystem.playSound((Entity)player, "c52s4");
			ARSystem.playSound((Entity)player, "c52down");
			player.setGameMode(GameMode.SPECTATOR);
			player.teleport(player.getLocation().clone().add(0,-1,0));
			sk3 = 10000;
			return true;
		}
		cooldown[3] = 0;
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(sk3 <= 0 && sk4 <= 0 && Rule.c.size() > 1) {
			boolean bsp = true;
			for(Player p : Rule.c.keySet()) {
				if(!sp.contains(p) && sp != player && ARSystem.isTarget(p, player, box.TARGET)) {
					bsp = false;
				}
			}
			if(bsp && skillCooldown(0)) {
				spskillon();
				spskillen();
				ARSystem.giveBuff(player, new TimeStop(player), 60);
				ARSystem.playSoundAll("c52sp");
				Rule.buffmanager.selectBuffTime(player, "noattack",0);
				sk2 = false;
				
				delay(()->{
					ARSystem.playSoundAll("c52s3");
					delay(()->{
						int i = 0;
						for(Player p : sp) {
							delay(()->{
								ARSystem.giveBuff(player, new Silence(player), 10);
								ARSystem.giveBuff(player, new Nodamage(player), 20);
								Location lc = p.getLocation();
								lc.setPitch(0);
								lc.setYaw(AMath.random(360));
								player.teleport(ULocal.offset(lc, new Vector(-1,0,0)));
								ARSystem.playSound((Entity)p,"c52s11");
								ARSystem.playSound((Entity)p,"0attack");
								ARSystem.spellCast(player, p, "c52_sp");
								skillmult += 0.05f;
								p.setNoDamageTicks(0);
								p.damage(4 * (skillmult+sskillmult),player);
								p.setVelocity(lc.getDirection().multiply(3.5f));
							},i*10);
							i++;
						}
						delay(()->{
							sp.clear();
							player.teleport(Map.randomLoc());
						},sp.size()*10+10);
					},20);
				},40);
				cooldown[4] = 0;
				return false;
			}
		} 
		if(mana > 10) {
			mana-=10;
			ARSystem.giveBuff(player, new Stun(player), 20);
			ARSystem.giveBuff(player, new Silence(player), 20);
			ARSystem.playSound((Entity)player, "c52hide");
			delay(()->{
				sk4 = 1000;
				player.setGameMode(GameMode.SPECTATOR);
			},20);
		} else {
			cooldown[4] = 0;
		}
		
		return true;
	}

	@Override
	public boolean skill5() {
		invskill = new InvSkill(player) {
			@Override
			public void Start(String st) {
				player.closeInventory();
			}
		};

		Set<Player> Player = ((HashMap<Player, c00main>) Rule.c.clone()).keySet();
		Player.remove(player);
		for(Player p : sp) Player.remove(p);
		Inventory.getlist(invskill,player,Player);
		invskill.openInventory(player);
		return true;
	}
	boolean skc = true;
	@Override
	public boolean tick() {
		if(skc && sskillmult+skillmult >= 3) {
			skc = false;
			Rule.playerinfo.get(player).tropy(52,1);
		}
		if(sk3 > 0) {
			sk3--;
			mana2 -= 0.1;
			if(sk3 <= 0 || mana2 <0 || BlockUtil.isPathable(player.getLocation().getBlock())) {
				cooldown[3] = setcooldown[3];
				sk3 = 0;
				ARSystem.playSound((Entity)player, "c52down");
				ARSystem.playSound((Entity)player, "c52s32");
				player.setGameMode(GameMode.ADVENTURE);
				Location lc = player.getLocation();
				for(int i = 0; i< 60; i++) {
					if(BlockUtil.isPathable(lc.getBlock())) {
						break;
					} else {
						lc = lc.clone().add(0,0.1,0);
					}
				}
				player.teleport(lc);
				skill("c52_s3");
			}
		}
		if(sk4 > 0) {
			sk4--;
			if(sk4 <= 0 || mana <= 0 || player.isSneaking()) {
				ARSystem.playSound((Entity)player, "c52hide2");
				player.setGameMode(GameMode.ADVENTURE);
				sk4 = 0;
				ARSystem.giveBuff(player, new Stun(player), 20);
				ARSystem.giveBuff(player, new Silence(player), 20);
			} else {
				mana -= 0.15f;
			}
		}
		if(tk%20 == 0) {
			if(!sk2) {
				if(sk4 <= 0) mana+= 0.6 * (skillmult + sskillmult);
				if(sk3 <= 0) mana2+= 0.5 * (skillmult + sskillmult);
				if(!isBattle()) {
					if(sk4 <= 0) mana+= 0.6 * (skillmult + sskillmult);
					if(sk3 <= 0) mana2+= 0.5 * (skillmult + sskillmult);
				}
				if(mana > 30) mana = 30;
				if(mana2 > 20) mana2 = 20;
			} else {
				mana-=3;
				if(mana < 3) {
					sk2 = false;
					Rule.buffmanager.selectBuffTime(player, "noattack",0);
					ARSystem.playSound((Entity)player, "c52s52");
					cooldown[2] = setcooldown[2];
				} else {
					ARSystem.giveBuff(player, new Nodamage(player), 30);
					ARSystem.giveBuff(player, new Noattack(player), 30);
				}
			}
			scoreBoardText.add("&c ["+Main.GetText("c52:t1")+ "] : "+ AMath.round(mana,1));
			scoreBoardText.add("&c ["+Main.GetText("c52:t2")+ "] : "+ AMath.round(mana2,1));
		}

		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(sp != null && sp.contains(p)) {
			sp.remove(p);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(sk2) {
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.playSound((Entity)player, "c52miss");
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		for(Entity e : ARSystem.box(player, new Vector(8,8,8), box.TARGET)) {
			if(Rule.c.get(e) != null && !sp.contains(e)) {
				sp.add((Player)e);
			}
		}
		
		if(AMath.random(2) == 3) {
			ARSystem.playSound((Entity)player,"c52db");
		} else {
			ARSystem.playSound((Entity)player,"c52db"+AMath.random(2));
		}
		return true;
	}
}
