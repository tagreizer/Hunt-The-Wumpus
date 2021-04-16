Design Decisions
-----------------------------------------------------------
New Classes:

Model Classes::

PlayerEffect Enum:
This was added to help keep track of different effects
that happen to the player throughout the game. They are include things like
being teleported by bats and missing arrows.

RoomAttribute Enum:
This was added to allow rooms to have effects, regardless of their type. Any
room type(except hallways) can be assigned attributes that give clues to the
rooms around them.

Controller Classes::

EventController Interface:
This represents listeners that views can call to interact with the model.
Currently the only methods of interaction are firing arrows and moving. This
was made separate from the controller interface as a listener does not have
to be the controller.

IMazeController Interface:
This interface represents controllers that run the game, currently the only
method they have is runGame().

MazeController Class:
This runs a maze game from an IMazeView and an IMaze, this is also the
EventController for the IMazeView. This is both because it is much easier to
work with the view and model if this is also the view listener.

View Classes::

IMazeView Interface:
This represents a view for the maze MVC structure. It currently has multiple
setters. It could work with only two setters for info, setNodes and
setPlayerEffects. I included setPlayerPos and setPossibleMoves because while
they can be extracted from the list of nodes, it is easier for the view to work
with that info already on hand rather than searching the nodes and solving that
info itself.

TextMazeView Class:
This is a text based view of the maze. It shows the maze as a text adventure,
with the user moving with text input. I decided to have the user not chose rooms
based off of location but off of direction. Because of hallways, choosing rooms
off of location in a text based style is confusing. The user might see that they
are in 1,1 and can move to 1,1 and 7,6 and 1,2. This would seem very strange, so
I show the directions that tunnels go from the room instead.


-----------------------------------------------------------
Code Refactors/Changes:

RoomType Enum:
No longer contains theives and gold, but now has bats and pits. A new type
hallway was added as well.

IMaze Interface:
Added two methods to fire arrows and to get the recent effects that happened
to the player.

Maze Class:
Basic generation was changed, previously hallways were not a thing, now when
the player moves through rooms that have two connections they automatically move
through them. These are considered hallways. Super bats and pits were also added
to support required functionality. The is gameover now checks for player effects
that signal the game has ended. Other private methods were added to help with
these different changes and handle smaller problems.

Player Class:
No longer keeps track of gold, but now has arrows and a effects that the player
has recently experienced. Methods were added to support these changes.

Nodes Interfaces/Classes:
Nodes now support attributes as discussed above. These are for when nodes are
connected to other nodes that have special types. Methods have been added to
these to support these changes.

MazeBuilder Class:
Has been moved into the model package as it goes with the Maze Class.

-----------------------------------------------------------

How to launch from Jar:

To launch from the Jar file the maze must be build by specifying any amount
of the following commands.
Must be followed by int:
-rows
-cols
-wallsRemaining
-sRow
-sCol
-gRow
-gCol
Must be followed by Long:
-seed
Followed by nothing:
-perfect
-nonPerfect


------------------------------------------------------------

How to get desired outcomes listed.
All desired outcomes are also shown in TextViewTest as tests.

Run 1
