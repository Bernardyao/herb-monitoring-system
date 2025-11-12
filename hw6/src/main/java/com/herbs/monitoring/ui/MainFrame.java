import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JTable sensorTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private SensorTableModel sensorTableModel;

    public MainFrame() {
        setTitle("中药种植监控系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        sensorTableModel = new SensorTableModel();
        sensorTable = new JTable(sensorTableModel);
        JScrollPane scrollPane = new JScrollPane(sensorTable);

        addButton = new JButton("添加传感器");
        editButton = new JButton("编辑传感器");
        deleteButton = new JButton("删除传感器");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 添加传感器的逻辑
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 编辑传感器的逻辑
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 删除传感器的逻辑
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}