package com.oom.as01;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class App {

	JFrame mainFrame;
	JPanel upperPanel;
	JPanel mainTextArea;
	JPanel mainBtnGroup;
	JPanel scrollable;
	List<JPanel> userPanels;
	
	SocialApp socialApp;

	public App() {
		mainUi();
	}
	
	
	@SuppressWarnings("serial")
	public void mainUi() {
		
		// Setup the main social app class
		socialApp = new SocialApp();

        // creating main frame which is include all the UI
        mainFrame = new JFrame("Social Media Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1400, 800);
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        userPanels = new ArrayList<>();

        // Panel A setup (upper panel)
        upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollable = new JPanel();
        scrollable.setLayout(new GridBagLayout());
        scrollPane.setViewportView(scrollable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        upperPanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.75; // set height 
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(upperPanel, gbc);

        JPanel panelB = new JPanel();
        panelB.setLayout(new GridBagLayout());

        // Panel B1 initiate
        mainTextArea = new JPanel();
        mainTextArea.setLayout(new GridBagLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (getLength() + str.length() <= 250) {
                    super.insertString(offs, str, a);
                }
            }
        });

        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add 10px padding
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);

        GridBagConstraints textAreaConstraints = new GridBagConstraints();
        textAreaConstraints.gridx = 0;
        textAreaConstraints.gridy = 0;
        textAreaConstraints.weightx = 1.0;
        textAreaConstraints.weighty = 1.0;
        textAreaConstraints.insets = new Insets(10, 10, 10, 10);
        textAreaConstraints.fill = GridBagConstraints.BOTH;

        mainTextArea.add(textAreaScrollPane, textAreaConstraints);

        // Panel B2 initiate
        mainBtnGroup = new JPanel();
        mainBtnGroup.setLayout(new GridBagLayout());
        JButton publishButton = new JButton("Publish");
        JButton newUserButton = new JButton("New User");

        GridBagConstraints b2Constraints = new GridBagConstraints();
        b2Constraints.gridx = 0;
        b2Constraints.gridy = 0;
        b2Constraints.weightx = 1.0;
        b2Constraints.weighty = 0.5;
        b2Constraints.fill = GridBagConstraints.BOTH;
        mainBtnGroup.add(publishButton, b2Constraints);

        b2Constraints.gridy = 1;
        mainBtnGroup.add(newUserButton, b2Constraints);

        // Adding Panel B1 and B2 to Panel B
        GridBagConstraints bConstraints = new GridBagConstraints();
        bConstraints.weightx = 0.5;
        bConstraints.weighty = 1.0;
        bConstraints.fill = GridBagConstraints.BOTH;

        bConstraints.gridx = 0;
        panelB.add(mainTextArea, bConstraints);

        bConstraints.gridx = 1;
        panelB.add(mainBtnGroup, bConstraints);

        // Constraints for Panel B
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25; // Panel B takes 25% height
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(panelB, gbc);

        // Add ActionListener for "New User" button
        newUserButton.addActionListener(e -> addNewUser());

        // Add ActionListener for "Publish" button
        publishButton.addActionListener(e -> publishMessage());

        // Display the frame
        mainFrame.setVisible(true);
	}
	
	private void addSubPanel() {
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.setPreferredSize(new Dimension(1400 / 3, 500));

        JScrollPane scrollPane = new JScrollPane();
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 25; i++) {
            JTextArea textArea = new JTextArea("Text Area " + i);
            textArea.setLineWrap(true);
            textArea.setMaximumSize(new Dimension(1400 / 3, 30));
            textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalPanel.add(textArea);
            verticalPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        }
        scrollPane.setViewportView(verticalPanel);
        subPanel.add(scrollPane, BorderLayout.CENTER);
        subPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton removeButton = new JButton("Remove");
        JButton unsubscribeButton = new JButton("Unsubscribe");
        JButton subscribeButton = new JButton("Subscribe");
        buttonPanel.add(removeButton);
        buttonPanel.add(subscribeButton);
        buttonPanel.add(unsubscribeButton);

        
        removeButton.addActionListener(e -> {
          
            Container parent = subPanel.getParent();
            if (parent != null) {
                parent.remove(subPanel);
                parent.revalidate();
                parent.repaint();
            }
        });

        subPanel.add(buttonPanel, BorderLayout.SOUTH);
        scrollable.add(Box.createRigidArea(new Dimension(10, 0)));
        scrollable.add(subPanel, 0);
        userPanels.add(verticalPanel);

        scrollable.revalidate();
        scrollable.repaint();
    }

	private void addNewUser() {
		CreateUser newUser = new CreateUser();
		socialApp.addObserver(newUser);
        List<String> messages = newUser.getAllMessages();
        addSubPanel();
    }

    private void publishMessage() {
        if (!userPanels.isEmpty()) {
            for (JPanel panel : userPanels) {
                JTextArea newTextArea = new JTextArea("New Text Area");
                newTextArea.setPreferredSize(new Dimension(1400 / 3, 80));
                newTextArea.setMaximumSize(new Dimension(1400 / 3, 80));
                newTextArea.setMinimumSize(new Dimension(1400 / 3, 80));
                newTextArea.setLineWrap(true);
                newTextArea.setWrapStyleWord(true);
                newTextArea.setEditable(false);
                newTextArea.setCaretPosition(1);
                newTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
                panel.add(newTextArea, 0); // Add to the top
                panel.add(Box.createRigidArea(new Dimension(0, 10)), 1); // Add vertical gap
                panel.revalidate();
                panel.repaint();
            }
        }
    }


	public static void main(String[] args) {
		SwingUtilities.invokeLater(App::new);
	}
}
