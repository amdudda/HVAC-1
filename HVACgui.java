import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
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
    private JButton quitButton;
    private String[] furnaceTypeOptions = {"Forced Air", "Boiler", "Octopus"};
    private JComboBox<String> furnaceTypeComboBox;
    private JTextField waterHeaterAgeTextField;
    private JScrollPane serviceCallListScrollPane;
    private JButton resolveSelectedCallButton;
    private JTextArea resolutionTextArea;
    private JTextField FeeTextField;
    //private JLabel Header;
    private ButtonGroup serviceCallTypeButtonGroup = new ButtonGroup();

    private DefaultListModel<ServiceCall> serviceCallListModel;

    public HVACgui() {
        super("HVAC Service Call Manager");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(700, 700));

        // define our list model for service calls
        serviceCallListModel = new DefaultListModel<ServiceCall>();
        ServiceCallList.setModel(serviceCallListModel);
        ServiceCallList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // define our button group
        serviceCallTypeButtonGroup.add(centralACRadioButton);
        serviceCallTypeButtonGroup.add(furnaceRadioButton);
        serviceCallTypeButtonGroup.add(waterHeaterRadioButton);
        // set AC as selected option
        serviceCallTypeButtonGroup.setSelected(centralACRadioButton.getModel(), true);
        // turn off the options that don't apply to AC units
        HVACgui.this.furnaceTypeComboBox.setEnabled(false);
        HVACgui.this.waterHeaterAgeTextField.setEnabled(false);
        // disable ability to enter a resolution
        HVACgui.this.resolutionTextArea.setEnabled(false);
        HVACgui.this.FeeTextField.setEnabled(false);

        // define the values available in the furnace type combobox
        for (int i = 0; i < furnaceTypeOptions.length; i++) {
            furnaceTypeComboBox.addItem(furnaceTypeOptions[i]);
        }

        addServiceCallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // adds service call to queue
                ServiceCall callToAdd;
                String svc_addr = HVACgui.this.serviceAddressTextArea.getText();
                String prob_desc = HVACgui.this.problemDescriptionTextArea.getText();
                Date rept_date = new Date();
                // the above attributes are common to all service types; there are a few that are specific to certain call types
                String calltype = serviceCallTypeButtonGroup.getSelection().getActionCommand();
                if (calltype.equals("ac")) {
                    String model = HVACgui.this.acModelTextField.getText();
                    callToAdd = new CentralAC(svc_addr, prob_desc, rept_date, model);
                } else if (calltype.equals("f")) {
                    int furnType = furnaceTypeComboBox.getSelectedIndex() + 1;
                    callToAdd = new Furnace(svc_addr, prob_desc, rept_date, furnType);
                } else {
                    // otherwise, we presumably have a water heater
                    String ageString = waterHeaterAgeTextField.getText();
                    try {  // error trapping for bad data entry
                        double age = Double.parseDouble(ageString);
                        callToAdd = new WaterHeater(svc_addr, prob_desc, rept_date, age);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(HVACgui.this, "Enter a number for water heater age.  (Can be decimal.)");
                        return;
                    }
                }
                HVAC.todayServiceCalls.add(callToAdd);

                updateServiceCallList();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // button item listeners to enable/disable items in window
        centralACRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                HVACgui.this.acModelTextField.setEnabled(HVACgui.this.centralACRadioButton.isSelected());
            }
        });
        furnaceRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                HVACgui.this.furnaceTypeComboBox.setEnabled(HVACgui.this.furnaceRadioButton.isSelected());
            }
        });
        waterHeaterRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                HVACgui.this.waterHeaterAgeTextField.setEnabled(HVACgui.this.waterHeaterRadioButton.isSelected());
            }
        });
        // listener to enable/disable resolution entry
        ServiceCallList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                HVACgui.this.resolutionTextArea.setEnabled(true);
                HVACgui.this.FeeTextField.setEnabled(true);
                }
            }
        );

        // resolve our tickets
        resolveSelectedCallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // need to do a few things here: mark the ticket as resolved, store the resolution and fee, and refresh
                // the list of tickets.
                ServiceCall selectedCall = HVACgui.this.ServiceCallList.getSelectedValue();
                selectedCall.setReportedDate(new Date());
                selectedCall.setResolution(HVACgui.this.resolutionTextArea.getText());
                String feeString = HVACgui.this.FeeTextField.getText();
                try {  // error trapping for bad data entry
                    double fee = Double.parseDouble(feeString);
                    selectedCall.setFee(fee);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(HVACgui.this, "Enter a number for the service fee.  Do not include the dollar sign.");
                    return;
                }
                HVAC.todayServiceCalls.remove(selectedCall);
                HVAC.resolvedServiceCalls.add(selectedCall);
                updateServiceCallList();
            }
        });
    }

    private void updateServiceCallList() {
        //and refresh the list of tickets
        HVACgui.this.serviceCallListModel.clear();
        for (ServiceCall sc : HVAC.todayServiceCalls) {
            HVACgui.this.serviceCallListModel.addElement(sc);
        }
    }
}
