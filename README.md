# GoFish
The GoFish program simulates the card game GoFish using Java. 

## Overview
My GoFish project allows a human player, or user, to play GoFish against 3 AI/computer bots. This program utilizes the Shell to display the game's relevant components.

## GoFish Gameplay & Rules
- Objective: Get the greatest # of 4 of a kind/card rank.
- Player left of dealer goes first.
- Each player is dealt 5 cards at the start of the game.
- Turn player asks another player for a card rank. The turn player MUST hold a card of the same rank.
- If an asked player has a card(s) of the same rank, he/she must give the asking player the card(s), and the asking player goes again.
- If an asked player does not have a card of the same rank, he/she says "Go Fish!", the asking player draws a card, and the next player goes.
- If a player draws a card and a pair is made or if a player starts the game with a hand that contains a pair, the pair is set aside and increases the player's pair count by 1. 
- The game continues until all pairs have been made (13 pairs with each pair containing 4 cards of the same rank).
- If a player runs out of cards, he/she draws 5 cards. If there are less than 5 cards in the deck, the drawing player draws the remaining cards.
- When the deck runs out, players continue playing until all players are left with 0 cards in their hands.
- If a player runs out of cards and the deck is out of cards, the player is out and no longer gets a turn.
- At the end, each player counts the number of pairs he/she made and the player with the most pairs wins!

## Installing & Running the Program
1. Clone the git repository in the desired directory and navigate to the directory using your Shell.
2. Run the command "javac GoFish.java" in the Shell.
3. Run the command "java GoFish" in the Shell and Enjoy! (NOTE: If the game freezes, press any number key and it should resume. Erase the number you pressed when you are prompted for which player to ask).

## Plans For the Future
- Enable Computer players to store and remember who asked for what and use this to play strategically.
- Fix Game-Freeze, Infinite Game Loop, and other bugs.

  
