package manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.util.Vector;

import api.Rule;
import ars.ARSystem;
import event.Skill;
import util.Holo;
import util.Text;
import util.ULocal;

public class MusicGame {
	public boolean speed = false;
	int combo = 0;
	int c_count = 0;
	int fullcombo = 0;
	int c_size = 0;
	int[] code;
	String cb = "";

	String msg2 = "";
	boolean title = false;
	Player player;
	Location loc;
	String bgm;
	
	int score = 0;
	int time = 0;
	
	int delay = 60;
	
	public MusicGame(Player player) {
		this.player = player;
	}


	public void Start(Location loc, String patten ,String bgm) {
		score = 0;
		this.bgm = bgm;
		this.loc = loc;
		code = new int[30000];
		for(int i = 0; i< code.length;i++) code[i] = 0;
		loc = player.getLocation().clone();
		loc.setPitch(0);
		time = 0;
		c_count = 0;
		combo = 0;
		Bgm.setForceBgm("no");
		c_size = 100;
		fullcombo = 0;
		for(String s : patten.split(";")) {
			c_size += Integer.parseInt(s.split(":")[0]);
			code[c_size] = Integer.parseInt(s.split(":")[1]);
			fullcombo++;
		}
		delay = Text.getI("c100003:delay");
	}
	
	
	public void key(int slot) {
		for(int i=0; i < 9;i++) {
			if(code[c_count+i] == slot) {
				if(i == 0) {
					combo = 0;
					cb = "§7miss";
					code[c_count+i] = 0;
					ARSystem.spellCast(player,"music_p3");
					ARSystem.spellLocCast(player, ULocal.offset(loc, new Vector(0,0,-2.5 + slot)), "music_color3");
				}
				if(i == 1 || i == 2 || i == 5 || i == 6){
					combo++;
					cb = "§aGood!";
					code[c_count+i] = 0;
					ARSystem.spellCast(player,"dance");
					ARSystem.spellCast(player,"music_p2");
					ARSystem.spellLocCast(player, ULocal.offset(loc, new Vector(0,0,-2.5 + slot)), "music_color2");
					score += 5*(10+combo/25*5);
				}
				if(i == 3 || i == 4){
					combo++;
					cb = "§6Perfect!";
					code[c_count+i] = 0;
					ARSystem.spellCast(player,"dance");
					ARSystem.spellCast(player,"music_p1");
					ARSystem.spellLocCast(player, ULocal.offset(loc, new Vector(0,0,-2.5 + slot)), "music_color1");
					score += 10*(10+combo/25*5);
				}
				if(i == 7) {
					combo = 0;
					cb = "§7miss";
					code[c_count+i] = 0;
					ARSystem.spellCast(player,"music_p3");
					ARSystem.spellLocCast(player, ULocal.offset(loc, new Vector(0,0,-2.5 + slot)), "music_color3");
				}
				if(title) player.sendTitle(cb+" §f| §ecombo : " + combo, "",0,10,0);
				Holo.create(ULocal.offset(loc.clone(), new Vector(0,-1.5,-2.5)), cb +"§f§l[x"+combo+"]" , 200, new Vector(0,0.07,0));
				break;
			}
		}
		player.getInventory().setHeldItemSlot(7);
	}
	
	public void tick() {
		if(time < 100) {
			time++;
			if(time%20 == 0)player.sendTitle(""+(5-(time/20)),"",0,10,10);
			if(time == 100) Bgm.setForceBgm(bgm);
		}
		if(isPlay()) {
			if(code[c_count+delay] != 0) {
				if(speed) {
					ARSystem.spellLocCast(player, loc, "music_pts"+code[c_count+delay]);
				} else {
					ARSystem.spellLocCast(player, loc, "music_pt"+code[c_count+delay]);
				}
						
			}
			if(code[c_count] != 0) {
				cb = "§7miss";
				ARSystem.spellCast(player,"music_p3");
				Holo.create(ULocal.offset(loc.clone(), new Vector(0,-1.5,-2.5)), cb , 300, new Vector(0,0.07,0));
				ARSystem.spellLocCast(player, ULocal.offset(loc, new Vector(0,0,-2.5 + code[c_count])), "music_color3");
				combo = 0;
			}
			if(c_count%20 == 0) {
				ARSystem.spellLocCast(player, loc, "music_pt0");
				ARSystem.spellLocCast(player, loc, "music_pt01");
				if(c_count%200 == 0) {
					ARSystem.spellLocCast(player, loc.clone().add(0,3,0), "music_pj");
				}
			}
			c_count++;
			msg2 = "";
			for(int i=0;i<20;i++) {
				if(i == 0) msg2 += "§e" + code[c_count+i];
				if(i == 1||i==2) msg2 += "§c§l" + code[c_count+i];
				if(i == 3||i==4) msg2 += "§4§l" + code[c_count+i] +"";
				if(i == 5|i==6) msg2 += "§6" + code[c_count+i];
				if(i == 7) msg2 += "§a" + code[c_count+i];
				if(i > 7) msg2 += code[c_count+i];
			}
			msg2 = msg2.replace("0", "_");
			if(title) player.sendTitle(cb+" §f| §ecombo : " + combo, msg2,0,10,0);
			if(cb.equals("§7miss")) {
				cb = "";
			}
			if(c_count%10 == 0) {
				Holo.create(ULocal.offset(loc.clone(), new Vector(0,0,2.5)), "§a§l[§6§fScore : "+score+"§a§l]" , 10, new Vector(0,0,0));
			}
		}
	}
	
	public boolean isFullCombo() {
		return c_count == c_size && combo == fullcombo;
	}
	public boolean isPlay() {
		return c_count < c_size;
	}
	public int score() {
		return score;
	}
}
