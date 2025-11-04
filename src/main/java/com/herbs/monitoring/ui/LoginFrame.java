package com.herbs.monitoring.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Swing based login interface for the experiment.
 */
public class LoginFrame extends JFrame {
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin";

    private final JTextField usernameField = new JTextField(6);
    private final JPasswordField passwordField = new JPasswordField(6);

    public LoginFrame() {
        setTitle("中药种植监控系统-登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 240);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 标题
        JLabel title = new JLabel("中药种植监控系统-登录", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // 输入面板 - 使用GridBagLayout实现标签在输入框左侧
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // 账号标签
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("账号:"), gbc);
        
        // 账号输入框
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        inputPanel.add(usernameField, gbc);
        
        // 密码标签
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("密码:"), gbc);
        
        // 密码输入框
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        inputPanel.add(passwordField, gbc);
        
        add(inputPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton loginButton = new JButton("登录");
        JButton resetButton = new JButton("重置");
        buttonPanel.add(loginButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 事件监听
        loginButton.addActionListener(event -> attemptLogin());
        resetButton.addActionListener(event -> resetFields());
        
        // Enter键登录功能
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attemptLogin();
                }
            }
        };
        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }

    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (DEFAULT_USERNAME.equals(username) && DEFAULT_PASSWORD.equals(password)) {
            JOptionPane.showMessageDialog(this, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "账号密码错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
