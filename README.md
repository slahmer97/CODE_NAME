# TP FINAL CODE NAME GAME
Implementation of codename game online Mode
## Technologies
This Game is based on server/client Architecture with MVC pattern
* **Serve side is made using spring-boot-mvc(java framework)**
* **Client side is made using html5/css/javascript**




### Prerequisites

MAVEN package manager
```
sudo apt install maven
```
JDK 8 (tested on jdk 10)



### Installing


```
No installation is required unless you have JDK,MVN INSTALLED
```

## Deployment

After downloading repository in your machine go to root file where you have to see following files : 
```
  mvnw.cmd  pom.xml  /src  /target
```
Then Run mvn to resolve all dependencies and build project for your(.jar will be resulted)
```
mvn clean install
```
After this command is done a directory of jar file will be shown by maven 
looks like this : 
```
[INFO] Installing /home/sidahmed/Downloads/CODE_NAME-master/target/demo-0.0.1.jar to /home/sidahmed/.m2/repository/com/codename/demo/0.0.1/demo-0.0.1.jar
```
Then you go to 
```
 cd /home/sidahmed/.m2/repository/com/codename/demo/0.0.1/
```
Then execute .jar file using command
```
java -jar file.jar

```
By default Server will run in localhost:8080 or (127.0.0.1:8080)
open your browser en navigate to localhost:8080 , Enjoy ^^

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning


## Authors

* **Lahmer SeyyidAhmed** - *Server Side(game logic management)+Network Side(Server Message management)* 
* **Massinissa Begriche** - *Client logic functionalities* 
* **Abderahmane Moussaoui** - *IHM design client interface* 



## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


