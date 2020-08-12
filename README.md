# CounterApi

This API reads the source text file and builds a frequency table on initialization and provides following API endpoints to work on that data:
1. /search: This method accepts a list of words to be searched and returns the count of occurances of each word in the text.
2. /top/{num}: This method returns the top ${num} frequently occuring words in the text in pipe-delemitted format.
