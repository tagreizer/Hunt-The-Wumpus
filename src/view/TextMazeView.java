package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controller.EventController;
import model.Direction;
import model.IReadableNode;
import model.PlayerEffect;
import model.Position;
import model.RoomAttribute;


/**
 * Represents a text based view for the maze wumpus game. Is player/controlled by text, and is
 * visualized by text as well.
 */
public class TextMazeView implements IMazeView {

  private final Appendable output;
  private final Scanner scanner;
  private int turn;
  private int arrowCount;
  private boolean shouldIShowEffects;
  private EventController listener;
  private List<List<IReadableNode>> nodes;
  private Position playerPos;
  private List<Direction> possibleMoves;
  private List<List<PlayerEffect>> effects;

  /**
   * Creates a text view with an output to write to and an input to read from.
   *
   * @param output the place to write to
   * @param input  the place to write to.
   */
  public TextMazeView(Appendable output, Readable input) {
    this.listener = null;
    this.nodes = new ArrayList<>();
    this.playerPos = null;
    this.possibleMoves = new ArrayList<>();
    this.effects = new ArrayList<>();
    this.output = output;
    this.scanner = new Scanner(input);
    this.shouldIShowEffects = true;
    this.turn = 0;
    this.arrowCount = 0;
  }

  @Override
  public void setEventController(EventController listener) {
    this.listener = listener;
  }

  @Override
  public void setNodes(List<List<IReadableNode>> nodes) {
    this.nodes = nodes;

  }

  @Override
  public void setPlayerPos(Position pos) {
    this.playerPos = pos;

  }

  @Override
  public void setPossibleMoves(List<Direction> directions) {
    this.possibleMoves = directions;

  }


  @Override
  public void animate() {

    if (playerPos == null) {
      throw new IllegalStateException("I havent been given the player pos!");
    }

    StringBuilder builder = new StringBuilder();
    if (this.shouldIShowEffects) {
      builder.append(this.playerEffectMsg());
    }

    IReadableNode playerNode = this.nodes.get(playerPos.getRow()).get(playerPos.getCol());
    builder.append("Player");
    builder.append(this.turn);
    builder.append("'s turn:\n");
    builder.append("You are in cave (");
    builder.append(playerPos.getRow());
    builder.append(",");
    builder.append(playerPos.getCol());
    builder.append(")\n");
    builder.append(this.roomAttributeMsg(playerNode));
    builder.append("You can move: ");

    for (Direction dir : this.possibleMoves) {
      builder.append(dir.toString());
      builder.append(", ");
    }
    builder.delete(builder.length() - 2, builder.length());

    builder.append("\nShoot or Move (S-M)?:");
    builder.append("\nYou have ");
    builder.append(this.arrowCount);
    builder.append(" arrows left.");

    this.outputString(builder.toString());

    this.decideDirection(this.shouldIMove());
    this.shouldIShowEffects = false;

  }

  @Override
  public void animateGameOver() {

    String finalString = this.playerEffectMsg() +
        this.decideGameOverMsg();
    this.outputString(finalString);

  }

  /**
   * Returns a string that represents if the player won or lost.
   *
   * @return the string if the player won or lost.
   */
  private String decideGameOverMsg() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < this.effects.size(); i++) {
      List<PlayerEffect> effectList = this.effects.get(i);
      builder.append("Player");
      builder.append(i + 1);
      builder.append("'s Outcome:\n");

      if (effectList.contains(PlayerEffect.SHOT_WUMPUS)) {
        builder.append("You Win!\nCongratulations!\n");
      } else {

        builder.append("Game Over!\nBetter luck next time!\n");
      }
    }

    return builder.toString();
  }

  /**
   * Generates the message for the player effect that the player currently has.
   *
   * @return the message for the player effects
   */
  private String playerEffectMsg() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < this.effects.size(); i++) {
      List<PlayerEffect> effectList = this.effects.get(i);
      builder.append("Player");
      builder.append(i + 1);
      builder.append("'s Effects:\n");
      for (PlayerEffect effect : effectList) {
        switch (effect) {
          case FELL_INTO_PIT:
            builder.append("AHHHHH....\nYou fell into a pit and died!");
            break;
          case GRABBED_BY_BAT:
            builder.append("WHOOOOSH...\nA bat grabbed you and flew you to another cave!");
            break;
          case AVOIDED_BAT:
            builder.append("WHOOOOSH...\nA bat just missed you!");
            break;
          case RAN_INTO_WUMPUS:
            builder.append("CHOMP CHOMP CHOMP...\nYou have been eaten by the Wumpus!");
            break;
          case SHOT_WUMPUS:
            builder.append("RAWRRRRRrrrr...\nYou hear the " +
                "screams of pain from a Wumpus pierced with an arrow!");
            break;
          case MISSED_WUMPUS:
            builder.append("Clank...\nYou hear your arrow echo off stone! You Missed!");
            break;
          case NO_ARROWS:
            builder.append("RAWRRRRRRRRR...\nYou're out of " +
                "arrows and the Wumpus knows!\nHe came to eat you!");
            break;
          default:
            //do nothing for other ones.
        }

        builder.append("\n");
      }
    }

    return builder.toString();
  }

  /**
   * Generates the message for the room attribute.
   *
   * @param node the node that has its attributes checked.
   * @return the message displaying the attributes.
   */
  private String roomAttributeMsg(IReadableNode node) {
    StringBuilder builder = new StringBuilder();
    for (RoomAttribute attribute : node.getRoomAttributes()) {
      switch (attribute) {

        case NEXT_TO_WUMPUS:
          builder.append("There is the stench of Wumpus in the air...\n");
          break;
        case NEXT_TO_PIT:
          builder.append("A cold wind blows...\n");
          break;
        default:
          //other attributes dont have any effect for this view type.
      }

    }

    return builder.toString();
  }

  /**
   * Generates the message for when the user doesnt select a valid direction.
   */
  private void badDirectionDecided() {
    StringBuilder builder = new StringBuilder();
    builder.append("Please enter the first letter of: ");
    for (Direction dir : this.possibleMoves) {
      builder.append(dir.toString());
      builder.append(", ");
    }
    builder.delete(builder.length() - 2, builder.length());
    builder.append("\n");
    this.outputString(builder.toString());

  }

  /**
   * Asks for the direction for either an arrow shot or a movement.
   *
   * @param moving whether or this is for movement.
   */
  private void decideDirection(boolean moving) {

    this.badDirectionDecided();

    while (scanner.hasNext()) {

      switch (scanner.next().toUpperCase()) {
        case "N":
          if (possibleMoves.contains(Direction.NORTH)) {
            if (moving) {
              this.listener.movePlayer(Direction.NORTH);
            } else {
              this.listener.shootArrow(Direction.NORTH, this.decideDistance());
            }
            return;

          }

          break;
        case "S":
          if (possibleMoves.contains(Direction.SOUTH)) {
            if (moving) {
              this.listener.movePlayer(Direction.SOUTH);
            } else {
              this.listener.shootArrow(Direction.SOUTH, this.decideDistance());
            }
            return;

          }
          break;
        case "E":
          if (possibleMoves.contains(Direction.EAST)) {
            if (moving) {
              this.listener.movePlayer(Direction.EAST);
            } else {
              this.listener.shootArrow(Direction.EAST, this.decideDistance());
            }
            return;

          }
          break;
        case "W":
          if (possibleMoves.contains(Direction.WEST)) {
            if (moving) {
              this.listener.movePlayer(Direction.WEST);
            } else {
              this.listener.shootArrow(Direction.WEST, this.decideDistance());
            }
            return;

          }
          break;
        default:

          break;

      }
      this.badDirectionDecided();
    }


  }

  /**
   * Prompts the user for the distance they should shoot the arrow.
   *
   * @return the distance the user wants to shoot.
   */
  private int decideDistance() {
    this.outputString("How far will you shoot your arrow?\nPlease enter an integer value: ");
    int num;
    while (true) {
      while (scanner.hasNext()) {
        try {
          num = Integer.parseInt(scanner.next());
          return num;
        } catch (InputMismatchException | NumberFormatException e) {
          this.outputString("Please enter an valid integer: ");
        }


      }
    }
  }

  /**
   * Prompts the player to see if they should move or shoot.
   *
   * @return if the player is moving.
   */
  private boolean shouldIMove() {
    while (true) {

      while (scanner.hasNext()) {
        switch (scanner.next().toUpperCase()) {
          case "M":
            return true;
          case "S":
            return false;
          default:
            this.outputString("Please enter S for Shoot and M for Move.\n");
        }
      }

    }
  }

  @Override
  public void setPlayerEffects(List<List<PlayerEffect>> effects) {
    this.effects = effects;
    this.shouldIShowEffects = true;
  }

  @Override
  public void setArrowAmount(int arrowAmount) {
    this.arrowCount = arrowAmount;
  }

  @Override
  public void setTurn(int playerNumber) {
    this.turn = playerNumber;
  }

  /**
   * Takes a string and writes it to the output.
   *
   * @param msg the string to display.
   */
  private void outputString(String msg) {
    try {
      this.output.append(msg).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Output has closed");
    }
  }

  @Override
  public void displayError(String error) {
    this.outputString(error);


  }

  @Override
  public boolean shouldQuit() {
    return true;
  }

  @Override
  public void close() {
    //does nothing for this type
  }


}
