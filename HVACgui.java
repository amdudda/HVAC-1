import javax.swing.*;

/**
 * Created by amdudda on 11/10/15.
 */
public class HVACgui extends JFrame {
    // GUI for viewing and adding service calls.
    private JList<ServiceCall> ServiceCallList;
    private JPanel rootPanel;
    private JButton addServiceCallButton;
    private JTextArea serviceAddressTextArea;
    private JTextArea problemDescriptionTextArea;
    private JRadioButton centralACRadioButton;
    private JRadioButton furnaceRadioButton;
    private JRadioButton waterHeaterRadioButton;
    private JLabel Header;
    private ButtonGroup serviceCallTypeButtonGroup;

    private DefaultListModel<ServiceCall> serviceCallListModel;

    public HVACgui() {
        super("HVAC Service Call Manager");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // define our list model for service calls
        serviceCallListModel = new DefaultListModel<ServiceCall>();
        ServiceCallList.setModel(serviceCallListModel);
        ServiceCallList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // define our button group
        serviceCallTypeButtonGroup.add(centralACRadioButton);
        serviceCallTypeButtonGroup.add(furnaceRadioButton);
        serviceCallTypeButtonGroup.add(waterHeaterRadioButton);


    }
}
