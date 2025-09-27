import uuid
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializers import QuoteSerializer

# In-memory store
quotes = []

class QuotesListCreateView(APIView):

    def get(self, request):
        serializer = QuoteSerializer(quotes, many=True)
        return Response(serializer.data)

    def post(self, request):
        global next_id
        serializer = QuoteSerializer(data=request.data)
        if serializer.is_valid():
            quote = serializer.validated_data
            quote["id"] = uuid.uuid4()
            quotes.append(quote)
            return Response(quote, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class QuoteDetailView(APIView):

    def get(self, request, idea_id):
        for quote in quotes:
            if quote["id"] == idea_id:
                serializer = QuoteSerializer(quote)
                return Response(serializer.data)
        return Response("Not found", status=status.HTTP_404_NOT_FOUND)
    