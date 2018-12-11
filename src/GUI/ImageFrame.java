package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ImageFrame extends JFrame {
    public ImageFrame() {
        super();

        JPanel mainPane = new JPanel();

        JPanel originalImageLabelPanel = new JPanel();
        originalImageLabelPanel.add(new JLabel("original image"));
        mainPane.add(originalImageLabelPanel);

        JPanel originalImageDisplayPanel = new JPanel();
        JLabel label = new JLabel();

        this.add(mainPane);
        this.setTitle("Send image through channel");
        this.setSize(400, 400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
    }
}
