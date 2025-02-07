package chars.c;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.TimeStop;
import chars.c2.c62shinon;
import event.Skill;
import manager.Bgm;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.MSUtil;
import util.MagicSpellVar;
import util.ULocal;

public class cMove extends c00main{
	List<Location> loc = new ArrayList<>();
	boolean start = false;
	String txt;
	
	public cMove(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 100003;
		load();
		text();
		ARSystem.playSound(player, "humendb1");
	}
	
	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player, "humendb"+(AMath.random(5)+1));
		return true;
	}

	@Override
	public boolean key_f() {
		if(!start) {
			player.sendTitle("", "Start!",0,40,0);
		} else {
			player.sendTitle("", "Stop!",0,40,0);
			txt = "";
			Location lc = player.getLocation();
			int i = 1;
			for(Location l : loc) {
				if(ULocal.isEqual(l, lc)) {
					i++;
				} else {
					txt += AMath.round(l.getX(),2)+","+AMath.round(l.getY(),2)+
							","+AMath.round(l.getZ(),2)+","+AMath.round(l.getYaw(),2)+","+AMath.round(l.getPitch(),2)+","+i+";";
					i = 1;
				}
				lc = l;
			}
			loc.clear();
		}
		start = !start;
		return super.key_f();
	}
	
	Location loc2 = player.getLocation().clone();

	@Override
	public boolean tick() {
		if(start) {
			loc.add(player.getLocation());
		}
		return super.tick();
	}
	
	@Override
	public boolean chat(PlayerChatEvent e) {
		if(e.getPlayer() == player) {
			if(e.getMessage().contains("-play")) {
				int t = 0;
				for(String s : txt.split(";")) {
					String[] l = s.split(",");
					Location lc = player.getLocation().clone();
					lc.setX(Double.parseDouble(l[0]));
					lc.setY(Double.parseDouble(l[1]));
					lc.setZ(Double.parseDouble(l[2]));
					lc.setYaw(Float.parseFloat(l[3]));
					lc.setPitch(Float.parseFloat(l[4]));
					Location tloc = lc.clone();
					for(int i = 0; i < Integer.parseInt(l[5]);i++) {
						delay(()->{
							player.teleport(tloc);
						},t++);
					}
				}
			} else if(e.getMessage().contains("-load")) {
				String load = e.getMessage().replace("-load", "").replace(" ", "");
				Bukkit.broadcastMessage("load Loc : " + load);
				txt = (String)Rule.Var.Load(load);

			} else if(e.getMessage().contains("-save")) {
				String save = e.getMessage().replace("-save", "").replace(" ", "");
				Rule.Var.Save(save, txt);
				Bukkit.broadcastMessage("Save Loc : " + save);
				
			} else if(e.getMessage().contains("-info")) {
				Bukkit.broadcastMessage("Load : " + txt);
				
			}
		}
		return super.chat(e);
	}
}
