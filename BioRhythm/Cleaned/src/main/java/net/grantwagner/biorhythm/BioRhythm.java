package net.grantwagner.biorhythm;

import static java.lang.Math.sin;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.print;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.println;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.readInt;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.seg;

import java.util.List;

import net.grantwagner.biorhythm.util.CompatibilityUtils;


// BIORHYTHM
// (C) 1984 DILITHIUM PRESS
public class BioRhythm {
  int L = 0; // 120 L=0
  double Z = 0.9999f; // 130 Z=.9999
  int T = 9; // 140 T=9
  double P = Math.PI; // 150 P=3.1415926535
  String QS = "";
  int M = 0;
  int D = 0;
  int Y = 0;
  int X = 0;
  int JD = 0;
  int JB = 0;
  String AS = "";
  private double W;
  private double B;
  private int JC;
  private int N;
  private int V;
  private int R;
  private String LS;
  private String CS;

  public void biorhythm() {

    // 160 CALL CLEAR
    goSub1660(); // 170 GOSUB 1660
    println("\t" + "BIORHYTHM"); // 180 PRINT TAB(10);"BIORHYTHM"
    println(""); // 190 PRINT
    println("Enter Birth Date"); // 200 PRINT "ENTER BIRTH DATE"
    println(""); // 210 PRINT

    M = 0;
    while (M < 1 // 230 IF M<1 THEN 220
      || M > 12) { // 240 IF M>12 THEN 220
      print("MONTH (1 TO 12)");
      M = readInt(); // 220 INPUT "MONTH (1 TO 12) ":M
    }

    // 290 DATA 31,29,31,30,31,30,31,31,30,31,30,31
    List<Integer> DATA = List.of(
      31,
      29,
      31,
      30,
      31,
      30,
      31,
      31,
      30,
      31,
      30,
      31);

    // 250 RESTORE
    for (int J = 1; J <= M; J++) { // 260 FOR J=1 TO M
      X = DATA.get(J - 1); // 270 READ X
    } // 280 NEXT J

    AS = "DAY (1 TO " + X + ") "; // 300 A$="DAY (1 TO " & STR$(X) & ") "

    D = 0;
    while (D < 1 // 330 IF D<1 THEN 310
      || D > X) { // 340 IF D>X THEN 310
      println(AS); // 310 PRINT A$;
      D = readInt(); // 320 INPUT D
    }

    Y = 0;
    while (Y < 1) { // 360 IF Y<0 THEN 350
      println("YEAR ");
      Y = readInt(); // 350 INPUT "YEAR ":Y
    }

    if (Y <= 100) { // 370 IF Y>100 THEN 400
      Y = Y + 1900; // 380 Y=Y+1900
      println(Y + " ASSUMED"); // 390 PRINT Y;"ASSUMED"
    }

    goSub1000(); // 400 GOSUB 1000

    JB = JD; // 410 JB=JD
    println(""); // 420 PRINT
    println("ENTER START DATE FOR CHART"); // 430 PRINT "ENTER START DATE FOR
                                           // CHART"
    println(""); // 440 PRINT

    M = 0;
    while (M < 1 // 460 IF M<1 THEN 450
      || M > 12) { // 470 IF M>12 THEN 450
      print("MONTH (1 TO 12)");
      M = readInt(); // 450 INPUT "MONTH (1 TO 12) ":M
    }

    // 480 RESTORE
    for (int J = 1; J <= M; J++) { // 490 FOR J=1 TO M
      X = DATA.get(J - 1); // 500 READ X
    } // 510 NEXT J

    AS = "DAY (1 TO " + X + ") "; // 520 A$="DAY (1 TO "&STR$(X)&") "

    D = 0;
    while (D < 1 // 550 IF D<1 THEN 530
      || D > X) { // 560 IF D>X THEN 530
      println(AS); // 530 PRINT A$;
      D = readInt(); // 540 INPUT D
    }

    Y = 0;
    while (Y < 1) { // 580 IF Y<0 THEN 570
      println("YEAR ");
      Y = readInt(); // 570 INPUT "YEAR ":Y
    }

    if (Y <= 100) { // 590 IF Y>100 THEN 620
      Y = Y + 1900; // 600 Y=Y+1900
      println(Y + " ASSUMED"); // 610 PRINT Y;"ASSUMED"
    }

    goSub1000(); // 620 GOSUB 1000
    JC = JD; // 630 JC=JD
    
    println(""); // 640 PRINT
    if (JC < JB) { // 650 IF JC >= JB THEN 710
      println("CHART DATE CAN'T BE EARLIER"); // 660 PRINT "CHART DATE CAN'T BE
                                              // EARLIER"
      println("THAN BIRTH DATE."); // 670 PRINT "THAN BIRTH DATE."
      for (int J = 1; J <= 1000; J++)
        ; // 680 FOR J=1 TO 1000
      // 690 NEXT J
      System.exit(0); // 700 GOTO 120
    }

    for (int J = 1; J <= 300; J++)
      ; // 710 FOR J=1 TO 300
    // 720 NEXT J
    while (true) {
      goSub1100(); // 730 GOSUB 1100
      do {
        N = JC - JB; // 740 N=JC-JB
        V = 23; // 750 V=23
        goSub1190(); // 760 GOSUB 1190
        goSub1220(); // 770 GOSUB 1220
        V = 28; // 780 V=28
        goSub1190(); // 790 GOSUB 1190
        goSub1220(); // 800 GOSUB 1220
        V = 33; // 810 V=33
        goSub1190(); // 820 GOSUB 1190
        goSub1220(); // 830 GOSUB 1220
        goSub1480(); // 840 GOSUB 1480
        L = L + 1; // 850 L=L+1
        println(CS + "\t" + LS); // 860 PRINT C$;TAB(9);L$
        JC = JC + 1; // 870 JC=JC+1
      } while (L < 18); // 880 IF L<18 THEN 740

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
      L = 0; // 980 L=0
    } // 990 GOTO 730
  }

  private void goSub1000() {
    W = (int) ((M - 14) / 12 + Z); // 1000 W=INT((M-14)/12+Z)
    JD = (int) (1461 * (Y + 4800 + W) / 4); // 1010 JD=INT(1461*(Y*4800+W)/4)
    B = 367.0 * (M - 2 - W * 12) / 12; // 1020 B=367*(M-2-W*12)/12
    if (B < 0) { // 1030 IF B>=0 THEN 1050
      B = B + Z; // 1040 B=B+Z
    }

    // 1050 B=INT(B)
    JD = JD + (int) B; // 1060 JD=JD+B
    B = (3 * (Y + 4900 + W) / 100) / 4; // 1070 B=INT((3*(Y*4900+W)/100)/4)
    JD = JD + D - 32075 - (int) B; // 1080 JD=JD+D-32075-B
  }

  private void goSub1100() {
    // 1100 CALL CLEAR
    println("     B I O R H Y T H M"); // 1110 PRINT TAB(5);"B I O R H Y T H M"
    print("——DATE—— DOWN"); // 1120 PRINT "—DATE— DOWN";
    println("     " + "0" + "    " + "UP"); // 1130 PRINT TAB(18);"0";TAB(23);"UP"
    print("         "); // 1140 PRINT TAB(9);
    for (int J = 1; J <= T + T + 1; J++) { // 1150 FOR J=1 TO T+T+1
      print('_'); // 1160 PRINT CHR$(95);
    } // 1170 NEXT J
    println("");
  } // 1180 RETURN

  private void goSub1190() {
    W = N / V; // 1190 W=INT(N/V)
    R = N - (int) (W) * V; // 1200 R=N-W*V
  } // 1210 RETURN

  private void goSub1220() {
    CS = "";
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

    W = R / (double)V; // 1290 W=R/V
    W = W * 2 * P; // 1300 W=W*2*P
    W = T * sin(W); // 1310 W=T*SIN(W)
    W = W + T + 1.5f; // 1320 W=W=T+1.5

    AS = seg(LS,(int)W,1); //LS.substring((int) W - 1, (int)W+1 - 1); // 1340 A$=SEG$(L$,W,1)
    if (AS.equals("P") // 1350 IF A$="P" THEN 1390
      || AS.equals("E") // 1360 IF A$="E" THEN 1390
      || AS.equals("*")) { // 1370 IF A$="*" THEN 1390
      CS = "*"; // 1390 C$="*"
    } // 1380 GOTO 1400

    if ((int) W == 1) { // 1400 IF W=1 THEN 1440
      LS = CS + seg(LS, 2, 18);
      //LS.substring(2 - 1, 2 + 18 - 1); // 1440 L$=C$&SEG$(L$,2,18)
      return; // 1450 RETURN
    }

    if ((int) W == 19) { // 1410 IF W=19 THEN 1460
      LS = seg(LS,1,18) + CS; //LS.substring(1 - 1, 1 + 18 - 1) + CS; // 1460 L$=SEG$(L$,1,18)&C$
      return; // 1470 RETURN
    }

    LS = seg(LS,1, (int)W -1) //LS.substring(1 - 1, (int) W-1) 
      + CS 
      + seg(LS, LS.length()-(19-(int)W)+1, 19 - (int)W);
//      + LS.substring(
//        LS.length() - (19 - (int) W) + 1 - 1,
//        LS.length()); // 1420
                     // L$=SEG$(L$,1,W-1)
                     //   &C$
                     //   &SEG$(L$,LEN(L$)-(19-W)+1,(19-W))
  } // 1430 RETURN

  private void goSub1480() {
    W = JC + 68_569; // 1480 W=JC+68569
    R = (int) (4 * W / 146_097); // 1490 R=INT(4*W/146097)
    W = W - (int)((146_097 * R + 3)/ 4); // 1500 W=W-INT((146097*R+3)/4)
    // Note Line 1510 was copied 3 times in the original manuscript
    Y = (int) (4000 * (W + 1) / 1461001); // 1510 Y=INT(4000*(W+1)/1461001)
    W = W - (int)(1461 * Y / 4) + 31; // 1520 W=W-INT(1461*Y/4)+31
    M = (int) (80 * W / 2447); // 1530 M=INT(80*W/2447)
    D = (int) (W - (2447 * M / 80)); // 1540 D=W-INT(2447*M/80)
    W = M / 11; // 1550 W=INT(M/11)
    M = (int) (M + 2 - 12 * W); // 1560 M=M+2-12*W
    Y = 100 * (R - 49) + Y + (int) (W); // 1570 Y=100*(R-49)+Y+W
    AS = "" + M; // 1580 A$=STR$(M)
    CS = AS + "/"; // 1590 C$=A$&"/"
    AS = "" + D; // 1600 A$=STR$(D)
    CS = CS + AS + "/"; // 1610 C$=C$&A$&"/"
    AS = "" + Y; // 1620 A$=STR$(Y)
    W = AS.length() - 1; // 1630 W=LEN(A$)-1
    CS = CS + seg(AS, (int)W, 2); // 1640 C$=C$&SEG$(A$,W,2)
    return; // 1650 RETURN
  }

  private void goSub1660() {
    QS = ""; // 1660 Q$=""
    for (int J = 1; J <= T; J++) { // 1670 FOR J»l TO T
      QS = QS + ' '; // 1680 Q$=Q$&CHRS(32), CHRS(32) is a space
    } // 1690 NEXT J
    QS = QS + ":" + QS; // 1700 Q$=Q$&":"$Qs
  } // 1710 RETURN

}
