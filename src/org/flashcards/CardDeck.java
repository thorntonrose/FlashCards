package org.flashcards;

import java.io.*;
import java.util.*;

/**
 * The CardDeck class represents a deck of flash cards. It is implemented as a
 * Vector. Flash card decks can be loaded from text files that look like this:
 * <pre>
 * # Comment
 * front 1 | back 1
 * # Another comment
 * front 2 | back 2
 * front 3 | back 3
 * ...
 * </pre>
 */
public class CardDeck extends Vector {
   private File deckFile;

   /**
    * Construct an empty card deck.
    */
   public CardDeck() {}

   /**
    * Get the deck file.
    */
   public File getFile() {
      return deckFile;
   }

   /**
    * Return the card at the given index.
    */
   public Card getCard(int index) {
      if ((index < 0) || (index > size())) {
         return null;
      }

      return (Card) elementAt(index);
   }

   /**
    * Load a flash card deck from a file with the given name.
    */
   public void load(File theFile) throws IOException {
      Card card;
      String line;
      StringTokenizer tokenizer;
      BufferedReader reader = new BufferedReader(new FileReader(theFile));

      // Clear the deck.

      removeAllElements();

      // Try reading from the file. If an error occurs, close the file.

      deckFile = theFile;
      reader = new BufferedReader(new FileReader(deckFile));

      try {
         while (true) {
            // Read a line. If it is null, then break.

            line = reader.readLine();

            if (line == null) {
               break;
            }

            line = line.trim();

            // Iterate through the lines, ignoring lines that start with #.

            if (! line.startsWith("#")) {
               // Create a tokenizer for the line, using the vertical bar (|)
               // as the delimiter.

               tokenizer = new StringTokenizer(line, "|");

               // If there are two token on the line (e.g. A | a), create a new
               // flash card, set the front to the first token, set the back to the
               // second token, then add the card to the deck.

               if (tokenizer.countTokens() >= 2) {
                  card = new Card(
                     tokenizer.nextToken().trim(),
                     tokenizer.nextToken().trim());
                  addElement(card);
               }
            }
         }
      } finally {
         reader.close();
      }
   }

   /**
   * Shuffle the deck.
   */
   public void shuffle() {
      // Object temp;
      // int newPosition;

      // Iterate through the deck. For each card, calculate a random position
      // for it, then, if the position is not equal to the current position,
      // swap the card at the current position with the card at the new
      // position.

      /*
      for (int i = 0; i < size(); i ++) {
         newPosition = (int) (Math.random() * (size() - 1));
         temp = elementAt(i);
         setElementAt(elementAt(newPosition), i);
         setElementAt(temp, newPosition);
      }
      */

      Collections.shuffle(this);
   }

   /**
   * Flip all cards in the deck.
   */
   public void flipAll() {
      Card card;

      // Iterate through the deck. For each card, swap the front with the back.

      for (int i = 0; i < size(); i ++) {
         card = (Card) elementAt(i);
         card.flip();
      }
   }
}

