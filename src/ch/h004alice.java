package ch;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.PowerUp;
import buff.Stun;
import c.c00main;
import event.Skill;
import manager.Bgm;
import util.AMath;
import util.Local;

public class h004alice extends c00main{
	boolean music = false;
	int select = 0;
	String m = "";
	String cb = "";
	int combo = 0;
	int fullcombo = 0;
	int[] code;
	int c_size = 0;
	int c_count = 0;
	int life = 3;
	String bgm = "";
	
	boolean musicc = false;
	String msg2 = "";
	int sk3c = 0;
	boolean sk3 = false;
	boolean move = false;
	
	Location local;
	
	String yee = "";
	String yee2 = "12133212552324456";
	
	public h004alice(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 996;
		load();
		text();
		ARSystem.playSound((Entity)player, "aliceselect");
		
	}
	
	@Override
	public boolean skill1() {
		m = Main.GetText("c996:a1");
		node();
		Bgm.setForceBgm("ac1");
		combo = 0;
		for(Player p : Rule.c.keySet()) {
			ARSystem.giveBuff(p, new PowerUp(p), 100, 0);
		}
		return true;
	}
	@Override
	public boolean skill2() {
		m = Main.GetText("c996:a2");
		node();
		Bgm.setForceBgm("ac2");
		combo = 0;
		return true;
	}
	@Override
	public boolean skill3() {
		m = Main.GetText("c996:a3");
		node();
		Bgm.setForceBgm("ac3");
		combo = 0;
		sk3c++;
		sk3 = true;
		return true;
	}
	
	void node() {
		local = player.getLocation();
		Location loc = player.getLocation();
		loc.setPitch(30);
		ARSystem.giveBuff(player, new Stun(player), 100);
		life = 3;
		fullcombo = 0;
		skill("c996_piano");
		player.teleport(loc);
		code = new int[10000];
		cb = "";
		c_size = 0;
		c_count = 0;
		musicc = true;
		music = true;
		m = m.replace(" ", "");
		while(m.length() > 0) {
			int i = Integer.parseInt(m.substring(0,1));
			m = m.substring(1);
			int j = Integer.parseInt(m.substring(0,1));
			m = m.substring(1);
			code[c_size++] = i;
			if(i!= 0) fullcombo++;
			for(;j>0;j--) {
				code[c_size++] = 0;
			}
		}
	}
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(music) {
			select = e.getNewSlot()+1;
			if(!musicc) {
				if(select != 8) {
					if(player.isSneaking()) {
						if(move) {
							ARSystem.playSound((Entity)player, "alice"+(select+7));
							skill("c996_piano_"+(select+7));
						} else {
							if(select != 9) ARSystem.playSound((Entity)player, "alices"+(select+5));
						}
						if(select == 9) super.key(e);
					} else {
						if(move) {
							if(select != 9) ARSystem.playSound((Entity)player, "alice"+select);
							skill("c996_piano_"+select);
						} else {
							if(select != 9) ARSystem.playSound((Entity)player, "alices"+select);
						}
						player.getInventory().setHeldItemSlot(8);
					}
					if(select < 9) yee += select;
					if(!yee2.contains(yee)) yee = "";
					if(yee.equals(yee2)) {
						delay(()->{
							ARSystem.playSound((Entity)player, "alicee");
						},10);
						yee = "";
					}
				}
			} else {
				for(int i=0; i < 5;i++) {
					if(code[c_count+i] == select) {
						if(i == 0) {
							combo = 0;
							cb = "§7miss";
							code[c_count+i] = 0;
							Location loc = player.getLocation();
							loc.setPitch(0);
							ARSystem.spellLocCast(player, Local.offset(loc, new Vector(2,2,1.0 - AMath.random(0, 20)*0.1)), "c996_p3");
						}
						if(i == 1){
							combo++;
							cb = "§aGood!";
							code[c_count+i] = 0;
							Location loc = player.getLocation();
							loc.setPitch(0);
							ARSystem.spellLocCast(player, Local.offset(loc, new Vector(2,1,1.0 - AMath.random(0, 20)*0.1)), "c996_p2");
						}
						if(i == 2){
							combo++;
							cb = "§6Perfect!";
							code[c_count+i] = 0;
							Location loc = player.getLocation();
							loc.setPitch(0);
							ARSystem.spellLocCast(player, Local.offset(loc, new Vector(2,1,1.0 - AMath.random(0, 20)*0.1)), "c996_p1");
						}
						if(i == 3){
							combo++;
							cb = "§aGood!";
							code[c_count+i] = 0;
							Location loc = player.getLocation();
							loc.setPitch(0);
							ARSystem.spellLocCast(player, Local.offset(loc, new Vector(2,1,1.0 - AMath.random(0, 20)*0.1)), "c996_p2");
						}
						if(i == 4) {
							combo = 0;
							cb = "§7miss";
							code[c_count+i] = 0;
							Location loc = player.getLocation();
							loc.setPitch(0);
							ARSystem.spellLocCast(player, Local.offset(loc, new Vector(2,2,1.0 - AMath.random(0, 20)*0.1)), "c996_p3");
						}
						skill("c996_piano_"+AMath.random(14));
						player.sendTitle(cb+" §f| §ecombo : " + combo, msg2,0,10,0);
						break;
					}
				}
			}
			player.getInventory().setHeldItemSlot(7);
			return true;
		} else {
			return super.key(e);
		}
	
	}

	@Override
	public boolean tick() {
		buff();
		if(tk%2==0) {
			if(music) {
				if(player.getLocation().distance(local) <= 0) {
					move = true;
				} else {
					move = false;
				}
				if(player.getLocation().getYaw() != local.getYaw() ||
				   player.getLocation().getPitch() != local.getPitch() ||
				   Math.abs(player.getLocation().getY() - local.getY()) > 0.3 ||
				   Math.abs(player.getLocation().getZ() - local.getZ()) > 0.05 ||
				   Math.abs(player.getLocation().getX() - local.getX()) > 0.05 ) {
					player.teleport(local);
					player.setVelocity(new Vector(0,0,0));
				}
				ARSystem.giveBuff(player, new Nodamage(player), 20);
			}
			if(c_count < c_size && musicc) {
				if(code[c_count] != 0) {
					cb = "§7miss";
					combo = 0;
					Location loc = player.getLocation();
					loc.setPitch(0);
					ARSystem.spellLocCast(player, Local.offset(loc, new Vector(2,2,1.0 - AMath.random(0, 20)*0.1)), "c996_p3");
				}
				c_count++;
				msg2 = "";
				for(int i=0;i<20;i++) {
					if(i == 0) msg2 += "§e" + code[c_count+i];
					if(i == 1) msg2 += "§c§l" + code[c_count+i];
					if(i == 2) msg2 += "§4§l§n" + code[c_count+i] +"";
					if(i == 3) msg2 += "§6" + code[c_count+i];
					if(i == 4) msg2 += "§a" + code[c_count+i];
					if(i > 4) msg2 += code[c_count+i];
				}
				msg2 = msg2.replace("0", "_");
				player.sendTitle(cb+" §f| §ecombo : " + combo, msg2,0,10,0);
				if(cb.equals("§7miss")) {
					life--;
					cb = "";
					if(life <= 0) {
						
						music = false;
						musicc = false;
						skill("c996_piano_remove");
						cooldown[1] =cooldown[2] = cooldown[3] = 10;
						Bgm.setTime(0);
						if(sk3c > 0) {
							for(Player p : Rule.c.keySet()) {
								Rule.c.get(p).sskillmult -= 0.005f*sk3c;
								if(Rule.c.get(p).sskillmult < 0) Rule.c.get(p).sskillmult = 0;
							}
							sk3c = 0;
							sk3 = false;
						}
					}
				}
				if(c_count == c_size && combo == fullcombo) {
					spskillon();
					spskillen();
					Skill.win(player);
				}
			} else if(musicc){
				ARSystem.giveBuff(player, new Stun(player), 5);
				ARSystem.giveBuff(player, new Nodamage(player), 5);
				music = false;
				musicc = false;
				skill("c996_piano_remove");
				cooldown[1] =cooldown[2] = cooldown[3] = 10;
				if(sk3c > 0) {
					for(Player p : Rule.c.keySet()) {
						Rule.c.get(p).sskillmult -= 0.005f*sk3c;
						if(Rule.c.get(p).sskillmult < 0) Rule.c.get(p).sskillmult = 0;
					}
					sk3c = 0;
					sk3 = false;
				}
				Bgm.setTime(0);
			}
		}
		return true;
	}

	public void buff() {
		String s = Bgm.bgmcode;
		if(s.equals("ac2")&& tk%5 == 0) {
			for(Player p : Rule.c.keySet()) ARSystem.heal(p, 0.3);
		}

		
		if(s.equals("ac1")&& tk%5 == 0) {
			for(Player p : Rule.c.keySet()) {
				PowerUp power = (PowerUp)Rule.buffmanager.selectBuff(p, "powerup");
				if(power == null) continue;
				power.addValue(0.01);
				power.setTime(40);
			}
		}
		
		if(s.equals("ac3")&& tk%5 == 0) {
			for(Player p : Rule.c.keySet()) {
				Rule.c.get(p).sskillmult += 0.005f;
			}
			sk3c++;
		}
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(musicc) {
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		if(music) {
			music = false;
			musicc = false;
			skill("c996_piano_remove");
			ARSystem.giveBuff(player, new Stun(player), 2);
			ARSystem.giveBuff(player, new Nodamage(player), 2);
		} else {
			skill("c996_piano");
			yee = "";
			music = true;
			Location loc = player.getLocation();
			loc.setPitch(30);
			player.teleport(loc);
			local = player.getLocation();
			ARSystem.playSound((Entity)player, "alicedb");
		}
		return true;
	}
}
