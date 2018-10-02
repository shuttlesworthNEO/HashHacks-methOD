import requests 
from bs4 import BeautifulSoup
import re
import json

GOOGLE_URL = "https://www.googleapis.com/customsearch/v1?key="APIKEY"&q="

def scraper_google(dummy):
	url = GOOGLE_URL
	for ix in dummy:
		url = url + ix + "+"

	scraper_r = requests.get(url)	

scraper_google(['it', 'is', '420','somewhere'])	
