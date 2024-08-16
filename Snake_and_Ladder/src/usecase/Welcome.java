package com.usecase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Welcome {

    // Snakes and Ladders on the board
    private static final Map<Integer, Integer> snakes = new HashMap<>();
    private static final Map<Integer, Integer> ladders = new HashMap<>();

    // Initializing the snakes and ladders positions
    static {
        // Snakes: Key is the head, value is the tail
        snakes.put(16, 6);
        snakes.put(47, 26);
        snakes.put(49, 11);
        snakes.put(56, 53);
        snakes.put(62, 19);
        snakes.put(64, 60);
        snakes.put(87, 24);
        snakes.put(93, 73);
        snakes.put(95, 75);
        snakes.put(98, 78);

        // Ladders: Key is the bottom, value is the top
        ladders.put(1, 38);
        ladders.put(4, 14);
        ladders.put(9, 31);
        ladders.put(21, 42);
        ladders.put(28, 84);
        ladders.put(36, 44);
        ladders.put(51, 67);
        ladders.put(71, 91);
        ladders.put(80, 100);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int player1Position = 0;
        int player2Position = 0;
        int player1RollCount = 0;
        int player2RollCount = 0;
        boolean player1Turn = true;  // Indicates whose turn it is

        System.out.println("Welcome to the Two-Player Snake and Ladder Game!");

        while (player1Position < 100 && player2Position < 100) {
            System.out.println("Press 'r' to roll the die.");
            String input = scanner.nextLine();

            if (input.equals("r")) {
                int roll = random.nextInt(6) + 1;  // Roll a die (1 to 6)
                int currentPosition;
                int rollCount;
                String currentPlayer;

                // Determine current player
                if (player1Turn) {
                    currentPlayer = "Player 1";
                    currentPosition = player1Position;
                    player1RollCount++;
                    rollCount = player1RollCount;
                } else {
                    currentPlayer = "Player 2";
                    currentPosition = player2Position;
                    player2RollCount++;
                    rollCount = player2RollCount;
                }

                System.out.println(currentPlayer + " rolled a " + roll);

                // Randomly determine if it's a No Play, Ladder, or Snake
                int option = random.nextInt(3);  // 0: No Play, 1: Ladder, 2: Snake

                switch (option) {
                    case 0:  // No Play
                        System.out.println("No Play. " + currentPlayer + " stays at position " + currentPosition);
                        break;

                    case 1:  // Ladder
                        if (currentPosition + roll <= 100) {
                            currentPosition += roll;
                            System.out.println("Ladder! " + currentPlayer + " moves ahead to position " + currentPosition);

                            // Check if the new position is a ladder or snake
                            if (ladders.containsKey(currentPosition)) {
                                currentPosition = ladders.get(currentPosition);
                                System.out.println("Great! " + currentPlayer + " landed on a ladder. Move up to position " + currentPosition);
                            } else if (snakes.containsKey(currentPosition)) {
                                currentPosition = snakes.get(currentPosition);
                                System.out.println("Oh no! " + currentPlayer + " landed on a snake. Move down to position " + currentPosition);
                            }
                        } else {
                            System.out.println("Roll too high to move. " + currentPlayer + " stays at position " + currentPosition);
                        }
                        player1Turn = !player1Turn; // Allow same player to play again after ladder
                        break;

                    case 2:  // Snake
                        currentPosition -= roll;
                        if (currentPosition < 0) {
                            System.out.println(currentPlayer + " moved below 0. Restarting from position 0.");
                            currentPosition = 0;
                        } else {
                            System.out.println("Snake! " + currentPlayer + " moves back to position " + currentPosition);

                            // Check if the new position is a ladder or snake
                            if (ladders.containsKey(currentPosition)) {
                                currentPosition = ladders.get(currentPosition);
                                System.out.println("Great! " + currentPlayer + " landed on a ladder. Move up to position " + currentPosition);
                            } else if (snakes.containsKey(currentPosition)) {
                                currentPosition = snakes.get(currentPosition);
                                System.out.println("Oh no! " + currentPlayer + " landed on a snake. Move down to position " + currentPosition);
                            }
                        }
                        player1Turn = !player1Turn; // Switch to other player
                        break;
                }

                // Print the position after every roll
                System.out.println(currentPlayer + "'s current position after this roll: " + currentPosition);

                // Update player position
                if (player1Turn) {
                    player1Position = currentPosition;
                } else {
                    player2Position = currentPosition;
                }

                // Check if either player has won
                if (player1Position == 100) {
                    System.out.println("Congratulations! Player 1 reached position 100 and won the game!");
                    System.out.println("Player 1 rolled the dice " + player1RollCount + " times to win the game.");
                    break;
                } else if (player2Position == 100) {
                    System.out.println("Congratulations! Player 2 reached position 100 and won the game!");
                    System.out.println("Player 2 rolled the dice " + player2RollCount + " times to win the game.");
                    break;
                }

                // Switch turn if it's not ladder
                if (option != 1) {
                    player1Turn = !player1Turn;
                }
            } else {
                System.out.println("Invalid input. Please press 'r' to roll the die.");
            }
        }

        scanner.close();
    }
}
