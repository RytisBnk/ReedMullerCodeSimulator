package GUI;

import Logic.Channel;
import Logic.Decoder;
import Logic.Encoder;

import javax.swing.*;
import java.awt.*;

class VectorFrame extends JFrame {
    /**
     * Nekomentuota dalis kodo nurodo kaip turi atrodyti langas.
     */
    VectorFrame() {
        super();

        JPanel mainPane = new JPanel();

        JPanel mInputPane = new JPanel();

        JLabel mLabel = new JLabel("R(1, M) : M = ");
        final JTextField mInput = new JTextField();
        mInput.setSize(100, 40);
        mInput.setPreferredSize(new Dimension(50, 20));

        mInputPane.add(mLabel);
        mInputPane.add(mInput);
        mainPane.add(mInputPane);

        JPanel probInputPane = new JPanel();
        JLabel probLabel = new JLabel("Error probability");
        final JTextField probInput = new JTextField();
        probInput.setPreferredSize(new Dimension(75, 20));

        probInputPane.add(probLabel);
        probInputPane.add(probInput);
        mainPane.add(probInputPane);

        JPanel vectorInputPane = new JPanel();
        JLabel vector = new JLabel("Original vector");
        final JTextField vectorInput = new JTextField();
        vectorInput.setPreferredSize(new Dimension(200, 20));

        vectorInputPane.add(vector);
        vectorInputPane.add(vectorInput);
        mainPane.add(vectorInputPane);

        JPanel vectorOutputPane = new JPanel();
        JLabel outputVectorLabel = new JLabel("Received vector");
        final JTextField outputVectorField = new JTextField();
        outputVectorField.setPreferredSize(new Dimension(200, 20));

        vectorOutputPane.add(outputVectorLabel);
        vectorOutputPane.add(outputVectorField);
        mainPane.add(vectorOutputPane);

        JPanel decodedVectorPane = new JPanel();
        JLabel decodedVectorLabel = new JLabel("Decoded vector");
        final JTextField decodedVectorField = new JTextField();
        decodedVectorField.setPreferredSize(new Dimension(200, 20));

        decodedVectorPane.add(decodedVectorLabel);
        decodedVectorPane.add(decodedVectorField);
        mainPane.add(decodedVectorPane);

        JPanel controlPane = new JPanel();
        JButton sendVector = new JButton("Send vector");
        /**
         * Siunciamas vektorius kanalu
         */
        sendVector.addActionListener(e -> {
            // surenkami ivesties duoemenys
            String vectorText = vectorInput.getText();
            int m = Integer.parseInt(mInput.getText());
            double probability = Double.parseDouble(probInput.getText());
            Encoder encoder = new Encoder(m);
            Channel channel = new Channel(probability);
            // uzkoduojamas vektorius, siunciamas kanalu, parodomas is kanalo isejes vektorius
            String encodedString = encoder.encode(vectorText);
            String receivedString = channel.sendData(encodedString);
            outputVectorField.setText(receivedString);
        });
        JButton decodeVector = new JButton("Decode vector");
        /**
         * Dekoduojamas is kanalo isejes vektorius ir parodomas ekrane
         */
        decodeVector.addActionListener(e -> {
            String receivedMessage = outputVectorField.getText();
            int m = Integer.parseInt(mInput.getText());
            Decoder decoder = new Decoder(m);
            String decodedString = decoder.decode(receivedMessage);
            decodedVectorField.setText(decodedString);
        });

        controlPane.add(sendVector);
        controlPane.add(decodeVector);
        mainPane.add(controlPane);

        this.add(mainPane);
        this.setTitle("Send vector through channel");
        this.setSize(400, 210);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
    }
}
