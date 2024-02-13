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
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdlAgBXbDADEaYFQCerDt178kg2wHcAFkjAxRFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAegt9KAAdNABvfMp7AFsUABoYXDVvaA06lErgJAQAX0xhGJgIl04ePgEhaNF4qFceSgAKcqgq2vq9LiaoFpg2joQASkw2YfcxvtEByLkwRWVVLnj2FDAAVQKFguWDq5uVNQvDbTxMgAUQAMsC4OkYItljAAGbmSrQgqYb5KX5cAaDI5uUaecYiFTxNAWBAIQ4zE74s4qf5o25qeIgab8FCveYw4DVOoNdbNL7ydF3f5GeIASQAciCWFDOdzVo1mq12p0YJL0ilkbQcSMPIIaQZBvSMUyWYEFBYwL53hUuSgBdchX9BqK1VLgTKtUs7XVgJbfOkIABrdBujUwP1W1GChmY0LYyl4-UTIkR-2BkNoCnHJMEqjneORPqUeKRgPB9C9aKULGRYLoMDxABMAAYW8USmWM+geugNCYzJZrDZoNJHjBQRBOGgfP5Aph62Ei9W4olUhlsjl9Gp8R25SteRsND1i7AIjqqcnCSh4ggp0g0DbvfLD-zs7i9XmC5cYyaYI8XjefcHR+YUXUBcgwQhWUPjteFES9aNHVjWshg-U4UxvL1lnfXUMOvOlfzuU0UFZC0rSfT4kNA51IldSVpRg215S7CsZ3VTUy2op04wTHNPwNUt0zY3DLy-WlCyiagSzTK1uyzU9UMXRsYFbdtSlYzNezQftTHMKxbDMFBQ0ndhLGYGw-ACIJkAbf5T3iBIZCg9JgU3bcuF3exhMzKtpLPcIL1zQSYDvMzLTmTT0AOIKBMwwjkL-AD2SgSKfOi7iUPAoEoMhbC4IRCAkTLb5MoxEUILYSoIBofKWPSmcUmeDUADFZN8MqwL49DqUw2JROC+LJIcvz+kk5TmzbTA+wHfTh2mDQJzcGAAHE7UxSy5xskJmEGBzEhW4Eslydg7WKKKFJXC5Yvw-NU2QHg1uqLg0rktiYsTOKCKNIjGX-J4ntUV7y0zECeIqnLwTy07nvgorVrtUrjS6yIbt668HnWgavruw09pXTHntGmtxtsjBJvUkp-3WxIShhlAxRkOorMCFLljqbQEFAIM2Z9anqglO0ukybTdMHAybGwCwoGwBADDgM0DEB2drIXMnduXfzHPXY6cnp86Go7enBeqE8rvPT7bsmGBmVIwJAbmY27Q+-irYkn9EuI-7APmC6wayuiIJBKGmOfAxCqRRZOudbq8PR3H4n3bG3cND2aPuG3FYdp3qn98rsrdRj+ZQE2DA44ukd+3jUct+Prfpxnk7riT8a14vG8U0mdopo27UZ0XZqHWxHBQMkIG8GAACkIAfBHqlsTnubVnb7IJtdms3fXvLezMO2UuAIDvKA6gbmQzf867a6vBOYAAKxntAHf3w-oBPvuZBdnrr+-GBkb+gDyLWj9tHOMgdIbQTquHBCJV5AgNQmjb+qYk4IPEqnX+VcHgAztHMZ+R837VEZnnMCYDIIh3bjIOGSJ6aV09jHGurtm5YXpk3RBeNNYxEJmITugwJqqSmjNPSQ8bBmGAM4RApFYDAGwLLQgn4VbzmUqvNuTkXJuVyEYYmAUUEhRAHLPAcxP5x1YQldOTI9GpSIbRAE8QWDAgALIpAAGrAi9DQ9O8Cr6oOJKSckf9QHWNYPYpxLiYHXDgZJbRfUYAkjJOEkhtiHHOIrrAvxHiGGsO8bEyJ31W4cOiT4zRSl1YU2mjpTAQA
'''
