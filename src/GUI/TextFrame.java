package GUI;

import Logic.BinaryConverter;
import Logic.Channel;
import Logic.Decoder;
import Logic.Encoder;

import javax.swing.*;
import java.awt.*;

public class TextFrame extends JFrame {
    /**
     * Sukuriamas grafinis interfeisas. Nekomentuotos dalys yra tiesiog grafinės sąsajos nusakymas.
     */
    public TextFrame() {
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

        JPanel originalTextLabelPanel = new JPanel();
        originalTextLabelPanel.add(new JLabel("Original text"));
        mainPane.add(originalTextLabelPanel);

        JPanel originalTextAreaPanel = new JPanel();
        final JTextArea originalTextArea = new JTextArea();
        originalTextArea.setPreferredSize(new Dimension(360, 100));
        originalTextAreaPanel.add(originalTextArea);
        mainPane.add(originalTextAreaPanel);

        JPanel nonEncodedTextLabelPanel = new JPanel();
        nonEncodedTextLabelPanel.add(new JLabel("Received non R(1,m) encoded text"));
        mainPane.add(nonEncodedTextLabelPanel);

        JPanel nonEncodedTextAreaPanel = new JPanel();
        final JTextArea nonEncodedTextArea = new JTextArea();
        nonEncodedTextArea.setPreferredSize(new Dimension(360, 100));
        nonEncodedTextAreaPanel.add(nonEncodedTextArea);
        mainPane.add(nonEncodedTextAreaPanel);

        JPanel encodedTextLabelPanel = new JPanel();
        encodedTextLabelPanel.add(new JLabel("Received R(1,m) encoded text"));
        mainPane.add(encodedTextLabelPanel);

        JPanel encodedTextAreaPanel = new JPanel();
        JTextArea encodedTextArea = new JTextArea();
        encodedTextArea.setPreferredSize(new Dimension(360, 100));
        encodedTextAreaPanel.add(encodedTextArea);
        mainPane.add(encodedTextAreaPanel);

        JPanel buttonPanel = new JPanel();
        JButton sendTextButton = new JButton("Send text");
        /**
         * Vykdomas paspaudus "send text" mygtuka
         */
        sendTextButton.addActionListener(e -> {
            // surenkama informacija is ivedimui skirtu lauku
            String message = originalTextArea.getText();
            int m = Integer.parseInt(mInput.getText());
            double probability = Double.parseDouble(probInput.getText());
            Channel channel = new Channel(probability);
            Encoder encoder = new Encoder(m);
            Decoder decoder = new Decoder(m);
            BinaryConverter converter = new BinaryConverter();
            // tekstas konvertuojamas i dvejetaine israiska
            String binaryMessage = converter.getBinaryString(message);

            // siunciamas dvejetainis tekstas nenaudojant kodo, konvertuojamas atgal is dvejetainio teksto, parodomas ekrane
            String nonEncodedBinaryResult= channel.sendData(binaryMessage);
            nonEncodedTextArea.setText(converter.getUTF8String(nonEncodedBinaryResult));

            // dvejetainis tekstas papildomas 0 kad butu reikiamo ilgio, uzkoduojamas R(1,m) kodu, siunciamas kanalu
            String encodedBinaryString = encoder.encode(addMissingZeroes(binaryMessage, m));
            String encodedBinaryResult = channel.sendData(encodedBinaryString);
            // atstatoma tarnybine informacija
            encodedBinaryResult = removeUtilityErrors(encodedBinaryResult, m);
            // dekoduojamas dvejetainis tekstas
            String decodedBinaryResult = decoder.decode(encodedBinaryResult);
            // isimama tarnybine informacija is dvejetainio dekoduoto teksto
            // dvejetainis tekstas konvertuojamas atgal is dvejetainio teksto
            // parodomas ekrane
            String originalMessage = converter.getUTF8String(decodedBinaryResult.substring((m + 1) - (binaryMessage.length() % (m + 1))));
            encodedTextArea.setText(originalMessage);
        });
        buttonPanel.add(sendTextButton);
        mainPane.add(buttonPanel);

        this.add(mainPane);
        this.setTitle("Send text through channel");
        this.setSize(400, 550);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
    }

    /**
     * Papildo teksta iki reikiamo skaidymui ilgio
     * @param binary - dvejetainis tekstas
     * @param m - R(1,m) kodo parametras
     * @return dvejetainis tekstas papildytas nuliais (pradzioje) iki reikiamo ilgio
     */
    private String addMissingZeroes(String binary, int m) {
        StringBuilder builder = new StringBuilder();
        int diff = (m + 1) - (binary.length() % (m + 1));
        for (int i = 0; i < diff; i++) {
            builder.append("0");
        }
        builder.append(binary);
        return builder.toString();
    }

    /**
     * Istaiso tarnybine informacija vektoriaus pradzioje
     * @param binary - is kanalo isejusi infoirmacija
     * @param m - kodo R(1,m) parametras
     * @return vektorius su istaisyta tarnybine informacija
     */
    private String removeUtilityErrors(String binary, int m) {
        int diff = (m + 1) - (binary.length() % (m + 1));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < diff; i++) {
            builder.append("0");
        }
        builder.append(binary.substring(diff));
        return builder.toString();
    }
}
