# Programming in Depth - Exercise 3 - JSON

Following exercise regards working with JSON, Javascript Object Notation. Some resources are provide in the following [links](https://www.w3schools.com/js/js_json_intro.asp), [json](https://json.org). JSON is a lightweight data-interchange format, meaning that it is used for formatting documents such that data can be exchanged between computers. These types of documents are human read- and writable. JSON documents are also easy for computers to parse and generate which means that it is compatible with many programming language. Therefore, it is suitable tool for long term storage for small applications. In this exercise you’ll code an application for taking attendance. The application should run in the terminal and have a text interface, similar to the project in Individual Project. The functionalities should include

1. Load in a member file (one sample file is provided for you).
2. Take the attendance of every member in the loaded file.
3. Save the attendance sheet onto a file using JSON formatting (either in a large single file, or a file for every member).
4. Display an attendance sheet given a certain date.
5. Add new member(s) to a new member file or an existing member file.
6. Delete a member from a member file.

Last but not least, you have write tests for your program. You should write tests for at least two classes and for each class there should be three tests. 
You should also convert you project into a .jar file with a [Build Tool](https://stackoverflow.com/questions/7249871/what-is-a-build-tool). For Java there are couple Build Tool you can choose from e.g. Gradle, Maven and Ant.

These are some of the open questions for you to think about,
1. How can we save the attendance sheet? What information do we need to save?
2. How is JSON library handling file writes?
3. What are the pros and cons of having to save a file
  - of the whole attendance sheet?
  - for every member?
4. What could be used instead of JSON?
5. Discuss the decision behind your chosen library.
6. What kind of data structure can we use to store members?
7. What kind of data structures can we use for the application?

<!---1. save and load JSON files.
2. create a new attendance sheet.
3. add and delete a member for an attendance sheet.
4. take attendance for existing and new members.
5. display the previous or current taken attendance.
6. update current taken attendance.
7. display latest date of taken attendance.
-->


All of these functionalities are active after the application is launched. It should not require a restart of the application for some of the functionalities to work. Java is the chosen programming language for this exercise. JSON sample file will be provide for you to load in your application. Explore and choose a suitable Java library of your own choice to parse and write JSON files. Good luck, and great job on the previous exercises.
