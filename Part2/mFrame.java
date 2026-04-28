package Part2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mFrame implements ActionListener {
    public mFrame() {
        //LAYOUT
        JFrame frame = new JFrame("Typing Race Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        //top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));


        JButton raceConfig = new JButton("Race Configuration");
        JButton typistConfig = new JButton("Typists Configuration");

        topPanel.add(raceConfig);
        topPanel.add(typistConfig);
        frame.add(topPanel,  BorderLayout.NORTH);

        //main panel
        JPanel box1 = new JPanel();
        JPanel box2 = new JPanel();
        JPanel box3 = new JPanel();

        //grid of main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        mainPanel.add(box1, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        mainPanel.add(box2, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        mainPanel.add(box3, c);

        frame.add(mainPanel, BorderLayout.CENTER);

        //COMPONENTS
        //box1 components
        box1.setLayout(new BoxLayout(box1, BoxLayout.Y_AXIS));

        JLabel passageLabel = new JLabel("Select Passage");
        JRadioButton shortPassage = new JRadioButton("The sun dipped below the horizon as a cool breeze...");
        JRadioButton mediumPassage = new JRadioButton("In the heart of the bustling city, people hurried along...");
        JRadioButton longPassage = new JRadioButton("Far beyond the rolling hills and winding rivers, there was a...");
        JRadioButton customPassage = new JRadioButton("Custom");
        JTextField passageText = new JTextField();

        ButtonGroup passageGroup = new ButtonGroup();
        passageGroup.add(shortPassage);
        passageGroup.add(mediumPassage);
        passageGroup.add(longPassage);
        passageGroup.add(customPassage);

        box1.add(passageLabel);
        box1.add(shortPassage);
        box1.add(mediumPassage);
        box1.add(longPassage);
        box1.add(customPassage);
        box1.add(passageText);


        //box2 components
        box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));

        JLabel playersLabel = new JLabel("Seat count");
        JLabel playersLabel2 = new JLabel("Choose how many players will compete(2-6)");
        JComboBox playersCount = new JComboBox<>(new Integer[]{2, 3, 4, 5, 6});

        playersCount.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        box2.add(playersLabel);
        box2.add(playersLabel2);
        box2.add(playersCount);

        //box3 components
        JRadioButton autocorrectRadio = new JRadioButton("Autocorrect On/Off");
        JRadioButton caffeineRadio = new JRadioButton("Caffeine Mode");
        JRadioButton nightRadio = new JRadioButton("Night Shift");

        box3.add(autocorrectRadio);
        box3.add(caffeineRadio);
        box3.add(nightRadio);

        //bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        frame.add(bottomPanel, BorderLayout.SOUTH);

        JButton statsButton = new JButton("Statistics");
        JButton startButton = new JButton("Start Race");

        bottomPanel.add(startButton);
        bottomPanel.add(statsButton);

        //main frame
        frame.add(mainPanel);
        frame.setVisible(true);

        //radio buttons actions
        passageText.setEnabled(false);

        customPassage.addActionListener(e -> passageText.setEnabled(true));
        shortPassage.addActionListener(e -> passageText.setEnabled(false));
        mediumPassage.addActionListener(e -> passageText.setEnabled(false));
        longPassage.addActionListener(e -> passageText.setEnabled(false));

        //start button actions
        startButton.addActionListener(e -> {
            String passage = "";
            if (shortPassage.isSelected()) {
                passage = PassageText.getPassage(0);
            } else if (mediumPassage.isSelected()) {
                passage = PassageText.getPassage(1);
            } else if (longPassage.isSelected()) {
                passage = PassageText.getPassage(2);
            } else if (customPassage.isSelected()) {
                passage = passageText.getText();
            }

            Object selected = playersCount.getSelectedItem();

            int playersC = (Integer) playersCount.getSelectedItem();

            Typist[] typists = new Typist[playersC];
            switch (playersC){
                case 2:
                    typists[0] = new Typist('1', "TURBOFINGERS", 0.3);
                    typists[1] = new Typist('2', "MIDDLEWALLS", 0.4);
                    break;
                case 3:
                    typists[0] = new Typist('1', "TURBOFINGERS", 0.3);
                    typists[1] = new Typist('2', "MIDDLEWALLS", 0.4);
                    typists[2] = new Typist('3', "BRAZILS", 0.7);
                    break;
                case 4:
                    typists[0] = new Typist('1', "TURBOFINGERS", 0.3);
                    typists[1] = new Typist('2', "MIDDLEWALLS", 0.4);
                    typists[2] = new Typist('3', "BRAZILS", 0.7);
                    typists[3] = new Typist('4', "UWA", 0.8);
                    break;
                case 5:
                    typists[0] = new Typist('1', "TURBOFINGERS", 0.3);
                    typists[1] = new Typist('2', "MIDDLEWALLS", 0.4);
                    typists[2] = new Typist('3', "BRAZILS", 0.7);
                    typists[3] = new Typist('4', "UWA", 0.8);
                    typists[4] = new Typist('5', "HUNT_N_PECK", 0.2);
                    break;
                case 6:
                    typists[0] = new Typist('1', "TURBOFINGERS", 0.3);
                    typists[1] = new Typist('2', "MIDDLEWALLS", 0.4);
                    typists[2] = new Typist('3', "BRAZILS", 0.7);
                    typists[3] = new Typist('4', "UWA", 0.8);
                    typists[4] = new Typist('5', "HUNT_N_PECK", 0.2);
                    typists[5] = new Typist('6', "UUUUU", 0.9);
            }



            new raceFrame(passage, typists, autocorrectRadio.isSelected(), caffeineRadio.isSelected(), nightRadio.isSelected());
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new mFrame();
    }
}
