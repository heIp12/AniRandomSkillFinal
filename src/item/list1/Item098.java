package item.list1;

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

public class Item098 extends itemBase{
	int count = 10;
	public Item098(Player p){
		super(p);
		itemCode = 98;
	}
	
	@Override
	protected void onStart() {
		ARSystem.playSoundAll("item98");
		delay(()->{
			random();
		},60);
		super.onStart();
	}
	
	void random(){
		if(count > 0) {
			count--;
			int r = AMath.random(5);
			if(AMath.random(10) <= 3) r = AMath.random(8);
			Player pl = null;
			if(r >= 3) {
				pl = ARSystem.RandomPlayer();
				if((r == 4 || r == 5 || r == 7 || r == 8) && pl != player) {
					pl = ARSystem.RandomPlayer();
				} else {
					pl = ARSystem.RandomPlayer();
				}
			}
			String s = "";
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p).number == 153 && Rule.c.get(p).cooldown[0] <= 0) {
					if(pl!=p && (r == 1 || r == 2 || r == 7 || r == 8)) {
						pl = p;
						Rule.c.get(p).spskillen();
						Rule.c.get(p).cooldown[0] = Rule.c.get(p).setcooldown[0];
						ARSystem.playSoundAll("c153sp");
						break;
					} else if(pl == p && Rule.c.size() > 1 && (r == 3 || r == 6)) {
						int i = 0;
						while(pl == p) {
							pl = ARSystem.RandomPlayer();
							i++;
							if(i > 1000) break;
						}
						Rule.c.get(p).spskillen();
						Rule.c.get(p).cooldown[0] = Rule.c.get(p).setcooldown[0];
						ARSystem.playSoundAll("c153sp");
						break;
					}
				}
			}
			
			if(pl != null) {
				s += pl.getName()+" ";
			} else {
				pl = player;
				s += player.getName()+" ";
			}
			
			int rd = r;
			Player p = pl;
			delay(()->{
				if(rd == 1) {
					Rule.c.get(p).skillmult += 0.3;
				}
				else if(rd == 2) {
					float hp = Rule.c.get(p).hp*0.2f;
					Rule.c.get(p).hp *= 1.2;
					p.setMaxHealth(Rule.c.get(p).hp);
					ARSystem.heal(p, hp);
				}
				else if(rd == 3) {
					p.damage(8,NpcPlayer.npc(p.getLocation()));
				}
				else if(rd == 4) {
					ARSystem.heal(p, 8);
				}
				else if(rd == 5) {
					for(int i =0; i<10; i++) Rule.c.get(p).cooldown[i] = 0;
				}
				else if(rd == 6) {
					Rule.c.put(p,new c000humen(p, Rule.gamerule, null));
				}
				else if(rd == 7) {
					new G_Supply(p);
				}
				else if(rd == 8) {
					ARSystem.addItem(p, ItemList.getValueCode(0, 2000));
				}
			},20);
			
			for(Player pr : Bukkit.getOnlinePlayers()) {
				pr.sendTitle(Text.get("item:"+itemCode), s+Text.get("item:98t"+rd));
			}
			
			delay(()->{
				if(count > 0) random();
			},40);
		}
	}
	
	@Override
	public void itemRemove() {
		count = 0;
	}
}
