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


###Link to the request and process diagrams
'''
https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdlAgBXbDADEaYFQCerDt178kg2wHcAFkjAxRFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAegt9KAAdNABvfMp7AFsUABoYXDVvaA06lErgJAQAX0xhGJgIl04ePgEhaNF4qFceSgAKcqgq2vq9LiaoFpg2joQASkw2YfcxvtEByLkwRWVVLnj2FDAAVQKFguWDq5uVNQvDbTxMgAUQAMsC4OkYItljAAGbmSrQgqYb5KX5cAaDI5uUaecYiFTxNAWBAIQ4zE74s4qf5o25qeIgab8FCveYw4DVOoNdbNL7ydF3f5GeIASQAciCWFDOdzVo1mq12p0YJL0ilkbQcSMPIIaQZBvSMUyWYEFBYwL53hUuSgBdchX9BqK1VLgTKtUs7XVgJbfOkIABrdBujUwP1W1GChmY0LYyl4-UTIkR-2BkNoCnHJMEqjneORPqUeKRgPB9C9aKULGRYLoMDxABMAAYW8USmWM+geugNCYzJZrDZoNJHjBQRBOGgfP5Aph62Ei9W4olUhlsjl9Gp8R25SteRsND1i7AIjqqcnCSh4ggp0g0DbvfLD-zs7i9XmC5cYyaYI8XjefcHR+YUXUBcgwQhKEACp4URL0YD8SgDH3aNHVjWshg-U4UxvL1vnfXVcOvLCL1zA14hiJA4UcJI1iPOZX02OpFm+A5yM-A06V-O5TRQVkLStJ9PnQ0DnUiV1JWlWUPh9NMrW7Gd1U1MsxKdOMExzLi8NLdMKyzTiSPzWlCyiagSwU8tMyrCyz0GRdGxgVt21KLsDN7NB+1McwrFsMwUFDSd2EsZgbD8AIgmQBt-lPeIEhkKD0mBTdty4Xd7H0mzTwuIzqV0mA7xCy05nczMOMTHTSKNXjGX-J52SgUqsvQECNJFCCQXBSEvVhBEICRMtCONMCtJw-Lr3iNC8qvEzDR-DC-2mSoIBoITrTK1r1Mw8D4jYFaaCspSYBSZ4NQAMSs4bas0yIZq-IkiMvB7DUGOLbP6MzHObNtMD7AdfOHaYNAnNwYAAcTtTFwrnKKQmYN6V3i8HgSyXJ2DtYpNqzHLz0q4zJkKtxIeqLhmsUgyKu0gnTIW8T7nqwD5mxtqdskzqoJ6-d4IGq75G2jEyPxia5qmuTqieii8J4xa+MZknVDmVnBd2yDuqhDHSZ5pEFeu2XnTG4iRcJzXVElqq5qwuL-yhj6ay+6KMB+1yShtrWEhKU2xRkOoIsCRrljqbQEFAIMA-k02JTtLpMk87zBz8mxsAsKBsAQAw4DNAwFdnSKF0dhHlzs+L1zRnJTaxlq0A7SPo7ts9wnuyiYGZATAgVuZa+qKnxtm78YBGuqAMa8nrK2we43ZoFOdk21qm1r0BdGu7hb71NptXl6Zfp-jWQ7rv7SXiSAXFd1PQPsNNVNvX6aF6njdTL2ZHNmnXqLmIHjtb366w77nLbGuX8ZBxwBkOWwjgUBkggN4GAAApCAD4IZ2lsMHUO+d4axSRmuM6m4K6ZQppmDsjk4AQDvFAOoT8TwrlypvZuAArBBaAO7ENIdAChQCe5GzXvNAeN0HhPHWqPJSyswJTzVtBXqyD+qDX9DfdqZkm4FQ3vfbh28NL8LAPvDhR9J4nyiOncAbsUDewXtffmOi769xep-CWijqp03US3EOSBKhkGwBAMAcwWFkPYdUb2rFxaHwnh1Ka2AtCBCMd8GA+hmB+zZIEnIqCQBh0CcUJCvgUJGO-sEhRtCCovwfm-cyH8YA-wdvDZ2f0vKgMTmYYAzhEACVgMAbAadCCflzvORymDi6JESuCZKm4jBlMbnkyaTimlKwKaomq+sGYgHTngKZOSxEsGBAAWRSAANWBARcxKy9FrM2Tsvm1wLGqyOds3ZZizk5MNs9ZuJIyTTK3mZa2TzyS4wcgXSp-0gA
'''
