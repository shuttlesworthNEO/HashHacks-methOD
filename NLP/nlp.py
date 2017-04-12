from nltk.tokenize import sent_tokenize, word_tokenize
from nltk.corpus import stopwords

inpString = raw_input()
exceptions = ["the","this",]
stop_word = list(set(stopwords.words("english")) - set(exceptions))
words = word_tokenize(inpString)

for w in words:
	if w not in stop_word:
		print(w)