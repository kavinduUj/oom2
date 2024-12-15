Release Notes for Social Media Application

Version 1.0.0

Overview

This release introduces the initial version of the Social Media Application, a Java Swing-based desktop application that mimics a social media platform. The application is designed to demonstrate user interactions such as subscribing to updates, publishing messages, and managing user panels. It includes foundational features for a social media experience and is accompanied by a robust set of unit tests.

Features

Core Application

Main UI (App.java):

A user-friendly interface built with Java Swing, featuring:

Upper panel for user-generated content.

Lower panel with a text area and buttons for publishing posts and managing users.

Scrollable panels for individual user views.

Supports creating and managing multiple user panels dynamically.

Buttons for publishing messages, adding new users, and managing subscriptions.

User Management:

Each user is represented by a dynamic panel, showing the user's unique ID and subscribed messages.

Users can subscribe to or unsubscribe from updates and remove themselves from the application.

Messaging System:

Allows users to publish messages visible to all subscribed users.

Messages are stored and displayed in reverse chronological order.

Restricts input messages to 250 characters for a concise user experience.

Backend Architecture

Observer Pattern Implementation:

Observable (SocialApp.java):

Manages the list of observers (users).

Notifies all subscribed observers when a new message is published.

Handles user subscription and unsubscription.

Observer (CreateUser.java):

Users receive and locally store updates from the observable.

Supports retrieving messages in reverse chronological order.

Testing

Unit Tests:

PublisherTests.java:

Validates user subscription and unsubscription.

Ensures notifications are properly delivered to subscribers.

UserTests.java:

Tests local message storage and retrieval for individual users.

UITests.java:

Ensures UI functionality for adding/removing user panels and publishing messages.

Uses Mockito for mocking where needed to isolate test scenarios.

Utilities

Helper methods for finding UI components dynamically during tests.

Modular and reusable components for UI interactions.

Improvements

Optimized the rendering of user panels for scalability and performance.

Ensured robust input validation for text areas.

Known Issues

No support for real-time updates across networked instances.

Limited to a fixed window size of 1400x800.

Requires manual UI testing for complex edge cases not covered in tests.

Next Steps

Add real-time networking features for a multi-user experience.

Implement a database for persistent user and message storage.

Expand the UI to support flexible resizing and mobile compatibility.

Include additional testing for edge cases and error handling.
