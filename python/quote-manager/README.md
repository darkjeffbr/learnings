# Random Quote Manager

A simple CRUD (Create, Read, Update, Delete) REST API for managing inspirational quotes. This service allows you to store, retrieve, and organize quotes with their authors and categories.

The main motivation for this is to learn [Django](https://www.djangoproject.com/)

## Data Model

### Quote Entity

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | UUID | Yes | Unique identifier for the quote |
| `text` | String | Yes | The actual quote content |
| `author` | String | Yes | Person who said or wrote the quote |
| `category` | String | No | Optional category (e.g., "Motivation", "Humor", "Wisdom") |

### Example Quote Object

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "text": "The only way to do great work is to love what you do.",
  "author": "Steve Jobs",
  "category": "Motivation"
}
```

## API Endpoints

| API Operation | Payload | Response | Error |
|---------------|----------------|----------|----------|
| `GET /api/quotes`  | -             | 200 OK - List of quotes |-|
| `GET /api/quotes?category=Motivation`  | -             | 200 OK - List of quotes filtered by category |-|
| `GET /api/quotes/{id}` | - | 200 OK - Quote item |`404 Not Found` if quote doesn't exist|
| `POST /api/quotes` | Quote object as json | 201 Created |-|
| `DELETE /api/quotes/{id}` | - | 204 No Content |`404 Not Found` if quote doesn't exist|


## Setup & Running

### Setup
To create the scafolding and basic project structure the following commands were used
1. First thing is to enable the virtual environment `. ./.venv/bin/activate`
2. Then install the dependencies `pip install -r requirements.txt` 
3. Run `django-admin startproject quote_manager .` 
4. Run `python manage.py startapp quote` 

### Running
To simply run the application we use the follwing commands
1. Start the virtual environment  `. ./.venv/bin/activate`
2. Start the server `python manage.py runserver 8080` 