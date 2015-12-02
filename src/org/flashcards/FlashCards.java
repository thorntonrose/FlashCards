package org.flashcards;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This is the the main class for the FlashCards application.
 *
 * @author Thornton Rose
 */
public class FlashCards extends JFrame {
   public static final String APP_TITLE = "FlashCards";
   public static final String VERSION   = "1.3";

   private CardDeck     deck = new CardDeck();
   private Card         currentCard;
   private int          currentCardIndex = -1;
   private JFileChooser fileChooser;

   private GridLayout   horizontalGridLayout = new GridLayout(1, 9, 2, 0);
   private JPanel       cardPanel            = new JPanel();
   private JLabel       cardLabel            = new JLabel();
   private JPanel       buttonPanel          = new JPanel();
   private JPanel       controlPanel         = new JPanel();
   private JButton      loadButton           = new JButton("^");
   private JButton      shuffleButton        = new JButton("~");
   private JButton      flipAllButton        = new JButton("!*");
   private JButton      flipButton           = new JButton("!");
   private JButton      firstButton          = new JButton("|<");
   private JButton      backButton           = new JButton("<");
   private JButton      nextButton           = new JButton(">");
   private JButton      lastButton           = new JButton(">|");
   private JButton      infoButton           = new JButton("i");
   private JButton      aboutButton          = new JButton("?");
   private BorderLayout borderLayout1        = new BorderLayout();
   private BorderLayout borderLayout2        = new BorderLayout();
   private JLabel       statLabel            = new JLabel();
   private JPanel       countPanel           = new JPanel();
   private CardLayout   cardLayout1          = new CardLayout();

   /**
    * Construct the FlashCards frame.
    */
   public FlashCards() {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

      jbInit();
      setLocation(
         (screenSize.width - getSize().width) / 2,
         ((screenSize.height - getSize().height) / 2) - 25);
      addListeners();
      enableButtons(false);
      showFirstCard();
   }

   /**
    * Initialize UI components.
    */
   private void jbInit() {
      this.setTitle(APP_TITLE);
      this.setSize(new Dimension(355, 235));

      this.getContentPane().setLayout(borderLayout1);
      buttonPanel.setMinimumSize(new Dimension(280, 23));
      countPanel.setMinimumSize(new Dimension(65, 17));
      countPanel.setPreferredSize(new Dimension(65, 23));
      this.getContentPane().add(cardPanel, BorderLayout.CENTER);
      this.getContentPane().add(controlPanel, BorderLayout.SOUTH);

      cardPanel.setLayout(new CardLayout());
      cardPanel.setBorder(new EtchedBorder(BevelBorder.LOWERED));
      cardPanel.add(cardLabel, "front");

      cardLabel.setFont(new Font("TimesRoman", 0, 18));
      cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
      cardLabel.setOpaque(true);
      cardLabel.setBackground(Color.white);
      cardLabel.setText("");

      controlPanel.setPreferredSize(new Dimension(250, 23));
      controlPanel.setLayout(borderLayout2);
      controlPanel.add(buttonPanel, BorderLayout.WEST);
      controlPanel.add(countPanel, BorderLayout.CENTER);

      cardLayout1.setHgap(3);
      countPanel.setLayout(cardLayout1);
      countPanel.add(statLabel, "card1");

      statLabel.setText("0/0");
      statLabel.setPreferredSize(new Dimension(50, 23));
      statLabel.setHorizontalAlignment(JLabel.RIGHT);

      buttonPanel.setPreferredSize(new Dimension(280, 23));
      buttonPanel.setLayout(horizontalGridLayout);
      buttonPanel.add(loadButton);
      buttonPanel.add(shuffleButton);
      buttonPanel.add(flipAllButton);
      buttonPanel.add(flipButton);
      buttonPanel.add(firstButton);
      buttonPanel.add(backButton);
      buttonPanel.add(nextButton);
      buttonPanel.add(lastButton);
      buttonPanel.add(infoButton);
      buttonPanel.add(aboutButton);

      loadButton.setMargin(new Insets(0, 0, 0, 0));
      loadButton.setToolTipText("Load deck. (Ctrl+L)");

      shuffleButton.setMargin(new Insets(0, 0, 0, 0));
      shuffleButton.setToolTipText("Shuffle deck. (Ctrl+S)");

      flipAllButton.setMargin(new Insets(0, 0, 0, 0));
      flipAllButton.setToolTipText("Flip over cards in deck. (Ctrl+F)");

      flipButton.setMargin(new Insets(0, 0, 0, 0));
      flipButton.setToolTipText("Flip card. (Up/Down)");

      firstButton.setMargin(new Insets(0, 0, 0, 0));
      firstButton.setToolTipText("Go to first card. (Home)");

      backButton.setMargin(new Insets(0, 0, 0, 0));
      backButton.setToolTipText("Go to previous card. (Left)");

      nextButton.setMargin(new Insets(0, 0, 0, 0));
      nextButton.setToolTipText("Go to next card. (Right)");

      lastButton.setMargin(new Insets(0, 0, 0, 0));
      lastButton.setToolTipText("Go to last card. (End)");

      infoButton.setMargin(new Insets(0, 0, 0, 0));
      infoButton.setToolTipText("Deck info.");

      aboutButton.setMargin(new Insets(0, 0, 0, 0));
      aboutButton.setToolTipText("About this application.");
   }

   /**
    * Add listeners to components.
    */
   private void addListeners() {
      // Add window listener to exit when window is closed.

      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent event) {
            exit();
         }
      });

      // Add listeners to load button.

      loadButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            loadDeck();
         }
      });

      // Add listeners to shuffle button.

      shuffleButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            shuffleDeck();
         }
      });

      // Add listeners to flip all button.

      flipAllButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            flipAllCards();
         }
      });

      // Add listeners to flip button.

      flipButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            flipCard();
         }
      });

      // Add listeners to first button.

      firstButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            showFirstCard();
         }
      });

      // Add listeners to back button.

      backButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            showPreviousCard();
         }
      });

      // Add listeners to next button.

      nextButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            showNextCard();
         }
      });

      // Add listeners to last button.

      lastButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            showLastCard();
         }
      });

      // Add listeners to deck info button.

      infoButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            showDeckInfo();
         }
      });

      // Add listeners to about button.

      aboutButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            showAbout();
         }
      });

      // Add keystroke listeners.

      // load = CTRL+L
      this.getRootPane().registerKeyboardAction(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               loadButton.doClick();
            }
         },
         KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK),
         JComponent.WHEN_IN_FOCUSED_WINDOW);

      // shuffle = CTRL+S
      this.getRootPane().registerKeyboardAction(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               shuffleButton.doClick();
            }
         },
         KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK),
         JComponent.WHEN_IN_FOCUSED_WINDOW);

      // flip all = CTRL+F
      this.getRootPane().registerKeyboardAction(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               flipAllButton.doClick();
            }
         },
         KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK),
         JComponent.WHEN_IN_FOCUSED_WINDOW);

      // flip = UP/DOWN
      this.getRootPane().registerKeyboardAction(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               flipButton.doClick();
            }
         },
         KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
         JComponent.WHEN_IN_FOCUSED_WINDOW);

      this.getRootPane().registerKeyboardAction(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               flipButton.doClick();
            }
         },
         KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
         JComponent.WHEN_IN_FOCUSED_WINDOW);

      // first = HOME
      this.getRootPane().registerKeyboardAction(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               firstButton.doClick();
            }
         },
         KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0),
         JComponent.WHEN_IN_FOCUSED_WINDOW);

      // back = LEFT
      this.getRootPane().registerKeyboardAction(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               backButton.doClick();
            }
         },
         KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
         JComponent.WHEN_IN_FOCUSED_WINDOW);

      // next = RIGHT
      this.getRootPane().registerKeyboardAction(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               nextButton.doClick();
            }
         },
         KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
         JComponent.WHEN_IN_FOCUSED_WINDOW);

      // last = END
      this.getRootPane().registerKeyboardAction(
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               lastButton.doClick();
            }
         },
         KeyStroke.getKeyStroke(KeyEvent.VK_END, 0),
         JComponent.WHEN_IN_FOCUSED_WINDOW);
   }

   /**
    * Enable/disable the buttons (except for load, which is always enabled).
    */
   private void enableButtons(boolean enabled) {
      loadButton.setEnabled(true);
      shuffleButton.setEnabled(enabled);
      flipAllButton.setEnabled(enabled);
      flipButton.setEnabled(enabled);
      firstButton.setEnabled(enabled);
      backButton.setEnabled(enabled);
      nextButton.setEnabled(enabled);
      lastButton.setEnabled(enabled);
   }

   /**
    * Show application description, author, copyright, ...
    */
   private void showAbout() {
      JOptionPane.showMessageDialog(
         this,
         APP_TITLE + " v" + VERSION +
            "\n\nDeveloped by Thornton Rose" +
            "\nCopyright 2000",
         "About",
         JOptionPane.INFORMATION_MESSAGE);
   }

   /**
    * Show deck information.
    */
   public void showDeckInfo() {
      StringBuffer msg = new StringBuffer();

      msg.append("File: ");

      if (deck.getFile() == null) {
         msg.append("(None)");
      } else {
         msg.append(deck.getFile().toString());
      }

      msg.append("\nCards: ");
      msg.append(deck.size());

      JOptionPane.showMessageDialog(
         this,
         msg.toString(),
         "Deck Info",
         JOptionPane.INFORMATION_MESSAGE);
   }

   /**
    * Load deck from text file.
    */
   private void loadDeck() {
      File file;

      // Show the file requester dialog, which will wait for the user to select
      // a file or click cancel.

      if (fileChooser == null) {
         fileChooser = new JFileChooser(System.getProperty("user.dir"));
      }

      fileChooser.showOpenDialog(this);
      file = fileChooser.getSelectedFile();

      // If the user selected a file, load it.

      if (file != null) {
         // Try loading the deck. If successful, enable the buttons. If an
         // exception occurs, show it.

         try {
            statLabel.setText("...");
            enableButtons(false);
            deck.load(file);
            showFirstCard();
            enableButtons(deck.size() > 0);
         } catch (IOException ex) {
            JOptionPane.showMessageDialog(
               this,
               ex.getClass().getName() + ":\n" + ex.getMessage(),
               "Exception",
               JOptionPane.WARNING_MESSAGE);
         }
      }
   }

   /**
    * Shuffle the deck.
    */
   private void shuffleDeck() {
      cardLabel.setText("...");
      deck.shuffle();
      showFirstCard();
   }

   /**
    * Flip the deck.
    */
   private void flipAllCards() {
      cardLabel.setText("...");
      deck.flipAll();
      showFirstCard();
   }

   /**
    * Flip the current card.
    */
   private void flipCard() {
      currentCard.flip();
      showCard();
   }

   /**
    * Show the current card.
    */
   private void showCard() {
      cardLabel.setForeground(currentCard.getFrontColor());
      cardLabel.setText(currentCard.getFront());
      statLabel.setText((currentCardIndex + 1) + "/" + deck.size());
   }

   /**
    * Show the first card.
    */
   private void showFirstCard() {
      if (deck.size() == 0) {
         cardLabel.setText("(No cards.)");
      } else {
         currentCardIndex = 0;
         currentCard = deck.getCard(currentCardIndex);
         showCard();
      }
   }

   /**
    * Show the previous card. (Note that the current index will "wrap" to
    * the last position if at the beginning.)
    */
   private void showPreviousCard() {
      currentCardIndex --;

      if (currentCardIndex < 0) {
         currentCardIndex = (deck.size() - 1);
      }

      currentCard = deck.getCard(currentCardIndex);
      showCard();
   }

   /**
    * Show the next card. (Note that the index will "wrap" to the first
    * position if at the end.)
    */
   private void showNextCard() {
      currentCardIndex ++;

      if (currentCardIndex >= deck.size()) {
         currentCardIndex = 0;
      }

      currentCard = deck.getCard(currentCardIndex);
      showCard();
   }

   /**
    * Show the last card.
    */
   private void showLastCard() {
      currentCardIndex = (deck.size() - 1);
      currentCard = deck.getCard(currentCardIndex);
      showCard();
   }

   /**
    * Exit the application.
    */
   private void exit() {
      // Dispose the frame, then exit.

      dispose();
      System.exit(0);
   }

   /**
    * Start the application.
    */
   public static void main(String[] args) {
      try {
         FlashCards app = new FlashCards();
         app.show();
      } catch(Exception ex) {
         System.out.println(ex);
         System.exit(0);
      }
   }
}
