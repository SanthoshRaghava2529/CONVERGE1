# CONVERGE

CONVERGE is a collaborative entertainment discovery and group decision platform that helps people find experiences everyone in the group can agree on.

Instead of searching for an experience individually, users can create a group, invite friends, submit their preferences, and use the matching system to find experiences that best satisfy the group.

## Features

- User registration and login
- JWT-based authentication
- BCrypt password hashing
- Entertainment experience discovery
- Experience categories and filtering
- Experience details and schedules
- Group creation
- Group joining using a unique group code
- Group member management
- Individual member preferences
- Collaborative group matching
- Weighted preference matching algorithm
- Fairness-based group scoring
- Ranked experience recommendations
- Explanation of matching results
- Group decision making
- Normal experience booking
- Group experience booking
- Booking history
- Saved experiences
- User profile management
- Input validation
- Global exception handling
- CORS support
- WebSocket/STOMP foundation for real-time group collaboration
- PostgreSQL database
- REST API architecture

## Group Converge

The main feature of CONVERGE is the Group Converge system.

A user can:

1. Create a group
2. Set the city, date and budget
3. Share the generated group code
4. Allow friends to join
5. Collect preferences from all members
6. Run the matching engine
7. Get ranked experience recommendations
8. View why an experience matches the group
9. Make a group decision
10. Proceed with the booking

The matching system considers multiple factors:

- Budget - 25%
- Time - 20%
- Genre - 15%
- Category - 15%
- Duration - 10%
- Distance - 10%
- Language - 5%

A fairness factor is also considered so that an experience is not selected only because it strongly matches some members while poorly matching another member.

## Technologies Used

### Frontend

- React
- Vite
- Tailwind CSS
- React Router
- Axios
- JavaScript

### Backend

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- BCrypt
- Bean Validation
- WebSocket
- STOMP
- Maven

### Database

- PostgreSQL
- Hibernate / JPA

### Deployment

- Vercel - Frontend
- Render - Backend
- Render PostgreSQL - Database

## Project Structure

```text
CONVERGE/
│
├── backend/
│   │
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── converge/
│   │       │           └── backend/
│   │       │               ├── config/
│   │       │               ├── controller/
│   │       │               ├── entity/
│   │       │               ├── repository/
│   │       │               ├── security/
│   │       │               ├── service/
│   │       │               └── ...
│   │       │
│   │       └── resources/
│   │
│   ├── Dockerfile
│   ├── mvnw
│   ├── pom.xml
│   └── ...
│
├── frontend/
│   │
│   ├── src/
│   │   ├── components/
│   │   │   ├── Navbar.jsx
│   │   │   └── ExperienceCard.jsx
│   │   │
│   │   ├── pages/
│   │   │   ├── Home.jsx
│   │   │   ├── Explore.jsx
│   │   │   ├── Login.jsx
│   │   │   ├── Register.jsx
│   │   │   ├── GroupConverge.jsx
│   │   │   ├── GroupRoom.jsx
│   │   │   ├── ExperienceDetails.jsx
│   │   │   ├── Profile.jsx
│   │   │   ├── Bookings.jsx
│   │   │   └── Saved.jsx
│   │   │
│   │   ├── services/
│   │   │   └── api.js
│   │   │
│   │   ├── App.jsx
│   │   ├── App.css
│   │   └── index.css
│   │
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
└── README.md
