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

        JPanel originalVectorOutputPane = new JPanel();
        JLabel originalVector = new JLabel("Encoded vector");
        JTextField originalVectorField = new JTextField();
        originalVectorField.setPreferredSize(new Dimension(200, 20));

        originalVectorOutputPane.add(originalVector);
        originalVectorOutputPane.add(originalVectorField);
        mainPane.add(originalVectorOutputPane);

        JPanel errorTooltipPanel = new JPanel();
        JLabel errorTooltip = new JLabel("Errors will be displayed in red font");
        errorTooltipPanel.add(errorTooltip);
        mainPane.add(errorTooltipPanel);

        JPanel vectorOutputPane = new JPanel();
        JLabel outputVectorLabel = new JLabel("Received vector");
        JEditorPane editor = new JEditorPane("text/html", "");
        editor.setPreferredSize(new Dimension(200, 15));

        vectorOutputPane.add(outputVectorLabel);
        vectorOutputPane.add(editor);
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
            // uzkoduojamas vektorius, siunciamas kanalu
            String encodedString = encoder.encode(vectorText);
            String receivedString = channel.sendData(encodedString);
            //tikrinamos padarytos klaidos
            char[] original = encodedString.toCharArray();
            char[] modified = receivedString.toCharArray();
            StringBuilder builder = new StringBuilder();
            builder.append("<span style=\"font-size: 12px;\">");
            int counter = 0;
            for (int i = 0; i < receivedString.length(); i++) {
                if (original[i] == modified[i]) {
                    builder.append(modified[i]);
                }
                else {
                    builder.append("<span style=\"color: red;\">");
                    builder.append(modified[i]);
                    builder.append("</span>");
                    counter++;
                }
            }
            builder.append("</span>");
            originalVectorField.setText(encodedString);
            editor.setText(builder.toString());
            outputVectorLabel.setText("Received vector (" + counter + " errors)");
        });
        JButton decodeVector = new JButton("Decode vector");
        /**
         * Dekoduojamas is kanalo isejes vektorius ir parodomas ekrane
         */
        decodeVector.addActionListener(e -> {
            String receivedMessage;
            try {
                receivedMessage = editor.getDocument().getText(0, editor.getDocument().getLength());
            }
            catch (Exception exc) {
                receivedMessage = "";
                exc.printStackTrace();
            }
            int m = Integer.parseInt(mInput.getText());
            receivedMessage = receivedMessage.substring(1);
            Decoder decoder = new Decoder(m);
            String decodedString = decoder.decode(receivedMessage);
            decodedVectorField.setText(decodedString);
        });

        controlPane.add(sendVector);
        controlPane.add(decodeVector);
        mainPane.add(controlPane);

        this.add(mainPane);
        this.setTitle("Send vector through channel");
        this.setSize(400, 310);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
    }
}
