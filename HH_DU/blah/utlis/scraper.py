import requests 
from bs4 import BeautifulSoup
import re
import json
import urlparse
import duckduckgo
import random
from .nlp import tokens

GOOGLE_URL = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCS3e1_sTWaczMI1aGgxHaq7_NKJO-hnWo&cx=002365234770750778602:wapboucdge0&q="
TWITTER_URL = "https://twitter.com/hashtag/"
YOUTUBE_URL = "https://www.youtube.com/results?search_query="
REDDIT_URL = "https://www.reddit.com/search?restrict_sr=&sort=top&t=month&q="

def response_render(temp):
	
	data = tokens(temp)

	query = []
	query.append(twitter_scraper(data,temp))
	query.append(reddit_scraper(data,temp))
	temp_1, temp_2 = youtube_scraper(data)
	temp_3, temp_4 = google_scraper(temp)
	query.append(temp_1)
	query.append(temp_3)
	query.append(temp_2)
	query.append(temp_4)
	
	return query


'''def scraper_google(data):
	
		URL = GOOGLE_URL
		for ix in data:
			URL = URL + ix + "+"
		scraper_r = requests.get(URL)	
	
	#scraper_google(['stoner','memes'])'''

def scraper_duckduckgo(temp):
	urls = []
	count = 1
	for link in duckduckgo.search(temp, max_results=8):
		if count%4==0:
			urls.append(link)
			count += 1
		else:
			count += 1
	return urls

def google_scraper(temp):
	link = scraper_duckduckgo(temp)
	
	ID = "kaisahai" + str(random.random())
	url_1 = link[0]
	image = "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSlJ6i0MqP20vfAwE1bCofPt6bKDQ27NA_GUrtR28GYHq1Xf67P"
	title_1 = link[0].split('/')[2]
	source_1 = link[0].split('/')[2]
	temp_1 = {'url' : url_1, 'imageURL': image, 'title':title_1,'source':source_1, 'id':ID}

	ID = "kaisahai" + str(random.random())
	url_2 = link[1]
	image = "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSlJ6i0MqP20vfAwE1bCofPt6bKDQ27NA_GUrtR28GYHq1Xf67P"
	title_2 = link[1].split('/')[2]
	source_2 = link[1].split('/')[2]
	temp_2 = {'url' : url_1, 'imageURL': image, 'title':title_1,'source':source_1, 'id':ID}

	return temp_1,temp_2 


def twitter_scraper(data,xyz):

	URL = TWITTER_URL
	for ix in data:
		URL = URL + ix
	URL = URL + '?src=tren'
	
	ID = "kaisahai" + str(random.random())

	dict_1 = dict()
	IMAGE = 'http://blog.larrybodine.com/uploads/image/Twitter%20logo.jpg'
	title = "twitter : " + str(xyz)
	source = 'www.twitter.com'
	temp = {'url' : URL, 'imageURL': IMAGE, 'title':title,'source':source, 'id':ID}

	return temp

def reddit_scraper(data,xyz):

	URL = REDDIT_URL
	for ix in data:
		URL = URL + ix + "+"
	
	ID = "kaisahai" + str(random.random())
	
	dict_1 = dict()
	IMAGE = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlv1VgA2QYZX77wNFki8h0kM50fFkAp8hj6nA5psajBXan5_Vk'
	title = "reddit : " + str(xyz)
	source = 'www.reddit.com'
	temp = {'url' : URL, 'imageURL': IMAGE, 'title':title,'source':source, 'id':ID}

	return temp

def youtube_scraper(data):

	URL = YOUTUBE_URL
	for ix in data:
		URL = URL + ix + "+"

	youtube_r = requests.get(URL)
	youtube_soup = BeautifulSoup(youtube_r.text,'html.parser')

	video_links = []
	titles = []

	for link in youtube_soup.findAll('a'):
		if re.findall('^/watch?.+', link.get('href')):
			video_links.append(link.get('href'))
			titles.append(link.get('title'))


	ID = "kaisahai" + str(random.random())
	dict_1 = dict()
	url_1 = 'https://www.youtube.com' + video_links[1]
	IMAGE_1 = "http://cdn2.hubspot.net/hub/69576/file-17500656-png/images/you-tube-logo.png?t=1364738137000"
	title_1 = titles[1]
	source = 'www.youtube.com'
	temp_1 = {'url' : url_1, 'imageURL': IMAGE_1, 'title':title_1,'source':source , 'id':ID}

	ID = "kaisahai" + str(random.random())
	dict_2 = dict()
	url_2 = 'https://www.youtube.com' + video_links[3]
	IMAGE_2 = "http://cdn2.hubspot.net/hub/69576/file-17500656-png/images/you-tube-logo.png?t=1364738137000"
	title_2 = titles[3]	
	source = 'www.youtube.com'
	temp_2 = {'url' : url_2, 'imageURL': IMAGE_2, 'title':title_2,'source':source, 'id':ID}

	return temp_1,temp_2