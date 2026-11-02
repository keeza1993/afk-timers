package com.afktimers;

import com.google.inject.Provides;
import java.awt.Color;
import java.util.*;
import javax.inject.Inject;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(name="AFK Timers",description="Coloured protection, prayer and boost timer bars",tags={"afk","timer","boost","prayer"})
public class AfkTimersPlugin extends Plugin {
 @Inject ConfigManager configManager;
 private volatile boolean running;
 private javax.swing.JDialog welcomeDialog;
 @Inject Client client;
 @Inject AfkTimersConfig config;
 @Inject OverlayManager overlays;
 @Inject AfkTimersOverlay overlay;
 private final Map<String,Sample> samples=new HashMap<>();
 private final Map<Skill,Integer> levels=new EnumMap<>(Skill.class);
 private int lastDecay=-1, prayerStartTick=-1, prayerStartPoints, lastPrayer=-1;
 private String prayerSignature="";
 volatile java.util.List<Bar> bars=Collections.emptyList();
 long lastTickNanos;
 @Provides AfkTimersConfig provideConfig(ConfigManager manager){return manager.getConfig(AfkTimersConfig.class);}
 @Override protected void startUp(){running=true;overlays.add(overlay);showWelcome(false);}
 @Override protected void shutDown(){running=false;overlays.remove(overlay);clear();javax.swing.SwingUtilities.invokeLater(() -> {if(welcomeDialog!=null){welcomeDialog.dispose();welcomeDialog=null;}});}
 @Subscribe public void onConfigChanged(net.runelite.client.events.ConfigChanged event){
  if("afktimers".equals(event.getGroup()) && "showWelcome".equals(event.getKey()) && "true".equals(event.getNewValue())){
   configManager.setConfiguration("afktimers","showWelcome",false);
   showWelcome(true);
  }
 }
 private void showWelcome(boolean force){
  javax.swing.SwingUtilities.invokeLater(() -> {
   if(!running || java.awt.GraphicsEnvironment.isHeadless())return;
   if(!force && Boolean.TRUE.equals(configManager.getConfiguration("afktimers","welcomeSeen",Boolean.class)))return;
   if(welcomeDialog!=null && welcomeDialog.isDisplayable()){welcomeDialog.toFront();return;}
   java.awt.Window owner=javax.swing.SwingUtilities.getWindowAncestor(client.getCanvas());
   welcomeDialog=WelcomeDialog.create(owner);
   welcomeDialog.setVisible(true);
   configManager.setConfiguration("afktimers","welcomeSeen",true);
  });
 }
 private void clear(){samples.clear();levels.clear();bars=Collections.emptyList();lastDecay=-1;prayerStartTick=-1;lastPrayer=-1;prayerSignature="";}
 @Subscribe public void onGameStateChanged(GameStateChanged e){if(e.getGameState()!=GameState.LOGGED_IN && e.getGameState()!=GameState.LOADING)clear();}
 @Subscribe public void onItemContainerChanged(ItemContainerChanged e){if(e.getContainerId()==InventoryID.EQUIPMENT.getId())prayerStartTick=-1;}
 @Subscribe public void onStatChanged(StatChanged e){
  Skill s=e.getSkill();Integer old=levels.put(s,e.getBoostedLevel());
  if(s!=Skill.PRAYER && s!=Skill.HITPOINTS && old!=null && old>e.getLevel() && e.getBoostedLevel()==old-1)lastDecay=client.getTickCount();
 }
 @Subscribe public void onGameTick(GameTick e){
  if(client.getGameState()!=GameState.LOGGED_IN)return;
  lastTickNanos=System.nanoTime();
  java.util.List<Bar> next=new ArrayList<>();
  timed(next,"antifire","Antifire",3981,30,600,2452,config.fireColour(),config.antifire());
  timed(next,"superfire","Super antifire",6101,20,300,21978,new Color(205,95,185),config.superAntifire());
  // Goading's server timer is in six-game-tick units.
  timed(next,"goading","Goading potion",11294,6,600,30137,new Color(210,135,65),config.goading());
  // Ring of endurance counts down first, then the ordinary stamina timer.
  int stamina=client.getVarbitValue(24)*10;
  int endurance=client.getVarbitValue(10385)*10;
  effect(next,"stamina",endurance>0?"Stamina (extended)":"Stamina",stamina+endurance,10,endurance>0?400:200,12625,new Color(180,150,65),config.stamina());
  int poison=client.getVarpValue(102);
  effect(next,"venom","Venom immunity",TimerMath.venomTicks(poison),30,300,12913,config.venomColour(),config.venom());
  effect(next,"poison","Poison immunity",TimerMath.poisonTicks(poison),30,200,2446,new Color(95,180,70),config.poison());

  timed(next,"cooldown","Heart cooldown",5361,10,500,27641,config.heartColour(),config.cooldown());
  int[] ids={8429,8430,8431,8432,8433,13663,13664,13665};
  String[] names={"Attack","Strength","Defence","Ranged","Magic","Combat","Bastion","Battlemage"};
  int[] icons={23697,23709,23721,23733,23745,23685,24635,24623};
  Color[] colours={new Color(235,195,105),new Color(220,90,125),new Color(130,185,225),new Color(130,195,110),new Color(150,145,215),new Color(175,190,150),new Color(185,145,170),new Color(165,170,205)};
  for(int i=0;i<ids.length;i++)timed(next,"divine"+names[i],"Divine "+names[i],ids[i],1,500,icons[i],colours[i],enabled("divine"+names[i]));
  prayer(next);
  for(Skill s:Skill.values()){
   if(s==Skill.OVERALL || s==Skill.PRAYER || s==Skill.HITPOINTS)continue;
   int boost=client.getBoostedSkillLevel(s)-client.getRealSkillLevel(s);
   if(boost<=0 || !enabled("skill"+title(s.getName())))continue;
   if(s==Skill.MAGIC && client.getVarbitValue(14704)>0)continue;
   if(divineActive(s))continue;
   int cycle=client.isPrayerActive(Prayer.PRESERVE)?150:100;
   int remaining=lastDecay<0?cycle:Math.max(1,cycle-(client.getTickCount()-lastDecay)%cycle);
   int idx=s==Skill.ATTACK?0:s==Skill.STRENGTH?1:s==Skill.DEFENCE?2:s==Skill.RANGED?3:4;
   next.add(new Bar(s.getName()+" +"+boost,remaining,cycle,s==Skill.ATTACK?2436:s==Skill.STRENGTH?2440:s==Skill.DEFENCE?2442:s==Skill.RANGED?2444:s==Skill.MAGIC?3040:12625,colours[idx],"~"+TimerMath.time(remaining)+" next",lastDecay>=0));
  }
  bars=Collections.unmodifiableList(next);
 }
 private String title(String s){return s.substring(0,1).toUpperCase(Locale.ROOT)+s.substring(1).toLowerCase(Locale.ROOT);}
 private boolean enabled(String key){try{return (boolean)AfkTimersConfig.class.getMethod(key).invoke(config);}catch(ReflectiveOperationException ex){return false;}}
 private boolean divineActive(Skill s){
  int specific=s==Skill.ATTACK?8429:s==Skill.STRENGTH?8430:s==Skill.DEFENCE?8431:s==Skill.RANGED?8432:s==Skill.MAGIC?8433:0;
  if(specific!=0 && client.getVarbitValue(specific)>0)return true;
  return ((s==Skill.ATTACK||s==Skill.STRENGTH||s==Skill.DEFENCE)&&client.getVarbitValue(13663)>0)
   ||((s==Skill.RANGED||s==Skill.DEFENCE)&&client.getVarbitValue(13664)>0)
   ||((s==Skill.MAGIC||s==Skill.DEFENCE)&&client.getVarbitValue(13665)>0);
 }
 private void timed(java.util.List<Bar> out,String key,String name,int varbit,int unit,int full,int icon,Color colour,boolean show){effect(out,key,name,client.getVarbitValue(varbit)*unit,unit,full,icon,colour,show);}
 private void effect(java.util.List<Bar> out,String key,String name,int raw,int unit,int full,int icon,Color colour,boolean show){
  if(raw<=0){samples.remove(key);return;}
  int tick=client.getTickCount();Sample sample=samples.get(key);
  if(sample==null){sample=new Sample(raw,tick,Math.max(full,raw));samples.put(key,sample);}
  else if(raw!=sample.raw){if(raw>sample.raw)sample.maximum=Math.max(full,raw);sample.raw=raw;sample.tick=tick;}
  double remaining=Math.max(1,raw-Math.min(unit-1,tick-sample.tick));
  if(show)out.add(new Bar(name,remaining,sample.maximum,icon,colour,null,true));
 }
 private void prayer(java.util.List<Bar> out){
  int points=client.getBoostedSkillLevel(Skill.PRAYER),tick=client.getTickCount();
  StringBuilder signature=new StringBuilder();for(Prayer p:Prayer.values())if(client.isPrayerActive(p))signature.append(p.name()).append(',');
  String active=signature.toString();
  if(!active.equals(prayerSignature)||points>lastPrayer||prayerStartTick<0){prayerStartTick=tick;prayerStartPoints=points;prayerSignature=active;}
  lastPrayer=points;
  String text=points+" pts";
  if(!active.isEmpty() && prayerStartPoints>points && tick>prayerStartTick){double rate=(double)(prayerStartPoints-points)/(tick-prayerStartTick);text+=" ~"+TimerMath.time(points/rate);}
  else if(!active.isEmpty())text+=" estimating";
  if(config.prayer())out.add(new Bar("Prayer",points,client.getRealSkillLevel(Skill.PRAYER),2434,config.prayerColour(),text,false));
 }
 static final class Sample {int raw,tick,maximum;Sample(int r,int t,int m){raw=r;tick=t;maximum=m;}}
 static final class Bar {
  final String name,text;final double remaining,maximum;final int icon;final Color colour;final boolean countdown;
  Bar(String n,double r,double m,int i,Color c,String t,boolean cd){name=n;remaining=r;maximum=m;icon=i;colour=c;text=t;countdown=cd;}
 }
}




