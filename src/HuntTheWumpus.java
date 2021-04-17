import java.io.InputStreamReader;

import controller.IMazeController;
import controller.MazeController;
import model.IMaze;
import model.MazeBuilder;
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
            System.out.println("-rows Must be followed by a valid int.");
            System.exit(0);
          }

          break;
        case "-cols":
          try {
            mazeBuilder.setCols((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            System.out.println("-cols Must be followed by a valid int.");
            System.exit(0);
          }
          break;
        case "-wallsRemaining":
          try {
            mazeBuilder.setWallsRemaining((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            System.out.println("-wallsRemaining Must be followed by a valid int.");
            System.exit(0);
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
            System.out.println("-sRow Must be followed by a valid int.");
            System.exit(0);
          }
          break;
        case "-sCol":
          try {
            mazeBuilder.setsCol((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            System.out.println("-sCol Must be followed by a valid int.");
            System.exit(0);
          }
          break;
        case "-gRow":
          try {
            mazeBuilder.setgRow((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            System.out.println("-gRow Must be followed by a valid int.");
            System.exit(0);
          }
          break;
        case "-gCol":
          try {
            mazeBuilder.setgCol((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            System.out.println("-gCol Must be followed by a valid int.");
            System.exit(0);
          }
          break;
        case "-bats":
          try {
            mazeBuilder.setBatsPercentage((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            System.out.println("-bats Must be followed by a valid int.");
            System.exit(0);
          }
          break;
        case "-pits":
          try {
            mazeBuilder.setPitsPercentage((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            System.out.println("-pits Must be followed by a valid int.");
            System.exit(0);
          }
          break;

        case "-arrows":
          try {
            mazeBuilder.setArrowCount((Integer.parseInt(args[i + 1])));
            i++;
          } catch (Exception e) {
            System.out.println("-arrows Must be followed by a valid int.");
            System.exit(0);
          }
          break;

        case "-seed":
          try {
            mazeBuilder.setSeed((Long.parseLong(args[i + 1])));
            i++;
          } catch (Exception e) {
            System.out.println("-seed Must be followed by a valid long.");
            System.exit(0);
          }
          break;
        default:
          System.out.println("Not a valid input command");
          System.exit(0);
      }
    }
    IMaze model = null;
    try {
      model = mazeBuilder.build();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }

    IMazeController controller = new MazeController(model, view);
    controller.runGame();


  }
}
