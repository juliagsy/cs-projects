#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ADD 1
#define UPDATE 2
#define DELETE 3
#define SEARCH 4
#define DISPLAY 5
#define SORT 6
#define SAVE 7
#define LOAD 8

#define NAME 1
#define BARCODE 2
#define PRICE 3
#define STOCK 4

#define INSTOCK 1
#define OUTOFSTOCK 2
#define CUSTOMISED 3
#define ALL 4

#define HIGHTOLOW 1
#define LOWTOHIGH 2

#define EXIT 9

#define MAXLENGTH 100
#define MAXSPACE 1000

typedef struct item
{
  char *name;
  char *barcode;
  char *price;
  char *stock;
} Item;

Item *inventory[MAXSPACE];
char *choicesDetails[] = {"Name","Barcode","Price","Stock"};
char *getInput(char*);
int getSelection(char*);
void freeAll(char*,char*,char*,char*);
void removeNewLine(char*);
void printItemDetails(int);
char *chooseList(int,int);
void autoSave(void);
char *checkDuplicate(int,char*);


void intro(void);
void descriptionMain(void);
void chooseMainKey(int);
void addItem(void);
Item *createList(char*,char*,char*,char*);
void functionMenu(int);
void searchMenu(void);
void chooseSearchKey(int,int);
void searchTool(char*,int,int);
void redirect(int,int);
void descriptionUpdate(int);
void chooseUpdateKey(int,int);
void updateDetail(char*,char*);
void actionRemove(int);
void displayMenu(void);
void chooseDisplayKey(int);
void displayAll(int);
void customisedMenu(void);
void chooseCustomisedKey(int);
void displayCategory(char*,int);
void sortingMenu(void);
void chooseSortKey(int);
void sortingType(int,int);
void sortProceed(int,int);
void reverseSort(int);
void flipDetails(int,int);
void saveMenu(void);
void saveFunction(char*);
void loadMenu(void);


char *getInput(char *ques)
{
  char *ans;
  while(1)
  {
    char input[MAXLENGTH];
    printf("%s",ques);
    fgets(input, MAXLENGTH,stdin);
    int length = strlen(input);
    ans = calloc(sizeof(char), length);
    strncpy(ans,input,length-1);
    ans[length-1] = '\0';
    if (!strcmp(ans,"\0"))
    {
      printf("Please enter something.\n\n");
    }
    else
    {
      break;
    }
  }
  return ans;
}

int getSelection(char *ques)
{
  int key = 0;
  printf("\n");
  char *keyStr = getInput(ques);
  printf("\n");
  sscanf(keyStr,"%d",&key);
  free(keyStr);
  return key;
}

void freeAll(char*name,char*barcode,char*price,char*stock)
{
  free(name);
  free(barcode);
  free(price);
  free(stock);
  return;
}

void removeNewLine(char *target)
{
  int length = strlen(target);
  if (length == 0)
  {
    return;
  }
  if (target[length-1] == '\n')
  {
    target[length-1] = '\0';
  }
  return;
}

void printItemDetails(int index)
{
  printf("Name: %s\nBarcode: %s\nPrice: %s\nStock: %s\n\n",
          inventory[index]->name,inventory[index]->barcode,
          inventory[index]->price,inventory[index]->stock);
  return ;
}

char *chooseList(int key, int index) // finds targeted item from inventory
{
  int offset = key - 1;
  char **list[] = {&inventory[index]->name,&inventory[index]->barcode,
                  &inventory[index]->price,&inventory[index]->stock};
  char *ans = *list[offset];
  return ans;
}

void autoSave(void)
{
  saveFunction("backup");
  printf("Autosave completed!\n\n");
  return ;
}

char *checkDuplicate(int key, char *question)
{
  int index;
  char *exist, *newDetail;
  while (1)
  {
    printf("%s ",choicesDetails[key-1]);
    newDetail = getInput(question);
    for (index=0;inventory[index]!=NULL;index++)
    {
      exist = chooseList(key,index);
      if (!strcmp(newDetail,exist))
      {
        printf("%s existed or same as previous. Please enter a different one.\n\n"
                ,choicesDetails[key-1]);
        break;
      }
    }
    if (inventory[index] == NULL)
    { break ; }
  }
  return newDetail ;
}


void intro(void)
{
  while (1)
  {
    descriptionMain();
    int key = getSelection("Selection is: ");
    if (key == EXIT)
    {
      if (inventory[0]!=NULL)
      { autoSave(); }
      printf("Goodbye!\n\n");
      break ;
    }
    else
    {
      chooseMainKey(key);
    }
  }
  return ;
}

void descriptionMain(void)
{
  printf("- MAIN MENU -\n");
  printf("%d - Input New Item\n", ADD);
  printf("%d - Update Existing Item\n",UPDATE);
  printf("%d - Delete Item\n",DELETE);
  printf("%d - Search For An Item\n",SEARCH);
  printf("%d - Display Partial or Full Inventory List\n",DISPLAY);
  printf("%d - Sort Inventory List\n",SORT);
  printf("%d - Save Current List\n",SAVE);
  printf("%d - Load Saved List\n",LOAD);
  printf("%d - Exit Program\n", EXIT);
  return ;
}

void chooseMainKey(int key)
{
  switch (key)
  {
    case ADD:
      addItem();
      break;

    case UPDATE:
    case DELETE:
    case SEARCH:
      functionMenu(key);
      break;

    case DISPLAY:
      displayMenu();
      break;

    case SORT:
      sortingMenu();
      break;

    case SAVE:
      saveMenu();
      break;

    case LOAD:
      loadMenu();
      break;

    default:
      printf("Invalid function. Please enter a valid key.\n\n");
  }
  return ;
}

void addItem(void)
{
  int index;
  for (index=0;inventory[index]!=NULL;index++)
  {
    if (index > MAXSPACE - 3)
    {
      printf("Inventory is full.\n\n");
      return ;
    }

  }
  printf("- INPUT FUNCTION -\n");
  char *name = checkDuplicate(NAME,"of item: ");
  char *barcode = checkDuplicate(BARCODE,"of item: ");
  char *price = getInput("Price is: ");
  char *stock = getInput("Stock is: ");
  Item *list = createList(name,barcode,price,stock);
  inventory[index] = list;
  inventory[index+1] = NULL;
  printf("Item added successfully!\n\n");
  freeAll(name,barcode,price,stock);
  autoSave();
  return ;
}

Item *createList(char*name,char*barcode,char*price,char*stock)
{
  Item *item = malloc(sizeof(Item));
  item->name = calloc(sizeof(char),strlen(name)+1);
  item->barcode = calloc(sizeof(char),strlen(barcode)+1);
  item->price = calloc(sizeof(char),strlen(price)+1);
  item->stock = calloc(sizeof(char),strlen(stock)+1);
  strcpy(item->name,name);
  strcpy(item->barcode,barcode);
  strcpy(item->price,price);
  strcpy(item->stock,stock);
  return item;
}

void functionMenu(int source)
{
  char *sourcetype[] = {"UPDATE", "DELETE", "SEARCH"};
  int index = source - 2;
  printf("- %s FUNCTION -\n\n**To return, please click any other key.\n",sourcetype[index]);
  searchMenu();
  int key = getSelection("Selection is: ");
  chooseSearchKey(key, source);
  return ;
}

void searchMenu(void)
{
  printf("Search by:-\n");
  printf("%d - Name of item\n", NAME);
  printf("%d - Barcode of item\n",BARCODE);
  return;
}

void chooseSearchKey(int key, int purpose)
{
  char *selection;
  if (key >= NAME && key <= BARCODE)
  {
    int index = key - 1;
    printf("%s ",choicesDetails[index]);
    selection = getInput("is: ");
    searchTool(selection,key,purpose);
  }

  return ;
}

void searchTool(char *target, int source, int purpose)
{
  int index;
  char *item;
  for (index=0;inventory[index]!= NULL; index++)
  {
    item = chooseList(source,index);
    if (!strcmp(item,target))
    {
      printf("\nItem found!\n");
      printItemDetails(index);
      redirect(purpose,index);
      break ;
    }
  }
  if (inventory[index] == NULL)
  {
    printf("Invalid item.\n\n");
  }
  return ;
}

void redirect(int purpose, int index) // trigger further features for Update and Delete
{
  if (purpose == UPDATE)
  {
    descriptionUpdate(index);
  }
  else if (purpose == DELETE)
  {
    actionRemove(index);
  }
  return ;
}

void descriptionUpdate(int index)
{
  printf("Select category to update:-\n\n**To return, please click any other key.\n");
  printf("%d - Name of item\n", NAME);
  printf("%d - Barcode of item\n",BARCODE);
  printf("%d - Price of item\n",PRICE);
  printf("%d - Stock Available\n",STOCK);
  int key = getSelection("Selection is: ");
  chooseUpdateKey(key,index);
  return ;
}

void chooseUpdateKey(int key, int index)
{
  char *newDetail;
  char *item = chooseList(key,index);
  char *ques = "(new) is: ";
  if (key == NAME || key == BARCODE)
  {
    newDetail = checkDuplicate(key,ques);
  }
  else if (key == PRICE || key == STOCK)
  {
    printf("%s ",choicesDetails[key-1]);
    newDetail = getInput(ques);
  }
  else
  { return ; }
    updateDetail(item,newDetail);
    free(newDetail);
  return ;
}

void updateDetail(char *old,char *new)
{
  free(old);
  old = calloc(sizeof(char),strlen(new)+1);
  strcpy(old,new);
  printf("Updated successfully!\n\n");
  autoSave();
  return ;
}

void actionRemove(int index)
{
  freeAll(inventory[index]->name,inventory[index]->barcode,
          inventory[index]->price,inventory[index]->stock);
  while(inventory[index]!=NULL)
  {
    inventory[index] = inventory[index+1];
    index ++;
  }
  free(inventory[index]);
  printf("Deleted successfully!\n\n");
  autoSave();
  return;
}

void displayMenu(void)
{
  printf("- DISPLAY FUNCTION -\n\n**To return, please click any other key.\n");
  printf("%d - Display In Stock Items\n", INSTOCK);
  printf("%d - Display Out of Stock Items\n", OUTOFSTOCK);
  printf("%d - Display Items of Customised Price or Stock\n", CUSTOMISED);
  printf("%d - Display All Items\n", ALL);
  int key = getSelection("Selection is: ");
  chooseDisplayKey(key);
  return ;
}

void chooseDisplayKey(int key)
{
  switch (key)
  {
    case INSTOCK:
    case ALL:
      displayAll(key);
      break;

    case OUTOFSTOCK:
      displayCategory("0",STOCK);
      break;

    case CUSTOMISED:
      customisedMenu();
      break;

    default:
      return ;
  }
  return ;
}

void displayAll(int type)
{
  int index, result;
  int times = 1;
  for (index=0;inventory[index]!=NULL;index++)
  {
    if (type == INSTOCK)
    {
      result = strcmp(inventory[index]->stock,"0");
      if (result == 0)
      { continue; }
    }
    printf("Entry %d:\n",times);
    printItemDetails(index);
    times ++;
  }
  if (times == 1)
  {
    printf("Empty Inventory.\n");
  }
  printf("- END -\n\n");
  return ;
}

void customisedMenu(void)
{
  printf("Customise by:-\n\n**To return, please click any other key.\n");
  printf("%d - Price of item\n",PRICE);
  printf("%d - Stock Available\n",STOCK);
  int key = getSelection("Selection is: ");
  chooseCustomisedKey(key);
  return ;
}

void chooseCustomisedKey(int key)
{
  char *target;
  int offset = key - 1;
  if (key >= PRICE && key <= STOCK)
  {
    printf("%s ",choicesDetails[offset]);
    target = getInput("to be searched is: ");
    displayCategory(target, key);
  }
  return ;
}

void displayCategory(char *target, int key)
{
  int index;
  char *item;
  int times = 1;
  for (index=0;inventory[index]!=NULL;index++)
  {
    item = chooseList(key,index);
    if (!strcmp(item, target))
    {
      printf("Entry %d:-\n", times);
      printItemDetails(index);
      times ++;
    }
  }
  if (times == 1)
  {
    printf("Target has zero entry.\n");
  }
  printf("- END -\n\n");
  return ;
}

void sortingMenu(void)
{
  printf("- SORT FUNCTION -\n**To return, please click any other key.\n\nSort by:-\n");
  printf("%d - Name\n",NAME);
  printf("%d - Price\n",PRICE);
  printf("%d - Stock\n",STOCK);
  int key = getSelection("Selection is: ");
  chooseSortKey(key);
  return ;
}

void chooseSortKey(int key)
{
  int sequence;
  if (key == NAME || key == PRICE || key == STOCK)
  {
    printf("Sequence of:-\n");
    printf("%d - High to Low (descending)\n",HIGHTOLOW);
    printf("%d - Low to High (ascending)\n",LOWTOHIGH);
    sequence = getSelection("Selection is: ");
    sortingType(key,sequence);
  }
  return ;
}

void sortingType(int key, int sequence)
{
  if (sequence >= HIGHTOLOW && sequence <= LOWTOHIGH)
  {
    sortProceed(key, sequence);
  }
  return ;
}

void sortProceed(int key, int sequence)
{
  if (inventory[0] == NULL)
  {
    printf("Sorting failed due to empty list.\n\n");
    return;
  }

  int index, newIndex, minIndex;
  char *item, *min;
  for (newIndex=0;inventory[newIndex]!=NULL;newIndex++)
  {
    minIndex = newIndex;
    min = chooseList(key,minIndex);
    for(index=newIndex+1;inventory[index]!=NULL;index++)
    {
      item = chooseList(key,index);
      if(strcmp(item,min) < 0)
      {
        minIndex = index;
        min = chooseList(key,minIndex);
      }
    }
    flipDetails(newIndex,minIndex);
  }
  if (sequence == HIGHTOLOW)
  {
    reverseSort(newIndex-1);
  }
  printf("Sorting successful!\n\n");
  autoSave();
  return ;
}

void reverseSort(int lastIndex)
{
  int index=0;
  int difference = lastIndex - index;
  while (1)
  {
    if (difference < 1)
    { break; }
    flipDetails(index,lastIndex);
    index ++ ;
    lastIndex -- ;
    difference = lastIndex - index;
  }
  return ;
}

void flipDetails(int index, int flipIndex)
{
  Item *buffer = malloc(sizeof(Item));
  buffer = inventory[index];
  inventory[index] = inventory[flipIndex];
  inventory[flipIndex] = buffer;
  return;
}

void saveMenu(void)
{
  printf("- SAVE FUNCTION -\n\n**To return, please input %d\n",EXIT);
  char *fileName = getInput("Enter the file name: ");
  if (atoi(fileName) == EXIT)
  {
    return ;
  }
  saveFunction(fileName);
  printf("Save completed!\n\n");
  free(fileName);
  return ;
}

void saveFunction(char *fileName)
{
  int index;
  FILE *savingFile = fopen(fileName,"w");
  for (index=0;inventory[index]!=NULL;index++)
  {
    fprintf(savingFile, "%s\n", inventory[index]->name);
    fprintf(savingFile, "%s\n", inventory[index]->barcode);
    fprintf(savingFile, "%s\n", inventory[index]->price);
    fprintf(savingFile, "%s\n", inventory[index]->stock);
  }
  fclose(savingFile);
  return ;
}

void loadMenu(void)
{
  int index;
  char name[MAXLENGTH],barcode[MAXLENGTH],price[MAXLENGTH],stock[MAXLENGTH];
  printf("- LOAD FUNCTION -\n\n**To exit, please input %d\n",EXIT);
  char *fileName = getInput("Enter the file name: ");
  if (atoi(fileName) == EXIT)
  {
    return ;
  }
  FILE *loadingFile = fopen(fileName,"r");
  if (loadingFile == NULL)
  {
    printf("\nUnable to read due to empty file\n");
    free(fileName);
    return;
  }
  for (index=0;index!=MAXSPACE-3;index++)
  {
    char *loading = fgets(name,MAXLENGTH-1,loadingFile);
    if (loading == NULL)
    {
      break;
    }
    fgets(barcode,MAXLENGTH-3,loadingFile);
    fgets(price,MAXLENGTH-3,loadingFile);
    fgets(stock,MAXLENGTH-3,loadingFile);
    removeNewLine(name);
    removeNewLine(barcode);
    removeNewLine(price);
    removeNewLine(stock);
    inventory[index] = createList(name, barcode, price, stock);
  }
  inventory[index] = NULL;
  fclose(loadingFile);
  printf("Loaded successfully!\n\n");
  free(fileName);
  return;
}


int main(void)
{
  printf("- Inventory Program -\n");
  inventory[0] = NULL;
  intro();
  return 0;
}
