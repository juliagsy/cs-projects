1) Compiling and Run:
- go to the "src" directory in the "COMP0002CW1" folder where the source code
  file is in.
- compile the following : gcc inventory.c
- after compiling, run the application by: ./a.out

2) Description of Application:
  A) Introduction
- It is an inventory application used to organise the list of products.
- After the program is executed, a Main Menu page of the application will appear,
  showing that it is running.
- To access its functions, click on their respective numbers stated beside.

  B) Description on Each Feature
- #1 : Add new item or stock
- #2 : Update details (eg. name, barcode, price, stock) of existing item
- #3 : Remove unwanted item
- #4 : Search an item by name or barcode
- #5 : Display Full or Filtered List (with Price or Stock customisation)
- #6 : Sort the whole list by ascending or descending name, price or stock
- #7 : Save the inventory list
- #8 : Load previously saved inventory list
- #9 : Exit application

  C) Autosave Feature
- Autosave function will be triggered whenever changes are made (eg. adding, updating,
deleting, sorting) as well as before program is exited.
- Data will be saved in a file named "backup" in the same directory as source
code.
- To save the inventory list in your desired file name, use the Save Function (#7).

  D) Name and Barcode Checking Feature
- For "Adding New Item" or "Updating Existing Item", the new name or barcode entered
  will be compared with existing ones to check if it is repeated.
- Message will be shown if existed or same as its own previous name.

  E) Save Function Notes
- If the entered file name had existed in the directory, the file will be overwritten.

3) References:
- Phonebook Example by Professor Graham Roberts
- C File Handling https://www.programiz.com/c-programming/c-file-input-output
