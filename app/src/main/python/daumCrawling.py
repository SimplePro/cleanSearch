import requests
from bs4 import BeautifulSoup

def daum_crawling(sentence) :

    def daum_search(url):
        try:
            html = requests.get(url)
            soup = BeautifulSoup(html.text, "html.parser")

            script = soup.find_all('body')

            content = []
            for string in script:
                result = string.get_text(" ", True)
                content.append(result)
            return content
        except:
            return "error"

    url = 'https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&sq=&o=&q={search}'.format(search=sentence)

    return daum_search(url)