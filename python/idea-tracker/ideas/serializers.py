from rest_framework import serializers

class IdeaSerializer(serializers.Serializer):
    id = serializers.IntegerField(read_only=True)
    title = serializers.CharField(max_length=100)
    created_at = serializers.CharField(max_length=100)