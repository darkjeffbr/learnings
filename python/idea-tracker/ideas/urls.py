from django.urls import path
from .views import IdeasListCreateView, IdeaDetailView

urlpatterns = [
    path("ideas/", IdeasListCreateView.as_view(), name="ideas-list-create"),
    path("ideas/<int:idea_id>", IdeaDetailView.as_view(), name="idea-detail"),
]
