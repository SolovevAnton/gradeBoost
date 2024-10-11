```toc

```

Really nice side lib to work with files: https://commons.apache.org/proper/commons-io/javadocs/api-2.5/org/apache/commons/io/FileUtils.html#copyDirectory(java.io.File,%20java.io.File,%20boolean)

The class that was created to operate with Files in combination with [[Path Class in Java Main]]
It has only static methods for working with files.

Nice article about working with this class:
https://javarush.com/groups/posts/2275-files-path

## Creating copying and deleting files and directories

| Method                                                                                          | Description                                                                                                            |
| ----------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| static long copy(InputStream in, Path target, CopyOption... options)                            | Copies all bytes from an input stream to a file.                                                                       |
| static long copy(Path source, OutputStream out)                                                 | Copies all bytes from a file to an output stream.                                                                      |
| static Path copy(Path source, Path target, CopyOption... options)                               | Copy a file to a target file.                                                                                          |
| static Path createDirectories(Path dir, FileAttribute`<?>`... attrs)                            | Creates a directory by creating all nonexistent parent directories first.                                              |
| static Path createDirectory(Path dir, FileAttribute`<?>`... attrs)                              | Creates a new directory.                                                                                               |
| static Path createFile(Path path, FileAttribute`<?>`... attrs)                                  | Creates a new and empty file, failing if the file already exists.                                                      |
| static Path createLink(Path link, Path existing)                                                | Creates a new link (directory entry) for an existing file (optional operation).                                        |
| static Path createSymbolicLink(Path link, Path target, FileAttribute`<?>`... attrs)             | Creates a symbolic link to a target (optional operation).                                                              |
| static Path createTempDirectory(Path dir, String prefix, FileAttribute`<?>`... attrs)           | Creates a new directory in the specified directory, using the given prefix to generate its name.                       |
| static Path createTempDirectory(String prefix, FileAttribute`<?>`... attrs)                     | Creates a new directory in the default temporary-file directory, using the given prefix to generate its name.          |
| static Path createTempFile(Path dir, String prefix, String suffix, FileAttribute`<?>`... attrs) | Creates a new empty file in the specified directory, using the given prefix and suffix strings to generate its name.   |
| static Path createTempFile(String prefix, String suffix, FileAttribute`<?>`... attrs)           | Creates an empty file in the default temporary-file directory, using the given prefix and suffix to generate its name. |
| static void delete(Path path)                                                                   | Deletes a file.                                                                                                        |
| static boolean deleteIfExists(Path path)                                                        | Deletes a file if it exists.                                                                                           |
| static Path move(Path source, Path target, CopyOption... options)                               | Move or rename a file to a target file.                                                                                |


All throws IOExceptions if fails

## Getting info about file

| Method                                                                | Description                                                                |
| --------------------------------------------------------------------- | -------------------------------------------------------------------------- |
| static boolean exists(Path path, LinkOption... options)               | Tests whether a file exists.                                               |
| static boolean isDirectory(Path path, LinkOption... options)          | Tests whether a file is a directory.                                       |
| static boolean isExecutable(Path path)                                | Tests whether a file is executable.                                        |
| static boolean isHidden(Path path)                                    | Tells whether or not a file is considered hidden.                          |
| static boolean isReadable(Path path)                                  | Tests whether a file is readable.                                          |
| static boolean isRegularFile(Path path, LinkOption... options)        | Tests whether a file is a regular file with opaque content.                |
| static boolean isSameFile(Path path, Path path2)                      | Tests if two paths locate the same file.                                   |
| static boolean isSymbolicLink(Path path)                              | Tests whether a file is a symbolic link.                                   |
| static boolean isWritable(Path path)                                  | Tests whether a file is writable.                                          |
| static boolean notExists(Path path, LinkOption... options)            | Tests whether the file located by this path does not exist.                |
| static long size(Path path)                                           | Returns the size of a file (in bytes).                                     |
| static UserPrincipal getOwner(Path path, LinkOption... options)       | Returns the owner of a file.                                               |
| static FileStore getFileStore(Path path)                              | Returns the FileStore representing the file store where a file is located. |
| static FileTime getLastModifiedTime(Path path, LinkOption... options) | Returns a file's last modified time.                                       |

## Working and changing file attributes

| Method                                                                                                          | Description                                         |
| --------------------------------------------------------------------------------------------------------------- | --------------------------------------------------- |
| static Object getAttribute(Path path, String attribute, LinkOption... options)                                  | Reads the value of a file attribute.                |
| static `<V extends FileAttributeView>`  getFileAttributeView(Path path, Class`<V>` type, LinkOption... options) | V Returns a file attribute view of a given type.    |
| static Set`<PosixFilePermission`> getPosixFilePermissions(Path path, LinkOption... options)                     | Returns a file's POSIX file permissions.            |
| static `<A extends BasicFileAttributes>`  readAttributes(Path path, Class`<A>` type, LinkOption... options)     | A Reads a file's attributes as a bulk operation.    |
| static Map`<String,Object>` readAttributes(Path path, String attributes, LinkOption... options)                 | Reads a set of file attributes as a bulk operation. |
| static Path setAttribute(Path path, String attribute, Object value, LinkOption... options)                      | Sets the value of a file attribute.                 |
| static Path setLastModifiedTime(Path path, FileTime time)                                                       | Updates a file's last modified time attribute.      |
| static Path setOwner(Path path, UserPrincipal owner)                                                            | Updates the file owner.                             |
| static Path setPosixFilePermissions(Path path, Set`<PosixFilePermission>` perms)                                | Sets a file's POSIX permissions.                    |

## Reading and Writing to file

| Method                                                                                                                 | Description                                                                                                                        |
| ---------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| static BufferedReader newBufferedReader(Path path, Charset cs)                                                         | Opens a file for reading, returning a BufferedReader that may be used to read text from the file in an efficient manner.           |
| static BufferedWriter newBufferedWriter(Path path, Charset cs, OpenOption... options)                                  | Opens or creates a file for writing, returning a BufferedWriter that may be used to write text to the file in an efficient manner. |
| static SeekableByteChannel newByteChannel(Path path, OpenOption... options)                                            | Opens or creates a file, returning a seekable byte channel to access the file.                                                     |
| static SeekableByteChannel newByteChannel(Path path, Set`<? extends OpenOption>` options, FileAttribute`<?>`... attrs) | Opens or creates a file, returning a seekable byte channel to access the file.                                                     |
| static DirectoryStream`<Path>` newDirectoryStream(Path dir)                                                            | Opens a directory, returning a DirectoryStream to iterate over all entries in the directory.                                       |
| static DirectoryStream`<Path>` newDirectoryStream(Path dir, DirectoryStream.Filter`<? super Path>` filter)             | Opens a directory, returning a DirectoryStream to iterate over the entries in the directory.                                       |
| static DirectoryStream`<Path>` newDirectoryStream(Path dir, String glob)                                               | Opens a directory, returning a DirectoryStream to iterate over the entries in the directory.                                       |
| static InputStream newInputStream(Path path, OpenOption... options)                                                    | Opens a file, returning an input stream to read from the file.                                                                     |
| static OutputStream newOutputStream(Path path, OpenOption... options)                                                  | Opens or creates a file, returning an output stream that may be used to write bytes to the file.                                   |
| static String probeContentType(Path path)                                                                              | Probes the content type of a file.                                                                                                 |
| static byte`[]` readAllBytes(Path path)                                                                                | Reads all the bytes from a file.                                                                                                   |
| static List`<String>` readAllLines(Path path, Charset cs)                                                              | Read all lines from a file.                                                                                                        |
| static Path readSymbolicLink(Path link)                                                                                | Reads the target of a symbolic link (optional operation).                                                                          |
| static Path write(Path path, byte`[]` bytes, OpenOption... options)                                                    | Writes bytes to a file.                                                                                                            |
| static Path write(Path path, Iterable`<? extends CharSequence>` lines, Charset cs, OpenOption... options)              | Write lines of text to a file.                                                                                                     |


## Walking the file tree

> [!warning]
> A file tree is walked depth first, but you cannot make any assumptions about the iteration order that subdirectories are visited.


The method to have a stream of files is:

| Method                                                                            | Description                                                                                                  |
| --------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| static Stream`<Path>` walk​(Path start, int maxDepth, FileVisitOption... options) | Return a Stream that is lazily populated with Path by walking the file tree rooted at a given starting file. |
| static Stream`<Path>` walk​(Path start, FileVisitOption... options)               | Return a Stream that is lazily populated with Path by walking the file tree rooted at a given starting file. |

Method to walk the tree:
About it in Rus: https://habr.com/ru/post/437694/

| Method                                                                                                                  | Description        |
| ----------------------------------------------------------------------------------------------------------------------- | ------------------ |
| static Path walkFileTree(Path start, FileVisitor`<? super Path>` visitor)                                               | Walks a file tree. |
| static Path walkFileTree(Path start, Set`<FileVisitOption>` options, int maxDepth, FileVisitor`<? super Path>` visitor) | Walks a file tree. |

This is unique methods to work with the whole tree.

```java
public static Path walkFileTree(Path start,
                Set<FileVisitOption> options,
                int maxDepth,
                FileVisitor<? super Path> visitor)
                         throws IOException

 
 /*Parameters:
    start - the starting file
    options - options to configure the traversal
    maxDepth - the maximum number of directory levels to visit
    visitor - the file visitor to invoke for each file
```

Walks a file tree.

This method walks a file tree rooted at a given starting file. The file tree traversal is depth-first with the given FileVisitor invoked for each file encountered. File tree traversal completes when all accessible files in the tree have been visited, or a visit method returns a result of TERMINATE. Where a visit method terminates due an IOException, an uncaught error, or runtime exception, then the traversal is terminated and the error or exception is propagated to the caller of this method.

For each file encountered this method attempts to read its BasicFileAttributes. If the file is not a directory then the visitFile method is invoked with the file attributes. If the file attributes cannot be read, due to an I/O exception, then the visitFileFailed method is invoked with the I/O exception.

Where the file is a directory, and the directory could not be opened, then the visitFileFailed method is invoked with the I/O exception, after which, the file tree <u>walk continues</u>, by default, at the next sibling of the directory.

Where the directory is opened successfully, then the entries in the directory, and their descendants are visited. When all entries have been visited, or an I/O error occurs during iteration of the directory, then the directory is closed and the visitor's postVisitDirectory method is invoked. The file tree walk then continues, by default, at the next sibling of the directory.

By default, symbolic links are not automatically followed by this method. If the options parameter contains the FOLLOW_LINKS option then symbolic links are followed. When following links, and the attributes of the target cannot be read, then this method attempts to get the BasicFileAttributes of the link. If they can be read then the visitFile method is invoked with the attributes of the link (otherwise the visitFileFailed method is invoked as specified above).

If the options parameter contains the FOLLOW_LINKS option then this method keeps track of directories visited so that cycles can be detected. A cycle arises when there is an entry in a directory that is an ancestor of the directory. Cycle detection is done by recording the file-key of directories, or if file keys are not available, by invoking the isSameFile method to test if a directory is the same file as an ancestor. When a cycle is detected it is treated as an I/O error, and the visitFileFailed method is invoked with an instance of FileSystemLoopException.

The maxDepth parameter is the maximum number of levels of directories to visit. A value of 0 means that only the starting file is visited, unless denied by the security manager. A value of MAX_VALUE may be used to indicate that all levels should be visited. The visitFile method is invoked for all files, including directories, encountered at maxDepth, unless the basic file attributes cannot be read, in which case the visitFileFailed method is invoked.

If a visitor returns a result of null then NullPointerException is thrown.

When a security manager is installed and it denies access to a file (or directory), then it is ignored and the visitor is not invoked for that file (or directory).

### FileVisitor API
The interface to work with the file.
There can be two options:
First one

#### Full Version of FileVisitor
To use it, Interface FileVisitor has to be overriten:

```java
public interface FileVisitor<T>
```

A visitor of files. An implementation of this interface is provided to the Files.walkFileTree methods to visit each file in a file tree.

Overall it has four methods:
```java
FileVisitResult 	postVisitDirectory(T dir, IOException exc)
//Invoked for a directory after entries in the directory, and all of their descendants, have been visited.
FileVisitResult 	preVisitDirectory(T dir, BasicFileAttributes attrs)
//Invoked for a directory before entries in the directory are visited.
FileVisitResult 	visitFile(T file, BasicFileAttributes attrs)
//Invoked for a file in a directory.
FileVisitResult 	visitFileFailed(T file, IOException exc)
//Invoked for a file that could not be visited.
```


If u need a version with only one method see simple file visitor

Usage Examples: Suppose we want to delete a file tree. In that case, each directory should be deleted after the entries in the directory are deleted.

```java
     Path start = ...
     Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
         @Override
         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
             throws IOException
         {
             Files.delete(file);
             return FileVisitResult.CONTINUE;
         }
         @Override
         public FileVisitResult postVisitDirectory(Path dir, IOException e)
             throws IOException
         {
             if (e == null) {
                 Files.delete(dir);
                 return FileVisitResult.CONTINUE;
             } else {
                 // directory iteration failed
                 throw e;
             }
         }
     })

```


#### SimpleFileVisitor
It is used when the only method used in the going logic is : visitFile()
For example, we need a program, that looks only for files with specific line:
```java
public class MyFileVisitor extends SimpleFileVisitor<Path> {

   @Override
   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

       List<String> lines = Files.readAllLines(file);
       for (String s: lines) {
           if (s.contains("This is the file we need")) {
               System.out.println("Нужный файл обнаружен!");
               System.out.println(file.toAbsolutePath());
               break;
           }
       }

       return FileVisitResult.CONTINUE;
```

#### FileVisitor Result
Possible options to do after visiting the file:

```java
public enum FileVisitResult extends Enum<FileVisitResult>
```
The result type of a FileVisitor.

Possible outcomes:
```java
CONTINUE  //Continue. When returned from a preVisitDirectory method then the entries in the directory should also be visited.
SKIP_SIBLINGS //Continue without visiting the siblings of this file or directory. If returned from the preVisitDirectory method then the entries in the directory are also skipped and the postVisitDirectory method is not invoked.
SKIP_SUBTREE //Continue without visiting the entries in this directory. This result is only meaningful when returned from the preVisitDirectory method; otherwise this result type is the same as returning CONTINUE.

TERMINATE //Terminate.
```

#### BasicFileAttributes

It is used to find some attributes from a file with the methods:


| Method                      | Description                                                                                        |
| --------------------------- | -------------------------------------------------------------------------------------------------- |
| FileTime creationTime()     | Returns the creation time.                                                                         |
| Object fileKey()            | Returns an object that uniquely identifies the given file, or null if a file key is not available. |
| boolean isDirectory()       | Tells whether the file is a directory.                                                             |
| boolean isOther()           | Tells whether the file is something other than a regular file, directory, or symbolic link.        |
| boolean isRegularFile()     | Tells whether the file is a regular file with opaque content.                                      |
| boolean isSymbolicLink()    | Tells whether the file is a symbolic link.                                                         |
| FileTime lastAccessTime()   | Returns the time of last access.                                                                   |
| FileTime lastModifiedTime() | Returns the time of last modification.                                                             |
| long size()                 | Returns the size of the file (in bytes).                                                           |