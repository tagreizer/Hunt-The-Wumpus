package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controller.EventController;
import model.*;

public class TextMazeView implements IMazeView {
  private EventController listener;
  private final Appendable output;
  private List<List<IReadableNode>> nodes;
  private Position playerPos;
  private List<Direction> possibleMoves;
  private List<PlayerEffect> effects;
  private final Scanner scanner;
  private boolean shouldIShowEffects;

  public TextMazeView(Appendable output, Readable input) {
    this.listener = null;
    this.output = output;
    this.nodes = new ArrayList<>();
    this.playerPos = null;
    this.possibleMoves = new ArrayList<>();
    this.scanner = new Scanner(input);
    this.shouldIShowEffects = true;
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
   builder.delete(builder.length()-2, builder.length());

    builder.append("\nShoot or Move (S-M)?:");

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

  private String decideGameOverMsg() {
    StringBuilder builder = new StringBuilder();
    for (PlayerEffect effect : this.effects) {
      switch (effect) {
        case FELL_INTO_PIT:
        case RAN_INTO_WUMPUS:
        case NO_ARROWS:
          builder.append("Game Over!\nBetter luck next time!");
          break;

        case SHOT_WUMPUS:
          builder.append("You Win!\nCongratulations!");
          break;

      }
    }
    return builder.toString();
  }

  private String playerEffectMsg() {
    StringBuilder builder = new StringBuilder();
    for (PlayerEffect effect : this.effects) {
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
          builder.append("RAWRRRRRrrrr...\nYou hear the screams of pain from a Wumpus pierced with an arrow!");
          break;
        case MISSED_WUMPUS:
          builder.append("Clank...\nYou hear your arrow echo off stone! You Missed!");
          break;
        case NO_ARROWS:
          builder.append("RAWRRRRRRRRR...\nYou're out of arrows and the Wumpus knows!\nHe came to eat you!");
          break;
        default:
          //do nothing for other ones.
      }
      builder.append("\n");
    }

    return builder.toString();
  }

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

  private void badDirectionDecided() {
    StringBuilder builder = new StringBuilder();
    builder.append("Please enter the first letter of: ");
    for (Direction dir : this.possibleMoves) {
      builder.append(dir.toString());
      builder.append(", ");
    }
    builder.delete(builder.length() - 2,builder.length() );
    builder.append("\n");
    this.outputString(builder.toString());

  }

  private void decideDirection(boolean moving) {

    this.badDirectionDecided();

    while (scanner.hasNext()) {


      switch (scanner.next().toUpperCase()) {
        case "N":
          if (possibleMoves.contains(Direction.NORTH)) {
            if (moving) {
              this.listener.movePlayer(Direction.NORTH);
            } else {
              this.listener.shootArrow(Direction.NORTH,this.decideDistance());
            }
            return;

          }

          break;
        case "S":
          if (possibleMoves.contains(Direction.SOUTH)) {
            if (moving) {
              this.listener.movePlayer(Direction.SOUTH);
            } else {
              this.listener.shootArrow(Direction.SOUTH,this.decideDistance());
            }
            return;

          }
          break;
        case "E":
          if (possibleMoves.contains(Direction.EAST)) {
            if (moving) {
              this.listener.movePlayer(Direction.EAST);
            } else {
              this.listener.shootArrow(Direction.EAST,this.decideDistance());
            }
            return;

          }
          break;
        case "W":
          if (possibleMoves.contains(Direction.WEST)) {
            if (moving) {
              this.listener.movePlayer(Direction.WEST);
            } else {
              this.listener.shootArrow(Direction.WEST,this.decideDistance());
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

  private int decideDistance() {
    this.outputString("How far will you shoot your arrow?\nPlease enter an integer value: ");
    int num;
    while (true) {
      while (scanner.hasNext()) {
        try {
          num = scanner.nextInt();
          return num;
        } catch (InputMismatchException e) {
          this.outputString("Please enter an valid integer: ");
        }


      }
    }
  }

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
  public void setPlayerEffects(List<PlayerEffect> effects) {
    this.effects = effects;
    this.shouldIShowEffects = true;
  }

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
}
