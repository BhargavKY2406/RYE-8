import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft, Star, Clock, MapPin, CalendarDays } from 'lucide-react';
import MenuCard from '../components/MenuCard';
import ReviewSection from '../components/ReviewSection';
import ReservationModal from '../components/ReservationModal';
import API from '../api';

const RestaurantPage = ({ onOpenAuth }) => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [restaurant, setRestaurant] = useState(null);
    const [menu, setMenu] = useState([]);
    const [loading, setLoading] = useState(true);
    const [isReservationOpen, setIsReservationOpen] = useState(false);

    useEffect(() => {
        window.scrollTo(0, 0);
        fetchData();
    }, [id]);

    const fetchData = async () => {
        setLoading(true);
        try {
            const [resData, menuData] = await Promise.all([
                API.getRestaurant(id),
                API.getMenu(id)
            ]);
            
            if (resData.success) setRestaurant(resData.data);
            if (menuData.success) setMenu(menuData.data);
        } catch (error) {
            console.error('Failed to fetch data:', error);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <div className="loading-spinner" style={{minHeight: '60vh'}}>
                <div className="spinner"></div>
                <p>Loading menu...</p>
            </div>
        );
    }

    if (!restaurant) {
        return (
            <div className="empty-state" style={{minHeight: '60vh'}}>
                <h2>Restaurant not found</h2>
                <button className="btn-primary" onClick={() => navigate('/')}>Go Back Home</button>
            </div>
        );
    }

    const fallbackImage = '/images/banner.jpg';

    return (
        <div className="restaurant-page">
            <div className="restaurant-banner">
                <img 
                    src={restaurant.imagePath || fallbackImage} 
                    alt={restaurant.name} 
                    className="banner-img"
                    onError={(e) => { e.target.src = fallbackImage; }}
                />
                
                <button className="btn-back" onClick={() => navigate('/')}>
                    <ArrowLeft size={20} /> Back
                </button>
            </div>

            <div className="restaurant-header">
                <h1 className="restaurant-title">{restaurant.name}</h1>
                <div className="restaurant-meta">
                    <span className="meta-item"><Star size={16} fill="currentColor"/> {restaurant.rating || 'New'}</span>
                    <span className="meta-divider">•</span>
                    <span className="meta-item"><Clock size={16} /> {restaurant.deliveryTime} mins</span>
                    <span className="meta-divider">•</span>
                    <span className="meta-item">{restaurant.cuisineType}</span>
                </div>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', flexWrap: 'wrap', gap: '20px' }}>
                    <div className="restaurant-address">
                        <MapPin size={16} /> {restaurant.address}
                    </div>
                    <button 
                        className="btn-primary" 
                        onClick={() => setIsReservationOpen(true)}
                        style={{ padding: '12px 24px', boxShadow: 'var(--shadow-glow)' }}
                    >
                        <CalendarDays size={18} /> Book a Table
                    </button>
                </div>
            </div>

            <div className="main-content">
                <div className="section-header">
                    <h2>Menu</h2>
                </div>
                
                {menu.length > 0 ? (
                    <div className="menu-grid">
                        {menu.map(item => (
                            <MenuCard 
                                key={item.menuId} 
                                item={item} 
                                restaurantId={restaurant.restaurantId}
                                restaurantName={restaurant.name}
                            />
                        ))}
                    </div>
                ) : (
                    <div className="empty-state">
                        <p>No menu items available at the moment.</p>
                    </div>
                )}
                
                <ReviewSection restaurantId={restaurant.restaurantId} />
            </div>

            <ReservationModal 
                isOpen={isReservationOpen}
                onClose={() => setIsReservationOpen(false)}
                restaurantId={restaurant.restaurantId}
                restaurantName={restaurant.name}
                onOpenAuth={onOpenAuth}
            />
        </div>
    );
};

export default RestaurantPage;
