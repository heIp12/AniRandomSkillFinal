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

public class Upitem123 extends upitemBase{
	int c = 0;
	public Upitem123(Player p){
		super(p);
		itemCode = 123;
		setcooldown = 60;
		cooldown = 30;
	}
	@Override
	public boolean skillCast() {
		ARSystem.playSoundAll("item123");
		c++;
		delay(()->{
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new Panic(p), 80);
				ARSystem.giveBuff(p, new PowerUp(p), 80, 4);
			}
		},80);
		return false;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * (1 + (c*0.3f)));
		return super.onHit(e);
	}
	
	@Override
	public String getActionbar() {
		if(cooldown > 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		return "§c§l<§6"+n+" : §4§l" +(int)((c*0.3)*100) +"§c§l>";
	}
	
}
