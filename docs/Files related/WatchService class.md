```toc

```

A watch service that _watches_ registered objects for changes and events. For example a file manager may use a watch service to monitor a directory for changes so that it can update its display of the list of files when files are created or deleted. Very useful when working with [[Files Class in Java|Files]]

*[javaDoc](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/nio/file/WatchService.html)*

## Overview
```java
import java.nio.file.*;
```


- **Key Components:**
    - *WatchService:* The main interface for watching a directory. It's obtained by invoking the `newWatchService()` method on a `Path` object.
    - *WatchKey:* Represents a registration with a `WatchService`. It holds information about the events and the state of the directory being watched.
    
- **Events Monitored:**
    - *ENTRY_CREATE:* A file or directory is created in the watched directory.
    - *ENTRY_MODIFY:* A file in the watched directory is modified.
    - *ENTRY_DELETE:* A file or directory in the watched directory is deleted.
    - _**StandardWatchEventKinds.OVERFLOW**_ – triggered to indicate lost or discarded events. We won’t focus much on it

### When to Use WatchService

- **Real-Time Monitoring:** Use `WatchService` when you need to react in real-time to changes in a directory, such as in file synchronization or log file monitoring.
- **Cross-Platform File Watching:** `WatchService` provides a platform-independent way to monitor file system events.

## How Does the Watchservice Work?

To use the _WatchService_ features, the first step is to create a _WatchService_ instance using the _java.nio.file.FileSystems_ class:

```java
WatchService watchService = FileSystems.getDefault().newWatchService();
```

Register a Directory:
```java
Path directory = Paths.get("/path/to/watched/directory");
WatchKey key = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
```

Watch for Events:

We can use the _poll_ API:

```java
WatchKey watchKey = watchService.poll();
WatchKey watchKey = watchService.poll(long timeout, TimeUnit units); //at takes a _timeout_ argument:
//OR

WatchKey watchKey = watchService.take(); //h simply blocks until an event occurs.
```


> [!warning] Pay Attention!
> when the _WatchKey_ instance is returned by either of the _poll_ or _take_ APIs, it will not capture more events if it’s reset API is not invoked:

```java
watchKey.reset();
```

The most practical application of the watcher service would require a loop within which we continuously check for changes in the watched directory and process accordingly. We can use the following idiom to implement this:

```java
WatchKey key;
while ((key = watchService.take()) != null) {
    for (WatchEvent<?> event : key.pollEvents()) {
        //process
    }
    boolean valid = key.reset();  
if (!valid) {  
break;  
}
}
```

**Close the WatchService:**

```java
watchService.close();
```