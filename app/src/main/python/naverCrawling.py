import requests
from bs4 import BeautifulSoup

def naver_crawling(sentence):
    def naver_search(url):
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

    url = 'https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query={search}'.format(search=sentence)

    return naver_search(url)