package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.EventController;
import model.Direction;
import model.IReadableNode;
import model.PlayerEffect;
import model.Position;

public class TextMazeView implements IMazeView {
  private EventController listener;
  private final Appendable output;
  private List<List<IReadableNode>> nodes;
  private Position playerPos;
  private List<Direction> possibleMoves;
  private List<PlayerEffect> effects;
  private final Scanner scanner;

  public TextMazeView(Appendable output, Readable input) {
    this.listener = null;
    this.output = output;
    this.nodes = new ArrayList<>();
    this.playerPos = null;
    this.possibleMoves = new ArrayList<>();
    this.scanner = new Scanner(input);
  }


  @Override
  public void animate() {

    if (playerPos == null) {
      throw new IllegalStateException("I havent been given the player pos!");
    }

    StringBuilder builder = new StringBuilder();
    builder.append(this.playerEffectMsg());
    IReadableNode playerNode = this.nodes.get(playerPos.getRow()).get(playerPos.getCol());

    builder.append("You are in cave (");
    builder.append(playerPos.getRow());
    builder.append(",");
    builder.append(playerPos.getCol());
    builder.append(")\n");
    builder.append("You can move: ");

    for (Direction dir : this.possibleMoves) {
      builder.append(dir.toString());
      builder.append(", ");
    }
    builder.substring(0, builder.length() - 2);
    builder.append("\nShoot or Move (S-M)?:");

    try {
      output.append(builder.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Output has close");
    }

    this.decideDirection(this.shouldIMove());


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
          builder.append("RAWRRRRRrrrr...\nYou hear the sounds of a dead Wumpus pierced by an arrow!");
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

  private void badDirectionDecided() {
    StringBuilder builder = new StringBuilder();
    for (Direction dir : this.possibleMoves) {
      builder.append(dir.toString());
      builder.append(", ");
    }
    builder.substring(0, builder.length() - 2);
    try {
      output.append("Please enter the first letter of: ");
      output.append(builder.toString());
      output.append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Out put has closed");
    }

  }

  private void decideDirection(boolean moving) {
    boolean wrongInput = false;
    this.badDirectionDecided();

    while (scanner.hasNext()) {
      if (wrongInput) {
        this.badDirectionDecided();
      }

      switch (scanner.next().toUpperCase()) {
        case "N":
          if (possibleMoves.contains(Direction.NORTH)) {
            if (moving) {
              this.listener.movePlayer(Direction.NORTH);
              return;
            } else {
              //shoot
            }
            wrongInput = false;
          } else {
            wrongInput = true;
          }

          break;
        case "S":
          if (possibleMoves.contains(Direction.SOUTH)) {
            if (moving) {
              this.listener.movePlayer(Direction.SOUTH);
              return;
            } else {
              //shoot
            } wrongInput = false;
          } else {
            wrongInput = true;
          }
          break;
        case "E":
          if (possibleMoves.contains(Direction.EAST)) {
            if (moving) {
              this.listener.movePlayer(Direction.EAST);
              return;
            } else {
              //shoot
            } wrongInput = false;
          } else {
            wrongInput = true;
          }
          break;
        case "W":
          if (possibleMoves.contains(Direction.WEST)) {
            if (moving) {
              this.listener.movePlayer(Direction.WEST);
              return;
            } else {
              //shoot
            } wrongInput = false;
          } else {
            wrongInput = true;
          }
          break;
        default:
          wrongInput = true;

      }
    }


  }

  private boolean shouldIMove() {
    while (true) {
      try {
        while (scanner.hasNext()) {
          switch (scanner.next().toUpperCase()) {
            case "M":
              return true;
            case "S":
              return false;
            default:
              output.append("Please enter S for Shoot and M for Move.\n");
          }
        }
      } catch (IOException e) {
        throw new IllegalStateException("Out put has closed");
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
  }

  @Override
  public void displayError(String error) {
    try {
      this.output.append(error).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Output has closed");
    }


  }
}
