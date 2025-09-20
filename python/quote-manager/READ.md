# Random Quote Manager

A simple CRUD (Create, Read, Update, Delete) REST API for managing inspirational quotes. This service allows you to store, retrieve, and organize quotes with their authors and categories.

The main motivation for this is to learn [Flask](https://flask.palletsprojects.com/en/stable/)

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

### Create a Quote
```
POST /quotes
```

**Request Body:**
```json
{
  "text": "Be yourself; everyone else is already taken.",
  "author": "Oscar Wilde",
  "category": "Wisdom"
}
```

**Response:** `201 Created`
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "text": "Be yourself; everyone else is already taken.",
  "author": "Oscar Wilde",
  "category": "Wisdom"
}
```

### Get All Quotes
```
GET /quotes
```

**Response:** `200 OK`
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "text": "The only way to do great work is to love what you do.",
    "author": "Steve Jobs",
    "category": "Motivation"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "text": "Be yourself; everyone else is already taken.",
    "author": "Oscar Wilde",
    "category": "Wisdom"
  }
]
```

### Get a Specific Quote
```
GET /quotes/{id}
```

**Response:** `200 OK`
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "text": "The only way to do great work is to love what you do.",
  "author": "Steve Jobs",
  "category": "Motivation"
}
```

**Error Response:** `404 Not Found` if quote doesn't exist

### Get a Random Quote
```
GET /quotes/random
```

**Response:** `200 OK`
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "text": "Be yourself; everyone else is already taken.",
  "author": "Oscar Wilde",
  "category": "Wisdom"
}
```

### Filter Quotes by Category
```
GET /quotes?category=Motivation
```

**Response:** `200 OK`
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "text": "The only way to do great work is to love what you do.",
    "author": "Steve Jobs",
    "category": "Motivation"
  }
]
```

### Delete a Quote
```
DELETE /quotes/{id}
```

**Response:** `204 No Content` on successful deletion

**Error Response:** `404 Not Found` if quote doesn't exist

## Installation & Setup

1. First thing is to enable the virtual environment `. ./.env/bin/activate`
2. Then install the dependnecies `pip install -r requirements.txt` 

