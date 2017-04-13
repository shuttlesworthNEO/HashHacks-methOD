import requests 
from bs4 import BeautifulSoup
import re
import json
import urlparse
from .nlp import tokens

GOOGLE_URL = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCS3e1_sTWaczMI1aGgxHaq7_NKJO-hnWo&cx=002365234770750778602:wapboucdge0&q="
TWITTER_URL = "https://twitter.com/hashtag/"
YOUTUBE_URL = "https://www.youtube.com/results?sp=CAESAggC&q="
REDDIT_URL = "https://www.reddit.com/search?restrict_sr=&sort=top&t=month&q="

def response_render(temp):
	
	data = tokens(temp)

	query = []
	query.append(twitter_scraper(data,temp))
	query.append(reddit_scraper(data,temp))
	'''temp_1, temp_2 = youtube_scraper(data)
				query.append(temp_1)
				query.append(temp_2)'''

	return query


'''def scraper_google(data):
	
		URL = GOOGLE_URL
		for ix in data:
			URL = URL + ix + "+"
		scraper_r = requests.get(URL)	
	
	#scraper_google(['stoner','memes'])'''	

def twitter_scraper(data,xyz):

	URL = TWITTER_URL
	for ix in data:
		URL = URL + ix
	URL = URL + '?src=tren'
	
	dict_1 = dict()
	IMAGE = 'http://blog.larrybodine.com/uploads/image/Twitter%20logo.jpg'
	title = xyz
	source = 'www.twitter.com'
	temp = {'url' : URL, 'imageURL': IMAGE, 'title':title,'source':source }

	return temp

def reddit_scraper(data,xyz):

	URL = REDDIT_URL
	for ix in data:
		URL = URL + ix + "+"
	
	dict_1 = dict()
	IMAGE = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlv1VgA2QYZX77wNFki8h0kM50fFkAp8hj6nA5psajBXan5_Vk'
	title = xyz
	source = 'www.reddit.com'
	temp = {'url' : URL, 'imageURL': IMAGE, 'title':title,'source':source }

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


	dict_1 = dict()
	url_1 = video_links[0]
	url_data = urlparse.urlparse(url_1)
	query = urlparse.parse_qs(url_data.query)
	video = query["v"][0]
	IMAGE_1 = "img.youtube.com/vi/" + video + "s/0.jpg"
	title = title[0]
	source = 'www.youtube.com'
	temp_1 = {'url' : url_1, 'imageURL': IMAGE_1, 'title':title,'source':source }

	dict_2 = dict()
	url_2 = video_links[1]
	url_data = urlparse.urlparse(url_2)
	query = urlparse.parse_qs(url_data.query)
	video = query["v"][0]
	IMAGE_2 = "img.youtube.com/vi/" + video + "s/0.jpg"
	title = title[1]	
	source = 'www.youtube.com'
	temp_2 = {'url' : url_2, 'imageURL': IMAGE_2, 'title':title,'source':source }

	return temp_1,temp_2