# 🚀 Zyra: Full-Stack Food Ordering Application
**Technical Interview Preparation Notes**

Use this document to confidently explain the architecture, tech stack, and logic of your project during technical interviews.

---

## 🏗️ 1. Project Overview & Architecture
**Zyra** is a premium, full-stack web application designed for food ordering and restaurant discovery. It features user authentication, a dynamic shopping cart, a responsive UI with dark/light mode, and a robust backend API.

### The Tech Stack
* **Frontend:** React 19 (built with Vite for fast bundling), React Router DOM, Context API, and Vanilla CSS.
* **Backend:** Java 21, Spring Boot 3.3, Spring Security, Spring Data JPA.
* **Database:** H2 (In-memory/File-based) configured via Hibernate (MySQL Dialect compatible).

---

## 💻 2. Frontend Deep Dive (React)
The frontend is a **Single Page Application (SPA)**. Instead of reloading the page when a user navigates, React dynamically updates the DOM.

### Key React Hooks Used
In interviews, you will likely be asked how you manage state and side effects. Here is how you used them:

1. **`useState` (Local State Management)**
   * **What it is:** A hook that lets you add state variables to functional components.
   * **How you used it:** Used for things that change within a single component. For example, in `AuthModal.jsx`, you used `useState` to toggle between the "Login" and "Register" forms (`const [isLogin, setIsLogin] = useState(true)`). You also used it to track form inputs like emails and passwords.

2. **`useEffect` (Side Effects)**
   * **What it is:** A hook used to perform side effects (like data fetching, subscriptions, or manually changing the DOM) after the component renders.
   * **How you used it:** In `HomePage.jsx` and `RestaurantPage.jsx`, you used `useEffect` to fetch restaurant data from the Spring Boot API as soon as the page loads. 
   * *Example:* `useEffect(() => { fetchRestaurants(); }, [])` — The empty dependency array `[]` ensures the fetch only happens exactly once when the component mounts.

3. **`useContext` (Global State Management)**
   * **What it is:** A hook that lets you read and subscribe to context from your component, avoiding "prop drilling" (passing data down through 10 layers of components).
   * **How you used it:** You built three major contexts:
     * `AuthContext`: Tracks if the user is logged in globally so the Navbar knows when to show the "Sign Out" button.
     * `CartContext`: Manages the food items in the shopping cart. Any component (like `MenuCard` or `Navbar`) can add items or check the total item count without needing to pass the cart data as props.
     * `ThemeContext`: Toggles a CSS class on the `<body>` tag to switch between dark and light modes.

4. **`useRef` (Direct DOM Access)**
   * **What it is:** A hook that lets you reference a value that’s not needed for rendering, often used to directly access a DOM element.
   * **How you used it:** In `HomePage.jsx`, you used `useRef` to scroll the user to specific sections (like "Top Rated" or "Cuisines") when they click a link in the Navbar.

### Styling & CSS
* You chose **Vanilla CSS** with **CSS Variables (`var(--color-primary)`)**. This demonstrates a strong fundamental understanding of CSS over relying heavily on frameworks like Tailwind.
* You utilized **CSS Grid** (for the Bento-box style hero images) and **Flexbox** (for navigation and centering).
* **Responsive Design:** You used `@media` queries to intelligently stack elements and adjust image heights on mobile devices.

---

## ⚙️ 3. Backend Deep Dive (Spring Boot)
The backend follows a classic **Model-View-Controller (MVC) / N-Tier Architecture**.

1. **Controllers (`@RestController`)**
   * These define the API endpoints (e.g., `GET /api/restaurants`). They receive the HTTP request from the React frontend, pass the work to the Service layer, and return a JSON response.
2. **Services (`@Service`)**
   * This is where the **business logic** lives. It handles calculations, validations, and prepares data before saving it.
3. **Repositories (`@Repository`)**
   * You used **Spring Data JPA**. Instead of writing raw SQL queries, you created interfaces extending `JpaRepository`. Spring automatically generated the SQL to find, save, or delete data in the database.
4. **Entities (`@Entity`)**
   * These are Java classes that map directly to database tables (e.g., `User`, `Restaurant`, `Order`).
5. **Security (`Spring Security`)**
   * You implemented security configurations to protect certain endpoints. For example, viewing restaurants is public, but placing an order requires the user to be authenticated.

### Database Strategy
* Originally built for Aiven MySQL, you successfully pivoted to an **Embedded H2 Database**. 
* **Why this is impressive to mention:** "When my cloud MySQL provider suspended my free-tier database, causing deployment crashes, I refactored the application to use an internal H2 database with a MySQL dialect. This made the application highly resilient, self-contained, and crash-proof for staging and portfolio demonstrations."

---

## 🎯 4. How to Answer Common Interview Questions

**Q: "What was the most challenging part of this project?"**
* **A:** "Managing the global state for the shopping cart and authentication. I initially struggled with prop-drilling, but I solved it by implementing React's Context API. This allowed me to wrap the application in a `CartProvider` and `AuthProvider`, so components deeply nested in the tree could easily add items to the cart or check if the user was logged in using the `useContext` hook."

**Q: "How does your frontend communicate with your backend?"**
* **A:** "I built a centralized `api.js` service wrapper around the native browser `fetch` API. This wrapper automatically attaches the correct `Content-Type` headers for JSON, handles error parsing, and uses `credentials: 'include'` so that Spring Boot session cookies are properly sent back and forth for authentication."

**Q: "Why didn't you use Redux for state management?"**
* **A:** "For a project of this scale, Redux would introduce unnecessary boilerplate. The Context API combined with `useReducer` or `useState` is more than powerful enough to handle the global state for the cart, theme, and user session natively within React."

**Q: "How did you handle application performance?"**
* **A:** "I implemented Optimistic UI caching in the `HomePage.jsx`. When the user loads the page, I check `localStorage` for cached restaurant data and render it instantly. In the background, `useEffect` fetches the absolute latest data from the Spring Boot API and silently updates the UI. This gives the user a zero-latency experience."
