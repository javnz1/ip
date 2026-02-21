# SaitamaSensei project template

This is a project template for a greenfield Java project. It's named after the One Punch Man anime _Saitama_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 17** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/saitama/Launcher.java` file, right-click it, and choose `Run Launcher.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
   Welcome to SaitamaSensei Taskbot. I'm Saitama. I'm a hero for fun.
   What's on your schedule today? Here's how to PUNCH in commands:
   👊 list
   👊 todo [description]
   👊 deadline [description] /by [dd-MM-yyyy HHmm]
   👊 event [description] /from [dd-MM-yyyy] /to [dd-MM-yyyy]
   👊 mark [task number in the list]
   👊 unmark [task number in the list]
   👊 find [task keyword]
   👊 schedule [dd-MM-yyyy]
   👊 delete [task number in the list]
   👊 bye"
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.
