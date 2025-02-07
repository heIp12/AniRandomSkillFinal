package item.etc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
import ars.TeamInfo;
import ars.gui.G_AdvSelect;
import ars.gui.G_Nitory;
import ars.gui.G_RareShop;
import ars.gui.G_Supply;
import ars.gui.solo.G_MapSelect;
import buff.Airborne;
import buff.Follow;
import buff.Nodie;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import item.list1.itemBase;
import manager.Bgm;
import mode.MEvent;
import mode.MQb;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import types.ItemList;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10103 extends itemBase{
	Entity tg;
	TeamInfo team;
	
	public Item10103(Player p){
		super(p);
		itemCode = 100103;
		setcooldown = 60;
		tg = p;
		if(Rule.team.isTeam(p)) {
			team = Rule.team.getTeam(p).get(0);
		}
	}
	
	@Override
	protected void onTick() {
		if(team != null && team.isTeam(player)) team.Join(player);
		
		if(player.getHealth()/player.getMaxHealth() <= 0.3 && isCooldown()) {
			MemoryNPCDataStore store = new MemoryNPCDataStore();
			ARSystem.potion(player, 14, 60, 1);
			ARSystem.giveBuff(player, new Nodie(player), 10);
			CitizensAPI.createNamedNPCRegistry(player.getName()+"", store);
			NPC npc1 = CitizensAPI.getNamedNPCRegistry(player.getName()+"").createNPC(EntityType.PLAYER, player.getName());
			npc1.spawn(player.getLocation());
			player.setHealth(player.getMaxHealth()*0.3f);

			ARSystem.giveBuff((LivingEntity) npc1.getEntity(), new Follow(npc1, (LivingEntity)tg, true), 60, 0.35);
			delay(()->{
				npc1.despawn();
				npc1.destroy();
				CitizensAPI.removeNamedNPCRegistry(player.getName()+"");
			},60);
		}
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		tg = e.getDamager();
		return super.onHit(e);
	}
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		tg = e.getEntity();
		return super.onAttack(e);
	}
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1453);
		item = ItemCreate.Name(item, "§c§l【§e§l☤§c§l】§a"+Text.get("item:"+itemCode).replace("??", MEvent.name));
		return item;
	}
}
