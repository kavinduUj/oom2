package com.oom.as01;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)  // This enables Mockito for mocking if needed
public class UITests {
    private App mainUI;

    @BeforeEach
    void setUp() {
        SwingUtilities.invokeLater(() -> {
        	mainUI = new App();
        	mainUI.mainFrame.setVisible(true);
        });
    }

    @AfterEach
    void tearDown() {
        SwingUtilities.invokeLater(() -> mainUI.mainFrame.dispose());
    }

    @Test
    @DisplayName("Test adding a new user panel")
    void testAddNewUserPanel() {
        SwingUtilities.invokeLater(() -> {
            int initialUserCount = mainUI.userPanels.size();

            // Simulate clicking the "New User" button
            JButton newUserButton = findButton(mainUI.mainBtnGroup, "New User");
            assertNotNull(newUserButton, "New User button should exist");
            newUserButton.doClick();

            // Check that a new user panel was added
            assertEquals(initialUserCount + 1, mainUI.userPanels.size(), "A new user panel should be added");
        });
    }

    @Test
    @DisplayName("Test publishing a message")
    void testPublishMessage() {
        SwingUtilities.invokeLater(() -> {
            // Set text in the main text area
            String message = "Test message";
            mainUI.mainTextArea.setText(message);

            // Simulate clicking the "Publish" button
            JButton publishButton = findButton(mainUI.mainBtnGroup, "Publish");
            assertNotNull(publishButton, "Publish button should exist");
            publishButton.doClick();

            // Check that the message was cleared from the main text area
            assertEquals("", mainUI.mainTextArea.getText(), "Text area should be cleared after publishing");

            // Verify the message was added to all user panels
            for (JPanel userPanel : mainUI.userPanels) {
                JTextArea textArea = findTextArea(userPanel, message);
                assertNotNull(textArea, "Message should be added to each user panel");
            }
        });
    }

    @Test
    @DisplayName("Test removing a user panel")
    void testRemoveUserPanel() {
        SwingUtilities.invokeLater(() -> {
            // Add a new user first
            JButton newUserButton = findButton(mainUI.mainBtnGroup, "New User");
            assertNotNull(newUserButton, "New User button should exist");
            newUserButton.doClick();

            int initialUserCount = mainUI.userPanels.size();

            // Get the first user panel and simulate clicking the "Remove" button
            JPanel firstPanel = mainUI.userPanels.get(0);
            JButton removeButton = findButton(firstPanel, "Remove");
            assertNotNull(removeButton, "Remove button should exist in user panel");
            removeButton.doClick();

            // Check that the user panel was removed
            assertEquals(initialUserCount - 1, mainUI.userPanels.size(), "A user panel should be removed");
        });
    }

    // Utility method to find a JButton by its text
    private JButton findButton(Container container, String buttonText) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals(buttonText)) {
                return (JButton) component;
            }
        }
        return null;
    }

    // Utility method to find a JTextArea containing specific text
    private JTextArea findTextArea(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextArea && ((JTextArea) component).getText().equals(text)) {
                return (JTextArea) component;
            }
        }
        return null;
    }

}
