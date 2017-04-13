from nltk.tokenize import sent_tokenize, word_tokenize
from nltk.corpus import stopwords

def tokens(data):
	exceptions = ["the","this",]
	stop_word = list(set(stopwords.words("english")) - set(exceptions))
	words = word_tokenize(data)

	resp = []
	for w in words:
		if w not in stop_word:
			resp.append(w)

	return resp		
			