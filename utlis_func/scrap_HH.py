import requests 
from bs4 import BeautifulSoup
import re

BASE_URL = "https://www.google.co.in/#q="

def scraper_google(dummy):
	URL = BASE_URL
	for ix in dummy:
		URL = URL + ix + "+"

	print URL	
	scraper_r = requests.get(URL)
	scraper_soup = BeautifulSoup(scraper_r.content)

	#links = []
	links = scraper_soup.findAll("a")
	for link in  scraper_soup.find_all("a",href=re.compile("(?<=/url\?q=)(htt.*://.*)")):
		print re.split(":(?=http)",link["href"].replace("/url?q=",""))

	for link in links:
		print link	
	

#scraper_google(['stoner','memes'])	

scraper_google(['fuck', 'this', 'shit'])		