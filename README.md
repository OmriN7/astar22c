# astar22c
Hi! This is a group project for 22C.

This program is loosely based off of an article that one of the group members made:
http://omriimakesgames.tumblr.com/private/126365623281/tumblr_nsd32lPYDI1u36bws


Rough TO-DO List:
- FAWZAN: Interface (accepting the txt file with the data)
- VAUGHN & YANG: Interpreting (converting the txt file into a 2D array and then to a graph)
- OMRI: The actual pathfinding algorithm 
- The Presentation itself

<br/>
<br/>

### /// PROGRAM DESCRIPTION
This program is meant to simulate the algorithm for which enemies at a tile-based RPG would use to find the shortest path towards the player to attack him. In order to visualize and help explain, the hypothetical game might look something like the following:

<img src="http://68.media.tumblr.com/0e2c978943d029ba4a5355f65bea7290/tumblr_inline_nsqdo8bMn41rrropq_500.gif" />

*The program will ask the user for an input file containing the appropriate data. If the input file is bad then the program will terminate (**FAWZAN**). However, if the data is fine then the program will then take the data, convert it from a string to a two-dimensional array and then to a graph (**VAUGHN & YANG**).  After that, the program will pass the graph to a pathfinding algorithm that will show the shortest path possible for the skeleton to walk to the player (**OMRI**).*

<br/>
<br/>

### /// PROGRAM INPUT FORMAT 
So, how do we turn the game scene into something a java program would understand? Again, let's visualize...

Here's the game:

<img src="http://68.media.tumblr.com/0e2c978943d029ba4a5355f65bea7290/tumblr_inline_nsqdo8bMn41rrropq_500.gif" />


We break it up into a grid:


<img src="http://68.media.tumblr.com/7caec7fc8e379220ec7a8a4dc72100b2/tumblr_inline_nsqdpmyNrb1rrropq_500.gif" />


Now, we'd assign each tile type to a different letter:


Where...
- `O` - **O**pen tile to walk on
- `W` - **W**all that can't be walked through
- `S` - **S**tarting point
- `T` - **T**arget


And convert it to text (that will be saved in a text file):
```
WOOOOWW
OOOWOOW
OSOWOTO
WOOWOOO
WOOOOOW
```



