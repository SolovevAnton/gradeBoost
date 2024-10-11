```toc

```

## Theory

> [!Warning]
> java.io.File is now considered as legacy, and not recommended for new projects.
> Instead use [[Path Class in Java Main]] 
> for working with directories, deleting creating files etc use :[[Files Class in Java]]

The File class is Java’s representation of a file or directory pathname
. Because file and directory names have different formats on different platforms, a simple string is not adequate to name them.

This is mostly used by [[Streams I O (Input Output) in java Main|Stream IO]] and [[Files Reading and Writing|working with files]]

User interfaces and operating systems use system-dependent _pathname strings_ to name files and directories. This class presents an abstract, system-independent view of hierarchical pathnames. An _abstract pathname_ has two components:

1.  An optional system-dependent _prefix_ string, such as a disk-drive specifier, `"/"` for the UNIX root directory, or `"\\\\"` for a Microsoft Windows UNC pathname, and
2.  A sequence of zero or more string _names_.

The first name in an abstract pathname may be a directory name or, in the case of Microsoft Windows UNC pathnames, a hostname. Each subsequent name in an abstract pathname denotes a directory; the last name may denote either a directory or a file. The _empty_ abstract pathname has no prefix and an empty name sequence.

it is part of the:

```java
import java.io.File
```

> [!NOTE]
> Instances of the `File` class are immutable; that is, once created, the abstract pathname represented by a `File` object will never change


For more there are path and [[Files Class in Java|Files class]] with A LOT of static methods to work with files.
So go check em;


## Using File class

### Fields

It has following fields:

| Modifier      | Field             | Description                                                                                       |
| ------------- | ----------------- | ------------------------------------------------------------------------------------------------- |
| static String | pathSeparator     | It is system-dependent path-separator character, represented as a string for convenience.         |
| static char   | pathSeparatorChar | It is system-dependent path-separator character.                                                  |
| static String | separator         | It is system-dependent default name-separator character, represented as a string for convenience. |
|static char|separatorChar|It is system-dependent default name-separator character.|

### Constructors

| Constructor                       | Description                                                                                       |
| --------------------------------- | ------------------------------------------------------------------------------------------------- |
| File (File parent, String child)   | It creates a new File instance from a parent abstract pathname and a child pathname string.       |
| File(String pathname)             | It creates a new File instance by converting the given pathname string into an abstract pathname. |
| File(String parent, String child) | It creates a new File instance from a parent pathname string and a child pathname string.         |
|File(URI uri)|It creates a new File instance by converting the given file: URI into an abstract pathname.|

### Methods

Here are most useful methods. More can be found [here](https://docs.oracle.com/javase/7/docs/api/java/io/File.html)

| Method                                                   | Description                                                                                                           |
| -------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| boolean canExecute()                                     | Tests whether the application can execute the file denoted by this abstract pathname.                                 |
| boolean canRead()                                        | Tests whether the application can read the file denoted by this abstract pathname.                                    |
| boolean canWrite()                                       | Tests whether the application can modify the file denoted by this abstract pathname.                                  |
| int compareTo(File pathname)                             | Compares two abstract pathnames lexicographically.                                                                    |
| boolean createNewFile()                                  | Atomically creates a new, empty file named by this abstract pathname.                                                 |
| File createTempFile(String prefix, String suffix)        | Creates an empty file in the default temporary-file directory.                                                        |
| boolean delete()                                         | Deletes the file or directory denoted by this abstract pathname.                                                      |
| void deleteOnExit()                                      | Requests that the file or directory denoted by this abstract pathname be deleted when the virtual machine terminates. |
| boolean equals(Object obj)                               | Tests this abstract pathname for equality with the given object.                                                      |
| boolean exists()                                         | Tests whether the file or directory denoted by this abstract pathname exists.                                         |
| String getAbsolutePath()                                 | Returns the absolute pathname string of this abstract pathname.                                                       |
| String`[]` list()                                        | Returns an array of strings naming the files and directories in the directory.                                        |
| String`[]` list(FilenameFilter filter)                   | Returns an array of strings naming the files and directories in the directory denoted by this abstract pathname that satisfy the specified filter. where FileFilter is a functional inteface                                                                                                                       |
| long getFreeSpace()                                      | Returns the number of unallocated bytes in the partition.                                                             |
| String getName()                                         | Returns the name of the file or directory denoted by this abstract pathname.                                          |
| String getParent()                                       | Returns the pathname string of this abstract pathname’s parent.                                                       |
| File getParentFile()                                     | Returns the abstract pathname of this abstract pathname’s parent.                                                     |
| String getPath()                                         | Converts this abstract pathname into a pathname string.                                                               |
| boolean setReadOnly()                                    | Marks the file or directory named so that only read operations are allowed.                                           |
| boolean isDirectory()                                    | Tests whether the file denoted by this pathname is a directory.                                                       |
| boolean isFile()                                         | Tests whether the file denoted by this abstract pathname is a normal file.                                            |
| boolean isHidden()                                       | Tests whether the file named by this abstract pathname is a hidden file.                                              |
| long length()                                            | Returns the length of the file denoted by this abstract pathname.                                                     |
| File`[]` listFiles()                                     | Returns an array of abstract pathnames denoting the files in the directory.                                           |
| boolean mkdir()                                          | Creates the directory named by this abstract pathname.                                                                |
| boolean renameTo(File dest)                              | Renames the file denoted by this abstract pathname.                                                                   |
| boolean setExecutable(boolean executable)                | A convenience method to set the owner’s execute permission.                                                           |
| boolean setReadable(boolean readable)                    | A convenience method to set the owner’s read permission.                                                              |
| boolean setReadable(boolean readable, boolean ownerOnly) | Sets the owner’s or everybody’s read permission.                                                                      |
| boolean setWritable(boolean writable)                    | A convenience method to set the owner’s write permission.                                                             |
| String toString()                                        | Returns the pathname string of this abstract pathname.                                                                |
| URI toURI()                                              | Constructs a file URI that represents this abstract pathname.                                                         |


## Example:

### File as File

```java
  //File is in the same folder as java prog  
  
  String fname ="Text.txt";  
  // object  
  File f = new File(fname);  
  // apply File class methods on File object  
  System.out.println("File name :" + f.getName());  
  System.out.println("Path: " + f.getPath());  
  System.out.println("Absolute path:"  
          + f.getAbsolutePath());  
  System.out.println("Parent:" + f.getParent());  
  System.out.println("Exists :" + f.exists());  
  
  if (f.exists()) {  
      System.out.println("Is writable:"  
              + f.canWrite());  
      System.out.println("Is readable" + f.canRead());  
      System.out.println("Is a directory:"  
              + f.isDirectory());  
      System.out.println("File Size in bytes "  
              + f.length());  
}

//OUT:
/*
File name :Text.txt
Path: Text.txt
Absolute path:D:\Git\javaepam\Test Folder\forQuckTests\Text.txt
Parent:null
Exists :true
Is writable:true
Is readabletrue
Is a directory:false
File Size in bytes 9
```

### File as directory

<u>Example1:</u>
```java
//file to list all files in the current dir:  
File currentDir = new File(".");  
System.out.println(Arrays.toString(currentDir.list())); //OUT: [.idea, forQuckTests.iml, out, src, Text.txt]
```


<u>Example2</u>

```java
 // Java Program to display all
// the contents of a directory
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

// Displaying the contents of a directory
class Contents {
	public static void main(String[] args)
		throws IOException
	{
		// enter the path and dirname from keyboard
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));

		System.out.println("Enter dirpath:");
		String dirpath = br.readLine();
		System.out.println("Enter the dirname");
		String dname = br.readLine();

		// create File object with dirpath and dname
		File f = new File(dirpath, dname);

		// if directory exists,then
		if (f.exists()) {
			// get the contents into arr[]
			// now arr[i] represent either a File or
			// Directory
			String arr[] = f.list();

			// find no. of entries in the directory
			int n = arr.length;

			// displaying the entries
			for (int i = 0; i < n; i++) {
				System.out.println(arr[i]);
				// create File object with the entry and
				// test if it is a file or directory
				File f1 = new File(arr[i]);
				if (f1.isFile())
					System.out.println(": is a file");
				if (f1.isDirectory())
					System.out.println(": is a directory");
			}
			System.out.println(
				"No of entries in this directory " + n);
		}
		else
			System.out.println("Directory not found");
	}
}

//OUT:
/*
Enter dirpath:
C:\Users\akki\IdeaProjects\
Enter the dirname
codewriting
.idea
: is a directory
an1.txt
: is a file
codewriting.iml
: is a file
file.txt
: is a file
out
: is a directory
src
: is a directory
text
: is a file
No of entries in this directory 7
```
