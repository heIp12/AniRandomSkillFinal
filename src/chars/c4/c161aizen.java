package chars.c4;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Fascination;
import buff.Follow;
import buff.Noattack;
import buff.Nodamage;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import chars.c2.c51zenos;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import types.box;
import util.AMath;
import util.ULocal;

public class c161aizen extends c00main{
	MemoryNPCDataStore store = new MemoryNPCDataStore();
	List<NPC> npcs = new ArrayList<>();
	List<Player> target = new ArrayList<>();
	NPC movenpc;
	int movetime = 0;
	Location lc;
	public int s1Damage = 10;
	boolean solowin;
	
	int sptick = 0;
	int show = 0;
	
	@Override
	public void setStack(float f) {
		int i = (int)f;
		s1Damage = 10 + i*4;
		setcooldown[1] -= i;
		if(setcooldown[1] < 1) setcooldown[1] = 1;
	}
	
	public c161aizen(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 161;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c161s1");
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.giveBuff(player, new Silence(player), 20);
		skill("c161_s1");
		return true;
	}
	
	NPC npcSpawn(int time) {
		int i = AMath.random(1000);
		CitizensAPI.createNamedNPCRegistry(player.getName()+""+i, store);
		NPC npc = CitizensAPI.getNamedNPCRegistry(player.getName()+""+i).createNPC(EntityType.PLAYER, player.getName());
		npc.spawn(player.getLocation());
		npcs.add(npc);
		Player p = ((Player)npc.getEntity());
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.hidePlayer(p);
		}
		for(Player player : target) {
			player.showPlayer(p);
		}
		tpsdelay(()->{
			npcs.remove(npc);
			npc.despawn();
			npc.destroy();
			CitizensAPI.removeNamedNPCRegistry(player.getName()+""+i);
		},time);
		return npc;
	}
	
	@Override
	public boolean skill2() {
		int time = 60+(s1Damage-10)/4*10;
		NPC p = npcSpawn(time);
		p.faceLocation(player.getLocation().clone().add(player.getLocation().getDirection().multiply(10)));
		((Player)p.getEntity()).teleport(player.getLocation());
		LivingEntity tg = (LivingEntity)ARSystem.boxSOne(((Player)p.getEntity()), new Vector(30,8,30), box.TARGET, player.getName());
		
		if(tg != null) {
			ARSystem.giveBuff((LivingEntity) p.getEntity(), new Follow((LivingEntity) p.getEntity(), tg), time, 0.3);
			for(int i = 0; i< time-1; i++) {
				delay(()->{
					if(p != null && tg != null) {
						Location loc = p.getEntity().getLocation();
						loc = ULocal.lookAt(loc, tg.getLocation());
						p.faceLocation(loc.clone().add(loc.getDirection().multiply(10)));
					}
				},i);
			}
		}
		for(Player player : target) {
			player.hidePlayer(this.player);
		}
		show = time;
		

		if(!player.isSneaking()) {
			player.teleport(ULocal.offset(player.getLocation().clone(), new Vector(8,0,0)));
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(target.size() > 0) {
			if(ARSystem.winstop < 300) ARSystem.winstop = 300;
    		if(!player.isSneaking()) {
	    		for(Player player : target) {
					player.hidePlayer(this.player);
				}
				NPC p = npcSpawn(200);
				movenpc = p;
				
				Location loc = player.getLocation().clone();
				loc.setPitch(0);
				loc = ULocal.offset(loc, new Vector(0,0,2 + (-4*(AMath.random(2)-2))));
				player.teleport(loc);
				show = 200;
				lc = p.getEntity().getLocation().clone().subtract(player.getLocation().clone());
				lc.setY(0);
    		}
			movetime = 200;
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	

	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			delay(()->{
				for(Buff b : Rule.buffmanager.getBuffs(target).getBuff()) {
					b.setTime(0);
				}
			},19);
			delay(()->{
				target.setNoDamageTicks(0);
				target.damage(s1Damage,player);
			},20);
			ARSystem.giveBuff(target, new Stun(target), 40);
			ARSystem.giveBuff(target, new Silence(target), 40);
		}
	}
	int l = 0;
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(e.getNewSlot() == 2 && skillCooldown(3)) skill3();
		return super.key(e);
	}
	@Override
	public boolean tick() {
		if(sptick > 0) {
			sptick--;
			
			if(AMath.random(10) <= 1) {
				for(int i = 0; i< Math.max(1,Rule.c.size()/3) ;i++) {
					int time = 40;
					if(Rule.c.size() >= 3) time = 20;
					if(Rule.c.size() >= 6) time = 10;
					NPC p = npcSpawn(time);
					Player tg = target.get(AMath.random(target.size())-1);
	    			Location lc = tg.getLocation().clone();
	    			lc.setYaw(AMath.random(360));
	    			lc.setPitch(0);
	    			(p.getEntity()).teleport(ULocal.offset(lc, new Vector(AMath.random(50)*0.1+2,0,0)));
	    			ARSystem.playSound(tg, "0miss",1.8f);
	    			
	    			tg.setNoDamageTicks(0);
	    			tg.damage(s1Damage*0.1,player);
				}
			}
		}
		if(tk%20 == 0) {
			if(Rule.c.size()-1 == target.size() && target.size() > 1 && skillCooldown(0)) {
				spskillon();
				spskillen();
				ARSystem.giveBuff(player, new TimeStop(player), 140);
				ARSystem.giveBuff(player, new Nodamage(player), 80);
				sptick = 200;
				movetime = 200;
				ARSystem.playSoundAll("c161sp");
			}
		}
		for(Entity e : ARSystem.PlayerBeamBox(player, 20, 2, box.TARGET)){
			float y = Math.abs(Math.abs((e.getLocation().getYaw()-180%180) - (player.getLocation().getYaw()))-180);
			float pc = Math.abs(e.getLocation().getPitch() - (player.getLocation().getPitch()*-1));
			if(y+pc < 30 && Rule.c.containsKey(e)) {
				if(!target.contains(e)) {
					target.add((Player)e);
					player.sendTitle("",""+e.getName(),0,10,60);
				}
			}
		}
		if(movetime > 0) {
			movetime--;
			if(movenpc != null) {
				try {
					Location loc = player.getLocation().clone().add(lc);
					movenpc.faceLocation(loc.clone().add(loc.getDirection().multiply(10)));
					((Player)movenpc.getEntity()).teleport(loc);
				} catch(Exception e) {
					
				}
			}
		}
		if(movetime > 0 || sptick > 0 || show > 0) {
			l++;
			if(target.size() > 1) l++;
			if(target.size() > 4) l++;
			if(sptick > 0) l++;
			if(AMath.random(20000) <= l) {
				l = 0;
				NPC p = npcSpawn(60);
				Player tg = target.get(AMath.random(target.size())-1);
				
    			Location lc = tg.getLocation().clone();
    			lc.setYaw(AMath.random(360));
    			lc.setPitch(0);
    			(p.getEntity()).teleport(ULocal.offset(lc, new Vector(AMath.random(50)*0.1+2,0,0)));
    			if(sptick > 0) {
    				ARSystem.spellLocCast(player, ULocal.lookAt(p.getEntity().getLocation(),tg.getLocation()), "c161_s1");
    			} else {
    				ARSystem.spellLocCast(player, ULocal.lookAt(p.getEntity().getLocation(),tg.getLocation()), "c161_s1d");
    			}
    			
    			ARSystem.playSound(tg, "c161s1");
    			ARSystem.playSound(tg, "0miss",1.8f);
				p.faceLocation(tg.getLocation());
			}
		}

		if(show > 0) {
			show--;
			if(show == 0) {
				for(Player player : target) {
					player.showPlayer(this.player);
				}
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c161:sk1")+ "] : "+ s1Damage);
			scoreBoardText.add("&c ["+Main.GetText("c161:sk2")+ "] : "+ AMath.round((60+(s1Damage-10)/4*10)*0.05,2));
			scoreBoardText.add("&c ["+Main.GetText("c161:sk3")+ "] : "+ AMath.round(movetime*0.05,2));
			if(sptick > 0) scoreBoardText.add("&c ["+Main.GetText("c161:sk0")+ "] : "+ AMath.round(sptick*0.05,2));
		}

		
		return true;
	}
    
    @Override
    public void PlayerDeath(Player player, Entity e) {
    	if(player == this.player && player != e) {
    		if(movetime > 0 && target.contains(e)) {
				for(Player pl : Bukkit.getOnlinePlayers()) {
					pl.showPlayer(this.player);
				}
				int damage = this.s1Damage + 4;
				float cooldown = Math.max(1, this.setcooldown[1]-1);
	        	List<Player> targets = this.target;
	        	
    			tpsdelay(()->{
        			Rule.c.put(this.player, new c161aizen(this.player, plugin, c));
        			((c161aizen)Rule.c.get(player)).target = targets;
        			((c161aizen)Rule.c.get(player)).s1Damage = damage;
        			Rule.c.get(player).setcooldown[1] = cooldown;
        			Rule.c.get(player).cooldown[3] = setcooldown[3]*0.25f;
        			Rule.c.get(player).cooldown[0] = this.cooldown[0];
        			Rule.c.get(player).hp = this.hp;
        			player.setMaxHealth(hp);
        			player.setHealth(hp);
        			
        			Location lc = e.getLocation().clone();
        			lc.setYaw(AMath.random(360));
        			lc.setPitch(0);
        			this.player.teleport(ULocal.offset(lc, new Vector(4,0,0)));
        			ARSystem.giveBuff(((LivingEntity)e), new Stun((LivingEntity)e), 60);
        			ARSystem.giveBuff(((LivingEntity)e), new Silence((LivingEntity)e), 60);
        			ARSystem.giveBuff(this.player, new Nodamage(this.player), 60);
        			
        			int i = AMath.random(3);
        			ARSystem.playSound(this.player, "c161s3"+i);
        			ARSystem.playSound(e, "c161s3"+i);
        			if(Rule.c.get(e) != null) Rule.c.get(e).s_kill-=1;
    			},60);
    		}
    		try {
				for(NPC n : npcs) {
	    			npcs.remove(n);
	    			n.despawn();
	    			n.destroy();
	    		}
    		} catch (Exception ex) {
    			
    		}
    	}
    	if(e == this.player) {
    		ARSystem.playSound(player, "c161kill"+AMath.random(2));
    	}

		if(Rule.c.size() <= 2 && e == this.player) {
			Rule.playerinfo.get(this.player).tropy(161,1);
		}
    	if(target.contains(player)) target.remove(player);
    }
    
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		return true;
	}
	
	@Override
	public boolean skill9(){
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e).cooldown[9] > 0) is = "ok";
			}
		}
		
		if(is.equals("ok")) {
			ARSystem.playSound((Entity)player, "c161db2");
		} else {
			ARSystem.playSound((Entity)player, "c161db");
		}
		
		return true;
	}

}