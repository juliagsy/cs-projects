main = do
  let file = "file.html"
  contents <- readFile file
  let tagList = parse contents
  let result = checkHtml tagList
  if result == [] then putStrLn "HTML is valid." else do
    let errorTag = getErrorTag result
    if errorTag == "" then putStr "Invalid" else putStr errorTag
    putStrLn " tag error occurred!"

skipWhiteSpace :: String -> String
skipWhiteSpace [] = ""
skipWhiteSpace (x:xs) = if (x == ' ') || (x == '\n') || (x =='\t') then skipWhiteSpace xs else (x:xs)

skipContents :: String -> String
skipContents [] = ""
skipContents (x:xs) = if (x /= '>') then skipContents xs else xs

getErrorTag :: [String] -> String
getErrorTag (x:xs) = if x == "false" then getErrorTag xs else x

getTag :: String -> String
getTag (x:xs) = if (x /= ' ') && (x /= '>') then ([x] ++ getTag xs) else ""

findCloseTag :: String -> [String] -> [String]
findCloseTag target (x:xs) = if x == target then xs else ["false"] ++ (x:xs)

parse :: String -> [String]
parse text = filterTags [] (skipWhiteSpace text)

filterTags :: [String] -> String -> [String]
filterTags [] (x:xs) = if (x /= '<') then ["false","html"] else filterTags [getTag xs] $ skipContents xs -- list is empty, html tag must be the first
filterTags y "" = y -- no more tags
filterTags y (x:xs) = if (x /= '<') then filterTags y xs else do
  let nextTag = getTag xs
  let nextText = skipContents xs
  if nextTag /= "/html" then filterTags (y ++ [nextTag]) nextText else do
     if skipWhiteSpace nextText == "" then y ++ [nextTag] else y ++ [nextTag] ++ [skipWhiteSpace nextText]
     -- last tag must be /html, else getErrorTag will parse error

checkHtml :: [String] -> [String]
checkHtml (x:xs) = if x == "html" then findCloseTag "/html" $ checkBodyDiv "body" $ checkHead xs else ["html"] ++ (x:xs)

checkHead :: [String] -> [String]
checkHead (x:xs) = if x == "head" then findCloseTag "/head" $ checkOptionalTitle xs else ["head"] ++ (x:xs)

checkOptionalTitle :: [String] -> [String]
checkOptionalTitle (x:xs) = if x == "title" then findCloseTag "/title" xs else (x:xs)

checkBodyDiv :: String -> [String] -> [String]
checkBodyDiv target (x:xs) = if x /= target then ["false"] ++ (x:xs) else findCloseTag ("/" ++ target) $ checkOptionalBodyDiv xs

checkOptionalBodyDiv :: [String] -> [String]
checkOptionalBodyDiv (x:xs) = do
  case x of
    "a" -> checkOptionalBodyDiv $ findCloseTag "/a" xs
    "ul" -> checkOptionalBodyDiv $ checkUl xs
    "h1" -> checkOptionalBodyDiv $ checkH x xs
    "h2" -> checkOptionalBodyDiv $ checkH x xs
    "h3" -> checkOptionalBodyDiv $ checkH x xs
    "p" -> checkOptionalBodyDiv $ checkP xs
    "div" -> checkOptionalBodyDiv $ checkBodyDiv "div" (x:xs)
    "hr" -> checkOptionalBodyDiv xs
    "br" -> checkOptionalBodyDiv xs
    otherwise -> (x:xs)

checkH :: String -> [String] -> [String]
checkH current (x:xs) = findCloseTag ("/"++current) $ checkOptionalH (x:xs)

checkOptionalH :: [String] -> [String]
checkOptionalH (x:xs) = if x == "a" then checkOptionalH $ findCloseTag "/a" xs else (x:xs)

checkP :: [String] -> [String]
checkP (x:xs) = findCloseTag "/p" $ checkOptionalP (x:xs)

checkOptionalP :: [String] -> [String]
checkOptionalP (x:xs) = do
  case x of
    "a" -> checkOptionalP $ findCloseTag "/a" xs
    "br" -> checkOptionalP xs
    otherwise -> (x:xs)

checkUl :: [String] -> [String]
checkUl (x:xs) = if x == "li" then checkUl $ checkLi xs else findCloseTag "/ul" (x:xs)

checkLi :: [String] -> [String]
checkLi (x:xs) = findCloseTag "/li" $ checkOptionalLi (x:xs)

checkOptionalLi :: [String] -> [String]
checkOptionalLi (x:xs) = do
  case x of
    "a" -> checkOptionalLi $ findCloseTag "/a" xs
    "p" -> checkOptionalLi $ checkP xs
    otherwise -> (x:xs)
