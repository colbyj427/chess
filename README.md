# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

'''
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdlAgBXbDADEaYFQCerDt178kg2wHcAFkjAxRFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAegt9KAAdNABvfMp7AFsUABoYXDVvaA06lErgJAQAX0xhGJgIl04ePgEhaNF4qFceSgAKcqgq2vq9LiaoFpg2joQASkw2YfcxvtEByLkwRWVVLnj2FDAAVQKFguWDq5uVNQvDbTxMgAUQAMsC4OkYItljAAGbmSrQgqYb5KX5cAaDI5uUaecYiFTxNAWBAIQ4zE74s4qf5o25qeIgab8FCveYw4DVOoNdbNL7ydF3f5GeIASQAciCWFDOdzVo1mq12p0YJL0ilkbQcSMPIIaQZBvSMUyWYEFBYwL53hUuSgBdchX9BqK1VLgTKtUs7XVgJbfOkIABrdBujUwP1W1GChmY0LYyl4-UTIkR-2BkNoCnHJMEqjneORPqUeKRgPB9C9aKULGRYLoMDxABMAAYW8USmWM+geugNCYzJZrDZoNJHjBQRBOGgfP5Aph62Ei9W4olUhlsjl9Gp8R25SteRsND1i7AIjqqcnCSh4ggp0g0DbvfLD-zs7i9XmC5cYyaYI8XjefcalfTYHR+YUXUBcgwQhWUPh9eFES9aNHVjWshg-U4UxvL1lnfXVsOvOlfzuU0UFZC0rSfT5UIg51IldSVpXg215S7CsZ3VTUyzop04wTHNPwNUt004gjLy-WlCyiagSzTK1uyzU8MMXRsYFbdtSg4zNezQftTHMKxbDMFBQ0ndhLGYGw-ACIJkAbf5T3iBIZFg9JgU3bcuF3ewxMzKs5LPcIL1zESYDvSzLTmHT0AOULhJwki0L-AD2SgGL-Livj0KgoFYMhPC7SQiAkTLb4coxEVoLYSoIBoIr2KymcUmeDUADEFN8SrIMErDqRw2IJLCpKZOcwL+hktTmzbTA+wHIzh2mDQJzcGAAHE7UxGy53skJmEGZzEnW4Eslydg7WKWLlJXC4EqI-NU2QHhNuqLhMsUzj4sTRLiKNUjGX-J5XtUD7y0zcD+Oq-LwUKi63pKpEQYq41esie6BuvB4tuG37HsNQ6V2xt6JprKaHIwGatJKf8tsSEp4ZQMUZDqWzAnS5Y6m0BBQCDDnEMZiU7S6TI9IMwdjJsbALCgbAEAMOAzQMEHZzshcKYO5cgpc9czpyRmruajtBeF0ngoxq98fI1kQbmE3qm+oSHu-GBUcBtK3muyHcsY6CQVh1jnwMBFSpQt2BPRn7ndTfdcejw0fxSsiYGZCjAlt+37R6hiAXFd1PUzsNNUZlGAYjzDCMxq3aeqZm46rgtCe1mumZkM3VI1qnjbtZmxYWodbEcFAyQgbwYAAKQgB8NuKmxud59X9qcom1zazcDb8z7Mw7NS4AgO8oDqRne-b88o4b1MACsp7QW3d-36Aj57mRHf6y2XfDh4nio60vezuNfYwzgo1YOyFyryH-hhC2UlcKx2gQaZK9F7hAzAHfDWe8D5P1ri-SBeUYDPCSDIBQHkW7M0Ri3UuSdnR9Uru-VMjN650IJlrGIxMxAqXJvtKmc19L90lmYYAzhEAUVgMAbActCCflVvONSy9m6uXcp5XIRhT4hXPkwpk8s8BzFfrQmBiD+KaJETo3BgDWDAgALIpAAGrAi9JQpBUD1EwOJKSck4dobmKsbYrqDioYyXgYNGAJIySmNzl4mxdiS4QI8QE5x4UQnkkCX9JurDgluNPoMaaGlZrzSAA
'''
