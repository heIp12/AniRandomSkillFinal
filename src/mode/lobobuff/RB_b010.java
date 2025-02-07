package mode.lobobuff;

import org.bukkit.entity.LivingEntity;

public class RB_b010 extends LoboBuffBase{
	public RB_b010() {
		id = 10;
		getlobo().humanDelect += 5;
		int i = 0;
		for(LivingEntity v : getlobo().vilager) {
			i++;
			v.remove();
			if(i == 5) break;
		}
	}
	
	@Override
	public void onRemove() {
		getlobo().humanDelect -= 5;
	}
}