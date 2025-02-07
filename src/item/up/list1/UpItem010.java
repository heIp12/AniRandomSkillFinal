package item.up.list1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import types.BuffType;
import util.ItemCreate;
import util.Text;

public class UpItem010 extends upitemBase{
	public UpItem010(Player p){
		super(p);
		itemCode = 10;
		setcooldown = 3;
	}
	
	
	@Override
	public void onTick() {
		for(Buff bf : Rule.buffmanager.getBuffs(player).getBuff()) {
			if(bf.istype(BuffType.CC) || bf.istype(BuffType.HEADCC)) {
				if(bf.getTime() >= 20 && isCooldown()) {
					bf.stop();
					ARSystem.playSound((Entity)player, "item10");
					ARSystem.spellCast(player, "item10");
				}
			}
		}
	}
	
	@Override
	protected void onStart() {
		if(Rule.c.get(player).number == 11) {
			for(int i =0; i<10; i++) Rule.c.get(player).setcooldown[i] *= 0.7f;
		}
	}
}
