package mode.lobobuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ars.Rule;
import util.GetChar;
import util.Text;

public class RB_b040 extends LoboBuffBase{
	Player death = null;
	int i = 0;
	public RB_b040() {
		id = 40;
	}
	
	@Override
	public void onNextStage() {
		death = null;
		i = 0;
	}
	
	@Override
	public void onPlayerDie(Player p) {
		if(i < 250){
			Bukkit.broadcastMessage("§a§l"+Text.get("lobo:b40") +" §a"+(250-i)+"(s)§f§l"+ " : §c" + p.getName());
			death = p;
		}
	}
	
	@Override
	public void onTime(int time) {
		i++;
		if(death == null) i = 249;
		if(i == 250) {
			Bukkit.broadcastMessage(Text.get("lobo:b40") + " : " + death.getName());
			Rule.c.put(death,GetChar.get(death, Rule.gamerule, "" + getlobo().cr.get(death.getName())));
			getlobo().charRep(death);
		}
	}
	
}