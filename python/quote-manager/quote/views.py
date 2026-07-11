import uuid, datetime, logging
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializers import QuoteSerializer

# In-memory store
quotes = []

logger = logging.getLogger("myapp")

class QuotesListCreateView(APIView):

    def get(self, request):
        category = request.GET.get('category', None)
        if category is not None:
            filteredQuotes = filter(lambda quote: quote["category"] is not None and quote["category"] == category, quotes)
            serializer = QuoteSerializer(filteredQuotes, many=True)
        else:
            serializer = QuoteSerializer(quotes, many=True)
        return Response(serializer.data)

    def post(self, request):
        global next_id
        serializer = QuoteSerializer(data=request.data)
        if serializer.is_valid():
            quote = serializer.validated_data
            quote["id"] = uuid.uuid4()
            quote["created_at"] = datetime.datetime.now()
            quotes.append(quote)
            return Response(quote, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class QuoteDetailView(APIView):

    def get(self, request, quote_id):
        for quote in quotes:
            if quote["id"] == quote_id:
                serializer = QuoteSerializer(quote)
                return Response(serializer.data)
        return Response("Not found", status=status.HTTP_404_NOT_FOUND)
    
    def delete(self, request, quote_id):
        logger.debug(">>>> -------------------------------------")
        logger.debug(quote_id)
        logger.debug("<<<< -------------------------------------")
        toDelete = None
        for quote in quotes:
            if quote["id"] == quote_id:
                toDelete = quote
        
        if toDelete is None:
            return Response("Not found", status=status.HTTP_404_NOT_FOUND)
        else:
            return Response(status=status.HTTP_204_NO_CONTENT)