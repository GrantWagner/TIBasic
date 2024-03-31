package net.grantwagner.biorhythm;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.print;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.println;
import static net.grantwagner.biorhythm.util.CompatibilityUtils.readInt;
import static net.grantwagner.biorhythm.util.GeneralUtils.sleep;

import java.time.Month;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.grantwagner.biorhythm.util.CompatibilityUtils;


// BIORHYTHM
// (C) 1984 DILITHIUM PRESS
// https://en.wikipedia.org/wiki/Biorhythm_(pseudoscience)
public class BioRhythm {
  private final int CHART_WIDTH = 9;
  private final int CHART_PAGE_LENGTH = 18;
  
  private final String EMPTY_CHART_LINE = generateEmptyChartLine(); 

  
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
    println("     B I O R H Y T H M");
    println("© 1984 Dilithium Press");
    println("Java Translation Grant Wagner"); 
    println("https://en.wikipedia.org/wiki/Biorhythm_(pseudoscience)");
    println(""); 

    int birthJulianDate = getJulianDate("Enter Birth Date");
    int currentJulianDate = getJulianDate("Enter Start Date for the chart");
    println("");

    validateDates(birthJulianDate, currentJulianDate);

    sleep(1);

    while (true) {
      displayChartHeader(); 
      for (int lineIndex = 0; lineIndex < CHART_PAGE_LENGTH; lineIndex++) {

        int daysDifference = currentJulianDate - birthJulianDate; 
        String currentDateString = julianDateToString(currentJulianDate); 
        currentJulianDate += 1; 
        
        StringBuffer currentLine = new StringBuffer(EMPTY_CHART_LINE);
        for (BioRhythmType currentType: BioRhythmType.values()) {
          generateChartLine(currentType, currentLine, daysDifference); 
        }
        currentLine.insert(0, currentDateString + " ");
        println(currentLine);
      }

      continueOrExit(); 
    } 
  }

  private String generateEmptyChartLine() {
    String generatedLine = " ".repeat(CHART_WIDTH);
    generatedLine = generatedLine + ":" + generatedLine;
    return generatedLine;
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
      println(dayRequest); 
      day = readInt(); 
    }

    int year = 0;
    while (year < 1) { 
      println("Year: ");
      year = readInt(); 
    }

    if (year <= 100) { 
      year = year + 1900; 
      println(year + " assumed."); 
    }

    return calculateJulianDate(month, day, year); 
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

  public void validateDates(int birthJulianDate, int currentJulianDate) {
    if (currentJulianDate < birthJulianDate) { 
      println("Chart date can't be earlier than birth date.");
      sleep(5);
      System.exit(0);
    }
  }

  private void displayChartHeader() {
    println("     B I O R H Y T H M");
    println("———Date——— Down     0    Up");
    println("           " + "_".repeat(CHART_WIDTH * 2));
    println("");
  }

  private void generateChartLine(BioRhythmType bioRhythmType, StringBuffer currentLine, int offset) {

    double currentBioRhythmValue = sin(offset * 2 * PI / bioRhythmType.getPeriod()); 
    int currentBioRhythmTranslated = (int) (currentBioRhythmValue * CHART_WIDTH + CHART_WIDTH + 0.5);
    

    char currentSymbol = bioRhythmType.getSymbol();
    Character currentChar = currentLine.charAt(currentBioRhythmTranslated);
    
    if (currentChar != ' ' && currentChar != ':')
      currentSymbol = '*';

    currentLine.setCharAt(currentBioRhythmTranslated, currentSymbol);
  } 

  private String julianDateToString(int julianDate) {
    int workingValue = julianDate + 68_569; 
    int remainder = 4 * workingValue / 146_097; 
    workingValue -= (146_097 * remainder + 3)/ 4; 
    int year = 4000 * (workingValue + 1) / 1461001; 

    workingValue -= (1461 * year / 4) + 31; 
    int month = 80 * workingValue / 2447; 

    int day = workingValue - (2447 * month / 80); 
    workingValue = month / 11; 
    month = (int) (month + 2 - 12 * workingValue); 
    year = 100 * (remainder - 49) + year + (int) (workingValue); 

    return String.format("%2d\\%2d\\%4d", month, day, year);
  }

  public void continueOrExit() {
    println(""); 
    println(" Press enter to continue, or type any string to exit.");

    String input = CompatibilityUtils.readLine();
    if (input.length() != 0) 
      System.exit(0);
  }

}
