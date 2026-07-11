from rest_framework import serializers

class QuoteSerializer(serializers.Serializer):
    id = serializers.CharField(read_only=True)
    text = serializers.CharField()
    author = serializers.CharField(max_length=100)
    category = serializers.CharField(max_length=100)
    created_at = serializers.DateTimeField(read_only=True)