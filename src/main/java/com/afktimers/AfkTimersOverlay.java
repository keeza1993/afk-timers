package com.afktimers;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.inject.Inject;
import net.runelite.api.*;
import net.runelite.api.widgets.*;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.*;

public class AfkTimersOverlay extends Overlay {
 private final AfkTimersPlugin plugin;
 private final Client client;
 private final AfkTimersConfig config;
 private final ItemManager items;
 private final java.util.Map<Integer,Color> potionColours=new java.util.HashMap<>();
  private java.awt.Point lastLocation;
 private Integer anchorBottom;
 private int previousHeight;
 private final net.runelite.client.config.ConfigManager configManager;
 @Inject AfkTimersOverlay(AfkTimersPlugin p,Client c,AfkTimersConfig cfg,ItemManager im,net.runelite.client.config.ConfigManager cm){
  super(p);plugin=p;client=c;config=cfg;items=im;configManager=cm;
  setPosition(OverlayPosition.DETACHED);setLayer(OverlayLayer.ABOVE_WIDGETS);
 }
 @Override public Dimension render(Graphics2D g){
  if(client.getGameState()!=GameState.LOGGED_IN)return null;
  java.util.List<AfkTimersPlugin.Bar> bars=plugin.bars;
  if(config.preview())bars=Arrays.asList(
   new AfkTimersPlugin.Bar("Antifire",300,600,2452,new Color(175,100,205),null,true),
   new AfkTimersPlugin.Bar("Venom immunity",25,300,12913,new Color(55,150,85),null,true),
   new AfkTimersPlugin.Bar("Prayer",40,99,2434,new Color(90,160,235),"40 pts ~2:10",false),
   new AfkTimersPlugin.Bar("Heart cooldown",350,500,27641,config.heartColour(),null,true));
  if(bars.isEmpty())return null;
  int w=config.width(),h=config.height(),total=bars.size()*(h+3)-3;
  boolean reset=getPreferredLocation()==null;
  if(reset){
   Widget inv=client.getWidget(WidgetInfo.INVENTORY);
   if(inv!=null && !inv.isHidden()){
    Rectangle bounds=inv.getBounds();
    setPreferredLocation(new java.awt.Point(Math.max(0,Math.min(client.getCanvasWidth()-w,bounds.x)),Math.max(0,bounds.y-total-8)));
   }else setPreferredLocation(new java.awt.Point(Math.max(0,client.getCanvasWidth()-w-25),Math.max(0,client.getCanvasHeight()-total-340)));
   anchorBottom=getPreferredLocation().y+total;
  }
  java.awt.Point location=getPreferredLocation();
  if(anchorBottom==null){
   Integer saved=configManager.getConfiguration("afktimers","stackAnchorBottom",Integer.class);
   anchorBottom=saved==null?location.y+total:saved;
  }else if(!reset && lastLocation!=null && !location.equals(lastLocation)){
   // RuneLite moved the overlay: capture the bottom of the stack being dragged.
   anchorBottom=location.y+(previousHeight>0?previousHeight:total);
  }
  int top=TimerMath.stackTop(anchorBottom,total);
  int verticalShift=top-location.y;
  if(top!=location.y){setPreferredLocation(new java.awt.Point(location.x,top));g.translate(0,top-location.y);}
  Integer savedAnchor=configManager.getConfiguration("afktimers","stackAnchorBottom",Integer.class);
  if(!anchorBottom.equals(savedAnchor))configManager.setConfiguration("afktimers","stackAnchorBottom",anchorBottom);
  lastLocation=new java.awt.Point(getPreferredLocation());previousHeight=total;
  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
  g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,12));
  double subtick=Math.min(1,(System.nanoTime()-plugin.lastTickNanos)/600000000.0);
  int alpha=config.opacity()*255/100,y=0;
  for(AfkTimersPlugin.Bar b:bars){
   double remaining=b.countdown && !config.preview()?Math.max(0,b.remaining-subtick):b.remaining;
   double fraction=TimerMath.fraction(remaining,b.maximum);
   boolean dim=config.flash() && fraction<0.10 && (System.currentTimeMillis()/450)%2==0;
   g.setColor(new Color(20,23,28,alpha));g.fillRoundRect(0,y,w,h,6,6);
   Color c=(b.icon==30137 || b.icon==12625)?potionColour(b.icon,b.colour):b.colour;g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),dim?alpha/3:alpha));
   g.fillRoundRect(0,y,(int)Math.round(w*fraction),h,6,6);
   g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),dim?100:220));g.drawRoundRect(0,y,w-1,h-1,6,6);
   BufferedImage icon=items.getImage(b.icon);if(icon!=null)g.drawImage(icon,3,y+(h-24)/2,26,24,null);
   String text=b.text==null?TimerMath.time(remaining):b.text;
   int right=w-7-g.getFontMetrics().stringWidth(text),base=y+(h+g.getFontMetrics().getAscent())/2-2;
   String label=b.name;while(label.length()>2 && g.getFontMetrics().stringWidth(label)>right-40)label=label.substring(0,label.length()-1);
   shadow(g,label,34,base);shadow(g,text,right,base);y+=h+3;
  }
  g.translate(0,-verticalShift);
  return new Dimension(w,total);
 }
 private void shadow(Graphics2D g,String t,int x,int y){g.setColor(Color.BLACK);g.drawString(t,x+1,y+1);g.setColor(Color.WHITE);g.drawString(t,x,y);}
 // Pick a vivid liquid colour from the actual item sprite; ignore the vial and dark outline.
 private Color potionColour(int item,Color fallback){
  Color cached=potionColours.get(item);if(cached!=null)return cached;
  BufferedImage image=items.getImage(item);if(image==null)return fallback;
  int r=0,g=0,b=0,count=0;
  for(int y=0;y<image.getHeight();y++)for(int x=0;x<image.getWidth();x++){
   int pixel=image.getRGB(x,y);int red=(pixel>>>16)&255,green=(pixel>>>8)&255,blue=pixel&255;
   int max=Math.max(red,Math.max(green,blue)),min=Math.min(red,Math.min(green,blue));
   if((pixel>>>24)>128 && max>65 && max-min>45){r+=red;g+=green;b+=blue;count++;}
  }
  if(count==0)return fallback;
  Color colour=new Color(r/count,g/count,b/count);potionColours.put(item,colour);return colour;
 }
}



