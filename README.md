#  Whot Game 🃏🇳🇬

A command-line version of the classic **Whot** card game, developed in Java.

---
## Game Overview

Whot is a Nigerian card game played with a special deck of cards.   
Players take turns to play by matching the shape or the number of the current call card. The first player to play all their cards wins.

Official game documentation: [https://en.wikipedia.org/wiki/Whot!](https://en.wikipedia.org/wiki/Whot!)

--- 
## How To Play

Consult the official game documentation to understand the rules of the game.

This version currently supports only a single player (you vs. the computer).

1. Launch the game
2. Enter your name.
3. Each player (you and computer) is dealt five cards
4. On your turn to play:
    - View the **call card**.
    - You can choose to play a card or go to market (i.e., draw a card).
    - You can only play a card that matches by shape or the number of the call card.
5. The deck has some special cards with special meanings: 

    | Card Number     | Action                                                                                                |
    |-----------------|-------------------------------------------------------------------------------------------------------|
    | 1               | Hold on: Player plays a second time                                                                   |
    | 8               | Similar to a 1 card: Player plays a second time                                                       |
    | 2               | Opponent picks 2 or can defend by playing a 2                                                         |
    | 5               | Opponent picks 3  or can defend by playing a 5                                                        |
    | 14              | General Market (all other players must pick a card from the market deck)                              |
    | 20 (Whot Card)  | Player can request a shape to be played from the opponent. Opponent can defend with a 20 (whot card.) |

6. First player to play all their cards wins the game.

---
## Technologies

- Java 11 (OpenJDK 11.0.26)
- JUnit 5 used for testing
--- 

## How to run the game 

### Option 1 (Using an IDE (e.g. IntelliJ IDEA))

If you are using an IDE such as IntelliJ, follow these steps:

1. Make sure you have java 11+ installed. 
   - To check your java version, run the following command in your terminal
   ```
   java --version
   javac --version
   ```
   - if you don't have java 11 or later installed, you can download a free and open source JDK from [Azul Zulu](https://www.azul.com/downloads/?version=java-11-lts&os=windows&architecture=x86-64-bit&package=jdk-fx#zulu) 

2. Open your terminal/command line and clone this repo  
    ```
    git clone https://github.com/rheedhar/WHOT.git
   ```
   Alternatively, you can download a zip folder of this repo using the green code button / drop down at the top of this page

3. Open the project folder in your IDE
4. Locate the Main.java file in the project folder.
5. Right click and run the file.

### Option 2 (Using the Terminal/Command Line)
1. Make sure you have java 11+ installed.
    - To check your java version, run the following command in your terminal
   ```
   java --version
   javac --version
   ```
    - if you don't have java 11 or later installed, you can download a free and open source JDK from [Azul Zulu](https://www.azul.com/downloads/?version=java-11-lts&os=windows&architecture=x86-64-bit&package=jdk-fx#zulu)

2. Clone or download this repo and navigate to the project folder from the command line
    ```
    git clone https://github.com/rheedhar/WHOT.git
    cd WHOT
   ```
3. Compile the source files using the following command
    ```
     javac -d out $(find src -name "*.java")
    ```
   This command will compile the source files in the src folder and place them in a folder called **out** within the project directory

4. Run the game using this command
   ```
     java -cp out whot.Main
   ```
