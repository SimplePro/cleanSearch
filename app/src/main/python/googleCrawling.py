import requests
from bs4 import BeautifulSoup

def google_crawling(sentence):
    def google_search(url):
        html = requests.get(url)
        soup = BeautifulSoup(html.text, "html.parser")
        script = soup.find_all('body')

        content = []
        for string in script:
            result = string.get_text(" ", True)
            content.append(result)
        return content

    search_url = 'https://www.google.com/search?q={search}&oq={search}&aqs=chrome..69i57j69i59l2j0l2j69i60l3.1957j0j4&sourceid=chrome&ie=UTF-8'.format(search=sentence)
    return google_search(search_url)