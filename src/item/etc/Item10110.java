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
import event.FixedDealEvent;
import event.Skill;
import item.etc.Item10001.Stats;
import item.list1.itemBase;
import manager.Bgm;
import mode.MEvent;
import mode.MKagerou;
import mode.MQb;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10110 extends itemBase{
	MemoryNPCDataStore store = new MemoryNPCDataStore();
	
	public Item10110(Player p){
		super(p);
		itemCode = 100110;
		setcooldown = 1;
	}
	
	
	@Override
	protected void onStart() {
		for(itemBase i : ARSystem.playerItem.get(player).items) {
			if(i instanceof Item10001) {
				for(Stats st : ((Item10001)i).stats.values()) {
					st.max *= 1.3;
					st.gold -= 0.8f;
				}
			}
		}
		if(ARSystem.isGameMode("kagerou")) {
			MKagerou.playerLife.put(player, MKagerou.playerLife.get(player)+1);
		}
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		for(int i =0; i<20;i++) {
			delay(()->{
				if(!((LivingEntity)e.getEntity()).isDead()) {
					ARSystem.fixedDamage((LivingEntity)e.getEntity(), player, e.getDamage()*0.025);
				}
			},2*i+2);
		}
		e.setDamage(e.getDamage()* 1.5f);
		return super.onHit(e);
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1460);
		item = ItemCreate.Name(item, "§c§l【§e§l☤§c§l】§a"+Text.get("item:"+itemCode).replace("??", MEvent.name));
		return item;
	}
}
