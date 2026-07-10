import React, { useState, useEffect } from 'react';
import { Star, Send } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import { useToast } from '../context/ToastContext';
import API from '../api';

const ReviewSection = ({ restaurantId }) => {
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [rating, setRating] = useState(5);
    const [comment, setComment] = useState('');
    const [submitting, setSubmitting] = useState(false);
    
    const { user } = useAuth();
    const { showToast } = useToast();

    // Home Reviews for premium display
    const homeReviews = [
        { reviewId: 'h1', username: 'Eleanor V.', rating: 5, comment: 'An absolutely flawless experience. The food arrived perfectly heated, and the packaging was pure quality. Will definitely order again for our next dinner party.', createdAt: new Date().toISOString() },
        { reviewId: 'h2', username: 'Marcus T.', rating: 5, comment: 'Rye-8 sets a new standard for food delivery. The Truffle Smashburger from The Rustic Grill was Michelin-star quality.', createdAt: new Date(Date.now() - 86400000).toISOString() },
        { reviewId: 'h3', username: 'Sophia L.', rating: 4, comment: 'Incredible selection of premium restaurants. Delivery was exactly on time. Highly recommended for a special night in.', createdAt: new Date(Date.now() - 172800000).toISOString() }
    ];

    const displayReviews = restaurantId === null ? homeReviews : reviews;

    useEffect(() => {
        fetchReviews();
    }, [restaurantId]);

    const fetchReviews = async () => {
        if (restaurantId === null) {
            setLoading(false);
            return;
        }
        setLoading(true);
        try {
            const res = await API.getReviews(restaurantId);
            if (res.success) {
                setReviews(res.data);
            }
        } catch (error) {
            console.error('Failed to fetch reviews:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!user) {
            showToast('error', 'You must be logged in to leave a review');
            return;
        }
        if (rating < 1 || rating > 5) {
            showToast('error', 'Rating must be between 1 and 5');
            return;
        }

        setSubmitting(true);
        try {
            const res = await API.addReview(restaurantId, { rating, comment });
            if (res.success) {
                showToast('success', 'Review added successfully!');
                setComment('');
                setRating(5);
                fetchReviews(); // Refresh list
            }
        } catch (error) {
            showToast('error', error.message || 'Failed to submit review');
        } finally {
            setSubmitting(false);
        }
    };

    const formatDate = (dateString) => {
        const d = new Date(dateString);
        return d.toLocaleDateString('en-IN', {
            year: 'numeric', month: 'short', day: 'numeric'
        });
    };

    return (
        <div className="review-section" style={{marginTop: '40px'}}>
            <div className="section-header">
                <h2>Reviews</h2>
            </div>
            
            {restaurantId !== null && user ? (
                <form className="review-form" onSubmit={handleSubmit} style={{marginBottom: '32px', background: 'var(--bg-surface-2)', padding: '24px', borderRadius: 'var(--radius-md)'}}>
                    <h3 style={{marginBottom: '16px'}}>Leave a Review</h3>
                    <div className="form-group" style={{marginBottom: '16px'}}>
                        <label>Rating</label>
                        <div style={{display: 'flex', gap: '8px'}}>
                            {[1, 2, 3, 4, 5].map(num => (
                                <Star 
                                    key={num} 
                                    size={28} 
                                    fill={num <= rating ? 'var(--color-warning)' : 'transparent'} 
                                    color={num <= rating ? 'var(--color-warning)' : 'var(--text-muted)'}
                                    style={{cursor: 'pointer'}}
                                    onClick={() => setRating(num)}
                                />
                            ))}
                        </div>
                    </div>
                    <div className="form-group" style={{marginBottom: '16px'}}>
                        <label>Comment</label>
                        <textarea 
                            style={{width: '100%', minHeight: '80px', padding: '12px', background: 'var(--bg-surface)', border: '1px solid var(--border-light)', borderRadius: 'var(--radius-sm)', color: 'var(--text-primary)', fontFamily: 'var(--font-family)'}}
                            placeholder="Share your experience..."
                            value={comment}
                            onChange={(e) => setComment(e.target.value)}
                        />
                    </div>
                    <button type="submit" className="btn-primary" disabled={submitting} style={{display: 'flex', alignItems: 'center', gap: '8px', width: 'auto'}}>
                        <Send size={16} /> {submitting ? 'Submitting...' : 'Submit Review'}
                    </button>
                </form>
            ) : null}

            {loading && restaurantId !== null ? (
                <div className="loading-spinner">
                    <div className="spinner"></div>
                </div>
            ) : displayReviews.length === 0 ? (
                <div className="empty-state">
                    <Star className="empty-icon" />
                    <p>No reviews yet. Be the first to review!</p>
                </div>
            ) : (
                <div className="reviews-list" style={{display: 'flex', flexDirection: 'column', gap: '16px'}}>
                    {displayReviews.map(review => (
                        <div key={review.reviewId} className="review-card" style={{padding: '20px', background: 'var(--bg-surface)', border: '1px solid var(--border-light)', borderRadius: 'var(--radius-md)'}}>
                            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom: '8px'}}>
                                <strong style={{color: 'var(--text-primary)'}}>{review.username}</strong>
                                <span style={{color: 'var(--text-muted)', fontSize: '13px'}}>{formatDate(review.createdAt)}</span>
                            </div>
                            <div style={{display: 'flex', gap: '4px', marginBottom: '12px'}}>
                                {[...Array(5)].map((_, i) => (
                                    <Star 
                                        key={i} 
                                        size={14} 
                                        fill={i < review.rating ? 'var(--color-warning)' : 'transparent'} 
                                        color={i < review.rating ? 'var(--color-warning)' : 'var(--text-muted)'}
                                    />
                                ))}
                            </div>
                            {review.comment && <p style={{color: 'var(--text-secondary)', fontSize: '15px'}}>{review.comment}</p>}
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default ReviewSection;
