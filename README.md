# Zyra: The Pinnacle of Fine Dining 🍽️✨

Zyra is a state-of-the-art, luxury restaurant application built to deliver Michelin-star dining experiences directly to users' doorsteps. 

Featuring an ultra-premium, cinematic UI/UX and a robust, scalable backend, Zyra redefines the digital dining landscape.

## 🌟 Key Features

- **Luxury User Interface:** A meticulously crafted "Midnight & Everose Gold" design system featuring high-end typographic hierarchies (`Didot`/`Bodoni MT`), subtle micro-interactions, and glassmorphic overlays.
- **Cinematic Hero Parallax:** A visually stunning homepage that responds to user scrolling, adding depth and prestige to high-resolution food photography.
- **Blazing Fast Performance:** 
  - **Route-Level Code Splitting:** Powered by React `lazy` and `Suspense`, ensuring users only download the JavaScript they absolutely need, resulting in near-instantaneous initial loads.
  - **Lazy Loading Imagery:** `loading="lazy"` applied to all high-res photography.
  - **Skeleton Loaders:** Elegant, pulsating skeleton screens instead of jarring loading spinners.
- **Smart Keep-Alive Backend:** A Spring `@Scheduled` service that actively prevents Render backend server "cold starts", ensuring the API is always instantly responsive.
- **Seamless Cart & Checkout:** Global state management powered by React Context API for flawless cart synchronization.
- **Full Backend API:** A RESTful Spring Boot architecture utilizing strict Data Transfer Objects (DTOs), session management, and robust exception handling.

---

## 🛠️ Technology Stack

### Frontend
- **React.js** (Functional Components, Hooks, Context API)
- **Vite** (Next-generation, blazing-fast frontend tooling)
- **React Router v6** (SPA Navigation & Route-Level Code Splitting)
- **Lucide React** (Premium vector iconography)
- **Vanilla CSS** (Custom, highly optimized CSS without bloated frameworks)

### Backend
- **Java Spring Boot**
- **Spring Web** (RESTful API architecture)
- **Spring Data JPA / Hibernate** (Object-Relational Mapping)
- **H2 Database** (Development) / PostgreSQL (Production)
- **Maven** (Dependency Management)

---

## 🚀 Getting Started

### Prerequisites
- Node.js (v18 or higher)
- Java Development Kit (JDK 17 or higher)
- Maven

### 1. Running the Backend (Spring Boot)
1. Navigate to the root directory.
2. Build the project using Maven:
   ```bash
   ./mvnw clean install
   ```
3. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
   *The backend server will start on `http://localhost:8080`.*

### 2. Running the Frontend (React / Vite)
1. Open a new terminal and navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install the necessary NPM packages:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
   *The frontend application will be available at `http://localhost:5173`.*

---

## 🎨 Design Philosophy
Zyra discards the heavy, bloated look of traditional delivery apps. It embraces minimalism, high contrast, and editorial typography. Every shadow, hover state, and font kerning choice has been optimized to emulate the physical menus of the world's most exclusive restaurants. 

*Designed and Developed with uncompromising standards.*
