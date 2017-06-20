# astar22c
Hi! This is a group project for 22C.

This program is loosely based off of an article that one of the group members made:
http://omriimakesgames.tumblr.com/private/126365623281/tumblr_nsd32lPYDI1u36bws

### /// PROGRAM DESCRIPTION
This program is meant to simulate the algorithm for which enemies at a tile-based RPG would use to find the shortest path towards the player to attack him. In order to visualize and help explain, the hypothetical game might look something like the following:

<img src="http://68.media.tumblr.com/0e2c978943d029ba4a5355f65bea7290/tumblr_inline_nsqdo8bMn41rrropq_500.gif" />

*The program will ask the user for an input file containing the appropriate data. If the input file is bad then the program will terminate. However, if the data is fine then the program will then take the data, convert it from a string to a two-dimensional array and then to a graph.  After that, the program will pass the graph to a pathfinding algorithm that will show the shortest path possible for the skeleton to walk to the player.*

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

### /// Misc images

<img src="https://gm1.ggpht.com/rNjxj8bmLb2Kf7urW7yYnjutfYmyHQDWu1eA2su6DEfQAgCBQNfIZkoJRS88Ind80WwatpibFNiqic8dBhmDW5QFz_Im59K0vOIm40zQPO-XsgWtnQ1xz2K6s2l9ueYINrpmntHfs-OhajB4WK-ykYOHvsJ-dXTcrxpngOY3lBz3HDTdrJaEnmMqw5IbgjjtBaRvxOFo2dTzrvhoC4KQNwF8KBpneksqYXyltQ4HvrZVir-4bzxml-L-IgZzDG6M43nR0PwYusOJx8WsrK1TsZ9xeWekrTJX58jYxfb7ljsP8doV8cL2SV3aDma2zfnOFAXLnMULeRx_2r8Xf1Dd6RtbZKE5-Joh584Lwi3CLyWM1TfSCGQtjziYCxFKC3bTiLkWhzM_9Qy83Xyw0jJgMvAfe2tDeMqAyuzGVurgrqs6h8p73H2DJ4ivZ-WYgK-_4W9UmGv1zXf0EeSPg67ndettSiEYc0Fo4uWfTpXSZF0k2iNvxXWcCbwftlN_Ua6dV9w_Hwfa_Vq80Gskd1DTCVhTak2XASY3leD6gaIGPo63326wtW9rjc-AwBAIOO2L-5RtiKhhpLMh5f3es8UfkvOgTtMJZ3FzGvD3Fh-q9MITWcuaVy6k7spJ-BM90bTTaNuMCg27LQ0U8PaE-YsmXSRvAquQIIK4bJ9xr1SmxFcpf-dtcw=w926-h662-l75-ft" />

<img src="https://gm1.ggpht.com/oVb0mu8Gr4e2x9EyAtjcF-TxEMWpIdnHQSPeaFDfLJY1oKiCNuwmGzROLOEaxr1PDLQsM46mfC6vKMwX3fq40gTOSL_1_TbZC6jMtIMHN-G5oOQrwo9SrDEyq5BYGj78IdFGTPsAVyxDv9ZCyb3c3mqf76SGbCQ4xD56pgIY9p5FbgPWXzIvA61sNNtsMhw9oPVFqFo-jzqPKE0HDYssjXU8M4qQ-Q9eRISUK2F3Qb95MRxdyTwP8OIw9O_TiFe5cDwbj-_3sKMWb8aqF_9JeOI2py54_bVmDisQYfLxk92aRvgyzVraGz4Y3jbXZGC8dN5EK4CGlputNGFZy3bQ-h64yNOLaFc70Of0BYY705IZ_aWmWcUDVz0qllBw_8WnJGSChuGOE-7asyISqpSp81AbMiM3TKs3T5MkDnEF6wSTBdQiuhEapdh1ROKjhiJYHma-4n7Tm9wisMtKuC7S5pZPQwV94BS-KFDXtUwytSnE8UebPRA9YAWgZPMgR8Jq_Rmt24Ro8ddIpCWdbyENukHfayXtAYGzt--dQBzLq9qSC0hnAAL_sK5OM6o5vMHobBZmLtY_eea5LuR7VFj2bTd6Jnjq-v3MlL0TnVZ2W8m1CtYcZOy3VaAFZ0t04PGgHNUEpXxla7w1IHmZM-pHi-CUVEkCVpFybbBpHevXuRDAn2wNySw=w918-h902-l75-ft"/>



