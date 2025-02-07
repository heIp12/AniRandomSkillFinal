package item.up.list1;

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
import buff.Buff;
import buff.Dancing;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import event.WinEvent;
import item.list1.itemBase;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class Upitem120 extends upitemBase{
	Location lc;
	int tk = 0;
	
	public Upitem120(Player p){
		super(p);
		itemCode = 120;
		setcooldown = 30;
	}
	@Override
	public boolean skillCast() {
		delay(()->{ARSystem.giveBuff(player, new PowerUp(player), 60,3);tk=60;},60);
		ARSystem.playSound(player, "item120");
		return false;
	}
	
	@Override
	protected void onTick() {
		if(tk > 0) {
			tk--;
		}
		super.onTick();
	}
	
	
	@Override
	public String getActionbar() {
		if(tk <= 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		return "§c§l<§6"+n+" : §4§l" +AMath.round(tk*0.05, 1) +"§c§l>";
	}
	
}
