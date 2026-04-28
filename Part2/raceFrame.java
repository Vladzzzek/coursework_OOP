package Part2;
import Part2.Typist;
import Part2.TypingRace;
import javax.swing.*;
import java.awt.*;

public class raceFrame extends JFrame {
    private String passage;
    private Typist[] typists;
    private TypingRace typingRace;

    private JLabel passageLabel;
    private JLabel[] typistLabels;
    private JLabel[] typistNameLabels;

    public raceFrame(String passage, Typist[] typists, boolean autocorrect, boolean caffeine, boolean night) {
        this.passage = passage;
        this.typists = typists;

        setTitle("Race Simulator");
        setSize(1200, 800);
        setLayout(new BorderLayout());


        passageLabel = new JLabel(passage);
        add(passageLabel, BorderLayout.NORTH);

        JPanel lanesPanel = new JPanel(new GridLayout(typists.length, 1));
        typistLabels = new JLabel[typists.length];
        typistNameLabels = new JLabel[typists.length];

        for (int i = 0; i < typists.length; i++) {
            JPanel lane = new  JPanel(new BorderLayout());
            typistNameLabels[i] = new JLabel(typists[i].getName());
            typistLabels[i] = new JLabel(passage);
            lane.add(typistNameLabels[i], BorderLayout.WEST);
            lane.add(typistLabels[i], BorderLayout.CENTER);
            lanesPanel.add(lane);
        }
        add(lanesPanel, BorderLayout.CENTER);

        setVisible(true);

        typingRace = new TypingRace(passage.length());
        for (int i = 0; i < typists.length; i++) {
            typingRace.addTypist(typists[i]);
        }

        if(autocorrect) typingRace.applyDifficultyModifier("autocorrect");
        if(night) typingRace.applyDifficultyModifier("night");
        if(caffeine) typingRace.setCaffeineMode();

        typingRace.setRaceRunnable(() -> {
            for (int i = 0; i < typists.length; i++) {
                int progress = typists[i].getProgress();
                String typed = passage.substring(0, progress);
                String remaining =  passage.substring(progress);
                typistLabels[i].setText("<html>"
                        + "<span style='color:green'>" + typed + "</span>"
                        + "<span style='color:grey'>" + remaining + "</span>"
                        + "</html>");
            }
        });

        new Thread(() -> typingRace.startRace()).start();
    }
}
