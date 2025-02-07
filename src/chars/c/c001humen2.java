package chars.c;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import ars.TeamInfo;
import chars.c2.c62shinon;
import types.box;
import util.AMath;
import util.GetChar;
import util.MSUtil;
import util.MagicSpellVar;
import util.Text;

public class c001humen2 extends c00main{
	boolean bt = false;
	TeamInfo p1;
	TeamInfo p2;
	public int i = 0;
	float p1p = 0;
	float p2p = 0;
	
	public c001humen2(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 10001;
		load();
		text();
		ARSystem.playSound(player, "humendb1");
		Rule.buffmanager.selectBuffTime(p, "silence", 0);
		Rule.playerinfo.get(player).itemcount = 0;
	}
	
	@Override
	public boolean skill1() {
		if(bt) {
			if(Rule.playerinfo.get(player).getCradit() > i*100) {
				i++;
				if(i > 10) i = 10;
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(bt) {
			if(Rule.playerinfo.get(player).getCradit() > -i*100) {
				i--;
				if(i < -10) i = -10;
			}
		}
		return true;
	}
	
	public void win(int i) {
		if(this.i > 0 && i == 1 || this.i < 0 && i == 2) {
			
		} else if(this.i != 0){
			float my = p(p2p,p1p)*100;
			if(i < 0) my = p(p1p,p2p)*100;
			
			float p = Math.abs(this.i)/p2p;
			if(this.i > 0) p = Math.abs(this.i)/p1p;
			
			System.out.println(p1p +" " + p2p + " [" + p +"] :"+ ((p1p+p2p)*p));
			float money = (p1p+p2p)*100* (p);
			money = Math.abs(money);
			float m2 = money * 0.1f;
			money -= m2;
			
			player.sendMessage("§a§l[ARSystem] §a§lPoint +"+ (int)money + " §e["+(p1p+p2p)*100+"§c("+AMath.round(p*100,0)+"%)§e]");
			player.sendMessage("§a§l[ARSystem] §a§l수수료 10% 차감");

			if(this.i > 0) {
				int coin = (int)((m2*0.9)/p1.getPlayer().size());
				for(Player pl : p1.getPlayer()) {
					Rule.playerinfo.get(pl).addcradit(coin,Text.get("main:mode11"));
					pl.sendMessage("§a§l[ARSystem] §a§lPoint +"+ coin + " §e["+player+"]");
				}
			} else {
				int coin = (int)((m2*0.9)/p2.getPlayer().size());
				for(Player pl : p2.getPlayer()) {
					Rule.playerinfo.get(pl).addcradit(coin,Text.get("main:mode11"));
					pl.sendMessage("§a§l[ARSystem] §a§lPoint +"+ coin + " §e["+player+"]");
				}
				
			}
			Rule.playerinfo.get(player).addcradit((int)(money),Text.get("main:mode11"));
			i = 0;
		}
	}
	
	public int be(boolean bt) {
		this.bt = bt;
		if(bt) {
			player.sendMessage("§a§l[ARSystem] §f"+ Text.get("c10001:p2"));
		} else {
			player.sendMessage("§a§l[ARSystem] §f"+ Text.get("c10001:p3"));
			
			Rule.playerinfo.get(player).addcradit(-Math.abs(this.i*100),Text.get("main:mode11"));
			return i;
		}
		return 0;
	}
	
	public void players(TeamInfo p12,TeamInfo p22) {
		this.p1 = p12;
		this.p2 = p22;
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0 && p1 != null && p2 != null) {
			String name = p1.getTeamName();
			if(i < 0) name = p2.getTeamName();
			scoreBoardText.add("&c ["+Main.GetText("c10001:p1")+ "] : &f"+ name +"&c[&a"+ Math.abs(i*100)+"&c]");
			if(!bt) {
				scoreBoardText.add("&f&l["+p1.getTeamName() +"]&e : "+p1p*100+" &c("+(int)AMath.round(p(p1p,p2p)*100, 0)+"%)");
				scoreBoardText.add("&f&l["+p2.getTeamName() +"]&e : "+p2p*100+" &c("+(int)AMath.round(p(p2p,p1p)*100, 0)+"%)");
			}
			float maxhp = 0;
			float hp = 0;
			for(Player p : p1.getPlayer()) {
				maxhp += p.getMaxHealth();
				hp += p.getHealth();
			}
			scoreBoardText.add("&f&l["+p1.getTeamName() +"]&4 Hp: &c" + hp + " / " + maxhp + " &e&l("+(int)(hp/maxhp*100)+"%)");
			maxhp = hp = 0;

			for(Player p : p2.getPlayer()) {
				maxhp += p.getMaxHealth();
				hp += p.getHealth();
			}
			scoreBoardText.add("&f&l["+p2.getTeamName() +"]&4 Hp: &c" + hp + " / " + maxhp + " &e&l("+(int)(hp/maxhp*100)+"%)");
		}
		
		return super.tick();
	}
	float p(float p,float p2) {
		float nm = p/(p+p2);
		
		if(p == 0) return 0;
		if(p2 == 0) return 1;
		return nm;
	}
	
	@Override
	public boolean skill6() {
		ARSystem.playSound((Entity)player, "humendb"+(AMath.random(5)+1));
		return true;
	}

	
	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player, "humendb"+(AMath.random(5)+1));
		return true;
	}

	public void be2(float p1p, float p2p) {
		this.p1p = p1p;
		this.p2p = p2p;
	}
}
