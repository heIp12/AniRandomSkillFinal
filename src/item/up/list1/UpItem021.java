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
import util.AMath;
import util.ItemCreate;
import util.Text;

public class UpItem021 extends upitemBase{
	public UpItem021(Player p){
		super(p);
		itemCode = 21;
	}
	
	@Override
	protected void onStart() {
		Rule.buffmanager.selectBuffAddValue(player, "barrier", 15);
		super.onStart();
	}
	
	@Override
	public void onTick() {
		if(Rule.c.get(player) != null) {
			if(Rule.c.get(player).isBattleTime() == 60) {
				float br = 15;
				for(int i=0; i<5; i++) delay(()->{ARSystem.playSound((Entity)player, "entity.generic.eat");},i*4);
				br -= Rule.buffmanager.GetBuffValue(player, "barrier");
				if(br <= 0) br = 0;
				Rule.buffmanager.selectBuffAddValue(player, "barrier", br);
			}
		}
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(Rule.c.get(player).number == 117 && e.getNewSlot() == 1 && Rule.c.get(player).cooldown[2] <= 0 && AMath.random(20) <= 5) {
			ARSystem.spellCast(player,"c117_s2");
		}
		
		return super.onSkill(e);
	}
}
