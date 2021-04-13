import java.io.InputStreamReader;

import controller.IMazeController;
import controller.MazeController;
import model.IMaze;
import view.IMazeView;
import view.TextMazeView;

/**
 * Generates a game of hunt the wumpus from the command line.
 */
public final class HuntTheWumpus {

  /**
   * Initializes the model, (not controller or view yet so they are not used).
   *
   * @param args the command line input.
   */
  public static void main(String[] args) {
    MazeBuilder mazeBuilder = new MazeBuilder();
    IMazeView view = new TextMazeView(System.out, new InputStreamReader(System.in));


    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-rows":
          try {
            mazeBuilder.setRows((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-rows Must be followed by a valid int.");
          }

          break;
        case "-cols":
          try {
            mazeBuilder.setCols((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-cols Must be followed by a valid int.");
          }
          break;
        case "-wallsRemaining":
          try {
            mazeBuilder.setWallsRemaining((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-wallsRemaining Must be followed by a valid int.");
          }
          break;
        case "-perfect":
          mazeBuilder.setPerfect(true);
          break;
        case "-nonPerfect":
          mazeBuilder.setPerfect(false);
          break;
        case "-wrapping":
          mazeBuilder.setWrapping(true);
          break;
        case "-nonWrapping":
          mazeBuilder.setWrapping(false);
          break;
        case "-sRow":
          try {
            mazeBuilder.setsRow((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-sRow Must be followed by a valid int.");
          }
          break;
        case "-sCol":
          try {
            mazeBuilder.setsCol((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-sCol Must be followed by a valid int.");
          }
          break;
        case "-gRow":
          try {
            mazeBuilder.setgRow((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-gRow Must be followed by a valid int.");
          }
          break;
        case "-gCol":
          try {
            mazeBuilder.setgCol((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-gCol Must be followed by a valid int.");
          }
          break;
        case "-bats":
          try {
            mazeBuilder.setBatsPercentage((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-bats Must be followed by a valid int.");
          }
          break;
        case "-pits":
          try {
            mazeBuilder.setPitsPercentage((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-pits Must be followed by a valid int.");
          }
          break;

        case "-arrows":
          try {
            mazeBuilder.setArrowCount((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-arrows Must be followed by a valid int.");
          }
          break;

        case "-seed":
          try {
            mazeBuilder.setSeed((Long.parseLong(args[i + 1])));
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("-seed Must be followed by a valid long.");
          }
          break;
        default:
          throw new IllegalArgumentException("Not a valid input command");
      }
    }
    IMaze model = mazeBuilder.build();
    IMazeController controller = new MazeController(model, view);
    controller.runGame();


  }
}
