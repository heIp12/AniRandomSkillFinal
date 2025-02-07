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
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
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
import ars.gui.G_Lvup;
import ars.gui.G_Saito;
import buff.Airborne;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Drop;
import buff.Exposure;
import buff.Fascination;
import buff.Ice;
import buff.NoCC;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
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
import chars.c.c38hajime;
import chars.c.c38hajime.gun;
import chars.c.c39sakuya;
import chars.c.c45momo;
import chars.c2.c57riri;
import chars.c2.c60gil;
import chars.c2.c69himi;
import chars.ca.c1394matan;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ItemCreate;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class c147bellp extends c00main{
	float damage = 2;
	float range = 1;
	float crt = 0.08f;
	int dg = 0;
	int stack = 0;
	HashMap<String,Integer> ore = new HashMap<>();
	List<Integer> mg = new ArrayList<Integer>();
	List<Player> teams = new ArrayList<>();
	
	public c147bellp(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 147;
		load();
		text();
		if(ARSystem.AniRandomSkill != null) {
			delay(()->{
				int power = 0;
				if(Rule.c.size() <= 2) power = 1;
				if(Rule.c.size() > 5 && Map.mapid < 100) power = 1;
				if(Rule.c.size() >= 15) power = 1;
				if(Rule.c.size() >= 20) {
					power = 2;
					ore.put("t5",1);
				}
				if(Rule.c.size() >= 25) {
					power = 2;
					ore.put("t5",2);
				}
				ore.put("t1", power);
				ore.put("t2", power);
				ore.put("t3", power);
				ore.put("t4", power);
			},Math.max(0,ARSystem.AniRandomSkill.time*-20)+20);
		}
	}


	@Override
	public boolean skill1() {
		if(player.isSneaking() && mg.size() > 0) {
			ARSystem.playSound((Entity)player, "c147s11");
			stack++;
			skill("c147_m"+mg.get(0));
			mg.remove(0);
			delay(()->{
				ARSystem.playSound((Entity)player, "minecraft:block.glass.break", 0.2f);
				if(stack > 7) {
					ARSystem.giveBuff(player, new Panic(player), 10 + (stack-8)*5);
				}
			},10);
			
		} else {
			if(isps && skillCooldown(0)) {
				ARSystem.playSound(player, "c147sp4");
				skill("c147_spa");
			} else {
				ARSystem.playSound((Entity)player, "0slash5");
				skill("c147_s1");
				for(int i = 0; i < 1+ (2*range); i++) {
					ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(), new Vector(i,0,0)), "c147_s1e");
				}
				for(Entity e : ARSystem.PlayerBeamBox(player, 1 + (2*range), 2, box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					delay(()->{
						float cr = this.crt;
						if(AMath.random(100)-(int)(cr*100) <= 1) {
							cr -= 1;
							int b = 2;
							while(cr > 0 && AMath.random(100)-(int)(cr*100) <= 1) {
								cr -= 1;
								b*=2;
							}
							Holo.create(en.getLocation(),"§cCritical! §4§lx"+b+"",40,new Vector(0,0.01,0));
							en.setNoDamageTicks(0);
							en.damage(damage*b,player);
						} else {
							en.setNoDamageTicks(0);
							en.damage(damage,player);
						}
					},5);
				}
			}
		}
		return true;
	}

	@Override
	public boolean skill2() {
		List<Player> pl = ARSystem.PlayerOnlyBeamBox(player, 80, 5, box.TARGET);
		boolean isskill = false;
		if(pl.size() > 0) { 
			for(Player p : pl) {
				if(Rule.c.get(p) != null) {
					if(Rule.c.get(p).delayEvent.size() > 0 || player.isSneaking()) {
						ARSystem.playSound((Entity)player, "c147s2"+AMath.random(2));
						ARSystem.playSound(p, "c147s2"+AMath.random(2));
						delay(()->{
							if(Rule.c.get(p).delayEvent.size() > 0) {
								ARSystem.playSound(p, "c147s23");
								ARSystem.playSound(player, "c147s23");
								float damage = 0;
								for(double i : Rule.c.get(p).delayEvent.values()) {
									damage += i*0.05;
								}
								for(Buff buff : Rule.buffmanager.getBuffs(p).getBuff()) {
									buff.stop();
								}
								float d = damage;
								delay(()->{
									p.setNoDamageTicks(0);
									p.damage(d,player);
								},0);
								Rule.c.get(p).delayEvent.clear();
							}
						},30);
						isskill = true;
						break;
					}
				}
			}
		}
		if(!isskill) {
			cooldown[2] = 0;
		}
		return true;
	}
	
	int sp = 0;
	
	@Override
	public boolean skill3() {
		if(o(5) > 2 && player.isSneaking() && !isps && skillCooldown(0)) {
			ore.clear();
			spskillon();
			ARSystem.playSound((Entity)player, "c147sp");
			ARSystem.potion(player, 9, 400, 10);
			sp = 700;
		}
		else if(o(1)+o(2)+o(3)+o(4)+o(5) > 0) {
			ARSystem.playSound((Entity)player, "c147s3");
			new G_Lvup(this, new ItemStack[] {ItemCreate.Name(ItemCreate.Item(272, 0), "c147:a1"),ItemCreate.Name(ItemCreate.Item(283, 0), "c147:a2")});
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	int type = 0;
	int count = 0;
	@Override
	public void select(String i) {
		dg = 10;
		if(i.equals("c147:a1")) {
			skill("c147_p");
			new G_Lvup(this, new ItemStack[] {it(377, 0,"c147:u1", true),it(326, 0,"c147:u2", true),it(288, 0,"c147:u3", true),it(3, 0,"c147:u4", true)});
		}
		if(i.equals("c147:a2")) {
			if(o(1)+o(2)+o(3)+o(4)+o(5) > 1) {
				skill("c147_p");
				new G_Lvup(this, new ItemStack[] {it(377, 0,"c147:m1", true),it(326, 0,"c147:m2", true),it(288, 0,"c147:m3", true),it(3, 0,"c147:m4", true)});
				type = 0;
			} else {
				player.sendTitle("", "광석이 부족해!", 0, 20, 0);
			}
		}
		
		if(i.contains("c147:m")) {
			int n = Integer.parseInt(i.replace("c147:m", ""));
			if(o(n) > 0 || o(5) > 0) {
				if(o(n) <= 0) {
					ore.put("t5", o(5)-1);
				} else {
					ore.put("t"+n, o(n)-1);
				}
				if(type == 0) {
					type = n;
				} else {
					if(n > type) {
						type = type * 10+n;
					} else {
						type = type + 10*n;
					}
				}
				skill("c147_p");
				if(type > 10) {
					mg.add(type);
					player.sendTitle(Main.GetText("c147:m"+type), Main.GetText("c147:m"+type+"_lore"), 0, 40, 20);
					ARSystem.playSound((Entity)player, "c147s3"+AMath.random(2));
					ARSystem.heal(player, 2);
				} else {
					new G_Lvup(this, new ItemStack[] {it(377, 0,"c147:m1", true),it(326, 0,"c147:m2", true),it(288, 0,"c147:m3", true),it(3, 0,"c147:m4", true)});
				}
			} else {
				ARSystem.playSound((Entity)player, "c147kang",2,1);
				player.sendTitle("", "광석이 부족해!", 0, 20, 0);
			}
		}
		if(i.contains("c147:u")) {
			type = Integer.parseInt(i.replace("c147:u", ""));
			if(o(type) > 0 || o(5) > 0) {
				if(o(type) <= 0) {
					ore.put("t5", o(5)-1);
				} else {
					ore.put("t"+type, o(type)-1);
				}
				skill("c147_p");
				count = 2;

				if(ARSystem.isGameMode("lobotomy")){
					new G_Lvup(this, new ItemStack[] {
						ItemCreate.Name(ItemCreate.Item(259, 0), (2+AMath.random(5))+"%"),
						ItemCreate.Name(ItemCreate.Item(318, 0), AMath.random(10)+"%"),
						ItemCreate.Name(ItemCreate.Item(275, 0), (AMath.random(50)-AMath.random(100))+"%"),
						ItemCreate.Name(ItemCreate.Item(377, 0), (AMath.random(30)-AMath.random(20))+"%"),
						ItemCreate.Name(ItemCreate.Item(326, 0), (AMath.random(30)-AMath.random(10))+"%")
				});
				} else {
					new G_Lvup(this, new ItemStack[] {
							ItemCreate.Name(ItemCreate.Item(259, 0), (2+AMath.random(8))+"%"),
							ItemCreate.Name(ItemCreate.Item(318, 0), AMath.random(16)+"%"),
							ItemCreate.Name(ItemCreate.Item(275, 0), (AMath.random(70)-AMath.random(200))+"%"),
							ItemCreate.Name(ItemCreate.Item(377, 0), (AMath.random(40)-AMath.random(25))+"%"),
							ItemCreate.Name(ItemCreate.Item(326, 0), (AMath.random(40)-AMath.random(15))+"%")
					});
				}
			} else {
				ARSystem.playSound((Entity)player, "c147kang",2,1);
				player.sendTitle("", "광석이 부족해!", 0, 20, 0);
			}
		}
		if(i.contains("%")) {
			int n = Integer.parseInt(i.replace("%", ""));
			skill("c147_p");
			if(type == 1) damage *= (1+(0.01*n));
			else if(type == 2) setcooldown[1] *= (1-(0.01*n));
			else if(type == 3) range *= (1+(0.01*n));
			else if(type == 4) crt += 0.01*n;
			if(range < 0.5) range = 0.5f;
			if(damage < 1) damage = 1;
			
			if(damage >= 3) Rule.playerinfo.get(player).tropy(147, 1);
			count--;
			if(count > 0) {
				new G_Lvup(this, new ItemStack[] {
						ItemCreate.Name(ItemCreate.Item(259, 0), (2+AMath.random(5))+"%"),
						ItemCreate.Name(ItemCreate.Item(318, 0), AMath.random(12)+"%"),
						ItemCreate.Name(ItemCreate.Item(275, 0), (AMath.random(60)-AMath.random(200))+"%"),
						ItemCreate.Name(ItemCreate.Item(377, 0), (AMath.random(30)-AMath.random(25))+"%"),
						ItemCreate.Name(ItemCreate.Item(326, 0), (AMath.random(30)-AMath.random(15))+"%")
				});
			} else {
				ARSystem.heal(player, 2);
				ARSystem.playSound((Entity)player, "c147s3"+AMath.random(2));
			}
		}
	}
	
	
	ItemStack it(int id,int data,String name,boolean lore) {
		if(lore) return ItemCreate.Lore(ItemCreate.Item(id,data),name,Text.getLine(name+"_lore", 1));
		return ItemCreate.Name(ItemCreate.Item(id,data),name);
	}
	int c = 0;
	int pdelay = 20;
	@Override
	public boolean tick() {
		Entity p2 = ARSystem.boxSPlayerOne(player, new Vector(6,6,6), box.ALL);
		if(p2 != null && Rule.c.get(p2) != null && !teams.contains(p2) && c <= 0 && ARSystem.E_sterEgg) {
			int n = Rule.c.get(p2).number;
			if(n == 10 || n == 57) {
				c = 40;
				player.teleport(ULocal.lookAt(player.getLocation().clone(), p2.getLocation()));
				ARSystem.giveBuff(player, new TimeStop(player), 20);
				ARSystem.giveBuff((Player)p2, new TimeStop((Player)p2), 20);
				skill("c147_p");
				ARSystem.playSound((Entity)player, "c147kang",2,1);
				ARSystem.playSound((Entity)player, "c147s3"+AMath.random(2));
				teams.add((Player)p2);
				if(n == 10) {
					Rule.c.get(p2).setcooldown[1] *=0.75;
					Rule.c.get(p2).setcooldown[2] *=0.5;
					Rule.c.get(p2).setcooldown[3] *=2;
					Rule.c.get(p2).setcooldown[0] *=5;
					Rule.c.get(p2).hp = 8;
					Rule.c.get(p2).frist_damage = 2.5f;
					Rule.c.get(p2).frist_defence = 0.25f;
					((LivingEntity)p2).setHealth(5);
					((LivingEntity)p2).setMaxHealth(5);
				}
				if(n == 57) {
					Rule.c.get(p2).s_damage += 20;
					Rule.c.get(p2).hp += 8;
					((LivingEntity)p2).setMaxHealth(((LivingEntity)p2).getMaxHealth()+8);
					ARSystem.heal((LivingEntity)p2, 8);
				}
			}
		}
		if(Rule.team.isTeam(player) && ARSystem.E_sterEgg) {
			Entity p = ARSystem.boxSPlayerOne(player, new Vector(6,6,6), box.TEAM);
			if(p != null && Rule.c.get(p) != null && !teams.contains(p) && c <= 0) {
				c = 40;
				player.teleport(ULocal.lookAt(player.getLocation().clone(), p.getLocation()));
				ARSystem.giveBuff(player, new TimeStop(player), 20);
				ARSystem.giveBuff((Player)p, new TimeStop((Player)p), 20);
				skill("c147_p");
				ARSystem.playSound((Entity)player, "c147kang",2,1);
				ARSystem.playSound((Entity)player, "c147s3"+AMath.random(2));
				teams.add((Player)p);
				if(Rule.c.get(p).number == 38 && ((Player)p).getMaxHealth() >= 20) {
					((c38hajime)Rule.c.get(p)).en +=1;
					((c38hajime)Rule.c.get(p)).enchent();
				} else {
					for(int i =0; i<10; i++) Rule.c.get(p).setcooldown[i] *= 0.8;
					Rule.c.get(p).hp += 2;
					((LivingEntity)p).setMaxHealth(Rule.c.get(p).hp);
				}
			}
		}
		if(c > 0) c--;
		if(dg > 0) dg--;
		if(sp > 0) {
			sp--;
			if(sp%20 == 0 && sp <= 640 && sp >= 100) {
				skill("c147_p");
			}
			if(tk%3 == 0) {
				ARSystem.giveBuff(player, new Stun(player), 10);
				ARSystem.giveBuff(player, new Silence(player), 10);
			}
			if(sp == 640) {
				ARSystem.playSound((Entity)player, "c147sp1");
			}
			if(sp == 270) {
				ARSystem.playSound((Entity)player, "c147sp2");
			}
			if(sp == 100) {
				skill("c147_sp");
				ARSystem.playSound((Entity)player, "c147sp3");
			} else if(sp < 640){
				ARSystem.potion(player, 15, 40, 1);
			}
			if(sp <= 0) {
				setcooldown[1] *= 15f;
				if(setcooldown[1] > 5) setcooldown[1] = 5;
				spskillen();
			}
		}
		if(pdelay > 0) pdelay--;
		if(pdelay <= 0 && player.isSneaking() && Rule.buffmanager.selectBuffType(player, BuffType.SILENCE).size() <= 0) {
			Block b = player.getTargetBlock(null, 3);
			if(BlockUtil.isPathable(b)) {
				b = player.getTargetBlock(null, 2);
				if(BlockUtil.isPathable(b)) b = player.getTargetBlock(null, 1);				
			}
			String s = b.getType().toString().toLowerCase();
			int i = 0;
			if(s.contains("magma") || s.contains("coal_block")) {
				i = 5;
			} else if(s.contains("lapis_block") || s.contains("diamond_block") || s.contains("gold_block") || s.contains("emerald_block") || s.contains("iron_block")) {
				i = 7;
			}
			else if(s.contains("ore")) {
				i = 6;
			}
			else if(s.contains("stone") || s.contains("iron") || s.contains("cobble")) {
				i = 3;
			}
			else if(s.contains("end")) {
				i = 3;
			} else if(s.contains("gravel")) {
				i = 2;
			}
			if(i > 0) {
				player.setSneaking(false);
				pdelay = 15;
				ARSystem.giveBuff(player, new Stun(player), 10);
				ARSystem.giveBuff(player, new Silence(player), 10);
				Location lc = b.getLocation().clone().add(0.5,-0.5,0.5);
				
				lc = ULocal.offset(ULocal.lookAt(lc.clone(), player.getLocation()), new Vector(1.5f,0,0));
				lc.setYaw(player.getLocation().getYaw());
				lc.setPitch(player.getLocation().getPitch());
				ARSystem.spellLocCast(player,lc, "c147_gan");
				if(AMath.random(10) <= i) {
					int o = AMath.random(4);
					if(i == 4 && AMath.random(10) <= 7) o = 1;
					if(o == 2 && i == 4 && AMath.random(10) <= 9) while(o == 2) o = AMath.random(4);
					if(AMath.random(100) <= 1+(i*2)) o = 5;
					if(ore.containsKey("t"+o)) {
						ore.put("t"+o,ore.get("t"+o)+(int)(skillmult+sskillmult));
					} else {
						ore.put("t"+o, (int)(skillmult+sskillmult));
					}
					player.sendTitle("", Text.get("c147:t"+o) +"속성 광석(을)를 얻었다.", 20, 10, 40);
				}
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&b[&e&l"+Main.GetText("c147:t5")+"&f("+o(5)+")&b]");
			scoreBoardText.add("&c["+Main.GetText("c147:t1")+"&f("+o(1)+")&c] &9["+Main.GetText("c147:t2")+"&f("+o(2)+")&9]");
			scoreBoardText.add("&a["+Main.GetText("c147:t3")+"&f("+o(3)+")&a] &6["+Main.GetText("c147:t4")+"&f("+o(4)+")&6]");
			scoreBoardText.add("&e[무기 데미지] &f" + AMath.round(damage,2));
			scoreBoardText.add("&e[무기 사거리] &f" + AMath.round(1 + (2*range),2));
			scoreBoardText.add("&c[크리티컬 확률] &f" + AMath.round(crt*100.0,2));
		}
		return true;
	}
	int o(int i) {
		if(ore.get("t"+i) == null) return 0;
		return ore.get("t"+i);
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			Wound w = new Wound(target);
			w.setValue(1);
			w.setEffect("c147_fire");
			w.setDelay(player,60,0);
			ARSystem.giveBuff(target, w, 900);
		}
		if(n.equals("2")) {
			ARSystem.spellCast(player, target, "c147_water");
			target.teleport(target.getLocation().getBlock().getLocation().add(new Vector(0.5,0,0.5)));
			ARSystem.giveBuff(target, new Stun(target), 80);
			target.setRemainingAir(0);
			delay(()->{target.damage(1);},10);
			delay(()->{target.damage(1);},30);
			delay(()->{target.damage(1);},50);
			delay(()->{target.damage(1);},70);
		}
		if(n.equals("3")) {
			ARSystem.giveBuff(target, new Drop(target), 100000);
		}
		if(n.equals("4")) {
			target.setNoDamageTicks(0);
			target.damage(3,player);
			target.setVelocity(new Vector(0,0.5,0));
			for(int i =0; i<20; i++) {
				delay(()->{
					target.setVelocity(ULocal.lookAt(player.getLocation().clone(), target.getLocation()).getDirection().multiply(2));
				},2*i);
			}
		}
		if(n.equals("5")) {
			target.setNoDamageTicks(0);
			target.damage(7,player);
			ARSystem.playSound((Entity)player, "c147kang",0.5f,1);
			ARSystem.giveBuff(target, new Stun(target), 100);
		}
		if(n.equals("6")) {
			Location lc = target.getLocation();
			delay(()->{
				target.setVelocity(ULocal.lookAt(target.getLocation().clone(), lc).getDirection().setY(0).multiply(0.8f));
			},1);	
		}

		if(n.equals("7")) {
			target.setVelocity(new Vector(0,-0.2f,0));
			ARSystem.giveBuff(target, new Silence(target), 10);
			target.setNoDamageTicks(0);
			target.damage(0.1,player);

			if(!Rule.buffmanager.isBuff(target, "exposure")) {
				ARSystem.giveBuff(target, new Exposure(target) , 40, 0.02f);
			} else {
				Rule.buffmanager.selectBuffAddTime(target, "exposure", 3);
				Rule.buffmanager.selectBuffAddValue(target, "exposure", 0.02f);
			}
		}
		if(n.equals("8")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			ARSystem.giveBuff(target, new NoHeal(target), 100);
		}
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(e.getCause() != DamageCause.ENTITY_ATTACK) {
			e.setCancelled(true);
			e.setDamage(0);
			return false;
		}
		return super.damage(e);
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) {
				float cr = this.crt;
				int b = 1;
				if(AMath.random(100)-(int)(cr*100) <= 1) {
					cr -= 1;
					b = 2;
					while(cr > 0 && AMath.random(100)-(int)(cr*100) <= 1) {
						cr -= 1;
						b*=2;
					}
					Holo.create(e.getEntity().getLocation(),"§cCritical! §4§lx"+b+"",40,new Vector(0,0.01,0));
				}
				e.setDamage((e.getDamage() + (damage*0.3)) * b);
			}
		} else {
			if(isps) {
				if(AMath.random(100) <= range*2) {
					e.setDamage(0);
					ARSystem.playSound((Entity)player, "0miss");
				}
			}
			if(dg > 0 || sp > 100) {
				e.setDamage(e.getDamage()*0.5f);
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
				if(Rule.c.get(e) instanceof c57riri) {
					is = "riri";
					break;
				}
				if(Rule.c.get(e) instanceof c10bell) {
					is = "bell";
					break;
				}
			}
		}
		
		if(is.equals("riri")) {
			ARSystem.playSound((Entity)player, "c147riri");
		} else if(is.equals("bell")) {
			ARSystem.playSound((Entity)player, "c147bell");
		} else {
			ARSystem.playSound((Entity)player, "c147db");
		}
		
		return true;
	}
}
