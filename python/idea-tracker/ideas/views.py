from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializers import IdeaSerializer

# In-memory store
ideas = []
next_id = 1

class IdeasListCreateView(APIView):

    def get(self, request):
        serializer = IdeaSerializer(ideas, many=True)
        return Response(serializer.data)

    def post(self, request):
        global next_id
        serializer = IdeaSerializer(data=request.data)
        if serializer.is_valid():
            idea = serializer.validated_data
            idea["id"] = next_id
            next_id += 1
            ideas.append(idea)
            return Response(idea, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class IdeaDetailView(APIView):

    def get(self, request, idea_id):
        for idea in ideas:
            if idea["id"] == idea_id:
                serializer = IdeaSerializer(idea)
                return Response(serializer.data)
        return Response("Not found", status=status.HTTP_404_NOT_FOUND)
    