package item.list2;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import ars.gui.G_Item;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import event.Skill;
import event.WinEvent;
import item.list1.itemBase;
import manager.Bgm;
import mode.MLoboTomy;
import mode.ModeBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class item115 extends itemBase{
	int time = 0;
	Location lc;
	int tk = 0;
	public item115(Player p){
		super(p);
		itemCode = 115;
		lc = player.getLocation().clone();
		ARSystem.playSound(player, "item115");
	}

	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		time -= 200;
		if(time <=0) {
			tk = 0;
			time = 0;
		}
		return super.onSkill(e);
	}
	
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 152) {
			tk = 2;
		}
	}
	
	@Override
	protected void onTick() {
		if(player.getLocation().distance(lc) <= 0.5) {
			time++;
		} else {
			lc = player.getLocation().clone();
			time -= 50;
			if(time <= 0) {
				time = 0;
				tk = 0;
			}
		}
		if(Rule.c.get(player).number == 152 && time < 600) {
			time = 600;
		}
		if(time == 500 && tk == 0) {
			tk = 1;
			ARSystem.playSound(player, "item115a");
		}
		if(time == 1000 && tk == 1) {
			tk = 2;
			ARSystem.playSound(player, "item115b");
		}
		if(time == 1500 && tk == 2) {
			tk = 3;
			ARSystem.playSound(player, "item115c");
		}
		if(time >= 2400) {
			time = 0;
			ARSystem.playerItem.get(player).remove(this);
			if(ARSystem.isGameMode("lobotomy")) {
				for(ModeBase m : ARSystem.AniRandomSkill.modes) {
					if(m instanceof MLoboTomy) {
						((MLoboTomy)m).dmg += 0.3f;
						break;
					}
				}
			} else {
				WinEvent event = new WinEvent(player);
				Bukkit.getPluginManager().callEvent(event);
				
				if(!event.isCancelled()) {
					win();
				}
			}
		}
		super.onTick();
	}
	
	void win(){
		Bgm.rep = false;
		Bgm.bgmlock = false;
		Bgm.setBgm("no");
		Bgm.nextbgm.add("yetu");
		for(Player p :Bukkit.getOnlinePlayers()) {
			p.stopSound("",SoundCategory.VOICE);
			p.stopSound("",SoundCategory.MASTER);
		}
		ARSystem.playSoundAll("item115win");
		Map.getMapinfo(1011);
		Location loc = Map.getCenter();
		loc.setY(4);
		loc.setPitch(0);
		player.teleport(loc);
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(Rule.c.get(p) != null && p != player) Rule.c.put(p, new c000humen(p, Rule.gamerule, null));
			if(Rule.c.get(p) != null) ARSystem.giveBuff(p, new TimeStop(p), 400);
			if(p != player) p.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(-5,0,5-AMath.random(0,100)*0.1)),loc));
			for(Player pl : Bukkit.getOnlinePlayers()) {
				if(p == player || pl == player) continue;
				p.hidePlayer(pl);
			}
		}
		ARSystem.spellLocCast(player,loc, "item115");
		ARSystem.giveBuff(player, new TimeStop(player), 120);
		for(int i = 0; i<8; i++) {
			delay(()->{
				Location lc = loc.clone();
				lc.setYaw(AMath.random(180)-90);
				player.teleport(ULocal.lookAt(ULocal.offset(lc,new Vector(2,0,0)), loc));
			},18*i);
		}

		delay(()->{
			player.teleport(ULocal.lookAt(ULocal.offset(loc,new Vector(-5,0,0)), loc));
			player.setGameMode(GameMode.SPECTATOR);
		},180);
		delay(()->{
			ARSystem.spellCast(player, "item115go");
		},200);
		delay(()->{
			Map.getMapinfo(1001);
			Location lc = Map.getCenter();
			lc.setY(31);
			lc.setPitch(0);
			player.teleport(lc);
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.teleport(lc);
			}
			ARSystem.spellLocCast(player,lc, "item115-2");
		},460);
		delay(()->{
			Skill.win(player);
		},520);
		
	}
	
	@Override
	public String getActionbar() {
		if(time > 2400) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		return "§c§l<§6"+n+" : §4§l" +AMath.round((2400-time)*0.05, 1) +"§c§l>";
	}
	
}
