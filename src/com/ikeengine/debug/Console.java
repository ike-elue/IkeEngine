package com.ikeengine.debug;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Jonathan Elue
 */
public class Console extends JFrame implements ActionListener{

    private final JPanel mainPanel;
    
    private final JPanel topPanel;
    private final JPanel bottomPanel;
    
    private final JLabel messages;
    private String display;
    private final List<Message> internalMessages;
    
    private final JTextField messageType, data;
    private final JButton enter, clear;
    
    public Console(String title) {
        super(title + "'s Console");
        
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        mainPanel = new JPanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        
        messages = new JLabel("", SwingConstants.CENTER);
        
        messageType = new JTextField("Enter in Message");
        data = new JTextField("Enter in Data");
        enter = new JButton("SEND");
        clear = new JButton("Clear");
        
        internalMessages = new ArrayList<>();
        display = "";
        
        init();
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void init() {
        mainPanel.setLayout(new BorderLayout());
        
        bottomPanel.setLayout(new GridLayout(1, 4, 5, 5));
        
        messages.setFont(new Font("Serif", Font.PLAIN, 10));
        
        enter.addActionListener(this);
        clear.addActionListener(this);
        
        topPanel.add(messages);
        
        bottomPanel.add(messageType);
        bottomPanel.add(data);
        bottomPanel.add(enter);
        bottomPanel.add(clear);        
    
        mainPanel.add(topPanel, BorderLayout.WEST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void update(MessageBus bus, String fpsString) {
        display = fpsString + "<br>";
        if(!internalMessages.isEmpty()) {
            bus.addMessages(internalMessages);
            internalMessages.clear();
        }
        bus.getMessages().stream().forEach((m) -> {
            display += m + "<br>";
        });
        messages.setText("<html>" + display + "</html>");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source.equals(enter) && !messageType.getText().equals("") && !data.getText().equals(""))
            internalMessages.add(new Message(-1, "console").setMessage(messageType.getText(), data.getText()));
        if(source.equals(clear)) {
            messageType.setText("");
            data.setText("");
        }
            
    }
}
