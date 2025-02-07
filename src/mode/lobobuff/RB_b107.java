package mode.lobobuff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.ArmorUp;
import buff.Bload;
import buff.PowerUp;
import types.BuffType;

public class RB_b107 extends LoboBuffBase{
	public RB_b107() {
		id = 107;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		ARSystem.addItem(p, 100011);
		Rule.c.get(p).frist_damage*=0.7f;
	}
}