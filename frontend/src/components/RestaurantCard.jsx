import React from 'react';
import { Star, Clock } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

const RestaurantCard = ({ restaurant }) => {
    const navigate = useNavigate();

    // Determine badge color based on cuisine
    const getBadgeStyle = (cuisine) => {
        const types = {
            'Italian': { bg: 'rgba(239, 68, 68, 0.8)', color: '#ffffff' },
            'Indian': { bg: 'rgba(249, 115, 22, 0.8)', color: '#ffffff' },
            'Chinese': { bg: 'rgba(234, 179, 8, 0.8)', color: '#ffffff' },
            'Mexican': { bg: 'rgba(16, 185, 129, 0.8)', color: '#ffffff' },
            'Japanese': { bg: 'rgba(139, 92, 246, 0.8)', color: '#ffffff' },
        };
        return types[cuisine] || { bg: 'rgba(59, 130, 246, 0.8)', color: '#ffffff' };
    };

    const fallbackImage = '/images/r1.jpg';
    const badgeStyle = getBadgeStyle(restaurant.cuisineType);

    return (
        <div className="restaurant-card" onClick={() => navigate(`/restaurant/${restaurant.restaurantId}`)}>
            <div className="restaurant-img-wrap">
                <img 
                    src={restaurant.imagePath || fallbackImage} 
                    alt={restaurant.name} 
                    className="restaurant-img"
                    loading="lazy"
                    onError={(e) => { e.target.src = fallbackImage; }}
                />
                {restaurant.cuisineType && (
                    <div className="restaurant-badge" style={{ background: badgeStyle.bg, color: badgeStyle.color, border: 'none' }}>
                        {restaurant.cuisineType}
                    </div>
                )}
            </div>
            <div className="restaurant-info">
                <h3 className="restaurant-name">{restaurant.name}</h3>
                <div className="restaurant-meta">
                    <span className="rating-badge">
                        <Star size={14} fill="currentColor" />
                        {restaurant.rating ? restaurant.rating.toFixed(1) : 'New'}
                    </span>
                    <span className="delivery-time">
                        <Clock size={14} />
                        {restaurant.deliveryTime} min
                    </span>
                </div>
            </div>
        </div>
    );
};

export default RestaurantCard;
