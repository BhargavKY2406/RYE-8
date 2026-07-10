const BASE = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

/**
 * Generic fetch wrapper.
 * Automatically sets Content-Type for JSON bodies,
 * parses the JSON response, and rejects on HTTP errors.
 * Includes credentials to send session cookies.
 */
async function request(endpoint, options = {}) {
    const url = `${BASE}${endpoint}`;
    const config = {
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include', // Crucial for session-based auth
        ...options,
    };
    
    try {
        const response = await fetch(url, config);
        const data = await response.json();
        
        if (!response.ok) {
            throw { status: response.status, ...data };
        }
        
        return data;
    } catch (error) {
        if (!error.status) {
            console.error('Network or parsing error:', error);
            throw new Error('Network error. Please check your connection.');
        }
        throw error;
    }
}

const API = {
    // Auth
    register: (body) => request('/auth/register', { method: 'POST', body: JSON.stringify(body) }),
    login:    (body) => request('/auth/login',    { method: 'POST', body: JSON.stringify(body) }),
    logout:   ()     => request('/auth/logout',   { method: 'POST' }),
    me:       ()     => request('/auth/me'),

    // Restaurants
    getRestaurants:   ()     => request('/restaurants'),
    getTopRestaurants:()     => request('/restaurants/top'),
    getRestaurant:    (id)   => request(`/restaurants/${id}`),
    searchRestaurants:(name) => request(`/restaurants/search?name=${encodeURIComponent(name)}`),
    filterByCuisine:  (type) => request(`/restaurants/cuisine?type=${encodeURIComponent(type)}`),

    // Menu
    getMenu: (restaurantId) => request(`/restaurants/${restaurantId}/menu`),
    getBestDishes: () => request('/restaurants/menu/best'),

    // Orders
    placeOrder: (body) => request('/orders', { method: 'POST', body: JSON.stringify(body) }),
    getOrders:  ()     => request('/orders'),
    simulateOrder: (id) => request(`/orders/${id}/simulate`, { method: 'POST' }),
    
    // Reviews
    getReviews: (restaurantId) => request(`/restaurants/${restaurantId}/reviews`),
    addReview:  (restaurantId, body) => request(`/restaurants/${restaurantId}/reviews`, { method: 'POST', body: JSON.stringify(body) }),

    // Reservations
    createReservation: (body) => request('/reservations', { method: 'POST', body: JSON.stringify(body) }),
    getMyReservations: () => request('/reservations/my-reservations'),
    cancelReservation: (id) => request(`/reservations/${id}/cancel`, { method: 'PUT' }),
};

export default API;
