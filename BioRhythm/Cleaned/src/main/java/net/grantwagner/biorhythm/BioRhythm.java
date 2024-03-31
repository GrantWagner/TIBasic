package net.grantwagner.biorhythm;

import static java.lang.Math.sin;
import static java.lang.Math.PI;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.print;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.println;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.readInt;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.seg;

import java.time.Month;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.grantwagner.biorhythm.util.CompatibilityUtils;


// BIORHYTHM
// (C) 1984 DILITHIUM PRESS
// https://en.wikipedia.org/wiki/Biorhythm_(pseudoscience)
public class BioRhythm {
  private final int CHART_WIDTH = 9;
  private final int CHART_PAGE_LENGTH = 18;
  
  @RequiredArgsConstructor
  @Getter
  private enum BioRhythmType {
    PHYSICAL('P', 23),
    EMOTIONAL('E', 28),
    INTELLECTUAL('I', 33);
    
    private final char symbol;
    private final int period;
  }
  
  public void biorhythm() {

    String QS = generateEmptyChartLine(); // 170 GOSUB 1660
    
    println("\t" + "BioRhyhm"); // 180 PRINT TAB(10);"BIORHYTHM"
    println(""); // 190 PRINT

    int JB = getJulianDate("Enter Birth Date");
    int JC = getJulianDate("Enter Start Date for the chart");

    println("");
    if (JC < JB) { 
      println("Chart date can't be earlier than birth date.");
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
      }
      System.exit(0);
    }

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
    }

    while (true) {
      displayChartHeader(); // 730 GOSUB 1100
      for (int L = 0; L < CHART_PAGE_LENGTH; L++) {
        int N = JC - JB; // 740 N=JC-JB
        int V = 23; // 750 V=23
        String LS = "";
        int R = calculateRhythmOffset(N, V); // 760 GOSUB 1190
        LS = generateChartLine(V, LS, QS, R); // 770 GOSUB 1220
        V = 28; // 780 V=28
        R = calculateRhythmOffset(N, V); // 760 GOSUB 1190
        LS = generateChartLine(V, LS, QS, R); // 800 GOSUB 1220
        V = 33; // 810 V=33
        R = calculateRhythmOffset(N, V); // 760 GOSUB 1190
        LS = generateChartLine(V, LS, QS, R); // 830 GOSUB 1220
        String CS = julianDateToString(JC); // 840 GOSUB 1480
        println(CS + "\t" + LS); // 860 PRINT C$;TAB(9);L$
        JC = JC + 1; // 870 JC=JC+1
      }

      println(""); // 890 PRINT
      println(" PRESS 'E' TO END"); // 900 PRINT " PRESS 'E' TO END"
      println(" OR SPACE TO CONTINUE"); // 910 PRINT " OR SPACE TO CONTINUE";
      char KEY = ' ';
      do {
        KEY = CompatibilityUtils.readChar(); // 920 CALL KEY(0,KEY,STATUS)
        // 930 IF STATUS=0 THEN 920
        if (KEY == 'E') { // 940 IF KEY<>69 THEN 970
          // 950 CALL CLEAR
          return; // 960 END
        }
      } while (KEY != ' '); // 970 IF KEY032 THEN 920
    } // 990 GOTO 730
  }

  private int getJulianDate(String title) {
    println(title); 
    println("");

    int month = 0;
    while (month < 1 || month > 12) {
      print("Month (1 TO 12): ");
      month = readInt(); 
    }

    int maxDays = Month.of(month).maxLength();
    String dayRequest = "Day (1 TO " + maxDays + "): "; 
    
    int day = 0;
    while (day < 1 || day > maxDays) {
      println(dayRequest); // 310 PRINT A$;
      day = readInt(); // 320 INPUT D
    }

    int year = 0;
    while (year < 1) { // 360 IF Y<0 THEN 350
      println("Year: ");
      year = readInt(); // 350 INPUT "YEAR ":Y
    }

    if (year <= 100) { // 370 IF Y>100 THEN 400
      year = year + 1900; // 380 Y=Y+1900
      println(year + " assumed."); // 390 PRINT Y;"ASSUMED"
    }

    return calculateJulianDate(month, day, year); // 400 GOSUB 1000
  }

  // https://en.wikipedia.org/wiki/Julian_day
  // 
  // Okay, this is really ugly, taken from a hard to find book with lots of magic numbers
  // We just have to take it as an axiom that this works and is optimized for integer division.
  // This apparently also takes leap years and leap seconds properly into account
  // 
  // https://books.google.com/books/about/Calendars.html?id=dRg7cgAACAAJ
  // Chapter 12, Page 604
  private int calculateJulianDate(int month, int day, int year) {
    int JD2 = (1461 * (year + 4800 + (month - 14)/12))/4 //days for year and month generally
       + (367 * (month - 2 - 12 * ((month - 14)/12)))/12 //leap seconds
       - (3 * ((year + 4900 + (month - 14)/12)/100))/4   //leap years
       + day 
       - 32075;
    return JD2;
  }

  private void displayChartHeader() {
    println("     B I O R H Y T H M");
    println("——Date—— Down     0    Up");
    println("         " + "_".repeat(CHART_WIDTH * 2));
    println("");
  }

  private int calculateRhythmOffset(int N, int V) {
    int W = N / V; // 1190 W=INT(N/V)
    int R = N - W * V; // 1200 R=N-W*V
    return R;
  } // 1210 RETURN

  private String generateChartLine(int V, String LS, String QS, int R) {
    String CS = "";
    if (V == 23) { // 1220 IF V<>23 THEN 1250
      LS = QS; // 1230 L$=Q$
      CS = "P"; // 1240 C$="P"
    }

    if (V == 28) { // 1250 IF V<>28 THEN 1270
      CS = "E"; // 1260 C$="E"
    }

    if (V == 33) { // 1270 IF V<>33 THEN 1290
      CS = "I"; // 1280 C$="I"
    }

    double W = R / (double)V; // 1290 W=R/V
    W = W * 2 * PI; // 1300 W=W*2*P
    W = CHART_WIDTH * sin(W); // 1310 W=T*SIN(W)
    W = W + CHART_WIDTH + 1.5f; // 1320 W=W=T+1.5

    String AS = seg(LS,(int)W,1); //LS.substring((int) W - 1, (int)W+1 - 1); // 1340 A$=SEG$(L$,W,1)
    if (AS.equals("P") // 1350 IF A$="P" THEN 1390
      || AS.equals("E") // 1360 IF A$="E" THEN 1390
      || AS.equals("*")) { // 1370 IF A$="*" THEN 1390
      CS = "*"; // 1390 C$="*"
    } // 1380 GOTO 1400

    if ((int) W == 1) { // 1400 IF W=1 THEN 1440
      LS = CS + seg(LS, 2, 18);
      //LS.substring(2 - 1, 2 + 18 - 1); // 1440 L$=C$&SEG$(L$,2,18)
//      return; // 1450 RETURN
    } else

    if ((int) W == 19) { // 1410 IF W=19 THEN 1460
      LS = seg(LS,1,18) + CS; //LS.substring(1 - 1, 1 + 18 - 1) + CS; // 1460 L$=SEG$(L$,1,18)&C$
//      return; // 1470 RETURN
    } else {

    LS = seg(LS,1, (int)W -1) //LS.substring(1 - 1, (int) W-1) 
      + CS 
      + seg(LS, LS.length()-(19-(int)W)+1, 19 - (int)W);
    }
//      + LS.substring(
//        LS.length() - (19 - (int) W) + 1 - 1,
//        LS.length()); // 1420
                     // L$=SEG$(L$,1,W-1)
                     //   &C$
                     //   &SEG$(L$,LEN(L$)-(19-W)+1,(19-W))
    return LS;
  } // 1430 RETURN

  private String julianDateToString(int JC) {
    int W = JC + 68_569; // 1480 W=JC+68569
    int R = (int) (4 * W / 146_097); // 1490 R=INT(4*W/146097)
    W = W - (int)((146_097 * R + 3)/ 4); // 1500 W=W-INT((146097*R+3)/4)
    // Note Line 1510 was copied 3 times in the original manuscript
    int Y = (int) (4000 * (W + 1) / 1461001); // 1510 Y=INT(4000*(W+1)/1461001)
    W = W - (int)(1461 * Y / 4) + 31; // 1520 W=W-INT(1461*Y/4)+31
    int M = (int) (80 * W / 2447); // 1530 M=INT(80*W/2447)
    int D = (int) (W - (2447 * M / 80)); // 1540 D=W-INT(2447*M/80)
    W = M / 11; // 1550 W=INT(M/11)
    M = (int) (M + 2 - 12 * W); // 1560 M=M+2-12*W
    Y = 100 * (R - 49) + Y + (int) (W); // 1570 Y=100*(R-49)+Y+W
    String AS = "" + M; // 1580 A$=STR$(M)
    String CS = AS + "/"; // 1590 C$=A$&"/"
    AS = "" + D; // 1600 A$=STR$(D)
    CS = CS + AS + "/"; // 1610 C$=C$&A$&"/"
    AS = "" + Y; // 1620 A$=STR$(Y)
    W = AS.length() - 1; // 1630 W=LEN(A$)-1
    CS = CS + seg(AS, (int)W, 2); // 1640 C$=C$&SEG$(A$,W,2)
    return String.format("%2d\\%2d\\%4d", M, D, Y);
  }

  private String generateEmptyChartLine() {
    String generatedLine = " ".repeat(CHART_WIDTH);
    generatedLine = generatedLine + ":" + generatedLine;
    return generatedLine;
  } 

}
