package item.etc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ars.ARSinfo;
import ars.ARSystem;
import ars.Rule;
import buff.Airborne;
import buff.Panic;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import event.Skill;
import item.list1.itemBase;
import types.TargetMap;
import types.box;
import util.AMath;
import util.Holo;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Item10012 extends itemBase{
	static public TargetMap<Player, Double> pl = new TargetMap<>();
	static public ARSinfo map = null;
	
	public Item10012(Player p){
		super(p);
		itemCode = 100012;
	}
	@Override
	protected void onStart() {
		if(map != ARSystem.AniRandomSkill) {
			map = ARSystem.AniRandomSkill;
			pl.clear();
		}
		setcooldown = 20;
		
		pl.add(player, 1);
		if(pl.get(player) >= 3) {
			Skill.death(player, player);
			ARSystem.playSoundAll("mindmix");
		}
	}
	
	@Override
	protected void onTick() {
		if(AMath.random(200) <= 1) {
			ARSystem.giveBuff(player, new Panic(player), 40);
		}
		if(timer%80 == 0) {
			ARSystem.playSound(player, "mindmix"+AMath.random(6));
		}
	}
	
	@Override
	public boolean skillCast(){
		ARSystem.playerItem.get(player).remove(this);
		Player p = ARSystem.RandomPlayer(player);
		player = p;
		ARSystem.playerItem.get(p).items.add(this);
		isStart = true;
		return false;
	}
	
	@Override
	public void onDeath(Player p, Entity e) {
		if(p == player) {
			skillCast();
		}
	}
	
	
	@Override
	public ItemStack getItem() {
		item = super.getItem();
		item.setTypeId(276);
		item.setDurability((short)1402);
		item = ItemCreate.Name(item, "§c§l【§e§l⸎§c§l】§a"+Text.get("item:"+itemCode));
		return item;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage()*0.7);
		return super.onAttack(e);
	}
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage()*1.5);
		return super.onHit(e);
	}
}
