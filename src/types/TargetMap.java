package types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TargetMap<K,V extends Double> {
	HashMap<K,Double> target = new HashMap<>();
	List<K> removeList = new ArrayList<>();
	
	public TargetMap(){
		target = new HashMap<>();
		removeList = new ArrayList<>();
	}
	
	public HashMap<K,Double> get() { return target; }
	
	public void add(K k,double v) {
		if(target.get(k) == null) {
			target.put(k, 0.0);
		}
		target.put(k, target.get(k)+ v);
	}
	
	public void set(K k,double v) {
		target.put(k, v);
	}
	
	public void remove(K k) {
		target.remove(k);
	}
	
	public void removeAdd(K k) {
		removeList.add(k);
	}
	
	public void removes() {
		if(removeList != null && removeList.size() > 0) {
			for(K k : removeList) target.remove(k);
			removeList = new ArrayList<>();
		}
	}
	
	public Double get(K k) {
		return target.get(k);
	}
	
	public void clear() {
		target.clear();
	}
	
}
