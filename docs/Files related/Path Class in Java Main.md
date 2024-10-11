```toc

```
## Theory
The **Path** class forms part of the NIO2 update, which came to Java with version 7. It delivers an entirely new API to work with I/O. Moreover, like the legacy File class, Path also creates an object that may be used to locate a file in a file system.

Technically in terms of Java, Path is an interface which is introduced in Java NIO file package during Java version 7,and is the representation of location in particular file system
.As path interface is in Java NIO package:
```java
import java.nio.file.Path.
```

In Java, _Path_ and [[File Class in java main|File]] are classes responsible for file [[Streams I O (Input Output) in java Main|I/O operations]] with files. They perform the same functions but belong to different packages.

```java
public interface Path extends Comparable<Path>, Iterable<Path>, Watchable
```

Also working with files is described here: [[Files Reading and Writing]]

### Difference with File class
As we know, the _java__.io_ package was delivered with the first release of the Java JDK, allowing us to perform I/O actions. Since then, many developers have reported many drawbacks, missing functionality, and problems with some of its capabilities.

**Error Handling**
The most common problem is poor error handling. Many methods don't tell us any details about the encountered problem or even throw exceptions at all. Mostly it is Boolean values, which is difficult to manage.
Path class now throw exceptions:
```java
File file = new File("baeldung/tutorial.txt");
boolean result = file.delete(); //This code compiles and runs successfully without any error. Of course, we have a _result_ flag containing a _false_ value, but we don't know the reason for this failure. The file might not exist, or the program may not have permission to delete it. 

Path path = Paths.get("baeldung/tutorial.txt");
Files.delete(path);
//Now, the compiler requires us to handle an _IOException_. Moreover, a thrown exception has details about its failure that will tell you, for example, if the file does not exist.
```

**Metadata Support**
The _File_ class in the _java.io_ package has poor metadata support, which leads to problems across different platforms with I/O operations requiring meta-information about files.
The metadata may also include permissions, file owner, and security attributes. Due to this, the _File_ class doesn't support symbolic links at all, and the rename()_ method doesn't work consistently across different platforms.

**Method Scaling and Performance**
There is also a performance problem because the methods of the File class don't scale. It leads to problems with some directories with a large number of files. Listing the contents of a directory could result in a hang, causing memory resource problems. Finally, it could lead to having a Denial of Service.

Due to some of these drawbacks, Oracle developed the improved NIO2 API. Developers should start new projects using this new java.nio package instead of legacy classes, where possible

## Using Path

Implementations of this interface are immutable and safe for use by multiple concurrent threads.

### Creating instance of the Path
To create an instance there is no constructor, and you should use one of the static method of the **Paths** class (It's a different class with only 2 static methods ):

```JAVA
static Path 	get(String first, String... more) // Converts a path string, or a sequence of strings that when joined form a path string, to a Path.
static Path 	get(URI uri) // Converts the given URI to a Path object.
```

<u>Example:</u>
```java
Path t1 = Paths.get("Text.txt");  
Path t2 = Paths.get("D:","Git","javaepam","Test Folder","forQuckTests","src","OtherText.txt");  
System.out.println(t1);  //OUT: Text.txt
System.out.println(t2.toUri()); //OUT: file:///D:/Git/javaepam/Test%20Folder/forQuckTests/src/OtherText.txt
```

 Paths may be used with the Files class to operate on files, directories, and other types of files. For example, suppose we want a BufferedReader to read text from a file "access.log". The file is located in a directory "logs" relative to the current working directory and is UTF-8 encoded.
 
```java
     Path path = FileSystems.getDefault().getPath("logs", "access.log");
     BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
 ```

### Methods

| Method                                                                                                   | Description                                                                                                                                             |
| -------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
| int compareTo(Path other)                                                                                | Compares two abstract paths lexicographically.                                                                                                          |
| boolean endsWith(Path other)                                                                             | Tests if this path ends with the given path.                                                                                                            |
| boolean endsWith(String other)                                                                           | Tests if this path ends with a Path, constructed by converting the given path string, in exactly the manner specified by the endsWith(Path) method.     |
| boolean equals(Object other)                                                                             | Tests this path for equality with the given object.                                                                                                     |
| Path getFileName()                                                                                       | Returns the name of the file or directory denoted by this path as a Path object.                                                                        |
| FileSystem getFileSystem()                                                                               | Returns the file system that created this object.                                                                                                       |
| Path getName(int index)                                                                                  | Returns a name element of this path as a Path object.                                                                                                   |
| int getNameCount()                                                                                       | Returns the number of name elements in the path.                                                                                                        |
| Path getParent()                                                                                         | Returns the parent path, or null if this path does not have a parent.                                                                                   |
| Path getRoot()                                                                                           | Returns the root component of this path as a Path object, or null if this path does not have a root component.                                          |
| int hashCode()                                                                                           | Computes a hash code for this path.                                                                                                                     |
| boolean isAbsolute()                                                                                     | Tells whether or not this path is absolute.                                                                                                             |
| Iterator`<Path>` iterator()                                                                              | Returns an iterator over the name elements of this path.                                                                                                |
| Path normalize()                                                                                         | Returns a path that is this path with redundant name elements eliminated.                                                                               |
| WatchKey register(WatchService watcher, WatchEvent.Kind`<?>.`.. events)                                  | Registers the file located by this path with a watch service.                                                                                           |
| WatchKey register(WatchService watcher, WatchEvent.Kind`<?>[]` events, WatchEvent.Modifier... modifiers) | Registers the file located by this path with a watch service.                                                                                           |
| Path relativize(Path other)                                                                              | Constructs a relative path between this path and a given path.                                                                                          |
| Path resolve(Path other)                                                                                 | Resolve the given path against this path.                                                                                                               |
| Path resolve(String other)                                                                               | Converts a given path string to a Path and resolves it against this Path in exactly the manner specified by the resolve method.                         |
| Path resolveSibling(Path other)                                                                          | Resolves the given path against this path's parent path.                                                                                                |
| Path resolveSibling(String other)                                                                        | Converts a given path string to a Path and resolves it against this path's parent path in exactly the manner specified by the resolveSibling method.    |
| boolean startsWith(Path other)                                                                           | Tests if this path starts with the given path.                                                                                                          |
| boolean startsWith(String other)                                                                         | Tests if this path starts with a Path, constructed by converting the given path string, in exactly the manner specified by the startsWith(Path) method. |
| Path subpath(int beginIndex, int endIndex)                                                               | Returns a relative Path that is a subsequence of the name elements of this path.                                                                        |
| Path toAbsolutePath()                                                                                    | Returns a Path object representing the absolute path of this path.                                                                                      |
| File toFile()                                                                                            | Returns a File object representing this path.                                                                                                           |
| Path toRealPath(LinkOption... options)                                                                   | Returns the real path of an existing file.                                                                                                              |
| String toString()                                                                                        | Returns the string representation of this path.                                                                                                         |
| URI toUri()                                                                                              | Returns a URI to represent this path.                                                                                                                   |

<u>Some Examples:</u>

```java
Path path = Paths.get("D:","Git","javaepam","Test Folder","forQuckTests","src","OtherText.txt");  
Path t1 = Paths.get("D:","Git","javaepam","Test Folder","forQuckTests","Text.txt");
  
System.out.println(path.getFileSystem()); //OUT: sun.nio.fs.WindowsFileSystem@568db2f2  
System.out.println(path.isAbsolute()); //OUT: true  
System.out.println(path.getFileName()); //OUT: OtherText.txt  
System.out.println(path.toAbsolutePath()); //OUT: D:\Git\javaepam\Test Folder\forQuckTests\src\OtherText.txt  
System.out.println(path.getRoot()); //OUT: D:\  
System.out.println(path.getParent()); //OUT: D:\Git\javaepam\Test Folder\forQuckTests\src  
System.out.println(path.getNameCount()); //OUT: 6  
System.out.println(path.getName(0)); //OUT: Git  
System.out.println(path.subpath(0, 2)); //OUT: Git\javaepam  
System.out.println(path); //OUT: D:\Git\javaepam\Test Folder\forQuckTests\src\OtherText.txt  
System.out.println(path.getNameCount()); //OUT: 6

System.out.println(path.relativize(t1)); //OUT: ..\..\Text.txt
```


#### Resolve method
used to resolve the given path against this path. This method will going to connect both paths together.If this path is “C/temp” and passed path is “drive/newFile” then this method will add passed path in the end of this path and use “/” as a separator. So resolved path will be “C/temp/drive/newFile”.

If the other parameter is an absolute path then this method trivially returns other. If other is an empty path then this method trivially returns this path.

<u>Example:</u>
```java
       // create an object of Path
        Path path
            = Paths.get("drive\\temp\\Spring");
  
        // create a string object
        String passedPath = "drive";
  
        // call resolve() to create resolved Path
        Path resolvedPath
            = path.resolve(passedPath);
  
        // print result
        System.out.println("Resolved Path:"
                           + resolvedPath); //Out: Resolved Path: drive\temp\Spring\drive
```

## Managing Files and Directories with Path

To do this, there are two options: use options From [[File Class in java main]]
or 
Use one of a LOT of static methods of the [[Files Class in Java|Files Class]] created for this purpose especially
It is better to use the  second option



**To create files**, we can use the _createNewFile()_ and _Files.createFile()_ methods:
```java
boolean result = file.createNewFile();
Path newPath = Files.createFile(path);
```

**To create a directory**, we need to use _mkdir()_ or _Files.createDirectory()_:
```vhdl
boolean result = file.mkdir();
File newPath = Files.createDirectory(path);
```
There are additional variants of those methods to include all non-existing subdirectories, via the _mkdirs()_ and _Files.createDirectories()_ methods:
```java
boolean result = file.mkdirs();
File newPath = Files.createDirectories(path);
```

**To rename or move a file**, we need to create another instance object and use _renameTo()_ or _Files.move()_:
```java
boolean result = file.renameTo(new File("baeldung/tutorial2.txt"));
Path newPath = Files.move(path, Paths.get("baeldung/tutorial2.txt"));
```

**To perform a delete operation**, we use _delete()_ or _Files.delete()_:
```java
boolean result = file.delete();
Files.delete(Paths.get(path));
```
Notice that legacy methods return a flag with a result set to _false_ in case of any errors. 

> [!NOTE]
>  NIO2 methods return a new _Path_ instance, except for the delete operation, which throws an _IOException_ when errors occur.

