import requests 
from bs4 import BeautifulSoup
import re
import json

BASE_URL = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCS3e1_sTWaczMI1aGgxHaq7_NKJO-hnWo&cx=002365234770750778602:wapboucdge0&q="
https://www.googleapis.com/customsearch/v1?q=fuck+this+shit&cx=002365234770750778602%3Awapboucdge0&key={YOUR_API_KEY}

def scraper_google(dummy):
	URL = BASE_URL
	for ix in dummy:
		URL = URL + ix + "+"

	scraper_r = requests.get(URL)	

scraper_google(['stoner','memes'])	