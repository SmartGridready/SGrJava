package communicator.modbus.integrationtest;

import com.smartgridready.ns.v0.BitmapEntryProduct;
import com.smartgridready.ns.v0.EnumEntryProductRecord;
import com.smartgridready.ns.v0.RegisterType;
import communicator.common.api.BitmapValue;
import communicator.common.api.BooleanValue;
import communicator.common.api.EnumValue;
import communicator.common.api.StringValue;
import communicator.common.api.Value;
import communicator.common.helper.DataTypeHelper;
import communicator.common.impl.SGrDeviceBase;
import communicator.common.runtime.GenDriverException;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import org.eclipse.emf.common.util.EList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SGrTestCommunicator extends JFrame {

    private JTree navigationTree;
    private DefaultTreeModel treeModel;

    private JPanel detailsPanel;

    private JTextField readValueField;
    private JTextField writeValueField;

    private final TestDevice testDevice = new TestDevice();

    // Custom TreeCellRenderer to customize node labels
    private static class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(
                JTree tree, Object value, boolean selected, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();

            if (userObject instanceof TestDevice.DataPointDescriptor) {
                setText(((TestDevice.DataPointDescriptor)userObject).dataPoint);
            } else {
                setText(userObject.toString());
            }
            return this;
        }
    }

    public SGrTestCommunicator() throws Exception {
        super("SGr Communicator");

        testDevice.init();

        createMainMenu();
        createNavigationTree();
        createDetailsPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(navigationTree), detailsPanel);
        splitPane.setDividerLocation(300);
        add(splitPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createNavigationTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Functional profiles");

        java.util.List<TestDevice.DataPointDescriptor> dataPoints = testDevice.getDataPoints();
        Map<String, java.util.List<TestDevice.DataPointDescriptor>> fpMap = dataPoints.stream().collect(Collectors.groupingBy(dp -> dp.functionalProfile));

        Map<String, java.util.List<TestDevice.DataPointDescriptor>> sortedMap = new TreeMap<>(fpMap);

        sortedMap.forEach((fpName, dpList) -> {
            DefaultMutableTreeNode fpNode = new DefaultMutableTreeNode(fpName);
            dpList.forEach(dp -> fpNode.add(new DefaultMutableTreeNode(dp)));
            root.add(fpNode);
        });

        treeModel = new DefaultTreeModel(root);
        navigationTree = new JTree(treeModel);
        navigationTree.setCellRenderer(new CustomTreeCellRenderer());
        navigationTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        navigationTree.addTreeSelectionListener(e -> onTreeSelectionChanged());
        navigationTree.revalidate();
        navigationTree.repaint();
    }

    private  void updateNavigationTree() {

        DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();

        java.util.List<TestDevice.DataPointDescriptor> dataPoints = testDevice.getDataPoints();
        Map<String, java.util.List<TestDevice.DataPointDescriptor>> fpMap = dataPoints.stream().collect(Collectors.groupingBy(dp -> dp.functionalProfile));

        root.removeAllChildren();
        fpMap.forEach((fpName, dpList) -> {
            DefaultMutableTreeNode fpNode = new DefaultMutableTreeNode(fpName);
            dpList.forEach(dp -> fpNode.add(new DefaultMutableTreeNode(dp)));
            root.add(fpNode);
        });

        treeModel.reload();
    }

    private void createDetailsPanel() {
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());

        JLabel detailsLabel = new JLabel("Details Panel");
        detailsLabel.setHorizontalAlignment(JLabel.CENTER);
        addComponent(detailsPanel, detailsLabel,0, 0);
    }

    private void onTreeSelectionChanged() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) navigationTree.getLastSelectedPathComponent();

        if (selectedNode!=null) {
            updateDetailsPanel(selectedNode);
        }
    }

    private void updateDetailsPanel(DefaultMutableTreeNode selectedNode) {

        detailsPanel.removeAll();

        addComponent(detailsPanel, createDatapointInfoPanel(selectedNode), 0, 0);
        addComponent(detailsPanel, createActionPanel(selectedNode), 0, 1 );

        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    private JPanel createDatapointInfoPanel(DefaultMutableTreeNode selectedNode) {

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());


        if (selectedNode.getUserObject() instanceof TestDevice.DataPointDescriptor) {

            TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor)selectedNode.getUserObject();

            JLabel fpLabel = new JLabel("Functional Profile:");
            JLabel fpName = new JLabel(dpDescriptor.functionalProfile);
            JLabel dpLabel = new JLabel("Data Point:");
            JLabel dpName = new JLabel(dpDescriptor.dataPoint);
            JLabel rwLabel = new JLabel("RW permission:");
            JLabel rwPermission = new JLabel((dpDescriptor.isReadable ? "R":"") + (dpDescriptor.isWritable ? "W":""));
            JLabel dpTypeLabel = new JLabel("Register type:");
            JLabel dpType = new JLabel(dpDescriptor.modbusRegisterType.name());
            JLabel genericTypeLabel = new JLabel("Generic type:");
            JLabel genericType = new JLabel(DataTypeHelper.getGenDataTypeName(dpDescriptor.genericType));
            JLabel unitLabel = new JLabel("Unit");
            JLabel unit = new JLabel(dpDescriptor.units);
            JLabel minValLabel = new JLabel("MinValue");
            JLabel minVal = new JLabel(dpDescriptor.minVal);
            JLabel maxValLabel = new JLabel("MaxValue");
            JLabel maxVal = new JLabel(dpDescriptor.maxVal);
            JLabel unitConversionFactorLabel = new JLabel("UnitConversionFactor");
            JLabel unitConversionFactor = new JLabel(dpDescriptor.conversionFactor);

            JLabel modubusTypeLabel = new JLabel("Modbus type;");
            JLabel modbusType = new JLabel(DataTypeHelper.getModbusDataTypeName(dpDescriptor.modbusType));
            JLabel modbusAddressLabel = new JLabel(
                    (dpDescriptor.modbusRegisterType == RegisterType.COIL || dpDescriptor.modbusRegisterType == RegisterType.DISCRETE_INPUT) ?
                    "Modbus bit address:" : "Modbus register address:");
            JLabel modbusAddress = new JLabel(dpDescriptor.modbusAddress);
            JLabel modbusNoOfRegistersLabel = new JLabel("No of modbus registers:");
            JLabel modbusNoOfRegisters = new JLabel(dpDescriptor.modbusNbOfRegisters);


            addComponent(infoPanel, fpLabel,  0, 0);
            addComponent(infoPanel, fpName, 1, 0);
            addComponent(infoPanel, dpLabel, 0, 1);
            addComponent(infoPanel, dpName, 1, 1);
            addComponent(infoPanel, rwLabel, 0, 2);
            addComponent(infoPanel, rwPermission, 1, 2);
            addComponent(infoPanel, dpTypeLabel, 0, 3);
            addComponent(infoPanel, dpType, 1, 3);
            addComponent(infoPanel, genericTypeLabel, 0, 4);
            addComponent(infoPanel, genericType, 1, 4);
            addComponent(infoPanel, unitLabel, 0, 5);
            addComponent(infoPanel, unit, 1, 5);
            addComponent(infoPanel, minValLabel, 0, 6);
            addComponent(infoPanel, minVal, 1, 6);
            addComponent(infoPanel, maxValLabel, 0, 7);
            addComponent(infoPanel, maxVal, 1, 7);
            addComponent(infoPanel, unitConversionFactorLabel, 0, 8);
            addComponent(infoPanel, unitConversionFactor, 1, 8);
            addComponent(infoPanel, modubusTypeLabel, 0, 9);
            addComponent(infoPanel, modbusType, 1, 9);
            addComponent(infoPanel, modbusAddressLabel, 0, 10);
            addComponent(infoPanel, modbusAddress, 1, 10);
            addComponent(infoPanel, modbusNoOfRegistersLabel, 0, 11);
            addComponent(infoPanel, modbusNoOfRegisters, 1, 11);
        } else {
           JLabel nameLabel = new JLabel(selectedNode.getUserObject().toString());
           addComponent(infoPanel, nameLabel, 0, 0);
        }
        return infoPanel;
    }

    private JPanel createActionPanel(DefaultMutableTreeNode selectedNode) {

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridBagLayout());

        if (selectedNode.getUserObject() instanceof TestDevice.DataPointDescriptor) {

            TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();
            if (dpDescriptor.genericType.getBitmap() != null) {
                addComponent(actionPanel, createBitmapPanel(selectedNode), 0, 0);
            } else if (dpDescriptor.genericType.getEnum() != null) {
                addComponent(actionPanel, createEnumPanel(selectedNode), 0, 0);
            } else if (dpDescriptor.genericType.getBoolean() != null) {
                addComponent(actionPanel, createBooleanPanel(selectedNode), 0, 0);
            } else {
                int gridy = 0;
                if (dpDescriptor.isWritable) {
                    addComponent(actionPanel, createWritePanel(selectedNode), 0, gridy++);
                }
                if (dpDescriptor.isReadable) {
                    addComponent(actionPanel, createReadPanel(selectedNode), 0, gridy);
                }
            }
        }
        return  actionPanel;
    }

    private JPanel createWritePanel(DefaultMutableTreeNode selectedNode) {
        JPanel writePanel = new JPanel(new GridBagLayout());

        JLabel writeLabel   = new JLabel("Write value:");
        writeValueField = new JTextField(16);
        JButton writeButton = new JButton("Write");
        writeButton.addActionListener(event -> onWriteButtonClicked(selectedNode));

        addComponent(writePanel, writeLabel, 0, 0);
        addComponent(writePanel, writeValueField, 1, 0);
        addComponent(writePanel, writeButton,  2, 0);

        return writePanel;
    }

    private void onWriteButtonClicked(DefaultMutableTreeNode selectedNode) {

        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();
        Value value = StringValue.of(writeValueField.getText());
        try {
            SGrDeviceBase<?, ?, ?> device = testDevice.getTestSystem();
            device.setVal(dpDescriptor.functionalProfile, dpDescriptor.dataPoint, value);

            Value readVal = device.getVal(dpDescriptor.functionalProfile, dpDescriptor.dataPoint);
            readValueField.setText(readVal.getString());
        } catch (Exception e) {
            showMessageDialog("Write to target system failed.", e);
        }
    }

    private JPanel createReadPanel(DefaultMutableTreeNode selectedNode) {

        JPanel readPanel = new JPanel(new GridBagLayout());

        JLabel readLabel = new JLabel("Read value:  ");
        readValueField = new JTextField(16);
        readValueField.setEditable(false);
        JButton readButton = new JButton("Read");
        readButton.addActionListener(event -> onReadButtonClicked(selectedNode));


        addComponent(readPanel, readLabel, 0, 0);
        addComponent(readPanel, readValueField, 1, 0);
        addComponent(readPanel, readButton, 2, 0);

        return readPanel;
    }

    private void onReadButtonClicked(DefaultMutableTreeNode selectedNode) {

        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();
        try {
            SGrDeviceBase<?, ?, ?> device = testDevice.getTestSystem();
            Value readVal = device.getVal(dpDescriptor.functionalProfile, dpDescriptor.dataPoint);
            readValueField.setText(readVal.getString());
        } catch (Exception e) {
            showMessageDialog("Get value from device failed.", e);
        }
    }

    private JPanel createBitmapPanel(DefaultMutableTreeNode selectedNode) {

        JPanel bitmapPanel = new JPanel(new GridBagLayout());

        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();

        EList<BitmapEntryProduct> bitmapEntries = dpDescriptor.genericType.getBitmap().getBitmapEntry();
        Optional<BitmapValue> currentBitmap = getCurrentBitmap(dpDescriptor.functionalProfile, dpDescriptor.dataPoint);

        Map<String, JCheckBox> checkBoxMap = new HashMap<>();

        for (int i = 0; i<bitmapEntries.size(); i++) {
            JCheckBox checkBox = new JCheckBox(bitmapEntries.get(i).getLiteral());
            currentBitmap.ifPresent(bitmapValue -> checkBox.setSelected(bitmapValue.getBitmap().get(checkBox.getText())));

            if (dpDescriptor.isWritable) {
                checkBox.addItemListener(checkBoxEvent -> onCheckboxChanged(checkBoxEvent, selectedNode));
            } else {
                checkBox.setEnabled(false);
            }
            checkBoxMap.put(checkBox.getText(), checkBox);
            addComponent(bitmapPanel, checkBox, i % 4, i / 4);
        }

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> onRefreshBitmapClicked(selectedNode, checkBoxMap));
        addComponent(bitmapPanel, refreshButton, 0, bitmapEntries.size());
        return bitmapPanel;
    }

    private Optional<BitmapValue> getCurrentBitmap(String functionalProfile, String dataPoint) {

        try {
            Value value = testDevice.getTestSystem().getVal(functionalProfile, dataPoint);
            if (value instanceof BitmapValue) {
                return Optional.of((BitmapValue)testDevice.getTestSystem().getVal(functionalProfile, dataPoint));
            } else {
                showMessageDialog("Read bitmap value failed", new GenDriverException("Read datapoint is not a bitmap."));
            }
        } catch (Exception e) {
            showMessageDialog("Read bitmap value failed.", e);
        }
        return Optional.empty();
    }

    private void onCheckboxChanged(ItemEvent event, DefaultMutableTreeNode selectedNode) {

        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();
        try {
            SGrDeviceBase<?, ?, ?> device = testDevice.getTestSystem();

            // Get the current bitmap values
            Value currentValue = device.getVal(dpDescriptor.functionalProfile, dpDescriptor.dataPoint);
            JCheckBox checkbox = (JCheckBox)event.getItem();

            // Modify the changed bitmap bit
            Map<String, Boolean> bitmap = currentValue.getBitmap();
            bitmap.put(checkbox.getText(), event.getStateChange() == ItemEvent.SELECTED);
            device.setVal(dpDescriptor.functionalProfile, dpDescriptor.dataPoint, BitmapValue.of(bitmap));

        } catch (Exception e) {
            showMessageDialog("Write bitmap value failed.", e);
        }
    }

    private void onRefreshBitmapClicked(DefaultMutableTreeNode selectedNode, Map<String, JCheckBox> checkBoxList) {

        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();
        Optional<BitmapValue> bitmapValue = getCurrentBitmap(dpDescriptor.functionalProfile, dpDescriptor.dataPoint);
        bitmapValue.ifPresent(bitmapValue1 ->
                bitmapValue1.getBitmap().forEach((key, value) ->
                        Optional.ofNullable(checkBoxList.get(key)).ifPresent(cb -> cb.setSelected(value))));
    }

    private JPanel createEnumPanel(DefaultMutableTreeNode selectedNode) {

        JPanel bitmapPanel = new JPanel(new GridBagLayout());

        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();

        EList<EnumEntryProductRecord> enumEntries = dpDescriptor.genericType.getEnum().getEnumEntry();
        Optional<EnumValue> currentEnum = getCurrentEnum(dpDescriptor.functionalProfile, dpDescriptor.dataPoint);

        JLabel selectedEnum = new JLabel("Current enum value");
        JComboBox<String> comboBox = new JComboBox<>(enumEntries.stream().map(EnumEntryProductRecord::getLiteral).toArray(String[]::new));
        currentEnum.ifPresent( enumValue ->  {
            comboBox.setSelectedItem(enumValue.getEnum().getLiteral());
            comboBox.addItemListener(e -> onComboboxChanged(e, selectedNode));
        });

        addComponent(bitmapPanel, selectedEnum, 0, 0);
        addComponent(bitmapPanel, comboBox, 1, 0);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> onRefreshEnum(comboBox, selectedNode));
        addComponent(bitmapPanel, refreshButton, 2, 0);

        return bitmapPanel;
    }

    private void onComboboxChanged(ItemEvent event, DefaultMutableTreeNode selectedNode) {

        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();
        if(event.getStateChange() == ItemEvent.SELECTED) {
            String selectedItem = (String) event.getItem();
            try {
                testDevice.getTestSystem().setVal(dpDescriptor.functionalProfile, dpDescriptor.dataPoint, EnumValue.of(selectedItem));
            } catch (Exception e) {
                showMessageDialog("Write enum value failed.", e);
            }
        }
    }


    private Optional<EnumValue> getCurrentEnum(String functionalProfile, String dataPoint) {

        try {
            Value enumValue = testDevice.getTestSystem().getVal(functionalProfile, dataPoint);
            if (enumValue instanceof EnumValue) {
                return Optional.of((EnumValue) enumValue);
            } else {
                showMessageDialog("Read enum value failed.", new GenDriverException("Read datapoint is not an enum."));
            }
        } catch (Exception e) {
            showMessageDialog("Read enum value failed.", e);

        }
        return Optional.empty();
    }

    private void onRefreshEnum(JComboBox<String> comboBox, DefaultMutableTreeNode selectedNode) {

        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();
        try {
            Value value = testDevice.getTestSystem().getVal(dpDescriptor.functionalProfile, dpDescriptor.dataPoint);
            comboBox.setSelectedItem(value.getEnum().getLiteral());
        } catch (Exception e) {
            showMessageDialog("Read enum failed.", e);
        }
    }

    private JPanel createBooleanPanel(DefaultMutableTreeNode selectedNode) {

        JPanel booleanPanel = new JPanel(new GridBagLayout());
        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();

        JCheckBox checkBox = new JCheckBox(dpDescriptor.dataPoint);
        if (!dpDescriptor.isWritable) {
            checkBox.setEnabled(false);
        } else {
            checkBox.addItemListener(e -> onBooleanChecked(e, selectedNode));
        }

        try {
            Value value = testDevice.getTestSystem().getVal(dpDescriptor.functionalProfile, dpDescriptor.dataPoint);
            checkBox.setSelected(value.getBoolean());
        } catch (Exception e) {
            showMessageDialog("Read digital input failed.", e);
        }
        addComponent(booleanPanel, checkBox, 0, 0);

        JButton buttonRefresh = new JButton("Refresh");
        buttonRefresh.addActionListener(event -> onBooleanRefresh(checkBox, selectedNode));
        addComponent(booleanPanel, buttonRefresh, 0, 1);

        return booleanPanel;
    }

    private void onBooleanChecked(ItemEvent event, DefaultMutableTreeNode selectedNode) {

        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();

        try {
            testDevice.getTestSystem().setVal(
                    dpDescriptor.functionalProfile,
                    dpDescriptor.dataPoint,
                    BooleanValue.of(event.getStateChange() == ItemEvent.SELECTED));
        } catch (Exception e) {
            showMessageDialog("Write digital datapoint failed.", e);
        }
    }

    private void onBooleanRefresh(JCheckBox checkBox, DefaultMutableTreeNode selectedNode) {
        TestDevice.DataPointDescriptor dpDescriptor = (TestDevice.DataPointDescriptor) selectedNode.getUserObject();
        try {
            Value value = testDevice.getTestSystem().getVal(dpDescriptor.functionalProfile, dpDescriptor.dataPoint);
            checkBox.setSelected(value.getBoolean());
        } catch (Exception e) {
            showMessageDialog("Read digital input failed.", e);
        }
    }

    private void addComponent(JPanel panel, JComponent component, int gridx, int gridy) {

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Optional: Add some padding
        panel.add(component, gbc);
    }

    private void showMessageDialog(String msg, Exception e) {

        JOptionPane.showMessageDialog(this, msg + "\n" + e.getMessage());
    }

    private void createMainMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem menuItem = new JMenuItem();
        menuItem.setAction(new AbstractAction("Open EI-XML...") {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoadEiXml();
            }
        });

        fileMenu.add(menuItem);
        this.setJMenuBar(menuBar);
    }

    private void onLoadEiXml() {

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File selectedFile = fileChooser.getSelectedFile();
                testDevice.loadDeviceDescriptionFile(
                        selectedFile,
                        this::ipConnParamDialog,
                        this::comPortConnParamDialog);

                updateNavigationTree();
                navigationTree.revalidate();
                navigationTree.repaint();
            } catch (Exception e) {
                showMessageDialog("Failed to load EI-XML", e);
            }
        }
    }

    private Tuple2<String, Integer> ipConnParamDialog(String msg) {

        JPanel connParamPanel = new JPanel(new GridBagLayout());

        JLabel ipAddrLabel = new JLabel("IP Address:");
        JTextField ipAddr  = new JTextField(16);
        JLabel ipPortLabel = new JLabel("Port:");
        JTextField ipPort  = new JTextField(6);

        addComponent(connParamPanel, ipAddrLabel, 0, 0);
        addComponent(connParamPanel, ipAddr, 1, 0);
        addComponent(connParamPanel, ipPortLabel, 0, 1);
        addComponent(connParamPanel, ipPort, 1, 1);

        JOptionPane.showMessageDialog(
                this,
                connParamPanel,
                "IP Connection Parameters",
                JOptionPane.QUESTION_MESSAGE);

        return new Tuple2<>(ipAddr.getText(), Integer.parseInt(ipPort.getText()));
    }

    private Tuple3<String, Integer, Integer> comPortConnParamDialog(String msg) {

        JPanel connParamPanel = new JPanel(new GridBagLayout());

        JLabel comPortLabel = new JLabel("COM Port (ex. COM1):");
        JTextField comPort  = new JTextField(16);
        JLabel baudRateLabel = new JLabel("Baudrate:");
        JTextField baudRate  = new JTextField(6);
        JLabel unitIdentifierLabel = new JLabel("Modbus Unit ID:");
        JTextField unitIdentifier = new JTextField(6);

        addComponent(connParamPanel, comPortLabel, 0, 0);
        addComponent(connParamPanel, comPort, 1, 0);
        addComponent(connParamPanel, baudRateLabel, 0, 1);
        addComponent(connParamPanel, baudRate, 1, 1);
        addComponent(connParamPanel, unitIdentifierLabel, 0, 2);
        addComponent(connParamPanel, unitIdentifier, 1, 2);

        JOptionPane.showMessageDialog(
                this,
                connParamPanel,
                "COM Port connection",
                JOptionPane.QUESTION_MESSAGE);

        return new Tuple3<>(comPort.getText(), Integer.parseInt(baudRate.getText()), Integer.parseInt(unitIdentifier.getText()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new SGrTestCommunicator();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
