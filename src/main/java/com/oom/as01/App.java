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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class App {

	JFrame mainFrame;
	JPanel upperPanel;
	JPanel mainTextAreaPanel;
	JPanel mainBtnGroup;
	JPanel scrollable;
	List<JPanel> userPanels;
	JTextArea mainTextArea;
	
	SocialApp socialApp;
	Map<String, JPanel> userIDPanelMap;

	public App() {
		mainUi();
	}
	
	
	@SuppressWarnings("serial")
	public void mainUi() {
		
		// Setup the main social app class
		socialApp = new SocialApp();
		
		// Setup map to keep track of user and their associated panel
        userIDPanelMap = new HashMap<>();

        // creating main frame which is include all the UI
        mainFrame = new JFrame("Social Media Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMaximumSize(new Dimension(1400, 800));
        mainFrame.setMinimumSize(new Dimension(1400, 800));
        mainFrame.setPreferredSize(new Dimension(1400, 800));
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setResizable(false);

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

        // Constraints for Panel A
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.75; // Panel A takes 75% height
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(upperPanel, gbc);

        // Panel B setup (lower panel)
        JPanel panelB = new JPanel();
        panelB.setLayout(new GridBagLayout());

        // Panel B1 setup
        mainTextAreaPanel = new JPanel();
        mainTextAreaPanel.setLayout(new GridBagLayout());

        mainTextArea = new JTextArea();
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (getLength() + str.length() <= 250) {
                    super.insertString(offs, str, a);
                }
            }
        });

        mainTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add 10px padding
        JScrollPane textAreaScrollPane = new JScrollPane(mainTextArea);

        GridBagConstraints textAreaConstraints = new GridBagConstraints();
        textAreaConstraints.gridx = 0;
        textAreaConstraints.gridy = 0;
        textAreaConstraints.weightx = 1.0;
        textAreaConstraints.weighty = 1.0;
        textAreaConstraints.insets = new Insets(10, 10, 10, 10);
        textAreaConstraints.fill = GridBagConstraints.BOTH;

        mainTextAreaPanel.add(textAreaScrollPane, textAreaConstraints);

        // Panel B2 setup
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
        panelB.add(mainTextAreaPanel, bConstraints);

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
	
	private void addSubPanel(CreateUser user) {
		JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.setPreferredSize(new Dimension(1400 / 3, 500)); // Fixed height of 500px

        JScrollPane scrollPane = new JScrollPane();
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setPreferredSize(new Dimension(1400 / 3, 500));

        this.userIDPanelMap.put(user.getUserID(), verticalPanel);

        for (String message : user.getAllMessagesInReverseOrder()) {
            JTextArea textArea = getJTextArea(message);
            verticalPanel.add(textArea);
            verticalPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical gap
        }

        scrollPane.setViewportView(verticalPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        subPanel.add(scrollPane, BorderLayout.CENTER);
        subPanel.add(Box.createVerticalGlue());

        // Create a JPanel for the bottom area of the subpanel
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcBottom = new GridBagConstraints();
        gbcBottom.insets = new Insets(5, 5, 5, 5); // Add padding

        // Row 1: Buttons (Remove and Unsubscribe)
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // Two buttons in a row
        JButton removeButton = new JButton("Remove");
        JButton unsubscribeButton = new JButton("Unsubscribe");
        JButton subscribeButton = new JButton("Subscribed");
        buttonsPanel.add(removeButton);
        buttonsPanel.add(subscribeButton);
        buttonsPanel.add(unsubscribeButton);

        subscribeButton.setEnabled(false); // New users are subscribed by default

        gbcBottom.gridx = 0;
        gbcBottom.gridy = 0;
        gbcBottom.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(buttonsPanel, gbcBottom);

        // Row 2: Label
        JLabel infoLabel = new JLabel("User ID: " + user.getUserID(), SwingConstants.CENTER);
        gbcBottom.gridx = 0;
        gbcBottom.gridy = 1;
        gbcBottom.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(infoLabel, gbcBottom);

        // Add the bottomPanel to the subpanel
        subPanel.add(bottomPanel, BorderLayout.SOUTH); // Add at the bottom of the subpanel


        // Add a listener to the "Remove" button to remove the sub-panel
        removeButton.addActionListener(e -> {
            this.socialApp.removeObserver(user);
            this.userIDPanelMap.remove(user.getUserID());
            // Remove the current sub-panel from its parent container (scrollablePanel)
            Container parent = subPanel.getParent();  // Get the parent container (scrollablePanel)
            if (parent != null) {
                parent.remove(subPanel);  // Remove the sub-panel from the parent container
                parent.revalidate();  // Revalidate the layout
                parent.repaint();  // Repaint to reflect the changes in the UI
            }
        });

        // Add a listener to the "Subscribe" button to add the subscription to main channel
        subscribeButton.addActionListener(e -> {
            user.clearLocalPosts();
            this.socialApp.addObserver(user);
            subscribeButton.setEnabled(false);
            subscribeButton.setText("Subscribed");
            unsubscribeButton.setEnabled(true);
            verticalPanel.removeAll();
            for (String message : user.getAllMessagesInReverseOrder()) {
                JTextArea textArea = getJTextArea(message);
                verticalPanel.add(textArea);
                verticalPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical gap
            }
            scrollable.revalidate();
            scrollable.repaint();

        });

        // Add a listener to the "Unsubscribe" button to remove the subscription from the main panel
        unsubscribeButton.addActionListener(e -> {
            this.socialApp.removeObserver(user);
            unsubscribeButton.setEnabled(false);
            unsubscribeButton.setText("Unsubscribed");
            subscribeButton.setEnabled(true);
            subscribeButton.setText("Subscribe");
        });

        subPanel.add(buttonsPanel, BorderLayout.SOUTH);
        scrollable.add(Box.createRigidArea(new Dimension(10, 0))); // Add horizontal gap
        scrollable.add(subPanel, 0);
        userPanels.add(verticalPanel);

        scrollable.revalidate();
        scrollable.repaint();
    }

	private void addNewUser() {
		CreateUser newUser = new CreateUser(Integer.toString(this.socialApp.getAndIncrementUserCounter()));
		socialApp.addObserver(newUser);
        addSubPanel(newUser);
    }

    private void publishMessage() {
    	if (this.mainTextArea.getText().isEmpty()) {
            // No need to publish empty messages
            return;
        }
        // Send the post to main app
        this.socialApp.addPost(this.mainTextArea.getText());
        // Clear out the text area for the next input
        this.mainTextArea.setText("");

        for (Observer observer : this.socialApp.getObserverList()) {
            JPanel panel = this.userIDPanelMap.get(observer.getUserID());

            if (panel != null) {
                JTextArea newTextArea = getJTextArea(observer.getLastMessage());
                panel.add(newTextArea, 0); // Add to the top
                panel.add(Box.createRigidArea(new Dimension(0, 10)), 1); // Add vertical gap
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    private static JTextArea getJTextArea(String message) {
        JTextArea newTextArea = new JTextArea(message);
        newTextArea.setMaximumSize(new Dimension(1400 / 3, 40)); // Fixed height for text areas
        newTextArea.setLineWrap(true);
        newTextArea.setWrapStyleWord(true);
        newTextArea.setEditable(false);
        newTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        return newTextArea;
    }

	public static void main(String[] args) {
		SwingUtilities.invokeLater(App::new);
	}
}
