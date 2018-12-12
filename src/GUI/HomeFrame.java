package GUI;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {
    /**
     * Sukuriamas grafinis interfeisas. Visi metodai cia skirti lango issidestymo nusakymui.
     */
    public HomeFrame() {
        super();

        this.setSize(400, 80);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setTitle("Reed-Muller code sending simulator");
        JPanel panel = new JPanel();

        JButton sendVector = new JButton();
        sendVector.setText("Vector");
        sendVector.addActionListener(e -> new VectorFrame());

        JButton sendText = new JButton();
        sendText.setText("Text");
        sendText.addActionListener(e -> new TextFrame());

        panel.add(sendVector);
        panel.add(sendText);

        panel.setVisible(true);
        this.add(panel);
        this.setVisible(true);
    }
}
