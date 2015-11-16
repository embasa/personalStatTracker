package wboard;


import javax.swing.*;
import java.awt.*;

/**
 * The Canvas class should be a subclass of JPanel with a white background and an initial size of 400 x 400 ­­ it will contain the
 little drawing shapes. Create a Whiteboard subclass of JFrame that sets up the components in a frame, and its main()should
 create a single Whiteboard frame. Install the Canvas at the center of a border layout, and position the controls in the west. To
 fit all the controls on screen, I put groups of related controls into a horizontal box, and then put those boxes into a vertical box
 in the west. Your GUI layout should be functional, but does not need to look exactly like ours. By default, all the components in
 a vertical box are centered, which does not look good for our purpose. You can call setAlignmentX(Box.LEFT_ALIGNMENT)
 on everything within the box like this...
 * Created by bruno on 11/15/15.
 */
public class Whiteboard extends JFrame{
  public Whiteboard(){
    super("whiteboard");
    this.setLayout(new BorderLayout());
    JPanel rootPanel = new JPanel(new FlowLayout());

    /* left panel */
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.setBackground(Color.red);

    JPanel topLeftPanel = controlPane();
    //topLeftPanel.setPreferredSize(new Dimension(400,400));

    /* bottom left will be scrollable */

    JPanel bottomLeftPanel = shapesPane();
    bottomLeftPanel.setPreferredSize(new Dimension(400,400));
    bottomLeftPanel.setBackground(Color.BLACK);

    leftPanel.add(topLeftPanel,BorderLayout.NORTH);
    leftPanel.add(bottomLeftPanel,BorderLayout.SOUTH);

    /* left panel done */
    JPanel rightPanel = new Canvas();

    rootPanel.add(leftPanel);
    rootPanel.add(rightPanel);
    this.add(rootPanel);
    this.pack();
  }

  public static void main(String[] args) {
    JFrame frame = new Whiteboard();
    frame.setVisible(true);

  }

  private JPanel controlPane(){
    /* top left has 4 rows */
    JPanel topLeftPanel = new JPanel();
    topLeftPanel.setBackground(Color.CYAN);
    Box parentBox = Box.createVerticalBox();

    /* add buttons */
    Box buttonBox = Box.createHorizontalBox();
    buttonBox.add(new JButton("row1"),Box.LEFT_ALIGNMENT);

    buttonBox.add(new JButton("row2"),Box.LEFT_ALIGNMENT);
    buttonBox.add(new JButton("row3"),Box.LEFT_ALIGNMENT);
    buttonBox.add(new JButton("row4"),Box.LEFT_ALIGNMENT);
    buttonBox.setPreferredSize(new Dimension(400,100));
    parentBox.add(buttonBox);

    Box colorBox = Box.createHorizontalBox();
    colorBox.add(new JButton("set color"));
    colorBox.setPreferredSize(new Dimension(400,100));
    parentBox.add(colorBox);

    Box textFieldBox = Box.createHorizontalBox();
    JTextField jTextField = new JFormattedTextField("textfield");
    textFieldBox.add(jTextField);
    textFieldBox.add(new JButton("!"));
    textFieldBox.setPreferredSize(new Dimension(400,100));
    parentBox.add(textFieldBox);

    Box moveCommandsBox = Box.createHorizontalBox();
    moveCommandsBox.add(new JButton("move"));
    moveCommandsBox.add(new JButton("move"));
    moveCommandsBox.add(new JButton("move"));
    moveCommandsBox.setPreferredSize(new Dimension(400,100));
    parentBox.add(moveCommandsBox);
    for (Component component : parentBox.getComponents()){
      ((JComponent) component).setAlignmentX(Box.LEFT_ALIGNMENT);
    }
   // topLeftPanel.setLayout(new BoxLayout(parentBox,BoxLayout.LINE_AXIS));
    topLeftPanel.add(parentBox,LEFT_ALIGNMENT);
    return topLeftPanel;
  }

  private JPanel shapesPane(){
    int limit = 40;
    String[] list = new String[limit];

    for(int i=0;i<limit;i++){
      list[i] = String.valueOf(i);
    }
    JList<String> jList = new JList<>(list);
    JScrollPane rootPane = new JScrollPane(jList);
    rootPane.setPreferredSize(new Dimension(400,400));

    JPanel panel = new JPanel();
    panel.add(rootPane);
    return panel;
  }

}
