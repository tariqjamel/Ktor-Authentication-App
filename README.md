# Ktor Authentication App

## Overview

This is a Ktor authentication app that demonstrates user authentication using a sign-up, sign-in, and home screen flow. The app integrates with an API for user authentication and utilizes Dagger Hilt for dependency injection and KSP for navigation destination.

## Features

- **Sign-Up Screen**: Allows users to register by entering a username and password.
- **Sign-In Screen**: Allows users to log in with their credentials.
- **Home Screen**: Displays after successful authentication and persists the user session for subsequent app launches.

## Architecture

- **Dagger Hilt**: Used for dependency injection to manage app components and dependencies.
- **KSP (Kotlin Symbol Processing)**: Used for navigation determination and to handle the app's navigation flow.

## API Integration

The app utilizes a Ktor-based authentication API featuring:

- **JWT (JSON Web Tokens)**: For secure user authentication.
- **MongoDB**: For data storage, including user information.
- **SHA-256**: For hashing passwords.

### Configuration

- The API is configurable for local use with customizable IP addresses and MongoDB settings.

For more details, check out the [**Ktor Auth API repository**](https://github.com/tariqjamel/Ktor-Auth-API).

## How It Works

1. **Sign-Up and Sign-In**:
   - Users can sign up or sign in using a username and password.
   - Authentication is handled via an API, and upon successful login, a token is issued.
   
2. **Token Management**:
   - The app stores the authentication token securely.
   - On subsequent app launches, the token is checked, and the user is redirected to the home screen if already authenticated.

3. **Navigation**:
   - After successful sign-in, users are automatically navigated to the home screen.
   - Navigation is handled via KSP, ensuring smooth transitions between screens.

## Dependencies

- **Dagger Hilt**: Dependency Injection
- **KSP**: Navigation Determination

## Usage

1. **Sign-Up**: Enter your desired username and password to create an account.
2. **Sign-In**: Log in with your credentials.
3. **Home Screen**: Access the home screen after successful authentication.

## Contribution

Feel free to contribute by submitting issues or pull requests. Ensure to follow the project's coding standards and guidelines.

