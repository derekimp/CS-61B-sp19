import edu.princeton.cs.algs4.StdAudio;
import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private GuitarString[] stringArray = new GuitarString[37];

    private double frequency(int index) {
        return 440. * Math.pow(2, (index-24)/12);
    }

    private void buildStringArray() {
        for (int i=0; i<37; i+=1) {
            GuitarString temp = new GuitarString(frequency(i));
            stringArray[i] = temp;
        }
    }

    public GuitarHero() {
        buildStringArray();
    }

    public GuitarString[] getGuitarString() {
        return stringArray;
    }

    public static void main(String[] args) {
        GuitarHero g = new GuitarHero();
        GuitarString[] stringArray = g.getGuitarString();

        while (true) {

            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1){
                    stringArray[index].pluck();
                }
            }

            double sample = 0;
            for (int i=0; i<37; i+=1) {
                sample += stringArray[i].sample();
            }

            StdAudio.play(sample);

            for (int i=0; i<37; i+=1) {
                stringArray[i].tic();
            }
        }
    }
}
