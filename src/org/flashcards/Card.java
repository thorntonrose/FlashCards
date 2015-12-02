package org.flashcards;

import java.awt.*;

/**
 * The Card class represents a flashcard, which has some text on the
 * front and some text on the back.
 */
public class Card {
   private String front = "";  // Text on the front of the card.
   private String back = "";   // Text on the back of the card.
   private Color frontColor =  Color.black;
   private Color backColor = Color.blue;

   /**
    * Construct a new flashcard.
    */
   public Card() {}

   /**
    * Construct a new flashcard with front and back set to the given text.
    */
   public Card(String front, String back) {
      setFront(front);
      setBack(back);
   }

   /**
    * Return the front of the flashcard.
    */
   public String getFront() {
      return front;
   }

   /**
    * Set the front of the flashcard to the given text.
    */
   public void setFront(String front) {
      this.front = front;
   }

   /**
    * Return the back of the flashcard.
    */
   public String getBack() {
      return back;
   }

   /**
    * Set the back of the flashcard to the given text.
    */
   public void setBack(String back) {
      this.back = back;
   }

   /**
    * Return the front color.
    */
   public Color getFrontColor() {
      return frontColor;
   }

   /**
    * Set the front color.
    */
   public void setFrontColor(Color c) {
      this.frontColor = c;
   }

   /**
    * Return the back color.
    */
   public Color getBackColor() {
      return backColor;
   }

   /**
    * Set the back color.
    */
   public void setBackColor(Color c) {
      this.backColor = c;
   }

   /**
    * Flip the card.
    */
   public void flip() {
      String t1 = getFront();
      setFront(getBack());
      setBack(t1);

      Color t2 = getFrontColor();
      setFrontColor(backColor);
      setBackColor(t2);
   }
}

