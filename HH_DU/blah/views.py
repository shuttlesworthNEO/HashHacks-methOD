from django.shortcuts import render
from django.http import JsonResponse, HttpResponse
from django.views.decorators.csrf import csrf_exempt
from utlis.scraper import response_render

@csrf_exempt
# Create your views here.
def fuck_blah(request):
	if request.method == 'POST':
		data = request.body
		print 'Raw Data: "%s"' % request.body;

		query = response_render(data)
		return JsonResponse(query, safe=False)