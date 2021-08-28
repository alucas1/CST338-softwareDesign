/* Name: Alberto Lucas
 * Date: 11/10/2020
 * Class: CST338
 * 
 * Program Name: Assig2
 * Description: This program simulates the slot machine casino game. The
 * user enters a bet and the main program rolls the wheels of the slot machine.
 * The user can play until a bet of 0 is entered. At the end, the individual
 * winnings and total winnings are displayed to the user.
 */

import java.util.Scanner;

/*  This class contains main(), which simulates the slot machine
    game. User is asked for a bet(int) between 1-100. Invalid bets are not 
    accepted and 0 quits the program. 
*/
public class Assig2
{
   // Initialize global scanner object.
   static Scanner keyboard = new Scanner(System.in);
   
   // Main game loop.
   public static void main(String[] args)
   {
      // Initialize variables. 
      ThreeString thePull = new ThreeString(); 
      int multiplier = 0;
      int winnings = 0;
      int bet = getBet();
      boolean saveWinningsNotFull = true;
      
      /* Loop to simulate slot machine game. The process is: get user bet ->
         pull slot -> determine multiplier -> calculate winnings -> display 
         -> save winning ->  repeat. Exits if input == 0 or MAX_PULLS is 
         reached.
       */   
      while (bet > 0 && saveWinningsNotFull)
      {  
         thePull = pull();
         multiplier = getPayMultiplier(thePull);
         winnings = bet * multiplier;
         saveWinningsNotFull = thePull.saveWinnings(winnings);
         if(saveWinningsNotFull) {
            display(thePull, winnings);  
            bet = getBet();
         } 
      }
      
      // Output winnings.
      System.out.println("\nThanks for playing at the Casino!");
      System.out.println("Your individual winnings were:");
      System.out.print(thePull.toStringWinnings());
      
      // Close scanner object.
      keyboard.close();
   }

   /** Gets user input. Valid input is between 0 - 100. 
      @return A valid bet from the user.
   */
   public static int getBet()
   {
      int validBet = -1; //starts @ -1 to initiate the user prompt in the loop.   
      while (validBet < 0 || validBet > 100)
      {
         System.out.print("Place your bet! (1 - 100 or 0 to quit): $");
         validBet = keyboard.nextInt();
      }
      return validBet;
   }

   /** Rolls the reels of the slot machine by assigning a random slot symbol
       to each string in a "ThreeString" object.
      @return A ThreeString object with randomly set reels (Strings).
   */
   public static ThreeString pull()
   {
      ThreeString SpinResults = new ThreeString();
      SpinResults.setString1(randString());
      SpinResults.setString2(randString());
      SpinResults.setString3(randString());
      return SpinResults;
   }

   /** Calculates the winnings multiplier after a slot pull.
      @param thePull The object who's slot reels will be checked.
      @return The multiplier of the winnings.
   */
   public static int getPayMultiplier(ThreeString thePull)
   {
      int multiplier = 0;
      
      // Checks "cherry" pattern variations and assigns multiplier.
      if (thePull.getString1().equals("cherries"))
      {
         if (thePull.getString2().equals("cherries"))
         {
            if (thePull.getString3().equals("cherries"))
            {
               multiplier = 30;
            }
            else
            {
               multiplier = 15;
            }
         }
         else
         {
            multiplier = 5;
         }
      }

      // Checks for triple "BAR" pattern and assigns multiplier.
      if (thePull.getString1().equals("BAR")
            && thePull.getString2().equals("BAR")
            && thePull.getString3().equals("BAR"))
      {
         multiplier = 50;
      }

      // Checks for triple "7" pattern and assigns multiplier.
      if (thePull.getString1().equals("7")
            && thePull.getString2().equals("7")
            && thePull.getString3().equals("7"))
      {
         multiplier = 100;
      }

      return multiplier;
   }

   /** Displays the user's winnings.
      @param thePull The already pulled slot machine results
      @param winnings the total winnings of the user.
   */
   public static void display(ThreeString thePull, int winnings)
   {
      System.out.println("Slot machine results:");
      System.out.println(thePull);
      if (winnings == 0)
      {
         System.out.println("Sorry, you lost.\n");
      } 
      else
      {
         System.out.println("Congrats, you won $" + winnings + "\n");
      }
   }

   /** Simulates the roll of a slot reel by randomly returning a reel string.
      @return A random reel string based on the given probability.
    */
   private static String randString()
   {
      double randomProbability = Math.random();
      if (randomProbability < 0.5) 
      {
         return "space"; //50% probability.
      }
      else if (randomProbability < 0.75)
      {
         return "cherries"; // 25% probability.
      }
      else if (randomProbability < 0.875)
      {
         return "BAR"; // 12.5% probability. 
      }
      else
      {
         return "7"; // 12.5% probability. 
      }
   }
}

/* This class keeps track of the slot reels using three strings and 
   the winnings using an array of MAX_PULLS size.
*/
class ThreeString
{
   // Initialize class variables.
   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;
   private static int numPulls = 0;
   private static int[] pullWinnings = new int[MAX_PULLS];

   // Instance variables.
   private String string1;
   private String string2;
   private String string3;
   
   // Default constructor - initializes all string members to "".
   public ThreeString()
   {
      string1 = "";
      string2 = "";
      string3 = "";
   }
   
   /** Sets string1 and filters bad inputs per the validString() method.
      @param string1 Value for the first slot reel.
      @return True if input is set, otherwise returns false.
   */
   public boolean setString1(String string1)
   {
      if (validString(string1))
      {
         this.string1 = string1;
         return true;
      }
      else
      {
         return false;
      }
   }
   
   /** Sets string2 and filters bad inputs per the validString() method.
      @param string2 Value for the second slot reel.
      @return True if input is set, otherwise returns false.
   */
   public boolean setString2(String string2)
   {
      if (validString(string2))
      {
         this.string2 = string2;
         return true;
      }
      else
      {
         return false;
      }
   }
   
   /** Sets string3 and filters bad inputs per the validString() method.
      @param string3 Value for the third slot reel.
      @return True if input is set, otherwise returns false.
   */
   public boolean setString3(String string3)
   {
      if (validString(string3))
      {
         this.string3 = string3;
         return true;
      }
      else
      {
         return false;
      }
   }
   
   // @return The value of string1.
   public String getString1()
   {
      return string1;
   }
   
   // @return The value of string2.
   public String getString2()
   {
      return string2;
   }
   
   // @return The value of string3.
   public String getString3()
   {
      return string3;
   }
   
   /** Saves the slot winnings into the pullWinnings[] array if its not full.
      @param Winnings the monetary amount won in a single slot pull.
      @return True if the winnings were successfully saved, else return false. 
   */
   public boolean saveWinnings(int winnings)
   {
      if (numPulls < MAX_PULLS) { 
         pullWinnings[numPulls] = winnings;
         numPulls++;
         return true;
      }
      else
      {
         return false;
      }
   }
   
   /** Changes the pullWinings array into a string and calculates the total
      @return The pullWinnings array (the user's winnings) as a string and the
      total winnings appended on a new line.
   */ 
   public String toStringWinnings()
   {
      String winnings = "";
      int sum = 0;
       
      for (int i = 0; i < numPulls; i++)
      {
         winnings += pullWinnings[i] + " ";
         sum += pullWinnings[i];
      }
      
      return winnings + "\nTotal winnings: $" + sum;
   }
   
   /** Helper method for string mutators. Checks if strings are not null
      and that they are <= MAX_LEN.
      @param str the string to check.
      @return true if it is a valid string, else return false.
    */
   private boolean validString(String str)
   {
      return (str != null) && (str.length() <= MAX_LEN);
   }
   
   // @return The three string members as a single concatenated string.
   @Override
   public String toString()
   {
      return "  " + string1 + "  " + string2 + "  " + string3;
   }
}

/* -------------------- TEST RUN ---------------------------* 
Place your bet! (1 - 100 or 0 to quit): $-1
Place your bet! (1 - 100 or 0 to quit): $101
Place your bet! (1 - 100 or 0 to quit): $-1000
Place your bet! (1 - 100 or 0 to quit): $1000
Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  7  space  space
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  cherries  space  7
Congrats, you won $5

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  BAR  space
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  7  space  cherries
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  BAR  space
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  BAR  BAR  space
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  BAR  cherries  7
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  space  cherries
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  cherries  cherries
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  space  space
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  7  cherries  space
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  cherries  space
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  cherries  space  cherries
Congrats, you won $5

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  cherries  cherries  space
Congrats, you won $15

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  cherries  cherries
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  cherries  space
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  cherries  7
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  space  BAR
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  cherries  space  7
Congrats, you won $5

Place your bet! (1 - 100 or 0 to quit): $1
Slot machine results:
  space  space  space
Sorry, you lost.

Place your bet! (1 - 100 or 0 to quit): $0

Thanks for playing at the Casino!
Your individual winnings were:
0 5 0 0 0 0 0 0 0 0 0 0 5 15 0 0 0 0 5 0 
Total winnings: $30
------------------------END RUN ---------------------- */