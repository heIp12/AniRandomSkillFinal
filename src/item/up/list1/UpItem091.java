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
import buff.Rampage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import types.BuffType;
import types.box;
import util.AMath;
import util.ItemCreate;
import util.Map;
import util.Text;
import util.ULocal;

public class UpItem091 extends upitemBase{
	int tick = 0;
	int cd = 0;
	public UpItem091(Player p){
		super(p);
		itemCode = 91;
		setcooldown = 40;
	}
	
	@Override
	public void onTick() {
		if(tick > 0) {
			if(tick%cd == 10) {
				ARSystem.playSound(player, "item91");
			}
			if(tick%cd == 0) {
				ARSystem.spellCast(player, "item91");
				for(int i = 0; i<3; i++) {
					delay(()->{
						ARSystem.spellCast(player, "item91");
						for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
							LivingEntity en = (LivingEntity)e;
							en.setNoDamageTicks(0);
							en.damage(0.5,player);
						}
					},i);
				}
			}
			tick--;
		}
	}

	
	public int night() {
		int time = (int) (player.getWorld().getTime()%24000);
		if(time > 0 && time < 12000) {
			if(time > 5000 && time < 7000) {
				return 4;
			} else if(time > 4000 && time < 8000) {
				return 3;
			} else  if(time > 2000 && time < 10000) {
				return 2;
			} else  
			return 1;
		}
		return 0;
	}

	@Override
	public String getActionbar() {
		if(tick <= 0) return super.getActionbar();
		String n = itemName.split(" ")[0];
		if(n.length() > 3) n = n.substring(0,2);
		return "§c§l<§6"+n+" : §4§l" +AMath.round(tick*0.05,1) +"§c§l>";
	}
	
	@Override
	public boolean skillCast(){
		int lv = night();
		if(lv == 0) return true;
		ARSystem.playSound((Entity)player, "item91");
		tick = 100;
		if(lv == 1) cd = 6;
		else if(lv == 2) cd = 4;
		else if(lv == 3) cd = 2;
		else if(lv == 4) cd = 1;
		return false;
	}
}
