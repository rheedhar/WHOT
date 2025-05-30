# 🃏 Whot Game 

A command-line version of the classic **Whot** card game, developed in Java.

---
## Game Overview

Whot is a Nigerian card game played with a special deck of cards.   
Players take turn to play by matching the shape of the call card or the number of the call card. The first player to play all their cards wins the game

Official game documentation: [https://en.wikipedia.org/wiki/Whot!](https://en.wikipedia.org/wiki/Whot!)

--- 
## How To Play

This version currently supports only a single player (i.e. you and the computer will play against each other)


1. Launch the game
2. Enter your name.
3. Each player (you and computer) is dealt five cards
4. On your turn to play:
    - View the **call card**.
    - You can choose to play a card or go to market (pick a card).
    - You can only play a card that matches by shape (i.e. suit) or card number (i.e. rank), or play a special card.
5. Special cards :

    | Card Number     | Action                                                                    |
    |-----------------|---------------------------------------------------------------------------|
    | 1               | Hold on (play again)                                                      |
    | 8               | Similar to a 1 card (Hold on (play again)  )                              |
    | 2               | Opponent picks 2                                                          |
    | 5               | Opponent picks 3                                                          |
    | 14              | General Market (all other players must pick a card from the market deck)  |
    | 20 (Whot Card)  | Wild card (player request a shape/suit to be played from the next player) |

6. First player to play all their cards wins the game.

--- 

## Features
 - Single player mode vs computer
 - Clean and readable terminal UI
 - Scoreboard to track scores and game rounds
 - Unit tested core components

---

## Technologies

- Java 11 (OpenJDK 11.0.26)
- JUnit 5 used for testing
- No external libraries required

--- 

## How to run game 

#### Prerequisites

- Java installed on local machine (Java 11 or newer)
- Optional: IntelliJ or another IDE

### Steps
1. Clone this repository
   ```
      git clone https://github.com/rheedhar/WHOT.git
      cd WHOT
   ```
2. Compile game.
    ```
       javac -d out src/whot/**/*.java
    ```
   Note: If you are using an IDE such as IntelliJ, you can simply open the project in intelliJ and run the Main.java file.  


3. Run game
    ```
       java -cp out whot.Main
    ```
