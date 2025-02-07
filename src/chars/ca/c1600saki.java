package chars.ca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import event.WinEvent;
import types.box;
import util.AMath;
import util.Map;
import util.Text;
import util.ULocal;


public class c1600saki extends c00main{
	
	public class mj {
		public String type = "";
		public int jr = 0;
		public int number = -1;
		public boolean jp = false;
		public boolean dr = false;
		public boolean lock = false;
		public boolean kang = false;
		public boolean t = true;
		public head owner = null;
		public String getText() {
			if(kang) return "§d"+type;
			if(lock) return "§b"+type;
			if(dora.contains(jr)) return "§e"+type;
			if(dr) return "§4"+type;
			return type;
		}
	}
	public class head {
		public mj[] heads = new mj[4];
		public int count = 0;
		public boolean ispair = false;
		public boolean lock = false;
		public boolean ankang = true;
		public boolean isND() {
			for(mj m : heads) {
				if(m != null) {
					if(m.number > 1 && m.number < 9) return false;
				}
			}
			return true;
		}
		public boolean isJP() {
			for(mj m : heads) {
				if(m != null) {
					if(m.number == -1) return false;
				}
			}
			return true;
		}
		public boolean isCt() {
			boolean ct = false;
			for(mj m : heads) {
				if(m != null) {
					if(m.number <= 1 && m.number >= 9) ct = true;
				}
			}
			return ct;
		}
	}
	
	public enum mjtype{
		NONE,CHI,PONG,KANG,HEAD
	}
	
	class headComparator implements Comparator<head> {
	    @Override    
	    public int compare(head f1, head f2) {
	    	if (f1.heads[0].jr > f2.heads[0].jr) {
	    		return 1;        
	    	} else {
	    		return -1;
	    	}
	    }
	}
	class mjComparator implements Comparator<mj> {
	    @Override    
	    public int compare(mj f1, mj f2) {
	    	if (f1.jr > f2.jr) {
	    		return 1;        
	    	} else {
	    		return -1;
	    	}
	    }
	}
	String set[] = {"①","②","③","④","⑤","⑥","⑦","⑧","⑨","一","九","東","南","西","北","白","發","中"};
	List<mj> dack = new ArrayList<>();
	List<head> mydeck = new ArrayList<head>();
	mj hand[] = new mj[11];
	List<String> pb;
	List<Integer> dora = new ArrayList<>();
	int loc = 0;
	boolean menjen = true;
	mj drow;
	mj drop;
	mj last;
	mj lastdrop;
	int jnp = 10 + AMath.random(4);
	int jpp = 10 + AMath.random(4);
	int kang = 0;
	int stack = 0;
	int hmscore;
	int dacksize = 0;
	public c1600saki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1016;
		load();
		text();
		reset();
	}
	
	@Override
	public void setStack(float f) {
		stack = (int)f;// TODO Auto-generated method stub
		super.setStack(f);
	}
	
	public void reset() {
		menjen = true;
		dora.clear();
		dack.clear();
		mydeck.clear();
		
		for(int i=0; i<18;i++) {
			for(int j=0;j<4;j++) {
				mj p = new mj();
				p.type = set[i];
				p.jr = i;
				if(i == 9 ||i == 10) p.t = false;
				if(i == 0 || i == 8) if(j==2) j++;
				if(i > 14) if(j==2) j++;
				if(i <= 0 || 8 <= i) p.jp = true;
				if(i < 9) p.number = (i+1);
				if(i == 4 && j == 0) p.dr = true;
				else if(i == 9) p.number = 1;
				else if(i == 10) p.number = 9;
				dack.add(p);
			}
		}
		last = null;
		
		if(stack == 52) {
			try {
			char[] drow = Text.get("c1016:set").toCharArray();
			for(int i = 0; i< hand.length; i++) {
				int j = 0;
				for(mj m : dack) {
					if(m.type.equals(""+drow[i])) {
						break;
					}
					j++;
				}
				if(j >= dack.size()) j = dack.size()-1;
				mj p = dack.get(j);
				hand[i] = p;
				dack.remove(p);
				last = p;
			}
			} catch (Exception e) {
				System.out.println("auto ichihime Error : " + e);
				stack = 0;
				reset();
			}
		} else {
			for(int i=0;i<hand.length;i++) {
				mj p = dack.get(AMath.random(0,dack.size()-1));
				hand[i] = p;
				dack.remove(p);
				last = p;
			}
		}
		mj p = dack.get(AMath.random(0,dack.size()-1));
		drow = p;
		dack.remove(p);
		if(dack.size() > 0) {
			p = dack.get(AMath.random(0,dack.size()-1));
			drop = p;
			dack.remove(p);
		}
		dacksize = dack.size();
		delay(()->{dora.add(AMath.random(set.length)-1);},2);
		delay(()->{jpp = 10 + AMath.random(4);},0);
		rep();
	}
	
	public void luck() {
		dack.clear();
		delay(()->{
			dora.clear();
			dora.add(2);
			dora.add(3);
			dora.add(7);
			dora.add(7);

			dack.clear();
			int i = 4;
			for(int j=0;j<1;j++) {
				mj p = new mj();
				p.type = set[i];
				p.jr = i;
				if(i == 9 ||i == 10) p.t = false;
				if(i == 0 || i == 8) if(j==2) j++;
				if(i > 14) if(j==2) j++;
				if(i <= 0 || 8 <= i) p.jp = true;
				if(i < 9) p.number = (i+1);
				dack.add(p);
				drow = p;
			}
			
		},300);
		dack.clear();
		
		mj p = new mj();
		p.type = set[4];
		p.jr = 4;
		p.dr = true;
		drow = p;
		p.number = 5;
		dack.add(p);
		
		int hd = 0;
		int i = 2;
		for(int j=0;j<4;j++) {
			p = new mj();
			p.type = set[i];
			p.jr = i;
			if(i <= 0 || 8 <= i) p.jp = true;
			if(i < 9) p.number = (i+1);
			hand[hd++] = p;
		}
		i = 3;
		for(int j=0;j<4;j++) {
			p = new mj();
			p.type = set[i];
			p.jr = i;
			if(i <= 0 || 8 <= i) p.jp = true;
			if(i < 9) p.number = (i+1);
			hand[hd++] = p;
		}
		i = 7;
		for(int j=0;j<6;j++) {
			p = new mj();
			p.type = set[i];
			p.jr = i;
			if(i <= 0 || 8 <= i) p.jp = true;
			if(i < 9) p.number = (i+1);
			if(j < 3) {
				hand[hd++] = p;
			} else {
				dack.add(p);
			}
		}
		i = 12;
		for(int j=0;j<4;j++) {
			 p = new mj();
			p.type = set[i];
			p.jr = i;
			if(i == 9 ||i == 10) p.t = false;
			if(i == 0 || i == 8) if(j==2) j++;
			if(i > 14) if(j==2) j++;
			if(i <= 0 || 8 <= i) p.jp = true;
			if(i < 9) p.number = (i+1);
			if(i == 4 && j == 1) p.dr = true;
			else if(i == 9) p.number = 1;
			else if(i == 10) p.number = 9;
			dack.add(p);
		}

	}
	
	void sp(int num) {
		if(stack != 52 && stack != 0 && stack != 7) num = stack;
		List<String> jr1 = new ArrayList<String>();
		List<String> jr2 = new ArrayList<String>();
		for(String s : pb) {
			if(!s.contains(":")) {
				int n = Integer.parseInt(s.substring(1, s.length()));
				if(n > 29) {
					jr1.add(s);
				} else {
					jr2.add(s);
				}
			}
		}
		pb.clear();
		for(String s : jr1) pb.add(s);
		for(String s : jr2) pb.add(s);
		
		Map.getMapinfo(1011);
		Location loc = Map.getCenter();
		loc.setY(4);
		loc.setZ(-29.5);
		loc.setPitch(0);
		player.teleport(loc);
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(Rule.c.get(p) != null && p != player) Rule.c.put(p, new c000humen(p, plugin, null));
			if(Rule.c.get(p) != null) ARSystem.giveBuff(p, new TimeStop(p), 500);
			if(p != player) p.teleport(ULocal.lookAt(ULocal.offset(loc, new Vector(7,0,0)),loc));
			for(Player pl : Bukkit.getOnlinePlayers()) {
				if(p == player || pl == player) continue;
				p.hidePlayer(pl);
			}
		}
		ARSystem.giveBuff(player, new TimeStop(player), 500);
		int type = 1;
		if(num == 30) type = 2;
		if(num == 31) type = 3;
		if(num == 32) type = 4;
		if(num == 33) type = 5;
		int t = type;
		
		for(int i = 0; i<10;i++) {
			ARSystem.spellLocCast(player, loc.clone().add(4-(i*0.75),-1,1), "c1016_"+(hand[i].jr+1));
		}
		delay(()->{
			skill("c1016_d");
			delay(()->{
				ARSystem.spellLocCast(player, loc.clone().add(-4,1.2,0.9), "c1016_"+(hand[10].jr+1));
				delay(()->{
					ARSystem.spellLocCast(player, loc.clone().add(-4,1,1), "c1016_m");
				},1);
			},5);
		},1);

		delay(()->{
			for(Player p : Bukkit.getOnlinePlayers()) {
				ARSystem.spellCast(player, p, "c1016_e4");
			}
			delay(()->{
				ARSystem.spellLocCast(player, loc.clone().add(0,0,-1.4), "c1016_bg"+t);
				if(t == 1) {
					for(int i = 0; i<11;i++) {
						Location lc =  loc.clone().add(4-(i*0.75),0,0);
						lc.setYaw(lc.getYaw()+180);
						ARSystem.spellLocCast(player,lc, "c1016_"+(hand[i].jr+1));
					}
					player.teleport(loc.clone().add(0,1.5,-1));
				}
				if(t == 2) {
					for(int i = 0; i<11;i++) {
						Location lc =  loc.clone().add(3-(i*0.75),1.8-i*0.1,0);
						lc.setYaw(lc.getYaw()+175 + i);
						ARSystem.spellLocCast(player,lc, "c1016_"+(hand[i].jr+1));
					}
					player.teleport(loc.clone().add(0,0.4,-1));
				}
				if(t == 3) {
					for(int i = 0; i<11;i++) {
						Location lc = loc.clone().add(3.8-(i*0.75),0, 0);
						if(i > 6) {
							lc = lc.add(0,(i*0.05) - 1,0);
						} else {
							lc = lc.add(0,-0.05 * i -0.4,0);
						}
						lc.setYaw(lc.getYaw()+170 + i*2);
						lc.setPitch(-5);
						ARSystem.spellLocCast(player,lc, "c1016_"+(hand[i].jr+1));
					}
					player.teleport(loc.clone().add(0,1.5,-1));
				}
				if(t == 4) {
					for(int i = 0; i<11;i++) {
						Location lc = loc.clone().add(2.7-(i*0.55),5-i*0.5,-0.5+i*0.1);
						lc.setYaw(lc.getYaw()+180);
						ARSystem.spellLocCast(player,lc, "c1016_"+(hand[i].jr+1));
					}
					delay(()->{
						skill("c1016_r");
					},2);
					player.teleport(loc.clone().add(0,1.2,-1));
				}
				if(t == 5) {
					for(int i = 0; i<11;i++) {
						Location lc =  loc.clone().add(4-(i*0.75),0,0);
						lc.setYaw(lc.getYaw()+180);
						ARSystem.spellLocCast(player,lc, "c1016_"+(hand[i].jr+1));
					}
					player.teleport(loc.clone().add(0,1.5,-1));
				}
				delay(()->{
				int l = 1;
				ARSystem.playSoundAll("c1016sp5");	
				for(String s : pb) {
						if(!s.contains(":")) {
							int n = Integer.parseInt(s.substring(1, s.length()));
							delay(()->{
								Title("§a§l『역만』", "§c§l《§6§l"+Main.GetText("c1016:t"+n)+"§c§l》§f",0,40,0);
								ARSystem.playSoundAll("c1016p"+n);
							},(40*l++));
						}
					}
				},80);
			},10);
		},220);

		delay(()->{
			ARSystem.playSoundAll("c1016end3");
			int p = 1;
			for(String s : pb) {
				if(!s.contains(":")) {
					int n = Integer.parseInt(s.substring(1, s.length()));
					if(n > 29) p += 201;
					else if(n > 24) p += 111;
				}
			}
			int j = 7;
			if(p > 300) {
				Title("§c§l『§e§l트리플 역만§c§l』","",0,40,0);
				j = 8;
			} else {
				Title("§e§l『§f§l더블 역만§e§l』","",0,40,0);
			}
			
			int i = j;
			delay(()->{
				ARSystem.playSoundAll("c1016c"+i);
				sendDragon(i);
				delay(()->{
					Skill.win(player);
					tpsdelay(()->{
						ARSystem.playSoundAll("c1016win");
					},40);
				},40);
			},20);
		},400);
		
	}
	
	void rep() {
		for(int i = 0; i< 11; i++) {
			for(int j = i; j < 11; j++) {
				if(hand[i].jr > hand[j].jr) {
					mj h = hand[i];
					hand[i] = hand[j];
					hand[j] = h;
				}
			}
		}
		
		List<mj> select = new ArrayList<>();
		int selc = 0;
		mydeck = new ArrayList<head>();
		
		int seln = 0;
		while(selc < 11 && seln < 11) {
			if(!select.contains(hand[seln])) {
				int type = hand[seln].jr;
				if(hand[seln].lock && hand[seln].owner != null) {
					head hd = hand[seln].owner;
					selc += Math.min(3,hd.count);
					String s = "";
					for(mj m : hd.heads) {
						if(m != null) {
							s+= m.getText();
							select.add(m);
						}
					}
					mydeck.add(hd);
					seln++;
					continue;
				}
				
				if(seln < 10 && !hand[seln].lock) {
					head h = new head();
					selc++;
					select.add(hand[seln]);
					h.heads[h.count++] = hand[seln];
					hand[seln].owner = h;
					
					//123
					int loc1=-1,loc2=-1;
					if(hand[seln].jr < 7) {
						for(int i = seln+1; i < 11; i++) {
							if(!select.contains(hand[i]) && !hand[i].lock) {
								if(loc1 == -1 && hand[i].number == hand[seln].number+1) {
									loc1 = i;
								}
								else if(loc2 == -1 && hand[i].number == hand[seln].number+2) {
									loc2 = i;
								}
							}
						}
					}
					if(loc1 != -1 && loc2 != -1) {
						selc+=2;
						h.heads[h.count++] = hand[loc1];
						select.add(hand[loc1]);
						hand[loc1].owner = h;
						h.heads[h.count++] = hand[loc2];
						select.add(hand[loc2]);
						hand[loc2].owner = h;
					} else {
						//111
						for(int i = seln+1; i < 11; i++) {
							if(hand[i].jr == type && !hand[i].lock) {
								selc++;
								select.add(hand[i]);
								h.heads[h.count++] = hand[i];
								h.ispair = true;
							}
						}
					}
					mydeck.add(h);
				} else if(!select.contains(hand[10])){
					selc++;
					select.add(hand[10]);
					if(!hand[10].lock) {
						head h = new head();
						h.count = 1;
						h.heads[0] = hand[10];
						mydeck.add(h);
						hand[10].owner = h;
					}
				}
			}
			seln++;
		}
		Collections.sort(mydeck, new headComparator());
	}
	
	mjtype isCheck(mj p, int loc,boolean jg) {
		int jr = p.jr;
		int loc1=-1, loc2=-1;
		
		if(jr < 9) {
			int a[][] = {{1,2},{-2,-1},{-1,1}};
			for(int j = 0; j < a.length; j++) {
				for(int i = 0; i < 11; i++) {
					if(i == loc || hand[i].jr > 9 || hand[i].jr < 0) continue;
					
					int tj = hand[i].jr;
					if(!hand[i].lock && hand[i].t == p.t) {
						if(loc1 == -1 && tj == jr-a[j][0]) {
							loc1 = i;
						}
						else if(loc2 == -1 && tj == jr-a[j][1]) {
							loc2 = i;
						}
					}
				}
				if(loc1 != -1 && loc2 != -1) {
					j = 999;
					break;
				} else {
					loc1=loc2=-1;
				}
			}
		}
		
		boolean islock = false;
		int loc5 =-1, loc6 = -1, loc7 = -1;
		for(int i = 0; i < 11; i++) {
			if(loc == i) continue;
			if(hand[i].jr == p.jr) {
				if(hand[i].lock) islock = true;
				if(loc5 == -1) loc5 = i;
				else if(loc6 == -1) loc6 = i;
				else if(loc7 == -1) loc7 = i;
				else break;
			}
		}
		if(loc7 != -1) {
			if(!islock || (hand[loc5].owner == hand[loc6].owner && hand[loc6].owner == hand[loc7].owner)) {
				if(jg) {
					hand[loc5].kang = true;
					hand[loc6].kang = true;
					hand[loc7].kang = true;
					p.kang = true;
					setLock(hand[loc5],hand[loc6],hand[loc7],p);
					hand[loc5].owner.count = 0;
					hand[loc5].owner.ankang = false;
					head h = hand[loc5].owner;
					h.heads[hand[loc5].owner.count++] = hand[loc5];
					h.heads[hand[loc5].owner.count++] = hand[loc6];
					h.heads[hand[loc5].owner.count++] = hand[loc7];
					h.heads[hand[loc5].owner.count++] = p;
	
					if(dora.size() < 4) {
						dora.add(AMath.random(set.length)-1);
						ARSystem.playSound((Entity)player, "c1016pk");
						delay(()->{
							ARSystem.playSound((Entity)player, "c1016dora");
						},20);
					}
					hand[loc7] = drow;
					rep();
					handTitle();
				}
				return mjtype.KANG; // 깡
			}
		}
		if(loc6 != -1 && !islock) {
			if(jg) {
				setLock(hand[loc5],hand[loc6],p);
			}
			return mjtype.PONG; // 퐁
		}
		if(loc1 != -1 && loc2 != -1) {
			if(jg) {
				setLock(hand[loc1],hand[loc2],p);
			}
			return mjtype.CHI; // 치
		}
		if(loc5 != -1) return mjtype.HEAD; // 머리 론
		return mjtype.NONE;
	}
	
	boolean isKang(mj p, int loc) {
		int loc5 =-1, loc6 = -1, loc7 = -1;
		for(int i = 0; i < 11; i++) {
			if(loc == i) continue;
			if(hand[i].jr == p.jr && !hand[i].kang) {
				if(loc5 == -1) loc5 = i;
				else if(loc6 == -1) loc6 = i;
				else if(loc7 == -1) loc7 = i;
				else break;
			}
		}
		if(loc7 != -1) {
			hand[loc5].kang = true;
			hand[loc6].kang = true;
			hand[loc7].kang = true;
			p.kang = true;
			setLock(hand[loc5],hand[loc6],p,hand[loc7]);

			
			if(dora.size() < 4) {
				dora.add(AMath.random(set.length)-1);
				delay(()->{
					ARSystem.playSound((Entity)player, "c1016dora");
				},20);
			}
			ARSystem.playSound((Entity)player, "c1016pk");

			p = dack.get(AMath.random(dack.size())-1);
			drow = p;
			last = drow;
			hand[loc7] = drow;
			dack.remove(p);
			rep();
			handTitle();
			return true;
		}
		return false;
	}
	
	void setLock(mj... m) {
		List<mj> mj = new ArrayList<mj>();
		for(mj mmj : m) {
			mj.add(mmj);
		}
		Collections.sort(mj, new mjComparator());
		m[0].owner.count = 0;
		m[0].owner.lock = true;
		for(mj mz : mj) {
			mz.lock = true;
			mz.owner = m[0].owner;
			m[0].owner.heads[m[0].owner.count++] = mz;
		}
	}
	
	void handTitle() {
		String t1 = "없음";
		if(drow != null) t1 = drow.getText();
		String t2 = "없음";
		if(drop != null) t2 = drop.getText();
		String s = "§a쯔모<§7" + t1 +"§a> §f§l["+dack.size()+"] §c<§7"+t2+"§c> 버림패";
		
		
		String ss = "§7";
		int i = 0;
		for(mj h : hand) {
			if(i == loc) ss +="§f§l[§f";
			ss+= "§7"+h.getText();
			if(i == loc) ss +="§f§l]§7";
			i++;
		}
		player.sendTitle(s, ss,0,600,0);
	}
	
	
	@Override
	public boolean skill1() {
		if(hand[loc].lock) {
			handTitle();
			return false;
		}
		kang++;
		if(player.isSneaking()) {
			mjtype type = isCheck(drop, loc, true);
			
			if(type != mjtype.NONE && type != mjtype.HEAD) {
				if(type == mjtype.CHI) ARSystem.playSound((Entity)player, "c1016pc");
				else if(type == mjtype.PONG)ARSystem.playSound((Entity)player, "c1016pp");
				else if(type == mjtype.KANG) {
					kang = 1;
					ARSystem.playSound((Entity)player, "c1016pk");
				}
				lastdrop = hand[loc];
				hand[loc] = drop;
				last = drop;
				menjen = false;
			} else {
				handTitle();
				return false;
			}
		} else {
			lastdrop = hand[loc];
			hand[loc] = drow;
			last = drow;
		}
		if(dack.size() == 0 && drop == null && drow == null) {
			ARSystem.playSound((Entity)player, "c1016get");
			ARSystem.playSound((Entity)player, "c1016nt");
			reset();
			handTitle();
		} else {
			mj jm = hand[loc];
			if(dack.size() > 0) {
				mj p = dack.get(AMath.random(dack.size())-1);
				if(AMath.random(10) <= 2) {
					for(mj m : dack) {
						if(lastdrop != m && lastdrop.jr == m.jr) p = m;
					}
				}
				drow = p;
				dack.remove(drow);
			} else {
				drow = null;
			}
			
			if(dack.size() > 0) {
				drop = dack.get(AMath.random(dack.size())-1);
				dack.remove(drop);
			} else {
				drop = null;
			}
			rep();
			for(int i = 0; i < 11; i++) {
				if(hand[i] == jm) loc = i;
			}
			handTitle();
		}
		ARSystem.playSound((Entity)player, "c16get");
		return true;
	}
		
	@Override
	public boolean skill2() {
		if(!player.isSneaking()) {
			loc++;
			if(loc >= hand.length) loc = 0;
			while(hand[loc].lock) {
				loc++;
				if(loc >= hand.length) loc = 0;
			}
		} else {
			loc--;
			if(loc < 0) loc = hand.length-1;
			while(hand[loc].lock) {
				loc--;
				if(loc < 0) loc = hand.length-1;
			}
		}
		rep();
		handTitle();
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(!player.isSneaking()) {
			String s = "";
			for(head h : mydeck) {
				for(int i = 0; i < h.count;i++) {
					if(h.ispair) {
						if(h.count == 2) s+="§l";
						if(h.count == 3) s+="§o";
						if(h.count == 4) s+="§o";
					} else {
						if(h.count == 3) s+="§n";
						else s+= "§7";
					}
					s+= h.heads[i].type;
				}
				s+="§7 ";
			}
			player.sendTitle("", s,0,600,0);
		} else {
			if(isKang(hand[loc], loc)) {
				kang = 1;
			}
		}
		return true;
	}
	
	List<Entity> targets;
	
	void Title(String a, String b, int i , int j, int k) {
		player.sendTitle(a, b, i, j, k);
		ARSystem.giveBuff(player, new TimeStop(player), 40);
		for(Entity e : targets) {
			if(e instanceof Player) {
				Player p = (Player)e;
				p.sendTitle(a, b, i, j, k);
				ARSystem.giveBuff(p, new TimeStop(p), 40);
			}
		}
		for(Player e : Bukkit.getOnlinePlayers()) {
			if(!targets.contains(e) && e.getLocation().distance(player.getLocation()) <= 18) {
				Player p = (Player)e;
				p.sendTitle(a, b, i, j, k);
			}
		}
	}
	float pdamage = 0.005f;
	@Override
	public boolean skill4() {
		if(player.isSneaking()) {
			reset();
			ARSystem.playSound((Entity)player, "c1016get");
			ARSystem.playSound((Entity)player, "c1016nt");
			cooldown[4] = (float) (8 * (skillmult+sskillmult));
			handTitle();
		} else {
			String key = "";
			String ptype = "";
			for(mj h : hand) key += h.getText()+"§f";
	
			pb = score(hand[loc], loc);
			
			targets = new ArrayList<Entity>();
			for(Entity e : ARSystem.box(player, new Vector(10,8,10), box.TARGET)) {
				if(e instanceof Entity) targets.add(e);
			}

			boolean ym = false;
			int p = 1;
			List<Integer> yk = new ArrayList<>();
			for(String s : pb) {
				if(!s.contains(":")) {
					int n = Integer.parseInt(s.substring(1, s.length()));
					if(n >= 25) {
						ym = true;
						if(n > 29) p += 201;
						else if(n > 24) p += 111;
						yk.add(n);
					}
				}
			}
			
			if(pb.size() <= 0) player.sendTitle("족보없음", "");
			if(p < 200 && targets.size() <= 0) player.sendTitle("대상없음", "");
			if(p > 200 || (pb.size() > 0 && targets.size() > 0)) {
				cooldown[4] = 100;
				
				int score = 0;
				String ss = "§a" + player.getName() +" : ";
				
				int time = 1;
				String tpp = "";
				if(targets.size() <= 1) {
					tpp = "론";
					sound("c1016prun");
				} else {
					tpp = "츠모";
					sound("c1016ptumo");
				}
				String tp = tpp;
				

				if(p >= 200) {
					targets.clear();
					
					WinEvent event = new WinEvent(player);
					Bukkit.getPluginManager().callEvent(event);
					if(!event.isCancelled()) {
						for(Entity e : Bukkit.getOnlinePlayers()) {
							if(e != player) targets.add(e);
						}
						if(p > 300) {
							spskillen("트리플 역만");
						} else {
							spskillen();
						}
						spskillon();
						if(yk.contains(33)) {
							sp(33);
						} else if(yk.contains(32)) {
							sp(32);
						} else if(yk.contains(31)) {
							sp(31);
						} else if(yk.contains(30)) {
							sp(30);
						} else if(yk.contains(25)) {
							sp(25);
						}
					}
				} else {
					int delay = 0;
					if(ym) {
						Title(tp, "§c[§f"+key+"§c]§f",5,40,0);
						delay = 40;
						String k = "";
						for(int i = 0; i < hand.length; i++) {
							k += hand[i].getText()+"§f";
							String t = k;
							delay(()->{
								ARSystem.playSound(player, "0explod", 1 , 0.5f);
								Title(tp, "§c[§f"+t+"§c]§f",2,20,0);
							},10+(3*i));
						}
					} else {
						Title(tp, "§c[§f"+key+"§c]§f",10,60,0);
					}
					
					for(String s : pb) {
						if(s.contains(":")) {
							String i = s.split(":")[0];
							int n = Integer.parseInt(s.split(":")[1]);
							if(i.equals("t3") || i.equals("t4")) {
								score += 1;
								int scores = score;
								delay(()->{
									Title(scores+" 판", "§c§l《§6§l1§c§l》§f"+Main.GetText("c1016:"+i),10,40,0);
									sound("c1016n"+n);
								},20*(time++) + delay);
								ptype+= "[1]"+Main.GetText("c1016:"+i)+", ";
								
							} else if(i.equals("t12") || i.equals("t13")) {
								score += n;
								int scores = score;
								delay(()->{
									Title(scores+" 판", "§c§l《§6§l"+n+"§c§l》§f"+Main.GetText("c1016:"+i),10,40,0);
									if(n > 11) {
										sound("c1016pdr11");
									} else {
										sound("c1016pdr"+n);
									}
								},20*(time++) + delay);
								ptype+= "["+n+"]"+Main.GetText("c1016:"+i)+", ";
							} else {
								score += 1;
								int scores = score;
								delay(()->{
									Title(scores+" 판", "§c§l《§6§l"+n+"§c§l》§f"+Main.GetText("c1016:"+i),01,40,0);
									sound("c1016p"+1);
								},20*(time++) + delay);
								ptype+= "["+n+"]"+Main.GetText("c1016:"+i)+", ";
							}
							
						} else {
							p = 1;
							int n = Integer.parseInt(s.substring(1, s.length()));
	
							if(n == 1) hpCost(10, false);
							if(n > 13) p++;
							if(n > 21) p++;
							if(n > 22) p+=3;
							if(n > 24) p += 111;
							
							if(n == 14 || n == 19 || n == 22 || n == 23) if(!menjen) p--;
							String text  = ""+p;
							if(p >= 100) text = "§4§lY§a";
							String msg = text;
							score += p;
							int damage = score;
							delay(()->{
								Title(damage+" 판", "§c§l《§6§l"+msg+"§c§l》§f"+Main.GetText("c1016:"+s),10,40,0);
								sound( "c1016p"+n);
							},20*(time++) + delay);
							ptype+= "["+p+"]"+Main.GetText("c1016:"+s)+", ";
						}
					}
					
					int damage = 0;
					int type = 0;
					hmscore = score;
					if(score > 0) {
						if(score <= 4) {
							pdamage = 0.0025f;
						} else {
							pdamage = 0.005f;
						}
						if(score <= 4) damage = getScore(score);
						else if(score <= 5) {
							damage = getScore(5);
							type = 1;
						} else if(score <= 7) {
							damage = getScore(6);
							type = 2;
						} else if(score <= 10) {
							damage = getScore(7);
							type = 3;
						} else if(score <= 12) {
							damage = getScore(8);
							type = 4;
						} else if(score < 100) {
							Rule.playerinfo.get(player).tropy(16, 4);
							damage = getScore(9);
							type = 5;
						} else if(score >= 100 && score <= 200){
							damage = getScore(9);
							type = 6;
						} else if(score > 200  && score <= 300) {
							damage = getScore(10);
							type = 7;
						} else if(score > 200) {
							damage = getScore(11);
							type = 8;
						}

						int typ = type;
						int dg = damage;
						int sc = score;
						String pty = ptype;
						
						delay(()->{
							sendDragon(typ);

							if(dg < 8000) {
								Title(dg+" 점","" + " ",20,80,0);
								delay(()->{Title(sc+" 판 §f§l|§f "+dg+" 점",pty,0,80,0);},5);
							} else {
								delay(()->{Title(sc+" 판 §f§l|§f "+dg+" 점",pty,0,80,0);},5);
							}
							if(dg > 32000) {
								sound("c1016end3");
							} else if(dg >= 16000) {
								sound("c1016end2");
							} else if(dg > 8000) {
								sound("c1016end1");
							}
							delay(()->{
								sound("c1016c"+typ);
							},20);
						},20*(time++)+20 + delay);
						
						String t = "";
						if(type > 0) {
							t = Main.GetText("c1016:s"+type);
						} else {
							t = "§7"+score+"판";
						}
						String ty = "§a§l【"+t+"§a§l】";
						
						
		
						if(type <= 4) {
							delay(()->{
								Title("§6데미지 : §a" + dg, ty,0,80,0);
								delay(()->{
									Title("§6데미지 : §2§k" + dg, ty,0,80,0);
									delay(()->{
										Title("§6데미지 : §3§k" + (dg/10), ty,0,80,0);
										delay(()->{
											Title("§6데미지 : §5§k" + (dg/100), ty,0,80,0);
											delay(()->{
												Title("§6데미지 : §k" + AMath.round(dg*pdamage,2), ty,0,80,0);
												delay(()->{
													Title("§6데미지 : §c§l" + AMath.round(dg*pdamage,2), ty,0,80,0);
												},3);
											},3);
										},3);
									},3);
								},3);
							},20*(time++)+20 + delay);
						} else {
							delay(()->{
								Title("§6데미지 : §a" + dg, ty,0,80,0);
								delay(()->{
									Title("§6데미지 : §2§k" + dg, ty,0,80,0);
									delay(()->{
										Title("§6데미지 : §3§k" + (dg/10), ty,0,80,0);
										delay(()->{
											Title("§6데미지 : §5§k" + (dg/100), ty,0,80,0);
											delay(()->{
												Title("§6데미지 : §4§l§n측정불가", ty,0,80,0);
											},3);
										},3);
									},3);
								},3);
							},20*(time++)+20 + delay);
						}
					}
					float dg = damage;
					delay(()->{
						if(dg >= 32000) {
							for(Entity e : targets) {
								s_damage += 100;
								Rule.buffmanager.selectBuffAddValue(player, "plushp", 120/targets.size());
								Skill.quit((LivingEntity)e);
							}
							
						} else {
							float d = (float)(dg*pdamage/targets.size());
							for(Entity e : targets) {
								LivingEntity target = (LivingEntity)e;
								
								if(!ARSystem.fixedDamage(target, player, d).isCancelled()) {
									Rule.buffmanager.selectBuffAddValue(player, "plushp", d*0.3f);
								}
							}
						}
						reset();
						ARSystem.playSound((Entity)player, "c1016get");
						cooldown[4] = 0;
					},20*(time++)+20 + delay);
				}
			}
		}
		return true;
	}
	
	void sendDragon(int type) {
		String t = "";
		if(type > 0) t = Main.GetText("c1016:s"+type);
		else t = "§7"+hmscore+"판";
		
		String ty = "§a§l【"+t+"§a§l】";
		int ttp = type;
		int dg = 0;
		
		String k = "";
		for(mj h : hand) k += h.getText()+"§f";
		String pt = "";
		
		int score = 0;
		for(String s : pb) {
			if(s.contains(":")) {
				String i = s.split(":")[0];
				int n = Integer.parseInt(s.split(":")[1]);
				if(i.equals("t3") || i.equals("t4")) {
					score += 1;
					pt += "[1]"+Main.GetText("c1016:"+i)+", ";
					
				} else if(i.equals("t12") || i.equals("t13")) {
					score += n;
					pt += "["+n+"]"+Main.GetText("c1016:"+i)+", ";
				} else {
					score += 1;
					pt+= "["+n+"]"+Main.GetText("c1016:"+i)+", ";
				}
				
			} else {
				int p = 1;
				int n = Integer.parseInt(s.substring(1, s.length()));

				if(n == 1) hpCost(10, false);
				if(n > 13) p++;
				if(n > 21) p++;
				if(n > 22) p+=3;
				if(n > 24) p += 111;
				
				if(n == 14 || n == 19 || n == 22 || n == 23) if(!menjen) p--;
				String text  = ""+p;
				if(p >= 100) text = "§4§lY§a";
				score += p;
				pt+= "["+p+"]"+Main.GetText("c1016:"+s)+", ";
			}
		}

		if(score <= 4) {
			dg = getScore(score);
		} else if(type >= 6) {
			dg = getScore(type+3);
		} else {
			dg = getScore(type+4);
		}
		
		int sce = score;
		
		String s = "§7";
		for(head h : mydeck) {
			for(mj m : h.heads) {
				if(m != null) {
					s+= m.getText()+"§7";
				}
			}
			s+= " ";
		}

		String dr = "";
		for(int i :dora) dr+= set[i]+ " ";
		String dmg = ""+AMath.round(dg*pdamage,2);
		if(dg >= 32000) dmg = "§c§l내쫒김";
		if(dg >= 64000) dmg = "§d§l승리함";
		String lt = "없음";
		if(last != null) last.getText();
		String msgs = "§a§l[§f시전자§a§l] §f"+player.getName() + " \n" +
				"§4§l[§c데미지§4§l]§f "+ dmg + " \n" +
				"§a§l[§f§l점수§a§l]§f "+ dg + ty + " \n" +
				"§9§l[§7§l장풍/자풍§9§l]§f "+ set[jnp] +" / "+ set[jpp] + "  §6§l[§7§l도라§6§l]§f "+ dr +" \n" +
				"§2§l[§f손§2§l]§f "+ k + "  §4§l[§e화료패§4§l]§f "+ lt +" \n" +
				"§2§l[§7분할§2§l]§f "+ s + " \n" +
				"§e§l[§f족보§e§l]§f "+ pt + " \n" +
				"§c§l[§7멘젠§c§l]§4 "+ menjen + " \n" +
				"§4§l[§c뽑은패수§4§l]§f "+ (dacksize-dack.size());
		if(sce > 12 && sce < 100) {
			if(Rule.Var.Load(player.getName()+".info.himeY") == null) {
				Rule.Var.Save(player.getName()+".info.himeY",0);
			}
			
			int n = Rule.Var.Loadint(player.getName()+".info.himeY");
			if(sce >= 38) {
				Rule.playerinfo.get(player).tropy(16, 8);
			}
			if(n <= sce) {
				Rule.Var.Save(player.getName()+".info.himeY",sce);
				Rule.Var.Save(player.getName()+".info.himeP",k +"§a§l【§7§l"+sce+"판§a§l】"+ ty);
				Rule.Var.Save(player.getName()+".info.himeD",msgs);
			}
		}
		System.out.println("§e§l★】§a§n"+player.getName()+ "§e§l]>§f §c§f"+k+"§c§f" + ty);
		for(Player pl : Bukkit.getOnlinePlayers()) pl.spigot().sendMessage(Text.hover(player,"§e§l※§a§n"+player.getName()+ "§e§l]>§f §f"+k+"§f" + ty, msgs));
	}
	
	int getScore(int i) {
		return Integer.parseInt(Main.GetText("c1016:score"+i));
	}
	
	
	void sound(String s){
		ARSystem.playSound((Entity)player, s);
		for(Entity e : targets) {
			if(e instanceof Player) {
				Player p = (Player)e;
				ARSystem.playSound(p, s);
			}
		}
	}
	
	List<String> score(mj p, int loc) {
		List<String> scores = new ArrayList<>();
		if(mydeck.size() <= 3) return scores;
		
		List<String> ym = new ArrayList<>();
		int i = 0, sj = 0, kj = 0;
		if(menjen) scores.add("t1");
		int jdr = 0, dr = 0;
		boolean ssw = false;
		boolean tnd = true,hnd = true,jct = true,ct = true;
		boolean tyo = true;
		int kan = 0;
		boolean ptype[] = {false,false,false,false}; //통패 만패 풍패 삼원패
		boolean[] it = new boolean[9];
		int rot = 0;
		String gs = "①⑨一九東南西北白發中";
		String gr = "①①②③④⑤⑥⑦⑧⑨⑨";
		for(mj m : hand) {
			if(gr.length() > 0 && m.type.equals(gr.substring(0,1))) {
				gr = gr.substring(1,gr.length());
			}
			if(gs.length() > 0 && m.type.equals(gs.substring(0,1))) {
				gs = gs.substring(1,gs.length());
			}
		}
		boolean stop = false;
		
		for(head h : mydeck) {
			if(h.heads[0].jr > 10 && h.heads[0].jr <= 14 && h.count >= 2) rot++;
			if(h.count < 2) stop = true;
			if(h.count < 3) {
				i++;
				if(i > 2) {
					stop = true;
				}
				if(h.heads[0].jr >= 15 && h.heads[0].jr <= 17) ssw = true;
			}
			if(h.count >= 3 && h.ispair) {
				if(h.heads[0].kang) kan++;
				if(h.heads[0].jr == jnp) scores.add("t3:"+(jnp-10));
				if(h.heads[0].jr == jpp) scores.add("t4:"+(jpp-10));
				if(h.heads[0].jr == 15) scores.add("t5");
				if(h.heads[0].jr == 16) scores.add("t6");
				if(h.heads[0].jr == 17) scores.add("t7");
				kj++;
			}
			for(mj m : h.heads) {
				if(m != null) {
					
					if(m.jr < 9) it[m.jr] = true;
					if(m.jr < 9) ptype[0] = true;
					else if(m.jr < 11) ptype[1] = true;
					else if(m.jr < 15) ptype[2] = true;
					else ptype[3] = true;
					
					if(m.dr) jdr++;
					for(int d : dora) {
						if(m.jr == d) dr++;
					}
					if(dr >= 11) {
						Rule.playerinfo.get(player).tropy(16, 3);
					}
				}
			}
			if(menjen && h.count >= 3 && !h.ispair) {
				sj++;
				if(sj == 2) scores.add("t8");
			}
			if(h.isND()) tyo = false;
			
			if(!h.isND() || !h.isJP()) tnd = false;
			if(!h.isCt() || h.isND()) jct = false;
			
			if(!h.isND()) hnd = false;
			if(!h.isCt()) ct = false;
		}

		if(dack.size() == 54 && !stop) {
			Rule.playerinfo.get(player).tropy(16, 2);
			ym.add("t25");
		}
		
		if(kang == 1) scores.add("t9");
		if(dack.size() <= 1) {
			if(drow == last) scores.add("t10");
			if(drop == last) scores.add("t11");
		}
		if(dr > 0) scores.add("t12:"+dr);
		if(jdr > 0) scores.add("t13:"+jdr);
		
		if(ptype[0] == true && ptype[1] == false && ptype[2] == false && ptype[3] == false) {
			scores.add("t23");
		} else if(ptype[0] == true && ptype[1] == false && ptype[2] == false && ptype[3] == false) {
			scores.add("t23");
		} else if((ptype[0] == true && ptype[1] == false) && (ptype[2] == true || ptype[3] == true)) {
			scores.add("t14");
		} else if((ptype[0] == false && ptype[1] == true) && (ptype[2] == true || ptype[3] == true)) {
			scores.add("t14");
		} 
		if(kj > 2) scores.add("t15");
		if(kj > 1 && menjen) scores.add("t16");
		if(ssw) {
			int s = 0;
			if(scores.contains("t5")) s++;
			if(scores.contains("t6")) s++;
			if(scores.contains("t7")) s++;
			if(s >= 2) {
				scores.add("t17");
			}
		} else {
			if(scores.contains("t5") && scores.contains("t6") && scores.contains("t7")) {
				ym.add("t26");
			}
		}

		if(hnd) scores.add("t18");
		if(ct) scores.add("t19");
		boolean itb = true;
		for(boolean ib : it) if(!ib) itb = false;
		if(itb) scores.add("t20");
		if(tyo) scores.add("t21");
		if(jct) scores.add("t22");
		if(kan >= 3) scores.add("t24");

		if(rot >= 4 && kan >= 3 && !stop && dr >= 2) {
			Rule.playerinfo.get(player).tropy(16, 7);
			ym.add("t30");
		}
		if(tnd) ym.add("t27");
		
		if(gr.length() <= 0 && menjen) {
			if(last.dr && dr + jdr >= 2) {
				if(dack.size() == 54) {
					Rule.playerinfo.get(player).tropy(16, 2);
					ym.add("t25");
				}
				ym.add("t32");
				Rule.playerinfo.get(player).tropy(16, 6);
			} else if(jdr >= 1){
				if(dack.size() == 54) {
					Rule.playerinfo.get(player).tropy(16, 2);
					ym.add("t25");
				}
				ym.add("t29");
			}
		}
		if(gs.length() <= 0) {
			if((dora.contains(last.jr)) && dack.size() > 25) {
				if(dack.size() == 54) {
					Rule.playerinfo.get(player).tropy(16, 2);
					ym.add("t25");
				}
				ym.add("t31");
				Rule.playerinfo.get(player).tropy(16, 5);
			} else if(dack.size() > 12){
				if(dack.size() == 54) {
					Rule.playerinfo.get(player).tropy(16, 2);
					ym.add("t25");
				}
				ym.add("t28");
			}
		}
		if(stack == 7) {
			ym.clear();
			ym.add("t33");
			return ym;
		}
		if(ym.size() > 0) return ym;
		if(stop) {
			scores.clear();
			return scores;
		}
		return scores;
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			String dr = "";
			for(int i :dora) dr+= set[i]+ " ";
			scoreBoardText.add("&c ["+Main.GetText("c1016:t12")+ "]&f : " + dr);
			scoreBoardText.add("&c ["+Main.GetText("c1016:t3")+ "]&f : " + set[jnp]);
			scoreBoardText.add("&c ["+Main.GetText("c1016:t4")+ "]&f : " + set[jpp]);
		}

		return false;
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			
		}
		return true;
	}	
	@Override
	public String getBgm() {
		if(!isps) {
			return "c16-3";
		}
		return super.getBgm();
	}
}
