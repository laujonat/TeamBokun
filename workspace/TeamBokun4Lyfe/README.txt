README.txt

---How to run the program---
1) Download zip file
2) Unzip package
3) Open up with eclipse
4) Confirm Eclipse is running Java Compiler JRE System Library[ SE 1.7]
5) Add the following .jar files (found in the “lib” folder) to the build path
	5a) gson-2.2.4-javadoc.jar
	5b) gson-2.2.4-sources.jar
	5c) gson-2.2.4..jar
	5d) mysql-connector-java-5.1.30-bin.jar
	5e) jcommon-1.0.21.jar
	5f) jfreechart-1.0.17.jar
6) Open package org.openstreetmap.gui.jmapviewer
7) Inside of org.openstreetmap.gui.jmapviewer open file: Demo.java
8) Compile & Run!
------------

---What works---

- User should enter address with city and state in the JTextFields
	ex: 123 fake street la, ca
- User must press enter after typing address in the JTextFields
- Starting Point and Destination Point are displayed on the map after addresses are entered
- Clicking Historical Data opens a new JPanel that displays a table and a graph 
		- holds all historical data
		- contains hours of the day, highways, and average speeds
- After typing in destination in the JTextField and pressing enter, a JTextArea is displayed at the bottom below the map
		- JTextArea contains information on distance, time at speed limit, and time at traffic speed
- Map Legend on bottom right hand corner of map
		- hovering mouse over each color shows tooltip containing what each color represents
- Map GUI displays map image from JMapViewer API
- Connects to http server successfully & able to pull updated json file every 3 minutes
- Parsers pulled json files correctly
	- Updates existing cars
	- Creates new cars
	- Null data handled
- Successfully pushes historical data to a MySQL database
------------

---What doesn’t work---
- Cars are not able to properly repaint, causing issues with the animation effects
- Unable to remove car marker after it reaches destination
	- Problem is connected with issues when matching destination coordinates due to a the high level precision of coordinates
- Color cannot be distinguished for speeds of the car due to not being able to properly animating the dots
- Graph X-Axis values are bugged
	- Problem arose when the file was overwritten
