package com.oom.as01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)  // This enables Mockito for mocking if needed
public class UITests {
    private App mainUI;

    @BeforeEach
    public void setUp() {
        // Ensure that the UI is initialized on the EDT
        SwingUtilities.invokeLater(() -> {
            mainUI = new App();
        });
    }

    @Test
    public void testInitialUIState() throws InterruptedException, InvocationTargetException {
        // Wait for the UI to be fully initialized
        SwingUtilities.invokeAndWait(() -> {
            // Test the initial state of the UI
            assertThat(mainUI.mainFrame.isVisible()).isTrue();  // Check if the frame is visible

            // Verify the initial number of sub-panels in Panel A is 0. i.e. No users at first
            assertThat(mainUI.scrollable.getComponentCount()).isEqualTo(0);
        });
    }

    @Test
    public void testPublishButtonAddsTextAreaToAllSubPanels() {

    }

    @Test
    public void testNewUserButtonAddsNewSubPanel() {

    }

    @Test
    public void testRemoveUserButtonRemovesSubPanel() {

    }
}
