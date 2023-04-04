#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tools.h"
#include "tagFinder.h"

int findTagName(FILE* file, char* tagname)
{
  int length = strlen(tagname);
  int index;
  for (index=1;index<length;index++)
  {
    if (fgetc(file) != tagname[index])
    {
      break;
    }
  }
  return (index == length);
}

int findOpenTag(FILE* file, char* tagname, char current) // find open tag and skip all attributes and details
{
  if (isWhiteSpace(current))
  {
    current = skipWhiteSpace(file);
  }
  if (current == tagname[0] && findTagName(file,tagname)) // if format is wrong, tagname will not be found
  {
    current = fgetc(file);
    return (current == '>' || (current == ' ' && skipContents(file) == '>')); // <tagname> or <tagname "att">
  }
  return FALSE;
}

int findCloseTag(FILE* file, char* tagname) // find close tag and skip all details
{
  char current = skipWhiteSpace(file); // now == tagname[0]
  if (current == tagname[0] && findTagName(file,tagname)) // if format is wrong, tagname will not be found
  {
    current = fgetc(file);
    return (current == '>' || (current == ' ' && skipWhiteSpace(file) == '>'));
  }
  return FALSE;
}

int findOptional(FILE* file, char* tagname, char current)
{
  if (findOpenTag(file,tagname,current))
  {
    current = skipContents(file);
    return findCloseTag(file,tagname);
  }
  return FALSE;
}
