package chars.c3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Saito;
import buff.ArmorUp;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c.c45momo;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c131saito extends c00main{
	boolean p = true;
	int type = 0;
	int t2s2= 0;
	int t2s2t = 0;
	int t3s2 = 0;
	Location t3s2l = null;
	
	int t2s4 = 0;
	int t2s42 = 0;
	int t3s4 = 0;
	int t3s1 = 0;
	Location t3s4loc;
	
	@Override
	public void setStack(float f) {
		s_score = (int)f;
	}
	

	public void setChar(int i) {
		type = i;
	}
	public c131saito(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 131;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		if(t2s4 > 0) {
			cooldown[1] = 0;
			return true;
		}
		if(type == 1) {
			ARSystem.playSound((Entity)player, "0katana3",1.4f);
			skill("c131_1s1");
			return true;
		}
		if(type == 2) {
			ARSystem.potion(player, 1, 40, 4);
			for(int i=0;i<8;i++) {
				delay(()->{
					ARSystem.playSound((Entity)player, "0slash4",2f);
					skill("c131_2s1");
				},3*i);
			}
			return true;
		}
		if(type == 3) {
			ARSystem.potion(player, 2, 40, 1);
			t3s1 = 40;
			for(int i=0;i<40;i++) {
				int j = i;
				delay(()->{
					Location loc = player.getLocation().clone();
					ARSystem.playerRotate(player, loc.getYaw(), loc.getPitch());
					if(j%2 == 0) {
						skill("c131_3s1");
						ARSystem.playSound((Entity)player, "0sword",1.2f);
					}
				},i);
			}
			return true;
		}
		skill("c131_s1");
		return true;
	}

	@Override
	public boolean skill2() {
		if(t2s4 > 0) {
			cooldown[2] = 0;
			return true;
		}
		if(type == 1) {
			if(t2s2 <= 0) t2s2 = 3;
			player.setVelocity(player.getLocation().getDirection().multiply(0.85));
			delay(()->{
				LivingEntity en = (LivingEntity)ARSystem.boxSOne(player, new Vector(9,3,9), box.TARGET);
				if(en != null) {
					player.teleport(ULocal.lookAt(player.getLocation(), en.getLocation()));
					player.setVelocity(new Vector(0,0,0));
					delay(()->{
						skill("c131_s2");
						ARSystem.playSound((Entity)player, "0katana",1.4f);
					},1);
				}
			},6);
			return true;
		}
		if(type == 2) {
			ARSystem.giveBuff(player, new Stun(player), 10);
			ARSystem.giveBuff(player, new Silence(player), 10);
			skill("c131_2s2");
			delay(()->{
				ARSystem.playSound((Entity)player, "0slash4",1.8f);
			},10);
			return true;
		}
		if(type == 3) {
			t3s2 = 16;
			t3s2l = player.getLocation();
			return true;
		}
		skill("c131_s2");
		return true;
	}
	@Override
	public boolean skill3() {
		if(t2s4 > 0) {
			cooldown[3] = 0;
			return true;
		}
		if(type == 1) ARSystem.giveBuff(player, new ArmorUp(player), 20, 0.85);
		player.setVelocity(player.getLocation().getDirection().multiply(1.5f).setY(0.2));
		if(type == 2) delay(()->{
			ARSystem.playSound((Entity)player, "0slash5",1.2f);
			skill("c131_2s1");
		},5);
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.giveBuff(player, new Stun(player), 40);
		ARSystem.giveBuff(player, new Silence(player), 40);
		if(type == 0) {
			ARSystem.playSound((Entity)player, "c131s4");
			skill("c131_s4c");
			delay(()->{
				skill("c131_s4");
			},40);
		}
		if(type == 1) {
			List<Entity> ens = ARSystem.box(player, new Vector(20,20,20), box.TARGET);
			Location lc = player.getLocation();
			if(ens.size() <= 0) {
				cooldown[4] = 0;
			} else {
				ARSystem.giveBuff(player, new Stun(player), 20);
				ARSystem.giveBuff(player, new Silence(player), 20);
				ARSystem.playSound((Entity)player, "c131s1");
				delay(()->{
					int i = 0;
					for(Entity e : ens) {
						i++;
						delay(()->{
							ARSystem.giveBuff(player, new Silence(player), 20);
							ARSystem.giveBuff(player, new Stun(player), 20);
							ARSystem.giveBuff(player, new Nodamage(player), 20);
							player.teleport(ULocal.lookAt(ULocal.offset(e.getLocation().clone(), new Vector(2,0,0)), e.getLocation()));
							ARSystem.playSound((Entity)player, "0katana4",2f);
							skill("c131_1s4");
						},i*10);
					}
					delay(()->{
						player.teleport(lc);
					},i*10+10);
				},20);
			}
		}
		if(type == 2) {
			ARSystem.giveBuff(player, new Silence(player), 0);
			if(t2s4 == 0) {
				t2s4 = 1;
				cooldown[4] = 0;
				skill("c131_2s4a");
			} else {
				t2s42 = t2s4;
				t2s4 = 0;
				skill("removemyall");
				ARSystem.giveBuff(player, new Stun(player), 20);
				ARSystem.giveBuff(player, new Silence(player), 20);
				ARSystem.playSound((Entity)player, "0slash",0.5f);
				skill("c131_2s4");
				ARSystem.playSound((Entity)player, "c131s1");
			}
		}
		if(type == 3) {
			t3s4loc = player.getLocation();
			skill("c131_s4c");
			ARSystem.giveBuff(player, new Stun(player), 20);
			ARSystem.giveBuff(player, new Silence(player), 20);
			t3s1 = 20;
			delay(()->{
				t3s4 = 30;
				ARSystem.playSound((Entity)player, "c131s1");
			},20);
		}
		return true;
	}
	
	public boolean tick() {
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c131:ps")+ "] : "+ p);
		}
		if(!isps && (score-200) >= 500 && skillCooldown(0)) {
			if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time <= 30) Rule.playerinfo.get(player).tropy(131, 1);
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c131sp");
			ARSystem.giveBuff(player, new TimeStop(player), 200);
			new G_Saito(player);
		}
		if(isps && type == 0 && tk%20 == 0) new G_Saito(player);
		if(t3s1 > 0) t3s1--;
		if(t2s2t > 0) {
			t2s2t--;
			if(t2s2t <= 0) {
				t2s2 = 0;
				cooldown[2] = setcooldown[2];
			}
		}
		if(type == 2) {
			if(t2s4 > 0) {
				t2s4++;
				ARSystem.giveBuff(player, new Stun(player), 2);
				if(t2s4 > 200) t2s4 = 200;
				player.sendTitle("Damage : "+AMath.round(50*(t2s4*0.01),1), t2s4 +" / 200",0,20,0);
			}
		}
		if(type == 3) {
			if(t3s2> 0) {
				player.setVelocity(t3s2l.getDirection().multiply(1));
				if(t3s2%2 == 0) {
					skill("c131_3s2");
					ARSystem.playSound((Entity)player, "0slash2",1.5f);
				}
				t3s2--;
			}
			if(t3s4 > 0) {
				t3s4--;
				player.setVelocity(t3s4loc.getDirection().multiply(2.2f));
				LivingEntity en = (LivingEntity)ARSystem.boxSOne(player, new Vector(3,3,3), box.TARGET);
				if(en != null) {
					t3s4 = 0;
					player.setVelocity(new Vector(0,0.2,0));
					player.setGameMode(GameMode.SPECTATOR);
					for(int i=0;i<60;i++) {
						if(i%2==0) {
							delay(()->{
								ARSystem.playSound(en, "0slash",2f);
								ARSystem.spellCast(player, en, "c131_3s4");
							},i);
						}
						delay(()->{
							en.setVelocity(t3s4loc.getDirection().multiply(0.5));
							ARSystem.spellCast(player, en, "c131_3s42");
						},i);
					}
					delay(()->{
						player.teleport(en);
						player.setGameMode(GameMode.ADVENTURE);
					},70);
				}
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(type == 0) {
			if(n.equals("1")) {
				target.setNoDamageTicks(0);
				target.damage(4,player);
			}
	
			if(n.equals("2")) {
				target.setNoDamageTicks(0);
				target.damage(2,player);
			}
		}
		if(type == 1) {
			List<Buff> b = Rule.buffmanager.selectBuffType(target, BuffType.BUFF);
			if(b.size() > 0) {
				b.get(AMath.random(b.size())-1).setTime(0);
			}
			if(n.equals("1")) {
				target.setNoDamageTicks(0);
				target.damage(3,player);
				if(Rule.c.get(target) != null) {
					int i = AMath.random(6)-1;
					for(int j = 0; j<100;j++) {
						if(Rule.c.get(target).setcooldown[j] <= 0) {
							i = AMath.random(10)-1;
						} else {
							break;
						}
					}
					Rule.c.get(target).cooldown[i] = 0;
				}
				return;
			}
			if(n.equals("2")) {
				target.setNoDamageTicks(0);
				target.damage(3,player);
				cooldown[2] *= 0.2;
				return;
			}
			if(n.equals("3")) {
				target.setNoDamageTicks(0);
				target.damage(4,player);
				return;
			}
		}
		if(type == 2) {
			if(n.equals("1")) {
				target.setNoDamageTicks(0);
				target.damage(1,player);
				return;
			}
			if(n.equals("2")) {
				target.setNoDamageTicks(0);
				target.damage(8,player);
				target.setVelocity(new Vector(0,2.5,0));
				return;
			}
			if(n.equals("3")) {
				target.setNoDamageTicks(0);
				target.damage(50 * (t2s42*0.01),player);
				return;
			}
		}
		if(type == 3) {
			if(n.equals("1")) {
				target.setNoDamageTicks(0);
				target.damage(0.5,player);
				return;
			}
			if(n.equals("2")) {
				target.setNoDamageTicks(0);
				target.damage(1,player);
				return;
			}
			if(n.equals("3")) {
				target.setNoDamageTicks(0);
				target.damage(0.5,player);
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(0.5,player);
				},1);
				return;
			}
		}
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(type == 2) {
				Rule.buffmanager.selectBuffAddValue(player, "barrier", (float) (e.getDamage()*0.15));
				ARSystem.heal(player, e.getDamage()*0.1);
			}
			if(p) e.setDamage(e.getDamage()*1.25f);
			
		} else {
			if(p && e.getDamage() >= 20) {
				p = false;
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.playSound((Entity)player, "c131p4");
				ARSystem.giveBuff(player, new Nodamage(player), 60);
				return false;
			}
			if(p && e.getDamage() > 10) {
				ARSystem.playSound((Entity)player, "c131p2");
			}
			if(type == 3) {
				if(t3s2 > 0 || t3s1 > 0) {
					e.setDamage(e.getDamage()* 0.7);
					((LivingEntity)e.getDamager()).damage(1,player);
					ARSystem.playSound((Entity)player, "c2g", 1.4f);
				}
			}
		}
		return true;
	}
	@Override
	public boolean skill9(){
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c128roise) {
					is = "r";
					break;
				}

			}
		}

		if(is.equals("r")) {
			ARSystem.playSound((Entity)player, "c131roisu");
		} else {
			ARSystem.playSound((Entity)player, "c131db");
		}
		return true;
	}
}
