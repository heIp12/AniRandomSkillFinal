package item.craft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import ars.gui.G_HeddenSelect;
import ars.gui.G_Item;
import ars.gui.G_Supply;
import ars.gui.solo.G_ItemSelect;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c001humen3;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import manager.Bgm;
import mode.MDummy;
import mode.MQb;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item1012 extends itemBase{
	LivingEntity e;
	public Item1012(Player p){
		super(p);
		itemCode = 1012;
		setcooldown = 10;
	}
	
	@Override
	public boolean skillCast() {
		ARSystem.playSound((Entity)player, "0gun4");
		ARSystem.spellCast(player, "item1012");
		delay(()->{
			ARSystem.playerAddRotate(player,0,(float) -20);
		},2);
		return false;
	}
	
	@Override
	protected void onTick() {

		if(timer%20 == 0 && Rule.c.size() == 2&& !ARSystem.isGameMode("dummy")) {
			ARSystem.addGameMode(new MDummy());
			Map.getMapinfo(1001);
			Location lc = Map.getCenter();
			lc.setY(31);
			Location lcc = lc.clone();
			lc.setZ(lc.getZ()-12);

			for(Player p : Bukkit.getOnlinePlayers()) {
				if(Rule.c.get(p) == null) p.teleport(Map.getCenter());
			}
			Bgm.setForceBgm("losa");
			Rule.team.reload();
			for(Player p : Rule.c.keySet()) {
				Rule.c.put(p, new c001humen3(p, Rule.gamerule, null));
				lc.setZ(lc.getZ()+8);
				((c001humen3)Rule.c.get(p)).SetLoc(ULocal.lookAt(lc.clone(),lcc));
				ARSystem.giveBuff(p, new Stun(p), 2000000);
				p.sendTitle("",Text.get("item:1012_t"),40,20,40);
			}
			Rule.c.get(player).delay(()->{
				ARSystem.playSoundAll("item1012");
				for(Player p : Rule.c.keySet()) {
					((c001humen3)Rule.c.get(p)).SetTime(System.currentTimeMillis());
					p.sendTitle("§4§l!!!","",20,0,40);
				}
			},100 + AMath.random(200));
		}
	}
}