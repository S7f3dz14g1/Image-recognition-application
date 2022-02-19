import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.train.strategy.ResetStrategy;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.platformspecific.j2se.TrainingDialog;
import org.encog.platformspecific.j2se.data.image.ImageMLData;
import org.encog.platformspecific.j2se.data.image.ImageMLDataSet;
import org.encog.util.downsample.Downsample;
import org.encog.util.downsample.RGBDownsample;
import org.encog.util.downsample.SimpleIntensityDownsample;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Network {
    private final ArrayList<Picture> imageList = new ArrayList<>();
    private ImageMLDataSet trening;
    private BasicNetwork network;
    private Downsample downsample;
    private int numberDowsample ;
    private String typePicture;
    private int hiddenLayer1;
    private int hiddenLayer2;
    private double error;
    private int cycle;
    private int outputs;
    private double errorNetwork;
    private ArrayList<String> ID = new ArrayList<>();

    public Network(int numberDowsample , String typePicture, int hiddenLayer1, int hiddenLayer2, double error, int cycle) throws IOException {
        this.numberDowsample  = numberDowsample ;
        this.typePicture = typePicture;
        this.hiddenLayer1 = hiddenLayer1;
        this.hiddenLayer2 = hiddenLayer2;
        this.error = error;
        this.cycle = cycle;
        outputs = 0;

    }

    public void addPictures(int ile, String nazwa) throws IOException {
        for (int i = 1; i < ile; ++i) {
            assignId(nazwa);
            String cos= "daneTreningowe\\"+nazwa+" ("+i+").png";
            imageList.add(new Picture(new File(cos), ID.size() - 1));
        }

    }

    public void creatingTrening() {
        if (typePicture.equals("RGB"))
            downsample = new RGBDownsample();
        else
            downsample = new SimpleIntensityDownsample();
        trening = new ImageMLDataSet(downsample, false,
                1, -1);
    }

    private void assignId(String name) {
        if (!ID.contains(name)) {
            ID.add(name);
            ++this.outputs;
        }
    }

    public void learningProcess() throws IOException {
        for (Picture image : imageList) {
            final MLData ideal = new BasicMLData(outputs);
            for (int i = 0; i < outputs; i++) {
                if (i == image.getId()) {
                    ideal.setData(i, 1);
                } else {
                    ideal.setData(i, -1);
                }
            }
            trening.add(new ImageMLData(ImageIO.read(image.getPlik())), ideal);
        }
        trening.downsample(numberDowsample , numberDowsample );
        network = createNetwork(trening.getInputSize(), hiddenLayer1, hiddenLayer2,
                trening.getIdealSize());
    }

    public void processTrain() throws IOException, InterruptedException {
        ResilientPropagation train = new ResilientPropagation(network, trening);
        train.addStrategy(new ResetStrategy(error, cycle));
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TrainingDialog.trainDialog(train, network,trening);
                errorNetwork=train.getError();
            }
        });
        thread.start();
    }

    public String result(File image) throws IOException {
        Image img = ImageIO.read(image);
        ImageMLData input = new ImageMLData(img);
        input.downsample(downsample, false, numberDowsample ,
                numberDowsample , 1.0, -1.0);
        return ID.get(network.winner(input));
    }

    public Double getError(){
        return errorNetwork;
    }

    private BasicNetwork createNetwork(int input, int hidden1,
                                       int hidden2, int output) {
        FeedForwardPattern pattern = new FeedForwardPattern();
        pattern.setInputNeurons(input);
        pattern.setOutputNeurons(output);
        pattern.setActivationFunction(new ActivationTANH());
        if (hidden1 > 0) {
            pattern.addHiddenLayer(hidden1);
        }
        if (hidden2 > 0) {
            pattern.addHiddenLayer(hidden2);
        }
        BasicNetwork network = (BasicNetwork) pattern.generate();
        network.reset();
        return network;
    }
}