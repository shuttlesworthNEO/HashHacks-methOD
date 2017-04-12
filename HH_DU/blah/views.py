from django.shortcuts import render
from django.http import JsonResponse, HttpResponse
from django.views.decorators.csrf import csrf_exempt

@csrf_exempt
# Create your views here.
def fuck_blah(request):
	if request.method == 'POST':
		print 'Raw Data: "%s"' % request.body;
		url = "https://www.youtube.com/watch?v=siwpn14IE7E"
		imageURL = "http://kingofwallpapers.com/top-gun/top-gun-003.jpg"
		title = 'bdskjankl'
		source = 'vkjsdnkl'
		return JsonResponse([{'url':url,'imageURL':imageURL,'title':title,'source':source},
			{'url':url,'imageURL':imageURL,'title':title,'source':source},
			{'url':url,'imageURL':imageURL,'title':title,'source':source},
			{'url':url,'imageURL':imageURL,'title':title,'source':source}], safe=False)