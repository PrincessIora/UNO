# UNO
Uno card game made w/ Java and JavaFX


✨ Overview

This project is a fully interactive UNO-style card game built with Java + JavaFX, featuring:

Turn-based multiplayer gameplay (2–4 players)
AI opponents with decision-making logic
Wild cards, draw mechanics, skip/reverse rules
UNO call system with timed penalty window
Animated UI with card interactions and visual feedback
MVC architecture for clean separation of logic, model, and view


⚙️ Requirements

Before installation, ensure your system has:

JDK 17 or later
JavaFX SDK 


📦 Installation & Setup
1. Clone or Download the Project
git clone <your-repo-url>
cd UnoProject
2. Add JavaFX SDK

Download JavaFX from:
https://openjfx.io/

Extract it somewhere like:

C:\javafx-sdk-21\
3. Configure VM Options

When running the project, add VM arguments:

Windows Example:
--module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml
Mac/Linux Example:
--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
4. Run the Application

Run the main class:

UnoApp.java

This will launch the setup screen first.


🎮 How to Play
🏁 Game Start
Select number of players (2–4)
Enter player names
Choose AI or human players
Click Start Game
🃏 Gameplay Rules
Match cards by color or value
Wild cards allow color selection
Draw Two / Wild Draw Four forces opponent to draw cards
Skip and Reverse control turn order
If no valid move exists → draw a card automatically
👑 UNO Rule

When a player reaches 1 card, they must press:

UNO! button within 2 seconds

Failing to do so results in:

Penalty: Draw 2 cards
🤖 AI Behavior

AI players:

Prioritize action cards (Skip, Reverse, Draw Two)
Use Wild Draw Four strategically
Fall back to playable neutral cards
🧠 Architecture (MVC Design)
Model

Handles game state and rules:

Deck generation
Card validation
Turn logic
Scoring system
View

JavaFX UI layer:

Card rendering
Animations
Player interaction
Setup & game screens
Controller

Bridge between Model and View:

Handles clicks
Processes turns
Manages AI flow
Enforces UNO rules
🎨 Features
Smooth card hover animation
Highlighted playable cards
Turn indicator display
Win screen with scoreboard
Animated victory sequence
Dynamic opponent card display
🏆 Winning Condition

A player wins when:

Their hand becomes empty
Score is calculated based on remaining opponent cards
🔁 Restart & Exit
Restart → Resets game with same players
Exit → Closes application safely
⚠️ Known Requirements
JavaFX must be correctly linked via VM arguments

Resources folder must contain card images:

/Resources/card_back.png
/Resources/{color}_{value}.png

