package manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.entity.LivingEntity;

import buff.Buff;

public class EntityBuffManager {
	List<Buff> buff;
	LivingEntity entity;
	lengthCompare lc = new lengthCompare();
	public EntityBuffManager(LivingEntity e){
		entity = e;
		buff = new ArrayList<Buff>();
	}
	public List<Buff> getBuff() {
		return buff;
	}
	public LivingEntity getTarget() {
		return entity;
	}
	
	public void clear() {
		entity = null;
		for(Buff buff : buff) {
			if(buff != null) {
				buff.stop();
			}
		}
		buff.clear();
	}
	
	public void bufforder() {
		if(buff != null && lc != null) {
			try {
				List<Buff> buffa = buff;
				Collections.sort(buffa,lc);
				buff = buffa;
			} catch (Exception e) {
				
			}
		}
	}
}

class lengthCompare implements Comparator<Buff> {
	@Override
	public int compare(Buff o1, Buff o2) {
		 if(o1.order > o2.order) {
	            return 1;
	        }else if(o1.order < o2.order) {
	            return -1;
	        }
		 return 0;
	}
}