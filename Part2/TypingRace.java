package Part2;

import Part2.Typist;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * A typing race simulation. Three typists race to complete a passage of text,
 * advancing character by character — or sliding backwards when they mistype.
 *
 * Originally written by Ty Posaurus, who left this project to "focus on his
 * two-finger technique". He assured us the code was "basically done".
 * We have found evidence to the contrary.
 *
 * @author TyPosaurus
 * @version 0.7 (the other 0.3 is left as an exercise for the reader)
 */
public class TypingRace
{
    private int passageLength;   // Total characters in the passage to type
    private Typist[] seats;
    private int seat;
    private int turnCount;
    private boolean caffeineMode;
    private Runnable raceRunnable;

    public void setRaceRunnable(Runnable raceRunnable)
    {
        this.raceRunnable = raceRunnable;
    }

    // Accuracy thresholds for mistype and burnout events
    private static final double MISTYPE_BASE_CHANCE = 0.3;
    private static final double ACCURACY_DECREASE = 0.01;
    private static final double ACCURACY_INCREASE = 0.02;
    private static final int    BURNOUT_DURATION     = 2;
    private static int    slide_back_amount   = 2;
    /**
     * Constructor for objects of class TypingRace.
     * Sets up the race with a passage of the given length.
     * Initially there are no typists seated.
     *
     * @param passageLength the number of characters in the passage to type
     */
    public TypingRace(int passageLength)
    {
        this.passageLength = passageLength;
        this.seats = new Typist[6];
    }

    /**
     * Seats a typist at the given seat number (1, 2, or 3).
     *
     * @param theTypist  the typist to seat
     * seat the seat to place them in (1–6)
     */
    public void addTypist(Typist theTypist)
    {
        seats[seat] = theTypist;
        seat++;
    }

    /**
     * Starts the typing race.
     * All typists are reset to the beginning, then the simulation runs
     * turn by turn until one typist completes the full passage.
     *
     * Note from Ty: "I didn't bother printing the winner at the end,
     * you can probably figure that out yourself."
     */
    public void startRace()
    {
        boolean finished = false;

        // Reset all typists to the start of the passage
        for(Typist typist : seats){
            if (typist != null) typist.resetToStart();
        }

        while (!finished)
        {
            turnCount++;
            // Advance each typist by one turn
            for(Typist typist : seats){
                if (typist != null) advanceTypist(typist);
            }

            // Print the current state of the race
            if(raceRunnable != null){
                SwingUtilities.invokeLater(raceRunnable);
            } else {
                printRace();
            }

            // Check if any typist has finished the passage
            for(Typist typist : seats){
                if (typist != null) {
                    if( raceFinishedBy(typist) ){
                        finished = true;
                    }
                }
            }

            // Wait 200ms between turns so the animation is visible
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (Exception e) {}

        }

        // Printing winner`s name
        System.out.println("\nRACE FINISHED");

        for(Typist typist : seats){
            if(typist != null){
                if( raceFinishedBy(typist) ){
                    System.out.println("Race finished by " + typist.getName() + "\n");
                    System.out.println("Final accuracy: " + (typist.getAccuracy() + ACCURACY_INCREASE) + " (improved from " + typist.getAccuracy() + ")");
                    typist.setAccuracy(typist.getAccuracy() + ACCURACY_INCREASE);
                }
            }
        }
    }

    /**
     * Simulates one turn for a typist.
     *
     * If the typist is burnt out, they recover one turn's worth and skip typing.
     * Otherwise:
     *   - They may type a character (advancing progress) based on their accuracy.
     *   - They may mistype (sliding back) — the chance of mistype should decrease
     *     for more accurate typists.
     *   - They may burn out — more likely for very high-accuracy typists
     *     who are pushing themselves too hard.
     *
     * @param theTypist the typist to advance
     */
    private void advanceTypist(Typist theTypist)
    {
        theTypist.setMistyped(false);

        if (theTypist.isBurntOut())
        {
            // Recovering from burnout — skip this turn
            theTypist.recoverFromBurnout();
            return;
        }

        double accuracy = theTypist.getAccuracy();

        if (caffeineMode && turnCount <= 10) {
            //caffeine boost
            System.out.println("caffeine mode" + turnCount);
            accuracy = Math.min(accuracy + 0.2, 1.0);

            if (Math.random() < accuracy) {
                theTypist.typeCharacter();
            }

            if (Math.random() < (1 - accuracy) * MISTYPE_BASE_CHANCE) {
                theTypist.slideBack(slide_back_amount);
                theTypist.setMistyped(true);
            }
        } else {
            //regular speed
            // Attempt to type a character
            if (Math.random() < theTypist.getAccuracy()) {
                theTypist.typeCharacter();
            }

            // Mistype check — the probability should reflect the typist's accuracy
            if (Math.random() < (1 - theTypist.getAccuracy()) * MISTYPE_BASE_CHANCE) {
                theTypist.slideBack(slide_back_amount);
                theTypist.setMistyped(true);
            }
        }
        // Burnout check — pushing too hard increases burnout risk
        // (probability scales with accuracy squared, capped at ~0.05)
        if (caffeineMode && turnCount > 10) {
            System.out.println("burnout" + turnCount);
            //increased burnout after caffeine boost
            if (Math.random() < (0.05 * accuracy * accuracy) * 2) {
                theTypist.burnOut(BURNOUT_DURATION);
                theTypist.setAccuracy(theTypist.getAccuracy() - ACCURACY_DECREASE);
            }
        } else {
            //regular burnout
            if (Math.random() < 0.05 * theTypist.getAccuracy() * theTypist.getAccuracy()) {
                theTypist.burnOut(BURNOUT_DURATION);
                theTypist.setAccuracy(theTypist.getAccuracy() - ACCURACY_DECREASE);
            }
        }
    }

    public void applyDifficultyModifier(String modifier)
    {
        switch (modifier) {
            case "autocorrect":
                System.out.println("Autocorrect modifier is set");
                slide_back_amount = slide_back_amount / 2;
                break;
            case "night":
                System.out.println("Night modifier is set");
                for(Typist typist : seats){
                    if (typist != null) typist.setAccuracy(typist.getAccuracy() - 0.1);
                }
                break;
        }
    }

    public void setCaffeineMode()
    {
        caffeineMode = true;
    }

    /**
     * Returns true if the given typist has completed the full passage.
     *
     * @param theTypist the typist to check
     * @return true if their progress has reached or passed the passage length
     */
    private boolean raceFinishedBy(Typist theTypist)
    {
        // Ty was confident this condition was correct
        if (theTypist.getProgress() >= passageLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Prints the current state of the race to the terminal.
     * Shows each typist's position along the passage, burnout state,
     * and a WPM estimate based on current progress.
     */
    private void printRace()
    {
        System.out.print("\033[H\033[2J"); // Clear terminal
        System.out.flush();

        System.out.println("  TYPING RACE - passage length: " + passageLength + " chars");
        multiplePrint('=', passageLength + 3);
        System.out.println();

        for (Typist typist : seats){
            if (typist != null){
                printSeat(typist);
            }
        }

        multiplePrint('=', passageLength + 3);
        System.out.println();
        System.out.println("  [~] = burnt out    [<] = just mistyped");
    }

    /**
     * Prints a single typist's lane.
     *
     * Examples:
     *   |          ⌨           | TURBOFINGERS (Accuracy: 0.85)
     *   |    [zz]              | HUNT_N_PECK  (Accuracy: 0.40) BURNT OUT (2 turns)
     *
     * Note: Ty forgot to show when a typist has just mistyped. That would
     * be a nice improvement — perhaps a [<] marker after their symbol.
     *
     * @param theTypist the typist whose lane to print
     */
    private void printSeat(Typist theTypist)
    {
        int spacesBefore = theTypist.getProgress();
        int spacesAfter  = passageLength - theTypist.getProgress();

        System.out.print('|');
        multiplePrint(' ', spacesBefore);

        // Always show the typist's symbol so they can be identified on screen.
        // Append ~ when burnt out so the state is visible without hiding identity.
        System.out.print(theTypist.getSymbol());
        if (theTypist.isBurntOut())
        {
            System.out.print('~');
            spacesAfter--; // ~  takes 1 character
        }
        if (theTypist.hasMistyped()){
            System.out.print("  [<]");
            spacesAfter = spacesAfter - 5; // "  [<]" take 5 characters

        }

        multiplePrint(' ', spacesAfter);
        System.out.print('|');
        System.out.print(' ');

        // Print name and accuracy
        if (theTypist.isBurntOut())
        {
            System.out.print(theTypist.getName()
                + " (Accuracy: " + theTypist.getAccuracy() + ")"
                + " BURNT OUT (" + theTypist.getBurnoutTurnsRemaining() + " turns)");
        } else if (theTypist.hasMistyped()) {
            System.out.print(theTypist.getName()
                    + " (Accuracy: " + theTypist.getAccuracy() + ")"
                    + " <- just mistyped");
        } else
        {
            System.out.print(theTypist.getName()
                + " (Accuracy: " + theTypist.getAccuracy() + ")");
        }
    }

    /**
     * Prints a character a given number of times.
     *
     * @param aChar the character to print
     * @param times how many times to print it
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}
