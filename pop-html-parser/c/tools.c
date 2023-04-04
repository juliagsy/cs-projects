#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tools.h"

int isWhiteSpace(char c)
{
  return (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\v' || c == '\f');
}

char skipWhiteSpace(FILE* file) // returns first character encountered after skipping whitespace
{
  char next;
  do
  {
    next = fgetc(file);
  } while (isWhiteSpace(next));

  return  next;
}

char skipContents(FILE* file) // returns first character encountered after skipping letters or digits except angle brackets
{
  char next;
  do
  {
    next = fgetc(file);
  } while (next != EOF && next != '>' && next != '<');

  return next;
}

char* getNextWord(FILE* file, char* current)
{
  char* buffer = (char*) malloc(sizeof(char)); // start with string of length 1
  buffer[0] = *current;

  int count = 1;
  *current = fgetc(file);
  while (!isWhiteSpace(*current) && *current != '<' && *current != '>' && *current != EOF)
  {
    buffer = realloc(buffer,count+1);
    buffer[count] = *current;
    count ++;
    *current = fgetc(file);
  }
  if (isWhiteSpace(*current))
  {
    *current = skipContents(file); // skip attribute and complete open tag
  }
  return buffer;
}

char getFirstChar(FILE* file)
{
  char next = skipContents(file);
  return fgetc(file);
}
