package mode.lobodebuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import mode.lobobuff.LoboBuffBase;
import util.NpcPlayer;

public class RB_db017 extends LoboBuffBase{
	public RB_db017() {
		id = 17;
		debuff = true;
	}
	
	@Override
	public void onPlayerDie(Player p) {
		ARSystem.playSoundAll("queenbeespawn");
		ARSystem.spellLocCast(NpcPlayer.npc(p.getLocation()),p.getLocation(), "lobo_bee");
		int size = getlobo().cr.size();
		if(size > 2) ARSystem.spellLocCast(NpcPlayer.npc(p.getLocation()),p.getLocation(), "lobo_bee");
		if(size > 4) ARSystem.spellLocCast(NpcPlayer.npc(p.getLocation()),p.getLocation(), "lobo_bee");
		if(size > 8) ARSystem.spellLocCast(NpcPlayer.npc(p.getLocation()),p.getLocation(), "lobo_bee");
		if(size > 13) ARSystem.spellLocCast(NpcPlayer.npc(p.getLocation()),p.getLocation(), "lobo_bee");
	}
}
