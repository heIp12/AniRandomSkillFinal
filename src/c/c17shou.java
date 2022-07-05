package c;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import types.box;
import types.modes;
import util.AMath;
import util.MSUtil;
import util.Text;

public class c17shou extends c00main{
	List<Entity> getab = new ArrayList<Entity>();
	double adddamage = 1;
	int ab[] = new int[8];
	int abselect = 0;
	int abcount = 0;
	int spell[] = new int[15];
	int spellcount = 0;
	
	int ticks = 0;
	int select = 0;
	int set = -1;
	float damage = 1;
	
	int skbuff = 0;
	
	public c17shou(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 17;
		load();
		text();
		for(int i=0; i<8;i++) {
			ab[i] = -2;
		}
		abcount++;
		ab[0]= AMath.random(7);
		if(ARSystem.gameMode == modes.LOBOTOMY) sskillmult += 1;
	}
	
	@Override
	public boolean skill1() {
		if(select > 0) skill(select-1);
		return true;
	}
	
	public boolean skill(int i) {
		skill("c"+number+"_s"+i);
		if(i == 4) sk4();
		if(i == 5) sk5();
		if(i == 6) sk6();
		if(i == 7) sk7();
		return true;
	}
	int i = 0;
	
	@Override
	public boolean skill2() {
		if(spellcount == 0) {
			cooldown[2] = 0;
			return false;
		}
		i = 0;
		for(int j=0; j<spellcount;j++) {
			delay(()->{
				skill20();
			},j*6);
		}
		cooldown[2] = 6+spellcount*2;
		return true;
	}
	
	void skill20(){
		skill(spell[i]);
		i++;
	}
	
	public void Skillmsg() {
		String one = Main.GetText("main:tp"+(ab[abselect]));
		String to = "";
		
		if(set > -1) {
			to = "§cSave : "+Main.GetText("main:tp"+(set-1));
		}
		if(abcount > 1) {
			int lk = abselect+1;
			int rk = abselect-1;
			if(lk == abcount) {
				lk = 0;
			}
			if(rk == -1) {
				rk = abcount-1;
			}
			lk = ab[lk];
			rk = ab[rk];
			one = "§7"+Main.GetText("main:tp"+rk)+"§a>>" +"§f§l" + one +"§a>>§f"+ Main.GetText("main:tp"+lk);
		}
		
		player.sendTitle(one, to ,0,20,0);
	}

	public void sk4() {
		List<Entity> entitys = ARSystem.box(player, new Vector(8, 4, 8),box.TARGET);
		for(Entity e : entitys) {
			if(Rule.buffmanager.OnBuffValue((LivingEntity) e, "barrier")) {
				Rule.buffmanager.selectBuffAddValue(player, "barrier",(float) (Rule.buffmanager.GetBuffValue((LivingEntity) e, "barrier")*0.8));
				Rule.buffmanager.selectBuffValue((LivingEntity) e, "barrier",0);
			}
			if(Rule.buffmanager.OnBuffValue((LivingEntity) e, "plushp")) {
				Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) (Rule.buffmanager.GetBuffValue((LivingEntity) e, "plushp")*0.8));
				Rule.buffmanager.selectBuffValue((LivingEntity) e, "plushp",0);
			}
		}
	}
	public void sk5() {
		
		LivingEntity e = ((LivingEntity)ARSystem.boxRandom(player, new Vector(5,5,5),box.TARGET));
		if(e != null) {
			e.setNoDamageTicks(0);
			e.damage(AMath.random(0, 14),player);
			ARSystem.spellCast(player, e, "c17_s5-2");
		}
		
	}
	public void sk6() {
		List<Entity> e = ARSystem.box(player, new Vector(8, 5, 8),box.TARGET);
		for(Entity entity : e) {
			ARSystem.giveBuff((LivingEntity) entity, new Silence((LivingEntity) entity), 60);
			ARSystem.giveBuff((LivingEntity) entity, new Stun((LivingEntity) entity), 40);
		}
	}
	public void sk7() {
		ARSystem.giveBuff(player, new TimeStop(player), 100);
		delay(()->{
			List<Entity> entitys = ARSystem.box(player, new Vector(7, 5, 7),box.TARGET);
			for(Entity en : entitys) {
				((LivingEntity)en).damage(33,player);
			}
		},102);
	}
	
	@Override
	public boolean skill3() {
		if(abcount > spellcount) {
			spell[spellcount++] = select-1;
		}
		return true;
	}

	@Override
	public boolean skill4() {
		if(player.isSneaking()) {
			if(abcount > 0) {
				abselect--;
				if(abselect <= -1) {
					abselect = abcount-1;
				}
				select = (ab[abselect]+1);
				Skillmsg();
			}
		} else {
			if(abcount > 0) {
				abselect++;
				if(abselect >= abcount) {
					abselect = 0;
				}
				select = (ab[abselect]+1);
				Skillmsg();
			}
		}
		return true;
	}
	
	@Override
	public boolean skill5() {
		spellcount = 0;
		return true;
	}

	@Override
	public boolean tick() {
		if(abcount > 7) {
			abcount = 7;
		}
		if(isps) {
			damage += 0.0005f;
			skillmult += 0.001f;
		}
		scoreBoardText.add("&c ["+Main.GetText("c17:s3")+ "]&f : "+abcount);
		if(tk%20==0&& abcount > 0) {

			scoreBoardText.add("&c ["+Main.GetText("c17:s1")+ "]&f : "+Main.GetText("main:tp"+(select-1)));
			
			if(spellcount > 0) {
				String n = "";
				String n2 = "";
				for(int i=0;i<spellcount;i++) {
					if(n.length() > 15) {
						n2+=Main.GetText("main:tp"+spell[i])+">";
					}else {
						n+=Main.GetText("main:tp"+spell[i])+">";
					}
				}
				scoreBoardText.add("&c ["+Main.GetText("c17:s2")+ "]&f : "+ n);
				if(!n2.equals("")) scoreBoardText.add("&f>"+ n2);
			}
			String n = "";
			for(int i = 0; i < spellcount;i++) {
				n += Main.GetText("main:tp"+spell[i]);
			}
		}
		ticks++;

		if(ticks%10==0) {
			if(abcount > 3 && !spben) {
				if(!isps) {
					spskillen();
					spskillon();
					for(int i=1; i<ab.length;i++) {
						ab[i-1] = i;
					}
					abcount = 7;
					hp = 20;
					player.setMaxHealth(20);
					Rule.playerinfo.get(player).tropy(17,1);
				}
			}
			for(Entity e : player.getNearbyEntities(8, 8, 8)) {
				if(e instanceof Player && ! getab.contains(e)) {
					if(Rule.c.get(e) != null && ((Player)e).getGameMode() == GameMode.ADVENTURE) {
						int nb = Rule.c.get(e).getCode();
						ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 100);
						player.setMaxHealth(player.getMaxHealth()-1);
						boolean ok = true;
						int cd = Integer.parseInt(Main.GetText("c"+nb+":type").replace(" tp",""));
						
						for(int i = 0 ; i<ab.length;i++) {
							if(ab[i] == cd) {
								ok = false;
							}
						}
						
						if(ok) {
							if(abcount == 0) ARSystem.playSound(player, "c17fs");
							ab[abcount++] = cd;
						}
						
						getab.add(e);
						skill("c"+number+"_p");
					}
				}
			}
		}
		ticks%=60;
		return false;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player,"c17db"+AMath.random(4));
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		e.setDamage(e.getDamage()*damage);
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
			if(isps) {
				ARSystem.heal(player,e.getDamage()*0.25);
			}
		} else {

		}
		return true;
	}
}
