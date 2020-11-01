import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;

/*
	This is the code for the GoFish program. I used "Thread.sleep()" throughout the game in order to 
	slow down the pace of play and make it run at a realistic pace. I employed an abstract Player class
	which is inherited by the HumanPlayer and ComputerPlayer classes, and I used a Deck class.   
	ComputerPlayer, and Deck classes. These classes represent the key components of the GoFish program
	and are used extensively throughout this program.
*/
public class GoFish
{
	public static void main(String[] args) throws InterruptedException 
	{
		Scanner scan = new Scanner(System.in);
		slowPrinter("Welcome To Go Fish! Let's Get Started. Enter Any Key To Continue. If You Want To Exit, Type \"Exit\" Now.", 29, 250, 800);
		String play = scan.next().toLowerCase();
		if (play.equals("exit"))
		{
			slowPrinter("Have a Great Day!", 26, 300);
			System.exit(0);
		}
		Deck standardDeck = new Deck();
		standardDeck.createDeck();
		Player playerOne = new HumanPlayer();
		Player playerTwo = new Computer();
		Player playerThree = new Computer();
		Player playerFour = new Computer();
		int dealerPlayer = (int)((Math.random()+0.25)*4);
		int turnPlayer = 0;
		//The First Player to go will be decided based on who the Dealer Player is 
		switch(dealerPlayer)
		{
			case 1:

				slowPrinter("Player 1 Is The Dealer.");
				playerOne = new HumanPlayer(standardDeck.distributeStartingHands(), 1);
				playerTwo = new Computer(standardDeck.distributeStartingHands(), 2);
				playerThree = new Computer(standardDeck.distributeStartingHands(), 3);
				playerFour = new Computer(standardDeck.distributeStartingHands(), 4);
				turnPlayer = 2;
				break;
			case 2:
				slowPrinter("Player 2 Is The Dealer.");
				playerTwo = new Computer(standardDeck.distributeStartingHands(), 2);
				playerThree = new Computer(standardDeck.distributeStartingHands(), 3);
				playerFour = new Computer(standardDeck.distributeStartingHands(), 4);
				playerOne = new HumanPlayer(standardDeck.distributeStartingHands(), 1);
				turnPlayer = 3;
				break;
			case 3:
				slowPrinter("Player 3 Is The Dealer.");
				playerThree = new Computer(standardDeck.distributeStartingHands(), 3);
				playerFour = new Computer(standardDeck.distributeStartingHands(), 4);
				playerOne = new HumanPlayer(standardDeck.distributeStartingHands(), 1);
				playerTwo = new Computer(standardDeck.distributeStartingHands(), 2);
				turnPlayer = 4;
				break;
			case 4:
				slowPrinter("Player 4 Is The Dealer.");
				playerFour = new Computer(standardDeck.distributeStartingHands(), 4);
				playerOne = new HumanPlayer(standardDeck.distributeStartingHands(), 1);
				playerTwo = new Computer(standardDeck.distributeStartingHands(), 2);
				playerThree = new Computer(standardDeck.distributeStartingHands(), 3);
				turnPlayer = 1;
		}
		boolean run = true;
		if(turnPlayer != 1)
		{
			slowPrinter("Your Hand: " + playerOne.cardsInHand, 14, 600);
		}
		//Initial check to see if any player's starting hand has a pair in it.
		playerOne.checkPair();
		playerOne.pairMade();
		playerTwo.checkPair();
		playerTwo.pairMade();
		playerThree.checkPair();
		playerThree.pairMade();
		playerFour.checkPair();
		playerFour.pairMade();
		while(run)
		{
			Collections.sort(playerOne.cardsInHand);
			Collections.sort(playerTwo.cardsInHand);
			Collections.sort(playerThree.cardsInHand);
			Collections.sort(playerFour.cardsInHand);
			Player currentPlayer = playerOne;
			Player askedPlayer = playerOne;
			String cardChoice = " "; 
			int checkHandWorked = 0;
			if(standardDeck.deck.size() == 0)
			{
				if(playerOne.handIsEmpty() && playerTwo.handIsEmpty() && playerThree.handIsEmpty() && playerFour.handIsEmpty())
				{
					break;
				}
			}
			if(turnPlayer > 4)
			{
				turnPlayer = 1;
				continue;
			}
			switch (turnPlayer)
			{
				case 1:
					currentPlayer = playerOne;
					break;
				case 2:
					currentPlayer = playerTwo;
					break;
				case 3:
					currentPlayer = playerThree;
					break;
				case 4:
					currentPlayer = playerFour;
					break;
			}
			if(currentPlayer.playerOut == true)
			{
				turnPlayer += 1;
				continue;
			}
			//This switch block determines which of the 2 blocks of code runs (case 1 is for the Human Player/User, while cases 2-4 are for the AI players)
			switch(turnPlayer)
			{
				case 1:
					slowPrinter("It's your turn!");
					slowPrinter("Your Hand: " + playerOne.cardsInHand, 14, 600);
					currentPlayer.checkPair();
					currentPlayer.pairMade();
					Player.checkEmptyHand(currentPlayer, standardDeck);
					slowPrinter("Your Pairs Pile: " + playerOne.pairsPile, 14, 600);

					if (playerTwo.cardsInHand.size() > 0)
						slowPrinter("Number of cards in Player 2's Hand: " + playerTwo.cardsInHand.size());
					slowPrinter("Player 2's Pairs Pile: " + playerTwo.pairsPile, 14, 600);

					if (playerThree.cardsInHand.size() > 0)
						slowPrinter("Number of cards in Player 3's Hand: " + playerThree.cardsInHand.size());
					slowPrinter("Player 3's Pairs Pile: " + playerThree.pairsPile, 14, 600);

					if (playerFour.cardsInHand.size() > 0)
						slowPrinter("Number of cards in Player 4's Hand: " + playerFour.cardsInHand.size());
					slowPrinter("Player 4's Pairs Pile: " + playerFour.pairsPile, 14, 600);

					boolean playerSelectionTrue = true;
					//Ensures User enters a number and asks a player that is still in the game (excluding themselves)
					while(playerSelectionTrue)
					{
						slowPrinter("Which player would you like to ask?", 28, 200);
						String playerChoice = scan.next();
						askedPlayer = currentPlayer.chooseAPlayer(Character.getNumericValue(playerChoice.charAt(0)), playerOne, playerTwo, playerThree, playerFour);
						if(askedPlayer.playerOut == true)
						{
							slowPrinter(askedPlayer.playerIdentifier + " is out of the game. Choose a different Player!");
							continue;
						}
						if(playerChoice == "1" || playerChoice.length() > 1)
						{
							slowPrinter("Please enter a number from 2-4");
							continue;
						}
						playerSelectionTrue = false;
					}
					boolean inputValidation = true;
					//Ensures the user does not ask for a rank they do not have in their hand
					while(inputValidation)
					{
						slowPrinter("Which Rank would you like to ask for?", 28, 200); 
						cardChoice = scan.next();
						if (cardChoice.length() >= 3)
							cardChoice = Character.toUpperCase(cardChoice.charAt(0)) + cardChoice.substring(1);
						int cardsExist = 0;
						for (String cards : currentPlayer.cardsInHand)
						{
							String cardArray = cards.split("\\s+")[0];
							if (cardArray.equals(cardChoice))
								cardsExist += 1;
						}
						if(cardsExist == 0)
						{
							slowPrinter("You do not have that Rank. Try Again!");
							continue;
						}
						inputValidation = false;
					}
					slowPrinter(currentPlayer.playerIdentifier + ": " + askedPlayer.playerIdentifier + ", do you have any " + cardChoice +"s?");
					while(askedPlayer.checkHand(cardChoice) == true)
					{
						String cardAdded = askedPlayer.giveSameRank(cardChoice);
						currentPlayer.cardsInHand.add(cardAdded);
						Collections.sort(currentPlayer.cardsInHand);
						Collections.sort(askedPlayer.cardsInHand);
						slowPrinter(askedPlayer.playerIdentifier + " gave you the " + cardAdded + ".");
						Player.checkEmptyHand(askedPlayer, standardDeck);
						checkHandWorked += 1;
						currentPlayer.checkPair();
						currentPlayer.pairMade();
						Player.checkEmptyHand(currentPlayer, standardDeck);
					}
					if (checkHandWorked == 0)
					{
						if(standardDeck.deck.size() > 0)
						{
							slowPrinter(askedPlayer.playerIdentifier + ": Go Fish!");
							currentPlayer.goFish(standardDeck);
							String cardDrawn = currentPlayer.cardsInHand.get(currentPlayer.cardsInHand.size() - 1).split("\\s+")[0];
							if (cardDrawn.equals(cardChoice)) 
							{
								slowPrinter(currentPlayer.playerIdentifier + " drew the " + currentPlayer.cardsInHand.get(currentPlayer.cardsInHand.size()-1)
										   + ". " + currentPlayer.playerIdentifier + " gets to go again!");
								continue;
							}
							slowPrinter("You drew the " + currentPlayer.cardsInHand.get(currentPlayer.cardsInHand.size()-1) + ".");
							Collections.sort(currentPlayer.cardsInHand);
							slowPrinter("Here is your new hand:" + currentPlayer.cardsInHand, 14, 600);
							currentPlayer.checkPair();
							currentPlayer.pairMade();
							Player.checkEmptyHand(currentPlayer, standardDeck);
							slowPrinter("Cards remaining in the deck: " + standardDeck.deck.size() + ".");
						}
						else
						{
							slowPrinter(askedPlayer.playerIdentifier + ": Go Fish!");
							slowPrinter("The deck is out of cards! The next Player goes!");
						}
						turnPlayer += 1;
					}
					else
						continue;
					break;
				case 2:
				case 3:
				case 4:
					/**
						By using a reference variable of type Player, the currentPlayer and askedPlayer only have to be assigned
						to the correct AI player and the code will run fine. This allows for great code reuse as all the cases 2-4,
						which account for when an AI is the turn player, are covered by the code block below.
					**/
					currentPlayer.checkPair();
					currentPlayer.pairMade();
					Player.checkEmptyHand(currentPlayer, standardDeck);
					int whoToAsk = currentPlayer.playerNumber;
					//This loop makes sure the AI player does not ask itself for a card!
					while(whoToAsk == currentPlayer.playerNumber)
						whoToAsk = (int)((Math.random() + 0.25)*4);
					askedPlayer = currentPlayer.chooseAPlayer(whoToAsk, playerOne, playerTwo, playerThree, playerFour);
					if(askedPlayer.playerOut == true)
						continue;
					slowPrinter("It's " + currentPlayer.playerIdentifier + "'s turn!");
					cardChoice = currentPlayer.cardsInHand.get((int)(Math.random()*currentPlayer.cardsInHand.size())).split("\\s+")[0];
					slowPrinter(currentPlayer.playerIdentifier + ": " + askedPlayer.playerIdentifier
									  + ", do you have any " + cardChoice.split("\\s+")[0] + "s?");
					while(askedPlayer.checkHand(cardChoice) == true)
					{
						String cardAdded = askedPlayer.giveSameRank(cardChoice);
						currentPlayer.cardsInHand.add(cardAdded);
						Collections.sort(currentPlayer.cardsInHand);
						Collections.sort(askedPlayer.cardsInHand);
						slowPrinter(askedPlayer.playerIdentifier + " gave " + currentPlayer.playerIdentifier + " the " + cardAdded + ".");
						Player.checkEmptyHand(askedPlayer, standardDeck);
						checkHandWorked += 1;
						currentPlayer.checkPair();
						currentPlayer.pairMade();
						Player.checkEmptyHand(currentPlayer, standardDeck);
					}
					if (checkHandWorked == 0)
					{
						if(standardDeck.deck.size() > 0)
						{
							slowPrinter(askedPlayer.playerIdentifier + ": Go Fish!");
							currentPlayer.goFish(standardDeck);
							String cardDrawn = currentPlayer.cardsInHand.get(currentPlayer.cardsInHand.size() - 1).split("\\s+")[0];
							if (cardDrawn.equals(cardChoice)) 
							{
								slowPrinter(currentPlayer.playerIdentifier + " drew the " + currentPlayer.cardsInHand.get(currentPlayer.cardsInHand.size()-1)
										   + ". " + currentPlayer.playerIdentifier + " gets to go again!", 28, 500);
								continue;
							}
							Collections.sort(currentPlayer.cardsInHand);
							currentPlayer.checkPair();
							currentPlayer.pairMade();
							Player.checkEmptyHand(currentPlayer, standardDeck);
							slowPrinter("Cards remaining in the deck: " + standardDeck.deck.size() + ".");
						}
						else
						{
							slowPrinter(askedPlayer.playerIdentifier + ": Go Fish!");
							slowPrinter("The deck is out of cards! The next Player goes!");
						}
						turnPlayer += 1;
					}
					else
						continue;
			}
		}
		slowPrinter("Game Over!");
		slowPrinter("Here are the results: ");
		slowPrinter("Player 1's Pairs: " + playerOne.pairsPile, 14, 600);
		slowPrinter("Player 1 had " + playerOne.pairsPile.size()/4 + " pairs.");
		slowPrinter("Player 2's Pairs: " + playerTwo.pairsPile, 14, 600);
		slowPrinter("Player 2 had " + playerTwo.pairsPile.size()/4 + " pairs.");
		slowPrinter("Player 3's Pairs: " + playerThree.pairsPile, 14, 600);
		slowPrinter("Player 3 had " + playerThree.pairsPile.size()/4 + " pairs.");
		slowPrinter("Player 4's Pairs: " + playerFour.pairsPile, 14, 600);
		slowPrinter("Player 4 had " + playerFour.pairsPile.size()/4 + " pairs.");
		ArrayList<Player> playerWithMaxPairs = new ArrayList<Player>(0);
		List<Integer> results = Arrays.asList(playerOne.pairsPile.size(), playerTwo.pairsPile.size(), playerThree.pairsPile.size(), playerFour.pairsPile.size()); 
		int max = Collections.max(results);
		for(int i = 0; i < results.size()-1; i++)
		{
			if (results.get(i) == max && i == 0)
				playerWithMaxPairs.add(playerOne);
			if (results.get(i) == max && i == 1)
				playerWithMaxPairs.add(playerTwo);
			if (results.get(i) == max && i == 2)
				playerWithMaxPairs.add(playerThree);
			if (results.get(i) == max && i == 3)
				playerWithMaxPairs.add(playerFour);
		}
		if (playerWithMaxPairs.size() == 1)
			slowPrinter(playerWithMaxPairs.get(0).playerIdentifier + " Wins!");
		else
			slowPrinter("It's a Tie!");
		slowPrinter("Thanks for playing GoFish! Have a Great Day!");

	}
	/*
		I created my own slowPrinter methods in order to make it so all of the text in my program
		is printed gradually and not instantly.
	*/
	public static void slowPrinter(String printThis) throws InterruptedException
	{
		char[] firstFlight = printThis.toCharArray();
		for(char letter : firstFlight)
		{
			System.out.print(letter);
			Thread.sleep(28);
			if (letter == '!' || letter == '.' || letter == '?')
				Thread.sleep(500);
		}
		Thread.sleep(750);
		System.out.println();
		System.out.println();
	}
	public static void slowPrinter(String printThis, int charSleepTime) throws InterruptedException
	{
		char[] firstFlight = printThis.toCharArray();
		for(char letter : firstFlight)
		{
			System.out.print(letter);
			Thread.sleep(charSleepTime);
			if (letter == '!' || letter == '.' || letter == '?')
				Thread.sleep(400);
		}
		Thread.sleep(750);
		System.out.println();
		System.out.println();
	}
	public static void slowPrinter(String printThis, int charSleepTime, int finalSleepTime) throws InterruptedException
	{
		char[] firstFlight = printThis.toCharArray();
		for(char letter : firstFlight)
		{
			System.out.print(letter);
			Thread.sleep(charSleepTime);
			if (letter == '!' || letter == '.' || letter == '?')
				Thread.sleep(400);
		}
		Thread.sleep(finalSleepTime);
		System.out.println();
		System.out.println();
	}
	public static void slowPrinter(String printThis, int charSleepTime, int finalSleepTime, int punctuationSleepTime) throws InterruptedException
	{
		char[] firstFlight = printThis.toCharArray();
		for(char letter : firstFlight)
		{
			System.out.print(letter);
			Thread.sleep(charSleepTime);
			if (letter == '!' || letter == '.' || letter == '?')
				Thread.sleep(punctuationSleepTime);
		}
		Thread.sleep(finalSleepTime);
		System.out.println();
		System.out.println();
	}
}
/*
	Deck class is separate from the Players and responsible for creating the deck
	and handling card distribution.  
*/
class Deck
{
	ArrayList<String> deck = new ArrayList<String>(0);
	final static String[] SUITS = {"Diamonds","Hearts","Spades","Clubs"};
	final static String[] RANKS = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
	Deck()
	{
	}
	ArrayList<String> createDeck()
	{
		for(String cardGroup:SUITS)
		{
			for(String cardNumber:RANKS)
			{
				String card = cardNumber + " of " + cardGroup;
				deck.add(card);
			}
		}
		return deck;
	}
	ArrayList<String> distributeStartingHands()
	{
		ArrayList<String> handArray = new ArrayList<String>(0);
		for(int i = 0; i < 5; i++)
		{
			int randomCardHolder = (int)(Math.random()*deck.size());
			handArray.add(deck.get(randomCardHolder));
			deck.remove(randomCardHolder);
		}
		return handArray;
	}
	String dealCard()
	{
		String cardToBeDealt = deck.get((int)(Math.random()*deck.size()));
		deck.remove(cardToBeDealt);
		return cardToBeDealt;
	}
	void emptyHandDealCards(Player playerOutOfCards) throws InterruptedException
	{
		if(deck.size() < 5)
		{
			GoFish.slowPrinter("There are less than 5 cards in the deck, so you will receive the remaining " + deck.size() + " cards.", 25);
			playerOutOfCards.cardsInHand.addAll(deck);
			deck.clear();
			Collections.sort(playerOutOfCards.cardsInHand);
		}
		else
		{
			for(int i = 0; i < 5; i++)
			{
				String cardToBeDealt = deck.get((int)(Math.random()*deck.size()));
				playerOutOfCards.cardsInHand.add(cardToBeDealt);
				deck.remove(cardToBeDealt);
			}
			Collections.sort(playerOutOfCards.cardsInHand);
		}
	}
}
/*
The abstract class Player is inherited by the HumanPlayer and ComputerPlayer. For the most part, both the HumanPlayer
and ComputerPlayer classes use the same methods but require different implementations. The methods for the HumanPlayer require print
statements and indicators, whereas the AIs don't require print statements and indicators.
*/
abstract class Player
{
	ArrayList<String> cardsInHand = new ArrayList<String>(0);
	ArrayList<String> pairsPile = new ArrayList<String>(0);
	int playerNumber = 0;
	boolean playerOut = false; 
	String playerIdentifier = "Player ";
	abstract boolean handIsEmpty();
	abstract void goFish(Deck standardDeck);
	abstract Player chooseAPlayer(int playerNumber, Player firstPlayer, Player secondPlayer, Player thirdPlayer, Player fourthPlayer);
	abstract boolean checkHand(String selectedCard);
	abstract String giveSameRank(String cardSelected);
	abstract void checkPair() throws InterruptedException;
	abstract void pairMade() throws InterruptedException;
	static void checkEmptyHand(Player playerInQuestion, Deck playingCardDeck) throws InterruptedException
	{
		if(playerInQuestion.cardsInHand.size() == 0)
		{
			if (playingCardDeck.deck.size() > 0)
			{
				playingCardDeck.emptyHandDealCards(playerInQuestion);
				if (playerInQuestion.playerNumber == 1)
					GoFish.slowPrinter("Your new hand: " + playerInQuestion.cardsInHand, 14, 600);
			}
			else
			{
				playerInQuestion.playerOut = true;
				GoFish.slowPrinter(playerInQuestion.playerIdentifier + " is Out!");
			}
		}
	}
}
class HumanPlayer extends Player
{
	HumanPlayer()
	{
	}
	HumanPlayer(ArrayList<String> hand, int playerCount)
	{
		this.cardsInHand = hand;
		this.playerNumber = playerCount;
		this.playerIdentifier = playerIdentifier + playerCount;
	}
	boolean handIsEmpty()
	{
		return cardsInHand.size() == 0;
	}
	void goFish(Deck gameDeck)
	{
		cardsInHand.add(gameDeck.dealCard());
	}
	Player chooseAPlayer(int playerNumber, Player firstPlayer, Player secondPlayer, Player thirdPlayer, Player fourthPlayer)
	{
		switch(playerNumber)
		{
			case 2:
				return secondPlayer;
			case 3:
				return thirdPlayer;
			case 4:
				return fourthPlayer;
		}
		return secondPlayer;
	}
	boolean checkHand(String desiredCard)
	{
		for (String card : cardsInHand)
		{
			if (card.split("\\s+")[0].equals(desiredCard))
				return true;
		}
		return false;
	}
	String giveSameRank(String cardToCheck)
	{
		for (String card : cardsInHand)
		{
			if (card.split("\\s+")[0].equals(cardToCheck))
			{	
				cardsInHand.remove(card);
				return card;
			}
		}
		return " ";
	}
	void checkPair() throws InterruptedException
	{
		ArrayList<String> possiblePair = new ArrayList<String>(0);
		boolean pairFound = false;
		for (String card : cardsInHand)
		{
			possiblePair = new ArrayList<String>(0);
			String cardRank = card.split("\\s+")[0];
			for(int j = cardsInHand.indexOf(card); j <= cardsInHand.size() - 1; j++)
			{
				String tempCard = cardsInHand.get(j);
				if (cardRank.equals(tempCard.split("\\s+")[0]))
				{
					possiblePair.add(tempCard);
				}
			}
			if (possiblePair.size() >= 4)
			{	
				pairsPile.addAll(possiblePair);
				GoFish.slowPrinter("Pair Found: " + possiblePair, 27, 1250);
				pairFound = true;
				break;
			}
		}
		if(pairFound)
		{
			for(String card : possiblePair)
			{
				cardsInHand.remove(card);
			}
			pairsPile.add("-1");
		}
	}
	void pairMade() throws InterruptedException
	{
		if (pairsPile.size() > 0 && pairsPile.size() % 4 != 0)
		{
			pairsPile.remove("-1");
			GoFish.slowPrinter("After removing the pair, your hand is: " + cardsInHand, 14, 600);
		}
	}
}
class Computer extends Player
{
	Computer()
	{
	}
	Computer(ArrayList<String> hand,int playerCount)
	{
		this.cardsInHand = hand;
		this.playerNumber = playerCount;
		this.playerIdentifier = playerIdentifier + playerCount;
	}
	boolean handIsEmpty()
	{
		return cardsInHand.size() == 0;
	}
	void goFish(Deck gameDeck)
	{
		cardsInHand.add(gameDeck.dealCard());
	}
	Player chooseAPlayer(int playerNumber, Player firstPlayer, Player secondPlayer, Player thirdPlayer, Player fourthPlayer)
	{
		switch(playerNumber)
		{
			case 1:
				return firstPlayer;
			case 2:
				return secondPlayer;
			case 3:
				return thirdPlayer;
			case 4:
				return fourthPlayer;
		}
		return firstPlayer;
	}
	boolean checkHand(String desiredCard)
	{
		for (String card : cardsInHand)
		{
			if (card.split("\\s+")[0].equals(desiredCard))
				return true;
		}
		return false;
	}
	String giveSameRank(String cardToCheck)
	{
		for (String card : cardsInHand)
		{
			if (card.split("\\s+")[0].equals(cardToCheck))
			{	
				cardsInHand.remove(card);
				return card;
			}
		}
		return " ";
	}
	void checkPair() throws InterruptedException
	{
		ArrayList<String> possiblePair = new ArrayList<String>(0);
		boolean pairFound = false;
		for (String card : cardsInHand)
		{
			possiblePair = new ArrayList<String>(0);
			String cardRank = card.split("\\s+")[0];
			for(int j = cardsInHand.indexOf(card); j <= cardsInHand.size() - 1; j++)
			{
				String tempCard = cardsInHand.get(j);
				if (cardRank.equals(tempCard.split("\\s+")[0]))
				{
					possiblePair.add(tempCard);
				}
			}
			if (possiblePair.size() >= 4)
			{	
				pairsPile.addAll(possiblePair);
				GoFish.slowPrinter(playerIdentifier + " made a pair of " + possiblePair.get(1).split("\\s+")[0] + "s!");
				pairFound = true;
				break;
			}
		}
		if(pairFound)
		{
			for(String partOfPair : possiblePair)
			{
				cardsInHand.remove(partOfPair);
			}

			pairsPile.add("-1");
		}
	}
	void pairMade() throws InterruptedException
	{
		if (pairsPile.size() > 0 && pairsPile.size() % 4 != 0)
		{
			pairsPile.remove("-1");
			GoFish.slowPrinter(playerIdentifier + "'s Pairs Pile:" + pairsPile, 14, 600);
		}
	}
}
