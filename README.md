JMS 2 Hands on Lab
==================
This hands-on-lab provides a basic introduction to the JMS 2 API included in Java EE 7. The lab uses GlassFish, NetBeans and 
Maven. You could choose to use Eclipse or any other IDE with a little bit of effort. Similarly it should be possible to use
any Java EE 7 compatible application server such as WildFly.

Setup
-----
* This lab assumes you are using your own machine. You will need to perform this setup on the machine you will use for the lab.
* Make sure you have at least JDK 7 installed. The lab was tested with JDK 7.
* Install NetBeans 8 or above. Make sure you use the Java EE version of NetBeans (NetBeans comes in several flavors - we need 
  the Java EE features). During NetBeans installation, please accept all the default choices. This will automatically install 
  GlassFish for you.
* Download this repository, probably as a zip. If using a zip download, unzip somewhere in your file system.
  
Instructions
------------
* Open NetBeans and start GlassFish (Services -> Servers -> GlassFish -> Start).
* Open the Maven project in the 'problem' directory in NetBeans (File -> Open Project). This is the project you will be 
  working on. Simply start reading the instructions in the comments for the 
  [unit test](problem/src/test/java/org/glassfish/jms2lab/Jms2Test.java). At this stage you should have a basic understanding 
  of JMS 2 and have had explored the JMS 2 Javadocs (if this is not the case you should start with the resources below). 
  You will be basically developing the unit test in the project (the tests will fail when you start). To run all the tests
  in NetBeans, right click the unit test file and select "Test File". Running the tests the first time will take some time as the
  Maven build is invoked for the first time and all required dependencies are downloaded. You will be much better off running 
  each test at a time througout the lab. You do this by right clicking on a given test method in the editor and selecting 
  "Run Focused Test Method".
* A solution is in the 'solution' directory. Feel free to open this in NetBeans and look at it if you need to. The best way of 
  learning is looking at the solution if you are absolutely stuck.
  
Resources
---------
* [JMS.Next(): JMS 2.0 and Beyond](http://www.slideshare.net/reza_rahman/whats-new-in-java-message-service-2)
* [JMS 2 Section of Java EE 7 Tutorial](http://docs.oracle.com/javaee/7/tutorial/doc/partmessaging.htm)
* [JMS 2 Javadocs](http://docs.oracle.com/javaee/7/api/index.html?javax/jms/package-summary.html)