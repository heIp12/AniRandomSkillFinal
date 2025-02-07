package mode.lobodebuff;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSinfo;
import ars.ARSystem;
import ars.Rule;
import buff.Follow;
import buff.PowerUp;
import buff.Rampage;
import event.Skill;
import mode.MLoboTomy;
import mode.lobobuff.LoboBuffBase;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import util.ItemCreate;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class RB_db004 extends LoboBuffBase{
	MemoryNPCDataStore store = new MemoryNPCDataStore();
	int delay = 0;
	Location l;
	public RB_db004() {
		id = 4;
		debuff = true;
	}
	
	@Override
	public void onNextStage() {
		l = Map.randomLoc();
	}
	
	@Override
	public void onTime(int time) {
		if(l == null) l = Map.randomLoc();
		if(delay <= 0) {
			ARSystem.spellLocCast(NpcPlayer.npc(l), l, "lobo_hamer");
			for(Player p : Rule.c.keySet()) {
				if(p.getLocation().distance(l) <= 1) {
					store = new MemoryNPCDataStore();
					delay = 30;
					p.setGameMode(GameMode.SPECTATOR);

					CitizensAPI.createNamedNPCRegistry(p.getName()+"", store);
					NPC npc1 = CitizensAPI.getNamedNPCRegistry(p.getName()+"").createNPC(EntityType.PLAYER, p.getName());
					npc1.spawn(p.getLocation());
					((Player)npc1.getEntity()).getInventory().setItemInMainHand(ItemCreate.Item(279, 193));
					delay(()->{
						npc1.despawn();
						npc1.destroy();
						CitizensAPI.removeNamedNPCRegistry(p.getName()+"");
					},100);
					Location lc = p.getLocation().clone();
					delay(()->{
						int i = 0;
						for(LivingEntity e : getlobo().mobs) {
							int j = i;
							CitizensAPI.createNamedNPCRegistry(p.getName()+""+i, store);
							NPC npc = CitizensAPI.getNamedNPCRegistry(p.getName()+""+i).createNPC(EntityType.PLAYER, p.getName());
							npc.spawn(lc);
							((Player)npc.getEntity()).getInventory().setItemInMainHand(ItemCreate.Item(279, 193));
							ARSystem.giveBuff((LivingEntity) npc.getEntity(), new Follow(npc, e, true), 560, 0.4);
							ARSystem.giveBuff((LivingEntity) npc.getEntity(), new Rampage((LivingEntity) npc.getEntity(),e), 560, 0.2);
							ARSystem.giveBuff((LivingEntity) npc.getEntity(), new PowerUp((LivingEntity) npc.getEntity()), 560, 7);
							for(int k =0; k < 31; k++) {
								delay(()->{
									if(npc != null && npc.isSpawned() && (e == null || e.isDead() || e.getHealth() < 1)) {
										npc.despawn();
										npc.destroy();
										CitizensAPI.removeNamedNPCRegistry(p.getName()+""+j);
									}
								},k*20);
							}
							delay(()->{
								if(npc != null && npc.isSpawned()) {
									npc.despawn();
									npc.destroy();
									CitizensAPI.removeNamedNPCRegistry(p.getName()+""+j);
								}
							},600);
							i++;
						}
	
						ARSinfo ars = ARSystem.AniRandomSkill;
						delay(()->{
							if(ars == ARSystem.AniRandomSkill) {
							Skill.quit(p);
							MLoboTomy.cr.remove(p.getName());
							}
						},620);
					},100);
				}
			}
		} else {
			delay--;
		}
	}
}
