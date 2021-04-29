Design Decisions
-----------------------------------------------------------
New Classes:
Controller Classes::

SwingMaze Controller Class:
This is a controller that works for guis instead of text. It is an asynchronous contoller and
inherits most of its code from the TextMazeController.


View Classes::

SwingMazeView Class (and its panel classes):
This IMazeView animates in a swing format. It consists of three different panels represented by
their own classes. Each panel is involved in one different step. The NodePanel is used to animate
the physical structure of the maze, and its contents. This panel also handles the mouse clicks
when rooms are clicked on to move into. The ArrowPanel handles all the arrow pieces. The user can
specify a direction and distance and then fire with this panel. This panel also displays how many
arrows the user currently has left. Lastly the RestartAndTurnPanel gives the user the two button
options to restart a maze, and to make a brand new maze. This panel also displays the turn number
as that needed to go somewhere and the arrow panel already handled alot. Keystrokes are handled by
the SwingMazeView class and not the node panel. This is because to register keystrokes the panel
must be in focus and it was simpler to have all its parent nodes refocus to the frame as a whole,
and not specifically to the node panel. Invalid moves are ignored. Unlike the text version
when a player tries to moves illegally this view type ignores it rather than displays an error.
It would be very annoying to see an error everytime, and the player can instead just see that they
didnt move. All images should be in the reasources directory

SwingMazeCreator:
This is a gui that will create a maze based off of user input and send the model to
the eventcontroller its linked to. It will also notify the player when invalid values are attempted
to be used in making the maze, with the reason why they are invalid.

ViewStyle Enum:
This enum represents the different styles that the view can be rendered in.


-----------------------------------------------------------
Code Refactors/Changes:


IMaze Interface:
Added methods to move the player based off of a given position, get the turn number, and restart
the maze.

Maze Class:
The maze now supports multiple players. Theoretically the maze (and the views) support any positive
number of players. It currently is just capped at two though. Refactors throughtout the maze were
not extensive to support this, but small changes were made to things like game over which makes
sure each player has died or if one player has won. Users of the Maze class cannot specify which
player does what. They can only specify that the player whos current turn it is does things. The
model then handles which player it applies too. This decision was made so that turn order was
strictly enforced. Once a bat is triggered by one player it will not fly the second player to
encounter it.

Player Class:
Players now have a tag to differentiate themselves with.

MazeBuilder Class:
The maze builder now supports randomizing some of the parameters of the maze. If randomization is
desired, they must be called in a certain order though or the builder cannot guarantee that they
will all be valid options.

EventController Interface:
The EventController now supports moving players by position, restarting the game, and creating
a new game.

MazeController Class:
The main run loop for the controller has been refactored. It now allows for animation to continue
after a game over if the view supports it. It can restart games(refreshing to a new model), and it
can also create new games. This has also been ranamed to the TextMazeController for clarity.

IMaze View Interface:
Many new setters were added to accommodate easily accessing information from the model. Neither
of the two view types need all the different options as they display info differently. As a result
if I were to do the project again I would likely create a more coherent single piece of read only
info for the views. Only the get nodes, player effects, get turn, and get arrowsamount are
necessary, but the others help the views deal with tht info more easily.

TextMazeView Class:
This class now supports multiple players, as well as it now shows the amount of arrows a player has
left(which was missing in the last version).
-----------------------------------------------------------

How to launch from Jar:

To launch from the Jar file the first argument must be either --gui or --text
if it is -gui then no more arguments will be read and it will prompt the gui maze creator.

if it is --text the maze can then be specified by following it with the options below.
Must be followed by int:
-rows
-cols
-wallsRemaining
-sRow
-sCol
-gRow
-gCol
-players
-bats
-pits
-arrows
Must be followed by Long:
-seed
Followed by nothing:
-perfect
-nonPerfect


------------------------------------------------------------

Playing the game(GUI):

To launch make sure the folder that contains the jar also contains the reasources folder with all
the images.

The green fairy is player one and the red one is player two (if doing a two person game)

To move the current player can either press the arrow key in the direction they want to move or
they can click on a room they want to move to. (they cannot click on halways to move, it must be
the room).

To fire arrows you select the direction on the left side and then tick the counter up or down for
distance. Once you have your desired direction and distance click the fire arrow button to shoot
the arrow.

The text version will prompt the user with what they need to do.

------------------------------------------------------------
All parts of the program are complete and should run properly. 
