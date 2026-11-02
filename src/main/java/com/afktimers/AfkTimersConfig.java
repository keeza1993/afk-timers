package com.afktimers;
import net.runelite.client.config.*;
@ConfigGroup("afktimers")
public interface AfkTimersConfig extends Config {
 @ConfigItem(keyName="antifire",name="Antifire",description="Show antifire protection",position=0)
 default boolean antifire(){return true;}
 @ConfigItem(keyName="superAntifire",name="Super antifire",description="Show super antifire protection",position=1)
 default boolean superAntifire(){return true;}
 @ConfigItem(keyName="venom",name="Venom immunity",description="Show actual venom immunity, separate from poison immunity",position=2)
 default boolean venom(){return true;}
 @ConfigItem(keyName="poison",name="Poison immunity",description="Show poison immunity",position=3)
 default boolean poison(){return true;}
 @ConfigItem(keyName="prayer",name="Prayer",description="Prayer points with estimated drain time; flashes below 10% prayer points",position=4)
 default boolean prayer(){return true;}
 @ConfigItem(keyName="cooldown",name="Heart cooldown",description="Single countdown until the heart can be used again",position=6)
 default boolean cooldown(){return true;}
 @ConfigItem(keyName="flash",name="Flash low bars",description="Flash below 10% remaining",position=7)
 default boolean flash(){return true;}
 @Range(min=20,max=100)
 @ConfigItem(keyName="opacity",name="Bar opacity (%)",description="Transparency of backgrounds and fills; text remains clear",position=8)
 default int opacity(){return 75;}
 @Range(min=180,max=450)
 @ConfigItem(keyName="width",name="Bar width",description="Width in pixels",position=9)
 default int width(){return 240;}
 @Range(min=24,max=45)
 @ConfigItem(keyName="height",name="Bar height",description="Height in pixels",position=10)
 default int height(){return 28;}
 @ConfigItem(keyName="preview",name="Preview bars",description="Show sample bars to adjust placement even without active effects",position=11)
 default boolean preview(){return false;}
 @ConfigItem(keyName="skillAttack",name="Attack boosts",description="Show Attack boosts and estimated next stat decrease",position=20)
 default boolean skillAttack(){return true;}
 @ConfigItem(keyName="skillStrength",name="Strength boosts",description="Show Strength boosts and estimated next stat decrease",position=21)
 default boolean skillStrength(){return true;}
 @ConfigItem(keyName="skillDefence",name="Defence boosts",description="Show Defence boosts and estimated next stat decrease",position=22)
 default boolean skillDefence(){return true;}
 @ConfigItem(keyName="skillRanged",name="Ranged boosts",description="Show Ranged boosts and estimated next stat decrease",position=23)
 default boolean skillRanged(){return true;}
 @ConfigItem(keyName="skillMagic",name="Magic boosts",description="Show Magic boosts and estimated next stat decrease",position=24)
 default boolean skillMagic(){return true;}
 @ConfigItem(keyName="skillCooking",name="Cooking boosts",description="Show Cooking boosts and estimated next stat decrease",position=25)
 default boolean skillCooking(){return true;}
 @ConfigItem(keyName="skillWoodcutting",name="Woodcutting boosts",description="Show Woodcutting boosts and estimated next stat decrease",position=26)
 default boolean skillWoodcutting(){return true;}
 @ConfigItem(keyName="skillFletching",name="Fletching boosts",description="Show Fletching boosts and estimated next stat decrease",position=27)
 default boolean skillFletching(){return true;}
 @ConfigItem(keyName="skillFishing",name="Fishing boosts",description="Show Fishing boosts and estimated next stat decrease",position=28)
 default boolean skillFishing(){return true;}
 @ConfigItem(keyName="skillFiremaking",name="Firemaking boosts",description="Show Firemaking boosts and estimated next stat decrease",position=29)
 default boolean skillFiremaking(){return true;}
 @ConfigItem(keyName="skillCrafting",name="Crafting boosts",description="Show Crafting boosts and estimated next stat decrease",position=30)
 default boolean skillCrafting(){return true;}
 @ConfigItem(keyName="skillSmithing",name="Smithing boosts",description="Show Smithing boosts and estimated next stat decrease",position=31)
 default boolean skillSmithing(){return true;}
 @ConfigItem(keyName="skillMining",name="Mining boosts",description="Show Mining boosts and estimated next stat decrease",position=32)
 default boolean skillMining(){return true;}
 @ConfigItem(keyName="skillHerblore",name="Herblore boosts",description="Show Herblore boosts and estimated next stat decrease",position=33)
 default boolean skillHerblore(){return true;}
 @ConfigItem(keyName="skillAgility",name="Agility boosts",description="Show Agility boosts and estimated next stat decrease",position=34)
 default boolean skillAgility(){return true;}
 @ConfigItem(keyName="skillThieving",name="Thieving boosts",description="Show Thieving boosts and estimated next stat decrease",position=35)
 default boolean skillThieving(){return true;}
 @ConfigItem(keyName="skillSlayer",name="Slayer boosts",description="Show Slayer boosts and estimated next stat decrease",position=36)
 default boolean skillSlayer(){return true;}
 @ConfigItem(keyName="skillFarming",name="Farming boosts",description="Show Farming boosts and estimated next stat decrease",position=37)
 default boolean skillFarming(){return true;}
 @ConfigItem(keyName="skillRunecraft",name="Runecraft boosts",description="Show Runecraft boosts and estimated next stat decrease",position=38)
 default boolean skillRunecraft(){return true;}
 @ConfigItem(keyName="skillHunter",name="Hunter boosts",description="Show Hunter boosts and estimated next stat decrease",position=39)
 default boolean skillHunter(){return true;}
 @ConfigItem(keyName="skillConstruction",name="Construction boosts",description="Show Construction boosts and estimated next stat decrease",position=40)
 default boolean skillConstruction(){return true;}
 @ConfigItem(keyName="skillSailing",name="Sailing boosts",description="Show Sailing boosts and estimated next stat decrease",position=41)
 default boolean skillSailing(){return true;}
 @ConfigItem(keyName="divineAttack",name="Divine Attack",description="Show Divine Attack timer",position=42)
 default boolean divineAttack(){return true;}
 @ConfigItem(keyName="divineStrength",name="Divine Strength",description="Show Divine Strength timer",position=43)
 default boolean divineStrength(){return true;}
 @ConfigItem(keyName="divineDefence",name="Divine Defence",description="Show Divine Defence timer",position=44)
 default boolean divineDefence(){return true;}
 @ConfigItem(keyName="divineRanged",name="Divine Ranged",description="Show Divine Ranged timer",position=45)
 default boolean divineRanged(){return true;}
 @ConfigItem(keyName="divineMagic",name="Divine Magic",description="Show Divine Magic timer",position=46)
 default boolean divineMagic(){return true;}
 @ConfigItem(keyName="divineCombat",name="Divine Combat",description="Show Divine Combat timer",position=47)
 default boolean divineCombat(){return true;}
 @ConfigItem(keyName="divineBastion",name="Divine Bastion",description="Show Divine Bastion timer",position=48)
 default boolean divineBastion(){return true;}
 @ConfigItem(keyName="divineBattlemage",name="Divine Battlemage",description="Show Divine Battlemage timer",position=49)
 default boolean divineBattlemage(){return true;}
 @ConfigItem(keyName="fireColour",name="Antifire colour",description="Override the purple antifire colour",position=60)
 default java.awt.Color fireColour(){return new java.awt.Color(175,100,205);}
 @ConfigItem(keyName="venomColour",name="Antivenom colour",description="Override the green antivenom colour",position=61)
 default java.awt.Color venomColour(){return new java.awt.Color(55,150,85);}
 @ConfigItem(keyName="prayerColour",name="Prayer colour",description="Override the blue prayer colour",position=62)
 default java.awt.Color prayerColour(){return new java.awt.Color(90,160,235);}
 @ConfigItem(keyName="heartColour",name="Heart colour",description="Override the cyan saturated heart colour",position=63)
 default java.awt.Color heartColour(){return new java.awt.Color(70,215,220);}
 @ConfigItem(keyName="goading",name="Goading potion",description="Show the goading potion aggression timer",position=12)
 default boolean goading(){return true;}
 @ConfigItem(keyName="stamina",name="Stamina potion",description="Show stamina duration, including extra duration from ring of endurance",position=13)
 default boolean stamina(){return true;}
 @ConfigItem(keyName="showWelcome",name="Show welcome",description="Reopen the introduction, movement instructions and OSRS geezers clan link",position=70)
 default boolean showWelcome(){return false;}
}
