import React, { useState, useEffect, useRef } from 'react';
import { Utensils, Star, Clock, Truck, ChevronRight, CheckCircle2, Search, Award, ChefHat, MessageSquare, ArrowUp, Mail, Phone, MapPin } from 'lucide-react';
import RestaurantCard from '../components/RestaurantCard';
import MenuCard from '../components/MenuCard';
import ReviewSection from '../components/ReviewSection';
import API from '../api';
const HomePage = ({ searchQuery, setSearchQuery }) => {
    const [restaurants, setRestaurants] = useState([]);
    const [topRestaurants, setTopRestaurants] = useState([]);
    const [bestDishes, setBestDishes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [activeCuisine, setActiveCuisine] = useState('All');

    const topRef = useRef(null);
    const [showTopBtn, setShowTopBtn] = useState(false);
    const [offsetY, setOffsetY] = useState(0);

    useEffect(() => {
        const handleScroll = () => {
            setOffsetY(window.pageYOffset);
            if (window.scrollY > 400) setShowTopBtn(true);
            else setShowTopBtn(false);
        };
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    const scrollToTop = () => {
        if (topRef.current) {
            topRef.current.scrollIntoView({ behavior: 'smooth' });
        }
    };

    const cuisines = ['All', 'Indian', 'Italian', 'Chinese', 'Mexican', 'Japanese', 'American', 'Healthy', 'Dessert'];

    useEffect(() => {
        // Optimistic UI: Immediately load cached data if it exists
        const cachedData = localStorage.getItem('zyra_home_cache');
        let hasCache = false;
        if (cachedData) {
            try {
                const parsed = JSON.parse(cachedData);
                if (parsed.restaurants && parsed.restaurants.length > 0) {
                    setRestaurants(parsed.restaurants);
                    setTopRestaurants(parsed.topRestaurants || []);
                    setBestDishes(parsed.bestDishes || []);
                    setLoading(false); // Instantly hide the spinner since we have cache!
                    hasCache = true;
                }
            } catch (e) {
                console.error("Cache read error", e);
            }
        }
        
        // If we don't have cache, show loading spinner while fetching
        fetchRestaurants(!hasCache);
    }, []);

    const fetchRestaurants = async (showSpinner = true) => {
        if (showSpinner) {
            setLoading(true);
        }
        
        try {
            const [res, topRes, bestRes] = await Promise.all([
                API.getRestaurants(),
                API.getTopRestaurants(),
                API.getBestDishes()
            ]);

            if (res.success) {
                setRestaurants(res.data || []);
            }
            if (topRes.success) {
                setTopRestaurants(topRes.data || []);
            }
            if (bestRes.success) {
                setBestDishes(bestRes.data || []);
            }
            
            // Save to cache for instant loading next time
            localStorage.setItem('zyra_home_cache', JSON.stringify({
                restaurants: res.success ? (res.data || []) : [],
                topRestaurants: topRes.success ? (topRes.data || []) : [],
                bestDishes: bestRes.success ? (bestRes.data || []) : []
            }));
        } catch (error) {
            console.error('Failed to fetch restaurants:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleCuisineClick = async (cuisine) => {
        setActiveCuisine(cuisine);
        setLoading(true);
        try {
            if (cuisine === 'All') {
                const res = await API.getRestaurants();
                setRestaurants(res.data || []);
            } else {
                const res = await API.filterByCuisine(cuisine);
                setRestaurants(res.data || []);
            }
        } catch (error) {
            console.error('Failed to filter:', error);
        } finally {
            setLoading(false);
        }
    };

    const filteredBySearch = restaurants.filter(r => {
        if (!searchQuery) return true;
        const q = searchQuery.toLowerCase();
        return (r.name && r.name.toLowerCase().includes(q)) || 
               (r.cuisineType && r.cuisineType.toLowerCase().includes(q)) ||
               (r.menuItems && r.menuItems.some(item => item && item.toLowerCase().includes(q)));
    });

    const handleSearch = () => {
        const resultsEl = document.getElementById('search-results');
        if (resultsEl) {
            resultsEl.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    };

    return (
        <div className="home-page" ref={topRef}>
            <section className="hero">
                <div className="hero-container">
                    <div className="hero-content">
                        <div className="hero-badge-wrap">
                            <span className="hero-badge">
                                <span className="badge-dot"></span> Michelin-Star Quality
                            </span>
                        </div>
                        <h1 className="hero-title">
                            The Pinnacle of Fine Dining.<br />
                            <span className="hero-highlight gold-text-shimmer">At Your Doorstep.</span>
                        </h1>
                        <p className="hero-text">
                            Curated culinary masterpieces from the city's most exclusive kitchens, delivered with white-glove precision.
                        </p>

                        <div className="hero-search-box">
                            <Search className="hero-search-icon" size={22} />
                            <input 
                                type="text" 
                                className="hero-search-input"
                                placeholder="What are you craving today?" 
                                value={searchQuery || ''}
                                onChange={(e) => setSearchQuery(e.target.value)}
                                onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
                            />
                            <button className="btn-primary hero-search-btn" onClick={handleSearch}>Discover</button>
                        </div>
                        
                        <div className="hero-stats">
                            <div className="stat-item">
                                <span className="stat-number">500+</span>
                                <span className="stat-label">Luxury Venues</span>
                            </div>
                            <div className="stat-divider"></div>
                            <div className="stat-item">
                                <span className="stat-number">30m</span>
                                <span className="stat-label">White-Glove Delivery</span>
                            </div>
                            <div className="stat-divider"></div>
                            <div className="stat-item">
                                <span className="stat-number">4.9<Star size={18} className="star-icon" fill="currentColor"/></span>
                                <span className="stat-label">Client Satisfaction</span>
                            </div>
                        </div>
                    </div>

                    <div className="hero-visual" style={{ transform: `translateY(${offsetY * 0.15}px)`, transition: 'transform 0.1s out' }}>
                        <div className="hero-glow-ring"></div>
                        <div className="hero-bento-grid">
                            <div className="bento-main bento-card">
                                <img src="/images/hero_1.jpg" alt="Luxury Food" className="bento-img" />
                                <div className="bento-glass-shine"></div>
                            </div>
                            <div className="bento-side" style={{ transform: `translateY(${offsetY * -0.1}px)` }}>
                                <div className="bento-sub bento-card bento-top">
                                    <img src="/images/hero_2.jpg" alt="Chef" className="bento-img" />
                                    <div className="bento-glass-shine"></div>
                                </div>
                                <div className="bento-sub bento-card bento-bottom">
                                    <img src="/images/hero_3.jpg" alt="Dessert" className="bento-img" />
                                    <div className="bento-glass-shine"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section className="main-content">
                
                {/* Top Restaurants Section */}
                {!searchQuery && topRestaurants.length > 0 && (
                    <div className="special-section" id="top-rated">
                        <div className="section-header">
                            <h2><Award size={32} className="section-icon" /> Top Rated Restaurants</h2>
                            <p className="section-subtitle" style={{color: 'var(--text-secondary)'}}>The most loved dining experiences in your city</p>
                        </div>
                        <div className="restaurant-grid">
                            {topRestaurants.map(restaurant => (
                                <RestaurantCard key={restaurant.restaurantId} restaurant={restaurant} />
                            ))}
                        </div>
                    </div>
                )}

                {/* Chef's Best Dishes Section */}
                {!searchQuery && bestDishes.length > 0 && (
                    <div className="special-section" id="chefs-favorites" style={{marginTop: '60px'}}>
                        <div className="section-header">
                            <h2><ChefHat size={32} className="section-icon" /> Chef's Best Dishes</h2>
                            <p className="section-subtitle" style={{color: 'var(--text-secondary)'}}>Handpicked signature dishes you must try</p>
                        </div>
                        <div className="menu-grid">
                            {bestDishes.map(item => (
                                <MenuCard key={item.menuId} item={item} restaurantId={item.restaurantId} restaurantName={item.restaurantName || 'Featured'} />
                            ))}
                        </div>
                    </div>
                )}

                <div className="section-header" id="cuisines" style={{marginTop: '60px'}}>
                    <h2>Explore Cuisines</h2>
                    <div className="cuisine-filter">
                        {cuisines.map(c => (
                            <button 
                                key={c}
                                className={`cuisine-btn ${activeCuisine === c ? 'active' : ''}`}
                                onClick={() => handleCuisineClick(c)}
                            >
                                {c}
                            </button>
                        ))}
                    </div>
                </div>

                <div className="section-header" id="search-results" style={{marginTop: '40px'}}>
                    <h2>{searchQuery ? `Search Results for "${searchQuery}"` : 'Popular Restaurants'}</h2>
                </div>

                {loading ? (
                    <div className="restaurant-grid">
                        {[1, 2, 3, 4, 5, 6].map(i => (
                            <div key={i} className="restaurant-card skeleton" style={{ height: '380px' }}></div>
                        ))}
                    </div>
                ) : filteredBySearch.length > 0 ? (
                    <div className="restaurant-grid">
                        {filteredBySearch.map(restaurant => (
                            <RestaurantCard key={restaurant.restaurantId} restaurant={restaurant} />
                        ))}
                    </div>
                ) : (
                    <div className="empty-state">
                        <Utensils className="empty-icon" />
                        <p>No restaurants found matching your criteria.</p>
                    </div>
                )}

                {/* FAQ & Reviews Section */}
                {!searchQuery && (
                    <div id="faq" style={{marginTop: '80px', marginBottom: '40px'}}>
                        <div className="section-header">
                            <h2><MessageSquare size={32} className="section-icon" /> What Our Customers Say</h2>
                            <p className="section-subtitle" style={{color: 'var(--text-secondary)'}}>Real reviews from real foodies</p>
                        </div>
                        <ReviewSection restaurantId={null} />
                        
                        <div className="faq-section" style={{marginTop: '60px', padding: '40px', background: 'var(--bg-surface-2)', borderRadius: 'var(--radius-xl)'}}>
                            <h2 style={{marginBottom: '20px', fontFamily: 'Cinzel, serif'}}>Frequently Asked Questions</h2>
                            <div className="faq-item" style={{marginBottom: '20px', borderBottom: '1px solid var(--border-color)', paddingBottom: '15px'}}>
                                <h4 style={{marginBottom: '8px', color: 'var(--color-primary)'}}>Do you offer table reservations?</h4>
                                <p style={{color: 'var(--text-secondary)'}}>Yes! Zyra now offers exclusive Table Reservations directly through our platform for all participating Michelin-star restaurants. You can easily book a table, select your party size, and specify any special requests like anniversaries or dietary restrictions.</p>
                            </div>
                            <div className="faq-item" style={{marginBottom: '20px', borderBottom: '1px solid var(--border-color)', paddingBottom: '15px'}}>
                                <h4 style={{marginBottom: '8px', color: 'var(--color-primary)'}}>How fast is your delivery?</h4>
                                <p style={{color: 'var(--text-secondary)'}}>We pride ourselves on an average 30-minute delivery time within city limits. We use specialized heated and cooled transport cases to ensure your fine dining experience arrives perfectly plated and at the correct temperature.</p>
                            </div>
                            <div className="faq-item" style={{marginBottom: '20px', borderBottom: '1px solid var(--border-color)', paddingBottom: '15px'}}>
                                <h4 style={{marginBottom: '8px', color: 'var(--color-primary)'}}>Are all these restaurants verified?</h4>
                                <p style={{color: 'var(--text-secondary)'}}>Yes! Every restaurant on Zyra undergoes a strict quality check to ensure a premium dining experience. We partner exclusively with top-tier culinary institutions.</p>
                            </div>
                            <div className="faq-item" style={{marginBottom: '20px', borderBottom: '1px solid var(--border-color)', paddingBottom: '15px'}}>
                                <h4 style={{marginBottom: '8px', color: 'var(--color-primary)'}}>Can I schedule orders in advance?</h4>
                                <p style={{color: 'var(--text-secondary)'}}>While we offer immediate delivery, scheduled orders for specific occasions (like corporate lunches or special dinners) will be rolling out in our next major update.</p>
                            </div>
                            <div className="faq-item" style={{marginBottom: '20px', borderBottom: '1px solid var(--border-color)', paddingBottom: '15px'}}>
                                <h4 style={{marginBottom: '8px', color: 'var(--color-primary)'}}>Do you cater to dietary restrictions?</h4>
                                <p style={{color: 'var(--text-secondary)'}}>Absolutely. When placing an order or making a table reservation, you can specify any dietary requirements such as vegan, gluten-free, or nut allergies. Our partner chefs are extremely accommodating.</p>
                            </div>
                            <div className="faq-item">
                                <h4 style={{marginBottom: '8px', color: 'var(--color-primary)'}}>How can I track my delivery?</h4>
                                <p style={{color: 'var(--text-secondary)'}}>Once your order is confirmed, you will receive real-time GPS tracking updates directly in the application.</p>
                            </div>
                        </div>
                        
                        <div id="contact-us" style={{marginTop: '80px', padding: '60px 40px', background: 'var(--bg-surface-2)', borderRadius: 'var(--radius-xl)', display: 'flex', flexDirection: 'column', alignItems: 'center', textAlign: 'center', position: 'relative', overflow: 'hidden'}}>
                            <div style={{position: 'absolute', top: '-50px', right: '-50px', width: '200px', height: '200px', background: 'var(--color-primary)', opacity: '0.05', borderRadius: '50%', filter: 'blur(40px)'}}></div>
                            <div style={{position: 'absolute', bottom: '-50px', left: '-50px', width: '200px', height: '200px', background: 'var(--color-primary)', opacity: '0.05', borderRadius: '50%', filter: 'blur(40px)'}}></div>
                            
                            <h2 style={{marginBottom: '15px', fontFamily: 'Cinzel, serif', fontSize: '32px'}}>Contact Our Concierge</h2>
                            <p style={{color: 'var(--text-secondary)', marginBottom: '40px', maxWidth: '600px', fontSize: '16px'}}>
                                Experience unparalleled service. Whether you need assistance with an order, or are looking to arrange a private dining experience, our dedicated concierge is available 24/7.
                            </p>
                            
                            <div style={{display: 'flex', gap: '40px', flexWrap: 'wrap', justifyContent: 'center', marginBottom: '40px'}}>
                                <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '10px'}}>
                                    <div style={{width: '50px', height: '50px', borderRadius: '50%', border: '1px solid var(--border-light)', background: 'var(--bg-surface)', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--color-primary)'}}>
                                        <Mail size={24} />
                                    </div>
                                    <span style={{color: 'var(--text-primary)', fontWeight: '500'}}>concierge@zyra.com</span>
                                </div>
                                <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '10px'}}>
                                    <div style={{width: '50px', height: '50px', borderRadius: '50%', border: '1px solid var(--border-light)', background: 'var(--bg-surface)', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--color-primary)'}}>
                                        <Phone size={24} />
                                    </div>
                                    <span style={{color: 'var(--text-primary)', fontWeight: '500'}}>+91 800-ZYRA-LUX</span>
                                </div>
                                <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '10px'}}>
                                    <div style={{width: '50px', height: '50px', borderRadius: '50%', border: '1px solid var(--border-light)', background: 'var(--bg-surface)', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--color-primary)'}}>
                                        <MapPin size={24} />
                                    </div>
                                    <span style={{color: 'var(--text-primary)', fontWeight: '500'}}>100 UB City, Bangalore</span>
                                </div>
                            </div>
                        </div>
                    </div>
                )}
            </section>

            {showTopBtn && (
                <button className="scroll-top-btn" onClick={scrollToTop} title="Go to Top">
                    <ArrowUp size={24} />
                </button>
            )}
        </div>
    );
};

export default HomePage;
