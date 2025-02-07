package item.craft;

import java.util.ArrayList;
import java.util.List;

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
import ars.gui.G_Item;
import ars.gui.G_Supply;
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import chars.c.c000humen;
import event.Skill;
import item.list1.itemBase;
import manager.AdvManager;
import types.BuffType;
import types.ItemList;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class Item1004 extends itemBase{
	int count = 15;
	public Item1004(Player p){
		super(p);
		itemCode = 1004;
	}
	
	
	@Override
	protected void onStart() {
		ARSystem.playSoundAll("item1004");
		count = 8 + Rule.c.size()*2;
		delay(()->{
			random();
		},60);
		super.onStart();
	}
	
	void random(){
		if(count > 0) {
			count--;
			Player pl = ARSystem.RandomPlayer();
			if(pl == player) {
				pl = ARSystem.RandomPlayer();
			}
			
			int power = 5;
			if(AMath.random(10) <= 4) power = 30;
			if(AMath.random(10) <= 1) power = 999;
			
			int rd = AMath.random(power);
			
			Player p = pl;
			delay(()->{
				p.setNoDamageTicks(0);
				p.damage(rd,NpcPlayer.npc(p.getLocation()));
			},20);
			
			for(Player pr : Bukkit.getOnlinePlayers()) {
				pr.sendTitle(Text.get("item:"+itemCode), p.getName() +Text.get("item:1004t1") + rd + Text.get("item:1004t2"));
			}
			
			delay(()->{
				if(count > 0) random();
			},40);
		}
	}
}
