import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;

public class GoFish
{
	public static void main(String[] args) throws InterruptedException 
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome To GoldFish! Let's Get Started. Type and Enter Any Key To Continue. If You Want To Exit, Type \"Exit\" Now.");
		String play = scan.next().toLowerCase();
		if (play.equals("exit"))
			System.exit(0);
		Deck standardDeck = new Deck();
		standardDeck.createDeck();
		Player playerOne = new HumanPlayer();
		Player playerTwo = new Computer();
		Player playerThree = new Computer();
		Player playerFour = new Computer();
		int dealerPlayer = (int)((Math.random()+0.25)*4);
		int turnPlayer = 0;
		//Based on the dealer player the first player will be decided
		switch(dealerPlayer)
		{
			case 1:
				System.out.println("Player 1 Is The Dealer.");
				playerOne = new HumanPlayer(standardDeck.distributeStartingHands(), 1);
				playerTwo = new Computer(standardDeck.distributeStartingHands(), 2);
				playerThree = new Computer(standardDeck.distributeStartingHands(), 3);
				playerFour = new Computer(standardDeck.distributeStartingHands(), 4);
				turnPlayer = 2;
				break;
			case 2:
				System.out.println("Player 2 Is The Dealer.");
				playerTwo = new Computer(standardDeck.distributeStartingHands(), 2);
				playerThree = new Computer(standardDeck.distributeStartingHands(), 3);
				playerFour = new Computer(standardDeck.distributeStartingHands(), 4);
				playerOne = new HumanPlayer(standardDeck.distributeStartingHands(), 1);
				turnPlayer = 3;
				break;
			case 3:
				System.out.println("Player 3 Is The Dealer.");
				playerThree = new Computer(standardDeck.distributeStartingHands(), 3);
				playerFour = new Computer(standardDeck.distributeStartingHands(), 4);
				playerOne = new HumanPlayer(standardDeck.distributeStartingHands(), 1);
				playerTwo = new Computer(standardDeck.distributeStartingHands(), 2);
				turnPlayer = 4;
				break;
			case 4:
				System.out.println("Player 4 Is The Dealer.");
				playerFour = new Computer(standardDeck.distributeStartingHands(), 4);
				playerOne = new HumanPlayer(standardDeck.distributeStartingHands(), 1);
				playerTwo = new Computer(standardDeck.distributeStartingHands(), 2);
				playerThree = new Computer(standardDeck.distributeStartingHands(), 3);
				turnPlayer = 1;
		}
		System.out.println();
		//Initial check to see if any player's starting hand has a pair in it.
		playerOne.checkPair();
		playerOne.pairMade();
		playerTwo.checkPair();
		playerTwo.pairMade();
		playerThree.checkPair();
		playerThree.pairMade();
		playerFour.checkPair();
		playerFour.pairMade();
		boolean run = true;
		if(turnPlayer != 1)
		{
			System.out.println("Here is Your Hand: " + playerOne.cardsInHand);
			Thread.sleep(750);
		}
		while(run)
		{
			Collections.sort(playerOne.cardsInHand);
			Collections.sort(playerTwo.cardsInHand);
			Collections.sort(playerThree.cardsInHand);
			Collections.sort(playerFour.cardsInHand);
			Player currentPlayer = playerOne;
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
				turnPlayer++;
				continue;
			}
			Player askedPlayer = playerOne;
			String cardChoice = " "; 
			int checkHandWorked = 0;
			if(turnPlayer > 4)
				turnPlayer = 1;
			switch(turnPlayer)
			{
				case 1:
					/**
						I used "Thread.sleep()" numerous times throughout the code in order to slow down code execution and set the pace similar to the pace
						of a Go Fish game in real life with other people.
					**/
					System.out.println("Here is Your Hand: " + playerOne.cardsInHand);
					Thread.sleep(750);
					System.out.println("Here is Your Pairs Pile: " + playerOne.pairsPile);
					Thread.sleep(750);
					System.out.println("Which player would you like to ask?");
					String playerChoice = scan.next();
					askedPlayer = currentPlayer.chooseAPlayer(Character.getNumericValue(playerChoice.charAt(playerChoice.length()-1)), playerOne, playerTwo, playerThree, playerFour);
					if(askedPlayer.playerOut == true)
					{
						System.out.println(askedPlayer.playerIdentifier + " is out of the game. Choose a different Player!");
						System.out.println();
						continue;
					}
					boolean inputValidation = true;
					while(inputValidation)
					{
						System.out.println("Which Rank would you like to ask for?"); 
						cardChoice = scan.next();
						int cardsExist = 0;
						for (String cards : currentPlayer.cardsInHand)
						{
							String cardArray = cards.split("\\s+")[0];
							if (cardArray.toLowerCase().equals(cardChoice.toLowerCase()))
								cardsExist += 1;
						}
						if(cardsExist == 0)
						{
							System.out.println("You do not have that Rank. Try Again!");
							continue;
						}
						inputValidation = false;
					}
					System.out.println();
					System.out.println(currentPlayer.playerIdentifier + ": " + askedPlayer.playerIdentifier + ", do you have any " + cardChoice +"s?");
					System.out.println();
					Thread.sleep(1500);
					while(askedPlayer.checkHand(cardChoice) == true)
					{
						String cardAdded = askedPlayer.giveSameRank(cardChoice);
						currentPlayer.cardsInHand.add(cardAdded);
						Collections.sort(currentPlayer.cardsInHand);
						Thread.sleep(1500);
						System.out.println();
						System.out.println(askedPlayer.playerIdentifier + " gave you the " + cardAdded);
						Thread.sleep(1500);
						System.out.println();
						System.out.println();
						checkHandWorked += 1;
						currentPlayer.checkPair();
						Thread.sleep(1500);
						currentPlayer.pairMade();
						Thread.sleep(1500);
						Player.checkEmptyHand(currentPlayer, standardDeck);
						Thread.sleep(1500);
						Player.checkEmptyHand(askedPlayer, standardDeck);
						if(askedPlayer.cardsInHand.size() == 0 && standardDeck.deck.size() == 0)
						{
							askedPlayer.playerOut = true;
							System.out.println(askedPlayer.playerIdentifier + " is Out!");
							Thread.sleep(1500);
						}
					}
					if (checkHandWorked == 0)
					{
						if(standardDeck.deck.size() > 0)
						{
							System.out.println(askedPlayer.playerIdentifier + ": Go Fish!");
							System.out.println();
							currentPlayer.goFish(standardDeck);
							Collections.sort(currentPlayer.cardsInHand);
							currentPlayer.checkPair();
							Thread.sleep(1500); 
							currentPlayer.pairMade();
							Player.checkEmptyHand(currentPlayer, standardDeck);
						}
						else
						{
							System.out.println(askedPlayer.playerIdentifier + ": Go Fish!");
							Thread.sleep(1500);
							System.out.println("The deck is out of cards! The next Player goes!");
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
						which are when the AI are the currentPlayer, are covered by the code block below.
					**/
					System.out.println("It is " + currentPlayer.playerIdentifier + "'s turn!");
					Thread.sleep(1500);
					int whoToAsk = currentPlayer.playerNumber;
					while(whoToAsk == currentPlayer.playerNumber)
						whoToAsk = (int)((Math.random() + 0.25)*4);
					askedPlayer = currentPlayer.chooseAPlayer(whoToAsk, playerOne, playerTwo, playerThree, playerFour);
					if(askedPlayer.playerOut == true)
						continue;
					cardChoice = currentPlayer.cardsInHand.get((int)(Math.random()*currentPlayer.cardsInHand.size())).split("\\s+")[0];
					System.out.println();
					System.out.println(currentPlayer.playerIdentifier + ": " + askedPlayer.playerIdentifier
									  + ", do you have any " + cardChoice.split("\\s+")[0] + "s?");
					Thread.sleep(1500);
					while(askedPlayer.checkHand(cardChoice) == true)
					{
						String cardAdded = askedPlayer.giveSameRank(cardChoice);
						currentPlayer.cardsInHand.add(cardAdded);
						Collections.sort(currentPlayer.cardsInHand);
						Collections.sort(askedPlayer.cardsInHand);
						Thread.sleep(1500);
						System.out.println();
						System.out.println(askedPlayer.playerIdentifier + " gave " + currentPlayer.playerIdentifier + " the " + cardAdded);
						Thread.sleep(1500);
						System.out.println();
						checkHandWorked += 1;
						Thread.sleep(1500);
						currentPlayer.checkPair();
						Thread.sleep(1500);
						currentPlayer.pairMade();
						Thread.sleep(1500);
						Player.checkEmptyHand(currentPlayer, standardDeck);
						Thread.sleep(1500);
						Player.checkEmptyHand(askedPlayer, standardDeck);
					}
					if (checkHandWorked == 0)
					{
						if(standardDeck.deck.size() > 0)
						{
							System.out.println(askedPlayer.playerIdentifier + ": Go Fish!");
							currentPlayer.goFish(standardDeck);
							Collections.sort(currentPlayer.cardsInHand);
							currentPlayer.checkPair();
							Thread.sleep(1500);
							currentPlayer.pairMade();
							Thread.sleep(1500);
							Player.checkEmptyHand(currentPlayer, standardDeck);
						}
						else
						{
							System.out.println(askedPlayer.playerIdentifier + ": Go Fish!");
							Thread.sleep(1500);
							System.out.println("The deck is out of cards! The next Player goes!");
						}
						turnPlayer += 1;
					}
					else
						continue;
					break;
			}
			if(currentPlayer.cardsInHand.size() == 0 && standardDeck.deck.size() == 0)
			{
				currentPlayer.playerOut = true;
				Thread.sleep(1000);
				System.out.println(currentPlayer.playerIdentifier + " is Out!");
			}
			if(askedPlayer.cardsInHand.size() == 0 && standardDeck.deck.size() == 0)
			{
				askedPlayer.playerOut = true;
				Thread.sleep(1000);
				System.out.println(askedPlayer.playerIdentifier + " is Out!");
			}
			if(standardDeck.deck.size() == 0)
			{
				if(playerOne.handIsEmpty() && playerTwo.handIsEmpty() && playerThree.handIsEmpty() && playerFour.handIsEmpty())
				{
					break;
				}
			}
			System.out.println("Cards remaining in the deck: " + standardDeck.deck.size());
			Thread.sleep(1500);
			System.out.println();
		}
		System.out.println("Game Over!");
		System.out.println();
		Thread.sleep(1500);
		System.out.println("Here are the results: ");
		System.out.println();
		System.out.println("Player 1's Pairs: " + playerOne.pairsPile);
		System.out.println("Player 1 had " + playerOne.pairsPile.size()/4 + " pairs");
		System.out.println();
		System.out.println("Player 2's Pairs: " + playerTwo.pairsPile);
		System.out.println("Player 2 had " + playerTwo.pairsPile.size()/4 + " pairs");
		System.out.println();
		System.out.println("Player 3's Pairs: " + playerThree.pairsPile);
		System.out.println("Player 3 had " + playerThree.pairsPile.size()/4 + " pairs");
		System.out.println();
		System.out.println("Player 4's Pairs: " + playerFour.pairsPile);
		System.out.println("Player 4 had " + playerFour.pairsPile.size()/4 + " pairs");
		System.out.println();
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
			System.out.println(playerWithMaxPairs.get(0).playerIdentifier + " Wins!");
		else
			System.out.println("It's a Tie!");
		System.out.println("Thanks for playing GoFish. Have a Great Day!");

	}
}
/**
	Deck class is used to create a standardDeck which is separate from the players and is responsible for creating the deck
	and dealing cards.
**/
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
	void emptyHandDealCards(Player playerOutOfCards)
	{
		if(deck.size() < 5)
		{
			System.out.println("There are less than 5 cards in the deck, so you will receive the remaining " + deck.size() + " cards.");
			System.out.println();
			playerOutOfCards.cardsInHand.addAll(deck);
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
		System.out.println("Here is your new hand: " + playerOutOfCards.cardsInHand);
	}
}
/**
Abstract Player class is inherited by the HumanPlayer and ComputerPlayer. For the most part, both the HumanPlayer
and ComputerPlayer classes use the same methods but require different implementations. The methods for the HumanPlayer require print
statements and indicators, whereas the AIs don't require print statements and indicators.
**/
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
	abstract void checkPair();
	abstract void pairMade();
	static void checkEmptyHand(Player playerInQuestion, Deck playingCardDeck)
	{
		if(playerInQuestion.cardsInHand.size() == 0)
		{
			if (playingCardDeck.deck.size() > 0)
				playingCardDeck.emptyHandDealCards(playerInQuestion);
			else
			{
				playerInQuestion.playerOut = true;
				System.out.println(playerInQuestion.playerIdentifier + " is Out!");
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
		System.out.println("You drew the " + cardsInHand.get(cardsInHand.size()-1));
		System.out.println("Here is your new hand:" + cardsInHand);
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
			if (card.split("\\s+")[0].toLowerCase().equals(desiredCard.toLowerCase()))
				return true;
		}
		return false;
	}
	String giveSameRank(String cardToCheck)
	{
		for (String card : cardsInHand)
		{
			if (card.split("\\s+")[0].toLowerCase().equals(cardToCheck.toLowerCase()))
			{	
				cardsInHand.remove(card);
				return card;
			}
		}
		return " ";
	}
	void checkPair()
	{
		ArrayList<String> possiblePair = new ArrayList<String>(0);
		boolean pairFound = false;
		for (String card : cardsInHand)
		{
			possiblePair = new ArrayList<String>(0);
			String cardRank = card.split("\\s+")[0].toLowerCase();
			for(int j = cardsInHand.indexOf(card); j <= cardsInHand.size() - 1; j++)
			{
				String tempCard = cardsInHand.get(j);
				if (cardRank.equals(tempCard.split("\\s+")[0].toLowerCase()))
				{
					possiblePair.add(tempCard);
				}
			}
			if (possiblePair.size() == 4)
			{	
				pairsPile.addAll(possiblePair);
				System.out.println("Pair Found: " + possiblePair);
				pairFound = true;
				break;
			}
		}
		if(pairFound)
		{
			for(int i=0; i < possiblePair.size(); i++)
			{
				cardsInHand.remove(possiblePair.get(i));
			}
			System.out.println("After removing the pair, your hand is: " + cardsInHand);
			System.out.println();
			pairsPile.add("-1");
		}
	}
	void pairMade()
	{
		if (pairsPile.size() > 0 && pairsPile.size() % 4 != 0)
		{
			pairsPile.remove("-1");
			System.out.println("Your Pairs Pile:" + pairsPile);
			System.out.println();
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
			if (card.split("\\s+")[0].toLowerCase().equals(desiredCard.toLowerCase()))
				return true;
		}
		return false;
	}
	String giveSameRank(String cardToCheck)
	{
		for (String card : cardsInHand)
		{
			if (card.split("\\s+")[0].toLowerCase().equals(cardToCheck.toLowerCase()))
			{	
				cardsInHand.remove(card);
				return card;
			}
		}
		return " ";
	}
	void checkPair()
	{
		ArrayList<String> possiblePair = new ArrayList<String>(0);
		boolean pairFound = false;
		for (String card : cardsInHand)
		{
			possiblePair = new ArrayList<String>(0);
			String cardRank = card.split("\\s+")[0].toLowerCase();
			for(int j = cardsInHand.indexOf(card); j < cardsInHand.size() - 1; j++)
			{
				String tempCard = cardsInHand.get(j);
				if (cardRank.equals(tempCard.split("\\s+")[0].toLowerCase()))
				{
					possiblePair.add(tempCard);
				}
			}
			if (possiblePair.size() == 4)
			{	
				pairsPile.addAll(possiblePair);
				System.out.println(playerIdentifier + " made a pair of " + possiblePair.get(1).split("\\s+")[0] + "s");
				System.out.println();
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
	void pairMade()
	{
		if (pairsPile.size() > 0 && pairsPile.size() % 4 != 0)
		{
			pairsPile.remove("-1");
			System.out.println(playerIdentifier + "'s Pairs Pile:" + pairsPile);
		}
	} 
}
