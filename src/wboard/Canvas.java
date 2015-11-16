package wboard;

import javax.swing.*;
import java.awt.*;

/**
 * Canvas 400x400 for displaying shapes
 * The Canvas class should be a subclass of JPanel with a white background and an initial size of 400 x 400 ­­ it will contain the
 little drawing shapes.
 * Created by bruno on 11/15/15.
 */
public class Canvas extends JPanel{
  public Canvas(){
    super();
    this.setBackground(Color.white);
    this.setPreferredSize(new Dimension(400,400));
  }

}
