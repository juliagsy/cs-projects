#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tools.h"
#include "tagFinder.h"
#include "parser.h"

FILE* htmlFile;
int valid;
char* error = "";

void setError(char* tagname, int notError)
{
  if (!notError && (strcmp(error,"") == 0))
  {
    error = tagname;
  }
}

void parse()
{
  valid = 0;
  if (skipWhiteSpace(htmlFile) == '<')
  {
    if (findHtml())
    {
      valid = (skipWhiteSpace(htmlFile) == EOF);
    }
  }
  setError("HTML",valid);
}

int findHtml()
{
  char current = fgetc(htmlFile);
  valid = findOpenTag(htmlFile,"html",current);
  if (valid && findHead() && findBody())
  {
    valid = (getFirstChar(htmlFile) == '/' && findCloseTag(htmlFile,"html"));
  }
  setError("HTML",valid);
  return valid;
}

int findHead()
{
  char current = getFirstChar(htmlFile);
  valid = findOpenTag(htmlFile,"head",current);
  current = getFirstChar(htmlFile);
  if (valid && current != '/' && findTitle(current))
  {
    current = getFirstChar(htmlFile); // current expected to equal '/'
  }
  valid = findCloseTag(htmlFile,"head"); // if last condition failed, this will not be valid
  setError("Head",valid);
  return valid;
}

int findTitle(char current)
{
  valid = findOpenTag(htmlFile,"title",current);
  valid = (getFirstChar(htmlFile) == '/' && findCloseTag(htmlFile,"title"));
  setError("Title",valid);
  return valid;
}

int findBody()
{
  char current = getFirstChar(htmlFile);
  valid = findOpenTag(htmlFile,"body",current);
  current = getFirstChar(htmlFile);
  while (valid && current != '/' && current != EOF)
  {
    valid = findOptionalBodyDiv(current);
    current = getFirstChar(htmlFile);
  }
  valid = (valid && findCloseTag(htmlFile,"body")); // if the last condition is valid only proceed
  setError("Body",valid);
  return valid;
}

int findOptionalBodyDiv(char current)
{
  if (isWhiteSpace(current))
  {
    current = skipWhiteSpace(htmlFile);
  }
  char* nextTagname = getNextWord(htmlFile,&current);
  valid = (current == '>');
  if (valid)
  {
    if (strcmp(nextTagname,"a") == 0)
    {
      current = getFirstChar(htmlFile); // skip details and get next tagname[0] or '/'
      valid = findCloseTag(htmlFile,"a");
      setError("a (anchor)",valid);
    }
    else if (strcmp(nextTagname,"ul") == 0)
    {
      current = getFirstChar(htmlFile); // skip details and get next tagname[0] or '/'
      valid = findUl(current);
    }
    else if (strcmp(nextTagname,"h1") == 0 || strcmp(nextTagname,"h2") == 0 || strcmp(nextTagname,"h3") == 0)
    {
      current = getFirstChar(htmlFile); // skip details and get next tagname[0] or '/'
      valid = findH(current,nextTagname);
    }
    else if (strcmp(nextTagname,"p") == 0)
    {
      current = getFirstChar(htmlFile); // skip details and get next tagname[0] or '/'
      valid = findP(current);
    }
    else if (strcmp(nextTagname,"div") == 0)
    {
      current = getFirstChar(htmlFile); // skip details and get next tagname[0] or '/'
      valid = findDiv(current);
    }
    else if (strcmp(nextTagname,"hr") == 0 || strcmp(nextTagname,"br") == 0)
    {
      valid = 1;
      setError(nextTagname,valid);
    }
    else
    {
      valid = 0;
      setError("Invalid or wrongly-nested",valid);
    }
  }
  return valid;
}

int findUl(char current)
{
  while (valid && current != '/' && current != EOF)
  {
    valid = findLi(current);
    current = getFirstChar(htmlFile);
  }
  valid = (valid && findCloseTag(htmlFile,"ul"));
  setError("ul",valid);
  return valid;
}

int findLi(char current)
{
  valid = findOpenTag(htmlFile,"li",current);
  current = getFirstChar(htmlFile);
  while (valid && current != '/' && current != EOF)
  {
    valid = findOptionalLi(current);
    current = getFirstChar(htmlFile);
  }
  valid = (valid && findCloseTag(htmlFile,"li"));
  setError("li",valid);
  return valid;
}

int findOptionalLi(char current)
{
  if (isWhiteSpace(current))
  {
    current = skipWhiteSpace(htmlFile);
  }
  char* nextTagname = getNextWord(htmlFile,&current);
  valid = (current == '>');
  if (valid)
  {
    current = getFirstChar(htmlFile); // skip details and get next tagname[0] or '/'
    if (strcmp(nextTagname,"a") == 0)
    {
      valid = findCloseTag(htmlFile,"a");
      setError("a (anchor)",valid);
    }
    else if (strcmp(nextTagname,"p") == 0)
    {
      valid = findP(current);
    }
    else
    {
      valid = 0;
      setError("Invalid or wrongly-nested",valid);
    }
  }
  return valid;
}

int findP(char current)
{
  while (valid && current != '/' && current != EOF)
  {
    valid = findOptionalP(current);
    current = getFirstChar(htmlFile);
  }
  valid = (valid && findCloseTag(htmlFile,"p"));
  setError("p (paragraph)",valid);
  return valid;
}

int findOptionalP(char current)
{
  if (isWhiteSpace(current))
  {
    current = skipWhiteSpace(htmlFile);
  }
  char* nextTagname = getNextWord(htmlFile,&current);
  valid = (current == '>');
  if (valid)
  {
    if (strcmp(nextTagname,"a") == 0)
    {
      current = getFirstChar(htmlFile); // skip details and get next tagname[0] or '/'
      valid = findCloseTag(htmlFile,"a");
      setError("a (anchor)",valid);
    }
    else if (strcmp(nextTagname,"br") == 0)
    {
      valid = 1;
      setError("br",valid);
    }
    else
    {
      valid = 0;
      setError("Invalid or wrongly-nested",valid);
    }
  }
  return valid;
}

int findH(char current, char* tagname)
{
  while (valid && current != '/' && current != EOF)
  {
    valid = findOptional(htmlFile,"a",current);
    current = getFirstChar(htmlFile);
  }
  valid = (valid && findCloseTag(htmlFile,tagname));
  setError(tagname,valid);
  return valid;
}

int findDiv(char current)
{
  while (valid && current != '/' && current != EOF)
  {
    valid = findOptionalBodyDiv(current);
    current = getFirstChar(htmlFile);
  }
  valid = (valid && findCloseTag(htmlFile,"div"));
  setError("Div",valid);
  return valid;
}

int main()
{
  htmlFile = fopen("./file.html","r");
  if (htmlFile == NULL)
  {
    printf("File loading error\n");
    exit(1);
  }

  parse();

  if (valid)
  {
    printf("HTML is valid.\n");
    return 0;
  }
  printf("%s tag error occurred!\n", error);
  return 0;
}
