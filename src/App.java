
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class App extends JFrame implements ActionListener {
    private JCheckBox CheckBoxCat,CheckBoxDog, CheckBoxMotobike,CheckBoxPlane;
    private  ButtonGroup numberDownsamoke=new ButtonGroup();
    private JRadioButton radio8,radio16,radio32;
    private JFileChooser open=new JFileChooser();
    private File file=new File("first.PNG");
    private int numberPixel=8;
    private JComponent jComponent;
    private JButton buuttonCreateTraning,buttonAddPicture,buttonRerult,buttonReset,buttonError;
    private boolean flagCat,flagDog,flagPlane,flagMotobike;
    private JLabel firstText,secondText,thirdText,fourthText,errorText;
    private Network network;
    private ImageIcon icon=new ImageIcon();
    private JLabel picture=new JLabel();

    private void setImage() throws IOException {
        Image img = ImageIO.read(file);
        icon.setImage(img);
        picture.setIcon(icon);
        picture.setBounds(370,50,250,250);
        add(picture);
    }

    public  App() throws IOException {
        createWindow();
        setCheckBox();
        setButton();
        setRadio();
        setText();
        setImage();
        resetBox();
    }

    private String Result() throws IOException {
        return network.result(file);
    }

    private void setText(){
        firstText=new JLabel("Select settings network");
        firstText.setBounds(55,5,150,20);
        add(firstText);

        thirdText=new JLabel("Images");
        thirdText.setBounds(5,25,150,20);
        add(thirdText);

        fourthText=new JLabel("Number of downsample");
        fourthText.setBounds(155,25,250,20);
        add(fourthText);

        secondText=new JLabel("Result: ");
        secondText.setBounds(210,260,150,30);
        add(secondText);

        errorText=new JLabel("");
        errorText.setBounds(135,175,250,20);
        add(errorText);
    }

    private void setTextResult() throws IOException {
        secondText.setText("Result: "+Result());
    }

    private void setTextError(boolean b){
        if(b){
           String text=" "+(network.getError()*100);
           if(network.getError()*100>100)
               errorText.setText("The error is equal 100%");
           else
            errorText.setText("The error is equal "+text.substring(0,9)+"%");
        }
        else
            errorText.setText(" ");
    }

    private void setRadio(){
        radio8=new JRadioButton("8",true);
        radio8.setBounds(150,45,50,20);
        numberDownsamoke.add(radio8);
        add(radio8);
        radio8.addActionListener(this);

        radio16=new JRadioButton("16",false);
        radio16.setBounds(150,65,50,20);
        numberDownsamoke.add(radio16);
        add(radio16);
        radio16.addActionListener(this);

        radio32=new JRadioButton("32",false);
        radio32.setBounds(150,85,50,20);
        numberDownsamoke.add(radio32);
        add(radio32);
        radio32.addActionListener(this);
    }

    private void setCheckBox(){

        CheckBoxCat = new JCheckBox("Cat");
        CheckBoxCat.setBounds(20,45,100,20);
        add(CheckBoxCat);
        CheckBoxCat.addActionListener(this);

        CheckBoxDog = new JCheckBox("Dog");
        CheckBoxDog.setBounds(20,65,100,20);
        add(CheckBoxDog);
        CheckBoxDog.addActionListener(this);

        CheckBoxMotobike = new JCheckBox("Motobike");
        CheckBoxMotobike.setBounds(20,105,100,20);
        add(CheckBoxMotobike);
        CheckBoxMotobike.addActionListener(this);

        CheckBoxPlane = new JCheckBox("Plane");
        CheckBoxPlane.setBounds(20,85,100,20);
        add(CheckBoxPlane);
        CheckBoxPlane.addActionListener(this);

    }

    private void setButton(){

        buttonError=new JButton("Check Error");
        buttonError.setBounds(5,175,125,20);
        add(buttonError);
        buttonError.addActionListener(this);
        buttonError.setEnabled(false);

        buuttonCreateTraning=new JButton("Traning");
        buuttonCreateTraning.setBounds(140,135,100,20);
        add(buuttonCreateTraning);
        buuttonCreateTraning.addActionListener(this);

        buttonReset=new JButton("Reset");
        buttonReset.setBounds(240,135,100,20);
        add(buttonReset);
        buttonReset.addActionListener(this);

        buttonRerult=new JButton("Check:");
        buttonRerult.setBounds(200,240,100,20);
        add(buttonRerult);
        buttonRerult.addActionListener(this);
        buttonRerult.setEnabled(false);

        buttonAddPicture=new JButton("Find Image:");
        buttonAddPicture.setBounds(435,25,100,20);
        add(buttonAddPicture);
        buttonAddPicture.addActionListener(this);
    }

    private void createWindow(){
        setSize(650,350);
        setTitle("Aplikacj do rozpozawania obiektow");
        setLayout(null);
    }

    private void resetFlag(){
        flagDog=false;
        flagMotobike=false;
        flagCat=false;
        flagPlane=false;
    }

    private boolean checkBox(){
        if(checkBoxs()>=2)
            return true;
        else
            JOptionPane.showMessageDialog(null,"Choose at least two types of images");
        return false;
    }

    private int checkBoxs(){
        int i=0;
        if(flagPlane)
            i++;
        if(flagCat)
            i++;
        if(flagMotobike)
            i++;
        if(flagDog)
            i++;
        return i;
    }

    private void resetBox(){
        CheckBoxDog.getDisabledSelectedIcon();
        CheckBoxCat.getDisabledSelectedIcon();
        CheckBoxMotobike.getDisabledSelectedIcon();
        CheckBoxPlane.getDisabledSelectedIcon();
        buttonError.removeNotify();
        buttonRerult.removeNotify();
    }

    private void checkFlag() throws IOException {
        if(flagPlane)
            network.addPictures(250,"plane");
        if(flagCat)
            network.addPictures(500,"cat");
        if(flagMotobike)
            network.addPictures(300,"motobike");
        if(flagDog)
            network.addPictures(500,"dog");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object == CheckBoxCat) {
            if (CheckBoxCat.isSelected())
                flagCat=true;
            else
                flagCat=false;

        }else if(object==buttonError){
            setTextError(true);
        }
        else if (object == CheckBoxDog) {
            if (CheckBoxDog.isSelected())
                if(CheckBoxDog.isSelected())
                    flagDog=true;
                else
                    flagDog=false;
        } else if (object == CheckBoxMotobike) {
            if (CheckBoxMotobike.isSelected())
                flagMotobike=true;
            else
                flagMotobike=false;
        } else if (object == CheckBoxPlane) {
            if (CheckBoxPlane.isSelected())
                flagPlane=true;
            else
                flagPlane= false;
        }
        else if(object==buuttonCreateTraning){
            if(checkBox()) {
                try {
                    network=new Network(numberPixel,"RGB",270,125,0.01,1000);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    checkFlag();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                network.creatingTrening();
                try {
                    network.learningProcess();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    network.processTrain();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
              buuttonCreateTraning.removeNotify();//zablokowanie przycisku
                buttonError.setEnabled(true);
                buttonError.addNotify();

            }
        }else if(object==buttonRerult){
            try {
                setTextResult();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else if(object==buttonAddPicture){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(open.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                        file=open.getSelectedFile();
                        Image img = null;
                        try {
                            img = ImageIO.read(file);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (checkEnlargement()==true){
                            icon.setImage(img);
                            picture.setIcon(icon);
                        }else {
                            JOptionPane.showMessageDialog(null,"The file has a wrong extension. Choose again.");
                        }
                        buttonRerult.setEnabled(true);
                        buttonRerult.addNotify();

                    }
                }
            });
            thread.start();

        }else if(object==radio8){
                if(radio8.isSelected()){
                    numberPixel=8;
                }
        }else if(object==radio16){
            if(radio16.isSelected()){
                numberPixel=16;
            }
        }else if(object==radio32){
            if(radio32.isSelected()){
                numberPixel=32;
            }
        }else if(object==buttonReset){
            resetFlag();
            buuttonCreateTraning.addNotify();
            buttonRerult.removeNotify();
            resetBox();
            setTextError(false);
        }
    }

    private boolean checkEnlargement(){
        if(file.getName().endsWith("PNG"))
            return true;
        return false;
    }
}

/*
 JOptionPane.showMessageDialog(null,"text "+File.getName());//okno do walidacji
 */

//zmienić zdj na ang nazwy