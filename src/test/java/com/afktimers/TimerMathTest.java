package com.afktimers;
import org.junit.Test;
import static org.junit.Assert.*;
public class TimerMathTest {
 @Test public void venomEndsBeforePoison(){assertEquals(300,TimerMath.venomTicks(-48));assertEquals(0,TimerMath.venomTicks(-38));assertEquals(1140,TimerMath.poisonTicks(-38));}
 @Test public void damageIsNotImmunity(){assertEquals(0,TimerMath.venomTicks(1000000));assertEquals(0,TimerMath.poisonTicks(5));}
 @Test public void fractionsAreClamped(){assertEquals(0,TimerMath.fraction(-1,100),0);assertEquals(1,TimerMath.fraction(200,100),0);assertEquals(.09,TimerMath.fraction(9,100),.0001);}
 @Test public void formatting(){assertEquals("5:00",TimerMath.time(500));assertEquals("0:01",TimerMath.time(1));assertEquals("0:00",TimerMath.time(-1));}
 @Test public void addedBarsKeepBottomFixed(){
  int bottom=500;
  assertEquals(472,TimerMath.stackTop(bottom,28));
  assertEquals(441,TimerMath.stackTop(bottom,59));
  assertEquals(bottom,TimerMath.stackTop(bottom,59)+59);
  assertEquals(472,TimerMath.stackTop(bottom,28));
 }
}
