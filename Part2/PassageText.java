package Part2;

public class PassageText {
    public static final String[] PASSAGES = {
            "The sun dipped below the horizon as a cool breeze swept through " +
                    "the quiet town. Streetlights flickered on, and the " +
                    "sound of distant laughter echoed softly in the evening air.",
            "In the heart of the bustling city, people hurried along crowded " +
                    "sidewalks, each lost in their own thoughts and destinations. " +
                    "Bright signs lit up the streets while cars streamed past in a " +
                    "steady flow. Despite the chaos, there was a rhythm to it all, " +
                    "a kind of harmony " +
                    "that made the city feel alive and constantly moving forward.",
            "Far beyond the rolling hills and winding rivers, there was a small " +
                    "village where time seemed to move a little slower. Every " +
                    "morning, the villagers would gather in the square to exchange " +
                    "stories, share fresh bread, and greet the day with a sense of " +
                    "calm purpose. Children played near the fountain while elders " +
                    "sat on wooden benches, watching life unfold with quiet " +
                    "contentment. As seasons changed, the village transformed " +
                    "in color and mood, yet its spirit remained the same—steady, " +
                    "warm, and deeply connected. Visitors who arrived by chance " +
                    "often found themselves staying longer than planned, drawn in " +
                    "by the simple beauty and the unspoken feeling that, " +
                    "just for a moment, everything was exactly as it should be."
    };
    public static String customPassage;

    public static void setPassage(String passage) {
        if (passage != null) {
            customPassage = passage;
        }
    }

    public static String getCustomPassage() {
        return customPassage;
    }

    public static String getPassage(int i) {
        return PASSAGES[i];
    }
}
