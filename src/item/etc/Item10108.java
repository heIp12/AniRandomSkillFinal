package item.etc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
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
import ars.gui.G_AdvSelect;
import ars.gui.G_Nitory;
import ars.gui.G_RareShop;
import ars.gui.G_Supply;
import ars.gui.solo.G_MapSelect;
import buff.Airborne;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.etc.Item10001.Stats;
import item.list1.itemBase;
import manager.Bgm;
import mode.MEvent;
import mode.MQb;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10108 extends itemBase{
	public Item10108(Player p){
		super(p);
		itemCode = 100108;
		setcooldown = 1;
	}
	
	@Override
	protected void onStart() {
		for(itemBase i : ARSystem.playerItem.get(player).items) {
			if(i instanceof Item10001) {
				for(Stats st : ((Item10001)i).stats.values()) {
					if(st.name.equals("crt")) {
						st.max += 5;
						st.value += 10;
					}
				}
			}
		}
	}
	
	@Override
	protected void onTick() {
		List<Player> e = ARSystem.PlayerOnlyBeamBox(player, 50, 2, box.TARGET);
		if(e.size() > 0) {
			String n = "§c";
			ARSystem.potion(e.get(0), 24, 10, 1);
			if(Rule.c.get(e.get(0)) != null) {
				int i = Rule.c.get(e.get(0)).number;
				n = Text.get("c"+i+":name1") + Text.get("c"+i+":name2");
			} else {
				n = e.get(0).getHealth() + " / " + e.get(0).getMaxHealth();
			}
			
			player.sendTitle(e.get(0).getName(), ""+ n,0,20,0);
		}
	}
	public List<Player> PlayerOnlyBeamBox(Entity player,float rangeblock, float size, types.box box){
		List<Player> entity = new ArrayList<Player>();
		Location loc = player.getLocation().clone();
		for(float i=0;i<rangeblock;i++) {
			loc.add(loc.getDirection());
			for (Player e : Bukkit.getOnlinePlayers()) {
				if(e.getLocation().distance(loc) <= size && e != player && e.getGameMode() != GameMode.SPECTATOR ) {
					if(ARSystem.isTarget(e, player ,box) && !entity.contains(e)) {
						entity.add(e);
					}
				}
			}
		}
		return entity;
	}
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1458);
		item = ItemCreate.Name(item, "§c§l【§e§l☤§c§l】§a"+Text.get("item:"+itemCode).replace("??", MEvent.name));
		return item;
	}
}
