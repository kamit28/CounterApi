This API reads the source text file and builds a frequency table on initialization and provides following API endpoints to work on that data:
1. 
2. 

<ol>
<li>
/search: This method accepts a list of words to be searched and returns the count of occurances of each word in the text. This endpoint can be accessed using curl command as follows:
<p>curl http://localhost:8080/counter-api/search -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -d'{"searchText":["Duis", "Sed", "Donec", "Augue", "Pellentesque", "123"]}' -H"Content-Type: application/json" -X POST</p>
</li>
<li>
/top/{num}: This method returns the top ${num} frequently occuring words in the text in pipe-delemitted format. This endpoint can be accessed using curl command as follows:
<p>curl http://localhost:8080/counter-api/top/20 -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" -H"Accept: text/plain"</p>
</li>
</ol>