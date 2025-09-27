from django.urls import path
from .views import QuotesListCreateView, QuoteDetailView

urlpatterns = [
    path("quotes/", QuotesListCreateView.as_view(), name="ideas-list-create"),
    path("quotes/<int:idea_id>", QuoteDetailView.as_view(), name="idea-detail"),
]
