# DERO Benchmarks

DERO Benchmarks allows you to list each user's benchmarks. Admins can manually confirm/delete each user's benchmarks from a dedicated page.

The backend part uses the [Paladin Framework](https://github.com/paladin-framework/paladin) and is made in Java.

The frontend part uses [VueJS Framework](https://vuejs.org/) and [Vuetify](https://vuetifyjs.com) and can be found in [dero-benchmark-vue](https://github.com/Slixe/dero-benchmarks/tree/master/dero-benchmark-vue) folder.

# How to start
To start, you will need to build the backend and frontend separately.

## Backend
First, you must have a java version higher or equal to Java 8.
To compile the backend, do the following command:
```
gradlew fatJar
```
The executable jar file will be found in `build/libs/` folder.
To execute it, all you'll have to do is:
```
java -jar dero-benchmarks.jar
```

Edit the `config.json` file to put the port on which the server should listen. 

### Create User
Edit the file `admins.json`, an example is created in it.
If you want the application to hash your password, replace `"hashedPassword"` with `"password"`.

Example of `admins.json`:
```json
[
  {
    "username": "Slixe",
    "password": "password123",
    "salt": "salt123"
  }
]
```

### Enable SSL
There are two ways to enable SSL on DERO Benchmarks:
- You can make a reverse proxy using NGINX (or another one) for example.
- You can directly activate it in the config.json file

For the second solution, you will need to generate a Java keystore file (.jks).
To create a java keystore file from an already existing certificate, please refer [here](https://stackoverflow.com/questions/906402/how-to-import-an-existing-x-509-certificate-and-private-key-in-java-keystore-to)

For more examples: [here](https://www.javacodegeeks.com/2014/07/java-keystore-tutorial.html)

## Frontend
First you will need to change the value of `Vue.prototype.$api` with your API address (so the IP address/domain name where your backend is running) in the `src/main.js` file.

Then, to build the frontend:
```
yarn run build
```
The result will be available in the `dist` folder and all you have to do is move its content to a web server.

# Screenshots
Some screenshots of the current design.

### Index
![](https://i.imgur.com/r3T2p0J.png)

### Submit
![](https://i.imgur.com/JoLyor7.png)

### Login
![](https://i.imgur.com/jmUFFJw.png)

### Unconfirmed Benchmarks (admin page)
![](https://i.imgur.com/ZZe0Atv.png)


## DERO
```
dERokevAZEZVJ2N7o39VH81BXBqX9ojtncnPTDMyiVbmYiTXQY93AUCLcor9xsWCKWhYy25ja89ikZWXWab9kXRB7LYfUmbQyS
```
