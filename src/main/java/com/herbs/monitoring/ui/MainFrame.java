package com.herbs.monitoring.ui;

import com.herbs.monitoring.model.SensorRecord;
import com.herbs.monitoring.sensor.AbstractSensor;
import com.herbs.monitoring.sensor.SensorDataCallback;
import com.herbs.monitoring.sensor.SensorFactory;
import com.herbs.monitoring.service.SensorService;
import com.herbs.monitoring.service.ThresholdService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main application window that exposes CRUD operations and sensor collection features.
 */
public class MainFrame extends JFrame {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String DEFAULT_LOCATION = "黄芪区";

    private final SensorService sensorService = new SensorService();
    private final ThresholdService thresholdService = new ThresholdService();
    private final SensorTableModel tableModel = new SensorTableModel();
    private final JTable sensorTable = new JTable(tableModel);
    private final JComboBox<String> sensorTypeCombo = new JComboBox<>(new String[]{"温度传感器/TCP", "湿度传感器/UDP"});
    private final JTextField sensorIdField = new JTextField(12);
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private Timer periodicTimer;

    public MainFrame() {
        setTitle("中药种植监控系统-主窗口");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("中药种植监控系统-主窗口", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        sensorTable.setPreferredScrollableViewportSize(new Dimension(800, 260));
        sensorTable.setFillsViewportHeight(true);
        sensorTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                SensorRecord selected = tableModel.getSensorAt(sensorTable.getSelectedRow());
                if (selected != null) {
                    sensorIdField.setText(selected.getSensorId());
                    selectComboByProtocol(selected.getProtocol());
                }
            }
        });
        TableColumnModel columnModel = sensorTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(140);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(160);
        columnModel.getColumn(3).setPreferredWidth(100);
        add(new JScrollPane(sensorTable), BorderLayout.CENTER);

        // 创建底部控制面板，使用GridBagLayout实现三行布局
        JPanel southPanel = new JPanel(new GridBagLayout());
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // === 第一行：CRUD操作区 ===
        JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        crudPanel.setBorder(BorderFactory.createTitledBorder("数据管理"));
        JButton addButton = new JButton("新增");
        JButton editButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");
        JButton refreshButton = new JButton("刷新");
        crudPanel.add(addButton);
        crudPanel.add(editButton);
        crudPanel.add(deleteButton);
        crudPanel.add(Box.createHorizontalStrut(20)); // 分隔符
        crudPanel.add(refreshButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        southPanel.add(crudPanel, gbc);

        // === 第二行：数据采集控制区 ===
        JPanel collectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        collectPanel.setBorder(BorderFactory.createTitledBorder("数据采集"));
        collectPanel.add(new JLabel("传感器类型:"));
        collectPanel.add(sensorTypeCombo);
        collectPanel.add(Box.createHorizontalStrut(10));
        collectPanel.add(new JLabel("传感器ID:"));
        collectPanel.add(sensorIdField);
        JButton collectButton = new JButton("采集传感器数据");
        collectPanel.add(Box.createHorizontalStrut(10));
        collectPanel.add(collectButton);

        gbc.gridy = 1;
        southPanel.add(collectPanel, gbc);

        // === 第三行：周期监控区 ===
        JPanel monitorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        monitorPanel.setBorder(BorderFactory.createTitledBorder("自动监控"));
        JButton startTimerButton = new JButton("▶ 启动周期采集");
        JButton stopTimerButton = new JButton("⏸ 停止周期采集");
        startTimerButton.setFont(startTimerButton.getFont().deriveFont(Font.BOLD));
        stopTimerButton.setFont(stopTimerButton.getFont().deriveFont(Font.BOLD));
        monitorPanel.add(new JLabel("周期间隔: 5秒"));
        monitorPanel.add(Box.createHorizontalStrut(10));
        monitorPanel.add(startTimerButton);
        monitorPanel.add(stopTimerButton);

        gbc.gridy = 2;
        southPanel.add(monitorPanel, gbc);

        add(southPanel, BorderLayout.SOUTH);

        addButton.addActionListener(event -> onAddSensor());
        editButton.addActionListener(event -> onEditSensor());
        deleteButton.addActionListener(event -> onDeleteSensor());
        refreshButton.addActionListener(event -> refreshTable());
        collectButton.addActionListener(event -> collectOnce());
        startTimerButton.addActionListener(event -> startPeriodicCollection());
        stopTimerButton.addActionListener(event -> stopPeriodicCollection());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                executor.shutdownNow();
                stopPeriodicCollection();
            }
        });

        refreshTable();
    }

    private void selectComboByProtocol(String protocol) {
        for (int i = 0; i < sensorTypeCombo.getItemCount(); i++) {
            String item = sensorTypeCombo.getItemAt(i);
            if (item.contains(protocol)) {
                sensorTypeCombo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void refreshTable() {
        List<SensorRecord> records = sensorService.listSensors();
        tableModel.setSensors(records);
    }

    private void onAddSensor() {
        SensorRecord record = promptSensorDetails(null);
        if (record != null) {
            try {
                sensorService.addSensor(record);
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "新增失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEditSensor() {
        int row = sensorTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择要修改的传感器", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        SensorRecord origin = tableModel.getSensorAt(row);
        SensorRecord updated = promptSensorDetails(origin);
        if (updated != null) {
            try {
                sensorService.updateSensor(updated);
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "修改失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDeleteSensor() {
        int row = sensorTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的传感器", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        SensorRecord record = tableModel.getSensorAt(row);
        int option = JOptionPane.showConfirmDialog(this, "确认删除传感器 " + record.getSensorId() + "?", "确认", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                sensorService.deleteSensor(record.getSensorId());
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private SensorRecord promptSensorDetails(SensorRecord existing) {
        // 创建输入组件
        JTextField idField = new JTextField(existing != null ? existing.getSensorId() : "", 15);
        
        // 传感器类型下拉框
        String[] sensorTypes = {"温度传感器", "湿度传感器"};
        JComboBox<String> typeCombo = new JComboBox<>(sensorTypes);
        if (existing != null) {
            typeCombo.setSelectedItem(existing.getSensorType());
            idField.setEditable(false);
        }
        
        JTextField locationField = new JTextField(existing != null ? existing.getLocation() : DEFAULT_LOCATION, 15);
        
        // 协议下拉框（根据传感器类型自动选择）
        String[] protocols = {"TCP", "UDP"};
        JComboBox<String> protocolCombo = new JComboBox<>(protocols);
        
        // 类型变化时自动更新协议
        typeCombo.addActionListener(e -> {
            String selectedType = (String) typeCombo.getSelectedItem();
            if ("温度传感器".equals(selectedType)) {
                protocolCombo.setSelectedItem("TCP");
            } else if ("湿度传感器".equals(selectedType)) {
                protocolCombo.setSelectedItem("UDP");
            }
        });
        
        // 初始化协议选择
        if (existing != null) {
            protocolCombo.setSelectedItem(existing.getProtocol());
        } else {
            // 新增时根据默认类型设置协议
            if ("温度传感器".equals(typeCombo.getSelectedItem())) {
                protocolCombo.setSelectedItem("TCP");
            } else {
                protocolCombo.setSelectedItem("UDP");
            }
        }

        // 使用GridBagLayout创建更好的布局
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // 传感器ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("传感器ID:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(idField, gbc);
        
        // 类型
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("传感器类型:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(typeCombo, gbc);
        
        // 位置
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("安装位置:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(locationField, gbc);
        
        // 协议
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("通信协议:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(protocolCombo, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, existing == null ? "新增传感器" : "修改传感器", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String sensorId = idField.getText().trim();
            if (sensorId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "传感器ID不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            return new SensorRecord(
                    sensorId,
                    (String) typeCombo.getSelectedItem(),
                    locationField.getText().trim(),
                    (String) protocolCombo.getSelectedItem()
            );
        }
        return null;
    }

    private void collectOnce() {
        String sensorId = resolveSensorId();
        if (sensorId == null) {
            return;
        }
        String typeKey = (String) sensorTypeCombo.getSelectedItem();
        submitSensorCollection(typeKey, sensorId);
    }

    private void submitSensorCollection(String typeKey, String sensorId) {
        executor.submit(() -> {
            try {
                String location = findLocation(sensorId).orElse(DEFAULT_LOCATION);
                AbstractSensor sensor = SensorFactory.createByType(typeKey, sensorId, location);
                sensor.addCallback(buildUiCallback());
                sensor.collect();
                sensor.listenData();
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, ex.getMessage(), "采集失败", JOptionPane.ERROR_MESSAGE));
            }
        });
    }

    private SensorDataCallback buildUiCallback() {
        return reading -> {
            Optional<String> alert = thresholdService.evaluate(reading);
            StringBuilder message = new StringBuilder();
            message.append("传感器: ").append(reading.getSensorId()).append('\n');
            message.append("类型: ").append(reading.getSensorType()).append('\n');
            message.append("位置: ").append(reading.getLocation()).append('\n');
            message.append("协议: ").append(reading.getProtocol()).append('\n');
            message.append("采集值: ").append(String.format(Locale.CHINA, "%.2f", reading.getValue())).append('\n');
            message.append("时间: ").append(reading.getTimestamp().format(FORMATTER));
            alert.ifPresent(a -> message.append('\n').append("告警: ").append(a));
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, message.toString(),
                    alert.isPresent() ? "采集告警" : "采集完成",
                    alert.isPresent() ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE));
        };
    }

    private void startPeriodicCollection() {
        if (periodicTimer != null) {
            JOptionPane.showMessageDialog(this, "周期采集已经启动", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String sensorId = resolveSensorId();
        if (sensorId == null) {
            return;
        }
        String typeKey = (String) sensorTypeCombo.getSelectedItem();
        periodicTimer = new Timer(true);
        periodicTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                submitSensorCollection(typeKey, sensorId);
            }
        }, 0, 5000);
        JOptionPane.showMessageDialog(this, "周期采集已启动 (5秒间隔)", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    private void stopPeriodicCollection() {
        if (periodicTimer != null) {
            periodicTimer.cancel();
            periodicTimer = null;
            JOptionPane.showMessageDialog(this, "周期采集已停止", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String resolveSensorId() {
        String sensorId = sensorIdField.getText().trim();
        if (!sensorId.isEmpty()) {
            return sensorId;
        }
        int row = sensorTable.getSelectedRow();
        if (row >= 0) {
            return tableModel.getSensorAt(row).getSensorId();
        }
        JOptionPane.showMessageDialog(this, "请在输入框或表格中提供传感器ID", "提示", JOptionPane.WARNING_MESSAGE);
        return null;
    }

    private Optional<String> findLocation(String sensorId) {
        return sensorService.findSensor(sensorId).map(SensorRecord::getLocation);
    }
}
