# Client-Server Key-Value Store 

The application stores a key-value pairs of Integers on the server. The client can connect to the server and do any one of the following operations: PUT, GET or DELETE. The application uses socket programming and is created to showcase multithreading and concurrency.

  - The Server is hosted on an AWS EC2 instance
  - The operating system used on the server side is Amazon Linux AMI 
  - The operating system used to run the client is Linux(Ubuntu)
  - The language used is Java
  - The IDE used is Netbeans, and has its default project structure

### Running the application

The application requires [JRE 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) to run.

To start the server:
```sh
$ cd ClientServerBasic/build/classes/
$ java Server <Port number>
```

Inorder to run the client:

```sh
$ cd ClientServerBasic/build/classes/
$ java Client <Server IP Address> <Server Port number>
```

The client can also connect to the remote server running on AWS with the following credentials:

```sh
SERVER IP:
SERVER Port Number: 4000
```
