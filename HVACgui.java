import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

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
    private JTextField acModelTextField;
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
        //initial debugging: set AC as selected option
        serviceCallTypeButtonGroup.setSelected(centralACRadioButton.getModel(),true);


        addServiceCallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // adds service call to queue
                ServiceCall callToAdd;
                String svc_addr = HVACgui.this.serviceAddressTextArea.getText();
                String prob_desc = HVACgui.this.problemDescriptionTextArea.getText();
                Date rept_date = new Date();
                if (serviceCallTypeButtonGroup.getSelection().getActionCommand().equals("ac")) {
                    String model = HVACgui.this.acModelTextField.getText();
                    callToAdd = new CentralAC(svc_addr,prob_desc,rept_date,model);
                    HVAC.todayServiceCalls.add(callToAdd);
                }
                //and refresh the list of tickets
                HVACgui.this.serviceCallListModel.clear();
                for (ServiceCall sc : HVAC.todayServiceCalls) {
                    HVACgui.this.serviceCallListModel.addElement(sc);
                }

            }
        });
    }
}
