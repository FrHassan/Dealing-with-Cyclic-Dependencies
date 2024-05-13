# Dealing-with-Cyclic-Dependencies
This repository contains technical information and practical implementations of the algorithms utilized in the research described in the paper "*An Iterative Approach for Dealing with Cyclic Dependencies in Microservices Architectures*".

The aforementioned paper offers additional illustrations, insights, and examples on how to address the cyclic dependencies anti-pattern in microservices architecture.

# Strongly Connected Components (SCC) algorithm using Neo4j graph database

The following instructions help implementing the SCC algorithm to detect the presence of cyclic depednencies based on the methodology outlined in the paper.

**1.** Install _Neo4j Desktop_ following the instructions mentioned in [[1]](https://neo4j.com/docs/desktop-manual/current/installation/download-installation/).  
**2.** Use _Neo4j Desktop_ to create a new graph database.  

<p align="center">
 <img src="Assets/Images/Neo4j Desktop Create DB.png" width="70%">
</p>

**3.** Under the database "_Plugins_" section, install "_APOC_" and "_Graph Data Science Library_". 
   - _Awesome Procedures On Cypher (APOC)_ [[2]](https://neo4j.com/labs/apoc/) adds a lot of useful_ Cypher_ functionalities that help manipulating the data. 
   - _Graph Data Science Library_ [[3]](https://neo4j.com/docs/graph-data-science/current/) will make it easier for us to execute the available Neo4j graph algorithms.

<p align="center">
 <img src="Assets/Images/Neo4j Desktop Plugins.PNG" width="70%">
</p>

**4.** Click on the "_Start_" button to run the database.  
   - Whenever the database is operational, Neo4j will indicate an active state with a green color.


<p align="center">
 <img src="Assets/Images/Neo4j Desktop DB Start.PNG" width="70%">
</p>

<p align="center">
 <img src="Assets/Images/Neo4j Desktop DB Starting.PNG" width="70%">
</p>

<p align="center">
 <img src="Assets/Images/Neo4j Desktop DB Running.PNG" width="70%">
</p>

**5.** Under the "_Open_" list, open _Neo4j Browser_.  
**6.** Create the microservices architecture associated graph using the "_Cypher_"  [[4]](https://neo4j.com/docs/cypher-manual/current/clauses/create/) (You can take inspiration from the code escorted to each existing project in this repository).
   - Now you can manipulate the graph as you want the graph using _Cypher Query Language_ [[5]](https://neo4j.com/developer/cypher/).  

<p align="center">
 <img src="Assets/Images/Neo4j Desktop Create Graph.PNG" width="70%">
</p>

<p align="center">
 <img src="Assets/Images/Neo4j Desktop Match Graph.PNG" width="70%">
</p>

**7.** Under the "_Graph Apps_" section, open "Graph Data Science Playground" aka _NEuler_.  
   - In case _NEuler_ is absent from the list, you can install it using this url:: [https://neo.jfrog.io/neo/api/npm/npm/neuler](https://neo.jfrog.io/neo/api/npm/npm/neuler)
   - Copy the url and click on "Install".
<p align="center">
 <img src="Assets/Images/Neo4j Desktop Graph Apps.PNG">
</p>

**8.** With _NEuler_, you have the ability to utilize all the graph algorithms that are currently at your disposal.
   - Ensure that you activate the "Store result?" checkbox and make a note of the "Write Property" option as it will be utilized later to save the acquired outcomes.
   - The official documentation provides comprehensive information on all the Neo4j graph algorithms that are available [[13]](https://neo4j.com/docs/graph-data-science/current/algorithms/).

<p align="center">
 <img src="Assets/Images/NEuler Graph Algorithms.PNG" width="70%">
</p>

<p align="center">
 <img src="Assets/Images/Neo4j Desktop DB result.PNG">
</p>

<p align="center">
 <img src="Assets/Images/NEuler Algorithm Result.PNG" width="70%">
</p>
